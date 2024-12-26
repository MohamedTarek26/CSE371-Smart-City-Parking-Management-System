package com.example.smart_city_parking.services;

import com.example.smart_city_parking.models.DynamicPricing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DynamicPricingService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DynamicPricing> getAllDynamicPricing() {
        String query = "SELECT * FROM DynamicPricing";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            DynamicPricing pricing = new DynamicPricing();
            pricing.setSpotId(rs.getInt("spot_id"));
            pricing.setBasePrice(rs.getBigDecimal("base_price"));
            pricing.setDemandLevel(rs.getString("demand_level"));
            pricing.setLocationFactor(rs.getBigDecimal("location_factor"));
            pricing.setPeakFactor(rs.getBigDecimal("peak_factor"));
            pricing.setCurrentPrice(rs.getBigDecimal("current_price"));
            pricing.setLastUpdated(rs.getTimestamp("last_updated"));
            return pricing;
        });
    }

    public void updatePeakFactor(int lotId) {
        // Get the total number of spots in the lot
        String totalSpotsQuery = "SELECT COUNT(*) FROM ParkingSpot WHERE lot_id = ?";
        int totalSpots = jdbcTemplate.queryForObject(totalSpotsQuery, Integer.class, lotId);

        // Get the number of reserved spots in the lot
        String reservedSpotsQuery = "SELECT COUNT(*) FROM Reservation r JOIN ParkingSpot ps ON r.spot_id = ps.spot_id WHERE ps.lot_id = ? AND r.status = 'Reserved'";
        int reservedSpots = jdbcTemplate.queryForObject(reservedSpotsQuery, Integer.class, lotId);

        BigDecimal peakFactor = BigDecimal.valueOf(1.0);
        if ((double) reservedSpots / totalSpots > 0.8) {
            peakFactor = BigDecimal.valueOf(2.0);
        } else if ((double) reservedSpots / totalSpots > 0.5) {
            peakFactor = BigDecimal.valueOf(1.5);
        }

        // Update peak factor for all spots in the lot
        String updateQuery = "UPDATE DynamicPricing dp JOIN ParkingSpot ps ON dp.spot_id = ps.spot_id SET dp.peak_factor = ? WHERE ps.lot_id = ?";
        jdbcTemplate.update(updateQuery, peakFactor, lotId);
    }
}
