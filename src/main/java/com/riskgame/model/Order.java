package com.riskgame.model;

/**
 * Executes the player order
 * Command class in command design pattern
 */
public interface Order {
     /**
      * Execution method to implement specific orders
      * 
      * @return true is order executed successfully, or false if it fails
      */
     public boolean execute();

     /**
      * Set game state.
      * @param gameState current game state
      */
     public void setGameState(GameState gameState);
}
