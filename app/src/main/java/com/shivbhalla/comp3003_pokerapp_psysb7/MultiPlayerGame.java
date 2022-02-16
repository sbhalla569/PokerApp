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

import com.google.firebase.auth.FirebaseAuth;
import com.shivbhalla.comp3003_pokerapp_psysb7.databinding.ActivityMultiPlayerGameBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
    private int gameID;
    private int thisPlayer;
    private GameInfo info;
    
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
            FirebaseManager.getGameInfo(gameID, new GameInfo.IGameReceiver() {
                @Override
                public void receiveGame(GameInfo game) {
                    if(game != null){
                        List<Player> playerList = game.getPlayers();
                        if(game.gameState == 2){
                            if(playerList.get(thisPlayer).getChipValue() > 0){
                                win.setVisibility(View.VISIBLE);
                            }else{
                                lose.setVisibility(View.VISIBLE);
                            }
                            return;
                        }

                        for(int i=0; i<4; i++){
                            players[i].setChipValue(0);
                            if(i < playerList.size()){
                                players[i].setChipValue(playerList.get(i).getChipValue());
                            }
                        }
                        // TODO: validate game object
                        if(game.currentPlayer == thisPlayer){
                            // Perform player actions
                            info = game;
                            if(players[thisPlayer].getChipValue() > 0) {
                                playerActed = false;
                                raiseButton.setVisibility(View.VISIBLE);
                                callButton.setVisibility(View.VISIBLE);
                                foldButton.setVisibility(View.VISIBLE);
                                int highest = 0;
                                for(int i = 0; i < 4; i++){
                                    if(info.table.get(i) > highest){
                                        highest = info.table.get(i);
                                    }
                                }
                                if(info.table.get(thisPlayer) == highest){

                                }
                            }else{
                                info.setCurrentPlayer((info.getCurrentPlayer() + 1) % info.getPlayers().size());
                                int count = 0;
                                for(int i = 0; i<4; i++){
                                    if(players[i].getChipValue()<1){
                                        count++;
                                    }
                                }
                                if(count == 3){
                                    info.setGameState(2);
                                }
                                FirebaseManager.setGameInfo(info);
                            }
                            return;
                        }
                    }
                    mainHandler.postDelayed(mMainLoop, 1000);
                }
            });
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
    private ActivityMultiPlayerGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMultiPlayerGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ImageView[] playerMarkers = new ImageView[4];
        playerMarkers[0] = findViewById(R.id.player_marker_0);
        playerMarkers[1] = findViewById(R.id.player_marker_1);
        playerMarkers[2] = findViewById(R.id.player_marker_2);
        playerMarkers[3] = findViewById(R.id.player_marker_3);

        final FirebaseAuth auth = FirebaseAuth.getInstance();

        gameID = getIntent().getIntExtra("gameID",-1);
        FirebaseManager.getGameInfo(gameID, new GameInfo.IGameReceiver() {
            @Override
            public void receiveGame(GameInfo game) {
                String displayName = Objects.requireNonNull(auth.getCurrentUser()).getEmail();
                if(game == null){
                    GameInfo gi = new GameInfo();
                    gi.setGameID(gameID);
                    gi.setPot(0);

                    List<Player> players = new ArrayList<>();
                    players.add(new Player(500, displayName));
                    gi.setPlayers(players);
                    gi.setCurrentPlayer(-1);
                    FirebaseManager.setGameInfo(gi);
                    thisPlayer = 0;
                    playerMarkers[0].setVisibility(View.VISIBLE);
                    return;
                }
                List<Player> players = game.getPlayers();
                for(int i=0; i< players.size(); i++){
                    if(players.get(i).getUsername().equals(displayName)){
                        thisPlayer = i;
                        playerMarkers[i].setVisibility(View.VISIBLE);
                        return;
                    }
                }
                if(players.size() >= 4){
                    finish();
                    return;
                }
                thisPlayer = players.size();
                playerMarkers[thisPlayer].setVisibility(View.VISIBLE);
                players.add(new Player(500, displayName));
                game.setPlayers(players);
                if(players.size() > 3){
                    game.setCurrentPlayer(3);
                    game.setGameState(1);
                    addBlinds(game, 3);
                }
                FirebaseManager.setGameInfo(game);
            }
        });
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


        // Raise sorted
        raiseButton.setOnClickListener(V -> {
            if(playerActed){
                return;
            }
            if(players[thisPlayer].getChipValue() >= 50 && !players[thisPlayer].getFolded() && !playerActed){
                int raiseValue = 50;
                int highest = 0;
                for(int i = 0; i < 4; i++){
                    if(info.table.get(i) > highest){
                        highest = info.table.get(i);
                    }
                }
                // DOUBLE CHECK FOR SAFETY
                raiseValue += highest - info.table.get(thisPlayer);
                if(info.players.get(thisPlayer).getChipValue() < raiseValue){
                    raiseValue = info.players.get(thisPlayer).getChipValue();
                }
                players[thisPlayer].setChipValue(players[thisPlayer].getChipValue() - raiseValue);
                Objects.requireNonNull(info.getPlayers().get(thisPlayer)).setChipValue(players[thisPlayer].getChipValue());
                pot.addChips(raiseValue);
                info.setPot(pot.getChipValue());
                playerPotValue[0] += raiseValue;
                info.table.set(thisPlayer, info.table.get(thisPlayer) + raiseValue);
                info.setCurrentPlayer((info.getCurrentPlayer() + 1) % info.getPlayers().size());
                FirebaseManager.setGameInfo(info);
                playerActed = true;
                raiseButton.setVisibility(View.GONE);
                callButton.setVisibility(View.GONE);
                foldButton.setVisibility(View.GONE);
                mainHandler.postDelayed(mMainLoop, 1000);
            }
        });

        // Folding sorted
        foldButton.setOnClickListener(V -> {
            if(playerActed){
                return;
            }
            players[thisPlayer].fold();
            Objects.requireNonNull(info.getPlayers().get(thisPlayer)).setFolded(true);
            info.setCurrentPlayer((info.getCurrentPlayer() + 1) % info.getPlayers().size());
            FirebaseManager.setGameInfo(info);
            playerActed = true;
            raiseButton.setVisibility(View.GONE);
            callButton.setVisibility(View.GONE);
            foldButton.setVisibility(View.GONE);
            mainHandler.postDelayed(mMainLoop, 1000);
        });

        // Calling sorted
        callButton.setOnClickListener(V -> {
            if(playerActed){
                return;
            }
            if(!players[thisPlayer].getFolded()){
                info.setCurrentPlayer((info.getCurrentPlayer() + 1) % info.getPlayers().size());
                int highest = 0;
                for(int i = 0; i < 4; i++){
                    if(info.table.get(i) > highest){
                        highest = info.table.get(i);
                    }
                }
                int current = info.table.get(thisPlayer);
                info.players.get(thisPlayer).setChipValue(info.players.get(thisPlayer).getChipValue() - (highest - current));
                info.table.set(thisPlayer, info.table.get(thisPlayer) + (highest - current));
                FirebaseManager.setGameInfo(info);
                playerActed = true;
                raiseButton.setVisibility(View.GONE);
                callButton.setVisibility(View.GONE);
                foldButton.setVisibility(View.GONE);
                mainHandler.postDelayed(mMainLoop, 1000);
            }
        });
        raiseButton.setVisibility(View.GONE);
        callButton.setVisibility(View.GONE);
        foldButton.setVisibility(View.GONE);

        deck = new Deck();
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainHandler.postDelayed(mMainLoop, 100);
            }
        }, 200);


    }

    public void addBlinds(GameInfo gameInfo, int playerValue){
        int i = 1;
        int smallBlind = (playerValue + i) % players.length;
        while(gameInfo.players.get(smallBlind).getChipValue() < 1){
            i++;
            smallBlind = (playerValue + i) % players.length;
        }
        i++;
        int bigBlind = (playerValue + i) % players.length;
        while(gameInfo.players.get(bigBlind).getChipValue() < 1){
            i++;
            bigBlind = (playerValue + i) % players.length;
        }
        gameInfo.players.get(smallBlind).setChipValue(gameInfo.players.get(smallBlind).getChipValue() - 25);
        gameInfo.table.set(smallBlind, 25);
        gameInfo.players.get(bigBlind).setChipValue(gameInfo.players.get(bigBlind).getChipValue() - 50);
        gameInfo.table.set(bigBlind, 50);
        gameInfo.pot += 75;
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