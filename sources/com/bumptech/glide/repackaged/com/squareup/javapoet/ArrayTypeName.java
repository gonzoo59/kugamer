package com.bumptech.glide.repackaged.com.squareup.javapoet;

import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.ArrayType;
/* loaded from: classes.dex */
public final class ArrayTypeName extends TypeName {
    public final TypeName componentType;

    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public /* bridge */ /* synthetic */ TypeName annotated(List list) {
        return annotated((List<AnnotationSpec>) list);
    }

    private ArrayTypeName(TypeName typeName) {
        this(typeName, new ArrayList());
    }

    private ArrayTypeName(TypeName typeName, List<AnnotationSpec> list) {
        super(list);
        this.componentType = (TypeName) Util.checkNotNull(typeName, "rawType == null", new Object[0]);
    }

    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public ArrayTypeName annotated(List<AnnotationSpec> list) {
        return new ArrayTypeName(this.componentType, concatAnnotations(list));
    }

    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public TypeName withoutAnnotations() {
        return new ArrayTypeName(this.componentType);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public CodeWriter emit(CodeWriter codeWriter) throws IOException {
        return codeWriter.emit("$T[]", this.componentType);
    }

    public static ArrayTypeName of(TypeName typeName) {
        return new ArrayTypeName(typeName);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ArrayTypeName get(ArrayType arrayType, Map<TypeParameterElement, TypeVariableName> map) {
        return new ArrayTypeName(get(arrayType.getComponentType(), map));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ArrayTypeName get(GenericArrayType genericArrayType, Map<Type, TypeVariableName> map) {
        return of(get(genericArrayType.getGenericComponentType(), map));
    }
}
