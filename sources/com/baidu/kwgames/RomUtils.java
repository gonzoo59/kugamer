package com.baidu.kwgames;

import android.os.Build;
import android.text.TextUtils;
import com.blankj.utilcode.util.DeviceUtils;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/* loaded from: classes.dex */
public class RomUtils {
    static final String ROM_COOLPAD = "COOLPAD";
    static final String ROM_EMUI = "EMUI";
    static final String ROM_FLYME = "FLYME";
    static final String ROM_LENOVO = "LENOVO";
    static final String ROM_LETV = "LETV";
    static final String ROM_MIUI = "MIUI";
    static final String ROM_NUBIA = "NUBIA";
    static final String ROM_OPPO = "OPPO";
    static final String ROM_QIKU = "QIKU";
    static final String ROM_SMARTISAN = "SMARTISAN";
    static final String ROM_UNKNOWN = "UNKNOWN";
    static final String ROM_VIVO = "VIVO";
    static final String ROM_ZTE = "ZTE";
    private static final String SYSTEM_VERSION_EMUI = "ro.build.version.emui";
    private static final String SYSTEM_VERSION_FLYME = "ro.build.display.id";
    private static final String SYSTEM_VERSION_LENOVO = "ro.lenovo.lvp.version";
    private static final String SYSTEM_VERSION_LETV = "ro.letv.eui";
    private static final String SYSTEM_VERSION_MIUI = "ro.miui.ui.version.name";
    private static final String SYSTEM_VERSION_OPPO = "ro.build.version.opporom";
    private static final String SYSTEM_VERSION_SMARTISAN = "ro.smartisan.version";
    private static final String SYSTEM_VERSION_VIVO = "ro.vivo.os.version";
    private static final String TAG = "RomUtils";

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface RomName {
    }

    private static String getSystemProperty(String str) {
        return SystemProperties.get(str, null);
    }

    public static String getRomName() {
        return isMiuiRom() ? ROM_MIUI : isHuaweiRom() ? ROM_EMUI : isVivoRom() ? ROM_VIVO : isOppoRom() ? ROM_OPPO : isMeizuRom() ? ROM_FLYME : isSmartisanRom() ? ROM_SMARTISAN : is360Rom() ? ROM_QIKU : isLetvRom() ? ROM_LETV : isLenovoRom() ? ROM_LENOVO : isZTERom() ? ROM_ZTE : isCoolPadRom() ? ROM_COOLPAD : ROM_UNKNOWN;
    }

    public static String getDeviceManufacture() {
        return isMiuiRom() ? "小米" : isHuaweiRom() ? "华为" : isVivoRom() ? ROM_VIVO : isOppoRom() ? ROM_OPPO : isMeizuRom() ? "魅族" : isSmartisanRom() ? "锤子" : is360Rom() ? "奇酷" : isLetvRom() ? "乐视" : isLenovoRom() ? "联想" : isZTERom() ? "中兴" : isCoolPadRom() ? "酷派" : DeviceUtils.getManufacturer();
    }

    public static boolean isMiuiRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_MIUI));
    }

    public static boolean isHuaweiRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_EMUI));
    }

    public static boolean isVivoRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_VIVO));
    }

    public static boolean isOppoRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_OPPO));
    }

    public static boolean isMeizuRom() {
        String systemProperty = getSystemProperty(SYSTEM_VERSION_FLYME);
        return !TextUtils.isEmpty(systemProperty) && systemProperty.toUpperCase().contains(ROM_FLYME);
    }

    public static boolean isSmartisanRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_SMARTISAN));
    }

    public static boolean is360Rom() {
        String str = Build.MANUFACTURER;
        return !TextUtils.isEmpty(str) && str.toUpperCase().contains(ROM_QIKU);
    }

    public static boolean isLetvRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_LETV));
    }

    public static boolean isLenovoRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_LENOVO));
    }

    public static boolean isCoolPadRom() {
        String str = Build.MODEL;
        String str2 = Build.FINGERPRINT;
        return (!TextUtils.isEmpty(str) && str.toLowerCase().contains(ROM_COOLPAD)) || (!TextUtils.isEmpty(str2) && str2.toLowerCase().contains(ROM_COOLPAD));
    }

    public static boolean isZTERom() {
        String str = Build.MANUFACTURER;
        String str2 = Build.FINGERPRINT;
        return (!TextUtils.isEmpty(str) && (str2.toLowerCase().contains(ROM_NUBIA) || str2.toLowerCase().contains(ROM_ZTE))) || (!TextUtils.isEmpty(str2) && (str2.toLowerCase().contains(ROM_NUBIA) || str2.toLowerCase().contains(ROM_ZTE)));
    }

    public static boolean isDomesticSpecialRom() {
        return isMiuiRom() || isHuaweiRom() || isMeizuRom() || is360Rom() || isOppoRom() || isVivoRom() || isLetvRom() || isZTERom() || isLenovoRom() || isCoolPadRom();
    }

    public static boolean isSmartisanR1() {
        return Build.MODEL.contains("DE106");
    }

    public static boolean isVivoStupidNotch() {
        return isVivoX21() || isVivoX21S() || isVivoX23() || isVivoZ1() || isVivoZ3() || isVivoY81s() || isVivoY83() || isVivoY85() || isVivoY93() || isVivoY97();
    }

    public static boolean isVivoX21() {
        return Build.MODEL.contains("vivo X21");
    }

    public static boolean isVivoX21S() {
        return Build.MODEL.contains("V1814");
    }

    public static boolean isVivoX23() {
        return Build.MODEL.contains("V1809") || Build.MODEL.contains("V1816");
    }

    public static boolean isVivoZ1() {
        return Build.MODEL.contains("V1730");
    }

    public static boolean isVivoZ3() {
        return Build.MODEL.contains("V1813BA");
    }

    public static boolean isVivoY81s() {
        return Build.MODEL.contains("V1732");
    }

    public static boolean isVivoY83() {
        return Build.MODEL.contains("Y83");
    }

    public static boolean isVivoY85() {
        return Build.MODEL.contains("vivo Y85");
    }

    public static boolean isVivoY93() {
        return Build.MODEL.contains("V1818");
    }

    public static boolean isVivoY97() {
        return Build.MODEL.contains("V1813A") || Build.MODEL.contains("V1813T");
    }

    public static boolean isHonorV10() {
        return Build.MODEL.contains("BKL-AL00");
    }

    public static boolean isHonor10() {
        return Build.MODEL.contains("COL-AL10");
    }

    public static boolean isMiPad4() {
        return TextUtils.equals(Build.MODEL, "MI PAD 4");
    }
}
