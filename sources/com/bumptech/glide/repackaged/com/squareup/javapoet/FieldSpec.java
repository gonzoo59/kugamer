package com.bumptech.glide.repackaged.com.squareup.javapoet;

import com.bumptech.glide.repackaged.com.squareup.javapoet.CodeBlock;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
/* loaded from: classes.dex */
public final class FieldSpec {
    public final List<AnnotationSpec> annotations;
    public final CodeBlock initializer;
    public final CodeBlock javadoc;
    public final Set<Modifier> modifiers;
    public final String name;
    public final TypeName type;

    private FieldSpec(Builder builder) {
        CodeBlock codeBlock;
        this.type = (TypeName) Util.checkNotNull(builder.type, "type == null", new Object[0]);
        this.name = (String) Util.checkNotNull(builder.name, "name == null", new Object[0]);
        this.javadoc = builder.javadoc.build();
        this.annotations = Util.immutableList(builder.annotations);
        this.modifiers = Util.immutableSet(builder.modifiers);
        if (builder.initializer == null) {
            codeBlock = CodeBlock.builder().build();
        } else {
            codeBlock = builder.initializer;
        }
        this.initializer = codeBlock;
    }

    public boolean hasModifier(Modifier modifier) {
        return this.modifiers.contains(modifier);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void emit(CodeWriter codeWriter, Set<Modifier> set) throws IOException {
        codeWriter.emitJavadoc(this.javadoc);
        codeWriter.emitAnnotations(this.annotations, false);
        codeWriter.emitModifiers(this.modifiers, set);
        codeWriter.emit("$T $L", this.type, this.name);
        if (!this.initializer.isEmpty()) {
            codeWriter.emit(" = ");
            codeWriter.emit(this.initializer);
        }
        codeWriter.emit(";\n");
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
        StringWriter stringWriter = new StringWriter();
        try {
            emit(new CodeWriter(stringWriter), Collections.emptySet());
            return stringWriter.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    public static Builder builder(TypeName typeName, String str, Modifier... modifierArr) {
        Util.checkNotNull(typeName, "type == null", new Object[0]);
        Util.checkArgument(SourceVersion.isName(str), "not a valid name: %s", str);
        return new Builder(typeName, str).addModifiers(modifierArr);
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private final List<AnnotationSpec> annotations;
        private CodeBlock initializer;
        private final CodeBlock.Builder javadoc;
        private final List<Modifier> modifiers;
        private final String name;
        private final TypeName type;

        private Builder(TypeName typeName, String str) {
            this.javadoc = CodeBlock.builder();
            this.annotations = new ArrayList();
            this.modifiers = new ArrayList();
            this.initializer = null;
            this.type = typeName;
            this.name = str;
        }

        public Builder addModifiers(Modifier... modifierArr) {
            Collections.addAll(this.modifiers, modifierArr);
            return this;
        }

        public FieldSpec build() {
            return new FieldSpec(this);
        }
    }
}
