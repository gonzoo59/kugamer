package com.jieli.component.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import com.blankj.utilcode.constant.CacheConstants;
import com.google.android.material.timepicker.TimeModel;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;
/* loaded from: classes2.dex */
public class ValueUtil {
    private static final String mHexStr = "0123456789ABCDEF";
    private static final char[] mChars = mHexStr.toCharArray();
    private static char[] chnNum = {38646, 19968, 20108, 19977, 22235, 20116, 20845, 19971, 20843, 20061};
    private static HashMap<Character, Integer> table = new HashMap<>();

    public static int byteToInt(byte b) {
        return b & 255;
    }

    public static int bytesToInt(byte b, byte b2) {
        return ((b & 255) << 8) + (b2 & 255);
    }

    public static byte[] int2byte2(int i) {
        return new byte[]{(byte) ((i >> 8) & 255), (byte) (i & 255)};
    }

    public static byte intToByte(int i) {
        return (byte) i;
    }

    public static byte[] intToBytes(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)};
    }

    public static byte[] shortToBytes(short s) {
        return new byte[]{(byte) (s & 255), (byte) ((s >> 8) & 255)};
    }

    public static int dp2px(Context context, int i) {
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        return (int) TypedValue.applyDimension(1, i, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static String formatTime(int i) {
        if (i <= 0) {
            return "00:00";
        }
        StringBuilder sb = new StringBuilder();
        int i2 = i / 1000;
        int i3 = i2 / CacheConstants.HOUR;
        if (i3 > 0) {
            sb.append(String.format(Locale.CHINA, TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i3)));
            sb.append(":");
        }
        if (i2 / 60 > 0) {
            sb.append(String.format(Locale.CHINA, TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf((i2 % CacheConstants.HOUR) / 60)));
            sb.append(":");
        } else {
            sb.append("00:");
        }
        sb.append(String.format(Locale.CHINA, TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i2 % 60)));
        return sb.toString();
    }

    public static boolean isMobileNum(String str) {
        return Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(14[0-9]))\\d{8}$").matcher(str).matches();
    }

    static {
        int i = 0;
        while (true) {
            char[] cArr = chnNum;
            if (i < cArr.length) {
                table.put(Character.valueOf(cArr[i]), Integer.valueOf(i));
                i++;
            } else {
                table.put((char) 21313, 10);
                table.put((char) 30334, 100);
                table.put((char) 21315, 1000);
                return;
            }
        }
    }

    public static boolean checkHexStr(String str) {
        String upperCase = str.trim().replace(" ", "").toUpperCase(Locale.US);
        int length = upperCase.length();
        if (length <= 1 || length % 2 != 0) {
            return false;
        }
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            if (!mHexStr.contains(upperCase.substring(i, i2))) {
                return false;
            }
            i = i2;
        }
        return true;
    }

    public static String str2HexStr(String str) {
        byte[] bArr;
        StringBuilder sb = new StringBuilder();
        try {
            bArr = str.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            bArr = null;
        }
        if (bArr == null) {
            return "";
        }
        for (byte b : bArr) {
            char[] cArr = mChars;
            sb.append(cArr[(b & 255) >> 4]);
            sb.append(cArr[b & 15]);
        }
        return sb.toString().trim();
    }

    public static String hexStr2Str(String str) {
        String upperCase = str.trim().replace(" ", "").toUpperCase(Locale.US);
        char[] charArray = upperCase.toCharArray();
        int length = upperCase.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) ((mHexStr.indexOf(charArray[i2 + 1]) | (mHexStr.indexOf(charArray[i2]) << 4)) & 255);
        }
        try {
            return new String(bArr, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String byte2HexStr(byte[] bArr, int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            char[] cArr = mChars;
            sb.append(cArr[(bArr[i2] & 255) >> 4]);
            sb.append(cArr[bArr[i2] & 15]);
        }
        return sb.toString().trim().toUpperCase(Locale.US);
    }

    public static String byte2HexStr(byte[] bArr) {
        return bArr == null ? "" : byte2HexStr(bArr, bArr.length);
    }

    public static String int2HexStr(int[] iArr, int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            char[] cArr = mChars;
            sb.append(cArr[(((byte) iArr[i2]) & 255) >> 4]);
            sb.append(cArr[((byte) iArr[i2]) & 15]);
        }
        return sb.toString().trim().toUpperCase(Locale.US);
    }

    public static String bytes2String(byte[] bArr, int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append((int) bArr[i2]);
        }
        return sb.toString();
    }

    public static byte[] hexStr2Bytes(String str) {
        String upperCase = str.trim().replace(" ", "").toUpperCase(Locale.US);
        int length = upperCase.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            int i3 = i2 + 1;
            bArr[i] = (byte) (Integer.decode("0x" + upperCase.substring(i2, i3) + upperCase.substring(i3, i3 + 1)).intValue() & 255);
        }
        return bArr;
    }

    public static String strToUnicode(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            String hexString = Integer.toHexString(charAt);
            if (charAt > 128) {
                sb.append("\\u");
            } else {
                sb.append("\\u00");
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static String unicodeToString(String str) {
        int length = str.length() / 6;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < length) {
            int i2 = i * 6;
            i++;
            String substring = str.substring(i2, i * 6);
            sb.append(new String(Character.toChars(Integer.valueOf(substring.substring(4), 16).intValue() | (Integer.valueOf(substring.substring(2, 4), 16).intValue() << 8))));
        }
        return sb.toString();
    }

    public static String intToHexString(int i) {
        return String.format("%02x", Integer.valueOf(i));
    }

    public static String byteToHexString(byte b) {
        return intToHexString(b & 255);
    }

    public static int cnStrToNumber(String str) {
        String replace = str.replace("零", "");
        if (table.get(Character.valueOf(replace.charAt(0))).intValue() >= 10) {
            replace = "一" + replace;
        }
        char[] charArray = replace.toCharArray();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < charArray.length; i3++) {
            int intValue = table.get(Character.valueOf(charArray[i3])).intValue();
            if (intValue == 10 || intValue == 100 || intValue == 1000) {
                intValue *= i2;
            } else if (i3 != charArray.length - 1) {
                i2 = intValue;
            }
            i += intValue;
        }
        return i;
    }

    public static long stringNumToNum(String str) {
        if (TextUtils.isEmpty(str) || !TextUtils.isDigitsOnly(str)) {
            return 0L;
        }
        return Long.valueOf(str).longValue();
    }

    public static int[] bytes2ints(byte[] bArr, int i) {
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = bArr[i2];
        }
        return iArr;
    }

    public static byte[] ints2bytes(int[] iArr, int i) {
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) iArr[i2];
        }
        return bArr;
    }

    public static int[] floats2ints(float[] fArr) {
        int[] iArr = new int[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            iArr[i] = (int) fArr[i];
        }
        return iArr;
    }
}
