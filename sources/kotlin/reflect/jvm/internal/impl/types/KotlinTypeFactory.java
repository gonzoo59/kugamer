package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleAwareClassDescriptorKt;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.TypeAliasExpansionReportStrategy;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
/* compiled from: KotlinTypeFactory.kt */
/* loaded from: classes2.dex */
public final class KotlinTypeFactory {
    public static final KotlinTypeFactory INSTANCE = new KotlinTypeFactory();
    private static final Function1<KotlinTypeRefiner, SimpleType> EMPTY_REFINED_TYPE_FACTORY = new Function1() { // from class: kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory$EMPTY_REFINED_TYPE_FACTORY$1
        @Override // kotlin.jvm.functions.Function1
        public final Void invoke(KotlinTypeRefiner kotlinTypeRefiner) {
            Intrinsics.checkParameterIsNotNull(kotlinTypeRefiner, "<anonymous parameter 0>");
            return null;
        }
    };

    private KotlinTypeFactory() {
    }

    private final MemberScope computeMemberScope(TypeConstructor typeConstructor, List<? extends TypeProjection> list, KotlinTypeRefiner kotlinTypeRefiner) {
        ClassifierDescriptor mo1092getDeclarationDescriptor = typeConstructor.mo1092getDeclarationDescriptor();
        if (mo1092getDeclarationDescriptor instanceof TypeParameterDescriptor) {
            return mo1092getDeclarationDescriptor.getDefaultType().getMemberScope();
        }
        if (mo1092getDeclarationDescriptor instanceof ClassDescriptor) {
            if (kotlinTypeRefiner == null) {
                kotlinTypeRefiner = DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtilsKt.getModule(mo1092getDeclarationDescriptor));
            }
            if (list.isEmpty()) {
                return ModuleAwareClassDescriptorKt.getRefinedUnsubstitutedMemberScopeIfPossible((ClassDescriptor) mo1092getDeclarationDescriptor, kotlinTypeRefiner);
            }
            return ModuleAwareClassDescriptorKt.getRefinedMemberScopeIfPossible((ClassDescriptor) mo1092getDeclarationDescriptor, TypeConstructorSubstitution.Companion.create(typeConstructor, list), kotlinTypeRefiner);
        } else if (mo1092getDeclarationDescriptor instanceof TypeAliasDescriptor) {
            MemberScope createErrorScope = ErrorUtils.createErrorScope("Scope for abbreviation: " + ((TypeAliasDescriptor) mo1092getDeclarationDescriptor).getName(), true);
            Intrinsics.checkExpressionValueIsNotNull(createErrorScope, "ErrorUtils.createErrorSc…{descriptor.name}\", true)");
            return createErrorScope;
        } else {
            throw new IllegalStateException("Unsupported classifier: " + mo1092getDeclarationDescriptor + " for constructor: " + typeConstructor);
        }
    }

    public static /* synthetic */ SimpleType simpleType$default(Annotations annotations, TypeConstructor typeConstructor, List list, boolean z, KotlinTypeRefiner kotlinTypeRefiner, int i, Object obj) {
        if ((i & 16) != 0) {
            kotlinTypeRefiner = null;
        }
        return simpleType(annotations, typeConstructor, list, z, kotlinTypeRefiner);
    }

    @JvmStatic
    public static final SimpleType simpleType(final Annotations annotations, final TypeConstructor constructor, final List<? extends TypeProjection> arguments, final boolean z, KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        Intrinsics.checkParameterIsNotNull(constructor, "constructor");
        Intrinsics.checkParameterIsNotNull(arguments, "arguments");
        if (annotations.isEmpty() && arguments.isEmpty() && !z && constructor.mo1092getDeclarationDescriptor() != null) {
            ClassifierDescriptor mo1092getDeclarationDescriptor = constructor.mo1092getDeclarationDescriptor();
            if (mo1092getDeclarationDescriptor == null) {
                Intrinsics.throwNpe();
            }
            Intrinsics.checkExpressionValueIsNotNull(mo1092getDeclarationDescriptor, "constructor.declarationDescriptor!!");
            SimpleType defaultType = mo1092getDeclarationDescriptor.getDefaultType();
            Intrinsics.checkExpressionValueIsNotNull(defaultType, "constructor.declarationDescriptor!!.defaultType");
            return defaultType;
        }
        return simpleTypeWithNonTrivialMemberScope(annotations, constructor, arguments, z, INSTANCE.computeMemberScope(constructor, arguments, kotlinTypeRefiner), new Function1<KotlinTypeRefiner, SimpleType>() { // from class: kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory$simpleType$1
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SimpleType invoke(KotlinTypeRefiner refiner) {
                KotlinTypeFactory.ExpandedTypeOrRefinedConstructor refineConstructor;
                Intrinsics.checkParameterIsNotNull(refiner, "refiner");
                refineConstructor = KotlinTypeFactory.INSTANCE.refineConstructor(TypeConstructor.this, refiner, arguments);
                if (refineConstructor != null) {
                    SimpleType expandedType = refineConstructor.getExpandedType();
                    if (expandedType != null) {
                        return expandedType;
                    }
                    Annotations annotations2 = annotations;
                    TypeConstructor refinedConstructor = refineConstructor.getRefinedConstructor();
                    if (refinedConstructor == null) {
                        Intrinsics.throwNpe();
                    }
                    return KotlinTypeFactory.simpleType(annotations2, refinedConstructor, arguments, z, refiner);
                }
                return null;
            }
        });
    }

    @JvmStatic
    public static final SimpleType computeExpandedType(TypeAliasDescriptor computeExpandedType, List<? extends TypeProjection> arguments) {
        Intrinsics.checkParameterIsNotNull(computeExpandedType, "$this$computeExpandedType");
        Intrinsics.checkParameterIsNotNull(arguments, "arguments");
        return new TypeAliasExpander(TypeAliasExpansionReportStrategy.DO_NOTHING.INSTANCE, false).expand(TypeAliasExpansion.Companion.create(null, computeExpandedType, arguments), Annotations.Companion.getEMPTY());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ExpandedTypeOrRefinedConstructor refineConstructor(TypeConstructor typeConstructor, KotlinTypeRefiner kotlinTypeRefiner, List<? extends TypeProjection> list) {
        ClassifierDescriptor refineDescriptor;
        ClassifierDescriptor mo1092getDeclarationDescriptor = typeConstructor.mo1092getDeclarationDescriptor();
        if (mo1092getDeclarationDescriptor == null || (refineDescriptor = kotlinTypeRefiner.refineDescriptor(mo1092getDeclarationDescriptor)) == null) {
            return null;
        }
        if (refineDescriptor instanceof TypeAliasDescriptor) {
            return new ExpandedTypeOrRefinedConstructor(computeExpandedType((TypeAliasDescriptor) refineDescriptor, list), null);
        }
        TypeConstructor refine = refineDescriptor.getTypeConstructor().refine(kotlinTypeRefiner);
        Intrinsics.checkExpressionValueIsNotNull(refine, "descriptor.typeConstruct…refine(kotlinTypeRefiner)");
        return new ExpandedTypeOrRefinedConstructor(null, refine);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: KotlinTypeFactory.kt */
    /* loaded from: classes2.dex */
    public static final class ExpandedTypeOrRefinedConstructor {
        private final SimpleType expandedType;
        private final TypeConstructor refinedConstructor;

        public ExpandedTypeOrRefinedConstructor(SimpleType simpleType, TypeConstructor typeConstructor) {
            this.expandedType = simpleType;
            this.refinedConstructor = typeConstructor;
        }

        public final SimpleType getExpandedType() {
            return this.expandedType;
        }

        public final TypeConstructor getRefinedConstructor() {
            return this.refinedConstructor;
        }
    }

    @JvmStatic
    public static final SimpleType simpleTypeWithNonTrivialMemberScope(final Annotations annotations, final TypeConstructor constructor, final List<? extends TypeProjection> arguments, final boolean z, final MemberScope memberScope) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        Intrinsics.checkParameterIsNotNull(constructor, "constructor");
        Intrinsics.checkParameterIsNotNull(arguments, "arguments");
        Intrinsics.checkParameterIsNotNull(memberScope, "memberScope");
        SimpleTypeImpl simpleTypeImpl = new SimpleTypeImpl(constructor, arguments, z, memberScope, new Function1<KotlinTypeRefiner, SimpleType>() { // from class: kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory$simpleTypeWithNonTrivialMemberScope$1
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SimpleType invoke(KotlinTypeRefiner kotlinTypeRefiner) {
                KotlinTypeFactory.ExpandedTypeOrRefinedConstructor refineConstructor;
                Intrinsics.checkParameterIsNotNull(kotlinTypeRefiner, "kotlinTypeRefiner");
                refineConstructor = KotlinTypeFactory.INSTANCE.refineConstructor(TypeConstructor.this, kotlinTypeRefiner, arguments);
                if (refineConstructor != null) {
                    SimpleType expandedType = refineConstructor.getExpandedType();
                    if (expandedType != null) {
                        return expandedType;
                    }
                    Annotations annotations2 = annotations;
                    TypeConstructor refinedConstructor = refineConstructor.getRefinedConstructor();
                    if (refinedConstructor == null) {
                        Intrinsics.throwNpe();
                    }
                    return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(annotations2, refinedConstructor, arguments, z, memberScope);
                }
                return null;
            }
        });
        if (annotations.isEmpty()) {
            return simpleTypeImpl;
        }
        return new AnnotatedSimpleType(simpleTypeImpl, annotations);
    }

    @JvmStatic
    public static final SimpleType simpleTypeWithNonTrivialMemberScope(Annotations annotations, TypeConstructor constructor, List<? extends TypeProjection> arguments, boolean z, MemberScope memberScope, Function1<? super KotlinTypeRefiner, ? extends SimpleType> refinedTypeFactory) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        Intrinsics.checkParameterIsNotNull(constructor, "constructor");
        Intrinsics.checkParameterIsNotNull(arguments, "arguments");
        Intrinsics.checkParameterIsNotNull(memberScope, "memberScope");
        Intrinsics.checkParameterIsNotNull(refinedTypeFactory, "refinedTypeFactory");
        SimpleTypeImpl simpleTypeImpl = new SimpleTypeImpl(constructor, arguments, z, memberScope, refinedTypeFactory);
        if (annotations.isEmpty()) {
            return simpleTypeImpl;
        }
        return new AnnotatedSimpleType(simpleTypeImpl, annotations);
    }

    @JvmStatic
    public static final SimpleType simpleNotNullType(Annotations annotations, ClassDescriptor descriptor, List<? extends TypeProjection> arguments) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        Intrinsics.checkParameterIsNotNull(arguments, "arguments");
        TypeConstructor typeConstructor = descriptor.getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "descriptor.typeConstructor");
        return simpleType$default(annotations, typeConstructor, arguments, false, null, 16, null);
    }

    @JvmStatic
    public static final UnwrappedType flexibleType(SimpleType lowerBound, SimpleType upperBound) {
        Intrinsics.checkParameterIsNotNull(lowerBound, "lowerBound");
        Intrinsics.checkParameterIsNotNull(upperBound, "upperBound");
        return Intrinsics.areEqual(lowerBound, upperBound) ? lowerBound : new FlexibleTypeImpl(lowerBound, upperBound);
    }

    @JvmStatic
    public static final SimpleType integerLiteralType(Annotations annotations, IntegerLiteralTypeConstructor constructor, boolean z) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        Intrinsics.checkParameterIsNotNull(constructor, "constructor");
        List emptyList = CollectionsKt.emptyList();
        MemberScope createErrorScope = ErrorUtils.createErrorScope("Scope for integer literal type", true);
        Intrinsics.checkExpressionValueIsNotNull(createErrorScope, "ErrorUtils.createErrorSc…eger literal type\", true)");
        return simpleTypeWithNonTrivialMemberScope(annotations, constructor, emptyList, z, createErrorScope);
    }
}
