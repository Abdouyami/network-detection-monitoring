package com.networkmonitor.controller;

import java.util.List;

import com.networkmonitor.config.APIConfig;
import com.networkmonitor.model.Device;
import com.networkmonitor.utils.HttpClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DeviceController {
    @FXML private TextField txtSearch;
    @FXML private TableView<Device> tblDevices;
    @FXML private TableColumn<Device, String> colIpAddress;
    @FXML private TableColumn<Device, String> colMacAddress;
    @FXML private TableColumn<Device, String> colHostname;
    @FXML private TableColumn<Device, String> colStatus;
    @FXML private TableColumn<Device, String> colOS;

    private ObservableList<Device> devices;
    private FilteredList<Device> filteredDevices;

    @FXML
    public void initialize() {
        // Bind columns to Device properties
        colIpAddress.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        colMacAddress.setCellValueFactory(new PropertyValueFactory<>("macAddress"));
        colHostname.setCellValueFactory(new PropertyValueFactory<>("hostname"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colOS.setCellValueFactory(new PropertyValueFactory<>("os"));

        // Set up status column colors
        colStatus.setCellFactory(column -> new TableCell<Device, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    switch (item.toLowerCase()) {
                        case "suspicious" -> setStyle("-fx-text-fill: red;");
                        case "inactive" -> setStyle("-fx-text-fill: orange;");
                        case "active" -> setStyle("-fx-text-fill: green;");
                        default -> setStyle("");
                    }
                }
            }
        });

        // Load devices from the API
        loadDevices();
    }

    private void loadDevices() {
        // Fetch real data from the API
        String url = APIConfig.DEVICES_URL; // Use the base devices URL
        String response = HttpClient.get(url); // Fetch data from the API

        if (response != null) {
            System.err.println("Fetched devices from the API: " + response);
            List<Device> realDevices = Device.parseFromJson(response);
            devices = FXCollections.observableArrayList(realDevices);

            // Initialize FilteredList
            filteredDevices = new FilteredList<>(devices);
            tblDevices.setItems(filteredDevices);

            // Add listener to the search text field
            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filterDevices(newValue); // Call filter method
            });
        } else {
            System.err.println("Failed to fetch devices from the API.");
        }
    }

    private void filterDevices(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            filteredDevices.setPredicate(device -> true); // Show all devices
        } else {
            String lowerCaseSearchText = searchText.toLowerCase();
            filteredDevices.setPredicate(device ->
                    device.getIp().toLowerCase().contains(lowerCaseSearchText) ||
                    device.getHostname().toLowerCase().contains(lowerCaseSearchText) ||
                    device.getMac().toLowerCase().contains(lowerCaseSearchText) ||
                    device.getStatus().toLowerCase().contains(lowerCaseSearchText)
            );
        }
    }

    @FXML
    private void handleIsolateDevice() {
        Device selectedDevice = tblDevices.getSelectionModel().getSelectedItem();
        if (selectedDevice != null) {
            System.out.println("Isolating device: " + selectedDevice.getIp());
            // TODO: Implement isolation logic
        }
    }

    @FXML
    private void handleViewDetails() {
        Device selectedDevice = tblDevices.getSelectionModel().getSelectedItem();
        if (selectedDevice != null) {
            System.out.println("Viewing details for device: " + selectedDevice.getIp());
            // TODO: Implement device details view
        }
    }
}