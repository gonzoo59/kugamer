package com.squareup.kotlinpoet;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: NameAllocator.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u001a\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\u0010\u0000\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B)\b\u0002\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u0000H\u0016J\u000e\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\bJ\u001a\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\bH\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/squareup/kotlinpoet/NameAllocator;", "", "()V", "allocatedNames", "", "", "tagToName", "", "", "(Ljava/util/Set;Ljava/util/Map;)V", "clone", "get", "tag", "newName", "suggestion", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class NameAllocator implements Cloneable {
    private final Set<String> allocatedNames;
    private final Map<Object, String> tagToName;

    public final String newName(String str) {
        return newName$default(this, str, null, 2, null);
    }

    private NameAllocator(Set<String> set, Map<Object, String> map) {
        this.allocatedNames = set;
        this.tagToName = map;
    }

    public NameAllocator() {
        this(new LinkedHashSet(), new LinkedHashMap());
    }

    public static /* bridge */ /* synthetic */ String newName$default(NameAllocator nameAllocator, String str, Object obj, int i, Object obj2) {
        if ((i & 2) != 0) {
            obj = UUID.randomUUID().toString();
            Intrinsics.checkExpressionValueIsNotNull(obj, "UUID.randomUUID().toString()");
        }
        return nameAllocator.newName(str, obj);
    }

    public final String newName(String suggestion, Object tag) {
        String javaIdentifier;
        Intrinsics.checkParameterIsNotNull(suggestion, "suggestion");
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        javaIdentifier = NameAllocatorKt.toJavaIdentifier(suggestion);
        while (true) {
            if (!UtilKt.isKeyword(javaIdentifier) && this.allocatedNames.add(javaIdentifier)) {
                break;
            }
            javaIdentifier = javaIdentifier + "_";
        }
        String put = this.tagToName.put(tag, javaIdentifier);
        if (put == null) {
            return javaIdentifier;
        }
        this.tagToName.put(tag, put);
        throw new IllegalArgumentException("tag " + tag + " cannot be used for both '" + put + "' and '" + javaIdentifier + '\'');
    }

    public final String get(Object tag) {
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        String str = this.tagToName.get(tag);
        if (str != null) {
            return str;
        }
        throw new IllegalArgumentException("unknown tag: " + tag);
    }

    /* renamed from: clone */
    public NameAllocator m138clone() {
        return new NameAllocator(CollectionsKt.toMutableSet(this.allocatedNames), MapsKt.toMutableMap(this.tagToName));
    }
}
