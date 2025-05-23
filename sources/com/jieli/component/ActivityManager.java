package com.jieli.component;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.util.Iterator;
import java.util.Stack;
/* loaded from: classes2.dex */
public class ActivityManager {
    private static boolean isInit = false;
    private static ActivityManager mInstance;
    private Stack<Activity> activityStack;
    private Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks() { // from class: com.jieli.component.ActivityManager.1
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
            ActivityManager.this.pushActivity(activity);
            Logcat.i("ActivityLifecycle", "onActivityCreated:" + activity.getClass().getName());
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            ActivityManager.this.currentActivity = activity;
            Logcat.i("ActivityLifecycle", "onActivityResumed:" + activity.getClass().getName());
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            Logcat.i("ActivityLifecycle", "onActivityDestroyed:" + activity.getClass().getName());
            ActivityManager.this.popActivity(activity);
            if (ActivityManager.this.activityStack.size() == 0) {
                ActivityManager.this.currentActivity = null;
            }
        }
    };
    private Activity currentActivity;

    public static ActivityManager getInstance() {
        if (!isInit) {
            throw new RuntimeException("ActivityManager没有初始化...");
        }
        return mInstance;
    }

    public static void init(Application application) {
        isInit = true;
        ActivityManager activityManager = new ActivityManager();
        mInstance = activityManager;
        activityManager.registerActivityLifecycleCallbacks(application);
    }

    private void registerActivityLifecycleCallbacks(Application application) {
        application.registerActivityLifecycleCallbacks(this.callbacks);
    }

    private ActivityManager() {
    }

    public Activity getCurrentActivity() {
        return this.currentActivity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushActivity(Activity activity) {
        if (this.activityStack == null) {
            this.activityStack = new Stack<>();
        }
        this.activityStack.add(activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void popActivity(Activity activity) {
        Stack<Activity> stack = this.activityStack;
        if (stack == null || activity == null) {
            return;
        }
        stack.remove(activity);
    }

    public Activity peekActivity() {
        return this.activityStack.peek();
    }

    public Activity getTopActivity() {
        Stack<Activity> stack = this.activityStack;
        if (stack != null) {
            return stack.lastElement();
        }
        return null;
    }

    public void popAllActivity() {
        Stack<Activity> stack = this.activityStack;
        if (stack == null) {
            return;
        }
        Iterator<Activity> it = stack.iterator();
        while (it.hasNext()) {
            Activity next = it.next();
            if (next != null) {
                next.finish();
            }
        }
    }

    public Activity findActivtyByName(String str) {
        Iterator<Activity> it = this.activityStack.iterator();
        while (it.hasNext()) {
            Activity next = it.next();
            if (next.getClass().getName().equals(str)) {
                return next;
            }
        }
        return null;
    }

    public int getCacheActivitySize() {
        Stack<Activity> stack = this.activityStack;
        if (stack == null) {
            return 0;
        }
        return stack.size();
    }
}
