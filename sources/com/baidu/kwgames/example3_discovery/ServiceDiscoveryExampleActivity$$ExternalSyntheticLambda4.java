package com.baidu.kwgames.example3_discovery;

import com.polidea.rxandroidble2.RxBleConnection;
import io.reactivex.functions.Function;
/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class ServiceDiscoveryExampleActivity$$ExternalSyntheticLambda4 implements Function {
    public static final /* synthetic */ ServiceDiscoveryExampleActivity$$ExternalSyntheticLambda4 INSTANCE = new ServiceDiscoveryExampleActivity$$ExternalSyntheticLambda4();

    private /* synthetic */ ServiceDiscoveryExampleActivity$$ExternalSyntheticLambda4() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        return ((RxBleConnection) obj).discoverServices();
    }
}
