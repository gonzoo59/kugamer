package com.jieli.jl_bt_ota.model;

import com.jieli.jl_bt_ota.model.response.TargetInfoResponse;
/* loaded from: classes2.dex */
public class DeviceStatus {
    private boolean isAuthDevice;
    private boolean isAuthProgressResult;
    private boolean isEnterLowPowerMode;
    private boolean isMandatoryUpgrade;
    private String mDevMD5;
    private TargetInfoResponse mTargetInfo;
    private String reconnectEdrAddress;
    private int status;

    public int getStatus() {
        return this.status;
    }

    public DeviceStatus setStatus(int i) {
        this.status = i;
        return this;
    }

    public boolean isAuthDevice() {
        return this.isAuthDevice;
    }

    public DeviceStatus setAuthDevice(boolean z) {
        this.isAuthDevice = z;
        return this;
    }

    public boolean isEnterLowPowerMode() {
        return this.isEnterLowPowerMode;
    }

    public DeviceStatus setEnterLowPowerMode(boolean z) {
        this.isEnterLowPowerMode = z;
        return this;
    }

    public boolean isMandatoryUpgrade() {
        return this.isMandatoryUpgrade;
    }

    public DeviceStatus setMandatoryUpgrade(boolean z) {
        this.isMandatoryUpgrade = z;
        return this;
    }

    public String getReconnectEdrAddress() {
        return this.reconnectEdrAddress;
    }

    public DeviceStatus setReconnectEdrAddress(String str) {
        this.reconnectEdrAddress = str;
        return this;
    }

    public TargetInfoResponse getTargetInfo() {
        return this.mTargetInfo;
    }

    public DeviceStatus setTargetInfo(TargetInfoResponse targetInfoResponse) {
        this.mTargetInfo = targetInfoResponse;
        return this;
    }

    public boolean isAuthProgressResult() {
        return this.isAuthProgressResult;
    }

    public DeviceStatus setAuthProgressResult(boolean z) {
        this.isAuthProgressResult = z;
        return this;
    }

    public String getDevMD5() {
        return this.mDevMD5;
    }

    public DeviceStatus setDevMD5(String str) {
        this.mDevMD5 = str;
        return this;
    }

    public String toString() {
        return "DeviceStatus{status=" + this.status + ", isAuthDevice=" + this.isAuthDevice + ", isAuthProgressResult=" + this.isAuthProgressResult + ", isEnterLowPowerMode=" + this.isEnterLowPowerMode + ", isMandatoryUpgrade=" + this.isMandatoryUpgrade + ", reconnectEdrAddress='" + this.reconnectEdrAddress + "', mTargetInfo=" + this.mTargetInfo + ", mDevMD5='" + this.mDevMD5 + "'}";
    }
}
