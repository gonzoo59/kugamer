package cn.bingoogolapple.bgabanner.transformer;

import android.view.View;
import androidx.core.view.ViewCompat;
/* loaded from: classes.dex */
public class RotatePageTransformer extends BGAPageTransformer {
    private float mMaxRotation = 15.0f;

    public RotatePageTransformer() {
    }

    public RotatePageTransformer(float f) {
        setMaxRotation(f);
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view, float f) {
        ViewCompat.setPivotX(view, view.getMeasuredWidth() * 0.5f);
        ViewCompat.setPivotY(view, view.getMeasuredHeight());
        ViewCompat.setRotation(view, 0.0f);
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view, float f) {
        float f2 = this.mMaxRotation * f;
        ViewCompat.setPivotX(view, view.getMeasuredWidth() * 0.5f);
        ViewCompat.setPivotY(view, view.getMeasuredHeight());
        ViewCompat.setRotation(view, f2);
    }

    @Override // cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view, float f) {
        handleLeftPage(view, f);
    }

    public void setMaxRotation(float f) {
        if (f < 0.0f || f > 40.0f) {
            return;
        }
        this.mMaxRotation = f;
    }
}
