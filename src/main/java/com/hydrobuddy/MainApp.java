package com.hydrobuddy;

import com.hydrobuddy.gui.GuiApplication;
import javafx.application.Application;

public class MainApp {

    public static void main(String[] args) {
        if (args.length > 0 && "--cli".equals(args[0])) {
            // Launch in command-line mode
            System.out.println("Launching HydroBuddy in CLI mode...");
            // Create a CliRunner.run() later
            // CliRunner.run();
        } else {
            // Launch in GUI mode
            System.out.println("Launching HydroBuddy in GUI mode...");
            Application.launch(GuiApplication.class, args);
        }
    }
}
