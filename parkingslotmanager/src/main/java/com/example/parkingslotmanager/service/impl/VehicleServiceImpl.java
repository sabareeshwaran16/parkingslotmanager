package com.example.parkingslotmanager.service.impl;

import com.example.parkingslotmanager.model.entity.User;
import com.example.parkingslotmanager.model.entity.Vehicle;
import com.example.parkingslotmanager.model.dto.VehicleDto;
import com.example.parkingslotmanager.repository.UserRepository;
import com.example.parkingslotmanager.repository.VehicleRepository;
import com.example.parkingslotmanager.service.VehicleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Vehicle registerVehicle(VehicleDto vehicleDto) {
        User user = userRepository.findById(vehicleDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID " + vehicleDto.getUserId()));

        if (vehicleRepository.findByLicensePlate(vehicleDto.getLicensePlate()).isPresent()) {
            throw new RuntimeException("License plate " + vehicleDto.getLicensePlate() + " is already registered.");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setUser(user);
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        vehicle.setType(vehicleDto.getType());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setRegisteredAt(LocalDateTime.now());

        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> getVehiclesByUserId(Integer userId) {
        return vehicleRepository.findByUserUserId(userId);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}