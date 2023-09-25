package main.java.com.riskgame.utility;

import org.jgrapht.*;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import main.java.com.riskgame.model.Continent;
import main.java.com.riskgame.model.Country;
import main.java.com.riskgame.model.GameMap;

/**
 * Helper method to validate game map.
 */
public class MapValidator {

    // Represents game map according to JGraphT format.
    private Graph<Country, DefaultEdge> d_mapGraph;

    public MapValidator() {
        this.d_mapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    /**
     * Checks if given continent already exists in the game map.
     * 
     * @param p_gameMap     GameMap object containing current states of continents
     *                      and countries.
     * @param p_continentId Name of continent.
     * @return Returns true if continent exists in the map, false otherwise.
     */
    public boolean isContinentExist(GameMap p_gameMap, String p_continentId) {
        if (p_gameMap.getContinents().containsKey(p_continentId.toLowerCase()))
            return true;
        else
            return false;
    }

    /**
     * Validates if the given continent can be added to the game map.
     * 
     * @param p_gameMap      GameMap object containing current states of continents
     *                       and countries.
     * @param p_continentId  Name of continent.
     * @param p_controlValue Control value of continent.
     * @return True if continent can be added to map, false otherwise.
     */
    public boolean isContinentAdded(GameMap p_gameMap, String p_continentId, int p_controlValue) {
        if (!(this.isContinentExist(p_gameMap, p_continentId))) {
            if (p_controlValue < 0)
                return false;
            Continent l_continent = new Continent(p_continentId, p_controlValue);
            p_gameMap.getContinents().put(p_continentId.toLowerCase(), l_continent);
            return true;
        } else {
            return false;
        }
    }
}
