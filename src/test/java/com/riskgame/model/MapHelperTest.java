package com.riskgame.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapHelperTest {

    MapHelper d_mapHelper;
    GameMap d_gameMap;
    String d_mapName;

    @Before
    public void before() {
        d_mapHelper = new MapHelper();
    }

    /**
     * Test to load the map which is not existing and build it from scratch.
     */
    @Test
    public void testLoadMap() {
        d_mapName = "world.map";
        d_gameMap = d_mapHelper.loadMap(d_mapName);
        assertEquals(d_gameMap.getMapName(), d_mapName);
    }
}
