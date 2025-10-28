package com.hydrobuddy.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ReminderServiceTest {

    private final ReminderService service = ReminderService.getInstance();

    @AfterEach
    void tearDown() {
        try {
            service.stop();
        } catch (Exception ignored) { }
    }

    @Test
    void startRunsTaskOnScheduleAndStopCancels() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(2);

        Runnable task = () -> {
            counter.incrementAndGet();
            latch.countDown();
        };

        service.start(task, 1, TimeUnit.SECONDS);

        boolean completed = latch.await(3, TimeUnit.SECONDS);
        assertTrue(completed, "Expected at least two task executions within timeout");
        assertTrue(service.isRunning(), "Service should still be running after executions");

        service.stop();
        assertFalse(service.isRunning(), "Service should not be running after stop()");
    }

    @Test
    void stopWhenNotRunningDoesNotThrow() {
        service.stop();
        service.stop();
        assertFalse(service.isRunning());
    }
}