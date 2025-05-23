package com.jieli.component.utils;

import java.io.UnsupportedEncodingException;
/* loaded from: classes2.dex */
public class Base64Utils {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final char LAST2BYTE = (char) Integer.parseInt("00000011", 2);
    private static final char LAST4BYTE = (char) Integer.parseInt("00001111", 2);
    private static final char LAST6BYTE = (char) Integer.parseInt("00111111", 2);
    private static final char LEAD6BYTE = (char) Integer.parseInt("11111100", 2);
    private static final char LEAD4BYTE = (char) Integer.parseInt("11110000", 2);
    private static final char LEAD2BYTE = (char) Integer.parseInt("11000000", 2);
    private static final char[] ENCODE_TABLE = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    public static String base64Encode(byte[] bArr) {
        int i;
        int i2;
        StringBuilder sb = new StringBuilder(((int) (bArr.length * 1.34d)) + 3);
        int i3 = 0;
        char c = 0;
        for (int i4 = 0; i4 < bArr.length; i4++) {
            i3 %= 8;
            while (i3 < 8) {
                if (i3 == 0) {
                    i = ((char) (bArr[i4] & LEAD6BYTE)) >>> 2;
                } else if (i3 == 2) {
                    i = bArr[i4] & LAST6BYTE;
                } else if (i3 == 4) {
                    c = (char) (((char) (bArr[i4] & LAST4BYTE)) << 2);
                    int i5 = i4 + 1;
                    if (i5 < bArr.length) {
                        i2 = (bArr[i5] & LEAD2BYTE) >>> 6;
                        i = c | i2;
                    } else {
                        sb.append(ENCODE_TABLE[c]);
                        i3 += 6;
                    }
                } else {
                    if (i3 == 6) {
                        c = (char) (((char) (bArr[i4] & LAST2BYTE)) << 4);
                        int i6 = i4 + 1;
                        if (i6 < bArr.length) {
                            i2 = (bArr[i6] & LEAD4BYTE) >>> 4;
                            i = c | i2;
                        }
                    }
                    sb.append(ENCODE_TABLE[c]);
                    i3 += 6;
                }
                c = (char) i;
                sb.append(ENCODE_TABLE[c]);
                i3 += 6;
            }
        }
        if (sb.length() % 4 != 0) {
            for (int length = 4 - (sb.length() % 4); length > 0; length--) {
                sb.append("=");
            }
        }
        return sb.toString();
    }

    public static String base64Encode(String str) throws UnsupportedEncodingException {
        return base64Encode(str.getBytes("UTF-8"));
    }
}
