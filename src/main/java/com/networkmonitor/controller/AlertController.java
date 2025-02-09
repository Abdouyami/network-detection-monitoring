package com.networkmonitor.controller;

import java.util.List;

import com.networkmonitor.model.Alert;
import com.networkmonitor.model.Device;
import com.networkmonitor.service.NotificationService;
import com.networkmonitor.utils.FakeDataGenerator;

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

    private ObservableList<Alert> alerts;
    private NotificationService notificationService;

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
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

        // Load fake alerts
        loadAlerts();
    }

    private void loadAlerts() {
        // Generate fake devices and alerts
        List<Device> fakeDevices = FakeDataGenerator.generateDevices(20);
        alerts = FXCollections.observableArrayList(FakeDataGenerator.generateAlerts(fakeDevices, 20));
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

            // Send a notification if the NotificationService is available
            if (notificationService != null) {
                String recipient = "belhamiciabderrahmane@gmail.com"; // Replace with actual recipient
                String subject = "Alert Acknowledged: " + selectedAlert.getType();
                String body = """
                              The following alert has been acknowledged:
                              
                              Type: """ + selectedAlert.getType() + "\n" +
                             "Severity: " + selectedAlert.getSeverity() + "\n" +
                             "Timestamp: " + selectedAlert.getTimestamp();

                notificationService.sendEmail(recipient, subject, body);
                System.out.println("Notification sent for acknowledged alert.");
            } else {
                System.err.println("Notification service is not configured.");
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