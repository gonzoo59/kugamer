package com.tencent.bugly.proguard;

import android.app.ActivityManager;
import android.os.Process;
import android.text.TextUtils;
import java.util.List;
/* compiled from: BUGLY */
/* loaded from: classes2.dex */
public final class az {
    public static ActivityManager.ProcessErrorStateInfo a(ActivityManager activityManager, long j) {
        if (activityManager == null) {
            al.c("get anr state, ActivityManager is null", new Object[0]);
            return null;
        }
        al.c("get anr state, timeout:%d", Long.valueOf(j));
        long j2 = j / 500;
        int i = 0;
        while (true) {
            ActivityManager.ProcessErrorStateInfo a = a(activityManager.getProcessesInErrorState());
            if (a == null) {
                al.c("found proc state is null", new Object[0]);
            } else if (a.condition == 2) {
                al.c("found proc state is anr! proc:%s", a.processName);
                return a;
            } else if (a.condition == 1) {
                al.c("found proc state is crashed!", new Object[0]);
                return null;
            }
            int i2 = i + 1;
            if (i < j2) {
                al.c("try the %s times:", Integer.valueOf(i2));
                ap.b(500L);
                i = i2;
            } else {
                return a("Find process anr, but unable to get anr message.");
            }
        }
    }

    private static ActivityManager.ProcessErrorStateInfo a(List<ActivityManager.ProcessErrorStateInfo> list) {
        if (list == null || list.isEmpty()) {
            al.c("error state info list is null", new Object[0]);
            return null;
        }
        int myPid = Process.myPid();
        for (ActivityManager.ProcessErrorStateInfo processErrorStateInfo : list) {
            if (processErrorStateInfo.pid == myPid) {
                if (TextUtils.isEmpty(processErrorStateInfo.longMsg)) {
                    return null;
                }
                al.c("found current proc in the error state", new Object[0]);
                return processErrorStateInfo;
            }
        }
        al.c("current proc not in the error state", new Object[0]);
        return null;
    }

    private static ActivityManager.ProcessErrorStateInfo a(String str) {
        ActivityManager.ProcessErrorStateInfo processErrorStateInfo = new ActivityManager.ProcessErrorStateInfo();
        processErrorStateInfo.pid = Process.myPid();
        processErrorStateInfo.processName = z.a(Process.myPid());
        processErrorStateInfo.shortMsg = str;
        return processErrorStateInfo;
    }
}
