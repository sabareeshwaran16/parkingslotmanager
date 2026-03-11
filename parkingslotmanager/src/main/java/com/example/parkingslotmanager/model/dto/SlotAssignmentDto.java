package com.example.parkingslotmanager.model.dto;

import jakarta.validation.constraints.NotBlank;

public class SlotAssignmentDto {
    @NotBlank(message = "License plate is required.")
    private String licensePlate;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}