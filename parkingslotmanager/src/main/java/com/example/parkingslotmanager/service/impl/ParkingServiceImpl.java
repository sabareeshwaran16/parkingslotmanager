package com.example.parkingslotmanager.service.impl;

import com.example.parkingslotmanager.model.entity.ParkingLog;
import com.example.parkingslotmanager.model.entity.ParkingSlot;
import com.example.parkingslotmanager.model.entity.Vehicle;
import com.example.parkingslotmanager.model.dto.SlotAssignmentDto;
import com.example.parkingslotmanager.repository.ParkingLogRepository;
import com.example.parkingslotmanager.repository.ParkingSlotRepository;
import com.example.parkingslotmanager.repository.VehicleRepository;
import com.example.parkingslotmanager.service.ParkingService;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingSlotRepository slotRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingLogRepository logRepository;

    public ParkingServiceImpl(
            ParkingSlotRepository slotRepository,
            VehicleRepository vehicleRepository,
            ParkingLogRepository logRepository) {
        this.slotRepository = slotRepository;
        this.vehicleRepository = vehicleRepository;
        this.logRepository = logRepository;
    }

    @Override
    @Transactional // Must be writable
    public ParkingSlot updateSlot(Integer slotId, ParkingSlot updatedSlotDetails) {
        ParkingSlot existingSlot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found with ID: " + slotId));

        // Apply necessary updates
        existingSlot.setType(updatedSlotDetails.getType());
        existingSlot.setStatus(updatedSlotDetails.getStatus());
        existingSlot.setFloor(updatedSlotDetails.getFloor());

        return slotRepository.save(existingSlot);
    }

    @Override
    public List<ParkingLog> getLogsByVehicleId(Integer vehicleId) {

        return logRepository.findByVehicleVehicleIdOrderByEntryTimeDesc(vehicleId);
    }

    @Override
    public ParkingSlot createNewSlot(ParkingSlot slot) {
        return slotRepository.save(slot);
    }

    @Override
    public List<ParkingSlot> getAllSlots() {
        return slotRepository.findAll();
    }

    @Override
    @Transactional
    public ParkingSlot assignSlot(Integer slotId, SlotAssignmentDto assignmentDto) {
        ParkingSlot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found with ID: " + slotId));

        Vehicle vehicle = vehicleRepository.findByLicensePlate(assignmentDto.getLicensePlate())
                .orElseThrow(() -> new RuntimeException("Vehicle not registered. Cannot assign slot."));

        if (!"Available".equalsIgnoreCase(slot.getStatus())) {
            throw new IllegalStateException("Slot " + slot.getSlotNumber() + " is currently " + slot.getStatus() + ".");
        }

        slot.setStatus("Occupied");
        slotRepository.save(slot);

        logEntry(vehicle.getLicensePlate(), slotId);

        return slot;
    }

    @Override
    @Transactional
    public ParkingLog logEntry(String licensePlate, Integer slotId) {
        ParkingSlot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found for entry log."));

        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new RuntimeException("Vehicle not registered. Cannot log entry."));

        if (!"Occupied".equalsIgnoreCase(slot.getStatus())) {
            slot.setStatus("Occupied");
            slotRepository.save(slot);
        }

        ParkingLog entryLog = new ParkingLog();
        entryLog.setSlot(slot);
        entryLog.setVehicle(vehicle);
        entryLog.setStatus("Entry");
        entryLog.setEntryTime(LocalDateTime.now());
        entryLog.setRemarks("Vehicle checked in.");

        return logRepository.save(entryLog);
    }

    @Override
    @Transactional
    public ParkingLog logExit(Integer logId) {
        ParkingLog openLog = logRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Parking Log record not found with ID: " + logId));

        if (openLog.getExitTime() != null) {
            throw new IllegalStateException("Parking Log " + logId + " is already marked as exited.");
        }

        openLog.setExitTime(LocalDateTime.now());
        openLog.setStatus("Exit");

        ParkingSlot slot = openLog.getSlot();
        slot.setStatus("Available");
        slotRepository.save(slot);

        return logRepository.save(openLog);
    }

    @Override
    @Transactional
    public ParkingLog releaseSlot(Integer slotId) {
        ParkingLog activeLog = logRepository.findFirstBySlotSlotIdAndExitTimeIsNullOrderByEntryTimeDesc(slotId)
                .orElseThrow(() -> new RuntimeException("No active parking log found for slot ID: " + slotId));

        return logExit(activeLog.getLogId());
    }

}