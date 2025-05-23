package com.baidu.kwgames.util;
/* loaded from: classes.dex */
public class AIKit {
    public int m_nScopeModelCh;
    public int m_nScopeModelEn;
    public int m_nTakeOffModel;
    public String m_strAIActiveRunFileName;
    public String m_strAIChModelFileName;
    public String m_strAICrosshairFileName;
    public String m_strAIEnModelFileName;
    public String m_strAIFloatViewPosSec;
    public String m_strAIGunDownFileName;
    public String m_strAIGunPartsReduceFileName;
    public String m_strAIGunStageFileName;
    public String m_strAIGunStageFileNameM4;
    public String m_strAIXScopeFileName;

    public AIKit(String str, String str2, String str3, String str4, String str5, String str6, String str7, int i, int i2, String str8, String str9, String str10, int i3) {
        this.m_strAIGunDownFileName = str;
        this.m_strAIGunStageFileName = str2;
        this.m_strAIGunPartsReduceFileName = str3;
        this.m_strAIFloatViewPosSec = str4;
        this.m_strAIChModelFileName = str5;
        this.m_strAIEnModelFileName = str6;
        this.m_strAICrosshairFileName = str7;
        this.m_strAIActiveRunFileName = str9;
        this.m_nScopeModelCh = i;
        this.m_nScopeModelEn = i2;
        this.m_strAIXScopeFileName = str8;
        this.m_strAIGunStageFileNameM4 = str10;
        this.m_nTakeOffModel = i3;
    }
}
