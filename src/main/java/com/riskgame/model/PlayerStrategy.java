package com.riskgame.model;

import com.riskgame.controller.GameEngine;
import com.riskgame.model.Order;

/**
 * This class is used to implement the strategy pattern for each player
 * behavior.
 */
public abstract class PlayerStrategy {
  GameEngine d_gameEngine;
  GameMap d_gameMap;
  Player d_player;

  /**
   * Constructor for PlayerStrategy
   * 
   * @param p_player     Player object
   * @param p_gameEngine GameEngine object
   */
  PlayerStrategy(Player p_player, GameEngine p_gameEngine) {
    this.d_player = p_player;
    this.d_gameEngine = p_gameEngine;
  }

  /**
   * Method to create order for each player based on strategy
   * 
   * @return Order object
   */
  public abstract Order createOrder();

  /**
   * Method to return the Country from which attack happens
   * 
   * @return Country
   */
  protected abstract Country attackFromCountry();

  /**
   * Method to return the Country to which attack happens
   * 
   * @return Country
   */
  protected abstract Country attackToCountry();

  /**
   * Method to return the Country from which army moves
   * 
   * @return Country
   */
  protected abstract Country moveArmyFromCountry();
}
