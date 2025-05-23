package com.jieli.otasdk.util;

import android.os.FileObserver;
import com.jieli.jl_bt_ota.util.JL_Log;
/* loaded from: classes2.dex */
public class OtaFileObserver extends FileObserver {
    private FileObserverCallback mFileObserverCallback;

    public OtaFileObserver(String str) {
        super(str);
    }

    public void setFileObserverCallback(FileObserverCallback fileObserverCallback) {
        this.mFileObserverCallback = fileObserverCallback;
    }

    @Override // android.os.FileObserver
    public void onEvent(int i, String str) {
        if (this.mFileObserverCallback == null || str == null) {
            return;
        }
        JL_Log.d("zzc_observer", "-onEvent- event = " + i + ", path = " + str);
        this.mFileObserverCallback.onChange(i, str);
    }
}
