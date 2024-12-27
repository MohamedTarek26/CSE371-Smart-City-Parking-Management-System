package com.example.smart_city_parking.services;

import com.example.smart_city_parking.models.ParkingSpot;
import com.example.smart_city_parking.models.ParkingSpotWithPrice;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpotService {

    private final JdbcTemplate jdbcTemplate;

    public ParkingSpotService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ParkingSpot> getAllParkingSpots() {
        String sql = "SELECT * FROM ParkingSpot";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ParkingSpot(
            rs.getInt("spot_id"),
            rs.getInt("lot_id"),
            rs.getString("status"),
            rs.getString("type")
        ));
    }

    public ParkingSpot getParkingSpotById(int spotId) {
        String sql = "SELECT * FROM ParkingSpot WHERE spot_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{spotId}, (rs, rowNum) -> new ParkingSpot(
            rs.getInt("spot_id"),
            rs.getInt("lot_id"),
            rs.getString("status"),
            rs.getString("type")
        ));
    }

    public void updateParkingSpotStatus(int spotId, String status) {
        String sql = "UPDATE ParkingSpot SET status = ? WHERE spot_id = ?";
        jdbcTemplate.update(sql, status, spotId);
    }

    public ParkingSpotWithPrice returnSpotWithPrice(int id){
        String sql = "SELECT * FROM ParkingSpot WHERE spot_id = ?";
        String sql2 = "SELECT price FROM DynamicPricing WHERE spot_id = ?";
        ParkingSpot spot = jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new ParkingSpot(
            rs.getInt("spot_id"),
            rs.getInt("lot_id"),
            rs.getString("status"),
            rs.getString("type")
        ));
        int price = jdbcTemplate.queryForObject(sql2, new Object[]{id}, Integer.class);
        return new ParkingSpotWithPrice(spot.getSpotId(), spot.getLotId(), spot.getStatus(), spot.getType(), price);
    }
}
