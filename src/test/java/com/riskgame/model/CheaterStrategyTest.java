package com.riskgame.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;

public class CheaterStrategyTest {

    GameEngine d_GameEngine;
    GameState d_GameState;
    Player d_player1;
    Player d_player2;
    CheaterStrategy d_cheaterStrategy;

        /**
     * Setting up the initial state for Diplomacy test cases.
     */
    @Before
    public void setUp() {
        d_GameEngine = new GameEngine();
        d_GameState = new GameState();
        d_player1 = new Player("Rushi");
        d_player1 = new Player("Romit");
        d_cheaterStrategy = new CheaterStrategy(d_player1, d_GameEngine);
    }
    
    @Test
    public void testCreateOrder() {

        // Set up a scenario where the cheater owns a neighboring country
        // and the army count of the owned country is doubled
        // You can use a mock or a test-specific game state to control the scenario
        // For simplicity, let's assume the cheater owns a country "A" and has a neighbor "B"

        // Create countries
        Country countryA = new Country("A", "Continent1");
        Country countryB = new Country("B", "Continent1");

        Continent d_continent = new Continent();
        d_continent.editCountry(d_GameEngine, d_GameState, "-add A B".split(" "));

        // Set up the scenario
        d_player1.addOwnedCountry(countryA);
        d_player1.addOwnedCountry(countryB);

        // Call the createOrder method
        Order resultOrder = d_cheaterStrategy.createOrder();

        // Assert that the cheater now owns countryB
        assertFalse(d_player1.getOwnedCountries().containsKey("B"));

        // Assert that the army count of countryA is doubled
        assertEquals(0, countryA.getNumberOfArmies());

        // Additional assertions based on your specific requirements
    }
}
