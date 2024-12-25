package com.example.smart_city_parking.controller;

import com.example.smart_city_parking.models.ParkingSpot;
import com.example.smart_city_parking.services.ParkingSpotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-spots")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping("/")
    public List<ParkingSpot> getAllParkingSpots() {
        return parkingSpotService.getAllParkingSpots();
    }

    @GetMapping("/{id}")
    public ParkingSpot getParkingSpotById(@PathVariable int id) {
        try {
            return parkingSpotService.getParkingSpotById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}/status")
    public void updateParkingSpotStatus(@PathVariable int id, @RequestBody String status) {
        parkingSpotService.updateParkingSpotStatus(id, status);
    }
}
