package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
/* loaded from: classes2.dex */
public class ClassConstructorDescriptorImpl extends FunctionDescriptorImpl implements ClassConstructorDescriptor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Name NAME = Name.special("<init>");
    protected final boolean isPrimary;

    /* JADX WARN: Removed duplicated region for block: B:12:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a5 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static /* synthetic */ void $$$reportNull$$$0(int r8) {
        /*
            Method dump skipped, instructions count: 324
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassConstructorDescriptorImpl.$$$reportNull$$$0(int):void");
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor
    public void setOverriddenDescriptors(Collection<? extends CallableMemberDescriptor> collection) {
        if (collection == null) {
            $$$reportNull$$$0(20);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassConstructorDescriptorImpl(ClassDescriptor classDescriptor, ConstructorDescriptor constructorDescriptor, Annotations annotations, boolean z, CallableMemberDescriptor.Kind kind, SourceElement sourceElement) {
        super(classDescriptor, constructorDescriptor, annotations, NAME, kind, sourceElement);
        if (classDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (annotations == null) {
            $$$reportNull$$$0(1);
        }
        if (kind == null) {
            $$$reportNull$$$0(2);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(3);
        }
        this.isPrimary = z;
    }

    public static ClassConstructorDescriptorImpl create(ClassDescriptor classDescriptor, Annotations annotations, boolean z, SourceElement sourceElement) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (annotations == null) {
            $$$reportNull$$$0(5);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(6);
        }
        return new ClassConstructorDescriptorImpl(classDescriptor, null, annotations, z, CallableMemberDescriptor.Kind.DECLARATION, sourceElement);
    }

    public ClassConstructorDescriptorImpl initialize(List<ValueParameterDescriptor> list, Visibility visibility, List<TypeParameterDescriptor> list2) {
        if (list == null) {
            $$$reportNull$$$0(10);
        }
        if (visibility == null) {
            $$$reportNull$$$0(11);
        }
        if (list2 == null) {
            $$$reportNull$$$0(12);
        }
        super.initialize(null, calculateDispatchReceiverParameter(), list2, list, null, Modality.FINAL, visibility);
        return this;
    }

    public ClassConstructorDescriptorImpl initialize(List<ValueParameterDescriptor> list, Visibility visibility) {
        if (list == null) {
            $$$reportNull$$$0(13);
        }
        if (visibility == null) {
            $$$reportNull$$$0(14);
        }
        initialize(list, visibility, getContainingDeclaration().getDeclaredTypeParameters());
        return this;
    }

    public ReceiverParameterDescriptor calculateDispatchReceiverParameter() {
        ClassDescriptor containingDeclaration = getContainingDeclaration();
        if (containingDeclaration.isInner()) {
            DeclarationDescriptor containingDeclaration2 = containingDeclaration.getContainingDeclaration();
            if (containingDeclaration2 instanceof ClassDescriptor) {
                return ((ClassDescriptor) containingDeclaration2).getThisAsReceiverParameter();
            }
            return null;
        }
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorNonRootImpl, kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor
    public ClassDescriptor getContainingDeclaration() {
        ClassDescriptor classDescriptor = (ClassDescriptor) super.getContainingDeclaration();
        if (classDescriptor == null) {
            $$$reportNull$$$0(15);
        }
        return classDescriptor;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor
    public ClassDescriptor getConstructedClass() {
        ClassDescriptor containingDeclaration = getContainingDeclaration();
        if (containingDeclaration == null) {
            $$$reportNull$$$0(16);
        }
        return containingDeclaration;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorNonRootImpl, kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor
    public ClassConstructorDescriptor getOriginal() {
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) super.getOriginal();
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(17);
        }
        return classConstructorDescriptor;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.Substitutable
    public ClassConstructorDescriptor substitute(TypeSubstitutor typeSubstitutor) {
        if (typeSubstitutor == null) {
            $$$reportNull$$$0(18);
        }
        return (ClassConstructorDescriptor) super.substitute(typeSubstitutor);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> declarationDescriptorVisitor, D d) {
        return declarationDescriptorVisitor.visitConstructorDescriptor(this, d);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor
    public boolean isPrimary() {
        return this.isPrimary;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor
    public Collection<? extends FunctionDescriptor> getOverriddenDescriptors() {
        Set emptySet = Collections.emptySet();
        if (emptySet == null) {
            $$$reportNull$$$0(19);
        }
        return emptySet;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl
    public ClassConstructorDescriptorImpl createSubstitutedCopy(DeclarationDescriptor declarationDescriptor, FunctionDescriptor functionDescriptor, CallableMemberDescriptor.Kind kind, Name name, Annotations annotations, SourceElement sourceElement) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(21);
        }
        if (kind == null) {
            $$$reportNull$$$0(22);
        }
        if (annotations == null) {
            $$$reportNull$$$0(23);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(24);
        }
        if (kind != CallableMemberDescriptor.Kind.DECLARATION && kind != CallableMemberDescriptor.Kind.SYNTHESIZED) {
            throw new IllegalStateException("Attempt at creating a constructor that is not a declaration: \ncopy from: " + this + "\nnewOwner: " + declarationDescriptor + "\nkind: " + kind);
        }
        return new ClassConstructorDescriptorImpl((ClassDescriptor) declarationDescriptor, this, annotations, this.isPrimary, CallableMemberDescriptor.Kind.DECLARATION, sourceElement);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor
    public ClassConstructorDescriptor copy(DeclarationDescriptor declarationDescriptor, Modality modality, Visibility visibility, CallableMemberDescriptor.Kind kind, boolean z) {
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) super.copy(declarationDescriptor, modality, visibility, kind, z);
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(25);
        }
        return classConstructorDescriptor;
    }
}
