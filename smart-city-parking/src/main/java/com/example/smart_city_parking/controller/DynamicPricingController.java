package com.example.smart_city_parking.controller;

import com.example.smart_city_parking.models.DynamicPricing;
import com.example.smart_city_parking.services.DynamicPricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dynamic-pricing")
public class DynamicPricingController {

    @Autowired
    private DynamicPricingService dynamicPricingService;

    @GetMapping
    public List<DynamicPricing> getAllDynamicPricing() {
        return dynamicPricingService.getAllDynamicPricing();
    }

    @PostMapping("/update-peak-factor/{lotId}")
    public void updatePeakFactor(@PathVariable int lotId) {
        dynamicPricingService.updatePeakFactor(lotId);
    }
}
