package com.example.smart_city_parking.models;

public class ParkingLot {

    private int lotId;
    private String location;
    private int capacity;
    private String pricingStructure;
    private String typesOfSpots;

    // Constructor
    public ParkingLot(int lotId, String location, int capacity, String pricingStructure, String typesOfSpots) {
        this.lotId = lotId;
        this.location = location;
        this.capacity = capacity;
        this.pricingStructure = pricingStructure;
        this.typesOfSpots = typesOfSpots;
    }

    // Getters and Setters
    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getPricingStructure() {
        return pricingStructure;
    }

    public void setPricingStructure(String pricingStructure) {
        this.pricingStructure = pricingStructure;
    }

    public String getTypesOfSpots() {
        return typesOfSpots;
    }

    public void setTypesOfSpots(String typesOfSpots) {
        this.typesOfSpots = typesOfSpots;
    }
}
