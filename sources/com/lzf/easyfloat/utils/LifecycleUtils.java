package com.lzf.easyfloat.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.os.Bundle;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.widget.appfloat.AppFloatManager;
import com.lzf.easyfloat.widget.appfloat.FloatManager;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: LifecycleUtils.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0012\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0002J\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006J#\u0010\u000f\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\u0010\u001a\u00020\r2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002¢\u0006\u0002\u0010\u0013R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/lzf/easyfloat/utils/LifecycleUtils;", "", "()V", "activityCount", "", "application", "Landroid/app/Application;", "checkHide", "", "checkShow", "activity", "Landroid/app/Activity;", "isForeground", "", "setLifecycleCallbacks", "setVisible", "isShow", "tag", "", "(ZLjava/lang/String;)Lkotlin/Unit;", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class LifecycleUtils {
    public static final LifecycleUtils INSTANCE = new LifecycleUtils();
    private static int activityCount;
    private static Application application;

    private LifecycleUtils() {
    }

    public final void setLifecycleCallbacks(Application application2) {
        Intrinsics.checkParameterIsNotNull(application2, "application");
        application = application2;
        application2.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: com.lzf.easyfloat.utils.LifecycleUtils$setLifecycleCallbacks$1
            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityPaused(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(Activity activity) {
                int i;
                if (activity != null) {
                    LifecycleUtils lifecycleUtils = LifecycleUtils.INSTANCE;
                    i = LifecycleUtils.activityCount;
                    LifecycleUtils.activityCount = i + 1;
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityResumed(Activity activity) {
                LifecycleUtils.INSTANCE.checkShow(activity);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(Activity activity) {
                int i;
                if (activity != null) {
                    LifecycleUtils lifecycleUtils = LifecycleUtils.INSTANCE;
                    i = LifecycleUtils.activityCount;
                    LifecycleUtils.activityCount = i - 1;
                    LifecycleUtils.INSTANCE.checkHide();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkShow(Activity activity) {
        if (activity == null) {
            return;
        }
        for (Map.Entry<String, AppFloatManager> entry : FloatManager.INSTANCE.getFloatMap().entrySet()) {
            String key = entry.getKey();
            FloatConfig config = entry.getValue().getConfig();
            if (config.getShowPattern() == ShowPattern.BACKGROUND) {
                INSTANCE.setVisible(false, key);
            } else if (config.getNeedShow$easyfloat_release()) {
                LifecycleUtils lifecycleUtils = INSTANCE;
                Set<String> filterSet = config.getFilterSet();
                ComponentName componentName = activity.getComponentName();
                Intrinsics.checkExpressionValueIsNotNull(componentName, "activity.componentName");
                lifecycleUtils.setVisible(!filterSet.contains(componentName.getClassName()), key);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkHide() {
        if (isForeground()) {
            return;
        }
        for (Map.Entry<String, AppFloatManager> entry : FloatManager.INSTANCE.getFloatMap().entrySet()) {
            String key = entry.getKey();
            FloatConfig config = entry.getValue().getConfig();
            INSTANCE.setVisible(config.getShowPattern() != ShowPattern.FOREGROUND && config.getNeedShow$easyfloat_release(), key);
        }
    }

    public final boolean isForeground() {
        return activityCount > 0;
    }

    static /* synthetic */ Unit setVisible$default(LifecycleUtils lifecycleUtils, boolean z, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            z = lifecycleUtils.isForeground();
        }
        return lifecycleUtils.setVisible(z, str);
    }

    private final Unit setVisible(boolean z, String str) {
        return FloatManager.visible$default(FloatManager.INSTANCE, z, str, false, 4, null);
    }
}
