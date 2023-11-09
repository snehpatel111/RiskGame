package com.riskgame.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;

/**
 * Test class for DeployTest class
 */
public class DeployTest {

    private Player d_player;
    private Deploy d_deployOrder;
    private GameState d_gameState;

    /**
     * Setting up the initial state for the Deploy test cases.
     */
    @Before
    public void setUp() {
        d_player = new Player("Player 1");
        d_gameState = new GameState(); // Set up game state

        // Adding a country to the player's owned countries
        Country l_country = new Country("CountryID", "CountryName");
        d_player.getOwnedCountries().put("CountryID".toLowerCase(), l_country);

        d_deployOrder = new Deploy(d_player, "CountryID", 5);
        d_deployOrder.setGameState(d_gameState);
    }

    /**
     * Test to ensure the successful execution of Deployment.
     */
    @Test
    public void testExecuteDeployment() {
        int l_initialArmies = d_player.getOwnedCountries().get("CountryID".toLowerCase()).getNumberOfArmies();
        assertTrue("Deployment execution should be successful", d_deployOrder.execute());
        int l_finalArmies = d_player.getOwnedCountries().get("CountryID".toLowerCase()).getNumberOfArmies();
        assertEquals("Deployment should increase the number of armies by 5", l_initialArmies + 5, l_finalArmies);
    }

    /**
     * Test to validate the getter and setter methods of Deploy.
     */
    @Test
    public void testGetterAndSetterMethods() {
        assertEquals("Player should match the one set in setUp", d_player, d_deployOrder.getPlayer());
        assertEquals("CountryID should match the one set in setUp", "CountryID", d_deployOrder.getCountryId());
        assertEquals("Army count should match the one set in setUp", 5, d_deployOrder.getNumArmies());

        Player l_newPlayer = new Player("NewPlayer");
        d_deployOrder.setPlayer(l_newPlayer);
        d_deployOrder.setCountryId("NewCountryID");
        d_deployOrder.setNumArmies(10);

        assertEquals("Player should be updated", l_newPlayer, d_deployOrder.getPlayer());
        assertEquals("CountryID should be updated", "NewCountryID", d_deployOrder.getCountryId());
        assertEquals("Army count should be updated", 10, d_deployOrder.getNumArmies());
    }
    
}
