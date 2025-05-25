package com.armuzzelectro.myprofileinformation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    Button btnContinueWithEmail, btnSignAsGuest;
    TextView btnGoToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome); // Pastikan ini sesuai dengan XML yang dipakai

        btnContinueWithEmail = findViewById(R.id.btnContinueWithEmail);
        btnSignAsGuest = findViewById(R.id.btnSignAsGuest);
        btnGoToSignUp = findViewById(R.id.btnGoToSignUp);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null && user.isEmailVerified()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }


        // Ubah logika tombol "Continue with Email" untuk menuju ke SignupActivity
        btnContinueWithEmail.setOnClickListener(v ->
                startActivity(new Intent(WelcomeActivity.this, SignupActivity.class)));

        // Tombol "Sign Up Now" tidak lagi diperlukan, kita bisa mengarahkan fungsinya ke tombol "Continue with Email"
        // Jika Anda ingin menghapus tombol "Sign Up Now" dari layout, Anda bisa menghapus baris kode di bawah ini:


       /* btnGoToSignUp.setOnClickListener(v ->
                startActivity(new Intent(WelcomeActivity.this, SignupActivity.class)));

        btnSignAsGuest.setOnClickListener(v -> {
            // Guest login bisa lanjut ke MainActivity tanpa login
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        }); */

        btnSignAsGuest.setOnClickListener(v ->
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class)));
    }
}
