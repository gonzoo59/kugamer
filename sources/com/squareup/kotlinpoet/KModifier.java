package com.squareup.kotlinpoet;

import com.baidu.kwgames.GameSettingFloat;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: KModifier.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b!\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001,B#\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\"\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0015\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0018\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\nj\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+¨\u0006-"}, d2 = {"Lcom/squareup/kotlinpoet/KModifier;", "", "keyword", "", "targets", "", "Lcom/squareup/kotlinpoet/KModifier$Target;", "(Ljava/lang/String;ILjava/lang/String;[Lcom/squareup/kotlinpoet/KModifier$Target;)V", "getKeyword$kotlinpoet", "()Ljava/lang/String;", "[Lcom/squareup/kotlinpoet/KModifier$Target;", "checkTarget", "", "target", "checkTarget$kotlinpoet", "EXPECT", "ACTUAL", "PUBLIC", "PROTECTED", "PRIVATE", "INTERNAL", "FINAL", "OPEN", "ABSTRACT", "OVERRIDE", "INNER", "ENUM", "ANNOTATION", "DATA", "SEALED", "INLINE", "NOINLINE", "CROSSINLINE", "REIFIED", "INFIX", "OPERATOR", "LATEINIT", "CONST", "EXTERNAL", "SUSPEND", "TAILREC", "IN", "OUT", "VARARG", "Target", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public enum KModifier {
    EXPECT("expect", Target.CLASS, Target.FUNCTION, Target.PROPERTY),
    ACTUAL("actual", Target.CLASS, Target.FUNCTION, Target.PROPERTY),
    PUBLIC("public", Target.PROPERTY),
    PROTECTED("protected", Target.PROPERTY),
    PRIVATE("private", Target.PROPERTY),
    INTERNAL("internal", Target.PROPERTY),
    FINAL("final", Target.CLASS, Target.FUNCTION, Target.PROPERTY),
    OPEN("open", Target.CLASS, Target.FUNCTION, Target.PROPERTY),
    ABSTRACT("abstract", Target.CLASS, Target.FUNCTION, Target.PROPERTY),
    OVERRIDE("override", Target.FUNCTION, Target.PROPERTY),
    INNER("inner", Target.CLASS),
    ENUM("enum", Target.CLASS),
    ANNOTATION("annotation", Target.CLASS),
    DATA(GameSettingFloat.TAG_ATTR_DATA, Target.CLASS),
    SEALED("sealed", Target.CLASS),
    INLINE("inline", Target.FUNCTION),
    NOINLINE("noinline", Target.PARAMETER),
    CROSSINLINE("crossinline", Target.PARAMETER),
    REIFIED("reified", Target.TYPE_PARAMETER),
    INFIX("infix", Target.FUNCTION),
    OPERATOR("operator", Target.FUNCTION),
    LATEINIT("lateinit", Target.PROPERTY),
    CONST("const", Target.PROPERTY),
    EXTERNAL("external", Target.FUNCTION),
    SUSPEND("suspend", Target.FUNCTION),
    TAILREC("tailrec", Target.FUNCTION),
    IN("in", Target.VARIANCE_ANNOTATION),
    OUT("out", Target.VARIANCE_ANNOTATION),
    VARARG("vararg", Target.PARAMETER);
    
    private final String keyword;
    private final Target[] targets;

    /* compiled from: KModifier.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lcom/squareup/kotlinpoet/KModifier$Target;", "", "(Ljava/lang/String;I)V", "CLASS", "VARIANCE_ANNOTATION", "PARAMETER", "TYPE_PARAMETER", "FUNCTION", "PROPERTY", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public enum Target {
        CLASS,
        VARIANCE_ANNOTATION,
        PARAMETER,
        TYPE_PARAMETER,
        FUNCTION,
        PROPERTY
    }

    KModifier(String keyword, Target... targets) {
        Intrinsics.checkParameterIsNotNull(keyword, "keyword");
        Intrinsics.checkParameterIsNotNull(targets, "targets");
        this.keyword = keyword;
        this.targets = targets;
    }

    public final String getKeyword$kotlinpoet() {
        return this.keyword;
    }

    public final void checkTarget$kotlinpoet(Target target) {
        Intrinsics.checkParameterIsNotNull(target, "target");
        if (ArraysKt.contains(this.targets, target)) {
            return;
        }
        throw new IllegalArgumentException(("unexpected modifier " + this + " for " + target).toString());
    }
}
