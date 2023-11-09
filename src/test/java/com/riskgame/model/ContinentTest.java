package com.riskgame.model;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;
import com.riskgame.model.Phase;
import com.riskgame.utility.Util;

import static org.junit.Assert.*;

/**
 * Test class for ContinentTest class
 */
public class ContinentTest {

    GameMap d_gameMap;
    String d_continentId;
    int d_controlValue;
    MapHelper d_mapHelper;
    Continent d_continent;
    GameState d_gamestate;
    GameEngine d_gameEngine;

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
        this.d_gameEngine = new GameEngine();
        this.d_gamestate = new GameState();
    }

    /**
     * Test cases for adding continent
     */
    @Test
    public void testAddContinent() {
        this.d_mapHelper.editMap(this.d_gameEngine, this.d_gamestate, "ameroki.map");
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
        this.d_mapHelper.editMap(this.d_gameEngine, this.d_gamestate, "ameroki.map");
        Continent l_continent = new Continent("azio", 5);

        boolean l_check = l_continent.removeContinent(this.d_gameMap, d_gamestate);
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
        this.d_gameMap.getContinents().clear();
        boolean l_isContinentAdded = this.d_continent.isContinentAdded(this.d_gameMap,
                this.d_continentId,
                this.d_controlValue);
        assertTrue(l_isContinentAdded);
    }

    /**
     * Test for checking that no existing continent are removed when we remove non
     * existing continent
     */
    @Test
    public void testEditContinentRemovingNonexistentContinent() {
        String[] args = { "editcontinent", "-remove", "NonexistentContinent" };
        this.d_gameMap.getContinents().clear(); // Clear existing continents
        boolean l_isContinentExist = this.d_continent.isContinentExist(this.d_gameMap, "NonexistentContinent");

        assertFalse(l_isContinentExist);
    }

    /**
     * Test case for editing continent with invalid arguments
     */
    @Test
    public void testEditContinentWithInvalidArguments() {
        String[] args = { "editcontinent", "-add", "Invalid$Continent", "4" };
        this.d_gameMap.getContinents().clear();
        Util.isValidCommandArgument(args, 4);

        assertTrue(Util.isValidCommandArgument(args, 4));
    }
}
