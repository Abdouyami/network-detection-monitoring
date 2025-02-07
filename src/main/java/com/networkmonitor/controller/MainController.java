package com.networkmonitor.controller;

import com.networkmonitor.utils.UIManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class MainController {
    @FXML
    private StackPane contentArea;

    @FXML
    private Button btnDevices;

    @FXML
    private Button btnAlerts;

    private void resetButtonStyles() {
        btnDevices.getStyleClass().remove("active");
        btnAlerts.getStyleClass().remove("active");
    }

    public void loadDeviceView() {
        resetButtonStyles();
        btnDevices.getStyleClass().add("active");
        UIManager.loadView(contentArea, "/com/networkmonitor//view/DeviceView.fxml");
    }

    public void loadAlertView() {
        resetButtonStyles();
        btnAlerts.getStyleClass().add("active");
        UIManager.loadView(contentArea, "/com/networkmonitor//view/AlertView.fxml");
    }
}