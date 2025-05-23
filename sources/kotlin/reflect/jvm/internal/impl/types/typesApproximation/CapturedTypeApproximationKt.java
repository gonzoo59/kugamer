package kotlin.reflect.jvm.internal.impl.types.typesApproximation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
/* compiled from: CapturedTypeApproximation.kt */
/* loaded from: classes2.dex */
public final class CapturedTypeApproximationKt {

    /* loaded from: classes2.dex */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Variance.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[Variance.INVARIANT.ordinal()] = 1;
            iArr[Variance.IN_VARIANCE.ordinal()] = 2;
            iArr[Variance.OUT_VARIANCE.ordinal()] = 3;
            int[] iArr2 = new int[Variance.values().length];
            $EnumSwitchMapping$1 = iArr2;
            iArr2[Variance.IN_VARIANCE.ordinal()] = 1;
            iArr2[Variance.OUT_VARIANCE.ordinal()] = 2;
        }
    }

    private static final TypeProjection toTypeProjection(final TypeArgument typeArgument) {
        typeArgument.isConsistent();
        Function1<Variance, Variance> function1 = new Function1<Variance, Variance>() { // from class: kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt$toTypeProjection$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Variance invoke(Variance variance) {
                Intrinsics.checkParameterIsNotNull(variance, "variance");
                return variance == TypeArgument.this.getTypeParameter().getVariance() ? Variance.INVARIANT : variance;
            }
        };
        if (Intrinsics.areEqual(typeArgument.getInProjection(), typeArgument.getOutProjection())) {
            return new TypeProjectionImpl(typeArgument.getInProjection());
        }
        if (!KotlinBuiltIns.isNothing(typeArgument.getInProjection()) || typeArgument.getTypeParameter().getVariance() == Variance.IN_VARIANCE) {
            return KotlinBuiltIns.isNullableAny(typeArgument.getOutProjection()) ? new TypeProjectionImpl(function1.invoke(Variance.IN_VARIANCE), typeArgument.getInProjection()) : new TypeProjectionImpl(function1.invoke(Variance.OUT_VARIANCE), typeArgument.getOutProjection());
        }
        return new TypeProjectionImpl(function1.invoke(Variance.OUT_VARIANCE), typeArgument.getOutProjection());
    }

    private static final TypeArgument toTypeArgument(TypeProjection typeProjection, TypeParameterDescriptor typeParameterDescriptor) {
        int i = WhenMappings.$EnumSwitchMapping$0[TypeSubstitutor.combine(typeParameterDescriptor.getVariance(), typeProjection).ordinal()];
        if (i == 1) {
            KotlinType type = typeProjection.getType();
            Intrinsics.checkExpressionValueIsNotNull(type, "type");
            KotlinType type2 = typeProjection.getType();
            Intrinsics.checkExpressionValueIsNotNull(type2, "type");
            return new TypeArgument(typeParameterDescriptor, type, type2);
        } else if (i == 2) {
            KotlinType type3 = typeProjection.getType();
            Intrinsics.checkExpressionValueIsNotNull(type3, "type");
            SimpleType nullableAnyType = DescriptorUtilsKt.getBuiltIns(typeParameterDescriptor).getNullableAnyType();
            Intrinsics.checkExpressionValueIsNotNull(nullableAnyType, "typeParameter.builtIns.nullableAnyType");
            return new TypeArgument(typeParameterDescriptor, type3, nullableAnyType);
        } else if (i == 3) {
            SimpleType nothingType = DescriptorUtilsKt.getBuiltIns(typeParameterDescriptor).getNothingType();
            Intrinsics.checkExpressionValueIsNotNull(nothingType, "typeParameter.builtIns.nothingType");
            KotlinType type4 = typeProjection.getType();
            Intrinsics.checkExpressionValueIsNotNull(type4, "type");
            return new TypeArgument(typeParameterDescriptor, nothingType, type4);
        } else {
            throw new NoWhenBranchMatchedException();
        }
    }

    public static final TypeProjection approximateCapturedTypesIfNecessary(TypeProjection typeProjection, boolean z) {
        if (typeProjection == null) {
            return null;
        }
        if (typeProjection.isStarProjection()) {
            return typeProjection;
        }
        KotlinType type = typeProjection.getType();
        Intrinsics.checkExpressionValueIsNotNull(type, "typeProjection.type");
        if (TypeUtils.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt$approximateCapturedTypesIfNecessary$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(UnwrappedType unwrappedType) {
                return Boolean.valueOf(invoke2(unwrappedType));
            }

            /* renamed from: invoke  reason: avoid collision after fix types in other method */
            public final boolean invoke2(UnwrappedType it) {
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                return CapturedTypeConstructorKt.isCaptured(it);
            }
        })) {
            Variance projectionKind = typeProjection.getProjectionKind();
            Intrinsics.checkExpressionValueIsNotNull(projectionKind, "typeProjection.projectionKind");
            if (projectionKind == Variance.OUT_VARIANCE) {
                return new TypeProjectionImpl(projectionKind, approximateCapturedTypes(type).getUpper());
            }
            if (z) {
                return new TypeProjectionImpl(projectionKind, approximateCapturedTypes(type).getLower());
            }
            return substituteCapturedTypesWithProjections(typeProjection);
        }
        return typeProjection;
    }

    private static final TypeProjection substituteCapturedTypesWithProjections(TypeProjection typeProjection) {
        TypeSubstitutor create = TypeSubstitutor.create(new TypeConstructorSubstitution() { // from class: kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt$substituteCapturedTypesWithProjections$typeSubstitutor$1
            @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution
            public TypeProjection get(TypeConstructor key) {
                Intrinsics.checkParameterIsNotNull(key, "key");
                if (!(key instanceof CapturedTypeConstructor)) {
                    key = null;
                }
                CapturedTypeConstructor capturedTypeConstructor = (CapturedTypeConstructor) key;
                if (capturedTypeConstructor != null) {
                    if (capturedTypeConstructor.getProjection().isStarProjection()) {
                        return new TypeProjectionImpl(Variance.OUT_VARIANCE, capturedTypeConstructor.getProjection().getType());
                    }
                    return capturedTypeConstructor.getProjection();
                }
                return null;
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(create, "TypeSubstitutor.create(o…ojection\n        }\n    })");
        return create.substituteWithoutApproximation(typeProjection);
    }

    public static final ApproximationBounds<KotlinType> approximateCapturedTypes(final KotlinType type) {
        SimpleType replaceTypeArguments;
        Intrinsics.checkParameterIsNotNull(type, "type");
        if (FlexibleTypesKt.isFlexible(type)) {
            ApproximationBounds<KotlinType> approximateCapturedTypes = approximateCapturedTypes(FlexibleTypesKt.lowerIfFlexible(type));
            ApproximationBounds<KotlinType> approximateCapturedTypes2 = approximateCapturedTypes(FlexibleTypesKt.upperIfFlexible(type));
            return new ApproximationBounds<>(TypeWithEnhancementKt.inheritEnhancement(KotlinTypeFactory.flexibleType(FlexibleTypesKt.lowerIfFlexible(approximateCapturedTypes.getLower()), FlexibleTypesKt.upperIfFlexible(approximateCapturedTypes2.getLower())), type), TypeWithEnhancementKt.inheritEnhancement(KotlinTypeFactory.flexibleType(FlexibleTypesKt.lowerIfFlexible(approximateCapturedTypes.getUpper()), FlexibleTypesKt.upperIfFlexible(approximateCapturedTypes2.getUpper())), type));
        }
        TypeConstructor constructor = type.getConstructor();
        boolean z = true;
        if (CapturedTypeConstructorKt.isCaptured(type)) {
            if (constructor != null) {
                TypeProjection projection = ((CapturedTypeConstructor) constructor).getProjection();
                Function1<KotlinType, KotlinType> function1 = new Function1<KotlinType, KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt$approximateCapturedTypes$1
                    /* JADX INFO: Access modifiers changed from: package-private */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final KotlinType invoke(KotlinType makeNullableIfNeeded) {
                        Intrinsics.checkParameterIsNotNull(makeNullableIfNeeded, "$this$makeNullableIfNeeded");
                        KotlinType makeNullableIfNeeded2 = TypeUtils.makeNullableIfNeeded(makeNullableIfNeeded, KotlinType.this.isMarkedNullable());
                        Intrinsics.checkExpressionValueIsNotNull(makeNullableIfNeeded2, "TypeUtils.makeNullableIf…s, type.isMarkedNullable)");
                        return makeNullableIfNeeded2;
                    }
                };
                KotlinType type2 = projection.getType();
                Intrinsics.checkExpressionValueIsNotNull(type2, "typeProjection.type");
                KotlinType invoke = function1.invoke(type2);
                int i = WhenMappings.$EnumSwitchMapping$1[projection.getProjectionKind().ordinal()];
                if (i == 1) {
                    SimpleType nullableAnyType = TypeUtilsKt.getBuiltIns(type).getNullableAnyType();
                    Intrinsics.checkExpressionValueIsNotNull(nullableAnyType, "type.builtIns.nullableAnyType");
                    return new ApproximationBounds<>(invoke, nullableAnyType);
                } else if (i == 2) {
                    SimpleType nothingType = TypeUtilsKt.getBuiltIns(type).getNothingType();
                    Intrinsics.checkExpressionValueIsNotNull(nothingType, "type.builtIns.nothingType");
                    return new ApproximationBounds<>(function1.invoke((KotlinType) nothingType), invoke);
                } else {
                    throw new AssertionError("Only nontrivial projections should have been captured, not: " + projection);
                }
            }
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructor");
        } else if (type.getArguments().isEmpty() || type.getArguments().size() != constructor.getParameters().size()) {
            return new ApproximationBounds<>(type, type);
        } else {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            List<TypeParameterDescriptor> parameters = constructor.getParameters();
            Intrinsics.checkExpressionValueIsNotNull(parameters, "typeConstructor.parameters");
            for (Pair pair : CollectionsKt.zip(type.getArguments(), parameters)) {
                TypeProjection typeProjection = (TypeProjection) pair.component1();
                TypeParameterDescriptor typeParameter = (TypeParameterDescriptor) pair.component2();
                Intrinsics.checkExpressionValueIsNotNull(typeParameter, "typeParameter");
                TypeArgument typeArgument = toTypeArgument(typeProjection, typeParameter);
                if (typeProjection.isStarProjection()) {
                    arrayList.add(typeArgument);
                    arrayList2.add(typeArgument);
                } else {
                    ApproximationBounds<TypeArgument> approximateProjection = approximateProjection(typeArgument);
                    arrayList.add(approximateProjection.component1());
                    arrayList2.add(approximateProjection.component2());
                }
            }
            ArrayList<TypeArgument> arrayList3 = arrayList;
            if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                for (TypeArgument typeArgument2 : arrayList3) {
                    if (!typeArgument2.isConsistent()) {
                        break;
                    }
                }
            }
            z = false;
            if (z) {
                SimpleType nothingType2 = TypeUtilsKt.getBuiltIns(type).getNothingType();
                Intrinsics.checkExpressionValueIsNotNull(nothingType2, "type.builtIns.nothingType");
                replaceTypeArguments = nothingType2;
            } else {
                replaceTypeArguments = replaceTypeArguments(type, arrayList);
            }
            return new ApproximationBounds<>(replaceTypeArguments, replaceTypeArguments(type, arrayList2));
        }
    }

    private static final KotlinType replaceTypeArguments(KotlinType kotlinType, List<TypeArgument> list) {
        kotlinType.getArguments().size();
        list.size();
        List<TypeArgument> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (TypeArgument typeArgument : list2) {
            arrayList.add(toTypeProjection(typeArgument));
        }
        return TypeSubstitutionKt.replace$default(kotlinType, arrayList, (Annotations) null, 2, (Object) null);
    }

    private static final ApproximationBounds<TypeArgument> approximateProjection(TypeArgument typeArgument) {
        ApproximationBounds<KotlinType> approximateCapturedTypes = approximateCapturedTypes(typeArgument.getInProjection());
        ApproximationBounds<KotlinType> approximateCapturedTypes2 = approximateCapturedTypes(typeArgument.getOutProjection());
        return new ApproximationBounds<>(new TypeArgument(typeArgument.getTypeParameter(), approximateCapturedTypes.component2(), approximateCapturedTypes2.component1()), new TypeArgument(typeArgument.getTypeParameter(), approximateCapturedTypes.component1(), approximateCapturedTypes2.component2()));
    }
}
