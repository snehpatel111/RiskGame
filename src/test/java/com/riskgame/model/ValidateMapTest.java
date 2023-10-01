package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for map validation.
 */
public class TestValidateMap {
    RunGameEngine d_Rgame;
    GameMap d_Map;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_rGame = new RunGameEngine();
        d_map = new GameMap("ameroki.map");
    }

    /**
     * Test if map is validated correctly.
     */
    @Test
    public void testValidateMap(){
        d_map = d_rGame.editMap("ameroki.map");
        boolean l_check = d_rGame.validateMap(d_map);
        assertTrue(l_check);
    }

    /**
     * Test to edit the map which is not existing and build it from scratch.
     */
    @Test
    public void testEditNewMap(){
        String d_mapName= "practice.map";
        d_map = d_rGame.editMap(d_mapName);
        assertEquals(d_map.getMapName(), d_mapName);
    }

    /**
     * Test to edit the map which exists in the folder.
     */
    @Test
    public void testEditExistingMap(){
        String d_mapName= "dummy.map";
        d_map = d_rGame.editMap(d_mapName);
        assertEquals(d_map.getMapName(), d_MapName);
    }
}
