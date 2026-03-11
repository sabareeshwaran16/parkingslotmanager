package com.example.parkingslotmanager.service;

import com.example.parkingslotmanager.model.entity.ParkingLog;
import com.example.parkingslotmanager.model.entity.ParkingSlot;
import com.example.parkingslotmanager.model.dto.SlotAssignmentDto;
import java.util.List;

public interface ParkingService {
  ParkingSlot createNewSlot(ParkingSlot slot);

  List<ParkingSlot> getAllSlots();

  ParkingSlot assignSlot(Integer slotId, SlotAssignmentDto assignmentDto);

  ParkingLog logEntry(String licensePlate, Integer slotId);

  ParkingLog logExit(Integer logId);

  List<ParkingLog> getLogsByVehicleId(Integer vehicleId);

  ParkingSlot updateSlot(Integer slotId, ParkingSlot updatedSlotDetails);

  ParkingLog releaseSlot(Integer slotId);
}