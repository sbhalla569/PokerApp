package com.shivbhalla.comp3003_pokerapp_psysb7;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Chips extends Fragment {
    private ImageView[] redChips; // 1
    private ImageView[] pinkChips; // 5
    private ImageView[] turquoiseChips; // 25
    private ImageView[] yellowChips; // 100
    private ImageView[] blueChips; // 500
    private ImageView[] greenChips; // 1500
    private TextView text;
    private int chipValue;

    public Chips(int value) {
        chipValue = value;
        // Required empty public constructor
    }
    public Chips(){
        chipValue = 0;
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

    // Function for add chips to the pot
    public void addChips(int value){
        setChipValue(chipValue + value);
    }

    // Setting default values
    public void setChipValue(){
        setChipValue(-1);
    }

    //Function to get chip value
    public int getChipValue(){
        return chipValue;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chips, container, false);
    }

    // Access to View
    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Creating Chips
        redChips = new ImageView[4];
        pinkChips = new ImageView[4];
        turquoiseChips = new ImageView[3];
        yellowChips = new ImageView[4];
        blueChips = new ImageView[2];
        greenChips = new ImageView[1];

        redChips[0] = (ImageView) view.findViewById(R.id.red_1);
        redChips[1] = (ImageView) view.findViewById(R.id.red_2);
        redChips[2] = (ImageView) view.findViewById(R.id.red_3);
        redChips[3] = (ImageView) view.findViewById(R.id.red_4);

        pinkChips[0] = (ImageView) view.findViewById(R.id.pink_1);
        pinkChips[1] = (ImageView) view.findViewById(R.id.pink_2);
        pinkChips[2] = (ImageView) view.findViewById(R.id.pink_3);
        pinkChips[3] = (ImageView) view.findViewById(R.id.pink_4);

        turquoiseChips[0] = (ImageView) view.findViewById(R.id.turquoise_1);
        turquoiseChips[1] = (ImageView) view.findViewById(R.id.turquoise_2);
        turquoiseChips[2] = (ImageView) view.findViewById(R.id.turquoise_3);

        yellowChips[0] = (ImageView) view.findViewById(R.id.yellow_1);
        yellowChips[1] = (ImageView) view.findViewById(R.id.yellow_2);
        yellowChips[2] = (ImageView) view.findViewById(R.id.yellow_3);
        yellowChips[3] = (ImageView) view.findViewById(R.id.yellow_4);

        blueChips[0] = (ImageView) view.findViewById(R.id.blue_1);
        blueChips[1] = (ImageView) view.findViewById(R.id.blue_2);

        greenChips[0] = (ImageView) view.findViewById(R.id.green_1);

        text = (TextView) view.findViewById(R.id.chip_value);

        setChipValue();
    }
}