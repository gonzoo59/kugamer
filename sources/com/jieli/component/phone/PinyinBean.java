package com.jieli.component.phone;
/* loaded from: classes2.dex */
public class PinyinBean {
    private String letterName;
    private String pinyinName;
    private String pinyinNameWithNoMatch;

    public void setPinyinNameWithNoMatch(String str) {
        this.pinyinNameWithNoMatch = str;
    }

    public void setPinyinName(String str) {
        this.pinyinName = str;
    }

    public void setLetterName(String str) {
        this.letterName = str;
    }

    public String getPinyinNameWithNoMatch() {
        return this.pinyinNameWithNoMatch;
    }

    public String getPinyinName() {
        return this.pinyinName;
    }

    public String toString() {
        return "PinyinBean{pinyinName='" + this.pinyinName + "', pinyinNameWithNoMatch='" + this.pinyinNameWithNoMatch + "', letterName='" + this.letterName + "'}";
    }

    public String getLetterName() {
        return this.letterName;
    }
}
