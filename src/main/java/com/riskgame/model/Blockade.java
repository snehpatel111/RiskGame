package com.riskgame.model;

import com.riskgame.model.Country;
import com.riskgame.model.Player;

/**
 * Class containing logic for implementation of Blockade order
 *
 */
public class Blockade implements Order {
    private boolean status = false;
    private String d_countryId;
    private Player d_player;
    public GameState d_gameState;

    /**
     * This constructor will initialize the order object with deploy details.
     * 
     * @param p_player    current player issuing blockade order
     * @param p_countryId country where blockade will effect
     */
    public Blockade(Player p_player, String p_countryId) {
        d_player = p_player;
        d_countryId = p_countryId;
        this.d_gameState = new GameState();
    }

    /**
     * Set game state.
     */
    public void setGameState(GameState p_gameState) {
        this.d_gameState = p_gameState;
    }

    /**
     * execution logic of Blockade order
     * 
     * @param p_gameState The current game state.
     * @return true if executed successfully else false if it fails
     */
    @Override
    public boolean execute() {
        if (this.status) {
            return true;
        }
        this.status = true;
        System.out.println("-----------blockade Order Execution inside---------");
        Country l_c = d_player.getOwnedCountries().get(d_countryId.toLowerCase());
        int l_existingArmies = l_c.getNumberOfArmies();
        l_existingArmies *= 3;
        l_c.setNumberOfArmies(l_existingArmies);

        // Making territory neutral
        d_player.getOwnedCountries().remove(l_c.getCountryId().toLowerCase());
        return true;
    }

    /**
     * Getter for current player
     * 
     * @return d_player
     */
    public Player getPlayer() {
        return d_player;
    }

    /**
     * Setter for current player
     * 
     * @param p_player player
     */
    public void setAttackPlayer(Player p_player) {
        this.d_player = p_player;
    }

    /**
     * Getter for ID of Country
     * 
     * @return d_countryId
     */
    public String getCountryId() {
        return d_countryId;
    }

    /**
     * Setter for ID of Country
     * 
     * @param p_countryId country ID
     */
    public void setCountryId(String p_countryId) {
        this.d_countryId = p_countryId;
    }

}
