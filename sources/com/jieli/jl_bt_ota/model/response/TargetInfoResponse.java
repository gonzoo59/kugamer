package com.jieli.jl_bt_ota.model.response;

import com.jieli.jl_bt_ota.model.base.CommonResponse;
/* loaded from: classes2.dex */
public class TargetInfoResponse extends CommonResponse {
    private int allowConnectFlag;
    private String authKey;
    private String bleAddr;
    private boolean bleOnly;
    private byte curFunction;
    private String customVersionMsg;
    private String edrAddr;
    private int emitterStatus;
    private boolean emitterSupport;
    private int functionMask;
    private boolean isGameMode;
    private boolean isNeedBootLoader;
    private boolean isSupportDoubleBackup;
    private boolean isSupportMD5;
    private String license;
    private int mandatoryUpgradeFlag;
    private int maxVol;
    private String name;
    private int pid;
    private String projectCode;
    private String protocolVersion;
    private int quantity;
    private int requestOtaFlag;
    private int sdkType;
    private int singleBackupOtaWay;
    private int ubootVersionCode;
    private String ubootVersionName;
    private int versionCode;
    private String versionName;
    private int vid;
    private int volume;
    private int edrStatus = 0;
    private int edrProfile = 0;
    private int platform = -1;

    public int getEmitterStatus() {
        return this.emitterStatus;
    }

    public TargetInfoResponse setEmitterStatus(int i) {
        this.emitterStatus = i;
        return this;
    }

    public boolean isEmitterSupport() {
        return this.emitterSupport;
    }

    public TargetInfoResponse setEmitterSupport(boolean z) {
        this.emitterSupport = z;
        return this;
    }

    public boolean isBleOnly() {
        return this.bleOnly;
    }

    public TargetInfoResponse setBleOnly(boolean z) {
        this.bleOnly = z;
        return this;
    }

    public TargetInfoResponse setFunctionMask(int i) {
        this.functionMask = i;
        return this;
    }

    public int getFunctionMask() {
        return this.functionMask;
    }

    public TargetInfoResponse setMaxVol(int i) {
        this.maxVol = i;
        return this;
    }

    public int getMaxVol() {
        return this.maxVol;
    }

    public TargetInfoResponse setVolume(int i) {
        this.volume = i;
        return this;
    }

    public int getVolume() {
        return this.volume;
    }

    public TargetInfoResponse setEdrProfile(int i) {
        this.edrProfile = i;
        return this;
    }

    public int getEdrProfile() {
        return this.edrProfile;
    }

    public TargetInfoResponse setEdrStatus(int i) {
        this.edrStatus = i;
        return this;
    }

    public int getEdrStatus() {
        return this.edrStatus;
    }

    public TargetInfoResponse setPlatform(int i) {
        this.platform = i;
        return this;
    }

    public int getPlatform() {
        return this.platform;
    }

    public TargetInfoResponse setLicense(String str) {
        this.license = str;
        return this;
    }

    public String getLicense() {
        return this.license;
    }

    public TargetInfoResponse setEdrAddr(String str) {
        this.edrAddr = str;
        return this;
    }

    public String getEdrAddr() {
        return this.edrAddr;
    }

    public String getName() {
        return this.name;
    }

    public TargetInfoResponse setName(String str) {
        this.name = str;
        return this;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public TargetInfoResponse setVersionCode(int i) {
        this.versionCode = i;
        return this;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public TargetInfoResponse setVersionName(String str) {
        this.versionName = str;
        return this;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public TargetInfoResponse setQuantity(int i) {
        this.quantity = i;
        return this;
    }

    public int getPid() {
        return this.pid;
    }

    public TargetInfoResponse setPid(int i) {
        this.pid = i;
        return this;
    }

    public int getVid() {
        return this.vid;
    }

    public TargetInfoResponse setVid(int i) {
        this.vid = i;
        return this;
    }

    public int getMandatoryUpgradeFlag() {
        return this.mandatoryUpgradeFlag;
    }

    public TargetInfoResponse setMandatoryUpgradeFlag(int i) {
        this.mandatoryUpgradeFlag = i;
        return this;
    }

    public int getUbootVersionCode() {
        return this.ubootVersionCode;
    }

    public TargetInfoResponse setUbootVersionCode(int i) {
        this.ubootVersionCode = i;
        return this;
    }

    public String getUbootVersionName() {
        return this.ubootVersionName;
    }

    public TargetInfoResponse setUbootVersionName(String str) {
        this.ubootVersionName = str;
        return this;
    }

    public TargetInfoResponse setCurFunction(byte b) {
        this.curFunction = b;
        return this;
    }

    public byte getCurFunction() {
        return this.curFunction;
    }

    public String getProtocolVersion() {
        return this.protocolVersion;
    }

    public TargetInfoResponse setProtocolVersion(String str) {
        this.protocolVersion = str;
        return this;
    }

    public boolean isSupportDoubleBackup() {
        return this.isSupportDoubleBackup;
    }

    public TargetInfoResponse setSupportDoubleBackup(boolean z) {
        this.isSupportDoubleBackup = z;
        return this;
    }

    public boolean isNeedBootLoader() {
        return this.isNeedBootLoader;
    }

    public TargetInfoResponse setNeedBootLoader(boolean z) {
        this.isNeedBootLoader = z;
        return this;
    }

    public String getBleAddr() {
        return this.bleAddr;
    }

    public TargetInfoResponse setBleAddr(String str) {
        this.bleAddr = str;
        return this;
    }

    public int getSdkType() {
        return this.sdkType;
    }

    public TargetInfoResponse setSdkType(int i) {
        this.sdkType = i;
        return this;
    }

    public String getAuthKey() {
        return this.authKey;
    }

    public TargetInfoResponse setAuthKey(String str) {
        this.authKey = str;
        return this;
    }

    public String getProjectCode() {
        return this.projectCode;
    }

    public TargetInfoResponse setProjectCode(String str) {
        this.projectCode = str;
        return this;
    }

    public int getAllowConnectFlag() {
        return this.allowConnectFlag;
    }

    public TargetInfoResponse setAllowConnectFlag(int i) {
        this.allowConnectFlag = i;
        return this;
    }

    public String getCustomVersionMsg() {
        return this.customVersionMsg;
    }

    public TargetInfoResponse setCustomVersionMsg(String str) {
        this.customVersionMsg = str;
        return this;
    }

    public boolean isSupportMD5() {
        return this.isSupportMD5;
    }

    public TargetInfoResponse setSupportMD5(boolean z) {
        this.isSupportMD5 = z;
        return this;
    }

    public boolean isGameMode() {
        return this.isGameMode;
    }

    public TargetInfoResponse setGameMode(boolean z) {
        this.isGameMode = z;
        return this;
    }

    public int getSingleBackupOtaWay() {
        return this.singleBackupOtaWay;
    }

    public TargetInfoResponse setSingleBackupOtaWay(int i) {
        this.singleBackupOtaWay = i;
        return this;
    }

    public int getRequestOtaFlag() {
        return this.requestOtaFlag;
    }

    public void setRequestOtaFlag(int i) {
        this.requestOtaFlag = i;
    }

    @Override // com.jieli.jl_bt_ota.model.base.CommonResponse, com.jieli.jl_bt_ota.model.base.BaseResponse
    public String toString() {
        return "TargetInfoResponse{versionName='" + this.versionName + "', versionCode=" + this.versionCode + ", protocolVersion='" + this.protocolVersion + "', edrAddr='" + this.edrAddr + "', edrStatus=" + this.edrStatus + ", edrProfile=" + this.edrProfile + ", bleAddr='" + this.bleAddr + "', platform=" + this.platform + ", license='" + this.license + "', volume=" + this.volume + ", maxVol=" + this.maxVol + ", quantity=" + this.quantity + ", functionMask=" + this.functionMask + ", curFunction=" + ((int) this.curFunction) + ", sdkType=" + this.sdkType + ", name='" + this.name + "', pid=" + this.pid + ", vid=" + this.vid + ", mandatoryUpgradeFlag=" + this.mandatoryUpgradeFlag + ", requestOtaFlag=" + this.requestOtaFlag + ", ubootVersionCode=" + this.ubootVersionCode + ", ubootVersionName='" + this.ubootVersionName + "', isSupportDoubleBackup=" + this.isSupportDoubleBackup + ", isNeedBootLoader=" + this.isNeedBootLoader + ", singleBackupOtaWay=" + this.singleBackupOtaWay + ", allowConnectFlag=" + this.allowConnectFlag + ", authKey='" + this.authKey + "', projectCode='" + this.projectCode + "', customVersionMsg='" + this.customVersionMsg + "', bleOnly=" + this.bleOnly + ", emitterSupport=" + this.emitterSupport + ", emitterStatus=" + this.emitterStatus + ", isSupportMD5=" + this.isSupportMD5 + ", isGameMode=" + this.isGameMode + "} " + super.toString();
    }
}
