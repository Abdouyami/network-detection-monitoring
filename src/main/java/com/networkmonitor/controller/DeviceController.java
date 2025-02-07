package com.networkmonitor.controller;

import com.networkmonitor.model.Device;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DeviceController {
    @FXML private TableView<Device> deviceTable;
    @FXML private TableColumn<Device, String> ipColumn;
    @FXML private TableColumn<Device, String> macColumn;
    @FXML private TableColumn<Device, String> statusColumn;

    private final ObservableList<Device> deviceList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ipColumn.setCellValueFactory(cellData -> cellData.getValue().ipProperty());
        macColumn.setCellValueFactory(cellData -> cellData.getValue().macProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        deviceTable.setItems(deviceList);
    }

    @FXML
    @SuppressWarnings("unused")
    private void handleAddDevice() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Feature to be implemented!");
        alert.show();
    }

    @FXML
    @SuppressWarnings("unused")
    private void handleRemoveDevice() {
        Device selected = deviceTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            deviceList.remove(selected);
        }
    }

    @FXML
    @SuppressWarnings("unused")
    private void handleRefresh() {
        // Placeholder for database integration
        System.out.println("Refreshing device list...");
    }
}
