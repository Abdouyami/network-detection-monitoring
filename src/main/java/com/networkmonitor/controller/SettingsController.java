package com.networkmonitor.controller;

import com.networkmonitor.service.NotificationService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SettingsController {

    @FXML private TextField smtpHostField;
    @FXML private TextField smtpPortField;
    @FXML private TextField smtpUsernameField;
    @FXML private PasswordField smtpPasswordField;
    @FXML private ComboBox<String> securityProtocolComboBox;
    @FXML private TextField scanIntervalField;
    @FXML private TextField dbURLField;
    @FXML private TextField dbUserField;
    @FXML private PasswordField dbPasswordField;

    private NotificationService notificationService;
    private MainController mainController;  

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        // Load settings from configuration (e.g., properties file, database)
        loadSettings();
    }

    private void loadSettings() {
        // Example: Replace with your actual settings loading logic
        smtpHostField.setText(loadSetting("smtp.host", "smtp.example.com")); // Default values
        smtpPortField.setText(loadSetting("smtp.port", "587"));
        smtpUsernameField.setText(loadSetting("smtp.Username", "your.email@example.com"));
        smtpPasswordField.setText(loadSetting("smtp.password", "yourpassword"));
        securityProtocolComboBox.setValue(loadSetting("smtp.security", "None")); // Default "None"

        scanIntervalField.setText(loadSetting("scan.interval", "60"));

        dbURLField.setText(loadSetting("db.url", "jdbc:mysql://localhost:3306/network_monitor"));
        dbUserField.setText(loadSetting("db.user", "dbuser"));
        dbPasswordField.setText(loadSetting("db.password", "dbpassword"));

        // Set ComboBox items programmatically:
        securityProtocolComboBox.setItems(FXCollections.observableArrayList(
                "None",
                "SSL",
                "TLS"
        ));

        // Set the initial value (after setting the items!):
        securityProtocolComboBox.setValue(loadSetting("smtp.security", "None")); // Default "None"
    }

    private String loadSetting(String key, String defaultValue) {
        // Replace with your actual settings retrieval logic
        // This could involve reading from a properties file, database, etc.
        // For demonstration, I am using System properties
        return System.getProperty(key, defaultValue);
    }

    @FXML
    private void handleSave() {
        // Save settings to configuration
        saveSettings();

        // Initialize NotificationService with the saved settings
        String smtpHost = smtpHostField.getText();
        String smtpPort = smtpPortField.getText();
        String smtpEmail = smtpUsernameField.getText();
        String smtpPassword = smtpPasswordField.getText();
        String securityProtocol = securityProtocolComboBox.getValue();

        // Debugging: Log the SMTP settings being used
        System.out.println("Initializing NotificationService with the following settings:");
        System.out.println("SMTP Host: " + smtpHost);
        System.out.println("SMTP Port: " + smtpPort);
        System.out.println("SMTP Email: " + smtpEmail);
        System.out.println("Security Protocol: " + securityProtocol);

        // Create new NotificationService instance
        notificationService = new NotificationService(
            smtpHost,
            smtpPort,
            smtpEmail,
            smtpPassword,
            securityProtocol
        );
        
        // Update the NotificationService in MainController
        if (mainController != null) {
            mainController.setNotificationService(notificationService);
        }
    }

    private void saveSettings() {
        // Example: Replace with your actual settings storage logic
        saveSetting("smtp.host", smtpHostField.getText());
        saveSetting("smtp.port", smtpPortField.getText());
        saveSetting("smtp.Email", smtpUsernameField.getText());
        saveSetting("smtp.password", smtpPasswordField.getText());
        saveSetting("smtp.security", securityProtocolComboBox.getValue());
        saveSetting("scan.interval", scanIntervalField.getText());
        saveSetting("db.url", dbURLField.getText());
        saveSetting("db.user", dbUserField.getText());
        saveSetting("db.password", dbPasswordField.getText());

        System.out.println("Settings saved!"); // Or show a success message
    }

    private void saveSetting(String key, String value) {
        // Replace with your actual settings storage logic
        System.setProperty(key, value); // Example: Using System properties
    }
}