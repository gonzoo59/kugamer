package com.squareup.kotlinpoet;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ParameterizedTypeName.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0011\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0007¢\u0006\u0002\b\u0003¨\u0006\u0004"}, d2 = {"asParameterizedTypeName", "Lcom/squareup/kotlinpoet/ParameterizedTypeName;", "Ljava/lang/reflect/ParameterizedType;", "get", "kotlinpoet"}, k = 2, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class ParameterizedTypeNames {
    public static final ParameterizedTypeName get(ParameterizedType receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return ParameterizedTypeName.Companion.get$kotlinpoet(receiver, new LinkedHashMap());
    }
}
