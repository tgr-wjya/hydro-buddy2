package com.hydrobuddy.gui;

import com.hydrobuddy.core.ReminderService;
import com.hydrobuddy.core.SoundPlayer; // 1. Import SoundPlayer
import javafx.application.Platform; // 2. Import Platform
import javafx.fxml.FXML;
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

    @FXML
    private ComboBox<Integer> intervalComboBox;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Label statusLabel;

    private final ReminderService reminderService = ReminderService.getInstance();

    // 3. Creare an instance of our SoundPlayer
    private final SoundPlayer soundPlayer = new SoundPlayer("/sounds/MGS_Alert.mp3");

    private int tickCounter = 0; // For our UI update test

    /**
     * This method is automatically called by JavaFX after the FXML
     * file has been loaded and all @FXML fields are injected.
     * This is the perfect place to set up event handlers.
     */
    @FXML
    private void initialize() {
        // ... (all the same)
        startButton.setOnAction(event -> onStartClicked());
        stopButton.setOnAction(event -> onStopClicked());
        intervalComboBox.getItems().addAll(1, 15, 30, 45, 60);
        intervalComboBox.setValue(30); // Set as default
        stopButton.setDisable(true); // Can't stop if not even running
        statusLabel.setText("Status: Ready");
        System.out.println("MainViewController initialized.");
    }

    /**
     * Called when the "Start" button is clicked.
     */
    private void onStartClicked() {
        statusLabel.setText("Status: Running...");
        startButton.setDisable(true);
        stopButton.setDisable(false);
        intervalComboBox.setDisable(true);
        tickCounter = 0; // Reset counter

        // 4. This is the task that runs on the BACKGROUND thread
        Runnable reminderTask = () -> {
            System.out.println("---------------------------------");
            System.out.println("REMINDER: Time to drink water!");
            System.out.println("---------------------------------");

            soundPlayer.playSound();

            // 6. THIS IS THE FIX
            // To update the UI, we must use Platform.runLater
            Platform.runLater(() -> {
                // This code runs safely on the main UI thread
                tickCounter++;
                statusLabel.setText("Status: Running... (Reminder #" + tickCounter + ")");
            });
        };

        long interval = intervalComboBox.getValue();
        reminderService.start(reminderTask, interval);
    }

    /**
     * Called when the "Stop" button clicked.
     */
    @FXML
    private void onStopClicked() {
        statusLabel.setText("Status: Stopped.");
        startButton.setDisable(false);
        stopButton.setDisable(true);
        intervalComboBox.setDisable(false);

        reminderService.stop();
    }
}
