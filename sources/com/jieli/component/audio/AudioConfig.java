package com.jieli.component.audio;

import android.content.Context;
import android.os.Environment;
import com.jieli.component.utils.FileUtil;
import java.io.File;
/* loaded from: classes2.dex */
public class AudioConfig {
    public static final String AUDIO_PCM_FILENAME = "test_data.pcm";
    public static final String DIR_AUDIO = "audio";
    private int audioInput = 1;
    private int audioSampleRate = 16000;
    private int channelConfig = 16;
    private int audioFormat = 2;
    private int outputChannel = 4;
    private int streamType = 3;

    public int getAudioInputWay() {
        return this.audioInput;
    }

    public void setAudioInputWay(int i) {
        this.audioInput = i;
    }

    public int getAudioSampleRate() {
        return this.audioSampleRate;
    }

    public void setAudioSampleRate(int i) {
        this.audioSampleRate = i;
    }

    public int getChannelConfig() {
        return this.channelConfig;
    }

    public void setChannelConfig(int i) {
        this.channelConfig = i;
    }

    public int getOutputChannel() {
        return this.outputChannel;
    }

    public void setOutputChannel(int i) {
        this.outputChannel = i;
    }

    public int getAudioFormat() {
        return this.audioFormat;
    }

    public void setAudioFormat(int i) {
        this.audioFormat = i;
    }

    public static boolean isSdcardExit() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static String getRawFilePath(Context context) {
        if (context == null) {
            return "";
        }
        return FileUtil.splicingFilePath(context, context.getPackageName(), DIR_AUDIO, null, null) + "/" + AUDIO_PCM_FILENAME;
    }

    public static long getFileSize(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.length();
        }
        return -1L;
    }

    public int getStreamType() {
        return this.streamType;
    }

    public void setStreamType(int i) {
        this.streamType = i;
    }
}
