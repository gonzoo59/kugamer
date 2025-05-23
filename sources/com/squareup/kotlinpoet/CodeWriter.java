package com.squareup.kotlinpoet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
/* compiled from: CodeWriter.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010%\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\t\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0000\u0018\u00002\u00020\u0001BG\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\b\u0012\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000b0\b¢\u0006\u0002\u0010\fJ\u000e\u0010\"\u001a\u00020\u00002\u0006\u0010#\u001a\u00020\u0006J\u001c\u0010$\u001a\u00020%2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'2\u0006\u0010)\u001a\u00020\u000eJ\u000e\u0010*\u001a\u00020\u00002\u0006\u0010+\u001a\u00020,J\u000e\u0010*\u001a\u00020\u00002\u0006\u0010#\u001a\u00020\u0006J+\u0010*\u001a\u00020\u00002\u0006\u0010-\u001a\u00020\u00062\u0016\u0010.\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010/\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u00100J\u000e\u00101\u001a\u00020%2\u0006\u0010+\u001a\u00020,J\b\u00102\u001a\u00020%H\u0002J\u000e\u00103\u001a\u00020%2\u0006\u00104\u001a\u00020,J\u0012\u00105\u001a\u00020%2\b\u00106\u001a\u0004\u0018\u00010\u0001H\u0002J$\u00107\u001a\u00020%2\f\u00108\u001a\b\u0012\u0004\u0012\u00020:092\u000e\b\u0002\u0010;\u001a\b\u0012\u0004\u0012\u00020:09J\u0018\u0010<\u001a\u00020\u000e2\u0006\u0010=\u001a\u00020\u00062\u0006\u0010>\u001a\u00020\u0006H\u0002J\u0014\u0010?\u001a\u00020%2\f\u0010@\u001a\b\u0012\u0004\u0012\u00020A0'J\u0014\u0010B\u001a\u00020%2\f\u0010@\u001a\b\u0012\u0004\u0012\u00020A0'J\u0006\u0010C\u001a\u00020\u0000J\u0010\u0010D\u001a\u00020%2\u0006\u0010E\u001a\u00020\u000bH\u0002J\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000b0\bJ\u0010\u0010\u0005\u001a\u00020\u00002\b\b\u0002\u0010F\u001a\u00020\u0012J\u000e\u0010G\u001a\u00020\u00062\u0006\u0010E\u001a\u00020\u000bJ\u0006\u0010H\u001a\u00020\u0000J\u0006\u0010I\u001a\u00020\u0000J\u000e\u0010J\u001a\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u0006J\u000e\u0010K\u001a\u00020\u00002\u0006\u0010L\u001a\u00020!J\u0012\u0010M\u001a\u0004\u0018\u00010\u000b2\u0006\u0010N\u001a\u00020\u0006H\u0002J\u0018\u0010O\u001a\u00020\u000b2\u0006\u0010P\u001a\u00020\u00122\u0006\u0010N\u001a\u00020\u0006H\u0002J\u0012\u0010Q\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000b0\bJ\u0010\u0010R\u001a\u00020\u00002\b\b\u0002\u0010F\u001a\u00020\u0012R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000b0\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006S"}, d2 = {"Lcom/squareup/kotlinpoet/CodeWriter;", "", "out", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "indent", "", "memberImports", "", "Lcom/squareup/kotlinpoet/Import;", "importedTypes", "Lcom/squareup/kotlinpoet/ClassName;", "(Ljava/lang/Appendable;Ljava/lang/String;Ljava/util/Map;Ljava/util/Map;)V", "comment", "", "importableTypes", "", "indentLevel", "", "kdoc", "memberImportClassNames", "", "Lcom/squareup/kotlinpoet/LineWrapper;", "packageName", "referencedNames", "statementLine", "getStatementLine", "()I", "setStatementLine", "(I)V", "trailingNewline", "typeSpecStack", "", "Lcom/squareup/kotlinpoet/TypeSpec;", "emit", "s", "emitAnnotations", "", "annotations", "", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "inline", "emitCode", "codeBlock", "Lcom/squareup/kotlinpoet/CodeBlock;", "format", "args", "", "(Ljava/lang/String;[Ljava/lang/Object;)Lcom/squareup/kotlinpoet/CodeWriter;", "emitComment", "emitIndentation", "emitKdoc", "kdocCodeBlock", "emitLiteral", "o", "emitModifiers", "modifiers", "", "Lcom/squareup/kotlinpoet/KModifier;", "implicitModifiers", "emitStaticImportMember", "canonical", "part", "emitTypeVariables", "typeVariables", "Lcom/squareup/kotlinpoet/TypeVariableName;", "emitWhereBlock", "emitWrappingSpace", "importableType", "className", "levels", "lookupName", "popPackage", "popType", "pushPackage", "pushType", "type", "resolve", "simpleName", "stackClassName", "stackDepth", "suggestedImports", "unindent", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class CodeWriter {
    private boolean comment;
    private final Map<String, ClassName> importableTypes;
    private final Map<String, ClassName> importedTypes;
    private final String indent;
    private int indentLevel;
    private boolean kdoc;
    private final Set<String> memberImportClassNames;
    private final Map<String, Import> memberImports;
    private final LineWrapper out;
    private String packageName;
    private final Set<String> referencedNames;
    private int statementLine;
    private boolean trailingNewline;
    private final List<TypeSpec> typeSpecStack;

    public CodeWriter(Appendable out, String indent, Map<String, Import> memberImports, Map<String, ClassName> importedTypes) {
        String str;
        Intrinsics.checkParameterIsNotNull(out, "out");
        Intrinsics.checkParameterIsNotNull(indent, "indent");
        Intrinsics.checkParameterIsNotNull(memberImports, "memberImports");
        Intrinsics.checkParameterIsNotNull(importedTypes, "importedTypes");
        this.indent = indent;
        this.memberImports = memberImports;
        this.importedTypes = importedTypes;
        this.out = new LineWrapper(out, indent, 100);
        str = CodeWriterKt.NO_PACKAGE;
        this.packageName = str;
        this.typeSpecStack = new ArrayList();
        this.memberImportClassNames = new LinkedHashSet();
        this.importableTypes = new LinkedHashMap();
        this.referencedNames = new LinkedHashSet();
        this.statementLine = -1;
        for (Map.Entry<String, Import> entry : memberImports.entrySet()) {
            String key = entry.getKey();
            Set<String> set = this.memberImportClassNames;
            int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) key, '.', 0, false, 6, (Object) null);
            if (key == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String substring = key.substring(0, lastIndexOf$default);
            Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            set.add(substring);
        }
    }

    public /* synthetic */ CodeWriter(Appendable appendable, String str, Map map, Map map2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(appendable, (i & 2) != 0 ? "  " : str, (i & 4) != 0 ? MapsKt.emptyMap() : map, (i & 8) != 0 ? MapsKt.emptyMap() : map2);
    }

    public final int getStatementLine() {
        return this.statementLine;
    }

    public final void setStatementLine(int i) {
        this.statementLine = i;
    }

    public final Map<String, ClassName> importedTypes() {
        return this.importedTypes;
    }

    public static /* bridge */ /* synthetic */ CodeWriter indent$default(CodeWriter codeWriter, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 1;
        }
        return codeWriter.indent(i);
    }

    public final CodeWriter indent(int i) {
        this.indentLevel += i;
        return this;
    }

    public static /* bridge */ /* synthetic */ CodeWriter unindent$default(CodeWriter codeWriter, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 1;
        }
        return codeWriter.unindent(i);
    }

    public final CodeWriter unindent(int i) {
        int i2 = this.indentLevel;
        if (!(i2 - i >= 0)) {
            throw new IllegalArgumentException(("cannot unindent " + i + " from " + this.indentLevel).toString());
        }
        this.indentLevel = i2 - i;
        return this;
    }

    public final CodeWriter pushPackage(String packageName) {
        String str;
        Intrinsics.checkParameterIsNotNull(packageName, "packageName");
        String str2 = this.packageName;
        str = CodeWriterKt.NO_PACKAGE;
        if (!(str2 == str)) {
            throw new IllegalArgumentException(("package already set: " + this.packageName).toString());
        }
        this.packageName = packageName;
        return this;
    }

    public final CodeWriter popPackage() {
        String str;
        String str2;
        String str3 = this.packageName;
        str = CodeWriterKt.NO_PACKAGE;
        if (str3 != str) {
            str2 = CodeWriterKt.NO_PACKAGE;
            this.packageName = str2;
            return this;
        }
        throw new IllegalArgumentException(("package already set: " + this.packageName).toString());
    }

    public final CodeWriter pushType(TypeSpec type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        this.typeSpecStack.add(type);
        return this;
    }

    public final CodeWriter popType() {
        List<TypeSpec> list = this.typeSpecStack;
        list.remove(list.size() - 1);
        return this;
    }

    public final void emitComment(CodeBlock codeBlock) {
        Intrinsics.checkParameterIsNotNull(codeBlock, "codeBlock");
        this.trailingNewline = true;
        this.comment = true;
        try {
            emitCode(codeBlock);
            emit("\n");
        } finally {
            this.comment = false;
        }
    }

    public final void emitKdoc(CodeBlock kdocCodeBlock) {
        Intrinsics.checkParameterIsNotNull(kdocCodeBlock, "kdocCodeBlock");
        if (kdocCodeBlock.isEmpty()) {
            return;
        }
        emit("/**\n");
        this.kdoc = true;
        try {
            emitCode(kdocCodeBlock);
            this.kdoc = false;
            emit(" */\n");
        } catch (Throwable th) {
            this.kdoc = false;
            throw th;
        }
    }

    public final void emitAnnotations(List<AnnotationSpec> annotations, boolean z) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        for (AnnotationSpec annotationSpec : annotations) {
            AnnotationSpec.emit$kotlinpoet$default(annotationSpec, this, z, false, 4, null);
            emit(z ? " " : "\n");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* bridge */ /* synthetic */ void emitModifiers$default(CodeWriter codeWriter, Set set, Set set2, int i, Object obj) {
        if ((i & 2) != 0) {
            set2 = SetsKt.emptySet();
        }
        codeWriter.emitModifiers(set, set2);
    }

    public final void emitModifiers(Set<? extends KModifier> modifiers, Set<? extends KModifier> implicitModifiers) {
        Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
        Intrinsics.checkParameterIsNotNull(implicitModifiers, "implicitModifiers");
        if (modifiers.isEmpty()) {
            return;
        }
        Iterator it = EnumSet.copyOf((Collection) modifiers).iterator();
        while (it.hasNext()) {
            KModifier kModifier = (KModifier) it.next();
            if (!implicitModifiers.contains(kModifier)) {
                emit(kModifier.getKeyword$kotlinpoet());
                emit(" ");
            }
        }
    }

    public final void emitTypeVariables(List<TypeVariableName> typeVariables) {
        Intrinsics.checkParameterIsNotNull(typeVariables, "typeVariables");
        if (typeVariables.isEmpty()) {
            return;
        }
        emit("<");
        int i = 0;
        for (TypeVariableName typeVariableName : typeVariables) {
            int i2 = i + 1;
            if (i > 0) {
                emit(", ");
            }
            if (typeVariableName.getVariance() != null) {
                emit("" + typeVariableName.getVariance().getKeyword$kotlinpoet() + ' ');
            }
            if (typeVariableName.getReified()) {
                emit("reified ");
            }
            emitCode("%L", typeVariableName.getName());
            if (typeVariableName.getBounds().size() == 1) {
                emitCode(" : %T", typeVariableName.getBounds().get(0));
            }
            i = i2;
        }
        emit(">");
    }

    public final void emitWhereBlock(List<TypeVariableName> typeVariables) {
        Intrinsics.checkParameterIsNotNull(typeVariables, "typeVariables");
        if (typeVariables.isEmpty()) {
            return;
        }
        boolean z = true;
        for (TypeVariableName typeVariableName : typeVariables) {
            if (typeVariableName.getBounds().size() > 1) {
                for (TypeName typeName : typeVariableName.getBounds()) {
                    emit(!z ? ", " : " where ");
                    emitCode("%L", typeVariableName.getName());
                    emitCode(" : %T", typeName);
                    z = false;
                }
            }
        }
    }

    public final CodeWriter emitCode(String s) {
        Intrinsics.checkParameterIsNotNull(s, "s");
        return emitCode(CodeBlock.Companion.of(s, new Object[0]));
    }

    public final CodeWriter emitCode(String format, Object... args) {
        Intrinsics.checkParameterIsNotNull(format, "format");
        Intrinsics.checkParameterIsNotNull(args, "args");
        return emitCode(CodeBlock.Companion.of(format, Arrays.copyOf(args, args.length)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01d4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0017 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00c0  */
    /* JADX WARN: Type inference failed for: r4v12, types: [com.squareup.kotlinpoet.TypeName] */
    /* JADX WARN: Type inference failed for: r4v14, types: [com.squareup.kotlinpoet.TypeName] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.squareup.kotlinpoet.CodeWriter emitCode(com.squareup.kotlinpoet.CodeBlock r13) {
        /*
            Method dump skipped, instructions count: 474
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.kotlinpoet.CodeWriter.emitCode(com.squareup.kotlinpoet.CodeBlock):com.squareup.kotlinpoet.CodeWriter");
    }

    public final CodeWriter emitWrappingSpace() {
        this.out.wrappingSpace(this.indentLevel + 2);
        return this;
    }

    private final boolean emitStaticImportMember(String str, String str2) {
        String extractMemberName;
        String extractMemberName2;
        if (str2 != null) {
            String substring = str2.substring(1);
            Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
            if (!(substring.length() == 0) && Character.isJavaIdentifierStart(substring.charAt(0))) {
                Map<String, Import> map = this.memberImports;
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(".");
                extractMemberName = CodeWriterKt.extractMemberName(substring);
                sb.append(extractMemberName);
                Import r9 = map.get(sb.toString());
                Map<String, Import> map2 = this.memberImports;
                Import r8 = map2.get(str + ".*");
                if (r9 == null && r8 == null) {
                    return false;
                }
                if ((r9 != null ? r9.getAlias$kotlinpoet() : null) != null) {
                    extractMemberName2 = CodeWriterKt.extractMemberName(substring);
                    emit(StringsKt.replaceFirst$default(substring, extractMemberName2, r9.getAlias$kotlinpoet(), false, 4, (Object) null));
                } else {
                    emit(substring);
                }
                return true;
            }
            return false;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    private final void emitLiteral(Object obj) {
        if (obj instanceof TypeSpec) {
            ((TypeSpec) obj).emit$kotlinpoet(this, null);
        } else if (obj instanceof AnnotationSpec) {
            ((AnnotationSpec) obj).emit$kotlinpoet(this, true, true);
        } else if (obj instanceof CodeBlock) {
            emitCode((CodeBlock) obj);
        } else {
            emit(String.valueOf(obj));
        }
    }

    public final String lookupName(ClassName className) {
        Intrinsics.checkParameterIsNotNull(className, "className");
        ClassName className2 = className;
        boolean z = false;
        while (className2 != null) {
            Import r2 = this.memberImports.get(className2.getCanonicalName());
            String alias$kotlinpoet = r2 != null ? r2.getAlias$kotlinpoet() : null;
            ClassName resolve = resolve(alias$kotlinpoet != null ? alias$kotlinpoet : className2.simpleName());
            boolean z2 = resolve != null;
            if (Intrinsics.areEqual(resolve, className2.asNonNullable())) {
                if (alias$kotlinpoet != null) {
                    return alias$kotlinpoet;
                }
                return CollectionsKt.joinToString$default(className.simpleNames().subList(className2.simpleNames().size() - 1, className.simpleNames().size()), ".", null, null, 0, null, null, 62, null);
            }
            className2 = className2.enclosingClassName();
            z = z2;
        }
        if (z) {
            return className.getCanonicalName();
        }
        if (Intrinsics.areEqual(this.packageName, className.packageName())) {
            this.referencedNames.add(className.topLevelClassName().simpleName());
            return CollectionsKt.joinToString$default(className.simpleNames(), ".", null, null, 0, null, null, 62, null);
        }
        if (!this.kdoc) {
            importableType(className);
        }
        return className.getCanonicalName();
    }

    private final void importableType(ClassName className) {
        String simpleName;
        if (className.packageName().length() == 0) {
            return;
        }
        ClassName className2 = className.topLevelClassName();
        Import r3 = this.memberImports.get(className.getCanonicalName());
        if (r3 == null || (simpleName = r3.getAlias$kotlinpoet()) == null) {
            simpleName = className2.simpleName();
        }
        ClassName put = this.importableTypes.put(simpleName, className2);
        if (put != null) {
            this.importableTypes.put(simpleName, put);
        }
    }

    private final ClassName resolve(String str) {
        IntProgression reversed = RangesKt.reversed(CollectionsKt.getIndices(this.typeSpecStack));
        int first = reversed.getFirst();
        int last = reversed.getLast();
        int step = reversed.getStep();
        if (step <= 0 ? first >= last : first <= last) {
            while (true) {
                for (TypeSpec typeSpec : this.typeSpecStack.get(first).getTypeSpecs()) {
                    if (Intrinsics.areEqual(typeSpec.getName(), str)) {
                        return stackClassName(first, str);
                    }
                }
                if (first == last) {
                    break;
                }
                first += step;
            }
        }
        if (this.typeSpecStack.size() > 0 && Intrinsics.areEqual(this.typeSpecStack.get(0).getName(), str)) {
            return new ClassName(this.packageName, str, new String[0]);
        }
        ClassName className = this.importedTypes.get(str);
        if (className != null) {
            return className;
        }
        return null;
    }

    private final ClassName stackClassName(int i, String str) {
        String str2 = this.packageName;
        String name = this.typeSpecStack.get(0).getName();
        if (name == null) {
            Intrinsics.throwNpe();
        }
        ClassName className = new ClassName(str2, name, new String[0]);
        int i2 = 1;
        if (1 <= i) {
            while (true) {
                String name2 = this.typeSpecStack.get(i2).getName();
                if (name2 == null) {
                    Intrinsics.throwNpe();
                }
                className = className.nestedClass(name2);
                if (i2 == i) {
                    break;
                }
                i2++;
            }
        }
        return className.nestedClass(str);
    }

    public final CodeWriter emit(String s) {
        Intrinsics.checkParameterIsNotNull(s, "s");
        boolean z = true;
        for (String str : StringsKt.split$default((CharSequence) s, new char[]{'\n'}, false, 0, 6, (Object) null)) {
            if (!z) {
                if ((this.kdoc || this.comment) && this.trailingNewline) {
                    emitIndentation();
                    this.out.append(this.kdoc ? " *" : "//");
                }
                this.out.append("\n");
                this.trailingNewline = true;
                int i = this.statementLine;
                if (i != -1) {
                    if (i == 0) {
                        indent(2);
                    }
                    this.statementLine++;
                }
            }
            if (!(str.length() == 0)) {
                if (this.trailingNewline) {
                    emitIndentation();
                    if (this.kdoc) {
                        this.out.append(" * ");
                    } else if (this.comment) {
                        this.out.append("// ");
                    }
                }
                this.out.append(str);
                this.trailingNewline = false;
            }
            z = false;
        }
        return this;
    }

    private final void emitIndentation() {
        int i = this.indentLevel;
        for (int i2 = 0; i2 < i; i2++) {
            this.out.append(this.indent);
        }
    }

    public final Map<String, ClassName> suggestedImports() {
        Map<String, ClassName> map = this.importableTypes;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<String, ClassName> entry : map.entrySet()) {
            if (!this.referencedNames.contains(entry.getKey())) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }
}
