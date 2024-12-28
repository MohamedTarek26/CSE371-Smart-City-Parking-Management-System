package com.example.smart_city_parking.services;

import com.example.smart_city_parking.models.Payment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class PaymentService {

    private final JdbcTemplate jdbcTemplate;

    // Constructor injection of JdbcTemplate
    public PaymentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to map the result set to a Payment object
    private static final RowMapper<Payment> paymentRowMapper = (rs, rowNum) -> {
        int paymentId = rs.getInt("payment_id");
        int reservationId = rs.getInt("reservation_id");
        BigDecimal amount = rs.getBigDecimal("amount");
        String paymentMethod = rs.getString("payment_method");
        Timestamp transactionDate = rs.getTimestamp("transaction_date");

        return new Payment(paymentId, reservationId, amount, paymentMethod, transactionDate);
    };

    // Make a payment for a reservation
    public boolean makePayment(Payment payment) {
        String query = "INSERT INTO Payment (reservation_id, amount, payment_method) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(query, payment.getReservationId(), payment.getAmount(), payment.getPaymentMethod());
        return rowsAffected > 0;
    }

    // Get payment details for a reservation
    public Payment getPaymentByReservationId(int reservationId) {
        String query = "SELECT * FROM Payment WHERE reservation_id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{reservationId}, paymentRowMapper);
    }
}
