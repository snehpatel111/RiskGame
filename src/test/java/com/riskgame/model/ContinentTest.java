package com.riskgame.model;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.utility.Phase;

import static org.junit.Assert.*;

public class ContinentTest {

    GameMap d_gameMap;
    String d_continentId;
    int d_controlValue;
    MapHelper d_mapHelper;
    Continent d_continent;

    @Before
    public void before() {
        d_gameMap = new GameMap("ameroki.map");
        d_mapHelper = new MapHelper();
        d_continent = new Continent();
        d_continentId = "eurozio";
        d_controlValue = 5;
        d_continent.isContinentAdded(d_gameMap, "azio", d_controlValue);
    }

    /**
     * Test cases for adding continent
     */
    @Test
    public void testAddContinent() {
        d_gameMap = d_mapHelper.editMap("ameroki.map");
        System.out.println(d_gameMap.getMapName());
        System.out.println(d_gameMap.getContinents().size());

        boolean l_check = d_continent.isContinentAdded(d_gameMap, d_continentId, d_controlValue);
        assertTrue(l_check);
    }

    /**
     * Test case for Remove continent
     */
    @Test
    public void testRemoveContinent() {
        d_gameMap = d_mapHelper.editMap("ameroki.map");
        Continent l_continent = new Continent("azio", 5);

        boolean l_check = l_continent.removeContinent(d_gameMap);
        assertTrue(l_check);
    }

    /**
     * Test case for checking existing continent
     */
    @Test
    public void testEditContinentAddingExistingContinent() {
        String l_continentId = "azio";
        int l_controlValue = 7;
        d_continent.isContinentAdded(d_gameMap, l_continentId, l_controlValue);

        String[] args = { "editcontinent", "-add", l_continentId, Integer.toString(l_controlValue) };
        d_gameMap.getContinents().clear(); // Clear existing continents
        d_continent.editContinent(d_gameMap, Phase.EDITMAP, args);

        assertEquals(l_controlValue, d_gameMap.getContinents().get(l_continentId.toLowerCase()).getControlValue());
    }

    /**
     * Test for checking that no existing continent are removed when we remove non
     * existing continent
     */
    @Test
    public void testEditContinentRemovingNonexistentContinent() {
        String[] args = { "editcontinent", "-remove", "NonexistentContinent" };
        d_gameMap.getContinents().clear(); // Clear existing continents
        d_continent.editContinent(d_gameMap, Phase.EDITMAP, args);

        assertTrue(d_gameMap.getContinents().isEmpty());
    }

    /**
     * Test case for editing continent with invalid arguments
     */
    @Test
    public void testEditContinentWithInvalidArguments() {
        String[] args = { "editcontinent", "-add", "Invalid$Continent", "4" };
        d_gameMap.getContinents().clear();
        d_continent.editContinent(d_gameMap, Phase.EDITMAP, args);

        assertTrue(d_gameMap.getContinents().isEmpty());
    }
}
