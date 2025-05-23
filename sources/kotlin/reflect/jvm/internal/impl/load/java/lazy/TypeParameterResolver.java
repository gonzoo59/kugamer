package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
/* compiled from: resolvers.kt */
/* loaded from: classes2.dex */
public interface TypeParameterResolver {
    TypeParameterDescriptor resolveTypeParameter(JavaTypeParameter javaTypeParameter);

    /* compiled from: resolvers.kt */
    /* loaded from: classes2.dex */
    public static final class EMPTY implements TypeParameterResolver {
        public static final EMPTY INSTANCE = new EMPTY();

        @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.TypeParameterResolver
        public TypeParameterDescriptor resolveTypeParameter(JavaTypeParameter javaTypeParameter) {
            Intrinsics.checkParameterIsNotNull(javaTypeParameter, "javaTypeParameter");
            return null;
        }

        private EMPTY() {
        }
    }
}
