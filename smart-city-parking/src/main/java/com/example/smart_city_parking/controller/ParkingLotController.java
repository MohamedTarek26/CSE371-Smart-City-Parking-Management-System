package com.example.smart_city_parking.controller;

import com.example.smart_city_parking.services.ParkingLotService;
import com.example.smart_city_parking.models.ParkingLot;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking-lots")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    // Get all parking lots
    @GetMapping("/")
    public List<ParkingLot> getAllParkingLots() {
        return parkingLotService.getAllParkingLots();
    }

    // Get a specific parking lot by ID
    @GetMapping("/{id}")
    public ParkingLot getParkingLotById(@PathVariable int id) {
        try {
            return parkingLotService.getParkingLotById(id);
        } catch (Exception e) {
            return null; // Handle the case where the lot is not found
        }
    }

    // Add a new parking lot
    @PostMapping
    public String addParkingLot(@RequestBody Map<String, Object> parkingLot) {
        int rows = parkingLotService.addParkingLot(
                parkingLot.get("location").toString(),
                Integer.parseInt(parkingLot.get("capacity").toString()),
                parkingLot.get("pricingStructure").toString(),
                parkingLot.get("typesOfSpots").toString(),
                Double.parseDouble(parkingLot.get("latitude").toString()),  // Include latitude
                Double.parseDouble(parkingLot.get("longitude").toString())  // Include longitude
        );
        return rows > 0 ? "Parking lot added successfully!" : "Failed to add parking lot.";
    }

    // Get the navigation URL to a specific parking lot
    @GetMapping("/navigate/{id}")
    public String getNavigationUrl(@PathVariable int id) {
        ParkingLot parkingLot = parkingLotService.getParkingLotById(id);
        
        if (parkingLot != null) {
            return parkingLotService.generateNavigationUrl(parkingLot.getLatitude(), parkingLot.getLongitude());
        } else {
            return "Parking lot not found.";
        }
    }

    // Search parking lots by location
    @GetMapping("/search")
    public List<ParkingLot> searchByLocation(@RequestParam String location) {
        return parkingLotService.searchByLocation(location);
    }
}
