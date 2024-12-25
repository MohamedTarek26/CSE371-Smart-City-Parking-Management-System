package com.example.smart_city_parking.services;

import com.example.smart_city_parking.models.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
        String sql = "INSERT INTO Reservation (user_id, spot_id, start_time, end_time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, userId, spotId, startTime, endTime);
    }
}
