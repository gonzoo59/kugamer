package com.baidu.kwgames;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.List;
/* loaded from: classes.dex */
public class PreSetAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ItemClickListener mClickListener;
    private final List<String> mData;
    private final LayoutInflater mInflater;
    private boolean m_boShowEdit;
    private Handler m_handleMsg;
    private String m_strPresetDir;

    /* loaded from: classes.dex */
    public interface ItemClickListener {
        void onItemClick(View view, int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PreSetAdapter(Context context, List<String> list, String str) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = list;
        this.m_strPresetDir = str;
    }

    public boolean get_enable_edit() {
        return this.m_boShowEdit;
    }

    public void toggle_enable_edit() {
        this.m_boShowEdit = !this.m_boShowEdit;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this.mInflater.inflate(R.layout.pre_set_row, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final String str = this.mData.get(i);
        viewHolder.myTextView.setText(str);
        if (this.m_boShowEdit) {
            viewHolder.m_viewDel.setVisibility(0);
            viewHolder.m_viewDel.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.PreSetAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    try {
                        PreSetAdapter.this.mData.remove(i);
                        File file = new File(PreSetAdapter.this.m_strPresetDir + str);
                        if (file.exists()) {
                            file.delete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    PreSetAdapter.this.notifyDataSetChanged();
                }
            });
            return;
        }
        viewHolder.m_viewDel.setVisibility(8);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mData.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getItem(int i) {
        return this.mData.get(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /* loaded from: classes.dex */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView m_viewDel;
        TextView myTextView;

        ViewHolder(View view) {
            super(view);
            this.myTextView = (TextView) view.findViewById(R.id.title);
            this.m_viewDel = (ImageView) view.findViewById(R.id.m_delete);
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (PreSetAdapter.this.mClickListener != null) {
                PreSetAdapter.this.mClickListener.onItemClick(view, getAbsoluteAdapterPosition());
            }
        }
    }
}
