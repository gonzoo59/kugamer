package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: AnnotationUseSiteTarget.kt */
/* loaded from: classes2.dex */
public enum AnnotationUseSiteTarget {
    FIELD(null, 1, null),
    FILE(null, 1, null),
    PROPERTY(null, 1, null),
    PROPERTY_GETTER("get"),
    PROPERTY_SETTER("set"),
    RECEIVER(null, 1, null),
    CONSTRUCTOR_PARAMETER("param"),
    SETTER_PARAMETER("setparam"),
    PROPERTY_DELEGATE_FIELD("delegate");
    
    private final String renderName;

    AnnotationUseSiteTarget(String str) {
        if (str == null) {
            String name = name();
            if (name == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            str = name.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.String).toLowerCase()");
        }
        this.renderName = str;
    }

    /* synthetic */ AnnotationUseSiteTarget(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str);
    }

    public final String getRenderName() {
        return this.renderName;
    }
}
