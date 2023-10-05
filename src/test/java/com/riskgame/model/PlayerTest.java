package com.riskgame.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;

public class PlayerTest {

    Player d_player;
    String d_playerName;
    ArrayList<Player> d_playerList;

    @Before
    public void before() {

        this.d_playerName = "TestPlayer";
        this.d_player = new Player(this.d_playerName);
        this.d_playerList = new ArrayList<Player>();
        String l_command = "deploy TestCountry 5";
        this.d_player.setArgs(l_command.split("\\s+"));
    }

    /**
     * Tets for checking if player exists
     */
    @Test
    public void isPlayerNameValidTest() {
        boolean l_check = this.d_player.isValidPlayerName("TestPlayer");
        assertTrue(l_check);

        l_check = this.d_player.isValidPlayerName("#@*");
        assertFalse(l_check);
    }

    /**
     * Tets for Set Player Name
     */
    @Test
    public void addPlayerTest() {
        boolean l_check = this.d_player.addPlayer(this.d_playerList, "MsDhoni");
        assertTrue(l_check);

        l_check = this.d_player.addPlayer(this.d_playerList, "TestPlayer");
        assertTrue(l_check);
    }

    /**
     * Tets for Set Player Name
     */
    @Test
    public void removePlayerTest() {

        this.d_player.addPlayer(d_playerList, "TestPlayer");
        boolean l_check = this.d_player.removePlayer(this.d_playerList, "TestPlayer");
        assertTrue(l_check);

        l_check = this.d_player.removePlayer(this.d_playerList, "Jadeja");
        assertFalse(l_check);
    }

    /**
     * Tets for checking if player exists
     */
    @Test
    public void isPlayerExistsTest() {
        this.d_player.addPlayer(d_playerList, "TestPlayer");
        boolean l_check = this.d_player.isPlayerExist(this.d_playerList, "MsDhoni");
        assertFalse(l_check);

        l_check = this.d_player.isPlayerExist(this.d_playerList, "TestPlayer");
        assertTrue(l_check);
    }

    /**
     * Test calculation of valid reinforcement armies.
     */
    @Test
    public void testIssueOrderValidReinforcement() {
        this.d_player.setOwnedArmyCount(10);
        this.d_player.getOwnedCountries().put("testcountry", null);

        this.d_player.issue_order();

        assertEquals(5, this.d_player.getOwnedArmyCount());
        assertEquals(1, this.d_player.getExecutionOrderList().size());
    }

    /**
     * Tests calculation of invalid reinforcement armies.
     */
    @Test
    public void testIssueOrderInvalidReinforcement() {
        this.d_player.setOwnedArmyCount(10);
        this.d_player.getOwnedCountries().put("testcountry", null);

        this.d_player.issue_order();

        assertNotEquals(8, this.d_player.getOwnedArmyCount());
    }
}
