
package com.riskgame.model;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class DominationMapTest {

    @Test
    public void testReadMap() {
        DominationMap dominationMap = new DominationMap();
        GameState gameState = new GameState();
        GameMap gameMap = dominationMap.readMap("valid_map_file.map", gameState);
        
        assertNotNull(gameMap);
    }

    @Test
    public void testSaveMapInvalidMapName() {
        DominationMap dominationMap = new DominationMap();
        GameState gameState = new GameState();
        // Initialize gameState and DominationMap as needed

        assertFalse(dominationMap.saveMap(gameState, "invalid map name"));
    }

    @Test
    public void testSaveMapInvalidMap() {
        DominationMap dominationMap = new DominationMap();
        GameState gameState = new GameState();
        // Initialize gameState and DominationMap with an invalid map as needed

        assertFalse(dominationMap.saveMap(gameState, "test_save_map_invalid"));
    }
}
