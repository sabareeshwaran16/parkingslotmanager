package com.example.parkingslotmanager.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleDto {
    @NotNull(message = "User ID is required.")
    private Integer userId;

    @NotBlank(message = "License plate is required.")
    private String licensePlate;

    @NotBlank(message = "Vehicle type is required.")
    private String type;

    private String model;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}