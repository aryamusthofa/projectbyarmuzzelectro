package com.armuzzelectro.myprofileinformation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddLinkActivity extends AppCompatActivity {

    private EditText inputName, inputUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_link);

        inputName = findViewById(R.id.inputName);
        inputUrl = findViewById(R.id.inputUrl);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(v -> {
            String name = inputName.getText().toString().trim();
            String url = inputUrl.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(url)) {
                Toast.makeText(this, "Isi semua kolom terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url; // tambahkan https jika user lupa
            }

            // Kirim kembali ke MainActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("link_name", name);
            resultIntent.putExtra("link_url", url);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        btnCancel.setOnClickListener(v -> finish());
    }
}
