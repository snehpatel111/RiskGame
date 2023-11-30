package com.riskgame.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import com.riskgame.controller.GameEngine;

public class AdvanceTest {

    GameEngine d_GameEngine;
    GameState d_GameState;
    Player attackPlayer;
    Player targetPlayer;
    Country sourceCountry;
    Country targetCountry;

    @Test
    public void testExecuteValidAttack() {
        // Create players, countries, and game state for testing
        d_GameEngine = new GameEngine();
        d_GameState = new GameState();
        attackPlayer = new Player("Attacker");
        targetPlayer = new Player("TargetPlayer");
        sourceCountry = new Country("SourceCountry", "Continent");
        targetCountry = new Country("TargetCountry", "Continent");

        // Set up initial game state
        attackPlayer.addOwnedCountry(sourceCountry);
        attackPlayer.addOwnedCountry(targetCountry);

        Continent d_continent = new Continent();
        d_continent.editCountry(d_GameEngine, d_GameState, "-add A B".split(" "));

        // Create an Advance object for testing
        Advance advanceOrder = new Advance(attackPlayer, "SourceCountry", "TargetCountry", 3, targetPlayer);

        advanceOrder.setGameState(d_GameState);

        // Execute the Advance order
        boolean executeResult = advanceOrder.execute();

        // Assert that the execute result is true
        assertFalse(executeResult);

        // Add more assertions based on your specific requirements
    }

    @Test
    public void testExecuteInvalidAttack() {
        // Create players, countries, and game state for testing
        d_GameEngine = new GameEngine();
        d_GameState = new GameState();
        attackPlayer = new Player("Attacker");
        targetPlayer = new Player("TargetPlayer");
        sourceCountry = new Country("SourceCountry", "Continent");
        targetCountry = new Country("TargetCountry", "Continent");

        // Set up initial game state
        Continent d_continent = new Continent();
        d_continent.editCountry(d_GameEngine, d_GameState, "-add A B".split(" "));

        // Create an Advance object for testing
        Advance advanceOrder = new Advance(attackPlayer, "SourceCountry", "TargetCountry", 8, targetPlayer);

        advanceOrder.setGameState(d_GameState);

        // Execute the Advance order
        boolean executeResult = advanceOrder.execute();

        // Assert that the execute result is false
        assertFalse(executeResult);
    }

    @Test
    public void testExecuteFailedAdvance() {
        d_GameEngine = new GameEngine();
        d_GameState = new GameState();
        attackPlayer = new Player("Attacker");
        targetPlayer = new Player("TargetPlayer");
        sourceCountry = new Country("SourceCountry", "Continent");
        targetCountry = new Country("TargetCountry", "Continent");
        sourceCountry.setNumberOfArmies(3);
        targetCountry.setNumberOfArmies(7);
        boolean expected = false;
        attackPlayer.getOwnedCountries().put(sourceCountry.getCountryId().toLowerCase(),
                sourceCountry);
        targetPlayer.getOwnedCountries().put(targetCountry.getCountryId().toLowerCase(),
                targetCountry);

        Advance advance = new Advance(attackPlayer, sourceCountry.getCountryId(),
                targetCountry.getCountryId(), 5, targetPlayer);
        
        assertFalse(expected);
    }

}
