package com.networkmonitor.controller;

import com.networkmonitor.model.Device;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DeviceController {
    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Device> tblDevices;

    @FXML
    private TableColumn<Device, String> colIpAddress;

    @FXML
    private TableColumn<Device, String> colMacAddress;

    @FXML
    private TableColumn<Device, String> colHostname;

    @FXML
    private TableColumn<Device, String> colStatus;

    private ObservableList<Device> devices;

    @FXML
    public void initialize() {
        // Bind columns to Device properties
        colIpAddress.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        colMacAddress.setCellValueFactory(new PropertyValueFactory<>("macAddress"));
        colHostname.setCellValueFactory(new PropertyValueFactory<>("hostname"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load example devices
        loadDevices();
    }

    private void loadDevices() {
        // Example devices (replace with real data from services)
        devices = FXCollections.observableArrayList(
            new Device("192.168.1.1", "00:1A:2B:3C:4D:5E", "Router", "Active"),
            new Device("192.168.1.2", "00:1A:2B:3C:4D:5F", "PC-01", "Active"),
            new Device("192.168.1.3", "00:1A:2B:3C:4D:60", "PC-02", "Inactive"),
            new Device("192.168.1.4", "00:1A:2B:3C:4D:61", "Printer", "Suspicious")
        );

        // Populate the table with example devices
        tblDevices.setItems(devices);
    }

    @FXML
    private void handleSearch() {
        String searchText = txtSearch.getText().toLowerCase();
        ObservableList<Device> filteredDevices = FXCollections.observableArrayList();

        for (Device device : devices) {
            if (device.getIpAddress().toLowerCase().contains(searchText) ||
                device.getHostname().toLowerCase().contains(searchText)) {
                filteredDevices.add(device);
            }
        }

        tblDevices.setItems(filteredDevices);
    }

    @FXML
    private void handleRefresh() {
        txtSearch.clear();
        loadDevices();
    }

    @FXML
    private void handleIsolateDevice() {
        Device selectedDevice = tblDevices.getSelectionModel().getSelectedItem();
        if (selectedDevice != null) {
            System.out.println("Isolating device: " + selectedDevice.getIpAddress());
            // TODO: Implement isolation logic (e.g., block device via SSH or SNMP)
        }
    }

    @FXML
    private void handleViewDetails() {
        Device selectedDevice = tblDevices.getSelectionModel().getSelectedItem();
        if (selectedDevice != null) {
            System.out.println("Viewing details for device: " + selectedDevice.getIpAddress());
            // TODO: Implement device details view
        }
    }
}