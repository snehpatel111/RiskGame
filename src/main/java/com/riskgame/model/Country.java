package main.java.com.riskgame.model;

import java.util.HashMap;

public class Country {
    public int d_index;
    private String d_countryId;
    private String d_belongingContinent;
    private HashMap<String, Country> d_neighbors;
    private int d_xCoOrdinate;
    private int d_yCoOrdinate;
    private int d_numberOfArmies;

    /**
     * Initialize Country object with default values.
     */
    public Country() {
    }

    /**
     * Initialize Country object with given arguments.
     * 
     * @param p_countryId          ID of the country
     * @param p_belongingContinent Name of the continent to which this country
     *                             belongs.
     */
    public Country(String p_countryId, String p_belongingContinent) {
        this.d_countryId = p_countryId;
        this.d_belongingContinent = p_belongingContinent;
        this.d_neighbors = new HashMap<String, Country>();
        this.d_numberOfArmies = 0;
    }

    /**
     * Initialize Country object as per passed parameters.
     * This constructor is used while reading ".map" files.
     * 
     * @param p_index          Index in the ".map" file
     * @param p_countryId      ID of the country
     * @param p_continentIndex Index of the continent this country belongs to
     * @param p_xCoOrdinate    X-Coordinate on GUI map
     * @param p_yCoOrdinate    y-Coordinate on GUI map
     * @param p_gameMap        GameMap in which this country resides
     */
    public Country(String p_index, String p_countryId, String p_continentIndex, String p_xCoOrdinate,
            String p_yCoOrdinate, GameMap p_gameMap) {
        this.d_index = Integer.parseInt(p_index);
        this.d_countryId = p_countryId;
        for (Continent continent : p_gameMap.getContinents().values()) {
            if (continent.getMapIndex() == Integer.parseInt(p_continentIndex)) {
                this.d_belongingContinent = continent.getContinentId();
                // break;
            }
        }
        this.d_neighbors = new HashMap<String, Country>();
        this.d_xCoOrdinate = Integer.parseInt(p_xCoOrdinate);
        this.d_yCoOrdinate = Integer.parseInt(p_yCoOrdinate);
        this.d_numberOfArmies = 0;
    }

    /**
     * Returns name of country.
     * 
     * @return Returns name of country.
     */
    public String getCountryId() {
        return this.d_countryId;
    }

    /**
     * Returns neighbors of country.
     * 
     * @return Returns neighbors of country.
     */
    public HashMap<String, Country> getNeighbors() {
        return this.d_neighbors;
    }

    public String getBelongingContinent() {
        return this.d_belongingContinent;
    }

    /**
     * Returns is neighbor removed or not
     * 
     * @param p_gameMap           GameMap object containing countries and
     *                            corresponding neighbors.
     * @param p_neighborCountryId Name of neighbor country to be removed from map.
     * @return Return true if neighbor is removed, otherwise false.
     */
    public boolean isNeighborRemoved(GameMap p_gameMap, String p_neighborCountryId) {
        if (p_gameMap.getCountries().containsKey(this.d_countryId.toLowerCase())
                && p_gameMap.getCountries().containsKey(p_neighborCountryId.toLowerCase())) {
            Country l_country1 = p_gameMap.getCountries().get(this.d_countryId.toLowerCase());
            Country l_country2 = p_gameMap.getCountries().get(p_neighborCountryId.toLowerCase());

            // check if both countries are neighbor or not.
            if (l_country1.getNeighbors().containsKey(l_country2.getCountryId().toLowerCase())) {
                l_country1.getNeighbors().remove(p_neighborCountryId.toLowerCase());
                System.out.println(this.d_countryId + " remove as neighbor to " + p_neighborCountryId);
            }
            if (l_country2.getNeighbors().containsKey(l_country1.getCountryId().toLowerCase())) {
                l_country2.getNeighbors().remove(this.d_countryId.toLowerCase());
                System.out.println(p_neighborCountryId + " remove as neighbor to " + this.d_countryId);
            }
            return true;
        } else {
            if (!p_gameMap.getCountries().containsKey(this.d_countryId.toLowerCase())
                    && !p_gameMap.getCountries().containsKey(p_neighborCountryId.toLowerCase()))
                System.out.println(this.d_countryId + " and " + p_neighborCountryId + "  does not exist.");
            else if (!p_gameMap.getCountries().containsKey(this.d_countryId.toLowerCase()))
                System.out.println(this.d_countryId + " does not exist.");
            else
                System.out.println(p_neighborCountryId + " does not exist.");
            return false;
        }
    }
}
