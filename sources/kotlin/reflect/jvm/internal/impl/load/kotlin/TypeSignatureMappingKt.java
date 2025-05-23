package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.SuspendFunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnhancementKt;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.SimpleClassicTypeSystemContext;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.FunctionsKt;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
/* compiled from: typeSignatureMapping.kt */
/* loaded from: classes2.dex */
public final class TypeSignatureMappingKt {
    private static final <T> T boxTypeIfNeeded(JvmTypeFactory<T> jvmTypeFactory, T t, boolean z) {
        return z ? jvmTypeFactory.boxType(t) : t;
    }

    public static /* synthetic */ Object mapType$default(KotlinType kotlinType, JvmTypeFactory jvmTypeFactory, TypeMappingMode typeMappingMode, TypeMappingConfiguration typeMappingConfiguration, JvmDescriptorTypeWriter jvmDescriptorTypeWriter, Function3 function3, int i, Object obj) {
        if ((i & 32) != 0) {
            function3 = FunctionsKt.getDO_NOTHING_3();
        }
        return mapType(kotlinType, jvmTypeFactory, typeMappingMode, typeMappingConfiguration, jvmDescriptorTypeWriter, function3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v12, types: [T, java.lang.Object] */
    public static final <T> T mapType(KotlinType kotlinType, JvmTypeFactory<T> factory, TypeMappingMode mode, TypeMappingConfiguration<? extends T> typeMappingConfiguration, JvmDescriptorTypeWriter<T> jvmDescriptorTypeWriter, Function3<? super KotlinType, ? super T, ? super TypeMappingMode, Unit> writeGenericType) {
        Object obj;
        KotlinType kotlinType2;
        Object mapType;
        Intrinsics.checkParameterIsNotNull(kotlinType, "kotlinType");
        Intrinsics.checkParameterIsNotNull(factory, "factory");
        Intrinsics.checkParameterIsNotNull(mode, "mode");
        Intrinsics.checkParameterIsNotNull(typeMappingConfiguration, "typeMappingConfiguration");
        Intrinsics.checkParameterIsNotNull(writeGenericType, "writeGenericType");
        KotlinType preprocessType = typeMappingConfiguration.preprocessType(kotlinType);
        if (preprocessType != null) {
            return (T) mapType(preprocessType, factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
        }
        if (FunctionTypesKt.isSuspendFunctionType(kotlinType)) {
            return (T) mapType(SuspendFunctionTypesKt.transformSuspendFunctionToRuntimeFunctionType(kotlinType, typeMappingConfiguration.releaseCoroutines()), factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
        }
        KotlinType kotlinType3 = kotlinType;
        Object mapBuiltInType = mapBuiltInType(SimpleClassicTypeSystemContext.INSTANCE, kotlinType3, factory, mode);
        if (mapBuiltInType != null) {
            ?? r11 = (Object) boxTypeIfNeeded(factory, mapBuiltInType, mode.getNeedPrimitiveBoxing());
            writeGenericType.invoke(kotlinType, r11, mode);
            return r11;
        }
        TypeConstructor constructor = kotlinType.getConstructor();
        if (constructor instanceof IntersectionTypeConstructor) {
            return (T) mapType(TypeUtilsKt.replaceArgumentsWithStarProjections(typeMappingConfiguration.commonSupertype(((IntersectionTypeConstructor) constructor).mo1093getSupertypes())), factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
        }
        ClassifierDescriptor mo1092getDeclarationDescriptor = constructor.mo1092getDeclarationDescriptor();
        if (mo1092getDeclarationDescriptor != null) {
            Intrinsics.checkExpressionValueIsNotNull(mo1092getDeclarationDescriptor, "constructor.declarationD…structor of $kotlinType\")");
            if (ErrorUtils.isError(mo1092getDeclarationDescriptor)) {
                T t = (T) factory.createObjectType("error/NonExistentClass");
                if (mo1092getDeclarationDescriptor != null) {
                    typeMappingConfiguration.processErrorType(kotlinType, (ClassDescriptor) mo1092getDeclarationDescriptor);
                    if (jvmDescriptorTypeWriter != 0) {
                        jvmDescriptorTypeWriter.writeClass(t);
                    }
                    return t;
                }
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
            boolean z = mo1092getDeclarationDescriptor instanceof ClassDescriptor;
            if (z && KotlinBuiltIns.isArray(kotlinType)) {
                if (kotlinType.getArguments().size() != 1) {
                    throw new UnsupportedOperationException("arrays must have one type argument");
                }
                TypeProjection typeProjection = kotlinType.getArguments().get(0);
                KotlinType type = typeProjection.getType();
                Intrinsics.checkExpressionValueIsNotNull(type, "memberProjection.type");
                if (typeProjection.getProjectionKind() == Variance.IN_VARIANCE) {
                    mapType = factory.createObjectType("java/lang/Object");
                    if (jvmDescriptorTypeWriter != 0) {
                        jvmDescriptorTypeWriter.writeArrayType();
                        jvmDescriptorTypeWriter.writeClass(mapType);
                        jvmDescriptorTypeWriter.writeArrayEnd();
                    }
                } else {
                    if (jvmDescriptorTypeWriter != 0) {
                        jvmDescriptorTypeWriter.writeArrayType();
                    }
                    Variance projectionKind = typeProjection.getProjectionKind();
                    Intrinsics.checkExpressionValueIsNotNull(projectionKind, "memberProjection.projectionKind");
                    mapType = mapType(type, factory, mode.toGenericArgumentMode(projectionKind), typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
                    if (jvmDescriptorTypeWriter != 0) {
                        jvmDescriptorTypeWriter.writeArrayEnd();
                    }
                }
                return (T) factory.createFromString("[" + factory.toString(mapType));
            } else if (z) {
                ClassDescriptor classDescriptor = (ClassDescriptor) mo1092getDeclarationDescriptor;
                if (classDescriptor.isInline() && !mode.getNeedInlineClassWrapping() && (kotlinType2 = (KotlinType) InlineClassMappingKt.computeExpandedTypeForInlineClass(SimpleClassicTypeSystemContext.INSTANCE, kotlinType3)) != null) {
                    return (T) mapType(kotlinType2, factory, mode.wrapInlineClassesMode(), typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
                }
                if (mode.isForAnnotationParameter() && KotlinBuiltIns.isKClass(classDescriptor)) {
                    obj = (Object) factory.getJavaLangClassType();
                } else {
                    ClassDescriptor original = classDescriptor.getOriginal();
                    Intrinsics.checkExpressionValueIsNotNull(original, "descriptor.original");
                    T predefinedTypeForClass = typeMappingConfiguration.getPredefinedTypeForClass(original);
                    if (predefinedTypeForClass != null) {
                        obj = (Object) predefinedTypeForClass;
                    } else {
                        if (classDescriptor.getKind() == ClassKind.ENUM_ENTRY) {
                            DeclarationDescriptor containingDeclaration = classDescriptor.getContainingDeclaration();
                            if (containingDeclaration == null) {
                                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                            }
                            classDescriptor = (ClassDescriptor) containingDeclaration;
                        }
                        ClassDescriptor original2 = classDescriptor.getOriginal();
                        Intrinsics.checkExpressionValueIsNotNull(original2, "enumClassIfEnumEntry.original");
                        obj = (Object) factory.createObjectType(computeInternalName(original2, typeMappingConfiguration));
                    }
                }
                writeGenericType.invoke(kotlinType, obj, mode);
                return (T) obj;
            } else if (mo1092getDeclarationDescriptor instanceof TypeParameterDescriptor) {
                T t2 = (T) mapType(TypeUtilsKt.getRepresentativeUpperBound((TypeParameterDescriptor) mo1092getDeclarationDescriptor), factory, mode, typeMappingConfiguration, null, FunctionsKt.getDO_NOTHING_3());
                if (jvmDescriptorTypeWriter != 0) {
                    Name name = mo1092getDeclarationDescriptor.getName();
                    Intrinsics.checkExpressionValueIsNotNull(name, "descriptor.getName()");
                    jvmDescriptorTypeWriter.writeTypeVariable(name, t2);
                }
                return t2;
            } else {
                throw new UnsupportedOperationException("Unknown type " + kotlinType);
            }
        }
        throw new UnsupportedOperationException("no descriptor for type constructor of " + kotlinType);
    }

    public static final boolean hasVoidReturnType(CallableDescriptor descriptor) {
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        if (descriptor instanceof ConstructorDescriptor) {
            return true;
        }
        KotlinType returnType = descriptor.getReturnType();
        if (returnType == null) {
            Intrinsics.throwNpe();
        }
        if (KotlinBuiltIns.isUnit(returnType)) {
            KotlinType returnType2 = descriptor.getReturnType();
            if (returnType2 == null) {
                Intrinsics.throwNpe();
            }
            if (!TypeUtils.isNullableType(returnType2) && !(descriptor instanceof PropertyGetterDescriptor)) {
                return true;
            }
        }
        return false;
    }

    public static final <T> T mapBuiltInType(TypeSystemCommonBackendContext mapBuiltInType, KotlinTypeMarker type, JvmTypeFactory<T> typeFactory, TypeMappingMode mode) {
        Intrinsics.checkParameterIsNotNull(mapBuiltInType, "$this$mapBuiltInType");
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(typeFactory, "typeFactory");
        Intrinsics.checkParameterIsNotNull(mode, "mode");
        TypeConstructorMarker typeConstructor = mapBuiltInType.typeConstructor(type);
        if (mapBuiltInType.isClassTypeConstructor(typeConstructor)) {
            PrimitiveType primitiveType = mapBuiltInType.getPrimitiveType(typeConstructor);
            boolean z = true;
            if (primitiveType != null) {
                JvmPrimitiveType jvmPrimitiveType = JvmPrimitiveType.get(primitiveType);
                Intrinsics.checkExpressionValueIsNotNull(jvmPrimitiveType, "JvmPrimitiveType.get(primitiveType)");
                String desc = jvmPrimitiveType.getDesc();
                Intrinsics.checkExpressionValueIsNotNull(desc, "JvmPrimitiveType.get(primitiveType).desc");
                T createFromString = typeFactory.createFromString(desc);
                if (!mapBuiltInType.isNullableType(type) && !TypeEnhancementKt.hasEnhancedNullability(mapBuiltInType, type)) {
                    z = false;
                }
                return (T) boxTypeIfNeeded(typeFactory, createFromString, z);
            }
            PrimitiveType primitiveArrayType = mapBuiltInType.getPrimitiveArrayType(typeConstructor);
            if (primitiveArrayType != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                JvmPrimitiveType jvmPrimitiveType2 = JvmPrimitiveType.get(primitiveArrayType);
                Intrinsics.checkExpressionValueIsNotNull(jvmPrimitiveType2, "JvmPrimitiveType.get(arrayElementType)");
                sb.append(jvmPrimitiveType2.getDesc());
                return typeFactory.createFromString(sb.toString());
            }
            if (mapBuiltInType.isUnderKotlinPackage(typeConstructor)) {
                FqNameUnsafe classFqNameUnsafe = mapBuiltInType.getClassFqNameUnsafe(typeConstructor);
                ClassId mapKotlinToJava = classFqNameUnsafe != null ? JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(classFqNameUnsafe) : null;
                if (mapKotlinToJava != null) {
                    if (!mode.getKotlinCollectionsToJavaCollections()) {
                        List<JavaToKotlinClassMap.PlatformMutabilityMapping> mutabilityMappings = JavaToKotlinClassMap.INSTANCE.getMutabilityMappings();
                        if (!(mutabilityMappings instanceof Collection) || !mutabilityMappings.isEmpty()) {
                            for (JavaToKotlinClassMap.PlatformMutabilityMapping platformMutabilityMapping : mutabilityMappings) {
                                if (Intrinsics.areEqual(platformMutabilityMapping.getJavaClass(), mapKotlinToJava)) {
                                    break;
                                }
                            }
                        }
                        z = false;
                        if (z) {
                            return null;
                        }
                    }
                    JvmClassName byClassId = JvmClassName.byClassId(mapKotlinToJava);
                    Intrinsics.checkExpressionValueIsNotNull(byClassId, "JvmClassName.byClassId(classId)");
                    String internalName = byClassId.getInternalName();
                    Intrinsics.checkExpressionValueIsNotNull(internalName, "JvmClassName.byClassId(classId).internalName");
                    return typeFactory.createObjectType(internalName);
                }
            }
            return null;
        }
        return null;
    }

    public static /* synthetic */ String computeInternalName$default(ClassDescriptor classDescriptor, TypeMappingConfiguration typeMappingConfiguration, int i, Object obj) {
        if ((i & 2) != 0) {
            typeMappingConfiguration = TypeMappingConfigurationImpl.INSTANCE;
        }
        return computeInternalName(classDescriptor, typeMappingConfiguration);
    }

    public static final String computeInternalName(ClassDescriptor klass, TypeMappingConfiguration<?> typeMappingConfiguration) {
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        Intrinsics.checkParameterIsNotNull(typeMappingConfiguration, "typeMappingConfiguration");
        String predefinedFullInternalNameForClass = typeMappingConfiguration.getPredefinedFullInternalNameForClass(klass);
        if (predefinedFullInternalNameForClass != null) {
            return predefinedFullInternalNameForClass;
        }
        DeclarationDescriptor containingDeclaration = klass.getContainingDeclaration();
        Intrinsics.checkExpressionValueIsNotNull(containingDeclaration, "klass.containingDeclaration");
        Name safeIdentifier = SpecialNames.safeIdentifier(klass.getName());
        Intrinsics.checkExpressionValueIsNotNull(safeIdentifier, "SpecialNames.safeIdentifier(klass.name)");
        String identifier = safeIdentifier.getIdentifier();
        Intrinsics.checkExpressionValueIsNotNull(identifier, "SpecialNames.safeIdentifier(klass.name).identifier");
        if (containingDeclaration instanceof PackageFragmentDescriptor) {
            FqName fqName = ((PackageFragmentDescriptor) containingDeclaration).getFqName();
            if (fqName.isRoot()) {
                return identifier;
            }
            StringBuilder sb = new StringBuilder();
            String asString = fqName.asString();
            Intrinsics.checkExpressionValueIsNotNull(asString, "fqName.asString()");
            sb.append(StringsKt.replace$default(asString, '.', '/', false, 4, (Object) null));
            sb.append('/');
            sb.append(identifier);
            return sb.toString();
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) (!(containingDeclaration instanceof ClassDescriptor) ? null : containingDeclaration);
        if (classDescriptor == null) {
            throw new IllegalArgumentException("Unexpected container: " + containingDeclaration + " for " + klass);
        }
        String predefinedInternalNameForClass = typeMappingConfiguration.getPredefinedInternalNameForClass(classDescriptor);
        if (predefinedInternalNameForClass == null) {
            predefinedInternalNameForClass = computeInternalName(classDescriptor, typeMappingConfiguration);
        }
        return predefinedInternalNameForClass + Typography.dollar + identifier;
    }
}
