package com.jieli.component.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import java.lang.ref.WeakReference;
/* loaded from: classes2.dex */
public class ToastUtil {
    private static WeakReference<Context> contextWeakReference;
    private static Toast mToast;

    public static void init(Context context) {
        contextWeakReference = new WeakReference<>(context);
    }

    public static void showToast(String str, int i) {
        WeakReference<Context> weakReference = contextWeakReference;
        if (weakReference == null) {
            throw new RuntimeException("u have not init toast utils");
        }
        if (weakReference.get() == null || TextUtils.isEmpty(str) || i < 0) {
            return;
        }
        Toast toast = mToast;
        if (toast == null) {
            mToast = Toast.makeText(contextWeakReference.get(), str, i);
        } else {
            toast.setText(str);
            mToast.setDuration(i);
        }
        mToast.setGravity(17, 0, 0);
        mToast.show();
    }

    public static void showToastTop(String str, int i) {
        WeakReference<Context> weakReference = contextWeakReference;
        if (weakReference == null) {
            throw new RuntimeException("u have not init toast utils");
        }
        if (weakReference.get() == null || TextUtils.isEmpty(str) || i < 0) {
            return;
        }
        Toast toast = mToast;
        if (toast == null) {
            mToast = Toast.makeText(contextWeakReference.get(), str, i);
        } else {
            toast.setText(str);
            mToast.setDuration(i);
        }
        mToast.setGravity(48, 0, ValueUtil.dp2px(contextWeakReference.get(), 48));
        mToast.show();
    }

    public static void showToastShort(String str) {
        showToast(str, 0);
    }

    public static void showToastShort(int i) {
        WeakReference<Context> weakReference = contextWeakReference;
        if (weakReference == null) {
            throw new RuntimeException("u have not init toast utils");
        }
        if (weakReference.get() == null) {
            return;
        }
        showToastShort(contextWeakReference.get().getResources().getString(i));
    }

    public static void showToastLong(String str) {
        showToast(str, 1);
    }

    public static void showToastLong(int i) {
        WeakReference<Context> weakReference = contextWeakReference;
        if (weakReference == null) {
            throw new RuntimeException("u have not init toast utils");
        }
        if (weakReference.get() == null) {
            return;
        }
        showToastLong(contextWeakReference.get().getResources().getString(i));
    }
}
