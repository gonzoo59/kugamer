package com.baidu.kwgames.util;

import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.baidu.kwgames.AppInstance;
import com.baidu.kwgames.Constants;
import com.baidu.kwgames.R;
import com.baidu.kwgames.UIConst;
import com.baidu.kwgames.view.FastView;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.lzf.easyfloat.widget.appfloat.AppFloatManager;
import com.lzf.easyfloat.widget.appfloat.FloatManager;
import java.util.HashMap;
import java.util.Map;
import org.opencv.videoio.Videoio;
/* loaded from: classes.dex */
public class FloatMgr {
    private static final String E_EMUI_REC_WINDOW_NAME = new String("ScreenRecorderTimer");
    private static final String E_MIUI_REC_WINDOW_NAME = new String("com.miui.screenrecorder");
    private static final String E_OPPO_REC_WINDOW_NAME = new String("com.coloros.screenrecorder.FloatView");
    private static final String E_VIVO_REC_WINDOW_NAME = new String("screen_record_menu");
    private static final String E_ONEPLUS_REC_WINDOW_NAME = new String("op_screenrecord");
    private static final String E_FLYME_REC_WINDOW_NAME = new String("SysScreenRecorder");
    private static final String E_NUBIA_REC_WINDOW_NAME = new String("NubiaScreenDecorOverlay");
    private static final String E_BLACKSHARK_REC_WINDOW_NAME = new String("com.blackshark.screenrecorder");
    private static final String E_ASUS_REC_WINDOW_NAME = new String("com.asus.force.layer.transparent.SR.floatingpanel");
    public static final Map<String, String> s_mapBrandToRecScreenWindowName = new HashMap<String, String>() { // from class: com.baidu.kwgames.util.FloatMgr.1
        {
            put("HUAWEI", FloatMgr.E_EMUI_REC_WINDOW_NAME);
            put("HONOR", FloatMgr.E_EMUI_REC_WINDOW_NAME);
            put("XIAOMI", FloatMgr.E_MIUI_REC_WINDOW_NAME);
            put("REDMI", FloatMgr.E_MIUI_REC_WINDOW_NAME);
            put("OPPO", FloatMgr.E_OPPO_REC_WINDOW_NAME);
            put("VIVO", FloatMgr.E_VIVO_REC_WINDOW_NAME);
            put("ONEPLUS", FloatMgr.E_ONEPLUS_REC_WINDOW_NAME);
            put("MEIZU", FloatMgr.E_FLYME_REC_WINDOW_NAME);
            put("NUBIA", FloatMgr.E_NUBIA_REC_WINDOW_NAME);
            put("BLACKSHARK", FloatMgr.E_BLACKSHARK_REC_WINDOW_NAME);
            put("ASUS", FloatMgr.E_ASUS_REC_WINDOW_NAME);
        }
    };
    private static String TAG_V_MOUSE = "virtualMouse";
    private static FastView mouseImage = null;
    private static int m_nMouseImageID = -1;
    private static int m_nMouseImageDefID = R.mipmap.lol2;
    private static String TAG_LOL_MOVE = "LOLMove";
    private static boolean s_boLOLMoveIsVisible = false;
    private static int m_nCurLOLMoveX = -1;
    private static int m_nCurLOLMoveY = -1;
    private static String TAG_AIM_FLOAT = "Aim";
    private static String CFG_AIM_FLOAT_SZ = "AimSZ";
    public static boolean s_boAimIsVisible = false;
    private static int s_nAimModel = -1;
    private static ImageView g_sAimImage = null;
    private static SPUtils m_ini = SPUtils.getInstance();
    static int s_nAimFloatX = 0;
    static int s_nAimFloatY = 0;
    static boolean s_boAimFloatNeedShow = false;
    static int s_nAimFloatSize = 200;

    public static String find_brand_rec_window_name(String str) {
        for (Map.Entry<String, String> entry : s_mapBrandToRecScreenWindowName.entrySet()) {
            if (-1 != str.indexOf(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "";
    }

    public static void init() {
        update_aim_float_size();
    }

    public static void get_float_untouchable_param(WindowManager.LayoutParams layoutParams) {
        layoutParams.flags |= 568;
        if (NEAT.is_huawei().booleanValue()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            layoutParams.type = 2038;
            if (Build.VERSION.SDK_INT >= 28) {
                layoutParams.layoutInDisplayCutoutMode = 1;
            }
        } else if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 23) {
            layoutParams.type = 2005;
        } else {
            layoutParams.type = 2002;
        }
        layoutParams.packageName = AppUtils.getAppPackageName();
        layoutParams.flags |= 16777216;
        layoutParams.flags |= 66816;
        layoutParams.format = -2;
    }

    public static void get_float_touchable_param(WindowManager.LayoutParams layoutParams) {
        layoutParams.flags |= Videoio.CAP_PROP_XI_ACQ_TRANSPORT_BUFFER_COMMIT;
        if (Build.VERSION.SDK_INT >= 26) {
            layoutParams.type = 2038;
            if (Build.VERSION.SDK_INT >= 28) {
                layoutParams.layoutInDisplayCutoutMode = 1;
            }
        } else if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 23) {
            layoutParams.type = 2005;
        } else {
            layoutParams.type = 2002;
        }
        layoutParams.packageName = AppUtils.getAppPackageName();
        layoutParams.flags |= 16777216;
        layoutParams.flags |= 66816;
        layoutParams.format = -2;
    }

    public static void initVirtualMouse() {
        if (mouseImage != null) {
            return;
        }
        final String str = TAG_V_MOUSE;
        EasyFloat.Builder layout = EasyFloat.with(AppInstance.get()).setTag(str).setGravity(17).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(false).setAnimator(null).setLayout(R.layout.fast_view, new OnInvokeView() { // from class: com.baidu.kwgames.util.FloatMgr.2
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                FastView unused = FloatMgr.mouseImage = (FastView) view.findViewById(R.id.image);
                WindowManager.LayoutParams params = FloatManager.INSTANCE.getAppFloatManager(str).getParams();
                FloatMgr.get_float_untouchable_param(params);
                int i = 0;
                int i2 = SPUtils.getInstance().getInt(Constants.CFG_CURSOR_IMAGE, 0);
                if (i2 < UIConst.s_arrCursorImages.length && i2 >= 0) {
                    i = i2;
                }
                FloatMgr.setMouseImage(UIConst.s_arrCursorImages[i]);
                params.alpha = 0.8f;
                FloatMgr.update_float_param_while_create(params);
                EasyFloat.updateFloat(str, AppInstance.s_nScreenW / 2, AppInstance.s_nScreenH / 2);
                EasyFloat.hideAppFloat(str);
            }
        });
        layout.setLocation(AppInstance.s_nScreenW / 2, AppInstance.s_nScreenH / 2);
        layout.show();
    }

    public static void update_aim_image() {
        int i = s_nAimModel;
        if (i <= 0 || i > 10) {
            return;
        }
        g_sAimImage.setBackgroundResource(Constants.s_arrCrosshairImage[s_nAimModel]);
    }

    public static void init_aim_float(int i) {
        if (g_sAimImage != null) {
            if (i != s_nAimModel) {
                s_nAimModel = i;
                update_aim_image();
            }
        } else if (i == 0) {
        } else {
            s_nAimModel = i;
            final String str = TAG_AIM_FLOAT;
            EasyFloat.with(AppInstance.get()).setTag(str).setGravity(17).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(false).setAnimator(null).setLayout(R.layout.aim, new OnInvokeView() { // from class: com.baidu.kwgames.util.FloatMgr.3
                @Override // com.lzf.easyfloat.interfaces.OnInvokeView
                public void invoke(View view) {
                    ImageView unused = FloatMgr.g_sAimImage = (ImageView) view.findViewById(R.id.image);
                    FloatMgr.update_aim_float_size();
                    WindowManager.LayoutParams params = FloatManager.INSTANCE.getAppFloatManager(str).getParams();
                    FloatMgr.get_float_untouchable_param(params);
                    int i2 = FloatMgr.s_nAimFloatSize;
                    params.width = i2;
                    params.height = i2;
                    params.alpha = 0.8f;
                    FloatMgr.update_float_param_while_create(params);
                    FloatMgr.update_aim_image();
                    EasyFloat.updateFloat(str, FloatMgr.s_nAimFloatX, FloatMgr.s_nAimFloatY);
                    if (!FloatMgr.s_boAimFloatNeedShow) {
                        EasyFloat.hideAppFloat(str);
                    }
                    FloatMgr.s_boAimFloatNeedShow = false;
                }
            }).show();
        }
    }

    public static void setMouseImage(int i) {
        FastView fastView = mouseImage;
        if (fastView == null || m_nMouseImageID == i) {
            return;
        }
        m_nMouseImageID = i;
        if (i != R.mipmap.lol) {
            m_nMouseImageDefID = i;
        }
        fastView.set_image(i);
    }

    public static void setMouseImageByDevice(Boolean bool) {
        setMouseImage(bool.booleanValue() ? R.mipmap.lol : m_nMouseImageDefID);
    }

    public static void showVirtualMouse(int i, int i2) {
        if (mouseImage != null) {
            if (-1 != i) {
                setVirtualMouseLocation(i, i2);
            }
            EasyFloat.showAppFloat(TAG_V_MOUSE);
        }
    }

    public static void hideVirtualMouse() {
        EasyFloat.hideAppFloat(TAG_V_MOUSE);
    }

    public static void setVirtualMouseLocation(int i, int i2) {
        EasyFloat.updateFloat(TAG_V_MOUSE, i, i2);
    }

    public static void resetVirtualMouse() {
        if (FloatManager.INSTANCE.getAppFloatManager(TAG_V_MOUSE) != null) {
            FloatManager.INSTANCE.getAppFloatManager(TAG_V_MOUSE).resetView();
            m_nMouseImageID = -1;
        }
    }

    public static void init_lol_vmouse() {
        final String str = TAG_LOL_MOVE;
        if (EasyFloat.getAppFloatView(str) != null) {
            return;
        }
        EasyFloat.Builder layout = EasyFloat.with(AppInstance.get()).setTag(str).setGravity(17).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(false).setAnimator(null).setLayout(R.layout.mouse, new OnInvokeView() { // from class: com.baidu.kwgames.util.FloatMgr.4
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                ((ImageView) view.findViewById(R.id.image)).setBackground(AppInstance.get().getResources().getDrawable(R.mipmap.lol_click));
                WindowManager.LayoutParams params = FloatManager.INSTANCE.getAppFloatManager(str).getParams();
                FloatMgr.get_float_untouchable_param(params);
                params.width = 57;
                params.height = 57;
                params.alpha = 0.8f;
                FloatMgr.update_float_param_while_create(params);
                EasyFloat.updateFloat(str, AppInstance.s_nScreenW / 2, AppInstance.s_nScreenH / 2);
                EasyFloat.hideAppFloat(str);
            }
        });
        layout.setLocation(AppInstance.s_nScreenW / 2, AppInstance.s_nScreenH / 2);
        layout.show();
    }

    public static void show_lol_move(boolean z, int i, int i2) {
        if (EasyFloat.getAppFloatView(TAG_LOL_MOVE) != null) {
            if (z) {
                if (!s_boLOLMoveIsVisible) {
                    EasyFloat.showAppFloat(TAG_LOL_MOVE);
                    s_boLOLMoveIsVisible = true;
                }
                EasyFloat.updateFloat(TAG_LOL_MOVE, i - 28, i2 - 28);
                return;
            }
            EasyFloat.hideAppFloat(TAG_LOL_MOVE);
            s_boLOLMoveIsVisible = false;
        }
    }

    public static void show_aim_float() {
        show_aim_float(0, 0);
    }

    public static void show_aim_float(int i, int i2) {
        if (g_sAimImage != null) {
            int i3 = ((AppInstance.s_nScreenW - s_nAimFloatSize) >> 1) + i;
            int i4 = ((AppInstance.s_nScreenH - s_nAimFloatSize) >> 1) + i2;
            if (s_nAimFloatX != i3 || s_nAimFloatY != i4) {
                s_nAimFloatX = i3;
                s_nAimFloatY = i4;
                EasyFloat.updateFloat(TAG_AIM_FLOAT, i3, i4);
            }
            if (s_boAimIsVisible) {
                return;
            }
            EasyFloat.showAppFloat(TAG_AIM_FLOAT);
            s_boAimIsVisible = true;
            return;
        }
        update_aim_float_size();
        s_boAimFloatNeedShow = true;
    }

    public static void update_aim_float_size() {
        s_nAimFloatSize = m_ini.getInt(CFG_AIM_FLOAT_SZ, 200);
        s_nAimFloatX = (AppInstance.s_nScreenW - s_nAimFloatSize) >> 1;
        s_nAimFloatY = (AppInstance.s_nScreenH - s_nAimFloatSize) >> 1;
    }

    public static int get_aim_float_size() {
        return s_nAimFloatSize;
    }

    public static void set_aim_float_size(int i) {
        if (g_sAimImage == null || i == s_nAimFloatSize) {
            return;
        }
        s_nAimFloatSize = i;
        m_ini.put(CFG_AIM_FLOAT_SZ, i);
        update_aim_float_size();
        WindowManager.LayoutParams params = FloatManager.INSTANCE.getAppFloatManager(TAG_AIM_FLOAT).getParams();
        int i2 = s_nAimFloatSize;
        params.width = i2;
        params.height = i2;
        g_sAimImage.getLayoutParams().width = s_nAimFloatSize;
        g_sAimImage.getLayoutParams().height = s_nAimFloatSize;
        g_sAimImage.requestLayout();
    }

    public static void show_aim_float(int i, int i2, int i3) {
        init_aim_float(i);
        show_aim_float(i2, i3);
    }

    public static void hide_aim_float() {
        if (s_boAimIsVisible) {
            EasyFloat.hideAppFloat(TAG_AIM_FLOAT);
            s_boAimIsVisible = false;
        }
    }

    public static void update_aim_float_visible(boolean z) {
        if (s_boAimIsVisible == z || EasyFloat.getAppFloatView(TAG_AIM_FLOAT) == null) {
            return;
        }
        s_boAimIsVisible = z;
        if (z) {
            EasyFloat.showAppFloat(TAG_AIM_FLOAT);
        } else {
            EasyFloat.hideAppFloat(TAG_AIM_FLOAT);
        }
    }

    static void set_float_window_name(String str, String str2, boolean z) {
        WindowManager.LayoutParams params;
        AppFloatManager appFloatManager = FloatManager.INSTANCE.getAppFloatManager(str);
        if (appFloatManager == null || (params = appFloatManager.getParams()) == null) {
            return;
        }
        params.setTitle(str2);
        if (z) {
            appFloatManager.resetView();
        }
    }

    public static String get_float_name() {
        return AppInstance.s_boLiveMode ? find_brand_rec_window_name(Build.BRAND.toUpperCase()) : "";
    }

    public static void update_float_for_live_mode(String str) {
        set_float_window_name(str, get_float_name(), true);
    }

    public static void on_live_mode_changed() {
        update_float_for_live_mode(TAG_LOL_MOVE);
        update_float_for_live_mode(TAG_AIM_FLOAT);
        update_float_for_live_mode(Constants.FLOAT_TAG_BALL);
        update_float_for_live_mode(TAG_V_MOUSE);
    }

    public static void update_float_param_while_create(WindowManager.LayoutParams layoutParams) {
        layoutParams.setTitle(get_float_name());
    }

    public static void update_float_param_while_create(String str) {
        WindowManager.LayoutParams params = FloatManager.INSTANCE.getAppFloatManager(str).getParams();
        if (params != null) {
            update_float_param_while_create(params);
        }
    }
}
