package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Function;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function11;
import kotlin.jvm.functions.Function12;
import kotlin.jvm.functions.Function13;
import kotlin.jvm.functions.Function14;
import kotlin.jvm.functions.Function15;
import kotlin.jvm.functions.Function16;
import kotlin.jvm.functions.Function17;
import kotlin.jvm.functions.Function18;
import kotlin.jvm.functions.Function19;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function20;
import kotlin.jvm.functions.Function21;
import kotlin.jvm.functions.Function22;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
/* compiled from: reflectClassUtil.kt */
/* loaded from: classes2.dex */
public final class ReflectClassUtilKt {
    private static final Map<Class<? extends Function<?>>, Integer> FUNCTION_CLASSES;
    private static final List<KClass<? extends Object>> PRIMITIVE_CLASSES;
    private static final Map<Class<? extends Object>, Class<? extends Object>> PRIMITIVE_TO_WRAPPER;
    private static final Map<Class<? extends Object>, Class<? extends Object>> WRAPPER_TO_PRIMITIVE;

    public static final ClassLoader getSafeClassLoader(Class<?> safeClassLoader) {
        Intrinsics.checkParameterIsNotNull(safeClassLoader, "$this$safeClassLoader");
        ClassLoader classLoader = safeClassLoader.getClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        Intrinsics.checkExpressionValueIsNotNull(systemClassLoader, "ClassLoader.getSystemClassLoader()");
        return systemClassLoader;
    }

    public static final boolean isEnumClassOrSpecializedEnumEntryClass(Class<?> isEnumClassOrSpecializedEnumEntryClass) {
        Intrinsics.checkParameterIsNotNull(isEnumClassOrSpecializedEnumEntryClass, "$this$isEnumClassOrSpecializedEnumEntryClass");
        return Enum.class.isAssignableFrom(isEnumClassOrSpecializedEnumEntryClass);
    }

    static {
        int i = 0;
        List<KClass<? extends Object>> listOf = CollectionsKt.listOf((Object[]) new KClass[]{Reflection.getOrCreateKotlinClass(Boolean.TYPE), Reflection.getOrCreateKotlinClass(Byte.TYPE), Reflection.getOrCreateKotlinClass(Character.TYPE), Reflection.getOrCreateKotlinClass(Double.TYPE), Reflection.getOrCreateKotlinClass(Float.TYPE), Reflection.getOrCreateKotlinClass(Integer.TYPE), Reflection.getOrCreateKotlinClass(Long.TYPE), Reflection.getOrCreateKotlinClass(Short.TYPE)});
        PRIMITIVE_CLASSES = listOf;
        List<KClass<? extends Object>> list = listOf;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            KClass kClass = (KClass) it.next();
            arrayList.add(TuplesKt.to(JvmClassMappingKt.getJavaObjectType(kClass), JvmClassMappingKt.getJavaPrimitiveType(kClass)));
        }
        WRAPPER_TO_PRIMITIVE = MapsKt.toMap(arrayList);
        List<KClass<? extends Object>> list2 = PRIMITIVE_CLASSES;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            KClass kClass2 = (KClass) it2.next();
            arrayList2.add(TuplesKt.to(JvmClassMappingKt.getJavaPrimitiveType(kClass2), JvmClassMappingKt.getJavaObjectType(kClass2)));
        }
        PRIMITIVE_TO_WRAPPER = MapsKt.toMap(arrayList2);
        List listOf2 = CollectionsKt.listOf((Object[]) new Class[]{Function0.class, Function1.class, Function2.class, Function3.class, Function4.class, Function5.class, Function6.class, Function7.class, Function8.class, Function9.class, Function10.class, Function11.class, Function12.class, Function13.class, Function14.class, Function15.class, Function16.class, Function17.class, Function18.class, Function19.class, Function20.class, Function21.class, Function22.class});
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listOf2, 10));
        for (Object obj : listOf2) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList3.add(TuplesKt.to((Class) obj, Integer.valueOf(i)));
            i = i2;
        }
        FUNCTION_CLASSES = MapsKt.toMap(arrayList3);
    }

    public static final Class<?> getPrimitiveByWrapper(Class<?> primitiveByWrapper) {
        Intrinsics.checkParameterIsNotNull(primitiveByWrapper, "$this$primitiveByWrapper");
        return WRAPPER_TO_PRIMITIVE.get(primitiveByWrapper);
    }

    public static final Class<?> getWrapperByPrimitive(Class<?> wrapperByPrimitive) {
        Intrinsics.checkParameterIsNotNull(wrapperByPrimitive, "$this$wrapperByPrimitive");
        return PRIMITIVE_TO_WRAPPER.get(wrapperByPrimitive);
    }

    public static final Integer getFunctionClassArity(Class<?> functionClassArity) {
        Intrinsics.checkParameterIsNotNull(functionClassArity, "$this$functionClassArity");
        return FUNCTION_CLASSES.get(functionClassArity);
    }

    public static final ClassId getClassId(Class<?> classId) {
        ClassId classId2;
        ClassId createNestedClassId;
        Intrinsics.checkParameterIsNotNull(classId, "$this$classId");
        if (classId.isPrimitive()) {
            throw new IllegalArgumentException("Can't compute ClassId for primitive type: " + classId);
        } else if (classId.isArray()) {
            throw new IllegalArgumentException("Can't compute ClassId for array type: " + classId);
        } else {
            if (classId.getEnclosingMethod() == null && classId.getEnclosingConstructor() == null) {
                String simpleName = classId.getSimpleName();
                Intrinsics.checkExpressionValueIsNotNull(simpleName, "simpleName");
                if (!(simpleName.length() == 0)) {
                    Class<?> declaringClass = classId.getDeclaringClass();
                    if (declaringClass == null || (classId2 = getClassId(declaringClass)) == null || (createNestedClassId = classId2.createNestedClassId(Name.identifier(classId.getSimpleName()))) == null) {
                        ClassId classId3 = ClassId.topLevel(new FqName(classId.getName()));
                        Intrinsics.checkExpressionValueIsNotNull(classId3, "ClassId.topLevel(FqName(name))");
                        return classId3;
                    }
                    return createNestedClassId;
                }
            }
            FqName fqName = new FqName(classId.getName());
            return new ClassId(fqName.parent(), FqName.topLevel(fqName.shortName()), true);
        }
    }

    public static final String getDesc(Class<?> desc) {
        Intrinsics.checkParameterIsNotNull(desc, "$this$desc");
        if (Intrinsics.areEqual(desc, Void.TYPE)) {
            return "V";
        }
        String name = createArrayType(desc).getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "createArrayType().name");
        if (name != null) {
            String substring = name.substring(1);
            Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
            return StringsKt.replace$default(substring, '.', '/', false, 4, (Object) null);
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    public static final Class<?> createArrayType(Class<?> createArrayType) {
        Intrinsics.checkParameterIsNotNull(createArrayType, "$this$createArrayType");
        return Array.newInstance(createArrayType, 0).getClass();
    }

    public static final List<Type> getParameterizedTypeArguments(Type parameterizedTypeArguments) {
        Intrinsics.checkParameterIsNotNull(parameterizedTypeArguments, "$this$parameterizedTypeArguments");
        if (parameterizedTypeArguments instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) parameterizedTypeArguments;
            if (parameterizedType.getOwnerType() == null) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                Intrinsics.checkExpressionValueIsNotNull(actualTypeArguments, "actualTypeArguments");
                return ArraysKt.toList(actualTypeArguments);
            }
            return SequencesKt.toList(SequencesKt.flatMap(SequencesKt.generateSequence(parameterizedTypeArguments, new Function1<ParameterizedType, ParameterizedType>() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt$parameterizedTypeArguments$1
                @Override // kotlin.jvm.functions.Function1
                public final ParameterizedType invoke(ParameterizedType it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    Type ownerType = it.getOwnerType();
                    if (!(ownerType instanceof ParameterizedType)) {
                        ownerType = null;
                    }
                    return (ParameterizedType) ownerType;
                }
            }), new Function1<ParameterizedType, Sequence<? extends Type>>() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt$parameterizedTypeArguments$2
                @Override // kotlin.jvm.functions.Function1
                public final Sequence<Type> invoke(ParameterizedType it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    Type[] actualTypeArguments2 = it.getActualTypeArguments();
                    Intrinsics.checkExpressionValueIsNotNull(actualTypeArguments2, "it.actualTypeArguments");
                    return ArraysKt.asSequence(actualTypeArguments2);
                }
            }));
        }
        return CollectionsKt.emptyList();
    }
}
