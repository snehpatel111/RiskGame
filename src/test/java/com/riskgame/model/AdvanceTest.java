// // package com.riskgame.model;

// // import static org.junit.Assert.*;
// // import org.junit.Before;
// // import org.junit.Test;

// // public class AdvanceTest {
// //     private Player attackingPlayer;
// //     private Player defendingPlayer;
// //     private Country sourceCountry;
// //     private Country targetCountry;

    // @Before
    // public void setUp() {
    //     attackingPlayer = new Player("Attacking Player");
    //     defendingPlayer = new Player("Defending Player");
    //     sourceCountry = new Country("SourceCountry", "ContinentA");
    //     targetCountry = new Country("TargetCountry", "ContinentB");
    // }

    // @Test
    // public void testFailedAdvanceDueToInsufficientArmies() {
    //     sourceCountry.setNumberOfArmies(5);
    //     targetCountry.setNumberOfArmies(8);
    //     attackingPlayer.getOwnedCountries().put(sourceCountry.getCountryId().toLowerCase(), sourceCountry);
    //     defendingPlayer.getOwnedCountries().put(targetCountry.getCountryId().toLowerCase(), targetCountry);

    //     Advance advance = new Advance(attackingPlayer, sourceCountry.getCountryId(), targetCountry.getCountryId(), 6,
    //             defendingPlayer);

    //     boolean result = advance.execute();

    //     assertFalse(result);
    // }

// //     // @Test
// //     // public void testFailedAdvanceWithInvalidPlayers() {
// //     // sourceCountry.setNumberOfArmies(10);
// //     // targetCountry.setNumberOfArmies(3);
// //     // attackingPlayer.getOwnedCountries().put(sourceCountry.getCountryId().toLowerCase(),
// //     // sourceCountry);
// //     // defendingPlayer.getOwnedCountries().put(targetCountry.getCountryId().toLowerCase(),
// //     // targetCountry);

// //     // Advance advance = new Advance(null, sourceCountry.getCountryId(),
// //     // targetCountry.getCountryId(), 4, null);

// //     // boolean result = advance.execute();

// //     // assertFalse(result);
// //     // }

    // @Test
    // public void testExecuteFailedAdvance() {
    //     sourceCountry.setNumberOfArmies(3);
    //     targetCountry.setNumberOfArmies(7);
    //     attackingPlayer.getOwnedCountries().put(sourceCountry.getCountryId().toLowerCase(), sourceCountry);
    //     defendingPlayer.getOwnedCountries().put(targetCountry.getCountryId().toLowerCase(), targetCountry);

    //     Advance advance = new Advance(attackingPlayer, sourceCountry.getCountryId(), targetCountry.getCountryId(), 5,
    //             defendingPlayer);

    //     boolean result = advance.execute();

    //     assertFalse(result);
    // }

// // }
// package com.riskgame.model;
// import static org.junit.Assert.*;
// import org.junit.Before;
// import org.junit.Test;

// /**
//  * Test class for AdvanceTest class
//  */
// public class AdvanceTest {
//     private Player attacker;
//     private Player targetPlayer;
//     private GameState gameState;
//     private Country sourceCountry;
//     private Country targetCountry;


//     @Before
//     public void setup() {
//         // Create necessary objects for testing
//         attacker = new Player("Attacker");
//         targetPlayer = new Player("TargetPlayer");
//         gameState = new GameState();

//         // Create source and target countries
//         sourceCountry = new Country("a", "abc");
//         targetCountry = new Country("b", "abc");
//     }

    
//     @Test
//     public void testValidConquest() {
//         attacker.addOwnedCountry("SourceCountryID", new Country("SourceCountryID", attacker));
//         targetPlayer.addOwnedCountry("TargetCountryID", new Country("TargetCountryID", targetPlayer));

//         Advance conquerOrder = new Advance(attacker, "SourceCountryID", "TargetCountryID", 6, targetPlayer);
//         conquerOrder.setGameState(gameState);

//         assertTrue("Conquering the country should be successful", conquerOrder.execute());

//         // Assert that the attacking player now owns the target country
//         assertTrue(attacker.ownsCountry("TargetCountryID"));

//         // Additional assertions based on the actual behavior of the Advance class
//     }
 
    
//     @Test
//     public void testInvalidConquest() {
//         // Setting up invalid conditions for the conquest
//         Advance conquerOrder = new Advance(attacker, "SourceCountryID", "TargetCountryID", 6, targetPlayer);
//         conquerOrder.setGameState(gameState);

//         assertFalse("Conquering the country should fail due to invalid conditions", conquerOrder.execute());
//         // Assert additional conditions for failure if applicable
//     }

    
//     /**
//      * Test end of game when all countries are conquered.
//      */
//     @Test
//     public void testEndOfGameWhenConqueringAllCountries() {

//         int l_totalNumberOfCountriesInGame = 1;
//         boolean l_isGameEnded = d_attackingPlayer.getOwnedCountries().size() == l_totalNumberOfCountriesInGame;
//         assertTrue("Game should end when all countries are conquered.", l_isGameEnded);
//     }

//     /**
//      * Test the failed execution of an Advance order due to null players.
//      */
//     @Test
//     public void testExecuteOrderInvalidPlayers() {
//         // Test if order execution fails due to null players
//         d_advanceOrder.setAttackPlayer(null);
//         assertFalse("Order execution should fail with null players.", d_advanceOrder.execute());
//     }

//     /**
//      * Test the failed execution of an Advance order due to null or invalid source/target/defending countries.
//      */
//     @Test
//     public void testExecuteOrderInvalidCountries() {
//         // Test if order execution fails due to null or invalid source/target/defending countries
//         d_advanceOrder.setSourceCountryId(null);
//         d_advanceOrder.setTargetCountryId(null);
//         assertFalse("Order execution should fail with null source/target countries.", d_advanceOrder.execute());
//     }

//     /**
//     * Test the failed execution of an Advance order with an invalid source country.
//     */
//     @Test
//     public void testExecuteOrderInvalidSourceCountry() {
//         // Test if order execution fails if the source country is not owned by the player
//         advanceOrder.setSourceCountryId("NonExistentSourceCountryID");
//         assertFalse("Order execution should fail if source country is not owned.", d_advanceOrder.execute());
//     }

//     /**
//      * Test the execution of an Advance order with a negotiation between players.
//     */
//     @Test
//     public void testExecuteNegotiateOrder() {
//         // Test if the order fails due to a negotiate order
//         d_attackingPlayer.addPlayerToNegotiateList(d_defendingPlayer);
//         assertFalse("Negotiate order should fail execution.", d_advanceOrder.execute());
//     }

//     /**
//     * Test the number of armies moved after conquering a country with an Advance order.
//     */
//     @Test
//     public void testArmiesMovedAfterConquering() {
//         // Test the number of armies moved after conquering
//         // Execute the order and validate the number of armies in source/target countries
//         d_advanceOrder.execute();
//         assertEquals("Armies moved from source country.", 0, d_attackingPlayer.getOwnedCountries().get("SourceCountryID".toLowerCase()).getNumberOfArmies());
//         assertEquals("Armies moved to target country.", 0, d_defendingPlayer.getOwnedCountries().get("TargetCountryID".toLowerCase()).getNumberOfArmies());
//     }

//     /**
//     * Test the failed execution of an Advance order due to insufficient armies in the defending country.
//     */
//     @Test
//     public void testFailedAdvanceDueToInsufficientArmies() {
//         d_sourceCountry.setNumberOfArmies(5);
//         d_targetCountry.setNumberOfArmies(8);
//         d_attackingPlayer.getOwnedCountries().put(d_sourceCountry.getCountryId().toLowerCase(), d_sourceCountry);
//         d_defendingPlayer.getOwnedCountries().put(d_targetCountry.getCountryId().toLowerCase(), d_targetCountry);

//         Advance l_advance = new Advance(d_attackingPlayer, d_sourceCountry.getCountryId(), d_targetCountry.getCountryId(), 6, d_defendingPlayer);

//         boolean l_result = l_advance.execute();

//         assertFalse(l_result);
//     }

//     /**
//     * Test the failed execution of an Advance order with invalid players.
//     */
//     @Test
//     public void testFailedAdvanceWithInvalidPlayers() {
//         d_sourceCountry.setNumberOfArmies(10);
//         d_targetCountry.setNumberOfArmies(3);
//         d_attackingPlayer.getOwnedCountries().put(d_sourceCountry.getCountryId().toLowerCase(), d_sourceCountry);
//         d_defendingPlayer.getOwnedCountries().put(d_targetCountry.getCountryId().toLowerCase(), d_targetCountry);

//         Advance l_advance = new Advance(null, d_sourceCountry.getCountryId(), d_targetCountry.getCountryId(), 4, null);

//         boolean l_result = l_advance.execute();

//         assertFalse(l_result);
//     }

//     @Test
//     public void testExecuteFailedAdvance() {
//         d_sourceCountry.setNumberOfArmies(3);
//         d_targetCountry.setNumberOfArmies(7);
//         d_attackingPlayer.getOwnedCountries().put(d_sourceCountry.getCountryId().toLowerCase(), d_sourceCountry);
//         d_defendingPlayer.getOwnedCountries().put(d_targetCountry.getCountryId().toLowerCase(), d_targetCountry);

//         Advance l_advance = new Advance(d_attackingPlayer, d_sourceCountry.getCountryId(), d_targetCountry.getCountryId(), 5, d_defendingPlayer);

//         boolean l_result = l_advance.execute();

//         assertFalse(l_result);
//     }


//     /**
//      * Test execution of an Advance order with invalid source/target.
//      */
//     @Test
//     public void testAdvanceOrderSourceTargetValidation() {
//         boolean l_result = d_advanceOrder.execute();

//         assertFalse("Advance order should fail with invalid source/target", l_result);
//     }

// }
