package com.riskgame.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapHelperTest {

    MapHelper d_mapHelper;
    GameMap d_gameMap;
    String d_gameMapName;

    /**
     * Setup context
     */
    @Before
    public void before() {
        this.d_mapHelper = new MapHelper();
    }

    /**
     * Test to load the map which is not existing and build it from scratch.
     */
    @Test
    public void testLoadMap() {
        this.d_gameMapName = "world.map";
        this.d_gameMap = this.d_mapHelper.loadMap(this.d_gameMapName);
        assertEquals(d_gameMap.getMapName(), this.d_gameMapName);
    }
}
