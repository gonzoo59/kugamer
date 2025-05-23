package kotlin.reflect.jvm.internal.impl.types;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
/* compiled from: TypeWithEnhancement.kt */
/* loaded from: classes2.dex */
public final class SimpleTypeWithEnhancement extends DelegatingSimpleType implements TypeWithEnhancement {
    private final SimpleType delegate;
    private final KotlinType enhancement;

    @Override // kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType
    protected SimpleType getDelegate() {
        return this.delegate;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancement
    public KotlinType getEnhancement() {
        return this.enhancement;
    }

    public SimpleTypeWithEnhancement(SimpleType delegate, KotlinType enhancement) {
        Intrinsics.checkParameterIsNotNull(delegate, "delegate");
        Intrinsics.checkParameterIsNotNull(enhancement, "enhancement");
        this.delegate = delegate;
        this.enhancement = enhancement;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancement
    public UnwrappedType getOrigin() {
        return getDelegate();
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    public SimpleType replaceAnnotations(Annotations newAnnotations) {
        Intrinsics.checkParameterIsNotNull(newAnnotations, "newAnnotations");
        UnwrappedType wrapEnhancement = TypeWithEnhancementKt.wrapEnhancement(getOrigin().replaceAnnotations(newAnnotations), getEnhancement());
        if (wrapEnhancement != null) {
            return (SimpleType) wrapEnhancement;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    public SimpleType makeNullableAsSpecified(boolean z) {
        UnwrappedType wrapEnhancement = TypeWithEnhancementKt.wrapEnhancement(getOrigin().makeNullableAsSpecified(z), getEnhancement().unwrap().makeNullableAsSpecified(z));
        if (wrapEnhancement != null) {
            return (SimpleType) wrapEnhancement;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType
    public SimpleTypeWithEnhancement replaceDelegate(SimpleType delegate) {
        Intrinsics.checkParameterIsNotNull(delegate, "delegate");
        return new SimpleTypeWithEnhancement(delegate, getEnhancement());
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType, kotlin.reflect.jvm.internal.impl.types.UnwrappedType, kotlin.reflect.jvm.internal.impl.types.KotlinType
    public SimpleTypeWithEnhancement refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkParameterIsNotNull(kotlinTypeRefiner, "kotlinTypeRefiner");
        KotlinType refineType = kotlinTypeRefiner.refineType(getDelegate());
        if (refineType != null) {
            return new SimpleTypeWithEnhancement((SimpleType) refineType, kotlinTypeRefiner.refineType(getEnhancement()));
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
    }
}
