package com.example.smart_city_parking.services;

import com.example.smart_city_parking.models.ParkingLot;
import com.example.smart_city_parking.models.UserInfo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Service
public class ParkingLotService {

    private final JdbcTemplate jdbcTemplate;
   private final SimpleJdbcCall simpleJdbcCallForAvailableSpots;
    private final SimpleJdbcCall simpleJdbcCallForRevenue;

    public ParkingLotService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        // Initialize SimpleJdbcCall for GetAvailableSpots stored procedure
        this.simpleJdbcCallForAvailableSpots = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetAvailableSpots")
                .declareParameters(
                        new SqlOutParameter("available_count", Types.INTEGER)
                );

        // Initialize SimpleJdbcCall for CalculateRevenue stored procedure
        this.simpleJdbcCallForRevenue = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CalculateRevenue")
                .declareParameters(
                        new SqlOutParameter("total_revenue", Types.DECIMAL)
                );
    }

    // Method to call GetAvailableSpots stored procedure
    public int getAvailableSpots(int lotId) {
        // Execute the stored procedure with the input parameter
        Map<String, Object> result = simpleJdbcCallForAvailableSpots.execute(Map.of("lot_id", lotId));

        // Retrieve the output parameter
        return (Integer) result.get("available_count");
    }

    // Method to call CalculateRevenue stored procedure
    public BigDecimal calculateRevenue(int lotId) {
        System.out.println("Calculating revenue for lot ID: " + lotId);
        // Execute the stored procedure with the input parameter
        Map<String, Object> result = simpleJdbcCallForRevenue.execute(Map.of("lot_id", lotId));
        if (result == null) {
            result = Map.of("total_revenue", BigDecimal.ZERO);
        }
        System.out.println("Result: " + result);
        if (result.get("total_revenue") == null) {
            return BigDecimal.ZERO;
        }
        // Retrieve the output parameter
        return (BigDecimal) result.get("total_revenue");
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

        // Method to get the top users for a parking lot
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

    // Get all parking lots
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

    // Get parking lot by ID
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

    // Add a new parking lot
    public int addParkingLot(String location, int capacity, String pricingStructure, String typesOfSpots, double latitude, double longitude) {
        String query = "INSERT INTO ParkingLot (location, capacity, pricing_structure, types_of_spots, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(query, location, capacity, pricingStructure, typesOfSpots, latitude, longitude);
    }

    // Method to generate the Google Maps navigation URL based on latitude and longitude
    public String generateNavigationUrl(double latitude, double longitude) {
        return "https://www.google.com/maps/dir/?api=1&destination=" + latitude + "," + longitude;
    }

    // Search parking lots by location
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
}
