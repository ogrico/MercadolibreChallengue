package com.geovany.challenge.dto.coupon;

public class Coupon {
    private String[] item_ids;
    private Double amount;

    public Coupon() {
    }
    public Coupon(String[] item_ids, Double amount) {
        this.item_ids = item_ids;
        this.amount = amount;
    }

    public String[] getItem_ids() {
        return item_ids;
    }

    public void setItem_ids(String[] item_ids) {
        this.item_ids = item_ids;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
