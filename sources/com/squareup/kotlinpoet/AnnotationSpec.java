package com.squareup.kotlinpoet;

import com.squareup.kotlinpoet.CodeBlock;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
/* compiled from: AnnotationSpec.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \"2\u00020\u0001:\u0004!\"#$B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J'\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u0017H\u0000¢\u0006\u0002\b\u0019J\u0013\u0010\u001a\u001a\u00020\u00172\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u0006\u0010\u001e\u001a\u00020\u0003J\b\u0010\u001f\u001a\u00020 H\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006%"}, d2 = {"Lcom/squareup/kotlinpoet/AnnotationSpec;", "", "builder", "Lcom/squareup/kotlinpoet/AnnotationSpec$Builder;", "(Lcom/squareup/kotlinpoet/AnnotationSpec$Builder;)V", "members", "", "Lcom/squareup/kotlinpoet/CodeBlock;", "getMembers", "()Ljava/util/List;", "type", "Lcom/squareup/kotlinpoet/TypeName;", "getType", "()Lcom/squareup/kotlinpoet/TypeName;", "useSiteTarget", "Lcom/squareup/kotlinpoet/AnnotationSpec$UseSiteTarget;", "getUseSiteTarget", "()Lcom/squareup/kotlinpoet/AnnotationSpec$UseSiteTarget;", "emit", "", "codeWriter", "Lcom/squareup/kotlinpoet/CodeWriter;", "inline", "", "asParameter", "emit$kotlinpoet", "equals", "other", "hashCode", "", "toBuilder", "toString", "", "Builder", "Companion", "UseSiteTarget", "Visitor", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class AnnotationSpec {
    public static final Companion Companion = new Companion(null);
    private final List<CodeBlock> members;
    private final TypeName type;
    private final UseSiteTarget useSiteTarget;

    @JvmStatic
    public static final Builder builder(ClassName type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        return Companion.builder(type);
    }

    @JvmStatic
    public static final Builder builder(Class<?> type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        return Companion.builder(type);
    }

    @JvmStatic
    public static final Builder builder(KClass<?> type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        return Companion.builder(type);
    }

    @JvmStatic
    public static final AnnotationSpec get(Annotation annotation) {
        return Companion.get$default(Companion, annotation, false, 2, null);
    }

    @JvmStatic
    public static final AnnotationSpec get(Annotation annotation, boolean z) {
        Intrinsics.checkParameterIsNotNull(annotation, "annotation");
        return Companion.get(annotation, z);
    }

    @JvmStatic
    public static final AnnotationSpec get(AnnotationMirror annotation) {
        Intrinsics.checkParameterIsNotNull(annotation, "annotation");
        return Companion.get(annotation);
    }

    private AnnotationSpec(Builder builder) {
        this.type = builder.getType$kotlinpoet();
        this.members = UtilKt.toImmutableList(builder.getMembers$kotlinpoet());
        this.useSiteTarget = builder.getUseSiteTarget$kotlinpoet();
    }

    public /* synthetic */ AnnotationSpec(Builder builder, DefaultConstructorMarker defaultConstructorMarker) {
        this(builder);
    }

    public final TypeName getType() {
        return this.type;
    }

    public final List<CodeBlock> getMembers() {
        return this.members;
    }

    public final UseSiteTarget getUseSiteTarget() {
        return this.useSiteTarget;
    }

    public static /* bridge */ /* synthetic */ void emit$kotlinpoet$default(AnnotationSpec annotationSpec, CodeWriter codeWriter, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z2 = false;
        }
        annotationSpec.emit$kotlinpoet(codeWriter, z, z2);
    }

    public final void emit$kotlinpoet(CodeWriter codeWriter, boolean z, boolean z2) {
        Intrinsics.checkParameterIsNotNull(codeWriter, "codeWriter");
        if (!z2) {
            codeWriter.emit("@");
        }
        if (this.useSiteTarget != null) {
            codeWriter.emit(this.useSiteTarget.getKeyword$kotlinpoet() + ":");
        }
        codeWriter.emitCode("%T", this.type);
        if (!this.members.isEmpty() || z2) {
            String str = z ? "" : "\n";
            String str2 = z ? ", " : ",\n";
            codeWriter.emit("(");
            if (this.members.size() > 1) {
                codeWriter.emit(str).indent(2);
            }
            List<CodeBlock> list = this.members;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (CodeBlock codeBlock : list) {
                arrayList.add(codeBlock.replaceAll$kotlinpoet("%W", str));
            }
            ArrayList<CodeBlock> arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            for (CodeBlock codeBlock2 : arrayList2) {
                if (z) {
                    codeBlock2 = codeBlock2.replaceAll$kotlinpoet("[%>|%<]", "");
                }
                arrayList3.add(codeBlock2);
            }
            codeWriter.emitCode(CodeBlocks.joinToCode$default(arrayList3, str2, null, null, 6, null));
            if (this.members.size() > 1) {
                codeWriter.unindent(2).emit(str);
            }
            codeWriter.emit(")");
        }
    }

    public final Builder toBuilder() {
        Builder builder = new Builder(this.type);
        CollectionsKt.addAll(builder.getMembers$kotlinpoet(), this.members);
        builder.setUseSiteTarget$kotlinpoet(this.useSiteTarget);
        return builder;
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
        emit$kotlinpoet(new CodeWriter(sb, null, null, null, 14, null), true, false);
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    /* compiled from: AnnotationSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f¨\u0006\u0010"}, d2 = {"Lcom/squareup/kotlinpoet/AnnotationSpec$UseSiteTarget;", "", "keyword", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getKeyword$kotlinpoet", "()Ljava/lang/String;", "FILE", "PROPERTY", "FIELD", "GET", "SET", "RECEIVER", "PARAM", "SETPARAM", "DELEGATE", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public enum UseSiteTarget {
        FILE("file"),
        PROPERTY("property"),
        FIELD("field"),
        GET("get"),
        SET("set"),
        RECEIVER("receiver"),
        PARAM("param"),
        SETPARAM("setparam"),
        DELEGATE("delegate");
        
        private final String keyword;

        UseSiteTarget(String keyword) {
            Intrinsics.checkParameterIsNotNull(keyword, "keyword");
            this.keyword = keyword;
        }

        public final String getKeyword$kotlinpoet() {
            return this.keyword;
        }
    }

    /* compiled from: AnnotationSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0007J'\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0017\"\u00020\u0001¢\u0006\u0002\u0010\u0018J\u0006\u0010\u0019\u001a\u00020\u001aJ\u0010\u0010\f\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\rR\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u001c"}, d2 = {"Lcom/squareup/kotlinpoet/AnnotationSpec$Builder;", "", "type", "Lcom/squareup/kotlinpoet/TypeName;", "(Lcom/squareup/kotlinpoet/TypeName;)V", "members", "", "Lcom/squareup/kotlinpoet/CodeBlock;", "getMembers$kotlinpoet", "()Ljava/util/List;", "getType$kotlinpoet", "()Lcom/squareup/kotlinpoet/TypeName;", "useSiteTarget", "Lcom/squareup/kotlinpoet/AnnotationSpec$UseSiteTarget;", "getUseSiteTarget$kotlinpoet", "()Lcom/squareup/kotlinpoet/AnnotationSpec$UseSiteTarget;", "setUseSiteTarget$kotlinpoet", "(Lcom/squareup/kotlinpoet/AnnotationSpec$UseSiteTarget;)V", "addMember", "codeBlock", "format", "", "args", "", "(Ljava/lang/String;[Ljava/lang/Object;)Lcom/squareup/kotlinpoet/AnnotationSpec$Builder;", "build", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "Companion", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Builder {
        public static final Companion Companion = new Companion(null);
        private final List<CodeBlock> members;
        private final TypeName type;
        private UseSiteTarget useSiteTarget;

        public Builder(TypeName type) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            this.type = type;
            this.members = new ArrayList();
        }

        public final TypeName getType$kotlinpoet() {
            return this.type;
        }

        public final List<CodeBlock> getMembers$kotlinpoet() {
            return this.members;
        }

        public final UseSiteTarget getUseSiteTarget$kotlinpoet() {
            return this.useSiteTarget;
        }

        public final void setUseSiteTarget$kotlinpoet(UseSiteTarget useSiteTarget) {
            this.useSiteTarget = useSiteTarget;
        }

        public final Builder addMember(String format, Object... args) {
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            return addMember(CodeBlock.Companion.of(format, Arrays.copyOf(args, args.length)));
        }

        public final Builder addMember(CodeBlock codeBlock) {
            Intrinsics.checkParameterIsNotNull(codeBlock, "codeBlock");
            this.members.add(codeBlock);
            return this;
        }

        public final Builder useSiteTarget(UseSiteTarget useSiteTarget) {
            this.useSiteTarget = useSiteTarget;
            return this;
        }

        public final AnnotationSpec build() {
            return new AnnotationSpec(this, null);
        }

        /* compiled from: AnnotationSpec.kt */
        @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0001H\u0000¢\u0006\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lcom/squareup/kotlinpoet/AnnotationSpec$Builder$Companion;", "", "()V", "memberForValue", "Lcom/squareup/kotlinpoet/CodeBlock;", "value", "memberForValue$kotlinpoet", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
        /* loaded from: classes2.dex */
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final CodeBlock memberForValue$kotlinpoet(Object value) {
                Intrinsics.checkParameterIsNotNull(value, "value");
                if (value instanceof Class) {
                    return CodeBlock.Companion.of("%T::class", value);
                }
                if (!(value instanceof Enum)) {
                    return value instanceof String ? CodeBlock.Companion.of("%S", value) : value instanceof Float ? CodeBlock.Companion.of("%Lf", value) : value instanceof Character ? CodeBlock.Companion.of("'%L'", UtilKt.characterLiteralWithoutSingleQuotes(((Character) value).charValue())) : CodeBlock.Companion.of("%L", value);
                }
                Enum r6 = (Enum) value;
                return CodeBlock.Companion.of("%T.%L", r6.getClass(), r6.name());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AnnotationSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J\u0018\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0014J\u0018\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u001e\u0010\u000f\u001a\u00020\u00022\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000b\u001a\u00020\u0003H\u0016R\u0014\u0010\u0004\u001a\u00020\u0002X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0019"}, d2 = {"Lcom/squareup/kotlinpoet/AnnotationSpec$Visitor;", "Ljavax/lang/model/util/SimpleAnnotationValueVisitor7;", "Lcom/squareup/kotlinpoet/CodeBlock$Builder;", "", "builder", "(Lcom/squareup/kotlinpoet/CodeBlock$Builder;)V", "getBuilder$kotlinpoet", "()Lcom/squareup/kotlinpoet/CodeBlock$Builder;", "defaultAction", "o", "", "name", "visitAnnotation", "a", "Ljavax/lang/model/element/AnnotationMirror;", "visitArray", "values", "", "Ljavax/lang/model/element/AnnotationValue;", "visitEnumConstant", "c", "Ljavax/lang/model/element/VariableElement;", "visitType", "t", "Ljavax/lang/model/type/TypeMirror;", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Visitor extends SimpleAnnotationValueVisitor7<CodeBlock.Builder, String> {
        private final CodeBlock.Builder builder;

        public /* bridge */ /* synthetic */ Object visitArray(List list, Object obj) {
            return visitArray((List<? extends AnnotationValue>) list, (String) obj);
        }

        public final CodeBlock.Builder getBuilder$kotlinpoet() {
            return this.builder;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Visitor(CodeBlock.Builder builder) {
            super(builder);
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            this.builder = builder;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public CodeBlock.Builder defaultAction(Object o, String name) {
            Intrinsics.checkParameterIsNotNull(o, "o");
            Intrinsics.checkParameterIsNotNull(name, "name");
            return this.builder.add(Builder.Companion.memberForValue$kotlinpoet(o));
        }

        public CodeBlock.Builder visitAnnotation(AnnotationMirror a, String name) {
            Intrinsics.checkParameterIsNotNull(a, "a");
            Intrinsics.checkParameterIsNotNull(name, "name");
            return this.builder.add("%L", AnnotationSpec.Companion.get(a));
        }

        public CodeBlock.Builder visitEnumConstant(VariableElement c, String name) {
            Intrinsics.checkParameterIsNotNull(c, "c");
            Intrinsics.checkParameterIsNotNull(name, "name");
            return this.builder.add("%T.%L", c.asType(), c.getSimpleName());
        }

        public CodeBlock.Builder visitType(TypeMirror t, String name) {
            Intrinsics.checkParameterIsNotNull(t, "t");
            Intrinsics.checkParameterIsNotNull(name, "name");
            return this.builder.add("%T::class", t);
        }

        public CodeBlock.Builder visitArray(List<? extends AnnotationValue> values, String name) {
            Intrinsics.checkParameterIsNotNull(values, "values");
            Intrinsics.checkParameterIsNotNull(name, "name");
            this.builder.add("[%W%>%>", new Object[0]);
            int i = 0;
            for (AnnotationValue annotationValue : values) {
                int i2 = i + 1;
                if (i > 0) {
                    this.builder.add(",%W", new Object[0]);
                }
                annotationValue.accept((AnnotationValueVisitor) this, name);
                i = i2;
            }
            this.builder.add("%W%<%<]", new Object[0]);
            return this.builder;
        }
    }

    /* compiled from: AnnotationSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0014\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0007H\u0007J\u0014\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\bH\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u001a\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007¨\u0006\u0010"}, d2 = {"Lcom/squareup/kotlinpoet/AnnotationSpec$Companion;", "", "()V", "builder", "Lcom/squareup/kotlinpoet/AnnotationSpec$Builder;", "type", "Lcom/squareup/kotlinpoet/ClassName;", "Ljava/lang/Class;", "Lkotlin/reflect/KClass;", "get", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "annotation", "Ljavax/lang/model/element/AnnotationMirror;", "", "includeDefaultValues", "", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Companion {
        @JvmStatic
        public final AnnotationSpec get(Annotation annotation) {
            return get$default(this, annotation, false, 2, null);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* bridge */ /* synthetic */ AnnotationSpec get$default(Companion companion, Annotation annotation, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            return companion.get(annotation, z);
        }

        @JvmStatic
        public final AnnotationSpec get(Annotation annotation, boolean z) {
            Intrinsics.checkParameterIsNotNull(annotation, "annotation");
            try {
                Companion companion = this;
                Class<? extends Annotation> annotationType = annotation.annotationType();
                Intrinsics.checkExpressionValueIsNotNull(annotationType, "javaAnnotation.annotationType()");
                Builder builder = builder(annotationType);
                for (Method method : ArraysKt.sortedWith(annotation.annotationType().getDeclaredMethods(), new Comparator<T>() { // from class: com.squareup.kotlinpoet.AnnotationSpec$Companion$get$$inlined$sortedBy$1
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt.compareValues(((Method) t).getName(), ((Method) t2).getName());
                    }
                })) {
                    Object value = method.invoke(annotation, new Object[0]);
                    if (z || !Objects.deepEquals(value, method.getDefaultValue())) {
                        CodeBlock.Builder builder2 = CodeBlock.Companion.builder();
                        if (!Intrinsics.areEqual(method.getName(), "value")) {
                            builder2.add("%L = ", method.getName());
                        }
                        if (value.getClass().isArray()) {
                            builder2.add("[%W%>%>", new Object[0]);
                            int length = Array.getLength(value);
                            for (int i = 0; i < length; i++) {
                                if (i > 0) {
                                    builder2.add(",%W", new Object[0]);
                                }
                                Builder.Companion companion2 = Builder.Companion;
                                Object obj = Array.get(value, i);
                                Intrinsics.checkExpressionValueIsNotNull(obj, "Array.get(value, i)");
                                builder2.add(companion2.memberForValue$kotlinpoet(obj));
                            }
                            builder2.add("%W%<%<]", new Object[0]);
                            builder.addMember(builder2.build());
                        } else if (value instanceof Annotation) {
                            Companion companion3 = this;
                            builder2.add("%L", get$default(this, (Annotation) value, false, 2, null));
                            builder.addMember(builder2.build());
                        } else {
                            Builder.Companion companion4 = Builder.Companion;
                            Intrinsics.checkExpressionValueIsNotNull(value, "value");
                            builder2.add("%L", companion4.memberForValue$kotlinpoet(value));
                            builder.addMember(builder2.build());
                        }
                    }
                }
                return builder.build();
            } catch (Exception e) {
                throw new RuntimeException("Reflecting " + annotation + " failed!", e);
            }
        }

        @JvmStatic
        public final AnnotationSpec get(AnnotationMirror annotation) {
            Intrinsics.checkParameterIsNotNull(annotation, "annotation");
            TypeElement asElement = annotation.getAnnotationType().asElement();
            if (asElement == null) {
                throw new TypeCastException("null cannot be cast to non-null type javax.lang.model.element.TypeElement");
            }
            Builder builder = AnnotationSpec.Companion.builder(ClassNames.get(asElement));
            for (ExecutableElement executableElement : annotation.getElementValues().keySet()) {
                CodeBlock.Builder builder2 = CodeBlock.Companion.builder();
                AnnotationValueVisitor visitor = new Visitor(builder2);
                String obj = executableElement.getSimpleName().toString();
                if (!Intrinsics.areEqual(obj, "value")) {
                    builder2.add("%L = ", obj);
                }
                Map elementValues = annotation.getElementValues();
                if (elementValues == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
                }
                Object obj2 = elementValues.get(executableElement);
                if (obj2 == null) {
                    Intrinsics.throwNpe();
                }
                ((AnnotationValue) obj2).accept(visitor, obj);
                builder.addMember(builder2.build());
            }
            return builder.build();
        }

        @JvmStatic
        public final Builder builder(ClassName type) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            return new Builder(type);
        }

        @JvmStatic
        public final Builder builder(Class<?> type) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            return builder(ClassNames.get(type));
        }

        @JvmStatic
        public final Builder builder(KClass<?> type) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            return builder(ClassNames.get(type));
        }
    }
}
