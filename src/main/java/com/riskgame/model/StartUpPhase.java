package com.riskgame.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Scanner;

import com.riskgame.controller.GameEngine;

import com.riskgame.model.ConquestMap;
import com.riskgame.model.Continent;
import com.riskgame.model.DominationMap;
import com.riskgame.model.MapAdapter;
import com.riskgame.model.MapHelper;
import com.riskgame.model.Player;
import com.riskgame.utility.Constant;
import com.riskgame.utility.MapValidator;
import com.riskgame.utility.Util;

/**
 * Implementation of the Start-Up Phase for gameplay using the State Pattern.
 */
public class StartUpPhase extends Phase implements Serializable {

  /**
   * Default constructor for StartUpPhase.
   */
  public StartUpPhase() {
    super();
  }

  /**
   * Constructor that initializes the GameEngine context in the Phase class.
   *
   * @param p_gameEngine The GameEngine context.
   * @param p_gameState  The current game state.
   */
  public StartUpPhase(GameEngine p_gameEngine, GameState p_gameState) {
    super(p_gameEngine, p_gameState);
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
  protected void showMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    if (!this.d_gameState.isGameMapLoaded()) {
      this.d_gameEngine.setGameEngineLog("Cannot show  map, please perform `editmap` or `loadmap` first",
          "effect");

      System.out.println(
          Constant.ERROR_COLOR
              + "Cannot show  map, please perform `editmap` or `loadmap` first"
              + Constant.RESET_COLOR);
      System.out.println(
          Constant.ERROR_COLOR
              + "Try command -> editmap sample.map or loadmap sample.map"
              + Constant.RESET_COLOR);
      return;
    } else if (!Util.isValidCommandArgument(p_args, 1)) {
      this.d_gameEngine.setGameEngineLog("Invalid command! Try command -> showMap", "effect");
      System.out.println(Constant.ERROR_COLOR
          + "Invalid command! Try command -> showMap"
          + Constant.RESET_COLOR);
      return;
    }
    MapHelper l_mapHelper = new MapHelper(p_gameState);
    l_mapHelper.showMap(this.d_gameState.getGameMap(), p_gameState);
  }

  /**
   * {@inheritDoc}
   */
  public void editMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    try {
      if (this.d_gameState.isGameMapLoaded()) {
        System.out.println(Constant.ERROR_COLOR + "Map already loaded."
            + Constant.RESET_COLOR);
        System.out.println(Constant.ERROR_COLOR +
            "Try any of following command: editcontinent, editcountry, editneighbor, savemap, showmap, loadmap, validatemap, or exit"
            + Constant.RESET_COLOR);
        this.d_gameEngine.setGameEngineLog(
            "Map already loaded. \nTry any of following command: editcontinent, editcountry, editneighbor, savemap, showmap, loadmap, validatemap, or exit ",
            "effect");

        return;
      } else if (!Util.isValidCommandArgument(p_args, 2)) {
        System.out.println(Constant.ERROR_COLOR
            + "Invalid command! Try command -> editmap sample.map"
            + Constant.RESET_COLOR);
        this.d_gameEngine.setGameEngineLog(
            "Invalid command! Try command -> editmap sample.map ",
            "effect");
        return;
      }
      String l_mapFileName = p_args[1];
      MapHelper l_mapHelper = new MapHelper();
      l_mapHelper.editMap(p_gameEngine, p_gameState, l_mapFileName);
      System.out.println("Editing for Map: " + l_mapFileName + "\n");
      System.out.println("See the selected map using " + Constant.SUCCESS_COLOR
          + "showmap"
          + Constant.RESET_COLOR);
      System.out.println("Edit continent using " + Constant.SUCCESS_COLOR
          + "editcontinent -add <continentId> <continentValue> or editcontinent -remove <continentId>"
          + Constant.RESET_COLOR);
      System.out.println("Edit country using " + Constant.SUCCESS_COLOR
          + "editcountry -add <countryId> <continentId> or editcountry -remove <countryId>"
          + Constant.RESET_COLOR);
      System.out.println("Edit neighbor using " + Constant.SUCCESS_COLOR
          + "editneighbor -add <countryId> <neighborCountryId> or editneighbor -remove <countryId> <neighborCountryId>"
          + Constant.RESET_COLOR + "\n");

    } catch (ArrayIndexOutOfBoundsException e) {
      System.out
          .println(Constant.ERROR_COLOR
              + "Invalid command! Try command -> editmap sample.map"
              + Constant.RESET_COLOR);
      this.d_gameEngine.setGameEngineLog(
          "Invalid command! Try command -> editmap sample.map ",
          "effect");
    }
  }

  /**
   * {@inheritDoc}
   */
  public void editContinent(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    if (!this.d_gameState.isGameMapLoaded()) {
      System.out.println(
          Constant.ERROR_COLOR
              + "Can not edit continent, please perform `editmap` first"
              + Constant.RESET_COLOR);
      this.d_gameEngine.setGameEngineLog(
          "Can not edit continent, please perform `editmap` first",
          "effect");
      System.out.println(
          Constant.ERROR_COLOR
              + "Try command -> editmap sample.map"
              + Constant.RESET_COLOR);
      return;
    }
    Continent l_continent = new Continent();
    l_continent.editContinent(p_gameEngine, p_gameState, p_args);
  }

  /**
   * {@inheritDoc}
   */
  public void saveMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    try {
      if (!this.d_gameState.isGameMapLoaded()) {
        System.out.println(
            Constant.ERROR_COLOR
                + "Map is not selected yet, please perform `editmap` first"
                + Constant.RESET_COLOR);
        System.out.println(
            Constant.ERROR_COLOR
                + "Try command -> editmap sample.map"
                + Constant.RESET_COLOR);
        this.d_gameEngine.setGameEngineLog("Map is not selected yet, please perform `editmap` first",
            "effect");
        return;
      } else if (!Util.isValidCommandArgument(p_args, 2)) {
        System.out.println(Constant.ERROR_COLOR
            + "Invalid command! Try command -> savemap sample.map"
            + Constant.RESET_COLOR);
        this.d_gameEngine.setGameEngineLog(
            "Invalid command! Try command -> savemap sample.map",
            "effect");
        return;
      }
      System.out.println(
          "Please type the map format to save the map: \"domination\" or \"conquest\"");
      Scanner l_scanner = new Scanner(System.in);
      String l_mapType = l_scanner.nextLine();
      MapAdapter l_mapAdapter = null;
      if (l_mapType.equals("domination")) {
        l_mapAdapter = new DominationMap();
      } else if (l_mapType.equals("conquest")) {
        l_mapAdapter = new ConquestMap();
      } else {
        System.out.println(Constant.ERROR_COLOR
            + "Invalid map format. Choose either \"domination\" or \"conquest\""
            + Constant.RESET_COLOR);
        return;
      }
      boolean l_isMapSaved = l_mapAdapter.saveMap(p_gameState, p_args[1]);
      if (l_isMapSaved) {
        this.d_gameEngine.setGameEngineLog("File have been saved.", "effect");
        System.out.println(
            Constant.SUCCESS_COLOR + "Map saved successfully"
                + Constant.RESET_COLOR);
      } else
        System.out.println(
            Constant.ERROR_COLOR + "Error while saving - invalid map"
                + Constant.RESET_COLOR);
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(Constant.ERROR_COLOR +
          "Invalid command - It should be of the form(without extension) savemap filename"
          + Constant.RESET_COLOR);
      this.d_gameEngine.setGameEngineLog(
          "Invalid command - It should be of the form(without extension) savemap filename",
          "effect");
    } catch (Exception e) {
      System.out.println(Constant.ERROR_COLOR +
          "Invalid command - It should be of the form(without extension) savemap filename"
          + Constant.RESET_COLOR);
      this.d_gameEngine.setGameEngineLog(
          "Invalid command - It should be of the form(without extension) savemap filename",
          "effect");
    }
  }

  /**
   * {@inheritDoc}
   */
  public void loadMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    try {
      if (!Util.isValidCommandArgument(p_args, 2)) {
        System.out.println(Constant.ERROR_COLOR
            + "Invalid command! Try command -> loadmap <mapName>"
            + Constant.RESET_COLOR);
        this.d_gameEngine.setGameEngineLog(
            "Invalid command! Try command -> loadmap <mapName>",
            "effect");
        return;
      }
      String l_mapFileName = p_args[1];
      MapHelper l_gameMapHelper = new MapHelper();
      l_gameMapHelper.loadMap(p_gameEngine, p_gameState, l_mapFileName);
    } catch (Exception e) {
      System.out.println(Constant.ERROR_COLOR
          + "Invalid command! Try command -> loadmap <mapName>"
          + Constant.RESET_COLOR);
      this.d_gameEngine.setGameEngineLog(
          "Invalid command! Try command -> loadmap <mapName>",
          "effect");
    }
  }

  /**
   * {@inheritDoc}
   */
  public void validateMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    try {
      if (!this.d_gameState.isGameMapLoaded()) {
        this.d_gameEngine.setGameEngineLog(
            "No map found to validate, please `loadmap` & `editmap` first",
            "effect");
        System.out.println(
            Constant.ERROR_COLOR
                + "Map is not found to validate,please `loadmap` & `editmap` first"
                + Constant.RESET_COLOR);
        System.out.println(
            Constant.ERROR_COLOR
                + "Try command -> editmap sample.map or loadmap sample.map"
                + Constant.RESET_COLOR);
        return;
      } else if (!Util.isValidCommandArgument(p_args, 1)) {
        this.d_gameEngine.setGameEngineLog(
            "Invalid number of arguments for validatemap command",
            "effect");
        System.out.println(Constant.ERROR_COLOR
            + "Invalid number of arguments for validatemap command"
            + Constant.RESET_COLOR);
        System.out.println(
            Constant.ERROR_COLOR
                + "Try command -> validatemap"
                + Constant.RESET_COLOR);
        return;
      }
      MapValidator l_mapValidator = new MapValidator();
      if (l_mapValidator.isValidMap(this.d_gameState.getGameMap())) {
        this.d_gameEngine.setGameEngineLog(
            "Map is validated successfully!",
            "effect");
        System.out.println(
            Constant.SUCCESS_COLOR + "Map is validated successfully!"
                + Constant.RESET_COLOR);
      }
    } catch (Exception e) {
      this.d_gameEngine.setGameEngineLog(
          "Invalid Map",
          "effect");
      System.out.println(Constant.ERROR_COLOR + "Invalid Map!"
          + Constant.RESET_COLOR);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void editCountry(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    if (!this.d_gameState.isGameMapLoaded()) {
      this.d_gameEngine.setGameEngineLog("Cannot edit country, please perform `editmap` first", "effect");
      System.out.println(
          Constant.ERROR_COLOR
              + "Cannot edit country, please perform `editmap` first"
              + Constant.RESET_COLOR);
      System.out.println(
          Constant.ERROR_COLOR
              + "Try command -> editmap sample.map"
              + Constant.RESET_COLOR);
      return;
    }
    Continent continent = new Continent();
    continent.editCountry(p_gameEngine, p_gameState, p_args);
  }

  /**
   * {@inheritDoc}
   */
  public void editNeighbor(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    if (!this.d_gameState.isGameMapLoaded()) {
      this.d_gameEngine.setGameEngineLog("Cannot edit neighbors, please perform `editmap` first",
          "effect");
      System.out.println(
          Constant.ERROR_COLOR
              + "Cannot edit neighbors, please perform `editmap` first"
              + Constant.RESET_COLOR);
      System.out.println(
          Constant.ERROR_COLOR
              + "Try command -> editmap sample.map"
              + Constant.RESET_COLOR);
      return;
    }
    Continent l_continent = new Continent();
    l_continent.editNeighborCountry(p_gameEngine, p_gameState, p_args);
  }

  /**
   * {@inheritDoc}
   */
  public void managePlayer(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    try {
      if (!this.d_gameState.isGameMapLoaded()) {
        this.d_gameEngine.setGameEngineLog(
            "No map found, please execute `editmap` or `loadmap` before adding game players",
            "effect");
        System.out.println(
            Constant.ERROR_COLOR
                + "No map found, please execute `editmap` or `loadmap` before adding game players"
                + Constant.RESET_COLOR);
        System.out.println(
            Constant.ERROR_COLOR
                + "Try command -> editmap sample.map or loadmap sample.map"
                + Constant.RESET_COLOR);
        return;
      } else if ((p_args[1] == "-add" && !Util.isValidCommandArgument(p_args, 3)
          || (p_args[1] == "-remove" && !Util.isValidCommandArgument(p_args, 3)))) {
        this.d_gameEngine.setGameEngineLog("Invalid number of arguments for gameplayer command", "effect");
        System.out.println(Constant.ERROR_COLOR
            + "Invalid number of arguments for gameplayer command" + Constant.RESET_COLOR);
        System.out.println(Constant.ERROR_COLOR
            + "Try command -> gameplayer -add <player_name> <player_stretagy>" + Constant.RESET_COLOR);
        return;
      }
      Player l_player = new Player(p_args[2]);
      l_player.managePlayer(p_gameEngine, p_gameState, p_args);
    } catch (Exception e) {
      this.d_gameEngine.setGameEngineLog(
          "Invalid command! Try command -> gameplayer -add <player_name> <player_strength>",
          "effect");
      System.out.println(Constant.ERROR_COLOR
          + "Invalid command! Try command -> gameplayer -add <player_name> <player_strength>"
          + Constant.RESET_COLOR);
    }

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
      if (this.d_gameEngine.getCurrentGamePhase() instanceof IssueOrderPhase) {
        this.d_gameEngine.setIssueOrderPhase();
        return;
      }
    } catch (Exception e) {
      this.d_gameEngine.setGameEngineLog("Error while loading the game: " + e.getMessage(), "effect");
      System.out
          .println(Constant.ERROR_COLOR + "Error while loading the game: " + e.getMessage() + Constant.RESET_COLOR);
      // e.printStackTrace();
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
      this.d_gameEngine.setGameState(p_gameState);
      FileOutputStream l_gameOutputFile = new FileOutputStream(
          Constant.GAME_PATH + p_args[1]);
      ObjectOutputStream l_gameOutputStream = new ObjectOutputStream(l_gameOutputFile);
      l_gameOutputStream.writeObject(p_gameEngine);
      l_gameOutputStream.flush();
      l_gameOutputStream.close();
      this.d_gameEngine.setGameEngineLog("Game saved successfully!", "effect");
      System.out.println(Constant.SUCCESS_COLOR + "Game saved successfully!" + Constant.RESET_COLOR);
    } catch (Exception e) {
      this.d_gameEngine.setGameEngineLog("Error while saving game: " + e.getMessage(), "effect");
      System.out
          .println(Constant.ERROR_COLOR + "Error while saving game " + e.getMessage() + Constant.RESET_COLOR);
      // e.printStackTrace();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void initPhase() {
    // System.out.println("\nWelcome to RiskGame!\n");
    System.out.println("You can start by creating/editing existing map or loading existing map.\n");
    this.showAvailableMap();
    System.out.println(
        "To create/edit map, use " + Constant.SUCCESS_COLOR + "editmap <map_name>"
            + Constant.RESET_COLOR
            + " command. e.x. " + Constant.SUCCESS_COLOR + "editmap sample.map"
            + Constant.RESET_COLOR);
    System.out.println("To load existing map, use " + Constant.SUCCESS_COLOR + "loadmap <map_name>"
        + Constant.RESET_COLOR + " command. e.x. " + Constant.SUCCESS_COLOR
        + "loadmap sample.map"
        + Constant.RESET_COLOR);
    System.out.println("To load existing game, use " + Constant.SUCCESS_COLOR + "loadgame <game_name>"
        + Constant.RESET_COLOR + " command. e.x. " + Constant.SUCCESS_COLOR
        + "loadgame sample.game"
        + Constant.RESET_COLOR);
    System.out
        .println("To exit, use " + Constant.SUCCESS_COLOR + "exit" + Constant.RESET_COLOR
            + " to quit\n");
    this.showAvailableGame();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    while (this.d_gameEngine.getCurrentGamePhase() instanceof StartUpPhase) {
      try {
        String l_command = reader.readLine();
        this.handleCommand(l_command);
      } catch (Exception e) {
        this.d_gameEngine.setGameEngineLog(e.getMessage(), "effect");
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void assignCountries(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    if (!this.d_gameState.isGameMapLoaded()) {
      this.d_gameEngine.setGameEngineLog(
          "No map found, please execute `editmap` or `loadmap` before assigning countries",
          "effect");
      System.out.println(Constant.ERROR_COLOR
          + "No map found, please execute `editmap` or `loadmap` before assigning countries"
          + Constant.RESET_COLOR);
      System.out.println(Constant.ERROR_COLOR
          + "Try command -> editmap sample.map or loadmap sample.map"
          + Constant.RESET_COLOR);
      return;
    } else if (this.d_gameState.getPlayerList().size() < 2) {
      this.d_gameEngine.setGameEngineLog("At least 2 players are required to play the game", "effect");
      System.out.println(Constant.ERROR_COLOR
          + "At least 2 players are required to play the game" + Constant.RESET_COLOR);
      System.out.println(Constant.ERROR_COLOR
          + "Try command -> gameplayer -add <player_name>" + Constant.RESET_COLOR);
      return;
    } else if (!Util.isValidCommandArgument(p_args, 1)) {
      this.d_gameEngine.setGameEngineLog(
          "Invalid number of arguments for assigncountries command",
          "effect");
      System.out.println(Constant.ERROR_COLOR
          + "Invalid number of arguments for assigncountries command"
          + Constant.RESET_COLOR);
      System.out.println(Constant.ERROR_COLOR
          + "Try -> assigncountries" + Constant.RESET_COLOR);
      return;
    }
    boolean l_isCountryAssigned = Player.assignCountries(p_gameEngine, p_gameState);
    if (l_isCountryAssigned) {
      this.d_gameEngine.setIssueOrderPhase();
    }
  }

  /**
   * Lists the names of saved game file in the specified folder.
   *
   */
  public void showAvailableGame() {
    File folder = new File(Constant.GAME_PATH);

    if (folder.isDirectory()) {
      File[] files = folder.listFiles();

      if (files != null) {
        // Find the maximum length of file names for formatting
        int maxLength = 0;
        for (File file : files) {
          if (file.isFile()) {
            int length = file.getName().length();
            if (length > maxLength) {
              maxLength = length;
            }
          }
        }

        System.out.printf("%85s\n",
            "-------------------------------------------------------------------------------------------");
        System.out.printf("%55s\n", "Saved Games");

        System.out.printf("%85s\n",
            "-------------------------------------------------------------------------------------------");

        // Print file names with even distribution
        for (File file : files) {
          if (file.isFile()) {
            String fileName = file.getName();
            System.out.println(fileName);
          }
        }
        System.out.println("\n");
      } else {
        System.out.println("No saved game found in the directory.");
      }
    } else {
      System.out.println("Specified path is not a directory.");
    }
  }

  /**
   * Lists the names of files in the specified folder.
   *
   */
  public void showAvailableMap() {
    File folder = new File(Constant.MAP_PATH);

    if (folder.isDirectory()) {
      File[] files = folder.listFiles();

      if (files != null) {
        // Find the maximum length of file names for formatting
        int maxLength = 0;
        for (File file : files) {
          if (file.isFile()) {
            int length = file.getName().length();
            if (length > maxLength) {
              maxLength = length;
            }
          }
        }

        System.out.printf("%85s\n",
            "-------------------------------------------------------------------------------------------");
        System.out.printf("%55s\n", "Existing Maps");

        System.out.printf("%85s\n",
            "-------------------------------------------------------------------------------------------");

        // Print file names with even distribution
        for (File file : files) {
          if (file.isFile()) {
            String fileName = file.getName();
            System.out.println(fileName);
          }
        }
        System.out.println("\n");
      } else {
        System.out.println("No files found in the directory.");
      }
    } else {
      System.out.println("Specified path is not a directory.");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void advance(GameState p_gameState, Player p_player, String[] l_data) {
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
  public void execute(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
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
}
