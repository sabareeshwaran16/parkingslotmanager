package com.example.parkingslotmanager.repository;

import com.example.parkingslotmanager.model.entity.ParkingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ParkingLogRepository extends JpaRepository<ParkingLog, Integer> {
    List<ParkingLog> findByVehicleVehicleIdOrderByEntryTimeDesc(Integer vehicleId);

    List<ParkingLog> findByEntryTimeAfterAndEntryTimeBefore(LocalDateTime startOfDay, LocalDateTime endOfDay);

    java.util.Optional<ParkingLog> findFirstBySlotSlotIdAndExitTimeIsNullOrderByEntryTimeDesc(Integer slotId);
}