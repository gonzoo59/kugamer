package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteGroup;
/* loaded from: classes.dex */
public class DoubleBounce extends SpriteGroup {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup
    public Sprite[] onCreateChild() {
        return new Sprite[]{new Bounce(), new Bounce()};
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteGroup
    public void onChildCreated(Sprite... spriteArr) {
        super.onChildCreated(spriteArr);
        spriteArr[1].setAnimationDelay(-1000);
    }

    /* loaded from: classes.dex */
    class Bounce extends CircleSprite {
        public Bounce() {
            setAlpha(153);
        }

        @Override // com.github.ybq.android.spinkit.sprite.CircleSprite, com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator getAnimation() {
            float[] fArr = {0.0f, 0.5f, 1.0f};
            return new SpriteAnimatorBuilder(this).scale(fArr, 0.0f, 1.0f, 0.0f).duration(2000L).easeInOut(fArr).build();
        }
    }
}
