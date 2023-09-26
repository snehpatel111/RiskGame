package main.java.com.riskgame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import main.java.com.riskgame.model.Country;
import main.java.com.riskgame.utility.Constant;
import main.java.com.riskgame.utility.MapValidator;
import main.java.com.riskgame.utility.Util;

/**
 * Contains details about continents like control value, color, countries,
 * continent id.
 * All countries belonging to this continent are stored in a HashMap, where key
 * is country id and value is Country object.
 */
public class Continent {
    private int d_controlValue;
    private String d_continentColor;
    private String d_continentId;
    private int d_mapIndex;
    private HashMap<String, Country> d_countries;

    /**
     * Constructor to initialize Continent object with default values.
     */
    public Continent() {
    }

    /**
     * Creates Continent object with given arguments.
     * This constructor will be used when loading <code>.map</code> files
     * 
     * @param p_continentId    Name of continent
     * @param p_controlValue   Control value for continent
     * @param p_continentColor Color of continent
     */
    public Continent(String p_continentId, int p_controlValue, String p_continentColor) {
        this.d_continentId = p_continentId;
        this.d_controlValue = p_controlValue;
        this.d_continentColor = p_continentColor;
        this.d_countries = new HashMap<>();
    }

    /**
     * Creates Continent object using continent id and control value (used in
     * editMap).
     * Default color is set to "000".
     * 
     * @param p_continentId  Name of continent
     * @param p_controlValue Control value for continent
     */
    public Continent(String p_continentId, int p_controlValue) {
        this(p_continentId, p_controlValue, "000");
    }

    /**
     * Returns continent id.
     * 
     * @return Returns continent id.
     */
    public String getContinentId() {
        return this.d_continentId;
    }

    /**
     * Returns countries of continent.
     * 
     * @return Returns countries of continent.
     */
    public HashMap<String, Country> getCountries() {
        return this.d_countries;
    }

    /**
     * Returns index value of the continent.
     * 
     * @return Returns index of continent.
     */
    public int getMapIndex() {
        return this.d_mapIndex;
    }

    /**
     * Edit an existing continent.
     * 
     * @param p_gameMap   GameMap object to add/remove continent to.
     * @param p_gamePhase GamePhase object to edit continent in.
     * @param p_args      Command line arguments to edit continent.
     */
    public void editContinent(GameMap p_gameMap, Phase p_gamePhase, String[] p_args) {
        try {
            if (p_args[1].equals("-add")) {
                if (Util.isAlphabetic(p_args[2])) {
                    this.d_continentId = p_args[2];
                } else {
                    System.out.println(Constant.ERROR_COLOR + "Invalid Continent Id" + Constant.RESET_COLOR);
                }
                this.d_controlValue = Integer.parseInt(p_args[3]);

                boolean l_isContinentAdded = this.isContinentAdded(p_gameMap, this.d_continentId,
                        this.d_controlValue);
                if (l_isContinentAdded) {
                    System.out.println(Constant.SUCCESS_COLOR + this.d_continentId + " added to the map"
                            + Constant.RESET_COLOR);
                    p_gamePhase = Phase.EDITMAP;
                } else {
                    System.out.println(this.d_continentId + " already exists - Please add valid Continent Id");
                }
            } else if (p_args[1].equals("-remove")) {
                if (Util.isAlphabetic(p_args[2])) {
                    this.d_continentId = p_args[2];
                } else {
                    System.out.println(Constant.ERROR_COLOR + "Invalid Continent Id" + Constant.RESET_COLOR);
                }
                boolean l_isContinentRemoved = this.removeContinent(p_gameMap);
                if (l_isContinentRemoved) {
                    System.out.println(
                            Constant.SUCCESS_COLOR + this.d_continentId + " removed from map" + Constant.RESET_COLOR);
                    p_gamePhase = Phase.EDITMAP;
                } else
                    System.out.println(this.d_continentId + " doesn't exist - Please enter valid Continent Id");
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println(
                    Constant.ERROR_COLOR
                            + "Invalid command - It should be of the form: editcontinent -add continentId controlvalue or editcontinent -remove continentId"
                            + Constant.RESET_COLOR);
        } catch (Exception e) {
            System.out.println(
                    Constant.ERROR_COLOR
                            + "Invalid command - it should be of the form: editcontinent -add continentId controlvalue or editcontinent -remove continentId"
                            + Constant.RESET_COLOR);
        }
    }

    public boolean removeContinent(GameMap p_gameMap) {
        if (p_gameMap.getContinents().containsKey(this.d_continentId.toLowerCase())) {
            Continent l_continent = p_gameMap.getContinents().get(this.d_continentId.toLowerCase());

            // remove each country of the continent
            ArrayList<Country> l_removeCountryList = new ArrayList<Country>();
            for (Country l_country : l_continent.getCountries().values()) {
                l_removeCountryList.add(l_country);
            }
            Iterator<Country> l_itr = l_removeCountryList.listIterator();
            while (l_itr.hasNext()) {
                Country l_country = l_itr.next();
                if (!this.isCountryRemovedFromContinent(p_gameMap, l_country.getCountryId()))
                    return false;
            }
            p_gameMap.getContinents().remove(this.d_continentId.toLowerCase());
            return true;
        } else {
            System.out.println(this.d_continentId + " does not exist.");
            return false;
        }
    }

    public boolean isCountryRemovedFromContinent(GameMap p_gameMap, String p_countryId) {
        if (p_gameMap.getCountries().containsKey(p_countryId.toLowerCase())) {
            Country l_country = p_gameMap.getCountries().get(p_countryId.toLowerCase());
            ArrayList<Country> l_removeNeighborList = new ArrayList<Country>();

            // remove each neighbor of this country
            for (Country l_neighbor : l_country.getNeighbors().values()) {
                l_removeNeighborList.add(l_neighbor);
            }
            Iterator<Country> l_itr = l_removeNeighborList.listIterator();
            while (l_itr.hasNext()) {
                Country l_neighbor = l_itr.next();
                if (!l_country.isNeighborRemoved(p_gameMap, l_neighbor.getCountryId()))
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

    public void editCountry(GameMap p_gameMap, Phase p_gamePhase, String[] p_args) {
        try {
            Country country = new Country();
            String l_countryId = null;
            String l_continentId = null;
            if (p_args[1].equals("-add")) {
                if (!Util.isAlphabetic(p_args[2]) || !Util.isAlphabetic(p_args[3])) {
                    System.out.println(Constant.ERROR_COLOR + "Invalid country/continent name" + Constant.RESET_COLOR);
                }
                l_countryId = p_args[2];
                l_continentId = p_args[3];
                boolean l_isCountryAdded = country.isCountryAdded(p_gameMap, l_countryId, l_continentId);
                if (l_isCountryAdded) {
                    System.out.println(Constant.SUCCESS_COLOR + l_countryId + " added to " + l_continentId
                            + Constant.RESET_COLOR);
                    p_gamePhase = Phase.EDITMAP;
                } else {
                    System.out.println(l_countryId + " already exists - Please add valid Country Id");
                }
            } else if (p_args[1].equals("-remove")) {
                if (!Util.isAlphabetic(p_args[2])) {
                    System.out.println(Constant.ERROR_COLOR + "Invalid country name" + Constant.RESET_COLOR);
                }
                l_countryId = p_args[2];
                boolean l_isCountryRemoved = country.removeCountry(p_gameMap, l_countryId);
                if (l_isCountryRemoved) {
                    System.out
                            .println(Constant.SUCCESS_COLOR + l_countryId + " removed"
                                    + Constant.RESET_COLOR);
                    p_gamePhase = Phase.EDITMAP;
                } else {
                    System.out.println(l_countryId + " does not exist - Please enter valid country name");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(
                    Constant.ERROR_COLOR
                            + "Invalid command - It should be of the form: editcountry -add countryId continentId or editcountry -remove countryId"
                            + Constant.RESET_COLOR);
        } catch (Exception e) {
            System.out.println(
                    Constant.ERROR_COLOR
                            + "Invalid command - It should be of the form: editcountry -add countryId continentId or editcountry -remove countryId"
                            + Constant.RESET_COLOR);
        }
    }

    /**
     * Checks if given continent already exists in the game map. If not, adds
     * continent in the map.
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
