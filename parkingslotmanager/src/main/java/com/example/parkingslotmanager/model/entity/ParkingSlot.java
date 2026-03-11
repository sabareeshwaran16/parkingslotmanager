package com.example.parkingslotmanager.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "parkingslots")
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id")
    private Integer slotId;

    @Column(name = "slot_number", length = 20, unique = true, nullable = false)
    private String slotNumber;

    @Column(length = 20, nullable = false)
    private String type;

    @Column(length = 20, nullable = false)
    private String status = "Available";

    @Column(length = 10, nullable = false)
    private String floor;

    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }
}