package com.liulishuo.filedownloader.services;

import android.app.Notification;
import android.content.Context;
import com.liulishuo.filedownloader.R;
import com.liulishuo.filedownloader.util.FileDownloadLog;
/* loaded from: classes2.dex */
public class ForegroundServiceConfig {
    private static final String DEFAULT_NOTIFICATION_CHANNEL_ID = "filedownloader_channel";
    private static final String DEFAULT_NOTIFICATION_CHANNEL_NAME = "Filedownloader";
    private static final int DEFAULT_NOTIFICATION_ID = 17301506;
    private boolean needRecreateChannelId;
    private Notification notification;
    private String notificationChannelId;
    private String notificationChannelName;
    private int notificationId;

    private ForegroundServiceConfig() {
    }

    public int getNotificationId() {
        return this.notificationId;
    }

    public String getNotificationChannelId() {
        return this.notificationChannelId;
    }

    public String getNotificationChannelName() {
        return this.notificationChannelName;
    }

    public Notification getNotification(Context context) {
        if (this.notification == null) {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "build default notification", new Object[0]);
            }
            this.notification = buildDefaultNotification(context);
        }
        return this.notification;
    }

    public boolean isNeedRecreateChannelId() {
        return this.needRecreateChannelId;
    }

    public void setNotificationId(int i) {
        this.notificationId = i;
    }

    public void setNotificationChannelId(String str) {
        this.notificationChannelId = str;
    }

    public void setNotificationChannelName(String str) {
        this.notificationChannelName = str;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public void setNeedRecreateChannelId(boolean z) {
        this.needRecreateChannelId = z;
    }

    private Notification buildDefaultNotification(Context context) {
        String string = context.getString(R.string.default_filedownloader_notification_title);
        String string2 = context.getString(R.string.default_filedownloader_notification_content);
        Notification.Builder builder = new Notification.Builder(context, this.notificationChannelId);
        builder.setContentTitle(string).setContentText(string2).setSmallIcon(DEFAULT_NOTIFICATION_ID);
        return builder.build();
    }

    public String toString() {
        return "ForegroundServiceConfig{notificationId=" + this.notificationId + ", notificationChannelId='" + this.notificationChannelId + "', notificationChannelName='" + this.notificationChannelName + "', notification=" + this.notification + ", needRecreateChannelId=" + this.needRecreateChannelId + '}';
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private boolean needRecreateChannelId;
        private Notification notification;
        private String notificationChannelId;
        private String notificationChannelName;
        private int notificationId;

        public Builder notificationId(int i) {
            this.notificationId = i;
            return this;
        }

        public Builder notificationChannelId(String str) {
            this.notificationChannelId = str;
            return this;
        }

        public Builder notificationChannelName(String str) {
            this.notificationChannelName = str;
            return this;
        }

        public Builder notification(Notification notification) {
            this.notification = notification;
            return this;
        }

        public Builder needRecreateChannelId(boolean z) {
            this.needRecreateChannelId = z;
            return this;
        }

        public ForegroundServiceConfig build() {
            ForegroundServiceConfig foregroundServiceConfig = new ForegroundServiceConfig();
            String str = this.notificationChannelId;
            if (str == null) {
                str = ForegroundServiceConfig.DEFAULT_NOTIFICATION_CHANNEL_ID;
            }
            foregroundServiceConfig.setNotificationChannelId(str);
            String str2 = this.notificationChannelName;
            if (str2 == null) {
                str2 = ForegroundServiceConfig.DEFAULT_NOTIFICATION_CHANNEL_NAME;
            }
            foregroundServiceConfig.setNotificationChannelName(str2);
            int i = this.notificationId;
            if (i == 0) {
                i = ForegroundServiceConfig.DEFAULT_NOTIFICATION_ID;
            }
            foregroundServiceConfig.setNotificationId(i);
            foregroundServiceConfig.setNeedRecreateChannelId(this.needRecreateChannelId);
            foregroundServiceConfig.setNotification(this.notification);
            return foregroundServiceConfig;
        }
    }
}
