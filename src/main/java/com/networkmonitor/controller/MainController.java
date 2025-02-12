package com.networkmonitor.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.networkmonitor.model.User;
import com.networkmonitor.service.NotificationService;
import com.networkmonitor.utils.UIManager;
import com.networkmonitor.utils.UserSession;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

    @FXML private Button btnTraffic;

    @FXML
    private Button btnNetworkMap;

    @FXML
    private Button btnNotifications;

    @FXML
    private Button btnLogs;

    @FXML
    private Button btnSettings;

    private NotificationService notificationService;

    // Add a setter for NotificationService
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
        System.out.println("NotificationService updated in MainController: " + (notificationService != null));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load Dashboard by default when the app starts
        loadDashboard();

        // Access user data from the singleton
        User user = UserSession.getInstance().getUser();
        if (user != null) {
            System.out.println("Logged-in user: " + user.getUsername());
            System.out.println("User role: " + user.getRole());
        } else {
            System.out.println("No user data found in MainController");
        }
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/networkmonitor/view/AlertView.fxml"));
        try {
            Parent view = loader.load();
            AlertController alertController = loader.getController();
            alertController.setNotificationService(notificationService);
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadVulnerabilityView() {
        resetButtonStyles();
        btnVulnerabilities.getStyleClass().add("active");
        UIManager.loadView(contentArea, "/com/networkmonitor/view/VulnerabilityView.fxml");
    }

    @FXML
    private void loadTrafficView() {
        resetButtonStyles();
        btnTraffic.getStyleClass().add("active");
        UIManager.loadView(contentArea, "/com/networkmonitor/view/TrafficView.fxml");   
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/networkmonitor/view/SettingsView.fxml"));
        try {
            Parent view = loader.load();
            SettingsController settingsController = loader.getController();
            settingsController.setMainController(this);
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        btnTraffic.getStyleClass().remove("active");
    }
}