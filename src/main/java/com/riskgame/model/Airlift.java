package com.riskgame.model;

/**
 * Class containing logic for implementation of airlift order
 *
 */
public class Airlift implements Order{

    private int d_armyCount;
    private String d_sourceCountryId;
    private String d_targetCountryId;
    private Player d_player;

    /**
     * This constructor will initialize the order object with deploy details.
     * @param p_player current player issuing deploy order
     * @param p_sourceCountryId country from which armies will be airlifted
     * @param p_targetCountryId country to which armies will be deployed
     * @param p_armyCount total armies which will be airlifted
     */
    public Airlift(Player p_player,String p_sourceCountryId, String p_targetCountryId, int p_armyCount) {
        d_player = p_player;
        d_sourceCountryId = p_sourceCountryId;
        d_targetCountryId = p_targetCountryId;
        d_armyCount = p_armyCount;
    }

    /**
     * execution logic of Airlift order
     * @return true if executed successfully else false if it fails
     */
    @Override
    public boolean execute() {
        Country l_source= d_player.getOwnedCountries().get(d_sourceCountryId.toLowerCase());
        int l_existingSourceArmies = l_source.getNumberOfArmies();
        l_existingSourceArmies -= d_armyCount;
        l_source.setNumberOfArmies(l_existingSourceArmies);

        Country l_target= d_player.getOwnedCountries().get(d_targetCountryId.toLowerCase());
        int l_existingTargetArmies = l_target.getNumberOfArmies();
        l_existingTargetArmies += d_armyCount;
        l_target.setNumberOfArmies(l_existingTargetArmies);

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
     * @param p_player player
     */
    public void setD_player(Player p_player) {
        this.d_player = p_player;
    }

    /**
     * Getter for ID of Source Country
     * @return d_SourceCountryId
     */
    public String getD_SourceCountryId() { return d_sourceCountryId; }

    /**
     * Setter for ID of Source Country
     * @param d_SourceCountryId country ID
     */
    public void setD_SourceCountryId(String p_sourceCountryId) { this.d_sourceCountryId = p_sourceCountryId; }

    /**
     * Getter for ID of Target Country
     * @return d_SourceCountryId
     */
    public String getD_TargetCountryId() { return d_targetCountryId; }

    /**
     * Setter for ID of Target Country
     * @param d_TargetCountryId country ID
     */
    public void setD_TargetCountryId(String p_targetCountryId) { this.d_targetCountryId = p_targetCountryId; }

    /**
     * Setter for number of armies
     * @return d_numArmies
     */
    public int getD_numArmies() {
        return d_armyCount;
    }

    /**
     * Setter for number of armies
     * @param d_numArmies number of armies
     */
    public void setD_numArmies(int p_numArmies) {
        this.d_armyCount = p_numArmies;
    }
}
