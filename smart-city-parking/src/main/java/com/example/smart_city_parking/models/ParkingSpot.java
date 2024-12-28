package com.example.smart_city_parking.models;

public class ParkingSpot {

    private int spotId;
    private int lotId;
    private String status;
    private String type;

    // Constructor
    public ParkingSpot(int spotId, int lotId, String status, String type) {
        this.spotId = spotId;
        this.lotId = lotId;
        this.status = status;
        this.type = type;
    }

    // Getters and Setters
    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
