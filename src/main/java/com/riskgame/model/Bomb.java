package com.riskgame.model;

import com.riskgame.model.Country;
import com.riskgame.model.Player;
import com.riskgame.utility.Constant;

/**
 * Class containing logic for implementation of Bomb order
 *
 */
public class Bomb implements Order {
    private boolean status = false;
    private String d_countryId;
    private Player d_targetPlayer;
    private Player d_attackPlayer;
    public GameState d_gameState;

    /**
     * This constructor will initialize the order object with deploy details.
     * 
     * @param p_targetPlayer target player where bomb will effect
     * @param p_countryId    adjacent opponent country where bomb card will take
     *                       effect
     * @param p_attackPlayer attack player who will bomb
     * @param p_gameState    The current game state.
     */
    public Bomb(Player p_attackPlayer, Player p_targetPlayer, String p_countryId) {
        this.d_targetPlayer = p_targetPlayer;
        this.d_attackPlayer = p_attackPlayer;
        this.d_countryId = p_countryId;
        this.d_gameState = new GameState();
    }

    /**
     * Set game state.
     */
    public void setGameState(GameState p_gameState) {
        this.d_gameState = p_gameState;
    }

    /**
     * execution logic of Bomb order
     * 
     * @return true if executed successfully else false if it fails
     */
    @Override
    public boolean execute() {
        if (this.status) {
            return true;
        }
        this.status = true;
        System.out.println("-----------bomb Order Execution inside---------");
        // Check if Source player negotiating target Player
        if (this.d_attackPlayer.d_negotiatePlayers.contains(this.d_targetPlayer)) {
            System.out.println(
                    this.d_attackPlayer.getPlayerName() + " has negotiated " + this.d_targetPlayer.getPlayerName());
            // skip execute
            return false;
        }
        // Check if Source player owns the country
        if (this.d_attackPlayer.getOwnedCountries().containsKey(this.d_countryId.toLowerCase())) {
            System.out.println(Constant.ERROR_COLOR + this.d_attackPlayer.getPlayerName()
                    + " cannot deploy bomb on owned " + this.d_countryId + " country" + Constant.RESET_COLOR);
            return false;
        }
        if (!this.d_attackPlayer.validateTargetCountry(this.d_countryId)) {
            System.out.println(Constant.ERROR_COLOR + this.d_attackPlayer.getPlayerName()
                    + this.d_countryId + " country does not exist on map" + Constant.RESET_COLOR);
            return false;
        }
        Country l_c = this.d_targetPlayer.getOwnedCountries().get(this.d_countryId.toLowerCase());
        int l_existingArmies = l_c.getNumberOfArmies();
        l_c.setNumberOfArmies((l_existingArmies / 2));
        return true;
    }

    /**
     * Getter for current player
     * 
     * @return d_player
     */
    public Player getPlayer() {
        return d_targetPlayer;
    }

    /**
     * Setter for current player
     * 
     * @param d_player player
     */
    public void setAttackPlayer(Player d_player) {
        this.d_targetPlayer = d_player;
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
     * @param d_countryId country ID
     */
    public void setCountryId(String d_countryId) {
        this.d_countryId = d_countryId;
    }

}
