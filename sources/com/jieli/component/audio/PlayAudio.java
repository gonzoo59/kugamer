package com.jieli.component.audio;

import android.media.AudioTrack;
import com.jieli.component.Logcat;
/* loaded from: classes2.dex */
public class PlayAudio extends Thread {
    private byte[] buf;
    private int bufferSize;
    private volatile boolean isPlay;
    private AudioTrack mAudioTrack;
    private PlayAudioListener mListener;

    public PlayAudio(byte[] bArr, PlayAudioListener playAudioListener) {
        this.buf = bArr;
        this.mListener = playAudioListener;
        createAudioPlayer();
    }

    public void createAudioPlayer() {
        try {
            AudioConfig audioConfig = new AudioConfig();
            int minBufferSize = AudioTrack.getMinBufferSize(audioConfig.getAudioSampleRate(), audioConfig.getOutputChannel(), audioConfig.getAudioFormat());
            this.bufferSize = minBufferSize;
            if (minBufferSize != -2) {
                this.mAudioTrack = new AudioTrack(3, audioConfig.getAudioSampleRate(), audioConfig.getOutputChannel(), audioConfig.getAudioFormat(), this.bufferSize, 1);
            }
            AudioTrack audioTrack = this.mAudioTrack;
            if (audioTrack == null || audioTrack.getState() != 1) {
                return;
            }
            this.mAudioTrack.setStereoVolume(AudioTrack.getMaxVolume(), AudioTrack.getMaxVolume());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override // java.lang.Thread
    public synchronized void start() {
        this.isPlay = true;
        super.start();
    }

    public void stopPlay() {
        this.isPlay = false;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        super.run();
        if (this.buf == null || this.mAudioTrack == null) {
            return;
        }
        Logcat.e("PlayAudio", "buf size = " + this.buf.length);
        if (this.mAudioTrack.getPlayState() != 3) {
            this.mAudioTrack.play();
            PlayAudioListener playAudioListener = this.mListener;
            if (playAudioListener != null) {
                playAudioListener.playAudioStart();
            }
        }
        int i = 0;
        int i2 = this.bufferSize * 2;
        while (this.isPlay) {
            try {
                this.mAudioTrack.write(this.buf, i, i2);
                i += i2;
            } catch (Exception e) {
                e.printStackTrace();
                PlayAudioListener playAudioListener2 = this.mListener;
                if (playAudioListener2 != null) {
                    playAudioListener2.playAudioError(e.hashCode(), e.getMessage());
                }
            }
            if (i >= this.buf.length) {
                break;
            }
        }
        PlayAudioListener playAudioListener3 = this.mListener;
        if (playAudioListener3 != null) {
            playAudioListener3.playAudioComplete();
        }
        this.mAudioTrack.stop();
        this.mAudioTrack.release();
        this.mAudioTrack = null;
    }
}
