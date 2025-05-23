package kotlin.reflect.jvm.internal.impl.types;
/* compiled from: TypeCapabilities.kt */
/* loaded from: classes2.dex */
public interface CustomTypeVariable {
    boolean isTypeVariable();

    KotlinType substitutionResult(KotlinType kotlinType);
}
