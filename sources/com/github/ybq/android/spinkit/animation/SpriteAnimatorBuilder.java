package com.github.ybq.android.spinkit.animation;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.util.Property;
import android.view.animation.Interpolator;
import com.github.ybq.android.spinkit.animation.interpolator.KeyFrameInterpolator;
import com.github.ybq.android.spinkit.sprite.Sprite;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/* loaded from: classes.dex */
public class SpriteAnimatorBuilder {
    private Interpolator interpolator;
    private Sprite sprite;
    private List<PropertyValuesHolder> propertyValuesHolders = new ArrayList();
    private int repeatCount = -1;
    private long duration = 2000;

    public SpriteAnimatorBuilder(Sprite sprite) {
        this.sprite = sprite;
    }

    public SpriteAnimatorBuilder scale(float[] fArr, float... fArr2) {
        holder(fArr, Sprite.SCALE, fArr2);
        return this;
    }

    public SpriteAnimatorBuilder alpha(float[] fArr, int... iArr) {
        holder(fArr, (Property) Sprite.ALPHA, iArr);
        return this;
    }

    public SpriteAnimatorBuilder scaleX(float[] fArr, float... fArr2) {
        holder(fArr, Sprite.SCALE, fArr2);
        return this;
    }

    public SpriteAnimatorBuilder scaleY(float[] fArr, float... fArr2) {
        holder(fArr, Sprite.SCALE_Y, fArr2);
        return this;
    }

    public SpriteAnimatorBuilder rotateX(float[] fArr, int... iArr) {
        holder(fArr, (Property) Sprite.ROTATE_X, iArr);
        return this;
    }

    public SpriteAnimatorBuilder rotateY(float[] fArr, int... iArr) {
        holder(fArr, (Property) Sprite.ROTATE_Y, iArr);
        return this;
    }

    public SpriteAnimatorBuilder translateX(float[] fArr, int... iArr) {
        holder(fArr, (Property) Sprite.TRANSLATE_X, iArr);
        return this;
    }

    public SpriteAnimatorBuilder translateY(float[] fArr, int... iArr) {
        holder(fArr, (Property) Sprite.TRANSLATE_Y, iArr);
        return this;
    }

    public SpriteAnimatorBuilder rotate(float[] fArr, int... iArr) {
        holder(fArr, (Property) Sprite.ROTATE, iArr);
        return this;
    }

    public SpriteAnimatorBuilder translateXPercentage(float[] fArr, float... fArr2) {
        holder(fArr, Sprite.TRANSLATE_X_PERCENTAGE, fArr2);
        return this;
    }

    public SpriteAnimatorBuilder translateYPercentage(float[] fArr, float... fArr2) {
        holder(fArr, Sprite.TRANSLATE_Y_PERCENTAGE, fArr2);
        return this;
    }

    public PropertyValuesHolder holder(float[] fArr, Property property, float[] fArr2) {
        ensurePair(fArr.length, fArr2.length);
        Keyframe[] keyframeArr = new Keyframe[fArr.length];
        for (int i = 0; i < fArr2.length; i++) {
            keyframeArr[i] = Keyframe.ofFloat(fArr[i], fArr2[i]);
        }
        PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe(property, keyframeArr);
        this.propertyValuesHolders.add(ofKeyframe);
        return ofKeyframe;
    }

    public PropertyValuesHolder holder(float[] fArr, Property property, int[] iArr) {
        ensurePair(fArr.length, iArr.length);
        Keyframe[] keyframeArr = new Keyframe[fArr.length];
        for (int i = 0; i < iArr.length; i++) {
            keyframeArr[i] = Keyframe.ofInt(fArr[i], iArr[i]);
        }
        PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe(property, keyframeArr);
        this.propertyValuesHolders.add(ofKeyframe);
        return ofKeyframe;
    }

    private void ensurePair(int i, int i2) {
        if (i != i2) {
            throw new IllegalStateException(String.format(Locale.getDefault(), "The fractions.length must equal values.length, fraction.length[%d], values.length[%d]", Integer.valueOf(i), Integer.valueOf(i2)));
        }
    }

    public SpriteAnimatorBuilder interpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public SpriteAnimatorBuilder easeInOut(float... fArr) {
        interpolator(KeyFrameInterpolator.easeInOut(fArr));
        return this;
    }

    public SpriteAnimatorBuilder duration(long j) {
        this.duration = j;
        return this;
    }

    public SpriteAnimatorBuilder repeatCount(int i) {
        this.repeatCount = i;
        return this;
    }

    public ObjectAnimator build() {
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this.sprite, (PropertyValuesHolder[]) this.propertyValuesHolders.toArray(new PropertyValuesHolder[this.propertyValuesHolders.size()]));
        ofPropertyValuesHolder.setDuration(this.duration);
        ofPropertyValuesHolder.setRepeatCount(this.repeatCount);
        ofPropertyValuesHolder.setInterpolator(this.interpolator);
        return ofPropertyValuesHolder;
    }
}
