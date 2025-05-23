package com.lzf.easyfloat.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.lzf.easyfloat.widget.appfloat.AppFloatManager;
import com.lzf.easyfloat.widget.appfloat.FloatManager;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: InputMethodUtils.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\u0010\u0007J\u001c\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007¨\u0006\u000b"}, d2 = {"Lcom/lzf/easyfloat/utils/InputMethodUtils;", "", "()V", "closedInputMethod", "", "tag", "", "(Ljava/lang/String;)Lkotlin/Unit;", "openInputMethod", "editText", "Landroid/widget/EditText;", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class InputMethodUtils {
    public static final InputMethodUtils INSTANCE = new InputMethodUtils();

    @JvmStatic
    public static final Unit closedInputMethod() {
        return closedInputMethod$default(null, 1, null);
    }

    @JvmStatic
    public static final void openInputMethod(EditText editText) {
        openInputMethod$default(editText, null, 2, null);
    }

    private InputMethodUtils() {
    }

    public static /* synthetic */ void openInputMethod$default(EditText editText, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        openInputMethod(editText, str);
    }

    @JvmStatic
    public static final void openInputMethod(final EditText editText, String str) {
        Intrinsics.checkParameterIsNotNull(editText, "editText");
        AppFloatManager appFloatManager = FloatManager.INSTANCE.getAppFloatManager(str);
        if (appFloatManager != null) {
            appFloatManager.getParams().flags = 32;
            appFloatManager.getWindowManager().updateViewLayout(appFloatManager.getFrameLayout(), appFloatManager.getParams());
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.lzf.easyfloat.utils.InputMethodUtils$openInputMethod$2
            @Override // java.lang.Runnable
            public final void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext().getSystemService("input_method");
                if (inputMethodManager != null) {
                    inputMethodManager.showSoftInput(editText, 0);
                }
            }
        }, 100L);
    }

    @JvmStatic
    public static final Unit closedInputMethod(String str) {
        AppFloatManager appFloatManager = FloatManager.INSTANCE.getAppFloatManager(str);
        if (appFloatManager != null) {
            appFloatManager.getParams().flags = 40;
            appFloatManager.getWindowManager().updateViewLayout(appFloatManager.getFrameLayout(), appFloatManager.getParams());
            return Unit.INSTANCE;
        }
        return null;
    }

    public static /* synthetic */ Unit closedInputMethod$default(String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        return closedInputMethod(str);
    }
}
