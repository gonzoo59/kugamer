package com.jieli.component.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
/* loaded from: classes2.dex */
public class CommonDecoration extends RecyclerView.ItemDecoration {
    private static final int[] attrs = {16843284};
    private int dividerHeight = 1;
    private Drawable mDivider;
    private int orientation;

    public CommonDecoration(Context context, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs);
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        if (drawable != null) {
            init(i, drawable, drawable.getIntrinsicHeight());
        }
        obtainStyledAttributes.recycle();
    }

    public CommonDecoration(Context context, int i, int i2, int i3) {
        init(i, new ColorDrawable(i2), i3);
    }

    public CommonDecoration(Context context, int i, Drawable drawable) {
        init(i, drawable, drawable.getIntrinsicHeight());
    }

    private void init(int i, Drawable drawable, int i2) {
        this.mDivider = drawable;
        this.orientation = i;
        this.dividerHeight = i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        drawHDeraction(canvas, recyclerView);
        drawVDeraction(canvas, recyclerView);
    }

    private void drawHDeraction(Canvas canvas, RecyclerView recyclerView) {
        int paddingLeft = recyclerView.getPaddingLeft();
        int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            int bottom = childAt.getBottom() + ((RecyclerView.LayoutParams) childAt.getLayoutParams()).bottomMargin;
            this.mDivider.setBounds(paddingLeft, bottom, width, this.dividerHeight + bottom);
            this.mDivider.draw(canvas);
        }
    }

    private void drawVDeraction(Canvas canvas, RecyclerView recyclerView) {
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
        if (this.orientation == 0) {
            rect.set(0, 0, this.dividerHeight, 0);
        } else {
            rect.set(0, 0, 0, this.dividerHeight);
        }
    }
}
