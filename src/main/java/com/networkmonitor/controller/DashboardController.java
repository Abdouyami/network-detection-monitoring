package com.networkmonitor.controller;

import com.networkmonitor.model.Alert;

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
        // Example data (replace with real data from services)
        lblActiveDevices.setText("10");
        lblCriticalAlerts.setText("3");
        lblVulnerabilities.setText("5");
    }

    private void loadRecentAlerts() {
        // Example alerts
        ObservableList<Alert> alerts = FXCollections.observableArrayList(
            new Alert("DHCP Spoofing", "High", "2025-02-07 14:30:00"),
            new Alert("Unauthorized Device", "Medium", "2025-02-07 14:35:00"),
            new Alert("Port Scan Detected", "Low", "2025-02-07 14:40:00")
        );

        // Populate the table with example alerts
        tblRecentAlerts.setItems(alerts);
    }
}