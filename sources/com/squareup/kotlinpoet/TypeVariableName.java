package com.squareup.kotlinpoet;

import com.baidu.kwgames.Constants;
import com.squareup.kotlinpoet.TypeName;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
/* compiled from: TypeVariableName.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 &2\u00020\u0001:\u0001&BM\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005¢\u0006\u0002\u0010\rJ\u0016\u0010\u0016\u001a\u00020\u00002\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005H\u0016J\b\u0010\u0017\u001a\u00020\u0000H\u0016J\b\u0010\u0018\u001a\u00020\u0000H\u0016J\u0015\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0010¢\u0006\u0002\b\u001cJ\u0010\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u001d\u001a\u00020\tJ\u001f\u0010\u001e\u001a\u00020\u00002\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u001f\"\u00020\u0001¢\u0006\u0002\u0010 J\u001f\u0010\u001e\u001a\u00020\u00002\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020!0\u001f\"\u00020!¢\u0006\u0002\u0010\"J'\u0010\u001e\u001a\u00020\u00002\u001a\u0010\u0004\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030#0\u001f\"\u0006\u0012\u0002\b\u00030#¢\u0006\u0002\u0010$J\u0014\u0010\u001e\u001a\u00020\u00002\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005J\b\u0010%\u001a\u00020\u0000H\u0016R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006'"}, d2 = {"Lcom/squareup/kotlinpoet/TypeVariableName;", "Lcom/squareup/kotlinpoet/TypeName;", "name", "", "bounds", "", "variance", "Lcom/squareup/kotlinpoet/KModifier;", "reified", "", "nullable", "annotations", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "(Ljava/lang/String;Ljava/util/List;Lcom/squareup/kotlinpoet/KModifier;ZZLjava/util/List;)V", "getBounds", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", "getReified", "()Z", "getVariance", "()Lcom/squareup/kotlinpoet/KModifier;", "annotated", "asNonNullable", "asNullable", "emit", "Lcom/squareup/kotlinpoet/CodeWriter;", "out", "emit$kotlinpoet", "value", "withBounds", "", "([Lcom/squareup/kotlinpoet/TypeName;)Lcom/squareup/kotlinpoet/TypeVariableName;", "Ljava/lang/reflect/Type;", "([Ljava/lang/reflect/Type;)Lcom/squareup/kotlinpoet/TypeVariableName;", "Lkotlin/reflect/KClass;", "([Lkotlin/reflect/KClass;)Lcom/squareup/kotlinpoet/TypeVariableName;", "withoutAnnotations", "Companion", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class TypeVariableName extends TypeName {
    public static final Companion Companion = new Companion(null);
    private final List<TypeName> bounds;
    private final String name;
    private final boolean reified;
    private final KModifier variance;

    @JvmStatic
    public static final TypeVariableName get(String str) {
        return Companion.get$default(Companion, str, null, 2, null);
    }

    @JvmStatic
    public static final TypeVariableName get(String name, KModifier kModifier) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return Companion.get(name, kModifier);
    }

    @JvmStatic
    public static final TypeVariableName get(String str, TypeName... typeNameArr) {
        return Companion.get$default(Companion, str, typeNameArr, (KModifier) null, 4, (Object) null);
    }

    @JvmStatic
    public static final TypeVariableName get(String name, TypeName[] bounds, KModifier kModifier) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(bounds, "bounds");
        return Companion.get(name, bounds, kModifier);
    }

    @JvmStatic
    public static final TypeVariableName get(String str, Type... typeArr) {
        return Companion.get$default(Companion, str, typeArr, (KModifier) null, 4, (Object) null);
    }

    @JvmStatic
    public static final TypeVariableName get(String name, Type[] bounds, KModifier kModifier) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(bounds, "bounds");
        return Companion.get(name, bounds, kModifier);
    }

    @JvmStatic
    public static final TypeVariableName get(String str, KClass<?>... kClassArr) {
        return Companion.get$default(Companion, str, kClassArr, (KModifier) null, 4, (Object) null);
    }

    @JvmStatic
    public static final TypeVariableName get(String name, KClass<?>[] bounds, KModifier kModifier) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(bounds, "bounds");
        return Companion.get(name, bounds, kModifier);
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public /* bridge */ /* synthetic */ TypeName annotated(List list) {
        return annotated((List<AnnotationSpec>) list);
    }

    public final String getName() {
        return this.name;
    }

    public final List<TypeName> getBounds() {
        return this.bounds;
    }

    /* synthetic */ TypeVariableName(String str, List list, KModifier kModifier, boolean z, boolean z2, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, list, (i & 4) != 0 ? null : kModifier, (i & 8) != 0 ? false : z, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? CollectionsKt.emptyList() : list2);
    }

    public final KModifier getVariance() {
        return this.variance;
    }

    public final boolean getReified() {
        return this.reified;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private TypeVariableName(String str, List<? extends TypeName> list, KModifier kModifier, boolean z, boolean z2, List<AnnotationSpec> list2) {
        super(z2, list2);
        this.name = str;
        this.bounds = list;
        this.variance = kModifier;
        this.reified = z;
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public TypeVariableName asNullable() {
        return new TypeVariableName(this.name, this.bounds, this.variance, this.reified, true, getAnnotations());
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public TypeVariableName asNonNullable() {
        return new TypeVariableName(this.name, this.bounds, this.variance, this.reified, false, getAnnotations());
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public TypeVariableName annotated(List<AnnotationSpec> annotations) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        return new TypeVariableName(this.name, this.bounds, this.variance, this.reified, getNullable(), annotations);
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public TypeVariableName withoutAnnotations() {
        return new TypeVariableName(this.name, this.bounds, this.variance, this.reified, getNullable(), null, 32, null);
    }

    public final TypeVariableName withBounds(Type... bounds) {
        Intrinsics.checkParameterIsNotNull(bounds, "bounds");
        Type[] typeArr = bounds;
        ArrayList arrayList = new ArrayList(typeArr.length);
        for (Type type : typeArr) {
            arrayList.add(TypeNames.get(type));
        }
        return withBounds(arrayList);
    }

    public final TypeVariableName withBounds(KClass<?>... bounds) {
        Intrinsics.checkParameterIsNotNull(bounds, "bounds");
        KClass<?>[] kClassArr = bounds;
        ArrayList arrayList = new ArrayList(kClassArr.length);
        for (KClass<?> kClass : kClassArr) {
            arrayList.add(TypeNames.get(kClass));
        }
        return withBounds(arrayList);
    }

    public final TypeVariableName withBounds(TypeName... bounds) {
        Intrinsics.checkParameterIsNotNull(bounds, "bounds");
        return withBounds(ArraysKt.toList(bounds));
    }

    public final TypeVariableName withBounds(List<? extends TypeName> bounds) {
        Intrinsics.checkParameterIsNotNull(bounds, "bounds");
        return new TypeVariableName(this.name, CollectionsKt.plus((Collection) this.bounds, (Iterable) bounds), this.variance, this.reified, getNullable(), getAnnotations());
    }

    public static /* bridge */ /* synthetic */ TypeVariableName reified$default(TypeVariableName typeVariableName, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return typeVariableName.reified(z);
    }

    public final TypeVariableName reified(boolean z) {
        return new TypeVariableName(this.name, this.bounds, this.variance, z, getNullable(), getAnnotations());
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public CodeWriter emit$kotlinpoet(CodeWriter out) {
        Intrinsics.checkParameterIsNotNull(out, "out");
        return out.emit(this.name);
    }

    /* compiled from: TypeVariableName.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J/\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00040\bH\u0000¢\u0006\u0002\b\nJ)\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00040\bH\u0000¢\u0006\u0002\b\nJ\"\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0087\u0002¢\u0006\u0002\b\u0003J8\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00160\u0015\"\u00020\u00162\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0087\u0002¢\u0006\u0004\b\u0003\u0010\u0017J8\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\u0015\"\u00020\t2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0087\u0002¢\u0006\u0004\b\u0003\u0010\u0018J@\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u001a\u0010\u0014\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00190\u0015\"\u0006\u0012\u0002\b\u00030\u00192\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0087\u0002¢\u0006\u0004\b\u0003\u0010\u001aJ-\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u001c2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0000¢\u0006\u0002\b\u001d¨\u0006\u001e"}, d2 = {"Lcom/squareup/kotlinpoet/TypeVariableName$Companion;", "", "()V", "get", "Lcom/squareup/kotlinpoet/TypeVariableName;", "type", "Ljava/lang/reflect/TypeVariable;", "map", "", "Ljava/lang/reflect/Type;", "get$kotlinpoet", Constants.FLOAT_TAG_AI_MIRROW, "Ljavax/lang/model/type/TypeVariable;", "typeVariables", "Ljavax/lang/model/element/TypeParameterElement;", "invoke", "name", "", "variance", "Lcom/squareup/kotlinpoet/KModifier;", "bounds", "", "Lcom/squareup/kotlinpoet/TypeName;", "(Ljava/lang/String;[Lcom/squareup/kotlinpoet/TypeName;Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/TypeVariableName;", "(Ljava/lang/String;[Ljava/lang/reflect/Type;Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/TypeVariableName;", "Lkotlin/reflect/KClass;", "(Ljava/lang/String;[Lkotlin/reflect/KClass;Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/TypeVariableName;", "of", "", "of$kotlinpoet", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Companion {
        @JvmStatic
        public final TypeVariableName get(String str) {
            return get$default(this, str, null, 2, null);
        }

        @JvmStatic
        public final TypeVariableName get(String str, TypeName... typeNameArr) {
            return get$default(this, str, typeNameArr, (KModifier) null, 4, (Object) null);
        }

        @JvmStatic
        public final TypeVariableName get(String str, Type... typeArr) {
            return get$default(this, str, typeArr, (KModifier) null, 4, (Object) null);
        }

        @JvmStatic
        public final TypeVariableName get(String str, KClass<?>... kClassArr) {
            return get$default(this, str, kClassArr, (KModifier) null, 4, (Object) null);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x005e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final com.squareup.kotlinpoet.TypeVariableName of$kotlinpoet(java.lang.String r12, java.util.List<? extends com.squareup.kotlinpoet.TypeName> r13, com.squareup.kotlinpoet.KModifier r14) {
            /*
                r11 = this;
                java.lang.String r0 = "name"
                kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r12, r0)
                java.lang.String r0 = "bounds"
                kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r13, r0)
                r0 = 1
                if (r14 == 0) goto L22
                com.squareup.kotlinpoet.KModifier r2 = com.squareup.kotlinpoet.KModifier.IN
                com.squareup.kotlinpoet.KModifier r3 = com.squareup.kotlinpoet.KModifier.OUT
                r4 = 0
                r5 = 0
                r6 = 0
                r7 = 0
                r8 = 60
                r9 = 0
                r1 = r14
                boolean r1 = com.squareup.kotlinpoet.UtilKt.isOneOf$default(r1, r2, r3, r4, r5, r6, r7, r8, r9)
                if (r1 == 0) goto L20
                goto L22
            L20:
                r1 = 0
                goto L23
            L22:
                r1 = 1
            L23:
                if (r1 == 0) goto L5e
                com.squareup.kotlinpoet.TypeVariableName r1 = new com.squareup.kotlinpoet.TypeVariableName
                java.lang.Iterable r13 = (java.lang.Iterable) r13
                java.util.ArrayList r2 = new java.util.ArrayList
                r2.<init>()
                java.util.Collection r2 = (java.util.Collection) r2
                java.util.Iterator r13 = r13.iterator()
            L34:
                boolean r3 = r13.hasNext()
                if (r3 == 0) goto L4e
                java.lang.Object r3 = r13.next()
                r4 = r3
                com.squareup.kotlinpoet.TypeName r4 = (com.squareup.kotlinpoet.TypeName) r4
                com.squareup.kotlinpoet.ClassName r5 = com.squareup.kotlinpoet.TypeNames.ANY
                boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)
                r4 = r4 ^ r0
                if (r4 == 0) goto L34
                r2.add(r3)
                goto L34
            L4e:
                r4 = r2
                java.util.List r4 = (java.util.List) r4
                r6 = 0
                r7 = 0
                r8 = 0
                r9 = 56
                r10 = 0
                r2 = r1
                r3 = r12
                r5 = r14
                r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10)
                return r1
            L5e:
                java.lang.StringBuilder r12 = new java.lang.StringBuilder
                r12.<init>()
                java.lang.String r13 = ""
                r12.append(r13)
                r12.append(r14)
                java.lang.String r13 = " is an invalid variance modifier, the only allowed values are in and out!"
                r12.append(r13)
                java.lang.String r12 = r12.toString()
                java.lang.IllegalArgumentException r13 = new java.lang.IllegalArgumentException
                java.lang.String r12 = r12.toString()
                r13.<init>(r12)
                java.lang.Throwable r13 = (java.lang.Throwable) r13
                throw r13
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.kotlinpoet.TypeVariableName.Companion.of$kotlinpoet(java.lang.String, java.util.List, com.squareup.kotlinpoet.KModifier):com.squareup.kotlinpoet.TypeVariableName");
        }

        @JvmStatic
        public static /* bridge */ /* synthetic */ TypeVariableName get$default(Companion companion, String str, KModifier kModifier, int i, Object obj) {
            if ((i & 2) != 0) {
                kModifier = null;
            }
            return companion.get(str, kModifier);
        }

        @JvmStatic
        public final TypeVariableName get(String name, KModifier kModifier) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            return TypeVariableName.Companion.of$kotlinpoet(name, CollectionsKt.emptyList(), kModifier);
        }

        @JvmStatic
        public static /* bridge */ /* synthetic */ TypeVariableName get$default(Companion companion, String str, TypeName[] typeNameArr, KModifier kModifier, int i, Object obj) {
            if ((i & 4) != 0) {
                kModifier = null;
            }
            return companion.get(str, typeNameArr, kModifier);
        }

        @JvmStatic
        public final TypeVariableName get(String name, TypeName[] bounds, KModifier kModifier) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(bounds, "bounds");
            return TypeVariableName.Companion.of$kotlinpoet(name, ArraysKt.toList(bounds), kModifier);
        }

        @JvmStatic
        public static /* bridge */ /* synthetic */ TypeVariableName get$default(Companion companion, String str, KClass[] kClassArr, KModifier kModifier, int i, Object obj) {
            if ((i & 4) != 0) {
                kModifier = null;
            }
            return companion.get(str, kClassArr, kModifier);
        }

        @JvmStatic
        public final TypeVariableName get(String name, KClass<?>[] bounds, KModifier kModifier) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(bounds, "bounds");
            Companion companion = TypeVariableName.Companion;
            KClass<?>[] kClassArr = bounds;
            ArrayList arrayList = new ArrayList(kClassArr.length);
            for (KClass<?> kClass : kClassArr) {
                arrayList.add(TypeNames.get(kClass));
            }
            return companion.of$kotlinpoet(name, arrayList, kModifier);
        }

        @JvmStatic
        public static /* bridge */ /* synthetic */ TypeVariableName get$default(Companion companion, String str, Type[] typeArr, KModifier kModifier, int i, Object obj) {
            if ((i & 4) != 0) {
                kModifier = null;
            }
            return companion.get(str, typeArr, kModifier);
        }

        @JvmStatic
        public final TypeVariableName get(String name, Type[] bounds, KModifier kModifier) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(bounds, "bounds");
            Companion companion = TypeVariableName.Companion;
            Type[] typeArr = bounds;
            ArrayList arrayList = new ArrayList(typeArr.length);
            for (Type type : typeArr) {
                arrayList.add(TypeNames.get(type));
            }
            return companion.of$kotlinpoet(name, arrayList, kModifier);
        }

        public final TypeVariableName get$kotlinpoet(TypeVariable mirror, Map<TypeParameterElement, TypeVariableName> typeVariables) {
            Intrinsics.checkParameterIsNotNull(mirror, "mirror");
            Intrinsics.checkParameterIsNotNull(typeVariables, "typeVariables");
            Element asElement = mirror.asElement();
            if (asElement == null) {
                throw new TypeCastException("null cannot be cast to non-null type javax.lang.model.element.TypeParameterElement");
            }
            TypeParameterElement typeParameterElement = (TypeParameterElement) asElement;
            TypeVariableName typeVariableName = typeVariables.get(typeParameterElement);
            if (typeVariableName == null) {
                ArrayList arrayList = new ArrayList();
                List visibleBounds = Collections.unmodifiableList(arrayList);
                String obj = typeParameterElement.getSimpleName().toString();
                Intrinsics.checkExpressionValueIsNotNull(visibleBounds, "visibleBounds");
                TypeVariableName typeVariableName2 = new TypeVariableName(obj, visibleBounds, null, false, false, null, 60, null);
                typeVariables.put(typeParameterElement, typeVariableName2);
                for (TypeMirror typeMirror : typeParameterElement.getBounds()) {
                    TypeName.Companion companion = TypeName.Companion;
                    Intrinsics.checkExpressionValueIsNotNull(typeMirror, "typeMirror");
                    arrayList.add(companion.get$kotlinpoet(typeMirror, typeVariables));
                }
                arrayList.remove(TypeNames.ANY);
                return typeVariableName2;
            }
            return typeVariableName;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* bridge */ /* synthetic */ TypeVariableName get$kotlinpoet$default(Companion companion, java.lang.reflect.TypeVariable typeVariable, Map map, int i, Object obj) {
            if ((i & 2) != 0) {
                map = new LinkedHashMap();
            }
            return companion.get$kotlinpoet(typeVariable, map);
        }

        public final TypeVariableName get$kotlinpoet(java.lang.reflect.TypeVariable<?> type, Map<Type, TypeVariableName> map) {
            Type[] bounds;
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(map, "map");
            TypeVariableName typeVariableName = map.get(type);
            if (typeVariableName == null) {
                ArrayList arrayList = new ArrayList();
                List visibleBounds = Collections.unmodifiableList(arrayList);
                String name = type.getName();
                Intrinsics.checkExpressionValueIsNotNull(name, "type.name");
                Intrinsics.checkExpressionValueIsNotNull(visibleBounds, "visibleBounds");
                TypeVariableName typeVariableName2 = new TypeVariableName(name, visibleBounds, null, false, false, null, 60, null);
                map.put(type, typeVariableName2);
                for (Type bound : type.getBounds()) {
                    TypeName.Companion companion = TypeName.Companion;
                    Intrinsics.checkExpressionValueIsNotNull(bound, "bound");
                    arrayList.add(companion.get$kotlinpoet(bound, map));
                }
                arrayList.remove(TypeNames.ANY);
                return typeVariableName2;
            }
            return typeVariableName;
        }
    }
}
