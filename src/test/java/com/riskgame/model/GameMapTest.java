package com.riskgame.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameMapTest {

    MapHelper d_mapHelper;
    GameMap d_gameMap;
    String d_mapName;

    /**
     * Set up the context
     */
    @Before
    public void before() {
        this.d_mapHelper = new MapHelper();
    }

    /**
     * Test to edit a new map which doesn't exist and build it from scratch.
     */
    @Test
    public void testEditNewMap() {
        this.d_mapName = "practice.map";
        this.d_gameMap = this.d_mapHelper.editMap(this.d_mapName);
        assertEquals(this.d_gameMap.getMapName(), this.d_mapName);
        // Add additional assertions to check the initial state of the map if needed
    }

    /**
     * Test to edit an existing map.
     */
    @Test
    public void testEditExistingMap() {
        this.d_mapName = "dummy.map";
        this.d_gameMap = this.d_mapHelper.editMap(this.d_mapName);
        assertEquals(this.d_gameMap.getMapName(), this.d_mapName);
    }

    /**
     * Test editing a map that is null (non-existent).
     */
    @Test
    public void testEditNullMap() {
        this.d_mapName = "nonexistent.map";
        this.d_gameMap = this.d_mapHelper.editMap(this.d_mapName);
        assertNull(this.d_gameMap);
    }

    /**
     * Test editing a map with invalid format (malformed map).
     */
    @Test
    public void testEditInvalidMapFormat() {
        this.d_mapName = "invalid.map";
        this.d_gameMap = this.d_mapHelper.editMap(this.d_mapName);
        assertNull(this.d_gameMap);
    }

    /**
     * Test editing a map that is read-only (e.g., a system map).
     */
    @Test
    public void testEditReadOnlyMap() {
        this.d_mapName = "readonly.map";
        this.d_gameMap = this.d_mapHelper.editMap(this.d_mapName);
        assertNull(this.d_gameMap);
    }
}
