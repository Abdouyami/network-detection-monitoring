package com.networkmonitor.controller;

import java.util.ArrayList;
import java.util.List;

import com.networkmonitor.model.Device;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class NetworkMapController {
    @FXML
    private TextField txtSearch;

    @FXML
    private CheckBox chkActive, chkSuspicious, chkInactive;

    @FXML
    private Pane mapCanvas;

    private List<Device> devices;

    @FXML
    public void initialize() {
        // Load example devices
        devices = new ArrayList<>();
        devices.add(new Device("192.168.1.1", "00:1A:2B:3C:4D:5E", "Router", "Active"));
        devices.add(new Device("192.168.1.2", "00:1A:2B:3C:4D:5F", "PC-01", "Active"));
        devices.add(new Device("192.168.1.3", "00:1A:2B:3C:4D:60", "PC-02", "Inactive"));
        devices.add(new Device("192.168.1.4", "00:1A:2B:3C:4D:61", "Printer", "Suspicious"));

        // Draw the network map
        drawNetworkMap();
    }

    private void drawNetworkMap() {
        // Clear the canvas
        mapCanvas.getChildren().clear();

        // Draw devices and connections
        double x = 100, y = 100;
        for (Device device : devices) {
            // Draw device as a circle
            Circle circle = new Circle(x, y, 20);
            circle.setFill(getStatusColor(device.getStatus())); // Use the updated method
            mapCanvas.getChildren().add(circle);

            // Add device label
            Text label = new Text(x - 20, y + 30, device.getHostname());
            mapCanvas.getChildren().add(label);

            // Draw connections (example: connect all devices to the router)
            if (!device.getHostname().equals("Router")) {
                Line line = new Line(100, 100, x, y);
                mapCanvas.getChildren().add(line);
            }

            // Update position for the next device
            x += 150;
        }
    }

    private Color getStatusColor(String status) {
        switch (status) {
            case "Active":
                return Color.GREEN;
            case "Suspicious":
                return Color.RED;
            case "Inactive":
                return Color.GRAY;
            default:
                return Color.BLACK;
        }
    }

    

    @FXML
    private void handleSearch() {
        // TODO: Implement search functionality
    }

    @FXML
    private void handleFilter() {
        // TODO: Implement filter functionality
    }

    @FXML
    private void handleRefresh() {
        drawNetworkMap();
    }

    @FXML
    private void handleExport() {
        // TODO: Implement export functionality
    }
}