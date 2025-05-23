package com.polidea.rxandroidble2.internal.connection;

import com.polidea.rxandroidble2.ConnectionParameters;
/* loaded from: classes2.dex */
public class ConnectionParametersImpl implements ConnectionParameters {
    private final int interval;
    private final int latency;
    private final int timeout;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ConnectionParametersImpl(int i, int i2, int i3) {
        this.interval = i;
        this.latency = i2;
        this.timeout = i3;
    }

    @Override // com.polidea.rxandroidble2.ConnectionParameters
    public int getConnectionInterval() {
        return this.interval;
    }

    @Override // com.polidea.rxandroidble2.ConnectionParameters
    public int getSlaveLatency() {
        return this.latency;
    }

    @Override // com.polidea.rxandroidble2.ConnectionParameters
    public int getSupervisionTimeout() {
        return this.timeout;
    }
}
