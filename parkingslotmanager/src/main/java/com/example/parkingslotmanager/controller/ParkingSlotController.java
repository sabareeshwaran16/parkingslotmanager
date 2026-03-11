package com.example.parkingslotmanager.controller;

import com.example.parkingslotmanager.model.entity.ParkingSlot;
import com.example.parkingslotmanager.model.dto.SlotAssignmentDto;
import com.example.parkingslotmanager.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class ParkingSlotController {

    @Autowired
    private ParkingService parkingService;

    public ParkingSlotController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }


    @GetMapping("/allslots")
    public List<ParkingSlot> getAllSlots() {
        return parkingService.getAllSlots();
    }


    @PutMapping("/createSlot")
    public ResponseEntity<Object> createSlot(@RequestBody ParkingSlot slot) {
        parkingService.createNewSlot(slot);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/assign/{slotId}")
    public ResponseEntity<Object> assignVehicleToSlot(
            @PathVariable("slotId") Integer slotId,
            @RequestBody SlotAssignmentDto assignmentDto) {

            ParkingSlot assignedSlot = parkingService.assignSlot(slotId, assignmentDto);
            return ResponseEntity.status(HttpStatus.OK).body(assignedSlot);

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteSlot(@PathVariable Integer id){

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}