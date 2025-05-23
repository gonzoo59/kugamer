package com.baidu.kwgames.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import com.baidu.kwgames.AppInstance;
import com.blankj.utilcode.util.FileIOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import org.opencv.videoio.Videoio;
/* loaded from: classes.dex */
public class NEAT {
    public static int make_sure_xy_in_range(int i, int i2, int i3) {
        return i + i2 > i3 ? i3 - i2 : i;
    }

    public static boolean write_file(Context context, String str, byte[] bArr) {
        File externalFilesDir = context.getExternalFilesDir(null);
        return FileIOUtils.writeFileFromBytesByStream(externalFilesDir + str, bArr);
    }

    public static boolean write_file(Context context, String str, String str2) {
        File externalFilesDir = context.getExternalFilesDir(null);
        return FileIOUtils.writeFileFromString(externalFilesDir + str, str2);
    }

    public static String get_string(int i) {
        return AppInstance.s_context.getString(i);
    }

    public static String s(int i) {
        return AppInstance.s_context.getString(i);
    }

    public static String s(Context context, int i) {
        return context.getString(i);
    }

    public static String int_to_string(int i) {
        return "" + i;
    }

    public static int string_to_int(String str) {
        return Integer.parseInt(str);
    }

    public static int get_color(int i) {
        return AppInstance.s_context.getResources().getColor(i);
    }

    public static String get_language() {
        return AppInstance.s_context.getResources().getConfiguration().locale.getLanguage();
    }

    public static boolean is_chinese() {
        return get_language().equals("zh");
    }

    public static boolean is_vitnam() {
        return get_language().equals("vi");
    }

    public static boolean is_turkey() {
        return get_language().equals("tr");
    }

    public static boolean is_pubg_ch(String str) {
        return str != null && str.contains("pubgmhd");
    }

    public static Boolean is_oppo() {
        return Boolean.valueOf(Build.BRAND.toUpperCase().contains("OPPO"));
    }

    public static Boolean is_vivo() {
        return Boolean.valueOf(Build.BRAND.toUpperCase().contains("VIVO"));
    }

    public static Boolean is_huawei() {
        String upperCase = Build.BRAND.toUpperCase();
        return Boolean.valueOf(upperCase.contains("HUAWEI") || upperCase.contains("HONOR"));
    }

    public static Boolean is_oneplus() {
        return Boolean.valueOf(Build.BRAND.toUpperCase().contains("ONEPLUS"));
    }

    public static Boolean is_xiaomi() {
        String upperCase = Build.BRAND.toUpperCase();
        return Boolean.valueOf(upperCase.contains("XIAOMI") || upperCase.contains("REDMI"));
    }

    public static Boolean is_nubia() {
        return Boolean.valueOf(Build.BRAND.toUpperCase().contains("NUBIA"));
    }

    public static Boolean is_meizu() {
        return Boolean.valueOf(Build.BRAND.toUpperCase().contains("MEIZU"));
    }

    public static Boolean is_black_shark() {
        return Boolean.valueOf(Build.BRAND.toUpperCase().contains("BLACKSHARK"));
    }

    public static int make_sure_x_visible(int i) {
        return make_sure_xy_in_range(i, Util.dip2px(AppInstance.s_context, 20.0f), AppInstance.s_nScreenW);
    }

    public static int make_sure_y_visible(int i) {
        return make_sure_xy_in_range(i, Util.dip2px(AppInstance.s_context, 20.0f), AppInstance.s_nScreenH);
    }

    public static double get_screen_inch(Context context) {
        Point point = new Point();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealSize(point);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i = displayMetrics.densityDpi;
        return Math.sqrt(Math.pow(point.x / displayMetrics.xdpi, 2.0d) + Math.pow(point.y / displayMetrics.ydpi, 2.0d));
    }

    public static boolean is_pad() {
        return get_screen_inch(AppInstance.s_context) > 8.0d;
    }

    public static void toast(int i) {
        Toast.makeText(AppInstance.s_context, AppInstance.s_context.getString(i), 1).show();
    }

    public static void toast(Context context, int i) {
        Toast.makeText(context, AppInstance.s_context.getString(i), 1).show();
    }

    public static void toast_short(int i) {
        Toast.makeText(AppInstance.s_context, AppInstance.s_context.getString(i), 0).show();
    }

    public static boolean is_gps_open() {
        return ((LocationManager) AppInstance.s_context.getSystemService("location")).isProviderEnabled("gps");
    }

    public static boolean is_bluetooth_open() {
        return BluetoothAdapter.getDefaultAdapter().isEnabled();
    }

    public static void bluetooth_onoff(boolean z) {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (z) {
            if (defaultAdapter.isEnabled()) {
                return;
            }
            defaultAdapter.enable();
        } else if (defaultAdapter.isEnabled()) {
            defaultAdapter.disable();
        }
    }

    public static void open_bluetooth_setting(Context context) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.settings.BLUETOOTH_SETTINGS");
            intent.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String byte_to_hex_string(byte[] bArr, int i) {
        StringBuilder sb = new StringBuilder();
        int min = Math.min(i, bArr.length);
        for (int i2 = 0; i2 < min; i2++) {
            sb.append(String.format("%02X", Integer.valueOf(bArr[i2] & 255)));
        }
        return sb.toString();
    }

    public static String int_array2_to_decimal_string(int[][] iArr, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append(String.format("%d,", Integer.valueOf(iArr[i][i3])));
        }
        return sb.toString();
    }

    public static byte[] hex_string_to_bytes(String str) {
        if (str == null || str.length() <= 2) {
            return null;
        }
        int length = str.length() >> 1;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            try {
                bArr[i] = (byte) (Integer.parseInt(str.substring(i2, i2 + 2), 16) & 255);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bArr;
    }

    public static void copy_file(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        File file = new File(str);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            return;
        }
        File file2 = new File(str2);
        if (!file2.getParentFile().exists()) {
            file2.getParentFile().mkdirs();
        }
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            byte[] bArr = new byte[5120];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copy_stream(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[10240];
        while (true) {
            int read = inputStream.read(bArr);
            if (read >= 0) {
                outputStream.write(bArr, 0, read);
            } else {
                outputStream.flush();
                inputStream.close();
                outputStream.close();
                return;
            }
        }
    }

    public static String get_path_name(String str) {
        int lastIndexOf = str.trim().lastIndexOf("/");
        if (-1 == lastIndexOf) {
            return null;
        }
        return str.trim().substring(lastIndexOf + 1);
    }

    public static boolean get_float_permission(Context context) {
        if (Build.VERSION.SDK_INT < 19) {
            return true;
        }
        if (Build.VERSION.SDK_INT < 23) {
            try {
                Class<?> cls = Class.forName("android.content.Context");
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (obj instanceof String) {
                    Object invoke = cls.getMethod("getSystemService", String.class).invoke(context, (String) obj);
                    Class<?> cls2 = Class.forName("android.app.AppOpsManager");
                    Field declaredField2 = cls2.getDeclaredField("MODE_ALLOWED");
                    declaredField2.setAccessible(true);
                    return ((Integer) cls2.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class).invoke(invoke, 24, Integer.valueOf(Binder.getCallingUid()), context.getPackageName())).intValue() == declaredField2.getInt(cls2);
                }
                return false;
            } catch (Exception unused) {
                return false;
            }
        }
        return Settings.canDrawOverlays(context);
    }

    public static long elapse(long j) {
        return System.currentTimeMillis() - j;
    }

    public static void open_website_url(Context context, String str) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            intent.setData(Uri.parse(str));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean is_file_exist(String str) {
        try {
            return Boolean.valueOf(new File(str).exists());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void d(String str) {
        Log.d("dbg", str);
    }
}
