package com.baidu.kwgames;

import com.blankj.utilcode.util.ConvertUtils;
/* loaded from: classes.dex */
public class KeyInfo {
    public int h;
    public byte id;
    public int imageId;
    public int m_nMacroTriggerMode = 0;
    public int m_nMacroUUID = -1;
    public int property;
    public String tag;
    public String title;
    public int type;
    public int w;
    public int x;
    public int y;

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean is_macro() {
        return this.type == 4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean is_macro_first_key() {
        return this.type == 4 && this.property != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void set_normal_size_xy(int i, int i2) {
        int dp2px = i - ConvertUtils.dp2px(15.0f);
        this.x = dp2px;
        if (dp2px < 0) {
            this.x = 0;
        }
        int dp2px2 = i2 - ConvertUtils.dp2px(15.0f);
        this.y = dp2px2;
        if (dp2px2 < 0) {
            this.y = 0;
        }
        this.w = ConvertUtils.dp2px(30.0f);
        this.h = ConvertUtils.dp2px(30.0f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void set_data(byte b, int i, int i2, int i3) {
        this.id = b;
        this.type = i;
        this.m_nMacroTriggerMode = i2;
        this.m_nMacroUUID = i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void ensure_xy_in_screen() {
        if (this.x < 0) {
            this.x = 0;
        }
        if (this.y < 0) {
            this.y = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean same_macro(KeyInfo keyInfo) {
        return this.m_nMacroUUID == keyInfo.m_nMacroUUID;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean same_key(KeyInfo keyInfo) {
        return this.id == keyInfo.id;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void copy_macro(KeyInfo keyInfo) {
        this.id = keyInfo.id;
        this.type = keyInfo.type;
        this.m_nMacroTriggerMode = keyInfo.m_nMacroTriggerMode;
        this.m_nMacroUUID = keyInfo.m_nMacroUUID;
    }
}
