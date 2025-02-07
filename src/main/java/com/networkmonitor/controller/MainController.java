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

    private void resetButtonStyles() {
        btnDashboard.getStyleClass().remove("active");
        btnDevices.getStyleClass().remove("active");
        btnAlerts.getStyleClass().remove("active");
        btnVulnerabilities.getStyleClass().remove("active");
    }
}