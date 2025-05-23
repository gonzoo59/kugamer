package com.chad.library.adapter.base;

import android.widget.LinearLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.MutablePropertyReference0;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
/* compiled from: BaseQuickAdapter.kt */
@Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 16})
/* loaded from: classes.dex */
final /* synthetic */ class BaseQuickAdapter$addHeaderView$1 extends MutablePropertyReference0 {
    BaseQuickAdapter$addHeaderView$1(BaseQuickAdapter baseQuickAdapter) {
        super(baseQuickAdapter);
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public String getName() {
        return "mHeaderLayout";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(BaseQuickAdapter.class);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public String getSignature() {
        return "getMHeaderLayout()Landroid/widget/LinearLayout;";
    }

    @Override // kotlin.reflect.KProperty0
    public Object get() {
        return BaseQuickAdapter.access$getMHeaderLayout$p((BaseQuickAdapter) this.receiver);
    }

    @Override // kotlin.reflect.KMutableProperty0
    public void set(Object obj) {
        ((BaseQuickAdapter) this.receiver).mHeaderLayout = (LinearLayout) obj;
    }
}
