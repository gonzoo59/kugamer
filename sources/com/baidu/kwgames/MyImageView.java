package com.baidu.kwgames;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
/* loaded from: classes.dex */
public class MyImageView extends AppCompatImageView {
    int[] m_arrPixels;
    Bitmap m_bmpBG;
    Bitmap m_bmpBGNew;
    boolean m_boRevertColor;

    public MyImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.m_bmpBG = null;
        this.m_bmpBGNew = null;
        this.m_boRevertColor = false;
        this.m_arrPixels = null;
    }

    public MyImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.m_bmpBG = null;
        this.m_bmpBGNew = null;
        this.m_boRevertColor = false;
        this.m_arrPixels = null;
    }

    public MyImageView(Context context) {
        super(context);
        this.m_bmpBG = null;
        this.m_bmpBGNew = null;
        this.m_boRevertColor = false;
        this.m_arrPixels = null;
    }

    public final void set_new_bg_bitmap(Bitmap bitmap, boolean z) {
        this.m_bmpBGNew = bitmap;
        this.m_boRevertColor = z;
        invalidate();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = this.m_bmpBG;
        if (bitmap != this.m_bmpBGNew) {
            if (bitmap != null) {
                bitmap.recycle();
            }
            Bitmap bitmap2 = this.m_bmpBGNew;
            this.m_bmpBG = bitmap2;
            if (this.m_boRevertColor) {
                int width = bitmap2.getWidth();
                int height = this.m_bmpBG.getHeight();
                int i = width * height;
                int[] iArr = this.m_arrPixels;
                if (iArr == null || iArr.length != i) {
                    this.m_arrPixels = new int[i];
                }
                this.m_bmpBG.getPixels(this.m_arrPixels, 0, width, 0, 0, width, height);
                for (int i2 = 0; i2 < i; i2++) {
                    int[] iArr2 = this.m_arrPixels;
                    int i3 = iArr2[i2];
                    iArr2[i2] = (255 - (i3 & 255)) | (-16777216) | ((255 - ((i3 >> 16) & 255)) << 16) | ((255 - ((i3 >> 8) & 255)) << 8);
                }
                this.m_bmpBG.setPixels(this.m_arrPixels, 0, width, 0, 0, width, height);
            }
        }
        if (this.m_bmpBG != null) {
            Rect clipBounds = canvas.getClipBounds();
            clipBounds.bottom--;
            clipBounds.right--;
            Paint paint = new Paint();
            canvas.drawBitmap(this.m_bmpBG, 0.0f, 0.0f, paint);
            paint.setColor(-16711936);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2.0f);
            canvas.drawRect(clipBounds, paint);
        }
    }
}
