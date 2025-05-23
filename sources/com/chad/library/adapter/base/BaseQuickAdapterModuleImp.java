package com.chad.library.adapter.base;

import com.chad.library.adapter.base.module.BaseDraggableModule;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.BaseUpFetchModule;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BaseQuickAdapter.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bb\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005H\u0016J\u0018\u0010\u0006\u001a\u00020\u00072\u000e\u0010\u0004\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005H\u0016J\u0018\u0010\b\u001a\u00020\t2\u000e\u0010\u0004\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005H\u0016¨\u0006\n"}, d2 = {"Lcom/chad/library/adapter/base/BaseQuickAdapterModuleImp;", "", "addDraggableModule", "Lcom/chad/library/adapter/base/module/BaseDraggableModule;", "baseQuickAdapter", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "addLoadMoreModule", "Lcom/chad/library/adapter/base/module/BaseLoadMoreModule;", "addUpFetchModule", "Lcom/chad/library/adapter/base/module/BaseUpFetchModule;", "com.github.CymChad.brvah"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes.dex */
public interface BaseQuickAdapterModuleImp {
    BaseDraggableModule addDraggableModule(BaseQuickAdapter<?, ?> baseQuickAdapter);

    BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter<?, ?> baseQuickAdapter);

    BaseUpFetchModule addUpFetchModule(BaseQuickAdapter<?, ?> baseQuickAdapter);

    /* compiled from: BaseQuickAdapter.kt */
    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 16})
    /* loaded from: classes.dex */
    public static final class DefaultImpls {
        public static BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapterModuleImp baseQuickAdapterModuleImp, BaseQuickAdapter<?, ?> baseQuickAdapter) {
            Intrinsics.checkParameterIsNotNull(baseQuickAdapter, "baseQuickAdapter");
            return new BaseLoadMoreModule(baseQuickAdapter);
        }

        public static BaseUpFetchModule addUpFetchModule(BaseQuickAdapterModuleImp baseQuickAdapterModuleImp, BaseQuickAdapter<?, ?> baseQuickAdapter) {
            Intrinsics.checkParameterIsNotNull(baseQuickAdapter, "baseQuickAdapter");
            return new BaseUpFetchModule(baseQuickAdapter);
        }

        public static BaseDraggableModule addDraggableModule(BaseQuickAdapterModuleImp baseQuickAdapterModuleImp, BaseQuickAdapter<?, ?> baseQuickAdapter) {
            Intrinsics.checkParameterIsNotNull(baseQuickAdapter, "baseQuickAdapter");
            return new BaseDraggableModule(baseQuickAdapter);
        }
    }
}
