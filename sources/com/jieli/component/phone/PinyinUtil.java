package com.jieli.component.phone;

import com.jieli.component.Logcat;
import java.util.Locale;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
/* loaded from: classes2.dex */
public class PinyinUtil {
    private static String tag = "PinyinUtil";

    public static PinyinBean getPinYin(String str) {
        String str2;
        String str3 = "";
        StringBuilder sb = new StringBuilder();
        try {
            char[] charArray = str.toLowerCase(Locale.CHINA).toCharArray();
            HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
            hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            hanyuPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
            str2 = "";
            for (int i = 0; i < charArray.length; i++) {
                try {
                    if (charArray[i] > 128) {
                        try {
                            String str4 = PinyinHelper.toHanyuPinyinStringArray(charArray[i], hanyuPinyinOutputFormat)[0];
                            str3 = str3 + str4;
                            String replace = str4.replace("ang", "an").replace("eng", "en").replace("ing", "in").replace("ch", "c").replace("sh", "s").replace("zh", "z").replace("hu", "wu").replace("long", "rong").replace("yun", "yue");
                            str2 = str2 + replace;
                            sb.append(replace.charAt(0));
                        } catch (Exception e) {
                            Logcat.w(tag, e.getMessage());
                        }
                    } else {
                        sb.append(charArray[i]);
                        str3 = str3 + charArray[i];
                        str2 = str2 + charArray[i];
                    }
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    PinyinBean pinyinBean = new PinyinBean();
                    pinyinBean.setPinyinName(str3);
                    pinyinBean.setPinyinNameWithNoMatch(str2);
                    pinyinBean.setLetterName(sb.toString());
                    return pinyinBean;
                }
            }
        } catch (Exception e3) {
            e = e3;
            str2 = "";
        }
        PinyinBean pinyinBean2 = new PinyinBean();
        pinyinBean2.setPinyinName(str3);
        pinyinBean2.setPinyinNameWithNoMatch(str2);
        pinyinBean2.setLetterName(sb.toString());
        return pinyinBean2;
    }
}
