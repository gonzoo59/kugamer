package com.jakewharton.rxbinding3.widget;

import android.widget.TextView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: TextViewBeforeTextChangeEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0007HÆ\u0003J;\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u0007HÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0011\u0010\t\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001f"}, d2 = {"Lcom/jakewharton/rxbinding3/widget/TextViewBeforeTextChangeEvent;", "", "view", "Landroid/widget/TextView;", "text", "", "start", "", "count", "after", "(Landroid/widget/TextView;Ljava/lang/CharSequence;III)V", "getAfter", "()I", "getCount", "getStart", "getText", "()Ljava/lang/CharSequence;", "getView", "()Landroid/widget/TextView;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "", "rxbinding_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class TextViewBeforeTextChangeEvent {
    private final int after;
    private final int count;
    private final int start;
    private final CharSequence text;
    private final TextView view;

    public static /* synthetic */ TextViewBeforeTextChangeEvent copy$default(TextViewBeforeTextChangeEvent textViewBeforeTextChangeEvent, TextView textView, CharSequence charSequence, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            textView = textViewBeforeTextChangeEvent.view;
        }
        if ((i4 & 2) != 0) {
            charSequence = textViewBeforeTextChangeEvent.text;
        }
        CharSequence charSequence2 = charSequence;
        if ((i4 & 4) != 0) {
            i = textViewBeforeTextChangeEvent.start;
        }
        int i5 = i;
        if ((i4 & 8) != 0) {
            i2 = textViewBeforeTextChangeEvent.count;
        }
        int i6 = i2;
        if ((i4 & 16) != 0) {
            i3 = textViewBeforeTextChangeEvent.after;
        }
        return textViewBeforeTextChangeEvent.copy(textView, charSequence2, i5, i6, i3);
    }

    public final TextView component1() {
        return this.view;
    }

    public final CharSequence component2() {
        return this.text;
    }

    public final int component3() {
        return this.start;
    }

    public final int component4() {
        return this.count;
    }

    public final int component5() {
        return this.after;
    }

    public final TextViewBeforeTextChangeEvent copy(TextView view, CharSequence text, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(text, "text");
        return new TextViewBeforeTextChangeEvent(view, text, i, i2, i3);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof TextViewBeforeTextChangeEvent) {
                TextViewBeforeTextChangeEvent textViewBeforeTextChangeEvent = (TextViewBeforeTextChangeEvent) obj;
                if (Intrinsics.areEqual(this.view, textViewBeforeTextChangeEvent.view) && Intrinsics.areEqual(this.text, textViewBeforeTextChangeEvent.text)) {
                    if (this.start == textViewBeforeTextChangeEvent.start) {
                        if (this.count == textViewBeforeTextChangeEvent.count) {
                            if (this.after == textViewBeforeTextChangeEvent.after) {
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        TextView textView = this.view;
        int hashCode = (textView != null ? textView.hashCode() : 0) * 31;
        CharSequence charSequence = this.text;
        return ((((((hashCode + (charSequence != null ? charSequence.hashCode() : 0)) * 31) + this.start) * 31) + this.count) * 31) + this.after;
    }

    public String toString() {
        return "TextViewBeforeTextChangeEvent(view=" + this.view + ", text=" + this.text + ", start=" + this.start + ", count=" + this.count + ", after=" + this.after + ")";
    }

    public TextViewBeforeTextChangeEvent(TextView view, CharSequence text, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(text, "text");
        this.view = view;
        this.text = text;
        this.start = i;
        this.count = i2;
        this.after = i3;
    }

    public final TextView getView() {
        return this.view;
    }

    public final CharSequence getText() {
        return this.text;
    }

    public final int getStart() {
        return this.start;
    }

    public final int getCount() {
        return this.count;
    }

    public final int getAfter() {
        return this.after;
    }
}
