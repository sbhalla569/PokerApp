package com.shivbhalla.comp3003_pokerapp_psysb7;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.shivbhalla.comp3003_pokerapp_psysb7.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    // sends and processes message and runnable objects
//    private static final Handler mainLoop = new Handler();

//    private Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        Button playSingle = findViewById(R.id.single_player_button);
        playSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SinglePlayerGame.class);
                startActivity(intent);
            }
        });

//        deck = new Deck();
//        // Binds Chips to the chip holder on both fragments
//        playerFragment player = new playerFragment(150);
//        getSupportFragmentManager().beginTransaction().add(R.id.chipHolder, player, "player").disallowAddToBackStack().commit();
//        mainLoop.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    int[] hand = deck.drawHand();
//                    player.setCards(hand[0],hand[1]);
//                    // player.showHand();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 200);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}