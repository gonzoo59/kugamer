package com.polidea.rxandroidble2.internal.connection;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.polidea.rxandroidble2.RxBleAdapterStateObservable;
import com.polidea.rxandroidble2.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble2.exceptions.BleException;
import com.polidea.rxandroidble2.exceptions.BleGattException;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
/* JADX INFO: Access modifiers changed from: package-private */
@ConnectionScope
/* loaded from: classes2.dex */
public class DisconnectionRouter implements DisconnectionRouterInput, DisconnectionRouterOutput {
    private final BehaviorRelay<BleException> bleExceptionBehaviorRelay;
    private final Observable<Object> firstDisconnectionExceptionObs;
    private final Observable<BleException> firstDisconnectionValueObs;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public DisconnectionRouter(@Named("mac-address") final String str, RxBleAdapterWrapper rxBleAdapterWrapper, Observable<RxBleAdapterStateObservable.BleAdapterState> observable) {
        BehaviorRelay<BleException> create = BehaviorRelay.create();
        this.bleExceptionBehaviorRelay = create;
        final Disposable subscribe = awaitAdapterNotUsable(rxBleAdapterWrapper, observable).map(new Function<Boolean, BleException>() { // from class: com.polidea.rxandroidble2.internal.connection.DisconnectionRouter.3
            @Override // io.reactivex.functions.Function
            public BleException apply(Boolean bool) {
                return BleDisconnectedException.adapterDisabled(str);
            }
        }).doOnNext(new Consumer<BleException>() { // from class: com.polidea.rxandroidble2.internal.connection.DisconnectionRouter.2
            @Override // io.reactivex.functions.Consumer
            public void accept(BleException bleException) {
                RxBleLog.v("An exception received, indicating that the adapter has became unusable.", new Object[0]);
            }
        }).subscribe(create, new Consumer<Throwable>() { // from class: com.polidea.rxandroidble2.internal.connection.DisconnectionRouter.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) {
                RxBleLog.e(th, "Failed to monitor adapter state.", new Object[0]);
            }
        });
        Observable<BleException> autoConnect = create.firstElement().toObservable().doOnTerminate(new Action() { // from class: com.polidea.rxandroidble2.internal.connection.DisconnectionRouter.4
            @Override // io.reactivex.functions.Action
            public void run() {
                subscribe.dispose();
            }
        }).replay().autoConnect(0);
        this.firstDisconnectionValueObs = autoConnect;
        this.firstDisconnectionExceptionObs = autoConnect.flatMap(new Function<BleException, ObservableSource<?>>() { // from class: com.polidea.rxandroidble2.internal.connection.DisconnectionRouter.5
            @Override // io.reactivex.functions.Function
            public ObservableSource<?> apply(BleException bleException) {
                return Observable.error(bleException);
            }
        });
    }

    private static Observable<Boolean> awaitAdapterNotUsable(RxBleAdapterWrapper rxBleAdapterWrapper, Observable<RxBleAdapterStateObservable.BleAdapterState> observable) {
        return observable.map(new Function<RxBleAdapterStateObservable.BleAdapterState, Boolean>() { // from class: com.polidea.rxandroidble2.internal.connection.DisconnectionRouter.7
            @Override // io.reactivex.functions.Function
            public Boolean apply(RxBleAdapterStateObservable.BleAdapterState bleAdapterState) {
                return Boolean.valueOf(bleAdapterState.isUsable());
            }
        }).startWith((Observable<R>) Boolean.valueOf(rxBleAdapterWrapper.isBluetoothEnabled())).filter(new Predicate<Boolean>() { // from class: com.polidea.rxandroidble2.internal.connection.DisconnectionRouter.6
            @Override // io.reactivex.functions.Predicate
            public boolean test(Boolean bool) {
                return !bool.booleanValue();
            }
        });
    }

    @Override // com.polidea.rxandroidble2.internal.connection.DisconnectionRouterInput
    public void onDisconnectedException(BleDisconnectedException bleDisconnectedException) {
        this.bleExceptionBehaviorRelay.accept(bleDisconnectedException);
    }

    @Override // com.polidea.rxandroidble2.internal.connection.DisconnectionRouterInput
    public void onGattConnectionStateException(BleGattException bleGattException) {
        this.bleExceptionBehaviorRelay.accept(bleGattException);
    }

    @Override // com.polidea.rxandroidble2.internal.connection.DisconnectionRouterOutput
    public Observable<BleException> asValueOnlyObservable() {
        return this.firstDisconnectionValueObs;
    }

    @Override // com.polidea.rxandroidble2.internal.connection.DisconnectionRouterOutput
    public <T> Observable<T> asErrorOnlyObservable() {
        return (Observable<T>) this.firstDisconnectionExceptionObs;
    }
}
