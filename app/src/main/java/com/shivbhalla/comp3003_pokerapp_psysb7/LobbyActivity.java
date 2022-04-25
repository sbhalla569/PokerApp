package com.shivbhalla.comp3003_pokerapp_psysb7;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class LobbyActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button menuButton = findViewById(R.id.menu_button);
        Button createButton = findViewById(R.id.create_button);

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        menuButton.setOnClickListener(view -> finish());

        createButton.setOnClickListener(view -> {
            // Find the next ID in the list
            FirebaseManager.getNewGameID(game -> {
                int ID = game.gameID + 1;
                Intent intent = new Intent(LobbyActivity.this, MultiPlayerGame.class);
                intent.putExtra("gameID", ID);
                finish();
                startActivity(intent);
            });
            // Go to the game

        });
        final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
        // Displays lobby information
        FirebaseManager.getGameList(list -> {
            if(list != null){
                String email = Objects.requireNonNull(auth.getCurrentUser()).getEmail();
                List<GameInfo> gameList = Arrays.stream(list).filter(gameInfo -> {
                    boolean playerSize = gameInfo.players.size() < 4;
                    boolean isPlaying = gameInfo.players.stream().anyMatch(player -> player.getEmail().equals(email));
                    return playerSize || isPlaying;
                }).collect(Collectors.toList());
                recyclerAdapter.setData(gameList);
            }
        });
    }
}