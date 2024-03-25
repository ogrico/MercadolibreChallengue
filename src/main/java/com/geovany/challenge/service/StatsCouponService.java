package com.geovany.challenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geovany.challenge.dto.stats.ResponseStats;
import com.geovany.challenge.dto.stats.StatsCoupon;
import com.sun.tools.javac.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatsCouponService {

    private static final Logger logger = LoggerFactory.getLogger(CouponService.class);

    private StatsCoupon[] topFive(StatsCoupon[] statsCoupons) {

        Map<String, StatsCoupon> map = new HashMap<>();

        // Se itera el array de StatsCoupon
        for (StatsCoupon coupon : statsCoupons) {
            // Si el id ya está en el Map, sumamos la cantidad al objeto StatsCoupon existente
            if (map.containsKey(coupon.getId())) {
                StatsCoupon existingCoupon = map.get(coupon.getId());
                existingCoupon.setQuantity(existingCoupon.getQuantity() + coupon.getQuantity());
            } else {
                // Si el id no está en el Map, simplemente lo añadimos
                map.put(coupon.getId(), coupon);
            }
        }

        StatsCoupon[] filterStatsCoupons = map.values().toArray(new StatsCoupon[0]);
        Arrays.sort(filterStatsCoupons, Comparator.comparingInt(StatsCoupon::getQuantity).reversed());
        int index = filterStatsCoupons.length;

        if (filterStatsCoupons.length > 4) {
            index = 5;
        }
        StatsCoupon[] tempResponse = new StatsCoupon[index];
        for (int i = 0; i < index; i++) {
            tempResponse[i] = filterStatsCoupons[i];
        }
        return tempResponse;


    }

    public ResponseEntity<ResponseStats> getStats() {

        ResponseStats responseStats = new ResponseStats();

        try {

            URL resourceUrl = Main.class.getClassLoader().getResource("StatsCoupon.json");

            if (resourceUrl != null) {
                String filePath = resourceUrl.getPath();
                if (!Files.exists(Paths.get(filePath))) {
                    responseStats.setMsg("Registros no encontrados");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseStats);
                }
                ObjectMapper objectMapper = new ObjectMapper();
                StatsCoupon[] statsCoupons = objectMapper.readValue(new File(filePath), StatsCoupon[].class);

                if (statsCoupons == null || statsCoupons.length == 0) {
                    responseStats.setMsg("No existen registros para mostrar");
                    return ResponseEntity.status(HttpStatus.OK).body(responseStats);
                }

                responseStats.setMsg("Top 5 productos más favoritos");
                responseStats.setStatsCoupon(topFive(statsCoupons));
                return ResponseEntity.status(HttpStatus.OK).body(responseStats);
            } else {
                logger.error("Error en la lectura de los registros");
                responseStats.setMsg("Error en la lectura de los registros");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseStats);
            }


        } catch (IOException e) {
            logger.error("Error en la lectura de los registros", e);
            responseStats.setMsg("Error en la lectura de los registros");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseStats);
        }
    }

}
