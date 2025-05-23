package com.bumptech.glide.repackaged.com.squareup.javapoet;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
/* loaded from: classes.dex */
public final class WildcardTypeName extends TypeName {
    public final List<TypeName> lowerBounds;
    public final List<TypeName> upperBounds;

    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public /* bridge */ /* synthetic */ TypeName annotated(List list) {
        return annotated((List<AnnotationSpec>) list);
    }

    private WildcardTypeName(List<TypeName> list, List<TypeName> list2) {
        this(list, list2, new ArrayList());
    }

    private WildcardTypeName(List<TypeName> list, List<TypeName> list2, List<AnnotationSpec> list3) {
        super(list3);
        List<TypeName> immutableList = Util.immutableList(list);
        this.upperBounds = immutableList;
        this.lowerBounds = Util.immutableList(list2);
        Util.checkArgument(immutableList.size() == 1, "unexpected extends bounds: %s", list);
        Iterator<TypeName> it = immutableList.iterator();
        while (it.hasNext()) {
            TypeName next = it.next();
            Util.checkArgument((next.isPrimitive() || next == VOID) ? false : true, "invalid upper bound: %s", next);
        }
        Iterator<TypeName> it2 = this.lowerBounds.iterator();
        while (it2.hasNext()) {
            TypeName next2 = it2.next();
            Util.checkArgument((next2.isPrimitive() || next2 == VOID) ? false : true, "invalid lower bound: %s", next2);
        }
    }

    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public WildcardTypeName annotated(List<AnnotationSpec> list) {
        return new WildcardTypeName(this.upperBounds, this.lowerBounds, concatAnnotations(list));
    }

    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public TypeName withoutAnnotations() {
        return new WildcardTypeName(this.upperBounds, this.lowerBounds);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public CodeWriter emit(CodeWriter codeWriter) throws IOException {
        return this.lowerBounds.size() == 1 ? codeWriter.emit("? super $T", this.lowerBounds.get(0)) : this.upperBounds.get(0).equals(TypeName.OBJECT) ? codeWriter.emit("?") : codeWriter.emit("? extends $T", this.upperBounds.get(0));
    }

    public static WildcardTypeName subtypeOf(TypeName typeName) {
        return new WildcardTypeName(Arrays.asList(typeName), Collections.emptyList());
    }

    public static WildcardTypeName subtypeOf(Type type) {
        return subtypeOf(TypeName.get(type));
    }

    public static WildcardTypeName supertypeOf(TypeName typeName) {
        return new WildcardTypeName(Arrays.asList(OBJECT), Arrays.asList(typeName));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static TypeName get(WildcardType wildcardType, Map<TypeParameterElement, TypeVariableName> map) {
        TypeMirror extendsBound = wildcardType.getExtendsBound();
        if (extendsBound == null) {
            TypeMirror superBound = wildcardType.getSuperBound();
            if (superBound == null) {
                return subtypeOf(Object.class);
            }
            return supertypeOf(TypeName.get(superBound, map));
        }
        return subtypeOf(TypeName.get(extendsBound, map));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static TypeName get(java.lang.reflect.WildcardType wildcardType, Map<Type, TypeVariableName> map) {
        return new WildcardTypeName(list(wildcardType.getUpperBounds(), map), list(wildcardType.getLowerBounds(), map));
    }
}
