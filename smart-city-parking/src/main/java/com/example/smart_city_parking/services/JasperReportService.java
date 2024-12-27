package com.example.smart_city_parking.services;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.example.smart_city_parking.services.ParkingLotService;

@Service
@Slf4j
public class JasperReportService {
    private final ParkingLotService parkingLotService;

    public JasperReportService(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }
    private HashMap<String, Object> generateData(int parkingLotId) {
        HashMap<String, Object> data = new HashMap<>();
        int totalSpots = parkingLotService.getCapacity(parkingLotId);
        int occupiedSpots = parkingLotService.getOccupiedSpots(parkingLotId);
        int availableSpots = totalSpots - occupiedSpots;
        String occupancyRate = String.format("%.1f%%", (occupiedSpots / (double) totalSpots) * 100);
        int revenue = parkingLotService.calculateRevenue(parkingLotId).intValue();
        
        // Adding parameters that match the JRXML
        data.put("reportTitle", "Smart City Parking Report for Parking Lot " + parkingLotId);
        data.put("generationDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        data.put("totalSpots", totalSpots);
        data.put("occupiedSpots", occupiedSpots);
        data.put("availableSpots", availableSpots);
        data.put("occupancyRate", occupancyRate);
        data.put("Revenue", "$" + revenue + ".00");

        log.info("Generated data: {}", data);
        return data;
    }
    
    public byte[] generateReport(int id) {
        HashMap<String, Object> parameters = generateData(id);
        log.info("Parameters being passed to report: {}", parameters);
        return generateReport(parameters);
    }
    
    public byte[] generateReport(HashMap<String, Object> parameters) {
        try {
            String reportPath = "src/main/resources/report.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            log.error("Failed to generate report: ", e);
            throw new RuntimeException("Failed to generate report: " + e.getMessage(), e);
        }
    }
}