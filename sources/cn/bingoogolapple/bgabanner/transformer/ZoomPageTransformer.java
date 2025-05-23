package cn.bingoogolapple.bgabanner.transformer;

import android.view.View;
import androidx.core.view.ViewCompat;
/* loaded from: classes.dex */
public class ZoomPageTransformer extends BGAPageTransformer {
    private float mMinScale = 0.85f;
    private float mMinAlpha = 0.65f;

    public ZoomPageTransformer() {
    }

    public ZoomPageTransformer(float f, float f2) {
        setMinAlpha(f);
        setMinScale(f2);
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view, float f) {
        ViewCompat.setAlpha(view, 0.0f);
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view, float f) {
        float max = Math.max(this.mMinScale, f + 1.0f);
        float f2 = 1.0f - max;
        ViewCompat.setTranslationX(view, ((view.getWidth() * f2) / 2.0f) - (((view.getHeight() * f2) / 2.0f) / 2.0f));
        ViewCompat.setScaleX(view, max);
        ViewCompat.setScaleY(view, max);
        float f3 = this.mMinAlpha;
        float f4 = this.mMinScale;
        ViewCompat.setAlpha(view, f3 + (((max - f4) / (1.0f - f4)) * (1.0f - f3)));
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view, float f) {
        float max = Math.max(this.mMinScale, 1.0f - f);
        float f2 = 1.0f - max;
        ViewCompat.setTranslationX(view, (-((view.getWidth() * f2) / 2.0f)) + (((view.getHeight() * f2) / 2.0f) / 2.0f));
        ViewCompat.setScaleX(view, max);
        ViewCompat.setScaleY(view, max);
        float f3 = this.mMinAlpha;
        float f4 = this.mMinScale;
        ViewCompat.setAlpha(view, f3 + (((max - f4) / (1.0f - f4)) * (1.0f - f3)));
    }

    public void setMinAlpha(float f) {
        if (f < 0.6f || f > 1.0f) {
            return;
        }
        this.mMinAlpha = f;
    }

    public void setMinScale(float f) {
        if (f < 0.6f || f > 1.0f) {
            return;
        }
        this.mMinScale = f;
    }
}
