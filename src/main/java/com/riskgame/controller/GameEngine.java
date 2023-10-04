package com.riskgame.controller;

import java.io.File;
import java.util.Scanner;

import java.util.Iterator;

import com.riskgame.model.StartUpPhase;
import com.riskgame.utility.Phase;

import com.riskgame.model.Continent;
import com.riskgame.model.Player;
import com.riskgame.utility.Constant;

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
            StartUpPhase l_startupPhase = new StartUpPhase();
            Phase l_gamePhase = l_startupPhase.parseCommand(null, l_command);

            // Get commands until initial phase ends.
            while (!l_gamePhase.equals(Phase.ISSUE_ORDERS)) {
                l_command = sc.nextLine();
                l_gamePhase = l_startupPhase.parseCommand(null, l_command);
            }
            assignReinforcementToPlayer(l_startupPhase);

            int l_playerCounter = 0;
            int l_totalPlayer = l_startupPhase.getPlayerList().size();
            while (true) {
                while (l_playerCounter < l_totalPlayer) {
                    Player l_player = l_startupPhase.getPlayerList().get(l_playerCounter);
                    System.out.println(
                            l_player.getPlayerName() + "'s turn (Remaining army count: " + l_player.getOwnedArmyCount() + ")");
                    l_gamePhase = Phase.ISSUE_ORDERS;
                    l_startupPhase.setGamePhase(l_gamePhase);
                    while (!l_gamePhase.equals(Phase.SWITCH_TURN)) {
                        l_command = sc.nextLine();
                        l_gamePhase = l_startupPhase.parseCommand(l_player, l_command);
                    }
                    l_playerCounter++;
                }
                l_gamePhase = Phase.ISSUE_ORDERS;
                l_startupPhase.setGamePhase(l_gamePhase);
                l_playerCounter = 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Assign reinforcement to each game player
     * 
     * @param p_startUpPhase Game phase that contains player list
     */
    public static void assignReinforcementToPlayer(StartUpPhase p_startUpPhase) {
        Iterator<Player> l_iterator = p_startUpPhase.getPlayerList().listIterator();

        while (l_iterator.hasNext()) {
            Player l_player = l_iterator.next();
            int l_totalControlValueCount = 0;
            int l_totalReinforcementArmyCount;

            if (l_player.getOwnedCountries().size() >= 10) {
                if (l_player.getOwnedContinents().size() > 0) {
                    for (Continent l_continent : l_player.getOwnedContinents().values()) {
                        l_totalControlValueCount += l_continent.getControlValue();
                    }
                    l_totalReinforcementArmyCount = (int) (l_player.getOwnedCountries().size() / 2)
                            + l_totalControlValueCount;
                } else {
                    l_totalReinforcementArmyCount = (int) (l_player.getOwnedCountries().size() / 2);
                }
            } else {
                l_totalReinforcementArmyCount = 5;
            }
            l_player.setOwnedArmyCount(l_totalReinforcementArmyCount);
        }
    }

    /**
     * Lists the names of files in the specified folder.
     *
     */
    public static void showAvailableMap() {
        File folder = new File(Constant.MAP_PATH);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

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
                    if (file.isFile()) {
                        String fileName = file.getName();
                        System.out.printf("%-" + (maxLength + 2) + "s", fileName);
                    }
                }
                System.out.println("\n");
            } else {
                System.out.println("No files found in the directory.");
            }
        } else {
            System.out.println("Specified path is not a directory.");
        }
    }
}
