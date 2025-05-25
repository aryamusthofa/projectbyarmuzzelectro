package com.armuzzelectro.myprofileinformation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifActivity extends AppCompatActivity {

    private FirebaseUser user;
    Button btnCheckVerif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        btnCheckVerif = findViewById(R.id.btnCheckVerif);

        btnCheckVerif.setOnClickListener(v -> {
            if (user != null) {
                user.reload().addOnSuccessListener(aVoid -> {
                    if (user.isEmailVerified()) {
                        Toast.makeText(this, "Email sudah diverifikasi", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Email belum diverifikasi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
