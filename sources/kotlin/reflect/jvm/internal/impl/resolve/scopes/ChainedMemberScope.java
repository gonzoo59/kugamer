package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.util.collectionUtils.ScopeUtilsKt;
/* compiled from: ChainedMemberScope.kt */
/* loaded from: classes2.dex */
public final class ChainedMemberScope implements MemberScope {
    public static final Companion Companion = new Companion(null);
    private final String debugName;
    private final List<MemberScope> scopes;

    /* JADX WARN: Multi-variable type inference failed */
    public ChainedMemberScope(String debugName, List<? extends MemberScope> scopes) {
        Intrinsics.checkParameterIsNotNull(debugName, "debugName");
        Intrinsics.checkParameterIsNotNull(scopes, "scopes");
        this.debugName = debugName;
        this.scopes = scopes;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    /* renamed from: getContributedClassifier */
    public ClassifierDescriptor mo1094getContributedClassifier(Name name, LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        ClassifierDescriptor classifierDescriptor = null;
        for (MemberScope memberScope : this.scopes) {
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
        List<MemberScope> list = this.scopes;
        if (list.isEmpty()) {
            return SetsKt.emptySet();
        }
        Collection<PropertyDescriptor> collection = null;
        for (MemberScope memberScope : list) {
            collection = ScopeUtilsKt.concat(collection, memberScope.getContributedVariables(name, location));
        }
        return collection != null ? collection : SetsKt.emptySet();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name, LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        List<MemberScope> list = this.scopes;
        if (list.isEmpty()) {
            return SetsKt.emptySet();
        }
        Collection<SimpleFunctionDescriptor> collection = null;
        for (MemberScope memberScope : list) {
            collection = ScopeUtilsKt.concat(collection, memberScope.getContributedFunctions(name, location));
        }
        return collection != null ? collection : SetsKt.emptySet();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkParameterIsNotNull(kindFilter, "kindFilter");
        Intrinsics.checkParameterIsNotNull(nameFilter, "nameFilter");
        List<MemberScope> list = this.scopes;
        if (list.isEmpty()) {
            return SetsKt.emptySet();
        }
        Collection<DeclarationDescriptor> collection = null;
        for (MemberScope memberScope : list) {
            collection = ScopeUtilsKt.concat(collection, memberScope.getContributedDescriptors(kindFilter, nameFilter));
        }
        return collection != null ? collection : SetsKt.emptySet();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    public Set<Name> getFunctionNames() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (MemberScope memberScope : this.scopes) {
            CollectionsKt.addAll(linkedHashSet, memberScope.getFunctionNames());
        }
        return linkedHashSet;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    public Set<Name> getVariableNames() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (MemberScope memberScope : this.scopes) {
            CollectionsKt.addAll(linkedHashSet, memberScope.getVariableNames());
        }
        return linkedHashSet;
    }

    public String toString() {
        return this.debugName;
    }

    /* compiled from: ChainedMemberScope.kt */
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final MemberScope create(String debugName, List<? extends MemberScope> scopes) {
            Intrinsics.checkParameterIsNotNull(debugName, "debugName");
            Intrinsics.checkParameterIsNotNull(scopes, "scopes");
            int size = scopes.size();
            if (size != 0) {
                if (size == 1) {
                    return (MemberScope) CollectionsKt.single((List<? extends Object>) scopes);
                }
                return new ChainedMemberScope(debugName, scopes);
            }
            return MemberScope.Empty.INSTANCE;
        }
    }
}
