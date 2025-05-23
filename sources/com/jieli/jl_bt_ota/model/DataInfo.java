package com.jieli.jl_bt_ota.model;

import com.jieli.jl_bt_ota.interfaces.CommandCallback;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import java.util.Arrays;
/* loaded from: classes2.dex */
public class DataInfo {
    public static final int DATA_TYPE_RECEIVE = 1;
    public static final int DATA_TYPE_SEND = 0;
    private BasePacket basePacket;
    private CommandCallback callback;
    private boolean isSend;
    private int reSendCount;
    private byte[] recvData;
    private long sendTime;
    private int timeoutMs;
    private int type;

    public int getType() {
        return this.type;
    }

    public DataInfo setType(int i) {
        this.type = i;
        return this;
    }

    public byte[] getRecvData() {
        return this.recvData;
    }

    public DataInfo setRecvData(byte[] bArr) {
        this.recvData = bArr;
        return this;
    }

    public BasePacket getBasePacket() {
        return this.basePacket;
    }

    public DataInfo setBasePacket(BasePacket basePacket) {
        this.basePacket = basePacket;
        return this;
    }

    public int getTimeoutMs() {
        return this.timeoutMs;
    }

    public DataInfo setTimeoutMs(int i) {
        this.timeoutMs = i;
        return this;
    }

    public CommandCallback getCallback() {
        return this.callback;
    }

    public DataInfo setCallback(CommandCallback commandCallback) {
        this.callback = commandCallback;
        return this;
    }

    public int getReSendCount() {
        return this.reSendCount;
    }

    public DataInfo setReSendCount(int i) {
        this.reSendCount = i;
        return this;
    }

    public boolean isSend() {
        return this.isSend;
    }

    public DataInfo setSend(boolean z) {
        this.isSend = z;
        return this;
    }

    public long getSendTime() {
        return this.sendTime;
    }

    public DataInfo setSendTime(long j) {
        this.sendTime = j;
        return this;
    }

    public String toString() {
        return "DataInfo{type=" + this.type + ", recvData=" + Arrays.toString(this.recvData) + ", basePacket=" + this.basePacket + ", timeoutMs=" + this.timeoutMs + ", callback=" + this.callback + ", reSendCount=" + this.reSendCount + ", isSend=" + this.isSend + ", sendTime=" + this.sendTime + '}';
    }
}
