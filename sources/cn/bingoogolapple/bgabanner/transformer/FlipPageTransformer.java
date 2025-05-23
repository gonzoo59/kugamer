package cn.bingoogolapple.bgabanner.transformer;

import android.view.View;
import androidx.core.view.ViewCompat;
/* loaded from: classes.dex */
public class FlipPageTransformer extends BGAPageTransformer {
    private static final float ROTATION = 180.0f;

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view, float f) {
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view, float f) {
        ViewCompat.setTranslationX(view, (-view.getWidth()) * f);
        ViewCompat.setRotationY(view, ROTATION * f);
        if (f > -0.5d) {
            view.setVisibility(0);
        } else {
            view.setVisibility(4);
        }
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view, float f) {
        ViewCompat.setTranslationX(view, (-view.getWidth()) * f);
        ViewCompat.setRotationY(view, ROTATION * f);
        if (f < 0.5d) {
            view.setVisibility(0);
        } else {
            view.setVisibility(4);
        }
    }
}
