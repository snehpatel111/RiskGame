import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestEditMap {

    RunGameEngine runGameEngine;
    GameMap gameMap;
    String mapName;

    /**
     * Set up the context
     */
    @Before
    public void before() {
        runGameEngine = new RunGameEngine();
    }

    /**
     * Test to edit a new map which doesn't exist and build it from scratch.
     */
    @Test
    public void testEditNewMap() {
        mapName = "practice.map";
        gameMap = runGameEngine.editMap(mapName);
        assertEquals(gameMap.getMapName(), mapName);
        // Add additional assertions to check the initial state of the map if needed
    }

    /**
     * Test to edit an existing map.
     */
    @Test
    public void testEditExistingMap() {
        mapName = "dummy.map";
        gameMap = runGameEngine.editMap(mapName);
        assertEquals(gameMap.getMapName(), mapName);
    }

    /**
     * Test editing a map that is null (non-existent).
     */
    @Test
    public void testEditNullMap() {
        mapName = "nonexistent.map";
        gameMap = runGameEngine.editMap(mapName);
        assertNull(gameMap);
    }

    /**
     * Test editing a map with invalid format (malformed map).
     */
    @Test
    public void testEditInvalidMapFormat() {
        mapName = "invalid.map";
        gameMap = runGameEngine.editMap(mapName);
        assertNull(gameMap);
    }

    /**
     * Test editing a map that is read-only (e.g., a system map).
     */
    @Test
    public void testEditReadOnlyMap() {
        mapName = "readonly.map";
        gameMap = runGameEngine.editMap(mapName);
        assertNull(gameMap);
    }
}
