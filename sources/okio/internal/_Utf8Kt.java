package okio.internal;

import com.baidu.kwgames.Constants;
import com.jieli.jl_bt_ota.constant.Command;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: -Utf8.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0002*\u00020\u0001¨\u0006\u0004"}, d2 = {"commonAsUtf8ToByteArray", "", "", "commonToUtf8String", "jvm"}, k = 2, mv = {1, 1, 11})
/* loaded from: classes2.dex */
public final class _Utf8Kt {
    /* JADX WARN: Code restructure failed: missing block: B:40:0x008d, code lost:
        if (((r16[r5] & com.baidu.kwgames.Constants.KEY_MOBA_CTRL_Q) == 128) == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0105, code lost:
        if (((r16[r5] & com.baidu.kwgames.Constants.KEY_MOBA_CTRL_Q) == 128) == false) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.String commonToUtf8String(byte[] r16) {
        /*
            Method dump skipped, instructions count: 416
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal._Utf8Kt.commonToUtf8String(byte[]):java.lang.String");
    }

    public static final byte[] commonAsUtf8ToByteArray(String receiver) {
        int i;
        int i2;
        char charAt;
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        byte[] bArr = new byte[receiver.length() * 4];
        int length = receiver.length();
        int i3 = 0;
        while (i3 < length) {
            char charAt2 = receiver.charAt(i3);
            if (charAt2 >= 128) {
                int length2 = receiver.length();
                int i4 = i3;
                while (i3 < length2) {
                    char charAt3 = receiver.charAt(i3);
                    if (charAt3 < 128) {
                        int i5 = i4 + 1;
                        bArr[i4] = (byte) charAt3;
                        i3++;
                        while (i3 < length2 && receiver.charAt(i3) < 128) {
                            bArr[i5] = (byte) receiver.charAt(i3);
                            i3++;
                            i5++;
                        }
                        i4 = i5;
                    } else {
                        if (charAt3 < 2048) {
                            int i6 = i4 + 1;
                            bArr[i4] = (byte) ((charAt3 >> 6) | Constants.E_SCOPE_MASK_68X);
                            i = i6 + 1;
                            bArr[i6] = (byte) ((charAt3 & '?') | 128);
                        } else if (55296 > charAt3 || 57343 < charAt3) {
                            int i7 = i4 + 1;
                            bArr[i4] = (byte) ((charAt3 >> '\f') | 224);
                            int i8 = i7 + 1;
                            bArr[i7] = (byte) (((charAt3 >> 6) & 63) | 128);
                            i = i8 + 1;
                            bArr[i8] = (byte) ((charAt3 & '?') | 128);
                        } else if (charAt3 > 56319 || length2 <= (i2 = i3 + 1) || 56320 > (charAt = receiver.charAt(i2)) || 57343 < charAt) {
                            i = i4 + 1;
                            bArr[i4] = 63;
                        } else {
                            int charAt4 = ((charAt3 << '\n') + receiver.charAt(i2)) - 56613888;
                            int i9 = i4 + 1;
                            bArr[i4] = (byte) ((charAt4 >> 18) | Command.CMD_CUSTOM);
                            int i10 = i9 + 1;
                            bArr[i9] = (byte) (((charAt4 >> 12) & 63) | 128);
                            int i11 = i10 + 1;
                            bArr[i10] = (byte) (((charAt4 >> 6) & 63) | 128);
                            i = i11 + 1;
                            bArr[i11] = (byte) ((charAt4 & 63) | 128);
                            i3 += 2;
                            i4 = i;
                        }
                        i3++;
                        i4 = i;
                    }
                }
                byte[] copyOf = Arrays.copyOf(bArr, i4);
                Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, newSize)");
                return copyOf;
            }
            bArr[i3] = (byte) charAt2;
            i3++;
        }
        byte[] copyOf2 = Arrays.copyOf(bArr, receiver.length());
        Intrinsics.checkExpressionValueIsNotNull(copyOf2, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf2;
    }
}
