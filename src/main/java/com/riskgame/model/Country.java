package com.riskgame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.riskgame.utility.Constant;

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
     * @param p_countryId          Id of the country
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
     * @param p_countryId      Id of the country
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
     * Returns the index of country in the ".map" file
     * 
     * @return Returns index of the country
     */
    public int getIndex() {
        return this.d_index;
    }

    /**
     * Returns neighbors of country.
     * 
     * @return Returns neighbours of country
     */
    public HashMap<String, Country> getNeighbours() {
        return this.d_neighbors;
    }

    /**
     * Returns x-coordinate of the country on map
     * 
     * @return Returns x-coordinate of map
     */
    public int getXCoOrdinate() {
        return this.d_xCoOrdinate;
    }

    /**
     * Set the x-coordinate of country on map
     * 
     * @param p_xCoOrdinate X-coordinate of country
     */
    public void setXCoOrdinate(int p_xCoOrdinate) {
        this.d_xCoOrdinate = p_xCoOrdinate;
    }

    /**
     * Returns y-coordinate of the country on map
     * 
     * @return Returns x-coordinate of map
     */
    public int getYCoOrdinate() {
        return this.d_yCoOrdinate;
    }

    /**
     * Set the y-coordinate of country on map
     * 
     * @param p_yCoOrdinate X-coordinate of country
     */
    public void setYCoOrdinate(int p_yCoOrdinate) {
        this.d_yCoOrdinate = p_yCoOrdinate;
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
     * Getter method to get number of armies in the country.
     * 
     * @return returns d_numberOfArmies
     */
    public int getNumberOfArmies() {
        return this.d_numberOfArmies;
    }

    /**
     * Setter method to set number of armies deployed at the country
     * 
     * @param p_numberOfArmies Number of armies to be deployed
     */
    public void setNumberOfArmies(int p_numberOfArmies) {
        this.d_numberOfArmies = p_numberOfArmies;
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
            Country l_country = p_gameMap.getCountries().get(this.d_countryId.toLowerCase());
            Country l_neighborCountry = p_gameMap.getCountries().get(p_neighborCountryId.toLowerCase());

            // check if both countries are neighbor or not.
            if (l_country.getNeighbors().containsKey(l_neighborCountry.getCountryId().toLowerCase())) {
                l_country.getNeighbors().remove(p_neighborCountryId.toLowerCase());
                System.out.println(Constant.SUCCESS_COLOR + this.d_countryId + " remove as neighbor of "
                        + p_neighborCountryId + Constant.RESET_COLOR);
            }
            if (l_neighborCountry.getNeighbors().containsKey(l_country.getCountryId().toLowerCase())) {
                l_neighborCountry.getNeighbors().remove(this.d_countryId.toLowerCase());
                System.out.println(Constant.SUCCESS_COLOR + p_neighborCountryId + " remove as neighbor of "
                        + this.d_countryId + Constant.RESET_COLOR);
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

    /**
     * Checks if country already exists in the game map. If not, adds country in the
     * map.
     * 
     * @param p_gameMap     GamePhase object containing continents and countries.
     * @param p_countryId   Name of country to be checked.
     * @param p_continentId Name of continent in which given country is being
     *                      checked.
     * @return Returns true if country is added, otherwise false.
     */
    public boolean isCountryAdded(GameMap p_gameMap, String p_countryId, String p_continentId) {
        if (!this.isCountryExist(p_gameMap, p_countryId)) {
            if (!p_gameMap.getContinents().containsKey(p_continentId.toLowerCase())) {
                System.out.println(p_continentId + " does not exist.");
                return false;
            }
            Country l_country = new Country(p_countryId, p_continentId);
            Continent l_continent = p_gameMap.getContinents().get(p_continentId.toLowerCase());
            l_continent.getCountries().put(p_countryId.toLowerCase(), l_country);
            p_gameMap.getCountries().put(p_countryId.toLowerCase(), l_country);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if country exist in map.
     * 
     * @param p_gameMap   GameMap object that contains continents and countries.
     * @param p_countryId Name of country to be checked.
     * @return Returns true if country exist in the map, otherwise false.
     */
    public boolean isCountryExist(GameMap p_gameMap, String p_countryId) {
        return p_gameMap.getCountries().containsKey(p_countryId.toLowerCase());
    }

    /**
     * Remove country from map.
     * 
     * @param p_gameMap   GameMap object containing continents and countries.
     * @param p_countryId Name of country to be removed.
     * @return Return true, if country is deleted otherwise false.
     */
    public boolean removeCountry(GameMap p_gameMap, String p_countryId) {
        if (p_gameMap.getCountries().containsKey(p_countryId.toLowerCase())) {
            Country l_country = p_gameMap.getCountries().get(p_countryId.toLowerCase());
            ArrayList<Country> l_removeCountryList = new ArrayList<Country>();

            // remove each neighbor of country
            for (Country l_neighbor : l_country.getNeighbors().values()) {
                l_removeCountryList.add(l_neighbor);
            }
            Iterator<Country> l_itr = l_removeCountryList.listIterator();
            while (l_itr.hasNext()) {
                Country l_neighbor = l_itr.next();
                if (!this.removeCountryNeighbor(p_gameMap, l_country.getCountryId(), l_neighbor.getCountryId()))
                    return false;
            }
            p_gameMap.getCountries().remove(p_countryId.toLowerCase());
            p_gameMap.getContinents().get(l_country.getBelongingContinent().toLowerCase()).getCountries()
                    .remove(p_countryId.toLowerCase());
            return true;
        } else {
            System.out.println(p_countryId + " does not exist.");
            return false;
        }
    }

    /**
     * Remove neighboring country of given country.
     * Remove link between teo given countries.
     * 
     * @param p_gameMap           GameMap object containing continents and
     *                            countries, and neighbors of each country.
     * @param p_countryId         Name of country whose neighbor is going to be
     *                            deleted.
     * @param p_neighborCountryId Neighbor country name to be deleted as neighbor of
     *                            given country
     * @return Returns true, if neighbor is deleted successfully, otherwise false.
     */
    public boolean removeCountryNeighbor(GameMap p_gameMap, String p_countryId, String p_neighborCountryId) {
        if (p_gameMap.getCountries().containsKey(p_countryId.toLowerCase())
                && p_gameMap.getCountries().containsKey(p_neighborCountryId.toLowerCase())) {
            Country l_country = p_gameMap.getCountries().get(p_countryId.toLowerCase());
            Country l_neighborCountry = p_gameMap.getCountries().get(p_neighborCountryId.toLowerCase());

            // Check if both countries are neighbor of each other
            if (l_country.getNeighbors().containsKey(l_neighborCountry.getCountryId().toLowerCase())) {
                l_country.getNeighbors().remove(p_neighborCountryId.toLowerCase());
                System.out.println(Constant.SUCCESS_COLOR + p_countryId + " removed as neighbor of "
                        + p_neighborCountryId + Constant.RESET_COLOR);
            }
            if (l_neighborCountry.getNeighbors().containsKey(l_country.getCountryId().toLowerCase())) {
                l_neighborCountry.getNeighbors().remove(p_countryId.toLowerCase());
                System.out.println(Constant.SUCCESS_COLOR + p_neighborCountryId + " removed as neighbor of "
                        + p_countryId + Constant.RESET_COLOR);
            }
            return true;
        } else {
            if (!p_gameMap.getCountries().containsKey(p_countryId.toLowerCase())
                    && !p_gameMap.getCountries().containsKey(p_neighborCountryId.toLowerCase()))
                System.out.println(p_countryId + " and " + p_neighborCountryId + "  does not exist.");
            else if (!p_gameMap.getCountries().containsKey(p_countryId.toLowerCase()))
                System.out.println(p_countryId + " does not exist.");
            else
                System.out.println(p_neighborCountryId + " does not exist.");
            return false;
        }
    }

    /**
     * Checks if non-existing neighbor is added to given country as neighbor.
     * Adds a link between two given given countries
     * 
     * @param p_gameMap           GameMap object with continents, countries, and
     *                            their respective neighbors.
     * @param p_countryId         Name of country whose neighbors are being checked.
     * @param p_neighborCountryId Name of neighbor country.
     * @return Return true, if country is added as neighbor, otherwise false.
     */
    public boolean isNeighborCountryAdded(GameMap p_gameMap, String p_countryId, String p_neighborCountryId) {
        if (p_gameMap.getCountries().containsKey(p_countryId.toLowerCase())
                && p_gameMap.getCountries().containsKey(p_neighborCountryId.toLowerCase())) {
            Country l_country = p_gameMap.getCountries().get(p_countryId.toLowerCase());
            Country l_neighborCountry = p_gameMap.getCountries().get(p_neighborCountryId.toLowerCase());

            // check if both countries are neighbor of each other or not
            if (!l_country.getNeighbors().containsKey(l_neighborCountry.getCountryId().toLowerCase())) {
                l_country.getNeighbors().put(p_neighborCountryId.toLowerCase(), l_neighborCountry);
                System.out.println(Constant.SUCCESS_COLOR + p_countryId + " added as neighbor of " + p_neighborCountryId
                        + Constant.RESET_COLOR);
            } else {
                System.out.println(Constant.ERROR_COLOR + p_countryId + " is already a neighbor of "
                        + p_neighborCountryId + Constant.RESET_COLOR);
            }
            if (!l_neighborCountry.getNeighbors().containsKey(l_country.getCountryId().toLowerCase())) {
                l_neighborCountry.getNeighbors().put(p_countryId.toLowerCase(), l_country);
                System.out.println(Constant.SUCCESS_COLOR + p_neighborCountryId + " added as neighbor of " + p_countryId
                        + Constant.RESET_COLOR);
            } else {
                System.out.println(Constant.ERROR_COLOR + p_neighborCountryId + " is already a neighbor of "
                        + p_countryId + Constant.RESET_COLOR);
            }
            return true;
        } else {
            if (!p_gameMap.getCountries().containsKey(p_countryId.toLowerCase())
                    && !p_gameMap.getCountries().containsKey(p_neighborCountryId.toLowerCase()))
                System.out.println(p_countryId + " or " + p_neighborCountryId
                        + "  does not exist. Create country first and then set their neighbors.");
            else if (!p_gameMap.getCountries().containsKey(p_countryId.toLowerCase()))
                System.out.println(p_countryId + " does not exist. Create country first and then set its neighbors.");
            else
                System.out.println(
                        p_neighborCountryId + " does not exist. Create country first and then set its neighbors.");
            return false;
        }
    }

    /**
     * Checks if given countries are neighbors of each other.
     * 
     * @param p_gameMap           GameMap object
     * @param p_countryId         Country whose neighbors are being checked.
     * @param p_neighborCountryId Neighbor country to check.
     * @return True if countries are neighbors, false otherwise.
     */
    public boolean isNeighbor(GameMap p_gameMap, String p_countryId, String p_neighborCountryId) {
        if (p_gameMap.getCountries().containsKey(p_countryId.toLowerCase())
                && p_gameMap.getCountries().containsKey(p_neighborCountryId.toLowerCase())) {
            Country l_country = p_gameMap.getCountries().get(p_countryId.toLowerCase());
            Country l_neighborCountry = p_gameMap.getCountries().get(p_neighborCountryId.toLowerCase());

            return l_country.getNeighbors().containsKey(l_neighborCountry.getCountryId().toLowerCase())
                    && l_neighborCountry.getNeighbors().containsKey(l_country.getCountryId().toLowerCase());
        }
        return false;
    }
}
