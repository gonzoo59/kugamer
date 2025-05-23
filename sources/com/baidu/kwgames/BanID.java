package com.baidu.kwgames;

import android.text.TextUtils;
import com.baidu.kwgames.net.HttpHelper;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import okhttp3.internal.cache.DiskLruCache;
/* loaded from: classes.dex */
public class BanID {
    public static final String E_BAN_DEVICE = "banDev";
    public static final String E_BAN_FILE = "banData";
    public static final String E_INI_BAN_VER = "banDatabaseVer";
    static Boolean m_boDevBanned = false;
    private static SPUtils m_ini = SPUtils.getInstance();
    static String m_strBanID;
    static String m_strBanPassword;

    public static void init(final int i, String str) {
        int i2 = m_ini.getInt(E_INI_BAN_VER, 0);
        m_strBanPassword = str;
        try {
            File file = new File(Constants.ADB_DIR);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (new File("/storage/emulated/0/SincoGamer/banData").exists() && i == i2) {
                m_strBanID = FileIOUtils.readFile2String("/storage/emulated/0/SincoGamer/banData").toUpperCase();
                return;
            }
            HttpHelper.request(Constants.URL_BAN_ID).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<String>() { // from class: com.baidu.kwgames.BanID.1
                @Override // io.reactivex.functions.Consumer
                public void accept(String str2) throws Exception {
                    if (TextUtils.isEmpty(str2)) {
                        return;
                    }
                    if (FileIOUtils.writeFileFromString("/storage/emulated/0/SincoGamer/banData", str2)) {
                        BanID.m_ini.put(BanID.E_INI_BAN_VER, i);
                    }
                    BanID.m_strBanID = str2.toUpperCase();
                }
            }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.BanID.2
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable th) throws Exception {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean is_device_baned(String str) {
        if (new File("/storage/emulated/0/SincoGamer/banDev").exists()) {
            return true;
        }
        if (TextUtils.isEmpty(m_strBanID) || -1 == m_strBanID.indexOf(str)) {
            return false;
        }
        FileIOUtils.writeFileFromString("/storage/emulated/0/SincoGamer/banDev", DiskLruCache.VERSION_1);
        return true;
    }

    public static boolean unlock(String str) {
        return str.equals(m_strBanPassword) && FileUtils.delete("/storage/emulated/0/SincoGamer/banDev");
    }
}
