package com.riskgame.model;

/**
 * Class containing logic for implementation of Bomb order
 *
 */
public class Bomb implements Order {

    private String d_countryId;
    private Player d_targetPlayer;
    private Player d_attackPlayer;

    /**
     * This constructor will initialize the order object with deploy details.
     * 
     * @param p_targetPlayer target player where bomb will effect
     * @param p_countryId    adjacent opponent country where bomb card will take
     *                       effect
     * @param p_attackPlayer attack player who will bomb
     */
    public Bomb(Player p_attackPlayer, Player p_targetPlayer, String p_countryId, GameState p_gameState) {
        d_targetPlayer = p_targetPlayer;
        d_attackPlayer = p_attackPlayer;
        d_countryId = p_countryId;
    }

    /**
     * execution logic of Bomb order
     * 
     * @param p_gameState The instance of the current game state in the GameEngine.
     * @return true if executed successfully else false if it fails
     */
    @Override
    public boolean execute(GameState p_gameState) {
        // Check if Source player negotiating target Player
        if (d_attackPlayer.d_negotiatePlayers.contains(d_targetPlayer)) {
            p_gameState.updateLog(d_attackPlayer.getPlayerName() + " has negotiated " + d_targetPlayer.getPlayerName(),
                    "effect");
            System.out.println(d_attackPlayer.getPlayerName() + " has negotiated " + d_targetPlayer.getPlayerName());
            // skip execute
            return false;
        }
        Country l_c = d_targetPlayer.getOwnedCountries().get(d_countryId.toLowerCase());
        int l_existingArmies = l_c.getNumberOfArmies();
        l_c.setNumberOfArmies((l_existingArmies / 2));

        return true;
    }

    /**
     * Getter for current player
     * 
     * @return d_player
     */
    public Player getD_player() {
        return d_targetPlayer;
    }

    /**
     * Setter for current player
     * 
     * @param d_player player
     */
    public void setD_player(Player d_player) {
        this.d_targetPlayer = d_player;
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
     * @param d_countryId country ID
     */
    public void setD_countryId(String d_countryId) {
        this.d_countryId = d_countryId;
    }

}
