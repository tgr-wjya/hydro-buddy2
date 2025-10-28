package com.hydrobuddy.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.hydrobuddy.notification.DesktopNotificationProvider;

import java.io.IOException;
import java.net.URL;

/**
 * The main JavaFX application class.
 * Responsible for loading the FXML, initializing the Scene, and displaying the primary GUI window.
 */
public class GuiApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL fxmlUrl = getClass().getResource("/gui/MainView.fxml");
        if (fxmlUrl == null) {
            System.err.println("Cannot find FXML file. Make sure it's in src/main/resource/gui");
            throw new IOException("FXML file not found");
        }

        Parent root = FXMLLoader.load(fxmlUrl);
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Hydrobuddy");
        primaryStage.setScene(scene);

        // Initialize notification provider early so tray icon is available while app runs
        DesktopNotificationProvider.getInstance();

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Window closing, shutting down service...");
            com.hydrobuddy.core.ReminderService.getInstance().shutdown();
            DesktopNotificationProvider.getInstance().shutdown();
            javafx.application.Platform.exit();
        });

        primaryStage.show();
    }
}