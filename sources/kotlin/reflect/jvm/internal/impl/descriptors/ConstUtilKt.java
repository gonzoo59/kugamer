package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.UnsignedTypes;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
/* compiled from: ConstUtil.kt */
/* loaded from: classes2.dex */
public final class ConstUtilKt {
    public static final boolean canBeUsedForConstVal(KotlinType canBeUsedForConstVal) {
        Intrinsics.checkParameterIsNotNull(canBeUsedForConstVal, "$this$canBeUsedForConstVal");
        return ((KotlinBuiltIns.isPrimitiveType(canBeUsedForConstVal) || UnsignedTypes.INSTANCE.isUnsignedType(canBeUsedForConstVal)) && !TypeUtils.isNullableType(canBeUsedForConstVal)) || KotlinBuiltIns.isString(canBeUsedForConstVal);
    }
}
