package com.shivbhalla.comp3003_pokerapp_psysb7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link playerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class playerFragment extends Fragment {

    private ImageView leftCard;
    private ImageView rightCard;

    private ImageView[] redChips; // 1
    private ImageView[] pinkChips; // 5
    private ImageView[] turquoiseChips; // 25
    private ImageView[] yellowChips; // 100
    private ImageView[] blueChips; // 500
    private ImageView[] greenChips; // 1500
    private TextView text;
    private TextView displayName;
    private int chipValue;
    private boolean showCards = false;
    private boolean hasFolded = false;
    private int leftCardValue;
    private int rightCardValue;
    private float foldValue;
    private float raiseValue;


    public playerFragment(int value) {
        chipValue = value;
        Random rand = new Random();
        foldValue = (rand.nextFloat() * 0.3f) + 0.1f;
        raiseValue = (rand.nextFloat() * 0.3f) + 0.6f;
        // Required public constructor
    }
    public playerFragment (){
        this(500);
    }

    public float getFoldValue() {
        return foldValue;
    }

    public float getRaiseValue() {
        return raiseValue;
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

    // Function to get the chip value
    public int getChipValue(){
        return chipValue;
    }

    // Function to fold the player
    public void fold(){
        hasFolded = true;
        leftCard.setImageResource(R.drawable.card_back);
        rightCard.setImageResource(R.drawable.card_back);
        leftCard.setVisibility(View.INVISIBLE);
        rightCard.setVisibility(View.INVISIBLE);
    }

    // Function to get true or false if the player has folded
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
        leftCard.setVisibility(View.VISIBLE);
        rightCard.setVisibility(View.VISIBLE);
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

        // Creating and Initialising Chips
        redChips = new ImageView[4];
        pinkChips = new ImageView[4];
        turquoiseChips = new ImageView[3];
        yellowChips = new ImageView[4];
        blueChips = new ImageView[2];
        greenChips = new ImageView[2];

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
        greenChips[1] = (ImageView) view.findViewById(R.id.green_b);

        text = (TextView) view.findViewById(R.id.chip_value2);
        displayName = (TextView) view.findViewById(R.id.displayname);

        setChipValue();
        return view;
    }

    // Function to set users username
    public void setDisplayName(String displayName){
        this.displayName.setText(displayName);
    }

    // Clears the chips
    public void clearChips(){

        for(int i = 0; i<2; i++){
            greenChips[i].setVisibility(View.INVISIBLE);
        }
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

    // Function to add chips
    public void addChips(int value){
        setChipValue(chipValue + value);
    }

    // Function to remove chips
    public void removeChips(int value){
        setChipValue(chipValue - value);
    }

    // Default values
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

        int chipID = 0;
        while (tempValue >= 1500){
            greenChips[chipID++].setVisibility(View.VISIBLE);
            tempValue -= 1500;
        }
        chipID = 0;
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