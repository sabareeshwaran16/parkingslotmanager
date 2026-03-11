package com.example.parkingslotmanager.service;

import com.example.parkingslotmanager.model.entity.ParkingLog;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

public interface ReportService {

    /**
     * Calculates current slot occupancy statistics.
     */
    @Transactional(readOnly = true)
    Map<String, Object> getCurrentOccupancyReport();

    /**
     * Retrieves all parking logs recorded for the current day.
     */
    @Transactional(readOnly = true)
    List<ParkingLog> getParkingLogsForToday();

    @Transactional(readOnly = true)
    List<ParkingLog> getAllLogs();
}