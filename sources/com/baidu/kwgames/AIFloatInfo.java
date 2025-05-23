package com.baidu.kwgames;

import android.view.View;
import com.baidu.kwgames.bean.ViewInfo;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class AIFloatInfo {
    public ViewInfo.MaskDTO m_data;
    public ViewInfo.MaskDTO m_dataAI;
    public String m_strTag;
    public View m_viewFrame;
    public View m_viewMask;

    AIFloatInfo() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AIFloatInfo(String str, ViewInfo.MaskDTO maskDTO, ViewInfo.MaskDTO maskDTO2) {
        this.m_strTag = str;
        this.m_data = maskDTO;
        this.m_dataAI = maskDTO2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void set_mask_frame(View view, View view2) {
        this.m_viewMask = view;
        this.m_viewFrame = view2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<String> get_same_x_tag() {
        ArrayList arrayList = new ArrayList();
        String str = this.m_strTag;
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case 179370297:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_HEAD1)) {
                    c = 0;
                    break;
                }
                break;
            case 179370298:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_HEAD2)) {
                    c = 1;
                    break;
                }
                break;
            case 190341321:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_TAIL1)) {
                    c = 2;
                    break;
                }
                break;
            case 190341322:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_TAIL2)) {
                    c = 3;
                    break;
                }
                break;
            case 473712625:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_HANDLE1)) {
                    c = 4;
                    break;
                }
                break;
            case 473712626:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_HANDLE2)) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_HEAD2);
                break;
            case 1:
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_HEAD1);
                break;
            case 2:
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_TAIL2);
                break;
            case 3:
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_TAIL1);
                break;
            case 4:
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_HANDLE2);
                break;
            case 5:
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_HANDLE1);
                break;
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<String> get_same_y_tag() {
        ArrayList arrayList = new ArrayList();
        String str = this.m_strTag;
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case 179370297:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_HEAD1)) {
                    c = 0;
                    break;
                }
                break;
            case 179370298:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_HEAD2)) {
                    c = 1;
                    break;
                }
                break;
            case 190341321:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_TAIL1)) {
                    c = 2;
                    break;
                }
                break;
            case 190341322:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_TAIL2)) {
                    c = 3;
                    break;
                }
                break;
            case 473712625:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_HANDLE1)) {
                    c = 4;
                    break;
                }
                break;
            case 473712626:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_HANDLE2)) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_HANDLE1);
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_TAIL1);
                break;
            case 1:
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_HANDLE2);
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_TAIL2);
                break;
            case 2:
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_HEAD1);
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_HANDLE1);
                break;
            case 3:
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_HEAD2);
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_HANDLE2);
                break;
            case 4:
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_HEAD1);
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_TAIL1);
                break;
            case 5:
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_HEAD2);
                arrayList.add(Constants.FLOAT_TAG_AI_GUN_TAIL2);
                break;
        }
        return arrayList;
    }
}
