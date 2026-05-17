package com.hnem06.practice.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ObjProduct2Json {

    private static final String PREF_NAME = "product_pref";
    private static final String KEY_PRODUCTS = "products";
    private static final Gson gson = new Gson();

    public static void save(Context context, ArrayList<Product> list) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = gson.toJson(list);
        prefs.edit().putString(KEY_PRODUCTS, json).apply();
    }

    public static ArrayList<Product> load(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_PRODUCTS, null);
        if (json == null) return null;

        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
