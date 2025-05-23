package com.squareup.kotlinpoet;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
/* compiled from: TypeAliasSpec.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 $2\u00020\u0001:\u0002#$B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0015\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0000¢\u0006\u0002\b\u001bJ\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001f\u001a\u00020 H\u0016J\u0006\u0010!\u001a\u00020\u0003J\b\u0010\"\u001a\u00020\u000bH\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006%"}, d2 = {"Lcom/squareup/kotlinpoet/TypeAliasSpec;", "", "builder", "Lcom/squareup/kotlinpoet/TypeAliasSpec$Builder;", "(Lcom/squareup/kotlinpoet/TypeAliasSpec$Builder;)V", "modifiers", "", "Lcom/squareup/kotlinpoet/KModifier;", "getModifiers", "()Ljava/util/Set;", "name", "", "getName", "()Ljava/lang/String;", "type", "Lcom/squareup/kotlinpoet/TypeName;", "getType", "()Lcom/squareup/kotlinpoet/TypeName;", "typeVariables", "", "Lcom/squareup/kotlinpoet/TypeVariableName;", "getTypeVariables", "()Ljava/util/List;", "emit", "", "codeWriter", "Lcom/squareup/kotlinpoet/CodeWriter;", "emit$kotlinpoet", "equals", "", "other", "hashCode", "", "toBuilder", "toString", "Builder", "Companion", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class TypeAliasSpec {
    public static final Companion Companion = new Companion(null);
    private final Set<KModifier> modifiers;
    private final String name;
    private final TypeName type;
    private final List<TypeVariableName> typeVariables;

    @JvmStatic
    public static final Builder builder(String name, TypeName type) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        return Companion.builder(name, type);
    }

    @JvmStatic
    public static final Builder builder(String name, Type type) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        return Companion.builder(name, type);
    }

    @JvmStatic
    public static final Builder builder(String name, KClass<?> type) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        return Companion.builder(name, type);
    }

    private TypeAliasSpec(Builder builder) {
        this.name = builder.getName$kotlinpoet();
        this.type = builder.getType$kotlinpoet();
        this.modifiers = UtilKt.toImmutableSet(builder.getModifiers$kotlinpoet());
        this.typeVariables = UtilKt.toImmutableList(builder.getTypeVariables$kotlinpoet());
    }

    public /* synthetic */ TypeAliasSpec(Builder builder, DefaultConstructorMarker defaultConstructorMarker) {
        this(builder);
    }

    public final String getName() {
        return this.name;
    }

    public final TypeName getType() {
        return this.type;
    }

    public final Set<KModifier> getModifiers() {
        return this.modifiers;
    }

    public final List<TypeVariableName> getTypeVariables() {
        return this.typeVariables;
    }

    public final void emit$kotlinpoet(CodeWriter codeWriter) {
        Intrinsics.checkParameterIsNotNull(codeWriter, "codeWriter");
        CodeWriter.emitModifiers$default(codeWriter, this.modifiers, null, 2, null);
        codeWriter.emitCode("typealias %L", this.name);
        codeWriter.emitTypeVariables(this.typeVariables);
        codeWriter.emitCode(" = %T", this.type);
        codeWriter.emit("\n");
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
        emit$kotlinpoet(new CodeWriter(sb, null, null, null, 14, null));
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    public final Builder toBuilder() {
        Builder builder = new Builder(this.name, this.type);
        CollectionsKt.addAll(builder.getModifiers$kotlinpoet(), this.modifiers);
        CollectionsKt.addAll(builder.getTypeVariables$kotlinpoet(), this.typeVariables);
        return builder;
    }

    /* compiled from: TypeAliasSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\tH\u0002J\u001f\u0010\u0016\u001a\u00020\u00002\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\u0017\"\u00020\t¢\u0006\u0002\u0010\u0018J\u000e\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u0011J\u0014\u0010\u001b\u001a\u00020\u00002\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u001cJ\u0006\u0010\u001d\u001a\u00020\u001eR\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000b¨\u0006\u001f"}, d2 = {"Lcom/squareup/kotlinpoet/TypeAliasSpec$Builder;", "", "name", "", "type", "Lcom/squareup/kotlinpoet/TypeName;", "(Ljava/lang/String;Lcom/squareup/kotlinpoet/TypeName;)V", "modifiers", "", "Lcom/squareup/kotlinpoet/KModifier;", "getModifiers$kotlinpoet", "()Ljava/util/Set;", "getName$kotlinpoet", "()Ljava/lang/String;", "getType$kotlinpoet", "()Lcom/squareup/kotlinpoet/TypeName;", "typeVariables", "Lcom/squareup/kotlinpoet/TypeVariableName;", "getTypeVariables$kotlinpoet", "addModifier", "", "modifier", "addModifiers", "", "([Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/TypeAliasSpec$Builder;", "addTypeVariable", "typeVariable", "addTypeVariables", "", "build", "Lcom/squareup/kotlinpoet/TypeAliasSpec;", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Builder {
        private final Set<KModifier> modifiers;
        private final String name;
        private final TypeName type;
        private final Set<TypeVariableName> typeVariables;

        public Builder(String name, TypeName type) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            this.name = name;
            this.type = type;
            this.modifiers = new LinkedHashSet();
            this.typeVariables = new LinkedHashSet();
            if (UtilKt.isName(name)) {
                return;
            }
            throw new IllegalArgumentException(("not a valid name: " + name).toString());
        }

        public final String getName$kotlinpoet() {
            return this.name;
        }

        public final TypeName getType$kotlinpoet() {
            return this.type;
        }

        public final Set<KModifier> getModifiers$kotlinpoet() {
            return this.modifiers;
        }

        public final Set<TypeVariableName> getTypeVariables$kotlinpoet() {
            return this.typeVariables;
        }

        public final Builder addModifiers(KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            for (KModifier kModifier : modifiers) {
                addModifier(kModifier);
            }
            return this;
        }

        private final void addModifier(KModifier kModifier) {
            if (!SetsKt.setOf((Object[]) new KModifier[]{KModifier.PUBLIC, KModifier.INTERNAL, KModifier.PRIVATE, KModifier.ACTUAL}).contains(kModifier)) {
                throw new IllegalArgumentException(("unexpected typealias modifier " + kModifier).toString());
            }
            this.modifiers.add(kModifier);
        }

        public final Builder addTypeVariables(Iterable<TypeVariableName> typeVariables) {
            Intrinsics.checkParameterIsNotNull(typeVariables, "typeVariables");
            CollectionsKt.addAll(this.typeVariables, typeVariables);
            return this;
        }

        public final Builder addTypeVariable(TypeVariableName typeVariable) {
            Intrinsics.checkParameterIsNotNull(typeVariable, "typeVariable");
            this.typeVariables.add(typeVariable);
            return this;
        }

        public final TypeAliasSpec build() {
            return new TypeAliasSpec(this, null);
        }
    }

    /* compiled from: TypeAliasSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\tH\u0007J\u001c\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\nH\u0007¨\u0006\u000b"}, d2 = {"Lcom/squareup/kotlinpoet/TypeAliasSpec$Companion;", "", "()V", "builder", "Lcom/squareup/kotlinpoet/TypeAliasSpec$Builder;", "name", "", "type", "Lcom/squareup/kotlinpoet/TypeName;", "Ljava/lang/reflect/Type;", "Lkotlin/reflect/KClass;", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final Builder builder(String name, TypeName type) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            return new Builder(name, type);
        }

        @JvmStatic
        public final Builder builder(String name, Type type) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            return builder(name, TypeNames.get(type));
        }

        @JvmStatic
        public final Builder builder(String name, KClass<?> type) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            return builder(name, TypeNames.get(type));
        }
    }
}
