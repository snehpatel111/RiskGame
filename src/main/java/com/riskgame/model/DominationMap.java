package com.riskgame.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.riskgame.model.GameMap;
import com.riskgame.model.GameState;

import com.riskgame.utility.Constant;
import com.riskgame.utility.MapValidator;

/**
 * DominationMap class is the adapter class for reading and saving domination
 * map
 */
public class DominationMap extends MapAdapter {

  /**
   * Stores the information about the current game map.
   */
  public GameMap d_gameMap;
  private HashMap<Integer, Country> d_countryList;

  /**
   * Default constructor for DominationMap.
   */
  public DominationMap() {
    this.d_countryList = new HashMap<Integer, Country>();
    this.d_gameMap = new GameMap();
  }

  /**
   * {@inheritDoc}
   *
   */
  public GameMap readMap(String p_mapFileName, GameState p_gameState) {
    try {
      // this.d_gameMap = p_gameState.getGameMap();
      this.d_countryList = new HashMap<Integer, Country>();
      BufferedReader l_reader = new BufferedReader(new FileReader(p_mapFileName));
      String l_line;
      while ((l_line = l_reader.readLine()) != null) {
        if (l_line.equals("[continents]"))
          l_reader = this.readContinent(l_reader);
        else if (l_line.equals("[countries]"))
          l_reader = this.readCountries(l_reader, p_gameState);
        else if (l_line.equals("[borders]"))
          l_reader = this.readBorders(l_reader, p_gameState);
      }
      l_reader.close();
    } catch (FileNotFoundException e) {
      p_gameState.updateLog("File not found", "effect");
      System.out.println(Constant.ERROR_COLOR + "File not found" + Constant.RESET_COLOR);
      System.out.println(Constant.ERROR_COLOR + e.getMessage() + Constant.RESET_COLOR);
    } catch (Exception e) {
      p_gameState.updateLog("Exception: " + e.getMessage(), "effect");
      System.out.println(Constant.ERROR_COLOR + "Exception: " + e.getMessage() + Constant.RESET_COLOR);
      System.out.println(Constant.ERROR_COLOR + e.getMessage() + Constant.RESET_COLOR);
    }
    return this.d_gameMap;
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
    } catch (Exception e) {
      System.out.println(e.getMessage());
      // e.printStackTrace();
    }
    this.d_mapIndex = 1;
    return p_reader;
  }

  /**
   * Reads countries from .map file.
   * 
   * @param p_gameState The current game state.
   * @param p_reader    Stream pointing to countries section of .map file
   * @return BufferReader stream pointing end of countries section of .map file.
   */
  private BufferedReader readCountries(BufferedReader p_reader, GameState p_gameState) {
    try {
      String l_line;
      while (!((l_line = p_reader.readLine()).equals(""))) {
        String[] l_countryList = l_line.split("\\s+");
        Country l_country = new Country(l_countryList[0], l_countryList[1], l_countryList[2], l_countryList[3],
            l_countryList[4], this.d_gameMap);
        try {
          if (l_country.getBelongingContinent() == null) {
            System.out.println(
                Constant.ERROR_COLOR + "Error reading country from the file." + Constant.RESET_COLOR);
            System.exit(-1);
          }
          // Add country to the appropriate continent in the map. Terminate if duplicate
          // entry.
          this.addCountryToContinent(l_country, p_gameState);
          this.d_countryList.put(l_country.getIndex(), l_country);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
      // e.printStackTrace();
    }
    return p_reader;
  }

  /**
   * Registers this new country as part of its continent.
   * If duplicate country exits the program throwing error.
   * 
   * @param p_country
   * @param p_gameState The current game state.
   */
  private void addCountryToContinent(Country p_country, GameState p_gameState) {
    Country l_country = new Country();
    if (!l_country.isCountryExist(this.d_gameMap, p_country.getCountryId())) {
      Continent l_belongingContinent = this.d_gameMap.getContinents()
          .get(p_country.getBelongingContinent().toLowerCase());
      l_belongingContinent.getCountries().put(p_country.getCountryId().toLowerCase(), p_country);
      this.d_gameMap.getCountries().put(p_country.getCountryId().toLowerCase(), p_country);
    } else {
      p_gameState.updateLog("Two countries of same name exists in the same continent.", null);
      System.out.println(Constant.ERROR_COLOR + "Error reading the file." + Constant.RESET_COLOR);
      System.out.println(Constant.ERROR_COLOR + "Two countries of same name exists in the same continent."
          + Constant.RESET_COLOR);
      System.exit(-1);
    }
  }

  /**
   * Reads borders from .map file.
   * 
   * @param p_gameState The current game state.
   * @param p_reader    Stream pointing to borders section of .map file
   * @return BufferReader stream pointing end of borders section of .map file.
   */
  private BufferedReader readBorders(BufferedReader p_reader, GameState p_gameState) {
    try {
      String l_line;
      while ((l_line = p_reader.readLine()) != null) {
        if (!l_line.equals("")) {
          String[] l_borderList = l_line.split("\\s+");
          Country l_country = new Country();
          l_country = this.d_countryList.get(Integer.parseInt(l_borderList[0]));
          for (int l_neighborCount = 1; l_neighborCount < l_borderList.length; l_neighborCount++) {
            this.addNeighbor(l_country, l_borderList[l_neighborCount], p_gameState);
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      // e.printStackTrace();
    }
    return p_reader;
  }

  /**
   * Add country at given index as neighbor to given country.
   * Throw error if invalid neighbor is found and terminate the program.
   * 
   * @param p_gameState    The current game state.
   * @param p_country      Country to which neighbor is to be added.
   * @param p_countryIndex Index of the country to be added as a neighbor to the
   *                       argument country
   */
  private void addNeighbor(Country p_country, String p_countryIndex, GameState p_gameState) {
    int l_borderIndex = Integer.parseInt(p_countryIndex);
    Country l_neighborCountry = new Country();
    try {
      l_neighborCountry = this.d_countryList.get(l_borderIndex);
    } catch (IndexOutOfBoundsException e) {
      p_gameState.updateLog(
          "Found error reading the .map file",
          "effect");
      p_gameState.updateLog(
          "The neighbor " + l_borderIndex + " does not exist.",
          "effect");

      System.out.println(Constant.ERROR_COLOR + "The neighbor " + l_borderIndex + " does not exist."
          + Constant.RESET_COLOR);
      System.exit(-1);
    }
    if (!p_country.getNeighbors().containsKey(l_neighborCountry.getCountryId().toLowerCase()))
      p_country.getNeighbors().put(l_neighborCountry.getCountryId().toLowerCase(), l_neighborCountry);
  }

  /**
   * {@inheritDoc}
   *
   */
  public boolean saveMap(GameState p_gameState, String p_mapFileName) {
    MapValidator l_mapValidator = new MapValidator();
    GameMap l_gameMap = p_gameState.getGameMap();

    if (l_mapValidator.isValidMapName(p_mapFileName)) {
      if (l_mapValidator.isValidMap(l_gameMap)) {
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
          for (Continent l_continent : l_gameMap.getContinents().values()) {
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

          for (Country l_country : l_gameMap.getCountries().values()) {
            l_writer.write(Integer.toString(l_countryIndex) + " " + l_country.getCountryId() + " "
                + Integer.toString(
                    l_gameMap.getContinents().get(l_country.getBelongingContinent().toLowerCase())
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
            Country l_country = l_gameMap.getCountries().get(l_countryId.toLowerCase());
            l_writer.write(Integer.toString(i) + " ");
            for (Country l_neighbor : l_country.getNeighbors().values()) {
              l_writer.write(
                  Integer.toString(l_countryIndexMap.get(l_neighbor.getCountryId().toLowerCase()))
                      + " ");
              l_writer.flush();
            }
            l_writer.newLine();
          }
        } catch (IOException e) {
          System.out.println(e.getMessage());
          // e.printStackTrace();
          return false;
        }
        return true;
      } else {
        p_gameState.updateLog(
            "Map not suitable for game play. Correct the map to continue with the new map or load a map from the existing maps.",
            "effect");

        System.out.println(Constant.ERROR_COLOR +
            "Map not suitable for game play. Correct the map to continue with the new map or load a map from the existing maps."
            + Constant.RESET_COLOR);
        return false;
      }
    } else {
      p_gameState.updateLog(
          "Invalid Map name",
          "effect");

      System.out.println(Constant.ERROR_COLOR +
          "Invalid Map name" + Constant.RESET_COLOR);
      return false;
    }
  }

}
