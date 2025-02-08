package com.networkmonitor.model;

import java.time.LocalDateTime;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Notification {
    private final IntegerProperty id;
    private final ObjectProperty<Alert> alert;
    private final StringProperty message;
    private final ObjectProperty<LocalDateTime> timestamp;
    private final StringProperty status;
    private final BooleanProperty isRead;

    public Notification(Integer id, Alert alert, String message, LocalDateTime timestamp, 
                       String status, boolean isRead) {
        this.id = new SimpleIntegerProperty(id);
        this.alert = new SimpleObjectProperty<>(alert);
        this.message = new SimpleStringProperty(message);
        this.timestamp = new SimpleObjectProperty<>(timestamp);
        this.status = new SimpleStringProperty(status);
        this.isRead = new SimpleBooleanProperty(isRead);
    }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public ObjectProperty<Alert> alertProperty() { return alert; }
    public StringProperty messageProperty() { return message; }
    public ObjectProperty<LocalDateTime> timestampProperty() { return timestamp; }
    public StringProperty statusProperty() { return status; }
    public BooleanProperty isReadProperty() { return isRead; }

    // Regular getters
    public Integer getId() { return id.get(); }
    public Alert getAlert() { return alert.get(); }
    public String getMessage() { return message.get(); }
    public LocalDateTime getTimestamp() { return timestamp.get(); }
    public String getStatus() { return status.get(); }
    public boolean isRead() { return isRead.get(); }

    // Setters
    public void setId(Integer id) { this.id.set(id); }
    public void setAlert(Alert alert) { this.alert.set(alert); }
    public void setMessage(String message) { this.message.set(message); }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp.set(timestamp); }
    public void setStatus(String status) { this.status.set(status); }
    public void setRead(boolean read) { this.isRead.set(read); }
}