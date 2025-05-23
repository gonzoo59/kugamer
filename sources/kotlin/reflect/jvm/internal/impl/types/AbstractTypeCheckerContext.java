package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
/* compiled from: AbstractTypeChecker.kt */
/* loaded from: classes2.dex */
public abstract class AbstractTypeCheckerContext implements TypeSystemContext {
    private int argumentsDepth;
    private ArrayDeque<SimpleTypeMarker> supertypesDeque;
    private boolean supertypesLocked;
    private Set<SimpleTypeMarker> supertypesSet;

    /* compiled from: AbstractTypeChecker.kt */
    /* loaded from: classes2.dex */
    public enum LowerCapturedTypePolicy {
        CHECK_ONLY_LOWER,
        CHECK_SUBTYPE_AND_LOWER,
        SKIP_LOWER
    }

    public Boolean addSubtypeConstraint(KotlinTypeMarker subType, KotlinTypeMarker superType) {
        Intrinsics.checkParameterIsNotNull(subType, "subType");
        Intrinsics.checkParameterIsNotNull(superType, "superType");
        return null;
    }

    public abstract boolean areEqualTypeConstructors(TypeConstructorMarker typeConstructorMarker, TypeConstructorMarker typeConstructorMarker2);

    public abstract boolean isAllowedTypeVariable(KotlinTypeMarker kotlinTypeMarker);

    public abstract boolean isErrorTypeEqualsToAnything();

    public abstract boolean isStubTypeEqualsToAnything();

    public KotlinTypeMarker prepareType(KotlinTypeMarker type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        return type;
    }

    public KotlinTypeMarker refineType(KotlinTypeMarker type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        return type;
    }

    public abstract SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker simpleTypeMarker);

    public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker fastCorrespondingSupertypes, TypeConstructorMarker constructor) {
        Intrinsics.checkParameterIsNotNull(fastCorrespondingSupertypes, "$this$fastCorrespondingSupertypes");
        Intrinsics.checkParameterIsNotNull(constructor, "constructor");
        return TypeSystemContext.DefaultImpls.fastCorrespondingSupertypes(this, fastCorrespondingSupertypes, constructor);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeArgumentMarker get(TypeArgumentListMarker get, int i) {
        Intrinsics.checkParameterIsNotNull(get, "$this$get");
        return TypeSystemContext.DefaultImpls.get(this, get, i);
    }

    public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker getArgumentOrNull, int i) {
        Intrinsics.checkParameterIsNotNull(getArgumentOrNull, "$this$getArgumentOrNull");
        return TypeSystemContext.DefaultImpls.getArgumentOrNull(this, getArgumentOrNull, i);
    }

    public boolean hasFlexibleNullability(KotlinTypeMarker hasFlexibleNullability) {
        Intrinsics.checkParameterIsNotNull(hasFlexibleNullability, "$this$hasFlexibleNullability");
        return TypeSystemContext.DefaultImpls.hasFlexibleNullability(this, hasFlexibleNullability);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemOptimizationContext
    public boolean identicalArguments(SimpleTypeMarker a, SimpleTypeMarker b) {
        Intrinsics.checkParameterIsNotNull(a, "a");
        Intrinsics.checkParameterIsNotNull(b, "b");
        return TypeSystemContext.DefaultImpls.identicalArguments(this, a, b);
    }

    public boolean isClassType(SimpleTypeMarker isClassType) {
        Intrinsics.checkParameterIsNotNull(isClassType, "$this$isClassType");
        return TypeSystemContext.DefaultImpls.isClassType(this, isClassType);
    }

    public boolean isDefinitelyNotNullType(KotlinTypeMarker isDefinitelyNotNullType) {
        Intrinsics.checkParameterIsNotNull(isDefinitelyNotNullType, "$this$isDefinitelyNotNullType");
        return TypeSystemContext.DefaultImpls.isDefinitelyNotNullType(this, isDefinitelyNotNullType);
    }

    public boolean isDynamic(KotlinTypeMarker isDynamic) {
        Intrinsics.checkParameterIsNotNull(isDynamic, "$this$isDynamic");
        return TypeSystemContext.DefaultImpls.isDynamic(this, isDynamic);
    }

    public boolean isIntegerLiteralType(SimpleTypeMarker isIntegerLiteralType) {
        Intrinsics.checkParameterIsNotNull(isIntegerLiteralType, "$this$isIntegerLiteralType");
        return TypeSystemContext.DefaultImpls.isIntegerLiteralType(this, isIntegerLiteralType);
    }

    public boolean isNothing(KotlinTypeMarker isNothing) {
        Intrinsics.checkParameterIsNotNull(isNothing, "$this$isNothing");
        return TypeSystemContext.DefaultImpls.isNothing(this, isNothing);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker lowerBoundIfFlexible) {
        Intrinsics.checkParameterIsNotNull(lowerBoundIfFlexible, "$this$lowerBoundIfFlexible");
        return TypeSystemContext.DefaultImpls.lowerBoundIfFlexible(this, lowerBoundIfFlexible);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public int size(TypeArgumentListMarker size) {
        Intrinsics.checkParameterIsNotNull(size, "$this$size");
        return TypeSystemContext.DefaultImpls.size(this, size);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(KotlinTypeMarker typeConstructor) {
        Intrinsics.checkParameterIsNotNull(typeConstructor, "$this$typeConstructor");
        return TypeSystemContext.DefaultImpls.typeConstructor(this, typeConstructor);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker upperBoundIfFlexible) {
        Intrinsics.checkParameterIsNotNull(upperBoundIfFlexible, "$this$upperBoundIfFlexible");
        return TypeSystemContext.DefaultImpls.upperBoundIfFlexible(this, upperBoundIfFlexible);
    }

    public LowerCapturedTypePolicy getLowerCapturedTypePolicy(SimpleTypeMarker subType, CapturedTypeMarker superType) {
        Intrinsics.checkParameterIsNotNull(subType, "subType");
        Intrinsics.checkParameterIsNotNull(superType, "superType");
        return LowerCapturedTypePolicy.CHECK_SUBTYPE_AND_LOWER;
    }

    public final ArrayDeque<SimpleTypeMarker> getSupertypesDeque() {
        return this.supertypesDeque;
    }

    public final Set<SimpleTypeMarker> getSupertypesSet() {
        return this.supertypesSet;
    }

    public final void initialize() {
        this.supertypesLocked = true;
        if (this.supertypesDeque == null) {
            this.supertypesDeque = new ArrayDeque<>(4);
        }
        if (this.supertypesSet == null) {
            this.supertypesSet = SmartSet.Companion.create();
        }
    }

    public final void clear() {
        ArrayDeque<SimpleTypeMarker> arrayDeque = this.supertypesDeque;
        if (arrayDeque == null) {
            Intrinsics.throwNpe();
        }
        arrayDeque.clear();
        Set<SimpleTypeMarker> set = this.supertypesSet;
        if (set == null) {
            Intrinsics.throwNpe();
        }
        set.clear();
        this.supertypesLocked = false;
    }

    /* compiled from: AbstractTypeChecker.kt */
    /* loaded from: classes2.dex */
    public static abstract class SupertypesPolicy {
        /* renamed from: transformType */
        public abstract SimpleTypeMarker mo1096transformType(AbstractTypeCheckerContext abstractTypeCheckerContext, KotlinTypeMarker kotlinTypeMarker);

        private SupertypesPolicy() {
        }

        public /* synthetic */ SupertypesPolicy(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* compiled from: AbstractTypeChecker.kt */
        /* loaded from: classes2.dex */
        public static final class None extends SupertypesPolicy {
            public static final None INSTANCE = new None();

            private None() {
                super(null);
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext.SupertypesPolicy
            /* renamed from: transformType  reason: collision with other method in class */
            public /* bridge */ /* synthetic */ SimpleTypeMarker mo1096transformType(AbstractTypeCheckerContext abstractTypeCheckerContext, KotlinTypeMarker kotlinTypeMarker) {
                return (SimpleTypeMarker) transformType(abstractTypeCheckerContext, kotlinTypeMarker);
            }

            public Void transformType(AbstractTypeCheckerContext context, KotlinTypeMarker type) {
                Intrinsics.checkParameterIsNotNull(context, "context");
                Intrinsics.checkParameterIsNotNull(type, "type");
                throw new UnsupportedOperationException("Should not be called");
            }
        }

        /* compiled from: AbstractTypeChecker.kt */
        /* loaded from: classes2.dex */
        public static final class UpperIfFlexible extends SupertypesPolicy {
            public static final UpperIfFlexible INSTANCE = new UpperIfFlexible();

            private UpperIfFlexible() {
                super(null);
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext.SupertypesPolicy
            /* renamed from: transformType */
            public SimpleTypeMarker mo1096transformType(AbstractTypeCheckerContext context, KotlinTypeMarker type) {
                Intrinsics.checkParameterIsNotNull(context, "context");
                Intrinsics.checkParameterIsNotNull(type, "type");
                return context.upperBoundIfFlexible(type);
            }
        }

        /* compiled from: AbstractTypeChecker.kt */
        /* loaded from: classes2.dex */
        public static final class LowerIfFlexible extends SupertypesPolicy {
            public static final LowerIfFlexible INSTANCE = new LowerIfFlexible();

            private LowerIfFlexible() {
                super(null);
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext.SupertypesPolicy
            /* renamed from: transformType */
            public SimpleTypeMarker mo1096transformType(AbstractTypeCheckerContext context, KotlinTypeMarker type) {
                Intrinsics.checkParameterIsNotNull(context, "context");
                Intrinsics.checkParameterIsNotNull(type, "type");
                return context.lowerBoundIfFlexible(type);
            }
        }

        /* compiled from: AbstractTypeChecker.kt */
        /* loaded from: classes2.dex */
        public static abstract class DoCustomTransform extends SupertypesPolicy {
            public DoCustomTransform() {
                super(null);
            }
        }
    }
}
