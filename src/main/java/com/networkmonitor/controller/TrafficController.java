package com.networkmonitor.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.networkmonitor.config.APIConfig;
import com.networkmonitor.model.Traffic;
import com.networkmonitor.utils.HttpClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TrafficController implements Initializable {
    @FXML private TextField txtSearch;
    @FXML private TableView<Traffic> tblTraffic;
    @FXML private TableColumn<Traffic, String> colSourceIp;
    @FXML private TableColumn<Traffic, String> colDestinationIp;
    @FXML private TableColumn<Traffic, String> colProtocol;
    @FXML private TableColumn<Traffic, Integer> colPort;
    @FXML private TableColumn<Traffic, Integer> colBytesSent;
    @FXML private TableColumn<Traffic, Integer> colBytesReceived;
    @FXML private TableColumn<Traffic, String> colTimestamp;

    private ObservableList<Traffic> trafficList;
    private FilteredList<Traffic> filteredTraffic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bind columns to Traffic properties
        colSourceIp.setCellValueFactory(new PropertyValueFactory<>("sourceIp"));
        colDestinationIp.setCellValueFactory(new PropertyValueFactory<>("destinationIp"));
        colProtocol.setCellValueFactory(new PropertyValueFactory<>("protocol"));
        colPort.setCellValueFactory(new PropertyValueFactory<>("port"));
        colBytesSent.setCellValueFactory(new PropertyValueFactory<>("bytesSent"));
        colBytesReceived.setCellValueFactory(new PropertyValueFactory<>("bytesReceived"));
        colTimestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

         // Add search listener
         txtSearch.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());

        // Load real traffic data from the API
        loadTraffic();
    }

    private void loadTraffic() {
        // Fetch real data from the API
        String url = APIConfig.TRAFFIC_URL; // Use the base traffic URL
        String response = HttpClient.get(url); // Fetch data from the API

        if (response != null) {
            System.err.println("Fetched traffic from the API: " + response);
            trafficList = FXCollections.observableArrayList(Traffic.parseFromJson(response));
            filteredTraffic = new FilteredList<>(trafficList);
            tblTraffic.setItems(filteredTraffic);
        } else {
            System.err.println("Failed to fetch traffic from the API.");
        }
    }

    @FXML
    private void handleSearch() {
        String searchText = txtSearch.getText().toLowerCase();
        filteredTraffic.setPredicate(traffic -> {
            boolean matchesSearch = 
                traffic.getSourceIp().toLowerCase().contains(searchText) ||
                traffic.getDestinationIp().toLowerCase().contains(searchText) ||
                traffic.getProtocol().toLowerCase().contains(searchText) ||
                Integer.toString(traffic.getPort()).contains(searchText);
            return matchesSearch;
        });
    }

    @FXML
    private void handleRefresh() {
        txtSearch.clear();
        loadTraffic();
    }

    @FXML
    private void handleExportTraffic() {
        System.out.println("Exporting traffic...");
        // TODO: Implement export logic (e.g., save traffic to a file)
    }

    @FXML
    private void handleViewDetails() {
        Traffic selectedTraffic = tblTraffic.getSelectionModel().getSelectedItem();
        if (selectedTraffic != null) {
            System.out.println("Viewing details for: " + selectedTraffic.getSourceIp());
            // TODO: Implement traffic details view
        }
    }
}