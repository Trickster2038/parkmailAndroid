package com.park.soulmates;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        FirebaseApp.initializeApp(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            // Already logged in
            // Do nothing
            Log.d("AuthStatus", "Already logged in as " + mAuth.getCurrentUser().getEmail());
            Log.d("DB_auth_start_uid", mAuth.getUid());
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_authentication);
            TextView email = findViewById(R.id.editTextEmail);
            TextView password = findViewById(R.id.editTextPassword);
            Button loginButton = findViewById(R.id.button_login);
            Button signupButton = findViewById(R.id.button_signup);
            loginButton.setOnClickListener(v -> mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d("AuthStatus", "Logged in as " + mAuth.getCurrentUser().getEmail());
                            Log.d("DB_auth_onPress_uid", mAuth.getUid());
                            Toast.makeText(AuthActivity.this, "Logged in as " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AuthStatus", "Authentication failed: ", task.getException());
                            Toast.makeText(AuthActivity.this, "Authentication failed: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }));
            signupButton.setOnClickListener(v -> mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d("AuthStatus", "Logged in as " + mAuth.getCurrentUser().getEmail());
                            Toast.makeText(AuthActivity.this, "Logged in as " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AuthStatus", "signInWithEmail:failure", task.getException());
                            Toast.makeText(AuthActivity.this, "Registration failed: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }));
        }
    }
}