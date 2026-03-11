# 🚗 Parking Slot Manager

A professional, cloud-ready Spring Boot application for managing parking slots, vehicle logs, and user roles. Built with a modern tech stack and optimized for cloud deployment.

## ✨ Features

- **User Management**: Register users with different roles (Admin/User).
- **Vehicle Tracking**: Register and manage multiple vehicles per user.
- **Smart Slot Allocation**: View available slots and assign them based on vehicle type.
- **Parking Logs**: Complete history of entry and exit times for every vehicle.
- **Interactive Dashboard**: Modern Thymeleaf-based UI for admins and users.
- **Cloud Database**: Integrated with TiDB Cloud for high availability.

## 🛠️ Tech Stack

- **Backend**: Java 17, Spring Boot 3.4.1
- **Database**: TiDB Cloud (MySQL Compatible Serverless)
- **UI Architecture**: Spring MVC, Thymeleaf, Vanilla CSS
- **Deployment**: Docker, Render.com

## 🚀 Deployment Guide

This project is configured for **Render.com** with **Docker**.

### Prerequisites
1. A **TiDB Cloud** account and a serverless cluster.
2. A **Render.com** account.

### Step 1: GitHub Push
Ensure your code is pushed to your repository:
```bash
git add .
git commit -m "Add professional README"
git push origin main
```

### Step 2: Render Configuration
1. Create a **New Web Service** and select your repository.
2. Set **Language** to `Docker`.
3. Set **Root Directory** to `parkingslotmanager`.
4. Add the following **Environment Variables**:
   - `DB_URL`: Your TiDB JDBC URL
   - `DB_USER`: Your TiDB Username
   - `DB_PASSWORD`: Your TiDB Password

## 💻 Local Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/sabareeshwaran16/parkingslotmanager.git
   ```
2. Navigate to the project folder:
   ```bash
   cd parkingslotmanager/parkingslotmanager
   ```
3. Run the application:
   ```bash
   ./gradlew bootRun
   ```
4. Access the UI at `http://localhost:8080`

---
*Developed for efficient parking management.*
