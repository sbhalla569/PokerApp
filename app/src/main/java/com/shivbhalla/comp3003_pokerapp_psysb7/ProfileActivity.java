package com.shivbhalla.comp3003_pokerapp_psysb7;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    TextView favCard;
    TextView timesFolded;
    TextView timesLost;
    TextView timesWon;
    TextView timesShouldHaveWon;
    private FirebaseAuth auth;
    static String[] suits = new String[]{
            "Clubs",
            "Spades",
            "Hearts",
            "Diamonds"
    };
    static String[] card = new String[]{
            "Ace", "Two", "Three", "Four",
            "Five", "Six", "Seven", "Eight",
            "Nine", "Ten", "Jack", "Queen",
            "King"
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        favCard = findViewById(R.id.favourite_card_number);
        timesFolded = findViewById(R.id.times_folded_number);
        timesLost = findViewById(R.id.times_lost_number);
        timesWon = findViewById(R.id.times_won_number);
        timesShouldHaveWon = findViewById(R.id.times_should_have_won_number);
        auth = FirebaseAuth.getInstance();

        String email = Objects.requireNonNull(auth.getCurrentUser()).getEmail();
        FirebaseManager.getStatistics(email, statistics -> {
            if(statistics == null){
                statistics = new Statistics();
                statistics.setEmail(email);
            }
            int largestCard = 0;
            int largestValue = -1;
            for(int i = 0; i<statistics.getCardRaiseValue().size(); i++){
                int cardOneValue = statistics.getCardRaiseValue().get(i);
                if(cardOneValue > largestValue){
                    largestValue = cardOneValue;
                    largestCard = i;
                }
            }
            favCard.setText(card[Cards.cardValue(largestCard)] + " " +"of"+ " " + suits[Cards.cardSuit(largestCard)]);
            timesWon.setText(Integer.toString(statistics.getTimesWon()));
            timesLost.setText(Integer.toString(statistics.getTimesLost()));
            timesFolded.setText(Integer.toString(statistics.getTimesFolded()));
            timesShouldHaveWon.setText(Integer.toString(statistics.getTimesShouldHaveWon()));
        });
    }
}