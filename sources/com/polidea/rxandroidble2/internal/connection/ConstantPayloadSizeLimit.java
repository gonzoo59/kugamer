package com.polidea.rxandroidble2.internal.connection;
/* loaded from: classes2.dex */
class ConstantPayloadSizeLimit implements PayloadSizeLimitProvider {
    private final int limit;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ConstantPayloadSizeLimit(int i) {
        this.limit = i;
    }

    @Override // com.polidea.rxandroidble2.internal.connection.PayloadSizeLimitProvider
    public int getPayloadSizeLimit() {
        return this.limit;
    }
}
