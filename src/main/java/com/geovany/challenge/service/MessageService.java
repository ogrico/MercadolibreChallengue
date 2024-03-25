package com.geovany.challenge.service;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public ResponseEntity<String> message() {

        String json = "{\"Metodo coupon\":\"localhost:8084/api/challengeMercadolibre/coupon\", \"Metodo stats\":\"localhost:8084/api/challengeMercadolibre/coupon/stats\"}";

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(json);
    }

}
