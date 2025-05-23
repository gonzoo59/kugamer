package com.squareup.kotlinpoet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: LambdaTypeName.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018BG\b\u0000\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0001\u0012\u000e\b\u0002\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0001\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004¢\u0006\u0002\u0010\nJ\u0016\u0010\u0010\u001a\u00020\u00002\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004H\u0016J\b\u0010\u0011\u001a\u00020\u0000H\u0016J\b\u0010\u0012\u001a\u00020\u0000H\u0016J\u0015\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0010¢\u0006\u0002\b\u0016J\b\u0010\u0017\u001a\u00020\u0000H\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000e¨\u0006\u0019"}, d2 = {"Lcom/squareup/kotlinpoet/LambdaTypeName;", "Lcom/squareup/kotlinpoet/TypeName;", "receiver", "parameters", "", "returnType", "nullable", "", "annotations", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "(Lcom/squareup/kotlinpoet/TypeName;Ljava/util/List;Lcom/squareup/kotlinpoet/TypeName;ZLjava/util/List;)V", "getParameters", "()Ljava/util/List;", "getReceiver", "()Lcom/squareup/kotlinpoet/TypeName;", "getReturnType", "annotated", "asNonNullable", "asNullable", "emit", "Lcom/squareup/kotlinpoet/CodeWriter;", "out", "emit$kotlinpoet", "withoutAnnotations", "Companion", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class LambdaTypeName extends TypeName {
    public static final Companion Companion = new Companion(null);
    private final List<TypeName> parameters;
    private final TypeName receiver;
    private final TypeName returnType;

    public LambdaTypeName() {
        this(null, null, null, false, null, 31, null);
    }

    @JvmStatic
    public static final LambdaTypeName get(TypeName typeName, List<? extends TypeName> parameters, TypeName returnType) {
        Intrinsics.checkParameterIsNotNull(parameters, "parameters");
        Intrinsics.checkParameterIsNotNull(returnType, "returnType");
        return Companion.get(typeName, parameters, returnType);
    }

    @JvmStatic
    public static final LambdaTypeName get(TypeName typeName, TypeName[] parameters, TypeName returnType) {
        Intrinsics.checkParameterIsNotNull(parameters, "parameters");
        Intrinsics.checkParameterIsNotNull(returnType, "returnType");
        return Companion.get(typeName, parameters, returnType);
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public /* bridge */ /* synthetic */ TypeName annotated(List list) {
        return annotated((List<AnnotationSpec>) list);
    }

    public /* synthetic */ LambdaTypeName(TypeName typeName, List list, ClassName className, boolean z, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : typeName, (i & 2) != 0 ? CollectionsKt.emptyList() : list, (i & 4) != 0 ? TypeNames.UNIT : className, (i & 8) != 0 ? false : z, (i & 16) != 0 ? CollectionsKt.emptyList() : list2);
    }

    public final TypeName getReceiver() {
        return this.receiver;
    }

    public final TypeName getReturnType() {
        return this.returnType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LambdaTypeName(TypeName typeName, List<? extends TypeName> parameters, TypeName returnType, boolean z, List<AnnotationSpec> annotations) {
        super(z, annotations);
        Intrinsics.checkParameterIsNotNull(parameters, "parameters");
        Intrinsics.checkParameterIsNotNull(returnType, "returnType");
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        this.receiver = typeName;
        this.returnType = returnType;
        this.parameters = UtilKt.toImmutableList(parameters);
    }

    public final List<TypeName> getParameters() {
        return this.parameters;
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public LambdaTypeName asNullable() {
        return new LambdaTypeName(this.receiver, this.parameters, this.returnType, true, getAnnotations());
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public LambdaTypeName asNonNullable() {
        return new LambdaTypeName(this.receiver, this.parameters, this.returnType, false, getAnnotations());
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public LambdaTypeName annotated(List<AnnotationSpec> annotations) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        return new LambdaTypeName(this.receiver, this.parameters, this.returnType, getNullable(), annotations);
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public LambdaTypeName withoutAnnotations() {
        return new LambdaTypeName(this.receiver, this.parameters, this.returnType, getNullable(), null, 16, null);
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public CodeWriter emit$kotlinpoet(CodeWriter out) {
        Intrinsics.checkParameterIsNotNull(out, "out");
        emitAnnotations$kotlinpoet(out);
        if (getNullable()) {
            out.emit("(");
        }
        TypeName typeName = this.receiver;
        if (typeName != null) {
            out.emitCode("%T.", typeName);
        }
        List<TypeName> list = this.parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(CodeBlock.Companion.of("%T", (TypeName) it.next()));
        }
        out.emitCode("(%L) -> %T", CodeBlocks.joinToCode$default(arrayList, null, null, null, 7, null), this.returnType);
        if (getNullable()) {
            out.emit(")");
        }
        return out;
    }

    /* compiled from: LambdaTypeName.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J7\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0014\b\u0002\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\b\"\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0007¢\u0006\u0002\u0010\nJ,\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\u0006\u0010\t\u001a\u00020\u0006H\u0007¨\u0006\f"}, d2 = {"Lcom/squareup/kotlinpoet/LambdaTypeName$Companion;", "", "()V", "get", "Lcom/squareup/kotlinpoet/LambdaTypeName;", "receiver", "Lcom/squareup/kotlinpoet/TypeName;", "parameters", "", "returnType", "(Lcom/squareup/kotlinpoet/TypeName;[Lcom/squareup/kotlinpoet/TypeName;Lcom/squareup/kotlinpoet/TypeName;)Lcom/squareup/kotlinpoet/LambdaTypeName;", "", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @JvmStatic
        public static /* bridge */ /* synthetic */ LambdaTypeName get$default(Companion companion, TypeName typeName, List list, TypeName typeName2, int i, Object obj) {
            if ((i & 1) != 0) {
                typeName = null;
            }
            if ((i & 2) != 0) {
                list = CollectionsKt.emptyList();
            }
            return companion.get(typeName, list, typeName2);
        }

        @JvmStatic
        public final LambdaTypeName get(TypeName typeName, List<? extends TypeName> parameters, TypeName returnType) {
            Intrinsics.checkParameterIsNotNull(parameters, "parameters");
            Intrinsics.checkParameterIsNotNull(returnType, "returnType");
            return new LambdaTypeName(typeName, parameters, returnType, false, null, 24, null);
        }

        @JvmStatic
        public static /* bridge */ /* synthetic */ LambdaTypeName get$default(Companion companion, TypeName typeName, TypeName[] typeNameArr, TypeName typeName2, int i, Object obj) {
            if ((i & 1) != 0) {
                typeName = null;
            }
            if ((i & 2) != 0) {
                typeNameArr = new TypeName[0];
            }
            return companion.get(typeName, typeNameArr, typeName2);
        }

        @JvmStatic
        public final LambdaTypeName get(TypeName typeName, TypeName[] parameters, TypeName returnType) {
            Intrinsics.checkParameterIsNotNull(parameters, "parameters");
            Intrinsics.checkParameterIsNotNull(returnType, "returnType");
            return new LambdaTypeName(typeName, ArraysKt.toList(parameters), returnType, false, null, 24, null);
        }
    }
}
