package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.animation.LinearInterpolator;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteGroup;
/* loaded from: classes.dex */
public class ChasingDots extends SpriteGroup {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup
    public Sprite[] onCreateChild() {
        return new Sprite[]{new Dot(), new Dot()};
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup
    public void onChildCreated(Sprite... spriteArr) {
        super.onChildCreated(spriteArr);
        spriteArr[1].setAnimationDelay(-1000);
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup, com.github.ybq.android.spinkit.sprite.Sprite
    public ValueAnimator getAnimation() {
        return new SpriteAnimatorBuilder(this).rotate(new float[]{0.0f, 1.0f}, 0, 360).duration(2000L).interpolator(new LinearInterpolator()).build();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect clipSquare = clipSquare(rect);
        int width = (int) (clipSquare.width() * 0.6f);
        getChildAt(0).setDrawBounds(clipSquare.left, clipSquare.top, clipSquare.right, clipSquare.top + width);
        getChildAt(1).setDrawBounds(clipSquare.left, clipSquare.bottom - width, clipSquare.right, clipSquare.bottom);
    }

    /* loaded from: classes.dex */
    class Dot extends CircleSprite {
        Dot() {
        }

        @Override // com.github.ybq.android.spinkit.sprite.CircleSprite, com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator getAnimation() {
            float[] fArr = {0.0f, 0.5f, 1.0f};
            return new SpriteAnimatorBuilder(this).scale(fArr, 0.0f, 1.0f, 0.0f).duration(2000L).easeInOut(fArr).build();
        }
    }
}
