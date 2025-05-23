package com.baidu.kwgames.util;

import android.text.TextUtils;
import com.baidu.kwgames.AppInstance;
import com.baidu.kwgames.Constants;
import com.baidu.kwgames.UIConst;
import com.baidu.kwgames.bean.ViewInfo;
import com.baidu.kwgames.bean.ViewInfo63;
import com.google.gson.Gson;
import org.opencv.videoio.Videoio;
/* loaded from: classes.dex */
public class ViewMgr {
    private static final int TAKE_OFF_SIZE_DEF = 72;
    private static ViewMgr instance;
    private static ViewInfo s_vfForAI = new ViewInfo();
    private static ViewInfo viewInfo;

    public static int make_sure_xy_in_range(int i, int i2, int i3) {
        if (i + i2 > i3) {
            return i3 - i2;
        }
        if (i < 0) {
            return 0;
        }
        return i;
    }

    public static ViewMgr getInstance() {
        if (instance == null) {
            instance = new ViewMgr();
        }
        return instance;
    }

    public static void make_all_viewinf_in_screen() {
        int i = AppInstance.s_nScreenW;
        int i2 = AppInstance.s_nScreenH;
        viewInfo.gun1.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun1.x.intValue(), viewInfo.gun1.width.intValue(), i));
        viewInfo.gun1.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun1.y.intValue(), viewInfo.gun1.height.intValue(), i2));
        viewInfo.gun2.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun2.x.intValue(), viewInfo.gun2.width.intValue(), i));
        viewInfo.gun2.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun2.y.intValue(), viewInfo.gun2.height.intValue(), i2));
        viewInfo.mirrorDTO.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.mirrorDTO.x.intValue(), viewInfo.mirrorDTO.width.intValue(), i));
        viewInfo.mirrorDTO.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.mirrorDTO.y.intValue(), viewInfo.mirrorDTO.height.intValue(), i2));
        viewInfo.downDTO.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.downDTO.x.intValue(), viewInfo.downDTO.width.intValue(), i));
        viewInfo.downDTO.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.downDTO.y.intValue(), viewInfo.downDTO.height.intValue(), i2));
        viewInfo.squatDTO.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.squatDTO.x.intValue(), viewInfo.squatDTO.width.intValue(), i));
        viewInfo.squatDTO.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.squatDTO.y.intValue(), viewInfo.squatDTO.height.intValue(), i2));
        viewInfo.doubleMirror.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.doubleMirror.x.intValue(), viewInfo.doubleMirror.width.intValue(), i));
        viewInfo.doubleMirror.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.doubleMirror.y.intValue(), viewInfo.doubleMirror.height.intValue(), i2));
        viewInfo.bagTag.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.bagTag.x.intValue(), viewInfo.bagTag.width.intValue(), i));
        viewInfo.bagTag.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.bagTag.y.intValue(), viewInfo.bagTag.height.intValue(), i2));
        viewInfo.takeOffTag.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.takeOffTag.x.intValue(), viewInfo.takeOffTag.width.intValue(), i));
        viewInfo.takeOffTag.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.takeOffTag.y.intValue(), viewInfo.takeOffTag.height.intValue(), i2));
        viewInfo.gun1Head.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun1Head.x.intValue(), viewInfo.gun1Head.width.intValue(), i));
        viewInfo.gun1Head.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun1Head.y.intValue(), viewInfo.gun1Head.height.intValue(), i2));
        viewInfo.gun2Head.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun2Head.x.intValue(), viewInfo.gun2Head.width.intValue(), i));
        viewInfo.gun2Head.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun2Head.y.intValue(), viewInfo.gun2Head.height.intValue(), i2));
        viewInfo.gun1Handle.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun1Handle.x.intValue(), viewInfo.gun1Handle.width.intValue(), i));
        viewInfo.gun1Handle.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun1Handle.y.intValue(), viewInfo.gun1Handle.height.intValue(), i2));
        viewInfo.gun2Handle.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun2Handle.x.intValue(), viewInfo.gun2Handle.width.intValue(), i));
        viewInfo.gun2Handle.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun2Handle.y.intValue(), viewInfo.gun2Handle.height.intValue(), i2));
        viewInfo.gun1Tail.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun1Tail.x.intValue(), viewInfo.gun1Tail.width.intValue(), i));
        viewInfo.gun1Tail.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun1Tail.y.intValue(), viewInfo.gun1Tail.height.intValue(), i2));
        viewInfo.gun2Tail.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun2Tail.x.intValue(), viewInfo.gun2Tail.width.intValue(), i));
        viewInfo.gun2Tail.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.gun2Tail.y.intValue(), viewInfo.gun2Tail.height.intValue(), i2));
        ensure_AI_result_float_visible();
    }

    public static void init_new_float_items_as_factory() {
        int i = AppInstance.s_nScreenW;
        int i2 = AppInstance.s_nScreenH;
        if (NEAT.is_pad()) {
            viewInfo.bagTag.set((i * 2472) / 2560, (i2 * Constants.E_BLE_BUF_TYPE_AI_XSCOPE) / Videoio.CAP_OPENNI2, 51, 63);
            if (AppInstance.is_hpjy_AI_kit()) {
                viewInfo.takeOffTag.set((i * 2308) / 2560, (i2 * 710) / Videoio.CAP_OPENNI2, 72, 72);
            } else {
                viewInfo.takeOffTag.set((i * 2308) / 2560, (i2 * 710) / Videoio.CAP_OPENNI2, 79, 73);
            }
            viewInfo.gun1Head.set((i * 1477) / 2560, (i2 * Videoio.CAP_PROP_XI_CC_MATRIX_20) / Videoio.CAP_OPENNI2, 115, 115);
            viewInfo.gun1Handle.set((i * 1628) / 2560, viewInfo.gun1Head.y.intValue(), 115, 115);
            viewInfo.gun1Tail.set((i * 1936) / 2560, viewInfo.gun1Head.y.intValue(), 115, 115);
            viewInfo.gun2Head.set(viewInfo.gun1Head.x.intValue(), (i2 * 1133) / Videoio.CAP_OPENNI2, 115, 115);
            viewInfo.gun2Handle.set(viewInfo.gun1Handle.x.intValue(), viewInfo.gun2Head.y.intValue(), 115, 115);
            viewInfo.gun2Tail.set(viewInfo.gun1Tail.x.intValue(), viewInfo.gun2Head.y.intValue(), 115, 115);
            return;
        }
        viewInfo.bagTag.set((i * 2186) / 2340, (i2 * 117) / UIConst.E_TOUPING_DISPLAY_Y_DEF, 40, 49);
        if (AppInstance.is_hpjy_AI_kit()) {
            viewInfo.takeOffTag.set((i * 2115) / Videoio.CAP_XINE, (i2 * 374) / 1088, 72, 72);
        } else {
            viewInfo.takeOffTag.set((i * 2115) / Videoio.CAP_XINE, (i2 * 374) / 1088, 62, 57);
        }
        viewInfo.gun1Head.set((i * 1387) / 2340, (i2 * 292) / UIConst.E_TOUPING_DISPLAY_Y_DEF, 91, 91);
        viewInfo.gun1Handle.set((i * 1512) / 2340, viewInfo.gun1Head.y.intValue(), 91, 91);
        viewInfo.gun1Tail.set((i * 1757) / 2340, viewInfo.gun1Head.y.intValue(), 91, 91);
        viewInfo.gun2Head.set(viewInfo.gun1Head.x.intValue(), (i2 * 709) / UIConst.E_TOUPING_DISPLAY_Y_DEF, 91, 91);
        viewInfo.gun2Handle.set(viewInfo.gun1Handle.x.intValue(), viewInfo.gun2Head.y.intValue(), 91, 91);
        viewInfo.gun2Tail.set(viewInfo.gun1Tail.x.intValue(), viewInfo.gun2Head.y.intValue(), 91, 91);
    }

    public static ViewInfo getViewInfo() {
        ViewInfo viewInfo2 = viewInfo;
        if (viewInfo2 != null) {
            return viewInfo2;
        }
        int i = AppInstance.s_nScreenW;
        int i2 = AppInstance.s_nScreenH;
        String item63 = Util.getItem63();
        if (TextUtils.isEmpty(item63)) {
            String item = Util.getItem(Constants.get_cur_AI_kit().m_strAIFloatViewPosSec);
            Boolean valueOf = Boolean.valueOf(TextUtils.isEmpty(item));
            if (!valueOf.booleanValue()) {
                try {
                    viewInfo = (ViewInfo) new Gson().fromJson(item, (Class<Object>) ViewInfo.class);
                    if (AppInstance.is_hpjy_AI_kit() && viewInfo.takeOffTag.width != viewInfo.takeOffTag.height) {
                        if (NEAT.is_pad()) {
                            viewInfo.takeOffTag.set((i * 2308) / 2560, (i2 * 710) / Videoio.CAP_OPENNI2, 72, 72);
                        } else {
                            viewInfo.takeOffTag.set((i * 2115) / Videoio.CAP_XINE, (i2 * 374) / 1088, 72, 72);
                        }
                    }
                    make_all_viewinf_in_screen();
                } catch (Exception e) {
                    e.printStackTrace();
                    valueOf = true;
                }
            }
            if (valueOf.booleanValue()) {
                ViewInfo viewInfo3 = new ViewInfo();
                viewInfo = viewInfo3;
                viewInfo3.selectTag = Constants.FLOAT_TAG_AI_GUN1;
                viewInfo.tipMask.x = Integer.valueOf(AppInstance.s_nScreenW / 2);
                viewInfo.tipMask.y = 100;
                if (NEAT.is_pad()) {
                    int i3 = (i2 * 1393) / Videoio.CAP_OPENNI2;
                    viewInfo.gun1.set((i * 962) / 2560, i3, 315, 100);
                    viewInfo.gun2.set((i * 1285) / 2560, i3, 315, 100);
                    viewInfo.mirrorDTO.set((i * 2404) / 2560, (i2 * 845) / Videoio.CAP_OPENNI2, 120, 120);
                    viewInfo.downDTO.set((i * 2378) / 2560, (i2 * 1407) / Videoio.CAP_OPENNI2, 120, 120);
                    viewInfo.squatDTO.set((i * 2147) / 2560, (i2 * 1461) / Videoio.CAP_OPENNI2, 120, 120);
                    if (AppInstance.is_hpjy_AI_kit()) {
                        viewInfo.doubleMirror.set((i * 2449) / 2560, (i2 * Videoio.CAP_PROP_XI_LUT_INDEX) / Videoio.CAP_OPENNI2, 76, 36);
                    } else if (AppInstance.is_pubg_AI_kit()) {
                        viewInfo.doubleMirror.set((i * 2168) / 2560, (i2 * Videoio.CAP_PROP_XI_DOWNSAMPLING_TYPE) / Videoio.CAP_OPENNI2, 49, 44);
                    }
                } else {
                    int i4 = (i2 * 915) / UIConst.E_TOUPING_DISPLAY_Y_DEF;
                    viewInfo.gun1.set((i * 917) / 2340, i4, 253, 81);
                    viewInfo.gun2.set((i * 1173) / 2340, i4, 253, 81);
                    viewInfo.mirrorDTO.set((i * 2118) / 2340, (i2 * Videoio.CAP_PROP_XI_TARGET_TEMP) / UIConst.E_TOUPING_DISPLAY_Y_DEF, 120, 120);
                    viewInfo.downDTO.set((i * 2091) / 2340, (i2 * 913) / UIConst.E_TOUPING_DISPLAY_Y_DEF, 120, 120);
                    viewInfo.squatDTO.set((i * 1904) / 2340, (i2 * 948) / UIConst.E_TOUPING_DISPLAY_Y_DEF, 120, 120);
                    if (AppInstance.is_hpjy_AI_kit()) {
                        viewInfo.doubleMirror.set((i * 2168) / 2340, (i2 * Videoio.CAP_PROP_XI_DOWNSAMPLING_TYPE) / UIConst.E_TOUPING_DISPLAY_Y_DEF, 57, 28);
                    } else if (AppInstance.is_pubg_AI_kit()) {
                        if (NEAT.is_turkey()) {
                            viewInfo.doubleMirror.set((i * 2168) / 2340, (i2 * Videoio.CAP_PROP_XI_DOWNSAMPLING_TYPE) / UIConst.E_TOUPING_DISPLAY_Y_DEF, 53, 27);
                        } else if (NEAT.is_vitnam()) {
                            viewInfo.doubleMirror.set((i * 2168) / 2340, (i2 * Videoio.CAP_PROP_XI_DOWNSAMPLING_TYPE) / UIConst.E_TOUPING_DISPLAY_Y_DEF, 56, 28);
                        } else {
                            viewInfo.doubleMirror.set((i * 2168) / 2340, (i2 * Videoio.CAP_PROP_XI_DOWNSAMPLING_TYPE) / UIConst.E_TOUPING_DISPLAY_Y_DEF, 49, 44);
                        }
                    }
                }
                init_new_float_items_as_factory();
                make_all_viewinf_in_screen();
                save();
            }
        } else {
            ViewInfo viewInfo4 = new ViewInfo();
            viewInfo = viewInfo4;
            ((ViewInfo63) new Gson().fromJson(item63, (Class<Object>) ViewInfo63.class)).convert_to_new(viewInfo4);
            Util.removeItem63();
            init_new_float_items_as_factory();
            make_all_viewinf_in_screen();
            save();
        }
        return viewInfo;
    }

    public static void ensure_AI_result_float_visible() {
        viewInfo.tipMask.x = Integer.valueOf(make_sure_xy_in_range(viewInfo.tipMask.x.intValue(), Util.dip2px(AppInstance.s_context, 20.0f), AppInstance.s_nScreenW));
        viewInfo.tipMask.y = Integer.valueOf(make_sure_xy_in_range(viewInfo.tipMask.y.intValue(), Util.dip2px(AppInstance.s_context, 20.0f), AppInstance.s_nScreenH));
    }

    public static void update_view_pos(ViewInfo.MaskDTO maskDTO, ViewInfo.MaskDTO maskDTO2) {
        maskDTO.x = Integer.valueOf(AIConvert.x(maskDTO2.x.intValue()));
        maskDTO.y = Integer.valueOf(AIConvert.y(maskDTO2.y.intValue()));
        maskDTO.width = Integer.valueOf(AIConvert.width(maskDTO2.width.intValue()));
        maskDTO.height = Integer.valueOf(AIConvert.height(maskDTO2.height.intValue()));
    }

    public static void update_view_info_for_AI() {
        if (viewInfo == null) {
            return;
        }
        update_view_pos(s_vfForAI.gun1, viewInfo.gun1);
        update_view_pos(s_vfForAI.gun2, viewInfo.gun2);
        update_view_pos(s_vfForAI.mirrorDTO, viewInfo.mirrorDTO);
        update_view_pos(s_vfForAI.downDTO, viewInfo.downDTO);
        update_view_pos(s_vfForAI.squatDTO, viewInfo.squatDTO);
        update_view_pos(s_vfForAI.doubleMirror, viewInfo.doubleMirror);
        update_view_pos(s_vfForAI.bagTag, viewInfo.bagTag);
        update_view_pos(s_vfForAI.takeOffTag, viewInfo.takeOffTag);
        update_view_pos(s_vfForAI.gun1Head, viewInfo.gun1Head);
        update_view_pos(s_vfForAI.gun2Head, viewInfo.gun2Head);
        update_view_pos(s_vfForAI.gun1Handle, viewInfo.gun1Handle);
        update_view_pos(s_vfForAI.gun2Handle, viewInfo.gun2Handle);
        update_view_pos(s_vfForAI.gun1Tail, viewInfo.gun1Tail);
        update_view_pos(s_vfForAI.gun2Tail, viewInfo.gun2Tail);
    }

    public static ViewInfo get_view_info_for_AI() {
        return s_vfForAI;
    }

    public static synchronized void save() {
        synchronized (ViewMgr.class) {
            if (viewInfo == null) {
                return;
            }
            Util.saveItem(Constants.get_cur_AI_kit().m_strAIFloatViewPosSec, new Gson().toJson(viewInfo));
        }
    }
}
