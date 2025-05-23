package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
/* compiled from: signatureEnhancement.kt */
/* loaded from: classes2.dex */
final class TypeAndDefaultQualifiers {
    private final JavaTypeQualifiers defaultQualifiers;
    private final KotlinType type;

    public final KotlinType component1() {
        return this.type;
    }

    public final JavaTypeQualifiers component2() {
        return this.defaultQualifiers;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof TypeAndDefaultQualifiers) {
                TypeAndDefaultQualifiers typeAndDefaultQualifiers = (TypeAndDefaultQualifiers) obj;
                return Intrinsics.areEqual(this.type, typeAndDefaultQualifiers.type) && Intrinsics.areEqual(this.defaultQualifiers, typeAndDefaultQualifiers.defaultQualifiers);
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        KotlinType kotlinType = this.type;
        int hashCode = (kotlinType != null ? kotlinType.hashCode() : 0) * 31;
        JavaTypeQualifiers javaTypeQualifiers = this.defaultQualifiers;
        return hashCode + (javaTypeQualifiers != null ? javaTypeQualifiers.hashCode() : 0);
    }

    public String toString() {
        return "TypeAndDefaultQualifiers(type=" + this.type + ", defaultQualifiers=" + this.defaultQualifiers + ")";
    }

    public TypeAndDefaultQualifiers(KotlinType type, JavaTypeQualifiers javaTypeQualifiers) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        this.type = type;
        this.defaultQualifiers = javaTypeQualifiers;
    }

    public final KotlinType getType() {
        return this.type;
    }
}
