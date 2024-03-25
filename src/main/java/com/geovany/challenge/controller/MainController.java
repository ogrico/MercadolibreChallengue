package com.geovany.challenge.controller;

import com.geovany.challenge.dto.coupon.Coupon;
import com.geovany.challenge.dto.stats.ResponseStats;
import com.geovany.challenge.service.CouponService;
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

    public MainController(CouponService couponService, StatsCouponService statsCouponService) {
        this.couponService = couponService;
        this.statsCouponService = statsCouponService;
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