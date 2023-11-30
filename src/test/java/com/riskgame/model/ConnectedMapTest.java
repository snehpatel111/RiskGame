package com.riskgame.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;
import com.riskgame.utility.MapValidator;

/**
 * Class to test if the given map is connected or not
 *
 */
public class ConnectedMapTest {

    MapHelper d_mapHelper;
	MapValidator d_MapValidator;
    GameEngine d_gameEngine;
    GameState d_gameState;


    /**
     * Set up the context
     */
    @Before
    public void before(){

        this.d_mapHelper = new MapHelper();
        this.d_MapValidator = new MapValidator();
        this.d_gameEngine = new GameEngine();
        this.d_gameState = new GameState();
    }

    /**
     * Test if tests are rightly identified or not for the given map
     */
    @Test
    public void testDisConnectedMap(){
    	this.d_mapHelper.editMap(this.d_gameEngine, this.d_gameState, "invalidMapOne.map");
        boolean l_check = d_MapValidator.isGraphConnected(d_MapValidator.createGraph(d_gameState.getGameMap()));
        assertFalse(l_check);
    }

    /**
     * Test if tests are rightly identified or not for the given map
     */
    @Test
    public void testConnectedMap(){
    	this.d_mapHelper.editMap(this.d_gameEngine, this.d_gameState, "world.map");
        boolean l_check = d_MapValidator.isGraphConnected(d_MapValidator.createGraph(d_gameState.getGameMap()));
        assertEquals(true, l_check);
    }
}
