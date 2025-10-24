package com.hydrobuddy.gui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller for the MainView.xml file.
 * This class handles all UI events and logic for the main window.
 */

public class MainViewController {

    // These @FXML are automatically inhected by JavaFX.
    // The variable name MUST match the fx:id in the FXML file.

    @FXML
    private Label intervalLabel;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Label statusLabel;

    /**
     * This methid is automatically called by JavaFX after the FXML
     * file has been loaded and all @FXML fields are injected.
     * This is the perfect place to set up event handlers.
     */
    @FXML
    private void initialize() {
        // Add click handlers for our buttons
        startButton.setOnAction(event -> onStartClicked());
        stopButton.setOnAction(event -> onStopClicked());

        // Set initial UI state
        stopButton.setDisable(true); // Can't stop if not even running
        statusLabel.setText("Status: Ready");

        System.out.println("MainViewController initialized.");
    }

    /**
     * Called when the "Start" button is clicked.
     */
    private void onStartClicked() {
        System.out.println("Start button clicked!");
        statusLabel.setText("Status: Running...");

        // Update button states
        startButton.setDisable(true);
        stopButton.setDisable(true);

        // We will start the real reminder service here later
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

        // We will stop the real reminder service here later
    }
}
