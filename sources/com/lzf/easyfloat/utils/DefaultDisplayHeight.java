package com.lzf.easyfloat.utils;

import android.content.Context;
import com.lzf.easyfloat.interfaces.OnDisplayHeight;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: DefaultDisplayHeight.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/lzf/easyfloat/utils/DefaultDisplayHeight;", "Lcom/lzf/easyfloat/interfaces/OnDisplayHeight;", "()V", "getDisplayRealHeight", "", "context", "Landroid/content/Context;", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class DefaultDisplayHeight implements OnDisplayHeight {
    @Override // com.lzf.easyfloat.interfaces.OnDisplayHeight
    public int getDisplayRealHeight(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        return DisplayUtils.INSTANCE.rejectedNavHeight(context);
    }
}
