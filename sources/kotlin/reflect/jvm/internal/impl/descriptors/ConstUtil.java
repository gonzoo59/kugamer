package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
/* compiled from: ConstUtil.kt */
/* loaded from: classes2.dex */
public final class ConstUtil {
    public static final ConstUtil INSTANCE = new ConstUtil();

    private ConstUtil() {
    }

    @JvmStatic
    public static final boolean canBeUsedForConstVal(KotlinType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        return ConstUtilKt.canBeUsedForConstVal(type);
    }
}
