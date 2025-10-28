package com.hydrobuddy.notification;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.hydrobuddy.core.SoundPlayer;

/**
 * Desktop implementation using AWT SystemTray + TrayIcon.
 * Falls back to console output when SystemTray is not available.
 */
public final class DesktopNotificationProvider implements NotificationProvider {

    private static final DesktopNotificationProvider INSTANCE = new DesktopNotificationProvider();

    private final boolean supported;
    private final TrayIcon trayIcon;
    private final SoundPlayer soundPlayer;

    private DesktopNotificationProvider() {
        TrayIcon temp = null;
        boolean isSupported = false;

        if (SystemTray.isSupported()) {
            try {
                SystemTray tray = SystemTray.getSystemTray();
                Image image = loadIcon("/images/logo.png");
                if (image == null) {
                    image = Toolkit.getDefaultToolkit().createImage(new byte[0]);
                } else {
                    image = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                }

                temp = new TrayIcon(image, "HydroBuddy");
                temp.setImageAutoSize(true);

                PopupMenu popup = new PopupMenu();
                MenuItem exit = new MenuItem("Exit HydroBuddy");
                exit.addActionListener(e -> {
                    shutdown();
                    try {
                        System.exit(0);
                    } catch (SecurityException ignored) {
                    }
                });
                popup.add(exit);
                temp.setPopupMenu(popup);

                tray.add(temp);
                isSupported = true;
            } catch (AWTException | RuntimeException ex) {
                System.err.println("Failed to initialize SystemTray: " + ex.getMessage());
                temp = null;
                isSupported = false;
            }
        }

        this.trayIcon = temp;
        this.supported = isSupported;
        this.soundPlayer = new SoundPlayer("/sounds/MGS_Alert.mp3");

        if (supported) {
            System.out.println("DesktopNotificationProvider: system tray initialized.");
        } else {
            System.out.println("DesktopNotificationProvider: system tray NOT supported, falling back to console.");
        }
    }

    public static DesktopNotificationProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public void showReminder(String message) {
        // Play sound first
        if (soundPlayer != null) {
            soundPlayer.playSound();
        }

        // Then show the notification
        if (supported && trayIcon != null) {
            try {
                trayIcon.displayMessage("HydroBuddy", message, MessageType.INFO);
            } catch (Exception ex) {
                System.err.println("Failed to show tray notification: " + ex.getMessage());
                System.out.println("[REMINDER] " + message);
            }
        } else {
            System.out.println("[REMINDER] " + message);
        }
    }

    @Override
    public void shutdown() {
        if (supported && trayIcon != null) {
            try {
                SystemTray.getSystemTray().remove(trayIcon);
            } catch (Exception ignored) {
            }
        }
    }

    private Image loadIcon(String resourcePath) {
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null)
                return null;
            return ImageIO.read(in);
        } catch (IOException e) {
            return null;
        }
    }
}
