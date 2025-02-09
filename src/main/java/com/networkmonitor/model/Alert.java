package com.networkmonitor.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Alert {
    private final StringProperty type;
    private final StringProperty severity;
    private final StringProperty timestamp;
    private final Device device; // Link to a device

    public Alert(String type, String severity, String timestamp, Device device) {
        this.type = new SimpleStringProperty(type);
        this.severity = new SimpleStringProperty(severity);
        this.timestamp = new SimpleStringProperty(timestamp);
        this.device = device;
    }

    // Property getters
    public StringProperty typeProperty() { return type; }
    public StringProperty severityProperty() { return severity; }
    public StringProperty timestampProperty() { return timestamp; }

    // Value getters
    public String getType() { return type.get(); }
    public String getSeverity() { return severity.get(); }
    public String getTimestamp() { return timestamp.get(); }
    public Device getDevice() { return device; }
}