package com.armuzzelectro.myprofileinformation;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class LoginActivity extends AppCompatActivity {

    EditText inputEmail, inputPassword;
    Button btnLogin;
    TextView txtSignUpNow;
    ImageView btnBackWelcome; // atau MaterialButton kalau kamu pakai button
    FirebaseAuth auth;
    ImageView btnViewPassword;
    boolean isPasswordVisible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUpNow = findViewById(R.id.txtSignUpNow);
        btnBackWelcome = findViewById(R.id.btnBackWelcome);
        inputPassword = findViewById(R.id.inputPassword);
        btnViewPassword = findViewById(R.id.btnViewPassword);
        auth = FirebaseAuth.getInstance();

        AtomicReference<FirebaseUser> user = new AtomicReference<>(FirebaseAuth.getInstance().getCurrentUser());
        if (user.get() != null && user.get().isEmailVerified()) {
            // Akses ke MainActivity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Email belum diverifikasi!", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut(); // force logout
        }

        btnBackWelcome.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class); // untuk LoginActivity
            // atau Intent intent = new Intent(SignupActivity.this, WelcomeActivity.class); // untuk SignupActivity
            startActivity(intent);
            finish(); // biar ga balik lagi ke login/signup pakai back
        });


        btnViewPassword.setOnClickListener(v -> {
            if (isPasswordVisible) {
                // Kembali sembunyi (mode password)
                inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                btnViewPassword.setImageResource(R.drawable.ic_eye_off);
            } else {
                // Tampilkan password
                inputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                btnViewPassword.setImageResource(R.drawable.ic_eye_on);
            }

            // Update status dan posisi kursor
            isPasswordVisible = !isPasswordVisible;
            inputPassword.setSelection(inputPassword.getText().length());
        });


        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            // Akses ke MainActivity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Email belum diverifikasi!", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut(); // force logout
        }*/


        btnLogin.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.set(auth.getCurrentUser());
                    if (user.get() != null && user.get().isEmailVerified()) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Akun belum diverifikasi. Silakan cek email kamu dan klik link verifikasi.", Toast.LENGTH_LONG).show();
                        auth.signOut();
                    }
                } else {
                    Toast.makeText(this, "Login gagal: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        txtSignUpNow.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
    }
}
