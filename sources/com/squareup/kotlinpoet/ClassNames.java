package com.squareup.kotlinpoet;

import java.util.ArrayList;
import javax.lang.model.element.Element;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.text.StringsKt;
/* compiled from: ClassName.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0002H\u0007¢\u0006\u0002\b\u0003\u001a\u0011\u0010\u0000\u001a\u00020\u0001*\u00020\u0004H\u0007¢\u0006\u0002\b\u0003\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0005H\u0007¢\u0006\u0002\b\u0003¨\u0006\u0006"}, d2 = {"asClassName", "Lcom/squareup/kotlinpoet/ClassName;", "Ljava/lang/Class;", "get", "Ljavax/lang/model/element/TypeElement;", "Lkotlin/reflect/KClass;", "kotlinpoet"}, k = 2, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class ClassNames {
    public static final ClassName get(Class<?> receiver) {
        ArrayList arrayList;
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        if (!(!receiver.isPrimitive())) {
            throw new IllegalArgumentException("primitive types cannot be represented as a ClassName".toString());
        }
        if (!(!Intrinsics.areEqual(Void.TYPE, receiver))) {
            throw new IllegalArgumentException("'void' type cannot be represented as a ClassName".toString());
        }
        if (!(!receiver.isArray())) {
            throw new IllegalArgumentException("array types cannot be represented as a ClassName".toString());
        }
        ArrayList arrayList2 = new ArrayList();
        while (true) {
            arrayList = arrayList2;
            arrayList.add(receiver.getSimpleName());
            Class<?> enclosingClass = receiver.getEnclosingClass();
            if (enclosingClass == null) {
                break;
            }
            receiver = enclosingClass;
        }
        int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) receiver.getName(), '.', 0, false, 6, (Object) null);
        if (lastIndexOf$default != -1) {
            String name = receiver.getName();
            if (name == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String substring = name.substring(0, lastIndexOf$default);
            Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            arrayList.add(substring);
        }
        CollectionsKt.reverse(arrayList2);
        return new ClassName(arrayList2, false, null, 6, null);
    }

    public static final ClassName get(KClass<?> receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        String qualifiedName = receiver.getQualifiedName();
        if (qualifiedName != null) {
            return ClassName.Companion.bestGuess(qualifiedName);
        }
        throw new IllegalArgumentException("" + receiver + " cannot be represented as a ClassName");
    }

    public static final ClassName get(TypeElement receiver) {
        boolean isOneOf;
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        ClassNames$asClassName$5 classNames$asClassName$5 = ClassNames$asClassName$5.INSTANCE;
        ClassNames$asClassName$6 classNames$asClassName$6 = ClassNames$asClassName$6.INSTANCE;
        ArrayList arrayList = new ArrayList();
        Element element = (Element) receiver;
        Element element2 = element;
        while (classNames$asClassName$5.invoke2(element2)) {
            if (element2 != null) {
                TypeElement typeElement = (TypeElement) element2;
                isOneOf = UtilKt.isOneOf(typeElement.getNestingKind(), NestingKind.TOP_LEVEL, NestingKind.MEMBER, (r16 & 4) != 0 ? null : null, (r16 & 8) != 0 ? null : null, (r16 & 16) != 0 ? null : null, (r16 & 32) != 0 ? null : null);
                if (!isOneOf) {
                    throw new IllegalArgumentException("unexpected type testing".toString());
                }
                arrayList.add(typeElement.getSimpleName().toString());
                element2 = typeElement.getEnclosingElement();
                Intrinsics.checkExpressionValueIsNotNull(element2, "eType.enclosingElement");
            } else {
                throw new TypeCastException("null cannot be cast to non-null type javax.lang.model.element.TypeElement");
            }
        }
        arrayList.add(classNames$asClassName$6.invoke(element).getQualifiedName().toString());
        CollectionsKt.reverse(arrayList);
        return new ClassName(arrayList, false, null, 6, null);
    }
}
