package com.jieli.component.audio;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import com.jieli.component.Logcat;
import java.io.IOException;
/* loaded from: classes2.dex */
public class ShortAudioPlayer {
    private static volatile ShortAudioPlayer mInstance;
    private String TAG = "ShortAudioPlayer";
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() { // from class: com.jieli.component.audio.ShortAudioPlayer.1
        @Override // android.media.MediaPlayer.OnCompletionListener
        public void onCompletion(MediaPlayer mediaPlayer) {
        }
    };
    private MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() { // from class: com.jieli.component.audio.ShortAudioPlayer.2
        @Override // android.media.MediaPlayer.OnErrorListener
        public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
            Logcat.e(ShortAudioPlayer.this.TAG, "---------------------mOnErrorListener------------------");
            return false;
        }
    };
    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() { // from class: com.jieli.component.audio.ShortAudioPlayer.3
        @Override // android.media.MediaPlayer.OnPreparedListener
        public void onPrepared(MediaPlayer mediaPlayer) {
            Logcat.e(ShortAudioPlayer.this.TAG, "---------------------mOnPreparedListener------------------");
            mediaPlayer.start();
        }
    };
    private MediaPlayer mediaPlayer;

    private ShortAudioPlayer() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mediaPlayer = mediaPlayer;
        mediaPlayer.setOnPreparedListener(this.mOnPreparedListener);
        this.mediaPlayer.setOnCompletionListener(this.mOnCompletionListener);
        this.mediaPlayer.setOnErrorListener(this.mOnErrorListener);
    }

    public static ShortAudioPlayer getInstance() {
        if (mInstance == null) {
            synchronized (ShortAudioPlayer.class) {
                if (mInstance == null) {
                    mInstance = new ShortAudioPlayer();
                }
            }
        }
        return mInstance;
    }

    public void play(String str) {
        try {
            this.mediaPlayer.reset();
            this.mediaPlayer.setDataSource(str);
            this.mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(AssetFileDescriptor assetFileDescriptor) {
        try {
            this.mediaPlayer.reset();
            if (Build.VERSION.SDK_INT >= 24) {
                this.mediaPlayer.setDataSource(assetFileDescriptor);
            } else {
                this.mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            }
            this.mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        this.mediaPlayer.start();
    }

    public void pause() {
        this.mediaPlayer.pause();
    }

    public void stop() {
        this.mediaPlayer.stop();
    }
}
