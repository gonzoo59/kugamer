package com.jieli.otasdk;

import android.app.Application;
import com.jieli.component.ActivityManager;
import com.jieli.component.utils.ToastUtil;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
/* loaded from: classes2.dex */
public class MainApplication {
    private static Application application = null;
    private static MainApplication instance = null;
    private static final boolean isDebug = false;

    private static void init() {
        ActivityManager.init(application);
        ToastUtil.init(application);
        CommonUtil.setMainContext(application);
        JL_Log.setLog(false);
        JL_Log.setIsSaveLogFile(application, false);
    }

    public static void setApp(Application application2) {
        if (instance == null) {
            new MainApplication();
        }
        application = application2;
        init();
    }

    public static Application getInstance() {
        if (instance == null) {
            new MainApplication();
        }
        return application;
    }
}
