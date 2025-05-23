package com.squareup.kotlinpoet;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
/* compiled from: ParameterSpec.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 +2\u00020\u0001:\u0002*+B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001f\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u001f\u001a\u00020 H\u0000¢\u0006\u0002\b!J\u0015\u0010\"\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0000¢\u0006\u0002\b#J\u0013\u0010$\u001a\u00020 2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010&\u001a\u00020'H\u0016J\u001a\u0010(\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0017\u001a\u00020\u0018J\b\u0010)\u001a\u00020\u0014H\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006,"}, d2 = {"Lcom/squareup/kotlinpoet/ParameterSpec;", "", "builder", "Lcom/squareup/kotlinpoet/ParameterSpec$Builder;", "(Lcom/squareup/kotlinpoet/ParameterSpec$Builder;)V", "annotations", "", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "getAnnotations", "()Ljava/util/List;", "defaultValue", "Lcom/squareup/kotlinpoet/CodeBlock;", "getDefaultValue", "()Lcom/squareup/kotlinpoet/CodeBlock;", "modifiers", "", "Lcom/squareup/kotlinpoet/KModifier;", "getModifiers", "()Ljava/util/Set;", "name", "", "getName", "()Ljava/lang/String;", "type", "Lcom/squareup/kotlinpoet/TypeName;", "getType", "()Lcom/squareup/kotlinpoet/TypeName;", "emit", "", "codeWriter", "Lcom/squareup/kotlinpoet/CodeWriter;", "includeType", "", "emit$kotlinpoet", "emitDefaultValue", "emitDefaultValue$kotlinpoet", "equals", "other", "hashCode", "", "toBuilder", "toString", "Builder", "Companion", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class ParameterSpec {
    public static final Companion Companion = new Companion(null);
    private final List<AnnotationSpec> annotations;
    private final CodeBlock defaultValue;
    private final Set<KModifier> modifiers;
    private final String name;
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
    public static final ParameterSpec get(VariableElement element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        return Companion.get(element);
    }

    @JvmStatic
    public static final List<ParameterSpec> parametersOf(ExecutableElement method) {
        Intrinsics.checkParameterIsNotNull(method, "method");
        return Companion.parametersOf(method);
    }

    private ParameterSpec(Builder builder) {
        this.name = builder.getName$kotlinpoet();
        this.annotations = UtilKt.toImmutableList(builder.getAnnotations$kotlinpoet());
        this.modifiers = UtilKt.toImmutableSet(builder.getModifiers$kotlinpoet());
        this.type = builder.getType$kotlinpoet();
        this.defaultValue = builder.getDefaultValue$kotlinpoet();
    }

    public /* synthetic */ ParameterSpec(Builder builder, DefaultConstructorMarker defaultConstructorMarker) {
        this(builder);
    }

    public final String getName() {
        return this.name;
    }

    public final List<AnnotationSpec> getAnnotations() {
        return this.annotations;
    }

    public final Set<KModifier> getModifiers() {
        return this.modifiers;
    }

    public final TypeName getType() {
        return this.type;
    }

    public final CodeBlock getDefaultValue() {
        return this.defaultValue;
    }

    public static /* bridge */ /* synthetic */ void emit$kotlinpoet$default(ParameterSpec parameterSpec, CodeWriter codeWriter, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        parameterSpec.emit$kotlinpoet(codeWriter, z);
    }

    public final void emit$kotlinpoet(CodeWriter codeWriter, boolean z) {
        Intrinsics.checkParameterIsNotNull(codeWriter, "codeWriter");
        codeWriter.emitAnnotations(this.annotations, true);
        CodeWriter.emitModifiers$default(codeWriter, this.modifiers, null, 2, null);
        codeWriter.emitCode("%L", this.name);
        if (z) {
            codeWriter.emitCode(": %T", this.type);
        }
        emitDefaultValue$kotlinpoet(codeWriter);
    }

    public final void emitDefaultValue$kotlinpoet(CodeWriter codeWriter) {
        Intrinsics.checkParameterIsNotNull(codeWriter, "codeWriter");
        CodeBlock codeBlock = this.defaultValue;
        if (codeBlock != null) {
            codeWriter.emitCode(" = %[%L%]", codeBlock);
        }
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
        emit$kotlinpoet$default(this, new CodeWriter(sb, null, null, null, 14, null), false, 2, null);
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    public static /* bridge */ /* synthetic */ Builder toBuilder$default(ParameterSpec parameterSpec, String str, TypeName typeName, int i, Object obj) {
        if ((i & 1) != 0) {
            str = parameterSpec.name;
        }
        if ((i & 2) != 0) {
            typeName = parameterSpec.type;
        }
        return parameterSpec.toBuilder(str, typeName);
    }

    public final Builder toBuilder(String name, TypeName type) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        Builder builder = new Builder(name, type);
        CollectionsKt.addAll(builder.getAnnotations$kotlinpoet(), this.annotations);
        CollectionsKt.addAll(builder.getModifiers$kotlinpoet(), this.modifiers);
        return builder;
    }

    /* compiled from: ParameterSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\tJ\u000e\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u001cJ\u0012\u0010\u0019\u001a\u00020\u00002\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u001dJ\u0012\u0010\u0019\u001a\u00020\u00002\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u001eJ\u0014\u0010\u001f\u001a\u00020\u00002\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\t0!J\u001f\u0010\"\u001a\u00020\u00002\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130#\"\u00020\u0013¢\u0006\u0002\u0010$J\u0014\u0010\"\u001a\u00020\u00002\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130!J\u0006\u0010%\u001a\u00020&J\u000e\u0010\f\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\rJ+\u0010\f\u001a\u00020\u00002\u0006\u0010(\u001a\u00020\u00032\u0016\u0010)\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010#\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010*J\u0014\u0010+\u001a\u00020\u00002\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020,0!R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006-"}, d2 = {"Lcom/squareup/kotlinpoet/ParameterSpec$Builder;", "", "name", "", "type", "Lcom/squareup/kotlinpoet/TypeName;", "(Ljava/lang/String;Lcom/squareup/kotlinpoet/TypeName;)V", "annotations", "", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "getAnnotations$kotlinpoet", "()Ljava/util/List;", "defaultValue", "Lcom/squareup/kotlinpoet/CodeBlock;", "getDefaultValue$kotlinpoet", "()Lcom/squareup/kotlinpoet/CodeBlock;", "setDefaultValue$kotlinpoet", "(Lcom/squareup/kotlinpoet/CodeBlock;)V", "modifiers", "Lcom/squareup/kotlinpoet/KModifier;", "getModifiers$kotlinpoet", "getName$kotlinpoet", "()Ljava/lang/String;", "getType$kotlinpoet", "()Lcom/squareup/kotlinpoet/TypeName;", "addAnnotation", "annotationSpec", "annotation", "Lcom/squareup/kotlinpoet/ClassName;", "Ljava/lang/Class;", "Lkotlin/reflect/KClass;", "addAnnotations", "annotationSpecs", "", "addModifiers", "", "([Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/ParameterSpec$Builder;", "build", "Lcom/squareup/kotlinpoet/ParameterSpec;", "codeBlock", "format", "args", "(Ljava/lang/String;[Ljava/lang/Object;)Lcom/squareup/kotlinpoet/ParameterSpec$Builder;", "jvmModifiers", "Ljavax/lang/model/element/Modifier;", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Builder {
        private final List<AnnotationSpec> annotations;
        private CodeBlock defaultValue;
        private final List<KModifier> modifiers;
        private final String name;
        private final TypeName type;

        @Metadata(bv = {1, 0, 2}, k = 3, mv = {1, 1, 7})
        /* loaded from: classes2.dex */
        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Modifier.values().length];
                $EnumSwitchMapping$0 = iArr;
                iArr[Modifier.FINAL.ordinal()] = 1;
            }
        }

        public Builder(String name, TypeName type) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            this.name = name;
            this.type = type;
            this.annotations = new ArrayList();
            this.modifiers = new ArrayList();
        }

        public final String getName$kotlinpoet() {
            return this.name;
        }

        public final TypeName getType$kotlinpoet() {
            return this.type;
        }

        public final List<AnnotationSpec> getAnnotations$kotlinpoet() {
            return this.annotations;
        }

        public final List<KModifier> getModifiers$kotlinpoet() {
            return this.modifiers;
        }

        public final CodeBlock getDefaultValue$kotlinpoet() {
            return this.defaultValue;
        }

        public final void setDefaultValue$kotlinpoet(CodeBlock codeBlock) {
            this.defaultValue = codeBlock;
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
            CollectionsKt.addAll(this.modifiers, modifiers);
            return this;
        }

        public final Builder addModifiers(Iterable<? extends KModifier> modifiers) {
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            CollectionsKt.addAll(this.modifiers, modifiers);
            return this;
        }

        public final Builder jvmModifiers(Iterable<? extends Modifier> modifiers) {
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            for (Modifier modifier : modifiers) {
                if (WhenMappings.$EnumSwitchMapping$0[modifier.ordinal()] == 1) {
                    this.modifiers.add(KModifier.FINAL);
                } else {
                    throw new IllegalArgumentException("unexpected parameter modifier " + modifier);
                }
            }
            return this;
        }

        public final Builder defaultValue(String format, Object... args) {
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            return defaultValue(CodeBlock.Companion.of(format, Arrays.copyOf(args, args.length)));
        }

        public final Builder defaultValue(CodeBlock codeBlock) {
            Intrinsics.checkParameterIsNotNull(codeBlock, "codeBlock");
            if (!(this.defaultValue == null)) {
                throw new IllegalStateException("initializer was already set".toString());
            }
            this.defaultValue = codeBlock;
            return this;
        }

        public final ParameterSpec build() {
            return new ParameterSpec(this, null);
        }
    }

    /* compiled from: ParameterSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J1\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000bH\u0007¢\u0006\u0002\u0010\fJ1\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\r2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000bH\u0007¢\u0006\u0002\u0010\u000eJ5\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000bH\u0007¢\u0006\u0002\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00120\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0007¨\u0006\u0019"}, d2 = {"Lcom/squareup/kotlinpoet/ParameterSpec$Companion;", "", "()V", "builder", "Lcom/squareup/kotlinpoet/ParameterSpec$Builder;", "name", "", "type", "Lcom/squareup/kotlinpoet/TypeName;", "modifiers", "", "Lcom/squareup/kotlinpoet/KModifier;", "(Ljava/lang/String;Lcom/squareup/kotlinpoet/TypeName;[Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/ParameterSpec$Builder;", "Ljava/lang/reflect/Type;", "(Ljava/lang/String;Ljava/lang/reflect/Type;[Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/ParameterSpec$Builder;", "Lkotlin/reflect/KClass;", "(Ljava/lang/String;Lkotlin/reflect/KClass;[Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/ParameterSpec$Builder;", "get", "Lcom/squareup/kotlinpoet/ParameterSpec;", "element", "Ljavax/lang/model/element/VariableElement;", "parametersOf", "", "method", "Ljavax/lang/model/element/ExecutableElement;", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final ParameterSpec get(VariableElement element) {
            Intrinsics.checkParameterIsNotNull(element, "element");
            Builder builder = ParameterSpec.Companion.builder(element.getSimpleName().toString(), TypeNames.get(element.asType()), new KModifier[0]);
            Set modifiers = element.getModifiers();
            Intrinsics.checkExpressionValueIsNotNull(modifiers, "element.modifiers");
            return builder.jvmModifiers(modifiers).build();
        }

        @JvmStatic
        public final List<ParameterSpec> parametersOf(ExecutableElement method) {
            Intrinsics.checkParameterIsNotNull(method, "method");
            List<VariableElement> parameters = method.getParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(parameters, 10));
            for (VariableElement it : parameters) {
                Companion companion = ParameterSpec.Companion;
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                arrayList.add(companion.get(it));
            }
            return arrayList;
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
    }
}
