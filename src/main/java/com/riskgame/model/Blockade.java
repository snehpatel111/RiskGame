package com.riskgame.model;

/**
 * Class containing logic for implementation of Blockade order
 *
 */
public class Blockade implements Order {

    private String d_countryId;
    private Player d_player;

    /**
     * This constructor will initialize the order object with deploy details.
     * 
     * @param p_player    current player issuing blockade order
     * @param p_countryId country where blockade will effect
     */
    public Blockade(Player p_player, String p_countryId) {
        d_player = p_player;
        d_countryId = p_countryId;
    }

    /**
     * execution logic of Blockade order
     * 
     * @return true if executed successfully else false if it fails
     */
    @Override
    public boolean execute(GameState p_gameState) {
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
    public Player getD_player() {
        return d_player;
    }

    /**
     * Setter for current player
     * 
     * @param p_player player
     */
    public void setD_player(Player p_player) {
        this.d_player = p_player;
    }

    /**
     * Getter for ID of Country
     * 
     * @return d_countryId
     */
    public String getD_countryId() {
        return d_countryId;
    }

    /**
     * Setter for ID of Country
     * 
     * @param p_countryId country ID
     */
    public void setD_countryId(String p_countryId) {
        this.d_countryId = p_countryId;
    }

}
