package com.example.parkingslotmanager.model.dto;

import jakarta.validation.constraints.NotBlank;

public class ParkingEntryExitDto {
    @NotBlank(message = "License plate is required.")
    private String licensePlate;

    private Integer slotId;

    @NotBlank(message = "Status must be 'Entry' or 'Exit'.")
    private String status;

    private Integer logId;

    private String remarks;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}