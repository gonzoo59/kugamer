package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteGroup;
/* loaded from: classes.dex */
public class WanderingCubes extends SpriteGroup {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup
    public Sprite[] onCreateChild() {
        return new Sprite[]{new Cube(), new Cube()};
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup
    public void onChildCreated(Sprite... spriteArr) {
        super.onChildCreated(spriteArr);
        spriteArr[1].setAnimationDelay(-900);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        Rect clipSquare = clipSquare(rect);
        super.onBoundsChange(clipSquare);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setDrawBounds(clipSquare.left, clipSquare.top, clipSquare.left + (clipSquare.width() / 4), clipSquare.top + (clipSquare.height() / 4));
        }
    }

    /* loaded from: classes.dex */
    class Cube extends RectSprite {
        Cube() {
        }

        @Override // com.github.ybq.android.spinkit.sprite.RectSprite, com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator getAnimation() {
            float[] fArr = {0.0f, 0.25f, 0.5f, 0.51f, 0.75f, 1.0f};
            return new SpriteAnimatorBuilder(this).rotate(fArr, 0, -90, -179, -180, -270, -360).translateXPercentage(fArr, 0.0f, 0.75f, 0.75f, 0.75f, 0.0f, 0.0f).translateYPercentage(fArr, 0.0f, 0.0f, 0.75f, 0.75f, 0.75f, 0.0f).scale(fArr, 1.0f, 0.5f, 1.0f, 1.0f, 0.5f, 1.0f).duration(1800L).easeInOut(fArr).build();
        }
    }
}
