package com.riskgame.model;

import java.io.Serializable;
import java.util.Random;

/**
 * This card class will handle all requests related to cards.
 */
public class Card implements Serializable {

    /**
     * Represents type of Card from Bomb, Airlift, Blockade and Diplomacy
     */
    String d_cardType;

    /**
     * This list contains the cards issued to players
     */
    String[] d_cardsList = { "Airlift", "Blockade", "Bomb", "Diplomacy" };

    /**
     * GameState object which is used to access the game map and other details
     */
    public GameState d_gameState;

    /**
     * The method gives type of a card.
     * 
     * @return string card type
     */
    public String getCardType() {
        return this.d_cardType;
    }

    /**
     * Default constructor of Card to access the methods of this class.
     */
    public Card() {
    }

    /**
     * This constructor will assign type of cards
     * 
     * @param p_cardType Card Type that is assigned
     */
    public Card(String p_cardType) {
        this.d_cardType = p_cardType;
        this.d_gameState = new GameState();
    }

    /**
     * Set game state.
     * 
     * @param p_gameState Game state object
     */
    public void setGameState(GameState p_gameState) {
        this.d_gameState = p_gameState;
    }

    /**
     * Stores the random card picked in the CardType String
     */
    public void createCard() {
        this.d_cardType = this.getRandomCard();
    }

    /**
     * Stores the temp card picked in the CardType String
     * 
     * @param p_cardType specific card
     */
    public void createCard(String p_cardType) {
        this.d_cardType = p_cardType;
    }

    /**
     * Picks a random card from the Cards List using random generator
     * 
     * @return The index of the Cards List
     */
    public String getRandomCard() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(this.d_cardsList.length);
        return this.d_cardsList[index];
    }

}
