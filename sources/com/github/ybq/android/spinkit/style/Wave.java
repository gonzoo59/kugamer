package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteGroup;
/* loaded from: classes.dex */
public class Wave extends SpriteGroup {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup
    public Sprite[] onCreateChild() {
        WaveItem[] waveItemArr = new WaveItem[5];
        for (int i = 0; i < 5; i++) {
            waveItemArr[i] = new WaveItem();
            waveItemArr[i].setAnimationDelay((i * 100) - 1200);
        }
        return waveItemArr;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect clipSquare = clipSquare(rect);
        int width = clipSquare.width() / getChildCount();
        int width2 = ((clipSquare.width() / 5) * 3) / 5;
        for (int i = 0; i < getChildCount(); i++) {
            Sprite childAt = getChildAt(i);
            int i2 = clipSquare.left + (i * width) + (width / 5);
            childAt.setDrawBounds(i2, clipSquare.top, i2 + width2, clipSquare.bottom);
        }
    }

    /* loaded from: classes.dex */
    class WaveItem extends RectSprite {
        WaveItem() {
        }

        @Override // com.github.ybq.android.spinkit.sprite.RectSprite, com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator getAnimation() {
            float[] fArr = {0.0f, 0.2f, 0.4f, 1.0f};
            return new SpriteAnimatorBuilder(this).scaleY(fArr, 0.4f, 1.0f, 0.4f, 0.4f).duration(1200L).easeInOut(fArr).build();
        }

        @Override // com.github.ybq.android.spinkit.sprite.Sprite, android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            invalidateSelf();
        }
    }
}
