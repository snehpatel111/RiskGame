package com.riskgame.model;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class CountryTest {

    GameMap d_gameMap;
    String d_countryId;
    String d_belongingContinent;
    Country d_country;

    @Before
    public void before() {
        this.d_gameMap = new GameMap("ameroki.map");
        this.d_countryId = "siberia";
        this.d_belongingContinent = "azio";
        this.d_country = new Country();
    }

    /**
     * Test case for removing non existing country
     */
    @Test
    public void testRemoveNonExistentCountry() {
        // Try to remove a country that doesn't exist
        boolean l_isCountryRemoved = this.d_country.removeCountry(this.d_gameMap, "nonExistentCountry");
        assertFalse(l_isCountryRemoved);
    }

    /**
     * Test case for removing non existing neighbor country
     */
    @Test
    public void testRemoveNonExistentNeighborCountry() {
        // Try to remove a neighbor country that doesn't exist
        boolean l_isCountryRemoved = this.d_country.removeCountryNeighbor(this.d_gameMap, this.d_countryId,
                "nonExistentNeighborCountry");
        assertFalse(l_isCountryRemoved);
    }

    /**
     * Test case for checking non neighbor countries
     */
    @Test
    public void testAreCountriesNotNeighbors() {
        String l_neighborCountryId = "neighborCountry";
        // Check if the countries are not neighbors without adding them as neighbors
        boolean l_areNeighbors = this.d_country.isNeighbor(this.d_gameMap, this.d_countryId, l_neighborCountryId);
        assertFalse(l_areNeighbors);
    }
}
