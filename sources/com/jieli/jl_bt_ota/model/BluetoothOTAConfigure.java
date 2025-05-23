package com.jieli.jl_bt_ota.model;

import com.jieli.jl_bt_ota.util.CHexConver;
/* loaded from: classes2.dex */
public class BluetoothOTAConfigure {
    public static final int PREFER_BLE = 0;
    public static final int PREFER_SPP = 1;
    private byte[] firmwareFileData;
    private String firmwareFilePath;
    private boolean isUseAuthDevice;
    @Deprecated
    private String scanFilterData;
    private int priority = 0;
    private boolean isUseReconnect = false;
    private int bleIntervalMs = 500;
    private int timeoutMs = 3000;
    private boolean isUseJLServer = false;
    private int mtu = 20;
    private boolean isNeedChangeMtu = false;
    private int bleScanMode = 0;

    public static BluetoothOTAConfigure createDefault() {
        return new BluetoothOTAConfigure().setPriority(0).setBleIntervalMs(500).setTimeoutMs(3000).setUseAuthDevice(false).setUseReconnect(false).setMtu(20).setUseJLServer(false).setNeedChangeMtu(false);
    }

    public int getPriority() {
        return this.priority;
    }

    public BluetoothOTAConfigure setPriority(int i) {
        this.priority = i;
        return this;
    }

    public boolean isUseReconnect() {
        return this.isUseReconnect;
    }

    public BluetoothOTAConfigure setUseReconnect(boolean z) {
        this.isUseReconnect = z;
        return this;
    }

    public boolean isUseAuthDevice() {
        return this.isUseAuthDevice;
    }

    public BluetoothOTAConfigure setUseAuthDevice(boolean z) {
        this.isUseAuthDevice = z;
        return this;
    }

    public int getBleIntervalMs() {
        return this.bleIntervalMs;
    }

    public BluetoothOTAConfigure setBleIntervalMs(int i) {
        this.bleIntervalMs = i;
        return this;
    }

    public int getTimeoutMs() {
        return this.timeoutMs;
    }

    public BluetoothOTAConfigure setTimeoutMs(int i) {
        this.timeoutMs = i;
        return this;
    }

    @Deprecated
    public String getScanFilterData() {
        return this.scanFilterData;
    }

    @Deprecated
    public BluetoothOTAConfigure setScanFilterData(String str) {
        this.scanFilterData = str;
        return this;
    }

    public int getMtu() {
        return this.mtu;
    }

    public BluetoothOTAConfigure setMtu(int i) {
        this.mtu = i;
        return this;
    }

    public boolean isUseJLServer() {
        return this.isUseJLServer;
    }

    public BluetoothOTAConfigure setUseJLServer(boolean z) {
        this.isUseJLServer = z;
        return this;
    }

    public String getFirmwareFilePath() {
        return this.firmwareFilePath;
    }

    public BluetoothOTAConfigure setFirmwareFilePath(String str) {
        this.firmwareFilePath = str;
        return this;
    }

    public int getBleScanMode() {
        return this.bleScanMode;
    }

    public BluetoothOTAConfigure setBleScanMode(int i) {
        this.bleScanMode = i;
        return this;
    }

    public boolean isNeedChangeMtu() {
        return this.isNeedChangeMtu;
    }

    public BluetoothOTAConfigure setNeedChangeMtu(boolean z) {
        this.isNeedChangeMtu = z;
        return this;
    }

    public byte[] getFirmwareFileData() {
        return this.firmwareFileData;
    }

    public BluetoothOTAConfigure setFirmwareFileData(byte[] bArr) {
        this.firmwareFileData = bArr;
        return this;
    }

    public String toString() {
        return "BluetoothOTAConfigure{priority=" + this.priority + ", isUseReconnect=" + this.isUseReconnect + ", isUseAuthDevice=" + this.isUseAuthDevice + ", bleIntervalMs=" + this.bleIntervalMs + ", timeoutMs=" + this.timeoutMs + ", isUseJLServer=" + this.isUseJLServer + ", firmwareFilePath='" + this.firmwareFilePath + "', scanFilterData='" + this.scanFilterData + "', mtu=" + this.mtu + ", bleScanMode=" + this.bleScanMode + ", firmwareFileData=" + CHexConver.byte2HexStr(this.firmwareFileData) + '}';
    }
}
