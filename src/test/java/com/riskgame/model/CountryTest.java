import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestCountry {

    GameMap d_map;
    String d_countryId;
    String d_belongingContinent;
    Country d_Country;

    @Before
    public void before() {
        d_map = new GameMap("ameroki.map");
        d_countryId = "country1";
        d_belongingContinent = "continent1";
        d_country = new Country(d_countryId, d_belongingContinent);
    }

      /**
     * Test case for adding country
     */
    @Test
    public void testAddCountry() {
        boolean isAdded = d_country.isCountryAdded(d_map, d_countryId, d_belongingContinent);
        assertTrue(isAdded);
    }

      /**
     * Test case for adding existing country
     */
    @Test
    public void testAddExistingCountry() {
        // Add the country once
        boolean isAdded = d_country.isCountryAdded(d_map, d_countryId, d_belongingContinent);
        assertTrue(isAdded);

        // Try to add the same country again
        isAdded = d_country.isCountryAdded(d_map, d_countryId, d_belongingContinent);
        assertFalse(isAdded);
    }

      /**
     * Test case for removing country
     */
    @Test
    public void testRemoveCountry() {
        // Add the country first
        boolean isAdded = d_country.isCountryAdded(d_map, d_countryId, d_belongingContinent);
        assertTrue(isAdded);

        // Remove the country
        boolean isRemoved = d_country.removeCountry(d_map, d_countryId);
        assertTrue(isRemoved);
    }

      /**
     * Test case for removing non existing country
     */
    @Test
    public void testRemoveNonexistentCountry() {
        // Try to remove a country that doesn't exist
        boolean isRemoved = d_country.removeCountry(d_map, "nonexistentCountry");
        assertFalse(isRemoved);
    }

      /**
     * Test case for adding neighbour country
     */
    @Test
    public void testAddNeighborCountry() {
        String neighborCountryId = "neighborCountry";
        boolean isAdded = d_country.isNeighborCountryAdded(d_map, d_countryId, neighborCountryId);
        assertTrue(isAdded);
    }

      /**
     * Test case for adding existing neighbour country
     */
    @Test
    public void testAddExistingNeighborCountry() {
        String neighborCountryId = "neighborCountry";
        // Add the neighbor country once
        boolean isAdded = d_country.isNeighborCountryAdded(d_map, d_countryId, neighborCountryId);
        assertTrue(isAdded);

        // Try to add the same neighbor country again
        isAdded = d_country.isNeighborCountryAdded(d_map, d_countryId, neighborCountryId);
        assertFalse(isAdded);
    }

      /**
     * Test case for removing neighbour country
     */
    @Test
    public void testRemoveNeighborCountry() {
        String neighborCountryId = "neighborCountry";
        // Add the neighbor country first
        boolean isAdded = d_country.isNeighborCountryAdded(d_map, d_countryId, neighborCountryId);
        assertTrue(isAdded);

        // Remove the neighbor country
        boolean isRemoved = d_country.removeCountryNeighbor(d_map, d_countryId, neighborCountryId);
        assertTrue(isRemoved);
    }

      /**
     * Test case for removing non existing neighbour country
     */
    @Test
    public void testRemoveNonexistentNeighborCountry() {
        // Try to remove a neighbor country that doesn't exist
        boolean isRemoved = d_country.removeCountryNeighbor(d_map, d_countryId, "nonexistentNeighborCountry");
        assertFalse(isRemoved);
    }

      /**
     * Test case for checking if a neighbor country
     */
    @Test
    public void testAreCountriesNeighbors() {
        String neighborCountryId = "neighborCountry";
        // Add the neighbor country first
        boolean isAdded = d_country.isNeighborCountryAdded(d_map, d_countryId, neighborCountryId);
        assertTrue(isAdded);

        // Check if the countries are neighbors
        boolean areNeighbors = d_country.isNeighbor(d_map, d_countryId, neighborCountryId);
        assertTrue(areNeighbors);
    }

      /**
     * Test case for checking non neighbor countries
     */
    @Test
    public void testAreCountriesNotNeighbors() {
        String neighborCountryId = "neighborCountry";
        // Check if the countries are not neighbors without adding them as neighbors
        boolean areNeighbors = d_country.isNeighbor(d_map, d_countryId, neighborCountryId);
        assertFalse(areNeighbors);
    }
}
