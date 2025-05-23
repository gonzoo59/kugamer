package com.baidu.kwgames.ai;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.baidu.kwgames.AIFloatInfo;
import com.baidu.kwgames.SystemStatus;
import com.baidu.kwgames.Units;
import com.baidu.kwgames.bean.ViewInfo;
import com.blankj.utilcode.util.SPUtils;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes.dex */
public class AIgnyx {
    private static SPUtils m_ini = SPUtils.getInstance();
    private static ViewInfo s_vInfo = null;
    private View doubleMirrorMask;
    private View doubleMirrorSelect;
    private View downMask;
    private View downSelect;
    private View gunMask1;
    private View gunMask2;
    private View gunMaskSelectIcon1;
    private View gunMaskSelectIcon2;
    private TextView mEditTipTextView;
    Context m_context;
    private ImageView m_imageAdjustPreview;
    private SystemStatus m_sSysStatus;
    TextView m_viewAIEditRebootRemind;
    View m_viewAIEditRoot;
    private View m_viewBagtag;
    private View m_viewBagtagFrame;
    private View m_viewBagtagImage;
    private View m_viewGun1Handle;
    private View m_viewGun1HandleFrame;
    private View m_viewGun1HandleImage;
    private View m_viewGun1Head;
    private View m_viewGun1HeadFrame;
    private View m_viewGun1HeadImage;
    private View m_viewGun1Tail;
    private View m_viewGun1TailFrame;
    private View m_viewGun1TailImage;
    private View m_viewGun2Handle;
    private View m_viewGun2HandleFrame;
    private View m_viewGun2HandleImage;
    private View m_viewGun2Head;
    private View m_viewGun2HeadFrame;
    private View m_viewGun2HeadImage;
    private View m_viewGun2Tail;
    private View m_viewGun2TailFrame;
    private View m_viewGun2TailImage;
    private View m_viewTakeOfftag;
    private View m_viewTakeOfftagFrame;
    private View m_viewTakeOfftagImage;
    private View mirror;
    private View mirrorSelect;
    private View squatMask;
    private View squatSelect;
    private View temp1;
    private View temp2;
    Matrix m_matrixScope = new Matrix();
    private int m_nScopeModelW = 76;
    private int m_nScopeModelH = 36;
    private float m_fScopeModeRateW2H = (76 * 1.0f) / 36;
    Matrix m_matrixGun = new Matrix();
    private int m_nGunModelW = 253;
    private int m_nGunModelH = 81;
    private int m_nGunAIW = 253;
    private int m_nGunAIH = 81;
    private float m_fGunModeRateW2H = (253 * 1.0f) / 81;
    Matrix m_matrixBagTag = new Matrix();
    private int m_nBagtagModelW = 53;
    private int m_nBagtagModelH = 64;
    private float m_fBagtagModelRateW2H = (53 * 1.0f) / 64;
    Matrix m_matrixTakeoff = new Matrix();
    private int m_nTakeoffModelW = 24;
    private int m_nTakeoffModelH = 24;
    private float m_fTakeoffModelRateW2H = (24 * 1.0f) / 24;
    Matrix m_matrixGunOtherParts = new Matrix();
    Matrix m_matrixGunHandle = new Matrix();
    private int m_nGunPartsModelWH = 92;
    private int m_nGunPartsHandleWH = 92;
    Matrix m_matrixAIPreview = new Matrix();
    public Map<String, AIFloatInfo> m_mapAIFloat = new HashMap();
    private boolean m_boAIEditing = false;
    private boolean m_boGunPartsAI = false;
    private boolean m_boNeedAICommit = false;

    public void AIgnyx(Context context) {
        this.m_context = context;
    }

    public static boolean is_bitmap_pixel_on(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth() - 1;
        int height = bitmap.getHeight() - 1;
        boolean z = false;
        int make_in_range = Units.make_in_range(i + 1, 0, width);
        int make_in_range2 = Units.make_in_range(i2, 0, height);
        int make_in_range3 = Units.make_in_range(i2 + 1, 0, height);
        for (int make_in_range4 = Units.make_in_range(i, 0, width); make_in_range4 <= make_in_range && !z; make_in_range4++) {
            int i3 = make_in_range2;
            while (true) {
                if (i3 <= make_in_range3) {
                    int pixel = bitmap.getPixel(make_in_range4, i3);
                    int i4 = (16711680 & pixel) >> 16;
                    int i5 = (65280 & pixel) >> 8;
                    int i6 = pixel & 255;
                    if (i4 > 150 && i5 > 150 && i6 < i4 - 80) {
                        z = true;
                        break;
                    }
                    i3++;
                }
            }
        }
        return z;
    }

    public static boolean is_gun_bitmap_pixel_on(Bitmap bitmap, int i, int i2) {
        int i3;
        int width = bitmap.getWidth() - 1;
        int height = bitmap.getHeight() - 1;
        int make_in_range = Units.make_in_range(i, 0, width);
        int make_in_range2 = Units.make_in_range(i + 20, 0, width);
        int make_in_range3 = Units.make_in_range(i + 40, 0, width);
        int make_in_range4 = Units.make_in_range(i2, 0, height);
        int make_in_range5 = Units.make_in_range(i2 + 1, 0, height);
        int i4 = make_in_range4;
        while (true) {
            if (i4 > make_in_range5) {
                i3 = 0;
                break;
            }
            int pixel = bitmap.getPixel(make_in_range, i4);
            int i5 = (pixel & 16711680) >> 16;
            int i6 = (pixel & 65280) >> 8;
            int i7 = pixel & 255;
            if (i5 > 150 && i6 > 130 && i7 < i5 - 60) {
                i3 = 1;
                break;
            }
            i4++;
        }
        int i8 = make_in_range4;
        while (true) {
            if (i8 > make_in_range5) {
                break;
            }
            int pixel2 = bitmap.getPixel(make_in_range2, i8);
            int i9 = (pixel2 & 16711680) >> 16;
            int i10 = (pixel2 & 65280) >> 8;
            int i11 = pixel2 & 255;
            if (i9 > 150 && i10 > 130 && i11 < i9 - 60) {
                i3++;
                break;
            }
            i8++;
        }
        while (true) {
            if (make_in_range4 > make_in_range5) {
                break;
            }
            int pixel3 = bitmap.getPixel(make_in_range3, make_in_range4);
            int i12 = (pixel3 & 16711680) >> 16;
            int i13 = (pixel3 & 65280) >> 8;
            int i14 = pixel3 & 255;
            if (i12 > 150 && i13 > 130 && i14 < i12 - 60) {
                i3++;
                break;
            }
            make_in_range4++;
        }
        return i3 >= 2;
    }

    public static boolean is_scope_bitmap_pixel_on(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth() - 1;
        int height = bitmap.getHeight() - 1;
        boolean z = false;
        int make_in_range = Units.make_in_range(i + 1, 0, width);
        int make_in_range2 = Units.make_in_range(i2, 0, height);
        int make_in_range3 = Units.make_in_range(i2 + 1, 0, height);
        for (int make_in_range4 = Units.make_in_range(i, 0, width); make_in_range4 <= make_in_range && !z; make_in_range4++) {
            int i3 = make_in_range2;
            while (true) {
                if (i3 <= make_in_range3) {
                    int pixel = bitmap.getPixel(make_in_range4, i3);
                    int i4 = (16711680 & pixel) >> 16;
                    int i5 = (65280 & pixel) >> 8;
                    int i6 = pixel & 255;
                    if (i4 > 150 && i5 > 150 && i6 < i4 - 80) {
                        z = true;
                        break;
                    }
                    i3++;
                }
            }
        }
        return z;
    }

    public void set_scope_model_size(int i, int i2) {
        this.m_nScopeModelW = i;
        this.m_nScopeModelH = i2;
        this.m_fScopeModeRateW2H = (i * 1.0f) / i2;
    }

    public void set_gun_model_size(int i, int i2) {
        this.m_nGunModelW = i;
        this.m_nGunModelH = i2;
        this.m_fGunModeRateW2H = (i * 1.0f) / i2;
    }

    public void set_bagtag_model_size(int i, int i2) {
        this.m_nBagtagModelW = i;
        this.m_nBagtagModelH = i2;
        this.m_fBagtagModelRateW2H = (i * 1.0f) / i2;
    }

    public void set_takeoff_model_size(int i, int i2) {
        this.m_nTakeoffModelW = i;
        this.m_nTakeoffModelH = i2;
        this.m_fTakeoffModelRateW2H = (i * 1.0f) / i2;
    }

    public void set_gun_parts_model_size(int i, int i2) {
        this.m_nGunPartsModelWH = i;
        this.m_nGunPartsHandleWH = i2;
    }
}
