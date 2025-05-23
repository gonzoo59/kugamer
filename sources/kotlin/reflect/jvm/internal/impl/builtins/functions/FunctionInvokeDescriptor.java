package kotlin.reflect.jvm.internal.impl.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.util.OperatorNameConventions;
/* compiled from: FunctionInvokeDescriptor.kt */
/* loaded from: classes2.dex */
public final class FunctionInvokeDescriptor extends SimpleFunctionDescriptorImpl {
    public static final Factory Factory = new Factory(null);

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
    public boolean isExternal() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor
    public boolean isInline() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor
    public boolean isTailrec() {
        return false;
    }

    public /* synthetic */ FunctionInvokeDescriptor(DeclarationDescriptor declarationDescriptor, FunctionInvokeDescriptor functionInvokeDescriptor, CallableMemberDescriptor.Kind kind, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(declarationDescriptor, functionInvokeDescriptor, kind, z);
    }

    private FunctionInvokeDescriptor(DeclarationDescriptor declarationDescriptor, FunctionInvokeDescriptor functionInvokeDescriptor, CallableMemberDescriptor.Kind kind, boolean z) {
        super(declarationDescriptor, functionInvokeDescriptor, Annotations.Companion.getEMPTY(), OperatorNameConventions.INVOKE, kind, SourceElement.NO_SOURCE);
        setOperator(true);
        setSuspend(z);
        setHasStableParameterNames(false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl
    public FunctionDescriptor doSubstitute(FunctionDescriptorImpl.CopyConfiguration configuration) {
        boolean z;
        Intrinsics.checkParameterIsNotNull(configuration, "configuration");
        FunctionInvokeDescriptor functionInvokeDescriptor = (FunctionInvokeDescriptor) super.doSubstitute(configuration);
        if (functionInvokeDescriptor != null) {
            List<ValueParameterDescriptor> valueParameters = functionInvokeDescriptor.getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(valueParameters, "substituted.valueParameters");
            List<ValueParameterDescriptor> list = valueParameters;
            boolean z2 = false;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                for (ValueParameterDescriptor it : list) {
                    Intrinsics.checkExpressionValueIsNotNull(it, "it");
                    KotlinType type = it.getType();
                    Intrinsics.checkExpressionValueIsNotNull(type, "it.type");
                    if (FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(type) != null) {
                        z = true;
                        continue;
                    } else {
                        z = false;
                        continue;
                    }
                    if (z) {
                        break;
                    }
                }
            }
            z2 = true;
            if (z2) {
                return functionInvokeDescriptor;
            }
            List<ValueParameterDescriptor> valueParameters2 = functionInvokeDescriptor.getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(valueParameters2, "substituted.valueParameters");
            List<ValueParameterDescriptor> list2 = valueParameters2;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (ValueParameterDescriptor it2 : list2) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                KotlinType type2 = it2.getType();
                Intrinsics.checkExpressionValueIsNotNull(type2, "it.type");
                arrayList.add(FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(type2));
            }
            return functionInvokeDescriptor.replaceParameterNames(arrayList);
        }
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl
    protected FunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor newOwner, FunctionDescriptor functionDescriptor, CallableMemberDescriptor.Kind kind, Name name, Annotations annotations, SourceElement source) {
        Intrinsics.checkParameterIsNotNull(newOwner, "newOwner");
        Intrinsics.checkParameterIsNotNull(kind, "kind");
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        Intrinsics.checkParameterIsNotNull(source, "source");
        return new FunctionInvokeDescriptor(newOwner, (FunctionInvokeDescriptor) functionDescriptor, kind, isSuspend());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v5, types: [java.lang.Object, kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl$CopyConfiguration] */
    private final FunctionDescriptor replaceParameterNames(List<Name> list) {
        boolean z;
        Name name;
        int size = getValueParameters().size() - list.size();
        boolean z2 = true;
        List<ValueParameterDescriptor> valueParameters = getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters, "valueParameters");
        List<ValueParameterDescriptor> list2 = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (ValueParameterDescriptor it : list2) {
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            Name name2 = it.getName();
            Intrinsics.checkExpressionValueIsNotNull(name2, "it.name");
            int index = it.getIndex();
            int i = index - size;
            if (i >= 0 && (name = list.get(i)) != null) {
                name2 = name;
            }
            arrayList.add(it.copy(this, name2, index));
        }
        ArrayList arrayList2 = arrayList;
        FunctionDescriptorImpl.CopyConfiguration newCopyBuilder = newCopyBuilder(TypeSubstitutor.EMPTY);
        List<Name> list3 = list;
        if (!(list3 instanceof Collection) || !list3.isEmpty()) {
            for (Name name3 : list3) {
                if (name3 == null) {
                    z = true;
                    continue;
                } else {
                    z = false;
                    continue;
                }
                if (z) {
                    break;
                }
            }
        }
        z2 = false;
        ?? original = newCopyBuilder.setHasSynthesizedParameterNames(z2).setValueParameters2((List<ValueParameterDescriptor>) arrayList2).setOriginal((CallableMemberDescriptor) getOriginal());
        Intrinsics.checkExpressionValueIsNotNull(original, "newCopyBuilder(TypeSubst…   .setOriginal(original)");
        FunctionDescriptor doSubstitute = super.doSubstitute(original);
        if (doSubstitute == null) {
            Intrinsics.throwNpe();
        }
        return doSubstitute;
    }

    /* compiled from: FunctionInvokeDescriptor.kt */
    /* loaded from: classes2.dex */
    public static final class Factory {
        private Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final FunctionInvokeDescriptor create(FunctionClassDescriptor functionClass, boolean z) {
            Intrinsics.checkParameterIsNotNull(functionClass, "functionClass");
            List<TypeParameterDescriptor> declaredTypeParameters = functionClass.getDeclaredTypeParameters();
            FunctionInvokeDescriptor functionInvokeDescriptor = new FunctionInvokeDescriptor(functionClass, null, CallableMemberDescriptor.Kind.DECLARATION, z, null);
            ReceiverParameterDescriptor thisAsReceiverParameter = functionClass.getThisAsReceiverParameter();
            List emptyList = CollectionsKt.emptyList();
            ArrayList arrayList = new ArrayList();
            for (Object obj : declaredTypeParameters) {
                if (!(((TypeParameterDescriptor) obj).getVariance() == Variance.IN_VARIANCE)) {
                    break;
                }
                arrayList.add(obj);
            }
            Iterable<IndexedValue> withIndex = CollectionsKt.withIndex(arrayList);
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(withIndex, 10));
            for (IndexedValue indexedValue : withIndex) {
                arrayList2.add(FunctionInvokeDescriptor.Factory.createValueParameter(functionInvokeDescriptor, indexedValue.getIndex(), (TypeParameterDescriptor) indexedValue.getValue()));
            }
            functionInvokeDescriptor.initialize((ReceiverParameterDescriptor) null, thisAsReceiverParameter, emptyList, (List) arrayList2, (KotlinType) ((TypeParameterDescriptor) CollectionsKt.last((List<? extends Object>) declaredTypeParameters)).getDefaultType(), Modality.ABSTRACT, Visibilities.PUBLIC);
            functionInvokeDescriptor.setHasSynthesizedParameterNames(true);
            return functionInvokeDescriptor;
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0032  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x0070  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private final kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor createValueParameter(kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionInvokeDescriptor r15, int r16, kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor r17) {
            /*
                r14 = this;
                kotlin.reflect.jvm.internal.impl.name.Name r0 = r17.getName()
                java.lang.String r0 = r0.asString()
                java.lang.String r1 = "typeParameter.name.asString()"
                kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r1)
                int r1 = r0.hashCode()
                r2 = 69
                if (r1 == r2) goto L25
                r2 = 84
                if (r1 == r2) goto L1a
                goto L30
            L1a:
                java.lang.String r1 = "T"
                boolean r1 = r0.equals(r1)
                if (r1 == 0) goto L30
                java.lang.String r0 = "instance"
                goto L3b
            L25:
                java.lang.String r1 = "E"
                boolean r1 = r0.equals(r1)
                if (r1 == 0) goto L30
                java.lang.String r0 = "receiver"
                goto L3b
            L30:
                if (r0 == 0) goto L70
                java.lang.String r0 = r0.toLowerCase()
                java.lang.String r1 = "(this as java.lang.String).toLowerCase()"
                kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r1)
            L3b:
                kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl r13 = new kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl
                r2 = r15
                kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor r2 = (kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor) r2
                r3 = 0
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations$Companion r1 = kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r5 = r1.getEMPTY()
                kotlin.reflect.jvm.internal.impl.name.Name r6 = kotlin.reflect.jvm.internal.impl.name.Name.identifier(r0)
                java.lang.String r0 = "Name.identifier(name)"
                kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r6, r0)
                kotlin.reflect.jvm.internal.impl.types.SimpleType r0 = r17.getDefaultType()
                java.lang.String r1 = "typeParameter.defaultType"
                kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r1)
                r7 = r0
                kotlin.reflect.jvm.internal.impl.types.KotlinType r7 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r7
                r8 = 0
                r9 = 0
                r10 = 0
                r11 = 0
                kotlin.reflect.jvm.internal.impl.descriptors.SourceElement r12 = kotlin.reflect.jvm.internal.impl.descriptors.SourceElement.NO_SOURCE
                java.lang.String r0 = "SourceElement.NO_SOURCE"
                kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r12, r0)
                r1 = r13
                r4 = r16
                r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
                kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor r13 = (kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor) r13
                return r13
            L70:
                kotlin.TypeCastException r0 = new kotlin.TypeCastException
                java.lang.String r1 = "null cannot be cast to non-null type java.lang.String"
                r0.<init>(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionInvokeDescriptor.Factory.createValueParameter(kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionInvokeDescriptor, int, kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor):kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor");
        }
    }
}
