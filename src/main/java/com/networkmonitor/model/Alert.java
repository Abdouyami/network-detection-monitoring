package com.networkmonitor.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Alert {
    private final StringProperty type;
    private final StringProperty severity;
    private final StringProperty timestamp;

    public Alert(String type, String severity, String timestamp) {
        this.type = new SimpleStringProperty(type);
        this.severity = new SimpleStringProperty(severity);
        this.timestamp = new SimpleStringProperty(timestamp);
    }

    // Property methods (required for JavaFX TableView binding)
    public StringProperty typeProperty() {
        return type;
    }

    public StringProperty severityProperty() {
        return severity;
    }

    public StringProperty timestampProperty() {
        return timestamp;
    }

    // Regular getters
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