package com.riskgame.model;

import com.riskgame.model.Player;

/**
 * Executes the player order
 */
public class ExecuteOrder {
    private int d_armyCount;
    private String d_countryId;
    private Player d_player;

    /**
     * Initializes the player order with deploy details
     * 
     * @param p_player    Player whose order is being executed
     * @param p_countryId Country id where armies are being deployed
     * @param p_armyCount Number of armies being deployed
     */
    public ExecuteOrder(Player p_player, String p_countryId, int p_armyCount) {
        this.d_player = p_player;
        this.d_countryId = p_countryId;
        this.d_armyCount = p_armyCount;
    }
}
