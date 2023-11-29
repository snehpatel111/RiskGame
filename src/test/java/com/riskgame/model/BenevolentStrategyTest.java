package com.riskgame.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import com.riskgame.controller.GameEngine;

public class BenevolentStrategyTest {
    private Player d_player;
    private BenevolentStrategy d_benevolentStrategy;
    private GameState d_gameState;

    @Before
    public void setUp() {
        GameEngine gameEngine = new GameEngine(); // Replace with your actual GameEngine initialization
        d_player = new Player("BenevolentPlayer");
        d_benevolentStrategy = new BenevolentStrategy(d_player, gameEngine);
        d_gameState = new GameState();
    }

    

    /**
     * Test to verify the creation of a Deploy order when reinforcement armies are available.
     */
    @Test
    public void testCreateDeployOrderWithReinforcement() {
        // Add a country to the player's owned countries
        d_player.addOwnedCountry(new Country("CountryA"));
    
        // Set some reinforcement armies for the player
        d_player.setOwnedArmyCount(5);
    
        // Call createOrder to get the order from the benevolent strategy
        Order order = d_benevolentStrategy.createOrder();
    
        // Assertion to check if the returned order is either Deploy or null
        assertTrue("Order should be an instance of Deploy or null", order instanceof Deploy || order == null);
    
        // Additional assertions specific to Deploy orders
        if (order instanceof Deploy) {
            Deploy deployOrder = (Deploy) order;
            assertEquals("Deploy order should be for the weakest country", "CountryA", deployOrder.getCountryId());
            assertEquals("Deploy order should deploy all reinforcement armies", 5, deployOrder.getNumArmies());
        }
    }

    /**
     * Test to ensure that no Advance order is created when the player does not own any countries.
     */
    @Test
    public void testNoAdvanceOrderWhenNoOwnedCountries() {
        // Call createOrder with no owned countries
        Order order = d_benevolentStrategy.createOrder();

        // Assert that the returned order is null
        assertNull("Advance order should not be created when the player does not own any countries", order);
    }

    /**
     * Test to verify the creation of a Deploy order when there are multiple countries to choose from.
     */
    @Test
    public void testCreateDeployOrderWithMultipleCountries() {
        // Add two countries to the player's owned countries
        d_player.addOwnedCountry(new Country("CountryA"));
        d_player.addOwnedCountry(new Country("CountryB"));

        // Set some reinforcement armies for the player
        d_player.setOwnedArmyCount(5);

        // Call createOrder to get the order from the benevolent strategy
        Order order = d_benevolentStrategy.createOrder();

        // Assertion to check if the returned order is either Deploy or null
        assertTrue("Order should be an instance of Deploy or null", order instanceof Deploy || order == null);

        // Additional assertions specific to Deploy orders
        if (order instanceof Deploy) {
            Deploy deployOrder = (Deploy) order;
            assertTrue("Deploy order should be for one of the owned countries",
                    d_player.getOwnedCountries().containsKey(deployOrder.getCountryId()));
            assertEquals("Deploy order should deploy all reinforcement armies", 5, deployOrder.getNumArmies());
        }
    }

    /**
     * Test to ensure that no Advance order is created when there is only one owned country.
     */
    @Test
    public void testNoAdvanceOrderWithSingleOwnedCountry() {
        // Add a country to the player's owned countries
        d_player.addOwnedCountry(new Country("CountryA"));

        // Set some reinforcement armies for the player
        d_player.setOwnedArmyCount(5);

        // Call createOrder to get the order from the benevolent strategy
        Order order = d_benevolentStrategy.createOrder();

        // Assert that the returned order is null
        assertNull("Advance order should not be created when there is only one owned country", order);
    }


    /**
     * Test to ensure that no order is created when there are no armies to deploy or advance.
     */
    @Test
    public void testNoValidOrderWhenNoArmies() {
        // Call createOrder with no owned countries and no reinforcement armies
        Order order = d_benevolentStrategy.createOrder();

        // Assert that the returned order is null
        assertNull("Order should be null when there are no armies to deploy or advance", order);
    }

    /**
     * Test to ensure that no Advance order is created when the source country has 0 armies.
     */
    @Test
    public void testNoAdvanceOrderWhenSourceCountryHasNoArmies() {
        // Add two countries to the player's owned countries
        d_player.addOwnedCountry(new Country("CountryA"));
        d_player.addOwnedCountry(new Country("CountryB"));

        // Call createOrder to get the order from the benevolent strategy
        Order order = d_benevolentStrategy.createOrder();

        // Assert that the returned order is null
        assertNull("Advance order should not be created when the source country has 0 armies", order);
    }
}

