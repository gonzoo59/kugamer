package kotlin.sequences;

import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: _USequences.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"}, d2 = {"sum", "Lkotlin/UInt;", "Lkotlin/sequences/Sequence;", "Lkotlin/UByte;", "sumOfUByte", "(Lkotlin/sequences/Sequence;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Lkotlin/sequences/Sequence;)J", "Lkotlin/UShort;", "sumOfUShort", "kotlin-stdlib"}, k = 5, mv = {1, 1, 16}, xi = 1, xs = "kotlin/sequences/USequencesKt")
/* loaded from: classes2.dex */
class USequencesKt___USequencesKt {
    public static final int sumOfUInt(Sequence<UInt> sum) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        int i = 0;
        for (UInt uInt : sum) {
            i = UInt.m227constructorimpl(i + uInt.m270unboximpl());
        }
        return i;
    }

    public static final long sumOfULong(Sequence<ULong> sum) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        long j = 0;
        for (ULong uLong : sum) {
            j = ULong.m296constructorimpl(j + uLong.m339unboximpl());
        }
        return j;
    }

    public static final int sumOfUByte(Sequence<UByte> sum) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        int i = 0;
        for (UByte uByte : sum) {
            i = UInt.m227constructorimpl(i + UInt.m227constructorimpl(uByte.m201unboximpl() & 255));
        }
        return i;
    }

    public static final int sumOfUShort(Sequence<UShort> sum) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        int i = 0;
        for (UShort uShort : sum) {
            i = UInt.m227constructorimpl(i + UInt.m227constructorimpl(uShort.m434unboximpl() & UShort.MAX_VALUE));
        }
        return i;
    }
}
