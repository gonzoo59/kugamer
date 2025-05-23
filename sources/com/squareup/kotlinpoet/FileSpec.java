package com.squareup.kotlinpoet;

import com.squareup.kotlinpoet.AnnotationSpec;
import com.squareup.kotlinpoet.CodeBlock;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KClass;
import kotlin.text.StringsKt;
/* compiled from: FileSpec.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 /2\u00020\u0001:\u0002./B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010!\u001a\u00020\"H\u0016J\u0006\u0010#\u001a\u00020\u0003J\u0006\u0010$\u001a\u00020%J\b\u0010&\u001a\u00020\u000fH\u0016J\u0010\u0010'\u001a\u00020\u001b2\u0006\u0010(\u001a\u00020)H\u0007J\u0010\u0010'\u001a\u00020\u001b2\u0006\u0010(\u001a\u00020*H\u0007J\u0014\u0010'\u001a\u00020\u001b2\n\u0010+\u001a\u00060,j\u0002`-H\u0007R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\tR\u0011\u0010\u0015\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017¨\u00060"}, d2 = {"Lcom/squareup/kotlinpoet/FileSpec;", "", "builder", "Lcom/squareup/kotlinpoet/FileSpec$Builder;", "(Lcom/squareup/kotlinpoet/FileSpec$Builder;)V", "annotations", "", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "getAnnotations", "()Ljava/util/List;", "comment", "Lcom/squareup/kotlinpoet/CodeBlock;", "getComment", "()Lcom/squareup/kotlinpoet/CodeBlock;", "indent", "", "memberImports", "", "Lcom/squareup/kotlinpoet/Import;", "members", "getMembers", "name", "getName", "()Ljava/lang/String;", "packageName", "getPackageName", "emit", "", "codeWriter", "Lcom/squareup/kotlinpoet/CodeWriter;", "equals", "", "other", "hashCode", "", "toBuilder", "toJavaFileObject", "Ljavax/tools/JavaFileObject;", "toString", "writeTo", "directory", "Ljava/io/File;", "Ljava/nio/file/Path;", "out", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "Builder", "Companion", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class FileSpec {
    public static final Companion Companion = new Companion(null);
    private final List<AnnotationSpec> annotations;
    private final CodeBlock comment;
    private final String indent;
    private final Map<String, Import> memberImports;
    private final List<Object> members;
    private final String name;
    private final String packageName;

    @JvmStatic
    public static final Builder builder(String packageName, String fileName) {
        Intrinsics.checkParameterIsNotNull(packageName, "packageName");
        Intrinsics.checkParameterIsNotNull(fileName, "fileName");
        return Companion.builder(packageName, fileName);
    }

    @JvmStatic
    public static final FileSpec get(String packageName, TypeSpec typeSpec) {
        Intrinsics.checkParameterIsNotNull(packageName, "packageName");
        Intrinsics.checkParameterIsNotNull(typeSpec, "typeSpec");
        return Companion.get(packageName, typeSpec);
    }

    private FileSpec(Builder builder) {
        this.annotations = UtilKt.toImmutableList(builder.getAnnotations$kotlinpoet());
        this.comment = builder.getComment$kotlinpoet().build();
        this.packageName = builder.getPackageName$kotlinpoet();
        this.name = builder.getName$kotlinpoet();
        this.members = CollectionsKt.toList(builder.getMembers$kotlinpoet());
        TreeSet<Import> memberImports$kotlinpoet = builder.getMemberImports$kotlinpoet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(memberImports$kotlinpoet, 10)), 16));
        for (Object obj : memberImports$kotlinpoet) {
            linkedHashMap.put(((Import) obj).getQualifiedName$kotlinpoet(), obj);
        }
        this.memberImports = linkedHashMap;
        this.indent = builder.getIndent$kotlinpoet();
    }

    public /* synthetic */ FileSpec(Builder builder, DefaultConstructorMarker defaultConstructorMarker) {
        this(builder);
    }

    public final List<AnnotationSpec> getAnnotations() {
        return this.annotations;
    }

    public final CodeBlock getComment() {
        return this.comment;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final String getName() {
        return this.name;
    }

    public final List<Object> getMembers() {
        return this.members;
    }

    public final void writeTo(Appendable out) throws IOException {
        Intrinsics.checkParameterIsNotNull(out, "out");
        CodeWriter codeWriter = new CodeWriter(NullAppendable.INSTANCE, this.indent, this.memberImports, null, 8, null);
        emit(codeWriter);
        emit(new CodeWriter(out, this.indent, this.memberImports, codeWriter.suggestedImports()));
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00dd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void writeTo(java.nio.file.Path r10) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 259
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.kotlinpoet.FileSpec.writeTo(java.nio.file.Path):void");
    }

    public final void writeTo(File directory) throws IOException {
        Intrinsics.checkParameterIsNotNull(directory, "directory");
        Path path = directory.toPath();
        Intrinsics.checkExpressionValueIsNotNull(path, "directory.toPath()");
        writeTo(path);
    }

    private final void emit(CodeWriter codeWriter) {
        if (this.comment.isNotEmpty()) {
            codeWriter.emitComment(this.comment);
        }
        int i = 0;
        if (!this.annotations.isEmpty()) {
            codeWriter.emitAnnotations(this.annotations, false);
            codeWriter.emit("\n");
        }
        codeWriter.pushPackage(this.packageName);
        String joinToString$default = CollectionsKt.joinToString$default(StringsKt.split$default((CharSequence) this.packageName, new char[]{'.'}, false, 0, 6, (Object) null), ".", null, null, 0, null, new Function1<String, String>() { // from class: com.squareup.kotlinpoet.FileSpec$emit$escapedPackageName$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(String it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return UtilKt.escapeIfKeyword(it);
            }
        }, 30, null);
        if (joinToString$default.length() > 0) {
            codeWriter.emitCode("package %L\n", joinToString$default);
            codeWriter.emit("\n");
        }
        Collection<ClassName> values = codeWriter.importedTypes().values();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(values, 10));
        for (ClassName className : values) {
            arrayList.add(className.getCanonicalName());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (!this.memberImports.keySet().contains((String) obj)) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = arrayList2;
        Map<String, Import> map = this.memberImports;
        ArrayList arrayList4 = new ArrayList(map.size());
        for (Map.Entry<String, Import> entry : map.entrySet()) {
            arrayList4.add(entry.getValue().toString());
        }
        List plus = CollectionsKt.plus((Collection) arrayList3, (Iterable) arrayList4);
        if (!plus.isEmpty()) {
            Iterator it = CollectionsKt.toSortedSet(plus).iterator();
            while (it.hasNext()) {
                codeWriter.emitCode("import %L", (String) it.next());
                codeWriter.emit("\n");
            }
            codeWriter.emit("\n");
        }
        for (Object obj2 : this.members) {
            int i2 = i + 1;
            if (i > 0) {
                codeWriter.emit("\n");
            }
            if (obj2 instanceof TypeSpec) {
                ((TypeSpec) obj2).emit$kotlinpoet(codeWriter, null);
            } else if (obj2 instanceof FunSpec) {
                ((FunSpec) obj2).emit$kotlinpoet(codeWriter, null, SetsKt.setOf(KModifier.PUBLIC));
            } else if (obj2 instanceof PropertySpec) {
                PropertySpec.emit$kotlinpoet$default((PropertySpec) obj2, codeWriter, SetsKt.setOf(KModifier.PUBLIC), false, false, 12, null);
            } else if (!(obj2 instanceof TypeAliasSpec)) {
                throw new AssertionError();
            } else {
                ((TypeAliasSpec) obj2).emit$kotlinpoet(codeWriter);
            }
            i = i2;
        }
        codeWriter.popPackage();
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
        writeTo(sb);
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    public final JavaFileObject toJavaFileObject() {
        String str;
        StringBuilder sb = new StringBuilder();
        if (this.packageName.length() == 0) {
            str = this.name;
        } else {
            str = StringsKt.replace$default(this.packageName, '.', '/', false, 4, (Object) null) + '/' + this.name;
        }
        sb.append(str);
        sb.append(".kt");
        final URI create = URI.create(sb.toString());
        final JavaFileObject.Kind kind = JavaFileObject.Kind.SOURCE;
        return new SimpleJavaFileObject(create, kind) { // from class: com.squareup.kotlinpoet.FileSpec$toJavaFileObject$1
            private final long lastModified = System.currentTimeMillis();

            public String getCharContent(boolean z) {
                return FileSpec.this.toString();
            }

            public InputStream openInputStream() {
                String charContent = getCharContent(true);
                Charset UTF_8 = StandardCharsets.UTF_8;
                Intrinsics.checkExpressionValueIsNotNull(UTF_8, "UTF_8");
                if (charContent != null) {
                    byte[] bytes = charContent.getBytes(UTF_8);
                    Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
                    return new ByteArrayInputStream(bytes);
                }
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            public long getLastModified() {
                return this.lastModified;
            }
        };
    }

    public final Builder toBuilder() {
        Builder builder = new Builder(this.packageName, this.name);
        builder.getAnnotations$kotlinpoet().addAll(this.annotations);
        builder.getComment$kotlinpoet().add(this.comment);
        builder.getMembers$kotlinpoet().addAll(this.members);
        builder.setIndent$kotlinpoet(this.indent);
        return builder;
    }

    /* compiled from: FileSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0010\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0016\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0003J\u001e\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\"\u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u0003J\u001a\u0010\u001e\u001a\u00020\u00002\n\u0010#\u001a\u0006\u0012\u0002\b\u00030$2\u0006\u0010!\u001a\u00020\u0003J\u001a\u0010\u001e\u001a\u00020\u00002\n\u0010#\u001a\u0006\u0012\u0002\b\u00030%2\u0006\u0010!\u001a\u00020\u0003J\u000e\u0010&\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\bJ\u000e\u0010&\u001a\u00020\u00002\u0006\u0010(\u001a\u00020 J\u0012\u0010&\u001a\u00020\u00002\n\u0010(\u001a\u0006\u0012\u0002\b\u00030$J\u0012\u0010&\u001a\u00020\u00002\n\u0010(\u001a\u0006\u0012\u0002\b\u00030%J'\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u00032\u0012\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010,\"\u00020\u0001¢\u0006\u0002\u0010-J\u000e\u0010.\u001a\u00020\u00002\u0006\u0010/\u001a\u000200J\u000e\u00101\u001a\u00020\u00002\u0006\u00102\u001a\u000203J'\u00104\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020 2\u0012\u00105\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030,\"\u00020\u0003¢\u0006\u0002\u00106J+\u00104\u001a\u00020\u00002\n\u0010#\u001a\u0006\u0012\u0002\b\u00030$2\u0012\u00105\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030,\"\u00020\u0003¢\u0006\u0002\u00107J\u0012\u00104\u001a\u00020\u00002\n\u00108\u001a\u0006\u0012\u0002\b\u000309J'\u00104\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u00105\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030,\"\u00020\u0003¢\u0006\u0002\u0010:J+\u00104\u001a\u00020\u00002\n\u0010#\u001a\u0006\u0012\u0002\b\u00030%2\u0012\u00105\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030,\"\u00020\u0003¢\u0006\u0002\u0010;J\u000e\u0010<\u001a\u00020\u00002\u0006\u0010=\u001a\u00020>J\u000e\u0010?\u001a\u00020\u00002\u0006\u0010@\u001a\u00020AJ\u0006\u0010B\u001a\u00020CJ\u000e\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0003R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0003X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R$\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u00160\u0015j\b\u0012\u0004\u0012\u00020\u0016`\u0017X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0011R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0011¨\u0006D"}, d2 = {"Lcom/squareup/kotlinpoet/FileSpec$Builder;", "", "packageName", "", "name", "(Ljava/lang/String;Ljava/lang/String;)V", "annotations", "", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "getAnnotations$kotlinpoet", "()Ljava/util/List;", "comment", "Lcom/squareup/kotlinpoet/CodeBlock$Builder;", "getComment$kotlinpoet", "()Lcom/squareup/kotlinpoet/CodeBlock$Builder;", "indent", "getIndent$kotlinpoet", "()Ljava/lang/String;", "setIndent$kotlinpoet", "(Ljava/lang/String;)V", "memberImports", "Ljava/util/TreeSet;", "Lcom/squareup/kotlinpoet/Import;", "Lkotlin/collections/TreeSet;", "getMemberImports$kotlinpoet", "()Ljava/util/TreeSet;", "members", "getMembers$kotlinpoet", "getName$kotlinpoet", "getPackageName$kotlinpoet", "addAliasedImport", "className", "Lcom/squareup/kotlinpoet/ClassName;", "as", "memberName", "class", "Ljava/lang/Class;", "Lkotlin/reflect/KClass;", "addAnnotation", "annotationSpec", "annotation", "addComment", "format", "args", "", "(Ljava/lang/String;[Ljava/lang/Object;)Lcom/squareup/kotlinpoet/FileSpec$Builder;", "addFunction", "funSpec", "Lcom/squareup/kotlinpoet/FunSpec;", "addProperty", "propertySpec", "Lcom/squareup/kotlinpoet/PropertySpec;", "addStaticImport", "names", "(Lcom/squareup/kotlinpoet/ClassName;[Ljava/lang/String;)Lcom/squareup/kotlinpoet/FileSpec$Builder;", "(Ljava/lang/Class;[Ljava/lang/String;)Lcom/squareup/kotlinpoet/FileSpec$Builder;", "constant", "", "(Ljava/lang/String;[Ljava/lang/String;)Lcom/squareup/kotlinpoet/FileSpec$Builder;", "(Lkotlin/reflect/KClass;[Ljava/lang/String;)Lcom/squareup/kotlinpoet/FileSpec$Builder;", "addType", "typeSpec", "Lcom/squareup/kotlinpoet/TypeSpec;", "addTypeAlias", "typeAliasSpec", "Lcom/squareup/kotlinpoet/TypeAliasSpec;", "build", "Lcom/squareup/kotlinpoet/FileSpec;", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Builder {
        private final List<AnnotationSpec> annotations;
        private final CodeBlock.Builder comment;
        private String indent;
        private final TreeSet<Import> memberImports;
        private final List<Object> members;
        private final String name;
        private final String packageName;

        @Metadata(bv = {1, 0, 2}, k = 3, mv = {1, 1, 7})
        /* loaded from: classes2.dex */
        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[AnnotationSpec.UseSiteTarget.values().length];
                $EnumSwitchMapping$0 = iArr;
                iArr[AnnotationSpec.UseSiteTarget.FILE.ordinal()] = 1;
            }
        }

        public Builder(String packageName, String name) {
            Intrinsics.checkParameterIsNotNull(packageName, "packageName");
            Intrinsics.checkParameterIsNotNull(name, "name");
            this.packageName = packageName;
            this.name = name;
            this.annotations = new ArrayList();
            this.comment = CodeBlock.Companion.builder();
            this.memberImports = SetsKt.sortedSetOf(new Import[0]);
            this.indent = "  ";
            this.members = new ArrayList();
            if (UtilKt.isName(name)) {
                return;
            }
            throw new IllegalArgumentException(("not a valid file name: " + name).toString());
        }

        public final String getPackageName$kotlinpoet() {
            return this.packageName;
        }

        public final String getName$kotlinpoet() {
            return this.name;
        }

        public final List<AnnotationSpec> getAnnotations$kotlinpoet() {
            return this.annotations;
        }

        public final CodeBlock.Builder getComment$kotlinpoet() {
            return this.comment;
        }

        public final TreeSet<Import> getMemberImports$kotlinpoet() {
            return this.memberImports;
        }

        public final String getIndent$kotlinpoet() {
            return this.indent;
        }

        public final void setIndent$kotlinpoet(String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            this.indent = str;
        }

        public final List<Object> getMembers$kotlinpoet() {
            return this.members;
        }

        public final Builder addAnnotation(AnnotationSpec annotationSpec) {
            Intrinsics.checkParameterIsNotNull(annotationSpec, "annotationSpec");
            AnnotationSpec.UseSiteTarget useSiteTarget = annotationSpec.getUseSiteTarget();
            if (useSiteTarget != null) {
                if (WhenMappings.$EnumSwitchMapping$0[useSiteTarget.ordinal()] == 1) {
                    this.annotations.add(annotationSpec);
                } else {
                    throw new IllegalStateException(("Use-site target " + annotationSpec.getUseSiteTarget() + " not supported for file annotations.").toString());
                }
            } else {
                this.annotations.add(annotationSpec.toBuilder().useSiteTarget(AnnotationSpec.UseSiteTarget.FILE).build());
            }
            return this;
        }

        public final Builder addAnnotation(ClassName annotation) {
            Intrinsics.checkParameterIsNotNull(annotation, "annotation");
            return addAnnotation(AnnotationSpec.Companion.builder(annotation).build());
        }

        public final Builder addAnnotation(Class<?> annotation) {
            Intrinsics.checkParameterIsNotNull(annotation, "annotation");
            return addAnnotation(ClassNames.get(annotation));
        }

        public final Builder addAnnotation(KClass<?> annotation) {
            Intrinsics.checkParameterIsNotNull(annotation, "annotation");
            return addAnnotation(ClassNames.get(annotation));
        }

        public final Builder addComment(String format, Object... args) {
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            this.comment.add(format, Arrays.copyOf(args, args.length));
            return this;
        }

        public final Builder addType(TypeSpec typeSpec) {
            Intrinsics.checkParameterIsNotNull(typeSpec, "typeSpec");
            this.members.add(typeSpec);
            return this;
        }

        public final Builder addFunction(FunSpec funSpec) {
            Intrinsics.checkParameterIsNotNull(funSpec, "funSpec");
            if (!((funSpec.isConstructor() || funSpec.isAccessor()) ? false : true)) {
                throw new IllegalArgumentException(("cannot add " + funSpec.getName() + " to file " + this.name).toString());
            }
            this.members.add(funSpec);
            return this;
        }

        public final Builder addProperty(PropertySpec propertySpec) {
            Intrinsics.checkParameterIsNotNull(propertySpec, "propertySpec");
            this.members.add(propertySpec);
            return this;
        }

        public final Builder addTypeAlias(TypeAliasSpec typeAliasSpec) {
            Intrinsics.checkParameterIsNotNull(typeAliasSpec, "typeAliasSpec");
            this.members.add(typeAliasSpec);
            return this;
        }

        public final Builder addStaticImport(Enum<?> constant) {
            Intrinsics.checkParameterIsNotNull(constant, "constant");
            return addStaticImport(ClassNames.get(constant.getDeclaringClass()), constant.name());
        }

        public final Builder addStaticImport(Class<?> cls, String... names) {
            Intrinsics.checkParameterIsNotNull(cls, "class");
            Intrinsics.checkParameterIsNotNull(names, "names");
            return addStaticImport(ClassNames.get(cls), (String[]) Arrays.copyOf(names, names.length));
        }

        public final Builder addStaticImport(KClass<?> kClass, String... names) {
            Intrinsics.checkParameterIsNotNull(kClass, "class");
            Intrinsics.checkParameterIsNotNull(names, "names");
            return addStaticImport(ClassNames.get(kClass), (String[]) Arrays.copyOf(names, names.length));
        }

        public final Builder addStaticImport(ClassName className, String... names) {
            Intrinsics.checkParameterIsNotNull(className, "className");
            Intrinsics.checkParameterIsNotNull(names, "names");
            if (!(!(names.length == 0))) {
                throw new IllegalStateException("names array is empty".toString());
            }
            for (String str : names) {
                this.memberImports.add(new Import(className.getCanonicalName() + "." + str, null, 2, null));
            }
            return this;
        }

        public final Builder addStaticImport(String packageName, String... names) {
            Intrinsics.checkParameterIsNotNull(packageName, "packageName");
            Intrinsics.checkParameterIsNotNull(names, "names");
            if (!(!(names.length == 0))) {
                throw new IllegalStateException("names array is empty".toString());
            }
            for (String str : names) {
                this.memberImports.add(new Import(packageName + "." + str, null, 2, null));
            }
            return this;
        }

        public final Builder addAliasedImport(Class<?> cls, String as) {
            Intrinsics.checkParameterIsNotNull(cls, "class");
            Intrinsics.checkParameterIsNotNull(as, "as");
            return addAliasedImport(ClassNames.get(cls), as);
        }

        public final Builder addAliasedImport(KClass<?> kClass, String as) {
            Intrinsics.checkParameterIsNotNull(kClass, "class");
            Intrinsics.checkParameterIsNotNull(as, "as");
            return addAliasedImport(ClassNames.get(kClass), as);
        }

        public final Builder addAliasedImport(ClassName className, String as) {
            Intrinsics.checkParameterIsNotNull(className, "className");
            Intrinsics.checkParameterIsNotNull(as, "as");
            this.memberImports.add(new Import(className.getCanonicalName(), as));
            return this;
        }

        public final Builder addAliasedImport(ClassName className, String memberName, String as) {
            Intrinsics.checkParameterIsNotNull(className, "className");
            Intrinsics.checkParameterIsNotNull(memberName, "memberName");
            Intrinsics.checkParameterIsNotNull(as, "as");
            this.memberImports.add(new Import("" + className.getCanonicalName() + '.' + memberName, as));
            return this;
        }

        public final Builder indent(String indent) {
            Intrinsics.checkParameterIsNotNull(indent, "indent");
            this.indent = indent;
            return this;
        }

        public final FileSpec build() {
            return new FileSpec(this, null);
        }
    }

    /* compiled from: FileSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0007J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0007¨\u0006\f"}, d2 = {"Lcom/squareup/kotlinpoet/FileSpec$Companion;", "", "()V", "builder", "Lcom/squareup/kotlinpoet/FileSpec$Builder;", "packageName", "", "fileName", "get", "Lcom/squareup/kotlinpoet/FileSpec;", "typeSpec", "Lcom/squareup/kotlinpoet/TypeSpec;", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final FileSpec get(String packageName, TypeSpec typeSpec) {
            Intrinsics.checkParameterIsNotNull(packageName, "packageName");
            Intrinsics.checkParameterIsNotNull(typeSpec, "typeSpec");
            String name = typeSpec.getName();
            if (name != null) {
                return builder(packageName, name).addType(typeSpec).build();
            }
            throw new IllegalArgumentException("file name required but type has no name");
        }

        @JvmStatic
        public final Builder builder(String packageName, String fileName) {
            Intrinsics.checkParameterIsNotNull(packageName, "packageName");
            Intrinsics.checkParameterIsNotNull(fileName, "fileName");
            return new Builder(packageName, fileName);
        }
    }
}
