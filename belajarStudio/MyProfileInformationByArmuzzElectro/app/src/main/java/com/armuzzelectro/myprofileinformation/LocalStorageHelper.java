package com.armuzzelectro.myprofileinformation;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class LocalStorageHelper {
    private static final String PREF_NAME = "link_data_cache";
    private static final String KEY_LINK_LIST = "link_list";

    public static void saveLinks(Context context, ArrayList<LinkModel> links) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = new Gson().toJson(links);
        prefs.edit().putString(KEY_LINK_LIST, json).apply();
    }

    public static ArrayList<LinkModel> loadLinks(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_LINK_LIST, null);
        if (json == null) return new ArrayList<>();
        return new Gson().fromJson(json, new TypeToken<ArrayList<LinkModel>>() {}.getType());
    }
}
