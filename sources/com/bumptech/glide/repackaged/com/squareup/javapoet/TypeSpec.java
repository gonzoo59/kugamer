package com.bumptech.glide.repackaged.com.squareup.javapoet;

import com.bumptech.glide.repackaged.com.squareup.javapoet.CodeBlock;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
/* loaded from: classes.dex */
public final class TypeSpec {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public final List<AnnotationSpec> annotations;
    public final CodeBlock anonymousTypeArguments;
    public final Map<String, TypeSpec> enumConstants;
    public final List<FieldSpec> fieldSpecs;
    public final CodeBlock initializerBlock;
    public final CodeBlock javadoc;
    public final Kind kind;
    public final List<MethodSpec> methodSpecs;
    public final Set<Modifier> modifiers;
    public final String name;
    public final List<Element> originatingElements;
    public final CodeBlock staticBlock;
    public final TypeName superclass;
    public final List<TypeName> superinterfaces;
    public final List<TypeSpec> typeSpecs;
    public final List<TypeVariableName> typeVariables;

    private TypeSpec(Builder builder) {
        this.kind = builder.kind;
        this.name = builder.name;
        this.anonymousTypeArguments = builder.anonymousTypeArguments;
        this.javadoc = builder.javadoc.build();
        this.annotations = Util.immutableList(builder.annotations);
        this.modifiers = Util.immutableSet(builder.modifiers);
        this.typeVariables = Util.immutableList(builder.typeVariables);
        this.superclass = builder.superclass;
        this.superinterfaces = Util.immutableList(builder.superinterfaces);
        this.enumConstants = Util.immutableMap(builder.enumConstants);
        this.fieldSpecs = Util.immutableList(builder.fieldSpecs);
        this.staticBlock = builder.staticBlock.build();
        this.initializerBlock = builder.initializerBlock.build();
        this.methodSpecs = Util.immutableList(builder.methodSpecs);
        this.typeSpecs = Util.immutableList(builder.typeSpecs);
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(builder.originatingElements);
        for (TypeSpec typeSpec : builder.typeSpecs) {
            arrayList.addAll(typeSpec.originatingElements);
        }
        this.originatingElements = Util.immutableList(arrayList);
    }

    private TypeSpec(TypeSpec typeSpec) {
        this.kind = typeSpec.kind;
        this.name = typeSpec.name;
        this.anonymousTypeArguments = null;
        this.javadoc = typeSpec.javadoc;
        this.annotations = Collections.emptyList();
        this.modifiers = Collections.emptySet();
        this.typeVariables = Collections.emptyList();
        this.superclass = null;
        this.superinterfaces = Collections.emptyList();
        this.enumConstants = Collections.emptyMap();
        this.fieldSpecs = Collections.emptyList();
        this.staticBlock = typeSpec.staticBlock;
        this.initializerBlock = typeSpec.initializerBlock;
        this.methodSpecs = Collections.emptyList();
        this.typeSpecs = Collections.emptyList();
        this.originatingElements = Collections.emptyList();
    }

    public static Builder classBuilder(String str) {
        return new Builder(Kind.CLASS, (String) Util.checkNotNull(str, "name == null", new Object[0]), null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void emit(CodeWriter codeWriter, String str, Set<Modifier> set) throws IOException {
        List<TypeName> singletonList;
        List<TypeName> list;
        int i = codeWriter.statementLine;
        codeWriter.statementLine = -1;
        boolean z = true;
        try {
            if (str != null) {
                codeWriter.emitJavadoc(this.javadoc);
                codeWriter.emitAnnotations(this.annotations, false);
                codeWriter.emit("$L", str);
                if (!this.anonymousTypeArguments.formatParts.isEmpty()) {
                    codeWriter.emit("(");
                    codeWriter.emit(this.anonymousTypeArguments);
                    codeWriter.emit(")");
                }
                if (this.fieldSpecs.isEmpty() && this.methodSpecs.isEmpty() && this.typeSpecs.isEmpty()) {
                    return;
                }
                codeWriter.emit(" {\n");
            } else if (this.anonymousTypeArguments != null) {
                codeWriter.emit("new $T(", !this.superinterfaces.isEmpty() ? this.superinterfaces.get(0) : this.superclass);
                codeWriter.emit(this.anonymousTypeArguments);
                codeWriter.emit(") {\n");
            } else {
                codeWriter.pushType(new TypeSpec(this));
                codeWriter.emitJavadoc(this.javadoc);
                codeWriter.emitAnnotations(this.annotations, false);
                codeWriter.emitModifiers(this.modifiers, Util.union(set, this.kind.asMemberModifiers));
                if (this.kind == Kind.ANNOTATION) {
                    codeWriter.emit("$L $L", "@interface", this.name);
                } else {
                    codeWriter.emit("$L $L", this.kind.name().toLowerCase(Locale.US), this.name);
                }
                codeWriter.emitTypeVariables(this.typeVariables);
                if (this.kind == Kind.INTERFACE) {
                    singletonList = this.superinterfaces;
                    list = Collections.emptyList();
                } else {
                    if (this.superclass.equals(ClassName.OBJECT)) {
                        singletonList = Collections.emptyList();
                    } else {
                        singletonList = Collections.singletonList(this.superclass);
                    }
                    list = this.superinterfaces;
                }
                if (!singletonList.isEmpty()) {
                    codeWriter.emit(" extends");
                    boolean z2 = true;
                    for (TypeName typeName : singletonList) {
                        if (!z2) {
                            codeWriter.emit(",");
                        }
                        codeWriter.emit(" $T", typeName);
                        z2 = false;
                    }
                }
                if (!list.isEmpty()) {
                    codeWriter.emit(" implements");
                    boolean z3 = true;
                    for (TypeName typeName2 : list) {
                        if (!z3) {
                            codeWriter.emit(",");
                        }
                        codeWriter.emit(" $T", typeName2);
                        z3 = false;
                    }
                }
                codeWriter.popType();
                codeWriter.emit(" {\n");
            }
            codeWriter.pushType(this);
            codeWriter.indent();
            Iterator<Map.Entry<String, TypeSpec>> it = this.enumConstants.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, TypeSpec> next = it.next();
                if (!z) {
                    codeWriter.emit("\n");
                }
                next.getValue().emit(codeWriter, next.getKey(), Collections.emptySet());
                if (it.hasNext()) {
                    codeWriter.emit(",\n");
                } else {
                    if (this.fieldSpecs.isEmpty() && this.methodSpecs.isEmpty() && this.typeSpecs.isEmpty()) {
                        codeWriter.emit("\n");
                    }
                    codeWriter.emit(";\n");
                }
                z = false;
            }
            for (FieldSpec fieldSpec : this.fieldSpecs) {
                if (fieldSpec.hasModifier(Modifier.STATIC)) {
                    if (!z) {
                        codeWriter.emit("\n");
                    }
                    fieldSpec.emit(codeWriter, this.kind.implicitFieldModifiers);
                    z = false;
                }
            }
            if (!this.staticBlock.isEmpty()) {
                if (!z) {
                    codeWriter.emit("\n");
                }
                codeWriter.emit(this.staticBlock);
                z = false;
            }
            for (FieldSpec fieldSpec2 : this.fieldSpecs) {
                if (!fieldSpec2.hasModifier(Modifier.STATIC)) {
                    if (!z) {
                        codeWriter.emit("\n");
                    }
                    fieldSpec2.emit(codeWriter, this.kind.implicitFieldModifiers);
                    z = false;
                }
            }
            if (!this.initializerBlock.isEmpty()) {
                if (!z) {
                    codeWriter.emit("\n");
                }
                codeWriter.emit(this.initializerBlock);
                z = false;
            }
            for (MethodSpec methodSpec : this.methodSpecs) {
                if (methodSpec.isConstructor()) {
                    if (!z) {
                        codeWriter.emit("\n");
                    }
                    methodSpec.emit(codeWriter, this.name, this.kind.implicitMethodModifiers);
                    z = false;
                }
            }
            for (MethodSpec methodSpec2 : this.methodSpecs) {
                if (!methodSpec2.isConstructor()) {
                    if (!z) {
                        codeWriter.emit("\n");
                    }
                    methodSpec2.emit(codeWriter, this.name, this.kind.implicitMethodModifiers);
                    z = false;
                }
            }
            for (TypeSpec typeSpec : this.typeSpecs) {
                if (!z) {
                    codeWriter.emit("\n");
                }
                typeSpec.emit(codeWriter, null, this.kind.implicitTypeModifiers);
                z = false;
            }
            codeWriter.unindent();
            codeWriter.popType();
            codeWriter.emit("}");
            if (str == null && this.anonymousTypeArguments == null) {
                codeWriter.emit("\n");
            }
        } finally {
            codeWriter.statementLine = i;
        }
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
            emit(new CodeWriter(stringWriter), null, Collections.emptySet());
            return stringWriter.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    /* loaded from: classes.dex */
    public enum Kind {
        CLASS(Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet()),
        INTERFACE(Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)), Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.ABSTRACT)), Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC)), Util.immutableSet(Arrays.asList(Modifier.STATIC))),
        ENUM(Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.singleton(Modifier.STATIC)),
        ANNOTATION(Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)), Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.ABSTRACT)), Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC)), Util.immutableSet(Arrays.asList(Modifier.STATIC)));
        
        private final Set<Modifier> asMemberModifiers;
        private final Set<Modifier> implicitFieldModifiers;
        private final Set<Modifier> implicitMethodModifiers;
        private final Set<Modifier> implicitTypeModifiers;

        Kind(Set set, Set set2, Set set3, Set set4) {
            this.implicitFieldModifiers = set;
            this.implicitMethodModifiers = set2;
            this.implicitTypeModifiers = set3;
            this.asMemberModifiers = set4;
        }
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private final List<AnnotationSpec> annotations;
        private final CodeBlock anonymousTypeArguments;
        private final Map<String, TypeSpec> enumConstants;
        private final List<FieldSpec> fieldSpecs;
        private final CodeBlock.Builder initializerBlock;
        private final CodeBlock.Builder javadoc;
        private final Kind kind;
        private final List<MethodSpec> methodSpecs;
        private final List<Modifier> modifiers;
        private final String name;
        private final List<Element> originatingElements;
        private final CodeBlock.Builder staticBlock;
        private TypeName superclass;
        private final List<TypeName> superinterfaces;
        private final List<TypeSpec> typeSpecs;
        private final List<TypeVariableName> typeVariables;

        private Builder(Kind kind, String str, CodeBlock codeBlock) {
            this.javadoc = CodeBlock.builder();
            this.annotations = new ArrayList();
            this.modifiers = new ArrayList();
            this.typeVariables = new ArrayList();
            this.superclass = ClassName.OBJECT;
            this.superinterfaces = new ArrayList();
            this.enumConstants = new LinkedHashMap();
            this.fieldSpecs = new ArrayList();
            this.staticBlock = CodeBlock.builder();
            this.initializerBlock = CodeBlock.builder();
            this.methodSpecs = new ArrayList();
            this.typeSpecs = new ArrayList();
            this.originatingElements = new ArrayList();
            Util.checkArgument(str == null || SourceVersion.isName(str), "not a valid name: %s", str);
            this.kind = kind;
            this.name = str;
            this.anonymousTypeArguments = codeBlock;
        }

        public Builder addJavadoc(String str, Object... objArr) {
            this.javadoc.add(str, objArr);
            return this;
        }

        public Builder addJavadoc(CodeBlock codeBlock) {
            this.javadoc.add(codeBlock);
            return this;
        }

        public Builder addAnnotation(AnnotationSpec annotationSpec) {
            this.annotations.add(annotationSpec);
            return this;
        }

        public Builder addModifiers(Modifier... modifierArr) {
            Util.checkState(this.anonymousTypeArguments == null, "forbidden on anonymous types.", new Object[0]);
            int length = modifierArr.length;
            for (int i = 0; i < length; i++) {
                Modifier modifier = modifierArr[i];
                Util.checkArgument(modifier != null, "modifiers contain null", new Object[0]);
                this.modifiers.add(modifier);
            }
            return this;
        }

        public Builder addTypeVariable(TypeVariableName typeVariableName) {
            Util.checkState(this.anonymousTypeArguments == null, "forbidden on anonymous types.", new Object[0]);
            this.typeVariables.add(typeVariableName);
            return this;
        }

        public Builder superclass(TypeName typeName) {
            boolean z = this.kind == Kind.CLASS;
            Util.checkState(z, "only classes have super classes, not " + this.kind, new Object[0]);
            boolean z2 = this.superclass == ClassName.OBJECT;
            Util.checkState(z2, "superclass already set to " + this.superclass, new Object[0]);
            Util.checkArgument(typeName.isPrimitive() ^ true, "superclass may not be a primitive", new Object[0]);
            this.superclass = typeName;
            return this;
        }

        public Builder addSuperinterface(TypeName typeName) {
            Util.checkArgument(typeName != null, "superinterface == null", new Object[0]);
            this.superinterfaces.add(typeName);
            return this;
        }

        public Builder addSuperinterface(Type type) {
            return addSuperinterface(TypeName.get(type));
        }

        public Builder addField(FieldSpec fieldSpec) {
            if (this.kind == Kind.INTERFACE || this.kind == Kind.ANNOTATION) {
                Util.requireExactlyOneOf(fieldSpec.modifiers, Modifier.PUBLIC, Modifier.PRIVATE);
                EnumSet of = EnumSet.of(Modifier.STATIC, Modifier.FINAL);
                Util.checkState(fieldSpec.modifiers.containsAll(of), "%s %s.%s requires modifiers %s", this.kind, this.name, fieldSpec.name, of);
            }
            this.fieldSpecs.add(fieldSpec);
            return this;
        }

        public Builder addField(TypeName typeName, String str, Modifier... modifierArr) {
            return addField(FieldSpec.builder(typeName, str, modifierArr).build());
        }

        public Builder addMethods(Iterable<MethodSpec> iterable) {
            Util.checkArgument(iterable != null, "methodSpecs == null", new Object[0]);
            for (MethodSpec methodSpec : iterable) {
                addMethod(methodSpec);
            }
            return this;
        }

        public Builder addMethod(MethodSpec methodSpec) {
            if (this.kind == Kind.INTERFACE) {
                Util.requireExactlyOneOf(methodSpec.modifiers, Modifier.ABSTRACT, Modifier.STATIC, Util.DEFAULT);
                Util.requireExactlyOneOf(methodSpec.modifiers, Modifier.PUBLIC, Modifier.PRIVATE);
            } else if (this.kind == Kind.ANNOTATION) {
                Util.checkState(methodSpec.modifiers.equals(this.kind.implicitMethodModifiers), "%s %s.%s requires modifiers %s", this.kind, this.name, methodSpec.name, this.kind.implicitMethodModifiers);
            }
            if (this.kind != Kind.ANNOTATION) {
                Util.checkState(methodSpec.defaultValue == null, "%s %s.%s cannot have a default value", this.kind, this.name, methodSpec.name);
            }
            if (this.kind != Kind.INTERFACE) {
                Util.checkState(!Util.hasDefaultModifier(methodSpec.modifiers), "%s %s.%s cannot be default", this.kind, this.name, methodSpec.name);
            }
            this.methodSpecs.add(methodSpec);
            return this;
        }

        public TypeSpec build() {
            boolean z = true;
            Util.checkArgument((this.kind == Kind.ENUM && this.enumConstants.isEmpty()) ? false : true, "at least one enum constant is required for %s", this.name);
            boolean z2 = this.modifiers.contains(Modifier.ABSTRACT) || this.kind != Kind.CLASS;
            for (MethodSpec methodSpec : this.methodSpecs) {
                Util.checkArgument(z2 || !methodSpec.hasModifier(Modifier.ABSTRACT), "non-abstract type %s cannot declare abstract method %s", this.name, methodSpec.name);
            }
            int size = (!this.superclass.equals(ClassName.OBJECT)) + this.superinterfaces.size();
            if (this.anonymousTypeArguments != null && size > 1) {
                z = false;
            }
            Util.checkArgument(z, "anonymous type has too many supertypes", new Object[0]);
            return new TypeSpec(this);
        }
    }
}
