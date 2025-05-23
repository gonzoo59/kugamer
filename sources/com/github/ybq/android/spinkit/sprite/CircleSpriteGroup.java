package com.github.ybq.android.spinkit.sprite;

import android.graphics.Canvas;
import android.graphics.Rect;
/* loaded from: classes.dex */
public abstract class CircleSpriteGroup extends SpriteGroup {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup
    public void drawChild(Canvas canvas) {
        for (int i = 0; i < getChildCount(); i++) {
            Sprite childAt = getChildAt(i);
            int save = canvas.save();
            canvas.rotate((i * 360) / getChildCount(), getBounds().centerX(), getBounds().centerY());
            childAt.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect clipSquare = clipSquare(rect);
        int width = (int) (((clipSquare.width() * 3.141592653589793d) / 3.5999999046325684d) / getChildCount());
        int centerX = clipSquare.centerX() - width;
        int centerX2 = clipSquare.centerX() + width;
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setDrawBounds(centerX, clipSquare.top, centerX2, clipSquare.top + (width * 2));
        }
    }
}
