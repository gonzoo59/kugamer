package cn.bingoogolapple.bgabanner.transformer;

import android.view.View;
import androidx.core.view.ViewCompat;
/* loaded from: classes.dex */
public class ZoomFadePageTransformer extends BGAPageTransformer {
    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view, float f) {
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view, float f) {
        ViewCompat.setTranslationX(view, (-view.getWidth()) * f);
        ViewCompat.setPivotX(view, view.getWidth() * 0.5f);
        ViewCompat.setPivotY(view, view.getHeight() * 0.5f);
        float f2 = f + 1.0f;
        ViewCompat.setScaleX(view, f2);
        ViewCompat.setScaleY(view, f2);
        ViewCompat.setAlpha(view, f2);
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view, float f) {
        ViewCompat.setTranslationX(view, (-view.getWidth()) * f);
        ViewCompat.setPivotX(view, view.getWidth() * 0.5f);
        ViewCompat.setPivotY(view, view.getHeight() * 0.5f);
        float f2 = 1.0f - f;
        ViewCompat.setScaleX(view, f2);
        ViewCompat.setScaleY(view, f2);
        ViewCompat.setAlpha(view, f2);
    }
}
