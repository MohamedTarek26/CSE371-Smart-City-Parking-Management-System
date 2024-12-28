package com.example.smart_city_parking.services;

import com.example.smart_city_parking.models.DynamicPricing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DynamicPricingService {

    private final JdbcTemplate jdbcTemplate;

    // Constructor injection of JdbcTemplate
    public DynamicPricingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to map the result set to a DynamicPricing object
    private static final RowMapper<DynamicPricing> dynamicPricingRowMapper = (rs, rowNum) -> {
        int pricingId = rs.getInt("pricing_id");
        int spotId = rs.getInt("spot_id");
        BigDecimal price = rs.getBigDecimal("price");
        String demandLevel = rs.getString("demand_level");

        return new DynamicPricing(pricingId, spotId, price, demandLevel);
    };

    // Fetch all dynamic pricing for a parking spot
    public List<DynamicPricing> getDynamicPricingBySpotId(int spotId) {
        String query = "SELECT * FROM DynamicPricing WHERE spot_id = ?";
        return jdbcTemplate.query(query, new Object[]{spotId}, dynamicPricingRowMapper);
    }

    // Set dynamic pricing for a parking spot
    public boolean setDynamicPricing(DynamicPricing dynamicPricing) {
        String query = "INSERT INTO DynamicPricing (spot_id, price, demand_level) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(query, dynamicPricing.getSpotId(), dynamicPricing.getPrice(), dynamicPricing.getDemandLevel());
        return rowsAffected > 0;
    }

    // Update dynamic pricing for a parking spot
    public boolean updateDynamicPricing(DynamicPricing dynamicPricing) {
        String query = "UPDATE DynamicPricing SET price = ?, demand_level = ? WHERE pricing_id = ?";
        int rowsAffected = jdbcTemplate.update(query, dynamicPricing.getPrice(), dynamicPricing.getDemandLevel(), dynamicPricing.getPricingId());
        return rowsAffected > 0;
    }
}
