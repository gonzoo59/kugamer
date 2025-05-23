package com.jieli.component.listeners;

import android.widget.SeekBar;
/* loaded from: classes2.dex */
public abstract class OnSeekBarChangeListerAdapter implements SeekBar.OnSeekBarChangeListener {
    public abstract void onProgressChangeByUser(SeekBar seekBar, int i, boolean z);

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            onProgressChangeByUser(seekBar, i, false);
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        onProgressChangeByUser(seekBar, seekBar.getProgress(), true);
    }
}
