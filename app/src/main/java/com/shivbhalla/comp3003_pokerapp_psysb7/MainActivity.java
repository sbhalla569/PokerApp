package com.shivbhalla.comp3003_pokerapp_psysb7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.shivbhalla.comp3003_pokerapp_psysb7.databinding.ActivityMainBinding;

import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    GoogleSignInClient client;
    private FirebaseAuth auth;
    LinearLayout linearLayout;
    TextView displayName;
    EditText changeUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseManager.getGameInfo(0, new GameInfo.IGameReceiver() {
            @Override
            public void receiveGame(GameInfo game) {
                if(game == null){
                    Log.e("Firebase", "Did not retrieve game");
                    return;
                }
                Log.d("Firebase", String.format("game retrieved %d", game.getActingPlayer()));
            }
        });

        auth = FirebaseAuth.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        linearLayout = findViewById(R.id.linearLayout);
        Button playSingle = findViewById(R.id.single_player_button);
        Button playMulti = findViewById(R.id.multi_player_button);
        Button logOut = findViewById(R.id.logout_button);
        displayName = findViewById(R.id.display_name);
        changeUsername = findViewById(R.id.change_username);
        SharedPreferences pref = getSharedPreferences("PokerGame", MODE_PRIVATE);
        changeUsername.setText(pref.getString("Username", "Player"));

        changeUsername.setOnKeyListener((view, i, keyEvent) -> {
            SharedPreferences.Editor editor = getSharedPreferences("PokerGame", MODE_PRIVATE).edit();
            editor.putString("Username", changeUsername.getText().toString());
            editor.apply();
            return false;
        });

        playSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SinglePlayerGame.class);
                startActivity(intent);
            }
        });
        playMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
                    startActivity(intent);
                } catch (NumberFormatException e){
                    System.out.println("ERROR");
                }

            }
        });

        logOut.setOnClickListener(view -> {
            auth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).requestEmail().build();
        client = GoogleSignIn.getClient(this,gso);
        // Create Lobby
        // Create/Join Lobby Button

        // Create Multiplayer Button

        if(auth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            displayName.setText(Objects.requireNonNull(auth.getCurrentUser()).getEmail());
            if(changeUsername.getText().toString().equals("Player")){
                changeUsername.setText(displayName.getText());
                SharedPreferences.Editor editor = getSharedPreferences("PokerGame", MODE_PRIVATE).edit();
                editor.putString("Username", changeUsername.getText().toString());
                editor.apply();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        displayName.setText(Objects.requireNonNull(auth.getCurrentUser()).getEmail());
        if(changeUsername.getText().toString().equals("Player")){
            changeUsername.setText(displayName.getText());
            SharedPreferences.Editor editor = getSharedPreferences("PokerGame", MODE_PRIVATE).edit();
            editor.putString("Username", changeUsername.getText().toString());
            editor.apply();
        }
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

    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser user = auth.getCurrentUser();
//        if(user != null){
//            signInButton.setVisibility(View.GONE);
//            linearLayout.setVisibility(View.VISIBLE);
//        }
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if(account != null){
//            signInButton.setVisibility(View.GONE);
//            linearLayout.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1234){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try{
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                if(account != null){
//                    signInButton.setVisibility(View.GONE);
//                    linearLayout.setVisibility(View.VISIBLE);
//                }
//            }catch (ApiException apiException){
//                Log.w("Poker", "Sign in result: Failed Code = " + apiException.getStatusCode());
//            }
//        }
    }
}