package main.java.com.riskgame.utility;

import org.jgrapht.*;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import main.java.com.riskgame.model.Continent;
import main.java.com.riskgame.model.Country;
import main.java.com.riskgame.model.GameMap;

/**
 * Helper method to validate game map.
 */
public class MapValidator {

    // Represents game map according to JGraphT format.
    private Graph<Country, DefaultEdge> d_mapGraph;

    public MapValidator() {
        this.d_mapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }
}
