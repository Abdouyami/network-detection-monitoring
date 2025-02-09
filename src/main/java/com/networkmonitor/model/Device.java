package com.networkmonitor.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Device {
    private final StringProperty ipAddress;
    private final StringProperty macAddress;
    private final StringProperty hostname;
    private final StringProperty status;
    private final StringProperty type;

    public Device(String ipAddress, String macAddress, String hostname, String status) {
        this.ipAddress = new SimpleStringProperty(ipAddress);
        this.macAddress = new SimpleStringProperty(macAddress);
        this.hostname = new SimpleStringProperty(hostname);
        this.status = new SimpleStringProperty(status);
        this.type = new SimpleStringProperty(determineType(hostname));
    }

    private String determineType(String hostname) {
        if (hostname.toLowerCase().contains("router")) return "router";
        if (hostname.toLowerCase().contains("pc")) return "pc";
        if (hostname.toLowerCase().contains("printer")) return "printer";
        return "default";
    }

    // Property getters
    public StringProperty ipAddressProperty() { return ipAddress; }
    public StringProperty macAddressProperty() { return macAddress; }
    public StringProperty hostnameProperty() { return hostname; }
    public StringProperty statusProperty() { return status; }
    public StringProperty typeProperty() { return type; }

    // Value getters
    public String getIp() { return ipAddress.get(); }
    public String getMac() { return macAddress.get(); }
    public String getHostname() { return hostname.get(); }
    public String getStatus() { return status.get(); }
    public String getType() { return type.get(); }

    // Value setters
    public void setStatus(String status) { this.status.set(status); }

    @Override
    public String toString() {
        return String.format("Device{hostname='%s', ip='%s', status='%s'}",
            getHostname(), getIp(), getStatus());
    }
}