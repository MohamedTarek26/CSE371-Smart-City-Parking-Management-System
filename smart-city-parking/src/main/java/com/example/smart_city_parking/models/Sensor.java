package com.example.smart_city_parking.models;

import java.sql.Timestamp;

public class Sensor {
    private int sensorId;
    private int spotId;
    private String status;
    private Timestamp lastUpdated;

    // Constructor
    public Sensor(int sensorId, int spotId, String status, Timestamp lastUpdated) {
        this.sensorId = sensorId;
        this.spotId = spotId;
        this.status = status;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters
    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
