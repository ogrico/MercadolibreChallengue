package com.geovany.challenge.service;

import com.geovany.challenge.dto.coupon.Coupon;
import com.geovany.challenge.dto.coupon.ProductDetail;
import com.geovany.challenge.dto.coupon.ResponseCoupon;
import com.mashape.unirest.http.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class CouponService {

    private static final Logger logger = LoggerFactory.getLogger(CouponService.class);

    public ResponseEntity<ResponseCoupon> applyCoupon(Coupon coupon) {

        String[] items = filterItems(coupon.getItem_ids());
        ProductDetail[] productDetails = getPriceItems(items);
        writeJson(productDetails);
        Arrays.sort(productDetails, Comparator.comparing(ProductDetail::getPrice));
        BigDecimal remainingCoupon = new BigDecimal(coupon.getAmount());
        int totalItems = 0;
        BigDecimal totalValue = BigDecimal.ZERO;

        if (items.length == 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseCoupon(new String[0], totalValue));
        }

        for (ProductDetail product : productDetails) {
            // Validación para ver si el saldo del coupon es mayor o igual que el del item
            if (remainingCoupon.compareTo(product.getPrice()) >= 0) {
                totalValue = totalValue.add(product.getPrice());
                remainingCoupon = remainingCoupon.subtract(product.getPrice());
                totalItems++;
            } else {
                break;
            }
        }

        // Se crea el array con los items que puede comprar
        String[] finalItems = new String[totalItems];
        for (int k = 0; k < totalItems; k++) {
            finalItems[k] = productDetails[k].getId();
        }

        ResponseCoupon responseCoupon = new ResponseCoupon(finalItems, totalValue);

        return ResponseEntity.status(HttpStatus.OK).body(responseCoupon);
    }

    private String[] filterItems(String[] items) {
        Arrays.sort(items);
        return Arrays.stream(items).distinct().toArray(String[]::new);
    }

    private ProductDetail[] getPriceItems(String[] items) {
        CompletableFuture<ProductDetail>[] futures = getPriceItemsAsync(items);

        // Se espera y recopilan los resultados de las consultas de precios y se excluyen las respuesas en null
        return Arrays.stream(futures).map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .toArray(ProductDetail[]::new);
    }

    private void writeJson(ProductDetail[] newProductDetails) {
        try {
            String filePath = "src/main/resources/StatsCoupon.json";
            ObjectMapper objectMapper = new ObjectMapper();

            // Se crear un array de ProductDetail con el contenido del archivo json
            ProductDetail[] currentProductDetails = objectMapper.readValue(new File(filePath), ProductDetail[].class);
            // Se exiende la longitud del arry actual má la cantidad de elementos que contiene la variable newProductDetails
            ProductDetail[] tempProductDetails = Arrays.copyOf(currentProductDetails, currentProductDetails.length + newProductDetails.length);
            // Se copia los elementos del array newProductDetails en el tempProductDetails generado anteriormente
            System.arraycopy(newProductDetails, 0, tempProductDetails, currentProductDetails.length, newProductDetails.length);

            objectMapper.writeValue(new File(filePath), tempProductDetails);

        } catch (IOException e) {
            logger.error("Error trabajando con el archivo json", e);
        }
    }

    private CompletableFuture<ProductDetail>[] getPriceItemsAsync(String[] items) {
        CompletableFuture<ProductDetail>[] pendingPrices = new CompletableFuture[items.length];

        for (int i = 0; i < items.length; i++) {
            String itemId = items[i];
            CompletableFuture<ProductDetail> pendingPrice = CompletableFuture.supplyAsync(() -> {
                try {
                    HttpResponse<JsonNode> response = Unirest.get("https://api.mercadolibre.com/items/" + itemId).header("Content-Type", "application/json").asJson();

                    int status = response.getStatus();
                    if (status == 200) {
                        JsonNode body = response.getBody();
                        BigDecimal price = body.getObject().getBigDecimal("price");
                        String title = body.getObject().getString("title");
                        return new ProductDetail(itemId, price, title);
                    } else {
                        return null;
                    }
                } catch (UnirestException e) {
                    logger.error("Ocurrió un error durante el consumo del servicio https://api.mercadolibre.com \n" + e);
                    return null;
                }
            });
            pendingPrices[i] = pendingPrice;
        }

        return pendingPrices;
    }

}
