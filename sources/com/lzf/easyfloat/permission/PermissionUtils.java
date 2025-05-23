package com.lzf.easyfloat.permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import com.lzf.easyfloat.interfaces.OnPermissionResult;
import com.lzf.easyfloat.permission.rom.HuaweiUtils;
import com.lzf.easyfloat.permission.rom.MeizuUtils;
import com.lzf.easyfloat.permission.rom.MiuiUtils;
import com.lzf.easyfloat.permission.rom.OppoUtils;
import com.lzf.easyfloat.permission.rom.QikuUtils;
import com.lzf.easyfloat.permission.rom.RomUtils;
import com.lzf.easyfloat.utils.Logger;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: PermissionUtils.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u0010\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0007J\u0015\u0010\u0016\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0000¢\u0006\u0002\b\u001bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/lzf/easyfloat/permission/PermissionUtils;", "", "()V", "TAG", "", "requestCode", "", "checkPermission", "", "context", "Landroid/content/Context;", "commonROMPermissionApply", "", "fragment", "Landroid/app/Fragment;", "commonROMPermissionApplyInternal", "commonROMPermissionCheck", "huaweiPermissionCheck", "meizuPermissionCheck", "miuiPermissionCheck", "oppoROMPermissionCheck", "qikuPermissionCheck", "requestPermission", "activity", "Landroid/app/Activity;", "onPermissionResult", "Lcom/lzf/easyfloat/interfaces/OnPermissionResult;", "requestPermission$easyfloat_release", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class PermissionUtils {
    public static final PermissionUtils INSTANCE = new PermissionUtils();
    private static final String TAG = "PermissionUtils--->";
    public static final int requestCode = 199;

    private PermissionUtils() {
    }

    @JvmStatic
    public static final boolean checkPermission(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (Build.VERSION.SDK_INT < 23) {
            if (RomUtils.INSTANCE.checkIsHuaweiRom()) {
                return INSTANCE.huaweiPermissionCheck(context);
            }
            if (RomUtils.INSTANCE.checkIsMiuiRom()) {
                return INSTANCE.miuiPermissionCheck(context);
            }
            if (RomUtils.INSTANCE.checkIsOppoRom()) {
                return INSTANCE.oppoROMPermissionCheck(context);
            }
            if (RomUtils.INSTANCE.checkIsMeizuRom()) {
                return INSTANCE.meizuPermissionCheck(context);
            }
            if (RomUtils.INSTANCE.checkIs360Rom()) {
                return INSTANCE.qikuPermissionCheck(context);
            }
            return true;
        }
        return INSTANCE.commonROMPermissionCheck(context);
    }

    @JvmStatic
    public static final void requestPermission(Activity activity, OnPermissionResult onPermissionResult) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        Intrinsics.checkParameterIsNotNull(onPermissionResult, "onPermissionResult");
        PermissionFragment.Companion.requestPermission(activity, onPermissionResult);
    }

    public final void requestPermission$easyfloat_release(Fragment fragment) {
        Intrinsics.checkParameterIsNotNull(fragment, "fragment");
        if (Build.VERSION.SDK_INT < 23) {
            if (RomUtils.INSTANCE.checkIsHuaweiRom()) {
                HuaweiUtils.applyPermission(fragment.getActivity());
                return;
            } else if (RomUtils.INSTANCE.checkIsMiuiRom()) {
                MiuiUtils.applyMiuiPermission(fragment.getActivity());
                return;
            } else if (RomUtils.INSTANCE.checkIsOppoRom()) {
                OppoUtils.applyOppoPermission(fragment.getActivity());
                return;
            } else if (RomUtils.INSTANCE.checkIsMeizuRom()) {
                MeizuUtils.applyPermission(fragment);
                return;
            } else if (RomUtils.INSTANCE.checkIs360Rom()) {
                QikuUtils.applyPermission(fragment.getActivity());
                return;
            } else {
                Logger.INSTANCE.i(TAG, "原生 Android 6.0 以下无需权限申请");
                return;
            }
        }
        commonROMPermissionApply(fragment);
    }

    private final boolean huaweiPermissionCheck(Context context) {
        return HuaweiUtils.checkFloatWindowPermission(context);
    }

    private final boolean miuiPermissionCheck(Context context) {
        return MiuiUtils.checkFloatWindowPermission(context);
    }

    private final boolean meizuPermissionCheck(Context context) {
        return MeizuUtils.checkFloatWindowPermission(context);
    }

    private final boolean qikuPermissionCheck(Context context) {
        return QikuUtils.checkFloatWindowPermission(context);
    }

    private final boolean oppoROMPermissionCheck(Context context) {
        return OppoUtils.checkFloatWindowPermission(context);
    }

    private final boolean commonROMPermissionCheck(Context context) {
        if (RomUtils.INSTANCE.checkIsMeizuRom()) {
            return meizuPermissionCheck(context);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Object invoke = Settings.class.getDeclaredMethod("canDrawOverlays", Context.class).invoke(null, context);
                if (invoke != null) {
                    return ((Boolean) invoke).booleanValue();
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        return true;
    }

    private final void commonROMPermissionApply(Fragment fragment) {
        if (RomUtils.INSTANCE.checkIsMeizuRom()) {
            MeizuUtils.applyPermission(fragment);
        } else if (Build.VERSION.SDK_INT >= 23) {
            try {
                commonROMPermissionApplyInternal(fragment);
            } catch (Exception e) {
                Logger logger = Logger.INSTANCE;
                String stackTraceString = Log.getStackTraceString(e);
                Intrinsics.checkExpressionValueIsNotNull(stackTraceString, "Log.getStackTraceString(e)");
                logger.e(TAG, stackTraceString);
            }
        } else {
            Logger.INSTANCE.d(TAG, "user manually refuse OVERLAY_PERMISSION");
        }
    }

    @JvmStatic
    public static final void commonROMPermissionApplyInternal(Fragment fragment) {
        Intrinsics.checkParameterIsNotNull(fragment, "fragment");
        try {
            Intent intent = new Intent(Settings.class.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION").get(null).toString());
            StringBuilder sb = new StringBuilder();
            sb.append("package:");
            Activity activity = fragment.getActivity();
            Intrinsics.checkExpressionValueIsNotNull(activity, "fragment.activity");
            sb.append(activity.getPackageName());
            intent.setData(Uri.parse(sb.toString()));
            fragment.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, String.valueOf(e));
        }
    }
}
