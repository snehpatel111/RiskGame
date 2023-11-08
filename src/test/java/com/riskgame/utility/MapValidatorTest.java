package com.riskgame.utility;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;
import com.riskgame.model.GameMap;
import com.riskgame.model.GameState;
import com.riskgame.model.MapHelper;

import com.riskgame.utility.MapValidator;

public class MapValidatorTest {

    GameMap d_gameMap;
    GameMap d_invalidGameMap;
    GameMap d_map;
    String d_gameMapName;
    MapValidator d_mapValidator;
    MapHelper d_mapHelper;
    GameEngine d_gameEngine;
    GameState d_gameState;

    @Before
    public void before() {
        this.d_gameMap = new GameMap("ameroki.map");
        this.d_invalidGameMap = new GameMap("invalidMapOne.map");
        this.d_gameMapName = d_gameMap.getMapName();
        this.d_mapValidator = new MapValidator();
        this.d_mapHelper = new MapHelper();
        this.d_gameEngine = new GameEngine();
        this.d_gameState = new GameState();
    }

    /**
     * to test the valid case of map name
     */
    @Test
    public void testValidMapName() {

        assertTrue(this.d_mapValidator.isValidMapName(d_gameMapName));

    }

    /**
     * to test the Invalid case of map name
     */
    @Test
    public void testInvalidMapName() {
        this.d_gameMapName = "11223hello.map";
        assertFalse(this.d_mapValidator.isValidMapName(this.d_gameMapName));

    }

    /**
     * Test if map is validated correctly.
     */
    @Test
    public void validateMapTest() {
        this.d_mapHelper.editMap(this.d_gameEngine, this.d_gameState, "ameroki.map");
        boolean l_check = this.d_mapValidator.isValidMap(this.d_gameState.getGameMap());
        assertTrue(l_check);
    }

    /**
     * Test the Invalid case of the game map
     */
    @Test
    public void testMapInValidity() {
        boolean l_invalidMap = this.d_mapValidator.isValidMap(this.d_invalidGameMap);
        assertFalse(l_invalidMap);
    }
}
