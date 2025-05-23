package com.bumptech.glide.repackaged.com.squareup.javapoet;

import com.bumptech.glide.repackaged.com.squareup.javapoet.CodeBlock;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;
/* loaded from: classes.dex */
public final class JavaFile {
    private static final Appendable NULL_APPENDABLE = new Appendable() { // from class: com.bumptech.glide.repackaged.com.squareup.javapoet.JavaFile.1
        @Override // java.lang.Appendable
        public Appendable append(char c) {
            return this;
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence charSequence) {
            return this;
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence charSequence, int i, int i2) {
            return this;
        }
    };
    public final CodeBlock fileComment;
    private final String indent;
    public final String packageName;
    public final boolean skipJavaLangImports;
    private final Set<String> staticImports;
    public final TypeSpec typeSpec;

    private JavaFile(Builder builder) {
        this.fileComment = builder.fileComment.build();
        this.packageName = builder.packageName;
        this.typeSpec = builder.typeSpec;
        this.skipJavaLangImports = builder.skipJavaLangImports;
        this.staticImports = Util.immutableSet(builder.staticImports);
        this.indent = builder.indent;
    }

    public void writeTo(Appendable appendable) throws IOException {
        CodeWriter codeWriter = new CodeWriter(NULL_APPENDABLE, this.indent, this.staticImports);
        emit(codeWriter);
        emit(new CodeWriter(appendable, this.indent, codeWriter.suggestedImports(), this.staticImports));
    }

    public void writeTo(Filer filer) throws IOException {
        String str;
        if (this.packageName.isEmpty()) {
            str = this.typeSpec.name;
        } else {
            str = this.packageName + "." + this.typeSpec.name;
        }
        List<Element> list = this.typeSpec.originatingElements;
        JavaFileObject createSourceFile = filer.createSourceFile(str, (Element[]) list.toArray(new Element[list.size()]));
        try {
            Writer openWriter = createSourceFile.openWriter();
            writeTo(openWriter);
            if (openWriter != null) {
                openWriter.close();
            }
        } catch (Exception e) {
            try {
                createSourceFile.delete();
            } catch (Exception unused) {
            }
            throw e;
        }
    }

    private void emit(CodeWriter codeWriter) throws IOException {
        codeWriter.pushPackage(this.packageName);
        if (!this.fileComment.isEmpty()) {
            codeWriter.emitComment(this.fileComment);
        }
        if (!this.packageName.isEmpty()) {
            codeWriter.emit("package $L;\n", this.packageName);
            codeWriter.emit("\n");
        }
        if (!this.staticImports.isEmpty()) {
            Iterator<String> it = this.staticImports.iterator();
            while (it.hasNext()) {
                codeWriter.emit("import static $L;\n", (String) it.next());
            }
            codeWriter.emit("\n");
        }
        Iterator it2 = new TreeSet(codeWriter.importedTypes().values()).iterator();
        int i = 0;
        while (it2.hasNext()) {
            ClassName className = (ClassName) it2.next();
            if (!this.skipJavaLangImports || !className.packageName().equals("java.lang")) {
                codeWriter.emit("import $L;\n", className);
                i++;
            }
        }
        if (i > 0) {
            codeWriter.emit("\n");
        }
        this.typeSpec.emit(codeWriter, null, Collections.emptySet());
        codeWriter.popPackage();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return toString().equals(obj.toString());
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        try {
            StringBuilder sb = new StringBuilder();
            writeTo(sb);
            return sb.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    public static Builder builder(String str, TypeSpec typeSpec) {
        Util.checkNotNull(str, "packageName == null", new Object[0]);
        Util.checkNotNull(typeSpec, "typeSpec == null", new Object[0]);
        return new Builder(str, typeSpec);
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private final CodeBlock.Builder fileComment;
        private String indent;
        private final String packageName;
        private boolean skipJavaLangImports;
        private final Set<String> staticImports;
        private final TypeSpec typeSpec;

        private Builder(String str, TypeSpec typeSpec) {
            this.fileComment = CodeBlock.builder();
            this.staticImports = new TreeSet();
            this.indent = "  ";
            this.packageName = str;
            this.typeSpec = typeSpec;
        }

        public Builder skipJavaLangImports(boolean z) {
            this.skipJavaLangImports = z;
            return this;
        }

        public JavaFile build() {
            return new JavaFile(this);
        }
    }
}
