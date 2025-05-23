package com.baidu.kwgames;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.blankj.utilcode.util.ScreenUtils;
/* loaded from: classes.dex */
public class Units {
    private static final int KEY_MAP_MAX = 8;
    private static final int TOUCH_RES_X = 1200;
    private static final int TOUCH_RES_Y = 2200;
    private static double DevXPerUiY = 1200.0d / ScreenUtils.getScreenHeight();
    private static double DevYPerUiX = 2200.0d / ScreenUtils.getScreenWidth();
    private static int SCREEN_WIDTH = ScreenUtils.getScreenWidth();
    private static int SCREEN_HEIGHT = ScreenUtils.getScreenHeight();
    private static final byte[][] g_arrKeyMap = new byte[8];

    public static int BYTE2INT(byte b, byte b2) {
        return (b & 255) | ((b2 << 8) & 65280);
    }

    public static int BYTE2INT(byte b, byte b2, byte b3, byte b4) {
        return (b & 255) | ((b2 << 8) & 65280) | ((b3 << 16) & 16711680) | ((b4 << 24) & (-16777216));
    }

    public static byte HIBYTE(int i) {
        return (byte) ((i >> 8) & 255);
    }

    public static byte LOBYTE(int i) {
        return (byte) (i & 255);
    }

    public static int Short2Int(byte b, byte b2) {
        int i = (b & 255) | ((b2 << 8) & 65280);
        return 32768 == (i & 32768) ? i | (-65536) : i;
    }

    public static byte clear_bit(byte b, int i) {
        return (byte) (b & (~(1 << i)));
    }

    public static boolean is_bit_on(byte b, int i) {
        return (b & (1 << i)) != 0;
    }

    public static int make_in_range(int i, int i2, int i3) {
        if (i < i2) {
            i = i2;
        }
        return i > i3 ? i3 : i;
    }

    public static byte set_bit(byte b, int i) {
        return (byte) (b | (1 << i));
    }

    public static void init() {
        DevXPerUiY = 1200.0d / AppInstance.s_nScreenH;
        DevYPerUiX = 2200.0d / AppInstance.s_nScreenW;
        SCREEN_WIDTH = AppInstance.s_nScreenW;
        SCREEN_HEIGHT = AppInstance.s_nScreenH;
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] get_key_map(int i) {
        if (i >= 8) {
            return null;
        }
        return g_arrKeyMap[i];
    }

    public static void set_key_map(int i, byte[] bArr) {
        if (i < 0 || i >= 8) {
            return;
        }
        g_arrKeyMap[i] = bArr;
    }

    public static String getSystemModel() {
        return Build.MODEL;
    }

    public static Boolean is_oppo_vivo() {
        String upperCase = Build.BRAND.toUpperCase();
        return Boolean.valueOf(upperCase.equals("OPPO") || upperCase.equals("VIVO"));
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    public static Drawable getAppIcon(Context context, String str) {
        if (str != null) {
            try {
                PackageManager packageManager = context.getPackageManager();
                return packageManager.getApplicationInfo(str, 0).loadIcon(packageManager);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getAppName(Context context, String str) {
        if (str != null) {
            try {
                PackageManager packageManager = context.getPackageManager();
                return packageManager.getApplicationInfo(str, 0).loadLabel(packageManager).toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String bytes2HexStr(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(String.format("0x%02X ", Byte.valueOf(bArr[i])));
        }
        sb.append("]");
        return sb.toString();
    }

    public static String bytes2HexStr(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append(String.format("0x%02X ", Byte.valueOf(bArr[i3 + i])));
        }
        sb.append("]");
        return sb.toString();
    }

    public static int checkArgumentPositive(int i, String str) {
        if (i > 0) {
            return i;
        }
        throw new IllegalArgumentException(str);
    }

    public static boolean isHardWareVendorMediaTek() {
        return Build.HARDWARE.matches("mt[0-9]*");
    }

    public static int cumBytes(byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        int i4 = 0;
        while (i < i3) {
            i4 += bArr[i] & 255;
            i++;
        }
        return i4;
    }

    public static int ui_y_2_dev_x(int i) {
        return (int) ((1200.0d - (DevXPerUiY * i)) + 0.5d);
    }

    public static int ui_x_2_dev_y(int i) {
        return (int) ((DevYPerUiX * i) + 0.5d);
    }

    public static int dev_x_2_ui_y(int i) {
        return Math.min(Math.max(0, (int) (((1200 - i) / DevXPerUiY) + 0.5d)), ScreenUtils.getScreenHeight());
    }

    public static int dev_y_2_ui_x(int i) {
        return Math.min(Math.max(0, (int) ((i / DevYPerUiX) + 0.5d)), ScreenUtils.getScreenWidth());
    }

    public static int mtk_dev_x_2_ui_y(int i) {
        return Math.max(0, SCREEN_HEIGHT - ((int) ((i / DevXPerUiY) + 0.5d)));
    }

    public static int mtk_dev_y_2_ui_x(int i) {
        return Math.min(Math.max(0, (int) ((i / DevYPerUiX) + 0.5d)), SCREEN_WIDTH);
    }

    public static void setLayoutX(View view, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        marginLayoutParams.setMargins(i, marginLayoutParams.topMargin, marginLayoutParams.width + i, marginLayoutParams.bottomMargin);
        view.setLayoutParams(new LinearLayout.LayoutParams(marginLayoutParams));
    }

    public static void setLayoutY(View view, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        marginLayoutParams.setMargins(marginLayoutParams.leftMargin, i, marginLayoutParams.rightMargin, marginLayoutParams.height + i);
        view.setLayoutParams(new LinearLayout.LayoutParams(marginLayoutParams));
    }

    public static int string_2_int(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String int_2_string(int i) {
        return String.valueOf(i);
    }

    public static byte set_bit(byte b, int i, boolean z) {
        return z ? set_bit(b, i) : clear_bit(b, i);
    }
}
