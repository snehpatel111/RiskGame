package com.riskgame.controller;

import com.riskgame.model.*;
import com.riskgame.utility.Constant;
import com.riskgame.view.TournamentResult;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;

/**
 * Manages Tournament
 */
public class TournamentEngine extends GameEngine {

    /**
     * StartUpPhase instance is used to handle all the startup logic.
     */
    StartUpPhase d_startUpPhase;

    /**
     * GameEngine instance is used to handle all the game logic.
     */
    GameEngine d_gameEngine;

    /**
     * Creates a tournament engine object.
     * 
     * @param p_gameEngine is object of gameEngine
     * @param p_gameState  is object of gameState
     */
    public TournamentEngine(GameEngine p_gameEngine, GameState p_gameState) {
        super();
        this.d_gameEngine = p_gameEngine;
        this.d_startUpPhase = new StartUpPhase(p_gameEngine, p_gameState);
    }

    /**
     * Function responsible for handling command for tournament
     * 
     * @param p_player     Player playing the move
     * @param p_newCommand Command to be interpreted
     * @return success if command is valid else appropriate message which indicates
     *         reason of failure
     */
    public String parse(Player p_player, String p_newCommand) {
        String[] l_data = p_newCommand.split("\\s+");
        String l_commandName = l_data[0];
        // int l_noOfMaps;
        int l_noOfGames;
        int l_noOfTurns;
        ArrayList<String> l_maps = new ArrayList<String>();
        ArrayList<String> l_strategies = new ArrayList<String>();

        if (l_commandName.equals("tournament")) {
            try {
                if (l_data[1].equals("-M")) {
                    int i = 2;
                    while (!l_data[i].equals("-P")) {
                        if (i < l_data.length) {
                            if (isMapNameValid(l_data[i])) {
                                l_maps.add(l_data[i]);
                            } else {
                                printFailureMessage("lol -- : map name validation fail");
                                return "lol -- : map name validation fail";
                            }
                        } else {
                            printFailureMessage("lol -- : -M i !< length fail");
                            return "lol -- : -M i !< length fail";
                        }
                        i++;
                    }

                    if (l_maps.size() >= 1 && l_maps.size() <= 5 && allMapExists(l_maps)) {

                        if (l_data[i].equals("-P")) {

                            int l_indexNew = i + 1;

                            while (!l_data[l_indexNew].equals("-G")) {
                                if (l_indexNew < l_data.length) {
                                    if (isPlayerStrategyValid(l_data[l_indexNew])) {
                                        l_strategies.add(l_data[l_indexNew]);
                                    } else {
                                        printFailureMessage("lol -- : stretagy validation fail");
                                        return "lol -- : stretagy validation fail";
                                    }

                                } else {
                                    printFailureMessage("lol -- : -P i !< length fail");
                                    return "lol -- : -P i !< length fail";
                                }
                                l_indexNew++;
                            }

                            if (l_strategies.size() >= 2 && l_strategies.size() <= 4
                                    && isPlayerStrategyDistinct(l_strategies)) {
                                // System.out.println(strategies);

                                if (l_data[l_indexNew].equals("-G")) {
                                    if (l_indexNew + 1 < l_data.length) {
                                        if (l_data[l_indexNew + 1].matches("[1-5]")) {
                                            l_noOfGames = Integer.parseInt(l_data[l_indexNew + 1]);
                                            // System.out.println("Number of Games:" + noOfGames);
                                        } else {
                                            printFailureMessage("lol -- : no of game not match [1-5]");
                                            return "lol -- : no of game not match [1-5]";
                                        }
                                    } else {
                                        printFailureMessage("lol -- : -P l_indexnew+1 !< length fail");
                                        return "lol -- : -P l_indexnew+1 !< length fail";
                                    }

                                    int l_newIndex = l_indexNew + 2;

                                    if (l_data[l_newIndex].equals("-D")) {

                                        if (l_newIndex + 1 < l_data.length) {

                                            int l_n = Integer.parseInt(l_data[l_newIndex + 1]);

                                            if (l_n >= 10 && l_n <= 50) {
                                                l_noOfTurns = Integer.parseInt(l_data[l_newIndex + 1]);
                                                playTournament(l_maps, l_strategies, l_noOfGames, l_noOfTurns);
                                                return "success";
                                            } else {
                                                printFailureMessage("lol -- : turns not between 10 50");
                                                return "lol -- : turns not between 10 50";
                                            }
                                        } else {
                                            printFailureMessage("lol -- : -D l_indexnew+1 !< length fail");
                                            return "lol -- : -D l_indexnew+1 !< length fail";
                                        }
                                    } else {
                                        printFailureMessage("lol -- : -D condition fail");
                                        return "lol -- : -D condition fail";
                                    }
                                } else {
                                    printFailureMessage("lol -- : -G condition fail");
                                    return "lol -- : -G condition fail";
                                }
                            } else {
                                printFailureMessage("strategy not between 2 4 or not valid strategy");
                                return "strategy not between 2 4 or not valid strategy";
                            }
                        } else {
                            printFailureMessage("lol -- : -P condition fail");
                            return "lol -- : -P condition fail";
                        }
                    } else {
                        printFailureMessage("lol -- : map size not between 1-5 or all map not exist");
                        return "lol -- : map size between 1 5 or all map not exist";
                    }
                } else {
                    printFailureMessage("lol -- : -M condition fail");
                    return "lol -- : -M condition fail";
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                String message = "Command has to be in form of 'tournament -M listofmapfiles{1-5} -P listofplayerstrategies{2-4} -G numberofgames{1-5} -D maxnumberofturns{10-50}";
                return message;
            }
        } else {
            printFailureMessage("command name tournament keyword not matched");
            return "command name tournament keyword not matched";
        }

    }

    /**
     * Conducts the tournament as per the mentioned arguments.
     * 
     * @param p_mapFiles      List of map files to play on
     * @param p_strategies    List of player strategies
     * @param p_numberOfGames Number of games to play
     * @param p_numberOfTurns Number of turns to play
     */
    public void playTournament(ArrayList<String> p_mapFiles, ArrayList<String> p_strategies, int p_numberOfGames,
            int p_numberOfTurns) {
        int l_numberOfPlayers = p_strategies.size();
        int l_traversalCounter = 0;
        int l_gameNumber = 0;
        HashMap<Integer, String> l_winner = new HashMap<Integer, String>();
        // int l_playerSize = d_gameEngine.d_gameState.getPlayerList().size();
        // System.out.println("lol Player Size: " + l_playerSize);
        // while (l_playerSize != 0) {
        // d_gameEngine.d_gameState.getPlayerList().remove(0);
        // l_playerSize -= 1;
        // }

        // Start playing on each map
        for (String l_mapName : p_mapFiles) {
            System.out.println("Playing on :" + l_mapName);
            // Start playing each game
            // int P = d_gameEngine.d_gameState.getPlayerList().size();
            // while (P != 0) {
            // d_gameEngine.d_gameState.getPlayerList().remove(0);
            // P -= 1;
            // }
            for (int i = 1; i <= p_numberOfGames; i++) {
                l_gameNumber++;
                System.out.println("Playing Game :" + l_gameNumber);
                this.d_gameEngine = new GameEngine();
                GameData l_gameData = new GameData();
                MapHelper l_mapHelper = new MapHelper();
                Player l_player = new Player(null);
                this.d_startUpPhase = new StartUpPhase(this.d_gameEngine, this.d_gameEngine.getGameState());
                // load the map
                l_mapHelper.loadMap(this.d_gameEngine, this.d_gameEngine.getGameState(), l_mapName);

                // Flushing Players
                // Flushing Objects which are getting reused
                // int Psiz = d_gameEngine.d_gameState.getPlayerList().size();
                // while (Psiz != 0) {
                // d_gameEngine.d_gameState.getPlayerList().remove(0);
                // Psiz -= 1;
                // }

                // Create player objects
                for (String l_strategy : p_strategies) {
                    l_player.addPlayer(this.d_gameEngine, this.d_gameEngine.getGameState().getPlayerList(),
                            l_strategy, this.d_gameEngine.getGameState(), l_strategy);
                }
                // Setting strategies as same as Player Names
                for (Player l_p : this.d_gameEngine.getGameState().getPlayerList()) {
                    switch (l_p.getPlayerName().toLowerCase()) {
                        case "aggressive":
                            l_p.setStrategy(new AggressiveStrategy(l_p, this.d_gameEngine));
                            l_p.setIsHuman(false);
                            break;
                        case "benevolent":
                            l_p.setStrategy(new BenevolentStrategy(l_p, this.d_gameEngine));
                            l_p.setIsHuman(false);
                            break;
                        case "random":
                            l_p.setStrategy(new RandomStrategy(l_p, this.d_gameEngine));
                            l_p.setIsHuman(false);
                            break;
                        case "cheater":
                            l_p.setStrategy(new CheaterStrategy(l_p, this.d_gameEngine));
                            l_p.setIsHuman(false);
                            break;
                        default:
                            System.out.println(Constant.ERROR_COLOR + "Invalid Player Strategy" + Constant.RESET_COLOR);
                            System.out.println(Constant.ERROR_COLOR
                                    + "It should be any of the following: aggressive, benevolent, random or cheater"
                                    + Constant.RESET_COLOR);
                            break;
                    }
                }
                // System.out.println(d_gameEngine.d_gameState.getPlayerList().size());
                // d_startUpPhase.assignCountries(d_gameEngine, d_gameState,
                // d_gameEngine.d_gameState.getPlayerList());
                Player.assignCountries(this.d_gameEngine, this.d_gameEngine.getGameState());
                // for (Player p : d_gameEngine.d_gameState.getPlayerList()) {
                // System.out.println(p.getOwnedCountries());
                // }
                // AssignCountries and Reinforcements
                assignEachPlayerReinforcements(this.d_gameEngine.getGameState().getPlayerList());
                // for (Player p : d_gameEngine.d_gameState.getPlayerList()) {
                // System.out.println(p.getOwnedArmyCount());
                // }
                // tournament -M demo.map -P cheater random -G 1 -D 10
                // tournament -M dummy.map ameroki.map -P cheater aggressive -G 4 -D 30

                boolean anyOneWon = false;
                for (int j = 1; j <= p_numberOfTurns; j++) {
                    int l_counter = 0;
                    System.out.println("It's " + j + "'th Turn");

                    // traverses through all players to check if armies left in pool
                    Iterator<Player> l_itr = this.d_gameEngine.getGameState().getPlayerList().listIterator();
                    while (l_itr.hasNext()) {
                        Player l_p = l_itr.next();
                        if (l_p.getOwnedArmyCount() > 0) {
                            l_counter = l_counter + l_p.getOwnedArmyCount();
                        }
                    }

                    System.out.println("Total Armies left with all Players in Pool: " + l_counter);
                    // Case when pool has at least 1 army left

                    // Issued Orders
                    // Gets Orders until all pool is consumed for turn
                    while (l_counter > 0) {
                        // get Orders
                        for (Player p : this.d_gameEngine.getGameState().getPlayerList()) {
                            if (p.getOwnedCountries().size() != 0) {
                                p.issueOrder();
                            } else {
                                p.setOwnedArmyCount(0);
                                // }
                            }
                            l_counter = 0;
                            for (Player l_p : this.d_gameEngine.getGameState().getPlayerList()) {
                                if (l_p.getOwnedArmyCount() > 0) {
                                    l_counter = l_counter + l_p.getOwnedArmyCount();
                                }
                            }
                        }

                        System.out.println("Total Armies left with all Players in Pool: " + l_counter);

                        // Execute current Pool of Orders
                        int l_count = 0;
                        for (Player l_p : this.d_gameEngine.getGameState().getPlayerList()) {
                            // Get Valid Orders Queue
                            Queue<Order> l_temp = l_p.getExecutionOrderList();
                            l_count = l_count + l_temp.size();
                        }

                        if (l_count == 0) {
                            // Case when no Order
                            System.out.println("Orders already executed!");
                        } else {
                            // Execute Orders and check if Player won
                            System.out.println("Total Orders  :" + l_count);
                            // Execute Current Orders
                            boolean win = true;
                            while (l_count != 0 && win) {
                                if (this.d_gameEngine.getGameState().getPlayerList().size() == 1) {
                                    Order l_toRemove = this.d_gameEngine.getGameState().getPlayerList().get(0)
                                            .next_order();
                                    System.out.println("Order  :" + l_toRemove + " : for "
                                            + this.d_gameEngine.getGameState().getPlayerList().get(0));
                                    l_winner.put(l_gameNumber,
                                            this.d_gameEngine.getGameState().getPlayerList().get(0).getPlayerName());
                                    Queue<Order> l_temp = this.d_gameEngine.getGameState().getPlayerList().get(0)
                                            .getExecutionOrderList();
                                    if (l_temp.size() > 0) {
                                        Order tempO = this.d_gameEngine.getGameState().getPlayerList().get(0)
                                                .next_order();
                                        if (tempO != null) {
                                            System.out.println("Executing " + l_toRemove);
                                            l_toRemove.execute();
                                            l_count--;
                                        }
                                    }
                                    anyOneWon = true;
                                    l_count = 0;
                                    win = false;
                                    break;
                                } else {
                                    System.out.println("When More Players");
                                    for (Player l_p : this.d_gameEngine.getGameState().getPlayerList()) {
                                        Queue<Order> l_temp = l_p.getExecutionOrderList();
                                        System.out.println("Got Order :" + l_temp + " from " + l_p.getPlayerName());
                                        if (l_temp.size() > 0) {
                                            Order l_toRemove = l_p.next_order();
                                            if (l_toRemove != null) {
                                                System.out.println("Executing " + l_toRemove);
                                                l_toRemove.execute();
                                                l_count--;
                                            }
                                        }
                                        // if (l_p.getOwnedCountries().size() == 0) {
                                        // d_gameEngine.d_gameState.getPlayerList().remove(l_p);
                                        // }
                                    }
                                    // Iterator itr=d_gameEngine.d_gameState.getPlayerList().listIterator();
                                    // while(itr.hasNext())
                                    // {
                                    // Player l_p = (Player)itr.next();
                                    // System.out.println(l_p.getPlayerName());
                                    // Queue<Order> l_temp = l_p.getD_orderList();
                                    // System.out.println(l_temp);
                                    // if (l_temp.size() > 0) {
                                    // Order l_toRemove = l_p.next_order();
                                    // System.out.println(l_toRemove);
                                    // System.out.println("Got Order :"+l_temp+" from "+l_p.getPlayerName());
                                    // if(l_toRemove != null){
                                    // System.out.println(l_toRemove);
                                    // System.out.println(l_p);
                                    // l_toRemove.execute();
                                    // l_count--;
                                    // }
                                    // }
                                    //// if (l_p.getOwnedCountries().size() == 0) {
                                    //// System.out.println(l_p.getPlayerName()+": removed");
                                    //// itr.remove();
                                    //// }
                                    // if(d_gameEngine.d_gameState.getPlayerList().size() == 1){
                                    // System.out.println(l_p.getPlayerName() + " has Won the Game!");
                                    // d_LogEntry.setMessage(l_p.getPlayerName() + " has Won the Game!");
                                    // l_winner.put(l_gameNumber, l_p.getPlayerName());
                                    // System.out.println(l_winner.size());
                                    // anyOneWon = true;
                                    // break;
                                    // }
                                    // }
                                    // l_count--;
                                }
                            }
                            System.out.println("Total Armies left with all Players in Pool: " + l_counter);
                            // Flush Owned Cards
                            for (Player l_p : this.d_gameEngine.getGameState().getPlayerList()) {
                                System.out.println("Flushing Cards of " + l_p.getPlayerName());
                                l_p.flushNegotiators();
                            }
                        }
                        assignEachPlayerReinforcements(this.d_gameEngine.getGameState().getPlayerList());
                    }
                    // Check if any Player Won
                    // if(anyOneWon = false){
                    // for (Player l_p : d_gameEngine.d_gameState.getPlayerList()) {
                    // if (l_p.getOwnedCountries().size() == d_Map.getCountries().size()) {
                    // System.out.println(l_p.getPlayerName() + " has Won the Game!");
                    // d_LogEntry.setMessage(l_p.getPlayerName() + " has Won the Game!");
                    // l_winner.put(l_gameNumber, l_p.getPlayerName());
                    // anyOneWon = true;
                    // break;
                    // }
                    // }
                    // }else{
                    // System.out.println("Current Game resulted in a Draw");
                    // l_winner.put(l_gameNumber, "Draw");
                    // }
                    for (Player l_p : this.d_gameEngine.getGameState().getPlayerList()) {
                        if (l_p.getOwnedCountries().size() == l_mapHelper.getCountries().values().size()) {
                            System.out.println(l_p.getPlayerName() + " has Won the Game!");
                            this.d_gameEngine.setGameEngineLog(l_p.getPlayerName() + " has Won the Game!", "effect");
                            // d_LogEntry.setMessage(l_p.getPlayerName() + " has Won the Game!");
                            l_winner.put(l_gameNumber, l_p.getPlayerName());
                            break;
                        } else {
                            System.out.println("Current Game resulted in a Draw");
                            l_winner.put(l_gameNumber, "Draw");
                            break;
                        }
                    }

                    // Flushing Objects which are getting reused
                    int Psize = this.d_gameEngine.getGameState().getPlayerList().size();
                    while (Psize != 0) {
                        this.d_gameEngine.getGameState().getPlayerList().remove(0);
                        Psize -= 1;
                    }
                    System.out.println(
                            "Players left after game " + this.d_gameEngine.getGameState().getPlayerList().size());
                }
            }
            // return winner;
            TournamentResult tournamentResultView = new TournamentResult();
            tournamentResultView.showTournamentWinner(l_winner, p_mapFiles);
        }
    }

    /**
     * Ensures that all maps exist
     * 
     * @param p_list list of name of maps
     * @return true if valid else false
     */
    public boolean allMapExists(ArrayList<String> p_list) {
        int l_counter = 0;
        for (String s : p_list) {
            if (isMapExists(s)) {
                l_counter++;
            }
        }
        if (l_counter == p_list.size()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the map with argument name exists or not.
     * If it exists, it also checks if its valid or not.
     * 
     * @param p_mapName Name of the map file to be checked.
     * @return true if file exists and is a valid file, otherwise false.
     */
    public boolean isMapExists(String p_mapName) {
        String l_filePath = Constant.MAP_PATH + p_mapName;
        File f = new File(l_filePath);
        if (f.exists()) {
            MapHelper l_mapHelper = new MapHelper();
            l_mapHelper.loadMap(this.d_gameEngine, this.d_gameEngine.getGameState(), p_mapName);
            ;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Prints failure message
     * 
     * @param p_msg message to be printed
     */
    public void printFailureMessage(String p_msg) {
        String message = "Command has to be in form of 'tournament -M listofmapfiles{1-5} -P listofplayerstrategies{2-4} -G numberofgames{1-5} -D maxnumberofturns{10-50}";
        System.out.println(p_msg);
        this.d_gameEngine.setGameEngineLog(
                "Command has to be in form of 'tournament -M listofmapfiles{1-5} -P listofplayerstrategies{2-4} -G numberofgames{1-5} -D maxnumberofturns{10-50}",
                "effect");

    }

    /**
     * Ensures map name is valid.
     *
     * @param p_s input string
     * @return true if valid match, else false
     */
    public boolean isMapNameValid(String p_s) {
        return p_s != null && p_s.matches("^[a-zA-Z.]*$");
    }

    /**
     * Ensures player strategy is valid
     * 
     * @param p_s strategy of the player
     * @return true if valid else false
     */
    public boolean isPlayerStrategyValid(String p_s) {

        String[] l_array = new String[] { "aggressive", "benevolent", "random", "cheater" };
        for (int i = 0; i < 4; i++) {
            if (p_s.equals(l_array[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ensure that all strategies of player are distinct
     * 
     * @param p_list list of player's strategy
     * @return true if valid else false
     */
    public boolean isPlayerStrategyDistinct(ArrayList<String> p_list) {

        for (int i = 0; i < p_list.size(); i++) {
            for (int j = i + 1; j < p_list.size(); j++) {
                if (p_list.get(i).equals(p_list.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * assigns default reinforcements to each player based on Countries owned.
     * 
     * @param p_Players GameEngine ref from main to get track of players
     */
    public void assignEachPlayerReinforcements(ArrayList<Player> p_Players) {
        // for (Player l_p : p_Players) {
        // AssignReinforcement.assignReinforcementArmies(l_p);
        this.d_startUpPhase.assignReinforcementToPlayer(this.d_gameEngine.getGameState());
        // }
    }
}