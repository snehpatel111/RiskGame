package com.riskgame.model;

import com.riskgame.model.Player;

/**
 * Executes the player order
 */
public class Order {
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
    public Order(Player p_player, String p_countryId, int p_armyCount) {
        this.d_player = p_player;
        this.d_countryId = p_countryId;
        this.d_armyCount = p_armyCount;
    }

    /**
     * method which enacts the order
     * @return true if successful, else false
     */
    public boolean execute(){
        Country l_c= d_player.getOwnedCountries().get(d_countryId.toLowerCase());
        int l_existingArmies = l_c.getNumberOfArmies();
        l_existingArmies += d_armyCount;
        l_c.setNumberOfArmies(l_existingArmies);
        return true;
    }

    /**
     * Getter for current player
     * @return d_player
     */
    public Player getD_player() {
        return d_player;
    }

    /**
     * Setter for current player
     * @param d_player player
     */
    public void setD_player(Player p_player) {
        this.d_player = p_player;
    }

    /**
     * Getter for ID of Country
     * @return d_countryId
     */
    public String getD_countryId() {
        return d_countryId;
    }

    /**
     * Setter for ID of Country
     * @param d_countryId country ID
     */
    public void setD_countryId(String p_countryId) {
        this.d_countryId = p_countryId;
    }
}
