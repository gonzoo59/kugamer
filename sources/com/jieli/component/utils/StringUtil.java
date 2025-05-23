package com.jieli.component.utils;

import java.util.regex.Pattern;
/* loaded from: classes2.dex */
public class StringUtil {
    public static boolean isAllLetter(String str) {
        return str.matches("[a-zA-Z]+");
    }

    public static boolean isAllLetterAndNum(String str) {
        return str.matches("[a-zA-Z0-9]+");
    }

    public static String removeAllMark(String str) {
        return Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& ;*（）_——+|{}【】‘；：”“’。，、？-]").matcher(str).replaceAll("").trim();
    }

    public static String splitAndFilterString(String str, int i) {
        if (str == null || str.trim().equals("")) {
            return "";
        }
        String replaceAll = str.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
        if (replaceAll.length() <= i) {
            return replaceAll;
        }
        String substring = replaceAll.substring(0, i);
        return substring + "......";
    }
}
