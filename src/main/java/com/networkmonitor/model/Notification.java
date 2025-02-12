package com.networkmonitor.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Notification {
    private final SimpleIntegerProperty id;
    private final StringProperty alertType;
    private final StringProperty alertSeverity;
    private final StringProperty username;
    private final StringProperty message;
    private final ObjectProperty<LocalDateTime> timestamp;
    private final StringProperty status;
    private final SimpleBooleanProperty isRead;
    private final SimpleIntegerProperty userId;
    private final SimpleIntegerProperty alertId;

    public Notification(int id, String alertType, String alertSeverity, String username, String message, 
                        String timestamp, String status, boolean isRead, int userId, int alertId) {
        this.id = new SimpleIntegerProperty(id);
        this.alertType = new SimpleStringProperty(alertType);
        this.alertSeverity = new SimpleStringProperty(alertSeverity);
        this.username = new SimpleStringProperty(username);
        this.message = new SimpleStringProperty(message);
        this.timestamp = new SimpleObjectProperty<>(parseTimestamp(timestamp));
        this.status = new SimpleStringProperty(status);
        this.isRead = new SimpleBooleanProperty(isRead);
        this.userId = new SimpleIntegerProperty(userId);
        this.alertId = new SimpleIntegerProperty(alertId);
    }

    // Helper method to parse timestamp
    private LocalDateTime parseTimestamp(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME; // Matches "2025-02-11T12:53:28Z"
        return LocalDateTime.parse(timestamp, formatter);
    }

    // Property getters
    public SimpleIntegerProperty idProperty() { return id; }
    public StringProperty alertTypeProperty() { return alertType; }
    public StringProperty alertSeverityProperty() { return alertSeverity; }
    public StringProperty usernameProperty() { return username; }
    public StringProperty messageProperty() { return message; }
    public ObjectProperty<LocalDateTime> timestampProperty() { return timestamp; }
    public StringProperty statusProperty() { return status; }
    public SimpleBooleanProperty isReadProperty() { return isRead; }
    public SimpleIntegerProperty userIdProperty() { return userId; }
    public SimpleIntegerProperty alertIdProperty() { return alertId; }

    // Value getters
    public int getId() { return id.get(); }
    public String getAlertType() { return alertType.get(); }
    public String getAlertSeverity() { return alertSeverity.get(); }
    public String getUsername() { return username.get(); }
    public String getMessage() { return message.get(); }
    public LocalDateTime getTimestamp() { return timestamp.get(); }
    public String getStatus() { return status.get(); }
    public boolean isRead() { return isRead.get(); }
    public int getUserId() { return userId.get(); }
    public int getAlertId() { return alertId.get(); }

    // Value setters
    public void setRead(boolean isRead) { this.isRead.set(isRead); }
    public void setStatus(String status) { this.status.set(status); }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp.set(timestamp); }

    public static List<Notification> parseFromJson(String json) {
        List<Notification> notifications = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json); // Directly parse the JSON array
    
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
    
                int id = jsonObject.getInt("id");
                String alertType = jsonObject.getString("alert_type");
                String alertSeverity = jsonObject.getString("alert_severity");
                String username = jsonObject.getString("username");
                String message = jsonObject.getString("message");
                String timestamp = jsonObject.getString("timestamp");
                String status = jsonObject.getString("status");
                boolean isRead = jsonObject.getBoolean("is_read");
                int userId = jsonObject.getInt("user");
                int alertId = jsonObject.getInt("alert");
    
                Notification notification = new Notification(
                    id, alertType, alertSeverity, username, message, timestamp, status, isRead, userId, alertId
                );
                notifications.add(notification);
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        }
        return notifications;
    }
    
}