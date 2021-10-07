package com.shivbhalla.comp3003_pokerapp_psysb7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.Collections;


public class TableCards extends Fragment {

    private ImageView river1;
    private ImageView river2;
    private ImageView river3;
    private ImageView flop;
    private int river1Value;
    private int river2Value;
    private int river3Value;
    private int flopValue;



    public TableCards() {
        // Required empty public constructor
    }

    public void showRiver(int[] riverCards) throws IllegalArgumentException{
        if(riverCards.length < 3){
            throw new IllegalArgumentException();
        }
        river1.setImageResource(Cards.getCard(riverCards[0]));
        river1Value = riverCards[0];
        river2.setImageResource(Cards.getCard(riverCards[1]));
        river2Value = riverCards[1];
        river3.setImageResource(Cards.getCard(riverCards[2]));
        river3Value = riverCards[2];
    }

    public void showFlop(int flopCard){
        flop.setImageResource(Cards.getCard(flopCard));
        flopValue = flopCard;
    }

    // Sets cards to face back
    public void reset(){
        river1.setImageResource(R.drawable.card_back);
        river2.setImageResource(R.drawable.card_back);
        river3.setImageResource(R.drawable.card_back);
        flop.setImageResource(R.drawable.card_back);
    }

    public int[] getBestHand(int leftCard, int rightCard){
        int[] value = {0,0,0};
        // Value[0] = High Card
        int card1 = Cards.cardValue(leftCard);
        int card2 = Cards.cardValue(rightCard);
        value[1] = Math.max(card1,card2);

        // Value[1] = Pair
        int test = getBestPair(new int[]{leftCard,rightCard,river1Value,river2Value,river3Value,flopValue});
        if(test >= 0){
            int test2 = getBestPair(new int[]{leftCard,rightCard,river1Value,river2Value,river3Value,flopValue}, test);
            // Value[2] = Two Pair
            value[0] = test2 >= 0? 2 : 1;
            value[1] = test;
            value[2] = test2;
        }

        // Value[3] = Three of a kind

        // Value[4] = Straight

        // Value[5] = Flush

        // Value[6] = Full House

        // Value[7] = Four of a Kind

        // Value[8] = Straight Flush

        // Value[9] = Royal Flush
        return value;
    }

    // Default values
    public int getBestPair(int[] cards){
        return getBestPair(cards,-1);
    }

    // Function to find the best Pair
    public int getBestPair(int[] cards, int ignore){
        int value = -1;
        for(int i = 0; i < cards.length - 1; i++){
            int card1 = Cards.cardValue(cards[i]);
            if(card1<=value || card1 == ignore){
                continue;
            }
            // Finding highest card value for pair
            for(int j=i+1; j < cards.length; j++){
                int card2 = Cards.cardValue(cards[j]);
                if(card1 == card2){
                    value = card1;
                    break;
                }
            }
        }
        return value;
    }

    public int[] getBestThreeOrBetter(int[] cards){
        int[] value = new int[13];
        int[] suit = new int[4];
        for(int i = 0; i < cards.length; i++){
            value[Cards.cardValue(cards[i])]++;
            suit[Cards.cardSuit(cards[i])]++;
        }
        int highCard = 0;
        int handType = 0;
        for(int i=0; i < 13; i++){
            if(value[i] > handType){
                highCard = i;
                handType = value[i];
            }
        }
        switch (handType){
            // Four of a kind
            case 4:
                return new int[]{7,highCard};
            case 3:
                int pair = getBestPair(cards,highCard);
                if(pair > 0){
                    // Full house
                    return new int[] {6,highCard};
                }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table_cards, container, false);

        // Creating cards for view
        river1 = view.findViewById(R.id.river_1);
        river2 = view.findViewById(R.id.river_2);
        river3 = view.findViewById(R.id.river_3);
        flop = view.findViewById(R.id.flop);
        return view;
    }
}