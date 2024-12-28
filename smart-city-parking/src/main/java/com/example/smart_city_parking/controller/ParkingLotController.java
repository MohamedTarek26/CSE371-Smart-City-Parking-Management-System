package com.example.smart_city_parking.controller;

import com.example.smart_city_parking.services.ParkingLotService;
import com.example.smart_city_parking.models.ParkingLot;
import com.example.smart_city_parking.models.ParkingSpot;
import com.example.smart_city_parking.models.UserInfo;

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
    public int addParkingLot(@RequestBody Map<String, Object> parkingLot) {
        int rows = parkingLotService.addParkingLot(
                parkingLot.get("location").toString(),
                Integer.parseInt(parkingLot.get("capacity").toString()),
                parkingLot.get("pricingStructure").toString(),
                parkingLot.get("typesOfSpots").toString(),
                Double.parseDouble(parkingLot.get("latitude").toString()),  // Include latitude
                Double.parseDouble(parkingLot.get("longitude").toString())  // Include longitude
        );
        return rows ;
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

    @GetMapping("/available-spots/{id}")
    public int getAvailableSpots(@PathVariable int id) {
        System.out.println("Getting available spots for parking lot with ID: " + id);
        return parkingLotService.getAvailableSpots(id);
    }
    
    @GetMapping("/revenue/{id}")
    public String calculateRevenue(@PathVariable int id) {
        System.out.println("Calculating revenue for parking lot with ID: " + id);
        return "Total revenue: $" + parkingLotService.calculateRevenue(id);
    }

    @GetMapping("/capacity/{id}")
    public int getCapacity(@PathVariable int id) {
        return parkingLotService.getCapacity(id);
    }

    @GetMapping("/top-users/{id}")
    public List<UserInfo> getTopUsers(@PathVariable int id) {
        return parkingLotService.getTopUsersForLot(id,5);
    }
    
    @GetMapping("/spots/{lotId}")
    public List<ParkingSpot> getAllSpotsForLot(@PathVariable int lotId) {
        return parkingLotService.getAllSpotsForLot(lotId);
    }
    
}
