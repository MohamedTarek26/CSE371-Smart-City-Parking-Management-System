package com.example.smart_city_parking.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {
            // Check if parking lots are already present
            String checkQuery = "SELECT COUNT(*) FROM ParkingLot";
            Integer count = jdbcTemplate.queryForObject(checkQuery, Integer.class);

            if (count == 0) {
                // Insert Parking Lots first
                String insertLot1 = "INSERT INTO ParkingLot (location, capacity, pricing_structure, types_of_spots) VALUES (?, ?, ?, ?)";
                jdbcTemplate.update(insertLot1, "Downtown", 100, "Hourly", "Regular, Disabled, EV Charging");

                String insertLot2 = "INSERT INTO ParkingLot (location, capacity, pricing_structure, types_of_spots) VALUES (?, ?, ?, ?)";
                jdbcTemplate.update(insertLot2, "Airport", 200, "Flat Rate", "Regular, Disabled, EV Charging");

                System.out.println("Database has been seeded with 2 parking lots.");

                // After inserting the ParkingLots, insert Parking Spots for each lot
                // Insert 100 spots for lot 1
                for (int i = 1; i <= 100; i++) {
                    String insertSpotLot1 = "INSERT INTO ParkingSpot (lot_id, type, status) VALUES (?, ?, ?)";
                    jdbcTemplate.update(insertSpotLot1, 1, "Regular", "Available");
                }
                System.out.println("Database has been seeded with 100 parking spots for lot 1.");

                // Insert 200 spots for lot 2
                for (int i = 1; i <= 200; i++) {
                    String insertSpotLot2 = "INSERT INTO ParkingSpot (lot_id, type, status) VALUES (?, ?, ?)";
                    jdbcTemplate.update(insertSpotLot2, 2, "EV Charging", "Available");
                }
                System.out.println("Database has been seeded with 200 parking spots for lot 2.");

                // Insert Dynamic Pricing for Parking Spots
                for (int i = 1; i <= 100; i++) {
                    String insertDynamicPricingLot1 = "INSERT INTO DynamicPricing (spot_id, price, demand_level) VALUES (?, ?, ?)";
                    jdbcTemplate.update(insertDynamicPricingLot1, i, new BigDecimal("5.00"), "Low");
                }
                System.out.println("Database has been seeded with dynamic pricing for 100 parking spots in lot 1.");

                for (int i = 101; i <= 200; i++) {
                    String insertDynamicPricingLot2 = "INSERT INTO DynamicPricing (spot_id, price, demand_level) VALUES (?, ?, ?)";
                    jdbcTemplate.update(insertDynamicPricingLot2, i, new BigDecimal("7.50"), "Medium");
                }
                System.out.println("Database has been seeded with dynamic pricing for 200 parking spots in lot 2.");

                // Insert Users
                String insertUser1 = "INSERT INTO Users (user_name, user_email, user_phone, license_plate, payment_method) VALUES (?, ?, ?, ?, ?)";
                jdbcTemplate.update(insertUser1, "John Doe", "john@example.com", "555-1234", "ABC123", "Credit Card");

                String insertUser2 = "INSERT INTO Users (user_name, user_email, user_phone, license_plate, payment_method) VALUES (?, ?, ?, ?, ?)";
                jdbcTemplate.update(insertUser2, "Jane Smith", "jane@example.com", "555-5678", "XYZ456", "Debit Card");

                System.out.println("Database has been seeded with 2 users.");

                // Insert Reservations
                String insertReservation1 = "INSERT INTO Reservation (user_id, spot_id, start_time, end_time, status, penalty) VALUES (?, ?, ?, ?, ?, ?)";
                String insertReservation2 = "INSERT INTO Reservation (user_id, spot_id, start_time, end_time, status, penalty) VALUES (?, ?, ?, ?, ?, ?)";

                jdbcTemplate.update(insertReservation1, 1, 1, Timestamp.valueOf(LocalDateTime.now().plusHours(1)), Timestamp.valueOf(LocalDateTime.now().plusHours(2)), "Reserved", new BigDecimal("0.00"));
                jdbcTemplate.update(insertReservation2, 2, 2, Timestamp.valueOf(LocalDateTime.now().plusHours(3)), Timestamp.valueOf(LocalDateTime.now().plusHours(4)), "Reserved", new BigDecimal("0.00"));
                
                System.out.println("Database has been seeded with 2 reservations.");

                // Insert Payments
                String insertPayment1 = "INSERT INTO Payment (reservation_id, amount, payment_method, transaction_date) VALUES (?, ?, ?, ?)";
                jdbcTemplate.update(insertPayment1, 1, new BigDecimal("10.00"), "Credit_card", Timestamp.valueOf(LocalDateTime.now()));
                
                String insertPayment2 = "INSERT INTO Payment (reservation_id, amount, payment_method, transaction_date) VALUES (?, ?, ?, ?)";
                jdbcTemplate.update(insertPayment2, 2, new BigDecimal("15.00"), "Debit_card", Timestamp.valueOf(LocalDateTime.now()));
                
                System.out.println("Database has been seeded with 2 payments.");
                System.out.println("Database has been seeded with initial data.");
            } else {
                System.out.println("Database already contains data. Skipping seeding.");
            }
        };
    }
}
