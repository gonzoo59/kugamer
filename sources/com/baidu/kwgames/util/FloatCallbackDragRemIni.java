package com.baidu.kwgames.util;

import android.view.MotionEvent;
import android.view.View;
import com.blankj.utilcode.util.SPUtils;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
/* loaded from: classes.dex */
public class FloatCallbackDragRemIni implements OnFloatCallbacks {
    String m_strIniX;
    String m_strIniY;

    @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
    public void createdResult(boolean z, String str, View view) {
    }

    @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
    public void dismiss() {
    }

    @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
    public void drag(View view, MotionEvent motionEvent) {
    }

    @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
    public void hide(View view) {
    }

    @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
    public void show(View view) {
    }

    @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
    public void touchEvent(View view, MotionEvent motionEvent) {
    }

    public FloatCallbackDragRemIni(String str, String str2) {
        this.m_strIniX = str;
        this.m_strIniY = str2;
    }

    @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
    public void dragEnd(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        SPUtils sPUtils = SPUtils.getInstance();
        sPUtils.put(this.m_strIniX, iArr[0]);
        sPUtils.put(this.m_strIniY, iArr[1]);
    }
}
