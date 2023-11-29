package com.riskgame.view;

import java.util.Scanner;

import com.riskgame.controller.GameEngine;
import com.riskgame.controller.TournamentEngine;
import com.riskgame.model.GameState;
import com.riskgame.utility.Constant;

public class InitializeGame {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            GameEngine l_gameEngine = new GameEngine();
            String l_cmd;
            String message = "";

            System.out.println("\n=================== Welcome To RiskGame! ======================\n");
            while (true) {
                System.out.println(
                        "Enter S to play single-game Mode OR T to play tournament-game Mode OR Q to quit/exit the game");
                l_cmd = sc.nextLine();
                switch (l_cmd.toLowerCase()) {
                    case "s":
                        l_gameEngine.getCurrentGamePhase().getGameState().updateLog(
                                "Initializing the Game ......" + System.lineSeparator(),
                                "start");
                        l_gameEngine.setGameEngineLog("Game Startup Phase", "phase");
                        l_gameEngine.getCurrentGamePhase().initPhase();

                        break;
                    case "t":
                        TournamentEngine l_tournamentEngine = new TournamentEngine(l_gameEngine,
                                l_gameEngine.getGameState());
                        do {
                            System.out.println(
                                    "Command has to be in form of 'tournament -M listofmapfiles{1-5} -P listofplayerstrategies{2-4}"
                                            + ""
                                            + "-G numberofgames{1-5} -D maxnumberofturns{10-50}");
                            l_cmd = sc.nextLine();
                            message = l_tournamentEngine.parse(null, l_cmd);
                        } while (!message.equals("success"));

                        break;

                    case "q":
                        System.exit(0);
                        break;

                    default:
                        System.out.println(Constant.ERROR_COLOR + "Invalid Command!\nEnter S to play" + "" +
                                "single-game Mode OR T to play tournament-game Mode" + Constant.RESET_COLOR);
                        break;
                }
            }
        }

    }
}
