package com.jieli.adb.shell.server;

import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.view.InputEvent;
import android.view.KeyEvent;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/* loaded from: assets/adb/sincoserver.dex */
class ImInjectHelper {
    private static Method Input_setSource;
    private static Method input_injectInputEvent;
    private static Method key_obtain;
    private static Object manager = getInputManager();

    /* JADX INFO: Access modifiers changed from: package-private */
    public ImInjectHelper() {
        try {
            key_obtain = Class.forName("android.view.KeyEvent").getMethod("obtain", Long.TYPE, Long.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class);
            Input_setSource = Class.forName("android.view.InputEvent").getMethod("setSource", Integer.TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static KeyEvent getKeyEvent(int i, int i2) {
        try {
            PrintStream printStream = System.out;
            printStream.println("getKeyEvent action=" + i + " code=" + i2);
            return (KeyEvent) key_obtain.invoke(null, Long.valueOf(SystemClock.uptimeMillis()), Long.valueOf(SystemClock.uptimeMillis()), Integer.valueOf(i), Integer.valueOf(i2), 0, 0, 0, 0, 0, 0, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static Class<?> getInputManagerClass() {
        try {
            return Class.forName("android.hardware.input.InputManagerGlobal");
        } catch (ClassNotFoundException unused) {
            return InputManager.class;
        }
    }

    private static Object getInputManager() {
        try {
            manager = getInputManagerClass().getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            input_injectInputEvent = manager.getClass().getMethod("injectInputEvent", InputEvent.class, Integer.TYPE);
            return manager;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean injectInputEvent(InputEvent inputEvent) {
        try {
            return ((Boolean) input_injectInputEvent.invoke(manager, inputEvent, 0)).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
