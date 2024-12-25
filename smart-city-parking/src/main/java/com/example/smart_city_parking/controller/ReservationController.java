package com.example.smart_city_parking.controller;

import com.example.smart_city_parking.models.Reservation;
import com.example.smart_city_parking.services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/get-all")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable int id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping("/")
    public void createReservation(@RequestBody Reservation reservation) {
        reservationService.createReservation(reservation.getUserId(), reservation.getSpotId(),
            reservation.getStartTime().toString(), reservation.getEndTime().toString());
    }
}
