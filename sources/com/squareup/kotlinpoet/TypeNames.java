package com.squareup.kotlinpoet;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
/* compiled from: TypeName.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0011\u0010\f\u001a\u00020\r*\u00020\u000eH\u0007¢\u0006\u0002\b\u000f\u001a\u0011\u0010\f\u001a\u00020\r*\u00020\u0010H\u0007¢\u0006\u0002\b\u000f\u001a\u0015\u0010\f\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0011H\u0007¢\u0006\u0002\b\u000f\"\u0010\u0010\u0000\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0002\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0003\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0004\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0005\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0006\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0007\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\b\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\t\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\n\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u000b\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"ANY", "Lcom/squareup/kotlinpoet/ClassName;", "ARRAY", "BOOLEAN", "BYTE", "CHAR", "DOUBLE", "FLOAT", "INT", "LONG", "SHORT", "UNIT", "asTypeName", "Lcom/squareup/kotlinpoet/TypeName;", "Ljava/lang/reflect/Type;", "get", "Ljavax/lang/model/type/TypeMirror;", "Lkotlin/reflect/KClass;", "kotlinpoet"}, k = 2, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class TypeNames {
    public static final ClassName ANY = new ClassName("kotlin", "Any", new String[0]);
    public static final ClassName ARRAY = new ClassName("kotlin", "Array", new String[0]);
    public static final ClassName UNIT = ClassNames.get(Reflection.getOrCreateKotlinClass(Unit.class));
    public static final ClassName BOOLEAN = new ClassName("kotlin", "Boolean", new String[0]);
    public static final ClassName BYTE = new ClassName("kotlin", "Byte", new String[0]);
    public static final ClassName SHORT = new ClassName("kotlin", "Short", new String[0]);
    public static final ClassName INT = new ClassName("kotlin", "Int", new String[0]);
    public static final ClassName LONG = new ClassName("kotlin", "Long", new String[0]);
    public static final ClassName CHAR = new ClassName("kotlin", "Char", new String[0]);
    public static final ClassName FLOAT = new ClassName("kotlin", "Float", new String[0]);
    public static final ClassName DOUBLE = new ClassName("kotlin", "Double", new String[0]);

    public static final TypeName get(TypeMirror receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return TypeName.Companion.get$kotlinpoet(receiver, new LinkedHashMap());
    }

    public static final ClassName get(KClass<?> receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return ClassNames.get(receiver);
    }

    public static final TypeName get(Type receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return TypeName.Companion.get$kotlinpoet(receiver, new LinkedHashMap());
    }
}
