package com.baidu.kwgames.util;

import com.baidu.kwgames.Units;
/* loaded from: classes.dex */
public class AIConvert {
    private static final String[] g_arrMate30Pro = {"LIO-AL00", "LIO-L29", "LIO-TL00", "LIO-AN00", "LIO-AN00P", "LIO-AN00M", "LIO-N29"};
    private static final String[] g_arrMate40Pro = {"NOP-AN00", "NOH-AN00", "NOH-AN01", "NOH-AL00", "NOH-AL10", "NOH-NX9", "NOH-AN50"};
    public static float s_fRatioH = 1.0f;
    public static float s_fRatioW = 1.0f;
    public static int s_nOffsetX;
    public static int s_nOffsetY;

    public static void init() {
        String upperCase = Units.getSystemModel().toUpperCase();
        for (String str : g_arrMate30Pro) {
            if (upperCase.equals(str)) {
                s_nOffsetX = 49;
                s_nOffsetY = 23;
                s_fRatioW = (2351 - 49) / 2400.0f;
                s_fRatioH = (1105 - 23) / 1128.0f;
                return;
            }
        }
        for (String str2 : g_arrMate40Pro) {
            if (upperCase.equals(str2)) {
                s_nOffsetX = 58;
                s_nOffsetY = 27;
                s_fRatioW = (2714 - 58) / 2772.0f;
                s_fRatioH = (1261 - 27) / 1288.0f;
                return;
            }
        }
    }

    public static int x(int i) {
        return (int) (s_nOffsetX + (i * s_fRatioW) + 0.5f);
    }

    public static int y(int i) {
        return (int) (s_nOffsetY + (i * s_fRatioH) + 0.5f);
    }

    public static int width(int i) {
        return (int) ((i * s_fRatioW) + 0.5f);
    }

    public static int height(int i) {
        return (int) ((i * s_fRatioH) + 0.5f);
    }

    public static boolean is_huawei_whole_screen(String str) {
        for (String str2 : g_arrMate30Pro) {
            if (str2.equals(str)) {
                return true;
            }
        }
        for (String str3 : g_arrMate40Pro) {
            if (str3.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
