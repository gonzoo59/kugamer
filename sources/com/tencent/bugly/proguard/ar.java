package com.tencent.bugly.proguard;
/* compiled from: BUGLY */
/* loaded from: classes2.dex */
public final class ar implements Comparable<ar> {
    public long a = -1;
    public long b = -1;
    public String c = null;
    public boolean d = false;
    public boolean e = false;
    public int f = 0;

    @Override // java.lang.Comparable
    public final /* bridge */ /* synthetic */ int compareTo(ar arVar) {
        int i;
        ar arVar2 = arVar;
        if (arVar2 == null || this.b - arVar2.b > 0) {
            return 1;
        }
        return i < 0 ? -1 : 0;
    }
}
