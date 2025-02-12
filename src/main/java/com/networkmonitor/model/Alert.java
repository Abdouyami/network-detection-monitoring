package com.networkmonitor.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Alert {
    private final StringProperty type;
    private final StringProperty severity;
    private final StringProperty timestamp;
    private final StringProperty deviceHostname; // New field
    private final StringProperty deviceOs; // New field

    public Alert(String type, String severity, String timestamp, String deviceHostname, String deviceOs) {
        this.type = new SimpleStringProperty(type);
        this.severity = new SimpleStringProperty(severity);
        this.timestamp = new SimpleStringProperty(timestamp);
        this.deviceHostname = new SimpleStringProperty(deviceHostname);
        this.deviceOs = new SimpleStringProperty(deviceOs);
    }

    // Property getters
    public StringProperty typeProperty() { return type; }
    public StringProperty severityProperty() { return severity; }
    public StringProperty timestampProperty() { return timestamp; }
    public StringProperty deviceHostnameProperty() { return deviceHostname; } // New getter
    public StringProperty deviceOsProperty() { return deviceOs; } // New getter

    // Value getters
    public String getType() { return type.get(); }
    public String getSeverity() { return severity.get(); }
    public String getTimestamp() { return timestamp.get(); }
    public String getDeviceHostname() { return deviceHostname.get(); } // New getter
    public String getDeviceOs() { return deviceOs.get(); } // New getter


    // Parse JSON response into a list of Alert objects
    public static List<Alert> parseFromJson(String json) {
        List<Alert> alerts = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json); // Parse the JSON array

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String type = jsonObject.getString("type");
                String severity = jsonObject.getString("severity");
                String timestamp = jsonObject.getString("timestamp");
                String deviceHostname = jsonObject.getString("device_hostname");
                String deviceOs = jsonObject.getString("device_os");

                Alert alert = new Alert(type, severity, timestamp, deviceHostname, deviceOs);
                alerts.add(alert);
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        }
        return alerts;
    }
}