package com.networkmonitor.utils;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UIManager {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static void loadScene(String fxml) {
        try {
            if (primaryStage != null) {
                Parent root = FXMLLoader.load(UIManager.class.getResource(fxml));
                Scene scene = new Scene(root);
                
                // Add CSS files
                scene.getStylesheets().add(UIManager.class.getResource("/common.css").toExternalForm());
                scene.getStylesheets().add(UIManager.class.getResource("/main.css").toExternalForm());
                scene.getStylesheets().add(UIManager.class.getResource("/device.css").toExternalForm());
                scene.getStylesheets().add(UIManager.class.getResource("/dashboard.css").toExternalForm());
                scene.getStylesheets().add(UIManager.class.getResource("/alert.css").toExternalForm());
                scene.getStylesheets().add(UIManager.class.getResource("/traffic.css").toExternalForm());
                scene.getStylesheets().add(UIManager.class.getResource("/user.css").toExternalForm());
                scene.getStylesheets().add(UIManager.class.getResource("/vulnerability.css").toExternalForm());
                scene.getStylesheets().add(UIManager.class.getResource("/network-map.css").toExternalForm());
                
                
                primaryStage.setScene(scene);
                primaryStage.setTitle("Network Admin Panel");
                // Set main view logo
                primaryStage.getIcons().add(new Image(UIManager.class.getResourceAsStream("/images/main-logo.png")));

                // Allow maximization and maximize the window for MainView
                primaryStage.setResizable(true);
                primaryStage.setMaximized(true);

                primaryStage.show(); // Ensure the stage is shown
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static void loadView(Pane container, String fxml) {
        try {
            Parent view = FXMLLoader.load(UIManager.class.getResource(fxml));
            container.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
