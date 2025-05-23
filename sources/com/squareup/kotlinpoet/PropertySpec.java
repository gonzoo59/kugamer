package com.squareup.kotlinpoet;

import com.squareup.kotlinpoet.CodeBlock;
import com.squareup.kotlinpoet.KModifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
/* compiled from: PropertySpec.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 :2\u00020\u0001:\u00029:B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J7\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\b\b\u0002\u00100\u001a\u00020\u000b2\b\b\u0002\u00101\u001a\u00020\u000bH\u0000¢\u0006\u0002\b2J\u0013\u00103\u001a\u00020\u000b2\b\u00104\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u00105\u001a\u000206H\u0016J\u0006\u00107\u001a\u00020\u0003J\b\u00108\u001a\u00020 H\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\rR\u0011\u0010\u001f\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0013\u0010#\u001a\u0004\u0018\u00010$¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0013\u0010'\u001a\u0004\u0018\u00010\u000f¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0011R\u0011\u0010)\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\b*\u0010&¨\u0006;"}, d2 = {"Lcom/squareup/kotlinpoet/PropertySpec;", "", "builder", "Lcom/squareup/kotlinpoet/PropertySpec$Builder;", "(Lcom/squareup/kotlinpoet/PropertySpec$Builder;)V", "annotations", "", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "getAnnotations", "()Ljava/util/List;", "delegated", "", "getDelegated", "()Z", "getter", "Lcom/squareup/kotlinpoet/FunSpec;", "getGetter", "()Lcom/squareup/kotlinpoet/FunSpec;", "initializer", "Lcom/squareup/kotlinpoet/CodeBlock;", "getInitializer", "()Lcom/squareup/kotlinpoet/CodeBlock;", "kdoc", "getKdoc", "modifiers", "", "Lcom/squareup/kotlinpoet/KModifier;", "getModifiers", "()Ljava/util/Set;", "mutable", "getMutable", "name", "", "getName", "()Ljava/lang/String;", "receiverType", "Lcom/squareup/kotlinpoet/TypeName;", "getReceiverType", "()Lcom/squareup/kotlinpoet/TypeName;", "setter", "getSetter", "type", "getType", "emit", "", "codeWriter", "Lcom/squareup/kotlinpoet/CodeWriter;", "implicitModifiers", "withInitializer", "inline", "emit$kotlinpoet", "equals", "other", "hashCode", "", "toBuilder", "toString", "Builder", "Companion", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class PropertySpec {
    public static final Companion Companion = new Companion(null);
    private final List<AnnotationSpec> annotations;
    private final boolean delegated;
    private final FunSpec getter;
    private final CodeBlock initializer;
    private final CodeBlock kdoc;
    private final Set<KModifier> modifiers;
    private final boolean mutable;
    private final String name;
    private final TypeName receiverType;
    private final FunSpec setter;
    private final TypeName type;

    @JvmStatic
    public static final Builder builder(String name, TypeName type, KModifier... modifiers) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
        return Companion.builder(name, type, modifiers);
    }

    @JvmStatic
    public static final Builder builder(String name, Type type, KModifier... modifiers) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
        return Companion.builder(name, type, modifiers);
    }

    @JvmStatic
    public static final Builder builder(String name, KClass<?> type, KModifier... modifiers) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
        return Companion.builder(name, type, modifiers);
    }

    @JvmStatic
    public static final Builder varBuilder(String name, TypeName type, KModifier... modifiers) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
        return Companion.varBuilder(name, type, modifiers);
    }

    @JvmStatic
    public static final Builder varBuilder(String name, Type type, KModifier... modifiers) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
        return Companion.varBuilder(name, type, modifiers);
    }

    @JvmStatic
    public static final Builder varBuilder(String name, KClass<?> type, KModifier... modifiers) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
        return Companion.varBuilder(name, type, modifiers);
    }

    private PropertySpec(Builder builder) {
        this.mutable = builder.getMutable$kotlinpoet();
        this.name = builder.getName$kotlinpoet();
        this.type = builder.getType$kotlinpoet();
        this.kdoc = builder.getKdoc$kotlinpoet().build();
        this.annotations = UtilKt.toImmutableList(builder.getAnnotations$kotlinpoet());
        this.modifiers = UtilKt.toImmutableSet(builder.getModifiers$kotlinpoet());
        this.initializer = builder.getInitializer$kotlinpoet();
        this.delegated = builder.getDelegated$kotlinpoet();
        this.getter = builder.getGetter$kotlinpoet();
        this.setter = builder.getSetter$kotlinpoet();
        this.receiverType = builder.getReceiverType$kotlinpoet();
    }

    public /* synthetic */ PropertySpec(Builder builder, DefaultConstructorMarker defaultConstructorMarker) {
        this(builder);
    }

    public final boolean getMutable() {
        return this.mutable;
    }

    public final String getName() {
        return this.name;
    }

    public final TypeName getType() {
        return this.type;
    }

    public final CodeBlock getKdoc() {
        return this.kdoc;
    }

    public final List<AnnotationSpec> getAnnotations() {
        return this.annotations;
    }

    public final Set<KModifier> getModifiers() {
        return this.modifiers;
    }

    public final CodeBlock getInitializer() {
        return this.initializer;
    }

    public final boolean getDelegated() {
        return this.delegated;
    }

    public final FunSpec getGetter() {
        return this.getter;
    }

    public final FunSpec getSetter() {
        return this.setter;
    }

    public final TypeName getReceiverType() {
        return this.receiverType;
    }

    public static /* bridge */ /* synthetic */ void emit$kotlinpoet$default(PropertySpec propertySpec, CodeWriter codeWriter, Set set, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        propertySpec.emit$kotlinpoet(codeWriter, set, z, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:105:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x00ae  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void emit$kotlinpoet(com.squareup.kotlinpoet.CodeWriter r6, java.util.Set<? extends com.squareup.kotlinpoet.KModifier> r7, boolean r8, boolean r9) {
        /*
            r5 = this;
            java.lang.String r0 = "codeWriter"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r6, r0)
            java.lang.String r0 = "implicitModifiers"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r7, r0)
            com.squareup.kotlinpoet.FunSpec r0 = r5.getter
            r1 = 0
            if (r0 == 0) goto L1c
            java.util.Set r0 = r0.getModifiers()
            if (r0 == 0) goto L1c
            com.squareup.kotlinpoet.KModifier r2 = com.squareup.kotlinpoet.KModifier.INLINE
            boolean r0 = r0.contains(r2)
            goto L1d
        L1c:
            r0 = 0
        L1d:
            r2 = 1
            if (r0 == 0) goto L36
            com.squareup.kotlinpoet.FunSpec r0 = r5.setter
            if (r0 == 0) goto L31
            java.util.Set r0 = r0.getModifiers()
            if (r0 == 0) goto L31
            com.squareup.kotlinpoet.KModifier r3 = com.squareup.kotlinpoet.KModifier.INLINE
            boolean r0 = r0.contains(r3)
            goto L32
        L31:
            r0 = 0
        L32:
            if (r0 == 0) goto L36
            r0 = 1
            goto L37
        L36:
            r0 = 0
        L37:
            java.util.Set<com.squareup.kotlinpoet.KModifier> r3 = r5.modifiers
            if (r0 == 0) goto L41
            com.squareup.kotlinpoet.KModifier r4 = com.squareup.kotlinpoet.KModifier.INLINE
            java.util.Set r3 = kotlin.collections.SetsKt.plus(r3, r4)
        L41:
            com.squareup.kotlinpoet.CodeBlock r4 = r5.kdoc
            r6.emitKdoc(r4)
            java.util.List<com.squareup.kotlinpoet.AnnotationSpec> r4 = r5.annotations
            r6.emitAnnotations(r4, r1)
            r6.emitModifiers(r3, r7)
            boolean r3 = r5.mutable
            if (r3 == 0) goto L55
            java.lang.String r3 = "var "
            goto L57
        L55:
            java.lang.String r3 = "val "
        L57:
            r6.emit(r3)
            com.squareup.kotlinpoet.TypeName r3 = r5.receiverType
            if (r3 == 0) goto L75
            boolean r4 = r3 instanceof com.squareup.kotlinpoet.LambdaTypeName
            if (r4 == 0) goto L6c
            java.lang.Object[] r4 = new java.lang.Object[r2]
            r4[r1] = r3
            java.lang.String r3 = "(%T)."
            r6.emitCode(r3, r4)
            goto L75
        L6c:
            java.lang.Object[] r4 = new java.lang.Object[r2]
            r4[r1] = r3
            java.lang.String r3 = "%T."
            r6.emitCode(r3, r4)
        L75:
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r4 = r5.name
            r3[r1] = r4
            com.squareup.kotlinpoet.TypeName r4 = r5.type
            r3[r2] = r4
            java.lang.String r4 = "%L: %T"
            r6.emitCode(r4, r3)
            if (r8 == 0) goto La5
            com.squareup.kotlinpoet.CodeBlock r8 = r5.initializer
            if (r8 == 0) goto La5
            boolean r8 = r5.delegated
            if (r8 == 0) goto L95
            java.lang.String r8 = " by "
            r6.emit(r8)
            goto L9a
        L95:
            java.lang.String r8 = " = "
            r6.emit(r8)
        L9a:
            java.lang.Object[] r8 = new java.lang.Object[r2]
            com.squareup.kotlinpoet.CodeBlock r2 = r5.initializer
            r8[r1] = r2
            java.lang.String r1 = "%[%L%]"
            r6.emitCode(r1, r8)
        La5:
            if (r9 != 0) goto Lac
            java.lang.String r8 = "\n"
            r6.emit(r8)
        Lac:
            if (r0 == 0) goto Lb4
            com.squareup.kotlinpoet.KModifier r8 = com.squareup.kotlinpoet.KModifier.INLINE
            java.util.Set r7 = kotlin.collections.SetsKt.plus(r7, r8)
        Lb4:
            com.squareup.kotlinpoet.FunSpec r8 = r5.getter
            java.lang.String r9 = "%<"
            r0 = 0
            java.lang.String r1 = "%>"
            if (r8 == 0) goto Lc8
            r6.emitCode(r1)
            com.squareup.kotlinpoet.FunSpec r8 = r5.getter
            r8.emit$kotlinpoet(r6, r0, r7)
            r6.emitCode(r9)
        Lc8:
            com.squareup.kotlinpoet.FunSpec r8 = r5.setter
            if (r8 == 0) goto Ld7
            r6.emitCode(r1)
            com.squareup.kotlinpoet.FunSpec r8 = r5.setter
            r8.emit$kotlinpoet(r6, r0, r7)
            r6.emitCode(r9)
        Ld7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.kotlinpoet.PropertySpec.emit$kotlinpoet(com.squareup.kotlinpoet.CodeWriter, java.util.Set, boolean, boolean):void");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || (true ^ Intrinsics.areEqual(getClass(), obj.getClass()))) {
            return false;
        }
        return Intrinsics.areEqual(toString(), obj.toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        emit$kotlinpoet$default(this, new CodeWriter(sb, null, null, null, 14, null), SetsKt.emptySet(), false, false, 12, null);
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    public final Builder toBuilder() {
        Builder builder = new Builder(this.name, this.type);
        builder.getKdoc$kotlinpoet().add(this.kdoc);
        CollectionsKt.addAll(builder.getAnnotations$kotlinpoet(), this.annotations);
        CollectionsKt.addAll(builder.getModifiers$kotlinpoet(), this.modifiers);
        builder.setInitializer$kotlinpoet(this.initializer);
        builder.setDelegated$kotlinpoet(this.delegated);
        return builder;
    }

    /* compiled from: PropertySpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u00103\u001a\u00020\u00002\u0006\u00104\u001a\u00020\tJ\u000e\u00103\u001a\u00020\u00002\u0006\u00105\u001a\u000206J\u0012\u00103\u001a\u00020\u00002\n\u00105\u001a\u0006\u0012\u0002\b\u000307J\u0012\u00103\u001a\u00020\u00002\n\u00105\u001a\u0006\u0012\u0002\b\u000308J\u0014\u00109\u001a\u00020\u00002\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\t0;J\u000e\u0010<\u001a\u00020\u00002\u0006\u0010=\u001a\u00020\u0019J'\u0010<\u001a\u00020\u00002\u0006\u0010>\u001a\u00020\u00032\u0012\u0010?\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010@\"\u00020\u0001¢\u0006\u0002\u0010AJ\u001f\u0010B\u001a\u00020\u00002\u0012\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020#0@\"\u00020#¢\u0006\u0002\u0010CJ\u0006\u0010D\u001a\u00020EJ\u000e\u0010F\u001a\u00020\u00002\u0006\u0010G\u001a\u00020\u0019J+\u0010F\u001a\u00020\u00002\u0006\u0010>\u001a\u00020\u00032\u0016\u0010?\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010@\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010AJ\u000e\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0018\u001a\u00020\u00002\u0006\u0010G\u001a\u00020\u0019J+\u0010\u0018\u001a\u00020\u00002\u0006\u0010>\u001a\u00020\u00032\u0016\u0010?\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010@\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010AJ\u000e\u0010%\u001a\u00020\u00002\u0006\u0010%\u001a\u00020\rJ\u000e\u0010H\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0005J\u000e\u0010H\u001a\u00020\u00002\u0006\u0010*\u001a\u00020IJ\u0012\u0010H\u001a\u00020\u00002\n\u0010*\u001a\u0006\u0012\u0002\b\u000308J\u000e\u0010/\u001a\u00020\u00002\u0006\u0010/\u001a\u00020\u0013R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u000bR\u001a\u0010%\u001a\u00020\rX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u000f\"\u0004\b'\u0010\u0011R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u001c\u0010*\u001a\u0004\u0018\u00010\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001c\u0010/\u001a\u0004\u0018\u00010\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u0015\"\u0004\b1\u0010\u0017R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u0010,¨\u0006J"}, d2 = {"Lcom/squareup/kotlinpoet/PropertySpec$Builder;", "", "name", "", "type", "Lcom/squareup/kotlinpoet/TypeName;", "(Ljava/lang/String;Lcom/squareup/kotlinpoet/TypeName;)V", "annotations", "", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "getAnnotations$kotlinpoet", "()Ljava/util/List;", "delegated", "", "getDelegated$kotlinpoet", "()Z", "setDelegated$kotlinpoet", "(Z)V", "getter", "Lcom/squareup/kotlinpoet/FunSpec;", "getGetter$kotlinpoet", "()Lcom/squareup/kotlinpoet/FunSpec;", "setGetter$kotlinpoet", "(Lcom/squareup/kotlinpoet/FunSpec;)V", "initializer", "Lcom/squareup/kotlinpoet/CodeBlock;", "getInitializer$kotlinpoet", "()Lcom/squareup/kotlinpoet/CodeBlock;", "setInitializer$kotlinpoet", "(Lcom/squareup/kotlinpoet/CodeBlock;)V", "kdoc", "Lcom/squareup/kotlinpoet/CodeBlock$Builder;", "getKdoc$kotlinpoet", "()Lcom/squareup/kotlinpoet/CodeBlock$Builder;", "modifiers", "Lcom/squareup/kotlinpoet/KModifier;", "getModifiers$kotlinpoet", "mutable", "getMutable$kotlinpoet", "setMutable$kotlinpoet", "getName$kotlinpoet", "()Ljava/lang/String;", "receiverType", "getReceiverType$kotlinpoet", "()Lcom/squareup/kotlinpoet/TypeName;", "setReceiverType$kotlinpoet", "(Lcom/squareup/kotlinpoet/TypeName;)V", "setter", "getSetter$kotlinpoet", "setSetter$kotlinpoet", "getType$kotlinpoet", "addAnnotation", "annotationSpec", "annotation", "Lcom/squareup/kotlinpoet/ClassName;", "Ljava/lang/Class;", "Lkotlin/reflect/KClass;", "addAnnotations", "annotationSpecs", "", "addKdoc", "block", "format", "args", "", "(Ljava/lang/String;[Ljava/lang/Object;)Lcom/squareup/kotlinpoet/PropertySpec$Builder;", "addModifiers", "([Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/PropertySpec$Builder;", "build", "Lcom/squareup/kotlinpoet/PropertySpec;", "delegate", "codeBlock", "receiver", "Ljava/lang/reflect/Type;", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Builder {
        private final List<AnnotationSpec> annotations;
        private boolean delegated;
        private FunSpec getter;
        private CodeBlock initializer;
        private final CodeBlock.Builder kdoc;
        private final List<KModifier> modifiers;
        private boolean mutable;
        private final String name;
        private TypeName receiverType;
        private FunSpec setter;
        private final TypeName type;

        public Builder(String name, TypeName type) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            this.name = name;
            this.type = type;
            this.kdoc = CodeBlock.Companion.builder();
            this.annotations = new ArrayList();
            this.modifiers = new ArrayList();
        }

        public final String getName$kotlinpoet() {
            return this.name;
        }

        public final TypeName getType$kotlinpoet() {
            return this.type;
        }

        public final boolean getMutable$kotlinpoet() {
            return this.mutable;
        }

        public final void setMutable$kotlinpoet(boolean z) {
            this.mutable = z;
        }

        public final CodeBlock.Builder getKdoc$kotlinpoet() {
            return this.kdoc;
        }

        public final List<AnnotationSpec> getAnnotations$kotlinpoet() {
            return this.annotations;
        }

        public final List<KModifier> getModifiers$kotlinpoet() {
            return this.modifiers;
        }

        public final CodeBlock getInitializer$kotlinpoet() {
            return this.initializer;
        }

        public final void setInitializer$kotlinpoet(CodeBlock codeBlock) {
            this.initializer = codeBlock;
        }

        public final boolean getDelegated$kotlinpoet() {
            return this.delegated;
        }

        public final void setDelegated$kotlinpoet(boolean z) {
            this.delegated = z;
        }

        public final FunSpec getGetter$kotlinpoet() {
            return this.getter;
        }

        public final void setGetter$kotlinpoet(FunSpec funSpec) {
            this.getter = funSpec;
        }

        public final FunSpec getSetter$kotlinpoet() {
            return this.setter;
        }

        public final void setSetter$kotlinpoet(FunSpec funSpec) {
            this.setter = funSpec;
        }

        public final TypeName getReceiverType$kotlinpoet() {
            return this.receiverType;
        }

        public final void setReceiverType$kotlinpoet(TypeName typeName) {
            this.receiverType = typeName;
        }

        public final Builder mutable(boolean z) {
            this.mutable = z;
            return this;
        }

        public final Builder addKdoc(String format, Object... args) {
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            this.kdoc.add(format, Arrays.copyOf(args, args.length));
            return this;
        }

        public final Builder addKdoc(CodeBlock block) {
            Intrinsics.checkParameterIsNotNull(block, "block");
            this.kdoc.add(block);
            return this;
        }

        public final Builder addAnnotations(Iterable<AnnotationSpec> annotationSpecs) {
            Intrinsics.checkParameterIsNotNull(annotationSpecs, "annotationSpecs");
            CollectionsKt.addAll(this.annotations, annotationSpecs);
            return this;
        }

        public final Builder addAnnotation(AnnotationSpec annotationSpec) {
            Intrinsics.checkParameterIsNotNull(annotationSpec, "annotationSpec");
            this.annotations.add(annotationSpec);
            return this;
        }

        public final Builder addAnnotation(ClassName annotation) {
            Intrinsics.checkParameterIsNotNull(annotation, "annotation");
            this.annotations.add(AnnotationSpec.Companion.builder(annotation).build());
            return this;
        }

        public final Builder addAnnotation(Class<?> annotation) {
            Intrinsics.checkParameterIsNotNull(annotation, "annotation");
            return addAnnotation(ClassNames.get(annotation));
        }

        public final Builder addAnnotation(KClass<?> annotation) {
            Intrinsics.checkParameterIsNotNull(annotation, "annotation");
            return addAnnotation(ClassNames.get(annotation));
        }

        public final Builder addModifiers(KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            for (KModifier kModifier : modifiers) {
                kModifier.checkTarget$kotlinpoet(KModifier.Target.PROPERTY);
            }
            CollectionsKt.addAll(this.modifiers, modifiers);
            return this;
        }

        public final Builder initializer(String format, Object... args) {
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            return initializer(CodeBlock.Companion.of(format, Arrays.copyOf(args, args.length)));
        }

        public final Builder initializer(CodeBlock codeBlock) {
            Intrinsics.checkParameterIsNotNull(codeBlock, "codeBlock");
            if (!(this.initializer == null)) {
                throw new IllegalStateException("initializer was already set".toString());
            }
            this.initializer = codeBlock;
            return this;
        }

        public final Builder delegate(String format, Object... args) {
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            return delegate(CodeBlock.Companion.of(format, Arrays.copyOf(args, args.length)));
        }

        public final Builder delegate(CodeBlock codeBlock) {
            Intrinsics.checkParameterIsNotNull(codeBlock, "codeBlock");
            if (!(this.initializer == null)) {
                throw new IllegalStateException("initializer was already set".toString());
            }
            this.initializer = codeBlock;
            this.delegated = true;
            return this;
        }

        public final Builder getter(FunSpec getter) {
            Intrinsics.checkParameterIsNotNull(getter, "getter");
            if (!Intrinsics.areEqual(getter.getName(), FunSpec.GETTER)) {
                throw new IllegalArgumentException(("" + getter.getName() + " is not a getter").toString());
            }
            if (!(this.getter == null)) {
                throw new IllegalStateException("getter was already set".toString());
            }
            this.getter = getter;
            return this;
        }

        public final Builder setter(FunSpec setter) {
            Intrinsics.checkParameterIsNotNull(setter, "setter");
            if (!Intrinsics.areEqual(setter.getName(), FunSpec.SETTER)) {
                throw new IllegalArgumentException(("" + setter.getName() + " is not a setter").toString());
            }
            if (!(this.setter == null)) {
                throw new IllegalStateException("setter was already set".toString());
            }
            this.setter = setter;
            return this;
        }

        public final Builder receiver(TypeName receiverType) {
            Intrinsics.checkParameterIsNotNull(receiverType, "receiverType");
            this.receiverType = receiverType;
            return this;
        }

        public final Builder receiver(Type receiverType) {
            Intrinsics.checkParameterIsNotNull(receiverType, "receiverType");
            return receiver(TypeNames.get(receiverType));
        }

        public final Builder receiver(KClass<?> receiverType) {
            Intrinsics.checkParameterIsNotNull(receiverType, "receiverType");
            return receiver(TypeNames.get(receiverType));
        }

        public final PropertySpec build() {
            return new PropertySpec(this, null);
        }
    }

    /* compiled from: PropertySpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J1\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000bH\u0007¢\u0006\u0002\u0010\fJ1\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\r2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000bH\u0007¢\u0006\u0002\u0010\u000eJ5\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000bH\u0007¢\u0006\u0002\u0010\u0010J1\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000bH\u0007¢\u0006\u0002\u0010\fJ1\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\r2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000bH\u0007¢\u0006\u0002\u0010\u000eJ5\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000bH\u0007¢\u0006\u0002\u0010\u0010¨\u0006\u0012"}, d2 = {"Lcom/squareup/kotlinpoet/PropertySpec$Companion;", "", "()V", "builder", "Lcom/squareup/kotlinpoet/PropertySpec$Builder;", "name", "", "type", "Lcom/squareup/kotlinpoet/TypeName;", "modifiers", "", "Lcom/squareup/kotlinpoet/KModifier;", "(Ljava/lang/String;Lcom/squareup/kotlinpoet/TypeName;[Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/PropertySpec$Builder;", "Ljava/lang/reflect/Type;", "(Ljava/lang/String;Ljava/lang/reflect/Type;[Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/PropertySpec$Builder;", "Lkotlin/reflect/KClass;", "(Ljava/lang/String;Lkotlin/reflect/KClass;[Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/PropertySpec$Builder;", "varBuilder", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final Builder builder(String name, TypeName type, KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            if (!UtilKt.isName(name)) {
                throw new IllegalArgumentException(("not a valid name: " + name).toString());
            }
            return new Builder(name, type).addModifiers((KModifier[]) Arrays.copyOf(modifiers, modifiers.length));
        }

        @JvmStatic
        public final Builder builder(String name, Type type, KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            return builder(name, TypeNames.get(type), (KModifier[]) Arrays.copyOf(modifiers, modifiers.length));
        }

        @JvmStatic
        public final Builder builder(String name, KClass<?> type, KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            return builder(name, TypeNames.get(type), (KModifier[]) Arrays.copyOf(modifiers, modifiers.length));
        }

        @JvmStatic
        public final Builder varBuilder(String name, TypeName type, KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            if (!UtilKt.isName(name)) {
                throw new IllegalArgumentException(("not a valid name: " + name).toString());
            }
            return new Builder(name, type).mutable(true).addModifiers((KModifier[]) Arrays.copyOf(modifiers, modifiers.length));
        }

        @JvmStatic
        public final Builder varBuilder(String name, Type type, KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            return varBuilder(name, TypeNames.get(type), (KModifier[]) Arrays.copyOf(modifiers, modifiers.length));
        }

        @JvmStatic
        public final Builder varBuilder(String name, KClass<?> type, KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            return varBuilder(name, TypeNames.get(type), (KModifier[]) Arrays.copyOf(modifiers, modifiers.length));
        }
    }
}
