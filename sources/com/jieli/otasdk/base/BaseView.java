package com.jieli.otasdk.base;

import com.jieli.otasdk.base.BasePresenter;
import kotlin.Metadata;
/* compiled from: BaseView.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003J\u0015\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lcom/jieli/otasdk/base/BaseView;", "T", "Lcom/jieli/otasdk/base/BasePresenter;", "", "setPresenter", "", "presenter", "(Lcom/jieli/otasdk/base/BasePresenter;)V", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public interface BaseView<T extends BasePresenter> {
    void setPresenter(T t);
}
