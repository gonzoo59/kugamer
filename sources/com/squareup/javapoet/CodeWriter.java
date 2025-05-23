package com.squareup.javapoet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class CodeWriter {
    private static final String NO_PACKAGE = new String();
    private boolean comment;
    private final Map<String, ClassName> importableTypes;
    private final Map<String, ClassName> importedTypes;
    private final String indent;
    private int indentLevel;
    private boolean javadoc;
    private final LineWrapper out;
    private String packageName;
    private final Set<String> referencedNames;
    int statementLine;
    private final Set<String> staticImportClassNames;
    private final Set<String> staticImports;
    private boolean trailingNewline;
    private final List<TypeSpec> typeSpecStack;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CodeWriter(Appendable appendable) {
        this(appendable, "  ", Collections.emptySet());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CodeWriter(Appendable appendable, String str, Set<String> set) {
        this(appendable, str, Collections.emptyMap(), set);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CodeWriter(Appendable appendable, String str, Map<String, ClassName> map, Set<String> set) {
        this.javadoc = false;
        this.comment = false;
        this.packageName = NO_PACKAGE;
        this.typeSpecStack = new ArrayList();
        this.importableTypes = new LinkedHashMap();
        this.referencedNames = new LinkedHashSet();
        this.statementLine = -1;
        this.out = new LineWrapper(appendable, str, 100);
        this.indent = (String) Util.checkNotNull(str, "indent == null", new Object[0]);
        this.importedTypes = (Map) Util.checkNotNull(map, "importedTypes == null", new Object[0]);
        this.staticImports = (Set) Util.checkNotNull(set, "staticImports == null", new Object[0]);
        this.staticImportClassNames = new LinkedHashSet();
        for (String str2 : set) {
            this.staticImportClassNames.add(str2.substring(0, str2.lastIndexOf(46)));
        }
    }

    public Map<String, ClassName> importedTypes() {
        return this.importedTypes;
    }

    public CodeWriter indent() {
        return indent(1);
    }

    public CodeWriter indent(int i) {
        this.indentLevel += i;
        return this;
    }

    public CodeWriter unindent() {
        return unindent(1);
    }

    public CodeWriter unindent(int i) {
        Util.checkArgument(this.indentLevel - i >= 0, "cannot unindent %s from %s", Integer.valueOf(i), Integer.valueOf(this.indentLevel));
        this.indentLevel -= i;
        return this;
    }

    public CodeWriter pushPackage(String str) {
        String str2 = this.packageName;
        Util.checkState(str2 == NO_PACKAGE, "package already set: %s", str2);
        this.packageName = (String) Util.checkNotNull(str, "packageName == null", new Object[0]);
        return this;
    }

    public CodeWriter popPackage() {
        String str = this.packageName;
        String str2 = NO_PACKAGE;
        Util.checkState(str != str2, "package already set: %s", str);
        this.packageName = str2;
        return this;
    }

    public CodeWriter pushType(TypeSpec typeSpec) {
        this.typeSpecStack.add(typeSpec);
        return this;
    }

    public CodeWriter popType() {
        List<TypeSpec> list = this.typeSpecStack;
        list.remove(list.size() - 1);
        return this;
    }

    public void emitComment(CodeBlock codeBlock) throws IOException {
        this.trailingNewline = true;
        this.comment = true;
        try {
            emit(codeBlock);
            emit("\n");
        } finally {
            this.comment = false;
        }
    }

    public void emitJavadoc(CodeBlock codeBlock) throws IOException {
        if (codeBlock.isEmpty()) {
            return;
        }
        emit("/**\n");
        this.javadoc = true;
        try {
            emit(codeBlock);
            this.javadoc = false;
            emit(" */\n");
        } catch (Throwable th) {
            this.javadoc = false;
            throw th;
        }
    }

    public void emitAnnotations(List<AnnotationSpec> list, boolean z) throws IOException {
        for (AnnotationSpec annotationSpec : list) {
            annotationSpec.emit(this, z);
            emit(z ? " " : "\n");
        }
    }

    public void emitModifiers(Set<Modifier> set, Set<Modifier> set2) throws IOException {
        if (set.isEmpty()) {
            return;
        }
        Iterator it = EnumSet.copyOf((Collection) set).iterator();
        while (it.hasNext()) {
            Modifier modifier = (Modifier) it.next();
            if (!set2.contains(modifier)) {
                emitAndIndent(modifier.name().toLowerCase(Locale.US));
                emitAndIndent(" ");
            }
        }
    }

    public void emitModifiers(Set<Modifier> set) throws IOException {
        emitModifiers(set, Collections.emptySet());
    }

    public void emitTypeVariables(List<TypeVariableName> list) throws IOException {
        if (list.isEmpty()) {
            return;
        }
        emit("<");
        boolean z = true;
        for (TypeVariableName typeVariableName : list) {
            if (!z) {
                emit(", ");
            }
            emit("$L", typeVariableName.name);
            boolean z2 = true;
            for (TypeName typeName : typeVariableName.bounds) {
                emit(z2 ? " extends $T" : " & $T", typeName);
                z2 = false;
            }
            z = false;
        }
        emit(">");
    }

    public CodeWriter emit(String str) throws IOException {
        return emitAndIndent(str);
    }

    public CodeWriter emit(String str, Object... objArr) throws IOException {
        return emit(CodeBlock.of(str, objArr));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v12, types: [com.squareup.javapoet.TypeName] */
    /* JADX WARN: Type inference failed for: r3v15, types: [com.squareup.javapoet.TypeName] */
    public CodeWriter emit(CodeBlock codeBlock) throws IOException {
        char c;
        int i;
        ListIterator<String> listIterator = codeBlock.formatParts.listIterator();
        ClassName className = null;
        int i2 = 0;
        while (listIterator.hasNext()) {
            String next = listIterator.next();
            next.hashCode();
            switch (next.hashCode()) {
                case 1152:
                    if (next.equals("$$")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1176:
                    if (next.equals("$<")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1178:
                    if (next.equals("$>")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1192:
                    if (next.equals("$L")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1194:
                    if (next.equals("$N")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1199:
                    if (next.equals("$S")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1200:
                    if (next.equals("$T")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1203:
                    if (next.equals("$W")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1207:
                    if (next.equals("$[")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1209:
                    if (next.equals("$]")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    emitAndIndent("$");
                    continue;
                case 1:
                    unindent();
                    continue;
                case 2:
                    indent();
                    continue;
                case 3:
                    i = i2 + 1;
                    emitLiteral(codeBlock.args.get(i2));
                    break;
                case 4:
                    i = i2 + 1;
                    emitAndIndent((String) codeBlock.args.get(i2));
                    break;
                case 5:
                    i = i2 + 1;
                    String str = (String) codeBlock.args.get(i2);
                    emitAndIndent(str != null ? Util.stringLiteralWithDoubleQuotes(str, this.indent) : "null");
                    break;
                case 6:
                    i = i2 + 1;
                    ?? r3 = (TypeName) codeBlock.args.get(i2);
                    boolean isAnnotated = r3.isAnnotated();
                    ClassName className2 = r3;
                    if (isAnnotated) {
                        r3.emitAnnotations(this);
                        className2 = r3.withoutAnnotations();
                    }
                    if ((className2 instanceof ClassName) && listIterator.hasNext() && !codeBlock.formatParts.get(listIterator.nextIndex()).startsWith("$")) {
                        ClassName className3 = className2;
                        if (this.staticImportClassNames.contains(className3.canonicalName)) {
                            Util.checkState(className == null, "pending type for static import?!", new Object[0]);
                            className = className3;
                            break;
                        }
                    }
                    className2.emit(this);
                    break;
                case 7:
                    this.out.wrappingSpace(this.indentLevel + 2);
                    continue;
                case '\b':
                    Util.checkState(this.statementLine == -1, "statement enter $[ followed by statement enter $[", new Object[0]);
                    this.statementLine = 0;
                    continue;
                case '\t':
                    Util.checkState(this.statementLine != -1, "statement exit $] has no matching statement enter $[", new Object[0]);
                    if (this.statementLine > 0) {
                        unindent(2);
                    }
                    this.statementLine = -1;
                    continue;
                default:
                    if (className != null) {
                        if (next.startsWith(".") && emitStaticImportMember(className.canonicalName, next)) {
                            className = null;
                        } else {
                            className.emit(this);
                            className = null;
                        }
                    }
                    emitAndIndent(next);
                    continue;
                    break;
            }
            i2 = i;
        }
        return this;
    }

    public CodeWriter emitWrappingSpace() throws IOException {
        this.out.wrappingSpace(this.indentLevel + 2);
        return this;
    }

    private static String extractMemberName(String str) {
        Util.checkArgument(Character.isJavaIdentifierStart(str.charAt(0)), "not an identifier: %s", str);
        for (int i = 1; i <= str.length(); i++) {
            if (!SourceVersion.isIdentifier(str.substring(0, i))) {
                return str.substring(0, i - 1);
            }
        }
        return str;
    }

    private boolean emitStaticImportMember(String str, String str2) throws IOException {
        String substring = str2.substring(1);
        if (!substring.isEmpty() && Character.isJavaIdentifierStart(substring.charAt(0))) {
            String str3 = str + "." + extractMemberName(substring);
            String str4 = str + ".*";
            if (this.staticImports.contains(str3) || this.staticImports.contains(str4)) {
                emitAndIndent(substring);
                return true;
            }
            return false;
        }
        return false;
    }

    private void emitLiteral(Object obj) throws IOException {
        if (obj instanceof TypeSpec) {
            ((TypeSpec) obj).emit(this, null, Collections.emptySet());
        } else if (obj instanceof AnnotationSpec) {
            ((AnnotationSpec) obj).emit(this, true);
        } else if (obj instanceof CodeBlock) {
            emit((CodeBlock) obj);
        } else {
            emitAndIndent(String.valueOf(obj));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String lookupName(ClassName className) {
        ClassName className2 = className;
        boolean z = false;
        while (className2 != null) {
            ClassName resolve = resolve(className2.simpleName());
            boolean z2 = resolve != null;
            if (!Objects.equals(resolve, className2)) {
                className2 = className2.enclosingClassName();
                z = z2;
            } else {
                return Util.join(".", className.simpleNames().subList(className2.simpleNames().size() - 1, className.simpleNames().size()));
            }
        }
        if (z) {
            return className.canonicalName;
        }
        if (Objects.equals(this.packageName, className.packageName())) {
            this.referencedNames.add(className.topLevelClassName().simpleName());
            return Util.join(".", className.simpleNames());
        }
        if (!this.javadoc) {
            importableType(className);
        }
        return className.canonicalName;
    }

    private void importableType(ClassName className) {
        ClassName className2;
        String simpleName;
        ClassName put;
        if (className.packageName().isEmpty() || (put = this.importableTypes.put((simpleName = (className2 = className.topLevelClassName()).simpleName()), className2)) == null) {
            return;
        }
        this.importableTypes.put(simpleName, put);
    }

    private ClassName resolve(String str) {
        for (int size = this.typeSpecStack.size() - 1; size >= 0; size--) {
            for (TypeSpec typeSpec : this.typeSpecStack.get(size).typeSpecs) {
                if (Objects.equals(typeSpec.name, str)) {
                    return stackClassName(size, str);
                }
            }
        }
        if (this.typeSpecStack.size() > 0 && Objects.equals(this.typeSpecStack.get(0).name, str)) {
            return ClassName.get(this.packageName, str, new String[0]);
        }
        ClassName className = this.importedTypes.get(str);
        if (className != null) {
            return className;
        }
        return null;
    }

    private ClassName stackClassName(int i, String str) {
        ClassName className = ClassName.get(this.packageName, this.typeSpecStack.get(0).name, new String[0]);
        for (int i2 = 1; i2 <= i; i2++) {
            className = className.nestedClass(this.typeSpecStack.get(i2).name);
        }
        return className.nestedClass(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CodeWriter emitAndIndent(String str) throws IOException {
        String[] split = str.split("\n", -1);
        int length = split.length;
        int i = 0;
        boolean z = true;
        while (i < length) {
            String str2 = split[i];
            if (!z) {
                if ((this.javadoc || this.comment) && this.trailingNewline) {
                    emitIndentation();
                    this.out.append(this.javadoc ? " *" : "//");
                }
                this.out.append("\n");
                this.trailingNewline = true;
                int i2 = this.statementLine;
                if (i2 != -1) {
                    if (i2 == 0) {
                        indent(2);
                    }
                    this.statementLine++;
                }
            }
            if (!str2.isEmpty()) {
                if (this.trailingNewline) {
                    emitIndentation();
                    if (this.javadoc) {
                        this.out.append(" * ");
                    } else if (this.comment) {
                        this.out.append("// ");
                    }
                }
                this.out.append(str2);
                this.trailingNewline = false;
            }
            i++;
            z = false;
        }
        return this;
    }

    private void emitIndentation() throws IOException {
        for (int i = 0; i < this.indentLevel; i++) {
            this.out.append(this.indent);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Map<String, ClassName> suggestedImports() {
        LinkedHashMap linkedHashMap = new LinkedHashMap(this.importableTypes);
        linkedHashMap.keySet().removeAll(this.referencedNames);
        return linkedHashMap;
    }
}
