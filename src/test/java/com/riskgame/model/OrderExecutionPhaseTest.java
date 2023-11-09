package com.riskgame.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

/**
 * Test class for OrderExecutionPhaseTest class
 */
public class OrderExecutionPhaseTest {

    private OrderExecutionPhase orderExecutionPhase;
    private GameEngine gameEngine;
    private GameState gameState;

    @Before
    public void setUp() {
        gameEngine = new GameEngine();
        gameState = new GameState();
        orderExecutionPhase = new OrderExecutionPhase(gameEngine, gameState);
    }

    @Test
    public void testInitPhaseWithNoUnexecutedOrders() {
        gameState.setUnexecutedOrders(new LinkedList<>());
        orderExecutionPhase.initPhase();
        assertTrue(true);
    }

    @Test
    public void testInitPhaseWithNoPlayers() {
        gameState.setUnexecutedOrders(new LinkedList<>());
        gameState.getPlayerList().clear();
        orderExecutionPhase.initPhase();

        assertTrue(gameState.getUnexecutedOrders().isEmpty());
        assertTrue(gameState.getPlayerList().isEmpty());
    }

}
