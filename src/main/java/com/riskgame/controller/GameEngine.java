package com.riskgame.controller;

import main.java.com.riskgame.model.Phase;
import main.java.com.riskgame.utility.Constant;

import java.io.File;
import java.util.*;

import com.riskgame.model.StartUpPhase;

/**
 * This is the entry point of the game. It initializes the game state and keeps
 * track of current game state.
 *
 */
public class GameEngine {
    public static void main(String[] p_args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("\nWelcome to RiskGame!\n");
            System.out.println("You can start by creating/editing existing map or loading existing map.\n");
            showAvailableMap();
            System.out.println(
                    "To create/edit map, use " + Constant.SUCCESS_COLOR + "editmap <map_name>" + Constant.RESET_COLOR
                            + " command. e.x. " + Constant.SUCCESS_COLOR + "editmap sample.map" + Constant.RESET_COLOR);
            System.out.println("To load existing map, use " + Constant.SUCCESS_COLOR + "loadmap <map_name>"
                    + Constant.RESET_COLOR + " command. e.x. " + Constant.SUCCESS_COLOR + "loadmap sample.map"
                    + Constant.RESET_COLOR + "\n");
            String l_command = sc.nextLine();
            StartUpPhase startupPhase = new StartUpPhase();
            Phase l_gamePhase = startupPhase.parseCommand(null, l_command);

            // Get commands until initial phase ends.
            while (l_gamePhase != Phase.ISSUE_ORDERS) {
                l_command = sc.nextLine();
                l_gamePhase = startupPhase.parseCommand(null, l_command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lists the names of files in the specified folder.
     *
     */
    public static void showAvailableMap() {
        File folder = new File(Constant.MAP_PATH);

        // Check if the specified path is a directory
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            // Check if there are any files in the directory
            if (files != null) {
                // Find the maximum length of file names for formatting
                int maxLength = 0;
                for (File file : files) {
                    if (file.isFile()) {
                        int length = file.getName().length();
                        if (length > maxLength) {
                            maxLength = length;
                        }
                    }
                }

                System.out.printf("%85s\n",
                        "-------------------------------------------------------------------------------------------");
                System.out.printf("%55s\n", "Existing Maps");

                System.out.printf("%85s\n",
                        "-------------------------------------------------------------------------------------------");

                // Print file names with even distribution
                for (File file : files) {
                    // Check if it's a file (not a subdirectory)
                    if (file.isFile()) {
                        String fileName = file.getName();
                        System.out.printf("%-" + (maxLength + 2) + "s", fileName);
                    }
                }
                System.out.println("\n"); // Move to the next line after printing
            } else {
                System.out.println("No files found in the directory.");
            }
        } else {
            System.out.println("Specified path is not a directory.");
        }
    }
}
