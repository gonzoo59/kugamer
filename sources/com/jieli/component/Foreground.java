package com.jieli.component;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes2.dex */
public class Foreground implements Application.ActivityLifecycleCallbacks {
    public static final String TAG = "com.jieli.component.Foreground";
    private static Foreground instance;
    private List<Listener> listeners = new CopyOnWriteArrayList();
    private Handler mHandler = new Handler();
    private int paused;
    private int resumed;
    private int started;
    private int stopped;

    /* loaded from: classes2.dex */
    public interface Listener {
        void onBecameBackground();

        void onBecameForeground();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public static Foreground init(Application application) {
        if (instance == null) {
            Foreground foreground = new Foreground();
            instance = foreground;
            application.registerActivityLifecycleCallbacks(foreground);
        }
        return instance;
    }

    public static Foreground get(Application application) {
        if (instance == null) {
            init(application);
        }
        return instance;
    }

    public static Foreground get(Context context) {
        if (instance == null) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext instanceof Application) {
                init((Application) applicationContext);
            } else {
                throw new IllegalStateException("Foreground is not initialised and cannot obtain the Application object");
            }
        }
        return instance;
    }

    public static Foreground get() {
        Foreground foreground = instance;
        if (foreground != null) {
            return foreground;
        }
        throw new IllegalStateException("Foreground is not initialised - invoke at least once with parameterised init/get");
    }

    public boolean isForeground() {
        return this.resumed > this.paused;
    }

    public boolean isBackground() {
        return this.started <= this.stopped;
    }

    public void addListener(Listener listener) {
        List<Listener> list = this.listeners;
        if (list == null || listener == null) {
            return;
        }
        list.add(listener);
    }

    public void removeListener(Listener listener) {
        List<Listener> list = this.listeners;
        if (list == null || listener == null) {
            return;
        }
        list.remove(listener);
    }

    private void notifyEvent(final int i) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.component.Foreground.1
            @Override // java.lang.Runnable
            public void run() {
                if (Foreground.this.listeners != null) {
                    for (Listener listener : Foreground.this.listeners) {
                        if (i == 0) {
                            listener.onBecameForeground();
                        } else {
                            listener.onBecameBackground();
                        }
                    }
                }
            }
        });
    }

    public void release() {
        instance = null;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        List<Listener> list = this.listeners;
        if (list != null) {
            list.clear();
            this.listeners = null;
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        int i = this.resumed + 1;
        this.resumed = i;
        if (i > this.paused) {
            notifyEvent(0);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        this.paused++;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        this.started++;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        int i = this.stopped + 1;
        this.stopped = i;
        if (this.started <= i) {
            notifyEvent(1);
        }
    }
}
