import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestContinent {

    GameMap d_map;
    String d_continentId;
    int d_controlValue;
    RunGameEngine d_Rgame;

    @Before
    public void before() {
        d_map = new GameMap("ameroki.map");
        d_Rgame = new RunGameEngine();
        d_continentId = "eurozio";
        d_controlValue = 5;
        d_Rgame.addContinent(d_map, "azio", d_controlValue);
    }

     /**
     * Test cases for adding continent
     */
    @Test
    public void testAddContinent() {
        d_map = d_Rgame.editMap("ameroki.map");
        System.out.println(d_map.getMapName());
        System.out.println(d_map.getContinents().size());

        boolean l_check = d_Rgame.addContinent(d_map, d_continentId, d_controlValue);
        assertTrue(l_check);
    }

     /**
     * Test case for Remove continent
     */
    @Test
    public void testRemoveContinent() {
        d_map = d_Rgame.editMap("ameroki.map");
        // azio is in the map, so it'll be removed
        d_continentId = "azio";
        boolean l_check = d_Rgame.removeContinent(d_map, d_continentId);
        assertTrue(l_check);

        // Asia not part of the map.
        d_continentId = "Asia";
        l_check = d_Rgame.removeContinent(d_map, d_continentId);
        assertFalse(l_check);
    }

     /**
     * Test case for checking existing continent
     */
    @Test
    public void testEditContinentAddingExistingContinent() {
        String continentId = "azio";
        int controlValue = 7;
        d_Rgame.addContinent(d_map, continentId, controlValue);
        
        String[] args = {"editcontinent", "-add", continentId, Integer.toString(controlValue)};
        d_map.getContinents().clear(); // Clear existing continents
        d_Rgame.editContinent(d_map, Phase.EDITMAP, args);
        
        assertEquals(controlValue, d_map.getContinents().get(continentId.toLowerCase()).getControlValue());
    }

     /**
     *  Test for checking that no existing continent are removed when we remove non existing continent
     */
    @Test
    public void testEditContinentRemovingNonexistentContinent() {
        String[] args = {"editcontinent", "-remove", "NonexistentContinent"};
        d_map.getContinents().clear(); // Clear existing continents
        d_Rgame.editContinent(d_map, Phase.EDITMAP, args);

        assertTrue(d_map.getContinents().isEmpty());
    }

     /**
     * Test case for editing continent with invalid arguments
     */
    @Test
    public void testEditContinentWithInvalidArguments() {
        String[] args = {"editcontinent", "-add", "Invalid$Continent", "4"};
        d_map.getContinents().clear(); 
        d_Rgame.editContinent(d_map, Phase.EDITMAP, args);

        assertTrue(d_map.getContinents().isEmpty());
    }
}
