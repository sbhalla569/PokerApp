package com.shivbhalla.comp3003_pokerapp_psysb7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link playerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class playerFragment extends Fragment {

    private ImageView leftCard;
    private ImageView rightCard;
    private Chips playerChips;

    private ImageView[] redChips; // 1
    private ImageView[] pinkChips; // 5
    private ImageView[] turquoiseChips; // 25
    private ImageView[] yellowChips; // 100
    private ImageView[] blueChips; // 500
    private ImageView[] greenChips; // 1500
    private ImageView dealerChip;
    private TextView text;
    private int chipValue;
    private boolean showCards = false;
    private boolean hasFolded = false;
    private int leftCardValue;
    private int rightCardValue;


    public playerFragment(int value) {
        chipValue = value;
        // Required public constructor
    }
    public playerFragment (){
        chipValue = 500;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment playerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static playerFragment newInstance(String param1, String param2) {
        playerFragment fragment = new playerFragment(150);
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public int getChipValue(){
        return chipValue;
    }

    public void fold(){
        hasFolded = true;
        leftCard.setImageResource(R.drawable.card_back);
        rightCard.setImageResource(R.drawable.card_back);
    }

    public boolean getFolded(){
        return hasFolded;
    }

    // Allows to set the cards and swap them from facing up and down
    public void setCards(int left, int right) {
        hasFolded = false;
        leftCardValue = left;
        rightCardValue = right;
        // When cards are set the image is now set to the back
        leftCard.setImageResource(R.drawable.card_back);
        rightCard.setImageResource(R.drawable.card_back);
    }

    // Function to get cards
    public int[] getCards(){
        return new int[]{leftCardValue,rightCardValue};
    }

    // Changes upside down cards to face up with the correct imageview
    public void showHand(){
        showCards = true;
        leftCard.setImageResource(Cards.getCard(leftCardValue));
        rightCard.setImageResource(Cards.getCard(rightCardValue));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        leftCard = view.findViewById(R.id.leftCard);
        rightCard = view.findViewById(R.id.rightCard);

        // Creating Chips
        redChips = new ImageView[4];
        pinkChips = new ImageView[4];
        turquoiseChips = new ImageView[3];
        yellowChips = new ImageView[4];
        blueChips = new ImageView[2];
        greenChips = new ImageView[1];

        redChips[0] = (ImageView) view.findViewById(R.id.red_a);
        redChips[1] = (ImageView) view.findViewById(R.id.red_b);
        redChips[2] = (ImageView) view.findViewById(R.id.red_c);
        redChips[3] = (ImageView) view.findViewById(R.id.red_d);

        pinkChips[0] = (ImageView) view.findViewById(R.id.pink_a);
        pinkChips[1] = (ImageView) view.findViewById(R.id.pink_b);
        pinkChips[2] = (ImageView) view.findViewById(R.id.pink_c);
        pinkChips[3] = (ImageView) view.findViewById(R.id.pink_d);

        turquoiseChips[0] = (ImageView) view.findViewById(R.id.turquoise_a);
        turquoiseChips[1] = (ImageView) view.findViewById(R.id.turquoise_b);
        turquoiseChips[2] = (ImageView) view.findViewById(R.id.turquoise_c);

        yellowChips[0] = (ImageView) view.findViewById(R.id.yellow_a);
        yellowChips[1] = (ImageView) view.findViewById(R.id.yellow_b);
        yellowChips[2] = (ImageView) view.findViewById(R.id.yellow_c);
        yellowChips[3] = (ImageView) view.findViewById(R.id.yellow_d);

        blueChips[0] = (ImageView) view.findViewById(R.id.blue_a);
        blueChips[1] = (ImageView) view.findViewById(R.id.blue_b);

        greenChips[0] = (ImageView) view.findViewById(R.id.green_a);

        text = (TextView) view.findViewById(R.id.chip_value2);

        setChipValue();
        return view;
    }

    // Clears the chips
    public void clearChips(){
        greenChips[0].setVisibility(View.INVISIBLE);

        for(int i = 0; i<2; i++){
            blueChips[i].setVisibility(View.INVISIBLE);
        }
        for(int i = 0; i<4; i++){
            yellowChips[i].setVisibility(View.INVISIBLE);
        }
        for(int i = 0; i<3; i++){
            turquoiseChips[i].setVisibility(View.INVISIBLE);
        }
        for(int i = 0; i<4; i++){
            pinkChips[i].setVisibility(View.INVISIBLE);
        }
        for(int i = 0; i<4; i++){
            redChips[i].setVisibility(View.INVISIBLE);
        }
    }

    public void addChips(int value){
        setChipValue(chipValue + value);
    }

    public void removeChips(int value){
        setChipValue(chipValue - value);
    }

    public void setChipValue(){
        setChipValue(-1);
    }

    // Sets chips visible depending on what number is inputted
    public void setChipValue(int newChipValue){
        if(newChipValue >= 0){
            chipValue = newChipValue;
        }
        int tempValue = chipValue;
        clearChips();

        if (tempValue >= 1500){
            greenChips[0].setVisibility(View.VISIBLE);
            tempValue -= 1500;
        }
        int chipID = 0;
        while(tempValue >= 500){
            blueChips[chipID++].setVisibility(View.VISIBLE);
            tempValue -= 500;
        }
        chipID = 0;
        while(tempValue >= 100){
            yellowChips[chipID++].setVisibility(View.VISIBLE);
            tempValue -= 100;
        }
        chipID = 0;
        while(tempValue >= 25){
            turquoiseChips[chipID++].setVisibility(View.VISIBLE);
            tempValue -= 25;
        }
        chipID = 0;
        while(tempValue >= 5){
            pinkChips[chipID++].setVisibility(View.VISIBLE);
            tempValue -= 5;
        }
        chipID = 0;
        while(tempValue >= 1){
            redChips[chipID++].setVisibility(View.VISIBLE);
            tempValue -= 1;
        }

        text.setText(String.valueOf(chipValue));
    }
}