package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.List;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.functions.BuiltInFictitiousFunctionClassFactory;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.BuiltInAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
/* compiled from: functionTypes.kt */
/* loaded from: classes2.dex */
public final class FunctionTypesKt {
    public static final boolean isFunctionType(KotlinType isFunctionType) {
        Intrinsics.checkParameterIsNotNull(isFunctionType, "$this$isFunctionType");
        ClassifierDescriptor mo1092getDeclarationDescriptor = isFunctionType.getConstructor().mo1092getDeclarationDescriptor();
        return (mo1092getDeclarationDescriptor != null ? getFunctionalClassKind(mo1092getDeclarationDescriptor) : null) == FunctionClassDescriptor.Kind.Function;
    }

    public static final boolean isSuspendFunctionType(KotlinType isSuspendFunctionType) {
        Intrinsics.checkParameterIsNotNull(isSuspendFunctionType, "$this$isSuspendFunctionType");
        ClassifierDescriptor mo1092getDeclarationDescriptor = isSuspendFunctionType.getConstructor().mo1092getDeclarationDescriptor();
        return (mo1092getDeclarationDescriptor != null ? getFunctionalClassKind(mo1092getDeclarationDescriptor) : null) == FunctionClassDescriptor.Kind.SuspendFunction;
    }

    public static final boolean isBuiltinFunctionalType(KotlinType isBuiltinFunctionalType) {
        Intrinsics.checkParameterIsNotNull(isBuiltinFunctionalType, "$this$isBuiltinFunctionalType");
        ClassifierDescriptor mo1092getDeclarationDescriptor = isBuiltinFunctionalType.getConstructor().mo1092getDeclarationDescriptor();
        FunctionClassDescriptor.Kind functionalClassKind = mo1092getDeclarationDescriptor != null ? getFunctionalClassKind(mo1092getDeclarationDescriptor) : null;
        return functionalClassKind == FunctionClassDescriptor.Kind.Function || functionalClassKind == FunctionClassDescriptor.Kind.SuspendFunction;
    }

    public static final boolean isBuiltinExtensionFunctionalType(KotlinType isBuiltinExtensionFunctionalType) {
        Intrinsics.checkParameterIsNotNull(isBuiltinExtensionFunctionalType, "$this$isBuiltinExtensionFunctionalType");
        return isBuiltinFunctionalType(isBuiltinExtensionFunctionalType) && isTypeAnnotatedWithExtensionFunctionType(isBuiltinExtensionFunctionalType);
    }

    private static final boolean isTypeAnnotatedWithExtensionFunctionType(KotlinType kotlinType) {
        Annotations annotations = kotlinType.getAnnotations();
        FqName fqName = KotlinBuiltIns.FQ_NAMES.extensionFunctionType;
        Intrinsics.checkExpressionValueIsNotNull(fqName, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
        return annotations.mo1087findAnnotation(fqName) != null;
    }

    public static final FunctionClassDescriptor.Kind getFunctionalClassKind(DeclarationDescriptor getFunctionalClassKind) {
        Intrinsics.checkParameterIsNotNull(getFunctionalClassKind, "$this$getFunctionalClassKind");
        if ((getFunctionalClassKind instanceof ClassDescriptor) && KotlinBuiltIns.isUnderKotlinPackage(getFunctionalClassKind)) {
            return getFunctionalClassKind(DescriptorUtilsKt.getFqNameUnsafe(getFunctionalClassKind));
        }
        return null;
    }

    private static final FunctionClassDescriptor.Kind getFunctionalClassKind(FqNameUnsafe fqNameUnsafe) {
        if (!fqNameUnsafe.isSafe() || fqNameUnsafe.isRoot()) {
            return null;
        }
        BuiltInFictitiousFunctionClassFactory.Companion companion = BuiltInFictitiousFunctionClassFactory.Companion;
        String asString = fqNameUnsafe.shortName().asString();
        Intrinsics.checkExpressionValueIsNotNull(asString, "shortName().asString()");
        FqName parent = fqNameUnsafe.toSafe().parent();
        Intrinsics.checkExpressionValueIsNotNull(parent, "toSafe().parent()");
        return companion.getFunctionalClassKind(asString, parent);
    }

    public static final KotlinType getReceiverTypeFromFunctionType(KotlinType getReceiverTypeFromFunctionType) {
        Intrinsics.checkParameterIsNotNull(getReceiverTypeFromFunctionType, "$this$getReceiverTypeFromFunctionType");
        isBuiltinFunctionalType(getReceiverTypeFromFunctionType);
        if (isTypeAnnotatedWithExtensionFunctionType(getReceiverTypeFromFunctionType)) {
            return ((TypeProjection) CollectionsKt.first((List<? extends Object>) getReceiverTypeFromFunctionType.getArguments())).getType();
        }
        return null;
    }

    public static final KotlinType getReturnTypeFromFunctionType(KotlinType getReturnTypeFromFunctionType) {
        Intrinsics.checkParameterIsNotNull(getReturnTypeFromFunctionType, "$this$getReturnTypeFromFunctionType");
        isBuiltinFunctionalType(getReturnTypeFromFunctionType);
        KotlinType type = ((TypeProjection) CollectionsKt.last((List<? extends Object>) getReturnTypeFromFunctionType.getArguments())).getType();
        Intrinsics.checkExpressionValueIsNotNull(type, "arguments.last().type");
        return type;
    }

    public static final List<TypeProjection> getValueParameterTypesFromFunctionType(KotlinType getValueParameterTypesFromFunctionType) {
        Intrinsics.checkParameterIsNotNull(getValueParameterTypesFromFunctionType, "$this$getValueParameterTypesFromFunctionType");
        isBuiltinFunctionalType(getValueParameterTypesFromFunctionType);
        List<TypeProjection> arguments = getValueParameterTypesFromFunctionType.getArguments();
        return arguments.subList(isBuiltinExtensionFunctionalType(getValueParameterTypesFromFunctionType) ? 1 : 0, arguments.size() - 1);
    }

    public static final Name extractParameterNameFromFunctionTypeArgument(KotlinType extractParameterNameFromFunctionTypeArgument) {
        String value;
        Intrinsics.checkParameterIsNotNull(extractParameterNameFromFunctionTypeArgument, "$this$extractParameterNameFromFunctionTypeArgument");
        Annotations annotations = extractParameterNameFromFunctionTypeArgument.getAnnotations();
        FqName fqName = KotlinBuiltIns.FQ_NAMES.parameterName;
        Intrinsics.checkExpressionValueIsNotNull(fqName, "KotlinBuiltIns.FQ_NAMES.parameterName");
        AnnotationDescriptor mo1087findAnnotation = annotations.mo1087findAnnotation(fqName);
        if (mo1087findAnnotation != null) {
            Object singleOrNull = CollectionsKt.singleOrNull(mo1087findAnnotation.getAllValueArguments().values());
            if (!(singleOrNull instanceof StringValue)) {
                singleOrNull = null;
            }
            StringValue stringValue = (StringValue) singleOrNull;
            if (stringValue != null && (value = stringValue.getValue()) != null) {
                if (!Name.isValidIdentifier(value)) {
                    value = null;
                }
                if (value != null) {
                    return Name.identifier(value);
                }
            }
        }
        return null;
    }

    public static final List<TypeProjection> getFunctionTypeArgumentProjections(KotlinType kotlinType, List<? extends KotlinType> parameterTypes, List<Name> list, KotlinType returnType, KotlinBuiltIns builtIns) {
        Intrinsics.checkParameterIsNotNull(parameterTypes, "parameterTypes");
        Intrinsics.checkParameterIsNotNull(returnType, "returnType");
        Intrinsics.checkParameterIsNotNull(builtIns, "builtIns");
        int i = 0;
        ArrayList arrayList = new ArrayList(parameterTypes.size() + (kotlinType != null ? 1 : 0) + 1);
        ArrayList arrayList2 = arrayList;
        kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(arrayList2, kotlinType != null ? TypeUtilsKt.asTypeProjection(kotlinType) : null);
        for (Object obj : parameterTypes) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            KotlinType kotlinType2 = (KotlinType) obj;
            Name name = (list == null || (name = list.get(i)) == null || name.isSpecial()) ? null : null;
            if (name != null) {
                FqName fqName = KotlinBuiltIns.FQ_NAMES.parameterName;
                Intrinsics.checkExpressionValueIsNotNull(fqName, "KotlinBuiltIns.FQ_NAMES.parameterName");
                Name identifier = Name.identifier("name");
                String asString = name.asString();
                Intrinsics.checkExpressionValueIsNotNull(asString, "name.asString()");
                kotlinType2 = TypeUtilsKt.replaceAnnotations(kotlinType2, Annotations.Companion.create(CollectionsKt.plus(kotlinType2.getAnnotations(), new BuiltInAnnotationDescriptor(builtIns, fqName, MapsKt.mapOf(TuplesKt.to(identifier, new StringValue(asString)))))));
            }
            arrayList2.add(TypeUtilsKt.asTypeProjection(kotlinType2));
            i = i2;
        }
        arrayList.add(TypeUtilsKt.asTypeProjection(returnType));
        return arrayList;
    }

    public static final SimpleType createFunctionType(KotlinBuiltIns builtIns, Annotations annotations, KotlinType kotlinType, List<? extends KotlinType> parameterTypes, List<Name> list, KotlinType returnType, boolean z) {
        Intrinsics.checkParameterIsNotNull(builtIns, "builtIns");
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        Intrinsics.checkParameterIsNotNull(parameterTypes, "parameterTypes");
        Intrinsics.checkParameterIsNotNull(returnType, "returnType");
        List<TypeProjection> functionTypeArgumentProjections = getFunctionTypeArgumentProjections(kotlinType, parameterTypes, list, returnType, builtIns);
        int size = parameterTypes.size();
        if (kotlinType != null) {
            size++;
        }
        ClassDescriptor suspendFunction = z ? builtIns.getSuspendFunction(size) : builtIns.getFunction(size);
        Intrinsics.checkExpressionValueIsNotNull(suspendFunction, "if (suspendFunction) bui…tFunction(parameterCount)");
        if (kotlinType != null) {
            FqName fqName = KotlinBuiltIns.FQ_NAMES.extensionFunctionType;
            Intrinsics.checkExpressionValueIsNotNull(fqName, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
            if (annotations.mo1087findAnnotation(fqName) == null) {
                Annotations.Companion companion = Annotations.Companion;
                FqName fqName2 = KotlinBuiltIns.FQ_NAMES.extensionFunctionType;
                Intrinsics.checkExpressionValueIsNotNull(fqName2, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
                annotations = companion.create(CollectionsKt.plus(annotations, new BuiltInAnnotationDescriptor(builtIns, fqName2, MapsKt.emptyMap())));
            }
        }
        return KotlinTypeFactory.simpleNotNullType(annotations, suspendFunction, functionTypeArgumentProjections);
    }
}
