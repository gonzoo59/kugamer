package com.baidu.kwgames.example4_characteristic.advanced;

import java.util.Arrays;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PresenterEvent.java */
/* loaded from: classes.dex */
public class ResultEvent implements PresenterEvent {
    final byte[] result;
    final Type type;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ResultEvent(byte[] bArr, Type type) {
        this.result = bArr;
        this.type = type;
    }

    public String toString() {
        return "ResultEvent{type=" + this.type + ", result=" + Arrays.toString(this.result) + '}';
    }
}
