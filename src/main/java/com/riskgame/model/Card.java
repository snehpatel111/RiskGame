package com.riskgame.model;

import java.util.Random;

/**
 * This card class will handle all requests related to cards.
 */
public class Card {
    
    /**
     * Represents type of Card from Bomb, Airlift, Blockade and Diplomacy
     */
    String d_cardType;

    /**
     * This list contains the cards issued to players
     */
    String[] d_cardsList = {"Airlift","Blockade","Bomb","Diplomacy"};

    /**
     *The method gives type of a card.
     * @return string card type
     */
    String getCardType()
    {
        return d_cardType;
    }

    /**
     * Default constructor of Card to access the methods of this class.
     */
    public Card(){
    }

    /**
     * This constructor will assign type of cards
     * @param p_cardType Card Type that is assigned
     */
    public Card(String p_cardType){
        this.d_cardType=p_cardType;
    }
    /**
     * Stores the random card picked in the CardType String
     */
    public void createCard()
    {
        d_cardType = randomCard();
    }

    /**
     * Stores the temp card picked in the CardType String
     * @param temp specific card
     */
    public void createCard(String p_cardType) { 
        d_cardType = p_cardType; 
    }

    /**
     * Picks a random card from the Cards List using random generator
     * @return The index of the Cards List
     */
    public String randomCard()
    {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(d_cardsList.length);
        return d_cardsList[index];
    }


}
