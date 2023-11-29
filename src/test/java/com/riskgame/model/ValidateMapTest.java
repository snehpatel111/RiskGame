package com.riskgame.model;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;
import com.riskgame.utility.MapValidator;

import static org.junit.Assert.*;

/**
 * Tests if map is validated or not
 */
public class ValidateMapTest {

    MapValidator d_mapValidator;
    MapHelper d_mapHelper;
    GameEngine d_gameEngine;
    GameState d_gameState;

    /**
     * Set up the context
     */
    @Before
    public void before(){

        this.d_mapValidator = new MapValidator();
        this.d_mapHelper = new MapHelper();
        this.d_gameEngine = new GameEngine();
        this.d_gameState = new GameState();
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
     * Test if map is validated correctly.
     */
    @Test
    public void inValidateMapTest() {
        this.d_mapHelper.editMap(this.d_gameEngine, this.d_gameState, "invalidMapOne.map");
        boolean l_check = this.d_mapValidator.isValidMap(this.d_gameState.getGameMap());
        assertFalse(l_check);
    }
}
