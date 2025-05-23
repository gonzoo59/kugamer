package com.baidu.kwgames;

import android.app.Application;
import android.content.Context;
import com.baidu.kwgames.USB.UsbDeviceBase;
/* loaded from: classes.dex */
public class AppInstance {
    private static Application app = null;
    public static SystemStatus g_sSysStatus = null;
    public static volatile boolean mBleConnected = false;
    public static boolean m_boAIWorking = false;
    public static boolean m_boFileAccess = false;
    public static boolean m_boIsChineseOS = false;
    public static String mac = "";
    public static boolean s_boLiveMode = false;
    public static volatile boolean s_boUSBConnected = false;
    public static int s_colorGreen = 65280;
    public static int s_colorRed = 16711680;
    public static Context s_context = null;
    public static int s_nAIKit = 10;
    public static int s_nCurKeyMap = -1;
    public static int s_nScreenH = 0;
    public static int s_nScreenW = 0;
    public static int s_nToupingDisplayX = 0;
    public static int s_nToupingDisplayY = 0;
    public static int s_nToupingPhoneX = 0;
    public static int s_nToupingPhoneY = 0;
    public static String s_strAppVer = null;
    public static String s_strBTName = "";
    public static String s_strKeyMapSaveAs;
    public static UsbDeviceBase s_usb;
    public static int settingPageCloseAmount;
    public static long settingPageCloseTime;
    public static long settingPageOpenTime;
    public static String s_strGamePackageName = new String("");
    public static byte[] s_arrUUID = new byte[16];

    public static void setApp(Application application) {
        app = application;
    }

    public static Application get() {
        return app;
    }

    public static int get_cur_AI_kit() {
        return s_nAIKit;
    }

    public static boolean is_hpjy_AI_kit() {
        int i = s_nAIKit;
        return i == 0 || i == 2;
    }

    public static boolean is_pubg_AI_kit() {
        int i = s_nAIKit;
        return i == 1 || i == 3 || i == 4;
    }

    public static boolean has_device_connect() {
        return mBleConnected || s_boUSBConnected;
    }
}
