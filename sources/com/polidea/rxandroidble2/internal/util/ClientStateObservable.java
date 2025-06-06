package com.polidea.rxandroidble2.internal.util;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.RxBleAdapterStateObservable;
import com.polidea.rxandroidble2.RxBleClient;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.concurrent.TimeUnit;
/* loaded from: classes2.dex */
public class ClientStateObservable extends Observable<RxBleClient.State> {
    final Observable<RxBleAdapterStateObservable.BleAdapterState> bleAdapterStateObservable;
    final Observable<Boolean> locationServicesOkObservable;
    private final LocationServicesStatus locationServicesStatus;
    final RxBleAdapterWrapper rxBleAdapterWrapper;
    private final Scheduler timerScheduler;

    /* JADX INFO: Access modifiers changed from: protected */
    @Inject
    public ClientStateObservable(RxBleAdapterWrapper rxBleAdapterWrapper, Observable<RxBleAdapterStateObservable.BleAdapterState> observable, @Named("location-ok-boolean-observable") Observable<Boolean> observable2, LocationServicesStatus locationServicesStatus, @Named("timeout") Scheduler scheduler) {
        this.rxBleAdapterWrapper = rxBleAdapterWrapper;
        this.bleAdapterStateObservable = observable;
        this.locationServicesOkObservable = observable2;
        this.locationServicesStatus = locationServicesStatus;
        this.timerScheduler = scheduler;
    }

    private static Single<Boolean> checkPermissionUntilGranted(final LocationServicesStatus locationServicesStatus, Scheduler scheduler) {
        return Observable.interval(0L, 1L, TimeUnit.SECONDS, scheduler).takeWhile(new Predicate<Long>() { // from class: com.polidea.rxandroidble2.internal.util.ClientStateObservable.2
            @Override // io.reactivex.functions.Predicate
            public boolean test(Long l) {
                return !LocationServicesStatus.this.isLocationPermissionOk();
            }
        }).count().map(new Function<Long, Boolean>() { // from class: com.polidea.rxandroidble2.internal.util.ClientStateObservable.1
            @Override // io.reactivex.functions.Function
            public Boolean apply(Long l) {
                return Boolean.valueOf(l.longValue() == 0);
            }
        });
    }

    static Observable<RxBleClient.State> checkAdapterAndServicesState(RxBleAdapterWrapper rxBleAdapterWrapper, Observable<RxBleAdapterStateObservable.BleAdapterState> observable, final Observable<Boolean> observable2) {
        return observable.startWith((Observable<RxBleAdapterStateObservable.BleAdapterState>) (rxBleAdapterWrapper.isBluetoothEnabled() ? RxBleAdapterStateObservable.BleAdapterState.STATE_ON : RxBleAdapterStateObservable.BleAdapterState.STATE_OFF)).switchMap(new Function<RxBleAdapterStateObservable.BleAdapterState, Observable<RxBleClient.State>>() { // from class: com.polidea.rxandroidble2.internal.util.ClientStateObservable.3
            @Override // io.reactivex.functions.Function
            public Observable<RxBleClient.State> apply(RxBleAdapterStateObservable.BleAdapterState bleAdapterState) {
                if (bleAdapterState != RxBleAdapterStateObservable.BleAdapterState.STATE_ON) {
                    return Observable.just(RxBleClient.State.BLUETOOTH_NOT_ENABLED);
                }
                return Observable.this.map(new Function<Boolean, RxBleClient.State>() { // from class: com.polidea.rxandroidble2.internal.util.ClientStateObservable.3.1
                    @Override // io.reactivex.functions.Function
                    public RxBleClient.State apply(Boolean bool) {
                        return bool.booleanValue() ? RxBleClient.State.READY : RxBleClient.State.LOCATION_SERVICES_NOT_ENABLED;
                    }
                });
            }
        });
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super RxBleClient.State> observer) {
        if (!this.rxBleAdapterWrapper.hasBluetoothAdapter()) {
            observer.onSubscribe(Disposables.empty());
            observer.onComplete();
            return;
        }
        checkPermissionUntilGranted(this.locationServicesStatus, this.timerScheduler).flatMapObservable(new Function<Boolean, Observable<RxBleClient.State>>() { // from class: com.polidea.rxandroidble2.internal.util.ClientStateObservable.4
            @Override // io.reactivex.functions.Function
            public Observable<RxBleClient.State> apply(Boolean bool) {
                Observable<RxBleClient.State> distinctUntilChanged = ClientStateObservable.checkAdapterAndServicesState(ClientStateObservable.this.rxBleAdapterWrapper, ClientStateObservable.this.bleAdapterStateObservable, ClientStateObservable.this.locationServicesOkObservable).distinctUntilChanged();
                return bool.booleanValue() ? distinctUntilChanged.skip(1L) : distinctUntilChanged;
            }
        }).subscribe(observer);
    }
}
