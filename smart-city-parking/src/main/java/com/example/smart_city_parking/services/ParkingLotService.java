package com.example.smart_city_parking.services;

import com.example.smart_city_parking.models.ParkingLot;
import com.example.smart_city_parking.models.ParkingSpot;
import com.example.smart_city_parking.models.UserInfo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ParkingLotService {

    private final JdbcTemplate jdbcTemplate;

    public ParkingLotService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to calculate revenue for a specific parking lot directly using JDBC
    public BigDecimal calculateRevenue(int lotId) {
        System.out.println("Calculating revenue for lot ID: " + lotId);

        // SQL query to calculate the revenue by summing up the 'amount' from 'Payment' table
        String sql = """
            SELECT COALESCE(SUM(amount), 0) AS total_revenue
            FROM Payment p
            WHERE p.reservation_id IN (
                SELECT r.reservation_id
                FROM Reservation r
                JOIN ParkingSpot ps ON r.spot_id = ps.spot_id
                WHERE ps.lot_id = ?
            )
        """;

        // Execute the query and get the result as a BigDecimal
        BigDecimal totalRevenue = jdbcTemplate.queryForObject(sql, new Object[]{lotId}, (ResultSet rs, int rowNum) -> {
            return rs.getBigDecimal("total_revenue");
        });

        // If the result is null, return BigDecimal.ZERO
        return totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
    }

    // Method to calculate available spots in a parking lot using JDBC
    public int getAvailableSpots(int lotId) {
        String sql = """
            SELECT COUNT(*) 
            FROM ParkingSpot 
            WHERE lot_id = ? AND status = 'Available'
        """;

        // Query the database and return the result
        return jdbcTemplate.queryForObject(sql, new Object[]{lotId}, Integer.class);
    }

    public int getCapacity(int lotId) {
        String sql = "SELECT capacity FROM ParkingLot WHERE lot_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{lotId}, Integer.class);
    }

    public int getOccupiedSpots(int lotId) {
        int availableSpots = getAvailableSpots(lotId);
        int totalSpots = getCapacity(lotId);
        return totalSpots - availableSpots;
    }

    public double getOccupancyRate(int lotId) {
        int availableSpots = getAvailableSpots(lotId);
        int totalSpots = getCapacity(lotId);
        return (double) (totalSpots - availableSpots) / totalSpots;
    }

    public List<UserInfo> getTopUsersForLot(int lotId, int topN) {
        String query = """
                SELECT u.user_id, u.user_name, u.user_email, COUNT(r.reservation_id) AS reservation_count
                FROM Reservation r
                JOIN Users u ON r.user_id = u.user_id
                JOIN ParkingSpot ps ON r.spot_id = ps.spot_id
                WHERE ps.lot_id = ?
                GROUP BY u.user_id
                ORDER BY reservation_count DESC
                LIMIT ?
                """;

        return jdbcTemplate.query(query, new Object[]{lotId, topN}, (rs, rowNum) -> new UserInfo(
                rs.getInt("user_id"),
                rs.getString("user_name"),
                rs.getString("user_email"),
                rs.getInt("reservation_count")
        ));
    }

    public List<ParkingLot> getAllParkingLots() {
        String sql = "SELECT * FROM ParkingLot";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ParkingLot(
            rs.getInt("lot_id"),
            rs.getString("location"),
            rs.getInt("capacity"),
            rs.getString("pricing_structure"),
            rs.getString("types_of_spots"),
            rs.getDouble("latitude"),
            rs.getDouble("longitude")
        ));
    }

    public ParkingLot getParkingLotById(int lotId) {
        String sql = "SELECT * FROM ParkingLot WHERE lot_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{lotId}, (rs, rowNum) -> new ParkingLot(
            rs.getInt("lot_id"),
            rs.getString("location"),
            rs.getInt("capacity"),
            rs.getString("pricing_structure"),
            rs.getString("types_of_spots"),
            rs.getDouble("latitude"),
            rs.getDouble("longitude")
        ));
    }

    public int addParkingLot(String location, int capacity, String pricingStructure, String typesOfSpots, double latitude, double longitude) {
        // SQL insert query
        String insertQuery = "INSERT INTO ParkingLot (location, capacity, pricing_structure, types_of_spots, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)";
        
        // Execute the insert query
        jdbcTemplate.update(insertQuery, location, capacity, pricingStructure, typesOfSpots, latitude, longitude);
    
        // Retrieve the last inserted lot_id using LAST_INSERT_ID() (for MySQL)
        String selectQuery = "SELECT LAST_INSERT_ID()";
        Integer lotId = jdbcTemplate.queryForObject(selectQuery, Integer.class);
    
        return lotId != null ? lotId : -1;
    }
    

    public String generateNavigationUrl(double latitude, double longitude) {
        return "https://www.google.com/maps/dir/?api=1&destination=" + latitude + "," + longitude;
    }

    public List<ParkingLot> searchByLocation(String location) {
        String sql = "SELECT * FROM ParkingLot WHERE location LIKE ?";
        return jdbcTemplate.query(sql, new Object[]{"%" + location + "%"}, (rs, rowNum) -> new ParkingLot(
            rs.getInt("lot_id"),
            rs.getString("location"),
            rs.getInt("capacity"),
            rs.getString("pricing_structure"),
            rs.getString("types_of_spots"),
            rs.getDouble("latitude"),
            rs.getDouble("longitude")
        ));
    }

    public List<ParkingSpot> getAllSpotsForLot(int lotId) {
    String sql = """
        SELECT ps.spot_id, ps.lot_id, ps.status, ps.type
        FROM ParkingSpot ps
        WHERE ps.lot_id = ?
    """;

    // Query the database and return the result as a list of ParkingSpot objects
    return jdbcTemplate.query(sql, new Object[]{lotId}, (rs, rowNum) -> new ParkingSpot(
        rs.getInt("spot_id"),
        rs.getInt("lot_id"),
        rs.getString("status"),
        rs.getString("type")
    ));
}

}

