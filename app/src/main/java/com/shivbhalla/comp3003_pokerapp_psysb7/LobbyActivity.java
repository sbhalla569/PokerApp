package com.shivbhalla.comp3003_pokerapp_psysb7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LobbyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button menuButton = findViewById(R.id.menu_button);
        Button createButton = findViewById(R.id.create_button);

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


    }
}