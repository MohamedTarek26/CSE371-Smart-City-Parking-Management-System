package com.example.smart_city_parking.services;

import com.example.smart_city_parking.models.ParkingSpot;
import com.example.smart_city_parking.models.ParkingSpotWithPrice;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public String  getNextAvailableTime(int id){
        String sql = "SELECT end_time FROM Reservation WHERE spot_id = ? ORDER BY end_time DESC LIMIT 1";
        //handle if no reservations found
        try {
            String endtime= jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
            if(endtime == null){
                return "available now";
            }
            String currentTime= jdbcTemplate.queryForObject("SELECT NOW()", String.class);
            LocalDateTime currentTimeParsed = LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime endtimeParsed = LocalDateTime.parse(endtime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if(endtimeParsed.isBefore(currentTimeParsed)){
                return "available now";
            }
            return "available at "+endtime;

        } catch (Exception e) {
            return "available now";
        }
     }

     public void createParkingSpots(int n, String type, int lotId) {
        String sql = "INSERT INTO ParkingSpot (lot_id, status, type) VALUES (?, 'Available', ?)";
        for (int i = 0; i < n; i++) {
            jdbcTemplate.update(sql, lotId, type);
        }
    }

}
