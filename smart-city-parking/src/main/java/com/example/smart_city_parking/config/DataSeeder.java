package com.example.smart_city_parking.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {
            // Check if parking lots are already present
            String checkQuery = "SELECT COUNT(*) FROM ParkingLot";
            Integer count = jdbcTemplate.queryForObject(checkQuery, Integer.class);

            if (count == 0) {
                // Insert Parking Lots
                String insertLot1 = "INSERT INTO ParkingLot (location, capacity, pricing_structure, types_of_spots) VALUES (?, ?, ?, ?)";
                jdbcTemplate.update(insertLot1, "Downtown", 100, "Hourly", "Regular, Disabled, EV Charging");

                String insertLot2 = "INSERT INTO ParkingLot (location, capacity, pricing_structure, types_of_spots) VALUES (?, ?, ?, ?)";
                jdbcTemplate.update(insertLot2, "Airport", 200, "Flat Rate", "Regular, Disabled, EV Charging");

                // Insert Parking Spots for each lot
                for (int i = 1; i <= 100; i++) {
                    String insertSpotLot1 = "INSERT INTO ParkingSpot (lot_id, type, status) VALUES (?, ?, ?)";
                    jdbcTemplate.update(insertSpotLot1, 1, "Regular", "Available");
                }

                for (int i = 1; i <= 200; i++) {
                    String insertSpotLot2 = "INSERT INTO ParkingSpot (lot_id, type, status) VALUES (?, ?, ?)";
                    jdbcTemplate.update(insertSpotLot2, 2, "EV Charging", "Available");
                }

                System.out.println("Database has been seeded with initial data.");
            } else {
                System.out.println("Database already contains data. Skipping seeding.");
            }
        };
    }
}
