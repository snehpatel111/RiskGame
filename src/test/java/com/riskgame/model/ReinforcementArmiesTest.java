package com.riskgame.model;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;    

/**
 * Tests if Reinforcements are assigned properly or not
 *
 */
public class ReinforcementArmiesTest {
    Player d_Player1;
    Player d_Player2;
    GameMap d_Map;
    ArrayList<Player> d_Players;
    StartUpPhase d_StartUpPhase;
    IssueOrderPhase d_issueOrderPhase;
    GameEngine d_gameEngine;
    GameState d_gameState;
    MapHelper d_mapHelper;

    /**
     * initial setup
     */
    @Before
    public void before() {
        d_Player1 = new Player("Rushi");
        d_Player2 = new Player("Romit");
        d_mapHelper = new MapHelper();
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_gameEngine = new GameEngine();
        d_gameState = new GameState();
    }

    /**
     * Test if the Reinforcements are assigned or not.
     * Sample reinforcements assigned for Player1 and tested with unassigned reinforcements for Player2
     */
    @Test
    public void testAssignReinforcements() {
        d_StartUpPhase = new StartUpPhase(d_gameEngine, d_gameState);
        d_mapHelper.loadMap(d_gameEngine, d_gameState, "dummy.map");

        boolean l_check = Player.assignCountries(d_gameEngine, d_gameState);
        System.out.println(d_Player1.getOwnedCountries());
        if(l_check){
            d_issueOrderPhase.assignReinforcementToPlayer(d_gameState);
        }
 
        assertEquals(0, d_Player1.getOwnedArmyCount());
        assertEquals(0, d_Player2.getOwnedArmyCount());
    }

}

