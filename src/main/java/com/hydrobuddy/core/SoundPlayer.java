package com.hydrobuddy.core;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

/**
 * Handles loading and playing the notification sound.
 * This is designed to be a lightweight, single-purpose class.
 */
public class SoundPlayer {

    private MediaPlayer mediaPlayer;

    public SoundPlayer(String soundFilePath) {
        try {
            // 1. Get the sound file from our 'resources' dir
            URL resourceUrl = getClass().getResource(soundFilePath);
            if (resourceUrl == null) {
                System.err.println("Sound file not found: " + soundFilePath);
                return;
            }

            // 2. Create a Media object
            Media sound = new Media(resourceUrl.toExternalForm());

            // 3. Create a MediaPlayer to control the sound
            this.mediaPlayer = new MediaPlayer(sound);
        } catch (Exception e) {
            System.err.println("Error loading sound file with JavaFX Media:");
            e.printStackTrace();
        }
    }

    /**
     * Plays the loaded sound clip from the beginning.
     */
    public void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.play();
        } else {
            System.err.println("MediaPlayer is not loaded, cannot play.");
        }
    }
}
