package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.UtilsKt;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.load.kotlin.DeserializedDescriptorResolver;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.util.collectionUtils.ScopeUtilsKt;
/* compiled from: JvmPackageScope.kt */
/* loaded from: classes2.dex */
public final class JvmPackageScope implements MemberScope {
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmPackageScope.class), "kotlinScopes", "getKotlinScopes()Ljava/util/List;"))};
    private final LazyJavaResolverContext c;
    private final LazyJavaPackageScope javaScope;
    private final NotNullLazyValue kotlinScopes$delegate;
    private final LazyJavaPackageFragment packageFragment;

    private final List<MemberScope> getKotlinScopes() {
        return (List) StorageKt.getValue(this.kotlinScopes$delegate, this, $$delegatedProperties[0]);
    }

    public JvmPackageScope(LazyJavaResolverContext c, JavaPackage jPackage, LazyJavaPackageFragment packageFragment) {
        Intrinsics.checkParameterIsNotNull(c, "c");
        Intrinsics.checkParameterIsNotNull(jPackage, "jPackage");
        Intrinsics.checkParameterIsNotNull(packageFragment, "packageFragment");
        this.c = c;
        this.packageFragment = packageFragment;
        this.javaScope = new LazyJavaPackageScope(c, jPackage, packageFragment);
        this.kotlinScopes$delegate = c.getStorageManager().createLazyValue(new Function0<List<? extends MemberScope>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.JvmPackageScope$kotlinScopes$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends MemberScope> invoke() {
                LazyJavaPackageFragment lazyJavaPackageFragment;
                LazyJavaResolverContext lazyJavaResolverContext;
                LazyJavaPackageFragment lazyJavaPackageFragment2;
                lazyJavaPackageFragment = JvmPackageScope.this.packageFragment;
                ArrayList arrayList = new ArrayList();
                for (KotlinJvmBinaryClass kotlinJvmBinaryClass : lazyJavaPackageFragment.getBinaryClasses$descriptors_jvm().values()) {
                    lazyJavaResolverContext = JvmPackageScope.this.c;
                    DeserializedDescriptorResolver deserializedDescriptorResolver = lazyJavaResolverContext.getComponents().getDeserializedDescriptorResolver();
                    lazyJavaPackageFragment2 = JvmPackageScope.this.packageFragment;
                    MemberScope createKotlinPackagePartScope = deserializedDescriptorResolver.createKotlinPackagePartScope(lazyJavaPackageFragment2, kotlinJvmBinaryClass);
                    if (createKotlinPackagePartScope != null) {
                        arrayList.add(createKotlinPackagePartScope);
                    }
                }
                return CollectionsKt.toList(arrayList);
            }
        });
    }

    public final LazyJavaPackageScope getJavaScope$descriptors_jvm() {
        return this.javaScope;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    /* renamed from: getContributedClassifier */
    public ClassifierDescriptor mo1094getContributedClassifier(Name name, LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        recordLookup(name, location);
        ClassDescriptor mo1094getContributedClassifier = this.javaScope.mo1094getContributedClassifier(name, location);
        if (mo1094getContributedClassifier != null) {
            return mo1094getContributedClassifier;
        }
        ClassifierDescriptor classifierDescriptor = null;
        for (MemberScope memberScope : getKotlinScopes()) {
            ClassifierDescriptor contributedClassifier = memberScope.mo1094getContributedClassifier(name, location);
            if (contributedClassifier != null) {
                if (!(contributedClassifier instanceof ClassifierDescriptorWithTypeParameters) || !((ClassifierDescriptorWithTypeParameters) contributedClassifier).isExpect()) {
                    return contributedClassifier;
                }
                if (classifierDescriptor == null) {
                    classifierDescriptor = contributedClassifier;
                }
            }
        }
        return classifierDescriptor;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    public Collection<PropertyDescriptor> getContributedVariables(Name name, LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        recordLookup(name, location);
        LazyJavaPackageScope lazyJavaPackageScope = this.javaScope;
        List<MemberScope> kotlinScopes = getKotlinScopes();
        Collection<? extends PropertyDescriptor> contributedVariables = lazyJavaPackageScope.getContributedVariables(name, location);
        Collection collection = contributedVariables;
        for (MemberScope memberScope : kotlinScopes) {
            collection = ScopeUtilsKt.concat(collection, memberScope.getContributedVariables(name, location));
        }
        return collection != null ? collection : SetsKt.emptySet();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name, LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        recordLookup(name, location);
        LazyJavaPackageScope lazyJavaPackageScope = this.javaScope;
        List<MemberScope> kotlinScopes = getKotlinScopes();
        Collection<? extends SimpleFunctionDescriptor> contributedFunctions = lazyJavaPackageScope.getContributedFunctions(name, location);
        Collection collection = contributedFunctions;
        for (MemberScope memberScope : kotlinScopes) {
            collection = ScopeUtilsKt.concat(collection, memberScope.getContributedFunctions(name, location));
        }
        return collection != null ? collection : SetsKt.emptySet();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkParameterIsNotNull(kindFilter, "kindFilter");
        Intrinsics.checkParameterIsNotNull(nameFilter, "nameFilter");
        LazyJavaPackageScope lazyJavaPackageScope = this.javaScope;
        List<MemberScope> kotlinScopes = getKotlinScopes();
        Collection<DeclarationDescriptor> contributedDescriptors = lazyJavaPackageScope.getContributedDescriptors(kindFilter, nameFilter);
        for (MemberScope memberScope : kotlinScopes) {
            contributedDescriptors = ScopeUtilsKt.concat(contributedDescriptors, memberScope.getContributedDescriptors(kindFilter, nameFilter));
        }
        return contributedDescriptors != null ? contributedDescriptors : SetsKt.emptySet();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    public Set<Name> getFunctionNames() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (MemberScope memberScope : getKotlinScopes()) {
            CollectionsKt.addAll(linkedHashSet, memberScope.getFunctionNames());
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        linkedHashSet2.addAll(this.javaScope.getFunctionNames());
        return linkedHashSet2;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    public Set<Name> getVariableNames() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (MemberScope memberScope : getKotlinScopes()) {
            CollectionsKt.addAll(linkedHashSet, memberScope.getVariableNames());
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        linkedHashSet2.addAll(this.javaScope.getVariableNames());
        return linkedHashSet2;
    }

    public void recordLookup(Name name, LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        UtilsKt.record(this.c.getComponents().getLookupTracker(), location, this.packageFragment, name);
    }
}
