package com.riskgame.controller;

import main.java.com.riskgame.model.Phase;
import main.java.com.riskgame.utility.Constant;

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
            System.out.println("Welcome to RiskGame!\n");
            System.out.println("You can start by creating/editing existing map or loading existing map.\n");
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
}
