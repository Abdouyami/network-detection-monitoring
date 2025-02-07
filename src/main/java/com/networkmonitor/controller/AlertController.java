package com.networkmonitor.controller;

import com.networkmonitor.model.Alert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AlertController {
    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Alert> tblAlerts;

    @FXML
    private TableColumn<Alert, String> colType;

    @FXML
    private TableColumn<Alert, String> colSeverity;

    @FXML
    private TableColumn<Alert, String> colTimestamp;

    private ObservableList<Alert> alerts;

    @FXML
    public void initialize() {
        // Bind columns to Alert properties
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colSeverity.setCellValueFactory(new PropertyValueFactory<>("severity"));
        colTimestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        // Load example alerts
        loadAlerts();
    }

    private void loadAlerts() {
        // Example alerts (replace with real data from services)
        alerts = FXCollections.observableArrayList(
            new Alert("DHCP Spoofing", "High", "2025-02-07 14:30:00"),
            new Alert("Unauthorized Device", "Medium", "2025-02-07 14:35:00"),
            new Alert("Port Scan Detected", "Low", "2025-02-07 14:40:00")
        );

        // Populate the table with example alerts
        tblAlerts.setItems(alerts);
    }

    @FXML
    private void handleSearch() {
        String searchText = txtSearch.getText().toLowerCase();
        ObservableList<Alert> filteredAlerts = FXCollections.observableArrayList();

        for (Alert alert : alerts) {
            if (alert.getType().toLowerCase().contains(searchText) ||
                alert.getSeverity().toLowerCase().contains(searchText)) {
                filteredAlerts.add(alert);
            }
        }

        tblAlerts.setItems(filteredAlerts);
    }

    @FXML
    private void handleRefresh() {
        txtSearch.clear();
        loadAlerts();
    }

    @FXML
    private void handleAcknowledge() {
        Alert selectedAlert = tblAlerts.getSelectionModel().getSelectedItem();
        if (selectedAlert != null) {
            System.out.println("Acknowledging alert: " + selectedAlert.getType());
            // TODO: Implement acknowledge logic (e.g., mark alert as resolved in the database)
        }
    }

    @FXML
    private void handleViewDetails() {
        Alert selectedAlert = tblAlerts.getSelectionModel().getSelectedItem();
        if (selectedAlert != null) {
            System.out.println("Viewing details for alert: " + selectedAlert.getType());
            // TODO: Implement alert details view
        }
    }
}