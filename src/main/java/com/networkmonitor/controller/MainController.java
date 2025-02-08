package com.networkmonitor.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.networkmonitor.utils.UIManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class MainController implements Initializable {
    @FXML
    private StackPane contentArea;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnDevices;

    @FXML
    private Button btnAlerts;

    @FXML
    private Button btnVulnerabilities;

    @FXML
    private Button btnNetworkMap;

    @FXML
    private Button btnNotifications;

    @FXML
    private Button btnLogs;

    @FXML
    private Button btnSettings;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load Dashboard by default when the app starts
        loadDashboard();
    }

    @FXML
    private void loadDashboard() {
        resetButtonStyles();
        btnDashboard.getStyleClass().add("active");
        UIManager.loadView(contentArea, "/com/networkmonitor/view/DashboardView.fxml");
    }

    @FXML
    private void loadDeviceView() {
        resetButtonStyles();
        btnDevices.getStyleClass().add("active");
        UIManager.loadView(contentArea, "/com/networkmonitor/view/DeviceView.fxml");
    }

    @FXML
    private void loadAlertView() {
        resetButtonStyles();
        btnAlerts.getStyleClass().add("active");
        UIManager.loadView(contentArea, "/com/networkmonitor/view/AlertView.fxml");
    }

    @FXML
    private void loadVulnerabilityView() {
        resetButtonStyles();
        btnVulnerabilities.getStyleClass().add("active");
        UIManager.loadView(contentArea, "/com/networkmonitor/view/VulnerabilityView.fxml");
    }

    @FXML
    private void loadNetworkMapView() {
        resetButtonStyles();
        btnNetworkMap.getStyleClass().add("active");
        UIManager.loadView(contentArea, "/com/networkmonitor/view/NetworkMapView.fxml");
    }
    
    @FXML
    private void loadNotificationsView() {
        resetButtonStyles();
        btnNotifications.getStyleClass().add("active");
        UIManager.loadView(contentArea, "/com/networkmonitor/view/NotificationsView.fxml");
    }

    @FXML
    private void loadLogsView() {
        resetButtonStyles();
        btnLogs.getStyleClass().add("active");
        UIManager.loadView(contentArea, "/com/networkmonitor/view/LogsView.fxml");
    }

    @FXML
    private void loadSettingsView() {
        resetButtonStyles();
        btnSettings.getStyleClass().add("active");
        UIManager.loadView(contentArea, "/com/networkmonitor/view/SettingsView.fxml");
    }

    private void resetButtonStyles() {
        btnDashboard.getStyleClass().remove("active");
        btnDevices.getStyleClass().remove("active");
        btnAlerts.getStyleClass().remove("active");
        btnVulnerabilities.getStyleClass().remove("active");
        btnNetworkMap.getStyleClass().remove("active");
        btnNotifications.getStyleClass().remove("active");
        btnLogs.getStyleClass().remove("active");
        btnSettings.getStyleClass().remove("active");
    }
}