package com.squareup.kotlinpoet;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: TypeVariableName.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0007¢\u0006\u0002\b\u0003\u001a\u0011\u0010\u0000\u001a\u00020\u0001*\u00020\u0004H\u0007¢\u0006\u0002\b\u0003¨\u0006\u0005"}, d2 = {"asTypeVariableName", "Lcom/squareup/kotlinpoet/TypeVariableName;", "Ljavax/lang/model/element/TypeParameterElement;", "get", "Ljavax/lang/model/type/TypeVariable;", "kotlinpoet"}, k = 2, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class TypeVariableNames {
    public static final TypeVariableName get(TypeVariable receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        TypeParameterElement asElement = receiver.asElement();
        if (asElement != null) {
            return get(asElement);
        }
        throw new TypeCastException("null cannot be cast to non-null type javax.lang.model.element.TypeParameterElement");
    }

    public static final TypeVariableName get(TypeParameterElement receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        String obj = receiver.getSimpleName().toString();
        List<TypeMirror> bounds = receiver.getBounds();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(bounds, 10));
        for (TypeMirror typeMirror : bounds) {
            arrayList.add(TypeNames.get(typeMirror));
        }
        return TypeVariableName.Companion.of$kotlinpoet(obj, arrayList, null);
    }
}
