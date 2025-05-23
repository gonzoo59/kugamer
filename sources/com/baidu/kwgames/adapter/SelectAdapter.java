package com.baidu.kwgames.adapter;

import android.content.Context;
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
public class SelectAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ItemClickListener mClickListener;
    private final List<String> mData;
    private final LayoutInflater mInflater;
    private final List<Boolean> m_arrItemSel;

    /* loaded from: classes.dex */
    public interface ItemClickListener {
        void onItemClick(View view, int i);
    }

    public SelectAdapter(Context context, List<String> list, List<Boolean> list2) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = list;
        this.m_arrItemSel = list2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this.mInflater.inflate(R.layout.pre_set_row, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.myTextView.setText(this.mData.get(i));
        viewHolder.myTextView.setTextColor(NEAT.get_color(this.m_arrItemSel.get(i).booleanValue() ? R.color.colorGreen : R.color.colorWhite));
        viewHolder.m_viewDel.setVisibility(0);
        viewHolder.m_viewDel.setBackgroundResource(this.m_arrItemSel.get(i).booleanValue() ? R.mipmap.ic_device_sel : R.mipmap.ic_device_normal);
        viewHolder.m_viewDel.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.adapter.SelectAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SelectAdapter.this.m_arrItemSel.set(i, Boolean.valueOf(!((Boolean) SelectAdapter.this.m_arrItemSel.get(i)).booleanValue()));
                SelectAdapter.this.notifyDataSetChanged();
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
            if (SelectAdapter.this.mClickListener != null) {
                SelectAdapter.this.mClickListener.onItemClick(view, getAbsoluteAdapterPosition());
            }
        }
    }
}
