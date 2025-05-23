package com.baidu.kwgames.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.baidu.kwgames.R;
import com.baidu.kwgames.util.NEAT;
import java.util.List;
/* loaded from: classes.dex */
public class BackupAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ItemClickListener mClickListener;
    private final List<String> mData;
    private final LayoutInflater mInflater;
    private boolean m_boShowEdit;
    private Handler m_handleMsg;
    private int m_nCurItem;
    private String m_strPresetDir;

    /* loaded from: classes.dex */
    public interface ItemClickListener {
        void onItemClick(View view, int i);
    }

    public BackupAdapter(Context context, List<String> list, String str) {
        this.m_nCurItem = -1;
        this.mInflater = LayoutInflater.from(context);
        this.mData = list;
        this.m_strPresetDir = str;
        this.m_nCurItem = -1;
    }

    public void set_cur_item(int i) {
        this.m_nCurItem = i;
        notifyDataSetChanged();
    }

    public int get_cur_item() {
        return this.m_nCurItem;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this.mInflater.inflate(R.layout.pre_set_row, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.myTextView.setText(this.mData.get(i));
        viewHolder.myTextView.setTextColor(NEAT.get_color(this.m_nCurItem == i ? R.color.colorGreen : R.color.colorWhite));
        viewHolder.m_viewDel.setVisibility(0);
        viewHolder.m_viewDel.setBackgroundResource(this.m_nCurItem == i ? R.mipmap.ic_device_sel : R.mipmap.ic_device_normal);
        viewHolder.m_viewDel.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.adapter.BackupAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BackupAdapter.this.m_nCurItem = i;
                if (BackupAdapter.this.mClickListener != null) {
                    BackupAdapter.this.mClickListener.onItemClick(view, i);
                }
                BackupAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mData.size();
    }

    String getItem(int i) {
        return this.mData.get(i);
    }

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
            if (BackupAdapter.this.mClickListener != null) {
                BackupAdapter.this.mClickListener.onItemClick(view, getAbsoluteAdapterPosition());
            }
        }
    }
}
