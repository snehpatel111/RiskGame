package com.riskgame.model;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.riskgame.controller.GameEngine;

public class RandomStrategyTest {
    private Player player;
    private GameEngine gameEngine;
    private RandomStrategy randomStrategy;

    @Before
    public void setUp() {
        player = new Player("TestPlayer");
        gameEngine = new GameEngine();
        randomStrategy = new RandomStrategy(player, gameEngine);
    }

    @Test
    public void testAttackFromCountry() {
        // Ensure the player has at least one country with armies
        Country countryWithArmies = new Country("CountryWithArmies", "Continent");
        countryWithArmies.setNumberOfArmies(5);
        player.getOwnedCountries().put(countryWithArmies.getCountryId().toLowerCase(), countryWithArmies);

        // Now, test the attackFromCountry method
        RandomStrategy randomStrategy = new RandomStrategy(player, gameEngine);
        Country attackingCountry = randomStrategy.attackFromCountry();

        assertNotNull("Attacking country should not be null", attackingCountry);
    }

    @Test
    public void testAttackFromCountryNotNull() {
        // Ensure the player has at least one country with armies
        Country countryWithArmies = new Country("CountryWithArmies", "Continent");
        countryWithArmies.setNumberOfArmies(5);
        player.getOwnedCountries().put(countryWithArmies.getCountryId().toLowerCase(), countryWithArmies);

        // Now, test the attackFromCountry method
        Country attackingCountry = randomStrategy.attackFromCountry();

        assertNotNull("Attacking country should not be null", attackingCountry);
    }

    @Test
    public void testAttackFromCountryNoArmies() {
        // Ensure the player has a country with no armies
        Country countryNoArmies = new Country("CountryNoArmies", "Continent");
        player.getOwnedCountries().put(countryNoArmies.getCountryId().toLowerCase(), countryNoArmies);

        // Now, test the attackFromCountry method
        Country attackingCountry = randomStrategy.attackFromCountry();

        assertNull("Attacking country should be null when no armies are present", attackingCountry);
    }

    @Test
    public void testAttackToCountryNoNeighbors() {
        // Ensure the player has a country with no neighboring countries
        Country ownCountry = new Country("OwnCountry", "Continent");
        player.getOwnedCountries().put(ownCountry.getCountryId().toLowerCase(), ownCountry);

        // Now, test the attackToCountry method
        Country attackingCountry = randomStrategy.attackFromCountry();
        Country defendingCountry = randomStrategy.attackToCountry(attackingCountry);

        assertNull("Defending country should be null when no neighbors are present", defendingCountry);
    }
    
    

}
