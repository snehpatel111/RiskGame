package com.riskgame.model;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import com.riskgame.controller.GameEngine;

/**
 * Unit test for Phase
 */
public class PhaseTest {

    GameEngine d_gameEngine;
    GameMap d_gameMap;
    ArrayList<Player> d_gamePlayers;
    Phase d_Phase;
    GameState d_gameState;

    /**
     * Set up the context
     */
    @Before
    public void setUp() {
        this.d_gameEngine = new GameEngine();
        this.d_gameMap = new GameMap("world.map");
        d_gamePlayers = new ArrayList<Player>();
        this.d_gameState = new GameState();
        // Initialize players and the game map as needed
        // Replace with the appropriate phase class
    }

    // @Test
    // public void testSetGameState() {
    // this.d_gameEngine.setIssueOrderPhase();
    // assertTrue(this.d_gameEngine.getCurrentGamePhase() instanceof
    // IssueOrderPhase);
    // }
}
