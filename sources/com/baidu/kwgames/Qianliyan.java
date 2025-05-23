package com.baidu.kwgames;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.baidu.kwgames.Qianliyan;
import com.baidu.kwgames.util.FloatMgr;
import com.baidu.kwgames.util.NEAT;
import com.blankj.utilcode.util.SPUtils;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.lzf.easyfloat.widget.appfloat.FloatManager;
/* loaded from: classes.dex */
public class Qianliyan {
    private static String INI_CONFIG = "QianliyanSW";
    private static String INI_FLOAT_REVERT_COLOR = "QianliyanRcolor";
    private static String INI_FLOAT_SCALE = "QianliyanScale";
    private static String INI_FLOAT_SIZE = "QianliyanSz";
    private static String INI_FLOAT_X = "QianliyanX";
    private static String INI_FLOAT_Y = "QianliyanY";
    public static final String SETTING_STATUS = "status";
    private static String TAG_FLOAT = "QIANLIYAN";
    private static String TAG_FLOAT_ADJUST = "QIANLIYANadj";
    boolean m_boOnOff;
    boolean m_boRevertColor;
    Context m_context;
    Handler m_handler;
    int m_nFloatSize;
    int m_nFloatX;
    int m_nFloatY;
    int m_nShowScreenSize;
    int m_nZoomScale;
    TextView m_textBoxSize;
    TextView m_textScale;
    Bitmap m_bmpPreview = null;
    Bitmap m_bmpOldPreview = null;
    MyImageView m_viewImage = null;
    Matrix m_matrixAIPreview = null;
    int m_nShowScreenX = 0;
    int m_nShowScreenY = 0;
    boolean m_boNeedUpdateParam = false;
    boolean m_boNeedUpdateSize = false;
    Runnable m_runableSetBG = new Runnable() { // from class: com.baidu.kwgames.Qianliyan$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            Qianliyan.this.m97lambda$new$0$combaidukwgamesQianliyan();
        }
    };
    Runnable m_runableShowAdjustPreview = new Runnable() { // from class: com.baidu.kwgames.Qianliyan$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            Qianliyan.this.m98lambda$new$1$combaidukwgamesQianliyan();
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public Qianliyan(Context context, Handler handler) {
        this.m_nFloatSize = 400;
        this.m_nZoomScale = 0;
        this.m_boRevertColor = false;
        this.m_boOnOff = true;
        this.m_nFloatX = 0;
        this.m_nFloatY = 0;
        this.m_context = context;
        this.m_handler = handler;
        this.m_boOnOff = SPUtils.getInstance().getBoolean(INI_CONFIG, true);
        this.m_nFloatX = SPUtils.getInstance().getInt(INI_FLOAT_X, 0);
        this.m_nFloatY = SPUtils.getInstance().getInt(INI_FLOAT_Y, 0);
        this.m_nFloatSize = SPUtils.getInstance().getInt(INI_FLOAT_SIZE, 400);
        this.m_nZoomScale = SPUtils.getInstance().getInt(INI_FLOAT_SCALE, 4);
        this.m_boRevertColor = SPUtils.getInstance().getBoolean(INI_FLOAT_REVERT_COLOR, false);
    }

    public final boolean get_onoff() {
        return this.m_boOnOff;
    }

    public final void set_onoff(boolean z) {
        this.m_boOnOff = z;
        SPUtils.getInstance().put(INI_CONFIG, this.m_boOnOff);
    }

    void init_float() {
        if (EasyFloat.getAppFloatView(TAG_FLOAT) != null) {
            return;
        }
        EasyFloat.Builder layout = EasyFloat.with(AppInstance.get()).setTag(TAG_FLOAT).setGravity(17).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setLayout(R.layout.diy_image, new OnInvokeView() { // from class: com.baidu.kwgames.Qianliyan.1
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                Qianliyan.this.update_param();
                WindowManager.LayoutParams params = FloatManager.INSTANCE.getAppFloatManager(Qianliyan.TAG_FLOAT).getParams();
                if (params != null) {
                    int i = Qianliyan.this.m_nFloatSize;
                    params.width = i;
                    params.height = i;
                }
                Qianliyan.this.m_viewImage = (MyImageView) view.findViewById(R.id.image);
                EasyFloat.updateFloat(Qianliyan.TAG_FLOAT, Qianliyan.this.m_nFloatX, Qianliyan.this.m_nFloatY);
                FloatMgr.resetVirtualMouse();
            }
        });
        layout.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.Qianliyan.2
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dismiss() {
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void drag(View view, MotionEvent motionEvent) {
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void hide(View view) {
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void show(View view) {
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void touchEvent(View view, MotionEvent motionEvent) {
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                Qianliyan.this.m_viewImage.getLocationOnScreen(iArr);
                Qianliyan.this.m_nFloatX = iArr[0];
                Qianliyan.this.m_nFloatY = iArr[1];
                SPUtils.getInstance().put(Qianliyan.INI_FLOAT_X, Qianliyan.this.m_nFloatX);
                SPUtils.getInstance().put(Qianliyan.INI_FLOAT_Y, Qianliyan.this.m_nFloatY);
            }
        });
        layout.setLocation(this.m_nFloatX, this.m_nFloatY);
        layout.show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-baidu-kwgames-Qianliyan  reason: not valid java name */
    public /* synthetic */ void m97lambda$new$0$combaidukwgamesQianliyan() {
        if (this.m_viewImage != null) {
            if (this.m_boNeedUpdateSize) {
                WindowManager.LayoutParams params = FloatManager.INSTANCE.getAppFloatManager(TAG_FLOAT).getParams();
                params.height = this.m_nFloatSize;
                params.width = this.m_nFloatSize;
                this.m_viewImage.getLayoutParams().height = this.m_nFloatSize;
                this.m_viewImage.getLayoutParams().width = this.m_nFloatSize;
                this.m_viewImage.requestLayout();
                this.m_boNeedUpdateSize = false;
            }
            this.m_viewImage.set_new_bg_bitmap(this.m_bmpPreview, this.m_boRevertColor);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$1$com-baidu-kwgames-Qianliyan  reason: not valid java name */
    public /* synthetic */ void m98lambda$new$1$combaidukwgamesQianliyan() {
        if (this.m_viewImage == null) {
            init_float();
        } else {
            EasyFloat.showAppFloat(TAG_FLOAT);
        }
    }

    void update_param() {
        if (this.m_matrixAIPreview == null) {
            this.m_matrixAIPreview = new Matrix();
        }
        this.m_matrixAIPreview.reset();
        Matrix matrix = this.m_matrixAIPreview;
        int i = this.m_nZoomScale;
        matrix.postScale(i, i);
        this.m_nShowScreenSize = (int) ((this.m_nFloatSize / this.m_nZoomScale) + 0.5d);
        this.m_nShowScreenX = (AppInstance.s_nScreenW - this.m_nShowScreenSize) >> 1;
        this.m_nShowScreenY = (AppInstance.s_nScreenH - this.m_nShowScreenSize) >> 1;
        this.m_boNeedUpdateParam = false;
        this.m_boNeedUpdateSize = true;
    }

    public void set_bitmap(Bitmap bitmap) {
        try {
            if (this.m_viewImage != null) {
                if (this.m_boNeedUpdateParam) {
                    update_param();
                }
                int i = this.m_nShowScreenX;
                int i2 = this.m_nShowScreenY;
                int i3 = this.m_nShowScreenSize;
                this.m_bmpPreview = Bitmap.createBitmap(bitmap, i, i2, i3, i3, this.m_matrixAIPreview, true);
                this.m_handler.post(this.m_runableSetBG);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void show(boolean z, boolean z2) {
        this.m_boRevertColor = z2;
        if (z) {
            this.m_handler.post(this.m_runableShowAdjustPreview);
        } else {
            this.m_handler.post(new Runnable() { // from class: com.baidu.kwgames.Qianliyan$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    EasyFloat.hideAppFloat(Qianliyan.TAG_FLOAT);
                }
            });
        }
    }

    public void show_adjust_float() {
        String str = TAG_FLOAT_ADJUST;
        if (EasyFloat.getAppFloatView(str) != null) {
            EasyFloat.dismissAppFloat(str);
            return;
        }
        EasyFloat.Builder layout = EasyFloat.with(this.m_context).setTag(str).setLayout(R.layout.ai_qianliyan, new AnonymousClass3(str));
        layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null);
        layout.setLocation(80, 0);
        layout.show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.Qianliyan$3  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass3 implements OnInvokeView {
        final /* synthetic */ String val$strTag;

        AnonymousClass3(String str) {
            this.val$strTag = str;
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            view.findViewById(R.id.m_btnBoxSizeMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.Qianliyan$3$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    Qianliyan.AnonymousClass3.this.m99lambda$invoke$0$combaidukwgamesQianliyan$3(view2);
                }
            });
            view.findViewById(R.id.m_btnBoxSizePlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.Qianliyan$3$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    Qianliyan.AnonymousClass3.this.m100lambda$invoke$1$combaidukwgamesQianliyan$3(view2);
                }
            });
            view.findViewById(R.id.m_btnZoomScaleMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.Qianliyan$3$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    Qianliyan.AnonymousClass3.this.m101lambda$invoke$2$combaidukwgamesQianliyan$3(view2);
                }
            });
            view.findViewById(R.id.m_btnZoomScalePlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.Qianliyan$3$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    Qianliyan.AnonymousClass3.this.m102lambda$invoke$3$combaidukwgamesQianliyan$3(view2);
                }
            });
            Switch r0 = (Switch) view.findViewById(R.id.m_swColorRevert);
            r0.setChecked(Qianliyan.this.m_boRevertColor);
            r0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.Qianliyan.3.1
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    Qianliyan.this.m_boRevertColor = z;
                    SPUtils.getInstance().put(Qianliyan.INI_FLOAT_REVERT_COLOR, Qianliyan.this.m_boRevertColor);
                }
            });
            Qianliyan.this.m_textBoxSize = (TextView) view.findViewById(R.id.m_textBoxSize);
            Qianliyan.this.m_textScale = (TextView) view.findViewById(R.id.m_textZoomScale);
            Qianliyan.this.update_adjust_UI();
            View findViewById = view.findViewById(R.id.close_window);
            final String str = this.val$strTag;
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.Qianliyan$3$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    EasyFloat.dismissAppFloat(str);
                }
            });
            FloatMgr.resetVirtualMouse();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$0$com-baidu-kwgames-Qianliyan$3  reason: not valid java name */
        public /* synthetic */ void m99lambda$invoke$0$combaidukwgamesQianliyan$3(View view) {
            if (Qianliyan.this.m_nFloatSize > 300) {
                Qianliyan qianliyan = Qianliyan.this;
                qianliyan.m_nFloatSize -= 50;
                Qianliyan.this.m_boNeedUpdateParam = true;
                Qianliyan.this.update_adjust_UI();
                SPUtils.getInstance().put(Qianliyan.INI_FLOAT_SIZE, Qianliyan.this.m_nFloatSize);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$1$com-baidu-kwgames-Qianliyan$3  reason: not valid java name */
        public /* synthetic */ void m100lambda$invoke$1$combaidukwgamesQianliyan$3(View view) {
            if (Qianliyan.this.m_nFloatSize < 1000) {
                Qianliyan.this.m_nFloatSize += 50;
                Qianliyan.this.m_boNeedUpdateParam = true;
                Qianliyan.this.update_adjust_UI();
                SPUtils.getInstance().put(Qianliyan.INI_FLOAT_SIZE, Qianliyan.this.m_nFloatSize);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$2$com-baidu-kwgames-Qianliyan$3  reason: not valid java name */
        public /* synthetic */ void m101lambda$invoke$2$combaidukwgamesQianliyan$3(View view) {
            if (Qianliyan.this.m_nZoomScale > 1) {
                Qianliyan.this.m_nZoomScale--;
                Qianliyan.this.m_boNeedUpdateParam = true;
                Qianliyan.this.update_adjust_UI();
                SPUtils.getInstance().put(Qianliyan.INI_FLOAT_SCALE, Qianliyan.this.m_nZoomScale);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$3$com-baidu-kwgames-Qianliyan$3  reason: not valid java name */
        public /* synthetic */ void m102lambda$invoke$3$combaidukwgamesQianliyan$3(View view) {
            if (Qianliyan.this.m_nZoomScale < 8) {
                Qianliyan.this.m_nZoomScale++;
                Qianliyan.this.m_boNeedUpdateParam = true;
                Qianliyan.this.update_adjust_UI();
                SPUtils.getInstance().put(Qianliyan.INI_FLOAT_SCALE, Qianliyan.this.m_nZoomScale);
            }
        }
    }

    public void update_adjust_UI() {
        TextView textView = this.m_textBoxSize;
        textView.setText("" + this.m_nFloatSize);
        TextView textView2 = this.m_textScale;
        textView2.setText(this.m_nZoomScale + NEAT.s(R.string.scale_unit));
    }
}
