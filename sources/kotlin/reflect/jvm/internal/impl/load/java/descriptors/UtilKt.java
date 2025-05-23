package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
/* compiled from: util.kt */
/* loaded from: classes2.dex */
public final class UtilKt {
    public static final List<ValueParameterDescriptor> copyValueParameters(Collection<ValueParameterData> newValueParametersTypes, Collection<? extends ValueParameterDescriptor> oldValueParameters, CallableDescriptor newOwner) {
        Intrinsics.checkParameterIsNotNull(newValueParametersTypes, "newValueParametersTypes");
        Intrinsics.checkParameterIsNotNull(oldValueParameters, "oldValueParameters");
        Intrinsics.checkParameterIsNotNull(newOwner, "newOwner");
        newValueParametersTypes.size();
        oldValueParameters.size();
        List<Pair> zip = CollectionsKt.zip(newValueParametersTypes, oldValueParameters);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(zip, 10));
        for (Pair pair : zip) {
            ValueParameterData valueParameterData = (ValueParameterData) pair.component1();
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) pair.component2();
            int index = valueParameterDescriptor.getIndex();
            Annotations annotations = valueParameterDescriptor.getAnnotations();
            Name name = valueParameterDescriptor.getName();
            Intrinsics.checkExpressionValueIsNotNull(name, "oldParameter.name");
            KotlinType type = valueParameterData.getType();
            boolean hasDefaultValue = valueParameterData.getHasDefaultValue();
            boolean isCrossinline = valueParameterDescriptor.isCrossinline();
            boolean isNoinline = valueParameterDescriptor.isNoinline();
            KotlinType arrayElementType = valueParameterDescriptor.getVarargElementType() != null ? DescriptorUtilsKt.getModule(newOwner).getBuiltIns().getArrayElementType(valueParameterData.getType()) : null;
            SourceElement source = valueParameterDescriptor.getSource();
            Intrinsics.checkExpressionValueIsNotNull(source, "oldParameter.source");
            arrayList.add(new ValueParameterDescriptorImpl(newOwner, null, index, annotations, name, type, hasDefaultValue, isCrossinline, isNoinline, arrayElementType, source));
        }
        return arrayList;
    }

    public static final LazyJavaStaticClassScope getParentJavaStaticClassScope(ClassDescriptor getParentJavaStaticClassScope) {
        Intrinsics.checkParameterIsNotNull(getParentJavaStaticClassScope, "$this$getParentJavaStaticClassScope");
        ClassDescriptor superClassNotAny = DescriptorUtilsKt.getSuperClassNotAny(getParentJavaStaticClassScope);
        if (superClassNotAny != null) {
            MemberScope staticScope = superClassNotAny.getStaticScope();
            LazyJavaStaticClassScope lazyJavaStaticClassScope = staticScope instanceof LazyJavaStaticClassScope ? staticScope : null;
            return lazyJavaStaticClassScope != null ? lazyJavaStaticClassScope : getParentJavaStaticClassScope(superClassNotAny);
        }
        return null;
    }

    public static final AnnotationDefaultValue getDefaultValueFromAnnotation(ValueParameterDescriptor getDefaultValueFromAnnotation) {
        ConstantValue<?> firstArgument;
        String value;
        Intrinsics.checkParameterIsNotNull(getDefaultValueFromAnnotation, "$this$getDefaultValueFromAnnotation");
        Annotations annotations = getDefaultValueFromAnnotation.getAnnotations();
        FqName fqName = JvmAnnotationNames.DEFAULT_VALUE_FQ_NAME;
        Intrinsics.checkExpressionValueIsNotNull(fqName, "JvmAnnotationNames.DEFAULT_VALUE_FQ_NAME");
        AnnotationDescriptor mo1087findAnnotation = annotations.mo1087findAnnotation(fqName);
        if (mo1087findAnnotation != null && (firstArgument = DescriptorUtilsKt.firstArgument(mo1087findAnnotation)) != null) {
            if (!(firstArgument instanceof StringValue)) {
                firstArgument = null;
            }
            StringValue stringValue = (StringValue) firstArgument;
            if (stringValue != null && (value = stringValue.getValue()) != null) {
                return new StringDefaultValue(value);
            }
        }
        Annotations annotations2 = getDefaultValueFromAnnotation.getAnnotations();
        FqName fqName2 = JvmAnnotationNames.DEFAULT_NULL_FQ_NAME;
        Intrinsics.checkExpressionValueIsNotNull(fqName2, "JvmAnnotationNames.DEFAULT_NULL_FQ_NAME");
        if (annotations2.hasAnnotation(fqName2)) {
            return NullDefaultValue.INSTANCE;
        }
        return null;
    }
}
