package com.riskgame.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import org.junit.Before;
import org.junit.Test;

public class GameStateTest {
    
    private GameState d_gameState;
    private GameMap d_gameMap;
    private Queue<Order> d_unexecutedOrderList;
     private ArrayList<Player> d_playerList; 


    /**
     * Set up the context
     */
    @Before
    public void setUp()
    {
        d_gameState =new GameState();
        d_gameMap =new GameMap();
        d_unexecutedOrderList = new ArrayDeque<>();
        d_playerList = new ArrayList<>();
        
    }

    /**
     * Test to verify the getSetGameMap method
     */
    @Test
    public void testGetSetGameMap() {
        d_gameState.setGameMap(d_gameMap);
        assertEquals(d_gameMap, d_gameState.getGameMap());
    }   
    /**
     * Test to check get and set UnexecutedOrders functionalities
     */
    @Test
    public void testGetSetUnexecutedOrders() {
        d_gameState.setUnexecutedOrders(d_unexecutedOrderList);
        assertEquals(d_unexecutedOrderList, d_gameState.getUnexecutedOrders());
    }

    /**
     * Test to validate Get and SetPlayerList functionalities
     */
    @Test
    public void testGetSetPlayerList() {
        d_gameState.setPlayers(d_playerList);
        assertEquals(d_playerList, d_gameState.getPlayerList());
    }

    /**
     *  Test to validate isGameMapLoaded functionality
     */
    @Test
    public void testIsGameMapLoaded() {
        d_gameState.setIsGameMapLoaded();
        assertTrue(d_gameState.isGameMapLoaded());
    }

    /**
     * Test to validate Total army of all players
     */
    @Test
    public void testGetTotalArmyOfAllPlayers() {
        Player d_player1 = new Player("Player1");
        d_player1.setOwnedArmyCount(10);
        Player d_player2 = new Player("Player2");
        d_player2.setOwnedArmyCount(15);
        d_playerList.add(d_player1);
        d_playerList.add(d_player2);
        d_gameState.setPlayers(d_playerList);

        int totalArmy = d_gameState.getTotalArmyOfAllPlayers();
        assertEquals(25, totalArmy);
    }

      
   




}
