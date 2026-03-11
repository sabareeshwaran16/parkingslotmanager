package com.example.parkingslotmanager.controller;

import com.example.parkingslotmanager.model.entity.ParkingLog;
import com.example.parkingslotmanager.model.dto.ParkingEntryExitDto;
import com.example.parkingslotmanager.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class ParkingLogController {

    @Autowired
    private ParkingService parkingService;


    @PostMapping("/logactivity")
    public ResponseEntity<?> logActivity(@Valid @RequestBody ParkingEntryExitDto logDto) {
        try {
            ParkingLog log;
            String status = logDto.getStatus();

            if ("Entry".equalsIgnoreCase(status)) {
                if (logDto.getSlotId() == null) {
                    throw new IllegalArgumentException("Slot ID is required for an Entry.");
                }
                log = parkingService.logEntry(logDto.getLicensePlate(), logDto.getSlotId());

            } else if ("Exit".equalsIgnoreCase(status)) {
                if (logDto.getLogId() == null) {
                    throw new IllegalArgumentException("Log ID is required for an Exit.");
                }
                log = parkingService.logExit(logDto.getLogId());

            } else {
                return new ResponseEntity<>("Status must be 'Entry' or 'Exit'.", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(log, HttpStatus.CREATED);

        }catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/byvehicle/{id}")
    public ResponseEntity<List<ParkingLog>> getLogsByVehicle(@PathVariable("id") Integer vehicleId) {
        List<ParkingLog> logs = parkingService.getLogsByVehicleId(vehicleId);

        if (logs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(logs);
    }
}