package com.armuzzelectro.myprofileinformation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditLinkActivity extends AppCompatActivity {

    private EditText inputEditName, inputEditUrl;

    private int editIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_link);

        inputEditName = findViewById(R.id.inputEditName);
        inputEditUrl = findViewById(R.id.inputEditUrl);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnCancelEdit = findViewById(R.id.btnCancelEdit);

        // Ambil data dari intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("link_name");
        String url = intent.getStringExtra("link_url");
        editIndex = intent.getIntExtra("link_index", -1);

        inputEditName.setText(name);
        inputEditUrl.setText(url);

        btnUpdate.setOnClickListener(v -> {
            String newName = inputEditName.getText().toString().trim();
            String newUrl = inputEditUrl.getText().toString().trim();

            if (TextUtils.isEmpty(newName) || TextUtils.isEmpty(newUrl)) {
                Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newUrl.startsWith("http://") && !newUrl.startsWith("https://")) {
                newUrl = "https://" + newUrl;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("new_name", newName);
            resultIntent.putExtra("new_url", newUrl);
            resultIntent.putExtra("link_index", editIndex);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        btnCancelEdit.setOnClickListener(v -> finish());
    }
}
