package com.bumptech.glide.repackaged.com.squareup.javapoet;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/* loaded from: classes.dex */
public final class ParameterizedTypeName extends TypeName {
    private final ParameterizedTypeName enclosingType;
    public final ClassName rawType;
    public final List<TypeName> typeArguments;

    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public /* bridge */ /* synthetic */ TypeName annotated(List list) {
        return annotated((List<AnnotationSpec>) list);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ParameterizedTypeName(ParameterizedTypeName parameterizedTypeName, ClassName className, List<TypeName> list) {
        this(parameterizedTypeName, className, list, new ArrayList());
    }

    private ParameterizedTypeName(ParameterizedTypeName parameterizedTypeName, ClassName className, List<TypeName> list, List<AnnotationSpec> list2) {
        super(list2);
        this.rawType = (ClassName) Util.checkNotNull(className, "rawType == null", new Object[0]);
        this.enclosingType = parameterizedTypeName;
        List<TypeName> immutableList = Util.immutableList(list);
        this.typeArguments = immutableList;
        Util.checkArgument((immutableList.isEmpty() && parameterizedTypeName == null) ? false : true, "no type arguments: %s", className);
        Iterator<TypeName> it = immutableList.iterator();
        while (it.hasNext()) {
            TypeName next = it.next();
            Util.checkArgument((next.isPrimitive() || next == VOID) ? false : true, "invalid type parameter: %s", next);
        }
    }

    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public ParameterizedTypeName annotated(List<AnnotationSpec> list) {
        return new ParameterizedTypeName(this.enclosingType, this.rawType, this.typeArguments, concatAnnotations(list));
    }

    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public TypeName withoutAnnotations() {
        return new ParameterizedTypeName(this.enclosingType, this.rawType, this.typeArguments, new ArrayList());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public CodeWriter emit(CodeWriter codeWriter) throws IOException {
        ParameterizedTypeName parameterizedTypeName = this.enclosingType;
        if (parameterizedTypeName != null) {
            parameterizedTypeName.emitAnnotations(codeWriter);
            this.enclosingType.emit(codeWriter);
            codeWriter.emit("." + this.rawType.simpleName());
        } else {
            this.rawType.emitAnnotations(codeWriter);
            this.rawType.emit(codeWriter);
        }
        if (!this.typeArguments.isEmpty()) {
            codeWriter.emitAndIndent("<");
            boolean z = true;
            for (TypeName typeName : this.typeArguments) {
                if (!z) {
                    codeWriter.emitAndIndent(", ");
                }
                typeName.emitAnnotations(codeWriter);
                typeName.emit(codeWriter);
                z = false;
            }
            codeWriter.emitAndIndent(">");
        }
        return codeWriter;
    }

    public ParameterizedTypeName nestedClass(String str, List<TypeName> list) {
        Util.checkNotNull(str, "name == null", new Object[0]);
        return new ParameterizedTypeName(this, this.rawType.nestedClass(str), list, new ArrayList());
    }

    public static ParameterizedTypeName get(ClassName className, TypeName... typeNameArr) {
        return new ParameterizedTypeName(null, className, Arrays.asList(typeNameArr));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ParameterizedTypeName get(ParameterizedType parameterizedType, Map<Type, TypeVariableName> map) {
        ClassName className = ClassName.get((Class<?>) parameterizedType.getRawType());
        ParameterizedType parameterizedType2 = (!(parameterizedType.getOwnerType() instanceof ParameterizedType) || Modifier.isStatic(((Class) parameterizedType.getRawType()).getModifiers())) ? null : (ParameterizedType) parameterizedType.getOwnerType();
        List<TypeName> list = TypeName.list(parameterizedType.getActualTypeArguments(), map);
        if (parameterizedType2 != null) {
            return get(parameterizedType2, map).nestedClass(className.simpleName(), list);
        }
        return new ParameterizedTypeName(null, className, list);
    }
}
