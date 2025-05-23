package com.baidu.kwgames.example4_characteristic.advanced;
/* compiled from: PresenterEvent.java */
/* loaded from: classes.dex */
class CompatibilityModeEvent implements PresenterEvent {
    final boolean show;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CompatibilityModeEvent(boolean z) {
        this.show = z;
    }

    public String toString() {
        return "CompatibilityModeEvent{show=" + this.show + '}';
    }
}
