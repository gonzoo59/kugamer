package com.jieli.jl_bt_ota.util;

import com.jieli.jl_bt_ota.model.base.AttrBean;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class ParseDataUtil {
    private static String TAG = "ParseDataUtil";

    public static List<AttrBean> coverParamDataToAttrBeans(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < bArr.length) {
            int i2 = i + 1;
            int byteToInt = CHexConver.byteToInt(bArr[i]);
            if (byteToInt < 1) {
                return arrayList;
            }
            AttrBean attrBean = new AttrBean();
            int i3 = i2 + 1;
            attrBean.setType(bArr[i2]);
            int i4 = byteToInt - 1;
            byte[] bArr2 = new byte[i4];
            if (bArr.length - i3 < i4) {
                return arrayList;
            }
            System.arraycopy(bArr, i3, bArr2, 0, i4);
            attrBean.setAttrData(bArr2);
            i = i4 + i3;
            arrayList.add(attrBean);
        }
        return arrayList;
    }
}
