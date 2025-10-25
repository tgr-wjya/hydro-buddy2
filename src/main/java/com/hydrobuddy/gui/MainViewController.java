package com.hydrobuddy.gui;

import javafx.fxml.FXML;
import com.hydrobuddy.core.ReminderService;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;

/**
 * Controller for the MainView.xml file.
 * This class handles all UI events and logic for the main window.
 */

public class MainViewController {

    // These @FXML are automatically inhected by JavaFX.
    // The variable name MUST match the fx:id in the FXML file.

    // 1. REMOVE intervalLabel, ADD intervalComboBox
    @FXML
    private ComboBox<Integer> intervalComboBox;

    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Label statusLabel;

    // 2. Get the singleton instance of our service
    private final ReminderService reminderService = ReminderService.getInstance();

    /**
     * This method is automatically called by JavaFX after the FXML
     * file has been loaded and all @FXML fields are injected.
     * This is the perfect place to set up event handlers.
     */
    @FXML
    private void initialize() {
        // Add click handlers for our buttons
        startButton.setOnAction(event -> onStartClicked());
        stopButton.setOnAction(event -> onStopClicked());

        // 3. Populate the ComboBox
        intervalComboBox.getItems().addAll(1, 15, 30, 45, 60);
        intervalComboBox.setValue(30); // Set as default

        // Set initial UI state
        stopButton.setDisable(true); // Can't stop if not even running
        statusLabel.setText("Status: Ready");

        System.out.println("MainViewController initialized.");
    }

    /**
     * Called when the "Start" button is clicked.
     */
    private void onStartClicked() {
        // ... (logging and status lines are the same)
        System.out.println("Start button clicked!");
        statusLabel.setText("Status: Running...");
        startButton.setDisable(true);
        stopButton.setDisable(false);
        intervalComboBox.setDisable(true); // 4. Disable dropdown while running

        // 5. This is the new part:
        // Define the task we want the service to run
        Runnable reminderTask = () -> {
            // This code will run on the background thread
            System.out.println("---------------------------------");
            System.out.println("REMINDER: Time to drink water!");
            System.out.println("---------------------------------");
        };

        // 6. GET THE VALUE from the ComboBox
        long interval = intervalComboBox.getValue();

        // Tell the service to start
        reminderService.start(reminderTask, interval);
    }

    /**
     * Called when the "Stop" button clicked.
     */
    @FXML
    private void onStopClicked() {
        System.out.println("Stop button clicked!");
        statusLabel.setText("Status: Stopped.");
        startButton.setDisable(true);
        stopButton.setDisable(true);
        intervalComboBox.setDisable(false); // 7. Re-enable dropdown

        // 8. Tell the service to stop
        reminderService.stop();
    }
}
