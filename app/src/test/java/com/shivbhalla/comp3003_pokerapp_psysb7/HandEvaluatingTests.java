package com.shivbhalla.comp3003_pokerapp_psysb7;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HandEvaluatingTests {
    @Test
    public void OnePair() {
        TableCards tableCards = new TableCards();
        tableCards.showRiver(new int[]{2,13,29});
        tableCards.showFlop(19);
        tableCards.showTurn(8);
        int[] hand = tableCards.getBestHand(new int[]{0,17});
        assertEquals(1,hand[0]);
        hand = tableCards.getBestHand(new int[]{15,17});
        assertEquals(1,hand[0]);
        hand = tableCards.getBestHand(new int[]{16,18});
        assertEquals(1,hand[0]);
        hand = tableCards.getBestHand(new int[]{31,18});
        assertEquals(1,hand[0]);
        hand = tableCards.getBestHand(new int[]{21,17});
        assertEquals(1,hand[0]);
    }

    @Test
    public void TwoPair() {
        TableCards tableCards = new TableCards();
        tableCards.showRiver(new int[]{2,13,29});
        tableCards.showFlop(6);
        tableCards.showTurn(8);
        int[] hand = tableCards.getBestHand(new int[]{0,15});
        assertEquals(2,hand[0]);
        hand = tableCards.getBestHand(new int[]{0,16});
        assertEquals(2,hand[0]);
        hand = tableCards.getBestHand(new int[]{0,19});
        assertEquals(2,hand[0]);
        hand = tableCards.getBestHand(new int[]{0,21});
        assertEquals(2,hand[0]);
        hand = tableCards.getBestHand(new int[]{15,16});
        assertEquals(2,hand[0]);
        hand = tableCards.getBestHand(new int[]{15,19});
        assertEquals(2,hand[0]);
        hand = tableCards.getBestHand(new int[]{15,21});
        assertEquals(2,hand[0]);
        hand = tableCards.getBestHand(new int[]{16,19});
        assertEquals(2,hand[0]);
        hand = tableCards.getBestHand(new int[]{16,21});
        assertEquals(2,hand[0]);
        hand = tableCards.getBestHand(new int[]{19,21});
        assertEquals(2,hand[0]);

        tableCards.showRiver(new int[]{19,13,3});
        tableCards.showFlop(6);
        hand = tableCards.getBestHand(new int[]{7,21});
        assertEquals(2,hand[0]);
    }

    @Test
    public void ThreeOfaKind(){
        TableCards tableCards = new TableCards();
        tableCards.showRiver(new int[]{2,13,29});
        tableCards.showFlop(6);
        tableCards.showTurn(8);
        int[] hand = tableCards.getBestHand(new int[]{0,26});
        assertEquals(3,hand[0]);
        hand = tableCards.getBestHand(new int[]{26,0});
        assertEquals(3,hand[0]);

        tableCards.showRiver(new int[]{29,13,2});
        hand = tableCards.getBestHand(new int[]{0,26});
        assertEquals(3,hand[0]);

        tableCards.showRiver(new int[]{9,13,29});
        tableCards.showFlop(2);
        hand = tableCards.getBestHand(new int[]{0,26});
        assertEquals(3,hand[0]);

        tableCards.showRiver(new int[]{6,13,29});
        tableCards.showFlop(8);
        tableCards.showTurn(2);
        hand = tableCards.getBestHand(new int[]{0,26});
        assertEquals(3,hand[0]);
    }

    @Test
    public void Straight(){
        TableCards tableCards = new TableCards();
        tableCards.showRiver(new int[]{15,29,4});
        tableCards.showFlop(19);
        tableCards.showTurn(8);
        int[] hand = tableCards.getBestHand(new int[]{5,9});
        assertEquals(4,hand[0]);
        hand = tableCards.getBestHand(new int[]{9,5});
        assertEquals(4,hand[0]);

        tableCards.showRiver(new int[]{29,15,4});
        hand = tableCards.getBestHand(new int[]{5,9});
        assertEquals(4,hand[0]);

        tableCards.showRiver(new int[]{29,4,15});
        hand = tableCards.getBestHand(new int[]{5,9});
        assertEquals(4,hand[0]);

        tableCards.showRiver(new int[]{4,15,29});
        hand = tableCards.getBestHand(new int[]{5,9});
        assertEquals(4,hand[0]);

        tableCards.showRiver(new int[]{19,15,4});
        tableCards.showFlop(29);
        hand = tableCards.getBestHand(new int[]{5,9});
        assertEquals(4,hand[0]);

        tableCards.showRiver(new int[]{8,15,4});
        tableCards.showFlop(19);
        tableCards.showTurn(29);
        hand = tableCards.getBestHand(new int[]{5,9});
        assertEquals(4,hand[0]);
    }

    @Test
    public void Flush(){
        TableCards tableCards = new TableCards();
        tableCards.showRiver(new int[]{2,3,4});
        tableCards.showFlop(19);
        tableCards.showTurn(34);
        int[] hand = tableCards.getBestHand(new int[]{5,9});
        assertEquals(5,hand[0]);
        hand = tableCards.getBestHand(new int[]{9,5});
        assertEquals(5,hand[0]);

        tableCards.showRiver(new int[]{3,2,4});
        hand = tableCards.getBestHand(new int[]{9,5});
        assertEquals(5,hand[0]);
        tableCards.showRiver(new int[]{4,2,3});
        hand = tableCards.getBestHand(new int[]{9,5});
        assertEquals(5,hand[0]);
        tableCards.showRiver(new int[]{3,4,2});
        hand = tableCards.getBestHand(new int[]{9,5});
        assertEquals(5,hand[0]);

        tableCards.showRiver(new int[]{19,2,4});
        tableCards.showFlop(3);
        hand = tableCards.getBestHand(new int[]{9,5});
        assertEquals(5,hand[0]);

        tableCards.showRiver(new int[]{34,2,4});
        tableCards.showFlop(19);
        tableCards.showTurn(3);
        hand = tableCards.getBestHand(new int[]{9,5});
        assertEquals(5,hand[0]);
    }

    @Test
    public void FullHouse(){
        TableCards tableCards = new TableCards();
        tableCards.showRiver(new int[]{0,13,1});
        tableCards.showFlop(19);
        tableCards.showTurn(34);
        int[] hand = tableCards.getBestHand(new int[]{26,14});
        assertEquals(6,hand[0]);
        hand = tableCards.getBestHand(new int[]{14,26});
        assertEquals(6,hand[0]);

        tableCards.showRiver(new int[]{13,0,1});
        hand = tableCards.getBestHand(new int[]{26,14});
        assertEquals(6,hand[0]);
        tableCards.showRiver(new int[]{1,0,13});
        hand = tableCards.getBestHand(new int[]{26,14});
        assertEquals(6,hand[0]);
        tableCards.showRiver(new int[]{13,1,0});
        hand = tableCards.getBestHand(new int[]{26,14});
        assertEquals(6,hand[0]);

        tableCards.showRiver(new int[]{19,0,1});
        tableCards.showFlop(13);
        hand = tableCards.getBestHand(new int[]{26,14});
        assertEquals(6,hand[0]);

        tableCards.showRiver(new int[]{34,0,1});
        tableCards.showFlop(19);
        tableCards.showTurn(13);
        hand = tableCards.getBestHand(new int[]{26,14});
        assertEquals(6,hand[0]);
    }
}