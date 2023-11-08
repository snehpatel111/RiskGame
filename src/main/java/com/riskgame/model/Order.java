package com.riskgame.model;

/**
 * Executes the player order
 * Command class in command design pattern
 */
public interface Order {
     /**
      * Execution method to implement specific orders
      * 
      * @param p_gameState The current game state.
      * @return true is order executed successfully, or false if it fails
      */
     public boolean execute();

     public void setGameState(GameState gameState);
}
