package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
/* compiled from: DescriptorEquivalenceForOverrides.kt */
/* loaded from: classes2.dex */
public final class DescriptorEquivalenceForOverrides {
    public static final DescriptorEquivalenceForOverrides INSTANCE = new DescriptorEquivalenceForOverrides();

    private DescriptorEquivalenceForOverrides() {
    }

    public final boolean areEquivalent(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2, boolean z) {
        return ((declarationDescriptor instanceof ClassDescriptor) && (declarationDescriptor2 instanceof ClassDescriptor)) ? areClassesEquivalent((ClassDescriptor) declarationDescriptor, (ClassDescriptor) declarationDescriptor2) : ((declarationDescriptor instanceof TypeParameterDescriptor) && (declarationDescriptor2 instanceof TypeParameterDescriptor)) ? areTypeParametersEquivalent$default(this, (TypeParameterDescriptor) declarationDescriptor, (TypeParameterDescriptor) declarationDescriptor2, z, null, 8, null) : ((declarationDescriptor instanceof CallableDescriptor) && (declarationDescriptor2 instanceof CallableDescriptor)) ? areCallableDescriptorsEquivalent$default(this, (CallableDescriptor) declarationDescriptor, (CallableDescriptor) declarationDescriptor2, z, false, 8, null) : ((declarationDescriptor instanceof PackageFragmentDescriptor) && (declarationDescriptor2 instanceof PackageFragmentDescriptor)) ? Intrinsics.areEqual(((PackageFragmentDescriptor) declarationDescriptor).getFqName(), ((PackageFragmentDescriptor) declarationDescriptor2).getFqName()) : Intrinsics.areEqual(declarationDescriptor, declarationDescriptor2);
    }

    private final boolean areClassesEquivalent(ClassDescriptor classDescriptor, ClassDescriptor classDescriptor2) {
        return Intrinsics.areEqual(classDescriptor.getTypeConstructor(), classDescriptor2.getTypeConstructor());
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ boolean areTypeParametersEquivalent$default(DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides, TypeParameterDescriptor typeParameterDescriptor, TypeParameterDescriptor typeParameterDescriptor2, boolean z, Function2 function2, int i, Object obj) {
        if ((i & 8) != 0) {
            function2 = new Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides$areTypeParametersEquivalent$1
                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final boolean invoke2(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
                    return false;
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Boolean invoke(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
                    return Boolean.valueOf(invoke2(declarationDescriptor, declarationDescriptor2));
                }
            };
        }
        return descriptorEquivalenceForOverrides.areTypeParametersEquivalent(typeParameterDescriptor, typeParameterDescriptor2, z, function2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean areTypeParametersEquivalent(TypeParameterDescriptor typeParameterDescriptor, TypeParameterDescriptor typeParameterDescriptor2, boolean z, Function2<? super DeclarationDescriptor, ? super DeclarationDescriptor, Boolean> function2) {
        if (Intrinsics.areEqual(typeParameterDescriptor, typeParameterDescriptor2)) {
            return true;
        }
        return !Intrinsics.areEqual(typeParameterDescriptor.getContainingDeclaration(), typeParameterDescriptor2.getContainingDeclaration()) && ownersEquivalent(typeParameterDescriptor, typeParameterDescriptor2, function2, z) && typeParameterDescriptor.getIndex() == typeParameterDescriptor2.getIndex();
    }

    private final SourceElement singleSource(CallableDescriptor callableDescriptor) {
        while (callableDescriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) callableDescriptor;
            if (callableMemberDescriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                break;
            }
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
            Intrinsics.checkExpressionValueIsNotNull(overriddenDescriptors, "overriddenDescriptors");
            CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) CollectionsKt.singleOrNull(overriddenDescriptors);
            if (callableMemberDescriptor2 == null) {
                return null;
            }
            callableDescriptor = callableMemberDescriptor2;
        }
        return callableDescriptor.getSource();
    }

    public static /* synthetic */ boolean areCallableDescriptorsEquivalent$default(DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides, CallableDescriptor callableDescriptor, CallableDescriptor callableDescriptor2, boolean z, boolean z2, int i, Object obj) {
        if ((i & 8) != 0) {
            z2 = false;
        }
        return descriptorEquivalenceForOverrides.areCallableDescriptorsEquivalent(callableDescriptor, callableDescriptor2, z, z2);
    }

    public final boolean areCallableDescriptorsEquivalent(final CallableDescriptor a, final CallableDescriptor b, final boolean z, boolean z2) {
        Intrinsics.checkParameterIsNotNull(a, "a");
        Intrinsics.checkParameterIsNotNull(b, "b");
        if (Intrinsics.areEqual(a, b)) {
            return true;
        }
        if (!Intrinsics.areEqual(a.getName(), b.getName())) {
            return false;
        }
        if (Intrinsics.areEqual(a.getContainingDeclaration(), b.getContainingDeclaration())) {
            if (!z || (!Intrinsics.areEqual(singleSource(a), singleSource(b)))) {
                return false;
            }
            if ((a instanceof MemberDescriptor) && (b instanceof MemberDescriptor) && ((MemberDescriptor) a).isExpect() != ((MemberDescriptor) b).isExpect()) {
                return false;
            }
        }
        CallableDescriptor callableDescriptor = a;
        if (!DescriptorUtils.isLocal(callableDescriptor)) {
            CallableDescriptor callableDescriptor2 = b;
            if (!DescriptorUtils.isLocal(callableDescriptor2) && ownersEquivalent(callableDescriptor, callableDescriptor2, new Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides$areCallableDescriptorsEquivalent$1
                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final boolean invoke2(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
                    return false;
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Boolean invoke(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
                    return Boolean.valueOf(invoke2(declarationDescriptor, declarationDescriptor2));
                }
            }, z)) {
                OverridingUtil createWithEqualityAxioms = OverridingUtil.createWithEqualityAxioms(new KotlinTypeChecker.TypeConstructorEquality() { // from class: kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides$areCallableDescriptorsEquivalent$overridingUtil$1
                    @Override // kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker.TypeConstructorEquality
                    /* renamed from: invoke */
                    public final boolean equals(TypeConstructor c1, TypeConstructor c2) {
                        boolean areTypeParametersEquivalent;
                        Intrinsics.checkParameterIsNotNull(c1, "c1");
                        Intrinsics.checkParameterIsNotNull(c2, "c2");
                        if (Intrinsics.areEqual(c1, c2)) {
                            return true;
                        }
                        ClassifierDescriptor mo1092getDeclarationDescriptor = c1.mo1092getDeclarationDescriptor();
                        ClassifierDescriptor mo1092getDeclarationDescriptor2 = c2.mo1092getDeclarationDescriptor();
                        if ((mo1092getDeclarationDescriptor instanceof TypeParameterDescriptor) && (mo1092getDeclarationDescriptor2 instanceof TypeParameterDescriptor)) {
                            areTypeParametersEquivalent = DescriptorEquivalenceForOverrides.INSTANCE.areTypeParametersEquivalent((TypeParameterDescriptor) mo1092getDeclarationDescriptor, (TypeParameterDescriptor) mo1092getDeclarationDescriptor2, z, new Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides$areCallableDescriptorsEquivalent$overridingUtil$1.1
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public /* bridge */ /* synthetic */ Boolean invoke(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
                                    return Boolean.valueOf(invoke2(declarationDescriptor, declarationDescriptor2));
                                }

                                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                                public final boolean invoke2(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
                                    return Intrinsics.areEqual(declarationDescriptor, a) && Intrinsics.areEqual(declarationDescriptor2, b);
                                }
                            });
                            return areTypeParametersEquivalent;
                        }
                        return false;
                    }
                });
                Intrinsics.checkExpressionValueIsNotNull(createWithEqualityAxioms, "OverridingUtil.createWit…= a && y == b }\n        }");
                OverridingUtil.OverrideCompatibilityInfo isOverridableBy = createWithEqualityAxioms.isOverridableBy(a, b, null, !z2);
                Intrinsics.checkExpressionValueIsNotNull(isOverridableBy, "overridingUtil.isOverrid… null, !ignoreReturnType)");
                if (isOverridableBy.getResult() == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE) {
                    OverridingUtil.OverrideCompatibilityInfo isOverridableBy2 = createWithEqualityAxioms.isOverridableBy(b, a, null, !z2);
                    Intrinsics.checkExpressionValueIsNotNull(isOverridableBy2, "overridingUtil.isOverrid… null, !ignoreReturnType)");
                    if (isOverridableBy2.getResult() == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private final boolean ownersEquivalent(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2, Function2<? super DeclarationDescriptor, ? super DeclarationDescriptor, Boolean> function2, boolean z) {
        DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingDeclaration();
        DeclarationDescriptor containingDeclaration2 = declarationDescriptor2.getContainingDeclaration();
        if ((containingDeclaration instanceof CallableMemberDescriptor) || (containingDeclaration2 instanceof CallableMemberDescriptor)) {
            return function2.invoke(containingDeclaration, containingDeclaration2).booleanValue();
        }
        return areEquivalent(containingDeclaration, containingDeclaration2, z);
    }
}
