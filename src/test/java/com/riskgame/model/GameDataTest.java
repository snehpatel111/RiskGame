package com.riskgame.model;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.riskgame.controller.GameEngine;
import com.riskgame.model.Card;
import com.riskgame.model.GameData;
import com.riskgame.model.GameMap;
import com.riskgame.model.OrderExecutionPhase;
import com.riskgame.model.Phase;
import com.riskgame.model.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class GameDataTest {

    private GameData d_gameData;

    @Before
    public void setUp() {
        d_gameData = new GameData();
    }

    @Test
    public void testSetAndGetMap() {
        GameMap map = new GameMap();
        d_gameData.setMap(map);
        assertEquals(map, d_gameData.getMap());
    }

    @Test
    public void testSetAndGetMapType() {
        String mapType = "Domination";
        d_gameData.setMapType(mapType);
        assertEquals(mapType, d_gameData.getMapType());
    }

    @Test
    public void testSetAndGetPlayers() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        d_gameData.setPlayers(new ArrayList<>(Arrays.asList(player1, player2)));
        assertEquals(Arrays.asList(player1, player2), d_gameData.getPlayers());
    }

    @Test
    public void testSetAndGetActivePlayer() {
        Player activePlayer = new Player("ActivePlayer");
        d_gameData.setActivePlayer(activePlayer);
        assertEquals(activePlayer, d_gameData.getActivePlayer());
    }

    @Test
    public void testSetAndGetDeck() {
        Card deck = new Card();
        d_gameData.setDeck(deck);
        assertEquals(deck, d_gameData.getDeck());
    }

    @Test
    public void testSetAndGetCardsDealt() {
        int cardsDealt = 3;
        d_gameData.setCardsDealt(cardsDealt);
        assertEquals(cardsDealt, d_gameData.getCardsDealt());
    }

    @Test
    public void testSetAndGetPhaseName() {
        String phaseName = "Reinforcement";
        d_gameData.setD_PhaseName(phaseName);
        assertEquals(phaseName, d_gameData.getD_PhaseName());
    }

    @Test
    public void testRemovePlayer() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        d_gameData.setPlayers(new ArrayList<>(Arrays.asList(player1, player2)));

        d_gameData.removePlayer(player1);
        assertEquals(Collections.singletonList(player2), d_gameData.getPlayers());
    }

   @Test
public void testResetGameData() {
    d_gameData.setMap(new GameMap());
    d_gameData.setGamePhase(new OrderExecutionPhase(d_gameData.getGameEngine(), d_gameData.getGameState()));

    Player player = new Player("Player");
    d_gameData.setPlayers(new ArrayList<>(Collections.singletonList(player)));
    d_gameData.setActivePlayer(player);
    d_gameData.setDeck(new Card());
    d_gameData.setCardsDealt(2);

    d_gameData.resetGameData();

    assertNull(d_gameData.getMap());
    assertNull(d_gameData.getMapType());
    assertNull(d_gameData.getGamePhase());
    assertEquals(Collections.emptyList(), d_gameData.getPlayers());
    assertNull(d_gameData.getActivePlayer());
    assertNull(d_gameData.getDeck());
    assertEquals(0, d_gameData.getCardsDealt());
}




    
}
