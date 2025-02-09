package com.networkmonitor.controller;

import java.util.List;

import com.networkmonitor.model.Alert;
import com.networkmonitor.model.Device;
import com.networkmonitor.model.Vulnerability;
import com.networkmonitor.utils.FakeDataGenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController {
    @FXML
    private Label lblActiveDevices;

    @FXML
    private Label lblInActiveDevices;

    @FXML
    private Label lblSuspiciousDevices;

    @FXML
    private Label lblCriticalAlerts;

    @FXML
    private Label lblVulnerabilities;

    @FXML
    private TableView<Alert> tblRecentAlerts;

    @FXML
    private TableColumn<Alert, String> colType;

    @FXML
    private TableColumn<Alert, String> colSeverity;

    @FXML
    private TableColumn<Alert, String> colTimestamp;

    private ObservableList<Alert> alerts;
    private ObservableList<Vulnerability> vulnerabilities;

    @FXML
    public void initialize() {
        // Set up table columns
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colSeverity.setCellValueFactory(new PropertyValueFactory<>("severity"));
        colTimestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        // Fetch and display dashboard data
        updateDashboardMetrics();
        loadRecentAlerts();
    }

    private void updateDashboardMetrics() {
        List<Device> fakeDevices = FakeDataGenerator.generateDevices(20);
        alerts = FXCollections.observableArrayList(FakeDataGenerator.generateAlerts(fakeDevices, 20));
        vulnerabilities = FXCollections.observableArrayList(FakeDataGenerator.generateVulnerabilities(fakeDevices, 20));

        // Get counts:
        long activeDeviceCountWithStringStatus = fakeDevices.stream()
        .filter(device -> "active".equalsIgnoreCase(device.getStatus())) // Case-insensitive comparison
        .count();
        long inActiveDeviceCountWithStringStatus = fakeDevices.stream()
        .filter(device -> "active".equalsIgnoreCase(device.getStatus())) // Case-insensitive comparison
        .count();
        long suspiciousDeviceCountWithStringStatus = fakeDevices.stream()
        .filter(device -> "suspicious".equalsIgnoreCase(device.getStatus())) // Case-insensitive comparison
        .count();
        int alertCount = alerts.size();
        int vulnerabilityCount = vulnerabilities.size();

        lblActiveDevices.setText(String.valueOf(activeDeviceCountWithStringStatus));
        lblInActiveDevices.setText(String.valueOf(inActiveDeviceCountWithStringStatus));
        lblSuspiciousDevices.setText(String.valueOf(suspiciousDeviceCountWithStringStatus));
        lblCriticalAlerts.setText(String.valueOf(alertCount));
        lblVulnerabilities.setText(String.valueOf(vulnerabilityCount));
    }

    private void loadRecentAlerts() {
         // Generate fake devices and alerts
        List<Device> fakeDevices = FakeDataGenerator.generateDevices(10);
        alerts = FXCollections.observableArrayList(FakeDataGenerator.generateAlerts(fakeDevices, 10));

        // Populate the table with example alerts
        tblRecentAlerts.setItems(alerts);
    }
}