package com.baidu.kwgames;

import com.baidu.kwgames.util.FloatMgr;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
/* loaded from: classes.dex */
public class CrosshairInfo {
    private static SPUtils m_ini = SPUtils.getInstance();
    public boolean m_boHideWhileScopeOn;
    public int m_nOffsetX;
    public int m_nOffsetY;
    public int m_nStyle;
    public String m_strTag;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CrosshairInfo() {
        reset();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void reset() {
        this.m_nStyle = 0;
        this.m_nOffsetX = 0;
        this.m_nOffsetY = 0;
        this.m_boHideWhileScopeOn = true;
    }

    public synchronized void save() {
        String str = this.m_strTag;
        if (str != null && !str.isEmpty()) {
            m_ini.put(this.m_strTag, new Gson().toJson(this));
        }
    }

    public void load(String str) {
        this.m_strTag = str;
        try {
            String string = m_ini.getString(str);
            if (string == null || string.isEmpty()) {
                return;
            }
            CrosshairInfo crosshairInfo = (CrosshairInfo) new Gson().fromJson(string, (Class<Object>) CrosshairInfo.class);
            this.m_nStyle = crosshairInfo.m_nStyle;
            this.m_nOffsetX = crosshairInfo.m_nOffsetX;
            this.m_nOffsetY = crosshairInfo.m_nOffsetY;
            this.m_boHideWhileScopeOn = crosshairInfo.m_boHideWhileScopeOn;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void x_offset_plus() {
        if (Math.abs(this.m_nOffsetX) < ((AppInstance.s_nScreenW - FloatMgr.get_aim_float_size()) >> 1)) {
            this.m_nOffsetX++;
            save();
        }
    }

    public void x_offset_minus() {
        if (Math.abs(this.m_nOffsetX) < ((AppInstance.s_nScreenW - FloatMgr.get_aim_float_size()) >> 1)) {
            this.m_nOffsetX--;
            save();
        }
    }

    public void y_offset_plus() {
        if (Math.abs(this.m_nOffsetY) < ((AppInstance.s_nScreenH - FloatMgr.get_aim_float_size()) >> 1)) {
            this.m_nOffsetY++;
            save();
        }
    }

    public void y_offset_minus() {
        if (Math.abs(this.m_nOffsetY) < ((AppInstance.s_nScreenH - FloatMgr.get_aim_float_size()) >> 1)) {
            this.m_nOffsetY--;
            save();
        }
    }

    public void set_scope_on_hide(boolean z) {
        this.m_boHideWhileScopeOn = z;
        save();
    }
}
