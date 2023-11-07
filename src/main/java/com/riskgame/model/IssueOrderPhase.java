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

  // @Override
  // protected void performCardHandle(String p_enteredCommand, Player p_player)
  // throws IOException {
  // if (p_player.getD_ownedCards().contains(p_enteredCommand.split(" ")[0])) {
  // p_player.handleCardCommands(p_enteredCommand, p_gameState);
  // p_gameEngine.setD_gameEngineLog(p_player.getD_playerLog(), "effect");
  // }
  // p_player.checkForMoreOrders();
  // }

  @Override
  protected void showMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    if (!Util.isValidCommandArgument(p_args, 2)) {
      System.out.println(Constant.ERROR_COLOR
          + "Invalid command! Try command -> showMap"
          + Constant.RESET_COLOR);
      return;
    }
    MapHelper l_mapHelper = new MapHelper(p_gameState);
    l_mapHelper.showMap(p_gameState.getGameMap());

    // getOrder(p_player);
  }

  // @Override
  // protected void performAdvance(String p_command, Player p_player) throws
  // IOException {
  // p_player.createAdvanceOrder(p_command, this.d_gameState);
  // p_gameState.updateLog(p_player.getPlayerLog(), "effect");
  // p_player.checkForMoreOrders();
  // }

  @Override
  public void initPhase() {
    System.out.println("lol issueOrder initPhase");
    BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
    while (this.d_gameEngine.getCurrentGamePhase() instanceof IssueOrderPhase) {
      try {
        int l_totalReinforcement = 0;
        Iterator<Player> l_iterator = this.d_gameState.getPlayerList().listIterator();
        System.out.println("lol issueOrder player list size is " + this.d_gameState.getPlayerList().size());
        while (l_iterator.hasNext()) {
          Player l_player = l_iterator.next();
          l_totalReinforcement += l_player.getOwnedArmyCount() > 0 ? l_player.getOwnedArmyCount() : 0;
        }
        System.out.println("lol total reinforcement is " + l_totalReinforcement);
        if (l_totalReinforcement <= 0) {
          break;
        }
        // if (l_totalReinforcement > 0) {
          l_iterator = this.d_gameState.getPlayerList().listIterator();
          while (l_iterator.hasNext()) {
            Player l_player = l_iterator.next();
            System.out.println("Player " + l_player.getPlayerName() + "'s turn");
            String l_command = l_reader.readLine();
            this.handleCommand(l_command, l_player);
          }
        // }
        break;
      } catch (Exception e) {
        this.d_gameEngine.setGameEngineLog(e.getMessage(), "effect");
      }
      // this.issueOrders();
    }
  }

  @Override
  protected void deploy(Player p_player, String[] p_args) {
    System.out.println("lol issueOrder deploy");
    // p_player.createDeployOrder(p_command);
    // p_gameState.updateLog(p_player.getD_playerLog(), "effect");
    // p_player.checkForMoreOrders();

    p_player.setArgs(p_args);
    p_player.issue_deployOrder();
  }

  // /**
  // * Accepts orders from players.
  // */
  // protected void issueOrders() {
  // do {
  // for (Player p_player : this.d_gameState.getPlayerList()) {
  // if (p_player.getD_moreOrders() &&
  // !p_player.getD_playerName().equals("Neutral")) {
  // try {
  // p_player.issueOrder(this);
  // } catch (InvalidCommand | IOException | InvalidMap p_exception) {
  // p_gameEngine.setD_gameEngineLog(p_exception.getMessage(), "effect");
  // }
  // }
  // }
  // } while
  // (p_playerService.checkForMoreOrders(this.d_gameState.getPlayerList()));

  // p_gameEngine.setOrderExecutionPhase();
  // }

  // /**
  // * Get order commands from the user.
  // *
  // * @param p_player The player for which commands are to be issued.
  // * @throws InvalidCommand Exception if the command is invalid.
  // * @throws IOException Indicates a failure in an I/O operation.
  // * @throws InvalidMap Indicates a failure in using an invalid map.
  // */
  // public void getOrder(Player p_player) throws InvalidCommand, IOException,
  // InvalidMap {
  // BufferedReader l_reader = new BufferedReader(new
  // InputStreamReader(System.in));
  // System.out.println("\nPlease enter a command to issue an order for player: "
  // + p_player.getD_playerName()
  // + " or give the 'showmap' command to view the current state of the game.");
  // String l_commandEntered = l_reader.readLine();

  // p_gameState.updateLog("(Player: " + p_player.getD_playerName() + ") " +
  // l_commandEntered, "order");

  // handleCommand(l_commandEntered, p_player);
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  public void assignCountries(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void managePlayer(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void editNeighbor(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void editCountry(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void validateMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void loadMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void saveMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void editContinent(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    printInvalidCommandInState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void editMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    printInvalidCommandInState();
  }
}
