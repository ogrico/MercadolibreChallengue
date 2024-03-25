package com.geovany.challenge.dto.stats;

import java.math.BigDecimal;

public class StatsCoupon implements Comparable<StatsCoupon>{
    private String id;
    private BigDecimal price;
    private String title;
    private int quantity;

    public StatsCoupon() {
    }

    public StatsCoupon(String id, BigDecimal price, String title, int quantity) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int compareTo(StatsCoupon other) {
        return Integer.compare(this.quantity, other.quantity);
    }

}
