package com.riskgame.utility;

import static org.junit.Assert.*;
// import static org.junit.Assert.assertFalse;
// import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import com.riskgame.model.GameMap;
import com.riskgame.model.MapHelper;

public class MapValidatorTest {

    GameMap d_gameMap;
    GameMap d_invalidGameMap;
    GameMap d_map;
    String d_gameMapName;
    MapValidator d_mapValidator;
    MapHelper d_mapHelper;

    @Before
    public void before() {
        this.d_gameMap = new GameMap("ameroki.map");
        this.d_invalidGameMap = new GameMap("invalidMapOne.map");
        this.d_gameMapName = d_gameMap.getMapName();
        this.d_mapValidator = new MapValidator();
        this.d_mapHelper = new MapHelper();
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
        this.d_map = this.d_mapHelper.editMap("ameroki.map");
        boolean l_check = this.d_mapValidator.isValidMap(this.d_map);
        assertTrue(l_check);
    }

    /**
     * to test the Invalid case of the game map
     */
    @Test
    public void testMapInValidity() {
        boolean l_invalidMap = this.d_mapValidator.isValidMap(this.d_invalidGameMap);
        assertFalse(l_invalidMap);

    }

}
