package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.CompositeAnnotations;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.SimpleClassicTypeSystemContext;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
/* compiled from: typeEnhancement.kt */
/* loaded from: classes2.dex */
public final class TypeEnhancementKt {
    private static final EnhancedTypeAnnotations ENHANCED_MUTABILITY_ANNOTATIONS;
    private static final EnhancedTypeAnnotations ENHANCED_NULLABILITY_ANNOTATIONS;

    /* loaded from: classes2.dex */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[MutabilityQualifier.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[MutabilityQualifier.READ_ONLY.ordinal()] = 1;
            iArr[MutabilityQualifier.MUTABLE.ordinal()] = 2;
            int[] iArr2 = new int[NullabilityQualifier.values().length];
            $EnumSwitchMapping$1 = iArr2;
            iArr2[NullabilityQualifier.NULLABLE.ordinal()] = 1;
            iArr2[NullabilityQualifier.NOT_NULL.ordinal()] = 2;
        }
    }

    public static final KotlinType enhance(KotlinType enhance, Function1<? super Integer, JavaTypeQualifiers> qualifiers) {
        Intrinsics.checkParameterIsNotNull(enhance, "$this$enhance");
        Intrinsics.checkParameterIsNotNull(qualifiers, "qualifiers");
        return enhancePossiblyFlexible(enhance.unwrap(), qualifiers, 0).getTypeIfChanged();
    }

    public static final boolean hasEnhancedNullability(KotlinType hasEnhancedNullability) {
        Intrinsics.checkParameterIsNotNull(hasEnhancedNullability, "$this$hasEnhancedNullability");
        return hasEnhancedNullability(SimpleClassicTypeSystemContext.INSTANCE, hasEnhancedNullability);
    }

    public static final boolean hasEnhancedNullability(TypeSystemCommonBackendContext hasEnhancedNullability, KotlinTypeMarker type) {
        Intrinsics.checkParameterIsNotNull(hasEnhancedNullability, "$this$hasEnhancedNullability");
        Intrinsics.checkParameterIsNotNull(type, "type");
        FqName fqName = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
        Intrinsics.checkExpressionValueIsNotNull(fqName, "JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION");
        return hasEnhancedNullability.hasAnnotation(type, fqName);
    }

    private static final Result enhancePossiblyFlexible(UnwrappedType unwrappedType, Function1<? super Integer, JavaTypeQualifiers> function1, int i) {
        UnwrappedType unwrappedType2 = unwrappedType;
        boolean z = false;
        if (KotlinTypeKt.isError(unwrappedType2)) {
            return new Result(unwrappedType2, 1, false);
        }
        if (unwrappedType instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) unwrappedType;
            SimpleResult enhanceInflexible = enhanceInflexible(flexibleType.getLowerBound(), function1, i, TypeComponentPosition.FLEXIBLE_LOWER);
            SimpleResult enhanceInflexible2 = enhanceInflexible(flexibleType.getUpperBound(), function1, i, TypeComponentPosition.FLEXIBLE_UPPER);
            enhanceInflexible.getSubtreeSize();
            enhanceInflexible2.getSubtreeSize();
            z = (enhanceInflexible.getWereChanges() || enhanceInflexible2.getWereChanges()) ? true : true;
            KotlinType enhancement = TypeWithEnhancementKt.getEnhancement(enhanceInflexible.getType());
            if (enhancement == null) {
                enhancement = TypeWithEnhancementKt.getEnhancement(enhanceInflexible2.getType());
            }
            if (z) {
                unwrappedType = TypeWithEnhancementKt.wrapEnhancement(unwrappedType instanceof RawTypeImpl ? new RawTypeImpl(enhanceInflexible.getType(), enhanceInflexible2.getType()) : KotlinTypeFactory.flexibleType(enhanceInflexible.getType(), enhanceInflexible2.getType()), enhancement);
            }
            return new Result(unwrappedType, enhanceInflexible.getSubtreeSize(), z);
        } else if (unwrappedType instanceof SimpleType) {
            return enhanceInflexible((SimpleType) unwrappedType, function1, i, TypeComponentPosition.INFLEXIBLE);
        } else {
            throw new NoWhenBranchMatchedException();
        }
    }

    private static final SimpleResult enhanceInflexible(SimpleType simpleType, Function1<? super Integer, JavaTypeQualifiers> function1, int i, TypeComponentPosition typeComponentPosition) {
        TypeProjection createProjection;
        if (shouldEnhance(typeComponentPosition) || !simpleType.getArguments().isEmpty()) {
            ClassifierDescriptor mo1092getDeclarationDescriptor = simpleType.getConstructor().mo1092getDeclarationDescriptor();
            if (mo1092getDeclarationDescriptor != null) {
                Intrinsics.checkExpressionValueIsNotNull(mo1092getDeclarationDescriptor, "constructor.declarationD…pleResult(this, 1, false)");
                JavaTypeQualifiers invoke = function1.invoke(Integer.valueOf(i));
                EnhancementResult<ClassifierDescriptor> enhanceMutability = enhanceMutability(mo1092getDeclarationDescriptor, invoke, typeComponentPosition);
                ClassifierDescriptor component1 = enhanceMutability.component1();
                Annotations component2 = enhanceMutability.component2();
                TypeConstructor typeConstructor = component1.getTypeConstructor();
                Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "enhancedClassifier.typeConstructor");
                int i2 = i + 1;
                boolean z = component2 != null;
                List<TypeProjection> arguments = simpleType.getArguments();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
                int i3 = 0;
                for (Object obj : arguments) {
                    int i4 = i3 + 1;
                    if (i3 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    TypeProjection typeProjection = (TypeProjection) obj;
                    if (typeProjection.isStarProjection()) {
                        i2++;
                        TypeConstructor typeConstructor2 = component1.getTypeConstructor();
                        Intrinsics.checkExpressionValueIsNotNull(typeConstructor2, "enhancedClassifier.typeConstructor");
                        createProjection = TypeUtils.makeStarProjection(typeConstructor2.getParameters().get(i3));
                    } else {
                        Result enhancePossiblyFlexible = enhancePossiblyFlexible(typeProjection.getType().unwrap(), function1, i2);
                        z = z || enhancePossiblyFlexible.getWereChanges();
                        i2 += enhancePossiblyFlexible.getSubtreeSize();
                        KotlinType type = enhancePossiblyFlexible.getType();
                        Variance projectionKind = typeProjection.getProjectionKind();
                        Intrinsics.checkExpressionValueIsNotNull(projectionKind, "arg.projectionKind");
                        createProjection = TypeUtilsKt.createProjection(type, projectionKind, typeConstructor.getParameters().get(i3));
                    }
                    arrayList.add(createProjection);
                    i3 = i4;
                }
                ArrayList arrayList2 = arrayList;
                EnhancementResult<Boolean> enhancedNullability = getEnhancedNullability(simpleType, invoke, typeComponentPosition);
                boolean booleanValue = enhancedNullability.component1().booleanValue();
                Annotations component22 = enhancedNullability.component2();
                int i5 = i2 - i;
                if (!(z || component22 != null)) {
                    return new SimpleResult(simpleType, i5, false);
                }
                NotNullTypeParameter simpleType$default = KotlinTypeFactory.simpleType$default(compositeAnnotationsOrSingle(CollectionsKt.listOfNotNull((Object[]) new Annotations[]{simpleType.getAnnotations(), component2, component22})), typeConstructor, arrayList2, booleanValue, null, 16, null);
                if (invoke.isNotNullTypeParameter()) {
                    simpleType$default = new NotNullTypeParameter(simpleType$default);
                }
                KotlinType wrapEnhancement = component22 != null && invoke.isNullabilityQualifierForWarning() ? TypeWithEnhancementKt.wrapEnhancement(simpleType, simpleType$default) : simpleType$default;
                if (wrapEnhancement != null) {
                    return new SimpleResult((SimpleType) wrapEnhancement, i5, true);
                }
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
            }
            return new SimpleResult(simpleType, 1, false);
        }
        return new SimpleResult(simpleType, 1, false);
    }

    private static final Annotations compositeAnnotationsOrSingle(List<? extends Annotations> list) {
        int size = list.size();
        if (size != 0) {
            if (size == 1) {
                return (Annotations) CollectionsKt.single((List<? extends Object>) list);
            }
            return new CompositeAnnotations(CollectionsKt.toList(list));
        }
        throw new IllegalStateException("At least one Annotations object expected".toString());
    }

    public static final boolean shouldEnhance(TypeComponentPosition shouldEnhance) {
        Intrinsics.checkParameterIsNotNull(shouldEnhance, "$this$shouldEnhance");
        return shouldEnhance != TypeComponentPosition.INFLEXIBLE;
    }

    private static final <T> EnhancementResult<T> noChange(T t) {
        return new EnhancementResult<>(t, null);
    }

    private static final <T> EnhancementResult<T> enhancedNullability(T t) {
        return new EnhancementResult<>(t, ENHANCED_NULLABILITY_ANNOTATIONS);
    }

    private static final <T> EnhancementResult<T> enhancedMutability(T t) {
        return new EnhancementResult<>(t, ENHANCED_MUTABILITY_ANNOTATIONS);
    }

    private static final EnhancementResult<ClassifierDescriptor> enhanceMutability(ClassifierDescriptor classifierDescriptor, JavaTypeQualifiers javaTypeQualifiers, TypeComponentPosition typeComponentPosition) {
        if (shouldEnhance(typeComponentPosition) && (classifierDescriptor instanceof ClassDescriptor)) {
            JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
            MutabilityQualifier mutability = javaTypeQualifiers.getMutability();
            if (mutability != null) {
                int i = WhenMappings.$EnumSwitchMapping$0[mutability.ordinal()];
                if (i == 1) {
                    if (typeComponentPosition == TypeComponentPosition.FLEXIBLE_LOWER) {
                        ClassDescriptor classDescriptor = (ClassDescriptor) classifierDescriptor;
                        if (javaToKotlinClassMap.isMutable(classDescriptor)) {
                            return enhancedMutability(javaToKotlinClassMap.convertMutableToReadOnly(classDescriptor));
                        }
                    }
                } else if (i == 2 && typeComponentPosition == TypeComponentPosition.FLEXIBLE_UPPER) {
                    ClassDescriptor classDescriptor2 = (ClassDescriptor) classifierDescriptor;
                    if (javaToKotlinClassMap.isReadOnly(classDescriptor2)) {
                        return enhancedMutability(javaToKotlinClassMap.convertReadOnlyToMutable(classDescriptor2));
                    }
                }
            }
            return noChange(classifierDescriptor);
        }
        return noChange(classifierDescriptor);
    }

    private static final EnhancementResult<Boolean> getEnhancedNullability(KotlinType kotlinType, JavaTypeQualifiers javaTypeQualifiers, TypeComponentPosition typeComponentPosition) {
        if (shouldEnhance(typeComponentPosition)) {
            NullabilityQualifier nullability = javaTypeQualifiers.getNullability();
            if (nullability != null) {
                int i = WhenMappings.$EnumSwitchMapping$1[nullability.ordinal()];
                if (i == 1) {
                    return enhancedNullability(true);
                }
                if (i == 2) {
                    return enhancedNullability(false);
                }
            }
            return noChange(Boolean.valueOf(kotlinType.isMarkedNullable()));
        }
        return noChange(Boolean.valueOf(kotlinType.isMarkedNullable()));
    }

    static {
        FqName fqName = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
        Intrinsics.checkExpressionValueIsNotNull(fqName, "JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION");
        ENHANCED_NULLABILITY_ANNOTATIONS = new EnhancedTypeAnnotations(fqName);
        FqName fqName2 = JvmAnnotationNames.ENHANCED_MUTABILITY_ANNOTATION;
        Intrinsics.checkExpressionValueIsNotNull(fqName2, "JvmAnnotationNames.ENHANCED_MUTABILITY_ANNOTATION");
        ENHANCED_MUTABILITY_ANNOTATIONS = new EnhancedTypeAnnotations(fqName2);
    }
}
