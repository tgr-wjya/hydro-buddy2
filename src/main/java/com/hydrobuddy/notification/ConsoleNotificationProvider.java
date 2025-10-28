package com.hydrobuddy.notification;

import com.hydrobuddy.core.SoundPlayer;

/**
 * Simple console fallback provider for environments without SystemTray.
 */
public final class ConsoleNotificationProvider implements NotificationProvider {

    private static final ConsoleNotificationProvider INSTANCE = new ConsoleNotificationProvider();
    private final SoundPlayer soundPlayer;

    private ConsoleNotificationProvider() {
        this.soundPlayer = new SoundPlayer("/sounds/MGS_Alert.mp3");
    }

    public static ConsoleNotificationProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public void showReminder(String message) {
        if (soundPlayer != null) {
            soundPlayer.playSound();
        }
        System.out.println("[HydroBuddy Reminder] " + message);
    }

    @Override
    public void shutdown() {
        // no-op
    }
}
