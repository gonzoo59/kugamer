package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.MemberComparator;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationContext;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.NameResolverUtilKt;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
/* compiled from: DeserializedMemberScope.kt */
/* loaded from: classes2.dex */
public abstract class DeserializedMemberScope extends MemberScopeImpl {
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DeserializedMemberScope.class), "functionNamesLazy", "getFunctionNamesLazy()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DeserializedMemberScope.class), "variableNamesLazy", "getVariableNamesLazy()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DeserializedMemberScope.class), "classNames", "getClassNames$deserialization()Ljava/util/Set;"))};
    private final DeserializationContext c;
    private final NotNullLazyValue classNames$delegate;
    private final NotNullLazyValue functionNamesLazy$delegate;
    private final Map<Name, byte[]> functionProtosBytes;
    private final MemoizedFunctionToNotNull<Name, Collection<SimpleFunctionDescriptor>> functions;
    private final MemoizedFunctionToNotNull<Name, Collection<PropertyDescriptor>> properties;
    private final Map<Name, byte[]> propertyProtosBytes;
    private final MemoizedFunctionToNullable<Name, TypeAliasDescriptor> typeAliasByName;
    private final Map<Name, byte[]> typeAliasBytes;
    private final NotNullLazyValue variableNamesLazy$delegate;

    private final Set<Name> getFunctionNamesLazy() {
        return (Set) StorageKt.getValue(this.functionNamesLazy$delegate, this, $$delegatedProperties[0]);
    }

    private final Set<Name> getVariableNamesLazy() {
        return (Set) StorageKt.getValue(this.variableNamesLazy$delegate, this, $$delegatedProperties[1]);
    }

    protected abstract void addEnumEntryDescriptors(Collection<DeclarationDescriptor> collection, Function1<? super Name, Boolean> function1);

    protected void computeNonDeclaredFunctions(Name name, Collection<SimpleFunctionDescriptor> functions) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(functions, "functions");
    }

    protected void computeNonDeclaredProperties(Name name, Collection<PropertyDescriptor> descriptors) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(descriptors, "descriptors");
    }

    protected abstract ClassId createClassId(Name name);

    public final Set<Name> getClassNames$deserialization() {
        return (Set) StorageKt.getValue(this.classNames$delegate, this, $$delegatedProperties[2]);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract Set<Name> getNonDeclaredFunctionNames();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract Set<Name> getNonDeclaredVariableNames();

    /* JADX INFO: Access modifiers changed from: protected */
    public final DeserializationContext getC() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public DeserializedMemberScope(DeserializationContext c, Collection<ProtoBuf.Function> functionList, Collection<ProtoBuf.Property> propertyList, Collection<ProtoBuf.TypeAlias> typeAliasList, final Function0<? extends Collection<Name>> classNames) {
        Map<Name, byte[]> emptyMap;
        Intrinsics.checkParameterIsNotNull(c, "c");
        Intrinsics.checkParameterIsNotNull(functionList, "functionList");
        Intrinsics.checkParameterIsNotNull(propertyList, "propertyList");
        Intrinsics.checkParameterIsNotNull(typeAliasList, "typeAliasList");
        Intrinsics.checkParameterIsNotNull(classNames, "classNames");
        this.c = c;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : functionList) {
            Name name = NameResolverUtilKt.getName(this.c.getNameResolver(), ((ProtoBuf.Function) ((MessageLite) obj)).getName());
            Object obj2 = linkedHashMap.get(name);
            if (obj2 == null) {
                obj2 = new ArrayList();
                linkedHashMap.put(name, obj2);
            }
            ((List) obj2).add(obj);
        }
        this.functionProtosBytes = packToByteArray(linkedHashMap);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Object obj3 : propertyList) {
            Name name2 = NameResolverUtilKt.getName(this.c.getNameResolver(), ((ProtoBuf.Property) ((MessageLite) obj3)).getName());
            Object obj4 = linkedHashMap2.get(name2);
            if (obj4 == null) {
                obj4 = new ArrayList();
                linkedHashMap2.put(name2, obj4);
            }
            ((List) obj4).add(obj3);
        }
        this.propertyProtosBytes = packToByteArray(linkedHashMap2);
        if (!this.c.getComponents().getConfiguration().getTypeAliasesAllowed()) {
            emptyMap = MapsKt.emptyMap();
        } else {
            LinkedHashMap linkedHashMap3 = new LinkedHashMap();
            for (Object obj5 : typeAliasList) {
                Name name3 = NameResolverUtilKt.getName(this.c.getNameResolver(), ((ProtoBuf.TypeAlias) ((MessageLite) obj5)).getName());
                Object obj6 = linkedHashMap3.get(name3);
                if (obj6 == null) {
                    obj6 = new ArrayList();
                    linkedHashMap3.put(name3, obj6);
                }
                ((List) obj6).add(obj5);
            }
            emptyMap = packToByteArray(linkedHashMap3);
        }
        this.typeAliasBytes = emptyMap;
        this.functions = this.c.getStorageManager().createMemoizedFunction(new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$functions$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<SimpleFunctionDescriptor> invoke(Name it) {
                Collection<SimpleFunctionDescriptor> computeFunctions;
                Intrinsics.checkParameterIsNotNull(it, "it");
                computeFunctions = DeserializedMemberScope.this.computeFunctions(it);
                return computeFunctions;
            }
        });
        this.properties = this.c.getStorageManager().createMemoizedFunction(new Function1<Name, Collection<? extends PropertyDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$properties$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<PropertyDescriptor> invoke(Name it) {
                Collection<PropertyDescriptor> computeProperties;
                Intrinsics.checkParameterIsNotNull(it, "it");
                computeProperties = DeserializedMemberScope.this.computeProperties(it);
                return computeProperties;
            }
        });
        this.typeAliasByName = this.c.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<Name, TypeAliasDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$typeAliasByName$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final TypeAliasDescriptor invoke(Name it) {
                TypeAliasDescriptor createTypeAlias;
                Intrinsics.checkParameterIsNotNull(it, "it");
                createTypeAlias = DeserializedMemberScope.this.createTypeAlias(it);
                return createTypeAlias;
            }
        });
        this.functionNamesLazy$delegate = this.c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$functionNamesLazy$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                Map map;
                map = DeserializedMemberScope.this.functionProtosBytes;
                return SetsKt.plus(map.keySet(), (Iterable) DeserializedMemberScope.this.getNonDeclaredFunctionNames());
            }
        });
        this.variableNamesLazy$delegate = this.c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$variableNamesLazy$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                Map map;
                map = DeserializedMemberScope.this.propertyProtosBytes;
                return SetsKt.plus(map.keySet(), (Iterable) DeserializedMemberScope.this.getNonDeclaredVariableNames());
            }
        });
        this.classNames$delegate = this.c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$classNames$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                return CollectionsKt.toSet((Iterable) Function0.this.invoke());
            }
        });
    }

    private final Set<Name> getTypeAliasNames() {
        return this.typeAliasBytes.keySet();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    public Set<Name> getFunctionNames() {
        return getFunctionNamesLazy();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    public Set<Name> getVariableNames() {
        return getVariableNamesLazy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0043 A[LOOP:0: B:9:0x003d->B:11:0x0043, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.Collection<kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor> computeFunctions(kotlin.reflect.jvm.internal.impl.name.Name r6) {
        /*
            r5 = this;
            java.util.Map<kotlin.reflect.jvm.internal.impl.name.Name, byte[]> r0 = r5.functionProtosBytes
            kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Function> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function.PARSER
            java.lang.String r2 = "ProtoBuf.Function.PARSER"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r1, r2)
            java.lang.Object r0 = r0.get(r6)
            byte[] r0 = (byte[]) r0
            if (r0 == 0) goto L2a
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream
            r2.<init>(r0)
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$computeDescriptors$$inlined$let$lambda$1 r0 = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$computeDescriptors$$inlined$let$lambda$1
            r0.<init>()
            kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
            kotlin.sequences.Sequence r0 = kotlin.sequences.SequencesKt.generateSequence(r0)
            java.util.List r0 = kotlin.sequences.SequencesKt.toList(r0)
            if (r0 == 0) goto L2a
            java.util.Collection r0 = (java.util.Collection) r0
            goto L30
        L2a:
            java.util.List r0 = kotlin.collections.CollectionsKt.emptyList()
            java.util.Collection r0 = (java.util.Collection) r0
        L30:
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.util.Collection r1 = (java.util.Collection) r1
            java.util.Iterator r0 = r0.iterator()
        L3d:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L5c
            java.lang.Object r2 = r0.next()
            kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Function r2 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function) r2
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationContext r3 = r5.c
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer r3 = r3.getMemberDeserializer()
            java.lang.String r4 = "it"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r2, r4)
            kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor r2 = r3.loadFunction(r2)
            r1.add(r2)
            goto L3d
        L5c:
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            r0 = r1
            java.util.Collection r0 = (java.util.Collection) r0
            r5.computeNonDeclaredFunctions(r6, r0)
            java.util.List r6 = kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact(r1)
            java.util.Collection r6 = (java.util.Collection) r6
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope.computeFunctions(kotlin.reflect.jvm.internal.impl.name.Name):java.util.Collection");
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name, LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        return !getFunctionNames().contains(name) ? CollectionsKt.emptyList() : this.functions.invoke(name);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0043 A[LOOP:0: B:9:0x003d->B:11:0x0043, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.Collection<kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor> computeProperties(kotlin.reflect.jvm.internal.impl.name.Name r6) {
        /*
            r5 = this;
            java.util.Map<kotlin.reflect.jvm.internal.impl.name.Name, byte[]> r0 = r5.propertyProtosBytes
            kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Property> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property.PARSER
            java.lang.String r2 = "ProtoBuf.Property.PARSER"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r1, r2)
            java.lang.Object r0 = r0.get(r6)
            byte[] r0 = (byte[]) r0
            if (r0 == 0) goto L2a
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream
            r2.<init>(r0)
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$computeDescriptors$$inlined$let$lambda$3 r0 = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$computeDescriptors$$inlined$let$lambda$3
            r0.<init>()
            kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
            kotlin.sequences.Sequence r0 = kotlin.sequences.SequencesKt.generateSequence(r0)
            java.util.List r0 = kotlin.sequences.SequencesKt.toList(r0)
            if (r0 == 0) goto L2a
            java.util.Collection r0 = (java.util.Collection) r0
            goto L30
        L2a:
            java.util.List r0 = kotlin.collections.CollectionsKt.emptyList()
            java.util.Collection r0 = (java.util.Collection) r0
        L30:
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.util.Collection r1 = (java.util.Collection) r1
            java.util.Iterator r0 = r0.iterator()
        L3d:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L5c
            java.lang.Object r2 = r0.next()
            kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Property r2 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property) r2
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationContext r3 = r5.c
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer r3 = r3.getMemberDeserializer()
            java.lang.String r4 = "it"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r2, r4)
            kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor r2 = r3.loadProperty(r2)
            r1.add(r2)
            goto L3d
        L5c:
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            r0 = r1
            java.util.Collection r0 = (java.util.Collection) r0
            r5.computeNonDeclaredProperties(r6, r0)
            java.util.List r6 = kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact(r1)
            java.util.Collection r6 = (java.util.Collection) r6
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope.computeProperties(kotlin.reflect.jvm.internal.impl.name.Name):java.util.Collection");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final TypeAliasDescriptor createTypeAlias(Name name) {
        ProtoBuf.TypeAlias parseDelimitedFrom;
        byte[] bArr = this.typeAliasBytes.get(name);
        if (bArr == null || (parseDelimitedFrom = ProtoBuf.TypeAlias.parseDelimitedFrom(new ByteArrayInputStream(bArr), this.c.getComponents().getExtensionRegistryLite())) == null) {
            return null;
        }
        return this.c.getMemberDeserializer().loadTypeAlias(parseDelimitedFrom);
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    public Collection<PropertyDescriptor> getContributedVariables(Name name, LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        return !getVariableNames().contains(name) ? CollectionsKt.emptyList() : this.properties.invoke(name);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final Collection<DeclarationDescriptor> computeDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(kindFilter, "kindFilter");
        Intrinsics.checkParameterIsNotNull(nameFilter, "nameFilter");
        Intrinsics.checkParameterIsNotNull(location, "location");
        ArrayList arrayList = new ArrayList(0);
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getSINGLETON_CLASSIFIERS_MASK())) {
            addEnumEntryDescriptors(arrayList, nameFilter);
        }
        ArrayList arrayList2 = arrayList;
        addFunctionsAndProperties(arrayList2, kindFilter, nameFilter, location);
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getCLASSIFIERS_MASK())) {
            for (Name name : getClassNames$deserialization()) {
                if (nameFilter.invoke(name).booleanValue()) {
                    kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(arrayList2, deserializeClass(name));
                }
            }
        }
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getTYPE_ALIASES_MASK())) {
            for (Name name2 : getTypeAliasNames()) {
                if (nameFilter.invoke(name2).booleanValue()) {
                    kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(arrayList2, this.typeAliasByName.invoke(name2));
                }
            }
        }
        return kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact(arrayList);
    }

    private final void addFunctionsAndProperties(Collection<DeclarationDescriptor> collection, DescriptorKindFilter descriptorKindFilter, Function1<? super Name, Boolean> function1, LookupLocation lookupLocation) {
        if (descriptorKindFilter.acceptsKinds(DescriptorKindFilter.Companion.getVARIABLES_MASK())) {
            ArrayList arrayList = new ArrayList();
            for (Name name : getVariableNames()) {
                if (function1.invoke(name).booleanValue()) {
                    arrayList.addAll(getContributedVariables(name, lookupLocation));
                }
            }
            MemberComparator.NameAndTypeMemberComparator nameAndTypeMemberComparator = MemberComparator.NameAndTypeMemberComparator.INSTANCE;
            Intrinsics.checkExpressionValueIsNotNull(nameAndTypeMemberComparator, "MemberComparator.NameAnd…MemberComparator.INSTANCE");
            CollectionsKt.sortWith(arrayList, nameAndTypeMemberComparator);
            collection.addAll(arrayList);
        }
        if (descriptorKindFilter.acceptsKinds(DescriptorKindFilter.Companion.getFUNCTIONS_MASK())) {
            ArrayList arrayList2 = new ArrayList();
            for (Name name2 : getFunctionNames()) {
                if (function1.invoke(name2).booleanValue()) {
                    arrayList2.addAll(getContributedFunctions(name2, lookupLocation));
                }
            }
            MemberComparator.NameAndTypeMemberComparator nameAndTypeMemberComparator2 = MemberComparator.NameAndTypeMemberComparator.INSTANCE;
            Intrinsics.checkExpressionValueIsNotNull(nameAndTypeMemberComparator2, "MemberComparator.NameAnd…MemberComparator.INSTANCE");
            CollectionsKt.sortWith(arrayList2, nameAndTypeMemberComparator2);
            collection.addAll(arrayList2);
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    /* renamed from: getContributedClassifier */
    public ClassifierDescriptor mo1094getContributedClassifier(Name name, LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        if (hasClass(name)) {
            return deserializeClass(name);
        }
        if (getTypeAliasNames().contains(name)) {
            return this.typeAliasByName.invoke(name);
        }
        return null;
    }

    private final ClassDescriptor deserializeClass(Name name) {
        return this.c.getComponents().deserializeClass(createClassId(name));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean hasClass(Name name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return getClassNames$deserialization().contains(name);
    }

    private final Map<Name, byte[]> packToByteArray(Map<Name, ? extends Collection<? extends AbstractMessageLite>> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
        Iterator<T> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Iterable<AbstractMessageLite> iterable = (Iterable) entry.getValue();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
            for (AbstractMessageLite abstractMessageLite : iterable) {
                abstractMessageLite.writeDelimitedTo(byteArrayOutputStream);
                arrayList.add(Unit.INSTANCE);
            }
            linkedHashMap.put(key, byteArrayOutputStream.toByteArray());
        }
        return linkedHashMap;
    }
}
