package com.networkmonitor.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Traffic {
    private final StringProperty sourceIp;
    private final StringProperty destinationIp;
    private final StringProperty protocol;
    private final IntegerProperty port;
    private final IntegerProperty bytesSent;
    private final IntegerProperty bytesReceived;
    private final StringProperty timestamp;

    public Traffic(String sourceIp, String destinationIp, String protocol, int port, int bytesSent, int bytesReceived, String timestamp) {
        this.sourceIp = new SimpleStringProperty(sourceIp);
        this.destinationIp = new SimpleStringProperty(destinationIp);
        this.protocol = new SimpleStringProperty(protocol);
        this.port = new SimpleIntegerProperty(port);
        this.bytesSent = new SimpleIntegerProperty(bytesSent);
        this.bytesReceived = new SimpleIntegerProperty(bytesReceived);
        this.timestamp = new SimpleStringProperty(timestamp);
    }

    // Property getters
    public StringProperty sourceIpProperty() { return sourceIp; }
    public StringProperty destinationIpProperty() { return destinationIp; }
    public StringProperty protocolProperty() { return protocol; }
    public IntegerProperty portProperty() { return port; }
    public IntegerProperty bytesSentProperty() { return bytesSent; }
    public IntegerProperty bytesReceivedProperty() { return bytesReceived; }
    public StringProperty timestampProperty() { return timestamp; }

    // Value getters
    public String getSourceIp() { return sourceIp.get(); }
    public String getDestinationIp() { return destinationIp.get(); }
    public String getProtocol() { return protocol.get(); }
    public int getPort() { return port.get(); }
    public int getBytesSent() { return bytesSent.get(); }
    public int getBytesReceived() { return bytesReceived.get(); }
    public String getTimestamp() { return timestamp.get(); }

    // Parse JSON response into a list of Traffic objects
    public static List<Traffic> parseFromJson(String json) {
        List<Traffic> trafficList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json); // Parse the JSON array

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String sourceIp = jsonObject.getString("source_ip");
                String destinationIp = jsonObject.getString("destination_ip");
                String protocol = jsonObject.getString("protocol");
                int port = jsonObject.getInt("port");
                int bytesSent = jsonObject.getInt("bytes_sent");
                int bytesReceived = jsonObject.getInt("bytes_received");
                String timestamp = jsonObject.getString("timestamp");

                Traffic traffic = new Traffic(sourceIp, destinationIp, protocol, port, bytesSent, bytesReceived, timestamp);
                trafficList.add(traffic);
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        }
        return trafficList;
    }
}