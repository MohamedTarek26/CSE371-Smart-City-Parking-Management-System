package com.example.smart_city_parking.services;

import com.example.smart_city_parking.models.ParkingLot;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotService {

    private final JdbcTemplate jdbcTemplate;

    public ParkingLotService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ParkingLot> getAllParkingLots() {
        String sql = "SELECT * FROM ParkingLot";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ParkingLot(
            rs.getInt("lot_id"),
            rs.getString("location"),
            rs.getInt("capacity"),
            rs.getString("pricing_structure"),
            rs.getString("types_of_spots")
        ));
    }

    public ParkingLot getParkingLotById(int lotId) {
        String sql = "SELECT * FROM ParkingLot WHERE lot_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{lotId}, (rs, rowNum) -> new ParkingLot(
            rs.getInt("lot_id"),
            rs.getString("location"),
            rs.getInt("capacity"),
            rs.getString("pricing_structure"),
            rs.getString("types_of_spots")
        ));
    }

        // Add a new parking lot
        public int addParkingLot(String location, int capacity, String pricingStructure, String typesOfSpots) {
            String query = "INSERT INTO ParkingLot (location, capacity, pricing_structure, types_of_spots) VALUES (?, ?, ?, ?)";
            return jdbcTemplate.update(query, location, capacity, pricingStructure, typesOfSpots);
        }
}
