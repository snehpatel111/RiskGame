package com.riskgame.model;
import com.riskgame.model.Advance;
import com.riskgame.model.Player;
import com.riskgame.model.Country;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdvanceTest {

    private Player attackingPlayer;
    private Player defendingPlayer;
    private Country sourceCountry;
    private Country targetCountry;

    @Before
    public void setUp() {
        attackingPlayer = new Player("AttackingPlayer");
        defendingPlayer = new Player("DefendingPlayer");

        // Use the existing constructors from the Country class
        sourceCountry = new Country("SourceCountry", "ContinentName1");
        targetCountry = new Country("TargetCountry", "ContinentName2");
    }

    @Test
    public void testExecuteAdvanceSuccess() {
        // Create the attacking player
        Player attackingPlayer = new Player("Attacking Player");
        // Create the defending player
        Player defendingPlayer = new Player("Defending Player");

        // Create source and target countries
        Country sourceCountry = new Country("SourceCountry", "ContinentA");
        Country targetCountry = new Country("TargetCountry", "ContinentB");

        // Set up the test scenario for a successful advance
        sourceCountry.setNumberOfArmies(10);
        targetCountry.setNumberOfArmies(5);
        attackingPlayer.getOwnedCountries().put(sourceCountry.getCountryId().toLowerCase(), sourceCountry);
        defendingPlayer.getOwnedCountries().put(targetCountry.getCountryId().toLowerCase(), targetCountry);

        // Create an Advance object for the test
        Advance advance = new Advance(attackingPlayer, sourceCountry.getCountryId(), targetCountry.getCountryId(), 3, defendingPlayer);


        // Execute the advance and check if it was successful
        boolean result = advance.execute();

        // Verify the changes after the advance
        assertEquals(7, sourceCountry.getNumberOfArmies());
        assertFalse(defendingPlayer.getOwnedCountries().containsKey(targetCountry.getCountryId()));
        assertTrue(result);
    }

    
    


    @Test
    public void testExecuteAttackWithDefenderWin() {
        // Set up the test scenario for an attack where the defender wins
        sourceCountry.setNumberOfArmies(5);
        targetCountry.setNumberOfArmies(10);
        attackingPlayer.addOwnedCountry(sourceCountry);
        defendingPlayer.addOwnedCountry(targetCountry);

        Advance advance = new Advance(attackingPlayer, sourceCountry.getCountryId(), targetCountry.getCountryId(), 3, defendingPlayer);

        assertTrue(advance.execute());

        // Verify the changes after the attack
        assertEquals(2, sourceCountry.getNumberOfArmies());
        assertTrue(defendingPlayer.getOwnedCountries().containsKey(targetCountry.getCountryId()));
        assertEquals(7, targetCountry.getNumberOfArmies());
    }

    

    @Test
    public void testExecuteAttackInvalidSourceCountry() {
        // Set up the test scenario for an attack with an invalid source country
        Advance advance = new Advance(attackingPlayer, sourceCountry.getCountryId(), targetCountry.getCountryId(), 3, defendingPlayer);

        assertFalse(advance.execute());
    }

    @Test
    public void testExecuteAttackInvalidTargetCountry() {
        // Set up the test scenario for an attack with an invalid target country
        sourceCountry.setNumberOfArmies(10);
        attackingPlayer.addOwnedCountry(sourceCountry);

        Advance advance = new Advance(attackingPlayer, sourceCountry.getCountryId(), targetCountry.getCountryId(), 3, defendingPlayer);

        assertFalse(advance.execute());
       
    }
}
