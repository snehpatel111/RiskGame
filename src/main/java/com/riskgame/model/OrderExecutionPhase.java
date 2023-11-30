package com.riskgame.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Queue;
import java.util.Scanner;

import com.riskgame.controller.GameEngine;
import com.riskgame.utility.Constant;

import com.riskgame.model.Player;

/**
 * Represents the order execution phase.
 */
public class OrderExecutionPhase extends Phase implements Serializable {

  /**
   * Default constructor for OrderExecutionPhase.
   */
  public OrderExecutionPhase() {
    super();
  }

  /**
   * {@inheritDoc}
   * 
   * @param p_gameEngine The game engine instance to update the state.
   * @param p_gameState  The instance of the current game state in the GameEngine.
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
          this.d_gameEngine.setIssueOrderPhase();
          break;
        } else {
          this.d_gameEngine.setGameEngineLog("total orders : " + l_numOfOrders, "effect");
          System.out.println("total orders : " + l_numOfOrders);

          while (!this.d_gameState.getUnexecutedOrders().isEmpty()) {
            Order l_order = this.d_gameState.getUnexecutedOrders().poll();
            l_order.setGameState(this.d_gameState);
            boolean l_executed = l_order.execute();
            l_order.setGameState(this.d_gameState);
          }

          this.d_gameEngine.setGameEngineLog("All orders are executed successfully." + l_numOfOrders, "effect");
          System.out.println(Constant.SUCCESS_COLOR + "All orders are executed successfully." + Constant.RESET_COLOR);
          System.err.println("lol after exec");

          Iterator<Player> l_iterator = this.d_gameState.getPlayerList().listIterator();
          for (Player l_p : this.d_gameState.getPlayerList()) {
            l_p.setGameState(this.d_gameState);
            System.out.println("lol for " + l_p.isWinner());
            if (l_p.isWinner()) {
              System.out.println("lol winner");
              MapHelper l_mh = new MapHelper();
              l_mh.showMap(this.d_gameState.getPlayerList(), this.d_gameState.getGameMap(), this.d_gameState);
              this.d_gameEngine.setGameEngineLog(l_p.getPlayerName() + " wins!", "effect");
              System.out.println(Constant.SUCCESS_COLOR + "\n" + l_p.getPlayerName() + " wins!" + Constant.RESET_COLOR);
              System.exit(0);
            }
          }
          // System.out.println("lol after for");
          // while (l_iterator.hasNext()) {
          // System.out.println("lol showing card");
          // Player l_player = l_iterator.next();
          // l_player.showCards();
          // }
          System.out.println("lol setting issue order phase");
          this.d_gameEngine.setIssueOrderPhase();
          System.out.println("lol set issue order phase");
        }
      } catch (Exception e) {
        this.d_gameEngine.setGameEngineLog(e.getMessage(), "effect");
        System.out.println("lol error execution order phase " + e.getMessage());
        e.printStackTrace();

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
  public void loadGame(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  public void saveGame(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
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
