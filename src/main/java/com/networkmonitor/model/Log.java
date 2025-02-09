package com.networkmonitor.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Log {
    private ObjectProperty<LocalDateTime> timestamp;
    private final StringProperty type;
    private final StringProperty message;
    private final StringProperty user;
    private final StringProperty severity;

    public Log(String timestamp, String type, String message, String user, String severity) {
        // Parse the initial timestamp string into LocalDateTime
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(timestamp, formatter);
            this.timestamp = new SimpleObjectProperty<>(localDateTime);
        } catch (Exception e) {
            // Handle parsing errors (log, default value, etc.)
            System.err.println("Error parsing timestamp: " + timestamp + " " + e.getMessage());
            this.timestamp = new SimpleObjectProperty<>(null); // Or a default LocalDateTime
        }
        this.type = new SimpleStringProperty(type);
        this.message = new SimpleStringProperty(message);
        this.user = new SimpleStringProperty(user);
        this.severity = new SimpleStringProperty(severity);
    }

    // Property getters
    public LocalDateTime getTimestamp() { // Returns LocalDateTime
        return timestamp.get();
    }
    public ObjectProperty<LocalDateTime> timestampProperty() { // Returns ObjectProperty<LocalDateTime>
        return timestamp;
    }
    public StringProperty typeProperty() { return type; }
    public StringProperty messageProperty() { return message; }
    public StringProperty userProperty() { return user; }
    public StringProperty severityProperty() { return severity; }

    // Value getters
    public String getType() { return type.get(); }
    public String getMessage() { return message.get(); }
    public String getUser() { return user.get(); }
    public String getSeverity() { return severity.get(); }
}