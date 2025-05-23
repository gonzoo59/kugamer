package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeSystemContext;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.DefinitelyNotNullTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.DynamicTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.FlexibleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;
/* compiled from: ClassicTypeCheckerContext.kt */
/* loaded from: classes2.dex */
public class ClassicTypeCheckerContext extends AbstractTypeCheckerContext implements ClassicTypeSystemContext {
    public static final Companion Companion = new Companion(null);
    private final boolean allowedTypeVariable;
    private final boolean errorTypeEqualsToAnything;
    private final KotlinTypeRefiner kotlinTypeRefiner;
    private final boolean stubTypeEqualsToAnything;

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public int argumentsCount(KotlinTypeMarker argumentsCount) {
        Intrinsics.checkParameterIsNotNull(argumentsCount, "$this$argumentsCount");
        return ClassicTypeSystemContext.DefaultImpls.argumentsCount(this, argumentsCount);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeArgumentListMarker asArgumentList(SimpleTypeMarker asArgumentList) {
        Intrinsics.checkParameterIsNotNull(asArgumentList, "$this$asArgumentList");
        return ClassicTypeSystemContext.DefaultImpls.asArgumentList(this, asArgumentList);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public CapturedTypeMarker asCapturedType(SimpleTypeMarker asCapturedType) {
        Intrinsics.checkParameterIsNotNull(asCapturedType, "$this$asCapturedType");
        return ClassicTypeSystemContext.DefaultImpls.asCapturedType(this, asCapturedType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker asDefinitelyNotNullType) {
        Intrinsics.checkParameterIsNotNull(asDefinitelyNotNullType, "$this$asDefinitelyNotNullType");
        return ClassicTypeSystemContext.DefaultImpls.asDefinitelyNotNullType(this, asDefinitelyNotNullType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public DynamicTypeMarker asDynamicType(FlexibleTypeMarker asDynamicType) {
        Intrinsics.checkParameterIsNotNull(asDynamicType, "$this$asDynamicType");
        return ClassicTypeSystemContext.DefaultImpls.asDynamicType(this, asDynamicType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker asFlexibleType) {
        Intrinsics.checkParameterIsNotNull(asFlexibleType, "$this$asFlexibleType");
        return ClassicTypeSystemContext.DefaultImpls.asFlexibleType(this, asFlexibleType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext, kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeSystemContext
    public SimpleTypeMarker asSimpleType(KotlinTypeMarker asSimpleType) {
        Intrinsics.checkParameterIsNotNull(asSimpleType, "$this$asSimpleType");
        return ClassicTypeSystemContext.DefaultImpls.asSimpleType(this, asSimpleType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeArgumentMarker asTypeArgument(KotlinTypeMarker asTypeArgument) {
        Intrinsics.checkParameterIsNotNull(asTypeArgument, "$this$asTypeArgument");
        return ClassicTypeSystemContext.DefaultImpls.asTypeArgument(this, asTypeArgument);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(status, "status");
        return ClassicTypeSystemContext.DefaultImpls.captureFromArguments(this, type, status);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker fastCorrespondingSupertypes, TypeConstructorMarker constructor) {
        Intrinsics.checkParameterIsNotNull(fastCorrespondingSupertypes, "$this$fastCorrespondingSupertypes");
        Intrinsics.checkParameterIsNotNull(constructor, "constructor");
        return ClassicTypeSystemContext.DefaultImpls.fastCorrespondingSupertypes(this, fastCorrespondingSupertypes, constructor);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext, kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeArgumentMarker get(TypeArgumentListMarker get, int i) {
        Intrinsics.checkParameterIsNotNull(get, "$this$get");
        return ClassicTypeSystemContext.DefaultImpls.get(this, get, i);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeArgumentMarker getArgument(KotlinTypeMarker getArgument, int i) {
        Intrinsics.checkParameterIsNotNull(getArgument, "$this$getArgument");
        return ClassicTypeSystemContext.DefaultImpls.getArgument(this, getArgument, i);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker getArgumentOrNull, int i) {
        Intrinsics.checkParameterIsNotNull(getArgumentOrNull, "$this$getArgumentOrNull");
        return ClassicTypeSystemContext.DefaultImpls.getArgumentOrNull(this, getArgumentOrNull, i);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker getClassFqNameUnsafe) {
        Intrinsics.checkParameterIsNotNull(getClassFqNameUnsafe, "$this$getClassFqNameUnsafe");
        return ClassicTypeSystemContext.DefaultImpls.getClassFqNameUnsafe(this, getClassFqNameUnsafe);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeParameterMarker getParameter(TypeConstructorMarker getParameter, int i) {
        Intrinsics.checkParameterIsNotNull(getParameter, "$this$getParameter");
        return ClassicTypeSystemContext.DefaultImpls.getParameter(this, getParameter, i);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public PrimitiveType getPrimitiveArrayType(TypeConstructorMarker getPrimitiveArrayType) {
        Intrinsics.checkParameterIsNotNull(getPrimitiveArrayType, "$this$getPrimitiveArrayType");
        return ClassicTypeSystemContext.DefaultImpls.getPrimitiveArrayType(this, getPrimitiveArrayType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public PrimitiveType getPrimitiveType(TypeConstructorMarker getPrimitiveType) {
        Intrinsics.checkParameterIsNotNull(getPrimitiveType, "$this$getPrimitiveType");
        return ClassicTypeSystemContext.DefaultImpls.getPrimitiveType(this, getPrimitiveType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker getRepresentativeUpperBound) {
        Intrinsics.checkParameterIsNotNull(getRepresentativeUpperBound, "$this$getRepresentativeUpperBound");
        return ClassicTypeSystemContext.DefaultImpls.getRepresentativeUpperBound(this, getRepresentativeUpperBound);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getSubstitutedUnderlyingType(KotlinTypeMarker getSubstitutedUnderlyingType) {
        Intrinsics.checkParameterIsNotNull(getSubstitutedUnderlyingType, "$this$getSubstitutedUnderlyingType");
        return ClassicTypeSystemContext.DefaultImpls.getSubstitutedUnderlyingType(this, getSubstitutedUnderlyingType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public KotlinTypeMarker getType(TypeArgumentMarker getType) {
        Intrinsics.checkParameterIsNotNull(getType, "$this$getType");
        return ClassicTypeSystemContext.DefaultImpls.getType(this, getType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker getTypeParameterClassifier) {
        Intrinsics.checkParameterIsNotNull(getTypeParameterClassifier, "$this$getTypeParameterClassifier");
        return ClassicTypeSystemContext.DefaultImpls.getTypeParameterClassifier(this, getTypeParameterClassifier);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeArgumentMarker getVariance) {
        Intrinsics.checkParameterIsNotNull(getVariance, "$this$getVariance");
        return ClassicTypeSystemContext.DefaultImpls.getVariance(this, getVariance);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeParameterMarker getVariance) {
        Intrinsics.checkParameterIsNotNull(getVariance, "$this$getVariance");
        return ClassicTypeSystemContext.DefaultImpls.getVariance(this, getVariance);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public boolean hasAnnotation(KotlinTypeMarker hasAnnotation, FqName fqName) {
        Intrinsics.checkParameterIsNotNull(hasAnnotation, "$this$hasAnnotation");
        Intrinsics.checkParameterIsNotNull(fqName, "fqName");
        return ClassicTypeSystemContext.DefaultImpls.hasAnnotation(this, hasAnnotation, fqName);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public boolean hasFlexibleNullability(KotlinTypeMarker hasFlexibleNullability) {
        Intrinsics.checkParameterIsNotNull(hasFlexibleNullability, "$this$hasFlexibleNullability");
        return ClassicTypeSystemContext.DefaultImpls.hasFlexibleNullability(this, hasFlexibleNullability);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext, kotlin.reflect.jvm.internal.impl.types.model.TypeSystemOptimizationContext
    public boolean identicalArguments(SimpleTypeMarker a, SimpleTypeMarker b) {
        Intrinsics.checkParameterIsNotNull(a, "a");
        Intrinsics.checkParameterIsNotNull(b, "b");
        return ClassicTypeSystemContext.DefaultImpls.identicalArguments(this, a, b);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkParameterIsNotNull(types, "types");
        return ClassicTypeSystemContext.DefaultImpls.intersectTypes(this, types);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isAnyConstructor(TypeConstructorMarker isAnyConstructor) {
        Intrinsics.checkParameterIsNotNull(isAnyConstructor, "$this$isAnyConstructor");
        return ClassicTypeSystemContext.DefaultImpls.isAnyConstructor(this, isAnyConstructor);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public boolean isClassType(SimpleTypeMarker isClassType) {
        Intrinsics.checkParameterIsNotNull(isClassType, "$this$isClassType");
        return ClassicTypeSystemContext.DefaultImpls.isClassType(this, isClassType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isClassTypeConstructor(TypeConstructorMarker isClassTypeConstructor) {
        Intrinsics.checkParameterIsNotNull(isClassTypeConstructor, "$this$isClassTypeConstructor");
        return ClassicTypeSystemContext.DefaultImpls.isClassTypeConstructor(this, isClassTypeConstructor);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isCommonFinalClassConstructor(TypeConstructorMarker isCommonFinalClassConstructor) {
        Intrinsics.checkParameterIsNotNull(isCommonFinalClassConstructor, "$this$isCommonFinalClassConstructor");
        return ClassicTypeSystemContext.DefaultImpls.isCommonFinalClassConstructor(this, isCommonFinalClassConstructor);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public boolean isDefinitelyNotNullType(KotlinTypeMarker isDefinitelyNotNullType) {
        Intrinsics.checkParameterIsNotNull(isDefinitelyNotNullType, "$this$isDefinitelyNotNullType");
        return ClassicTypeSystemContext.DefaultImpls.isDefinitelyNotNullType(this, isDefinitelyNotNullType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isDenotable(TypeConstructorMarker isDenotable) {
        Intrinsics.checkParameterIsNotNull(isDenotable, "$this$isDenotable");
        return ClassicTypeSystemContext.DefaultImpls.isDenotable(this, isDenotable);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public boolean isDynamic(KotlinTypeMarker isDynamic) {
        Intrinsics.checkParameterIsNotNull(isDynamic, "$this$isDynamic");
        return ClassicTypeSystemContext.DefaultImpls.isDynamic(this, isDynamic);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
        Intrinsics.checkParameterIsNotNull(c1, "c1");
        Intrinsics.checkParameterIsNotNull(c2, "c2");
        return ClassicTypeSystemContext.DefaultImpls.isEqualTypeConstructors(this, c1, c2);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isError(KotlinTypeMarker isError) {
        Intrinsics.checkParameterIsNotNull(isError, "$this$isError");
        return ClassicTypeSystemContext.DefaultImpls.isError(this, isError);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public boolean isInlineClass(TypeConstructorMarker isInlineClass) {
        Intrinsics.checkParameterIsNotNull(isInlineClass, "$this$isInlineClass");
        return ClassicTypeSystemContext.DefaultImpls.isInlineClass(this, isInlineClass);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public boolean isIntegerLiteralType(SimpleTypeMarker isIntegerLiteralType) {
        Intrinsics.checkParameterIsNotNull(isIntegerLiteralType, "$this$isIntegerLiteralType");
        return ClassicTypeSystemContext.DefaultImpls.isIntegerLiteralType(this, isIntegerLiteralType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker isIntegerLiteralTypeConstructor) {
        Intrinsics.checkParameterIsNotNull(isIntegerLiteralTypeConstructor, "$this$isIntegerLiteralTypeConstructor");
        return ClassicTypeSystemContext.DefaultImpls.isIntegerLiteralTypeConstructor(this, isIntegerLiteralTypeConstructor);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isIntersection(TypeConstructorMarker isIntersection) {
        Intrinsics.checkParameterIsNotNull(isIntersection, "$this$isIntersection");
        return ClassicTypeSystemContext.DefaultImpls.isIntersection(this, isIntersection);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public boolean isMarkedNullable(KotlinTypeMarker isMarkedNullable) {
        Intrinsics.checkParameterIsNotNull(isMarkedNullable, "$this$isMarkedNullable");
        return ClassicTypeSystemContext.DefaultImpls.isMarkedNullable(this, isMarkedNullable);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isMarkedNullable(SimpleTypeMarker isMarkedNullable) {
        Intrinsics.checkParameterIsNotNull(isMarkedNullable, "$this$isMarkedNullable");
        return ClassicTypeSystemContext.DefaultImpls.isMarkedNullable((ClassicTypeSystemContext) this, isMarkedNullable);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public boolean isNothing(KotlinTypeMarker isNothing) {
        Intrinsics.checkParameterIsNotNull(isNothing, "$this$isNothing");
        return ClassicTypeSystemContext.DefaultImpls.isNothing(this, isNothing);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isNothingConstructor(TypeConstructorMarker isNothingConstructor) {
        Intrinsics.checkParameterIsNotNull(isNothingConstructor, "$this$isNothingConstructor");
        return ClassicTypeSystemContext.DefaultImpls.isNothingConstructor(this, isNothingConstructor);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isNullableType(KotlinTypeMarker isNullableType) {
        Intrinsics.checkParameterIsNotNull(isNullableType, "$this$isNullableType");
        return ClassicTypeSystemContext.DefaultImpls.isNullableType(this, isNullableType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isPrimitiveType(SimpleTypeMarker isPrimitiveType) {
        Intrinsics.checkParameterIsNotNull(isPrimitiveType, "$this$isPrimitiveType");
        return ClassicTypeSystemContext.DefaultImpls.isPrimitiveType(this, isPrimitiveType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isSingleClassifierType(SimpleTypeMarker isSingleClassifierType) {
        Intrinsics.checkParameterIsNotNull(isSingleClassifierType, "$this$isSingleClassifierType");
        return ClassicTypeSystemContext.DefaultImpls.isSingleClassifierType(this, isSingleClassifierType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isStarProjection(TypeArgumentMarker isStarProjection) {
        Intrinsics.checkParameterIsNotNull(isStarProjection, "$this$isStarProjection");
        return ClassicTypeSystemContext.DefaultImpls.isStarProjection(this, isStarProjection);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isStubType(SimpleTypeMarker isStubType) {
        Intrinsics.checkParameterIsNotNull(isStubType, "$this$isStubType");
        return ClassicTypeSystemContext.DefaultImpls.isStubType(this, isStubType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public boolean isUnderKotlinPackage(TypeConstructorMarker isUnderKotlinPackage) {
        Intrinsics.checkParameterIsNotNull(isUnderKotlinPackage, "$this$isUnderKotlinPackage");
        return ClassicTypeSystemContext.DefaultImpls.isUnderKotlinPackage(this, isUnderKotlinPackage);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBound(FlexibleTypeMarker lowerBound) {
        Intrinsics.checkParameterIsNotNull(lowerBound, "$this$lowerBound");
        return ClassicTypeSystemContext.DefaultImpls.lowerBound(this, lowerBound);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext, kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker lowerBoundIfFlexible) {
        Intrinsics.checkParameterIsNotNull(lowerBoundIfFlexible, "$this$lowerBoundIfFlexible");
        return ClassicTypeSystemContext.DefaultImpls.lowerBoundIfFlexible(this, lowerBoundIfFlexible);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public KotlinTypeMarker lowerType(CapturedTypeMarker lowerType) {
        Intrinsics.checkParameterIsNotNull(lowerType, "$this$lowerType");
        return ClassicTypeSystemContext.DefaultImpls.lowerType(this, lowerType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker makeNullable(KotlinTypeMarker makeNullable) {
        Intrinsics.checkParameterIsNotNull(makeNullable, "$this$makeNullable");
        return ClassicTypeSystemContext.DefaultImpls.makeNullable(this, makeNullable);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public int parametersCount(TypeConstructorMarker parametersCount) {
        Intrinsics.checkParameterIsNotNull(parametersCount, "$this$parametersCount");
        return ClassicTypeSystemContext.DefaultImpls.parametersCount(this, parametersCount);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker possibleIntegerTypes) {
        Intrinsics.checkParameterIsNotNull(possibleIntegerTypes, "$this$possibleIntegerTypes");
        return ClassicTypeSystemContext.DefaultImpls.possibleIntegerTypes(this, possibleIntegerTypes);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext, kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public int size(TypeArgumentListMarker size) {
        Intrinsics.checkParameterIsNotNull(size, "$this$size");
        return ClassicTypeSystemContext.DefaultImpls.size(this, size);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker supertypes) {
        Intrinsics.checkParameterIsNotNull(supertypes, "$this$supertypes");
        return ClassicTypeSystemContext.DefaultImpls.supertypes(this, supertypes);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext, kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(KotlinTypeMarker typeConstructor) {
        Intrinsics.checkParameterIsNotNull(typeConstructor, "$this$typeConstructor");
        return ClassicTypeSystemContext.DefaultImpls.typeConstructor(this, typeConstructor);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext, kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeSystemContext
    public TypeConstructorMarker typeConstructor(SimpleTypeMarker typeConstructor) {
        Intrinsics.checkParameterIsNotNull(typeConstructor, "$this$typeConstructor");
        return ClassicTypeSystemContext.DefaultImpls.typeConstructor((ClassicTypeSystemContext) this, typeConstructor);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleTypeMarker upperBound(FlexibleTypeMarker upperBound) {
        Intrinsics.checkParameterIsNotNull(upperBound, "$this$upperBound");
        return ClassicTypeSystemContext.DefaultImpls.upperBound(this, upperBound);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext, kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker upperBoundIfFlexible) {
        Intrinsics.checkParameterIsNotNull(upperBoundIfFlexible, "$this$upperBoundIfFlexible");
        return ClassicTypeSystemContext.DefaultImpls.upperBoundIfFlexible(this, upperBoundIfFlexible);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleTypeMarker withNullability(SimpleTypeMarker withNullability, boolean z) {
        Intrinsics.checkParameterIsNotNull(withNullability, "$this$withNullability");
        return ClassicTypeSystemContext.DefaultImpls.withNullability(this, withNullability, z);
    }

    public /* synthetic */ ClassicTypeCheckerContext(boolean z, boolean z2, boolean z3, KotlinTypeRefiner.Default r5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? true : z2, (i & 4) != 0 ? true : z3, (i & 8) != 0 ? KotlinTypeRefiner.Default.INSTANCE : r5);
    }

    public ClassicTypeCheckerContext(boolean z, boolean z2, boolean z3, KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkParameterIsNotNull(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.errorTypeEqualsToAnything = z;
        this.stubTypeEqualsToAnything = z2;
        this.allowedTypeVariable = z3;
        this.kotlinTypeRefiner = kotlinTypeRefiner;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public KotlinTypeMarker prepareType(KotlinTypeMarker type) {
        String errorMessage;
        Intrinsics.checkParameterIsNotNull(type, "type");
        if (!(type instanceof KotlinType)) {
            errorMessage = ClassicTypeCheckerContextKt.errorMessage(type);
            throw new IllegalArgumentException(errorMessage.toString());
        }
        return NewKotlinTypeChecker.Companion.getDefault().transformToNewType(((KotlinType) type).unwrap());
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public KotlinTypeMarker refineType(KotlinTypeMarker type) {
        String errorMessage;
        Intrinsics.checkParameterIsNotNull(type, "type");
        if (!(type instanceof KotlinType)) {
            errorMessage = ClassicTypeCheckerContextKt.errorMessage(type);
            throw new IllegalArgumentException(errorMessage.toString());
        }
        return this.kotlinTypeRefiner.refineType((KotlinType) type);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public boolean isErrorTypeEqualsToAnything() {
        return this.errorTypeEqualsToAnything;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public boolean isStubTypeEqualsToAnything() {
        return this.stubTypeEqualsToAnything;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public boolean areEqualTypeConstructors(TypeConstructorMarker a, TypeConstructorMarker b) {
        String errorMessage;
        String errorMessage2;
        Intrinsics.checkParameterIsNotNull(a, "a");
        Intrinsics.checkParameterIsNotNull(b, "b");
        if (!(a instanceof TypeConstructor)) {
            errorMessage = ClassicTypeCheckerContextKt.errorMessage(a);
            throw new IllegalArgumentException(errorMessage.toString());
        } else if (!(b instanceof TypeConstructor)) {
            errorMessage2 = ClassicTypeCheckerContextKt.errorMessage(b);
            throw new IllegalArgumentException(errorMessage2.toString());
        } else {
            return areEqualTypeConstructors((TypeConstructor) a, (TypeConstructor) b);
        }
    }

    public boolean areEqualTypeConstructors(TypeConstructor a, TypeConstructor b) {
        Intrinsics.checkParameterIsNotNull(a, "a");
        Intrinsics.checkParameterIsNotNull(b, "b");
        return a instanceof IntegerLiteralTypeConstructor ? ((IntegerLiteralTypeConstructor) a).checkConstructor(b) : b instanceof IntegerLiteralTypeConstructor ? ((IntegerLiteralTypeConstructor) b).checkConstructor(a) : Intrinsics.areEqual(a, b);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public AbstractTypeCheckerContext.SupertypesPolicy.DoCustomTransform substitutionSupertypePolicy(SimpleTypeMarker type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        return Companion.classicSubstitutionSupertypePolicy(this, type);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext
    public boolean isAllowedTypeVariable(KotlinTypeMarker isAllowedTypeVariable) {
        Intrinsics.checkParameterIsNotNull(isAllowedTypeVariable, "$this$isAllowedTypeVariable");
        return (isAllowedTypeVariable instanceof UnwrappedType) && this.allowedTypeVariable && (((UnwrappedType) isAllowedTypeVariable).getConstructor() instanceof NewTypeVariableConstructor);
    }

    /* compiled from: ClassicTypeCheckerContext.kt */
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final AbstractTypeCheckerContext.SupertypesPolicy.DoCustomTransform classicSubstitutionSupertypePolicy(final ClassicTypeSystemContext classicSubstitutionSupertypePolicy, SimpleTypeMarker type) {
            String errorMessage;
            Intrinsics.checkParameterIsNotNull(classicSubstitutionSupertypePolicy, "$this$classicSubstitutionSupertypePolicy");
            Intrinsics.checkParameterIsNotNull(type, "type");
            if (!(type instanceof SimpleType)) {
                errorMessage = ClassicTypeCheckerContextKt.errorMessage(type);
                throw new IllegalArgumentException(errorMessage.toString());
            }
            final TypeSubstitutor buildSubstitutor = TypeConstructorSubstitution.Companion.create((KotlinType) type).buildSubstitutor();
            return new AbstractTypeCheckerContext.SupertypesPolicy.DoCustomTransform() { // from class: kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeCheckerContext$Companion$classicSubstitutionSupertypePolicy$2
                @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext.SupertypesPolicy
                /* renamed from: transformType */
                public SimpleTypeMarker mo1096transformType(AbstractTypeCheckerContext context, KotlinTypeMarker type2) {
                    Intrinsics.checkParameterIsNotNull(context, "context");
                    Intrinsics.checkParameterIsNotNull(type2, "type");
                    ClassicTypeSystemContext classicTypeSystemContext = ClassicTypeSystemContext.this;
                    TypeSubstitutor typeSubstitutor = buildSubstitutor;
                    SimpleTypeMarker lowerBoundIfFlexible = classicTypeSystemContext.lowerBoundIfFlexible(type2);
                    if (lowerBoundIfFlexible != null) {
                        KotlinType safeSubstitute = typeSubstitutor.safeSubstitute((KotlinType) lowerBoundIfFlexible, Variance.INVARIANT);
                        Intrinsics.checkExpressionValueIsNotNull(safeSubstitute, "substitutor.safeSubstitu…ANT\n                    )");
                        SimpleTypeMarker asSimpleType = classicTypeSystemContext.asSimpleType(safeSubstitute);
                        if (asSimpleType == null) {
                            Intrinsics.throwNpe();
                        }
                        return asSimpleType;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.KotlinType");
                }
            };
        }
    }
}
