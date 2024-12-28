package com.example.smart_city_parking.controller;

import com.example.smart_city_parking.services.JasperReportService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ContentDisposition;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
@RequestMapping("/api/reports")
@Slf4j
public class ReportController {

    @Autowired
    private JasperReportService reportService;

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateReport(@RequestBody HashMap<String, Object> parameters) {
        try {
            log.info("Received report generation request with parameters: {}", parameters);
            byte[] report = reportService.generateReport(parameters);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                ContentDisposition.builder("inline")
                    .filename("report.pdf")
                    .build()
            );
            
            return new ResponseEntity<>(report, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to generate report: ", e);
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to generate report: " + e.getMessage(),
                e
            );
        }
    }

    @GetMapping("/generate/{id}")
    public ResponseEntity<byte[]> generateReport(@PathVariable int id) {
        try {
            log.info("Received report generation request for parking lot ID: {}", id);
            byte[] report = reportService.generateReport(id);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                ContentDisposition.builder("inline")
                    .filename("report.pdf")
                    .build()
            );
            
            return new ResponseEntity<>(report, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to generate report: ", e);
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to generate report: " + e.getMessage(),
                e
            );
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadReport(@PathVariable int id) {
        try {
            log.info("Received download request for parking lot report with ID: {}", id);
            byte[] report = reportService.generateReport(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                    .filename("parking_lot_report_" + id + ".pdf")
                    .build()
            );

            return new ResponseEntity<>(report, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to download report: ", e);
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to download report: " + e.getMessage(),
                e
            );
        }
    }
}
