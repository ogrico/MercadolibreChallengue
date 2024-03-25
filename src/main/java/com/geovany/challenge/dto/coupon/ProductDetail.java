package com.geovany.challenge.dto.coupon;

import java.math.BigDecimal;

public class ProductDetail implements Comparable<ProductDetail>{
    private String id;
    private BigDecimal price;
    private String title;

    private Number quantity;

    public ProductDetail() {
    }

    public ProductDetail(String id, BigDecimal price, String title) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.quantity = 1;
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

    public Number getQuantity() {
        return quantity;
    }

    public void setQuantity(Number quantity) {
        this.quantity = quantity;
    }

    @Override
    public int compareTo(ProductDetail other) {
        return this.id.compareTo(other.getId());
    }
}
