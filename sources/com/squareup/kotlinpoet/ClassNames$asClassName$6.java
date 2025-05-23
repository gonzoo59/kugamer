package com.squareup.kotlinpoet;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
/* compiled from: ClassName.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"getPackage", "Ljavax/lang/model/element/PackageElement;", "type", "Ljavax/lang/model/element/Element;", "invoke"}, k = 3, mv = {1, 1, 7})
/* loaded from: classes2.dex */
final class ClassNames$asClassName$6 extends Lambda implements Function1<Element, PackageElement> {
    public static final ClassNames$asClassName$6 INSTANCE = new ClassNames$asClassName$6();

    ClassNames$asClassName$6() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final PackageElement invoke(Element type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        while (!Intrinsics.areEqual(type.getKind(), ElementKind.PACKAGE)) {
            type = type.getEnclosingElement();
            Intrinsics.checkExpressionValueIsNotNull(type, "t.enclosingElement");
        }
        if (type != null) {
            return (PackageElement) type;
        }
        throw new TypeCastException("null cannot be cast to non-null type javax.lang.model.element.PackageElement");
    }
}
