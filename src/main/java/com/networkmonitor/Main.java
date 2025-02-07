package com.networkmonitor;

import java.io.IOException;

import com.networkmonitor.utils.UIManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void start(Stage primaryStage) {
        try {            
            Parent root = FXMLLoader.load(getClass().getResource("/com/networkmonitor/view/LoginView.fxml"));
            Scene scene = new Scene(root);
            // Load CSS file
            scene.getStylesheets().add(getClass().getResource("/common.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/login.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/main.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/alert.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/dashboard.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/traffic.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/user.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/vulnerability.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/device.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Network Admin Login");
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
            primaryStage.centerOnScreen(); // Centre la fenêtre à l'écran
            // Prevent window from being maximized
            primaryStage.setResizable(false);

            UIManager.setPrimaryStage(primaryStage);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}