package com.baidu.kwgames.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.blankj.utilcode.util.ImageUtils;
/* loaded from: classes.dex */
public class FastView extends View {
    Bitmap m_bmpImage;
    Bitmap m_cacheBitmap;
    Canvas m_cacheCanvas;
    int m_nBitmapID;
    int m_nNewBitmapID;
    private Paint m_paint;

    public FastView(Context context) {
        this(context, null);
    }

    public FastView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.m_paint = null;
        this.m_cacheBitmap = null;
        this.m_cacheCanvas = null;
        this.m_nBitmapID = -1;
        this.m_nNewBitmapID = -1;
        this.m_bmpImage = null;
        init();
    }

    private void init() {
        this.m_paint = new Paint();
    }

    public void set_image(int i) {
        if (this.m_nNewBitmapID != i) {
            this.m_nNewBitmapID = i;
            this.m_bmpImage = ImageUtils.getBitmap(i);
            Bitmap bitmap = this.m_cacheBitmap;
            if (bitmap != null) {
                create_image(bitmap.getWidth(), this.m_cacheBitmap.getHeight());
            }
            invalidate();
        }
    }

    void create_image(int i, int i2) {
        Bitmap bitmap = this.m_cacheBitmap;
        if (bitmap != null) {
            bitmap.recycle();
        }
        this.m_cacheBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        this.m_cacheCanvas = canvas;
        canvas.setBitmap(this.m_cacheBitmap);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i == 0 || i2 == 0) {
            return;
        }
        create_image(i, i2);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int i = this.m_nNewBitmapID;
        if (i != this.m_nBitmapID && this.m_cacheCanvas != null) {
            this.m_nBitmapID = i;
            if (this.m_bmpImage != null) {
                this.m_cacheCanvas.drawBitmap(this.m_bmpImage, new Rect(0, 0, this.m_bmpImage.getWidth(), this.m_bmpImage.getHeight()), new Rect(0, 0, getWidth(), getHeight()), this.m_paint);
            }
        }
        Bitmap bitmap = this.m_cacheBitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.m_paint);
        }
    }
}
