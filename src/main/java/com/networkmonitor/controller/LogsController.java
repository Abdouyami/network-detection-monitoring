package com.networkmonitor.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import com.networkmonitor.model.Log;
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

public class LogsController implements Initializable {
    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> cmbFilter;
    @FXML private TableView<Log> tblLogs;
    @FXML private TableColumn<Log, LocalDateTime> colTimestamp;
    @FXML private TableColumn<Log, String> colType;
    @FXML private TableColumn<Log, String> colMessage;
    @FXML private TableColumn<Log, String> colUser;
    @FXML private TableColumn<Log, String> colSeverity;

    private ObservableList<Log> logs;
    private FilteredList<Log> filteredLogs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize table columns
        colTimestamp.setCellValueFactory(cellData -> 
            cellData.getValue().timestampProperty());
        colType.setCellValueFactory(cellData -> 
            cellData.getValue().typeProperty());
        colMessage.setCellValueFactory(cellData -> 
            cellData.getValue().messageProperty());
        colUser.setCellValueFactory(cellData -> 
            cellData.getValue().userProperty());
        colSeverity.setCellValueFactory(cellData -> 
            cellData.getValue().severityProperty());

        // Format timestamp column
        colTimestamp.setCellFactory(column -> new TableCell<Log, LocalDateTime>() {
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

        // Set up severity column colors
        colSeverity.setCellFactory(column -> new TableCell<Log, String>() {
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

         // Set up type column colors
         colType.setCellFactory(column -> new TableCell<Log, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    switch (item.toLowerCase()) {
                        case "warning" -> setStyle("-fx-text-fill: red;");
                        case "error" -> setStyle("-fx-text-fill: orange;");
                        case "info" -> setStyle("-fx-text-fill: green;");
                        default -> setStyle("");
                    }
                }
            }
        });

        // Initialize filter options
        cmbFilter.getItems().addAll("All", "Info", "Warning", "Error");
        cmbFilter.setValue("All");

        // Initialize logs list
        logs = FXCollections.observableArrayList();
        filteredLogs = new FilteredList<>(logs);
        tblLogs.setItems(filteredLogs);

        // Add search listener
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());

        // Load initial data
        loadLogs();
    }

    private void loadLogs() {
        // Generate fake logs
        List<Log> fakeLogs = FakeDataGenerator.generateLogs(20);
        logs.addAll(fakeLogs);
    }

    @FXML
    private void applyFilter() {
        String searchText = txtSearch.getText().toLowerCase();
        String filter = cmbFilter.getValue();

        filteredLogs.setPredicate(log -> {
            boolean matchesSearch = 
                log.getMessage().toLowerCase().contains(searchText) ||
                log.getType().toLowerCase().contains(searchText) ||
                log.getUser().toLowerCase().contains(searchText) ||
                log.getSeverity().toLowerCase().contains(searchText);
            
            boolean matchesFilter = switch (filter) {
                case "Info" -> log.getType().equalsIgnoreCase("info");
                case "Warning" -> log.getType().equalsIgnoreCase("warning");
                case "Error" -> log.getType().equalsIgnoreCase("error");
                default -> true;
            };
            return matchesSearch && matchesFilter;
        });
    }

    @FXML
    private void exportLogs() {
        System.out.println("Exporting logs...");
        // TODO: Implement export logic (e.g., save logs to a file)
    }

    @FXML
    private void clearLogs() {
        logs.clear();
        tblLogs.refresh();
    }
}