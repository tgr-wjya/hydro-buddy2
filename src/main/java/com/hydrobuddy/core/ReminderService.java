package com.hydrobuddy.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Manages the reminder scheduling using a background thread.
 * This class follows the Singleton pattern to ensure only one
 * timer service exists in the application.
 */

public class ReminderService {

    // 1. The Singleton instance
    private static final ReminderService INSTANCE = new ReminderService();

    // 2. The core Java scheduler
    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> reminderTaskHandle; // This lets us cancel the task

    /**
     * Private constructor to prevent anyone else from creating one.
     * This is part of the Singleton pattern.
     */
    private ReminderService() {
        // Creates a thread pool with one background thread
        // We add a "ThreadFactory" to make this thread a DAEMON thread.
        // This is the key to fix the Zombie thread.
        this.scheduler = Executors.newScheduledThreadPool(1, (Runnable r) -> {
            Thread t = new Thread(r);
            t.setDaemon(true); // Allow the JVM to exit even if this thread is running.
            return t;
        });
    }

    /**
     * The public method to get the one and only instance.
     */
    public static ReminderService getInstance() {
        return INSTANCE;
    }

    /**
     * Starts the reminder task.
     *
     * @param task  The code to run (passed in from the controller)
     * @param interval  The time, in minutes, between reminders
     */
    public void start(Runnable task, long interval) {
        if (isRunning()) {
            System.out.println("Service is already running. Stopping first.");
            stop();
        }

        System.out.println("Starting service. Interval: " + interval + " minutes.");
        // This is the core timer logic:
        // It runs the 'task' repeatedly with a 'interval' delay
        this.reminderTaskHandle = scheduler.scheduleAtFixedRate(
                task, // The task to run
                0, // Initial delat (start immediately)
                interval, // Time between runs
                TimeUnit.MINUTES // Unit of time
        );
    }

    /**
     * Stops the currently running reminder task.
     */
    public void stop() {
        if (isRunning()) {
            System.out.println("Stopping service.");
            // true = interrupt the task if it's currently running.
            this.reminderTaskHandle.cancel(true);
            this.reminderTaskHandle = null;
        } else {
            System.out.println("Service is not running.");
        }
    }

    /**
     * Check if the service is currently on scheduled.
     */
    public boolean isRunning() {
        return reminderTaskHandle != null && !reminderTaskHandle.isCancelled();
    }

    /**
     * Shuts down the background thread.
     * This is crucial for a clean application exit.
     */
    public void shutdown() {
        System.out.println("Shutting down scheduler.");
        this.scheduler.shutdown();
    }
}
