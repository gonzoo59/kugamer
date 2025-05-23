package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
/* compiled from: Modality.kt */
/* loaded from: classes2.dex */
public final class ModalityKt {
    public static final boolean isFinalClass(ClassDescriptor isFinalClass) {
        Intrinsics.checkParameterIsNotNull(isFinalClass, "$this$isFinalClass");
        return isFinalClass.getModality() == Modality.FINAL && isFinalClass.getKind() != ClassKind.ENUM_CLASS;
    }
}
