package com.networkmonitor.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Device {
    private final StringProperty ipAddress;
    private final StringProperty macAddress;
    private final StringProperty hostname;
    private final StringProperty status;
    private final StringProperty type;
    private final StringProperty os; // New OS field

    public Device(String ipAddress, String macAddress, String hostname, String status, String os) {
        this.ipAddress = new SimpleStringProperty(ipAddress);
        this.macAddress = new SimpleStringProperty(macAddress);
        this.hostname = new SimpleStringProperty(hostname);
        this.status = new SimpleStringProperty(status);
        this.type = new SimpleStringProperty(determineType(hostname));
        this.os = new SimpleStringProperty(os);
    }

    private String determineType(String hostname) {
        if (hostname.toLowerCase().contains("router")) return "router";
        if (hostname.toLowerCase().contains("pc")) return "pc";
        return "default";
    }

    // Property getters
    public StringProperty ipAddressProperty() { return ipAddress; }
    public StringProperty macAddressProperty() { return macAddress; }
    public StringProperty hostnameProperty() { return hostname; }
    public StringProperty statusProperty() { return status; }
    public StringProperty typeProperty() { return type; }

    // Value getters
    public String getIp() { return ipAddress.get(); }
    public String getMac() { return macAddress.get(); }
    public String getHostname() { return hostname.get(); }
    public String getStatus() { return status.get(); }
    public String getType() { return type.get(); }
    public StringProperty osProperty() { return os; } 
    public String getOs() { return os.get(); } 

    // Value setters
    public void setStatus(String status) { this.status.set(status); }
    public void setOs(String os) { this.os.set(os); } 

    @Override
    public String toString() {
        return String.format("Device{hostname='%s', ip='%s', status='%s', os='%s'}",
            getHostname(), getIp(), getStatus(), getOs());
    }

     public static List<Device> parseFromJson(String json) {
        List<Device> devices = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json); // Parse the JSON array

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String ipAddress = jsonObject.getString("ip_address");
                String macAddress = jsonObject.getString("mac_address");
                String hostname = jsonObject.getString("hostname");
                String os = jsonObject.getString("os");
                String status = jsonObject.getString("status");

                Device device = new Device(ipAddress, macAddress, hostname, status, os);
                devices.add(device);
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        }
        return devices;
    }
}