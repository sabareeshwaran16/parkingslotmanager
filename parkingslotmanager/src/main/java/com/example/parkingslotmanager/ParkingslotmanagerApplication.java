package com.example.parkingslotmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan; // <-- ADD THIS

@SpringBootApplication
@ComponentScan(basePackages = "com.example.parkingslotmanager") // <-- ADD THIS LINE
public class ParkingslotmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingslotmanagerApplication.class, args);
    }
}