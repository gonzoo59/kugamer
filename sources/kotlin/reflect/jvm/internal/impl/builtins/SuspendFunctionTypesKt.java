package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EmptyPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.MutableClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
/* compiled from: suspendFunctionTypes.kt */
/* loaded from: classes2.dex */
public final class SuspendFunctionTypesKt {
    private static final MutableClassDescriptor FAKE_CONTINUATION_CLASS_DESCRIPTOR_EXPERIMENTAL;
    private static final MutableClassDescriptor FAKE_CONTINUATION_CLASS_DESCRIPTOR_RELEASE;

    static {
        ModuleDescriptor errorModule = ErrorUtils.getErrorModule();
        Intrinsics.checkExpressionValueIsNotNull(errorModule, "ErrorUtils.getErrorModule()");
        FqName fqName = DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_EXPERIMENTAL;
        Intrinsics.checkExpressionValueIsNotNull(fqName, "DescriptorUtils.COROUTIN…KAGE_FQ_NAME_EXPERIMENTAL");
        MutableClassDescriptor mutableClassDescriptor = new MutableClassDescriptor(new EmptyPackageFragmentDescriptor(errorModule, fqName), ClassKind.INTERFACE, false, false, DescriptorUtils.CONTINUATION_INTERFACE_FQ_NAME_EXPERIMENTAL.shortName(), SourceElement.NO_SOURCE, LockBasedStorageManager.NO_LOCKS);
        mutableClassDescriptor.setModality(Modality.ABSTRACT);
        mutableClassDescriptor.setVisibility(Visibilities.PUBLIC);
        mutableClassDescriptor.setTypeParameterDescriptors(CollectionsKt.listOf(TypeParameterDescriptorImpl.createWithDefaultBound(mutableClassDescriptor, Annotations.Companion.getEMPTY(), false, Variance.IN_VARIANCE, Name.identifier("T"), 0, LockBasedStorageManager.NO_LOCKS)));
        mutableClassDescriptor.createTypeConstructor();
        FAKE_CONTINUATION_CLASS_DESCRIPTOR_EXPERIMENTAL = mutableClassDescriptor;
        ModuleDescriptor errorModule2 = ErrorUtils.getErrorModule();
        Intrinsics.checkExpressionValueIsNotNull(errorModule2, "ErrorUtils.getErrorModule()");
        FqName fqName2 = DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE;
        Intrinsics.checkExpressionValueIsNotNull(fqName2, "DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE");
        MutableClassDescriptor mutableClassDescriptor2 = new MutableClassDescriptor(new EmptyPackageFragmentDescriptor(errorModule2, fqName2), ClassKind.INTERFACE, false, false, DescriptorUtils.CONTINUATION_INTERFACE_FQ_NAME_RELEASE.shortName(), SourceElement.NO_SOURCE, LockBasedStorageManager.NO_LOCKS);
        mutableClassDescriptor2.setModality(Modality.ABSTRACT);
        mutableClassDescriptor2.setVisibility(Visibilities.PUBLIC);
        mutableClassDescriptor2.setTypeParameterDescriptors(CollectionsKt.listOf(TypeParameterDescriptorImpl.createWithDefaultBound(mutableClassDescriptor2, Annotations.Companion.getEMPTY(), false, Variance.IN_VARIANCE, Name.identifier("T"), 0, LockBasedStorageManager.NO_LOCKS)));
        mutableClassDescriptor2.createTypeConstructor();
        FAKE_CONTINUATION_CLASS_DESCRIPTOR_RELEASE = mutableClassDescriptor2;
    }

    public static final SimpleType transformSuspendFunctionToRuntimeFunctionType(KotlinType suspendFunType, boolean z) {
        TypeConstructor typeConstructor;
        SimpleType createFunctionType;
        Intrinsics.checkParameterIsNotNull(suspendFunType, "suspendFunType");
        FunctionTypesKt.isSuspendFunctionType(suspendFunType);
        KotlinBuiltIns builtIns = TypeUtilsKt.getBuiltIns(suspendFunType);
        Annotations annotations = suspendFunType.getAnnotations();
        KotlinType receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(suspendFunType);
        List<TypeProjection> valueParameterTypesFromFunctionType = FunctionTypesKt.getValueParameterTypesFromFunctionType(suspendFunType);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameterTypesFromFunctionType, 10));
        for (TypeProjection typeProjection : valueParameterTypesFromFunctionType) {
            arrayList.add(typeProjection.getType());
        }
        ArrayList arrayList2 = arrayList;
        Annotations empty = Annotations.Companion.getEMPTY();
        if (z) {
            typeConstructor = FAKE_CONTINUATION_CLASS_DESCRIPTOR_RELEASE.getTypeConstructor();
        } else {
            typeConstructor = FAKE_CONTINUATION_CLASS_DESCRIPTOR_EXPERIMENTAL.getTypeConstructor();
        }
        TypeConstructor typeConstructor2 = typeConstructor;
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor2, "if (isReleaseCoroutines)…ERIMENTAL.typeConstructor");
        List plus = CollectionsKt.plus((Collection<? extends SimpleType>) arrayList2, KotlinTypeFactory.simpleType$default(empty, typeConstructor2, CollectionsKt.listOf(TypeUtilsKt.asTypeProjection(FunctionTypesKt.getReturnTypeFromFunctionType(suspendFunType))), false, null, 16, null));
        SimpleType nullableAnyType = TypeUtilsKt.getBuiltIns(suspendFunType).getNullableAnyType();
        Intrinsics.checkExpressionValueIsNotNull(nullableAnyType, "suspendFunType.builtIns.nullableAnyType");
        createFunctionType = FunctionTypesKt.createFunctionType(builtIns, annotations, receiverTypeFromFunctionType, plus, null, nullableAnyType, (r14 & 64) != 0 ? false : false);
        return createFunctionType.makeNullableAsSpecified(suspendFunType.isMarkedNullable());
    }

    public static final boolean isContinuation(FqName fqName, boolean z) {
        if (z) {
            return Intrinsics.areEqual(fqName, DescriptorUtils.CONTINUATION_INTERFACE_FQ_NAME_RELEASE);
        }
        return Intrinsics.areEqual(fqName, DescriptorUtils.CONTINUATION_INTERFACE_FQ_NAME_EXPERIMENTAL);
    }
}
