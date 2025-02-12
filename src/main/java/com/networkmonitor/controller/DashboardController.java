package com.networkmonitor.controller;

import java.util.List;

import com.networkmonitor.config.APIConfig;
import com.networkmonitor.model.Alert;
import com.networkmonitor.model.Device;
import com.networkmonitor.model.Vulnerability;
import com.networkmonitor.utils.HttpClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController {
    @FXML private Label lblActiveDevices;
    @FXML private Label lblInActiveDevices;
    @FXML private Label lblSuspiciousDevices;
    @FXML private Label lblCriticalAlerts;
    @FXML private Label lblVulnerabilities;
    @FXML private TableView<Alert> tblRecentAlerts;
    @FXML private TableColumn<Alert, String> colType;
    @FXML private TableColumn<Alert, String> colSeverity;
    @FXML private TableColumn<Alert, String> colTimestamp;
    @FXML private TableColumn<Alert, String> colDeviceHostname;
    @FXML private TableColumn<Alert, String> colDeviceOs;

    private ObservableList<Alert> alerts;

    @FXML
    public void initialize() {
        // Set up table columns
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colSeverity.setCellValueFactory(new PropertyValueFactory<>("severity"));
        colTimestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        colDeviceHostname.setCellValueFactory(new PropertyValueFactory<>("deviceHostname"));    
        colDeviceOs.setCellValueFactory(new PropertyValueFactory<>("deviceOs"));

        // Set up severity column colors
        colSeverity.setCellFactory(column -> new TableCell<Alert, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    switch (item.toLowerCase()) {
                        case "high" -> setStyle("-fx-text-fill: red;");
                        case "medium" -> setStyle("-fx-text-fill: orange;");
                        case "low" -> setStyle("-fx-text-fill: green;");
                        default -> setStyle("");
                    }
                }
            }
        });

        // Fetch and display dashboard data
        updateDashboardMetrics();
        loadRecentAlerts();
    }

    private void updateDashboardMetrics() {
        // Fetch devices from the API
        String devicesUrl = APIConfig.DEVICES_URL;
        String devicesResponse = HttpClient.get(devicesUrl);

        if (devicesResponse != null) {
            List<Device> devices = Device.parseFromJson(devicesResponse);

            // Calculate counts
            long activeDeviceCount = devices.stream()
                    .filter(device -> "active".equalsIgnoreCase(device.getStatus()))
                    .count();
            long inActiveDeviceCount = devices.stream()
                    .filter(device -> "inactive".equalsIgnoreCase(device.getStatus()))
                    .count();
            long suspiciousDeviceCount = devices.stream()
                    .filter(device -> "suspicious".equalsIgnoreCase(device.getStatus()))
                    .count();

            // Update labels
            lblActiveDevices.setText(String.valueOf(activeDeviceCount));
            lblInActiveDevices.setText(String.valueOf(inActiveDeviceCount));
            lblSuspiciousDevices.setText(String.valueOf(suspiciousDeviceCount));
        } else {
            System.err.println("Failed to fetch devices from the API.");
        }

        // Fetch alerts from the API
        String alertsUrl = APIConfig.ALERTS_URL;
        String alertsResponse = HttpClient.get(alertsUrl);

        if (alertsResponse != null) {
            List<Alert> alerts = Alert.parseFromJson(alertsResponse);
            long criticalAlertCount = alerts.stream()
                    .filter(alert -> "high".equalsIgnoreCase(alert.getSeverity()))
                    .count();
            lblCriticalAlerts.setText(String.valueOf(criticalAlertCount));
        } else {
            System.err.println("Failed to fetch alerts from the API.");
        }

        // Fetch vulnerabilities from the API
        String vulnerabilitiesUrl = APIConfig.VULNERABILITIES_URL; // Add this to APIConfig
        String vulnerabilitiesResponse = HttpClient.get(vulnerabilitiesUrl);

        if (vulnerabilitiesResponse != null) {
            List<Vulnerability> vulnerabilities = Vulnerability.parseFromJson(vulnerabilitiesResponse);
            lblVulnerabilities.setText(String.valueOf(vulnerabilities.size()));
        } else {
            System.err.println("Failed to fetch vulnerabilities from the API.");
        }
    }

    private void loadRecentAlerts() {
        // Fetch recent alerts from the API
        String alertsUrl = APIConfig.ALERTS_URL;
        String alertsResponse = HttpClient.get(alertsUrl);

        if (alertsResponse != null) {
            List<Alert> alerts = Alert.parseFromJson(alertsResponse);
            this.alerts = FXCollections.observableArrayList(alerts);
            tblRecentAlerts.setItems(this.alerts);
        } else {
            System.err.println("Failed to fetch recent alerts from the API.");
        }
    }
}