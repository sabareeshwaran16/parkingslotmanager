package com.example.parkingslotmanager.repository;

import com.example.parkingslotmanager.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findByUserUserId(Integer userId);
    Optional<Vehicle> findByLicensePlate(String licensePlate);
}