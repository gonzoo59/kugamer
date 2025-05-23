package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorBase;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EmptyPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.ClassTypeConstructorImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
/* compiled from: NotFoundClasses.kt */
/* loaded from: classes2.dex */
public final class NotFoundClasses {
    private final MemoizedFunctionToNotNull<ClassRequest, ClassDescriptor> classes;
    private final ModuleDescriptor module;
    private final MemoizedFunctionToNotNull<FqName, PackageFragmentDescriptor> packageFragments;
    private final StorageManager storageManager;

    public NotFoundClasses(StorageManager storageManager, ModuleDescriptor module) {
        Intrinsics.checkParameterIsNotNull(storageManager, "storageManager");
        Intrinsics.checkParameterIsNotNull(module, "module");
        this.storageManager = storageManager;
        this.module = module;
        this.packageFragments = storageManager.createMemoizedFunction(new Function1<FqName, EmptyPackageFragmentDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses$packageFragments$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final EmptyPackageFragmentDescriptor invoke(FqName fqName) {
                ModuleDescriptor moduleDescriptor;
                Intrinsics.checkParameterIsNotNull(fqName, "fqName");
                moduleDescriptor = NotFoundClasses.this.module;
                return new EmptyPackageFragmentDescriptor(moduleDescriptor, fqName);
            }
        });
        this.classes = storageManager.createMemoizedFunction(new Function1<ClassRequest, MockClassDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses$classes$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            /* JADX WARN: Removed duplicated region for block: B:12:0x0066  */
            /* JADX WARN: Removed duplicated region for block: B:13:0x006c  */
            @Override // kotlin.jvm.functions.Function1
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses.MockClassDescriptor invoke(kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses.ClassRequest r10) {
                /*
                    r9 = this;
                    java.lang.String r0 = "<name for destructuring parameter 0>"
                    kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r10, r0)
                    kotlin.reflect.jvm.internal.impl.name.ClassId r0 = r10.component1()
                    java.util.List r10 = r10.component2()
                    boolean r1 = r0.isLocal()
                    if (r1 != 0) goto L73
                    kotlin.reflect.jvm.internal.impl.name.ClassId r1 = r0.getOuterClassId()
                    if (r1 == 0) goto L31
                    kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses r2 = kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses.this
                    java.lang.String r3 = "outerClassId"
                    kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r1, r3)
                    r3 = r10
                    java.lang.Iterable r3 = (java.lang.Iterable) r3
                    r4 = 1
                    java.util.List r3 = kotlin.collections.CollectionsKt.drop(r3, r4)
                    kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor r1 = r2.getClass(r1, r3)
                    if (r1 == 0) goto L31
                    kotlin.reflect.jvm.internal.impl.descriptors.ClassOrPackageFragmentDescriptor r1 = (kotlin.reflect.jvm.internal.impl.descriptors.ClassOrPackageFragmentDescriptor) r1
                    goto L46
                L31:
                    kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses r1 = kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses.this
                    kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull r1 = kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses.access$getPackageFragments$p(r1)
                    kotlin.reflect.jvm.internal.impl.name.FqName r2 = r0.getPackageFqName()
                    java.lang.String r3 = "classId.packageFqName"
                    kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r2, r3)
                    java.lang.Object r1 = r1.invoke(r2)
                    kotlin.reflect.jvm.internal.impl.descriptors.ClassOrPackageFragmentDescriptor r1 = (kotlin.reflect.jvm.internal.impl.descriptors.ClassOrPackageFragmentDescriptor) r1
                L46:
                    boolean r6 = r0.isNestedClass()
                    kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses$MockClassDescriptor r8 = new kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses$MockClassDescriptor
                    kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses r2 = kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses.this
                    kotlin.reflect.jvm.internal.impl.storage.StorageManager r3 = kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses.access$getStorageManager$p(r2)
                    r4 = r1
                    kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor r4 = (kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor) r4
                    kotlin.reflect.jvm.internal.impl.name.Name r5 = r0.getShortClassName()
                    java.lang.String r0 = "classId.shortClassName"
                    kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r5, r0)
                    java.lang.Object r10 = kotlin.collections.CollectionsKt.firstOrNull(r10)
                    java.lang.Integer r10 = (java.lang.Integer) r10
                    if (r10 == 0) goto L6c
                    int r10 = r10.intValue()
                    r7 = r10
                    goto L6e
                L6c:
                    r10 = 0
                    r7 = 0
                L6e:
                    r2 = r8
                    r2.<init>(r3, r4, r5, r6, r7)
                    return r8
                L73:
                    java.lang.UnsupportedOperationException r10 = new java.lang.UnsupportedOperationException
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    java.lang.String r2 = "Unresolved local class: "
                    r1.append(r2)
                    r1.append(r0)
                    java.lang.String r0 = r1.toString()
                    r10.<init>(r0)
                    java.lang.Throwable r10 = (java.lang.Throwable) r10
                    throw r10
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses$classes$1.invoke(kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses$ClassRequest):kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses$MockClassDescriptor");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: NotFoundClasses.kt */
    /* loaded from: classes2.dex */
    public static final class ClassRequest {
        private final ClassId classId;
        private final List<Integer> typeParametersCount;

        public final ClassId component1() {
            return this.classId;
        }

        public final List<Integer> component2() {
            return this.typeParametersCount;
        }

        public boolean equals(Object obj) {
            if (this != obj) {
                if (obj instanceof ClassRequest) {
                    ClassRequest classRequest = (ClassRequest) obj;
                    return Intrinsics.areEqual(this.classId, classRequest.classId) && Intrinsics.areEqual(this.typeParametersCount, classRequest.typeParametersCount);
                }
                return false;
            }
            return true;
        }

        public int hashCode() {
            ClassId classId = this.classId;
            int hashCode = (classId != null ? classId.hashCode() : 0) * 31;
            List<Integer> list = this.typeParametersCount;
            return hashCode + (list != null ? list.hashCode() : 0);
        }

        public String toString() {
            return "ClassRequest(classId=" + this.classId + ", typeParametersCount=" + this.typeParametersCount + ")";
        }

        public ClassRequest(ClassId classId, List<Integer> typeParametersCount) {
            Intrinsics.checkParameterIsNotNull(classId, "classId");
            Intrinsics.checkParameterIsNotNull(typeParametersCount, "typeParametersCount");
            this.classId = classId;
            this.typeParametersCount = typeParametersCount;
        }
    }

    /* compiled from: NotFoundClasses.kt */
    /* loaded from: classes2.dex */
    public static final class MockClassDescriptor extends ClassDescriptorBase {
        private final boolean isInner;
        private final ClassTypeConstructorImpl typeConstructor;
        private final List<TypeParameterDescriptor> typeParameters;

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        /* renamed from: getCompanionObjectDescriptor */
        public ClassDescriptor mo1085getCompanionObjectDescriptor() {
            return null;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        /* renamed from: getUnsubstitutedPrimaryConstructor */
        public ClassConstructorDescriptor mo1086getUnsubstitutedPrimaryConstructor() {
            return null;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
        public boolean isActual() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public boolean isCompanionObject() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public boolean isData() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
        public boolean isExpect() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorBase, kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
        public boolean isExternal() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public boolean isInline() {
            return false;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MockClassDescriptor(StorageManager storageManager, DeclarationDescriptor container, Name name, boolean z, int i) {
            super(storageManager, container, name, SourceElement.NO_SOURCE, false);
            Intrinsics.checkParameterIsNotNull(storageManager, "storageManager");
            Intrinsics.checkParameterIsNotNull(container, "container");
            Intrinsics.checkParameterIsNotNull(name, "name");
            this.isInner = z;
            IntRange until = RangesKt.until(0, i);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(until, 10));
            Iterator<Integer> it = until.iterator();
            while (it.hasNext()) {
                int nextInt = ((IntIterator) it).nextInt();
                Annotations empty = Annotations.Companion.getEMPTY();
                Variance variance = Variance.INVARIANT;
                StringBuilder sb = new StringBuilder();
                sb.append('T');
                sb.append(nextInt);
                arrayList.add(TypeParameterDescriptorImpl.createWithDefaultBound(this, empty, false, variance, Name.identifier(sb.toString()), nextInt, storageManager));
            }
            ArrayList arrayList2 = arrayList;
            this.typeParameters = arrayList2;
            this.typeConstructor = new ClassTypeConstructorImpl(this, arrayList2, SetsKt.setOf(DescriptorUtilsKt.getModule(this).getBuiltIns().getAnyType()), storageManager);
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public ClassKind getKind() {
            return ClassKind.CLASS;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
        public Modality getModality() {
            return Modality.FINAL;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility, kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
        public Visibility getVisibility() {
            Visibility visibility = Visibilities.PUBLIC;
            Intrinsics.checkExpressionValueIsNotNull(visibility, "Visibilities.PUBLIC");
            return visibility;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor
        public ClassTypeConstructorImpl getTypeConstructor() {
            return this.typeConstructor;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters
        public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
            return this.typeParameters;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters
        public boolean isInner() {
            return this.isInner;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated
        public Annotations getAnnotations() {
            return Annotations.Companion.getEMPTY();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleAwareClassDescriptor
        public MemberScope.Empty getUnsubstitutedMemberScope(KotlinTypeRefiner kotlinTypeRefiner) {
            Intrinsics.checkParameterIsNotNull(kotlinTypeRefiner, "kotlinTypeRefiner");
            return MemberScope.Empty.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public MemberScope.Empty getStaticScope() {
            return MemberScope.Empty.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public Collection<ClassConstructorDescriptor> getConstructors() {
            return SetsKt.emptySet();
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public Collection<ClassDescriptor> getSealedSubclasses() {
            return CollectionsKt.emptyList();
        }

        public String toString() {
            return "class " + getName() + " (not found)";
        }
    }

    public final ClassDescriptor getClass(ClassId classId, List<Integer> typeParametersCount) {
        Intrinsics.checkParameterIsNotNull(classId, "classId");
        Intrinsics.checkParameterIsNotNull(typeParametersCount, "typeParametersCount");
        return this.classes.invoke(new ClassRequest(classId, typeParametersCount));
    }
}
