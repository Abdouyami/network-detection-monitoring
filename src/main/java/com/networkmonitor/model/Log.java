package com.networkmonitor.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Log {
    private  ObjectProperty<LocalDateTime> timestamp;
    private final StringProperty type;
    private final StringProperty message;
    private final StringProperty user;
    private final StringProperty severity;

    public Log(String timestamp, String type, String message, String user, String severity) {
        // Parse the timestamp from ISO 8601 format
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME; // Matches "2025-02-11T12:53:21Z"
            LocalDateTime localDateTime = LocalDateTime.parse(timestamp, formatter);
            this.timestamp = new SimpleObjectProperty<>(localDateTime);
        } catch (Exception e) {
            System.err.println("Error parsing timestamp: " + timestamp + " " + e.getMessage());
            this.timestamp = new SimpleObjectProperty<>(null); // Fallback to null
        }

        this.type = new SimpleStringProperty(type);
        this.message = new SimpleStringProperty(message);
        this.user = new SimpleStringProperty(user);
        this.severity = new SimpleStringProperty(severity);
    }

    // Property getters
    public ObjectProperty<LocalDateTime> timestampProperty() { return timestamp; }
    public StringProperty typeProperty() { return type; }
    public StringProperty messageProperty() { return message; }
    public StringProperty userProperty() { return user; }
    public StringProperty severityProperty() { return severity; }

    // Value getters
    public LocalDateTime getTimestamp() { return timestamp.get(); }
    public String getType() { return type.get(); }
    public String getMessage() { return message.get(); }
    public String getUser() { return user.get(); }
    public String getSeverity() { return severity.get(); }

    public static List<Log> parseFromJson(String json) {
        List<Log> logs = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json); // Parse the JSON array

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String timestamp = jsonObject.getString("timestamp");
                String type = jsonObject.getString("type");
                String message = jsonObject.getString("message");
                String user = jsonObject.getString("username");
                String severity = jsonObject.getString("severity");

                Log log = new Log(timestamp, type, message, user, severity);
                logs.add(log);
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        }
        return logs;
    }

}