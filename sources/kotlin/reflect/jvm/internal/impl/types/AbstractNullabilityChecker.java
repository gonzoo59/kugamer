package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayDeque;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
/* compiled from: AbstractTypeChecker.kt */
/* loaded from: classes2.dex */
public final class AbstractNullabilityChecker {
    public static final AbstractNullabilityChecker INSTANCE = new AbstractNullabilityChecker();

    private AbstractNullabilityChecker() {
    }

    public final boolean isPossibleSubtype(AbstractTypeCheckerContext context, SimpleTypeMarker subType, SimpleTypeMarker superType) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(subType, "subType");
        Intrinsics.checkParameterIsNotNull(superType, "superType");
        return runIsPossibleSubtype(context, subType, superType);
    }

    private final boolean runIsPossibleSubtype(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
        if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS) {
            if (!abstractTypeCheckerContext.isSingleClassifierType(simpleTypeMarker) && !abstractTypeCheckerContext.isIntersection(abstractTypeCheckerContext.typeConstructor(simpleTypeMarker))) {
                abstractTypeCheckerContext.isAllowedTypeVariable(simpleTypeMarker);
            }
            if (!abstractTypeCheckerContext.isSingleClassifierType(simpleTypeMarker2)) {
                abstractTypeCheckerContext.isAllowedTypeVariable(simpleTypeMarker2);
            }
        }
        if (abstractTypeCheckerContext.isMarkedNullable(simpleTypeMarker2) || abstractTypeCheckerContext.isDefinitelyNotNullType(simpleTypeMarker) || hasNotNullSupertype(abstractTypeCheckerContext, simpleTypeMarker, AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE)) {
            return true;
        }
        if (abstractTypeCheckerContext.isDefinitelyNotNullType(simpleTypeMarker2) || hasNotNullSupertype(abstractTypeCheckerContext, simpleTypeMarker2, AbstractTypeCheckerContext.SupertypesPolicy.UpperIfFlexible.INSTANCE) || abstractTypeCheckerContext.isClassType(simpleTypeMarker)) {
            return false;
        }
        return hasPathByNotMarkedNullableNodes(abstractTypeCheckerContext, simpleTypeMarker, abstractTypeCheckerContext.typeConstructor(simpleTypeMarker2));
    }

    public final boolean hasNotNullSupertype(AbstractTypeCheckerContext hasNotNullSupertype, SimpleTypeMarker type, AbstractTypeCheckerContext.SupertypesPolicy supertypesPolicy) {
        Intrinsics.checkParameterIsNotNull(hasNotNullSupertype, "$this$hasNotNullSupertype");
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(supertypesPolicy, "supertypesPolicy");
        if (!((hasNotNullSupertype.isClassType(type) && !hasNotNullSupertype.isMarkedNullable(type)) || hasNotNullSupertype.isDefinitelyNotNullType(type))) {
            hasNotNullSupertype.initialize();
            ArrayDeque<SimpleTypeMarker> supertypesDeque = hasNotNullSupertype.getSupertypesDeque();
            if (supertypesDeque == null) {
                Intrinsics.throwNpe();
            }
            Set<SimpleTypeMarker> supertypesSet = hasNotNullSupertype.getSupertypesSet();
            if (supertypesSet == null) {
                Intrinsics.throwNpe();
            }
            supertypesDeque.push(type);
            while (!supertypesDeque.isEmpty()) {
                if (supertypesSet.size() > 1000) {
                    throw new IllegalStateException(("Too many supertypes for type: " + type + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
                }
                SimpleTypeMarker current = supertypesDeque.pop();
                Intrinsics.checkExpressionValueIsNotNull(current, "current");
                if (supertypesSet.add(current)) {
                    AbstractTypeCheckerContext.SupertypesPolicy.None none = hasNotNullSupertype.isMarkedNullable(current) ? AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE : supertypesPolicy;
                    if (!(!Intrinsics.areEqual(none, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                        none = null;
                    }
                    if (none != null) {
                        for (KotlinTypeMarker kotlinTypeMarker : hasNotNullSupertype.supertypes(hasNotNullSupertype.typeConstructor(current))) {
                            SimpleTypeMarker mo1096transformType = none.mo1096transformType(hasNotNullSupertype, kotlinTypeMarker);
                            if ((hasNotNullSupertype.isClassType(mo1096transformType) && !hasNotNullSupertype.isMarkedNullable(mo1096transformType)) || hasNotNullSupertype.isDefinitelyNotNullType(mo1096transformType)) {
                                hasNotNullSupertype.clear();
                            } else {
                                supertypesDeque.add(mo1096transformType);
                            }
                        }
                        continue;
                    } else {
                        continue;
                    }
                }
            }
            hasNotNullSupertype.clear();
            return false;
        }
        return true;
    }

    public final boolean hasPathByNotMarkedNullableNodes(AbstractTypeCheckerContext hasPathByNotMarkedNullableNodes, SimpleTypeMarker start, TypeConstructorMarker end) {
        Intrinsics.checkParameterIsNotNull(hasPathByNotMarkedNullableNodes, "$this$hasPathByNotMarkedNullableNodes");
        Intrinsics.checkParameterIsNotNull(start, "start");
        Intrinsics.checkParameterIsNotNull(end, "end");
        if (INSTANCE.isApplicableAsEndNode(hasPathByNotMarkedNullableNodes, start, end)) {
            return true;
        }
        hasPathByNotMarkedNullableNodes.initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = hasPathByNotMarkedNullableNodes.getSupertypesDeque();
        if (supertypesDeque == null) {
            Intrinsics.throwNpe();
        }
        Set<SimpleTypeMarker> supertypesSet = hasPathByNotMarkedNullableNodes.getSupertypesSet();
        if (supertypesSet == null) {
            Intrinsics.throwNpe();
        }
        supertypesDeque.push(start);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + start + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker current = supertypesDeque.pop();
            Intrinsics.checkExpressionValueIsNotNull(current, "current");
            if (supertypesSet.add(current)) {
                AbstractTypeCheckerContext.SupertypesPolicy supertypesPolicy = hasPathByNotMarkedNullableNodes.isMarkedNullable(current) ? AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE : AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                if (!(!Intrinsics.areEqual(supertypesPolicy, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                    supertypesPolicy = null;
                }
                if (supertypesPolicy != null) {
                    for (KotlinTypeMarker kotlinTypeMarker : hasPathByNotMarkedNullableNodes.supertypes(hasPathByNotMarkedNullableNodes.typeConstructor(current))) {
                        SimpleTypeMarker mo1096transformType = supertypesPolicy.mo1096transformType(hasPathByNotMarkedNullableNodes, kotlinTypeMarker);
                        if (INSTANCE.isApplicableAsEndNode(hasPathByNotMarkedNullableNodes, mo1096transformType, end)) {
                            hasPathByNotMarkedNullableNodes.clear();
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
        hasPathByNotMarkedNullableNodes.clear();
        return false;
    }

    private final boolean isApplicableAsEndNode(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker typeConstructorMarker) {
        if (abstractTypeCheckerContext.isNothing(simpleTypeMarker)) {
            return true;
        }
        if (abstractTypeCheckerContext.isMarkedNullable(simpleTypeMarker)) {
            return false;
        }
        if (abstractTypeCheckerContext.isStubTypeEqualsToAnything() && abstractTypeCheckerContext.isStubType(simpleTypeMarker)) {
            return true;
        }
        return abstractTypeCheckerContext.isEqualTypeConstructors(abstractTypeCheckerContext.typeConstructor(simpleTypeMarker), typeConstructorMarker);
    }
}
