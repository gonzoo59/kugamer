package com.baidu.kwgames.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;
/* loaded from: classes.dex */
public class MarqueTextView extends TextView {
    @Override // android.view.View
    public boolean isFocused() {
        return true;
    }

    public MarqueTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public MarqueTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MarqueTextView(Context context) {
        super(context);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        if (z) {
            super.onFocusChanged(z, i, rect);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onWindowFocusChanged(boolean z) {
        if (z) {
            super.onWindowFocusChanged(z);
        }
    }
}
