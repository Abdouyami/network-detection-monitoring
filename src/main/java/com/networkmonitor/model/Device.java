package com.networkmonitor.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Device {
    private final StringProperty ip;
    private final StringProperty mac;
    private final StringProperty status;

    public Device(String ip, String mac, String status) {
        this.ip = new SimpleStringProperty(ip);
        this.mac = new SimpleStringProperty(mac);
        this.status = new SimpleStringProperty(status);
    }

    public StringProperty ipProperty() { return ip; }
    public StringProperty macProperty() { return mac; }
    public StringProperty statusProperty() { return status; }
}
