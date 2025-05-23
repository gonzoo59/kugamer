package com.chad.library.adapter.base.diff;

import androidx.recyclerview.widget.DiffUtil;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BrvahAsyncDiffer.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "T", "run"}, k = 3, mv = {1, 1, 16})
/* loaded from: classes.dex */
public final class BrvahAsyncDiffer$submitList$1 implements Runnable {
    final /* synthetic */ Runnable $commitCallback;
    final /* synthetic */ List $newList;
    final /* synthetic */ List $oldList;
    final /* synthetic */ int $runGeneration;
    final /* synthetic */ BrvahAsyncDiffer this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BrvahAsyncDiffer$submitList$1(BrvahAsyncDiffer brvahAsyncDiffer, List list, List list2, int i, Runnable runnable) {
        this.this$0 = brvahAsyncDiffer;
        this.$oldList = list;
        this.$newList = list2;
        this.$runGeneration = i;
        this.$commitCallback = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Executor executor;
        final DiffUtil.DiffResult calculateDiff = DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: com.chad.library.adapter.base.diff.BrvahAsyncDiffer$submitList$1$result$1
            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public int getOldListSize() {
                return BrvahAsyncDiffer$submitList$1.this.$oldList.size();
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public int getNewListSize() {
                return BrvahAsyncDiffer$submitList$1.this.$newList.size();
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public boolean areItemsTheSame(int i, int i2) {
                BrvahAsyncDifferConfig brvahAsyncDifferConfig;
                Object obj = BrvahAsyncDiffer$submitList$1.this.$oldList.get(i);
                Object obj2 = BrvahAsyncDiffer$submitList$1.this.$newList.get(i2);
                if (obj == null || obj2 == null) {
                    return obj == null && obj2 == null;
                }
                brvahAsyncDifferConfig = BrvahAsyncDiffer$submitList$1.this.this$0.config;
                return brvahAsyncDifferConfig.getDiffCallback().areItemsTheSame(obj, obj2);
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public boolean areContentsTheSame(int i, int i2) {
                BrvahAsyncDifferConfig brvahAsyncDifferConfig;
                Object obj = BrvahAsyncDiffer$submitList$1.this.$oldList.get(i);
                Object obj2 = BrvahAsyncDiffer$submitList$1.this.$newList.get(i2);
                if (obj != null && obj2 != null) {
                    brvahAsyncDifferConfig = BrvahAsyncDiffer$submitList$1.this.this$0.config;
                    return brvahAsyncDifferConfig.getDiffCallback().areContentsTheSame(obj, obj2);
                } else if (obj == null && obj2 == null) {
                    return true;
                } else {
                    throw new AssertionError();
                }
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public Object getChangePayload(int i, int i2) {
                BrvahAsyncDifferConfig brvahAsyncDifferConfig;
                Object obj = BrvahAsyncDiffer$submitList$1.this.$oldList.get(i);
                Object obj2 = BrvahAsyncDiffer$submitList$1.this.$newList.get(i2);
                if (obj != null && obj2 != null) {
                    brvahAsyncDifferConfig = BrvahAsyncDiffer$submitList$1.this.this$0.config;
                    return brvahAsyncDifferConfig.getDiffCallback().getChangePayload(obj, obj2);
                }
                throw new AssertionError();
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(calculateDiff, "DiffUtil.calculateDiff(o…         }\n            })");
        executor = this.this$0.mMainThreadExecutor;
        executor.execute(new Runnable() { // from class: com.chad.library.adapter.base.diff.BrvahAsyncDiffer$submitList$1.1
            @Override // java.lang.Runnable
            public final void run() {
                int i;
                i = BrvahAsyncDiffer$submitList$1.this.this$0.mMaxScheduledGeneration;
                if (i == BrvahAsyncDiffer$submitList$1.this.$runGeneration) {
                    BrvahAsyncDiffer$submitList$1.this.this$0.latchList(BrvahAsyncDiffer$submitList$1.this.$newList, calculateDiff, BrvahAsyncDiffer$submitList$1.this.$commitCallback);
                }
            }
        });
    }
}
