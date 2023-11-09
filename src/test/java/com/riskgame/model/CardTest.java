package com.riskgame.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for CardTest class
 */
public class CardTest {

    private Card card;

    /**
     * Set up the context
     */
    @Before
    public void setUp() {
        card = new Card();
    }

    /**
     * Test to verify the addition of a custom card to a deck.
     */
    @Test
    public void testAddCardToDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        String cardType = "Bomb";
        Card customCard = new Card(cardType);
        deck.add(customCard);
        assertEquals(deck.get(0).getCardType(), cardType);
    }

    /**
     * Test to set a card type and check if it is returned correctly
     */
    @Test
    public void testGetCardType() {
        assertNull(card.getCardType());
        card.createCard("Airlift");
        assertEquals("Airlift", card.getCardType());
    }

    /**
     * Test to create a card and verify if it is valid
     */
    @Test
    public void testCreateCard() {
        card.createCard();
        String cardType = card.getCardType();
        assertNotNull(cardType);
        boolean validCardType = false;
        for (String validType : card.d_cardsList) {
            if (validType.equals(cardType)) {
                validCardType = true;
                break;
            }
        }
        assertTrue(validCardType);
    }
}
