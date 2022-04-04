package com.shivbhalla.comp3003_pokerapp_psysb7;

import org.junit.Test;

import static org.junit.Assert.*;

public class CardsTests {

    @Test
    public void CardGetter(){
        assertEquals(R.drawable.ace_c,Cards.getCard(0));
        assertEquals(R.drawable.five_c,Cards.getCard(4));
        assertEquals(R.drawable.ten_s,Cards.getCard(22));
        assertEquals(R.drawable.ace_d,Cards.getCard(39));
    }

    @Test
    public void CardValues(){
        assertEquals(0,Cards.cardValue(0));
        assertEquals(9,Cards.cardValue(22));
        assertEquals(1,Cards.cardValue(27));
        assertEquals(3,Cards.cardValue(42));
    }

    @Test
    public void CardSuit(){
        assertEquals( 0,Cards.cardSuit(4));
        assertEquals( 1,Cards.cardSuit(25));
        assertEquals( 2,Cards.cardSuit(37));
        assertEquals( 3,Cards.cardSuit(44));
    }
}
