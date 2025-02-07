package com.networkmonitor.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Device {
    private final StringProperty ipAddress;
    private final StringProperty macAddress;
    private final StringProperty hostname;
    private final StringProperty status;

    public Device(String ipAddress, String macAddress, String hostname, String status) {
        this.ipAddress = new SimpleStringProperty(ipAddress);
        this.macAddress = new SimpleStringProperty(macAddress);
        this.hostname = new SimpleStringProperty(hostname);
        this.status = new SimpleStringProperty(status);
    }

    // Getters for properties
    public StringProperty ipAddressProperty() {
        return ipAddress;
    }

    public StringProperty macAddressProperty() {
        return macAddress;
    }

    public StringProperty hostnameProperty() {
        return hostname;
    }

    public StringProperty statusProperty() {
        return status;
    }

    // Regular getters
    public String getIpAddress() {
        return ipAddress.get();
    }

    public String getMacAddress() {
        return macAddress.get();
    }

    public String getHostname() {
        return hostname.get();
    }

    public String getStatus() {
        return status.get();
    }
}