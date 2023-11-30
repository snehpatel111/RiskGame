package com.riskgame.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.model.*;

/**
 * Unit test for simple GameEngine.
 */
public class GameEngineTest {
    Player d_player;
    StartUpPhase d_startUpPhase;
    Continent d_continent;
    GameEngine d_gameEngine;
    GameState d_gameState;

    /**
     * Set up the context
     */
    @Before
    public void before() {
        this.d_gameEngine = new GameEngine();
        this.d_gameState = new GameState();
        this.d_startUpPhase = new StartUpPhase(this.d_gameEngine, this.d_gameState);
        this.d_player = new Player("TestPlayer");
        this.d_continent = new Continent("TestContinent", 6);
    }

    /**
     * Test for assign reinforcement function
     */
    @Test
    public void testAssignReinforcementToPlayerBasic() {
        this.d_gameState.getPlayerList().add(this.d_player);

        this.d_startUpPhase.assignReinforcementToPlayer(this.d_gameState);

        assertEquals(5, this.d_player.getOwnedArmyCount());
    }

    /**
     * Test assign reinforcement with 9 countries
     */
    @Test
    public void testAssignReinforcementToPlayerWithNineCountries() {
        for (int i = 0; i < 10; i++) {
            String l_countryId = "TestCountry " + i;
            Country l_country = new Country(l_countryId, this.d_continent.getContinentId());
            this.d_player.getOwnedCountries().put(l_countryId.toLowerCase(), l_country);
        }

        this.d_gameState.getPlayerList().add(this.d_player);

        this.d_startUpPhase.assignReinforcementToPlayer(this.d_gameState);
        assertEquals(5, this.d_player.getOwnedArmyCount());
    }

    /**
     * Test assign reinforcement with 10 countries
     */
    @Test
    public void testAssignReinforcementToPlayerWithTenCountries() {
        for (int i = 0; i < 11; i++) {
            String l_countryId = "TestCountry " + i;
            Country l_country = new Country(l_countryId, this.d_continent.getContinentId());
            this.d_player.getOwnedCountries().put(l_countryId.toLowerCase(), l_country);
        }

        this.d_gameState.getPlayerList().add(this.d_player);

        this.d_startUpPhase.assignReinforcementToPlayer(this.d_gameState);

        assertEquals(5, this.d_player.getOwnedArmyCount());
    }

    // @Test
    // public void testSetIssueOrderPhase() {
    // this.d_gameEngine.setIssueOrderPhase();
    // assertTrue(this.d_gameEngine.getCurrentGamePhase() instanceof
    // IssueOrderPhase);
    // }

    @Test
    public void testInitialGamePhase() {
        assertTrue(this.d_gameEngine.getCurrentGamePhase() instanceof StartUpPhase);
    }
}
