package main.java.com.riskgame.utility;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import main.java.com.riskgame.model.Country;

/**
 * Helper method to validate game map.
 */
public class MapValidator {

    // Represents game map according to JGraphT format.
    private Graph<Country, DefaultEdge> d_mapGraph;

    public MapValidator() {
        this.d_mapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }
    
    
    /**
	 * It consist of various validity checks to ensure that map is suitable for playing the game.
	 * Checks:
	 * 	1) any empty continent is present or not, i.e. continent without any country
	 * 	2) map for the game is connected graph or not
	 * 	3) each continent in map is a connected sub-graph or not
	 * @param p_gameMap GameMap  to be be checked.
	 * @return return true if map is valid, else false if map is invalid
	 */
	public boolean validateMap(GameMap p_gameMap) {
		
		if(!this.isContinentEmpty(p_gameMap)) {
			System.out.println("** Invalid map - "+Constant.ERROR_COLOR +" There is No Continent present in the Map"+Constant.RESET_COLOR);
			return false;
		}
		else if(!this.isGraphConnected(this.createGraph(p_gameMap))) {
			System.out.println("** Invalid map - "+Constant.ERROR_COLOR +" Graph is not Connected"+Constant.RESET_COLOR);
			return false;
		}
		else if(!this.isContinentConnected(p_gameMap)) {
			System.out.println("** Invalid map - "+Constant.ERROR_COLOR +" please check that One of the Continent is not connected to sub-graph "+Constant.RESET_COLOR);
			return false;
		}
		return true;
	}
	
	/**
	 * This function will Check if any continent is empty and does not hold any country.
	 * @param p_gameMap  object representing the game map
	 * @return true if no continent is empty, else false indicating empty continent
	 */
	public boolean isContinentEmpty(GameMap p_gameMap) {
		for(Continent l_continent : p_gameMap.getContinents().values()) {
			if(l_continent.getCountries().size()==0)
				return false;
		}
		return true;
	}
	
	/**
     * This function checks if the graph is a connected graph.
     * @param p_graph The graph whose connectivity is checked
     * @return returns true if the graph is connected
     */
    public boolean isGraphConnected(Graph<Country, DefaultEdge> p_graph) {
        ConnectivityInspector<Country, DefaultEdge> l_graphCheck = new ConnectivityInspector<>(p_graph);
        if (l_graphCheck.isConnected())
            return true;
        else
            return false;
    }
    
    
    /**
     * Creates a graph(using jgrapht library) by taking countries as vertices and adds edges between country and its neighbors
     *
     * @param p_gameMap is GameMap object  representing countries ,continents and borders.
     * @return returns graph representing the map
     */
    public Graph<Country, DefaultEdge> createGraph(GameMap p_gameMap) {

        //add Country to the Graph
        for (Country l_countryDetails : p_gameMap.getCountries().values()) {
            d_mapGraph.addVertex(l_countryDetails);
        }

        //add Edges between country and its neighbours
        for (Country l_countryDetails : p_gameMap.getCountries().values()) {
            for (Country l_neighbor : l_countryDetails.getNeighbors().values()) {
                d_mapGraph.addEdge(l_countryDetails, l_neighbor);
            }
        }
        return d_mapGraph;
    }

    /**
   	 * Checks if all continents are connected sub-graphs or not
   	 * @param p_gameMap is a GameMap object representing the game map
   	 * @return true if all continents are connected sub-graph, else false indicating incorrect map
   	 */
   	public boolean isContinentConnected(GameMap p_gameMap) {
   		for(Continent l_continent : p_gameMap.getContinents().values()) {
   			Graph<Country, DefaultEdge> l_subGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
   			l_subGraph = createSubGraph(l_subGraph, l_continent.getCountries());
   			if(!isGraphConnected(l_subGraph)) {
   				return false;
   			}
   		}
   		return true;
   	}
   	
    /**
     *This function creates a subgraph for a continent
     * @param p_subGraph subgraph for a continent
     * @param p_countries countries of a continent
     * @return p_subGraph a subgraph for a continent
     */
    public Graph<Country, DefaultEdge> createSubGraph(Graph<Country, DefaultEdge> p_subGraph, HashMap<String, Country> p_countries) {

        for (Country l_country : p_countries.values()) {
            p_subGraph.addVertex(l_country);
        }

        for (Country l_country : p_countries.values()) {
            for (Country l_neighbour : l_country.getNeighbors().values()) {
                if (p_countries.containsKey(l_neighbour.getCountryId().toLowerCase())) {
                    p_subGraph.addEdge(l_country, l_neighbour);
                }
            }
        }
        return p_subGraph;
    }
    
}
