package com.baidu.kwgames.util;

import android.view.View;
import com.baidu.kwgames.AppInstance;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes.dex */
public class ImageSwButton {
    static List<SwButtonInfo> s_lstButtons = new ArrayList();

    /* loaded from: classes.dex */
    static class SwButtonInfo {
        public boolean m_boOnOff = false;
        public View m_btn;
        public int m_nChineseOffImageID;
        public int m_nChineseOnImageID;
        public int m_nEnglishOffImageID;
        public int m_nEnglishOnImageID;

        public SwButtonInfo(View view, int i, int i2, int i3, int i4) {
            this.m_btn = view;
            this.m_nChineseOnImageID = i;
            this.m_nChineseOffImageID = i2;
            this.m_nEnglishOnImageID = i3;
            this.m_nEnglishOffImageID = i4;
        }

        public void set_onoff(boolean z) {
            this.m_boOnOff = z;
            if (z) {
                this.m_btn.setBackground(AppInstance.s_context.getResources().getDrawable(AppInstance.m_boIsChineseOS ? this.m_nChineseOnImageID : this.m_nEnglishOnImageID));
            } else {
                this.m_btn.setBackground(AppInstance.s_context.getResources().getDrawable(AppInstance.m_boIsChineseOS ? this.m_nChineseOffImageID : this.m_nEnglishOffImageID));
            }
        }
    }

    public static void add(View view, int i, int i2, int i3, int i4) {
        s_lstButtons.add(new SwButtonInfo(view, i, i2, i3, i4));
    }

    public static void delete(View view) {
        Iterator<SwButtonInfo> it = s_lstButtons.iterator();
        while (it.hasNext()) {
            if (it.next().m_btn == view) {
                it.remove();
            }
        }
    }

    public static void set_onoff(View view, boolean z) {
        for (SwButtonInfo swButtonInfo : s_lstButtons) {
            if (swButtonInfo.m_btn == view) {
                swButtonInfo.set_onoff(z);
                return;
            }
        }
    }

    public static boolean get_onoff(View view) {
        for (SwButtonInfo swButtonInfo : s_lstButtons) {
            if (swButtonInfo.m_btn == view) {
                return swButtonInfo.m_boOnOff;
            }
        }
        return false;
    }
}
