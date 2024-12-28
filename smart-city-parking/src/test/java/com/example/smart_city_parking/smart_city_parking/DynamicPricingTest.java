package com.example.smart_city_parking.smart_city_parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional  // Ensures that each test method runs in a transaction that rolls back at the end of the test
public class DynamicPricingTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private int parkingLotId;
    private int parkingSpotId1;

    @BeforeEach
    public void setUp() {
        // Clean up and set up initial data before each test
        setupInitialData();
    }

    @Test
    public void testDynamicPricing() {
        // Step 1: Insert a reservation to trigger dynamic pricing
        insertReservation(1, parkingSpotId1);  // User 1 reserves Spot 1
        checkDynamicPricing(parkingSpotId1);  // Check dynamic pricing for Spot 1

        // Step 2: Simulate 50% occupancy and check dynamic pricing
        simulateHighOccupancy(50);  // Simulate 50% occupancy
        checkDynamicPricing(parkingSpotId1);  // Check dynamic pricing after 50% occupancy

        // Step 3: Simulate 40% additional occupancy and check dynamic pricing
        simulateHighOccupancy(40);  // Simulate 40% additional occupancy
        checkDynamicPricing(parkingSpotId1);  // Check dynamic pricing after 90% occupancy
    }

    private void setupInitialData() {
        // Insert a parking lot
        String insertParkingLot = "INSERT INTO ParkingLot (location, capacity, pricing_structure, types_of_spots, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertParkingLot, "Downtown", 100, "Standard", "Regular, EV Charging", 40.7128, -74.0060);
    
        // Get the last inserted Parking Lot ID
        String getLastParkingLotId = "SELECT LAST_INSERT_ID()";
        parkingLotId = jdbcTemplate.queryForObject(getLastParkingLotId, Integer.class);

        // Insert Parking Spots for the Parking Lot
        for (int i = 1; i <= 100; i++) {
            String insertParkingSpot = "INSERT INTO ParkingSpot (lot_id, status, type) VALUES (?, 'Available', 'Regular')";
            jdbcTemplate.update(insertParkingSpot, parkingLotId);
        }

        // Get the ID of the first inserted parking spot
        String getLastParkingSpotId1 = "SELECT LAST_INSERT_ID()";
        parkingSpotId1 = jdbcTemplate.queryForObject(getLastParkingSpotId1, Integer.class);

        // Insert Dynamic Pricing for the parking spots
        String insertDynamicPricing = "INSERT INTO DynamicPricing (spot_id, base_price, location_factor) VALUES (?, ?, ?)";
        for (int i = parkingSpotId1 - 99; i <= parkingSpotId1; i++) {
            jdbcTemplate.update(insertDynamicPricing, i, new BigDecimal("10.00"), new BigDecimal("1.00"));
        }
    }
    
    private void insertReservation(int userId, int spotId) {
        // Insert a reservation for a user and spot
        String insertReservation = "INSERT INTO Reservation (user_id, spot_id, start_time, end_time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertReservation, userId, spotId, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusHours(2)));
    }

    private void checkDynamicPricing(int spotId) {
        // Query the DynamicPricing table to check if the peak_factor is updated
        String selectDynamicPricing = "SELECT peak_factor FROM DynamicPricing WHERE spot_id = ?";
        Double peakFactor = jdbcTemplate.queryForObject(selectDynamicPricing, Double.class, spotId);

        // Assert that peak_factor is not null (you can add further assertions here)
        assertNotNull(peakFactor, "Peak factor should not be null for spot: " + spotId);
        System.out.println("Spot " + spotId + " Peak Factor: " + peakFactor);
    }

    private void simulateHighOccupancy(int percentage) {
        // Insert reservations to simulate high occupancy for a given percentage
        int spotsToReserve = (int) (100 * (percentage / 100.0));  // Calculate the number of spots to reserve
        for (int i = 1; i <= spotsToReserve; i++) {
            insertReservation(1, i);  // Each user reserves a spot
        }
    }
}
