package com.example.smart_city_parking.controller;

import com.example.smart_city_parking.models.ParkingSpot;
import com.example.smart_city_parking.models.ParkingSpotWithPrice;
import com.example.smart_city_parking.services.ParkingSpotService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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

    @GetMapping("/with_price/{id}")
    public ParkingSpotWithPrice returnSpotWithPrice(@PathVariable int id) {
        return parkingSpotService.returnSpotWithPrice(id);
    }

    @GetMapping("/next_available/{id}")
    public String getNextAvailableTime(@PathVariable int id) {
        return parkingSpotService.getNextAvailableTime(id);
    }
    
    @PutMapping("/{id}/status")
    public void updateParkingSpotStatus(@PathVariable int id, @RequestBody String status) {
        parkingSpotService.updateParkingSpotStatus(id, status);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createParkingSpots(@RequestBody Map<String, Object> payload) {
        try {
            int n = (int) payload.get("n");
            String type = (String) payload.get("type");
            int lotId = (int) payload.get("lotId");
            
            parkingSpotService.createParkingSpots(n, type, lotId);
            return ResponseEntity.ok(n + " parking spots of type '" + type + "' created for lot ID " + lotId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating parking spots: " + e.getMessage());
        }
    }

}
