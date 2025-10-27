package com.hydrobuddy.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.BeanProperty;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ReminderService.
 */
public class ReminderServiceTest {

    private ReminderService reminderService;

    // This method runs BEFORE each @Test
    @BeforeEach
    void setUp() {
        // We get a fresh instance for every test to ensure isolation
        reminderService = ReminderService.getInstance();
        // We must stop any lingering tasks from a previous test run
        reminderService.stop();
    }
}
