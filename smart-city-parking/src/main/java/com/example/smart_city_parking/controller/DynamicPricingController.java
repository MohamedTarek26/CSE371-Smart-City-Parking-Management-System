package com.example.smart_city_parking.controller;

import org.springframework.web.bind.annotation.*;

import com.example.smart_city_parking.models.DynamicPricing;
import com.example.smart_city_parking.services.DynamicPricingService;

import org.springframework.beans.factory.annotation.Autowired;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/dynamic-pricing")
public class DynamicPricingController {
    @Autowired
    private DynamicPricingService dynamicPricingService;

    // Get dynamic pricing for a parking spot
    @GetMapping("/{spotId}")
    public List<DynamicPricing> getDynamicPricing(@PathVariable int spotId) {
        return dynamicPricingService.getDynamicPricingBySpotId(spotId);
    }

    // Set dynamic pricing for a parking spot
    @PostMapping("/set")
    public boolean setDynamicPricing(@RequestBody DynamicPricing dynamicPricing) {
            return dynamicPricingService.setDynamicPricing(dynamicPricing);
    }

    // Update dynamic pricing for a parking spot
    @PutMapping("/update")
    public boolean updateDynamicPricing(@RequestBody DynamicPricing dynamicPricing) {
            return dynamicPricingService.updateDynamicPricing(dynamicPricing);
    }
}
