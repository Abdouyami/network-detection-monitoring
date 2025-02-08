package com.networkmonitor.controller;

import java.util.HashMap;
import java.util.Map;

import com.networkmonitor.model.Device;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.transform.Scale;

public class NetworkMapController {
    @FXML private Pane mapCanvas;
    @FXML private ScrollPane scrollPane;
    @FXML private TextField txtSearch;
    @FXML private CheckBox chkActive;
    @FXML private CheckBox chkSuspicious;
    @FXML private CheckBox chkInactive;

    private final Map<String, ImageView> deviceIcons = new HashMap<>();
    private final ObservableList<Device> devices = FXCollections.observableArrayList();
    private final double ICON_SIZE = 40.0;
    private double dragStartX, dragStartY;
    private final Scale scaleTransform = new Scale(1, 1);

    @FXML
    private void initialize() {
        setupInitialDevices();
        setupMapControls();
        drawDevices();
    }

    private void setupInitialDevices() {
        devices.addAll(
            new Device("192.168.1.1", "00:1A:2B:3C:4D:5E", "Router-1", "Active"),
            new Device("192.168.1.2", "00:1A:2B:3C:4D:5A", "Router-2", "Inactive"),
            new Device("192.168.1.3", "00:1A:2B:3C:4D:5F", "PC-01", "Active"),
            new Device("192.168.1.4", "00:1A:2B:3C:4D:60", "PC-02", "Suspicious")
        );
    }

    private void setupMapControls() {
        mapCanvas.getTransforms().add(scaleTransform);
        scrollPane.setOnScroll(event -> {
            double zoomFactor = event.getDeltaY() > 0 ? 1.1 : 0.9;
            scaleTransform.setX(scaleTransform.getX() * zoomFactor);
            scaleTransform.setY(scaleTransform.getY() * zoomFactor);
            event.consume();
        });

        mapCanvas.setOnMousePressed(event -> {
            dragStartX = event.getSceneX() - mapCanvas.getTranslateX();
            dragStartY = event.getSceneY() - mapCanvas.getTranslateY();
            event.consume();
        });

        mapCanvas.setOnMouseDragged(event -> {
            mapCanvas.setTranslateX(event.getSceneX() - dragStartX);
            mapCanvas.setTranslateY(event.getSceneY() - dragStartY);
            event.consume();
        });

        chkActive.selectedProperty().addListener((obs, old, newVal) -> handleFilter());
        chkSuspicious.selectedProperty().addListener((obs, old, newVal) -> handleFilter());
        chkInactive.selectedProperty().addListener((obs, old, newVal) -> handleFilter());
    }

    @FXML
    private void handleSearch() {
        String searchTerm = txtSearch.getText().toLowerCase();
        mapCanvas.getChildren().clear();
        deviceIcons.clear();

        for (Device device : devices) {
            if (device.getHostname().toLowerCase().contains(searchTerm) ||
                device.getIp().toLowerCase().contains(searchTerm)) {
                addDeviceToMap(device);
            }
        }
        drawConnections();
    }

    private void addDeviceToMap(Device device) {
        double x = 100 + (deviceIcons.size() * 150);
        double y = 100;
        if (x > 800) {
            x = 100;
            y += 150;
        }
        createDeviceIcon(device, x, y);

        // Create Label with MAC and Hostname
        Label Infolabel = new Label(device.getHostname() + "\n" + device.getMac());
        Infolabel.setTextFill(Color.WHITE); // Set text color
        Infolabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;"); // Font styling
        Infolabel.setLayoutX(x);
        Infolabel.setLayoutY(y + ICON_SIZE + 5); // Position below the icon
    
        mapCanvas.getChildren().add(Infolabel);
    }

    @FXML
    private void handleFilter() {
        mapCanvas.getChildren().clear();
        deviceIcons.clear();

        for (Device device : devices) {
            String status = device.getStatus().toLowerCase();
            if ((status.equals("active") && chkActive.isSelected()) ||
                (status.equals("suspicious") && chkSuspicious.isSelected()) ||
                (status.equals("inactive") && chkInactive.isSelected())) {
                addDeviceToMap(device);
            }
        }
        drawConnections();
    }

    @FXML
    private void handleRefresh() {
        drawDevices();
    }

    @FXML
    private void handleExport() {
        // TODO: Implement export functionality
        System.out.println("Export functionality to be implemented");
    }

    private void drawDevices() {
        mapCanvas.getChildren().clear();
        deviceIcons.clear();

        for (Device device : devices) {
            addDeviceToMap(device);
        }
        drawConnections();
    }

    private ImageView createDeviceIcon(Device device, double x, double y) {
        String iconPath = String.format("/device-icons/%s_%s.png", 
            device.getType().toLowerCase(),
            device.getStatus().toLowerCase());
        
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(iconPath), 
            ICON_SIZE, ICON_SIZE, true, true));
        
        icon.setX(x);
        icon.setY(y);
        icon.setId(device.getType().toLowerCase() + "_" + device.getIp());

        setupDeviceTooltip(icon, device);
        setupDeviceContextMenu(icon, device);
        
        deviceIcons.put(device.getIp(), icon);
        mapCanvas.getChildren().add(icon);
        
        return icon;
    }

    private void setupDeviceTooltip(ImageView icon, Device device) {
        Tooltip tooltip = new Tooltip(
            String.format("Hostname: %s\nIP: %s\nMAC: %s\nStatus: %s",
                device.getHostname(), device.getIp(),
                device.getMac(), device.getStatus())
        );
        Tooltip.install(icon, tooltip);
    }

    private void setupDeviceContextMenu(ImageView icon, Device device) {
        ContextMenu menu = new ContextMenu();
        
        MenuItem pingItem = new MenuItem("Ping Device");
        pingItem.setOnAction(e -> pingDevice(device));
        
        MenuItem infoItem = new MenuItem("Device Info");
        infoItem.setOnAction(e -> showDeviceInfo(device));
        
        MenuItem removeItem = new MenuItem("Remove Device");
        removeItem.setOnAction(e -> removeDevice(device));

        menu.getItems().addAll(pingItem, infoItem, removeItem);
        
        icon.setOnContextMenuRequested(e -> 
            menu.show(icon, e.getScreenX(), e.getScreenY()));
    }

    private void drawConnections() {
        // Draw connection from router to all other devices
        ImageView routerIcon = deviceIcons.values().stream()
            .filter(icon -> icon.getId().contains("router"))
            .findFirst()
            .orElse(null);

        if (routerIcon != null) {
            for (ImageView deviceIcon : deviceIcons.values()) {
                if (!deviceIcon.equals(routerIcon)) {
                    drawConnection(routerIcon, deviceIcon);
                }
            }
        }
    }

    private void drawConnection(ImageView start, ImageView end) {
        double startX = start.getX() + ICON_SIZE / 2;
        double startY = start.getY() + ICON_SIZE / 2;
        double endX = end.getX() + ICON_SIZE / 2;
        double endY = end.getY() + ICON_SIZE / 2;

        CubicCurve curve = new CubicCurve(
            startX, startY,
            (startX + endX) / 2, startY,
            (startX + endX) / 2, endY,
            endX, endY
        );

        curve.setStroke(Color.LIGHTBLUE);
        curve.setStrokeWidth(2);
        curve.setFill(null);

        mapCanvas.getChildren().add(0, curve);
    }

    private void pingDevice(Device device) {
        System.out.println("Pinging device: " + device.getIp());
    }

    private void showDeviceInfo(Device device) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Device Information");
        alert.setHeaderText(device.getHostname());
        alert.setContentText(String.format(
            "IP Address: %s\nMAC Address: %s\nStatus: %s\nType: %s",
            device.getIp(), device.getMac(),
            device.getStatus(), device.getType()));
        alert.showAndWait();
    }

    private void removeDevice(Device device) {
        ImageView icon = deviceIcons.remove(device.getIp());
        if (icon != null) {
            mapCanvas.getChildren().remove(icon);
            devices.remove(device);
        }
        drawConnections();
    }
}