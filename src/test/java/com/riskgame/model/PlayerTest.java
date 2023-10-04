package com.riskgame.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest{

    Player d_player;
    String d_playerName;
    ArrayList<Player> d_playerList;

    @Before
    public void before() {

        this.d_playerName = "ViratKohli";
        this.d_player = new Player(this.d_playerName);
        this.d_playerList = new ArrayList<Player>();
    } 

    /**
     * Tets for checking if player exists
     */
    @Test
    public void isPlayerNameValidTest() {
        boolean l_check = this.d_player.isValidPlayerName("ViratKohli");
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

        l_check = this.d_player.addPlayer(this.d_playerList, "ViratKohli");
        assertTrue(l_check);
    }

    /**
     * Tets for Set Player Name
     */
    @Test
    public void removePlayerTest() {

        this.d_player.addPlayer(d_playerList, "ViratKohli");
        boolean l_check = this.d_player.removePlayer(this.d_playerList, "ViratKohli");
        assertTrue(l_check);

        l_check = this.d_player.removePlayer(this.d_playerList, "Jadeja");
        assertFalse(l_check);
    }

    /**
     * Tets for checking if player exists
     */
    @Test
    public void isPlayerExistsTest() {
        this.d_player.addPlayer(d_playerList, "ViratKohli");
        boolean l_check = this.d_player.isPlayerExist(this.d_playerList, "MsDhoni");
        assertFalse(l_check);

        l_check = this.d_player.isPlayerExist(this.d_playerList, "ViratKohli");
        assertTrue(l_check);
    }

    
}
