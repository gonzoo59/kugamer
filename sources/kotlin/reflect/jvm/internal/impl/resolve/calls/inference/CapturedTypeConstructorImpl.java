package kotlin.reflect.jvm.internal.impl.resolve.calls.inference;

import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedTypeConstructor;
/* compiled from: CapturedTypeConstructor.kt */
/* loaded from: classes2.dex */
public final class CapturedTypeConstructorImpl implements CapturedTypeConstructor {
    private NewCapturedTypeConstructor newTypeConstructor;
    private final TypeProjection projection;

    public Void getDeclarationDescriptor() {
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    public boolean isDenotable() {
        return false;
    }

    public CapturedTypeConstructorImpl(TypeProjection projection) {
        Intrinsics.checkParameterIsNotNull(projection, "projection");
        this.projection = projection;
        getProjection().getProjectionKind();
        Variance variance = Variance.INVARIANT;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    /* renamed from: getDeclarationDescriptor  reason: collision with other method in class */
    public /* bridge */ /* synthetic */ ClassifierDescriptor mo1092getDeclarationDescriptor() {
        return (ClassifierDescriptor) getDeclarationDescriptor();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructor
    public TypeProjection getProjection() {
        return this.projection;
    }

    public final NewCapturedTypeConstructor getNewTypeConstructor() {
        return this.newTypeConstructor;
    }

    public final void setNewTypeConstructor(NewCapturedTypeConstructor newCapturedTypeConstructor) {
        this.newTypeConstructor = newCapturedTypeConstructor;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    public List<TypeParameterDescriptor> getParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    /* renamed from: getSupertypes */
    public Collection<KotlinType> mo1093getSupertypes() {
        SimpleType nullableAnyType;
        if (getProjection().getProjectionKind() == Variance.OUT_VARIANCE) {
            nullableAnyType = getProjection().getType();
        } else {
            nullableAnyType = getBuiltIns().getNullableAnyType();
        }
        Intrinsics.checkExpressionValueIsNotNull(nullableAnyType, "if (projection.projectio… builtIns.nullableAnyType");
        return CollectionsKt.listOf(nullableAnyType);
    }

    public String toString() {
        return "CapturedTypeConstructor(" + getProjection() + ')';
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    public KotlinBuiltIns getBuiltIns() {
        KotlinBuiltIns builtIns = getProjection().getType().getConstructor().getBuiltIns();
        Intrinsics.checkExpressionValueIsNotNull(builtIns, "projection.type.constructor.builtIns");
        return builtIns;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    public CapturedTypeConstructorImpl refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkParameterIsNotNull(kotlinTypeRefiner, "kotlinTypeRefiner");
        TypeProjection refine = getProjection().refine(kotlinTypeRefiner);
        Intrinsics.checkExpressionValueIsNotNull(refine, "projection.refine(kotlinTypeRefiner)");
        return new CapturedTypeConstructorImpl(refine);
    }
}
