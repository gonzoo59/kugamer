package com.baidu.kwgames.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.baidu.kwgames.AppInstance;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import java.io.InputStream;
/* loaded from: classes.dex */
public class Util {
    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void hideSystemUI(View view) {
        if (Build.VERSION.SDK_INT < 19 || view == null) {
            return;
        }
        view.setSystemUiVisibility(5894);
    }

    public static void saveItem(String str, String str2) {
        SharedPreferences.Editor edit = AppInstance.get().getSharedPreferences(str, 0).edit();
        edit.putString("value", str2);
        edit.commit();
    }

    public static String getItem(String str) {
        return AppInstance.get().getSharedPreferences(str, 0).getString("value", "");
    }

    public static String getItem63() {
        return AppInstance.get().getSharedPreferences("configData63", 0).getString("value", "");
    }

    public static void removeItem63() {
        SharedPreferences.Editor edit = AppInstance.get().getSharedPreferences("configData63", 0).edit();
        edit.remove("value");
        edit.commit();
    }

    public static byte[] read_assets(String str) {
        byte[] bArr = null;
        try {
            InputStream open = AppInstance.get().getResources().getAssets().open(str);
            bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return bArr;
        } catch (Exception e) {
            e.printStackTrace();
            return bArr;
        }
    }

    public static void send_handler_msg(Handler handler, int i, int i2) {
        Message obtain = Message.obtain();
        obtain.arg1 = i;
        obtain.arg2 = i2;
        handler.sendMessage(obtain);
    }

    public static void save_ini_int_array(SPUtils sPUtils, String str, int[] iArr) {
        sPUtils.put(str, new Gson().toJson(iArr));
    }

    public static int[] read_ini_int_array(SPUtils sPUtils, String str) {
        String string = sPUtils.getString(str, "");
        if (string.isEmpty()) {
            return null;
        }
        return (int[]) new Gson().fromJson(string, (Class<Object>) int[].class);
    }
}
