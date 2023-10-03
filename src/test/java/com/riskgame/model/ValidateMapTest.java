package com.riskgame.model;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.utility.MapValidator;

import static org.junit.Assert.*;

/**
 * Tests for map validation.
 */
public class ValidateMapTest {
    MapValidator d_mapValidator;
    GameMap d_gameMap;
    MapHelper d_mapHelper;

    /**
     * Set up the context
     */
    @Before
    public void before() {
        this.d_mapValidator = new MapValidator();
        this.d_gameMap = new GameMap("ameroki.map");
    }

    /**
     * Test if map is validated correctly.
     */
    @Test
    public void validateMapTest() {
        this.d_gameMap = this.d_mapHelper.editMap("ameroki.map");
        boolean l_check = this.d_mapValidator.isValidMap(this.d_gameMap);
        assertTrue(l_check);
    }

    /**
     * Test to edit the map which is not existing and build it from scratch.
     */
    @Test
    public void testEditNewMap() {
        String l_mapName = "practice.map";
        this.d_gameMap = this.d_mapHelper.editMap(l_mapName);
        assertEquals(this.d_gameMap.getMapName(), l_mapName);
    }

    /**
     * Test to edit the map which exists in the folder.
     */
    @Test
    public void testEditExistingMap() {
        String l_mapName = "dummy.map";
        this.d_gameMap = this.d_mapHelper.editMap(l_mapName);
        assertEquals(this.d_gameMap.getMapName(), l_mapName);
    }
}
