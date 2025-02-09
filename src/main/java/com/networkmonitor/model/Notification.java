package com.networkmonitor.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Notification {
    private final Alert alert;
    private final User user;
    private final StringProperty message;
    private ObjectProperty<LocalDateTime> timestamp;  // Changed to LocalDateTime
    private final StringProperty status;
    private final SimpleBooleanProperty isRead;

    public Notification(Alert alert, User user, String message, String timestamp, String status, boolean isRead) {
        this.alert = alert;
        this.user = user;
        this.message = new SimpleStringProperty(message);

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

        this.status = new SimpleStringProperty(status);
        this.isRead = new SimpleBooleanProperty(isRead);
    }

    // Property getters
    public StringProperty messageProperty() {
        return message;
    }

    public ObjectProperty<LocalDateTime> timestampProperty() { // Returns ObjectProperty<LocalDateTime>
        return timestamp;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public SimpleBooleanProperty isReadProperty() {
        return isRead;
    }

    // Value getters
    public Alert getAlert() {
        return alert;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message.get();
    }

    public LocalDateTime getTimestamp() { // Returns LocalDateTime
        return timestamp.get();
    }

    public String getStatus() {
        return status.get();
    }

    public boolean isRead() {
        return isRead.get();
    }

    // Value setters
    public void setRead(boolean isRead) {
        this.isRead.set(isRead);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp.set(timestamp);
    }

    public void setTimestamp(String timestampString) { // Overload to take string
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(timestampString, formatter);
            this.timestamp.set(localDateTime);
        } catch (Exception e) {
            System.err.println("Error parsing timestamp: " + timestampString + " " + e.getMessage());
            this.timestamp.set(null);
        }
    }
}