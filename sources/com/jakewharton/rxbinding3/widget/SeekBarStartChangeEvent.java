package com.jakewharton.rxbinding3.widget;

import android.widget.SeekBar;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: SeekBarChangeEvent.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/jakewharton/rxbinding3/widget/SeekBarStartChangeEvent;", "Lcom/jakewharton/rxbinding3/widget/SeekBarChangeEvent;", "view", "Landroid/widget/SeekBar;", "(Landroid/widget/SeekBar;)V", "getView", "()Landroid/widget/SeekBar;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "rxbinding_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class SeekBarStartChangeEvent extends SeekBarChangeEvent {
    private final SeekBar view;

    public static /* synthetic */ SeekBarStartChangeEvent copy$default(SeekBarStartChangeEvent seekBarStartChangeEvent, SeekBar seekBar, int i, Object obj) {
        if ((i & 1) != 0) {
            seekBar = seekBarStartChangeEvent.getView();
        }
        return seekBarStartChangeEvent.copy(seekBar);
    }

    public final SeekBar component1() {
        return getView();
    }

    public final SeekBarStartChangeEvent copy(SeekBar view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        return new SeekBarStartChangeEvent(view);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            return (obj instanceof SeekBarStartChangeEvent) && Intrinsics.areEqual(getView(), ((SeekBarStartChangeEvent) obj).getView());
        }
        return true;
    }

    public int hashCode() {
        SeekBar view = getView();
        if (view != null) {
            return view.hashCode();
        }
        return 0;
    }

    public String toString() {
        return "SeekBarStartChangeEvent(view=" + getView() + ")";
    }

    @Override // com.jakewharton.rxbinding3.widget.SeekBarChangeEvent
    public SeekBar getView() {
        return this.view;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SeekBarStartChangeEvent(SeekBar view) {
        super(null);
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.view = view;
    }
}
