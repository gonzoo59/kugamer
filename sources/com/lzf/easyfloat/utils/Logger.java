package com.lzf.easyfloat.utils;

import android.util.Log;
import com.lzf.easyfloat.EasyFloat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: Logger.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0001J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006J\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0001J\u0016\u0010\n\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006J\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0001J\u0016\u0010\u000b\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006J\u000e\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0001J\u0016\u0010\f\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006J\u000e\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0001J\u0016\u0010\r\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/lzf/easyfloat/utils/Logger;", "", "()V", "logEnable", "", "tag", "", "d", "", "msg", "e", "i", "v", "w", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class Logger {
    public static final Logger INSTANCE = new Logger();
    private static String tag = "EasyFloat--->";
    private static boolean logEnable = EasyFloat.Companion.isDebug$easyfloat_release();

    private Logger() {
    }

    public final void i(Object msg) {
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        i(tag, msg.toString());
    }

    public final void v(Object msg) {
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        v(tag, msg.toString());
    }

    public final void d(Object msg) {
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        d(tag, msg.toString());
    }

    public final void w(Object msg) {
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        w(tag, msg.toString());
    }

    public final void e(Object msg) {
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        e(tag, msg.toString());
    }

    public final void i(String tag2, String msg) {
        Intrinsics.checkParameterIsNotNull(tag2, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        if (logEnable) {
            Log.i(tag2, msg);
        }
    }

    public final void v(String tag2, String msg) {
        Intrinsics.checkParameterIsNotNull(tag2, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        if (logEnable) {
            Log.v(tag2, msg);
        }
    }

    public final void d(String tag2, String msg) {
        Intrinsics.checkParameterIsNotNull(tag2, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        if (logEnable) {
            Log.d(tag2, msg);
        }
    }

    public final void w(String tag2, String msg) {
        Intrinsics.checkParameterIsNotNull(tag2, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        if (logEnable) {
            Log.w(tag2, msg);
        }
    }

    public final void e(String tag2, String msg) {
        Intrinsics.checkParameterIsNotNull(tag2, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        if (logEnable) {
            Log.e(tag2, msg);
        }
    }
}
