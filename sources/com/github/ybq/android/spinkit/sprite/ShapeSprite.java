package com.github.ybq.android.spinkit.sprite;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
/* loaded from: classes.dex */
public abstract class ShapeSprite extends Sprite {
    private int mBaseColor;
    private Paint mPaint;
    private int mUseColor;

    public abstract void drawShape(Canvas canvas, Paint paint);

    public ShapeSprite() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setColor(-1);
    }

    @Override // com.github.ybq.android.spinkit.sprite.Sprite
    public void setColor(int i) {
        this.mBaseColor = i;
        updateUseColor();
    }

    @Override // com.github.ybq.android.spinkit.sprite.Sprite
    public int getColor() {
        return this.mBaseColor;
    }

    public int getUseColor() {
        return this.mUseColor;
    }

    @Override // com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        super.setAlpha(i);
        updateUseColor();
    }

    private void updateUseColor() {
        int alpha = getAlpha();
        int i = this.mBaseColor;
        this.mUseColor = ((((i >>> 24) * (alpha + (alpha >> 7))) >> 8) << 24) | ((i << 8) >>> 8);
    }

    @Override // com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    @Override // com.github.ybq.android.spinkit.sprite.Sprite
    protected final void drawSelf(Canvas canvas) {
        this.mPaint.setColor(this.mUseColor);
        drawShape(canvas, this.mPaint);
    }
}
