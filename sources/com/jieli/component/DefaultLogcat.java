package com.jieli.component;

import android.util.Log;
import com.jieli.component.Logcat;
/* compiled from: Logcat.java */
/* loaded from: classes2.dex */
class DefaultLogcat implements Logcat.AbsLogcat {
    @Override // com.jieli.component.Logcat.AbsLogcat
    public void v(String str, String str2) {
        Log.v(str, str2);
    }

    @Override // com.jieli.component.Logcat.AbsLogcat
    public void d(String str, String str2) {
        Log.d(str, str2);
    }

    @Override // com.jieli.component.Logcat.AbsLogcat
    public void i(String str, String str2) {
        Log.i(str, str2);
    }

    @Override // com.jieli.component.Logcat.AbsLogcat
    public void w(String str, String str2) {
        Log.w(str, str2);
    }

    @Override // com.jieli.component.Logcat.AbsLogcat
    public void e(String str, String str2) {
        Log.e(str, str2);
    }
}
