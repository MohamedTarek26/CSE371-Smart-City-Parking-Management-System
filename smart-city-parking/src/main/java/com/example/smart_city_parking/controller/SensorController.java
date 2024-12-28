package com.example.smart_city_parking.controller;

import com.example.smart_city_parking.models.Sensor;
import com.example.smart_city_parking.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    // Get all sensors
    @GetMapping("/")
    public List<Sensor> getAllSensors() {
        return sensorService.getAllSensors();
    }

    // Get sensor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Sensor> getSensorById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(sensorService.getSensorById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Add a new sensor
    @PostMapping("/")
    public ResponseEntity<String> addSensor(@RequestBody Map<String, Object> request) {
        int spotId = Integer.parseInt(request.get("spotId").toString());
        String status = request.get("status").toString();
        int rows = sensorService.addSensor(spotId, status);
        if (rows > 0) {
            return ResponseEntity.ok("Sensor added successfully!");
        } else {
            return ResponseEntity.status(500).body("Failed to add sensor.");
        }
    }

    // Update sensor status
    @PutMapping("/{id}")
    public ResponseEntity<String> updateSensorStatus(@PathVariable int id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        int rows = sensorService.updateSensorStatus(id, status);
        if (rows > 0) {
            return ResponseEntity.ok("Sensor status updated successfully!");
        } else {
            return ResponseEntity.status(500).body("Failed to update sensor status.");
        }
    }

    // Delete a sensor
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSensor(@PathVariable int id) {
        int rows = sensorService.deleteSensor(id);
        if (rows > 0) {
            return ResponseEntity.ok("Sensor deleted successfully!");
        } else {
            return ResponseEntity.status(500).body("Failed to delete sensor.");
        }
    }
}
