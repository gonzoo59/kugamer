package com.polidea.rxandroidble2.internal.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import bleshadow.javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;
import io.reactivex.schedulers.Schedulers;
/* loaded from: classes2.dex */
public class LocationServicesOkObservableApi23Factory {
    final Context context;
    final LocationServicesStatus locationServicesStatus;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public LocationServicesOkObservableApi23Factory(Context context, LocationServicesStatus locationServicesStatus) {
        this.context = context;
        this.locationServicesStatus = locationServicesStatus;
    }

    public Observable<Boolean> get() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() { // from class: com.polidea.rxandroidble2.internal.util.LocationServicesOkObservableApi23Factory.1
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<Boolean> observableEmitter) {
                boolean isLocationProviderOk = LocationServicesOkObservableApi23Factory.this.locationServicesStatus.isLocationProviderOk();
                final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.polidea.rxandroidble2.internal.util.LocationServicesOkObservableApi23Factory.1.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent) {
                        observableEmitter.onNext(Boolean.valueOf(LocationServicesOkObservableApi23Factory.this.locationServicesStatus.isLocationProviderOk()));
                    }
                };
                observableEmitter.onNext(Boolean.valueOf(isLocationProviderOk));
                LocationServicesOkObservableApi23Factory.this.context.registerReceiver(broadcastReceiver, new IntentFilter("android.location.MODE_CHANGED"));
                observableEmitter.setCancellable(new Cancellable() { // from class: com.polidea.rxandroidble2.internal.util.LocationServicesOkObservableApi23Factory.1.2
                    @Override // io.reactivex.functions.Cancellable
                    public void cancel() {
                        LocationServicesOkObservableApi23Factory.this.context.unregisterReceiver(broadcastReceiver);
                    }
                });
            }
        }).distinctUntilChanged().subscribeOn(Schedulers.trampoline()).unsubscribeOn(Schedulers.trampoline());
    }
}
