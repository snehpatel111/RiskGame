package com.riskgame.model;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.model.Phase;

import static org.junit.Assert.*;

public class ContinentTest {

    GameMap d_gameMap;
    String d_continentId;
    int d_controlValue;
    MapHelper d_mapHelper;
    Continent d_continent;

    /**
     * Set up the context
     */
    @Before
    public void before() {
        this.d_gameMap = new GameMap("ameroki.map");
        this.d_mapHelper = new MapHelper();
        this.d_continent = new Continent();
        this.d_continentId = "eurozio";
        this.d_controlValue = 5;
        this.d_continent.isContinentAdded(this.d_gameMap, "azio", this.d_controlValue);
    }

    /**
     * Test cases for adding continent
     */
    @Test
    public void testAddContinent() {
        this.d_gameMap = this.d_mapHelper.editMap("ameroki.map");
        System.out.println(this.d_gameMap.getMapName());
        System.out.println(this.d_gameMap.getContinents().size());

        boolean l_check = this.d_continent.isContinentAdded(this.d_gameMap, this.d_continentId, this.d_controlValue);
        assertTrue(l_check);
    }

    /**
     * Test case for Remove continent
     */
    @Test
    public void testRemoveContinent() {
        this.d_gameMap = this.d_mapHelper.editMap("ameroki.map");
        Continent l_continent = new Continent("azio", 5);

        boolean l_check = l_continent.removeContinent(this.d_gameMap);
        assertTrue(l_check);
    }

    /**
     * Test case for checking existing continent
     */
    @Test
    public void testEditContinentAddingExistingContinent() {
        String l_continentId = "azio";
        int l_controlValue = 7;
        this.d_continent.isContinentAdded(this.d_gameMap, l_continentId, l_controlValue);

        String[] args = { "editcontinent", "-add", l_continentId, Integer.toString(l_controlValue) };
        this.d_gameMap.getContinents().clear(); // Clear existing continents
        this.d_continent.editContinent(this.d_gameMap, Phase.EDITMAP, args);

        assertEquals(l_controlValue, this.d_gameMap.getContinents().get(l_continentId.toLowerCase()).getControlValue());
    }

    /**
     * Test for checking that no existing continent are removed when we remove non
     * existing continent
     */
    @Test
    public void testEditContinentRemovingNonexistentContinent() {
        String[] args = { "editcontinent", "-remove", "NonexistentContinent" };
        this.d_gameMap.getContinents().clear(); // Clear existing continents
        this.d_continent.editContinent(this.d_gameMap, Phase.EDITMAP, args);

        assertTrue(this.d_gameMap.getContinents().isEmpty());
    }

    /**
     * Test case for editing continent with invalid arguments
     */
    @Test
    public void testEditContinentWithInvalidArguments() {
        String[] args = { "editcontinent", "-add", "Invalid$Continent", "4" };
        this.d_gameMap.getContinents().clear();
        this.d_continent.editContinent(this.d_gameMap, Phase.EDITMAP, args);

        assertTrue(this.d_gameMap.getContinents().isEmpty());
    }
}
