package cn.bingoogolapple.bgabanner.transformer;

import android.view.View;
import androidx.core.view.ViewCompat;
/* loaded from: classes.dex */
public class DepthPageTransformer extends BGAPageTransformer {
    private float mMinScale = 0.8f;

    public DepthPageTransformer() {
    }

    public DepthPageTransformer(float f) {
        setMinScale(f);
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view, float f) {
        ViewCompat.setAlpha(view, 0.0f);
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view, float f) {
        ViewCompat.setAlpha(view, 1.0f);
        ViewCompat.setTranslationX(view, 0.0f);
        ViewCompat.setScaleX(view, 1.0f);
        ViewCompat.setScaleY(view, 1.0f);
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view, float f) {
        float f2 = 1.0f - f;
        ViewCompat.setAlpha(view, f2);
        ViewCompat.setTranslationX(view, (-view.getWidth()) * f);
        float f3 = this.mMinScale;
        float f4 = f3 + ((1.0f - f3) * f2);
        ViewCompat.setScaleX(view, f4);
        ViewCompat.setScaleY(view, f4);
    }

    public void setMinScale(float f) {
        if (f < 0.6f || f > 1.0f) {
            return;
        }
        this.mMinScale = f;
    }
}
