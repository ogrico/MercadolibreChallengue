package com.geovany.challenge.dto.coupon;

import java.math.BigDecimal;

public class ResponseCoupon {
    private String[] item_ids;
    private BigDecimal total;

    public ResponseCoupon() {
    }

    public ResponseCoupon(String[] item_ids, BigDecimal total) {
        this.item_ids = item_ids;
        this.total = total;
    }

    public String[] getItem_ids() {
        return item_ids;
    }

    public void setItem_ids(String[] item_ids) {
        this.item_ids = item_ids;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
