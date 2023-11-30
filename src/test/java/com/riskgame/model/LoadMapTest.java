package com.riskgame.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
/**
 * Test Class checks the loading of the map.
 */

import com.riskgame.controller.GameEngine;
public class LoadMapTest {

    GameEngine d_gameEngine;
    GameState d_gameState;
    MapHelper d_mapHelper;
    GameMap d_Map;
    String d_MapName;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_gameEngine = new GameEngine();
        d_gameState = new GameState();
        d_mapHelper = new MapHelper();
    }

    /**
     * Test to load the domination map 
     */
    @Test
    public void testDominationLoadMap(){
        d_MapName= "dummmy.map";
        d_mapHelper.editMap(d_gameEngine, d_gameState, d_MapName);
        assertEquals(d_mapHelper.d_gameMap.getMapName(), d_MapName);
    }
     

    /**
     * Test to load the conquest map
     */
    @Test
    public void testConquestLoadMap(){
        d_MapName= "Asiia.map";
        d_mapHelper.editMap(d_gameEngine, d_gameState, d_MapName);
        assertEquals(d_gameState.getGameMap().getMapName(), d_MapName);
    }

}
