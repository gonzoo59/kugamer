package com.github.ybq.android.spinkit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.github.ybq.android.library.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.WanderingCubes;
/* loaded from: classes.dex */
public class SpinKitView extends ProgressBar {
    private int mColor;
    private Sprite mSprite;
    private Style mStyle;

    public SpinKitView(Context context) {
        this(context, null);
    }

    public SpinKitView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.SpinKitViewStyle);
    }

    public SpinKitView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.SpinKitView);
    }

    public SpinKitView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SpinKitView, i, i2);
        this.mStyle = Style.values()[obtainStyledAttributes.getInt(R.styleable.SpinKitView_SpinKit_Style, 0)];
        this.mColor = obtainStyledAttributes.getColor(R.styleable.SpinKitView_SpinKit_Color, -1);
        obtainStyledAttributes.recycle();
        init();
        setIndeterminate(true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.github.ybq.android.spinkit.SpinKitView$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$github$ybq$android$spinkit$Style;

        static {
            int[] iArr = new int[Style.values().length];
            $SwitchMap$com$github$ybq$android$spinkit$Style = iArr;
            try {
                iArr[Style.ROTATING_PLANE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.DOUBLE_BOUNCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.WAVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.WANDERING_CUBES.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.PULSE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.CHASING_DOTS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.THREE_BOUNCE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.CIRCLE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.CUBE_GRID.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.FADING_CIRCLE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.FOLDING_CUBE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    private void init() {
        switch (AnonymousClass1.$SwitchMap$com$github$ybq$android$spinkit$Style[this.mStyle.ordinal()]) {
            case 1:
                setIndeterminateDrawable((Sprite) new RotatingPlane());
                return;
            case 2:
                setIndeterminateDrawable((Sprite) new DoubleBounce());
                return;
            case 3:
                setIndeterminateDrawable((Sprite) new RotatingPlane());
                return;
            case 4:
                setIndeterminateDrawable((Sprite) new WanderingCubes());
                return;
            case 5:
                setIndeterminateDrawable((Sprite) new Pulse());
                return;
            case 6:
                setIndeterminateDrawable((Sprite) new ChasingDots());
                return;
            case 7:
                setIndeterminateDrawable((Sprite) new ThreeBounce());
                return;
            case 8:
                setIndeterminateDrawable((Sprite) new Circle());
                return;
            case 9:
                setIndeterminateDrawable((Sprite) new CubeGrid());
                return;
            case 10:
                setIndeterminateDrawable((Sprite) new FadingCircle());
                return;
            case 11:
                setIndeterminateDrawable((Sprite) new FoldingCube());
                return;
            default:
                return;
        }
    }

    @Override // android.widget.ProgressBar
    public void setIndeterminateDrawable(Drawable drawable) {
        super.setIndeterminateDrawable(drawable);
        if (!(drawable instanceof Sprite)) {
            throw new IllegalArgumentException();
        }
        setIndeterminateDrawable((Sprite) drawable);
    }

    public void setIndeterminateDrawable(Sprite sprite) {
        super.setIndeterminateDrawable((Drawable) sprite);
        this.mSprite = sprite;
        sprite.setColor(this.mColor);
    }

    @Override // android.widget.ProgressBar
    public Sprite getIndeterminateDrawable() {
        return this.mSprite;
    }

    @Override // android.widget.ProgressBar
    public void setIndeterminateDrawableTiled(Drawable drawable) {
        super.setIndeterminateDrawableTiled(drawable);
    }
}
