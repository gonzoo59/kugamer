package com.jieli.component.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.HashSet;
import java.util.Set;
/* loaded from: classes2.dex */
public class HandlerManager {
    private static volatile HandlerManager mInstance;
    private Set<Handler.Callback> callbacks = new HashSet();
    private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.jieli.component.utils.HandlerManager.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            for (Handler.Callback callback : HandlerManager.this.callbacks) {
                callback.handleMessage(message);
            }
            return false;
        }
    });

    public Handler getMainHandler() {
        return this.mHandler;
    }

    private HandlerManager() {
    }

    public static HandlerManager getInstance() {
        if (mInstance == null) {
            synchronized (HandlerManager.class) {
                if (mInstance == null) {
                    mInstance = new HandlerManager();
                }
            }
        }
        return mInstance;
    }

    public void addMsgCallback(Handler.Callback callback) {
        if (this.callbacks.contains(callback)) {
            return;
        }
        this.callbacks.add(callback);
    }

    public void removeMsgCallback(Handler.Callback callback) {
        if (this.callbacks.contains(callback)) {
            this.callbacks.remove(callback);
        }
    }
}
