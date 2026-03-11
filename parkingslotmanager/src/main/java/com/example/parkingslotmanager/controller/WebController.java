package com.example.parkingslotmanager.controller;

import com.example.parkingslotmanager.model.entity.User;
import com.example.parkingslotmanager.model.entity.Vehicle;
import com.example.parkingslotmanager.model.entity.ParkingSlot;
import com.example.parkingslotmanager.model.dto.UserDto;
import com.example.parkingslotmanager.model.dto.VehicleDto;
import com.example.parkingslotmanager.model.dto.SlotAssignmentDto;
import com.example.parkingslotmanager.service.UserService;
import com.example.parkingslotmanager.service.VehicleService;
import com.example.parkingslotmanager.service.ReportService;
import com.example.parkingslotmanager.service.ParkingService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    // Dependencies injected via constructor
    private final UserService userService;
    private final VehicleService vehicleService;
    private final ReportService reportService;
    private final ParkingService parkingService;

    // Constructor Injection
    public WebController(UserService userService, VehicleService vehicleService,
            ReportService reportService, ParkingService parkingService) {
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.reportService = reportService;
        this.parkingService = parkingService;
    }

    // --- 1. Core Navigation & Index ---

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    // --- 2. User Management ---

    @GetMapping("/user-register")
    public String showUserRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "user-register";
    }

    @PostMapping("/register-user")
    public String registerUser(@ModelAttribute("userDto") UserDto userDto, RedirectAttributes redirectAttributes) {
        userService.registerUser(userDto);
        redirectAttributes.addFlashAttribute("message", "Success! User " + userDto.getName() + " registered.");

        return "redirect:/user-register";
    }

    @GetMapping("/view-all-users")
    public String viewAllUsers(Model model) {

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users-list";

    }

    // --- 3. Vehicle Management ---

    @GetMapping("/vehicle-register")
    public String showVehicleRegistrationForm(Model model) {
        model.addAttribute("vehicleDto", new VehicleDto());
        model.addAttribute("users", userService.getAllUsers());
        return "vehicle-register";
    }

    @PostMapping("/register-vehicle")
    public String registerVehicle(@ModelAttribute("vehicleDto") VehicleDto vehicleDto,
            RedirectAttributes redirectAttributes) {
        try {
            vehicleService.registerVehicle(vehicleDto);
            redirectAttributes.addFlashAttribute("message",
                    "Success! Vehicle " + vehicleDto.getLicensePlate() + " registered.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error registering vehicle: " + e.getMessage());
        }
        return "redirect:/vehicle-register";
    }

    @GetMapping("/view-all-vehicles")
    public String viewAllVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "vehicles-list";
    }

    // --- 4. Dashboard and Slot Management ---

    @GetMapping("/admin-dashboard")
    public String showAdminDashboard(Model model) {

        Map<String, Object> occupancyReport = reportService.getCurrentOccupancyReport();
        List<ParkingSlot> allSlots = parkingService.getAllSlots();

        model.addAttribute("report", occupancyReport);
        model.addAttribute("slots", allSlots);
        return "admin-dashboard";

    }

    @GetMapping("/slot-management")
    public String showSlotManagementForm(@RequestParam(value = "id", required = false) Integer slotId, Model model) {

        ParkingSlot slot = (slotId != null) ? parkingService.getAllSlots().stream()
                .filter(s -> s.getSlotId().equals(slotId)).findFirst().orElse(new ParkingSlot())
                : new ParkingSlot();

        model.addAttribute("slot", slot);
        return "slot-management-form";
    }

    @PostMapping("/create-slot-execute")
    public String createSlotExecute(@ModelAttribute("slot") ParkingSlot slot, RedirectAttributes redirectAttributes) {

        parkingService.createNewSlot(slot);
        redirectAttributes.addFlashAttribute("message", "Slot " + slot.getSlotNumber() + " successfully created.");

        return "redirect:/admin-dashboard";
    }

    @PostMapping("/update-slot-execute")
    public String updateSlotExecute(@ModelAttribute("slot") ParkingSlot slot, RedirectAttributes redirectAttributes) {

        parkingService.updateSlot(slot.getSlotId(), slot);
        redirectAttributes.addFlashAttribute("message", "Slot " + slot.getSlotNumber() + " successfully updated.");

        return "redirect:/admin-dashboard";
    }

    @GetMapping("/assign-slot-form")
    public String showAssignSlotForm(@RequestParam("slotId") Integer slotId, Model model) {

        ParkingSlot slot = parkingService.getAllSlots().stream()
                .filter(s -> s.getSlotId().equals(slotId)).findFirst()
                .orElseThrow(() -> new RuntimeException("Slot not found."));

        model.addAttribute("slot", slot);
        model.addAttribute("assignmentDto", new SlotAssignmentDto());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        return "assign-slot-form";

    }

    @PostMapping("/execute-assignment")
    public String executeAssignment(
            @ModelAttribute("assignmentDto") SlotAssignmentDto assignmentDto,
            @RequestParam("slotId") Integer slotId,
            RedirectAttributes redirectAttributes) {

        parkingService.assignSlot(slotId, assignmentDto);
        redirectAttributes.addFlashAttribute("message",
                "SUCCESS! Slot " + slotId + " assigned to " + assignmentDto.getLicensePlate() + ".");

        return "redirect:/admin-dashboard";
    }

    @PostMapping("/release-slot")
    public String releaseSlot(@RequestParam("slotId") Integer slotId, RedirectAttributes redirectAttributes) {
        try {
            parkingService.releaseSlot(slotId);
            redirectAttributes.addFlashAttribute("message", "Slot released successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error releasing slot: " + e.getMessage());
        }
        return "redirect:/admin-dashboard";
    }

    @GetMapping("/view-daily-logs") // New URL to access the log list
    public String viewDailyLogs(Model model) {
        try {
            // Fetch all logs to ensure visibility during debugging/initial setup
            model.addAttribute("logs", reportService.getAllLogs());
            return "logs-list";
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching logs: " + e.getMessage());
            return "logs-list";
        }
    }
}