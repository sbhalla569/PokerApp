package com.shivbhalla.comp3003_pokerapp_psysb7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


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

    public int getBestHand(int leftCard, int rightCard){
        return 0;
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