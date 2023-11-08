package com.riskgame.model;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;
import com.riskgame.model.Phase;
import com.riskgame.utility.Util;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Unit test for StartUpPhase.
 */
public class StartUpPhaseTest {

    private GameEngine gameEngine;
    private GameState gameState;
    private StartUpPhase startUpPhase;

    /**
     * Set up the context
     */
    @Before
    public void setUp() {
        gameEngine = new GameEngine(); // Instantiate GameEngine
        gameState = new GameState(); // Instantiate GameState
        startUpPhase = new StartUpPhase(gameEngine, gameState);
    }

    /**
     * Validates the output of the showmap method when the game map is not loaded.
     */
    @Test
    public void testShowMapWhenGameMapNotLoaded() {
        gameState.setIsGameMapLoaded();

        String capturedOutput = captureShowMapOutput("");

        assertFalse(capturedOutput.contains("Cannot show  map, please perform `editmap` or `loadmap` first"));
    }

    /**
     * Validates the output of the showmap method when the command is invalid.
     */
    @Test
    public void testShowMapWithInvalidCommandArgument() {
        gameState.setIsGameMapLoaded();

        String capturedOutput = captureShowMapOutput("showMap extraArgument");

        assertTrue(capturedOutput.contains("Invalid command! Try command -> showMap"));
    }

    /**
     * @param command Input command from the user
     * @return Output of the input command
     */
    private String captureShowMapOutput(String command) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        startUpPhase.showMap(gameEngine, gameState, command.split(" "));
        System.setOut(originalOut);

        return outputStream.toString();
    }

    /**
     * Test the behavior of editMap method when the map is not loaded.
     */
    @Test
    public void testEditMapWhenMapNotLoaded() {
        assertFalse(gameState.isGameMapLoaded());

        startUpPhase.editMap(gameEngine, gameState, null);

        assertFalse(gameState.isGameMapLoaded());
    }

    /**
     * Test the behavior of editMap method when the map is already loaded.
     */
    @Test
    public void testEditMapWhenMapLoaded() {
        gameState.setIsGameMapLoaded();

        startUpPhase.editMap(gameEngine, gameState, null);

        assertTrue(gameState.isGameMapLoaded());
    }

    /**
     * Test case for edit continent when the map is not loaded
     */
    @Test
    public void testEditContinentWhenMapNotLoaded() {
        assertFalse(gameState.isGameMapLoaded());

        startUpPhase.editContinent(gameEngine, gameState, null);

        assertFalse(gameState.isGameMapLoaded());
    }

    /**
     * Test case for edit continent when the map is loaded
     */
    @Test
    public void testEditContinentWhenMapLoaded() {
        gameState.setIsGameMapLoaded();

        startUpPhase.editContinent(gameEngine, gameState, null);

        assertTrue(gameState.isGameMapLoaded());
    }

    /**
     * Test case to save the map when the map is not loaded.
     */
    @Test
    public void testSaveMapWhenMapNotLoaded() {
        assertFalse(gameState.isGameMapLoaded());

        startUpPhase.saveMap(gameEngine, gameState, new String[] { "savemap", "sample.map" });

        assertNull(gameState.getError());
    }

    /**
     * Test case for saving the map with an invalid command.
     */
    @Test
    public void testSaveMapInvalidCommand() {
        GameEngine gameEngine = new GameEngine();
        GameState gameState = new GameState();
        String[] args = { "savemap" };

        StartUpPhase startUpPhase = new StartUpPhase(gameEngine, gameState);
        startUpPhase.saveMap(gameEngine, gameState, args);

        assertFalse(gameState.isGameMapLoaded());
    }

    /**
     * Test case to save the map when the map is not selected.
     */
    @Test
    public void testSaveMapMapNotSelected() {
        GameEngine gameEngine = new GameEngine();
        GameState gameState = new GameState();
        String[] args = { "savemap" };

        StartUpPhase startUpPhase = new StartUpPhase(gameEngine, gameState);
        startUpPhase.saveMap(gameEngine, gameState, args);

        assertFalse(gameState.isGameMapLoaded());
    }

    /**
     * Test valid arguments
     */
    @Test
    public void testValidArguments() {
        String[] args = { "arg1", "arg2", "arg3" };
        int expectedLength = 3;
        assertTrue(Util.isValidCommandArgument(args, expectedLength));
    }

    /**
     * Test null arguments
     */
    @Test
    public void testNullArguments() {
        String[] args = null;
        int expectedLength = 3;
        assertFalse(Util.isValidCommandArgument(args, expectedLength));
    }

    /**
     * Test invalid arguments
     */
    @Test
    public void testInvalidArguments() {
        String[] args = { "arg1", "arg2" };
        int expectedLength = 3;
        assertFalse(Util.isValidCommandArgument(args, expectedLength));
    }

    /**
     * Test empty arguments
     */
    @Test
    public void testEmptyArguments() {
        String[] args = {};
        int expectedLength = 0;
        assertTrue(Util.isValidCommandArgument(args, expectedLength));
    }
}
