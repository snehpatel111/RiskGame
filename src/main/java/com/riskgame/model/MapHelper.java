package main.java.com.riskgame.model;

import java.util.*;

import main.java.com.riskgame.utility.Constant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class contains helper functions related to Map.
 */
public class MapHelper {
    public int d_mapIndex = 1;
    public GameMap d_gameMap;

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

    public void loadMap(String mapFileName) throws IOException {
        System.out.println("Load map from file: " + mapFileName);
    }

    /**
     * Reads <code>.map</code> files and create GameMap object accordingly.
     * 
     * @param p_mapFileName Name of .map file
     * @return Returns GameMap object of respective map
     */
    public void readMap(String p_mapFileName) {
        try {
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

    private BufferedReader readContinent(BufferedReader p_reader) {
        String l_line;
        try {
            while (!((l_line = p_reader.readLine()).equals(""))) {
                String[] l_continentString = l_line.split("\\s+");

                if (Integer.parseInt(l_continentString[1]) >= 0) {
                    this.d_gameMap.getContinents().put(l_continentString[0].toLowerCase(),
                            new Continent(l_continentString[0], Integer.parseInt(l_continentString[1]), l_continentString[2]));
                    this.d_mapIndex++;
                } else {
                    System.out.println(Constant.ERROR_COLOR + "Error reading the file." + Constant.RESET_COLOR);
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
        return p_reader;
    }

    /**
     * Reads borders from <code>.map</code> file.
     * 
     * @param p_reader Stream pointing to borders section of .map file
     * @return BufferReader stream pointing end of borders section of .map file.
     */
    private BufferedReader readBorders(BufferedReader p_reader) {
        return p_reader;
    }
}
