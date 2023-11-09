package com.riskgame.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for DiplomacyTest class
 */
public class DiplomacyTest {

    private Player d_currentPlayer;
    private Player d_targetPlayer;
    private Diplomacy d_diplomacyOrder;
    private GameState d_gameState;

    /**
     * Setting up the initial state for Diplomacy test cases.
     */
    @Before
    public void setUp() {
        d_currentPlayer = new Player("Player A");
        d_targetPlayer = new Player("Player B");
        d_gameState = new GameState();
        ArrayList<Player> l_player = new ArrayList<>();
        l_player.add(d_currentPlayer);
        l_player.add(d_targetPlayer);
        d_gameState.setPlayers(l_player);
        d_diplomacyOrder = new Diplomacy(d_currentPlayer, d_targetPlayer);
        d_diplomacyOrder.setGameState(d_gameState);
    }

    /**
     * Test to ensure the successful execution of Diplomacy.
     */
    @Test
    public void testDiplomacyExecutionSuccessful() {
        assertTrue("Diplomacy execution should be successful", d_diplomacyOrder.execute());
    }

    /**
     * Test to verify the failure of Diplomacy with an invalid target player.
     */
    @Test
    public void testDiplomacyWithInvalidd_targetPlayer() {
        Player l_invalidPlayer = new Player("InvalidPlayer");
        Diplomacy l_invalidd_diplomacyOrder = new Diplomacy(d_currentPlayer, l_invalidPlayer);
        l_invalidd_diplomacyOrder.setGameState(d_gameState);

        assertFalse("Diplomacy should fail with an invalid target player", l_invalidd_diplomacyOrder.execute());
    }

    /**
     * Test to verify the negotiate list after Diplomacy execution.
     */
    @Test
    public void testDiplomacyNegotiateList() {
        d_diplomacyOrder.execute();

        assertTrue("Current player should be in the negotiate list of current player",
                d_currentPlayer.getNegotiatePlayerList().contains(d_currentPlayer));
        assertTrue("Current player should be in the negotiate list of target player",
                d_targetPlayer.getNegotiatePlayerList().contains(d_currentPlayer));
    }
}
