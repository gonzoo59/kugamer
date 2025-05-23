package kotlin.reflect.full;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;
import kotlin.reflect.KType;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KClassImpl;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
/* compiled from: KClasses.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000Z\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\r\u001a+\u0010S\u001a\u0002H\u001d\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00022\b\u0010T\u001a\u0004\u0018\u00010\u0010H\u0007¢\u0006\u0002\u0010U\u001a!\u0010V\u001a\u0002H\u001d\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u0002H\u0007¢\u0006\u0002\u0010\u0013\u001a\u001c\u0010W\u001a\u000203*\u0006\u0012\u0002\b\u00030\u00022\n\u0010X\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0007\u001a\u001c\u0010Y\u001a\u000203*\u0006\u0012\u0002\b\u00030\u00022\n\u0010Z\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0007\u001a-\u0010[\u001a\u0004\u0018\u0001H\u001d\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00022\b\u0010T\u001a\u0004\u0018\u00010\u0010H\u0007¢\u0006\u0002\u0010U\",\u0010\u0000\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"(\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006\"(\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0002*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\f\u0010\u0004\u001a\u0004\b\r\u0010\u000e\"$\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0011\u0010\u0004\u001a\u0004\b\u0012\u0010\u0013\",\u0010\u0014\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0016\u0010\u0004\u001a\u0004\b\u0017\u0010\u0006\",\u0010\u0018\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0019\u0010\u0004\u001a\u0004\b\u001a\u0010\u0006\"B\u0010\u001b\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u001d\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001c0\u0001\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u001e\u0010\u0004\u001a\u0004\b\u001f\u0010\u0006\",\u0010 \u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b!\u0010\u0004\u001a\u0004\b\"\u0010\u0006\">\u0010#\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002H\u001d\u0012\u0002\b\u00030$0\u0001\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b%\u0010\u0004\u001a\u0004\b&\u0010\u0006\",\u0010'\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030(0\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b)\u0010\u0004\u001a\u0004\b*\u0010\u0006\"\"\u0010+\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b,\u0010\u0004\u001a\u0004\b-\u0010.\",\u0010/\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b0\u0010\u0004\u001a\u0004\b1\u0010\u0006\"\u001c\u00102\u001a\u000203*\u0006\u0012\u0002\b\u0003048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b2\u00105\"\u001c\u00106\u001a\u000203*\u0006\u0012\u0002\b\u0003048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b6\u00105\",\u00107\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b8\u0010\u0004\u001a\u0004\b9\u0010\u0006\"B\u0010:\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u001d\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001c0\u0001\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b;\u0010\u0004\u001a\u0004\b<\u0010\u0006\",\u0010=\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b>\u0010\u0004\u001a\u0004\b?\u0010\u0006\">\u0010@\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002H\u001d\u0012\u0002\b\u00030$0\u0001\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\bA\u0010\u0004\u001a\u0004\bB\u0010\u0006\"6\u0010C\u001a\n\u0012\u0004\u0012\u0002H\u001d\u0018\u00010\u0015\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\bD\u0010\u0004\u001a\u0004\bE\u0010F\",\u0010G\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\bH\u0010\u0004\u001a\u0004\bI\u0010\u0006\",\u0010J\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030K0\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\bL\u0010\u0004\u001a\u0004\bM\u0010\u0006\",\u0010N\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020O*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\bP\u0010\u0004\u001a\u0004\bQ\u0010R¨\u0006\\"}, d2 = {"allSuperclasses", "", "Lkotlin/reflect/KClass;", "allSuperclasses$annotations", "(Lkotlin/reflect/KClass;)V", "getAllSuperclasses", "(Lkotlin/reflect/KClass;)Ljava/util/Collection;", "allSupertypes", "Lkotlin/reflect/KType;", "allSupertypes$annotations", "getAllSupertypes", "companionObject", "companionObject$annotations", "getCompanionObject", "(Lkotlin/reflect/KClass;)Lkotlin/reflect/KClass;", "companionObjectInstance", "", "companionObjectInstance$annotations", "getCompanionObjectInstance", "(Lkotlin/reflect/KClass;)Ljava/lang/Object;", "declaredFunctions", "Lkotlin/reflect/KFunction;", "declaredFunctions$annotations", "getDeclaredFunctions", "declaredMemberExtensionFunctions", "declaredMemberExtensionFunctions$annotations", "getDeclaredMemberExtensionFunctions", "declaredMemberExtensionProperties", "Lkotlin/reflect/KProperty2;", "T", "declaredMemberExtensionProperties$annotations", "getDeclaredMemberExtensionProperties", "declaredMemberFunctions", "declaredMemberFunctions$annotations", "getDeclaredMemberFunctions", "declaredMemberProperties", "Lkotlin/reflect/KProperty1;", "declaredMemberProperties$annotations", "getDeclaredMemberProperties", "declaredMembers", "Lkotlin/reflect/KCallable;", "declaredMembers$annotations", "getDeclaredMembers", "defaultType", "defaultType$annotations", "getDefaultType", "(Lkotlin/reflect/KClass;)Lkotlin/reflect/KType;", "functions", "functions$annotations", "getFunctions", "isExtension", "", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "(Lkotlin/reflect/jvm/internal/KCallableImpl;)Z", "isNotExtension", "memberExtensionFunctions", "memberExtensionFunctions$annotations", "getMemberExtensionFunctions", "memberExtensionProperties", "memberExtensionProperties$annotations", "getMemberExtensionProperties", "memberFunctions", "memberFunctions$annotations", "getMemberFunctions", "memberProperties", "memberProperties$annotations", "getMemberProperties", "primaryConstructor", "primaryConstructor$annotations", "getPrimaryConstructor", "(Lkotlin/reflect/KClass;)Lkotlin/reflect/KFunction;", "staticFunctions", "staticFunctions$annotations", "getStaticFunctions", "staticProperties", "Lkotlin/reflect/KProperty0;", "staticProperties$annotations", "getStaticProperties", "superclasses", "", "superclasses$annotations", "getSuperclasses", "(Lkotlin/reflect/KClass;)Ljava/util/List;", "cast", "value", "(Lkotlin/reflect/KClass;Ljava/lang/Object;)Ljava/lang/Object;", "createInstance", "isSubclassOf", "base", "isSuperclassOf", "derived", "safeCast", "kotlin-reflection"}, k = 2, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class KClasses {
    public static /* synthetic */ void allSuperclasses$annotations(KClass kClass) {
    }

    public static /* synthetic */ void allSupertypes$annotations(KClass kClass) {
    }

    public static /* synthetic */ void companionObject$annotations(KClass kClass) {
    }

    public static /* synthetic */ void companionObjectInstance$annotations(KClass kClass) {
    }

    public static /* synthetic */ void declaredFunctions$annotations(KClass kClass) {
    }

    public static /* synthetic */ void declaredMemberExtensionFunctions$annotations(KClass kClass) {
    }

    public static /* synthetic */ void declaredMemberExtensionProperties$annotations(KClass kClass) {
    }

    public static /* synthetic */ void declaredMemberFunctions$annotations(KClass kClass) {
    }

    public static /* synthetic */ void declaredMemberProperties$annotations(KClass kClass) {
    }

    public static /* synthetic */ void declaredMembers$annotations(KClass kClass) {
    }

    @Deprecated(message = "This function creates a type which rarely makes sense for generic classes. For example, such type can only be used in signatures of members of that class. Use starProjectedType or createType() for clearer semantics.")
    public static /* synthetic */ void defaultType$annotations(KClass kClass) {
    }

    public static /* synthetic */ void functions$annotations(KClass kClass) {
    }

    public static /* synthetic */ void memberExtensionFunctions$annotations(KClass kClass) {
    }

    public static /* synthetic */ void memberExtensionProperties$annotations(KClass kClass) {
    }

    public static /* synthetic */ void memberFunctions$annotations(KClass kClass) {
    }

    public static /* synthetic */ void memberProperties$annotations(KClass kClass) {
    }

    public static /* synthetic */ void primaryConstructor$annotations(KClass kClass) {
    }

    public static /* synthetic */ void staticFunctions$annotations(KClass kClass) {
    }

    public static /* synthetic */ void staticProperties$annotations(KClass kClass) {
    }

    public static /* synthetic */ void superclasses$annotations(KClass kClass) {
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0044, code lost:
        return (kotlin.reflect.KFunction) r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <T> kotlin.reflect.KFunction<T> getPrimaryConstructor(kotlin.reflect.KClass<T> r2) {
        /*
            java.lang.String r0 = "$this$primaryConstructor"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r2, r0)
            kotlin.reflect.jvm.internal.KClassImpl r2 = (kotlin.reflect.jvm.internal.KClassImpl) r2
            java.util.Collection r2 = r2.getConstructors()
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            java.util.Iterator r2 = r2.iterator()
        L11:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L41
            java.lang.Object r0 = r2.next()
            r1 = r0
            kotlin.reflect.KFunction r1 = (kotlin.reflect.KFunction) r1
            if (r1 == 0) goto L39
            kotlin.reflect.jvm.internal.KFunctionImpl r1 = (kotlin.reflect.jvm.internal.KFunctionImpl) r1
            kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor r1 = r1.getDescriptor()
            if (r1 == 0) goto L31
            kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor r1 = (kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor) r1
            boolean r1 = r1.isPrimary()
            if (r1 == 0) goto L11
            goto L42
        L31:
            kotlin.TypeCastException r2 = new kotlin.TypeCastException
            java.lang.String r0 = "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ConstructorDescriptor"
            r2.<init>(r0)
            throw r2
        L39:
            kotlin.TypeCastException r2 = new kotlin.TypeCastException
            java.lang.String r0 = "null cannot be cast to non-null type kotlin.reflect.jvm.internal.KFunctionImpl"
            r2.<init>(r0)
            throw r2
        L41:
            r0 = 0
        L42:
            kotlin.reflect.KFunction r0 = (kotlin.reflect.KFunction) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.full.KClasses.getPrimaryConstructor(kotlin.reflect.KClass):kotlin.reflect.KFunction");
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0036, code lost:
        return (kotlin.reflect.KClass) r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final kotlin.reflect.KClass<?> getCompanionObject(kotlin.reflect.KClass<?> r2) {
        /*
            java.lang.String r0 = "$this$companionObject"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r2, r0)
            java.util.Collection r2 = r2.getNestedClasses()
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            java.util.Iterator r2 = r2.iterator()
        Lf:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L33
            java.lang.Object r0 = r2.next()
            r1 = r0
            kotlin.reflect.KClass r1 = (kotlin.reflect.KClass) r1
            if (r1 == 0) goto L2b
            kotlin.reflect.jvm.internal.KClassImpl r1 = (kotlin.reflect.jvm.internal.KClassImpl) r1
            kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor r1 = r1.getDescriptor()
            boolean r1 = r1.isCompanionObject()
            if (r1 == 0) goto Lf
            goto L34
        L2b:
            kotlin.TypeCastException r2 = new kotlin.TypeCastException
            java.lang.String r0 = "null cannot be cast to non-null type kotlin.reflect.jvm.internal.KClassImpl<*>"
            r2.<init>(r0)
            throw r2
        L33:
            r0 = 0
        L34:
            kotlin.reflect.KClass r0 = (kotlin.reflect.KClass) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.full.KClasses.getCompanionObject(kotlin.reflect.KClass):kotlin.reflect.KClass");
    }

    public static final Object getCompanionObjectInstance(KClass<?> companionObjectInstance) {
        Intrinsics.checkParameterIsNotNull(companionObjectInstance, "$this$companionObjectInstance");
        KClass<?> companionObject = getCompanionObject(companionObjectInstance);
        if (companionObject != null) {
            return companionObject.getObjectInstance();
        }
        return null;
    }

    public static final KType getDefaultType(final KClass<?> defaultType) {
        Intrinsics.checkParameterIsNotNull(defaultType, "$this$defaultType");
        SimpleType defaultType2 = ((KClassImpl) defaultType).getDescriptor().getDefaultType();
        Intrinsics.checkExpressionValueIsNotNull(defaultType2, "(this as KClassImpl<*>).descriptor.defaultType");
        return new KTypeImpl(defaultType2, new Function0<Class<? extends Object>>() { // from class: kotlin.reflect.full.KClasses$defaultType$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Class<? extends Object> invoke() {
                return ((KClassImpl) KClass.this).getJClass();
            }
        });
    }

    public static final Collection<KCallable<?>> getDeclaredMembers(KClass<?> declaredMembers) {
        Intrinsics.checkParameterIsNotNull(declaredMembers, "$this$declaredMembers");
        return ((KClassImpl.Data) ((KClassImpl) declaredMembers).getData().invoke()).getDeclaredMembers();
    }

    public static final Collection<KFunction<?>> getFunctions(KClass<?> functions) {
        Intrinsics.checkParameterIsNotNull(functions, "$this$functions");
        ArrayList arrayList = new ArrayList();
        for (Object obj : functions.getMembers()) {
            if (obj instanceof KFunction) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final Collection<KFunction<?>> getStaticFunctions(KClass<?> staticFunctions) {
        Intrinsics.checkParameterIsNotNull(staticFunctions, "$this$staticFunctions");
        ArrayList arrayList = new ArrayList();
        for (Object obj : ((KClassImpl.Data) ((KClassImpl) staticFunctions).getData().invoke()).getAllStaticMembers()) {
            if (obj instanceof KFunction) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final Collection<KFunction<?>> getMemberFunctions(KClass<?> memberFunctions) {
        Intrinsics.checkParameterIsNotNull(memberFunctions, "$this$memberFunctions");
        ArrayList arrayList = new ArrayList();
        for (Object obj : ((KClassImpl.Data) ((KClassImpl) memberFunctions).getData().invoke()).getAllNonStaticMembers()) {
            KCallableImpl kCallableImpl = (KCallableImpl) obj;
            if (isNotExtension(kCallableImpl) && (kCallableImpl instanceof KFunction)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final Collection<KFunction<?>> getMemberExtensionFunctions(KClass<?> memberExtensionFunctions) {
        Intrinsics.checkParameterIsNotNull(memberExtensionFunctions, "$this$memberExtensionFunctions");
        ArrayList arrayList = new ArrayList();
        for (Object obj : ((KClassImpl.Data) ((KClassImpl) memberExtensionFunctions).getData().invoke()).getAllNonStaticMembers()) {
            KCallableImpl kCallableImpl = (KCallableImpl) obj;
            if (isExtension(kCallableImpl) && (kCallableImpl instanceof KFunction)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final Collection<KFunction<?>> getDeclaredFunctions(KClass<?> declaredFunctions) {
        Intrinsics.checkParameterIsNotNull(declaredFunctions, "$this$declaredFunctions");
        ArrayList arrayList = new ArrayList();
        for (Object obj : ((KClassImpl.Data) ((KClassImpl) declaredFunctions).getData().invoke()).getDeclaredMembers()) {
            if (obj instanceof KFunction) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final Collection<KFunction<?>> getDeclaredMemberFunctions(KClass<?> declaredMemberFunctions) {
        Intrinsics.checkParameterIsNotNull(declaredMemberFunctions, "$this$declaredMemberFunctions");
        ArrayList arrayList = new ArrayList();
        for (Object obj : ((KClassImpl.Data) ((KClassImpl) declaredMemberFunctions).getData().invoke()).getDeclaredNonStaticMembers()) {
            KCallableImpl kCallableImpl = (KCallableImpl) obj;
            if (isNotExtension(kCallableImpl) && (kCallableImpl instanceof KFunction)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final Collection<KFunction<?>> getDeclaredMemberExtensionFunctions(KClass<?> declaredMemberExtensionFunctions) {
        Intrinsics.checkParameterIsNotNull(declaredMemberExtensionFunctions, "$this$declaredMemberExtensionFunctions");
        ArrayList arrayList = new ArrayList();
        for (Object obj : ((KClassImpl.Data) ((KClassImpl) declaredMemberExtensionFunctions).getData().invoke()).getDeclaredNonStaticMembers()) {
            KCallableImpl kCallableImpl = (KCallableImpl) obj;
            if (isExtension(kCallableImpl) && (kCallableImpl instanceof KFunction)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final Collection<KProperty0<?>> getStaticProperties(KClass<?> staticProperties) {
        Intrinsics.checkParameterIsNotNull(staticProperties, "$this$staticProperties");
        ArrayList arrayList = new ArrayList();
        for (Object obj : ((KClassImpl.Data) ((KClassImpl) staticProperties).getData().invoke()).getAllStaticMembers()) {
            KCallableImpl kCallableImpl = (KCallableImpl) obj;
            if (isNotExtension(kCallableImpl) && (kCallableImpl instanceof KProperty0)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final <T> Collection<KProperty1<T, ?>> getMemberProperties(KClass<T> memberProperties) {
        Intrinsics.checkParameterIsNotNull(memberProperties, "$this$memberProperties");
        ArrayList arrayList = new ArrayList();
        for (T t : ((KClassImpl) memberProperties).getData().invoke().getAllNonStaticMembers()) {
            KCallableImpl kCallableImpl = (KCallableImpl) t;
            if (isNotExtension(kCallableImpl) && (kCallableImpl instanceof KProperty1)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static final <T> Collection<KProperty2<T, ?, ?>> getMemberExtensionProperties(KClass<T> memberExtensionProperties) {
        Intrinsics.checkParameterIsNotNull(memberExtensionProperties, "$this$memberExtensionProperties");
        ArrayList arrayList = new ArrayList();
        for (T t : ((KClassImpl) memberExtensionProperties).getData().invoke().getAllNonStaticMembers()) {
            KCallableImpl kCallableImpl = (KCallableImpl) t;
            if (isExtension(kCallableImpl) && (kCallableImpl instanceof KProperty2)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static final <T> Collection<KProperty1<T, ?>> getDeclaredMemberProperties(KClass<T> declaredMemberProperties) {
        Intrinsics.checkParameterIsNotNull(declaredMemberProperties, "$this$declaredMemberProperties");
        ArrayList arrayList = new ArrayList();
        for (T t : ((KClassImpl) declaredMemberProperties).getData().invoke().getDeclaredNonStaticMembers()) {
            KCallableImpl kCallableImpl = (KCallableImpl) t;
            if (isNotExtension(kCallableImpl) && (kCallableImpl instanceof KProperty1)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static final <T> Collection<KProperty2<T, ?, ?>> getDeclaredMemberExtensionProperties(KClass<T> declaredMemberExtensionProperties) {
        Intrinsics.checkParameterIsNotNull(declaredMemberExtensionProperties, "$this$declaredMemberExtensionProperties");
        ArrayList arrayList = new ArrayList();
        for (T t : ((KClassImpl) declaredMemberExtensionProperties).getData().invoke().getDeclaredNonStaticMembers()) {
            KCallableImpl kCallableImpl = (KCallableImpl) t;
            if (isExtension(kCallableImpl) && (kCallableImpl instanceof KProperty2)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    private static final boolean isExtension(KCallableImpl<?> kCallableImpl) {
        return kCallableImpl.getDescriptor().getExtensionReceiverParameter() != null;
    }

    private static final boolean isNotExtension(KCallableImpl<?> kCallableImpl) {
        return !isExtension(kCallableImpl);
    }

    public static final List<KClass<?>> getSuperclasses(KClass<?> superclasses) {
        Intrinsics.checkParameterIsNotNull(superclasses, "$this$superclasses");
        ArrayList arrayList = new ArrayList();
        for (KType kType : superclasses.getSupertypes()) {
            KClassifier classifier = kType.getClassifier();
            if (!(classifier instanceof KClass)) {
                classifier = null;
            }
            KClass kClass = (KClass) classifier;
            if (kClass != null) {
                arrayList.add(kClass);
            }
        }
        return arrayList;
    }

    public static final Collection<KType> getAllSupertypes(KClass<?> allSupertypes) {
        Intrinsics.checkParameterIsNotNull(allSupertypes, "$this$allSupertypes");
        Object dfs = DFS.dfs(allSupertypes.getSupertypes(), new DFS.Neighbors<KType>() { // from class: kotlin.reflect.full.KClasses$allSupertypes$1
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.Neighbors
            public final Iterable<KType> getNeighbors(KType kType) {
                KClassifier classifier = kType.getClassifier();
                if (!(classifier instanceof KClass)) {
                    classifier = null;
                }
                KClass kClass = (KClass) classifier;
                if (kClass == null) {
                    throw new KotlinReflectionInternalError("Supertype not a class: " + kType);
                }
                List<KType> supertypes = kClass.getSupertypes();
                if (kType.getArguments().isEmpty()) {
                    return supertypes;
                }
                if (kType != null) {
                    TypeSubstitutor create = TypeSubstitutor.create(((KTypeImpl) kType).getType());
                    List<KType> list = supertypes;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (KType kType2 : list) {
                        if (kType2 != null) {
                            KotlinType substitute = create.substitute(((KTypeImpl) kType2).getType(), Variance.INVARIANT);
                            if (substitute != null) {
                                Intrinsics.checkExpressionValueIsNotNull(substitute, "substitutor.substitute((…: $supertype ($current)\")");
                                arrayList.add(new KTypeImpl(substitute, new Function0() { // from class: kotlin.reflect.full.KClasses$allSupertypes$1$1$1$1
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Void invoke() {
                                        throw new NotImplementedError("An operation is not implemented: Java type for supertype");
                                    }
                                }));
                            } else {
                                throw new KotlinReflectionInternalError("Type substitution failed: " + kType2 + " (" + kType + ')');
                            }
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KTypeImpl");
                        }
                    }
                    return arrayList;
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KTypeImpl");
            }
        }, new DFS.VisitedWithSet(), new DFS.NodeHandlerWithListResult<KType, KType>() { // from class: kotlin.reflect.full.KClasses$allSupertypes$2
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.AbstractNodeHandler, kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler
            public boolean beforeChildren(KType current) {
                Intrinsics.checkParameterIsNotNull(current, "current");
                ((LinkedList) this.result).add(current);
                return true;
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(dfs, "DFS.dfs(\n            sup…    }\n            }\n    )");
        return (Collection) dfs;
    }

    public static final Collection<KClass<?>> getAllSuperclasses(KClass<?> allSuperclasses) {
        Intrinsics.checkParameterIsNotNull(allSuperclasses, "$this$allSuperclasses");
        Collection<KType> allSupertypes = getAllSupertypes(allSuperclasses);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(allSupertypes, 10));
        for (KType kType : allSupertypes) {
            KClassifier classifier = kType.getClassifier();
            if (!(classifier instanceof KClass)) {
                classifier = null;
            }
            KClass kClass = (KClass) classifier;
            if (kClass == null) {
                throw new KotlinReflectionInternalError("Supertype not a class: " + kType);
            }
            arrayList.add(kClass);
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [kotlin.reflect.full.KClasses$sam$org_jetbrains_kotlin_utils_DFS_Neighbors$0] */
    public static final boolean isSubclassOf(KClass<?> isSubclassOf, final KClass<?> base) {
        Intrinsics.checkParameterIsNotNull(isSubclassOf, "$this$isSubclassOf");
        Intrinsics.checkParameterIsNotNull(base, "base");
        if (!Intrinsics.areEqual(isSubclassOf, base)) {
            List listOf = CollectionsKt.listOf(isSubclassOf);
            final KProperty1 kProperty1 = KClasses$isSubclassOf$1.INSTANCE;
            if (kProperty1 != null) {
                kProperty1 = new DFS.Neighbors() { // from class: kotlin.reflect.full.KClasses$sam$org_jetbrains_kotlin_utils_DFS_Neighbors$0
                    @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.Neighbors
                    public final /* synthetic */ Iterable getNeighbors(Object obj) {
                        return (Iterable) Function1.this.invoke(obj);
                    }
                };
            }
            Boolean ifAny = DFS.ifAny(listOf, (DFS.Neighbors) kProperty1, new Function1<KClass<?>, Boolean>() { // from class: kotlin.reflect.full.KClasses$isSubclassOf$2
                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Boolean invoke(KClass<?> kClass) {
                    return Boolean.valueOf(invoke2(kClass));
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final boolean invoke2(KClass<?> kClass) {
                    return Intrinsics.areEqual(kClass, KClass.this);
                }
            });
            Intrinsics.checkExpressionValueIsNotNull(ifAny, "DFS.ifAny(listOf(this), …erclasses) { it == base }");
            if (!ifAny.booleanValue()) {
                return false;
            }
        }
        return true;
    }

    public static final boolean isSuperclassOf(KClass<?> isSuperclassOf, KClass<?> derived) {
        Intrinsics.checkParameterIsNotNull(isSuperclassOf, "$this$isSuperclassOf");
        Intrinsics.checkParameterIsNotNull(derived, "derived");
        return isSubclassOf(derived, isSuperclassOf);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> T cast(KClass<T> cast, Object obj) {
        Intrinsics.checkParameterIsNotNull(cast, "$this$cast");
        if (cast.isInstance(obj)) {
            if (obj != 0) {
                return obj;
            }
            throw new TypeCastException("null cannot be cast to non-null type T");
        }
        throw new TypeCastException("Value cannot be cast to " + cast.getQualifiedName());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> T safeCast(KClass<T> safeCast, Object obj) {
        Intrinsics.checkParameterIsNotNull(safeCast, "$this$safeCast");
        if (safeCast.isInstance(obj)) {
            if (obj != 0) {
                return obj;
            }
            throw new TypeCastException("null cannot be cast to non-null type T");
        }
        return null;
    }

    public static final <T> T createInstance(KClass<T> createInstance) {
        boolean z;
        Intrinsics.checkParameterIsNotNull(createInstance, "$this$createInstance");
        Iterator<T> it = createInstance.getConstructors().iterator();
        KFunction kFunction = null;
        KFunction kFunction2 = null;
        boolean z2 = false;
        while (true) {
            if (it.hasNext()) {
                T next = it.next();
                List<KParameter> parameters = ((KFunction) next).getParameters();
                if (!(parameters instanceof Collection) || !parameters.isEmpty()) {
                    for (KParameter kParameter : parameters) {
                        if (!kParameter.isOptional()) {
                            z = false;
                            break;
                        }
                    }
                }
                z = true;
                if (z) {
                    if (z2) {
                        break;
                    }
                    kFunction2 = next;
                    z2 = true;
                }
            } else if (z2) {
                kFunction = kFunction2;
            }
        }
        KFunction kFunction3 = kFunction;
        if (kFunction3 == null) {
            throw new IllegalArgumentException("Class should have a single no-arg constructor: " + createInstance);
        }
        return (T) kFunction3.callBy(MapsKt.emptyMap());
    }
}
