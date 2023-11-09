package com.riskgame.model;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;

/**
 * Test class for IssueOrderPhaseTest class
 */
public class IssueOrderPhaseTest {
    private IssueOrderPhase issueOrderPhase;
    private GameState gameState;
    private GameEngine gameEngine;

    @Before
    public void setUp() {
        gameEngine = new GameEngine();
        gameState = new GameState();
        issueOrderPhase = new IssueOrderPhase(gameEngine, gameState);
    }

    @Test
    public void testInitPhaseWithNoPlayers() {
        gameState.getPlayerList().clear();
        issueOrderPhase.initPhase();

        assertTrue(gameState.getPlayerList().isEmpty());
    }

    @Test
    public void testInitPhaseWithNoCountries() {
        gameState.getPlayerList().clear();
        issueOrderPhase.initPhase();

        assertTrue(gameState.getPlayerList().isEmpty());
    }

    @Test
    public void testShowMap() {

        issueOrderPhase.showMap(null, gameState, new String[] { "showMap" });

    }

    @Test
    public void testInitPhaseWithZeroReinforcements() {
        IssueOrderPhase issueOrderPhase = new IssueOrderPhase(gameEngine, gameState);
        Player player1 = new Player("Player1");
        player1.setOwnedArmyCount(0);
        gameState.getPlayerList().add(player1);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        issueOrderPhase.initPhase();

        String consoleOutput = output.toString().trim();
        assertTrue(consoleOutput.contains("Reinforcement assigned to each player!"));
        assertFalse(consoleOutput.contains("Player's turn"));

    }

    @Test
    public void testInitPhaseWithZeroTotalReinforcements() {
        Player player1 = new Player("Player1");
        player1.setOwnedArmyCount(0);
        gameState.getPlayerList().add(player1);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        issueOrderPhase.initPhase();

        String consoleOutput = output.toString().trim();
        assertTrue(consoleOutput.contains("Reinforcement assigned to each player!"));
        assertFalse(consoleOutput.contains("Player's turn"));
    }

}
