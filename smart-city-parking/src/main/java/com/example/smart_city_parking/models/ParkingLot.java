package com.example.smart_city_parking.models;

public class ParkingLot {
    private int lotId;
    private String location;
    private int capacity;
    private String pricingStructure;
    private String typesOfSpots;
    private double latitude;  // Latitude of the parking lot
    private double longitude; // Longitude of the parking lot

    // Constructor
    public ParkingLot(int lotId, String location, int capacity, String pricingStructure, String typesOfSpots, double latitude, double longitude) {
        this.lotId = lotId;
        this.location = location;
        this.capacity = capacity;
        this.pricingStructure = pricingStructure;
        this.typesOfSpots = typesOfSpots;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters for all fields
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
