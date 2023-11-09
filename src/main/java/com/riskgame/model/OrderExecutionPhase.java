package com.riskgame.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Queue;
import java.util.Scanner;

import com.riskgame.controller.GameEngine;
import com.riskgame.utility.Constant;

import com.riskgame.model.Player;

/**
 * Represents the order execution phase.
 */
public class OrderExecutionPhase extends Phase {

  /**
   * {@inheritDoc}
   * 
   * @param p_gameEngine
   * @param p_gameState
   */
  public OrderExecutionPhase(GameEngine p_gameEngine, GameState p_gameState) {
    super(p_gameEngine, p_gameState);
  }

  @Override
  protected void showMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  @Override
  public void initPhase() {
    while (this.d_gameEngine.getCurrentGamePhase() instanceof OrderExecutionPhase) {
      try {
        int l_numOfOrders = this.d_gameState.getUnexecutedOrders().size();
        if (l_numOfOrders == 0) {
          this.d_gameEngine.setGameEngineLog("Orders already executed!!", "effect");
          System.out.println("Orders already executed!!");
          break;
        } else {
          this.d_gameEngine.setGameEngineLog("total orders : " + l_numOfOrders, "effect");
          System.out.println("total orders : " + l_numOfOrders);

          while (!this.d_gameState.getUnexecutedOrders().isEmpty()) {
            Order l_order = this.d_gameState.getUnexecutedOrders().poll();
            boolean l_executed = l_order.execute();
            l_order.setGameState(this.d_gameState);
          }

          this.d_gameEngine.setGameEngineLog("All orders are executed successfully." + l_numOfOrders, "effect");
          System.out.println(Constant.SUCCESS_COLOR + "All orders are executed successfully." + Constant.RESET_COLOR);

          Iterator<Player> l_iterator = this.d_gameState.getPlayerList().listIterator();
          while (l_iterator.hasNext()) {
            Player l_player = l_iterator.next();
            l_player.showCards();
          }
          this.d_gameEngine.setIssueOrderPhase();
        }
      } catch (Exception e) {
        this.d_gameEngine.setGameEngineLog(e.getMessage(), "effect");
      }
    }

  }

  /**
   * {@inheritDoc}
   */
  public void execute(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void deploy(GameState p_gameState, Player p_player, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void assignCountries(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void editNeighbor(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void editCountry(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void validateMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void loadMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void saveMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void editContinent(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void editMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_mapFileName) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void assignReinforcementToPlayer(GameState p_gameState) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void managePlayer(GameEngine p_gameEngine, GameState p_gameState, String[] p_mapFileName) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void bomb(GameState d_gameState, Player p_player, String[] l_data) {
    this.printInvalidCommandInState();

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void negotiate(GameState d_gameState, Player p_player, String[] l_data) {
    this.printInvalidCommandInState();

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void blockade(GameState p_gameState, Player p_player, String[] l_data) {
    this.printInvalidCommandInState();

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void airLift(GameState p_gameState, Player p_player, String[] l_data) {
    this.printInvalidCommandInState();

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void advance(GameState p_gameState, Player p_player, String[] l_data) {
    this.printInvalidCommandInState();

  }
}
