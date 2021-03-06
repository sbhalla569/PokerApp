package com.shivbhalla.comp3003_pokerapp_psysb7;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.shivbhalla.comp3003_pokerapp_psysb7.databinding.ActivityMultiPlayerGameBinding;
import com.shivbhalla.comp3003_pokerapp_psysb7.databinding.ActivitySinglerPlayerGameBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SinglePlayerGame extends AppCompatActivity {

    private playerFragment[] players;
    private Chips pot;
    private TableCards tableCards;

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mainHandler = new Handler();
    private View mContentView;
    private Deck deck;
    private int state = 0; // 0 = pre flop; 1 = flop; 2 = river; 3 = show cards
    private boolean moveForward = false;
    private boolean playerActed = false;
    private Button callButton;
    private Button foldButton;
    private Button raiseButton;
    private SeekBar raiseBar;
    // Counts how many times looped
    private int runCounter = 0;
    // Who is allowed to make actions
    private int currentAction = 0;
    private int currentDealer = 0;
    private ImageView[] dealerChips;
    // Keeps track of how much goes into the pot
    private int[] playerPotValue = new int[4];
    // How much each player puts in at the current stage
    private int[] roundPotValue = new int[4];
    private FrameLayout win;
    private FrameLayout lose;
    private static final List<String> botNames = new ArrayList<String>(){{
        add("Bob");
        add("Mary");
        add("Jim");
        add("Henry");
        add("Sophie");
        add("Milena");
        add("Harry");
    }};

    // Function to get the raise value of players
    private int getCurrentRaiseValue(){
        int maxValue = 0;
        for(int value : playerPotValue){
            if(value > maxValue){
                maxValue = value;
            }
        }
        return maxValue;
    }

    // Main update loop of game
    private final Runnable mMainLoop = new Runnable() {
        @Override
        public void run() {
            int playersFolded = 0;
            for(int i = 0; i<players.length; i++) {
                if (players[i].getFolded()) {
                    playersFolded++;
                }
            }
            // AI section
            if(playersFolded > 2){
                moveForward = true;
            }else if(playerActed){
                boolean action = false;
                int raiseValue = getCurrentRaiseValue();
                for(int i = 1; i<players.length; i++){
                    if(players[i].getFolded() || players[0].getChipValue() < 1){
                        continue;
                    }
                    int callValue = raiseValue - playerPotValue[i];
                    if(callValue > 0){
                        double winChance = tableCards.getWinChance(players[i].getCards(), state == 0? 4 : 1);
                        double playerLoss = roundPotValue[i] / 800.0;
                        winChance -= playerLoss;
                        // Figure out if we need to fold
                        if(winChance < players[i].getFoldValue()){
                            players[i].fold();
                            continue;
                        }
                        action = true;
                        // Figure out if we need to raise
                        if(winChance > players[i].getRaiseValue()){
                            pot.addChips(callValue + 50);
                            players[i].removeChips(callValue + 50);
                            playerPotValue[i] += callValue + 50;
                            roundPotValue[i] += callValue + 50;
                            continue;
                        }
                        // Otherwise call
                        pot.addChips(callValue);
                        players[i].removeChips(callValue);
                        playerPotValue[i] += callValue;
                        roundPotValue[i] += callValue;
                    }
                }
                playerActed = players[0].getFolded();
                moveForward = !action;
            }
            if (moveForward){
                moveForward = false;
                switch (state){
                    case 0:
                        // Flop
                        state = 1;
                        try {
                            tableCards.showRiver(deck.drawRiver());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        roundPotValue = new int[players.length];
                        break;
                    case 1:
                        // Turn
                        state = 2;
                        try {
                            tableCards.showFlop(deck.drawCard());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        roundPotValue = new int[players.length];
                        break;
                    case 2:
                        // River
                        state = 3;
                        try {
                            tableCards.showTurn(deck.drawCard());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        roundPotValue = new int[players.length];
                        break;
                    case 3:
                        state = 4;
                        // Determine best hand using Hand Evaluation Algorithm
                        int[] value;
                        int bestHand = 0;
                        int bestCard = 0;
                        int bestPlayer = 0;
                        for (int i = 0; i <players.length; i++){
                            playerFragment player = players[i];
                            // If player fold they are out of the game
                            if(player.getFolded()){
                                continue;
                            }
                            player.showHand();
                            value = tableCards.getBestHand(player.getCards());
                            if(value[0] >= bestHand){
                                if(value[0] > bestHand || value[1] > bestCard){
                                    bestPlayer = i;
                                    bestHand = value[0];
                                    bestCard = value[1];
                                }
                            }
                        }
                        // Giving winning player chips
                        players[bestPlayer].addChips(pot.getChipValue());
                        pot.setChipValue(0);
                        break;
                    case 4:
                        // Reset Game
                        state = 0;
                        deck.shuffle();
                        for (int i = 0; i<4; i++){
                            try {
                                if(players[i].getChipValue() > 0){
                                    players[i].setCards(deck.drawCard(),deck.drawCard());
                                }else{
                                    players[i].fold();
                                }
                                // Resets pot value
                                playerPotValue[i] = 0;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        tableCards.reset();
                        players[0].showHand();
                        int endDeal = currentDealer;
                        currentDealer = (currentDealer + 1) % players.length;
                        while(currentDealer != endDeal){
                            if(players[currentDealer].getChipValue() > 0){
                                break;
                            }
                            currentDealer = (currentDealer + 1) % players.length;
                        }
                        setCurrentDealer(currentDealer);
                        addBlinds();
                        break;

                }
            }

            int playersOut = 0;
            boolean playerWins = true;
            for(int i = 0; i<players.length; i++) {
                if (players[i].getChipValue() < 1) {
                    playersOut++;
                }
            }
            if(players[0].getChipValue() < 1){
                playersOut = 3;
                playerWins = false;
            }
            if(playersOut < 3){
                mainHandler.postDelayed(mMainLoop, players[0].getFolded()? 1000:100);
                return;
            }
            //Display win or loss screen, button which takes back to main page
            raiseButton.setVisibility(View.GONE);
            callButton.setVisibility(View.GONE);
            foldButton.setVisibility(View.GONE);
            if(playerWins){
                win.setVisibility(View.VISIBLE);
            }else {
                lose.setVisibility(View.VISIBLE);
            }
            mainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 10000);
        }
    };

    // Setting the appropriate dealer
    private void setCurrentDealer(int newDealer){
        for(int i = 0; i<dealerChips.length; i++){
            dealerChips[i].setVisibility(i == newDealer? View.VISIBLE:View.INVISIBLE);
        }
        currentDealer = newDealer;
    }

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        delayedHide(AUTO_HIDE_DELAY_MILLIS);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return false;
        }
    };
    private ActivityMultiPlayerGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMultiPlayerGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mVisible = true;

        for(int i = 0; i<4; i++){
            playerPotValue[i] = 0;
        }
        // Initialising objects
        players = new playerFragment[4];
        players[0] = (playerFragment) getSupportFragmentManager().findFragmentById(R.id.player_1);
        players[1] = (playerFragment) getSupportFragmentManager().findFragmentById(R.id.player_2);
        players[2] = (playerFragment) getSupportFragmentManager().findFragmentById(R.id.player_3);
        players[3] = (playerFragment) getSupportFragmentManager().findFragmentById(R.id.player_4);

        dealerChips = new ImageView[4];
        dealerChips[0] = (ImageView) findViewById(R.id.dealer_0);
        dealerChips[1] = (ImageView) findViewById(R.id.dealer_1);
        dealerChips[2] = (ImageView) findViewById(R.id.dealer_2);
        dealerChips[3] = (ImageView) findViewById(R.id.dealer_3);

        pot = (Chips) getSupportFragmentManager().findFragmentById(R.id.pot);
        tableCards = (TableCards) getSupportFragmentManager().findFragmentById(R.id.table_cards);
        callButton = findViewById(R.id.call_button);
        foldButton = findViewById(R.id.fold_button);
        raiseButton = findViewById(R.id.raise_button);
        win = findViewById(R.id.win_frame);
        lose = findViewById(R.id.lose_frame);
        raiseBar = findViewById(R.id.raiseslider);
        raiseBar.setVisibility(View.INVISIBLE);

        // Raise button
        raiseButton.setOnClickListener(View -> {
            if(players[0].getChipValue() >= 50 && !players[0].getFolded() && !playerActed){
                players[0].setChipValue(players[0].getChipValue() - 50);
                pot.addChips(50);
                playerPotValue[0] += 50;
                playerActed = true;
            }
        });
        // Fold Button
        foldButton.setOnClickListener(View -> {
            players[0].fold();
            playerActed = true;
        });
        // Call Button
        callButton.setOnClickListener(View -> {
            if(!players[0].getFolded()){
                playerActed = true;
            }
        });

        // Initialising deck
        deck = new Deck();
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(playerFragment player : players){
                    player.setChipValue(700);
                    try {
                        player.setCards(deck.drawCard(),deck.drawCard());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // Setting players and bots names
                players[0].showHand();
                Random rand = new Random();
                players[0].setDisplayName("Player");
                players[1].setDisplayName(botNames.get(rand.nextInt(7)));
                players[2].setDisplayName(botNames.get(rand.nextInt(7)));
                players[3].setDisplayName(botNames.get(rand.nextInt(7)));
                setCurrentDealer(0);
                assert pot != null;
                addBlinds();
                mainHandler.postDelayed(mMainLoop, 100);
            }
        }, 200);


    }

    // Adding the blinds to pot and removing from player
    public void addBlinds(){
        int smallBlind = (currentDealer + 1) % players.length;
        int bigBlind = (currentDealer + 2) % players.length;
        players[smallBlind].removeChips(25);
        players[bigBlind].removeChips(50);
        playerPotValue[smallBlind] += 25;
        playerPotValue[bigBlind] += 50;
        roundPotValue[smallBlind] += 25;
        roundPotValue[bigBlind] += 50;
        pot.addChips(75);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
//        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mainHandler.removeCallbacks(mShowPart2Runnable);
        mainHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mainHandler.removeCallbacks(mHidePart2Runnable);
        mainHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mainHandler.removeCallbacks(mHideRunnable);
        mainHandler.postDelayed(mHideRunnable, delayMillis);
    }
}