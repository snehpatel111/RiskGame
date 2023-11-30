package com.riskgame.model;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;

import static org.junit.Assert.*;

/**
 * Test class for MapHelperTest class
 */
public class MapHelperTest {

    private GameEngine d_gameEngine;
    private GameState d_gameState;
    private MapHelper d_mapHelper;

    @Before
    public void setUp() {
        d_gameEngine = new GameEngine();
        d_gameState = new GameState();
        d_mapHelper = new MapHelper();
    }

    @Test
    public void testEditMapExistingFile() {
        // Arrange
        String existingMapFileName = "dummy.map";

        // Act
        d_mapHelper.editMap(d_gameEngine, d_gameState, existingMapFileName);

        // Assert
        assertNotNull(d_gameState.getGameMap().getCountries());
        assertNotNull(d_gameState.getGameMap().getContinents());
    }

        @Test
    public void testEditMapNonExistingFile() {
        // Arrange
        String existingMapFileName = "existingMap.map";

        // Act
        d_mapHelper.editMap(d_gameEngine, d_gameState, existingMapFileName);

        // Assert
        assertEquals(0, d_gameState.getGameMap().getCountries().size());
        assertEquals(0, d_gameState.getGameMap().getContinents().size());
    }
}
