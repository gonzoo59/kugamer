package kotlin.collections;

import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: UArraySorting.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0012\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001d\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\bH\u0001ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001f\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000bH\u0001ø\u0001\u0000¢\u0006\u0004\b \u0010!\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000eH\u0001ø\u0001\u0000¢\u0006\u0004\b\"\u0010#\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006$"}, d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "sortArray-GBYM_sE", "([B)V", "sortArray--ajY-9A", "([I)V", "sortArray-QwZRm1k", "([J)V", "sortArray-rL5Bavg", "([S)V", "kotlin-stdlib"}, k = 2, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class UArraySortingKt {
    /* renamed from: partition-4UcCI2c  reason: not valid java name */
    private static final int m462partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte m209getimpl = UByteArray.m209getimpl(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                i3 = m209getimpl & 255;
                if (Intrinsics.compare(UByteArray.m209getimpl(bArr, i) & 255, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.m209getimpl(bArr, i2) & 255, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte m209getimpl2 = UByteArray.m209getimpl(bArr, i);
                UByteArray.m214setVurrAj0(bArr, i, UByteArray.m209getimpl(bArr, i2));
                UByteArray.m214setVurrAj0(bArr, i2, m209getimpl2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-4UcCI2c  reason: not valid java name */
    private static final void m466quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int m462partition4UcCI2c = m462partition4UcCI2c(bArr, i, i2);
        int i3 = m462partition4UcCI2c - 1;
        if (i < i3) {
            m466quickSort4UcCI2c(bArr, i, i3);
        }
        if (m462partition4UcCI2c < i2) {
            m466quickSort4UcCI2c(bArr, m462partition4UcCI2c, i2);
        }
    }

    /* renamed from: partition-Aa5vz7o  reason: not valid java name */
    private static final int m463partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short m442getimpl = UShortArray.m442getimpl(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int m442getimpl2 = UShortArray.m442getimpl(sArr, i) & UShort.MAX_VALUE;
                i3 = m442getimpl & UShort.MAX_VALUE;
                if (Intrinsics.compare(m442getimpl2, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.m442getimpl(sArr, i2) & UShort.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short m442getimpl3 = UShortArray.m442getimpl(sArr, i);
                UShortArray.m447set01HTLdE(sArr, i, UShortArray.m442getimpl(sArr, i2));
                UShortArray.m447set01HTLdE(sArr, i2, m442getimpl3);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-Aa5vz7o  reason: not valid java name */
    private static final void m467quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int m463partitionAa5vz7o = m463partitionAa5vz7o(sArr, i, i2);
        int i3 = m463partitionAa5vz7o - 1;
        if (i < i3) {
            m467quickSortAa5vz7o(sArr, i, i3);
        }
        if (m463partitionAa5vz7o < i2) {
            m467quickSortAa5vz7o(sArr, m463partitionAa5vz7o, i2);
        }
    }

    /* renamed from: partition-oBK06Vg  reason: not valid java name */
    private static final int m464partitionoBK06Vg(int[] iArr, int i, int i2) {
        int m278getimpl = UIntArray.m278getimpl(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.uintCompare(UIntArray.m278getimpl(iArr, i), m278getimpl) < 0) {
                i++;
            }
            while (UnsignedKt.uintCompare(UIntArray.m278getimpl(iArr, i2), m278getimpl) > 0) {
                i2--;
            }
            if (i <= i2) {
                int m278getimpl2 = UIntArray.m278getimpl(iArr, i);
                UIntArray.m283setVXSXFK8(iArr, i, UIntArray.m278getimpl(iArr, i2));
                UIntArray.m283setVXSXFK8(iArr, i2, m278getimpl2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-oBK06Vg  reason: not valid java name */
    private static final void m468quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int m464partitionoBK06Vg = m464partitionoBK06Vg(iArr, i, i2);
        int i3 = m464partitionoBK06Vg - 1;
        if (i < i3) {
            m468quickSortoBK06Vg(iArr, i, i3);
        }
        if (m464partitionoBK06Vg < i2) {
            m468quickSortoBK06Vg(iArr, m464partitionoBK06Vg, i2);
        }
    }

    /* renamed from: partition--nroSd4  reason: not valid java name */
    private static final int m461partitionnroSd4(long[] jArr, int i, int i2) {
        long m347getimpl = ULongArray.m347getimpl(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.ulongCompare(ULongArray.m347getimpl(jArr, i), m347getimpl) < 0) {
                i++;
            }
            while (UnsignedKt.ulongCompare(ULongArray.m347getimpl(jArr, i2), m347getimpl) > 0) {
                i2--;
            }
            if (i <= i2) {
                long m347getimpl2 = ULongArray.m347getimpl(jArr, i);
                ULongArray.m352setk8EXiF4(jArr, i, ULongArray.m347getimpl(jArr, i2));
                ULongArray.m352setk8EXiF4(jArr, i2, m347getimpl2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort--nroSd4  reason: not valid java name */
    private static final void m465quickSortnroSd4(long[] jArr, int i, int i2) {
        int m461partitionnroSd4 = m461partitionnroSd4(jArr, i, i2);
        int i3 = m461partitionnroSd4 - 1;
        if (i < i3) {
            m465quickSortnroSd4(jArr, i, i3);
        }
        if (m461partitionnroSd4 < i2) {
            m465quickSortnroSd4(jArr, m461partitionnroSd4, i2);
        }
    }

    /* renamed from: sortArray-GBYM_sE  reason: not valid java name */
    public static final void m470sortArrayGBYM_sE(byte[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m466quickSort4UcCI2c(array, 0, UByteArray.m210getSizeimpl(array) - 1);
    }

    /* renamed from: sortArray-rL5Bavg  reason: not valid java name */
    public static final void m472sortArrayrL5Bavg(short[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m467quickSortAa5vz7o(array, 0, UShortArray.m443getSizeimpl(array) - 1);
    }

    /* renamed from: sortArray--ajY-9A  reason: not valid java name */
    public static final void m469sortArrayajY9A(int[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m468quickSortoBK06Vg(array, 0, UIntArray.m279getSizeimpl(array) - 1);
    }

    /* renamed from: sortArray-QwZRm1k  reason: not valid java name */
    public static final void m471sortArrayQwZRm1k(long[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m465quickSortnroSd4(array, 0, ULongArray.m348getSizeimpl(array) - 1);
    }
}
