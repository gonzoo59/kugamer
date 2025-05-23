package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
/* compiled from: dynamicTypes.kt */
/* loaded from: classes2.dex */
public final class DynamicTypesKt {
    public static final boolean isDynamic(KotlinType isDynamic) {
        Intrinsics.checkParameterIsNotNull(isDynamic, "$this$isDynamic");
        return isDynamic.unwrap() instanceof DynamicType;
    }
}
