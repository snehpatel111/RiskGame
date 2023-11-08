package com.riskgame.model;

import com.riskgame.model.Country;
import com.riskgame.model.Player;

import com.riskgame.utility.Constant;

/**
 * Class containing logic for implementation of airlift order
 *
 */
public class Airlift implements Order {
    private boolean status = false;
    private int d_armyCount;
    private String d_sourceCountryId;
    private String d_targetCountryId;
    private Player d_player;
    public GameState d_gameState;

    /**
     * This constructor will initialize the order object with deploy details.
     * 
     * @param p_player          current player issuing deploy order
     * @param p_sourceCountryId country from which armies will be airlifted
     * @param p_targetCountryId country to which armies will be deployed
     * @param p_armyCount       total armies which will be airlifted
     */
    public Airlift(Player p_player, String p_sourceCountryId, String p_targetCountryId, int p_armyCount) {
        d_player = p_player;
        d_sourceCountryId = p_sourceCountryId;
        d_targetCountryId = p_targetCountryId;
        d_armyCount = p_armyCount;
        this.d_gameState = new GameState();
    }

    /**
     * Set game state.
     */
    public void setGameState(GameState p_gameState) {
        this.d_gameState = p_gameState;
    }

    /**
     * execution logic of Airlift order
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
        System.out.println("-----------Airlift Order Execution inside---------");
        if (this.d_player.getOwnedCountries().containsKey(this.d_sourceCountryId.toLowerCase()) &&
                this.d_player.getOwnedCountries().containsKey(this.d_targetCountryId.toLowerCase())) {

            Country l_source = d_player.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase());
            int l_existingSourceArmies = l_source.getNumberOfArmies();
            l_existingSourceArmies -= d_armyCount;
            l_source.setNumberOfArmies(l_existingSourceArmies);

            Country l_target = d_player.getOwnedCountries().get(this.d_targetCountryId.toLowerCase());
            int l_existingTargetArmies = l_target.getNumberOfArmies();
            l_existingTargetArmies += d_armyCount;
            l_target.setNumberOfArmies(l_existingTargetArmies);

            return true;
        } else {
            this.d_gameState.updateLog(
                    this.d_player.getPlayerName() + " does not own either " + this.d_sourceCountryId + " or "
                            + this.d_targetCountryId + " country",
                    "effect");
            System.out.println(Constant.ERROR_COLOR
                    + this.d_player.getPlayerName() + " does not own either " + this.d_sourceCountryId + " or "
                    + this.d_targetCountryId + " country" + Constant.RESET_COLOR);
            return false;
        }
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
     * Getter for ID of Source Country
     * 
     * @return d_SourceCountryId
     */
    public String getSourceCountryId() {
        return d_sourceCountryId;
    }

    /**
     * Setter for ID of Source Country
     * 
     * @param d_SourceCountryId country ID
     */
    public void setSourceCountryId(String p_sourceCountryId) {
        this.d_sourceCountryId = p_sourceCountryId;
    }

    /**
     * Getter for ID of Target Country
     * 
     * @return d_SourceCountryId
     */
    public String getTargetCountryId() {
        return d_targetCountryId;
    }

    /**
     * Setter for ID of Target Country
     * 
     * @param d_TargetCountryId country ID
     */
    public void setTargetCountryId(String p_targetCountryId) {
        this.d_targetCountryId = p_targetCountryId;
    }

    /**
     * Setter for number of armies
     * 
     * @return d_numArmies
     */
    public int getNumArmies() {
        return d_armyCount;
    }

    /**
     * Setter for number of armies
     * 
     * @param d_numArmies number of armies
     */
    public void setNumArmies(int p_numArmies) {
        this.d_armyCount = p_numArmies;
    }
}
