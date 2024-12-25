package com.example.smart_city_parking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ParkingLotService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Retrieve all parking lots
    public List<Map<String, Object>> getAllParkingLots() {
        String query = "SELECT * FROM ParkingLot";
        return jdbcTemplate.queryForList(query);
    }

    // Add a new parking lot
    public int addParkingLot(String location, int capacity, String pricingStructure, String typesOfSpots) {
        String query = "INSERT INTO ParkingLot (location, capacity, pricing_structure, types_of_spots) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(query, location, capacity, pricingStructure, typesOfSpots);
    }
}
