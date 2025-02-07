package com.networkmonitor.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Alert {
    private final StringProperty type;
    private final StringProperty severity;
    private final StringProperty description;

    public Alert(String type, String severity, String description) {
        this.type = new SimpleStringProperty(type);
        this.severity = new SimpleStringProperty(severity);
        this.description = new SimpleStringProperty(description);
    }

    public StringProperty typeProperty() { return type; }
    public StringProperty severityProperty() { return severity; }
    public StringProperty descriptionProperty() { return description; }
}
