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

    /**
     * Set up the context
     */
    private GameEngine d_gameEngine;
    private GameState d_gameState;
    private StartUpPhase d_startUpPhase;
    private IssueOrderPhase d_issueOrderPhase;

    @Before
    public void setUp() {
        d_gameEngine = new GameEngine(); // Instantiate GameEngine
        d_gameState = new GameState(); // Instantiate GameState
        d_startUpPhase = new StartUpPhase(d_gameEngine, d_gameState);
        d_issueOrderPhase = new IssueOrderPhase(d_gameEngine, d_gameState);
    }

    /**
     * Validates the StartUpPhase.
     */
    @Test
    public void validateStartUpPhase() {
        d_gameState.setIsGameMapLoaded();
        String[] l_args = "editmap world.map".split(" ");
        this.d_startUpPhase.editMap(this.d_gameEngine, this.d_gameState, l_args);
        assertTrue(this.d_gameEngine.getCurrentGamePhase() instanceof StartUpPhase);
    }

    /**
     * Validates the Issue Order Phase.
     */
    @Test
    public void validateIssueOrderPhase() {
        d_gameState.setIsGameMapLoaded();
        String[] l_args = "assigncountries 12".split(" ");
        this.d_issueOrderPhase.assignCountries(d_gameEngine, d_gameState, l_args);
        assertFalse(this.d_gameEngine.getCurrentGamePhase() instanceof IssueOrderPhase);
    }

    /**
     * Validates the output of the showmap method when the game map is not loaded.
     */
    @Test
    public void testShowMapWhenGameMapNotLoaded() {
        this.d_gameState.setIsGameMapLoaded();

        String capturedOutput = captureShowMapOutput("");

        assertFalse(capturedOutput.contains("Cannot show  map, please perform `editmap` or `loadmap` first"));
    }

    /**
     * Validates the output of the showmap method when the command is invalid.
     */
    @Test
    public void testShowMapWithInvalidCommandArgument() {
        this.d_gameState.setIsGameMapLoaded();

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

        this.d_startUpPhase.showMap(this.d_gameEngine, this.d_gameState, command.split(" "));
        System.setOut(originalOut);

        return outputStream.toString();
    }

    /**
     * Test the behavior of editMap method when the map is not loaded.
     */
    @Test
    public void testEditMapWhenMapNotLoaded() {
        assertFalse(d_gameState.isGameMapLoaded());

        d_startUpPhase.editMap(d_gameEngine, d_gameState, null);

        assertFalse(d_gameState.isGameMapLoaded());
    }

    /**
     * Test the behavior of editMap method when the map is already loaded.
     */
    @Test
    public void testEditMapWhenMapLoaded() {
        d_gameState.setIsGameMapLoaded();

        d_startUpPhase.editMap(d_gameEngine, d_gameState, null);

        assertTrue(d_gameState.isGameMapLoaded());
    }

    /**
     * Test case for edit continent when the map is not loaded
     */
    @Test
    public void testEditContinentWhenMapNotLoaded() {
        assertFalse(d_gameState.isGameMapLoaded());

        d_startUpPhase.editContinent(d_gameEngine, d_gameState, null);

        assertFalse(d_gameState.isGameMapLoaded());
    }

    /**
     * Test case for edit continent when the map is loaded
     */
    @Test
    public void testEditContinentWhenMapLoaded() {
        d_gameState.setIsGameMapLoaded();

        d_startUpPhase.editContinent(d_gameEngine, d_gameState, null);

        assertTrue(d_gameState.isGameMapLoaded());
    }

    /**
     * Test case to save the map when the map is not loaded.
     */
    @Test
    public void testSaveMapWhenMapNotLoaded() {
        assertFalse(d_gameState.isGameMapLoaded());

        this.d_startUpPhase.saveMap(this.d_gameEngine, this.d_gameState, new String[] { "savemap", "sample.map" });

        assertNull(this.d_gameState.getError());
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
