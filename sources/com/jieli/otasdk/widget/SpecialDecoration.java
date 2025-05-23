package com.jieli.otasdk.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.jieli.component.utils.ValueUtil;
/* loaded from: classes2.dex */
public class SpecialDecoration extends RecyclerView.ItemDecoration {
    private static final int[] attrs = {16843284};
    private int dividerHeight = 1;
    private Drawable mDivider;
    private Drawable mDivider2;
    private int orientation;

    public SpecialDecoration(Context context, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs);
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        init(i, drawable, drawable.getIntrinsicHeight());
        obtainStyledAttributes.recycle();
    }

    public SpecialDecoration(Context context, int i, int i2, int i3) {
        init(i, new ColorDrawable(i2), i3);
    }

    public SpecialDecoration(Context context, int i, Drawable drawable) {
        init(i, drawable, drawable.getIntrinsicHeight());
    }

    private void init(int i, Drawable drawable, int i2) {
        this.mDivider = drawable;
        this.orientation = i;
        this.dividerHeight = i2;
        this.mDivider2 = new ColorDrawable(-1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        if (this.orientation == 0) {
            drawHDeraction(canvas, recyclerView, state);
        } else {
            drawVDeraction(canvas, recyclerView, state);
        }
    }

    private void drawHDeraction(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        int paddingLeft = recyclerView.getPaddingLeft() + ValueUtil.dp2px(recyclerView.getContext(), 16);
        int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            if (recyclerView.getChildAdapterPosition(childAt) < state.getItemCount() - 1) {
                int bottom = childAt.getBottom() + ((RecyclerView.LayoutParams) childAt.getLayoutParams()).bottomMargin;
                int i2 = this.dividerHeight + bottom;
                this.mDivider2.setBounds(0, bottom, ValueUtil.dp2px(recyclerView.getContext(), 16), i2);
                this.mDivider2.draw(canvas);
                this.mDivider.setBounds(paddingLeft, bottom, width, i2);
                this.mDivider.draw(canvas);
            }
        }
    }

    private void drawVDeraction(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        int paddingTop = recyclerView.getPaddingTop();
        int height = recyclerView.getHeight() - recyclerView.getPaddingBottom();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            int right = childAt.getRight() + ((RecyclerView.LayoutParams) childAt.getLayoutParams()).rightMargin;
            this.mDivider.setBounds(right, paddingTop, this.dividerHeight + right, height);
            this.mDivider.draw(canvas);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (this.mDivider == null) {
            rect.set(0, 0, 0, 0);
        } else if (this.orientation == 0) {
            if (recyclerView.getChildAdapterPosition(view) < state.getItemCount() - 1) {
                rect.set(0, 0, 0, this.dividerHeight);
            } else {
                rect.set(0, 0, 0, 0);
            }
        } else {
            rect.set(0, 0, this.dividerHeight, 0);
        }
    }
}
