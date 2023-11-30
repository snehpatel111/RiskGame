package com.riskgame.model;

import java.io.BufferedReader;
import java.io.FileReader;

import com.riskgame.model.GameState;

import com.riskgame.utility.Constant;

/**
 * Adapter class for reading and saving map
 * 
 */
public abstract class MapAdapter {
  /**
   * Map index is used to identify the map file.
   */
  public static int d_mapIndex = 1;

  /**
   * Reads the map
   * 
   * @param p_mapFileName File name of the map
   * @param p_gameState   Game state object
   * @return Returns the game map
   */
  public abstract GameMap readMap(String p_mapFileName, GameState p_gameState);

  /**
   * Save the game map
   * 
   * @param p_gameState   Game state to save
   * @param p_mapFileName File name to save the map
   * 
   * @return Returns true if map saved successfully, otherwise false
   */
  public abstract boolean saveMap(GameState p_gameState, String p_mapFileName);

  /**
   * Method to get the map type
   * 
   * @param p_mapFileName File name of the map
   * @return Returns the map type
   */
  public static String getMapType(String p_mapFileName) {
    String l_mapType = "";
    try {
      BufferedReader l_reader = new BufferedReader(new FileReader(p_mapFileName));
      String l_string;
      while ((l_string = l_reader.readLine()) != null) {
        if (l_string.equals("[countries]")) {
          l_mapType = "domination";
        }
        if (l_string.equals("[Territories]")) {
          l_mapType = "conquest";
        }
      }
      l_reader.close();
    } catch (Exception e) {
      System.out.println(Constant.ERROR_COLOR + "Cannot set map type " + Constant.RESET_COLOR);
    }
    return l_mapType;
  }
}
