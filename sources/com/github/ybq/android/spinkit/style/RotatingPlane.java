package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
/* loaded from: classes.dex */
public class RotatingPlane extends RectSprite {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        setDrawBounds(clipSquare(rect));
    }

    @Override // com.github.ybq.android.spinkit.sprite.RectSprite, com.github.ybq.android.spinkit.sprite.Sprite
    public ValueAnimator getAnimation() {
        float[] fArr = {0.0f, 0.5f, 1.0f};
        return new SpriteAnimatorBuilder(this).rotateX(fArr, 0, -180, -180).rotateY(fArr, 0, 0, -180).duration(1200L).easeInOut(fArr).build();
    }

    @Override // com.github.ybq.android.spinkit.sprite.RectSprite, com.github.ybq.android.spinkit.sprite.ShapeSprite
    public void drawShape(Canvas canvas, Paint paint) {
        super.drawShape(canvas, paint);
    }
}
