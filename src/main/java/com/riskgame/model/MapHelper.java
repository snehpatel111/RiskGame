package com.riskgame.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.riskgame.utility.Constant;
import com.riskgame.utility.MapValidator;

/**
 * This class contains helper functions related to Map.
 */
public class MapHelper {
    public static int d_mapIndex = 1;
    public GameMap d_gameMap;
    private HashMap<Integer, Country> d_countryList;

    /**
     * Helper method to add/remove a new continent to the game map.
     * 
     * @param p_mapFileName Name of map file.
     * @return Edited game map
     */
    public GameMap editMap(String p_mapFileName) {
        String l_filePath = Constant.MAP_PATH + p_mapFileName;
        this.d_gameMap = new GameMap(p_mapFileName);
        File l_file = new File(l_filePath);

        if (l_file.exists()) {
            System.out.println(p_mapFileName + " map file exists. You can edit it.");
            this.readMap(l_filePath);
        } else {
            System.out.println(p_mapFileName + " does not exist.");
            System.out.println("Creating a new Map named: " + p_mapFileName);
            this.d_gameMap = new GameMap(p_mapFileName);
        }
        return this.d_gameMap;
    }

    /**
     * Loads map data from file and populates GameMap object.
     * 
     * @param p_mapFileName Name of map file
     * @return Returns GameMap object constructed from map file
     */
    public GameMap loadMap(String p_mapFileName) {
        String l_filePath = Constant.MAP_PATH + p_mapFileName;
        File l_file = new File(l_filePath);
        if (l_file.exists()) {
            this.d_gameMap = new GameMap(p_mapFileName);
            System.out.println("Loading map from " + p_mapFileName + "...");
            this.readMap(l_filePath);
            MapValidator l_mapValidator = new MapValidator();
            if (!l_mapValidator.isValidMap(this.d_gameMap)) {
                System.out.println(Constant.ERROR_COLOR
                        + "Map is not valid for playing. Try to correct map or choose from existing one"
                        + Constant.RESET_COLOR);
                return null;
            }
            System.out.println(
                    Constant.SUCCESS_COLOR + p_mapFileName + "loaded successfully." + Constant.RESET_COLOR);
            return this.d_gameMap;
        }
        System.out.println(Constant.ERROR_COLOR + p_mapFileName
                + " does not exist. Please check file path or create new map using editmap <mapName> command."
                + Constant.RESET_COLOR);
        return null;
    }

    /**
     * Reads <code>.map</code> files and create GameMap object accordingly.
     * 
     * @param p_mapFileName Name of .map file
     * @return Returns GameMap object of respective map
     */
    public void readMap(String p_mapFileName) {
        try {
            this.d_countryList = new HashMap<Integer, Country>();
            BufferedReader l_reader = new BufferedReader(new FileReader(p_mapFileName));
            String l_line;
            while ((l_line = l_reader.readLine()) != null) {
                if (l_line.equals("[continents]"))
                    l_reader = this.readContinent(l_reader);
                else if (l_line.equals("[countries]"))
                    l_reader = this.readCountries(l_reader);
                else if (l_line.equals("[borders]"))
                    l_reader = this.readBorders(l_reader);
            }
            l_reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(Constant.ERROR_COLOR + "FileNotFoundException" + Constant.RESET_COLOR);
            System.out.println(Constant.ERROR_COLOR + e.getMessage() + Constant.RESET_COLOR);
        } catch (IOException e) {
            System.out.println(Constant.ERROR_COLOR + "IOException" + Constant.RESET_COLOR);
            System.out.println(Constant.ERROR_COLOR + e.getMessage() + Constant.RESET_COLOR);
        }
    }

    /**
     * Read Continents from file
     * 
     * @param p_reader BufferReader object to read file
     * @return BufferReader object pointing end of continent section of .map file
     */
    private BufferedReader readContinent(BufferedReader p_reader) {
        try {
            String l_line;
            while (!((l_line = p_reader.readLine()).equals(""))) {
                String[] l_continentString = l_line.split("\\s+");

                if (Integer.parseInt(l_continentString[1]) >= 0) {
                    this.d_gameMap.getContinents().put(l_continentString[0].toLowerCase(),
                            new Continent(l_continentString[0], Integer.parseInt(l_continentString[1]),
                                    l_continentString[2]));
                    this.d_mapIndex++;
                } else {
                    System.out.println(
                            Constant.ERROR_COLOR + "Error reading continent from the file." + Constant.RESET_COLOR);
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.d_mapIndex = 1;
        return p_reader;
    }

    /**
     * Reads countries from <code>.map</code> file.
     * 
     * @param p_reader Stream pointing to countries section of .map file
     * @return BufferReader stream pointing end of countries section of .map file.
     */
    private BufferedReader readCountries(BufferedReader p_reader) {
        try {
            String l_line;
            while (!((l_line = p_reader.readLine()).equals(""))) {
                String[] l_countryList = l_line.split("\\s+");
                Country l_country = new Country(l_countryList[0], l_countryList[1], l_countryList[2], l_countryList[3],
                        l_countryList[4],
                        this.d_gameMap);
                try {
                    if (l_country.getBelongingContinent() == null) {
                        System.out.println(
                                Constant.ERROR_COLOR + "Error reading country from the file." + Constant.RESET_COLOR);
                        System.exit(-1);
                    }
                    // Add country to the appropriate continent in the map. Terminate if duplicate
                    // entry.
                    this.addCountryToContinent(l_country);
                    this.d_countryList.put(l_country.getIndex(), l_country);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p_reader;
    }

    /**
     * Reads borders from <code>.map</code> file.
     * 
     * @param p_reader Stream pointing to borders section of .map file
     * @return BufferReader stream pointing end of borders section of .map file.
     */
    private BufferedReader readBorders(BufferedReader p_reader) {
        try {
            String l_line;
            while ((l_line = p_reader.readLine()) != null) {
                if (!l_line.equals("")) {
                    String[] l_borderList = l_line.split("\\s+");
                    Country l_country = new Country();
                    l_country = this.d_countryList.get(Integer.parseInt(l_borderList[0]));
                    for (int l_neighborCount = 1; l_neighborCount < l_borderList.length; l_neighborCount++) {
                        this.addNeighbor(l_country, l_borderList[l_neighborCount]);

                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p_reader;
    }

    /**
     * Add country at given index as neighbor to given country.
     * Throw error if invalid neighbor is found and terminate the program.
     * 
     * @param p_country      Country to which neighbor is to be added.
     * @param p_countryIndex Index of the country to be added as a neighbor to the
     *                       argument country
     */
    private void addNeighbor(Country p_country, String p_countryIndex) {
        int l_borderIndex = Integer.parseInt(p_countryIndex);
        Country l_neighborCountry = new Country();
        try {
            l_neighborCountry = this.d_countryList.get(l_borderIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(Constant.ERROR_COLOR + "Found error reading the .map file" + Constant.RESET_COLOR);
            System.out.println(Constant.ERROR_COLOR + "The neighbor " + l_borderIndex + " does not exist."
                    + Constant.RESET_COLOR);
            System.exit(-1);
        }
        if (!p_country.getNeighbors().containsKey(l_neighborCountry.getCountryId().toLowerCase()))
            p_country.getNeighbors().put(l_neighborCountry.getCountryId().toLowerCase(), l_neighborCountry);
    }

    /**
     * Registers this new country as part of its continent.
     * If duplicate country exits the program throwing error.
     * 
     * @param p_country
     */
    private void addCountryToContinent(Country p_country) {
        Country country = new Country();
        if (!country.isCountryExist(this.d_gameMap, p_country.getCountryId())) {
            Continent l_belongingContinent = this.d_gameMap.getContinents()
                    .get(p_country.getBelongingContinent().toLowerCase());
            l_belongingContinent.getCountries().put(p_country.getCountryId().toLowerCase(), p_country);
            this.d_gameMap.getCountries().put(p_country.getCountryId().toLowerCase(), p_country);
        } else {
            System.out.println(Constant.ERROR_COLOR + "Error reading the file." + Constant.RESET_COLOR);
            System.out.println(Constant.ERROR_COLOR + "Two countries of same name exists in the same continent."
                    + Constant.RESET_COLOR);
            System.exit(-1);
        }
    }

    /**
     * Display Map as a text
     * 
     * @param p_gameMap GameMap object containing continents and countries
     * 
     */
    public void showMap(GameMap p_gameMap) {
        if (p_gameMap == null)
            return;
        System.out.printf("%85s\n",
                "-------------------------------------------------------------------------------------------");
        System.out.printf("%25s%25s%35s\n", "Continents", "Country", "Country's neighbors");
        System.out.printf("%85s\n",
                "-------------------------------------------------------------------------------------------");
        boolean l_printContinentName = true;
        boolean l_printCountryName = true;
        for (Continent l_continent : p_gameMap.getContinents().values()) {
            if (l_continent.getCountries().size() == 0) {
                System.out.printf("\n%25s%25s%25s\n", l_continent.getContinentId(), "", "");
            }
            for (Country l_country : l_continent.getCountries().values()) {
                if (l_country.getNeighbors().size() == 0) {
                    if (l_printContinentName && l_printCountryName) {
                        System.out.printf("\n%25s%25s%25s\n", l_continent.getContinentId(), l_country.getCountryId(),
                                "");
                        l_printContinentName = false;
                        l_printCountryName = false;
                    } else if (l_printCountryName) {
                        System.out.printf("\n%25s%25s%25s\n", "", l_country.getCountryId(), "");
                        l_printCountryName = false;
                    }
                }
                for (Country l_neighbor : l_country.getNeighbors().values()) {
                    if (l_printContinentName && l_printCountryName) {
                        System.out.printf("\n%25s%25s%25s\n", l_continent.getContinentId(), l_country.getCountryId(),
                                l_neighbor.getCountryId());
                        l_printContinentName = false;
                        l_printCountryName = false;
                    } else if (l_printCountryName) {
                        System.out.printf("\n%25s%25s%25s\n", "", l_country.getCountryId(), l_neighbor.getCountryId());
                        l_printCountryName = false;
                    } else {
                        System.out.printf("%25s%25s%25s\n", "", "", l_neighbor.getCountryId());
                    }
                }
                l_printCountryName = true;
            }
            l_printContinentName = true;
            l_printCountryName = true;
        }
    }

    /**
     * Save current game map object into .map file
     * 
     * @param p_gameMap     Current map object to be saved
     * @param p_mapFileName Name of the file
     * @return Returns true if successfully saved, otherwise false
     */
    public boolean saveMap(GameMap p_gameMap, String p_mapFileName) {
        MapValidator l_mapValidator = new MapValidator();

        if (l_mapValidator.isValidMapName(p_mapFileName)) {
            if (l_mapValidator.isValidMap(p_gameMap)) {
                try {
                    BufferedWriter l_writer = new BufferedWriter(
                            new FileWriter(Constant.MAP_PATH + p_mapFileName + ".map"));
                    int l_continentIndex = 1;
                    int l_countryIndex = 1;
                    HashMap<Integer, String> l_indexCountryMap = new HashMap<Integer, String>();
                    HashMap<String, Integer> l_countryIndexMap = new HashMap<String, Integer>();

                    l_writer.write("name " + p_mapFileName + " Map");
                    l_writer.newLine();
                    l_writer.newLine();
                    l_writer.write("[files]");
                    l_writer.newLine();
                    l_writer.newLine();
                    l_writer.flush();

                    l_writer.write("[continents]");
                    l_writer.newLine();
                    for (Continent l_continent : p_gameMap.getContinents().values()) {

                        System.out.println("Continent ID:" + l_continent.getContinentId());
                        System.out.println("Controller Value:" + l_continent.getControlValue());
                        System.out.println("------------------------");

                        l_writer.write(l_continent.getContinentId() + " " + l_continent.getControlValue()
                                + " " + l_continent.getContinentColor());
                        l_writer.newLine();
                        l_writer.flush();
                        l_continent.setBelongingMapIndex(l_continentIndex);
                        l_continentIndex++;
                    }
                    l_writer.newLine();

                    l_writer.write("[countries]");
                    l_writer.newLine();

                    for (Country l_country : p_gameMap.getCountries().values()) {
                        l_writer.write(Integer.toString(l_countryIndex) + " " + l_country.getCountryId() + " "
                                + Integer.toString(
                                        p_gameMap.getContinents().get(l_country.getBelongingContinent().toLowerCase())
                                                .getBelongingMapIndex())
                                + " " + l_country.getXCoOrdinate() + " " + l_country.getYCoOrdinate());

                        l_writer.newLine();
                        l_writer.flush();
                        l_indexCountryMap.put(l_countryIndex, l_country.getCountryId().toLowerCase());
                        l_countryIndexMap.put(l_country.getCountryId().toLowerCase(), l_countryIndex);
                        l_countryIndex++;
                    }
                    l_writer.newLine();

                    l_writer.write("[borders]");
                    l_writer.newLine();
                    l_writer.flush();
                    for (int i = 1; i < l_countryIndex; i++) {
                        String l_countryId = l_indexCountryMap.get(i);
                        Country l_country = p_gameMap.getCountries().get(l_countryId.toLowerCase());
                        l_writer.write(Integer.toString(i) + " ");
                        for (Country l_neighbor : l_country.getNeighbours().values()) {
                            l_writer.write(
                                    Integer.toString(l_countryIndexMap.get(l_neighbor.getCountryId().toLowerCase()))
                                            + " ");
                            l_writer.flush();
                        }
                        l_writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            } else {
                System.out.println(Constant.ERROR_COLOR +
                        "Map not suitable for game play. Correct the map to continue with the new map or load a map from the existing maps."
                        + Constant.RESET_COLOR);
                return false;
            }
        } else {
            System.out.println(Constant.ERROR_COLOR +
                    "Invalid Map name" + Constant.RESET_COLOR);
            return false;
        }
    }
}
