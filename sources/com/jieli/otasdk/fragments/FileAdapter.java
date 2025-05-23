package com.jieli.otasdk.fragments;

import android.widget.TextView;
import com.baidu.kwgames.GameSettingFloat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jieli.otasdk.R;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: FileAdapter.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0015\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0002H\u0014J\u0006\u0010\r\u001a\u00020\u0002J\u000e\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\bR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/jieli/otasdk/fragments/FileAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Ljava/io/File;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", GameSettingFloat.TAG_ATTR_DATA, "", "(Ljava/util/List;)V", "selectedIndex", "", "convert", "", "holder", "item", "getSelectedItem", "setSelectedIndex", "pos", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class FileAdapter extends BaseQuickAdapter<File, BaseViewHolder> {
    private int selectedIndex;

    public FileAdapter(List<File> list) {
        super(R.layout.item_file_list, list);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder holder, File item) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        Intrinsics.checkParameterIsNotNull(item, "item");
        TextView textView = (TextView) holder.getView(R.id.tv_item_file_name);
        textView.setText(item.getName());
        if (holder.getAdapterPosition() - getHeaderLayoutCount() == this.selectedIndex) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_blue, 0);
        } else {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    public final void setSelectedIndex(int i) {
        if (this.selectedIndex != i) {
            this.selectedIndex = i;
            notifyDataSetChanged();
        }
    }

    public final File getSelectedItem() {
        return getItem(this.selectedIndex);
    }
}
