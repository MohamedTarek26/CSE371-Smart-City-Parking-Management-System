package com.example.smart_city_parking.services;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class JasperReportService {
    
    private HashMap<String, Object> generateDummyData() {
        HashMap<String, Object> data = new HashMap<>();
        
        // Adding parameters that match the JRXML
        data.put("reportTitle", "Smart City Parking Report");
        data.put("generationDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        data.put("totalSpots", new Integer(500));
        data.put("occupiedSpots", new Integer(342));
        data.put("availableSpots", new Integer(158));
        data.put("occupancyRate", "68.4%");
        data.put("dailyRevenue", "$2,450.00");
        data.put("monthlyRevenue", "$72,300.00");
        data.put("peakHours", "08:00-10:00, 12:00-14:00, 17:00-19:00");
        data.put("parkingViolations", new Integer(23));
        
        log.info("Generated data: {}", data);
        return data;
    }
    
    public byte[] generateReport() {
        HashMap<String, Object> parameters = generateDummyData();
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