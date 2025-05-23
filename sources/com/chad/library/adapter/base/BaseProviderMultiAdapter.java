package com.chad.library.adapter.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.baidu.kwgames.GameSettingFloat;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: BaseProviderMultiAdapter.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00030\u0002B\u0017\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0016J\u0018\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\u0010\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0003H\u0014J\u0018\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\u001d\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u001aJ+\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00028\u00002\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0014¢\u0006\u0002\u0010\u001eJ\u0010\u0010\u001f\u001a\u00020\u00142\u0006\u0010 \u001a\u00020\u0014H\u0014J\u0018\u0010!\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\u001e\u0010\"\u001a\u00020\u00142\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u001c2\u0006\u0010 \u001a\u00020\u0014H$J\u0018\u0010#\u001a\u00020\u00032\u0006\u0010$\u001a\u00020%2\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\u0010\u0010&\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0003H\u0016J\u0010\u0010'\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0003H\u0016R'\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\t0\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006("}, d2 = {"Lcom/chad/library/adapter/base/BaseProviderMultiAdapter;", "T", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", GameSettingFloat.TAG_ATTR_DATA, "", "(Ljava/util/List;)V", "mItemProviders", "Landroid/util/SparseArray;", "Lcom/chad/library/adapter/base/provider/BaseItemProvider;", "getMItemProviders", "()Landroid/util/SparseArray;", "mItemProviders$delegate", "Lkotlin/Lazy;", "addItemProvider", "", "provider", "bindChildClick", "viewHolder", "viewType", "", "bindClick", "bindViewClickListener", "convert", "holder", "item", "(Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;Ljava/lang/Object;)V", "payloads", "", "", "(Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;Ljava/lang/Object;Ljava/util/List;)V", "getDefItemViewType", "position", "getItemProvider", "getItemType", "onCreateDefViewHolder", "parent", "Landroid/view/ViewGroup;", "onViewAttachedToWindow", "onViewDetachedFromWindow", "com.github.CymChad.brvah"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes.dex */
public abstract class BaseProviderMultiAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    private final Lazy mItemProviders$delegate;

    public BaseProviderMultiAdapter() {
        this(null, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SparseArray<BaseItemProvider<T>> getMItemProviders() {
        return (SparseArray) this.mItemProviders$delegate.getValue();
    }

    protected abstract int getItemType(List<? extends T> list, int i);

    public /* synthetic */ BaseProviderMultiAdapter(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    public BaseProviderMultiAdapter(List<T> list) {
        super(0, list);
        this.mItemProviders$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<SparseArray<BaseItemProvider<T>>>() { // from class: com.chad.library.adapter.base.BaseProviderMultiAdapter$mItemProviders$2
            @Override // kotlin.jvm.functions.Function0
            public final SparseArray<BaseItemProvider<T>> invoke() {
                return new SparseArray<>();
            }
        });
    }

    public void addItemProvider(BaseItemProvider<T> provider) {
        Intrinsics.checkParameterIsNotNull(provider, "provider");
        provider.setAdapter$com_github_CymChad_brvah(this);
        getMItemProviders().put(provider.getItemViewType(), provider);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkParameterIsNotNull(parent, "parent");
        BaseItemProvider<T> itemProvider = getItemProvider(i);
        if (itemProvider == null) {
            throw new IllegalStateException(("ViewType: " + i + " no such provider found，please use addItemProvider() first!").toString());
        }
        Context context = parent.getContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "parent.context");
        itemProvider.setContext(context);
        BaseViewHolder onCreateViewHolder = itemProvider.onCreateViewHolder(parent, i);
        itemProvider.onViewHolderCreated(onCreateViewHolder, i);
        return onCreateViewHolder;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected int getDefItemViewType(int i) {
        return getItemType(getData(), i);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected void convert(BaseViewHolder holder, T t) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        BaseItemProvider<T> itemProvider = getItemProvider(holder.getItemViewType());
        if (itemProvider == null) {
            Intrinsics.throwNpe();
        }
        itemProvider.convert(holder, t);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected void convert(BaseViewHolder holder, T t, List<? extends Object> payloads) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        Intrinsics.checkParameterIsNotNull(payloads, "payloads");
        BaseItemProvider<T> itemProvider = getItemProvider(holder.getItemViewType());
        if (itemProvider == null) {
            Intrinsics.throwNpe();
        }
        itemProvider.convert(holder, t, payloads);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void bindViewClickListener(BaseViewHolder viewHolder, int i) {
        Intrinsics.checkParameterIsNotNull(viewHolder, "viewHolder");
        super.bindViewClickListener(viewHolder, i);
        bindClick(viewHolder);
        bindChildClick(viewHolder, i);
    }

    protected BaseItemProvider<T> getItemProvider(int i) {
        return getMItemProviders().get(i);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        super.onViewAttachedToWindow((BaseProviderMultiAdapter<T>) holder);
        BaseItemProvider<T> itemProvider = getItemProvider(holder.getItemViewType());
        if (itemProvider != null) {
            itemProvider.onViewAttachedToWindow(holder);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(BaseViewHolder holder) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        super.onViewDetachedFromWindow((BaseProviderMultiAdapter<T>) holder);
        BaseItemProvider<T> itemProvider = getItemProvider(holder.getItemViewType());
        if (itemProvider != null) {
            itemProvider.onViewDetachedFromWindow(holder);
        }
    }

    protected void bindClick(final BaseViewHolder viewHolder) {
        Intrinsics.checkParameterIsNotNull(viewHolder, "viewHolder");
        if (getOnItemClickListener() == null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.chad.library.adapter.base.BaseProviderMultiAdapter$bindClick$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View it) {
                    SparseArray mItemProviders;
                    int adapterPosition = viewHolder.getAdapterPosition();
                    if (adapterPosition == -1) {
                        return;
                    }
                    int headerLayoutCount = adapterPosition - BaseProviderMultiAdapter.this.getHeaderLayoutCount();
                    int itemViewType = viewHolder.getItemViewType();
                    mItemProviders = BaseProviderMultiAdapter.this.getMItemProviders();
                    BaseViewHolder baseViewHolder = viewHolder;
                    Intrinsics.checkExpressionValueIsNotNull(it, "it");
                    ((BaseItemProvider) mItemProviders.get(itemViewType)).onClick(baseViewHolder, it, BaseProviderMultiAdapter.this.getData().get(headerLayoutCount), headerLayoutCount);
                }
            });
        }
        if (getOnItemLongClickListener() == null) {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.chad.library.adapter.base.BaseProviderMultiAdapter$bindClick$2
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View it) {
                    SparseArray mItemProviders;
                    int adapterPosition = viewHolder.getAdapterPosition();
                    if (adapterPosition == -1) {
                        return false;
                    }
                    int headerLayoutCount = adapterPosition - BaseProviderMultiAdapter.this.getHeaderLayoutCount();
                    int itemViewType = viewHolder.getItemViewType();
                    mItemProviders = BaseProviderMultiAdapter.this.getMItemProviders();
                    BaseViewHolder baseViewHolder = viewHolder;
                    Intrinsics.checkExpressionValueIsNotNull(it, "it");
                    return ((BaseItemProvider) mItemProviders.get(itemViewType)).onLongClick(baseViewHolder, it, BaseProviderMultiAdapter.this.getData().get(headerLayoutCount), headerLayoutCount);
                }
            });
        }
    }

    protected void bindChildClick(final BaseViewHolder viewHolder, int i) {
        final BaseItemProvider<T> itemProvider;
        Intrinsics.checkParameterIsNotNull(viewHolder, "viewHolder");
        if (getOnItemChildClickListener() == null) {
            final BaseItemProvider<T> itemProvider2 = getItemProvider(i);
            if (itemProvider2 == null) {
                return;
            }
            for (Number number : itemProvider2.getChildClickViewIds()) {
                View findViewById = viewHolder.itemView.findViewById(number.intValue());
                if (findViewById != null) {
                    if (!findViewById.isClickable()) {
                        findViewById.setClickable(true);
                    }
                    findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.chad.library.adapter.base.BaseProviderMultiAdapter$bindChildClick$$inlined$forEach$lambda$1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View v) {
                            int adapterPosition = viewHolder.getAdapterPosition();
                            if (adapterPosition == -1) {
                                return;
                            }
                            int headerLayoutCount = adapterPosition - BaseProviderMultiAdapter.this.getHeaderLayoutCount();
                            BaseItemProvider baseItemProvider = itemProvider2;
                            BaseViewHolder baseViewHolder = viewHolder;
                            Intrinsics.checkExpressionValueIsNotNull(v, "v");
                            baseItemProvider.onChildClick(baseViewHolder, v, BaseProviderMultiAdapter.this.getData().get(headerLayoutCount), headerLayoutCount);
                        }
                    });
                }
            }
        }
        if (getOnItemChildLongClickListener() != null || (itemProvider = getItemProvider(i)) == null) {
            return;
        }
        for (Number number2 : itemProvider.getChildLongClickViewIds()) {
            View findViewById2 = viewHolder.itemView.findViewById(number2.intValue());
            if (findViewById2 != null) {
                if (!findViewById2.isLongClickable()) {
                    findViewById2.setLongClickable(true);
                }
                findViewById2.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.chad.library.adapter.base.BaseProviderMultiAdapter$bindChildClick$$inlined$forEach$lambda$2
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View v) {
                        int adapterPosition = viewHolder.getAdapterPosition();
                        if (adapterPosition == -1) {
                            return false;
                        }
                        int headerLayoutCount = adapterPosition - BaseProviderMultiAdapter.this.getHeaderLayoutCount();
                        BaseItemProvider baseItemProvider = itemProvider;
                        BaseViewHolder baseViewHolder = viewHolder;
                        Intrinsics.checkExpressionValueIsNotNull(v, "v");
                        return baseItemProvider.onChildLongClick(baseViewHolder, v, BaseProviderMultiAdapter.this.getData().get(headerLayoutCount), headerLayoutCount);
                    }
                });
            }
        }
    }
}
