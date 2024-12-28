package com.example.smart_city_parking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.smart_city_parking.models.ParkingLot;
import com.example.smart_city_parking.services.ParkingLotService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/navigation")
public class NavigationController {

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping("/toLot/{lotId}")
    public RedirectView getNavigationUrl(@PathVariable int lotId) {
        ParkingLot lot = parkingLotService.getParkingLotById(lotId);

        // Generate the navigation URL based on the lot's latitude and longitude
        String navigationUrl = parkingLotService.generateNavigationUrl(lot.getLatitude(), lot.getLongitude());

        // Redirect the user to the navigation URL
        return new RedirectView(navigationUrl); // This will perform the redirect
    }

    @GetMapping("/toLot/String/{lotId}")
    public ResponseEntity<RedirectView> getNavigationUrlSting(@PathVariable int lotId) {
        ParkingLot lot = parkingLotService.getParkingLotById(lotId);

        // Generate the navigation URL based on the lot's latitude and longitude
        String navigationUrl = parkingLotService.generateNavigationUrl(lot.getLatitude(), lot.getLongitude());

        // Redirect the user to the navigation URL
        RedirectView redirectView = new RedirectView(navigationUrl);
        return new ResponseEntity<>(redirectView, HttpStatus.FOUND); // 302 Found
    }
    
}
