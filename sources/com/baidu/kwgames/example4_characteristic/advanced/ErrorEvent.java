package com.baidu.kwgames.example4_characteristic.advanced;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PresenterEvent.java */
/* loaded from: classes.dex */
public class ErrorEvent implements PresenterEvent {
    final Throwable error;
    final Type type;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ErrorEvent(Throwable th, Type type) {
        this.error = th;
        this.type = type;
    }

    public String toString() {
        return "ErrorEvent{type=" + this.type + ", error=" + this.error + '}';
    }
}
