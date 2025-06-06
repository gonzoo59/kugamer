package com.bumptech.glide.request;
/* loaded from: classes.dex */
public interface Request {
    void begin();

    void clear();

    boolean isCleared();

    boolean isComplete();

    boolean isEquivalentTo(Request request);

    boolean isRunning();

    void pause();
}
