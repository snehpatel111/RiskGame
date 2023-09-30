package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestEditMap {
	
	RunGameEngine d_RunGame;
	GameMap d_map;
	String d_mapName;
	
	/**
     * Set up the context
     */
    @Before
    public void before(){
        d_runGame = new RunGameEngine();
    }

    /**
     * Test to edit the map which is not existing and build it from scratch.
     */
    @Test
    public void testEditNewMap(){
    	d_mapName= "practice.map";
        d_map = d_runGame.editMap(d_mapName);
        assertEquals(d_map.getMapName(), d_mapName);
    }
    

    /**
     * Test editing an existing map and verify if it loads correctly.
     */
    @Test
    public void testEditExistingMap() {
        d_mapName = "existing_map.map"; // Replace with the name of an existing map
        d_map = d_runGame.editMap(d_mapName);
        assertNotNull(d_map);
        assertEquals(d_map.getMapName(), d_mapName);
        
    }

    /**
     * Test editing a map with invalid format (e.g., missing required sections).
     */
    @Test
    public void testEditMapWithInvalidFormat() {
        d_mapName = "invalid_map.map"; // Create a map file with missing sections for testing
        d_map = d_runGame.editMap(d_mapName);
        assertNull(d_map); // Expecting null as the map format is invalid.
    }

    /**
     * Test editing a map with duplicate country names.
     */
    @Test
    public void testEditMapWithDuplicateCountryNames() {
        d_mapName = "map_with_duplicate_countries.map"; // Create a map file with duplicate country names for testing
        d_map = d_runGame.editMap(d_mapName);
        assertNull(d_map); // Expecting null as the map contains duplicate country names.
    }

    /**
     * Test editing a map with missing continent definitions.
     */
    @Test
    public void testEditMapWithMissingContinents() {
        d_mapName = "map_with_missing_continents.map"; // Create a map file with missing continent definitions for testing
        d_map = d_runGame.editMap(d_mapName);
        assertNull(d_map); // Expecting null as the map is missing continent definitions.
    }

    /**
     * Test editing a map with disconnected graph components.
     */
    @Test
    public void testEditMapWithDisconnectedComponents() {
        d_mapName = "map_with_disconnected_components.map"; // Create a map file with disconnected graph components for testing
        d_map = d_RunGame.editMap(d_mapName);
        assertNull(d_map); // Expecting null as the map contains disconnected graph components.
    }

    
}
