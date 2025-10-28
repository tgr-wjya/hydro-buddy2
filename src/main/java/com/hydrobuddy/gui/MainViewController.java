package com.hydrobuddy.gui;

import javafx.fxml.FXML;
import com.hydrobuddy.core.ReminderService;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import com.hydrobuddy.notification.DesktopNotificationProvider;
import com.hydrobuddy.notification.NotificationProvider;

/**
 * Controller for the MainView.fxml file.
 * Handles UI events and logic for the main window.
 */
public class MainViewController {

    @FXML
    private ComboBox<Integer> intervalComboBox;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Label statusLabel;

    private final ReminderService reminderService = ReminderService.getInstance();
    private final NotificationProvider notifier = DesktopNotificationProvider.getInstance();

    @FXML
    private void initialize() {
        startButton.setOnAction(event -> onStartClicked());
        stopButton.setOnAction(event -> onStopClicked());

        intervalComboBox.getItems().addAll(1, 15, 30, 45, 60);
        intervalComboBox.setValue(30); // default

        stopButton.setDisable(true);
        statusLabel.setText("Status: Ready");
    }

    private void onStartClicked() {
        statusLabel.setText("Status: Running...");
        startButton.setDisable(true);
        stopButton.setDisable(false);
        intervalComboBox.setDisable(true);

        Runnable reminderTask = () -> notifier.showReminder("Time to drink water!");
        long interval = intervalComboBox.getValue();
        reminderService.start(reminderTask, interval);
    }

    @FXML
    private void onStopClicked() {
        statusLabel.setText("Status: Stopped.");
        startButton.setDisable(false);  // re-enable
        stopButton.setDisable(true);
        intervalComboBox.setDisable(false);

        reminderService.stop();
    }
}