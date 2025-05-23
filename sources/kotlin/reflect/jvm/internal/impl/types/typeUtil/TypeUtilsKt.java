package kotlin.reflect.jvm.internal.impl.types.typeUtil;

import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedType;
import kotlin.reflect.jvm.internal.impl.types.checker.NewTypeVariableConstructor;
/* compiled from: TypeUtils.kt */
/* loaded from: classes2.dex */
public final class TypeUtilsKt {
    public static final KotlinBuiltIns getBuiltIns(KotlinType builtIns) {
        Intrinsics.checkParameterIsNotNull(builtIns, "$this$builtIns");
        KotlinBuiltIns builtIns2 = builtIns.getConstructor().getBuiltIns();
        Intrinsics.checkExpressionValueIsNotNull(builtIns2, "constructor.builtIns");
        return builtIns2;
    }

    public static final KotlinType makeNullable(KotlinType makeNullable) {
        Intrinsics.checkParameterIsNotNull(makeNullable, "$this$makeNullable");
        KotlinType makeNullable2 = TypeUtils.makeNullable(makeNullable);
        Intrinsics.checkExpressionValueIsNotNull(makeNullable2, "TypeUtils.makeNullable(this)");
        return makeNullable2;
    }

    public static final KotlinType makeNotNullable(KotlinType makeNotNullable) {
        Intrinsics.checkParameterIsNotNull(makeNotNullable, "$this$makeNotNullable");
        KotlinType makeNotNullable2 = TypeUtils.makeNotNullable(makeNotNullable);
        Intrinsics.checkExpressionValueIsNotNull(makeNotNullable2, "TypeUtils.makeNotNullable(this)");
        return makeNotNullable2;
    }

    public static final boolean isTypeParameter(KotlinType isTypeParameter) {
        Intrinsics.checkParameterIsNotNull(isTypeParameter, "$this$isTypeParameter");
        return TypeUtils.isTypeParameter(isTypeParameter);
    }

    public static final boolean isSubtypeOf(KotlinType isSubtypeOf, KotlinType superType) {
        Intrinsics.checkParameterIsNotNull(isSubtypeOf, "$this$isSubtypeOf");
        Intrinsics.checkParameterIsNotNull(superType, "superType");
        return KotlinTypeChecker.DEFAULT.isSubtypeOf(isSubtypeOf, superType);
    }

    public static final KotlinType replaceAnnotations(KotlinType replaceAnnotations, Annotations newAnnotations) {
        Intrinsics.checkParameterIsNotNull(replaceAnnotations, "$this$replaceAnnotations");
        Intrinsics.checkParameterIsNotNull(newAnnotations, "newAnnotations");
        return (replaceAnnotations.getAnnotations().isEmpty() && newAnnotations.isEmpty()) ? replaceAnnotations : replaceAnnotations.unwrap().replaceAnnotations(newAnnotations);
    }

    public static final TypeProjection createProjection(KotlinType type, Variance projectionKind, TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(projectionKind, "projectionKind");
        if ((typeParameterDescriptor != null ? typeParameterDescriptor.getVariance() : null) == projectionKind) {
            projectionKind = Variance.INVARIANT;
        }
        return new TypeProjectionImpl(projectionKind, type);
    }

    public static final TypeProjection asTypeProjection(KotlinType asTypeProjection) {
        Intrinsics.checkParameterIsNotNull(asTypeProjection, "$this$asTypeProjection");
        return new TypeProjectionImpl(asTypeProjection);
    }

    public static final boolean contains(KotlinType contains, Function1<? super UnwrappedType, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        return TypeUtils.contains(contains, predicate);
    }

    public static final boolean containsTypeAliasParameters(KotlinType containsTypeAliasParameters) {
        Intrinsics.checkParameterIsNotNull(containsTypeAliasParameters, "$this$containsTypeAliasParameters");
        return contains(containsTypeAliasParameters, new Function1<UnwrappedType, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt$containsTypeAliasParameters$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(UnwrappedType unwrappedType) {
                return Boolean.valueOf(invoke2(unwrappedType));
            }

            /* renamed from: invoke  reason: avoid collision after fix types in other method */
            public final boolean invoke2(UnwrappedType it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                ClassifierDescriptor mo1092getDeclarationDescriptor = it.getConstructor().mo1092getDeclarationDescriptor();
                if (mo1092getDeclarationDescriptor != null) {
                    return TypeUtilsKt.isTypeAliasParameter(mo1092getDeclarationDescriptor);
                }
                return false;
            }
        });
    }

    public static final boolean isTypeAliasParameter(ClassifierDescriptor isTypeAliasParameter) {
        Intrinsics.checkParameterIsNotNull(isTypeAliasParameter, "$this$isTypeAliasParameter");
        return (isTypeAliasParameter instanceof TypeParameterDescriptor) && (((TypeParameterDescriptor) isTypeAliasParameter).getContainingDeclaration() instanceof TypeAliasDescriptor);
    }

    public static final boolean requiresTypeAliasExpansion(KotlinType requiresTypeAliasExpansion) {
        Intrinsics.checkParameterIsNotNull(requiresTypeAliasExpansion, "$this$requiresTypeAliasExpansion");
        return contains(requiresTypeAliasExpansion, new Function1<UnwrappedType, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt$requiresTypeAliasExpansion$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(UnwrappedType unwrappedType) {
                return Boolean.valueOf(invoke2(unwrappedType));
            }

            /* renamed from: invoke  reason: avoid collision after fix types in other method */
            public final boolean invoke2(UnwrappedType it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                ClassifierDescriptor mo1092getDeclarationDescriptor = it.getConstructor().mo1092getDeclarationDescriptor();
                if (mo1092getDeclarationDescriptor != null) {
                    return (mo1092getDeclarationDescriptor instanceof TypeAliasDescriptor) || (mo1092getDeclarationDescriptor instanceof TypeParameterDescriptor);
                }
                return false;
            }
        });
    }

    public static final boolean canHaveUndefinedNullability(UnwrappedType canHaveUndefinedNullability) {
        Intrinsics.checkParameterIsNotNull(canHaveUndefinedNullability, "$this$canHaveUndefinedNullability");
        return (canHaveUndefinedNullability.getConstructor() instanceof NewTypeVariableConstructor) || (canHaveUndefinedNullability.getConstructor().mo1092getDeclarationDescriptor() instanceof TypeParameterDescriptor) || (canHaveUndefinedNullability instanceof NewCapturedType);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0054, code lost:
        r3 = r2;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0055 A[EDGE_INSN: B:23:0x0055->B:18:0x0055 ?: BREAK  , SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final kotlin.reflect.jvm.internal.impl.types.KotlinType getRepresentativeUpperBound(kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor r7) {
        /*
            java.lang.String r0 = "$this$representativeUpperBound"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r7, r0)
            java.util.List r0 = r7.getUpperBounds()
            java.lang.String r1 = "upperBounds"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r1)
            java.util.Collection r0 = (java.util.Collection) r0
            r0.isEmpty()
            java.util.List r0 = r7.getUpperBounds()
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r1)
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.Iterator r0 = r0.iterator()
        L20:
            boolean r2 = r0.hasNext()
            r3 = 0
            if (r2 == 0) goto L55
            java.lang.Object r2 = r0.next()
            r4 = r2
            kotlin.reflect.jvm.internal.impl.types.KotlinType r4 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r4
            kotlin.reflect.jvm.internal.impl.types.TypeConstructor r4 = r4.getConstructor()
            kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor r4 = r4.mo1092getDeclarationDescriptor()
            boolean r5 = r4 instanceof kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
            if (r5 != 0) goto L3b
            goto L3c
        L3b:
            r3 = r4
        L3c:
            kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor r3 = (kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor) r3
            r4 = 0
            if (r3 == 0) goto L52
            kotlin.reflect.jvm.internal.impl.descriptors.ClassKind r5 = r3.getKind()
            kotlin.reflect.jvm.internal.impl.descriptors.ClassKind r6 = kotlin.reflect.jvm.internal.impl.descriptors.ClassKind.INTERFACE
            if (r5 == r6) goto L52
            kotlin.reflect.jvm.internal.impl.descriptors.ClassKind r3 = r3.getKind()
            kotlin.reflect.jvm.internal.impl.descriptors.ClassKind r5 = kotlin.reflect.jvm.internal.impl.descriptors.ClassKind.ANNOTATION_CLASS
            if (r3 == r5) goto L52
            r4 = 1
        L52:
            if (r4 == 0) goto L20
            r3 = r2
        L55:
            kotlin.reflect.jvm.internal.impl.types.KotlinType r3 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r3
            if (r3 == 0) goto L5a
            goto L6d
        L5a:
            java.util.List r7 = r7.getUpperBounds()
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r7, r1)
            java.lang.Object r7 = kotlin.collections.CollectionsKt.first(r7)
            java.lang.String r0 = "upperBounds.first()"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r7, r0)
            r3 = r7
            kotlin.reflect.jvm.internal.impl.types.KotlinType r3 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r3
        L6d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt.getRepresentativeUpperBound(kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor):kotlin.reflect.jvm.internal.impl.types.KotlinType");
    }

    public static final KotlinType replaceArgumentsWithStarProjections(KotlinType replaceArgumentsWithStarProjections) {
        SimpleType simpleType;
        Intrinsics.checkParameterIsNotNull(replaceArgumentsWithStarProjections, "$this$replaceArgumentsWithStarProjections");
        UnwrappedType unwrap = replaceArgumentsWithStarProjections.unwrap();
        if (unwrap instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) unwrap;
            SimpleType lowerBound = flexibleType.getLowerBound();
            if (!lowerBound.getConstructor().getParameters().isEmpty() && lowerBound.getConstructor().mo1092getDeclarationDescriptor() != null) {
                List<TypeParameterDescriptor> parameters = lowerBound.getConstructor().getParameters();
                Intrinsics.checkExpressionValueIsNotNull(parameters, "constructor.parameters");
                List<TypeParameterDescriptor> list = parameters;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (TypeParameterDescriptor typeParameterDescriptor : list) {
                    arrayList.add(new StarProjectionImpl(typeParameterDescriptor));
                }
                lowerBound = TypeSubstitutionKt.replace$default(lowerBound, (List) arrayList, (Annotations) null, 2, (Object) null);
            }
            SimpleType upperBound = flexibleType.getUpperBound();
            if (!upperBound.getConstructor().getParameters().isEmpty() && upperBound.getConstructor().mo1092getDeclarationDescriptor() != null) {
                List<TypeParameterDescriptor> parameters2 = upperBound.getConstructor().getParameters();
                Intrinsics.checkExpressionValueIsNotNull(parameters2, "constructor.parameters");
                List<TypeParameterDescriptor> list2 = parameters2;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                for (TypeParameterDescriptor typeParameterDescriptor2 : list2) {
                    arrayList2.add(new StarProjectionImpl(typeParameterDescriptor2));
                }
                upperBound = TypeSubstitutionKt.replace$default(upperBound, (List) arrayList2, (Annotations) null, 2, (Object) null);
            }
            simpleType = KotlinTypeFactory.flexibleType(lowerBound, upperBound);
        } else if (unwrap instanceof SimpleType) {
            SimpleType simpleType2 = (SimpleType) unwrap;
            if (!simpleType2.getConstructor().getParameters().isEmpty() && simpleType2.getConstructor().mo1092getDeclarationDescriptor() != null) {
                List<TypeParameterDescriptor> parameters3 = simpleType2.getConstructor().getParameters();
                Intrinsics.checkExpressionValueIsNotNull(parameters3, "constructor.parameters");
                List<TypeParameterDescriptor> list3 = parameters3;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                for (TypeParameterDescriptor typeParameterDescriptor3 : list3) {
                    arrayList3.add(new StarProjectionImpl(typeParameterDescriptor3));
                }
                simpleType2 = TypeSubstitutionKt.replace$default(simpleType2, (List) arrayList3, (Annotations) null, 2, (Object) null);
            }
            simpleType = simpleType2;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return TypeWithEnhancementKt.inheritEnhancement(simpleType, unwrap);
    }
}
