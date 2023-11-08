package com.riskgame.model;

import com.riskgame.model.Country;
import com.riskgame.model.Player;

public class Deploy implements Order {

    private boolean status = false;
    private int d_armyCount;
    private String d_countryId;
    private Player d_player;
    public GameState d_gameState;

    /**
     * Initializes the player's deploy order with deploy details
     * 
     * @param p_player    Player whose order is being executed
     * @param p_countryId Country id where armies are being deployed
     * @param p_armyCount Number of armies being deployed
     */
    public Deploy(Player p_player, String p_countryId, int p_armyCount) {
        this.d_player = p_player;
        this.d_countryId = p_countryId;
        this.d_armyCount = p_armyCount;
        this.d_gameState = new GameState();
    }

    /**
     * Set game state.
     */
    public void setGameState(GameState p_gameState) {
        this.d_gameState = p_gameState;
    }

    /**
     * This method execute the order
     * 
     * @param p_gameState The current game state.
     * @return Returns true if order is executed successfully, otherwise false
     */
    @Override
    public boolean execute() {
        // if(this.status){
        // return true;
        // }
        // this.status = true;
        System.out.println("-----------diploy Order Execution inside---------");
        Country l_country = this.d_player.getOwnedCountries().get(this.d_countryId.toLowerCase());
        int l_existingArmies = l_country.getNumberOfArmies();
        l_existingArmies += d_armyCount;
        l_country.setNumberOfArmies(l_existingArmies);
        return true;
    }
    

    /**
     * Getter method to get player
     * 
     * @return Returns player who has given order
     */
    public Player getPlayer() {
        return this.d_player;
    }

    /**
     * Setter method to set player for order
     * 
     * @param p_player Player to set as a executor of order
     */
    public void setPlayer(Player p_player) {
        this.d_player = p_player;
    }

    /**
     * Getter method for country
     * 
     * @return Returns name of country
     */
    public String getCountryId() {
        return this.d_countryId;
    }

    /**
     * Setter method to set country name
     * 
     * @param p_countryId Country name to set
     */
    public void setCountryId(String p_countryId) {
        this.d_countryId = p_countryId;
    }

    /**
     * Getter method to get army count
     * 
     * @return d_armyCount
     */
    public int getNumArmies() {
        return d_armyCount;
    }

    /**
     * Setter method to set army count
     * 
     * @param p_armyCount number of armies
     */
    public void setNumArmies(int p_armyCount) {
        this.d_armyCount = p_armyCount;
    }

}
