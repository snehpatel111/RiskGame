package com.riskgame.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;

/**
 * Test class for GameMapTest class
 */
public class GameMapTest {

    MapHelper d_mapHelper;
    GameMap d_gameMap;
    String d_mapName;
    GameEngine d_gameEngine;
    GameState d_gameState;

    /**
     * Set up the context
     */
    @Before
    public void before() {
        this.d_mapHelper = new MapHelper();
        this.d_gameState = new GameState();
        this.d_gameEngine = new GameEngine();
        this.d_gameMap = new GameMap("ameroki.map");
    }

    /**
     * Test to edit a new map which doesn't exist and build it from scratch.
     */
    @Test
    public void testEditNewMap() {
        this.d_mapName = "ameroki.map";
        this.d_mapHelper.editMap(this.d_gameEngine, this.d_gameState, this.d_mapName);

        assertNotNull(this.d_gameMap);
        assertEquals(this.d_mapHelper.d_gameMap.getMapName(), this.d_mapName);
    }

    /**
     * Test to edit an existing map.
     */
    @Test
    public void testEditExistingMap() {
        this.d_mapName = "dummy.map";
        this.d_mapHelper.editMap(this.d_gameEngine, this.d_gameState, this.d_mapName);
        assertEquals(this.d_mapHelper.d_gameMap.getMapName(), this.d_mapName);
    }

    /**
     * Test editing a map that is null (non-existent).
     */
    @Test
    public void testEditNullMap() {
        this.d_mapName = "nonexistent.map";
        this.d_mapHelper.editMap(this.d_gameEngine, this.d_gameState, this.d_mapName);

        assertNotNull(this.d_gameMap);
        assertEquals(this.d_mapHelper.d_gameMap.getMapName(), this.d_mapName);
    }

    /**
     * Test setting the map name
     */
    @Test
    public void testSetMapName() {
        this.d_mapName = "new.map";
        this.d_gameMap.setMapName(d_mapName);
        assertEquals(d_mapName, this.d_gameMap.getMapName());
    }

    /**
     * Test getting the map name
     */
    @Test
    public void testGetMapName() {
        assertEquals("ameroki.map", this.d_gameMap.getMapName());
    }

}
