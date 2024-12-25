package com.example.smart_city_parking.controller;

import com.example.smart_city_parking.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping
    public List<Map<String, Object>> getAllParkingLots() {
        return parkingLotService.getAllParkingLots();
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
