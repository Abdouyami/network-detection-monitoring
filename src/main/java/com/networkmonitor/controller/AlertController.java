package com.networkmonitor.controller;

import com.networkmonitor.model.Alert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AlertController {
    // @FXML private TableView<Alert> alertTable;
    // @FXML private TableColumn<Alert, String> typeColumn;
    // @FXML private TableColumn<Alert, String> severityColumn;
    // @FXML private TableColumn<Alert, String> descriptionColumn;

    // @SuppressWarnings("FieldMayBeFinal")
    // private ObservableList<Alert> alertList = FXCollections.observableArrayList();

    // @FXML
    // public void initialize() {
    //     typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
    //     severityColumn.setCellValueFactory(cellData -> cellData.getValue().severityProperty());
    //     descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

    //     alertTable.setItems(alertList);
    // }

    // @FXML
    // @SuppressWarnings("unused")
    // private void handleClearAlert() {
    //     Alert selected = alertTable.getSelectionModel().getSelectedItem();
    //     if (selected != null) {
    //         alertList.remove(selected);
    //     }
    // }

    // @FXML
    // @SuppressWarnings("unused")
    // private void handleRefreshAlerts() {
    //     System.out.println("Refreshing alert list...");
    // }
}
