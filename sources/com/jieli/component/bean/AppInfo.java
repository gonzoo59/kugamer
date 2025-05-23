package com.jieli.component.bean;

import android.graphics.drawable.Drawable;
/* loaded from: classes2.dex */
public class AppInfo {
    private boolean isSystem;
    private Drawable logo;
    private String name;
    private String packageName;
    private int versionCode;
    private String versionName;

    public String getName() {
        return this.name;
    }

    public AppInfo setName(String str) {
        this.name = str;
        return this;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public AppInfo setPackageName(String str) {
        this.packageName = str;
        return this;
    }

    public boolean isSystem() {
        return this.isSystem;
    }

    public AppInfo setSystem(boolean z) {
        this.isSystem = z;
        return this;
    }

    public Drawable getLogo() {
        return this.logo;
    }

    public AppInfo setLogo(Drawable drawable) {
        this.logo = drawable;
        return this;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public AppInfo setVersionName(String str) {
        this.versionName = str;
        return this;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public AppInfo setVersionCode(int i) {
        this.versionCode = i;
        return this;
    }

    public String toString() {
        return "AppInfo{name='" + this.name + "', packageName='" + this.packageName + "', isSystem=" + this.isSystem + ", logo=" + this.logo + ", versionName='" + this.versionName + "', versionCode=" + this.versionCode + '}';
    }
}
