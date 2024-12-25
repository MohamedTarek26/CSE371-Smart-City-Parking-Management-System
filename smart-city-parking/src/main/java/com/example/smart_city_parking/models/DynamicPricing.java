package com.example.smart_city_parking.models;

import java.math.BigDecimal;

public class DynamicPricing {

    private int pricingId;
    private int spotId;
    private BigDecimal price;
    private String demandLevel;

    // Constructor
    public DynamicPricing(int pricingId, int spotId, BigDecimal price, String demandLevel) {
        this.pricingId = pricingId;
        this.spotId = spotId;
        this.price = price;
        this.demandLevel = demandLevel;
    }

    // Getters and Setters
    public int getPricingId() {
        return pricingId;
    }

    public void setPricingId(int pricingId) {
        this.pricingId = pricingId;
    }

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDemandLevel() {
        return demandLevel;
    }

    public void setDemandLevel(String demandLevel) {
        this.demandLevel = demandLevel;
    }
}
