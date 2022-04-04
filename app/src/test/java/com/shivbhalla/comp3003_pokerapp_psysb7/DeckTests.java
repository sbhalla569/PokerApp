package com.shivbhalla.comp3003_pokerapp_psysb7;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTests {

    @Test
    public void CreateDeck() throws Exception {
        Deck deck = new Deck();
        assertEquals(deck.getCard(0), deck.drawCard());
        assertEquals(deck.getCard(1), deck.drawCard());
        assertEquals(deck.getCard(2), deck.drawCard());
        assertEquals(deck.getCard(3), deck.drawCard());
    }
}
