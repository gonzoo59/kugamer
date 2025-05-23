package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;
/* loaded from: classes2.dex */
public abstract class AbstractClassTypeConstructor extends AbstractTypeConstructor implements TypeConstructor {
    private int hashCode;

    /* JADX WARN: Removed duplicated region for block: B:23:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static /* synthetic */ void $$$reportNull$$$0(int r9) {
        /*
            r0 = 4
            r1 = 3
            r2 = 1
            if (r9 == r2) goto Lc
            if (r9 == r1) goto Lc
            if (r9 == r0) goto Lc
            java.lang.String r3 = "Argument for @NotNull parameter '%s' of %s.%s must not be null"
            goto Le
        Lc:
            java.lang.String r3 = "@NotNull method %s.%s must not return null"
        Le:
            r4 = 2
            if (r9 == r2) goto L17
            if (r9 == r1) goto L17
            if (r9 == r0) goto L17
            r5 = 3
            goto L18
        L17:
            r5 = 2
        L18:
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.String r6 = "kotlin/reflect/jvm/internal/impl/types/AbstractClassTypeConstructor"
            r7 = 0
            if (r9 == r2) goto L2f
            if (r9 == r4) goto L2a
            if (r9 == r1) goto L2f
            if (r9 == r0) goto L2f
            java.lang.String r8 = "storageManager"
            r5[r7] = r8
            goto L31
        L2a:
            java.lang.String r8 = "descriptor"
            r5[r7] = r8
            goto L31
        L2f:
            r5[r7] = r6
        L31:
            if (r9 == r2) goto L3f
            if (r9 == r1) goto L3a
            if (r9 == r0) goto L3a
            r5[r2] = r6
            goto L43
        L3a:
            java.lang.String r6 = "getAdditionalNeighboursInSupertypeGraph"
            r5[r2] = r6
            goto L43
        L3f:
            java.lang.String r6 = "getBuiltIns"
            r5[r2] = r6
        L43:
            if (r9 == r2) goto L54
            if (r9 == r4) goto L50
            if (r9 == r1) goto L54
            if (r9 == r0) goto L54
            java.lang.String r6 = "<init>"
            r5[r4] = r6
            goto L54
        L50:
            java.lang.String r6 = "hasMeaningfulFqName"
            r5[r4] = r6
        L54:
            java.lang.String r3 = java.lang.String.format(r3, r5)
            if (r9 == r2) goto L64
            if (r9 == r1) goto L64
            if (r9 == r0) goto L64
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            r9.<init>(r3)
            goto L69
        L64:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            r9.<init>(r3)
        L69:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.AbstractClassTypeConstructor.$$$reportNull$$$0(int):void");
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor, kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    /* renamed from: getDeclarationDescriptor */
    public abstract ClassDescriptor mo1092getDeclarationDescriptor();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractClassTypeConstructor(StorageManager storageManager) {
        super(storageManager);
        if (storageManager == null) {
            $$$reportNull$$$0(0);
        }
        this.hashCode = 0;
    }

    public final int hashCode() {
        int identityHashCode;
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        ClassDescriptor mo1092getDeclarationDescriptor = mo1092getDeclarationDescriptor();
        if (hasMeaningfulFqName(mo1092getDeclarationDescriptor)) {
            identityHashCode = DescriptorUtils.getFqName(mo1092getDeclarationDescriptor).hashCode();
        } else {
            identityHashCode = System.identityHashCode(this);
        }
        this.hashCode = identityHashCode;
        return identityHashCode;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    public KotlinBuiltIns getBuiltIns() {
        KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(mo1092getDeclarationDescriptor());
        if (builtIns == null) {
            $$$reportNull$$$0(1);
        }
        return builtIns;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof TypeConstructor) && obj.hashCode() == hashCode()) {
            TypeConstructor typeConstructor = (TypeConstructor) obj;
            if (typeConstructor.getParameters().size() != getParameters().size()) {
                return false;
            }
            ClassDescriptor mo1092getDeclarationDescriptor = mo1092getDeclarationDescriptor();
            ClassifierDescriptor mo1092getDeclarationDescriptor2 = typeConstructor.mo1092getDeclarationDescriptor();
            if (hasMeaningfulFqName(mo1092getDeclarationDescriptor) && ((mo1092getDeclarationDescriptor2 == null || hasMeaningfulFqName(mo1092getDeclarationDescriptor2)) && (mo1092getDeclarationDescriptor2 instanceof ClassDescriptor))) {
                return areFqNamesEqual(mo1092getDeclarationDescriptor, (ClassDescriptor) mo1092getDeclarationDescriptor2);
            }
            return false;
        }
        return false;
    }

    private static boolean areFqNamesEqual(ClassDescriptor classDescriptor, ClassDescriptor classDescriptor2) {
        if (classDescriptor.getName().equals(classDescriptor2.getName())) {
            DeclarationDescriptor containingDeclaration = classDescriptor.getContainingDeclaration();
            for (DeclarationDescriptor containingDeclaration2 = classDescriptor2.getContainingDeclaration(); containingDeclaration != null && containingDeclaration2 != null; containingDeclaration2 = containingDeclaration2.getContainingDeclaration()) {
                if (containingDeclaration instanceof ModuleDescriptor) {
                    return containingDeclaration2 instanceof ModuleDescriptor;
                }
                if (containingDeclaration2 instanceof ModuleDescriptor) {
                    return false;
                }
                if (containingDeclaration instanceof PackageFragmentDescriptor) {
                    return (containingDeclaration2 instanceof PackageFragmentDescriptor) && ((PackageFragmentDescriptor) containingDeclaration).getFqName().equals(((PackageFragmentDescriptor) containingDeclaration2).getFqName());
                } else if ((containingDeclaration2 instanceof PackageFragmentDescriptor) || !containingDeclaration.getName().equals(containingDeclaration2.getName())) {
                    return false;
                } else {
                    containingDeclaration = containingDeclaration.getContainingDeclaration();
                }
            }
            return true;
        }
        return false;
    }

    private static boolean hasMeaningfulFqName(ClassifierDescriptor classifierDescriptor) {
        if (classifierDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        return (ErrorUtils.isError(classifierDescriptor) || DescriptorUtils.isLocal(classifierDescriptor)) ? false : true;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor
    protected Collection<KotlinType> getAdditionalNeighboursInSupertypeGraph(boolean z) {
        DeclarationDescriptor containingDeclaration = mo1092getDeclarationDescriptor().getContainingDeclaration();
        if (!(containingDeclaration instanceof ClassDescriptor)) {
            List emptyList = Collections.emptyList();
            if (emptyList == null) {
                $$$reportNull$$$0(3);
            }
            return emptyList;
        }
        SmartList smartList = new SmartList();
        ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
        smartList.add(classDescriptor.getDefaultType());
        ClassDescriptor mo1085getCompanionObjectDescriptor = classDescriptor.mo1085getCompanionObjectDescriptor();
        if (z && mo1085getCompanionObjectDescriptor != null) {
            smartList.add(mo1085getCompanionObjectDescriptor.getDefaultType());
        }
        return smartList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor
    public KotlinType defaultSupertypeIfEmpty() {
        if (KotlinBuiltIns.isSpecialClassWithNoSupertypes(mo1092getDeclarationDescriptor())) {
            return null;
        }
        return getBuiltIns().getAnyType();
    }
}
