package com.riskgame.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Queue;

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
public class IssueOrderPhase extends Phase implements Serializable {

  /**
   * Represents the current phase of the game.
   */
  Phase d_phase;

  /**
   * Default constructor for IssueOrderPhase.
   */
  public IssueOrderPhase() {
    super();
  }

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
      this.d_gameState.updateLog("Invalid command! Try command -> showMap", "effect");
      System.out.println(Constant.ERROR_COLOR
          + "Invalid command! Try command -> showMap"
          + Constant.RESET_COLOR);
      return;
    }
    MapHelper l_mapHelper = new MapHelper(this.d_gameState);
    l_mapHelper.showMap(this.d_gameState.getPlayerList(), this.d_gameState.getGameMap(), this.d_gameState);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initPhase() {
    for (Player l_p : this.d_gameState.getPlayerList()) {
      l_p.setGameState(this.d_gameState);
      System.out.println("lol for " + l_p.isWinner());
      if (l_p.isWinner()) {
        System.out.println("lol winner");
        this.d_gameEngine.setGameEngineLog(l_p.getPlayerName() + " wins!", "effect");
        System.out.println(Constant.SUCCESS_COLOR + "\n" + l_p.getPlayerName() + " wins!" + Constant.RESET_COLOR);
        MapHelper l_mh = new MapHelper();
        l_mh.showMap(this.d_gameState.getPlayerList(), this.d_gameState.getGameMap(), this.d_gameState);
        System.exit(0);
      }
    }
    BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
    this.assignReinforcementToPlayer(this.d_gameState);
    MapHelper l_mapHelper = new MapHelper();
    this.d_gameState.updateLog("Reinforcement assigned to each player! \nBegin to issue order as per turn!", "effect");
    System.out.println(
        Constant.SUCCESS_COLOR
            + "Reinforcement assigned to each player! \nBegin to issue order as per turn!"
            + Constant.RESET_COLOR);
    while (this.d_gameEngine.getCurrentGamePhase() instanceof IssueOrderPhase) {
      try {
        int l_totalReinforcement = this.d_gameState.getTotalArmyOfAllPlayers();
        this.d_gameEngine.setGameEngineLog("Total armies in the reinforcement pool: " + l_totalReinforcement, "effect");
        System.out.println("Total armies in the reinforcement pool: " + l_totalReinforcement);
        if (l_totalReinforcement == 0) {
          this.d_gameEngine.setOrderExecutionPhase();
          break;
        }
        for (Player l_player : this.d_gameState.getPlayerList()) {
          if (this.d_gameState.getTotalArmyOfAllPlayers() == 0) {
            break;
          }
          // Case for Not Human Player
          System.out.println("lol Not Human outside if" + l_player.d_isHuman);
          if (!l_player.d_isHuman) {
            for (Player l_p : this.d_gameState.getPlayerList()) {
              l_p.setGameState(this.d_gameState);
              System.out.println("lol for " + l_p.isWinner());
              if (l_p.isWinner()) {
                System.out.println("lol winner");
                this.d_gameEngine.setGameEngineLog(l_p.getPlayerName() + " wins!", "effect");
                System.out.println(Constant.SUCCESS_COLOR + "\n" + l_p.getPlayerName() + " wins!" + Constant.RESET_COLOR);
                MapHelper l_mh = new MapHelper();
                l_mh.showMap(this.d_gameState.getPlayerList(), this.d_gameState.getGameMap(), this.d_gameState);
                System.exit(0);
              }
            }
            l_player.setGameState(this.d_gameState);
            System.out.println("lol Not Human");
            this.printPlayerArmies(this.d_gameState);
            this.d_gameEngine.setGameEngineLog("Player " + l_player.getPlayerName(), "effect");
            System.out.println("Player " + l_player.getPlayerName() + "'s turn (Remaining Army count: "
                + l_player.getOwnedArmyCount() + ")");
            l_player.showCards();
            l_player.issueOrder();
            l_player.setGameState(this.d_gameState);
            continue;
          }
          this.printPlayerArmies(this.d_gameState);
          this.d_gameEngine.setGameEngineLog("Player " + l_player.getPlayerName(), "effect");
          System.out.println("Player " + l_player.getPlayerName() + "'s turn (Remaining Army count: "
              + l_player.getOwnedArmyCount() + ")");
          l_player.showCards();
          String l_command = l_reader.readLine();
          this.handleCommand(l_command, l_player);
        }
      } catch (Exception e) {
        this.d_gameEngine.setGameEngineLog(e.getMessage(), "effect");
        System.out.println("lol - " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void execute(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    if (!Util.isValidCommandArgument(p_args, 1)) {
      this.d_gameState.updateLog("Invalid command! Try command -> execute", "effect");
      System.out.println(Constant.ERROR_COLOR
          + "Invalid command! Try command -> execute"
          + Constant.RESET_COLOR);
      return;
    }
    this.d_gameEngine.setOrderExecutionPhase();
  }

  /**
   * Prints the armies of all the players.
   * 
   * @param p_gameState GameState object containing current game state
   */
  public void printPlayerArmies(GameState p_gameState) {
    Iterator<Player> l_itr = this.d_gameState.getPlayerList().listIterator();
    while (l_itr.hasNext()) {
      Player l_p = l_itr.next();
      this.d_gameEngine.setGameEngineLog(
          "Player " + l_p.getPlayerName() + " has " + l_p.getOwnedArmyCount() + " armies currently.", "effect");
      System.out.println("Player " + l_p.getPlayerName() + " has " + l_p.getOwnedArmyCount() + " armies currently.");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void deploy(GameState p_gameState, Player p_player, String[] p_args) {
    p_player.setArgs(p_args);
    p_player.setGameState(this.d_gameState);
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
  public void loadGame(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    try {
      if (!Util.isValidCommandArgument(p_args, 2)) {
        this.d_gameEngine.setGameEngineLog("Invalid number of arguments for loadgame command", "effect");
        System.out.println(Constant.ERROR_COLOR
            + "Invalid number of arguments for loadgame command" + Constant.RESET_COLOR);
        System.out.println(Constant.ERROR_COLOR
            + "Try command -> loadgame <filename>" + Constant.RESET_COLOR);
        return;
      }
      ObjectInputStream l_inputStream = new ObjectInputStream(
          new FileInputStream(Constant.GAME_PATH + p_args[1]));
      this.d_gameEngine = (GameEngine) l_inputStream.readObject();
      this.d_gameState = this.d_gameEngine.getGameState();
      this.d_gameEngine.setGameState(this.d_gameState);
      this.d_gameEngine.getCurrentGamePhase().setGameState(this.d_gameState);
      l_inputStream.close();
      this.d_gameEngine.setGameEngineLog("Game loaded successfully!", "effect");
      System.out.println(Constant.SUCCESS_COLOR + "Game loaded successfully!" + Constant.RESET_COLOR);
    } catch (Exception e) {
      this.d_gameEngine.setGameEngineLog("Error while loading the game: " + e.getMessage(), "effect");
      System.out
          .println(Constant.ERROR_COLOR + "Error while loading the game: " + e.getMessage() + Constant.RESET_COLOR);
      e.printStackTrace();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void saveGame(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    try {
      if (!Util.isValidCommandArgument(p_args, 2)) {
        this.d_gameEngine.setGameEngineLog("Invalid number of arguments for savegame command", "effect");
        System.out.println(Constant.ERROR_COLOR
            + "Invalid number of arguments for savegame command" + Constant.RESET_COLOR);
        System.out.println(Constant.ERROR_COLOR
            + "Try command -> savegame <filename>" + Constant.RESET_COLOR);
        return;
      }
      this.d_gameEngine.setGameState(this.d_gameState);
      FileOutputStream l_gameOutputFile = new FileOutputStream(
          Constant.GAME_PATH + p_args[1]);
      ObjectOutputStream l_gameOutputStream = new ObjectOutputStream(l_gameOutputFile);
      l_gameOutputStream.writeObject(this.d_gameEngine);
      l_gameOutputStream.flush();
      l_gameOutputStream.close();
      this.d_gameEngine.setGameEngineLog("Game saved successfully!", "effect");
      System.out.println(Constant.SUCCESS_COLOR + "Game saved successfully!" + Constant.RESET_COLOR);
    } catch (Exception e) {
      this.d_gameEngine.setGameEngineLog("Error while saving game: " + e.getMessage(), "effect");
      System.out
          .println(Constant.ERROR_COLOR + "Error while saving game " + e.getMessage() + Constant.RESET_COLOR);
      e.printStackTrace();
    }
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
    Iterator<Player> l_iterator = this.d_gameState.getPlayerList().listIterator();

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
    p_player.setGameState(this.d_gameState);
    p_player.issue_advanceOrder();
  }

  @Override
  protected void bomb(GameState p_gameState, Player p_player, String[] l_data) {
    p_player.setArgs(l_data);
    p_player.setGameState(this.d_gameState);
    p_player.issue_bombOrder();
  }

  @Override
  protected void negotiate(GameState p_gameState, Player p_player, String[] l_data) {
    p_player.setArgs(l_data);
    p_player.setGameState(this.d_gameState);
    p_player.issue_diplomacyOrder();
  }

  @Override
  protected void blockade(GameState p_gameState, Player p_player, String[] l_data) {
    p_player.setArgs(l_data);
    p_player.setGameState(this.d_gameState);
    p_player.issue_blockadeOrder();
  }

  @Override
  protected void airLift(GameState p_gameState, Player p_player, String[] l_data) {
    p_player.setArgs(l_data);
    p_player.setGameState(this.d_gameState);
    p_player.issue_airliftOrder();
  }
}
