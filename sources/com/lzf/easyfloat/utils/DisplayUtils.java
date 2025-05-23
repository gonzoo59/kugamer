package com.lzf.easyfloat.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.lzf.easyfloat.permission.rom.RomUtils;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: DisplayUtils.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0016\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\nJ\u0016\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\nJ\u000e\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\nJ\u000e\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020!R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/lzf/easyfloat/utils/DisplayUtils;", "", "()V", "TAG", "", "dp2px", "", "context", "Landroid/content/Context;", "dpVal", "", "getNavigationBarCurrentHeight", "getNavigationBarHeight", "getScreenHeight", "getScreenSize", "Landroid/graphics/Point;", "getScreenWidth", "getStatusBarHeight", "hasNavigationBar", "", "isHasNavigationBar", "isHuaWeiHideNav", "isMiuiFullScreen", "isVivoFullScreen", "px2dp", "pxVal", "px2sp", "pxValue", "rejectedNavHeight", "sp2px", "spValue", "statusBarHeight", "view", "Landroid/view/View;", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class DisplayUtils {
    public static final DisplayUtils INSTANCE = new DisplayUtils();
    private static final String TAG = "DisplayUtils--->";

    private DisplayUtils() {
    }

    public final int px2dp(Context context, float f) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Resources resources = context.getResources();
        Intrinsics.checkExpressionValueIsNotNull(resources, "context.resources");
        return (int) ((f / resources.getDisplayMetrics().density) + 0.5f);
    }

    public final int dp2px(Context context, float f) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Resources resources = context.getResources();
        Intrinsics.checkExpressionValueIsNotNull(resources, "context.resources");
        return (int) ((f * resources.getDisplayMetrics().density) + 0.5f);
    }

    public final int px2sp(Context context, float f) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Resources resources = context.getResources();
        Intrinsics.checkExpressionValueIsNotNull(resources, "context.resources");
        return (int) ((f / resources.getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public final int sp2px(Context context, float f) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Resources resources = context.getResources();
        Intrinsics.checkExpressionValueIsNotNull(resources, "context.resources");
        return (int) ((f * resources.getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public final int getScreenWidth(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Object systemService = context.getSystemService("window");
        if (systemService == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.view.WindowManager");
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) systemService).getDefaultDisplay().getRealMetrics(displayMetrics);
        Resources resources = context.getResources();
        Intrinsics.checkExpressionValueIsNotNull(resources, "context.resources");
        if (resources.getConfiguration().orientation == 1) {
            return displayMetrics.widthPixels;
        }
        return displayMetrics.widthPixels - getNavigationBarCurrentHeight(context);
    }

    public final int getScreenHeight(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        return getScreenSize(context).y;
    }

    public final Point getScreenSize(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Point point = new Point();
        Object systemService = context.getSystemService("window");
        if (systemService == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.view.WindowManager");
        }
        ((WindowManager) systemService).getDefaultDisplay().getRealSize(point);
        return point;
    }

    public final int getStatusBarHeight(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public final int statusBarHeight(View view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Context context = view.getContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "view.context");
        Context applicationContext = context.getApplicationContext();
        Intrinsics.checkExpressionValueIsNotNull(applicationContext, "view.context.applicationContext");
        return getStatusBarHeight(applicationContext);
    }

    public final int getNavigationBarHeight(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public final int getNavigationBarCurrentHeight(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (hasNavigationBar(context)) {
            return getNavigationBarHeight(context);
        }
        return 0;
    }

    public final boolean hasNavigationBar(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (getNavigationBarHeight(context) == 0) {
            return false;
        }
        if (RomUtils.INSTANCE.checkIsHuaweiRom() && isHuaWeiHideNav(context)) {
            return false;
        }
        if (RomUtils.INSTANCE.checkIsMiuiRom() && isMiuiFullScreen(context)) {
            return false;
        }
        if (RomUtils.INSTANCE.checkIsVivoRom() && isVivoFullScreen(context)) {
            return false;
        }
        return isHasNavigationBar(context);
    }

    public final int rejectedNavHeight(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Point screenSize = getScreenSize(context);
        return screenSize.x > screenSize.y ? screenSize.y : screenSize.y - getNavigationBarCurrentHeight(context);
    }

    private final boolean isHuaWeiHideNav(Context context) {
        int i;
        if (Build.VERSION.SDK_INT < 21) {
            i = Settings.System.getInt(context.getContentResolver(), "navigationbar_is_min", 0);
        } else {
            i = Settings.Global.getInt(context.getContentResolver(), "navigationbar_is_min", 0);
        }
        return i != 0;
    }

    private final boolean isMiuiFullScreen(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
    }

    private final boolean isVivoFullScreen(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "navigation_gesture_on", 0) != 0;
    }

    private final boolean isHasNavigationBar(Context context) {
        Object systemService = context.getSystemService("window");
        if (systemService == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.view.WindowManager");
        }
        Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealMetrics(displayMetrics);
        }
        int i = displayMetrics.heightPixels;
        int i2 = displayMetrics.widthPixels;
        DisplayMetrics displayMetrics2 = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics2);
        int i3 = displayMetrics2.heightPixels;
        int i4 = displayMetrics2.widthPixels;
        if (getNavigationBarHeight(context) + i3 > i) {
            return false;
        }
        return i2 - i4 > 0 || i - i3 > 0;
    }
}
