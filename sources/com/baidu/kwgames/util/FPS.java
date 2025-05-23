package com.baidu.kwgames.util;

import android.view.Choreographer;
/* loaded from: classes.dex */
public class FPS {
    private static final int FPS_UPDATE_INTERVAL_MS = 1000;
    static FPS s_instance;
    private long m_lLastFrameTimeNanos;
    private int m_nFrameCount;
    public boolean m_boStart = false;
    private final Choreographer.FrameCallback m_frameCallback = new Choreographer.FrameCallback() { // from class: com.baidu.kwgames.util.FPS.1
        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            if (FPS.this.m_lLastFrameTimeNanos != 0) {
                long j2 = j - FPS.this.m_lLastFrameTimeNanos;
                FPS.access$108(FPS.this);
                if (j2 >= 1000000000) {
                    FPS fps = FPS.this;
                    fps.m_nCurFPS = (int) ((fps.m_nFrameCount * 1000000000) / j2);
                    FPS.this.m_nFrameCount = 0;
                }
            }
            FPS.this.m_lLastFrameTimeNanos = j;
            if (FPS.this.m_boStart) {
                Choreographer.getInstance().postFrameCallback(FPS.this.m_frameCallback);
            }
        }
    };
    public int m_nCurFPS = 0;

    static /* synthetic */ int access$108(FPS fps) {
        int i = fps.m_nFrameCount;
        fps.m_nFrameCount = i + 1;
        return i;
    }

    FPS() {
    }

    static FPS get_instance() {
        if (s_instance == null) {
            s_instance = new FPS();
        }
        return s_instance;
    }

    public void start() {
        if (this.m_boStart) {
            return;
        }
        this.m_boStart = true;
        Choreographer.getInstance().postFrameCallback(this.m_frameCallback);
    }

    public void stop() {
        this.m_boStart = false;
    }
}
