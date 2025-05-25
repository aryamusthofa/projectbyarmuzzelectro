package com.armuzzelectro.myprofileinformation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linkContainer;
    private TextView txtEmptyMessage;
    private CircleImageView imgProfile;
    private final ArrayList<LinkModel> linkList = new ArrayList<>();
    private final HashMap<CheckBox, LinkModel> selectedMap = new HashMap<>();
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private String uid;
    private Uri selectedImageUri;
    private final ActivityResultLauncher<Intent> addLinkLauncher;
    private ActivityResultLauncher<Intent> editLinkLauncher;
    private ActivityResultLauncher<Intent> deleteLinkLauncher;


    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    if (isOnline()) {
                        uploadProfileImageToFirebase(selectedImageUri);
                    } else {
                        // simpan lokal jika offline
                        getSharedPreferences("profile", MODE_PRIVATE)
                                .edit().putString("pending_profile_uri", selectedImageUri.toString()).apply();
                        imgProfile.setImageURI(selectedImageUri);
                    }

                }
            });

    // wajib ada constructor kosong default
    public MainActivity() {
        addLinkLauncher = null;
    }

    private void uploadProfileImageToFirebase(Uri uri) {
        if (uri == null) return;

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_loading, null);
        AppCompatDialog loadingDialog = new AlertDialog.Builder(this, R.style.TransparentDialog)
                .setView(view)
                .setCancelable(false)
                .create();

        if (loadingDialog.getWindow() != null) {
            loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        loadingDialog.show();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                .child("profile_images/" + uid + ".jpg");

        storageRef.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                    firestore.collection("users").document(uid)
                            .update("profile_image_url", downloadUri.toString());

                    Glide.with(this).load(downloadUri).into(imgProfile);

                    loadingDialog.dismiss();
                    Toast.makeText(this, "Foto profil berhasil diunggah", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    loadingDialog.dismiss();
                    Toast.makeText(this, "Gagal mengambil URL foto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                })
                ).addOnFailureListener(e -> {
                    loadingDialog.dismiss();
                    Toast.makeText(this, "Upload foto gagal: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgProfile = findViewById(R.id.imgProfile);
        ImageButton btnEditProfile = findViewById(R.id.btnEditProfile);
        txtEmptyMessage = findViewById(R.id.txtEmptyMessage);
        linkContainer = findViewById(R.id.linkContainer);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);
        ImageButton btnLogout = findViewById(R.id.btnLogout);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        // Ambil foto profil dari local
        String savedUri = getSharedPreferences("profile", MODE_PRIVATE).getString("profile_uri", null);
        if (savedUri != null) {
            selectedImageUri = Uri.parse(savedUri);
            imgProfile.setImageURI(selectedImageUri);
        }

        // Sinkronisasi otomatis
        NetworkChangeReceiver.setOnNetworkAvailableListener(() -> {
            Toast.makeText(this, "Online lagi! Sinkronisasi...", Toast.LENGTH_SHORT).show();

            syncLinksToFirebase();

            // Upload pending foto profil
            String pendingUri = getSharedPreferences("profile", MODE_PRIVATE)
                    .getString("pending_profile_uri", null);
            if (pendingUri != null) {
                uploadProfileImageToFirebase(Uri.parse(pendingUri));
                getSharedPreferences("profile", MODE_PRIVATE).edit().remove("pending_profile_uri").apply();
            }
        });

        //notif belum tersimpan
        btnLogout.setOnClickListener(v -> {
            if (!isOnline()) {
                Toast.makeText(this, "Anda sedang offline. Data Anda belum tersimpan ke cloud.", Toast.LENGTH_LONG).show();
            } else {
                syncLinksToFirebase(); // simpan data saat online
            }

            auth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // Load data
        firestore.collection("users").document(uid).get().addOnSuccessListener(doc -> {
            if (doc.exists()) {
                String json = doc.getString("link_list_json");
                String imageUrl = doc.getString("profile_image_url");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Glide.with(this).load(imageUrl).into(imgProfile);
                }

                if (json != null) {
                    linkList.clear();
                    linkList.addAll(new Gson().fromJson(json, new TypeToken<ArrayList<LinkModel>>(){}.getType()));
                    LocalStorageHelper.saveLinks(this, linkList);
                    renderLinks();
                    return;
                }
            }
            // Kalau Firebase kosong, ambil dari lokal
            linkList.clear();
            linkList.addAll(LocalStorageHelper.loadLinks(this));
            renderLinks();
        });

        // Aksi tombol
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddLinkActivity.class);
            addLinkLauncher.launch(intent);
        });

        btnEdit.setOnClickListener(v -> {
            LinkModel selected = getSelectedOne();
            if (selected != null) {
                int index = linkList.indexOf(selected);
                Intent intent = new Intent(this, EditLinkActivity.class);
                intent.putExtra("link_name", selected.getName());
                intent.putExtra("link_url", selected.getUrl());
                intent.putExtra("link_index", index);
                editLinkLauncher.launch(intent);
                editLinkLauncher = registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                                int indexFromResult = result.getData().getIntExtra("link_index", -1);
                                String newName = result.getData().getStringExtra("new_name");
                                String newUrl = result.getData().getStringExtra("new_url");

                                if (indexFromResult != -1) {
                                    linkList.set(indexFromResult, new LinkModel(newName, newUrl));
                                    LocalStorageHelper.saveLinks(this, linkList);
                                    renderLinks();
                                    syncLinksToFirebase();
                                }

                            }
                        });

            } else {
                Toast.makeText(this, "Pilih satu link untuk edit", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            ArrayList<LinkModel> selectedLinks = getSelectedLinks();
            if (selectedLinks.isEmpty()) {
                Toast.makeText(this, "Pilih link yang akan dihapus", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<String> names = new ArrayList<>();
            ArrayList<Integer> indexes = new ArrayList<>();
            for (LinkModel link : selectedLinks) {
                names.add(link.getName());
                indexes.add(linkList.indexOf(link));
            }

            Intent intent = new Intent(this, DeleteLinkActivity.class);
            intent.putStringArrayListExtra("link_names", names);
            intent.putIntegerArrayListExtra("link_indexes", indexes);
            deleteLinkLauncher.launch(intent);
            deleteLinkLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            ArrayList<Integer> toDelete = result.getData().getIntegerArrayListExtra("link_indexes_to_delete");
                            if (toDelete != null) {
                                toDelete.sort(Collections.reverseOrder());
                                for (int index : toDelete) {
                                    linkList.remove(index);
                                }
                                LocalStorageHelper.saveLinks(this, linkList);
                                renderLinks();
                                syncLinksToFirebase();
                            }
                        }
                    });

        });

        btnLogout.setOnClickListener(v -> {
            syncLinksToFirebase(); // <-- WAJIB! Simpan ke Firebase sebelum logout
            auth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            if (selectedImageUri != null) {
                getSharedPreferences("profile", MODE_PRIVATE)
                        .edit().putString("profile_uri", selectedImageUri.toString()).apply();
            }
        });


        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        NetworkChangeReceiver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NetworkChangeReceiver.unregister(this); // bisa dikembangkan jika kamu simpan reference ke callback
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int REQUEST_ADD_LINK = 100;
        if (requestCode == REQUEST_ADD_LINK && resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra("link_name");
            String url = data.getStringExtra("link_url");
            linkList.add(new LinkModel(name, url));
            LocalStorageHelper.saveLinks(this, linkList);
            renderLinks();
            syncLinksToFirebase(); // Tambahan
        }

        int REQUEST_EDIT_LINK = 101;
        if (requestCode == REQUEST_EDIT_LINK && resultCode == RESULT_OK && data != null) {
            int index = data.getIntExtra("link_index", -1);
            String newName = data.getStringExtra("new_name");
            String newUrl = data.getStringExtra("new_url");

            if (index != -1) {
                linkList.set(index, new LinkModel(newName, newUrl));
                LocalStorageHelper.saveLinks(this, linkList);
                renderLinks();
                syncLinksToFirebase(); // Tambahan
            }
        }

        int REQUEST_DELETE_LINK = 102;
        if (requestCode == REQUEST_DELETE_LINK && resultCode == RESULT_OK && data != null) {
            ArrayList<Integer> toDelete = data.getIntegerArrayListExtra("link_indexes_to_delete");
            if (toDelete != null) {
                toDelete.sort(Collections.reverseOrder());
                for (int index : toDelete) {
                    linkList.remove(index);
                }
                LocalStorageHelper.saveLinks(this, linkList);
                renderLinks();
                syncLinksToFirebase(); // Tambahan
            }
        }
    }

    private void renderLinks() {
        linkContainer.removeAllViews();
        selectedMap.clear();

        if (linkList.isEmpty()) {
            txtEmptyMessage.setVisibility(View.VISIBLE);
            return;
        }

        txtEmptyMessage.setVisibility(View.GONE);

        for (LinkModel link : linkList) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            CheckBox cb = new CheckBox(this);
            Button btn = new Button(this);
            btn.setText(link.getName());
            selectedMap.put(cb, link);

            btn.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.getUrl()));
                startActivity(intent);
            });

            row.addView(cb);
            row.addView(btn);
            linkContainer.addView(row);
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = cm.getActiveNetwork();
        if (network == null) return false;
        NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }

    private void syncLinksToFirebase() {
        if (!isOnline()) return;
        String json = new Gson().toJson(linkList);
        firestore.collection("users").document(uid)
                .set(Collections.singletonMap("link_list_json", json));
    }

    private ArrayList<LinkModel> getSelectedLinks() {
        ArrayList<LinkModel> selected = new ArrayList<>();
        for (CheckBox cb : selectedMap.keySet()) {
            if (cb.isChecked()) selected.add(selectedMap.get(cb));
        }
        return selected;
    }

    private LinkModel getSelectedOne() {
        LinkModel selected = null;
        for (CheckBox cb : selectedMap.keySet()) {
            if (cb.isChecked()) {
                if (selected != null) return null; // Lebih dari satu
                selected = selectedMap.get(cb);
            }
        }
        return selected;
    }

    /*public NetworkChangeReceiver getNetworkChangeReceiver() {
        return networkChangeReceiver;
    }

     */
}
