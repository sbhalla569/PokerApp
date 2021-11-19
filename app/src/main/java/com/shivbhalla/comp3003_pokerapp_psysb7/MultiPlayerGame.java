package com.shivbhalla.comp3003_pokerapp_psysb7;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.shivbhalla.comp3003_pokerapp_psysb7.databinding.ActivitySinglerPlayerGameBinding;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MultiPlayerGame extends AppCompatActivity {

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
            // Check if it is my action
            // Enable buttons
            // When and who is going to move state forward (dealer?)
            mainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 10000);
        }
    };

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
    private ActivitySinglerPlayerGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySinglerPlayerGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mVisible = true;

        for(int i = 0; i<4; i++){
            playerPotValue[i] = 0;
        }
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

        raiseButton.setOnClickListener(View -> {
            if(players[0].getChipValue() >= 50 && !players[0].getFolded() && !playerActed){
                players[0].setChipValue(players[0].getChipValue() - 50);
                pot.addChips(50);
                playerPotValue[0] += 50;
                playerActed = true;
            }
        });

        foldButton.setOnClickListener(View -> {
            players[0].fold();
            playerActed = true;
        });
        callButton.setOnClickListener(View -> {
            if(!players[0].getFolded()){
                playerActed = true;
            }
        });

        deck = new Deck();
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(playerFragment player : players){
//                    try {
//                        int[] hand = deck.drawHand();
//                        player.setCards(hand[0],hand[1]);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    player.showHand();
                    player.setChipValue(700);
                    try {
                        player.setCards(deck.drawCard(),deck.drawCard());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                players[0].showHand();
                setCurrentDealer(0);
                assert pot != null;
                addBlinds();
                mainHandler.postDelayed(mMainLoop, 100);
            }
        }, 200);


    }

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