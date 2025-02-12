package com.networkmonitor.controller;

import org.json.JSONObject;

import com.networkmonitor.model.User;
import com.networkmonitor.service.AuthService;
import com.networkmonitor.utils.UIManager;
import com.networkmonitor.utils.UserSession;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML
    private ProgressIndicator loginProgress;

    @FXML
    public void initialize() {
        // Request focus on the username field initially
        usernameField.requestFocus();

        // Add event filter to username field for ENTER key press
        usernameField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus(); // Move focus to password field
                event.consume(); // Prevent default behavior (like adding a newline)
            }
        });

        // Add event filter to password field for ENTER key press
        passwordField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin(); // Trigger login
                event.consume();
            }
        });
    }

    
    @FXML
private void handleLogin() {
    String username = usernameField.getText();
    String password = passwordField.getText();

    if (username.isEmpty() || password.isEmpty()) {
        showCustomErrorDialog("Please enter both username and password!");
        return;
    }

    loginProgress.setVisible(true);  // Assuming you added the ProgressIndicator
    
    new Thread(() -> {
        try {
            AuthService.LoginResponse response = AuthService.login(username, password);
            
            Platform.runLater(() -> {
                loginProgress.setVisible(false);
                if (response.isSuccess()) {
                    // You can store user data if needed
                    JSONObject userData = response.getUserData();
                    System.err.println("User data: " + userData.toString());
                    // Create a User object
                    User user = new User(
                        userData.getString("username"),
                        userData.getString("email"),
                        userData.getString("role")
                    );

                    // Store the User object in the singleton
                    UserSession.getInstance().setUser(user);
                    // Store user role or other data as needed
                    UIManager.loadScene("../view/MainView.fxml");
                } else {
                    showCustomErrorDialog(response.getMessage());
                }
            });
        } catch (Exception e) {
            Platform.runLater(() -> {
                loginProgress.setVisible(false);
                showCustomErrorDialog("Connection error: " + e.getMessage());
                e.printStackTrace();
            });
        }
    }).start();
}
    private void showCustomErrorDialog(String message) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Error");  // Set the title
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK); // Add OK button

        Stage primaryStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/error-logo.png")));

        // Create content for the dialog
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #FFFFFF;"); // Style the message
        VBox dialogContent = new VBox(messageLabel);
        dialogContent.setPadding(new Insets(20));
        dialogContent.setSpacing(10);

        dialog.getDialogPane().setContent(dialogContent);

        // Apply your CSS to the dialog
        Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        Scene scene = dialogStage.getScene();

        if (scene == null) {
          scene = new Scene(dialog.getDialogPane());
          dialogStage.setScene(scene);
        }
        scene.getStylesheets().add(getClass().getResource("/login.css").toExternalForm()); // Path to your CSS

        dialog.showAndWait();
    }
}


