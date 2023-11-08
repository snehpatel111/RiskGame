package com.riskgame.model;

/**
 * Class containing logic for implementation of Diplomacy order
 *
 */
public class Diplomacy implements Order{
    private boolean status = false;
    private Player d_currentPlayer, d_targetPlayer;

    /**
     * Constructor that initializes class variable.
     * 
     * @param p_currentPlayer sorce player giving order
     * @param p_targetPlayer  target player
     */
    public Diplomacy(Player p_currentPlayer, Player p_targetPlayer) {
        this.d_currentPlayer = p_currentPlayer;
        this.d_targetPlayer = p_targetPlayer;
    }

    /**
     * execution logic of Diplomacy order
     * 
     * @return true if executed successfully else false if it fails
     */
    @Override
    public boolean execute() {
        if(this.status){
			return true;
		}
		this.status = true;
        System.out.println("-----------diplomacy Order Execution inside---------");
        this.d_currentPlayer.addPlayerToNegotiateList(d_currentPlayer);
        this.d_targetPlayer.addPlayerToNegotiateList(d_currentPlayer);

        return true;
    }

}
