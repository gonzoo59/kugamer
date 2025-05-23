package com.baidu.kwgames.example4_characteristic.advanced;

import android.bluetooth.BluetoothGattCharacteristic;
import androidx.core.util.Pair;
import com.polidea.rxandroidble2.NotificationSetupMode;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.RxBleDeviceServices;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.Arrays;
import java.util.UUID;
/* loaded from: classes.dex */
final class Presenter {
    private static final UUID clientCharacteristicConfigDescriptorUuid = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ ObservableSource lambda$repeatAfterCompleted$20(Observable observable) throws Exception {
        return observable;
    }

    private Presenter() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Observable<PresenterEvent> prepareActivityLogic(final RxBleDevice rxBleDevice, final UUID uuid, Observable<Boolean> observable, final Observable<Boolean> observable2, final Observable<Boolean> observable3, final Observable<Boolean> observable4, final Observable<byte[]> observable5, final Observable<Boolean> observable6, final Observable<Boolean> observable7, final Observable<Boolean> observable8, final Observable<Boolean> observable9, final Observable<Boolean> observable10, final Observable<Boolean> observable11) {
        return observable.take(1L).flatMap(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource onErrorReturn;
                Boolean bool = (Boolean) obj;
                onErrorReturn = RxBleDevice.this.establishConnection(false).flatMapSingle(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda7
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj2) {
                        SingleSource pairWithCharacteristic;
                        pairWithCharacteristic = Presenter.pairWithCharacteristic(r1, (RxBleConnection) obj2);
                        return pairWithCharacteristic;
                    }
                }).flatMap(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda6
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj2) {
                        return Presenter.lambda$prepareActivityLogic$1(Observable.this, r2, r3, r4, r5, r6, r7, r8, (Pair) obj2);
                    }
                }).compose(Presenter.takeUntil(observable2, observable3)).onErrorReturn(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda13
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj2) {
                        return Presenter.lambda$prepareActivityLogic$2((Throwable) obj2);
                    }
                });
                return onErrorReturn;
            }
        }).compose(repeatAfterCompleted());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ ObservableSource lambda$prepareActivityLogic$1(Observable observable, Observable observable2, Observable observable3, Observable observable4, Observable observable5, Observable observable6, Observable observable7, Observable observable8, Pair pair) throws Exception {
        BluetoothGattCharacteristic bluetoothGattCharacteristic = (BluetoothGattCharacteristic) pair.second;
        RxBleConnection rxBleConnection = (RxBleConnection) pair.first;
        return Observable.merge(setupReadingBehaviour(observable, bluetoothGattCharacteristic, rxBleConnection), setupWritingBehaviour(observable2, bluetoothGattCharacteristic, rxBleConnection), setupNotificationAndIndicationBehaviour(rxBleConnection, bluetoothGattCharacteristic, observable3, observable4, observable5, observable6, observable7, observable8)).startWith((Observable) new InfoEvent("Hey, connection has been established!"));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ PresenterEvent lambda$prepareActivityLogic$2(Throwable th) throws Exception {
        return new InfoEvent("Connection error: " + th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Single<Pair<RxBleConnection, BluetoothGattCharacteristic>> pairWithCharacteristic(UUID uuid, final RxBleConnection rxBleConnection) {
        return getCharacteristic(uuid, rxBleConnection).map(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda21
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                Pair create;
                create = Pair.create(RxBleConnection.this, (BluetoothGattCharacteristic) obj);
                return create;
            }
        });
    }

    private static Observable<PresenterEvent> setupReadingBehaviour(Observable<Boolean> observable, final BluetoothGattCharacteristic bluetoothGattCharacteristic, final RxBleConnection rxBleConnection) {
        if (!hasProperty(bluetoothGattCharacteristic, 2)) {
            return Observable.empty();
        }
        return observable.flatMapSingle(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                SingleSource readCharacteristic;
                Boolean bool = (Boolean) obj;
                readCharacteristic = RxBleConnection.this.readCharacteristic(bluetoothGattCharacteristic);
                return readCharacteristic;
            }
        }).compose(transformToPresenterEvent(Type.READ));
    }

    private static Observable<PresenterEvent> setupWritingBehaviour(Observable<byte[]> observable, final BluetoothGattCharacteristic bluetoothGattCharacteristic, final RxBleConnection rxBleConnection) {
        if (!hasProperty(bluetoothGattCharacteristic, 8)) {
            return Observable.empty();
        }
        return observable.flatMapSingle(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                SingleSource writeCharacteristic;
                writeCharacteristic = RxBleConnection.this.writeCharacteristic(bluetoothGattCharacteristic, (byte[]) obj);
                return writeCharacteristic;
            }
        }).compose(transformToPresenterEvent(Type.WRITE));
    }

    private static Observable<PresenterEvent> setupNotificationAndIndicationBehaviour(final RxBleConnection rxBleConnection, final BluetoothGattCharacteristic bluetoothGattCharacteristic, Observable<Boolean> observable, final Observable<Boolean> observable2, final Observable<Boolean> observable3, Observable<Boolean> observable4, final Observable<Boolean> observable5, final Observable<Boolean> observable6) {
        NotificationSetupMode notificationSetupMode;
        Observable map;
        Observable map2;
        if (bluetoothGattCharacteristic.getDescriptor(clientCharacteristicConfigDescriptorUuid) == null) {
            notificationSetupMode = NotificationSetupMode.COMPAT;
        } else {
            notificationSetupMode = NotificationSetupMode.DEFAULT;
        }
        final NotificationSetupMode notificationSetupMode2 = notificationSetupMode;
        if (!hasProperty(bluetoothGattCharacteristic, 16)) {
            map = Observable.never();
        } else {
            map = observable.take(1L).map(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda10
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    Boolean bool;
                    Boolean bool2 = (Boolean) obj;
                    bool = Boolean.FALSE;
                    return bool;
                }
            });
        }
        if (!hasProperty(bluetoothGattCharacteristic, 32)) {
            map2 = Observable.never();
        } else {
            map2 = observable4.take(1L).map(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda12
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    Boolean bool;
                    Boolean bool2 = (Boolean) obj;
                    bool = Boolean.TRUE;
                    return bool;
                }
            });
        }
        boolean z = false;
        Observable compose = Observable.amb(Arrays.asList(map, map2)).flatMap(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return Presenter.lambda$setupNotificationAndIndicationBehaviour$9(RxBleConnection.this, bluetoothGattCharacteristic, notificationSetupMode2, observable5, observable6, observable2, observable3, (Boolean) obj);
            }
        }).compose(repeatAfterCompleted());
        if (hasProperty(bluetoothGattCharacteristic, 48) && notificationSetupMode2 == NotificationSetupMode.COMPAT) {
            z = true;
        }
        return compose.startWith((Observable) new CompatibilityModeEvent(z));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ ObservableSource lambda$setupNotificationAndIndicationBehaviour$9(RxBleConnection rxBleConnection, BluetoothGattCharacteristic bluetoothGattCharacteristic, NotificationSetupMode notificationSetupMode, Observable observable, Observable observable2, Observable observable3, Observable observable4, Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            return rxBleConnection.setupIndication(bluetoothGattCharacteristic, notificationSetupMode).compose(takeUntil(observable, observable2)).compose(transformToNotificationPresenterEvent(Type.INDICATE));
        }
        return rxBleConnection.setupNotification(bluetoothGattCharacteristic, notificationSetupMode).compose(takeUntil(observable3, observable4)).compose(transformToNotificationPresenterEvent(Type.NOTIFY));
    }

    private static Single<BluetoothGattCharacteristic> getCharacteristic(final UUID uuid, RxBleConnection rxBleConnection) {
        return rxBleConnection.discoverServices().flatMap(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                SingleSource characteristic;
                characteristic = ((RxBleDeviceServices) obj).getCharacteristic(uuid);
                return characteristic;
            }
        });
    }

    private static boolean hasProperty(BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        return (bluetoothGattCharacteristic.getProperties() & i) > 0;
    }

    private static <T> ObservableTransformer<T, T> takeUntil(final Observable<?> observable, final Observable<?> observable2) {
        return new ObservableTransformer() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda14
            @Override // io.reactivex.ObservableTransformer
            public final ObservableSource apply(Observable observable3) {
                ObservableSource publish;
                publish = observable3.publish(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda5
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj) {
                        ObservableSource takeUntil;
                        takeUntil = Observable.amb(Arrays.asList(r3, r3.takeUntil(r2))).takeUntil(((Observable) obj).take(1L).ignoreElements().andThen(Observable.this));
                        return takeUntil;
                    }
                });
                return publish;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ PresenterEvent lambda$transformToPresenterEvent$13(Type type, byte[] bArr) throws Exception {
        return new ResultEvent(bArr, type);
    }

    private static ObservableTransformer<byte[], PresenterEvent> transformToPresenterEvent(final Type type) {
        return new ObservableTransformer() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda11
            @Override // io.reactivex.ObservableTransformer
            public final ObservableSource apply(Observable observable) {
                ObservableSource onErrorReturn;
                onErrorReturn = observable.map(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda20
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj) {
                        return Presenter.lambda$transformToPresenterEvent$13(Type.this, (byte[]) obj);
                    }
                }).onErrorReturn(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda18
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj) {
                        return Presenter.lambda$transformToPresenterEvent$14(Type.this, (Throwable) obj);
                    }
                });
                return onErrorReturn;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ PresenterEvent lambda$transformToPresenterEvent$14(Type type, Throwable th) throws Exception {
        return new ErrorEvent(th, type);
    }

    private static ObservableTransformer<Observable<byte[]>, PresenterEvent> transformToNotificationPresenterEvent(final Type type) {
        return new ObservableTransformer() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda0
            @Override // io.reactivex.ObservableTransformer
            public final ObservableSource apply(Observable observable) {
                ObservableSource onErrorReturn;
                onErrorReturn = observable.flatMap(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda16
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj) {
                        ObservableSource map;
                        map = ((Observable) obj).map(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda19
                            @Override // io.reactivex.functions.Function
                            public final Object apply(Object obj2) {
                                return Presenter.lambda$transformToNotificationPresenterEvent$16(Type.this, (byte[]) obj2);
                            }
                        });
                        return map;
                    }
                }).onErrorReturn(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda17
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj) {
                        return Presenter.lambda$transformToNotificationPresenterEvent$18(Type.this, (Throwable) obj);
                    }
                });
                return onErrorReturn;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ PresenterEvent lambda$transformToNotificationPresenterEvent$16(Type type, byte[] bArr) throws Exception {
        return new ResultEvent(bArr, type);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ PresenterEvent lambda$transformToNotificationPresenterEvent$18(Type type, Throwable th) throws Exception {
        return new ErrorEvent(th, type);
    }

    private static <T> ObservableTransformer<T, T> repeatAfterCompleted() {
        return new ObservableTransformer() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda15
            @Override // io.reactivex.ObservableTransformer
            public final ObservableSource apply(Observable observable) {
                ObservableSource repeatWhen;
                repeatWhen = observable.repeatWhen(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.Presenter$$ExternalSyntheticLambda9
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj) {
                        return Presenter.lambda$repeatAfterCompleted$20((Observable) obj);
                    }
                });
                return repeatWhen;
            }
        };
    }
}
