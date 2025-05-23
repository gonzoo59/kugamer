package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteGroup;
/* loaded from: classes.dex */
public class CubeGrid extends SpriteGroup {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup
    public Sprite[] onCreateChild() {
        int[] iArr = {200, 300, 400, 100, 200, 300, 0, 100, 200};
        GridItem[] gridItemArr = new GridItem[9];
        for (int i = 0; i < 9; i++) {
            gridItemArr[i] = new GridItem();
            gridItemArr[i].setAnimationDelay(iArr[i]);
        }
        return gridItemArr;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect clipSquare = clipSquare(rect);
        int width = (int) (clipSquare.width() * 0.33f);
        int height = (int) (clipSquare.height() * 0.33f);
        for (int i = 0; i < getChildCount(); i++) {
            int i2 = clipSquare.left + ((i % 3) * width);
            int i3 = clipSquare.top + ((i / 3) * height);
            getChildAt(i).setDrawBounds(i2, i3, i2 + width, i3 + height);
        }
    }

    /* loaded from: classes.dex */
    class GridItem extends RectSprite {
        GridItem() {
        }

        @Override // com.github.ybq.android.spinkit.sprite.RectSprite, com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator getAnimation() {
            float[] fArr = {0.0f, 0.35f, 0.7f, 1.0f};
            return new SpriteAnimatorBuilder(this).scale(fArr, 1.0f, 0.0f, 1.0f, 1.0f).duration(1300L).easeInOut(fArr).build();
        }
    }
}
