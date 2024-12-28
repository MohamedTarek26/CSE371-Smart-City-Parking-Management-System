package com.example.smart_city_parking.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DynamicPricing {
    private int spotId;
    private BigDecimal basePrice;
    private String demandLevel; // Low, Medium, High
    private BigDecimal locationFactor;
    private BigDecimal peakFactor;
    private BigDecimal currentPrice;
    private Timestamp lastUpdated;

    // Getters and setters
    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getDemandLevel() {
        return demandLevel;
    }

    public void setDemandLevel(String demandLevel) {
        this.demandLevel = demandLevel;
    }

    public BigDecimal getLocationFactor() {
        return locationFactor;
    }

    public void setLocationFactor(BigDecimal locationFactor) {
        this.locationFactor = locationFactor;
    }

    public BigDecimal getPeakFactor() {
        return peakFactor;
    }

    public void setPeakFactor(BigDecimal peakFactor) {
        this.peakFactor = peakFactor;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
