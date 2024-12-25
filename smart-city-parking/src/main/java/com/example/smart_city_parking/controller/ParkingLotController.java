package com.example.smart_city_parking.controller;

import com.example.smart_city_parking.services.ParkingLotService;
import com.example.smart_city_parking.models.ParkingLot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking-lots")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @GetMapping("/")
    public List<ParkingLot> getAllParkingLots() {
        return parkingLotService.getAllParkingLots();
    }

    @GetMapping("/{id}")
    public ParkingLot getParkingLotById(@PathVariable int id) {
        try {
            return parkingLotService.getParkingLotById(id);
        } catch (Exception e) {
            return null;
        }
    }
    
    @PostMapping
    public String addParkingLot(@RequestBody Map<String, Object> parkingLot) {
        int rows = parkingLotService.addParkingLot(
                parkingLot.get("location").toString(),
                Integer.parseInt(parkingLot.get("capacity").toString()),
                parkingLot.get("pricingStructure").toString(),
                parkingLot.get("typesOfSpots").toString()
        );
        return rows > 0 ? "Parking lot added successfully!" : "Failed to add parking lot.";
    }
}
