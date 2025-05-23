package kotlin.reflect.jvm.internal.impl.types;
/* compiled from: TypeCapabilities.kt */
/* loaded from: classes2.dex */
public interface SubtypingRepresentatives {
    KotlinType getSubTypeRepresentative();

    KotlinType getSuperTypeRepresentative();

    boolean sameTypeConstructor(KotlinType kotlinType);
}
