package com.jieli.otasdk.util;

import android.os.Handler;
import android.os.Looper;
import com.jieli.jl_bt_ota.util.FileUtil;
import com.jieli.otasdk.MainApplication;
import java.util.ArrayList;
import java.util.Iterator;
/* loaded from: classes2.dex */
public class OtaFileObserverHelper {
    private static volatile OtaFileObserverHelper instance;
    private boolean isWatching;
    private final ArrayList<FileObserverCallback> mFileObserverCallbacks = new ArrayList<>();
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final OtaFileObserver mOtaFileObserver;
    private final String watchPath;

    private OtaFileObserverHelper() {
        String splicingFilePath = FileUtil.splicingFilePath(MainApplication.getInstance(), MainApplication.getInstance().getPackageName(), JL_Constant.DIR_UPGRADE, null, null);
        this.watchPath = splicingFilePath;
        OtaFileObserver otaFileObserver = new OtaFileObserver(splicingFilePath);
        this.mOtaFileObserver = otaFileObserver;
        otaFileObserver.setFileObserverCallback(new FileObserverCallback() { // from class: com.jieli.otasdk.util.OtaFileObserverHelper$$ExternalSyntheticLambda0
            @Override // com.jieli.otasdk.util.FileObserverCallback
            public final void onChange(int i, String str) {
                OtaFileObserverHelper.this.m134lambda$new$1$comjieliotasdkutilOtaFileObserverHelper(i, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$1$com-jieli-otasdk-util-OtaFileObserverHelper  reason: not valid java name */
    public /* synthetic */ void m134lambda$new$1$comjieliotasdkutilOtaFileObserverHelper(final int i, final String str) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.otasdk.util.OtaFileObserverHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                OtaFileObserverHelper.this.m133lambda$new$0$comjieliotasdkutilOtaFileObserverHelper(i, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-jieli-otasdk-util-OtaFileObserverHelper  reason: not valid java name */
    public /* synthetic */ void m133lambda$new$0$comjieliotasdkutilOtaFileObserverHelper(int i, String str) {
        if (this.mFileObserverCallbacks.isEmpty()) {
            return;
        }
        Iterator it = new ArrayList(this.mFileObserverCallbacks).iterator();
        while (it.hasNext()) {
            ((FileObserverCallback) it.next()).onChange(i, str);
        }
    }

    public static OtaFileObserverHelper getInstance() {
        if (instance == null) {
            synchronized (OtaFileObserverHelper.class) {
                if (instance == null) {
                    instance = new OtaFileObserverHelper();
                }
            }
        }
        return instance;
    }

    public void registerFileObserverCallback(FileObserverCallback fileObserverCallback) {
        if (fileObserverCallback == null || this.mFileObserverCallbacks.contains(fileObserverCallback)) {
            return;
        }
        this.mFileObserverCallbacks.add(fileObserverCallback);
    }

    public void unregisterFileObserverCallback(FileObserverCallback fileObserverCallback) {
        if (fileObserverCallback == null || this.mFileObserverCallbacks.isEmpty()) {
            return;
        }
        this.mFileObserverCallbacks.remove(fileObserverCallback);
    }

    public boolean isWatching() {
        return this.isWatching;
    }

    public String getWatchPath() {
        return this.watchPath;
    }

    public void startObserver() {
        this.mOtaFileObserver.startWatching();
        this.isWatching = true;
    }

    public void stopObserver() {
        this.mOtaFileObserver.stopWatching();
        this.isWatching = false;
    }

    public void destroy() {
        stopObserver();
        this.mOtaFileObserver.setFileObserverCallback(null);
        this.mFileObserverCallbacks.clear();
        instance = null;
    }
}
