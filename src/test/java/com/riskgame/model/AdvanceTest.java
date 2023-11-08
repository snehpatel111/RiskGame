package com.riskgame.model;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class AdvanceTest {
    private Player attackingPlayer;
    private Player defendingPlayer;
    private Country sourceCountry;
    private Country targetCountry;

    @Before
    public void setUp() {
        attackingPlayer = new Player("Attacking Player");
        defendingPlayer = new Player("Defending Player");
        sourceCountry = new Country("SourceCountry", "ContinentA");
        targetCountry = new Country("TargetCountry", "ContinentB");
    }

    @Test
    public void testFailedAdvanceDueToInsufficientArmies() {
        sourceCountry.setNumberOfArmies(5);
        targetCountry.setNumberOfArmies(8);
        attackingPlayer.getOwnedCountries().put(sourceCountry.getCountryId().toLowerCase(), sourceCountry);
        defendingPlayer.getOwnedCountries().put(targetCountry.getCountryId().toLowerCase(), targetCountry);

        Advance advance = new Advance(attackingPlayer, sourceCountry.getCountryId(), targetCountry.getCountryId(), 6, defendingPlayer);

        boolean result = advance.execute();

        assertFalse(result);
    }

    @Test
    public void testFailedAdvanceWithInvalidPlayers() {
        sourceCountry.setNumberOfArmies(10);
        targetCountry.setNumberOfArmies(3);
        attackingPlayer.getOwnedCountries().put(sourceCountry.getCountryId().toLowerCase(), sourceCountry);
        defendingPlayer.getOwnedCountries().put(targetCountry.getCountryId().toLowerCase(), targetCountry);

        Advance advance = new Advance(null, sourceCountry.getCountryId(), targetCountry.getCountryId(), 4, null);

        boolean result = advance.execute();

        assertFalse(result);
    }

    

    @Test
    public void testExecuteFailedAdvance() {
        sourceCountry.setNumberOfArmies(3);
        targetCountry.setNumberOfArmies(7);
        attackingPlayer.getOwnedCountries().put(sourceCountry.getCountryId().toLowerCase(), sourceCountry);
        defendingPlayer.getOwnedCountries().put(targetCountry.getCountryId().toLowerCase(), targetCountry);

        Advance advance = new Advance(attackingPlayer, sourceCountry.getCountryId(), targetCountry.getCountryId(), 5, defendingPlayer);

        boolean result = advance.execute();

        assertFalse(result);
    }

}
