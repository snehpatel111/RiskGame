package com.riskgame.model;
import static org.junit.Assert.*;
import com.riskgame.model.Player;
import com.riskgame.model.Country;

import org.junit.Test;
import com.riskgame.model.Blockade; // Import the Blockade cla

public class BlockadeTest {

    @Test
    public void testExecuteBlockadeSuccess() {
        Player player = new Player("Blockading Player");
        
        Country country = new Country("Blockaded Country", "ContinentA");
        country.setNumberOfArmies(5);
        player.getOwnedCountries().put(country.getCountryId().toLowerCase(), country);

        Blockade blockade = new Blockade(player, country.getCountryId());

        boolean result = blockade.execute();

        assertEquals(15, country.getNumberOfArmies()); 
        assertFalse(player.getOwnedCountries().containsKey(country.getCountryId()));
        assertTrue(result);
    }

    @Test
    public void testExecuteBlockadeNoCountry() {
        Player player = new Player("Blockading Player");

        Blockade blockade = new Blockade(player, "NonExistentCountry");

        boolean result = blockade.execute();

        assertFalse(result);
    }

    @Test
    public void testExecuteBlockadeZeroArmies() {
        Player player = new Player("Blockading Player");
        
        Country country = new Country("Blockaded Country", "ContinentA");
        player.getOwnedCountries().put(country.getCountryId().toLowerCase(), country);

        Blockade blockade = new Blockade(player, country.getCountryId());

        boolean result = blockade.execute();

        assertEquals(0, country.getNumberOfArmies());
        assertFalse(player.getOwnedCountries().containsKey(country.getCountryId())); 
        assertTrue(result);
    }

    @Test
    public void testExecuteBlockadeFailure() {
        Player player = new Player("Blockading Player");

        Blockade blockade = new Blockade(player, "NonExistentCountry");

        boolean result = blockade.execute();

        assertFalse(result);
    }

    @Test
    public void testExecuteBlockadeInvalidCountry() {
        Player player = new Player("Blockading Player");
        
        Country country = new Country("Blockaded Country", "ContinentA");
        country.setNumberOfArmies(5);
        player.getOwnedCountries().put(country.getCountryId().toLowerCase(), country);

        Blockade blockade = new Blockade(player, "InvalidCountry");

        boolean result = blockade.execute();

        assertFalse(result);
    }

    @Test
    public void testExecuteBlockadeNonExistentCountry() {
        Player player = new Player("Blockading Player");
        
        Country country = new Country("Blockaded Country", "ContinentA");
        country.setNumberOfArmies(5);
        player.getOwnedCountries().put(country.getCountryId().toLowerCase(), country);

        Blockade blockade = new Blockade(player, "NonExistentCountry");

        boolean result = blockade.execute();

        assertFalse(result);
    }

    @Test
    public void testExecuteBlockadeCountryNotOwned() {
        Player player = new Player("Blockading Player");
        
        Country country = new Country("Blockaded Country", "ContinentA");
        country.setNumberOfArmies(5);

        Blockade blockade = new Blockade(player, country.getCountryId());

        boolean result = blockade.execute();

        assertFalse(result);
    }

    @Test
    public void testExecuteBlockadeInvalidPlayer() {
        Player player = new Player("Blockading Player");
        
        Country country = new Country("Blockaded Country", "ContinentA");
        country.setNumberOfArmies(5);
        Player anotherPlayer = new Player("Another Player");
        anotherPlayer.getOwnedCountries().put(country.getCountryId().toLowerCase(), country);

        Blockade blockade = new Blockade(player, country.getCountryId());

        boolean result = blockade.execute();

        assertFalse(result);
    }
    
   
}