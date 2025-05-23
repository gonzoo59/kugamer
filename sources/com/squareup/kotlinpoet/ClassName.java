package com.squareup.kotlinpoet;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Typography;
/* compiled from: ClassName.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 $2\u00020\u00012\b\u0012\u0004\u0012\u00020\u00000\u0002:\u0001$B+\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0007\"\u00020\u0004¢\u0006\u0002\u0010\bB/\b\u0000\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\n¢\u0006\u0002\u0010\u000fJ\u0016\u0010\u0013\u001a\u00020\u00002\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\nH\u0016J\b\u0010\u0014\u001a\u00020\u0000H\u0016J\b\u0010\u0015\u001a\u00020\u0000H\u0016J\u0011\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0000H\u0096\u0002J\u0015\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0010¢\u0006\u0002\b\u001cJ\b\u0010\u001d\u001a\u0004\u0018\u00010\u0000J\u000e\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020\u0004J\u0006\u0010\u0003\u001a\u00020\u0004J\u000e\u0010 \u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020\u0004J\u0006\u0010!\u001a\u00020\u0004J\u0006\u0010\u0005\u001a\u00020\u0004J\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\nJ\u0006\u0010\"\u001a\u00020\u0000J\b\u0010#\u001a\u00020\u0000H\u0016R\u0011\u0010\u0010\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/squareup/kotlinpoet/ClassName;", "Lcom/squareup/kotlinpoet/TypeName;", "", "packageName", "", "simpleName", "simpleNames", "", "(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", "names", "", "nullable", "", "annotations", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "(Ljava/util/List;ZLjava/util/List;)V", "canonicalName", "getCanonicalName", "()Ljava/lang/String;", "annotated", "asNonNullable", "asNullable", "compareTo", "", "other", "emit", "Lcom/squareup/kotlinpoet/CodeWriter;", "out", "emit$kotlinpoet", "enclosingClassName", "nestedClass", "name", "peerClass", "reflectionName", "topLevelClassName", "withoutAnnotations", "Companion", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class ClassName extends TypeName implements Comparable<ClassName> {
    public static final Companion Companion = new Companion(null);
    private final String canonicalName;
    private final List<String> names;

    @JvmStatic
    public static final ClassName bestGuess(String classNameString) {
        Intrinsics.checkParameterIsNotNull(classNameString, "classNameString");
        return Companion.bestGuess(classNameString);
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public /* bridge */ /* synthetic */ TypeName annotated(List list) {
        return annotated((List<AnnotationSpec>) list);
    }

    public /* synthetic */ ClassName(List list, boolean z, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? false : z, (i & 4) != 0 ? CollectionsKt.emptyList() : list2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassName(List<String> names, boolean z, List<AnnotationSpec> annotations) {
        super(z, annotations);
        String joinToString$default;
        Intrinsics.checkParameterIsNotNull(names, "names");
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        this.names = UtilKt.toImmutableList(names);
        if (names.get(0).length() == 0) {
            joinToString$default = CollectionsKt.joinToString$default(names.subList(1, names.size()), ".", null, null, 0, null, null, 62, null);
        } else {
            joinToString$default = CollectionsKt.joinToString$default(names, ".", null, null, 0, null, null, 62, null);
        }
        this.canonicalName = joinToString$default;
        int size = names.size();
        for (int i = 1; i < size; i++) {
            if (!UtilKt.isName(names.get(i))) {
                throw new IllegalArgumentException(("part " + names.get(i) + " is keyword").toString());
            }
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public ClassName(java.lang.String r7, java.lang.String r8, java.lang.String... r9) {
        /*
            r6 = this;
            java.lang.String r0 = "packageName"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r7, r0)
            java.lang.String r0 = "simpleName"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r8, r0)
            java.lang.String r0 = "simpleNames"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r9, r0)
            kotlin.jvm.internal.SpreadBuilder r0 = new kotlin.jvm.internal.SpreadBuilder
            r1 = 3
            r0.<init>(r1)
            r0.add(r7)
            r0.add(r8)
            r0.addSpread(r9)
            int r7 = r0.size()
            java.lang.String[] r7 = new java.lang.String[r7]
            java.lang.Object[] r7 = r0.toArray(r7)
            java.lang.String[] r7 = (java.lang.String[]) r7
            java.util.List r1 = kotlin.collections.CollectionsKt.listOf(r7)
            r2 = 0
            r3 = 0
            r4 = 6
            r5 = 0
            r0 = r6
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.kotlinpoet.ClassName.<init>(java.lang.String, java.lang.String, java.lang.String[]):void");
    }

    public final String getCanonicalName() {
        return this.canonicalName;
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public ClassName asNullable() {
        return new ClassName(this.names, true, getAnnotations());
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public ClassName asNonNullable() {
        return new ClassName(this.names, false, getAnnotations());
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public ClassName annotated(List<AnnotationSpec> annotations) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        return new ClassName(this.names, getNullable(), CollectionsKt.plus((Collection) getAnnotations(), (Iterable) annotations));
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public ClassName withoutAnnotations() {
        return new ClassName(this.names, getNullable(), null, 4, null);
    }

    public final String packageName() {
        return this.names.get(0);
    }

    public final ClassName enclosingClassName() {
        if (this.names.size() != 2) {
            List<String> list = this.names;
            return new ClassName(list.subList(0, list.size() - 1), false, null, 6, null);
        }
        return null;
    }

    public final ClassName topLevelClassName() {
        return new ClassName(this.names.subList(0, 2), false, null, 6, null);
    }

    public final String reflectionName() {
        if (this.names.size() == 2) {
            String packageName = packageName();
            if (packageName.length() == 0) {
                return this.names.get(1);
            }
            return packageName + "." + this.names.get(1);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(topLevelClassName());
        for (String str : simpleNames().subList(1, simpleNames().size())) {
            sb.append(Typography.dollar);
            sb.append(str);
        }
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    public final ClassName nestedClass(String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return new ClassName(CollectionsKt.plus((Collection<? extends String>) this.names, name), false, null, 6, null);
    }

    public final List<String> simpleNames() {
        List<String> list = this.names;
        return list.subList(1, list.size());
    }

    public final ClassName peerClass(String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        List mutableList = CollectionsKt.toMutableList((Collection) this.names);
        mutableList.set(mutableList.size() - 1, name);
        return new ClassName(mutableList, false, null, 6, null);
    }

    public final String simpleName() {
        List<String> list = this.names;
        return list.get(list.size() - 1);
    }

    @Override // java.lang.Comparable
    public int compareTo(ClassName other) {
        Intrinsics.checkParameterIsNotNull(other, "other");
        return this.canonicalName.compareTo(other.canonicalName);
    }

    @Override // com.squareup.kotlinpoet.TypeName
    public CodeWriter emit$kotlinpoet(CodeWriter out) {
        Intrinsics.checkParameterIsNotNull(out, "out");
        return out.emit(out.lookupName(this));
    }

    /* compiled from: ClassName.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lcom/squareup/kotlinpoet/ClassName$Companion;", "", "()V", "bestGuess", "Lcom/squareup/kotlinpoet/ClassName;", "classNameString", "", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:35:0x00bd A[LOOP:1: B:20:0x008a->B:35:0x00bd, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:53:0x00c1 A[SYNTHETIC] */
        @kotlin.jvm.JvmStatic
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final com.squareup.kotlinpoet.ClassName bestGuess(java.lang.String r19) {
            /*
                Method dump skipped, instructions count: 268
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.kotlinpoet.ClassName.Companion.bestGuess(java.lang.String):com.squareup.kotlinpoet.ClassName");
        }
    }
}
