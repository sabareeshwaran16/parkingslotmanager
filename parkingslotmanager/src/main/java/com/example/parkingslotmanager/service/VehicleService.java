package com.example.parkingslotmanager.service;

import com.example.parkingslotmanager.model.entity.Vehicle;
import com.example.parkingslotmanager.model.dto.VehicleDto;
import java.util.List;

public interface VehicleService {
    Vehicle registerVehicle(VehicleDto vehicleDto);

    List<Vehicle> getVehiclesByUserId(Integer userId);

    List<Vehicle> getAllVehicles();
}