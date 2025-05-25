package com.armuzzelectro.myprofileinformation;

import android.content.Intent;
import android.graphics.Paint;
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
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class SignupActivity extends AppCompatActivity {

    EditText inputFullName, inputEmail, inputPassword;
    Button btnSignUp;
    TextView tvResendVerif;
    ImageView btnBackWelcome; // atau MaterialButton kalau kamu pakai button
    TextView txtLoginNow;
    FirebaseAuth auth;
    ImageView btnViewPassword;
    boolean isPasswordVisible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputFullName = findViewById(R.id.inputFullName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtLoginNow = findViewById(R.id.txtLoginNow);
        tvResendVerif = findViewById(R.id.tvResendVerif);
        btnBackWelcome = findViewById(R.id.btnBackWelcome);
        inputPassword = findViewById(R.id.inputPassword);
        btnViewPassword = findViewById(R.id.btnViewPassword);
        TextView tvResendVerif = findViewById(R.id.tvResendVerif);

        auth = FirebaseAuth.getInstance();

        tvResendVerif.setPaintFlags(tvResendVerif.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        AtomicReference<FirebaseUser> user = new AtomicReference<>(FirebaseAuth.getInstance().getCurrentUser());
        if (user.get() != null && !user.get().isEmailVerified()) {
            user.get().sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Link verifikasi dikirim ke email kamu", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, VerifActivity.class));
                    finish(); // tutup halaman signup
                } else {
                    Toast.makeText(this, "Gagal mengirim email verifikasi", Toast.LENGTH_SHORT).show();
                }
            });
        }


        btnBackWelcome.setOnClickListener(v -> {
            // Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class); // untuk LoginActivity atau
            Intent intent = new Intent(SignupActivity.this, WelcomeActivity.class); // untuk SignupActivity
            startActivity(intent);
            finish(); // biar ga balik lagi ke login/signup pakai back
        });


        tvResendVerif.setOnClickListener(v -> {
            if (user.get() != null) {
                user.get().sendEmailVerification().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignupActivity.this,
                                "Link verifikasi telah dikirim ulang, silahkan cek email Anda.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SignupActivity.this,
                                "Gagal mengirim ulang verifikasi: " + Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(SignupActivity.this,
                        "User belum login. Tidak bisa mengirim ulang verifikasi.",
                        Toast.LENGTH_SHORT).show();
            }
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


        btnSignUp.setOnClickListener(v -> {
            String fullName = inputFullName.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.set(FirebaseAuth.getInstance().getCurrentUser()); // ✅ tanpa `FirebaseUser`

                    if (user.get() != null) {
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(fullName)
                                .build();
                        user.get().updateProfile(profileUpdate);

                        user.get().sendEmailVerification().addOnCompleteListener(emailTask -> {
                            if (emailTask.isSuccessful()) {
                                Toast.makeText(this, "Link verifikasi dikirim ke email kamu. Silakan cek dan klik linknya.", Toast.LENGTH_LONG).show();
                                auth.signOut(); // Logout user setelah signup
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(this, "Gagal mengirim email verifikasi.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "Signup gagal: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        txtLoginNow.setOnClickListener(v ->
                startActivity(new Intent(SignupActivity.this, LoginActivity.class)));
    }
}
