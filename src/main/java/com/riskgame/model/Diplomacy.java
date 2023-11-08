package com.riskgame.model;

import javax.swing.text.html.HTMLDocument.Iterator;

import com.riskgame.utility.Constant;

/**
 * Class containing logic for implementation of Diplomacy order
 *
 */
public class Diplomacy implements Order {
    private boolean status = false;
    private Player d_currentPlayer, d_targetPlayer;
    public GameState d_gameState;

    /**
     * Constructor that initializes class variable.
     * 
     * @param p_currentPlayer sorce player giving order
     * @param p_targetPlayer  target player
     */
    public Diplomacy(Player p_currentPlayer, Player p_targetPlayer) {
        this.d_currentPlayer = p_currentPlayer;
        this.d_targetPlayer = p_targetPlayer;
        this.d_gameState = new GameState();
    }

    /**
     * Set game state.
     */
    public void setGameState(GameState p_gameState) {
        this.d_gameState = p_gameState;
    }

    /**
     * execution logic of Diplomacy order
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
        System.out.println("-----------diplomacy Order Execution inside---------");
        if (!this.d_gameState.getPlayerList().contains(this.d_targetPlayer)) {
            return false;
        }
        this.d_currentPlayer.addPlayerToNegotiateList(this.d_currentPlayer);
        this.d_targetPlayer.addPlayerToNegotiateList(this.d_currentPlayer);
        return true;
    }

}
