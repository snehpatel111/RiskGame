package com.riskgame.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;


/**
 * Represents the state of the game.
 */
public class GameState {

    /**
     * The game map.
     */
    private GameMap d_gameMap;

    /**
     * Buffer for log entries.
     */
    private LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

    /**
     * List of players in the game.
     */
    private ArrayList<Player> d_playerList = new ArrayList<>();

    /**
     * List of unexecuted orders.
     */
    private Queue<Order> d_unexecutedOrderList = new ArrayDeque<>();

    /**
     * Error message.
     */
    private String d_error;

    /**
     * Indicates whether the game map is loaded.
     */
    private boolean d_isGameMapLoaded = false;

    /**
     * Gets the game map.
     *
     * @return The game map.
     */
    public GameMap getGameMap() {
        return this.d_gameMap;
    }

    /**
     * Sets the game map.
     *
     * @param p_gameMap The game map.
     */
    public void setGameMap(GameMap p_gameMap) {
        this.d_gameMap = p_gameMap;
    }

    /**
     * Gets the list of players.
     *
     * @return List of players.
     */
    public ArrayList<Player> getPlayerList() {
        return this.d_playerList;
    }

    /**
     * Sets the list of players.
     *
     * @param players List of players.
     */
    public void setPlayers(ArrayList<Player> p_playerList) {
        this.d_playerList = p_playerList;
    }

    /**
     * Gets the list of unexecuted orders.
     *
     * @return List of unexecuted orders.
     */
    public Queue<Order> getUnexecutedOrders() {
        return this.d_unexecutedOrderList;
    }

    /**
     * Sets the list of unexecuted orders.
     *
     * @param p_unexecutedOrderList List of unexecuted orders.
     */
    public void setUnexecutedOrders(Queue<Order> p_unexecutedOrderList) {
        this.d_unexecutedOrderList = p_unexecutedOrderList;
    }

    /**
     * Gets the error message.
     *
     * @return Error message.
     */
    public String getError() {
        return this.d_error;
    }

    /**
     * Sets the error message.
     *
     * @param p_error Error message.
     */
    public void setError(String p_error) {
        this.d_error = p_error;
    }

    /**
     * Updates the game log with a new log entry.
     *
     * @param p_logMessage The log message to be added.
     * @param p_logType    The type of log message.
     */
    public void updateLog(String p_logMessage, String p_logType) {
        this.d_logEntryBuffer.currentLog(p_logMessage, p_logType);
    }

    // /**
    //  * Gets the most recent log entry in the current game state.
    //  *
    //  * @return The recent log message.
    //  */
    // public String getRecentLog() {
    //     return this.d_logEntryBuffer.getLogMessage();
    // }

    /**
     * Sets the game map loaded flag.
     */
    public void setIsGameMapLoaded() {
        this.d_isGameMapLoaded = true;
    }

    /**
     * Checks if the game map is loaded or not.
     *
     * @return True if the game map is loaded, false otherwise.
     */
    public boolean isGameMapLoaded() {
        return this.d_isGameMapLoaded;
    }

    public int getTotalArmyOfAllPlayers() {
        int total = 0;
        for(Player player: this.d_playerList){
            total += player.getOwnedArmyCount();
        }
        return total;
    }
}
