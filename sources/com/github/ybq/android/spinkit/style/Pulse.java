package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
/* loaded from: classes.dex */
public class Pulse extends CircleSprite {
    @Override // com.github.ybq.android.spinkit.sprite.CircleSprite, com.github.ybq.android.spinkit.sprite.Sprite
    public ValueAnimator getAnimation() {
        float[] fArr = {0.0f, 1.0f};
        return new SpriteAnimatorBuilder(this).scale(fArr, 0.0f, 1.0f).alpha(fArr, 255, 0).duration(1000L).easeInOut(fArr).build();
    }
}
