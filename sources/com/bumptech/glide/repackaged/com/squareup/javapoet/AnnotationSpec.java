package com.bumptech.glide.repackaged.com.squareup.javapoet;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;
/* loaded from: classes.dex */
public final class AnnotationSpec {
    public final Map<String, List<CodeBlock>> members;
    public final TypeName type;

    /* synthetic */ AnnotationSpec(Builder builder, AnonymousClass1 anonymousClass1) {
        this(builder);
    }

    private AnnotationSpec(Builder builder) {
        this.type = builder.type;
        this.members = Util.immutableMultimap(builder.members);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void emit(CodeWriter codeWriter, boolean z) throws IOException {
        String str = z ? "" : "\n";
        String str2 = z ? ", " : ",\n";
        if (this.members.isEmpty()) {
            codeWriter.emit("@$T", this.type);
        } else if (this.members.size() == 1 && this.members.containsKey("value")) {
            codeWriter.emit("@$T(", this.type);
            emitAnnotationValues(codeWriter, str, str2, this.members.get("value"));
            codeWriter.emit(")");
        } else {
            codeWriter.emit("@$T(" + str, this.type);
            codeWriter.indent(2);
            Iterator<Map.Entry<String, List<CodeBlock>>> it = this.members.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, List<CodeBlock>> next = it.next();
                codeWriter.emit("$L = ", next.getKey());
                emitAnnotationValues(codeWriter, str, str2, next.getValue());
                if (it.hasNext()) {
                    codeWriter.emit(str2);
                }
            }
            codeWriter.unindent(2);
            codeWriter.emit(str + ")");
        }
    }

    private void emitAnnotationValues(CodeWriter codeWriter, String str, String str2, List<CodeBlock> list) throws IOException {
        boolean z = true;
        if (list.size() == 1) {
            codeWriter.indent(2);
            codeWriter.emit(list.get(0));
            codeWriter.unindent(2);
            return;
        }
        codeWriter.emit("{" + str);
        codeWriter.indent(2);
        for (CodeBlock codeBlock : list) {
            if (!z) {
                codeWriter.emit(str2);
            }
            codeWriter.emit(codeBlock);
            z = false;
        }
        codeWriter.unindent(2);
        codeWriter.emit(str + "}");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.bumptech.glide.repackaged.com.squareup.javapoet.AnnotationSpec$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass1 implements Comparator<Method> {
        @Override // java.util.Comparator
        public int compare(Method method, Method method2) {
            return method.getName().compareTo(method2.getName());
        }
    }

    public static AnnotationSpec get(AnnotationMirror annotationMirror) {
        Builder builder = builder(ClassName.get(annotationMirror.getAnnotationType().asElement()));
        Visitor visitor = new Visitor(builder);
        for (ExecutableElement executableElement : annotationMirror.getElementValues().keySet()) {
            ((AnnotationValue) annotationMirror.getElementValues().get(executableElement)).accept(visitor, executableElement.getSimpleName().toString());
        }
        return builder.build();
    }

    public static Builder builder(ClassName className) {
        Util.checkNotNull(className, "type == null", new Object[0]);
        return new Builder(className, null);
    }

    public static Builder builder(Class<?> cls) {
        return builder(ClassName.get(cls));
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
            new CodeWriter(stringWriter).emit("$L", this);
            return stringWriter.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private final Map<String, List<CodeBlock>> members;
        private final TypeName type;

        /* synthetic */ Builder(TypeName typeName, AnonymousClass1 anonymousClass1) {
            this(typeName);
        }

        private Builder(TypeName typeName) {
            this.members = new LinkedHashMap();
            this.type = typeName;
        }

        public Builder addMember(String str, String str2, Object... objArr) {
            return addMember(str, CodeBlock.of(str2, objArr));
        }

        public Builder addMember(String str, CodeBlock codeBlock) {
            List<CodeBlock> list = this.members.get(str);
            if (list == null) {
                list = new ArrayList<>();
                this.members.put(str, list);
            }
            list.add(codeBlock);
            return this;
        }

        Builder addMemberForValue(String str, Object obj) {
            Util.checkNotNull(str, "memberName == null", new Object[0]);
            Util.checkNotNull(obj, "value == null, constant non-null value expected for %s", str);
            if (obj instanceof Class) {
                return addMember(str, "$T.class", obj);
            }
            if (obj instanceof Enum) {
                return addMember(str, "$T.$L", obj.getClass(), ((Enum) obj).name());
            }
            if (obj instanceof String) {
                return addMember(str, "$S", obj);
            }
            if (obj instanceof Float) {
                return addMember(str, "$Lf", obj);
            }
            return obj instanceof Character ? addMember(str, "'$L'", Util.characterLiteralWithoutSingleQuotes(((Character) obj).charValue())) : addMember(str, "$L", obj);
        }

        public AnnotationSpec build() {
            return new AnnotationSpec(this, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class Visitor extends SimpleAnnotationValueVisitor7<Builder, String> {
        final Builder builder;

        public /* bridge */ /* synthetic */ Object visitArray(List list, Object obj) {
            return visitArray((List<? extends AnnotationValue>) list, (String) obj);
        }

        Visitor(Builder builder) {
            super(builder);
            this.builder = builder;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public Builder defaultAction(Object obj, String str) {
            return this.builder.addMemberForValue(str, obj);
        }

        public Builder visitAnnotation(AnnotationMirror annotationMirror, String str) {
            return this.builder.addMember(str, "$L", AnnotationSpec.get(annotationMirror));
        }

        public Builder visitEnumConstant(VariableElement variableElement, String str) {
            return this.builder.addMember(str, "$T.$L", variableElement.asType(), variableElement.getSimpleName());
        }

        public Builder visitType(TypeMirror typeMirror, String str) {
            return this.builder.addMember(str, "$T.class", typeMirror);
        }

        public Builder visitArray(List<? extends AnnotationValue> list, String str) {
            for (AnnotationValue annotationValue : list) {
                annotationValue.accept(this, str);
            }
            return this.builder;
        }
    }
}
