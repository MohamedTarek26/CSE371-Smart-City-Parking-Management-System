package com.example.smart_city_parking.controller;

import com.example.smart_city_parking.models.Reservation;
import com.example.smart_city_parking.services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/user/{id}")
    public List<Reservation> getReservationsByUserId(@PathVariable int id) {
        return reservationService.getReservationsByUserId(id);
    }

    @PostMapping("/reserve")
    public String createReservation(@RequestBody Map<String, Object> reservationData) {
        try {
            int userId = Integer.parseInt(reservationData.get("user_id").toString());
            int spotId = Integer.parseInt(reservationData.get("spot_id").toString());
            String startTime = reservationData.get("start_time").toString();
            String endTime = reservationData.get("end_time").toString();
            
            reservationService.createReservation(userId, spotId, startTime, endTime);
            return "Reservation created successfully!";
        } catch (IllegalStateException e) {
            return "Error: " + e.getMessage();  // Return the error message if the spot is already reserved
        }
    }

}
