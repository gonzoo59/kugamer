package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.DynamicTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
/* compiled from: dynamicTypes.kt */
/* loaded from: classes2.dex */
public final class DynamicType extends FlexibleType implements DynamicTypeMarker {
    private final Annotations annotations;

    @Override // kotlin.reflect.jvm.internal.impl.types.FlexibleType, kotlin.reflect.jvm.internal.impl.types.KotlinType
    public boolean isMarkedNullable() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    public DynamicType makeNullableAsSpecified(boolean z) {
        return this;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType, kotlin.reflect.jvm.internal.impl.types.KotlinType
    public DynamicType refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkParameterIsNotNull(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.FlexibleType
    public String render(DescriptorRenderer renderer, DescriptorRendererOptions options) {
        Intrinsics.checkParameterIsNotNull(renderer, "renderer");
        Intrinsics.checkParameterIsNotNull(options, "options");
        return "dynamic";
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.FlexibleType, kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.annotations;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public DynamicType(kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns r3, kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r4) {
        /*
            r2 = this;
            java.lang.String r0 = "builtIns"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r3, r0)
            java.lang.String r0 = "annotations"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r4, r0)
            kotlin.reflect.jvm.internal.impl.types.SimpleType r0 = r3.getNothingType()
            java.lang.String r1 = "builtIns.nothingType"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r1)
            kotlin.reflect.jvm.internal.impl.types.SimpleType r3 = r3.getNullableAnyType()
            java.lang.String r1 = "builtIns.nullableAnyType"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r3, r1)
            r2.<init>(r0, r3)
            r2.annotations = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.DynamicType.<init>(kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns, kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations):void");
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.FlexibleType
    public SimpleType getDelegate() {
        return getUpperBound();
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    public DynamicType replaceAnnotations(Annotations newAnnotations) {
        Intrinsics.checkParameterIsNotNull(newAnnotations, "newAnnotations");
        return new DynamicType(TypeUtilsKt.getBuiltIns(getDelegate()), newAnnotations);
    }
}
