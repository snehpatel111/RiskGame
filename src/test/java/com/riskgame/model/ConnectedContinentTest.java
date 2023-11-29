package com.riskgame.model;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;
import com.riskgame.utility.MapValidator;

import static org.junit.Assert.*;

/**
 * Tests if connected continent are being rightly identified or not.
 */
public class ConnectedContinentTest {
    MapValidator d_mapValidator;
    MapHelper d_mapHelper;
    GameEngine d_gameEngine;
    GameState d_gameState;

    /**
     * Set up the context
     */
    @Before
    public void before() {
        this.d_mapValidator = new MapValidator();
        this.d_mapHelper = new MapHelper();
        this.d_gameEngine = new GameEngine();
        this.d_gameState = new GameState();
    }

    /**
     * Test cases when Continent subgraphs are connected.
     */
    @Test
    public void testConnectedContinent() {
        this.d_mapHelper.editMap(this.d_gameEngine, this.d_gameState, "world.map");
        boolean l_check = d_mapValidator.isContinentConnected(d_gameState.getGameMap());
        assertTrue(l_check);

        //Continent with a single country is connected subgraph
        this.d_mapHelper.editMap(this.d_gameEngine, this.d_gameState, "createdMap.map");
        l_check = d_mapValidator.isContinentConnected(d_gameState.getGameMap());
        assertTrue(l_check);
    }

    /**
     * Tests case where a continent subgraph is not connected
     */
    @Test
    public void testUnConnectedContinent() {
        this.d_mapHelper.editMap(this.d_gameEngine, this.d_gameState, "unconnectedContinent.map");
        boolean l_check = d_mapValidator.isContinentConnected(d_gameState.getGameMap());
        assertEquals(false, l_check);
    }


}
