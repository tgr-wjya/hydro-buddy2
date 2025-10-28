package com.hydrobuddy.notification;

/**
 * Abstraction for showing reminders/notifications.
 * Implementations should be safe to call from background threads.
 */
public interface NotificationProvider {
    /**
     * Show a reminder message to the user.
     *
     * @param message the message to display
     */
    void showReminder(String message);

    /**
     * Clean up any resources used by the provider (e.g. remove tray icon).
     * Safe to call multiple times.
     */
    void shutdown();
}