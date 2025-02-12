package com.networkmonitor.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.networkmonitor.model.Device;
import com.networkmonitor.model.Log;

public class FakeDataGenerator {
    private static final Random random = new Random();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Generate fake devices
    public static List<Device> generateDevices(int count) {
        List<Device> devices = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String ip = "192.168.1." + i;
            String mac = String.format("00:1A:2B:3C:4D:%02X", i);
            String hostname = randomDevice() + i;
            String status = randomStatus();
            String os = randomOS(hostname); // Generate OS based on device type
            devices.add(new Device(ip, mac, hostname, status, os));
        }
        return devices;
    }

    // // Generate fake alerts
    // public static List<Alert> generateAlerts(List<Device> devices, int count) {
    //     List<Alert> alerts = new ArrayList<>();
    //     for (int i = 1; i <= count; i++) {
    //         Device device = devices.get(random.nextInt(devices.size()));
    //         String type = randomAlertType();
    //         String severity = randomSeverity();
    //         String timestamp = LocalDateTime.now().format(formatter);
    //         alerts.add(new Alert(type, severity, timestamp, device));
    //     }
    //     return alerts;
    // }

    // Generate fake vulnerabilities
    // public static List<Vulnerability> generateVulnerabilities(List<Device> devices, int count) {
    //     List<Vulnerability> vulnerabilities = new ArrayList<>();
    //     for (int i = 1; i <= count; i++) {
    //         Device device = devices.get(random.nextInt(devices.size()));
    //         String cveId = "CVE-2023-" + String.format("%04d", i);
    //         String description = "Vulnerability Description " + i;
    //         String severity = randomSeverity();
    //         String timestamp = LocalDateTime.now().format(formatter);
    //         vulnerabilities.add(new Vulnerability(cveId, description, severity, timestamp, device));
    //     }
    //     return vulnerabilities;
    // }

    // Generate fake users
    // public static List<User> generateUsers(int count) {
    //     List<User> users = new ArrayList<>();
    //     for (int i = 1; i <= count; i++) {
    //         String username = "user" + i;
    //         String password = "password" + i;
    //         String email = "user" + i + "@example.com";
    //         String role = (i == 1) ? "admin" : "user"; // First user is admin
    //         users.add(new User(username, password, email, role));
    //     }
    //     return users;
    // }

    // // Generate fake notifications
    // public static List<Notification> generateNotifications(List<Alert> alerts, List<User> users, int count) {
    //     List<Notification> notifications = new ArrayList<>();
    //     for (int i = 1; i <= count; i++) {
    //         Alert alert = alerts.get(random.nextInt(alerts.size()));
    //         User user = users.get(random.nextInt(users.size()));
    //         String message = "Notification for alert: " + alert.getType();
    //         String timestamp = LocalDateTime.now().format(formatter);
    //         String status = randomStatusNotif();
    //         boolean isRead = random.nextBoolean();
    //         notifications.add(new Notification(alert, user, message, timestamp, status, isRead));
    //     }
    //     return notifications;
    // }

    // Generate fake logs
    public static List<Log> generateLogs(int count) {
        List<Log> logs = new ArrayList<>();
        String[] types = {"Info", "Warning", "Error"};
        String[] users = {"admin", "user1", "user2"};
        String[] severities = {"low", "medium", "high"};
    
        for (int i = 1; i <= count; i++) {
            LocalDateTime timestamp = LocalDateTime.now().minusMinutes(random.nextInt(60));
            String type = types[random.nextInt(types.length)];
            String message = "Log message " + i;
            String user = users[random.nextInt(users.length)];
            String severity = severities[random.nextInt(severities.length)];
            logs.add(new Log(timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), type, message, user, severity));
        }
        return logs;
    }

    // Helper methods for random data generation
    private static String randomOS(String hostname) {
        if (hostname.toLowerCase().contains("router")) {
            return "N/A"; // Routers don't have an OS
        } else {
            String[] osList = {"Windows 10", "Windows 11", "Windows xp", "Windows 8", "Windows 7", "Linux", "macOS"};
            return osList[random.nextInt(osList.length)];
        }
    }

    private static String randomDevice() {
        String[] statuses = {"Pc", "Router"};
        return statuses[random.nextInt(statuses.length)];
    }

    private static String randomStatus() {
        String[] statuses = {"active", "inactive", "suspicious"};
        return statuses[random.nextInt(statuses.length)];
    }

    private static String randomAlertType() {
        String[] types = {"DHCP Spoofing", "Unauthorized Device", "Port Scan", "Malware Detected"};
        return types[random.nextInt(types.length)];
    }

    private static String randomSeverity() {
        String[] severities = {"low", "medium", "high"};
        return severities[random.nextInt(severities.length)];
    }

    private static String randomStatusNotif() {
        String[] statuses = {"sent", "pending",};
        return statuses[random.nextInt(statuses.length)];
    }
}