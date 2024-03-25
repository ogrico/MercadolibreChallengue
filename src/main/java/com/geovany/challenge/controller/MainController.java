package com.geovany.challenge.controller;

import com.geovany.challenge.dto.coupon.Coupon;
import com.geovany.challenge.dto.stats.ResponseStats;
import com.geovany.challenge.service.CouponService;
import com.geovany.challenge.service.MessageService;
import com.geovany.challenge.service.StatsCouponService;
import com.geovany.challenge.dto.coupon.ResponseCoupon;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final CouponService couponService;
    public final StatsCouponService statsCouponService;

    private final MessageService messageService;

    public MainController(CouponService couponService, StatsCouponService statsCouponService, MessageService messageService) {
        this.couponService = couponService;
        this.statsCouponService = statsCouponService;
        this.messageService = messageService;
    }

    @GetMapping("/message")
    public ResponseEntity<String> message(){
        return messageService.message();
    }

    @PostMapping("/coupon")
    public ResponseEntity<ResponseCoupon> coupon(@RequestBody Coupon data) {
        return couponService.applyCoupon(data);
    }

    @GetMapping("/coupon/stats")
    public ResponseEntity<ResponseStats> getStats() {
        return statsCouponService.getStats();
    }

}