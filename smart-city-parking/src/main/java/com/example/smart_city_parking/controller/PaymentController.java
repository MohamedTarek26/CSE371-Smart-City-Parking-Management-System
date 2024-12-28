package com.example.smart_city_parking.controller;

import org.springframework.web.bind.annotation.*;

import com.example.smart_city_parking.models.Payment;
import com.example.smart_city_parking.services.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    // Make a payment for a reservation
    @PostMapping("/pay")
    public boolean makePayment(@RequestBody Payment payment) {
        return paymentService.makePayment(payment);
    }

    // Get payment details for a reservation
    @GetMapping("/{reservationId}")
    public Payment getPayment(@PathVariable int reservationId) {
        return paymentService.getPaymentByReservationId(reservationId);
    }
}
