package com.baidu.kwgames.example4_characteristic.advanced;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PresenterEvent.java */
/* loaded from: classes.dex */
public class InfoEvent implements PresenterEvent {
    final String infoText;

    /* JADX INFO: Access modifiers changed from: package-private */
    public InfoEvent(String str) {
        this.infoText = str;
    }

    public String toString() {
        return "InfoEvent{infoText='" + this.infoText + "'}";
    }
}
