package com.baidu.kwgames;

import android.content.Context;
/* loaded from: classes.dex */
public class Mouse extends AbsFloatBase {
    @Override // com.baidu.kwgames.AbsFloatBase
    protected void onAddWindowFailed(Exception exc) {
    }

    public Mouse(Context context) {
        super(context);
    }

    @Override // com.baidu.kwgames.AbsFloatBase
    public void create() {
        super.create();
        this.mViewMode = 2;
        inflate(R.layout.mouse);
    }

    public void setLocation(int i, int i2) {
        this.mLayoutParams.x = i;
        this.mLayoutParams.y = i2;
        this.mWindowManager.updateViewLayout(this.mInflate, this.mLayoutParams);
    }
}
