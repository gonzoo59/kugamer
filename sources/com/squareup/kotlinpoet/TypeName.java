package com.squareup.kotlinpoet;

import com.baidu.kwgames.Constants;
import com.squareup.kotlinpoet.ParameterizedTypeName;
import com.squareup.kotlinpoet.TypeName;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.SimpleTypeVisitor7;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
/* compiled from: TypeName.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0004\b&\u0018\u0000 '2\u00020\u0001:\u0001'B\u001d\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u001f\u0010\u0013\u001a\u00020\u00002\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0014\"\u00020\u0006¢\u0006\u0002\u0010\u0015J\u0016\u0010\u0013\u001a\u00020\u00002\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\b\u0010\u0016\u001a\u00020\u0000H&J\b\u0010\u0017\u001a\u00020\u0000H&J\u0015\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H ¢\u0006\u0002\b\u001bJ\u0015\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001a\u001a\u00020\u0019H\u0000¢\u0006\u0002\b\u001eJ\u0015\u0010\u001f\u001a\u00020\u001d2\u0006\u0010\u001a\u001a\u00020\u0019H\u0000¢\u0006\u0002\b J\u0013\u0010!\u001a\u00020\u00032\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020\u000bH\u0016J\b\u0010&\u001a\u00020\u0000H&R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0010\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011¨\u0006("}, d2 = {"Lcom/squareup/kotlinpoet/TypeName;", "", "nullable", "", "annotations", "", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "(ZLjava/util/List;)V", "getAnnotations", "()Ljava/util/List;", "cachedString", "", "getCachedString", "()Ljava/lang/String;", "cachedString$delegate", "Lkotlin/Lazy;", "isAnnotated", "()Z", "getNullable", "annotated", "", "([Lcom/squareup/kotlinpoet/AnnotationSpec;)Lcom/squareup/kotlinpoet/TypeName;", "asNonNullable", "asNullable", "emit", "Lcom/squareup/kotlinpoet/CodeWriter;", "out", "emit$kotlinpoet", "emitAnnotations", "", "emitAnnotations$kotlinpoet", "emitNullable", "emitNullable$kotlinpoet", "equals", "other", "hashCode", "", "toString", "withoutAnnotations", "Companion", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public abstract class TypeName {
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(TypeName.class), "cachedString", "getCachedString()Ljava/lang/String;"))};
    public static final Companion Companion = new Companion(null);
    private final List<AnnotationSpec> annotations;
    private final Lazy cachedString$delegate;
    private final boolean nullable;

    private final String getCachedString() {
        Lazy lazy = this.cachedString$delegate;
        KProperty kProperty = $$delegatedProperties[0];
        return (String) lazy.getValue();
    }

    public abstract TypeName annotated(List<AnnotationSpec> list);

    public abstract TypeName asNonNullable();

    public abstract TypeName asNullable();

    public abstract CodeWriter emit$kotlinpoet(CodeWriter codeWriter);

    public abstract TypeName withoutAnnotations();

    public TypeName(boolean z, List<AnnotationSpec> annotations) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        this.nullable = z;
        this.annotations = UtilKt.toImmutableList(annotations);
        this.cachedString$delegate = LazyKt.lazy(new Function0<String>() { // from class: com.squareup.kotlinpoet.TypeName$cachedString$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                StringBuilder sb = new StringBuilder();
                CodeWriter codeWriter = new CodeWriter(sb, null, null, null, 14, null);
                TypeName.this.emitAnnotations$kotlinpoet(codeWriter);
                TypeName.this.emit$kotlinpoet(codeWriter);
                if (TypeName.this.getNullable()) {
                    sb.append("?");
                }
                String sb2 = sb.toString();
                Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
                return sb2;
            }
        });
    }

    public final boolean getNullable() {
        return this.nullable;
    }

    public final List<AnnotationSpec> getAnnotations() {
        return this.annotations;
    }

    public final TypeName annotated(AnnotationSpec... annotations) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        return annotated(ArraysKt.toList(annotations));
    }

    public final boolean isAnnotated() {
        return !this.annotations.isEmpty();
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
        return getCachedString();
    }

    public final void emitAnnotations$kotlinpoet(CodeWriter out) {
        Intrinsics.checkParameterIsNotNull(out, "out");
        for (AnnotationSpec annotationSpec : this.annotations) {
            AnnotationSpec.emit$kotlinpoet$default(annotationSpec, out, true, false, 4, null);
            out.emit(" ");
        }
    }

    public final void emitNullable$kotlinpoet(CodeWriter out) {
        Intrinsics.checkParameterIsNotNull(out, "out");
        if (this.nullable) {
            out.emit("?");
        }
    }

    /* compiled from: TypeName.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J)\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\bH\u0000¢\u0006\u0002\b\nJ)\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\t0\bH\u0000¢\u0006\u0002\b\n¨\u0006\u000f"}, d2 = {"Lcom/squareup/kotlinpoet/TypeName$Companion;", "", "()V", "get", "Lcom/squareup/kotlinpoet/TypeName;", "type", "Ljava/lang/reflect/Type;", "map", "", "Lcom/squareup/kotlinpoet/TypeVariableName;", "get$kotlinpoet", Constants.FLOAT_TAG_AI_MIRROW, "Ljavax/lang/model/type/TypeMirror;", "typeVariables", "Ljavax/lang/model/element/TypeParameterElement;", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Companion {

        @Metadata(bv = {1, 0, 2}, k = 3, mv = {1, 1, 7})
        /* loaded from: classes2.dex */
        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[TypeKind.values().length];
                $EnumSwitchMapping$0 = iArr;
                iArr[TypeKind.BOOLEAN.ordinal()] = 1;
                iArr[TypeKind.BYTE.ordinal()] = 2;
                iArr[TypeKind.SHORT.ordinal()] = 3;
                iArr[TypeKind.INT.ordinal()] = 4;
                iArr[TypeKind.LONG.ordinal()] = 5;
                iArr[TypeKind.CHAR.ordinal()] = 6;
                iArr[TypeKind.FLOAT.ordinal()] = 7;
                iArr[TypeKind.DOUBLE.ordinal()] = 8;
            }
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final TypeName get$kotlinpoet(TypeMirror mirror, final Map<TypeParameterElement, TypeVariableName> typeVariables) {
            Intrinsics.checkParameterIsNotNull(mirror, "mirror");
            Intrinsics.checkParameterIsNotNull(typeVariables, "typeVariables");
            Object accept = mirror.accept(new SimpleTypeVisitor7<TypeName, Void>() { // from class: com.squareup.kotlinpoet.TypeName$Companion$get$1
                public TypeName visitPrimitive(PrimitiveType t, Void r2) {
                    Intrinsics.checkParameterIsNotNull(t, "t");
                    TypeKind kind = t.getKind();
                    if (kind != null) {
                        switch (TypeName.Companion.WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
                            case 1:
                                return TypeNames.BOOLEAN;
                            case 2:
                                return TypeNames.BYTE;
                            case 3:
                                return TypeNames.SHORT;
                            case 4:
                                return TypeNames.INT;
                            case 5:
                                return TypeNames.LONG;
                            case 6:
                                return TypeNames.CHAR;
                            case 7:
                                return TypeNames.FLOAT;
                            case 8:
                                return TypeNames.DOUBLE;
                        }
                    }
                    throw new AssertionError();
                }

                public TypeName visitDeclared(DeclaredType t, Void r10) {
                    Intrinsics.checkParameterIsNotNull(t, "t");
                    TypeElement asElement = t.asElement();
                    if (asElement == null) {
                        throw new TypeCastException("null cannot be cast to non-null type javax.lang.model.element.TypeElement");
                    }
                    ClassName className = ClassNames.get(asElement);
                    TypeMirror enclosingType = t.getEnclosingType();
                    TypeName typeName = null;
                    if ((!Intrinsics.areEqual(enclosingType.getKind(), TypeKind.NONE)) && !t.asElement().getModifiers().contains(Modifier.STATIC)) {
                        typeName = (TypeName) enclosingType.accept((TypeVisitor) this, (Object) null);
                    }
                    if (t.getTypeArguments().isEmpty() && !(typeName instanceof ParameterizedTypeName)) {
                        return className;
                    }
                    ArrayList arrayList = new ArrayList();
                    for (TypeMirror typeArgument : t.getTypeArguments()) {
                        TypeName.Companion companion = TypeName.Companion;
                        Intrinsics.checkExpressionValueIsNotNull(typeArgument, "typeArgument");
                        arrayList.add(companion.get$kotlinpoet(typeArgument, typeVariables));
                    }
                    if (typeName instanceof ParameterizedTypeName) {
                        return ((ParameterizedTypeName) typeName).nestedClass(className.simpleName(), arrayList);
                    }
                    return new ParameterizedTypeName(null, className, arrayList, false, null, 24, null);
                }

                public TypeName visitError(ErrorType t, Void r3) {
                    Intrinsics.checkParameterIsNotNull(t, "t");
                    return visitDeclared((DeclaredType) t, r3);
                }

                public ParameterizedTypeName visitArray(ArrayType t, Void r6) {
                    Intrinsics.checkParameterIsNotNull(t, "t");
                    ParameterizedTypeName.Companion companion = ParameterizedTypeName.Companion;
                    ClassName className = TypeNames.ARRAY;
                    TypeName.Companion companion2 = TypeName.Companion;
                    TypeMirror componentType = t.getComponentType();
                    Intrinsics.checkExpressionValueIsNotNull(componentType, "t.componentType");
                    return companion.get(className, companion2.get$kotlinpoet(componentType, typeVariables));
                }

                public TypeName visitTypeVariable(TypeVariable t, Void r3) {
                    Intrinsics.checkParameterIsNotNull(t, "t");
                    return TypeVariableName.Companion.get$kotlinpoet(t, MapsKt.toMutableMap(typeVariables));
                }

                public TypeName visitWildcard(WildcardType t, Void r3) {
                    Intrinsics.checkParameterIsNotNull(t, "t");
                    return WildcardTypeName.Companion.get$kotlinpoet(t, typeVariables);
                }

                public TypeName visitNoType(NoType t, Void r4) {
                    Intrinsics.checkParameterIsNotNull(t, "t");
                    if (Intrinsics.areEqual(t.getKind(), TypeKind.VOID)) {
                        return TypeNames.UNIT;
                    }
                    Object visitUnknown = super.visitUnknown((TypeMirror) t, r4);
                    Intrinsics.checkExpressionValueIsNotNull(visitUnknown, "super.visitUnknown(t, p)");
                    return (TypeName) visitUnknown;
                }

                /* JADX INFO: Access modifiers changed from: protected */
                public TypeName defaultAction(TypeMirror typeMirror, Void r4) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unexpected type mirror: ");
                    if (typeMirror == null) {
                        Intrinsics.throwNpe();
                    }
                    sb.append(typeMirror);
                    throw new IllegalArgumentException(sb.toString());
                }
            }, (Object) null);
            Intrinsics.checkExpressionValueIsNotNull(accept, "mirror.accept(object : S…\n        }\n      }, null)");
            return (TypeName) accept;
        }

        public final TypeName get$kotlinpoet(Type type, Map<Type, TypeVariableName> map) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(map, "map");
            if (type instanceof Class) {
                if (type == Void.TYPE) {
                    return TypeNames.UNIT;
                }
                if (type == JvmClassMappingKt.getJavaPrimitiveType(Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                    return TypeNames.BOOLEAN;
                }
                if (type == JvmClassMappingKt.getJavaPrimitiveType(Reflection.getOrCreateKotlinClass(Byte.TYPE))) {
                    return TypeNames.BYTE;
                }
                if (type == JvmClassMappingKt.getJavaPrimitiveType(Reflection.getOrCreateKotlinClass(Short.TYPE))) {
                    return TypeNames.SHORT;
                }
                if (type == JvmClassMappingKt.getJavaPrimitiveType(Reflection.getOrCreateKotlinClass(Integer.TYPE))) {
                    return TypeNames.INT;
                }
                if (type == JvmClassMappingKt.getJavaPrimitiveType(Reflection.getOrCreateKotlinClass(Long.TYPE))) {
                    return TypeNames.LONG;
                }
                if (type == JvmClassMappingKt.getJavaPrimitiveType(Reflection.getOrCreateKotlinClass(Character.TYPE))) {
                    return TypeNames.CHAR;
                }
                if (type == JvmClassMappingKt.getJavaPrimitiveType(Reflection.getOrCreateKotlinClass(Float.TYPE))) {
                    return TypeNames.FLOAT;
                }
                if (type == JvmClassMappingKt.getJavaPrimitiveType(Reflection.getOrCreateKotlinClass(Double.TYPE))) {
                    return TypeNames.DOUBLE;
                }
                Class cls = (Class) type;
                if (cls.isArray()) {
                    ParameterizedTypeName.Companion companion = ParameterizedTypeName.Companion;
                    ClassName className = TypeNames.ARRAY;
                    Class<?> componentType = cls.getComponentType();
                    Intrinsics.checkExpressionValueIsNotNull(componentType, "type.componentType");
                    return companion.get(className, get$kotlinpoet(componentType, map));
                }
                return ClassNames.get(cls);
            } else if (type instanceof ParameterizedType) {
                return ParameterizedTypeName.Companion.get$kotlinpoet((ParameterizedType) type, map);
            } else {
                if (type instanceof java.lang.reflect.WildcardType) {
                    return WildcardTypeName.Companion.get$kotlinpoet((java.lang.reflect.WildcardType) type, map);
                }
                if (type instanceof java.lang.reflect.TypeVariable) {
                    return TypeVariableName.Companion.get$kotlinpoet((java.lang.reflect.TypeVariable) type, map);
                }
                if (!(type instanceof GenericArrayType)) {
                    throw new IllegalArgumentException("unexpected type: " + type);
                }
                ParameterizedTypeName.Companion companion2 = ParameterizedTypeName.Companion;
                ClassName className2 = TypeNames.ARRAY;
                Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
                Intrinsics.checkExpressionValueIsNotNull(genericComponentType, "type.genericComponentType");
                return companion2.get(className2, get$kotlinpoet(genericComponentType, map));
            }
        }
    }
}
