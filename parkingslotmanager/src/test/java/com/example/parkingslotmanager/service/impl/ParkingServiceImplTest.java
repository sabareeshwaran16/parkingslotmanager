package com.example.parkingslotmanager.service.impl;

import com.example.parkingslotmanager.model.dto.SlotAssignmentDto;
import com.example.parkingslotmanager.model.entity.ParkingSlot;
import com.example.parkingslotmanager.model.entity.Vehicle;
import com.example.parkingslotmanager.repository.ParkingLogRepository;
import com.example.parkingslotmanager.repository.ParkingSlotRepository;
import com.example.parkingslotmanager.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ParkingServiceImplTest {

    @Mock
    private ParkingSlotRepository slotRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private ParkingLogRepository logRepository;

    @InjectMocks
    private ParkingServiceImpl parkingServiceImpl;

    private ParkingSlot availableSlot;
    private Vehicle existingVehicle;
    private SlotAssignmentDto assignmentDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        availableSlot = new ParkingSlot();
        availableSlot.setSlotId(10);
        availableSlot.setStatus("Available");

        existingVehicle = new Vehicle();
        existingVehicle.setLicensePlate("KL 07 AB 1234");

        assignmentDto = new SlotAssignmentDto();
        assignmentDto.setLicensePlate("KL 07 AB 1234");
    }

    @Test
    void assignSlot_Success() {
        // Arrange
        when(slotRepository.findById(10)).thenReturn(Optional.of(availableSlot));
        when(vehicleRepository.findByLicensePlate("KL 07 AB 1234")).thenReturn(Optional.of(existingVehicle));

        // Act
        ParkingSlot result = parkingServiceImpl.assignSlot(10, assignmentDto);

        // Assert
        assertEquals("Occupied", result.getStatus());
        verify(slotRepository, times(1)).save(availableSlot);
        verify(logRepository, times(1)).save(any()); // Verifies that logEntry was called
    }

    @Test
    void assignSlot_SlotOccupied_ThrowsException() {
        // Arrange
        ParkingSlot occupiedSlot = new ParkingSlot();
        occupiedSlot.setSlotId(10);
        occupiedSlot.setStatus("Occupied");
        when(slotRepository.findById(10)).thenReturn(Optional.of(occupiedSlot));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            parkingServiceImpl.assignSlot(10, assignmentDto);
        });
        verify(logRepository, never()).save(any());
    }

    @Test
    void assignSlot_VehicleNotFound_ThrowsException() {
        // Arrange
        when(slotRepository.findById(10)).thenReturn(Optional.of(availableSlot));
        when(vehicleRepository.findByLicensePlate(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            parkingServiceImpl.assignSlot(10, assignmentDto);
        });
        verify(logRepository, never()).save(any());
    }
}