package com.baidu.kwgames;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
/* loaded from: classes.dex */
public class WebHandler extends Handler {
    public static final String SETTING_AD_DOWN_AUTO_AD = "byADDownAutoAD";
    public static final String SETTING_AD_SPEED = "byADDownAutoADSpeed";
    public static final String SETTING_CTRL_REPEAT_SPEED = "ctrlrepeatsp";
    public static final String SETTING_HP_VOL = "hpvol";
    public static final String SETTING_SHUNFENGER = "shunfenger";
    public static final String SETTING_STATUS = "status";
    public static final String SETTING_STATUS_EXT = "statusExt";
    public static final String SETTING_STATUS_EXT3 = "statusExt3";
    public static final String SETTING_STATUS_EXT4 = "statusExt4";
    public static final String SETTING_STATUS_EXT5 = "statusExt5";
    Context m_context;
    String m_strURL;

    /* JADX INFO: Access modifiers changed from: package-private */
    public WebHandler(Context context) {
        this.m_context = context;
    }

    public WebHandler set_url(String str) {
        this.m_strURL = str;
        return this;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (1 == message.arg2) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(this.m_strURL));
            this.m_context.startActivity(intent);
        }
    }
}
