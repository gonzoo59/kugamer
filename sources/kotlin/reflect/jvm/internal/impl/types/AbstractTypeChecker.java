package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.model.ArgumentList;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;
/* compiled from: AbstractTypeChecker.kt */
/* loaded from: classes2.dex */
public final class AbstractTypeChecker {
    public static final AbstractTypeChecker INSTANCE = new AbstractTypeChecker();
    public static boolean RUN_SLOW_ASSERTIONS;

    /* loaded from: classes2.dex */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[TypeVariance.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[TypeVariance.INV.ordinal()] = 1;
            iArr[TypeVariance.OUT.ordinal()] = 2;
            iArr[TypeVariance.IN.ordinal()] = 3;
            int[] iArr2 = new int[AbstractTypeCheckerContext.LowerCapturedTypePolicy.values().length];
            $EnumSwitchMapping$1 = iArr2;
            iArr2[AbstractTypeCheckerContext.LowerCapturedTypePolicy.CHECK_ONLY_LOWER.ordinal()] = 1;
            iArr2[AbstractTypeCheckerContext.LowerCapturedTypePolicy.CHECK_SUBTYPE_AND_LOWER.ordinal()] = 2;
            iArr2[AbstractTypeCheckerContext.LowerCapturedTypePolicy.SKIP_LOWER.ordinal()] = 3;
        }
    }

    private AbstractTypeChecker() {
    }

    public final boolean isSubtypeOf(AbstractTypeCheckerContext context, KotlinTypeMarker subType, KotlinTypeMarker superType) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(subType, "subType");
        Intrinsics.checkParameterIsNotNull(superType, "superType");
        if (subType == superType) {
            return true;
        }
        return INSTANCE.completeIsSubTypeOf(context, context.prepareType(context.refineType(subType)), context.prepareType(context.refineType(superType)));
    }

    public final boolean equalTypes(AbstractTypeCheckerContext context, KotlinTypeMarker a, KotlinTypeMarker b) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(a, "a");
        Intrinsics.checkParameterIsNotNull(b, "b");
        if (a == b) {
            return true;
        }
        AbstractTypeChecker abstractTypeChecker = INSTANCE;
        if (abstractTypeChecker.isCommonDenotableType(context, a) && abstractTypeChecker.isCommonDenotableType(context, b)) {
            KotlinTypeMarker refineType = context.refineType(a);
            KotlinTypeMarker refineType2 = context.refineType(b);
            SimpleTypeMarker lowerBoundIfFlexible = context.lowerBoundIfFlexible(refineType);
            if (!context.areEqualTypeConstructors(context.typeConstructor(refineType), context.typeConstructor(refineType2))) {
                return false;
            }
            if (context.argumentsCount(lowerBoundIfFlexible) == 0) {
                return context.hasFlexibleNullability(refineType) || context.hasFlexibleNullability(refineType2) || context.isMarkedNullable(lowerBoundIfFlexible) == context.isMarkedNullable(context.lowerBoundIfFlexible(refineType2));
            }
        }
        return abstractTypeChecker.isSubtypeOf(context, a, b) && abstractTypeChecker.isSubtypeOf(context, b, a);
    }

    private final boolean completeIsSubTypeOf(AbstractTypeCheckerContext abstractTypeCheckerContext, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2) {
        Boolean checkSubtypeForSpecialCases = checkSubtypeForSpecialCases(abstractTypeCheckerContext, abstractTypeCheckerContext.lowerBoundIfFlexible(kotlinTypeMarker), abstractTypeCheckerContext.upperBoundIfFlexible(kotlinTypeMarker2));
        if (checkSubtypeForSpecialCases != null) {
            boolean booleanValue = checkSubtypeForSpecialCases.booleanValue();
            abstractTypeCheckerContext.addSubtypeConstraint(kotlinTypeMarker, kotlinTypeMarker2);
            return booleanValue;
        }
        Boolean addSubtypeConstraint = abstractTypeCheckerContext.addSubtypeConstraint(kotlinTypeMarker, kotlinTypeMarker2);
        return addSubtypeConstraint != null ? addSubtypeConstraint.booleanValue() : isSubtypeOfForSingleClassifierType(abstractTypeCheckerContext, abstractTypeCheckerContext.lowerBoundIfFlexible(kotlinTypeMarker), abstractTypeCheckerContext.upperBoundIfFlexible(kotlinTypeMarker2));
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.reflect.jvm.internal.impl.types.AbstractTypeChecker$checkSubtypeForIntegerLiteralType$1] */
    private final Boolean checkSubtypeForIntegerLiteralType(final AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
        if (abstractTypeCheckerContext.isIntegerLiteralType(simpleTypeMarker) || abstractTypeCheckerContext.isIntegerLiteralType(simpleTypeMarker2)) {
            ?? r0 = new Function3<SimpleTypeMarker, SimpleTypeMarker, Boolean, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeChecker$checkSubtypeForIntegerLiteralType$1
                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Boolean invoke(SimpleTypeMarker simpleTypeMarker3, SimpleTypeMarker simpleTypeMarker4, Boolean bool) {
                    return Boolean.valueOf(invoke(simpleTypeMarker3, simpleTypeMarker4, bool.booleanValue()));
                }

                public final boolean invoke(SimpleTypeMarker integerLiteralType, SimpleTypeMarker type, boolean z) {
                    boolean z2;
                    Intrinsics.checkParameterIsNotNull(integerLiteralType, "integerLiteralType");
                    Intrinsics.checkParameterIsNotNull(type, "type");
                    Collection<KotlinTypeMarker> possibleIntegerTypes = AbstractTypeCheckerContext.this.possibleIntegerTypes(integerLiteralType);
                    if (!(possibleIntegerTypes instanceof Collection) || !possibleIntegerTypes.isEmpty()) {
                        for (KotlinTypeMarker kotlinTypeMarker : possibleIntegerTypes) {
                            if (Intrinsics.areEqual(AbstractTypeCheckerContext.this.typeConstructor(kotlinTypeMarker), AbstractTypeCheckerContext.this.typeConstructor(type)) || (z && AbstractTypeChecker.INSTANCE.isSubtypeOf(AbstractTypeCheckerContext.this, type, kotlinTypeMarker))) {
                                z2 = true;
                                continue;
                            } else {
                                z2 = false;
                                continue;
                            }
                            if (z2) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            };
            if (abstractTypeCheckerContext.isIntegerLiteralType(simpleTypeMarker) && abstractTypeCheckerContext.isIntegerLiteralType(simpleTypeMarker2)) {
                return true;
            }
            if (abstractTypeCheckerContext.isIntegerLiteralType(simpleTypeMarker)) {
                if (r0.invoke(simpleTypeMarker, simpleTypeMarker2, false)) {
                    return true;
                }
            } else if (abstractTypeCheckerContext.isIntegerLiteralType(simpleTypeMarker2) && r0.invoke(simpleTypeMarker2, simpleTypeMarker, true)) {
                return true;
            }
            return null;
        }
        return null;
    }

    private final boolean hasNothingSupertype(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker simpleTypeMarker) {
        AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible lowerIfFlexible;
        TypeConstructorMarker typeConstructor = abstractTypeCheckerContext.typeConstructor(simpleTypeMarker);
        if (abstractTypeCheckerContext.isClassTypeConstructor(typeConstructor)) {
            return abstractTypeCheckerContext.isNothingConstructor(typeConstructor);
        }
        if (abstractTypeCheckerContext.isNothingConstructor(abstractTypeCheckerContext.typeConstructor(simpleTypeMarker))) {
            return true;
        }
        abstractTypeCheckerContext.initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = abstractTypeCheckerContext.getSupertypesDeque();
        if (supertypesDeque == null) {
            Intrinsics.throwNpe();
        }
        Set<SimpleTypeMarker> supertypesSet = abstractTypeCheckerContext.getSupertypesSet();
        if (supertypesSet == null) {
            Intrinsics.throwNpe();
        }
        supertypesDeque.push(simpleTypeMarker);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + simpleTypeMarker + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker current = supertypesDeque.pop();
            Intrinsics.checkExpressionValueIsNotNull(current, "current");
            if (supertypesSet.add(current)) {
                if (abstractTypeCheckerContext.isClassType(current)) {
                    lowerIfFlexible = AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE;
                } else {
                    lowerIfFlexible = AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                }
                if (!(!Intrinsics.areEqual(lowerIfFlexible, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                    lowerIfFlexible = null;
                }
                if (lowerIfFlexible != null) {
                    for (KotlinTypeMarker kotlinTypeMarker : abstractTypeCheckerContext.supertypes(abstractTypeCheckerContext.typeConstructor(current))) {
                        SimpleTypeMarker mo1096transformType = lowerIfFlexible.mo1096transformType(abstractTypeCheckerContext, kotlinTypeMarker);
                        if (abstractTypeCheckerContext.isNothingConstructor(abstractTypeCheckerContext.typeConstructor(mo1096transformType))) {
                            abstractTypeCheckerContext.clear();
                            return true;
                        }
                        supertypesDeque.add(mo1096transformType);
                    }
                    continue;
                } else {
                    continue;
                }
            }
        }
        abstractTypeCheckerContext.clear();
        return false;
    }

    private final boolean isSubtypeOfForSingleClassifierType(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
        KotlinTypeMarker type;
        if (RUN_SLOW_ASSERTIONS) {
            if (!abstractTypeCheckerContext.isSingleClassifierType(simpleTypeMarker) && !abstractTypeCheckerContext.isIntersection(abstractTypeCheckerContext.typeConstructor(simpleTypeMarker))) {
                abstractTypeCheckerContext.isAllowedTypeVariable(simpleTypeMarker);
            }
            if (!abstractTypeCheckerContext.isSingleClassifierType(simpleTypeMarker2)) {
                abstractTypeCheckerContext.isAllowedTypeVariable(simpleTypeMarker2);
            }
        }
        if (AbstractNullabilityChecker.INSTANCE.isPossibleSubtype(abstractTypeCheckerContext, simpleTypeMarker, simpleTypeMarker2)) {
            SimpleTypeMarker simpleTypeMarker3 = simpleTypeMarker;
            SimpleTypeMarker simpleTypeMarker4 = simpleTypeMarker2;
            Boolean checkSubtypeForIntegerLiteralType = checkSubtypeForIntegerLiteralType(abstractTypeCheckerContext, abstractTypeCheckerContext.lowerBoundIfFlexible(simpleTypeMarker3), abstractTypeCheckerContext.upperBoundIfFlexible(simpleTypeMarker4));
            if (checkSubtypeForIntegerLiteralType != null) {
                boolean booleanValue = checkSubtypeForIntegerLiteralType.booleanValue();
                abstractTypeCheckerContext.addSubtypeConstraint(simpleTypeMarker3, simpleTypeMarker4);
                return booleanValue;
            }
            TypeConstructorMarker typeConstructor = abstractTypeCheckerContext.typeConstructor(simpleTypeMarker2);
            if ((abstractTypeCheckerContext.isEqualTypeConstructors(abstractTypeCheckerContext.typeConstructor(simpleTypeMarker), typeConstructor) && abstractTypeCheckerContext.parametersCount(typeConstructor) == 0) || abstractTypeCheckerContext.isAnyConstructor(abstractTypeCheckerContext.typeConstructor(simpleTypeMarker2))) {
                return true;
            }
            List<SimpleTypeMarker> findCorrespondingSupertypes = findCorrespondingSupertypes(abstractTypeCheckerContext, simpleTypeMarker, typeConstructor);
            int size = findCorrespondingSupertypes.size();
            if (size != 0) {
                if (size == 1) {
                    return isSubtypeForSameConstructor(abstractTypeCheckerContext, abstractTypeCheckerContext.asArgumentList((SimpleTypeMarker) CollectionsKt.first((List<? extends Object>) findCorrespondingSupertypes)), simpleTypeMarker2);
                }
                ArgumentList argumentList = new ArgumentList(abstractTypeCheckerContext.parametersCount(typeConstructor));
                int parametersCount = abstractTypeCheckerContext.parametersCount(typeConstructor);
                boolean z = false;
                for (int i = 0; i < parametersCount; i++) {
                    z = z || abstractTypeCheckerContext.getVariance(abstractTypeCheckerContext.getParameter(typeConstructor, i)) != TypeVariance.OUT;
                    if (!z) {
                        List<SimpleTypeMarker> list = findCorrespondingSupertypes;
                        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                        for (SimpleTypeMarker simpleTypeMarker5 : list) {
                            TypeArgumentMarker argumentOrNull = abstractTypeCheckerContext.getArgumentOrNull(simpleTypeMarker5, i);
                            if (argumentOrNull != null) {
                                if (!(abstractTypeCheckerContext.getVariance(argumentOrNull) == TypeVariance.INV)) {
                                    argumentOrNull = null;
                                }
                                if (argumentOrNull != null && (type = abstractTypeCheckerContext.getType(argumentOrNull)) != null) {
                                    arrayList.add(type);
                                }
                            }
                            throw new IllegalStateException(("Incorrect type: " + simpleTypeMarker5 + ", subType: " + simpleTypeMarker + ", superType: " + simpleTypeMarker2).toString());
                        }
                        argumentList.add(abstractTypeCheckerContext.asTypeArgument(abstractTypeCheckerContext.intersectTypes(arrayList)));
                    }
                }
                if (z || !isSubtypeForSameConstructor(abstractTypeCheckerContext, argumentList, simpleTypeMarker2)) {
                    List<SimpleTypeMarker> list2 = findCorrespondingSupertypes;
                    if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                        for (SimpleTypeMarker simpleTypeMarker6 : list2) {
                            if (INSTANCE.isSubtypeForSameConstructor(abstractTypeCheckerContext, abstractTypeCheckerContext.asArgumentList(simpleTypeMarker6), simpleTypeMarker2)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
                return true;
            }
            return hasNothingSupertype(abstractTypeCheckerContext, simpleTypeMarker);
        }
        return false;
    }

    public final boolean isSubtypeForSameConstructor(AbstractTypeCheckerContext isSubtypeForSameConstructor, TypeArgumentListMarker capturedSubArguments, SimpleTypeMarker superType) {
        int i;
        int i2;
        boolean equalTypes;
        int i3;
        Intrinsics.checkParameterIsNotNull(isSubtypeForSameConstructor, "$this$isSubtypeForSameConstructor");
        Intrinsics.checkParameterIsNotNull(capturedSubArguments, "capturedSubArguments");
        Intrinsics.checkParameterIsNotNull(superType, "superType");
        TypeConstructorMarker typeConstructor = isSubtypeForSameConstructor.typeConstructor(superType);
        int parametersCount = isSubtypeForSameConstructor.parametersCount(typeConstructor);
        for (int i4 = 0; i4 < parametersCount; i4++) {
            TypeArgumentMarker argument = isSubtypeForSameConstructor.getArgument(superType, i4);
            if (!isSubtypeForSameConstructor.isStarProjection(argument)) {
                KotlinTypeMarker type = isSubtypeForSameConstructor.getType(argument);
                TypeArgumentMarker typeArgumentMarker = isSubtypeForSameConstructor.get(capturedSubArguments, i4);
                isSubtypeForSameConstructor.getVariance(typeArgumentMarker);
                TypeVariance typeVariance = TypeVariance.INV;
                KotlinTypeMarker type2 = isSubtypeForSameConstructor.getType(typeArgumentMarker);
                TypeVariance effectiveVariance = effectiveVariance(isSubtypeForSameConstructor.getVariance(isSubtypeForSameConstructor.getParameter(typeConstructor, i4)), isSubtypeForSameConstructor.getVariance(argument));
                if (effectiveVariance != null) {
                    i = isSubtypeForSameConstructor.argumentsDepth;
                    if (i > 100) {
                        throw new IllegalStateException(("Arguments depth is too high. Some related argument: " + type2).toString());
                    }
                    i2 = isSubtypeForSameConstructor.argumentsDepth;
                    isSubtypeForSameConstructor.argumentsDepth = i2 + 1;
                    int i5 = WhenMappings.$EnumSwitchMapping$0[effectiveVariance.ordinal()];
                    if (i5 == 1) {
                        equalTypes = INSTANCE.equalTypes(isSubtypeForSameConstructor, type2, type);
                    } else if (i5 == 2) {
                        equalTypes = INSTANCE.isSubtypeOf(isSubtypeForSameConstructor, type2, type);
                    } else if (i5 != 3) {
                        throw new NoWhenBranchMatchedException();
                    } else {
                        equalTypes = INSTANCE.isSubtypeOf(isSubtypeForSameConstructor, type, type2);
                    }
                    i3 = isSubtypeForSameConstructor.argumentsDepth;
                    isSubtypeForSameConstructor.argumentsDepth = i3 - 1;
                    if (!equalTypes) {
                        return false;
                    }
                } else {
                    return isSubtypeForSameConstructor.isErrorTypeEqualsToAnything();
                }
            }
        }
        return true;
    }

    private final boolean isCommonDenotableType(AbstractTypeCheckerContext abstractTypeCheckerContext, KotlinTypeMarker kotlinTypeMarker) {
        return abstractTypeCheckerContext.isDenotable(abstractTypeCheckerContext.typeConstructor(kotlinTypeMarker)) && !abstractTypeCheckerContext.isDynamic(kotlinTypeMarker) && !abstractTypeCheckerContext.isDefinitelyNotNullType(kotlinTypeMarker) && Intrinsics.areEqual(abstractTypeCheckerContext.typeConstructor(abstractTypeCheckerContext.lowerBoundIfFlexible(kotlinTypeMarker)), abstractTypeCheckerContext.typeConstructor(abstractTypeCheckerContext.upperBoundIfFlexible(kotlinTypeMarker)));
    }

    public final TypeVariance effectiveVariance(TypeVariance declared, TypeVariance useSite) {
        Intrinsics.checkParameterIsNotNull(declared, "declared");
        Intrinsics.checkParameterIsNotNull(useSite, "useSite");
        if (declared == TypeVariance.INV) {
            return useSite;
        }
        if (useSite == TypeVariance.INV || declared == useSite) {
            return declared;
        }
        return null;
    }

    private final Boolean checkSubtypeForSpecialCases(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
        SimpleTypeMarker simpleTypeMarker3 = simpleTypeMarker;
        boolean z = false;
        if (abstractTypeCheckerContext.isError(simpleTypeMarker3) || abstractTypeCheckerContext.isError(simpleTypeMarker2)) {
            if (abstractTypeCheckerContext.isErrorTypeEqualsToAnything()) {
                return true;
            }
            if (!abstractTypeCheckerContext.isMarkedNullable(simpleTypeMarker) || abstractTypeCheckerContext.isMarkedNullable(simpleTypeMarker2)) {
                return Boolean.valueOf(AbstractStrictEqualityTypeChecker.INSTANCE.strictEqualTypes(abstractTypeCheckerContext, abstractTypeCheckerContext.withNullability(simpleTypeMarker, false), abstractTypeCheckerContext.withNullability(simpleTypeMarker2, false)));
            }
            return false;
        } else if (abstractTypeCheckerContext.isStubType(simpleTypeMarker) || abstractTypeCheckerContext.isStubType(simpleTypeMarker2)) {
            return Boolean.valueOf(abstractTypeCheckerContext.isStubTypeEqualsToAnything());
        } else {
            CapturedTypeMarker asCapturedType = abstractTypeCheckerContext.asCapturedType(simpleTypeMarker2);
            KotlinTypeMarker lowerType = asCapturedType != null ? abstractTypeCheckerContext.lowerType(asCapturedType) : null;
            if (asCapturedType != null && lowerType != null) {
                int i = WhenMappings.$EnumSwitchMapping$1[abstractTypeCheckerContext.getLowerCapturedTypePolicy(simpleTypeMarker, asCapturedType).ordinal()];
                if (i == 1) {
                    return Boolean.valueOf(isSubtypeOf(abstractTypeCheckerContext, simpleTypeMarker3, lowerType));
                }
                if (i == 2 && isSubtypeOf(abstractTypeCheckerContext, simpleTypeMarker3, lowerType)) {
                    return true;
                }
            }
            TypeConstructorMarker typeConstructor = abstractTypeCheckerContext.typeConstructor(simpleTypeMarker2);
            if (abstractTypeCheckerContext.isIntersection(typeConstructor)) {
                abstractTypeCheckerContext.isMarkedNullable(simpleTypeMarker2);
                Collection<KotlinTypeMarker> supertypes = abstractTypeCheckerContext.supertypes(typeConstructor);
                if (!(supertypes instanceof Collection) || !supertypes.isEmpty()) {
                    for (KotlinTypeMarker kotlinTypeMarker : supertypes) {
                        if (!INSTANCE.isSubtypeOf(abstractTypeCheckerContext, simpleTypeMarker3, kotlinTypeMarker)) {
                            break;
                        }
                    }
                }
                z = true;
                return Boolean.valueOf(z);
            }
            return null;
        }
    }

    private final List<SimpleTypeMarker> collectAllSupertypesWithGivenTypeConstructor(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker typeConstructorMarker) {
        AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible substitutionSupertypePolicy;
        List<SimpleTypeMarker> fastCorrespondingSupertypes = abstractTypeCheckerContext.fastCorrespondingSupertypes(simpleTypeMarker, typeConstructorMarker);
        if (fastCorrespondingSupertypes != null) {
            return fastCorrespondingSupertypes;
        }
        if (abstractTypeCheckerContext.isClassTypeConstructor(typeConstructorMarker) || !abstractTypeCheckerContext.isClassType(simpleTypeMarker)) {
            if (abstractTypeCheckerContext.isCommonFinalClassConstructor(typeConstructorMarker)) {
                if (abstractTypeCheckerContext.areEqualTypeConstructors(abstractTypeCheckerContext.typeConstructor(simpleTypeMarker), typeConstructorMarker)) {
                    SimpleTypeMarker captureFromArguments = abstractTypeCheckerContext.captureFromArguments(simpleTypeMarker, CaptureStatus.FOR_SUBTYPING);
                    if (captureFromArguments != null) {
                        simpleTypeMarker = captureFromArguments;
                    }
                    return CollectionsKt.listOf(simpleTypeMarker);
                }
                return CollectionsKt.emptyList();
            }
            SmartList smartList = new SmartList();
            abstractTypeCheckerContext.initialize();
            ArrayDeque<SimpleTypeMarker> supertypesDeque = abstractTypeCheckerContext.getSupertypesDeque();
            if (supertypesDeque == null) {
                Intrinsics.throwNpe();
            }
            Set<SimpleTypeMarker> supertypesSet = abstractTypeCheckerContext.getSupertypesSet();
            if (supertypesSet == null) {
                Intrinsics.throwNpe();
            }
            supertypesDeque.push(simpleTypeMarker);
            while (!supertypesDeque.isEmpty()) {
                if (supertypesSet.size() > 1000) {
                    throw new IllegalStateException(("Too many supertypes for type: " + simpleTypeMarker + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
                }
                SimpleTypeMarker current = supertypesDeque.pop();
                Intrinsics.checkExpressionValueIsNotNull(current, "current");
                if (supertypesSet.add(current)) {
                    SimpleTypeMarker captureFromArguments2 = abstractTypeCheckerContext.captureFromArguments(current, CaptureStatus.FOR_SUBTYPING);
                    if (captureFromArguments2 == null) {
                        captureFromArguments2 = current;
                    }
                    if (abstractTypeCheckerContext.areEqualTypeConstructors(abstractTypeCheckerContext.typeConstructor(captureFromArguments2), typeConstructorMarker)) {
                        smartList.add(captureFromArguments2);
                        substitutionSupertypePolicy = AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE;
                    } else if (abstractTypeCheckerContext.argumentsCount(captureFromArguments2) == 0) {
                        substitutionSupertypePolicy = AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                    } else {
                        substitutionSupertypePolicy = abstractTypeCheckerContext.substitutionSupertypePolicy(captureFromArguments2);
                    }
                    if (!(!Intrinsics.areEqual(substitutionSupertypePolicy, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                        substitutionSupertypePolicy = null;
                    }
                    if (substitutionSupertypePolicy != null) {
                        for (KotlinTypeMarker kotlinTypeMarker : abstractTypeCheckerContext.supertypes(abstractTypeCheckerContext.typeConstructor(current))) {
                            supertypesDeque.add(substitutionSupertypePolicy.mo1096transformType(abstractTypeCheckerContext, kotlinTypeMarker));
                        }
                    }
                }
            }
            abstractTypeCheckerContext.clear();
            return smartList;
        }
        return CollectionsKt.emptyList();
    }

    private final List<SimpleTypeMarker> collectAndFilter(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker typeConstructorMarker) {
        return selectOnlyPureKotlinSupertypes(abstractTypeCheckerContext, collectAllSupertypesWithGivenTypeConstructor(abstractTypeCheckerContext, simpleTypeMarker, typeConstructorMarker));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<SimpleTypeMarker> selectOnlyPureKotlinSupertypes(AbstractTypeCheckerContext abstractTypeCheckerContext, List<? extends SimpleTypeMarker> list) {
        if (list.size() < 2) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            TypeArgumentListMarker asArgumentList = abstractTypeCheckerContext.asArgumentList((SimpleTypeMarker) next);
            AbstractTypeCheckerContext abstractTypeCheckerContext2 = abstractTypeCheckerContext;
            int size = abstractTypeCheckerContext2.size(asArgumentList);
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (!(abstractTypeCheckerContext.asFlexibleType(abstractTypeCheckerContext.getType(abstractTypeCheckerContext2.get(asArgumentList, i))) == null)) {
                    z = false;
                    break;
                }
                i++;
            }
            if (z) {
                arrayList.add(next);
            }
        }
        ArrayList arrayList2 = arrayList;
        return arrayList2.isEmpty() ^ true ? arrayList2 : list;
    }

    public final List<SimpleTypeMarker> findCorrespondingSupertypes(AbstractTypeCheckerContext findCorrespondingSupertypes, SimpleTypeMarker subType, TypeConstructorMarker superConstructor) {
        AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible lowerIfFlexible;
        Intrinsics.checkParameterIsNotNull(findCorrespondingSupertypes, "$this$findCorrespondingSupertypes");
        Intrinsics.checkParameterIsNotNull(subType, "subType");
        Intrinsics.checkParameterIsNotNull(superConstructor, "superConstructor");
        if (findCorrespondingSupertypes.isClassType(subType)) {
            return collectAndFilter(findCorrespondingSupertypes, subType, superConstructor);
        }
        if (!findCorrespondingSupertypes.isClassTypeConstructor(superConstructor) && !findCorrespondingSupertypes.isIntegerLiteralTypeConstructor(superConstructor)) {
            return collectAllSupertypesWithGivenTypeConstructor(findCorrespondingSupertypes, subType, superConstructor);
        }
        SmartList<SimpleTypeMarker> smartList = new SmartList();
        findCorrespondingSupertypes.initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = findCorrespondingSupertypes.getSupertypesDeque();
        if (supertypesDeque == null) {
            Intrinsics.throwNpe();
        }
        Set<SimpleTypeMarker> supertypesSet = findCorrespondingSupertypes.getSupertypesSet();
        if (supertypesSet == null) {
            Intrinsics.throwNpe();
        }
        supertypesDeque.push(subType);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + subType + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker current = supertypesDeque.pop();
            Intrinsics.checkExpressionValueIsNotNull(current, "current");
            if (supertypesSet.add(current)) {
                if (findCorrespondingSupertypes.isClassType(current)) {
                    smartList.add(current);
                    lowerIfFlexible = AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE;
                } else {
                    lowerIfFlexible = AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                }
                if (!(!Intrinsics.areEqual(lowerIfFlexible, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                    lowerIfFlexible = null;
                }
                if (lowerIfFlexible != null) {
                    for (KotlinTypeMarker kotlinTypeMarker : findCorrespondingSupertypes.supertypes(findCorrespondingSupertypes.typeConstructor(current))) {
                        supertypesDeque.add(lowerIfFlexible.mo1096transformType(findCorrespondingSupertypes, kotlinTypeMarker));
                    }
                }
            }
        }
        findCorrespondingSupertypes.clear();
        ArrayList arrayList = new ArrayList();
        for (SimpleTypeMarker it : smartList) {
            AbstractTypeChecker abstractTypeChecker = INSTANCE;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            CollectionsKt.addAll(arrayList, abstractTypeChecker.collectAndFilter(findCorrespondingSupertypes, it, superConstructor));
        }
        return arrayList;
    }
}
