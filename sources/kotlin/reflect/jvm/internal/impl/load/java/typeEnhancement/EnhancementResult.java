package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
/* compiled from: typeEnhancement.kt */
/* loaded from: classes2.dex */
final class EnhancementResult<T> {
    private final Annotations enhancementAnnotations;
    private final T result;

    public final T component1() {
        return this.result;
    }

    public final Annotations component2() {
        return this.enhancementAnnotations;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof EnhancementResult) {
                EnhancementResult enhancementResult = (EnhancementResult) obj;
                return Intrinsics.areEqual(this.result, enhancementResult.result) && Intrinsics.areEqual(this.enhancementAnnotations, enhancementResult.enhancementAnnotations);
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        T t = this.result;
        int hashCode = (t != null ? t.hashCode() : 0) * 31;
        Annotations annotations = this.enhancementAnnotations;
        return hashCode + (annotations != null ? annotations.hashCode() : 0);
    }

    public String toString() {
        return "EnhancementResult(result=" + this.result + ", enhancementAnnotations=" + this.enhancementAnnotations + ")";
    }

    public EnhancementResult(T t, Annotations annotations) {
        this.result = t;
        this.enhancementAnnotations = annotations;
    }
}
