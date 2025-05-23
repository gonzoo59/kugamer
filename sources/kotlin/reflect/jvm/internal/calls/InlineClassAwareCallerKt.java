package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
/* compiled from: InlineClassAwareCaller.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u0002H\u0000\u001a6\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\n\b\u0000\u0010\n*\u0004\u0018\u00010\u000b*\b\u0012\u0004\u0012\u0002H\n0\t2\u0006\u0010\u0007\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\rH\u0000\u001a\u0018\u0010\u000e\u001a\u00020\u000f*\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0007\u001a\u00020\u0002H\u0000\u001a\u0018\u0010\u0011\u001a\u00020\u000f*\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0007\u001a\u00020\u0002H\u0000\u001a\f\u0010\u0012\u001a\u00020\r*\u00020\u0002H\u0002\u001a\u0014\u0010\u0013\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0010*\u0004\u0018\u00010\u0014H\u0000\u001a\u0012\u0010\u0013\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0010*\u00020\u0001H\u0000\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0015"}, d2 = {"expectedReceiverType", "Lkotlin/reflect/jvm/internal/impl/types/KotlinType;", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableMemberDescriptor;", "getExpectedReceiverType", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Lorg/jetbrains/kotlin/types/KotlinType;", "coerceToExpectedReceiverType", "", "descriptor", "createInlineClassAwareCallerIfNeeded", "Lkotlin/reflect/jvm/internal/calls/Caller;", "M", "Ljava/lang/reflect/Member;", "isDefault", "", "getBoxMethod", "Ljava/lang/reflect/Method;", "Ljava/lang/Class;", "getUnboxMethod", "hasInlineClassReceiver", "toInlineClass", "Lkotlin/reflect/jvm/internal/impl/descriptors/DeclarationDescriptor;", "kotlin-reflection"}, k = 2, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class InlineClassAwareCallerKt {
    public static /* synthetic */ Caller createInlineClassAwareCallerIfNeeded$default(Caller caller, CallableMemberDescriptor callableMemberDescriptor, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return createInlineClassAwareCallerIfNeeded(caller, callableMemberDescriptor, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0060, code lost:
        if (kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt.isInlineClassType(r0) != true) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x006a, code lost:
        if (hasInlineClassReceiver(r6) == false) goto L17;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <M extends java.lang.reflect.Member> kotlin.reflect.jvm.internal.calls.Caller<M> createInlineClassAwareCallerIfNeeded(kotlin.reflect.jvm.internal.calls.Caller<? extends M> r5, kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor r6, boolean r7) {
        /*
            java.lang.String r0 = "$this$createInlineClassAwareCallerIfNeeded"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r5, r0)
            java.lang.String r0 = "descriptor"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r6, r0)
            r0 = r6
            kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor r0 = (kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor) r0
            boolean r0 = kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt.isGetterOfUnderlyingPropertyOfInlineClass(r0)
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L6c
            java.util.List r0 = r6.getValueParameters()
            java.lang.String r3 = "descriptor.valueParameters"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r3)
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            boolean r3 = r0 instanceof java.util.Collection
            if (r3 == 0) goto L2f
            r3 = r0
            java.util.Collection r3 = (java.util.Collection) r3
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L2f
        L2d:
            r0 = 0
            goto L54
        L2f:
            java.util.Iterator r0 = r0.iterator()
        L33:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L2d
            java.lang.Object r3 = r0.next()
            kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor r3 = (kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor) r3
            java.lang.String r4 = "it"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r3, r4)
            kotlin.reflect.jvm.internal.impl.types.KotlinType r3 = r3.getType()
            java.lang.String r4 = "it.type"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r3, r4)
            boolean r3 = kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt.isInlineClassType(r3)
            if (r3 == 0) goto L33
            r0 = 1
        L54:
            if (r0 != 0) goto L6c
            kotlin.reflect.jvm.internal.impl.types.KotlinType r0 = r6.getReturnType()
            if (r0 == 0) goto L62
            boolean r0 = kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt.isInlineClassType(r0)
            if (r0 == r2) goto L6c
        L62:
            boolean r0 = r5 instanceof kotlin.reflect.jvm.internal.calls.BoundCaller
            if (r0 != 0) goto L6d
            boolean r0 = hasInlineClassReceiver(r6)
            if (r0 == 0) goto L6d
        L6c:
            r1 = 1
        L6d:
            if (r1 == 0) goto L77
            kotlin.reflect.jvm.internal.calls.InlineClassAwareCaller r0 = new kotlin.reflect.jvm.internal.calls.InlineClassAwareCaller
            r0.<init>(r6, r5, r7)
            r5 = r0
            kotlin.reflect.jvm.internal.calls.Caller r5 = (kotlin.reflect.jvm.internal.calls.Caller) r5
        L77:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.calls.InlineClassAwareCallerKt.createInlineClassAwareCallerIfNeeded(kotlin.reflect.jvm.internal.calls.Caller, kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor, boolean):kotlin.reflect.jvm.internal.calls.Caller");
    }

    private static final boolean hasInlineClassReceiver(CallableMemberDescriptor callableMemberDescriptor) {
        KotlinType expectedReceiverType = getExpectedReceiverType(callableMemberDescriptor);
        return expectedReceiverType != null && InlineClassesUtilsKt.isInlineClassType(expectedReceiverType);
    }

    public static final Method getUnboxMethod(Class<?> getUnboxMethod, CallableMemberDescriptor descriptor) {
        Intrinsics.checkParameterIsNotNull(getUnboxMethod, "$this$getUnboxMethod");
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        try {
            Method declaredMethod = getUnboxMethod.getDeclaredMethod("unbox-impl", new Class[0]);
            Intrinsics.checkExpressionValueIsNotNull(declaredMethod, "getDeclaredMethod(\"unbox…FOR_INLINE_CLASS_MEMBERS)");
            return declaredMethod;
        } catch (NoSuchMethodException unused) {
            throw new KotlinReflectionInternalError("No unbox method found in inline class: " + getUnboxMethod + " (calling " + descriptor + ')');
        }
    }

    public static final Method getBoxMethod(Class<?> getBoxMethod, CallableMemberDescriptor descriptor) {
        Intrinsics.checkParameterIsNotNull(getBoxMethod, "$this$getBoxMethod");
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        try {
            Method declaredMethod = getBoxMethod.getDeclaredMethod("box-impl", getUnboxMethod(getBoxMethod, descriptor).getReturnType());
            Intrinsics.checkExpressionValueIsNotNull(declaredMethod, "getDeclaredMethod(\"box\" …d(descriptor).returnType)");
            return declaredMethod;
        } catch (NoSuchMethodException unused) {
            throw new KotlinReflectionInternalError("No box method found in inline class: " + getBoxMethod + " (calling " + descriptor + ')');
        }
    }

    public static final Class<?> toInlineClass(KotlinType toInlineClass) {
        Intrinsics.checkParameterIsNotNull(toInlineClass, "$this$toInlineClass");
        return toInlineClass(toInlineClass.getConstructor().mo1092getDeclarationDescriptor());
    }

    public static final Class<?> toInlineClass(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor;
            if (classDescriptor.isInline()) {
                Class<?> javaClass = UtilKt.toJavaClass(classDescriptor);
                if (javaClass != null) {
                    return javaClass;
                }
                throw new KotlinReflectionInternalError("Class object for the class " + classDescriptor.getName() + " cannot be found (classId=" + DescriptorUtilsKt.getClassId((ClassifierDescriptor) declarationDescriptor) + ')');
            }
        }
        return null;
    }

    private static final KotlinType getExpectedReceiverType(CallableMemberDescriptor callableMemberDescriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter = callableMemberDescriptor.getExtensionReceiverParameter();
        ReceiverParameterDescriptor dispatchReceiverParameter = callableMemberDescriptor.getDispatchReceiverParameter();
        if (extensionReceiverParameter != null) {
            return extensionReceiverParameter.getType();
        }
        if (dispatchReceiverParameter == null) {
            return null;
        }
        if (callableMemberDescriptor instanceof ConstructorDescriptor) {
            return dispatchReceiverParameter.getType();
        }
        DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingDeclaration();
        if (!(containingDeclaration instanceof ClassDescriptor)) {
            containingDeclaration = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
        return classDescriptor != null ? classDescriptor.getDefaultType() : null;
    }

    public static final Object coerceToExpectedReceiverType(Object obj, CallableMemberDescriptor descriptor) {
        KotlinType expectedReceiverType;
        Class<?> inlineClass;
        Method unboxMethod;
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        return (((descriptor instanceof PropertyDescriptor) && InlineClassesUtilsKt.isUnderlyingPropertyOfInlineClass((VariableDescriptor) descriptor)) || (expectedReceiverType = getExpectedReceiverType(descriptor)) == null || (inlineClass = toInlineClass(expectedReceiverType)) == null || (unboxMethod = getUnboxMethod(inlineClass, descriptor)) == null) ? obj : unboxMethod.invoke(obj, new Object[0]);
    }
}
