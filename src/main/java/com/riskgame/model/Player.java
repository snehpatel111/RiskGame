package com.riskgame.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;

import com.riskgame.utility.Phase;
import com.riskgame.utility.Constant;
import com.riskgame.model.ExecuteOrder;

/**
 * Represents a player in the game.
 */
public class Player {
    private String d_playerName;
    private HashMap<String, Continent> d_ownedContinents;
    private HashMap<String, Country> d_ownedCountries;
    private int d_ownedArmyCount;
    private int d_reinforcementArmyCount;
    private String d_countryId;
    private ExecuteOrder d_executeOrder;
    private Queue<ExecuteOrder> d_executionOrderList;

    /**
     * This constructor assigns name to the player.
     *
     * @param p_playerName name of the player
     */
    public Player(String p_playerName) {
        this.d_playerName = p_playerName;
        this.d_ownedContinents = new HashMap<>();
        this.d_ownedCountries = new HashMap<>();
        this.d_ownedArmyCount = 0;
        this.d_executionOrderList = new ArrayDeque<>();
    }

    public String getPlayerName() {
        return this.d_playerName;
    }

    /**
     * Validates player name.
     * 
     * @param p_playerName Player name to validate
     * @param p_gamPhase   Current game phase
     * @return Return true if name is valid, false otherwise
     */
    public boolean isValidPlayerName(String p_playerName) {
        return p_playerName != null && p_playerName.matches("[a-zA-Z0-9]+");
    }

    /**
     * Manage player related operations based on input arguments.
     * 
     * @param p_playerList List of all players
     * @param p_gamPhase   Current game phase
     * @param p_args       Command line arguments
     */
    public void managePlayer(ArrayList<Player> p_playerList, Phase p_gamPhase, String[] p_args) {
        try {
            String l_playerName = p_args[2];
            if (p_args[1].equals("-add")) {
                if (this.isValidPlayerName(l_playerName)) {
                    boolean l_isPlayerAdded = this.addPlayer(p_playerList, l_playerName);
                    if (l_isPlayerAdded) {
                        System.out
                                .println(Constant.SUCCESS_COLOR + "Player added successfully!" + Constant.RESET_COLOR);
                        p_gamPhase = Phase.STARTUP;
                    }
                    p_gamPhase = Phase.STARTUP;
                } else {
                    System.out.println(Constant.ERROR_COLOR + "Invalid player name" + Constant.RESET_COLOR);
                }
            } else if (p_args[1].equals("-remove")) {
                if (this.isValidPlayerName(l_playerName)) {
                    boolean l_isPlayerRemoved = this.removePlayer(p_playerList, l_playerName);
                    if (l_isPlayerRemoved)
                        System.out.println(
                                Constant.SUCCESS_COLOR + "Player removed successfully!" + Constant.RESET_COLOR);
                    else
                        System.out.println(Constant.ERROR_COLOR + "Player doesn't exist" + Constant.RESET_COLOR);
                    p_gamPhase = Phase.STARTUP;
                } else
                    System.out.println(Constant.ERROR_COLOR + "Invalid player name" + Constant.RESET_COLOR);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command - it should be of the form gameplayer -add playername or gameplayer -remove playername"
                    + Constant.RESET_COLOR);
        } catch (Exception e) {
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command - it should be of the form gameplayer -add playername or gameplayer -remove playername"
                    + Constant.RESET_COLOR);
        }
    }

    /**
     * Adds a player to the game
     * 
     * @param p_playerList List of existing players
     * @param p_playerName Name of player to add
     * @return Return true if player added successfully, false otherwise
     */
    public boolean addPlayer(ArrayList<Player> p_playerList, String p_playerName) {
        if (p_playerList.size() == 6) {
            System.out.println(Constant.ERROR_COLOR + "Max number of players (6) reached!" + Constant.RESET_COLOR);
            return false;
        }
        if (this.isPlayerExist(p_playerList, p_playerName)) {
            System.out.println(Constant.ERROR_COLOR + "Player already exists!" + Constant.RESET_COLOR);
            return false;
        }
        p_playerList.add(new Player(p_playerName));
        return true;
    }

    /**
     * Removes a player from the game.
     * 
     * @param p_playerList List of existing players
     * @param p_playerName Name of player to remove
     * @return Returns true if player removed successfully, false otherwise
     */
    public boolean removePlayer(ArrayList<Player> p_playerList, String p_playerName) {
        Iterator<Player> l_iterator = p_playerList.listIterator();
        while (l_iterator.hasNext()) {
            Player l_player = l_iterator.next();
            if (l_player.getPlayerName().equals(p_playerName)) {
                p_playerList.remove(l_player);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a player exists in the given player list
     * 
     * @param p_playerList List of existing players
     * @param p_playerName Name of player to check
     * @return Returns true if player exists, false otherwise
     */
    public boolean isPlayerExist(ArrayList<Player> p_playerList, String p_playerName) {
        for (Player player : p_playerList) {
            if (player.getPlayerName().equals(p_playerName)) {
                return true;
            }
        }
        return false;
    }
}
