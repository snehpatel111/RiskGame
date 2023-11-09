package com.riskgame.model;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for AdvanceTest class
 */
public class AdvanceTest {
    private Player d_attackingPlayer;
    private Player d_defendingPlayer;
    private Country d_sourceCountry;
    private Country d_targetCountry;
    private Advance d_advanceOrder;
    private GameState d_gameState;

    @Before
    public void setUp() {
        d_attackingPlayer = new Player("Attacking Player");
        d_defendingPlayer = new Player("Defending Player");
        d_sourceCountry = new Country("SourceCountry", "ContinentA");
        d_targetCountry = new Country("TargetCountry", "ContinentB");

        d_gameState = new GameState();
        // Add the countries to the player's owned countries for testing
        d_attackingPlayer.getOwnedCountries().put("SourceCountryID".toLowerCase(), d_sourceCountry);
        d_defendingPlayer.getOwnedCountries().put("TargetCountryID".toLowerCase(), d_targetCountry);

        d_advanceOrder = new Advance(d_attackingPlayer, "SourceCountryID", "TargetCountryID", 5, d_defendingPlayer);
        d_advanceOrder.setGameState(d_gameState);
    }

    /**
     * Test the failed execution of an Advance order due to null players.
     */
    @Test
    public void testExecuteOrderInvalidPlayers() {
        // Test if order execution fails due to null players
        d_advanceOrder.setAttackPlayer(null);
        assertFalse("Order execution should fail with null players.", d_advanceOrder.execute());
    }

    /**
     * Test the failed execution of an Advance order due to null or invalid source/target/defending countries.
     */
    @Test
    public void testExecuteOrderInvalidCountries() {
        // Test if order execution fails due to null or invalid source/target/defending countries
        d_advanceOrder.setSourceCountryId(null);
        d_advanceOrder.setTargetCountryId(null);
        assertFalse("Order execution should fail with null source/target countries.", d_advanceOrder.execute());
    }

    /**
    * Test the failed execution of an Advance order with an invalid source country.
    */
    @Test
    public void testExecuteOrderInvalidSourceCountry() {
        // Test if order execution fails if the source country is not owned by the player
        d_advanceOrder.setSourceCountryId("NonExistentSourceCountryID");
        assertFalse("Order execution should fail if source country is not owned.", d_advanceOrder.execute());
    }

    /**
     * Test the execution of an Advance order with a negotiation between players.
    */
    @Test
    public void testExecuteNegotiateOrder() {
        // Test if the order fails due to a negotiate order
        d_attackingPlayer.addPlayerToNegotiateList(d_defendingPlayer);
        assertFalse("Negotiate order should fail execution.", d_advanceOrder.execute());
    }

    /**
    * Test the number of armies moved after conquering a country with an Advance order.
    */
    @Test
    public void testArmiesMovedAfterConquering() {
        // Test the number of armies moved after conquering
        // Execute the order and validate the number of armies in source/target countries
        d_advanceOrder.execute();
        assertEquals("Armies moved from source country.", 0, d_attackingPlayer.getOwnedCountries().get("SourceCountryID".toLowerCase()).getNumberOfArmies());
        assertEquals("Armies moved to target country.", 0, d_defendingPlayer.getOwnedCountries().get("TargetCountryID".toLowerCase()).getNumberOfArmies());
    }

    /**
    * Test the failed execution of an Advance order due to insufficient armies in the defending country.
    */
    @Test
    public void testFailedAdvanceDueToInsufficientArmies() {
        d_sourceCountry.setNumberOfArmies(5);
        d_targetCountry.setNumberOfArmies(8);
        d_attackingPlayer.getOwnedCountries().put(d_sourceCountry.getCountryId().toLowerCase(), d_sourceCountry);
        d_defendingPlayer.getOwnedCountries().put(d_targetCountry.getCountryId().toLowerCase(), d_targetCountry);

        Advance l_advance = new Advance(d_attackingPlayer, d_sourceCountry.getCountryId(), d_targetCountry.getCountryId(), 6, d_defendingPlayer);

        boolean l_result = l_advance.execute();

        assertFalse(l_result);
    }

    /**
    * Test the failed execution of an Advance order with invalid players.
    */
    @Test
    public void testFailedAdvanceWithInvalidPlayers() {
        d_sourceCountry.setNumberOfArmies(10);
        d_targetCountry.setNumberOfArmies(3);
        d_attackingPlayer.getOwnedCountries().put(d_sourceCountry.getCountryId().toLowerCase(), d_sourceCountry);
        d_defendingPlayer.getOwnedCountries().put(d_targetCountry.getCountryId().toLowerCase(), d_targetCountry);

        Advance l_advance = new Advance(null, d_sourceCountry.getCountryId(), d_targetCountry.getCountryId(), 4, null);

        boolean l_result = l_advance.execute();

        assertFalse(l_result);
    }

    @Test
    public void testExecuteFailedAdvance() {
        d_sourceCountry.setNumberOfArmies(3);
        d_targetCountry.setNumberOfArmies(7);
        d_attackingPlayer.getOwnedCountries().put(d_sourceCountry.getCountryId().toLowerCase(), d_sourceCountry);
        d_defendingPlayer.getOwnedCountries().put(d_targetCountry.getCountryId().toLowerCase(), d_targetCountry);

        Advance l_advance = new Advance(d_attackingPlayer, d_sourceCountry.getCountryId(), d_targetCountry.getCountryId(), 5, d_defendingPlayer);

        boolean l_result = l_advance.execute();

        assertFalse(l_result);
    }

    /**
     * Test end of game when all countries are conquered.
     */
    @Test
    public void testEndOfGameWhenConqueringAllCountries() {

        int l_totalNumberOfCountriesInGame = 1;
        boolean l_isGameEnded = d_attackingPlayer.getOwnedCountries().size() == l_totalNumberOfCountriesInGame;
        assertTrue("Game should end when all countries are conquered.", l_isGameEnded);
    }

    /**
     * Test execution of an Advance order with invalid source/target.
     */
    @Test
    public void testAdvanceOrderSourceTargetValidation() {
        boolean l_result = d_advanceOrder.execute();

        assertFalse("Advance order should fail with invalid source/target", l_result);
    }

}
