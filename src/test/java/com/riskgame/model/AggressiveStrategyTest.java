package com.riskgame.model;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import com.riskgame.controller.GameEngine;

public class AggressiveStrategyTest {
    private Player d_player;
    private AggressiveStrategy d_aggressiveStrategy;
    private GameState d_gameState;

    @Before
    public void setUp() {
        d_player = new Player("AggressivePlayer");
        d_gameState = new GameState();
        ArrayList<Player> l_playerList = new ArrayList<>();
        l_playerList.add(d_player);
        d_gameState.setPlayers(l_playerList);

        // Use the initialized player and game state for AggressiveStrategy
        GameEngine gameEngine = new GameEngine(); // Replace with your actual GameEngine initialization
        d_aggressiveStrategy = new AggressiveStrategy(d_player, gameEngine);
    }

    /**
     * Test to ensure that the order value is set correctly.
     */
    @Test
    public void testSetOrderValue() {
        d_aggressiveStrategy.setOrderValue();
        assertTrue("Order value should be between 0 and 2", d_aggressiveStrategy.getOrderValue() >= 0 && d_aggressiveStrategy.getOrderValue() <= 2);
    }


    @Test
public void testCreateDeployOrderWithReinforcement() {
    // Add a country to the player's owned countries
    d_player.addOwnedCountry(new Country("CountryA"));

    // Set some reinforcement armies for the player
    d_player.setOwnedArmyCount(5);

    // Call createOrder to get the order from the aggressive strategy
    Order order = d_aggressiveStrategy.createOrder();

    // Debugging statements to help identify the issue
    if (order != null) {
        System.out.println("Order class: " + order.getClass().getSimpleName());

        // Assertion to check if the returned order is an instance of Deploy
        assertTrue("Order should be an instance of Deploy", order instanceof Deploy);

        // Additional assertions specific to Deploy orders
        Deploy deployOrder = (Deploy) order;
        assertEquals("Deploy order should be for the strongest country", "CountryA", deployOrder.getCountryId());
        assertEquals("Deploy order should deploy all reinforcement armies", 5, deployOrder.getNumArmies());
    } else {
        // If order is null, print a message or handle accordingly
        System.out.println("Order is null");
        fail("Order should not be null");
    }
}

    


// @Test
// public void testNoValidAirlift() {
//     // Setup: Player with no valid airlift scenario
//     aggressiveStrategy.setTestOrderValue(2); // Set order value for airlift

//     // Execution
//     Order order = aggressiveStrategy.createOrder();

//     // Assertion
//     assertNull(order);
// }



    



    // @Test
    // public void testAttackFromCountry() {
    //     // Add implementation to set up test scenario
    //     // For example: set up player's countries and armies

    //     // Call the method to test
    //     Country attackingCountry = aggressiveStrategy.attackFromCountry();

    //     // Add assertions based on the expected behavior
    //     assertNotNull(attackingCountry);
    //     assertEquals(attackingCountry, aggressiveStrategy.getStrongestCountry());
    // }

    // @Test
    // public void testMoveArmyFromCountry() {
    //     // Add implementation to set up test scenario
    //     // For example: set up player's countries and armies

    //     // Call the method to test
    //     Country moveFromCountry = aggressiveStrategy.moveArmyFromCountry();

    //     // Add assertions based on the expected behavior
    //     assertNotNull(moveFromCountry);
    //     assertEquals(moveFromCountry, aggressiveStrategy.getMoveArmyFromCountry());
    // }

    // @Test
    // public void testCreateOrder() {
    //     // Add implementation to set up test scenario
    //     // For example: set up player's countries and armies

    //     // Call the method to test
    //     Order order = aggressiveStrategy.createOrder();

    //     // Add assertions based on the expected behavior
    //     assertNotNull(order);
    //     // Add more assertions as needed based on the expected behavior of createOrder
    // }
}
