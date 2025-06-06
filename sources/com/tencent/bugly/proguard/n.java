package com.tencent.bugly.proguard;

import com.baidu.kwgames.Constants;
import java.nio.ByteBuffer;
/* compiled from: BUGLY */
/* loaded from: classes2.dex */
public final class n {
    private static final byte[] a;
    private static final byte[] b;

    public static boolean a(int i, int i2) {
        return i == i2;
    }

    public static boolean a(long j, long j2) {
        return j == j2;
    }

    public static boolean a(boolean z, boolean z2) {
        return z == z2;
    }

    public static boolean a(Object obj, Object obj2) {
        return obj.equals(obj2);
    }

    public static byte[] a(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        byte[] bArr = new byte[position];
        System.arraycopy(byteBuffer.array(), 0, bArr, 0, position);
        return bArr;
    }

    static {
        byte[] bArr = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, Constants.KEY_F10, Constants.KEY_F11, Constants.KEY_F12, Constants.KEY_SYSRQ};
        byte[] bArr2 = new byte[256];
        byte[] bArr3 = new byte[256];
        for (int i = 0; i < 256; i++) {
            bArr2[i] = bArr[i >>> 4];
            bArr3[i] = bArr[i & 15];
        }
        a = bArr2;
        b = bArr3;
    }
}
