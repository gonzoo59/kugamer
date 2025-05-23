package cn.bingoogolapple.bgabanner;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;
import java.util.List;
/* loaded from: classes.dex */
public class BGABannerUtil {
    private BGABannerUtil() {
    }

    public static int dp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(2, f, context.getResources().getDisplayMetrics());
    }

    public static ImageView getItemImageView(Context context, int i) {
        return getItemImageView(context, i, ImageView.ScaleType.CENTER_CROP);
    }

    public static ImageView getItemImageView(Context context, int i, ImageView.ScaleType scaleType) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(i);
        imageView.setClickable(true);
        imageView.setScaleType(scaleType);
        return imageView;
    }

    public static void resetPageTransformer(List<? extends View> list) {
        if (list == null) {
            return;
        }
        for (View view : list) {
            view.setVisibility(0);
            ViewCompat.setAlpha(view, 1.0f);
            ViewCompat.setPivotX(view, view.getMeasuredWidth() * 0.5f);
            ViewCompat.setPivotY(view, view.getMeasuredHeight() * 0.5f);
            ViewCompat.setTranslationX(view, 0.0f);
            ViewCompat.setTranslationY(view, 0.0f);
            ViewCompat.setScaleX(view, 1.0f);
            ViewCompat.setScaleY(view, 1.0f);
            ViewCompat.setRotationX(view, 0.0f);
            ViewCompat.setRotationY(view, 0.0f);
            ViewCompat.setRotation(view, 0.0f);
        }
    }
}
