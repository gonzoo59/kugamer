package com.jieli.jl_bt_ota.util;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Set;
/* loaded from: classes2.dex */
public class PreferencesHelper {
    private static String PREFERENCES_NAME = "ji_bluetooth_ota_preferences";

    public void setPreferencesName(String str) {
        PREFERENCES_NAME = str;
    }

    public static String getPreferencesName() {
        return PREFERENCES_NAME;
    }

    public static void putIntValue(Context context, String str, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES_NAME, 0).edit();
        edit.putInt(str, i);
        edit.apply();
    }

    public static void putLongValue(Context context, String str, long j) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES_NAME, 0).edit();
        edit.putLong(str, j);
        edit.apply();
    }

    public static void putStringValue(Context context, String str, String str2) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES_NAME, 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static void putBooleanValue(Context context, String str, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES_NAME, 0).edit();
        edit.putBoolean(str, z);
        edit.apply();
    }

    public static void putStringSetValue(Context context, String str, Set<String> set) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES_NAME, 0).edit();
        edit.putStringSet(str, set);
        edit.apply();
    }

    public static void remove(Context context, String str) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES_NAME, 0).edit();
        edit.remove(str);
        edit.apply();
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, 0);
    }
}
