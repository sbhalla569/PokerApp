package com.shivbhalla.comp3003_pokerapp_psysb7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button createAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        email = findViewById(R.id.create_username);
        password = findViewById(R.id.create_password);
        createAcc = findViewById(R.id.create);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(CreateActivity.this, "You are Registered", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(CreateActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}