package com.ajguan.library;
/* loaded from: classes.dex */
public interface IRefreshHeader {
    void complete();

    void onPositionChange(float f, float f2, float f3, boolean z, State state);

    void pull();

    void refreshing();

    void reset();
}
