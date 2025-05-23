package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.sequences.SequencesKt;
/* compiled from: findClassInModule.kt */
/* loaded from: classes2.dex */
public final class FindClassInModuleKt {
    public static final ClassifierDescriptor findClassifierAcrossModuleDependencies(ModuleDescriptor findClassifierAcrossModuleDependencies, ClassId classId) {
        Intrinsics.checkParameterIsNotNull(findClassifierAcrossModuleDependencies, "$this$findClassifierAcrossModuleDependencies");
        Intrinsics.checkParameterIsNotNull(classId, "classId");
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkExpressionValueIsNotNull(packageFqName, "classId.packageFqName");
        PackageViewDescriptor packageViewDescriptor = findClassifierAcrossModuleDependencies.getPackage(packageFqName);
        List<Name> pathSegments = classId.getRelativeClassName().pathSegments();
        Intrinsics.checkExpressionValueIsNotNull(pathSegments, "classId.relativeClassName.pathSegments()");
        MemberScope memberScope = packageViewDescriptor.getMemberScope();
        Object first = CollectionsKt.first((List<? extends Object>) pathSegments);
        Intrinsics.checkExpressionValueIsNotNull(first, "segments.first()");
        ClassDescriptor contributedClassifier = memberScope.mo1094getContributedClassifier((Name) first, NoLookupLocation.FROM_DESERIALIZATION);
        if (contributedClassifier != null) {
            for (Name name : pathSegments.subList(1, pathSegments.size())) {
                if (!(contributedClassifier instanceof ClassDescriptor)) {
                    return null;
                }
                MemberScope unsubstitutedInnerClassesScope = ((ClassDescriptor) contributedClassifier).getUnsubstitutedInnerClassesScope();
                Intrinsics.checkExpressionValueIsNotNull(name, "name");
                ClassifierDescriptor contributedClassifier2 = unsubstitutedInnerClassesScope.mo1094getContributedClassifier(name, NoLookupLocation.FROM_DESERIALIZATION);
                if (!(contributedClassifier2 instanceof ClassDescriptor)) {
                    contributedClassifier2 = null;
                }
                ClassDescriptor classDescriptor = (ClassDescriptor) contributedClassifier2;
                if (classDescriptor == null) {
                    return null;
                }
                contributedClassifier = classDescriptor;
            }
            return contributedClassifier;
        }
        return null;
    }

    public static final ClassDescriptor findClassAcrossModuleDependencies(ModuleDescriptor findClassAcrossModuleDependencies, ClassId classId) {
        Intrinsics.checkParameterIsNotNull(findClassAcrossModuleDependencies, "$this$findClassAcrossModuleDependencies");
        Intrinsics.checkParameterIsNotNull(classId, "classId");
        ClassifierDescriptor findClassifierAcrossModuleDependencies = findClassifierAcrossModuleDependencies(findClassAcrossModuleDependencies, classId);
        if (!(findClassifierAcrossModuleDependencies instanceof ClassDescriptor)) {
            findClassifierAcrossModuleDependencies = null;
        }
        return (ClassDescriptor) findClassifierAcrossModuleDependencies;
    }

    public static final ClassDescriptor findNonGenericClassAcrossDependencies(ModuleDescriptor findNonGenericClassAcrossDependencies, ClassId classId, NotFoundClasses notFoundClasses) {
        Intrinsics.checkParameterIsNotNull(findNonGenericClassAcrossDependencies, "$this$findNonGenericClassAcrossDependencies");
        Intrinsics.checkParameterIsNotNull(classId, "classId");
        Intrinsics.checkParameterIsNotNull(notFoundClasses, "notFoundClasses");
        ClassDescriptor findClassAcrossModuleDependencies = findClassAcrossModuleDependencies(findNonGenericClassAcrossDependencies, classId);
        return findClassAcrossModuleDependencies != null ? findClassAcrossModuleDependencies : notFoundClasses.getClass(classId, SequencesKt.toList(SequencesKt.map(SequencesKt.generateSequence(classId, FindClassInModuleKt$findNonGenericClassAcrossDependencies$typeParametersCount$1.INSTANCE), new Function1<ClassId, Integer>() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt$findNonGenericClassAcrossDependencies$typeParametersCount$2
            /* renamed from: invoke  reason: avoid collision after fix types in other method */
            public final int invoke2(ClassId it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return 0;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Integer invoke(ClassId classId2) {
                return Integer.valueOf(invoke2(classId2));
            }
        })));
    }

    public static final TypeAliasDescriptor findTypeAliasAcrossModuleDependencies(ModuleDescriptor findTypeAliasAcrossModuleDependencies, ClassId classId) {
        Intrinsics.checkParameterIsNotNull(findTypeAliasAcrossModuleDependencies, "$this$findTypeAliasAcrossModuleDependencies");
        Intrinsics.checkParameterIsNotNull(classId, "classId");
        ClassifierDescriptor findClassifierAcrossModuleDependencies = findClassifierAcrossModuleDependencies(findTypeAliasAcrossModuleDependencies, classId);
        if (!(findClassifierAcrossModuleDependencies instanceof TypeAliasDescriptor)) {
            findClassifierAcrossModuleDependencies = null;
        }
        return (TypeAliasDescriptor) findClassifierAcrossModuleDependencies;
    }
}
