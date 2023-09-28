package main.java.com.riskgame.model;

import java.util.HashMap;

/**
 * Contains details of the map like continents, countries etc during the game.
 * 
 * @author Sneh
 */
public class GameMap {
    private String d_mapName;
    private boolean d_isValid;
    private HashMap<String, Continent> d_continents;
    private HashMap<String, Country> d_countries;

    /**
     * Initialize GameMap object with default values.
     */
    public GameMap() {
        this.d_mapName = "";
        this.d_isValid = false;
        this.d_continents = new HashMap<>();
        this.d_countries = new HashMap<>();
    }

    /**
     * Initialize GameMap object with given map name and default values.
     */
    public GameMap(String p_mapName) {
        this.d_mapName = p_mapName;
        this.d_isValid = false;
        this.d_continents = new HashMap<>();
        this.d_countries = new HashMap<>();
    }

    /**
     * Returns the map of continents.
     * 
     * @return Returns the map of continents.
     */
    public HashMap<String, Continent> getContinents() {
        return this.d_continents;
    }

    /**
     * Returns countries of continent.
     * @return Returns countries of continent.
     */
    public HashMap<String, Country> getCountries() {
        return this.d_countries;
    }
}
