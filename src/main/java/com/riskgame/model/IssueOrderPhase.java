package com.riskgame.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import com.riskgame.model.GameState;
import com.riskgame.model.MapHelper;
import com.riskgame.model.Phase;

import com.riskgame.model.Player;

import com.riskgame.utility.Constant;
import com.riskgame.utility.Util;

import com.riskgame.model.Continent;

import com.riskgame.controller.GameEngine;

/**
 * Implementation of the Issue Order Phase for gameplay using the State Pattern.
 */
public class IssueOrderPhase extends Phase {

  /**
   * Constructor to initialize the game engine context.
   *
   * @param p_gameEngine The game engine instance to update the state.
   * @param p_gameState  The instance of the current game state in the GameEngine.
   */
  public IssueOrderPhase(GameEngine p_gameEngine, GameState p_gameState) {
    super(p_gameEngine, p_gameState);
  }

  /**
   * @param p_gameEngine The GameEngine context.
   * @param p_gameState  The current game state.
   * @param p_args       The arguments
   */
  @Override
  protected void showMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    if (!Util.isValidCommandArgument(p_args, 1)) {
      p_gameState.updateLog("Invalid command! Try command -> showMap", "effect");
      System.out.println(Constant.ERROR_COLOR
          + "Invalid command! Try command -> showMap"
          + Constant.RESET_COLOR);
      return;
    }
    MapHelper l_mapHelper = new MapHelper(p_gameState);
    l_mapHelper.showMap(p_gameState.getPlayerList(), p_gameState.getGameMap(), p_gameState);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initPhase() {
    BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
    this.assignReinforcementToPlayer(this.d_gameState);
    System.out.println(
        Constant.SUCCESS_COLOR
            + "Reinforcement assigned to each player! \nBegin to issue order as per turn!"
            + Constant.RESET_COLOR);
    while (this.d_gameEngine.getCurrentGamePhase() instanceof IssueOrderPhase) {
      try {
        int l_totalReinforcement = this.d_gameState.getTotalArmyOfAllPlayers();
        System.out.println("Total armies in the reinforcement pool: " + l_totalReinforcement);
        if (l_totalReinforcement == 0) {
          this.d_gameEngine.setOrderExecutionPhase();
          break;
        }
        for (Player l_player : this.d_gameState.getPlayerList()) {
          if (this.d_gameState.getTotalArmyOfAllPlayers() == 0) {
            break;
          }
          this.printPlayerArmies(this.d_gameState);
          System.out.println("Player " + l_player.getPlayerName() + "'s turn (Remaining Army count: "
              + l_player.getOwnedArmyCount() + ")");
          l_player.showCards();
          String l_command = l_reader.readLine();
          this.handleCommand(l_command, l_player);
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
    if (!Util.isValidCommandArgument(p_args, 1)) {
      p_gameState.updateLog("Invalid command! Try command -> execute", "effect");
      System.out.println(Constant.ERROR_COLOR
          + "Invalid command! Try command -> execute"
          + Constant.RESET_COLOR);
      return;
    }
    System.out.println("lol execute issue order phase");
    this.d_gameEngine.setOrderExecutionPhase();
  }

  /**
   * Prints the armies of all the players.
   * 
   * @param p_gameState GameState object containing current game state
   */
  public void printPlayerArmies(GameState p_gameState) {
    Iterator<Player> l_itr = p_gameState.getPlayerList().listIterator();
    while (l_itr.hasNext()) {
      Player l_p = l_itr.next();
      System.out.println("Player " + l_p.getPlayerName() + " has " + l_p.getOwnedArmyCount() + " armies currently.");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void deploy(GameState p_gameState, Player p_player, String[] p_args) {
    p_player.setArgs(p_args);
    p_player.setGameState(p_gameState);
    p_player.issue_deployOrder();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void assignCountries(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void managePlayer(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void editNeighbor(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void editCountry(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void validateMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void loadMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void saveMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void editContinent(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void editMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    this.printInvalidCommandInState();
  }

  /**
   * method to assign reignforcement to each player
   * 
   * @param p_gameState game state
   */
  @Override
  public void assignReinforcementToPlayer(GameState p_gameState) {
    Iterator<Player> l_iterator = p_gameState.getPlayerList().listIterator();

    while (l_iterator.hasNext()) {
      Player l_player = l_iterator.next();
      int l_totalControlValueCount = 0;
      int l_totalReinforcementArmyCount;

      if (l_player.getOwnedCountries().size() >= 10) {
        if (l_player.getOwnedContinents().size() > 0) {
          for (Continent l_continent : l_player.getOwnedContinents().values()) {
            l_totalControlValueCount += l_continent.getControlValue();
          }
          l_totalReinforcementArmyCount = (int) (l_player.getOwnedCountries().size() / 2)
              + l_totalControlValueCount;
        } else {
          l_totalReinforcementArmyCount = (int) (l_player.getOwnedCountries().size() / 2);
        }
      } else {
        l_totalReinforcementArmyCount = 5;
      }
      l_player.setOwnedArmyCount(l_player.getOwnedArmyCount() + l_totalReinforcementArmyCount);
    }
  }

  @Override
  protected void advance(GameState p_gameState, Player p_player, String[] l_data) {
    p_player.setArgs(l_data);
    p_player.setGameState(p_gameState);
    p_player.issue_advanceOrder();
  }

  @Override
  protected void bomb(GameState p_gameState, Player p_player, String[] l_data) {
    p_player.setArgs(l_data);
    p_player.setGameState(p_gameState);
    p_player.issue_bombOrder();
  }

  @Override
  protected void negotiate(GameState p_gameState, Player p_player, String[] l_data) {
    p_player.setArgs(l_data);
    p_player.setGameState(p_gameState);
    p_player.issue_diplomacyOrder();
  }

  @Override
  protected void blockade(GameState p_gameState, Player p_player, String[] l_data) {
    p_player.setArgs(l_data);
    p_player.setGameState(p_gameState);
    p_player.issue_blockadeOrder();
  }

  @Override
  protected void airLift(GameState p_gameState, Player p_player, String[] l_data) {
    p_player.setArgs(l_data);
    p_player.setGameState(p_gameState);
    p_player.issue_airliftOrder();
  }
}
