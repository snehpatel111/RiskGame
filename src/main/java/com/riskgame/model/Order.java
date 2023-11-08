package com.riskgame.model;

/**
 * Executes the player order
 * Command class in command design pattern
 */
public interface Order {
    /**
     * Execution method to implement specific orders
     * 
     * @return true if order executed successfully, false otherwise
     */
    public boolean execute();

    public void setGameState(GameState gameState);
}
