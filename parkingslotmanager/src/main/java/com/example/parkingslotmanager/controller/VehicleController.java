package com.example.parkingslotmanager.controller;

import com.example.parkingslotmanager.model.entity.Vehicle;
import com.example.parkingslotmanager.model.dto.VehicleDto;
import com.example.parkingslotmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // Endpoint: PUT /api/vehicles/registerVehicle (Register a new vehicle)
    @PutMapping("/registerVehicle")
    public ResponseEntity<?> registerVehicle(@Valid @RequestBody VehicleDto vehicleDto) {

            Vehicle newVehicle = vehicleService.registerVehicle(vehicleDto);
            return new ResponseEntity<>(newVehicle, HttpStatus.CREATED);

    }


    @GetMapping("/byuser/{id}")
    public ResponseEntity<List<Vehicle>> getVehiclesByUser(@PathVariable("id") Integer userId) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByUserId(userId);

        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(vehicles);
    }
}