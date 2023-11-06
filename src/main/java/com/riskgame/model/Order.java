package com.riskgame.model;

/**
 * Executes the player order
 * Command class in command design pattern
 */
public interface Order {

    /**
     * execution method to implement specific orders
     * @return true is order executed successfully, or false if it fails
     */
    public boolean execute();
}
