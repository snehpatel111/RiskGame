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
 * ConquestMap class is the adapter class for reading and saving domination
 * map
 */
public class ConquestMap extends MapAdapter {
  /**
   * Stores the information about the current game map.
   */
  public GameMap d_gameMap;
  private HashMap<Integer, Country> d_countryList;

  /**
   * Default constructor for ConquestMap.
   */
  public ConquestMap() {
    this.d_countryList = new HashMap<Integer, Country>();
    this.d_gameMap = new GameMap();
  }

  /**
   * {@inheritDoc}
   *
   */
  public GameMap readMap(String p_mapFileName, GameState p_gameState) {
    try {
      BufferedReader l_reader = new BufferedReader(new FileReader(p_mapFileName));
      String l_line;
      while ((l_line = l_reader.readLine()) != null) {
        if (l_line.equals("[Continents]")) {
          l_reader = this.readContinents(l_reader, this.d_gameMap);
        }
        if (l_line.equals("[Territories]")) {
          l_reader = this.readTerritories(l_reader, this.d_gameMap);
        }
      }
      l_reader.close();
      for (Country l_country : this.d_gameMap.getCountries().values()) {
        for (String l_neighbor : l_country.getNeighbors().keySet()) {
          if (l_country.getNeighbors().get(l_neighbor.toLowerCase()) == null) {
            if (this.d_gameMap.getCountries().get(l_neighbor.toLowerCase()) == null) {
              return null;
            }
            l_country.getNeighbors().put(l_neighbor.toLowerCase(),
                this.d_gameMap.getCountries().get(l_neighbor.toLowerCase()));
          }
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println(Constant.ERROR_COLOR + "File not found" + Constant.RESET_COLOR);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return this.d_gameMap;
  }

  /**
   * Reads the continents from the map file.
   * 
   * @param p_reader  Stream starting from continents section of ".map" file
   * @param p_gameMap Game map object to store the information about the map
   * 
   * @return BufferedReader stream at the point where it has finished reading
   *         continents
   */
  public BufferedReader readContinents(BufferedReader p_reader, GameMap p_gameMap) {
    String l_line;
    try {
      while (!((l_line = p_reader.readLine()).equals(""))) {
        String[] l_continent = l_line.split("=");

        if (Integer.parseInt(l_continent[1]) >= 0) {
          p_gameMap.getContinents().put(l_continent[0].toLowerCase(),
              new Continent(l_continent[0], Integer.parseInt(l_continent[1])));
          this.d_mapIndex++;
        } else {
          System.out.println(Constant.ERROR_COLOR + "Error reading the continent." + Constant.RESET_COLOR);
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
      // e.printStackTrace();
    }
    this.d_mapIndex = 1;
    return p_reader;
  }

  /**
   * Reads the territories from the ".map" files.
   * 
   * @param p_reader  Stream starting from countries section of ".map" file
   * @param p_gameMap Represents the game map
   * @return BufferedReader stream at the point where it has finished reading
   *         countries
   */
  public BufferedReader readTerritories(BufferedReader p_reader, GameMap p_gameMap) {
    String l_s;
    try {
      while ((l_s = p_reader.readLine()) != null) {
        if (l_s.equals("")) {
          continue;
        }
        String[] l_countryString = l_s.split(",");
        Country l_country = new Country(l_countryString[0], l_countryString[1], l_countryString[2],
            l_countryString[3]);
        try {
          if (l_country.getBelongingContinent() == null) {
            System.out.println(Constant.ERROR_COLOR + "Continent does not exist." + Constant.RESET_COLOR);
            return p_reader;
          }
          this.addCountryToMap(l_country, p_gameMap);
          for (int i = 4; i < l_countryString.length; i++) {
            if (p_gameMap.getCountries().containsKey(l_countryString[i].toLowerCase())) {
              l_country.getNeighbors().put(l_countryString[i].toLowerCase(),
                  p_gameMap.getCountries().get(l_countryString[i].toLowerCase()));
              p_gameMap.getCountries().get(l_countryString[i].toLowerCase()).getNeighbors()
                  .put(l_country.getCountryId().toLowerCase(), l_country);
            } else {
              l_country.getNeighbors().put(l_countryString[i].toLowerCase(), null);
            }
          }
        } catch (NullPointerException e) {
          System.out.println(e.getMessage());
          // e.printStackTrace();
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
      // e.printStackTrace();
    }
    return p_reader;
  }

  /**
   * Adds the country to the continent.
   * 
   * @param p_country Country to be added to the continent
   * @param p_gameMap Game map object to store the information about the map
   */
  public void addCountryToMap(Country p_country, GameMap p_gameMap) {
    Continent l_continent = new Continent();
    if (!l_continent.isCountryExist(p_gameMap, p_country.getCountryId())) {
      Continent l_belongingContinent = p_gameMap.getContinents().get(p_country.getBelongingContinent().toLowerCase());
      l_belongingContinent.getCountries().put(p_country.getCountryId().toLowerCase(), p_country);
      p_gameMap.getCountries().put(p_country.getCountryId().toLowerCase(), p_country);
    } else {
      System.out.println(Constant.ERROR_COLOR + "Country already exists in the map." + Constant.RESET_COLOR);
    }
  }

  /**
   * {@inheritDoc}
   *
   */
  public boolean saveMap(GameState p_gameState, String p_mapFileName) {
    MapValidator l_mapValidator = new MapValidator();
    GameMap l_gameMap = p_gameState.getGameMap();
    try {
      BufferedWriter l_writer = new BufferedWriter(new FileWriter(Constant.MAP_PATH + p_mapFileName + ".map"));

      // write preliminary information
      l_writer.write("[Map]");
      l_writer.newLine();
      l_writer.newLine();
      l_writer.flush();

      // write information about all the continents
      l_writer.write("[Continents]");
      l_writer.newLine();
      for (Continent continent : l_gameMap.getContinents().values()) {
        l_writer.write(continent.getContinentId() + "=" + continent.getControlValue());
        l_writer.newLine();
        l_writer.flush();
      }
      l_writer.newLine();

      // write information about countries and its neighbors
      l_writer.write("[Territories]");
      l_writer.newLine();
      String l_string;
      for (Country l_country : l_gameMap.getCountries().values()) {
        l_string = l_country.getCountryId() + "," + l_country.getXCoOrdinate() + "," + l_country.getYCoOrdinate()
            + ","
            + l_country.getBelongingContinent();
        for (Country neighbor : l_country.getNeighbors().values()) {
          l_string += "," + neighbor.getCountryId();
        }
        l_writer.write(l_string);
        l_writer.newLine();
        l_writer.flush();
      }

    } catch (IOException e) {
      System.out.println(e.getMessage());
      // e.printStackTrace();
      return false;
    }
    return true;
  }
}
