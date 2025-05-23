package kotlin.reflect.jvm.internal.calls;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KClass;
/* compiled from: AnnotationConstructorCaller.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"equals", "", "T", "", "other", "invoke"}, k = 3, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class AnnotationConstructorCallerKt$createAnnotationInstance$2 extends Lambda implements Function1<Object, Boolean> {
    final /* synthetic */ Class $annotationClass;
    final /* synthetic */ List $methods;
    final /* synthetic */ Map $values;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnnotationConstructorCallerKt$createAnnotationInstance$2(Class cls, List list, Map map) {
        super(1);
        this.$annotationClass = cls;
        this.$methods = list;
        this.$values = map;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Boolean invoke(Object obj) {
        return Boolean.valueOf(invoke2(obj));
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final boolean invoke2(Object obj) {
        boolean areEqual;
        boolean z;
        KClass annotationClass;
        Class cls = null;
        Annotation annotation = (Annotation) (!(obj instanceof Annotation) ? null : obj);
        if (annotation != null && (annotationClass = JvmClassMappingKt.getAnnotationClass(annotation)) != null) {
            cls = JvmClassMappingKt.getJavaClass(annotationClass);
        }
        if (Intrinsics.areEqual(cls, this.$annotationClass)) {
            List<Method> list = this.$methods;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                for (Method method : list) {
                    Object obj2 = this.$values.get(method.getName());
                    Object invoke = method.invoke(obj, new Object[0]);
                    if (obj2 instanceof boolean[]) {
                        boolean[] zArr = (boolean[]) obj2;
                        if (invoke != null) {
                            areEqual = Arrays.equals(zArr, (boolean[]) invoke);
                            continue;
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.BooleanArray");
                        }
                    } else if (obj2 instanceof char[]) {
                        char[] cArr = (char[]) obj2;
                        if (invoke != null) {
                            areEqual = Arrays.equals(cArr, (char[]) invoke);
                            continue;
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharArray");
                        }
                    } else if (obj2 instanceof byte[]) {
                        byte[] bArr = (byte[]) obj2;
                        if (invoke != null) {
                            areEqual = Arrays.equals(bArr, (byte[]) invoke);
                            continue;
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.ByteArray");
                        }
                    } else if (obj2 instanceof short[]) {
                        short[] sArr = (short[]) obj2;
                        if (invoke != null) {
                            areEqual = Arrays.equals(sArr, (short[]) invoke);
                            continue;
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.ShortArray");
                        }
                    } else if (obj2 instanceof int[]) {
                        int[] iArr = (int[]) obj2;
                        if (invoke != null) {
                            areEqual = Arrays.equals(iArr, (int[]) invoke);
                            continue;
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.IntArray");
                        }
                    } else if (obj2 instanceof float[]) {
                        float[] fArr = (float[]) obj2;
                        if (invoke != null) {
                            areEqual = Arrays.equals(fArr, (float[]) invoke);
                            continue;
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.FloatArray");
                        }
                    } else if (obj2 instanceof long[]) {
                        long[] jArr = (long[]) obj2;
                        if (invoke != null) {
                            areEqual = Arrays.equals(jArr, (long[]) invoke);
                            continue;
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.LongArray");
                        }
                    } else if (obj2 instanceof double[]) {
                        double[] dArr = (double[]) obj2;
                        if (invoke != null) {
                            areEqual = Arrays.equals(dArr, (double[]) invoke);
                            continue;
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.DoubleArray");
                        }
                    } else if (obj2 instanceof Object[]) {
                        Object[] objArr = (Object[]) obj2;
                        if (invoke != null) {
                            areEqual = Arrays.equals(objArr, (Object[]) invoke);
                            continue;
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<*>");
                        }
                    } else {
                        areEqual = Intrinsics.areEqual(obj2, invoke);
                        continue;
                    }
                    if (!areEqual) {
                        z = false;
                        break;
                    }
                }
            }
            z = true;
            if (z) {
                return true;
            }
        }
        return false;
    }
}
