package com.riskgame.model;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;

/**
 * test case for saving the game.
 *
 */
public class SaveGameTest {

    GameEngine d_gameEngine;
    GameState d_gameState;
	MapHelper d_MapHelper;
	GameData d_Game;
	Player d_Player1, d_Player2;
	PlayerStrategy d_Pg1, d_Pg2;
	    /**
	     * initial setup
	     */
	    @Before
	    public void before() {
            d_gameEngine = new GameEngine();
            d_gameState = new GameState();
	    	d_MapHelper = new MapHelper();
	    	d_Player1= new Player("p1");
	    	d_Player2= new Player("p2");
	    	d_Game= new GameData();
	    	d_MapHelper.loadMap(d_gameEngine, d_gameState, "demo.map");
	    	d_Game.setMap(d_gameState.getGameMap());
	    	// d_Pg1 = new PlayerStrategy(d_Player1 ,d_gameEngine);
	        d_Game.getPlayers().add(d_Player1);
	        // d_Pg2 = new PlayerStrategy(d_Player2 ,d_gameEngine);
	        d_Game.getPlayers().add(d_Player2);
	        
	        d_Player1.getOwnedCountries().put("one", d_gameState.getGameMap().getCountries().get("one"));
	        d_Player1.getOwnedCountries().put("two", d_gameState.getGameMap().getCountries().get("two"));
	        
	        d_Player2.getOwnedCountries().put("four", d_gameState.getGameMap().getCountries().get("four"));
	        d_Player2.getOwnedCountries().put("three", d_gameState.getGameMap().getCountries().get("three"));
	        d_Player2.getOwnedCountries().put("five", d_gameState.getGameMap().getCountries().get("five"));
			  
	        d_Player2.getOwnedCountries().put("four", d_Game.getMap().getCountries().get("four"));
	        d_Player2.getOwnedCountries().put("three", d_Game.getMap().getCountries().get("three"));
	        d_Player2.getOwnedCountries().put("five", d_Game.getMap().getCountries().get("five"));
	       
	        d_Player2.getOwnedCountries().get("four").setOwnerPlayer(d_Player2);
	        d_Player2.getOwnedCountries().get("three").setOwnerPlayer(d_Player2);
	        d_Player2.getOwnedCountries().get("five").setOwnerPlayer(d_Player2);
	       
	        d_Player1.getOwnedCountries().get("one").setNumberOfArmies(9);
	        d_Player1.getOwnedCountries().get("two").setNumberOfArmies(8);
	        
	        d_Player2.getOwnedCountries().get("four").setNumberOfArmies(10);
	        d_Player2.getOwnedCountries().get("three").setNumberOfArmies(9);
	        d_Player2.getOwnedCountries().get("five").setNumberOfArmies(7);
	        
	        d_Player1.getOwnedCountries().get("one").getNeighbors().put("three", d_Player2.getOwnedCountries().get("three")); 
	        d_Player1.getOwnedCountries().get("two").getNeighbors().put("three", d_Player2.getOwnedCountries().get("three"));
	        d_Player1.getOwnedCountries().get("two").getNeighbors().put("four", d_Player2.getOwnedCountries().get("four"));
	        
	        d_Player2.getOwnedCountries().get("four").getNeighbors().put("two", d_Player1.getOwnedCountries().get("two"));
	        d_Player2.getOwnedCountries().get("three").getNeighbors().put("one", d_Player1.getOwnedCountries().get("one"));
	        d_Player2.getOwnedCountries().get("four").getNeighbors().put("five", d_Player2.getOwnedCountries().get("five"));
	        d_Player2.getOwnedCountries().get("three").getNeighbors().put("two", d_Player1.getOwnedCountries().get("two"));
	        d_Player2.getOwnedCountries().get("five").getNeighbors().put("four", d_Player2.getOwnedCountries().get("four"));
	    
	    }

	    /**
	     * Tests if a specific a specific game is saved or not.
	     */
	    @Test
	    public void testGameSave() {
	    	String d_Savegame="GameTen";
	    	try { 
                boolean l_save = d_MapHelper.saveMap(d_gameState, d_Savegame);
	    	if(l_save) {
                System.out.println("current Game saved is saved ");
                }
	    	 assertTrue(l_save);
	    	}catch(Exception e) {System.out.println("exception");}
	    	
	    }}


