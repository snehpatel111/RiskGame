package com.riskgame.utility;

import java.util.HashMap;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.riskgame.model.Continent;
import com.riskgame.model.Country;
import com.riskgame.model.GameMap;

/**
 * Contains methods that will be used to validate the game map object.
 */
public class MapValidator {

    // Represents game map according to JGraphT format.
    private Graph<Country, DefaultEdge> d_mapGraph;

    public MapValidator() {
        this.d_mapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    /**
     * Validate map name.
     * 
     * @param p_mapName Map name to check
     * @return Returns true if map name is valid, otherwise false
     */
    public boolean isValidMapName(String p_mapName) {
        return p_mapName != null && p_mapName.matches("^[a-zA-Z.]*$");
    }

    /**
     * Validates the game map.
     * 
     * @param p_gameMap GameMap to be be checked.
     * @return Returns true if map is valid, otherwise false
     */
    public boolean isValidMap(GameMap p_gameMap) {
        if (this.isEmptyContinent(p_gameMap)) {
            System.out.println(Constant.ERROR_COLOR + "Invalid map!"
                    + Constant.RESET_COLOR);
            System.out.println(Constant.ERROR_COLOR + "Continent is not present in the map"
                    + Constant.RESET_COLOR);
            return false;
        } else if (!this.isGraphConnected(this.createGraph(p_gameMap))) {
            System.out.println(Constant.ERROR_COLOR + "Graph is not connected!" + Constant.RESET_COLOR);
            return false;
        } else if (!this.isContinentConnected(p_gameMap)) {
            System.out.println(Constant.ERROR_COLOR
                    + "One of the continent is not connected sub-graph " + Constant.RESET_COLOR);
            return false;
        }
        return true;
    }

    /**
     * This function checks if any continent is empty.
     * 
     * @param p_gameMap GameMap object representing countries, continents and
     *                  neighbors.
     * @return Returns true if continent is empty, otherwise false.
     */
    public boolean isEmptyContinent(GameMap p_gameMap) {
        for (Continent l_continent : p_gameMap.getContinents().values()) {
            if (l_continent.getCountries().size() == 0)
                return true;
        }
        return false;
    }

    /**
     * This function checks if graph is connected.
     * 
     * @param p_graph Graph object to be checked
     * @return Returns true if graph is connected, otherwise false
     */
    public boolean isGraphConnected(Graph<Country, DefaultEdge> p_graph) {
        ConnectivityInspector<Country, DefaultEdge> l_graphConnection = new ConnectivityInspector<>(p_graph);
        return l_graphConnection.isConnected();
    }

    /**
     * Creates a graph by taking countries as vertices and adds edges between
     * neighbors.
     * 
     * @param p_gameMap GameMap object representing countries, continents and
     *                  neighbors.
     * @return Returns graph object that represents the game map.
     */
    public Graph<Country, DefaultEdge> createGraph(GameMap p_gameMap) {
        // Add Country to the Graph
        for (Country l_country : p_gameMap.getCountries().values()) {
            this.d_mapGraph.addVertex(l_country);
        }

        // Add Edges between country and its neighbors
        for (Country l_country : p_gameMap.getCountries().values()) {
            for (Country l_neighbor : l_country.getNeighbors().values()) {
                this.d_mapGraph.addEdge(l_country, l_neighbor);
            }
        }
        return this.d_mapGraph;
    }

    /**
     * Validates if all continents are connected sub-graph.
     * 
     * @param p_gameMap GameMap object representing countries, continents and
     *                  neighbors.
     * @return Returns true if all continents are connected sub-graph, otherwise
     *         false
     */
    public boolean isContinentConnected(GameMap p_gameMap) {
        for (Continent l_continent : p_gameMap.getContinents().values()) {
            Graph<Country, DefaultEdge> l_subGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
            l_subGraph = this.createSubGraph(l_subGraph, l_continent.getCountries());
            if (!this.isGraphConnected(l_subGraph))
                return false;
        }
        return true;
    }

    /**
     * This function creates a subgraph for a continent.
     * 
     * @param p_subGraph  Subgraph of a continent
     * @param p_countries Countries of a continent
     * @return Returns a subgraph for a continent
     */
    public Graph<Country, DefaultEdge> createSubGraph(Graph<Country, DefaultEdge> p_subGraph,
            HashMap<String, Country> p_countries) {

        for (Country l_country : p_countries.values()) {
            p_subGraph.addVertex(l_country);
        }

        for (Country l_country : p_countries.values()) {
            for (Country l_neighbor : l_country.getNeighbors().values()) {
                if (p_countries.containsKey(l_neighbor.getCountryId().toLowerCase())) {
                    p_subGraph.addEdge(l_country, l_neighbor);
                }
            }
        }
        return p_subGraph;
    }

}
