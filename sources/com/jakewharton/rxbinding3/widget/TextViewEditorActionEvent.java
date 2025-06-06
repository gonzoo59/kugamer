package com.jakewharton.rxbinding3.widget;

import android.view.KeyEvent;
import android.widget.TextView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: TextViewEditorActionEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lcom/jakewharton/rxbinding3/widget/TextViewEditorActionEvent;", "", "view", "Landroid/widget/TextView;", "actionId", "", "keyEvent", "Landroid/view/KeyEvent;", "(Landroid/widget/TextView;ILandroid/view/KeyEvent;)V", "getActionId", "()I", "getKeyEvent", "()Landroid/view/KeyEvent;", "getView", "()Landroid/widget/TextView;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "rxbinding_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class TextViewEditorActionEvent {
    private final int actionId;
    private final KeyEvent keyEvent;
    private final TextView view;

    public static /* synthetic */ TextViewEditorActionEvent copy$default(TextViewEditorActionEvent textViewEditorActionEvent, TextView textView, int i, KeyEvent keyEvent, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            textView = textViewEditorActionEvent.view;
        }
        if ((i2 & 2) != 0) {
            i = textViewEditorActionEvent.actionId;
        }
        if ((i2 & 4) != 0) {
            keyEvent = textViewEditorActionEvent.keyEvent;
        }
        return textViewEditorActionEvent.copy(textView, i, keyEvent);
    }

    public final TextView component1() {
        return this.view;
    }

    public final int component2() {
        return this.actionId;
    }

    public final KeyEvent component3() {
        return this.keyEvent;
    }

    public final TextViewEditorActionEvent copy(TextView view, int i, KeyEvent keyEvent) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        return new TextViewEditorActionEvent(view, i, keyEvent);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof TextViewEditorActionEvent) {
                TextViewEditorActionEvent textViewEditorActionEvent = (TextViewEditorActionEvent) obj;
                if (Intrinsics.areEqual(this.view, textViewEditorActionEvent.view)) {
                    if (!(this.actionId == textViewEditorActionEvent.actionId) || !Intrinsics.areEqual(this.keyEvent, textViewEditorActionEvent.keyEvent)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        TextView textView = this.view;
        int hashCode = (((textView != null ? textView.hashCode() : 0) * 31) + this.actionId) * 31;
        KeyEvent keyEvent = this.keyEvent;
        return hashCode + (keyEvent != null ? keyEvent.hashCode() : 0);
    }

    public String toString() {
        return "TextViewEditorActionEvent(view=" + this.view + ", actionId=" + this.actionId + ", keyEvent=" + this.keyEvent + ")";
    }

    public TextViewEditorActionEvent(TextView view, int i, KeyEvent keyEvent) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.view = view;
        this.actionId = i;
        this.keyEvent = keyEvent;
    }

    public final TextView getView() {
        return this.view;
    }

    public final int getActionId() {
        return this.actionId;
    }

    public final KeyEvent getKeyEvent() {
        return this.keyEvent;
    }
}
