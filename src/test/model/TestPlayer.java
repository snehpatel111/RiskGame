import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestPlayer {

    Player player;

    @Before
    public void setUp() {
        player = new Player("Alice");
    }

    @Test
    public void testPlayerName() {
        assertEquals("Alice", player.getPlayerName());
        
        player.setPlayerName("Bob");
        assertEquals("Bob", player.getPlayerName());
    }

     /**
     * Test for Add a country to the player's owned countries
     */
    @Test
    public void testOwnedCountries() {
        assertEquals(0, player.getOwnedCountries().size());

        CountryDetails country = new CountryDetails("Canada");
        player.getOwnedCountries().put("Canada", country);

        assertEquals(1, player.getOwnedCountries().size());
        assertTrue(player.getOwnedCountries().containsKey("Canada"));
    }

     /**
     * Test for Add a continent to the player's owned continents
     */
    @Test
    public void testOwnedContinents() {
        assertEquals(0, player.getOwnedContinents().size());

        Continent continent = new Continent("Europe", 5);
        player.getOwnedContinents().put("Europe", continent);

        assertEquals(1, player.getOwnedContinents().size());
        assertTrue(player.getOwnedContinents().containsKey("Europe"));
    }

    @Test
    public void testOwnedArmies() {
        assertEquals(0, player.getOwnedArmies());

        player.setOwnedArmies(10);

        assertEquals(10, player.getOwnedArmies());
    }
}
