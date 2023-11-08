package com.riskgame.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BombTest {

    private GameState d_gameState;
    private Player d_targetPlayer;
    private Player d_attackPlayer;
    private Bomb d_bombOrder;
    
    /**
     * Setting up the initial state for Bomb test cases.
     */
    @Before
    public void setup() {
        d_gameState = new GameState(); 
        d_targetPlayer = new Player("ab");
        d_attackPlayer = new Player("bc");
        String d_countryId = "a";
        d_bombOrder = new Bomb(d_attackPlayer, d_targetPlayer, d_countryId);
    }

    /**
     * Test case to simulate executing the Bomb order when players have negotiated.
     * Verifies that the execute method returns false when the target player is already negotiated.
     */
    @Test
    public void testExecuteWhenPlayersNegotiated() {
        Player d_attackPlayer = new Player("AttackPlayer");
        Player d_targetPlayer = new Player("TargetPlayer");
    
        String d_countryId = "b";
        Bomb bombOrder = new Bomb(d_attackPlayer, d_targetPlayer, d_countryId);
    
        d_attackPlayer.addPlayerToNegotiateList(d_targetPlayer);
    
        boolean result = bombOrder.execute();
    
        assertFalse(result);
    }

    /**
     * Test case to execute the Bomb order when players have not negotiated.
     * Validates the Bomb order execution logic and its effect on the target player's country.
     */
    @Test
    public void testExecuteWhenPlayersNotNegotiated() {
        Player d_targetPlayer = new Player("TargetPlayer");
        Player d_attackPlayer = new Player("AttackPlayer");
        String d_countryId = "TestCountry";
        
        GameState gameState = new GameState();
        
        d_attackPlayer.addPlayerToNegotiateList(d_targetPlayer); // Simulate players not negotiated

        // Creating the Bomb object
        Bomb bombOrder = new Bomb(d_attackPlayer, d_targetPlayer, d_countryId);

        // Executing the Bomb order
        boolean result = bombOrder.execute();

        // Verifying that the execute method returns false when the players are not negotiated
        assertFalse(result);
    }
}
