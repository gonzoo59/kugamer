package com.squareup.kotlinpoet;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
/* compiled from: ParameterizedTypeName.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bBA\b\u0000\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006¢\u0006\u0002\u0010\u000bJ\u0016\u0010\u0010\u001a\u00020\u00002\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006H\u0016J\b\u0010\u0011\u001a\u00020\u0000H\u0016J\b\u0010\u0012\u001a\u00020\u0000H\u0016J\u0015\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0010¢\u0006\u0002\b\u0016J\u001c\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006J\b\u0010\u001a\u001a\u00020\u0000H\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001c"}, d2 = {"Lcom/squareup/kotlinpoet/ParameterizedTypeName;", "Lcom/squareup/kotlinpoet/TypeName;", "enclosingType", "rawType", "Lcom/squareup/kotlinpoet/ClassName;", "typeArguments", "", "nullable", "", "annotations", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "(Lcom/squareup/kotlinpoet/ParameterizedTypeName;Lcom/squareup/kotlinpoet/ClassName;Ljava/util/List;ZLjava/util/List;)V", "getRawType", "()Lcom/squareup/kotlinpoet/ClassName;", "getTypeArguments", "()Ljava/util/List;", "annotated", "asNonNullable", "asNullable", "emit", "Lcom/squareup/kotlinpoet/CodeWriter;", "out", "emit$kotlinpoet", "nestedClass", "name", "", "withoutAnnotations", "Companion", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class ParameterizedTypeName extends TypeName {
    public static final Companion Companion = new Companion(null);
    private final ParameterizedTypeName enclosingType;
    private final ClassName rawType;
    private final List<TypeName> typeArguments;

    @JvmStatic
    public static final ParameterizedTypeName get(ClassName rawType, TypeName... typeArguments) {
        Intrinsics.checkParameterIsNotNull(rawType, "rawType");
        Intrinsics.checkParameterIsNotNull(typeArguments, "typeArguments");
        return Companion.get(rawType, typeArguments);
    }

    @JvmStatic
    public static final ParameterizedTypeName get(Class<?> rawType, Type... typeArguments) {
        Intrinsics.checkParameterIsNotNull(rawType, "rawType");
        Intrinsics.checkParameterIsNotNull(typeArguments, "typeArguments");
        return Companion.get(rawType, typeArguments);
    }

    @JvmStatic
    public static final ParameterizedTypeName get(KClass<?> rawType, KClass<?>... typeArguments) {
        Intrinsics.checkParameterIsNotNull(rawType, "rawType");
        Intrinsics.checkParameterIsNotNull(typeArguments, "typeArguments");
        return Companion.get(rawType, typeArguments);
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public /* bridge */ /* synthetic */ TypeName annotated(List list) {
        return annotated((List<AnnotationSpec>) list);
    }

    public final ClassName getRawType() {
        return this.rawType;
    }

    public /* synthetic */ ParameterizedTypeName(ParameterizedTypeName parameterizedTypeName, ClassName className, List list, boolean z, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(parameterizedTypeName, className, list, (i & 8) != 0 ? false : z, (i & 16) != 0 ? CollectionsKt.emptyList() : list2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ParameterizedTypeName(ParameterizedTypeName parameterizedTypeName, ClassName rawType, List<? extends TypeName> typeArguments, boolean z, List<AnnotationSpec> annotations) {
        super(z, annotations);
        Intrinsics.checkParameterIsNotNull(rawType, "rawType");
        Intrinsics.checkParameterIsNotNull(typeArguments, "typeArguments");
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        this.enclosingType = parameterizedTypeName;
        this.rawType = rawType;
        List<? extends TypeName> list = typeArguments;
        this.typeArguments = UtilKt.toImmutableList(list);
        boolean z2 = true;
        if (!(!list.isEmpty()) && parameterizedTypeName == null) {
            z2 = false;
        }
        if (z2) {
            return;
        }
        throw new IllegalArgumentException(("no type arguments: " + rawType).toString());
    }

    public final List<TypeName> getTypeArguments() {
        return this.typeArguments;
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public ParameterizedTypeName asNullable() {
        return new ParameterizedTypeName(this.enclosingType, this.rawType, this.typeArguments, true, getAnnotations());
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public ParameterizedTypeName asNonNullable() {
        return new ParameterizedTypeName(this.enclosingType, this.rawType, this.typeArguments, false, getAnnotations());
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public ParameterizedTypeName annotated(List<AnnotationSpec> annotations) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        return new ParameterizedTypeName(this.enclosingType, this.rawType, this.typeArguments, getNullable(), CollectionsKt.plus((Collection) getAnnotations(), (Iterable) annotations));
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public ParameterizedTypeName withoutAnnotations() {
        return new ParameterizedTypeName(this.enclosingType, this.rawType, this.typeArguments, getNullable(), null, 16, null);
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public CodeWriter emit$kotlinpoet(CodeWriter out) {
        Intrinsics.checkParameterIsNotNull(out, "out");
        ParameterizedTypeName parameterizedTypeName = this.enclosingType;
        if (parameterizedTypeName != null) {
            parameterizedTypeName.emitAnnotations$kotlinpoet(out);
            this.enclosingType.emit$kotlinpoet(out);
            out.emit("." + this.rawType.simpleName());
        } else {
            this.rawType.emitAnnotations$kotlinpoet(out);
            this.rawType.emit$kotlinpoet(out);
        }
        if (!this.typeArguments.isEmpty()) {
            out.emit("<");
            int i = 0;
            for (TypeName typeName : this.typeArguments) {
                int i2 = i + 1;
                if (i > 0) {
                    out.emit(", ");
                }
                typeName.emitAnnotations$kotlinpoet(out);
                typeName.emit$kotlinpoet(out);
                typeName.emitNullable$kotlinpoet(out);
                i = i2;
            }
            out.emit(">");
        }
        return out;
    }

    public final ParameterizedTypeName nestedClass(String name, List<? extends TypeName> typeArguments) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(typeArguments, "typeArguments");
        return new ParameterizedTypeName(this, this.rawType.nestedClass(name), typeArguments, false, null, 24, null);
    }

    /* compiled from: ParameterizedTypeName.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J)\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\b\"\u00020\tH\u0007¢\u0006\u0002\u0010\nJ-\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u000b2\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\b\"\u00020\fH\u0007¢\u0006\u0002\u0010\rJ)\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00120\u0011H\u0000¢\u0006\u0002\b\u0013J5\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00142\u001a\u0010\u0007\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00140\b\"\u0006\u0012\u0002\b\u00030\u0014H\u0007¢\u0006\u0002\u0010\u0015¨\u0006\u0016"}, d2 = {"Lcom/squareup/kotlinpoet/ParameterizedTypeName$Companion;", "", "()V", "get", "Lcom/squareup/kotlinpoet/ParameterizedTypeName;", "rawType", "Lcom/squareup/kotlinpoet/ClassName;", "typeArguments", "", "Lcom/squareup/kotlinpoet/TypeName;", "(Lcom/squareup/kotlinpoet/ClassName;[Lcom/squareup/kotlinpoet/TypeName;)Lcom/squareup/kotlinpoet/ParameterizedTypeName;", "Ljava/lang/Class;", "Ljava/lang/reflect/Type;", "(Ljava/lang/Class;[Ljava/lang/reflect/Type;)Lcom/squareup/kotlinpoet/ParameterizedTypeName;", "type", "Ljava/lang/reflect/ParameterizedType;", "map", "", "Lcom/squareup/kotlinpoet/TypeVariableName;", "get$kotlinpoet", "Lkotlin/reflect/KClass;", "(Lkotlin/reflect/KClass;[Lkotlin/reflect/KClass;)Lcom/squareup/kotlinpoet/ParameterizedTypeName;", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final ParameterizedTypeName get(ClassName rawType, TypeName... typeArguments) {
            Intrinsics.checkParameterIsNotNull(rawType, "rawType");
            Intrinsics.checkParameterIsNotNull(typeArguments, "typeArguments");
            return new ParameterizedTypeName(null, rawType, ArraysKt.toList(typeArguments), false, null, 24, null);
        }

        @JvmStatic
        public final ParameterizedTypeName get(KClass<?> rawType, KClass<?>... typeArguments) {
            Intrinsics.checkParameterIsNotNull(rawType, "rawType");
            Intrinsics.checkParameterIsNotNull(typeArguments, "typeArguments");
            ClassName className = ClassNames.get(rawType);
            KClass<?>[] kClassArr = typeArguments;
            ArrayList arrayList = new ArrayList(kClassArr.length);
            for (KClass<?> kClass : kClassArr) {
                arrayList.add(TypeNames.get(kClass));
            }
            return new ParameterizedTypeName(null, className, arrayList, false, null, 24, null);
        }

        @JvmStatic
        public final ParameterizedTypeName get(Class<?> rawType, Type... typeArguments) {
            Intrinsics.checkParameterIsNotNull(rawType, "rawType");
            Intrinsics.checkParameterIsNotNull(typeArguments, "typeArguments");
            ClassName className = ClassNames.get(rawType);
            Type[] typeArr = typeArguments;
            ArrayList arrayList = new ArrayList(typeArr.length);
            for (Type type : typeArr) {
                arrayList.add(TypeNames.get(type));
            }
            return new ParameterizedTypeName(null, className, arrayList, false, null, 24, null);
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x005c A[LOOP:0: B:19:0x0059->B:21:0x005c, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0076  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0086  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final com.squareup.kotlinpoet.ParameterizedTypeName get$kotlinpoet(java.lang.reflect.ParameterizedType r11, java.util.Map<java.lang.reflect.Type, com.squareup.kotlinpoet.TypeVariableName> r12) {
            /*
                r10 = this;
                java.lang.String r0 = "type"
                kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r11, r0)
                java.lang.String r0 = "map"
                kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r12, r0)
                java.lang.reflect.Type r0 = r11.getRawType()
                java.lang.String r1 = "null cannot be cast to non-null type java.lang.Class<*>"
                if (r0 == 0) goto L93
                java.lang.Class r0 = (java.lang.Class) r0
                com.squareup.kotlinpoet.ClassName r4 = com.squareup.kotlinpoet.ClassNames.get(r0)
                java.lang.reflect.Type r0 = r11.getOwnerType()
                boolean r0 = r0 instanceof java.lang.reflect.ParameterizedType
                if (r0 == 0) goto L49
                java.lang.reflect.Type r0 = r11.getRawType()
                if (r0 == 0) goto L43
                java.lang.Class r0 = (java.lang.Class) r0
                int r0 = r0.getModifiers()
                boolean r0 = java.lang.reflect.Modifier.isStatic(r0)
                if (r0 != 0) goto L49
                java.lang.reflect.Type r0 = r11.getOwnerType()
                if (r0 == 0) goto L3b
                java.lang.reflect.ParameterizedType r0 = (java.lang.reflect.ParameterizedType) r0
                goto L4a
            L3b:
                kotlin.TypeCastException r11 = new kotlin.TypeCastException
                java.lang.String r12 = "null cannot be cast to non-null type java.lang.reflect.ParameterizedType"
                r11.<init>(r12)
                throw r11
            L43:
                kotlin.TypeCastException r11 = new kotlin.TypeCastException
                r11.<init>(r1)
                throw r11
            L49:
                r0 = 0
            L4a:
                java.lang.reflect.Type[] r11 = r11.getActualTypeArguments()
                java.lang.Object[] r11 = (java.lang.Object[]) r11
                java.util.ArrayList r1 = new java.util.ArrayList
                int r2 = r11.length
                r1.<init>(r2)
                java.util.Collection r1 = (java.util.Collection) r1
                r2 = 0
            L59:
                int r3 = r11.length
                if (r2 >= r3) goto L71
                r3 = r11[r2]
                java.lang.reflect.Type r3 = (java.lang.reflect.Type) r3
                com.squareup.kotlinpoet.TypeName$Companion r5 = com.squareup.kotlinpoet.TypeName.Companion
                java.lang.String r6 = "it"
                kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r3, r6)
                com.squareup.kotlinpoet.TypeName r3 = r5.get$kotlinpoet(r3, r12)
                r1.add(r3)
                int r2 = r2 + 1
                goto L59
            L71:
                r5 = r1
                java.util.List r5 = (java.util.List) r5
                if (r0 == 0) goto L86
                r11 = r10
                com.squareup.kotlinpoet.ParameterizedTypeName$Companion r11 = (com.squareup.kotlinpoet.ParameterizedTypeName.Companion) r11
                com.squareup.kotlinpoet.ParameterizedTypeName r11 = r10.get$kotlinpoet(r0, r12)
                java.lang.String r12 = r4.simpleName()
                com.squareup.kotlinpoet.ParameterizedTypeName r11 = r11.nestedClass(r12, r5)
                goto L92
            L86:
                com.squareup.kotlinpoet.ParameterizedTypeName r11 = new com.squareup.kotlinpoet.ParameterizedTypeName
                r3 = 0
                r6 = 0
                r7 = 0
                r8 = 24
                r9 = 0
                r2 = r11
                r2.<init>(r3, r4, r5, r6, r7, r8, r9)
            L92:
                return r11
            L93:
                kotlin.TypeCastException r11 = new kotlin.TypeCastException
                r11.<init>(r1)
                throw r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.kotlinpoet.ParameterizedTypeName.Companion.get$kotlinpoet(java.lang.reflect.ParameterizedType, java.util.Map):com.squareup.kotlinpoet.ParameterizedTypeName");
        }
    }
}
