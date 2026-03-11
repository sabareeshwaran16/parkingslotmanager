package com.example.parkingslotmanager.service.impl;

import com.example.parkingslotmanager.model.entity.ParkingLog;
import com.example.parkingslotmanager.repository.ParkingLogRepository;
import com.example.parkingslotmanager.repository.ParkingSlotRepository;
import com.example.parkingslotmanager.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true) // Applies read-only context to all methods in the class
public class ReportServiceImpl implements ReportService {

    private final ParkingSlotRepository slotRepository;
    private final ParkingLogRepository logRepository;

    // Constructor Injection
    public ReportServiceImpl(ParkingSlotRepository slotRepository, ParkingLogRepository logRepository) {
        this.slotRepository = slotRepository;
        this.logRepository = logRepository;
    }

    @Override
    public Map<String, Object> getCurrentOccupancyReport() {
        // Calculation logic uses slotRepository
        long totalSlots = slotRepository.count();
        long occupiedSlots = slotRepository.countByStatus("Occupied");
        long reservedSlots = slotRepository.countByStatus("Reserved");
        long availableSlots = totalSlots - (occupiedSlots + reservedSlots);

        double occupancyRate = 0.0;
        if (totalSlots > 0) {
            occupancyRate = ((double) (occupiedSlots + reservedSlots) / totalSlots) * 100;
        }

        Map<String, Object> report = new HashMap<>();
        report.put("totalSlots", totalSlots);
        report.put("occupiedSlots", occupiedSlots);
        report.put("reservedSlots", reservedSlots);
        report.put("availableSlots", availableSlots);
        report.put("occupancyRate", String.format("%.2f%%", occupancyRate));

        if (occupancyRate > 80) {
            report.put("status", "High");
        } else {
            report.put("status", "Normal");
        }

        return report;
    }

    @Override
    public List<ParkingLog> getParkingLogsForToday() {
        LocalDateTime now = LocalDateTime.now();

        // Calculate time boundaries for the current day
        LocalDateTime startOfDay = now.with(LocalTime.MIN);
        LocalDateTime endOfDay = now.with(LocalTime.MAX);

        // Uses logRepository
        return logRepository.findByEntryTimeAfterAndEntryTimeBefore(startOfDay, endOfDay);
    }

    @Override
    public List<ParkingLog> getAllLogs() {
        return logRepository.findAll();
    }
}