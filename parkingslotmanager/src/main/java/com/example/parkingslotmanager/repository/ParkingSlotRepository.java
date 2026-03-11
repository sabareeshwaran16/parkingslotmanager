package com.example.parkingslotmanager.repository;

import com.example.parkingslotmanager.model.entity.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Integer> {
  long countByStatus(String status);
}