package com.squareup.kotlinpoet;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: Import.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0080\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0000H\u0096\u0002J\u000e\u0010\r\u001a\u00020\u0003HÀ\u0003¢\u0006\u0002\b\u000eJ\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0003HÀ\u0003¢\u0006\u0002\b\u0010J\u001f\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\f\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u000bHÖ\u0001J\b\u0010\u0016\u001a\u00020\u0003H\u0016R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\u0017"}, d2 = {"Lcom/squareup/kotlinpoet/Import;", "", "qualifiedName", "", "alias", "(Ljava/lang/String;Ljava/lang/String;)V", "getAlias$kotlinpoet", "()Ljava/lang/String;", "importString", "getQualifiedName$kotlinpoet", "compareTo", "", "other", "component1", "component1$kotlinpoet", "component2", "component2$kotlinpoet", "copy", "equals", "", "", "hashCode", "toString", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class Import implements Comparable<Import> {
    private final String alias;
    private final String importString;
    private final String qualifiedName;

    public static /* bridge */ /* synthetic */ Import copy$default(Import r0, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = r0.qualifiedName;
        }
        if ((i & 2) != 0) {
            str2 = r0.alias;
        }
        return r0.copy(str, str2);
    }

    public final String component1$kotlinpoet() {
        return this.qualifiedName;
    }

    public final String component2$kotlinpoet() {
        return this.alias;
    }

    public final Import copy(String qualifiedName, String str) {
        Intrinsics.checkParameterIsNotNull(qualifiedName, "qualifiedName");
        return new Import(qualifiedName, str);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof Import) {
                Import r3 = (Import) obj;
                return Intrinsics.areEqual(this.qualifiedName, r3.qualifiedName) && Intrinsics.areEqual(this.alias, r3.alias);
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        String str = this.qualifiedName;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.alias;
        return hashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public Import(String qualifiedName, String str) {
        Intrinsics.checkParameterIsNotNull(qualifiedName, "qualifiedName");
        this.qualifiedName = qualifiedName;
        this.alias = str;
        StringBuilder sb = new StringBuilder();
        sb.append(qualifiedName);
        if (str != null) {
            sb.append(" as " + str);
        }
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        this.importString = sb2;
    }

    public final String getQualifiedName$kotlinpoet() {
        return this.qualifiedName;
    }

    public /* synthetic */ Import(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2);
    }

    public final String getAlias$kotlinpoet() {
        return this.alias;
    }

    public String toString() {
        return this.importString;
    }

    @Override // java.lang.Comparable
    public int compareTo(Import other) {
        Intrinsics.checkParameterIsNotNull(other, "other");
        return this.importString.compareTo(other.importString);
    }
}
