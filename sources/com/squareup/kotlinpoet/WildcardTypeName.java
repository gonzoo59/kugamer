package com.squareup.kotlinpoet;

import com.baidu.kwgames.Constants;
import com.squareup.kotlinpoet.TypeName;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
/* compiled from: WildcardTypeName.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B=\b\u0002\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003¢\u0006\u0002\u0010\tJ\u0016\u0010\r\u001a\u00020\u00002\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u0016J\b\u0010\u000e\u001a\u00020\u0000H\u0016J\b\u0010\u000f\u001a\u00020\u0000H\u0016J\u0015\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0010¢\u0006\u0002\b\u0013J\b\u0010\u0014\u001a\u00020\u0000H\u0016R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0016"}, d2 = {"Lcom/squareup/kotlinpoet/WildcardTypeName;", "Lcom/squareup/kotlinpoet/TypeName;", "upperBounds", "", "lowerBounds", "nullable", "", "annotations", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "(Ljava/util/List;Ljava/util/List;ZLjava/util/List;)V", "getLowerBounds", "()Ljava/util/List;", "getUpperBounds", "annotated", "asNonNullable", "asNullable", "emit", "Lcom/squareup/kotlinpoet/CodeWriter;", "out", "emit$kotlinpoet", "withoutAnnotations", "Companion", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class WildcardTypeName extends TypeName {
    public static final Companion Companion = new Companion(null);
    private final List<TypeName> lowerBounds;
    private final List<TypeName> upperBounds;

    @JvmStatic
    public static final WildcardTypeName subtypeOf(TypeName upperBound) {
        Intrinsics.checkParameterIsNotNull(upperBound, "upperBound");
        return Companion.subtypeOf(upperBound);
    }

    @JvmStatic
    public static final WildcardTypeName subtypeOf(Type upperBound) {
        Intrinsics.checkParameterIsNotNull(upperBound, "upperBound");
        return Companion.subtypeOf(upperBound);
    }

    @JvmStatic
    public static final WildcardTypeName subtypeOf(KClass<?> upperBound) {
        Intrinsics.checkParameterIsNotNull(upperBound, "upperBound");
        return Companion.subtypeOf(upperBound);
    }

    @JvmStatic
    public static final WildcardTypeName supertypeOf(TypeName lowerBound) {
        Intrinsics.checkParameterIsNotNull(lowerBound, "lowerBound");
        return Companion.supertypeOf(lowerBound);
    }

    @JvmStatic
    public static final WildcardTypeName supertypeOf(Type lowerBound) {
        Intrinsics.checkParameterIsNotNull(lowerBound, "lowerBound");
        return Companion.supertypeOf(lowerBound);
    }

    @JvmStatic
    public static final WildcardTypeName supertypeOf(KClass<?> lowerBound) {
        Intrinsics.checkParameterIsNotNull(lowerBound, "lowerBound");
        return Companion.supertypeOf(lowerBound);
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public /* bridge */ /* synthetic */ TypeName annotated(List list) {
        return annotated((List<AnnotationSpec>) list);
    }

    /* synthetic */ WildcardTypeName(List list, List list2, boolean z, List list3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, list2, (i & 4) != 0 ? false : z, (i & 8) != 0 ? CollectionsKt.emptyList() : list3);
    }

    private WildcardTypeName(List<? extends TypeName> list, List<? extends TypeName> list2, boolean z, List<AnnotationSpec> list3) {
        super(z, list3);
        List<TypeName> immutableList = UtilKt.toImmutableList(list);
        this.upperBounds = immutableList;
        this.lowerBounds = UtilKt.toImmutableList(list2);
        if (immutableList.size() == 1) {
            return;
        }
        throw new IllegalArgumentException(("unexpected extends bounds: " + list).toString());
    }

    public final List<TypeName> getUpperBounds() {
        return this.upperBounds;
    }

    public final List<TypeName> getLowerBounds() {
        return this.lowerBounds;
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public WildcardTypeName asNullable() {
        return new WildcardTypeName(this.upperBounds, this.lowerBounds, true, getAnnotations());
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public WildcardTypeName asNonNullable() {
        return new WildcardTypeName(this.upperBounds, this.lowerBounds, false, getAnnotations());
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public WildcardTypeName annotated(List<AnnotationSpec> annotations) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        return new WildcardTypeName(this.upperBounds, this.lowerBounds, getNullable(), CollectionsKt.plus((Collection) getAnnotations(), (Iterable) annotations));
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public WildcardTypeName withoutAnnotations() {
        return new WildcardTypeName(this.upperBounds, this.lowerBounds, getNullable(), null, 8, null);
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public CodeWriter emit$kotlinpoet(CodeWriter out) {
        Intrinsics.checkParameterIsNotNull(out, "out");
        return this.lowerBounds.size() == 1 ? out.emitCode("in %T", this.lowerBounds.get(0)) : Intrinsics.areEqual(this.upperBounds.get(0), TypeNames.ANY) ? out.emit("*") : out.emitCode("out %T", this.upperBounds.get(0));
    }

    /* compiled from: WildcardTypeName.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J)\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bH\u0000¢\u0006\u0002\b\u000bJ)\u0010\u0003\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\n0\bH\u0000¢\u0006\u0002\b\u000bJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0004H\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\tH\u0007J\u0014\u0010\u0010\u001a\u00020\u00112\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0004H\u0007J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\tH\u0007J\u0014\u0010\u0014\u001a\u00020\u00112\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0007¨\u0006\u0016"}, d2 = {"Lcom/squareup/kotlinpoet/WildcardTypeName$Companion;", "", "()V", "get", "Lcom/squareup/kotlinpoet/TypeName;", "wildcardName", "Ljava/lang/reflect/WildcardType;", "map", "", "Ljava/lang/reflect/Type;", "Lcom/squareup/kotlinpoet/TypeVariableName;", "get$kotlinpoet", Constants.FLOAT_TAG_AI_MIRROW, "Ljavax/lang/model/type/WildcardType;", "typeVariables", "Ljavax/lang/model/element/TypeParameterElement;", "subtypeOf", "Lcom/squareup/kotlinpoet/WildcardTypeName;", "upperBound", "Lkotlin/reflect/KClass;", "supertypeOf", "lowerBound", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final WildcardTypeName subtypeOf(TypeName upperBound) {
            Intrinsics.checkParameterIsNotNull(upperBound, "upperBound");
            return new WildcardTypeName(CollectionsKt.listOf(upperBound), CollectionsKt.emptyList(), false, null, 12, null);
        }

        @JvmStatic
        public final WildcardTypeName subtypeOf(Type upperBound) {
            Intrinsics.checkParameterIsNotNull(upperBound, "upperBound");
            return subtypeOf(TypeNames.get(upperBound));
        }

        @JvmStatic
        public final WildcardTypeName subtypeOf(KClass<?> upperBound) {
            Intrinsics.checkParameterIsNotNull(upperBound, "upperBound");
            return subtypeOf(TypeNames.get(upperBound));
        }

        @JvmStatic
        public final WildcardTypeName supertypeOf(TypeName lowerBound) {
            Intrinsics.checkParameterIsNotNull(lowerBound, "lowerBound");
            return new WildcardTypeName(CollectionsKt.listOf(TypeNames.ANY), CollectionsKt.listOf(lowerBound), false, null, 12, null);
        }

        @JvmStatic
        public final WildcardTypeName supertypeOf(Type lowerBound) {
            Intrinsics.checkParameterIsNotNull(lowerBound, "lowerBound");
            return supertypeOf(TypeNames.get(lowerBound));
        }

        @JvmStatic
        public final WildcardTypeName supertypeOf(KClass<?> lowerBound) {
            Intrinsics.checkParameterIsNotNull(lowerBound, "lowerBound");
            return supertypeOf(TypeNames.get(lowerBound));
        }

        public final TypeName get$kotlinpoet(WildcardType mirror, Map<TypeParameterElement, TypeVariableName> typeVariables) {
            Intrinsics.checkParameterIsNotNull(mirror, "mirror");
            Intrinsics.checkParameterIsNotNull(typeVariables, "typeVariables");
            TypeMirror extendsBound = mirror.getExtendsBound();
            if (extendsBound == null) {
                TypeMirror superBound = mirror.getSuperBound();
                return superBound == null ? subtypeOf(TypeNames.ANY) : supertypeOf(TypeName.Companion.get$kotlinpoet(superBound, typeVariables));
            }
            return subtypeOf(TypeName.Companion.get$kotlinpoet(extendsBound, typeVariables));
        }

        public final TypeName get$kotlinpoet(java.lang.reflect.WildcardType wildcardName, Map<Type, TypeVariableName> map) {
            Intrinsics.checkParameterIsNotNull(wildcardName, "wildcardName");
            Intrinsics.checkParameterIsNotNull(map, "map");
            Type[] upperBounds = wildcardName.getUpperBounds();
            ArrayList arrayList = new ArrayList(upperBounds.length);
            for (Type it : upperBounds) {
                TypeName.Companion companion = TypeName.Companion;
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                arrayList.add(companion.get$kotlinpoet(it, map));
            }
            ArrayList arrayList2 = arrayList;
            Type[] lowerBounds = wildcardName.getLowerBounds();
            ArrayList arrayList3 = new ArrayList(lowerBounds.length);
            for (Type it2 : lowerBounds) {
                TypeName.Companion companion2 = TypeName.Companion;
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                arrayList3.add(companion2.get$kotlinpoet(it2, map));
            }
            return new WildcardTypeName(arrayList2, arrayList3, false, null, 12, null);
        }
    }
}
