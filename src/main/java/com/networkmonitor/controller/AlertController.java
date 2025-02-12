package com.networkmonitor.controller;

import java.util.List;

import com.networkmonitor.config.APIConfig;
import com.networkmonitor.model.Alert;
import com.networkmonitor.service.NotificationService;
import com.networkmonitor.utils.HttpClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
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
    @FXML TableColumn<Alert, String> colDeviceHostname;
    @FXML TableColumn<Alert, String> colDeviceOs;

    private ObservableList<Alert> alerts;
    private NotificationService notificationService;

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
        System.out.println("NotificationService set in AlertController");
    }

    @FXML
    public void initialize() {
        // You can now use notificationService here
        if (notificationService != null) {
            System.out.println("NotificationService injected successfully!");
        }
        // Bind columns to Alert properties
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

         // Add search listener
         txtSearch.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());

        // Load fake alerts
        loadAlerts();
    }

    private void loadAlerts() {
        // Fetch real data from the API
        String url = APIConfig.ALERTS_URL; // Use the base alerts URL
        String response = HttpClient.get(url); // Fetch data from the API

        if (response != null) {
            System.err.println("Fetched alerts from the API: " + response);
            List<Alert> realAlerts = Alert.parseFromJson(response);
            alerts = FXCollections.observableArrayList(realAlerts);
            tblAlerts.setItems(alerts);
        } else {
            System.err.println("Failed to fetch alerts from the API.");
        }
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
            
            // Send email notification if NotificationService is configured
            if (notificationService != null) {
                String subject = "Alert Acknowledged: " + selectedAlert.getType();
                String body = String.format("""
                    Alert Details:
                    Type: %s
                    Severity: %s
                    Timestamp: %s
                    
                    This alert has been acknowledged in the system.
                    """, 
                    selectedAlert.getType(),
                    selectedAlert.getSeverity(),
                    selectedAlert.getTimestamp()
                );
                
                // Use the same email as configured in SMTP settings
                notificationService.sendEmail(
                    System.getProperty("smtp.Email"), // Get the email from saved settings
                    subject,
                    body
                );
            } else {
                System.out.println("Warning: NotificationService not configured. Please check email settings.");
            }
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