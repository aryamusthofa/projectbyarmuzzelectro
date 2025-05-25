package com.armuzzelectro.myprofileinformation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class DeleteLinkActivity extends AppCompatActivity {

    private ArrayList<Integer> selectedIndexes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_link);

        TextView deleteList = findViewById(R.id.deleteList);
        Button btnDeleteConfirm = findViewById(R.id.btnDeleteConfirm);
        Button btnCancelDelete = findViewById(R.id.btnCancelDelete);

        // Ambil nama-nama link dan index
        ArrayList<String> linkNames = getIntent().getStringArrayListExtra("link_names");
        selectedIndexes = getIntent().getIntegerArrayListExtra("link_indexes");

        // Tampilkan daftar link
        StringBuilder builder = new StringBuilder("Link berikut akan dihapus:\n\n");
        for (String name : Objects.requireNonNull(linkNames)) {
            builder.append("• ").append(name).append("\n");
        }
        deleteList.setText(builder.toString());

        btnDeleteConfirm.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putIntegerArrayListExtra("link_indexes_to_delete", selectedIndexes);
            setResult(RESULT_OK, result);
            finish();
        });

        btnCancelDelete.setOnClickListener(v -> finish());
    }
}
