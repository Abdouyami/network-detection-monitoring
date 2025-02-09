package com.networkmonitor.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import com.networkmonitor.model.Alert;
import com.networkmonitor.model.Device;
import com.networkmonitor.model.Notification;
import com.networkmonitor.model.User;
import com.networkmonitor.utils.FakeDataGenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class NotificationsController implements Initializable {
    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> cmbFilter;
    @FXML private TableView<Notification> tblNotifications;
    @FXML private TableColumn<Notification, String> colAlertType;
    @FXML private TableColumn<Notification, String> colSeverity;
    @FXML private TableColumn<Notification, String> colMessage;
    @FXML private TableColumn<Notification, LocalDateTime> colTimestamp;
    @FXML private TableColumn<Notification, String> colStatus;
    @FXML private TableColumn<Notification, Boolean> colRead;

    private ObservableList<Notification> notifications;
    private FilteredList<Notification> filteredNotifications;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize table columns
        colAlertType.setCellValueFactory(cellData -> 
            cellData.getValue().getAlert().typeProperty());
        colSeverity.setCellValueFactory(cellData -> 
            cellData.getValue().getAlert().severityProperty());
        colMessage.setCellValueFactory(cellData -> 
            cellData.getValue().messageProperty());
        colTimestamp.setCellValueFactory(cellData -> 
            cellData.getValue().timestampProperty());
        colStatus.setCellValueFactory(cellData -> 
            cellData.getValue().statusProperty());
        colRead.setCellValueFactory(cellData -> 
            cellData.getValue().isReadProperty());

        // Format timestamp column
        colTimestamp.setCellFactory(column -> new TableCell<Notification, LocalDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });

        // Format read status column
        colRead.setCellFactory(column -> new TableCell<Notification, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Yes" : "No");
                }
            }
        });

        // Set up severity column colors
        colSeverity.setCellFactory(column -> new TableCell<Notification, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    switch (item.toLowerCase()) {
                        case "high" -> setStyle("-fx-text-fill: red;");
                        case "medium" -> setStyle("-fx-text-fill: orange;");
                        case "low" -> setStyle("-fx-text-fill: green;");
                        default -> setStyle("");
                    }
                }
            }
        });

        // Set up status column colors
        colStatus.setCellFactory(column -> new TableCell<Notification, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    switch (item.toLowerCase()) {
                        case "pending" -> setStyle("-fx-text-fill: orange;");
                        case "sent" -> setStyle("-fx-text-fill: green;");
                        default -> setStyle("");
                    }
                }
            }
        });

        // Initialize filter options
        //"All", "Read", "Unread",
        cmbFilter.getItems().addAll( "All","Sent", "Pending");
        cmbFilter.setValue("All");

        // Initialize notifications list
        notifications = FXCollections.observableArrayList();
        filteredNotifications = new FilteredList<>(notifications);
        tblNotifications.setItems(filteredNotifications);

        // Add search listener
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());

        // Load initial data
        loadNotifications();
    }

    private void loadNotifications() {
        // Generate fake data
        List<Device> fakeDevices = FakeDataGenerator.generateDevices(20);
        List<Alert> fakeAlerts = FakeDataGenerator.generateAlerts(fakeDevices, 20);
        List<User> fakeUsers = FakeDataGenerator.generateUsers(5);
        List<Notification> fakeNotifications = FakeDataGenerator.generateNotifications(fakeAlerts, fakeUsers, 20);

        // Add fake notifications to the observable list
        notifications.addAll(fakeNotifications);
    }

    @FXML
    private void applyFilter() {
        String searchText = txtSearch.getText().toLowerCase();
        String filter = cmbFilter.getValue();

        filteredNotifications.setPredicate(notification -> {
            boolean matchesSearch = 
                notification.getMessage().toLowerCase().contains(searchText) ||
                notification.getAlert().getType().toLowerCase().contains(searchText) ||
                notification.getAlert().getSeverity().toLowerCase().contains(searchText);
            
            boolean matchesFilter = switch (filter) {
                case "Read" -> notification.isRead();
                case "Unread" -> !notification.isRead();
                case "Sent" -> notification.getStatus().equals("sent");
                case "Pending" -> notification.getStatus().equals("pending");
                default -> true;
            };
            return matchesSearch && matchesFilter;
        });
    }

    @FXML
    private void markAsRead() {
        ObservableList<Notification> selectedItems = tblNotifications.getSelectionModel().getSelectedItems();
        for (Notification notification : selectedItems) {
            notification.setRead(true);
        }
        tblNotifications.refresh();
    }

    @FXML
    private void markAsResolved() {
        ObservableList<Notification> selectedItems = tblNotifications.getSelectionModel().getSelectedItems();
        for (Notification notification : selectedItems) {
            notification.setStatus("sent");
        }
        tblNotifications.refresh();
    }
}