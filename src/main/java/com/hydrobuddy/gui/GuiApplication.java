package com.hydrobuddy.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


/**
 * The main JavaFX application class.
 * This class is responsible for loading the FXML,
 * initializing the Scene, and displaying the primary GUI window.
 */
public class GuiApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // 1. Find our FXML file in the resources/gui/ folder
        URL fxmlUrl = getClass().getResource("/gui/MainView.fxml");
        if (fxmlUrl == null) {
            System.err.println("Cannot find FXML file. Make sure it's in src/main/resource/gui");
            throw new IOException("FXML file not found");
        }

        // 2. Load the FXML file
        // This process creates the UI (View) and links it to the Controller
        Parent root = FXMLLoader.load(fxmlUrl);

        // 3. Create a "Scene"
        Scene scene = new Scene(root, 400, 300);

        // 4. Configure the main window ("Stage")
        primaryStage.setTitle("Hydrobuddy");
        primaryStage.setScene(scene);

        // 5. THIS IS THE NEW PART: Add a shutdown hook
        // Tell the app what to do when the "X" button is clicked.
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Window closing, shutting down service...");
            com.hydrobuddy.core.ReminderService.getInstance().shutdown();
            javafx.application.Platform.exit();
        });

        // 6. Show the window here
        primaryStage.show();
    }
}
