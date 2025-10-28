package com.hydrobuddy.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ConsoleNotificationProviderTest {

    @Test
    void showReminderDoesNotThrow() {
        var provider = ConsoleNotificationProvider.getInstance();
        assertDoesNotThrow(() -> provider.showReminder("Test message"));
        assertDoesNotThrow(provider::shutdown);
    }
}