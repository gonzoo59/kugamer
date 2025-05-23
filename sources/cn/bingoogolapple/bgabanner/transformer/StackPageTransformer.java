package cn.bingoogolapple.bgabanner.transformer;

import android.view.View;
import androidx.core.view.ViewCompat;
/* loaded from: classes.dex */
public class StackPageTransformer extends BGAPageTransformer {
    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view, float f) {
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view, float f) {
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view, float f) {
        ViewCompat.setTranslationX(view, (-view.getWidth()) * f);
    }
}
