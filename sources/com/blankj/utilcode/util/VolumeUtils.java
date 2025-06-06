package com.blankj.utilcode.util;

import android.media.AudioManager;
import android.os.Build;
import com.jieli.component.audio.AudioConfig;
/* loaded from: classes.dex */
public class VolumeUtils {
    public static int getVolume(int i) {
        return ((AudioManager) Utils.getApp().getSystemService(AudioConfig.DIR_AUDIO)).getStreamVolume(i);
    }

    public static void setVolume(int i, int i2, int i3) {
        try {
            ((AudioManager) Utils.getApp().getSystemService(AudioConfig.DIR_AUDIO)).setStreamVolume(i, i2, i3);
        } catch (SecurityException unused) {
        }
    }

    public static int getMaxVolume(int i) {
        return ((AudioManager) Utils.getApp().getSystemService(AudioConfig.DIR_AUDIO)).getStreamMaxVolume(i);
    }

    public static int getMinVolume(int i) {
        AudioManager audioManager = (AudioManager) Utils.getApp().getSystemService(AudioConfig.DIR_AUDIO);
        if (Build.VERSION.SDK_INT >= 28) {
            return audioManager.getStreamMinVolume(i);
        }
        return 0;
    }
}
