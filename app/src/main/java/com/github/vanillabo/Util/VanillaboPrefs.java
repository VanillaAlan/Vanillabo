package com.github.vanillabo.Util;

import com.github.vanillabo.model.AccessToken;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by alan on 16/4/9.
 */
public class VanillaboPrefs {

    private static final String PREF_NAME = "vanillabo";

    private static final String KEY_ACCESS_TOKEN = "access_token";

    private static final String KEY_REMIND_IN = "remind_in";

    private static final String KEY_EXPIRES_IN = "expires_in";

    private static final String KEY_UID = "uid";

    private static volatile VanillaboPrefs singleton;

    private final SharedPreferences mSharedPreferences;

    public static VanillaboPrefs getInstance(Context context) {
        if (singleton == null) {
            synchronized (VanillaboPrefs.class) {
                singleton = new VanillaboPrefs(context);
            }
        }
        return singleton;
    }

    public VanillaboPrefs(Context context) {
        mSharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setAccessToken(AccessToken accessToken) {
        saveString(KEY_ACCESS_TOKEN, accessToken.access_token);
        saveInt(KEY_EXPIRES_IN, accessToken.expires_in);
        saveInt(KEY_REMIND_IN, accessToken.remind_in);
        saveString(KEY_UID, accessToken.uid);
    }

    public AccessToken getAccessToken() {
        return new AccessToken(
                getString(KEY_ACCESS_TOKEN, null),
                getInt(KEY_REMIND_IN, 0),
                getInt(KEY_EXPIRES_IN, 0),
                getString(KEY_UID, null));
    }

    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

}
