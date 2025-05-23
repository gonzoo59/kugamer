package com.baidu.kwgames;
/* loaded from: classes.dex */
public class ModelInfo {
    public boolean m_boSupportAIGunDown;
    public boolean m_boSupportMOBA;
    public boolean m_boSupportMouse;
    public boolean m_boSupportUAC;
    public boolean m_boSupportUSBMode;
    public boolean m_boSupportVMouseUSB;
    public boolean m_boSupportX1;
    public int m_nKeyMapCnt;

    ModelInfo() {
        this.m_nKeyMapCnt = 8;
        this.m_boSupportMouse = true;
        this.m_boSupportX1 = false;
        this.m_boSupportMOBA = false;
        this.m_boSupportUSBMode = false;
        this.m_boSupportUAC = false;
        this.m_boSupportAIGunDown = false;
        this.m_boSupportVMouseUSB = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ModelInfo(int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.m_nKeyMapCnt = i;
        this.m_boSupportX1 = z;
        this.m_boSupportMOBA = z2;
        this.m_boSupportUSBMode = z3;
        this.m_boSupportUAC = z4;
        this.m_boSupportAIGunDown = z5;
        this.m_boSupportVMouseUSB = z6;
    }
}
