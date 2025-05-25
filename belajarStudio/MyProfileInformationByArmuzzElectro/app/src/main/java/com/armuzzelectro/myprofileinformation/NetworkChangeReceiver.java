package com.armuzzelectro.myprofileinformation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;

/**
 * Modern pengganti BroadcastReceiver untuk deteksi jaringan secara real-time.
 */
public class NetworkChangeReceiver {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        NetworkChangeReceiver.context = context;
    }

    public interface OnNetworkAvailableListener {
        void onNetworkAvailable();
    }

    private static OnNetworkAvailableListener listener;

    public static void setOnNetworkAvailableListener(OnNetworkAvailableListener l) {
        listener = l;
    }

    public static void register(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest request = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        cm.registerNetworkCallback(request, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                if (listener != null) {
                    listener.onNetworkAvailable();
                }
            }
        });
    }

    public static void unregister(Context context) {
        // kosong saat ini, bisa diisi untuk unregister jika perlu
        NetworkChangeReceiver.context = context;
    }
}


/*package com.armuzzelectro.myprofileinformation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

 */

/*
  Receiver untuk mendeteksi perubahan jaringan dan memicu listener ketika online.
 */
/*public class NetworkChangeReceiver extends BroadcastReceiver {

    // Interface untuk diberi tahu ketika device kembali online
    public interface OnNetworkAvailableListener {
        void onNetworkAvailable();
    }

    // Static listener agar bisa dipakai di MainActivity
    private static OnNetworkAvailableListener listener;

 */

    /*
      Digunakan oleh MainActivity untuk mendaftarkan listener.
     */
    /*public static void setOnNetworkAvailableListener(OnNetworkAvailableListener l) {
        listener = l;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        // Jika sudah terkoneksi dan listener sudah di-set, panggil
        if (info != null && info.isConnected() && listener != null) {
            listener.onNetworkAvailable();
        }
    }
}

     */
