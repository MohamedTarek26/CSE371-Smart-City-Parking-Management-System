package com.example.smart_city_parking.services;

import com.example.smart_city_parking.models.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReservationService {

    private final JdbcTemplate jdbcTemplate;

    public ReservationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getAllReservations() {
        String sql = "SELECT * FROM Reservation";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Reservation(
            rs.getInt("reservation_id"),
            rs.getInt("user_id"),
            rs.getInt("spot_id"),
            rs.getTimestamp("start_time"),
            rs.getTimestamp("end_time"),
            rs.getString("status"),
            rs.getBigDecimal("penalty")
        ));
    }

    public Reservation getReservationById(int reservationId) {
        String sql = "SELECT * FROM Reservation WHERE reservation_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{reservationId}, (rs, rowNum) -> new Reservation(
            rs.getInt("reservation_id"),
            rs.getInt("user_id"),
            rs.getInt("spot_id"),
            rs.getTimestamp("start_time"),
            rs.getTimestamp("end_time"),
            rs.getString("status"),
            rs.getBigDecimal("penalty")
        ));
    }

    public void createReservation(int userId, int spotId, String startTime, String endTime) {
        // Convert start and end times to Timestamp
        Timestamp startTimestamp = Timestamp.valueOf(startTime);
        Timestamp endTimestamp = Timestamp.valueOf(endTime);
        
        // Check for overlapping reservations
        String overlapCheckSql = "SELECT COUNT(*) FROM Reservation WHERE spot_id = ? " +
                "AND (start_time < ? AND end_time > ?)"; // Check for any overlap
        int overlappingCount = jdbcTemplate.queryForObject(overlapCheckSql, new Object[]{spotId, endTimestamp, startTimestamp}, Integer.class);

        if (overlappingCount > 0) {
            throw new IllegalStateException("The parking spot is already reserved during the requested time.");
        }

        // Proceed to create the reservation if no overlap found
        String insertSql = "INSERT INTO Reservation (user_id, spot_id, start_time, end_time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertSql, userId, spotId, startTimestamp, endTimestamp);
    }

    public List<Reservation> getReservationsByUserId(int userId) {
        String sql = "SELECT * FROM Reservation WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> new Reservation(
            rs.getInt("reservation_id"),
            rs.getInt("user_id"),
            rs.getInt("spot_id"),
            rs.getTimestamp("start_time"),
            rs.getTimestamp("end_time"),
            rs.getString("status"),
            rs.getBigDecimal("penalty")
        ));
    }
}
