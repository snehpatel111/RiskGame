package com.riskgame.model;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;

import static org.junit.Assert.*;

public class MapHelperTest {

    MapHelper d_mapHelper;
    GameMap d_gameMap;
    String d_gameMapName;
    GameEngine d_gameEngine;
    GameState d_gameState;

    /**
     * Setup context
     */
    @Before
    public void before() {
        this.d_mapHelper = new MapHelper();
        this.d_gameState = new GameState();
        this.d_gameEngine = new GameEngine();
        this.d_gameMap = new GameMap("ameroki.map");
    }

    /**
     * Test to load the map which is not existing and build it from scratch.
     */
    @Test
    public void testLoadMap() {
        this.d_gameMapName = "world.map";
        this.d_mapHelper.loadMap(this.d_gameEngine, this.d_gameState, this.d_gameMapName);
        assertEquals(this.d_mapHelper.d_gameMap.getMapName(), this.d_gameMapName);
    }
}
