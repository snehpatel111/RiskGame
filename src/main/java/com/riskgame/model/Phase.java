package com.riskgame.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.riskgame.controller.GameEngine;
import com.riskgame.model.GameMap;

import com.riskgame.model.Player;

import com.riskgame.utility.Constant;

import com.riskgame.model.GameState;

/**
 * This abstract class enforces the method requirements for each game phase.
 */
public abstract class Phase {
    /**
     * Stores the information about the current game state.
     */
    GameState d_gameState;

    /**
     * Stores the information about the current game engine.
     */
    protected GameEngine d_gameEngine;

    /**
     * MapService instance is used to handle loading, reading, parsing, editing, and
     * saving map files.
     */
    // MapService mapService = new MapService();

    /**
     * List of players in the game.
     */
    public ArrayList<Player> d_playerList;

    /**
     * Constructor to initialize the current game engine.
     *
     * @param p_gameEngine the game engine instance to update the state
     * @param p_gameState  the game state instance
     */
    public Phase(GameEngine p_gameEngine, GameState p_gameState) {
        this.d_gameEngine = p_gameEngine;
        this.d_gameState = p_gameState;
    }

    /**
     * Gets the current game state.
     *
     * @return the current game state
     */
    public GameState getGameState() {
        return this.d_gameState;
    }

    /**
     * Sets the current game state.
     *
     * @param p_gameState the game state instance to set for the phase
     */
    public void setGameState(GameState p_gameState) {
        this.d_gameState = p_gameState;
    }

    /**
     * Handles all state-specific commands that can be entered by the user.
     *
     * @param p_enteredCommand command entered by the user in CLI
     */
    public void handleCommand(String p_enteredCommand) {
        parseCommand(null, p_enteredCommand);
    }

    /**
     * Handles all state-specific commands that can be entered by the user.
     *
     * @param p_enteredCommand command entered by the user in CLI
     * @param p_player         an instance of Player Object
     */
    public void handleCommand(String p_enteredCommand, Player p_player) {
        parseCommand(p_player, p_enteredCommand);
    }

    /**
     * Processes the command entered by the user and redirects them to specific
     * phase implementations.
     *
     * @param p_command command entered by the user in CLI
     * @param p_player  an instance of Player Object
     */
    private void parseCommand(Player p_player, String p_command) {
        // Command l_commandName = new Command(p_command);
        // this.d_isMapLoaded = this.d_gameState.getMap() != null;

        String[] l_data = p_command.split("\\s+");
        String l_commandName = l_data[0];
        this.d_gameState.updateLog(l_commandName, "command");

        switch (l_commandName) {
            case "editmap":
                this.editMap(this.d_gameEngine, this.d_gameState, l_data);
                break;
            case "editcontinent":
                this.editContinent(this.d_gameEngine, this.d_gameState, l_data);
                break;
            case "savemap":
                this.saveMap(this.d_gameEngine, this.d_gameState, l_data);
                break;
            case "loadmap":
                this.loadMap(this.d_gameEngine, this.d_gameState, l_data);
                break;
            case "validatemap":
                validateMap(this.d_gameEngine, this.d_gameState, l_data);
                break;
            case "editcountry":
                this.editCountry(this.d_gameEngine, this.d_gameState, l_data);
                break;
            case "editneighbor":
                this.editNeighbor(this.d_gameEngine, this.d_gameState, l_data);
                break;
            case "gameplayer":
                this.managePlayer(this.d_gameEngine, this.d_gameState, l_data);
                break;
            case "assigncountries":
                this.assignCountries(this.d_gameEngine, this.d_gameState, l_data);
                break;
            case "showmap":
                this.showMap(this.d_gameEngine, this.d_gameState, l_data);
                break;
            case "deploy":
                this.deploy(p_player, l_data);
                break;
            // case "advance":
            // this.advance(this.d_gameEngine, this.d_gameState, l_data);
            // break;
            // case "airlift":
            // case "blockade":
            // case "negotiate":
            // case "bomb":
            // this.cardHandle(this.d_gameEngine, this.d_gameState, l_data);
            // break;
            case "exit":
                this.d_gameEngine.setGameEngineLog("Exit Command Entered, Game Ends!", "effect");
                System.exit(0);
                break;
            default:
                this.d_gameEngine.setGameEngineLog("Invalid Command", "effect");
                System.out.println(Constant.ERROR_COLOR
                        + "Invalid command!" + Constant.RESET_COLOR);
                break;
        }
    }

    /**
     * Handles the Card Commands.
     *
     * @param p_enteredCommand String of entered Command
     * @param p_player         player instance
     */
    // protected abstract void performCardHandle(String p_enteredCommand, Player
    // p_player) throws IOException;

    /**
     * @param p_gameEngine GameEngine object
     * @param p_gameState  GameState object containing current game state
     *                     state.
     * @param p_args       Command line arguments to edit continent.
     * 
     */
    protected abstract void showMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args);

    // /**
    // * this method handles the advance order in game play.
    // *
    // * @param p_command command entered by user
    // * @param p_player instance of player object
    // *
    // */
    // // protected abstract void performAdvance(String p_command, Player p_player)
    // // throws IOException;

    /**
     * This is the main method executed on phase change.
     */
    public abstract void initPhase();

    /**
     * This method handles the deploy order in gameplay.
     *
     * @param p_player Instance of Player object
     * @param p_args   Command line arguments to edit continent.
     * 
     */
    protected abstract void deploy(Player p_player, String[] p_args);

    /**
     * Method to Log and Print if the command can't be executed in current phase.
     */
    public void printInvalidCommandInState() {
        this.d_gameEngine.setGameEngineLog("Invalid command in current state", "effect");
        System.out
                .println(Constant.ERROR_COLOR + "Invalid command in current phase"
                        + Constant.RESET_COLOR);
    }

    /**
     * Randomly assigns countries to players from the game map and player list
     * 
     * @param p_gameEngine GameEngine object
     * @param p_gameState  GameState object containing current game state
     *                     state.
     * @param p_args       Command line arguments to edit continent.
     * 
     */
    protected abstract void assignCountries(GameEngine p_gameEngine, GameState p_gameState, String[] p_args);

    /**
     * Edit the neighbor country with given params.
     * 
     * @param p_gameEngine GameEngine object
     * @param p_gameState  GameState object containing current game state
     *                     state.
     * @param p_args       Command line arguments to edit continent.
     */
    protected abstract void editNeighbor(GameEngine p_gameEngine, GameState p_gameState, String[] p_args);

    /**
     * Edit the country with given params.
     * 
     * @param p_gameEngine GameEngine object
     * @param p_gameState  GameState object containing current game state
     *                     state.
     * @param p_args       Command line arguments to edit continent.
     */
    protected abstract void editCountry(GameEngine p_gameEngine, GameState p_gameState, String[] p_args);

    /**
     * Validates the game map.
     * 
     * @param p_gameEngine GameEngine object
     * @param p_gameState  GameState object containing current game state
     *                     state.
     * @param p_args       Command line arguments to edit continent.
     * @return Returns true if map is valid, otherwise false
     */
    protected abstract void validateMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args);

    /**
     * Edit an existing continent.
     * 
     * @param p_gameEngine GameEngine object
     * @param p_gameState  GameState object containing current game state
     *                     state.
     * @param p_args       Command line arguments to edit continent.
     */
    protected abstract void loadMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args);

    /**
     * Save current game map object into .map file
     * 
     * @param p_gameEngine  GameEngine object
     * @param p_gameState   GameState object containing current game state
     * @param p_mapFileName Name of the file
     */
    protected abstract void saveMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args);

    /**
     * Edit an existing continent.
     * 
     * @param p_gameEngine GameEngine object
     * @param p_gameState  GameState object containing current game state
     *                     state.
     * @param p_args       Command line arguments to edit continent.
     */
    protected abstract void editContinent(GameEngine p_gameEngine, GameState p_gameState, String[] p_args);

    /**
     * Edit the game map.
     *
     * @param p_gameEngine GameEngine object
     * @param p_gameState  GameState object containing current game state
     *                     state.
     * @param p_args       Command line arguments to edit continent.
     */
    protected abstract void editMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_mapFileName);

    /**
     * Manage the game player.
     *
     * @param p_gameEngine GameEngine object
     * @param p_gameState  GameState object containing current game state
     *                     state.
     * @param p_args       Command line arguments to edit continent.
     */
    protected abstract void managePlayer(GameEngine p_gameEngine, GameState p_gameState, String[] p_mapFileName);
}
