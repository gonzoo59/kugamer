package com.jieli.component.audio;

import android.content.Context;
import android.media.AudioRecord;
import android.media.audiofx.AcousticEchoCanceler;
import com.jieli.component.Logcat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/* loaded from: classes2.dex */
public class AudioManager {
    public static final int ERROR_AUDIO_RECORD_UNINIT = -3;
    public static final int ERROR_SD_CARD_NOT_EXIST = -2;
    public static final int ERROR_STATE_IS_RECORDING = -1;
    public static final int START_RECORD_SUCCESS = 1;
    private static AudioManager instance;
    private AudioRecord audioRecord;
    private RecorderListener listener;
    private AcousticEchoCanceler mAcousticEchoCanceler;
    private String tag = getClass().getSimpleName();
    private int bufferSizeInBytes = 0;
    private boolean isRecord = false;
    private int voiceLevel = 1;
    private AudioConfig config = new AudioConfig();
    private ExecutorService mService = Executors.newSingleThreadExecutor();

    /* loaded from: classes2.dex */
    public interface RecorderListener {
        void onRecord(byte[] bArr, int i);
    }

    public static String getErrorMessage(int i) {
        return i != -3 ? i != -2 ? i != -1 ? "" : "录音忙" : "SD卡不在线" : "麦克风初始化失败";
    }

    public void setRecordListerner(RecorderListener recorderListener) {
        this.listener = recorderListener;
    }

    public AudioManager() {
        createAudioRecord();
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            synchronized (AudioManager.class) {
                if (instance == null) {
                    instance = new AudioManager();
                }
            }
        }
        return instance;
    }

    private void createAudioRecord() {
        AudioConfig audioConfig = this.config;
        if (audioConfig == null) {
            return;
        }
        try {
            int minBufferSize = AudioRecord.getMinBufferSize(audioConfig.getAudioSampleRate(), this.config.getChannelConfig(), this.config.getAudioFormat());
            this.bufferSizeInBytes = minBufferSize;
            if (minBufferSize != -2) {
                this.audioRecord = new AudioRecord(this.config.getAudioInputWay(), this.config.getAudioSampleRate(), this.config.getChannelConfig(), this.config.getAudioFormat(), this.bufferSizeInBytes);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private int getAudioSessionId() {
        AudioRecord audioRecord = this.audioRecord;
        if (audioRecord != null) {
            return audioRecord.getAudioSessionId();
        }
        return -1;
    }

    public void setAcousticEchoCanceler() {
        AcousticEchoCanceler acousticEchoCanceler;
        if (getAudioSessionId() == -1) {
            return;
        }
        if (this.mAcousticEchoCanceler == null) {
            this.mAcousticEchoCanceler = AcousticEchoCanceler.create(getAudioSessionId());
        }
        if (AcousticEchoCanceler.isAvailable() || (acousticEchoCanceler = this.mAcousticEchoCanceler) == null) {
            return;
        }
        acousticEchoCanceler.setEnabled(true);
    }

    public void stopAcousticEchoCanceler() {
        if (this.mAcousticEchoCanceler != null) {
            if (AcousticEchoCanceler.isAvailable()) {
                this.mAcousticEchoCanceler.setEnabled(false);
            }
            this.mAcousticEchoCanceler.release();
            this.mAcousticEchoCanceler = null;
        }
    }

    public int startRecordAndFile(final Context context) {
        if (AudioConfig.isSdcardExit()) {
            if (this.isRecord) {
                return -1;
            }
            AudioRecord audioRecord = this.audioRecord;
            if (audioRecord == null || audioRecord.getState() == 0) {
                createAudioRecord();
            }
            AudioRecord audioRecord2 = this.audioRecord;
            if (audioRecord2 != null && audioRecord2.getState() == 1) {
                this.audioRecord.startRecording();
                this.isRecord = true;
                this.mService.submit(new Runnable() { // from class: com.jieli.component.audio.AudioManager.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AudioManager.this.writeDateTOFile(AudioConfig.getRawFilePath(context));
                    }
                });
                return 1;
            }
            this.audioRecord = null;
            this.isRecord = false;
            return -3;
        }
        return -2;
    }

    public void stopRecordAndFile() {
        AudioRecord audioRecord = this.audioRecord;
        if (audioRecord != null) {
            this.isRecord = false;
            if (audioRecord.getState() == 3) {
                this.audioRecord.stop();
            }
            this.audioRecord.release();
            this.audioRecord = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeDateTOFile(String str) {
        FileOutputStream fileOutputStream;
        byte[] bArr = new byte[this.bufferSizeInBytes];
        try {
            File file = new File(str);
            if (file.exists() && file.delete()) {
                Logcat.i(this.tag, "-writeDateTOFile- delete file success!");
            }
            fileOutputStream = new FileOutputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
            fileOutputStream = null;
        }
        while (this.isRecord) {
            if (-3 != this.audioRecord.read(bArr, 0, this.bufferSizeInBytes) && fileOutputStream != null) {
                RecorderListener recorderListener = this.listener;
                if (recorderListener != null) {
                    recorderListener.onRecord(bArr, 0);
                }
                try {
                    fileOutputStream.write(bArr);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        this.isRecord = false;
    }

    public String getOutFilePath(Context context) {
        return AudioConfig.getRawFilePath(context);
    }

    public long getRecordFileSize(Context context) {
        return AudioConfig.getFileSize(AudioConfig.getRawFilePath(context));
    }

    public int getVoiceLevel() {
        return this.voiceLevel;
    }

    public void release() {
        stopRecordAndFile();
        ExecutorService executorService = this.mService;
        if (executorService != null) {
            executorService.shutdownNow();
            this.mService = null;
        }
        instance = null;
    }
}
