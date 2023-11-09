package com.riskgame.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for AirliftTest class
 */
public class AirliftTest {

    private GameState d_gameState;
    private Airlift d_airliftOrder;

    /**
     * Set up method executed before each test case to initialize common data for
     * the Airlift tests.
     */
    @Before
    public void setup() {
        d_gameState = new GameState();
        Player d_player = new Player("PlayerName");
        String d_sourceCountryId = "SourceCountry";
        String d_targetCountryId = "TargetCountry";
        int d_armyCount = 5;
        d_airliftOrder = new Airlift(d_player, d_sourceCountryId, d_targetCountryId, d_armyCount);
    }

    /**
     * Test case to simulate executing the Airlift order and validate its
     * functionality.
     * Verifies the execute method, checking the transfer of armies between source
     * and target countries.
     */
    @Test
    public void testExecuteAirliftOrder() {
        Country d_sourceCountry = new Country("a", "abc");
        Country d_targetCountry = new Country("b", "abc");
        d_sourceCountry.setNumberOfArmies(10);
        d_targetCountry.setNumberOfArmies(5);
        d_airliftOrder.getPlayer().getOwnedCountries().put("sourcecountry", d_sourceCountry);
        d_airliftOrder.getPlayer().getOwnedCountries().put("targetcountry", d_targetCountry);

        boolean d_result = d_airliftOrder.execute();

        assertTrue(d_result);
        assertEquals(5, d_sourceCountry.getNumberOfArmies());
        assertEquals(10, d_targetCountry.getNumberOfArmies());
    }
}
