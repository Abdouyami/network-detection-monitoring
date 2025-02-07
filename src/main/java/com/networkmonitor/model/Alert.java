package com.networkmonitor.model;

import javafx.beans.property.SimpleStringProperty;

public class Alert {
    private final SimpleStringProperty type;
    private final SimpleStringProperty severity;
    private final SimpleStringProperty timestamp;

    public Alert(String type, String severity, String timestamp) {
        this.type = new SimpleStringProperty(type);
        this.severity = new SimpleStringProperty(severity);
        this.timestamp = new SimpleStringProperty(timestamp);
    }

    public String getType() {
        return type.get();
    }

    public String getSeverity() {
        return severity.get();
    }

    public String getTimestamp() {
        return timestamp.get();
    }
}