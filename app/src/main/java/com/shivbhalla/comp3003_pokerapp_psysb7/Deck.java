package com.shivbhalla.comp3003_pokerapp_psysb7;

import java.util.Random;

public class Deck {

    // Array of cards
    private int[] cards;
    private int currentCard = 0;

    // Constructor for the deck of 52 cards
    public Deck(){
        cards = new int[52];
        for(int i = 0; i<52; i++){
            cards[i] = i;
        }
        shuffle();
    }
    // Shuffles deck
    public void shuffle(){
        Random rand = new Random();
        for(int i = 0; i<52; i++){
            int card = rand.nextInt(52);
            int temp = cards[i];
            cards[i] = cards[card];
            cards[card] = temp;
        }
        currentCard = 0;
    }
    // Draws the card from shuffled deck
    public int drawCard() throws Exception {
        if(currentCard < 0 || currentCard > 51){
            throw new Exception("ERROR");
        }
        return cards[currentCard++];
    }

    //Draws the hand to the player
    public int [] drawHand() throws Exception{
        int [] hand = new int[2];
        hand[0] = drawCard();
        hand[1] = drawCard();
        return hand;
    }

    // draws a card for the flop
    public int [] drawFlop() throws Exception{
        int [] flop = new int[3];
        flop[0] = drawCard();
        flop[1] = drawCard();
        flop[2] = drawCard();
        return flop;
    }
}
