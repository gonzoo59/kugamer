package com.squareup.kotlinpoet;

import javax.lang.model.element.Element;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
/* compiled from: ClassName.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"isClassOrInterface", "", "e", "Ljavax/lang/model/element/Element;", "invoke"}, k = 3, mv = {1, 1, 7})
/* loaded from: classes2.dex */
final class ClassNames$asClassName$5 extends Lambda implements Function1<Element, Boolean> {
    public static final ClassNames$asClassName$5 INSTANCE = new ClassNames$asClassName$5();

    ClassNames$asClassName$5() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Boolean invoke(Element element) {
        return Boolean.valueOf(invoke2(element));
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final boolean invoke2(Element e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        return e.getKind().isClass() || e.getKind().isInterface();
    }
}
