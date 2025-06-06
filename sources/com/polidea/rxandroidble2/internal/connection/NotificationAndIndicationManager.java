package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.NotificationSetupMode;
import com.polidea.rxandroidble2.exceptions.BleCannotSetCharacteristicNotificationException;
import com.polidea.rxandroidble2.exceptions.BleConflictingNotificationAlreadySetException;
import com.polidea.rxandroidble2.internal.util.ActiveCharacteristicNotification;
import com.polidea.rxandroidble2.internal.util.CharacteristicChangedEvent;
import com.polidea.rxandroidble2.internal.util.CharacteristicNotificationId;
import com.polidea.rxandroidble2.internal.util.ObservableUtil;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.subjects.PublishSubject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
/* JADX INFO: Access modifiers changed from: package-private */
@ConnectionScope
/* loaded from: classes2.dex */
public class NotificationAndIndicationManager {
    static final UUID CLIENT_CHARACTERISTIC_CONFIG_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    final Map<CharacteristicNotificationId, ActiveCharacteristicNotification> activeNotificationObservableMap = new HashMap();
    final BluetoothGatt bluetoothGatt;
    final byte[] configDisable;
    final byte[] configEnableIndication;
    final byte[] configEnableNotification;
    final DescriptorWriter descriptorWriter;
    final RxBleGattCallback gattCallback;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public NotificationAndIndicationManager(@Named("enable-notification-value") byte[] bArr, @Named("enable-indication-value") byte[] bArr2, @Named("disable-notification-value") byte[] bArr3, BluetoothGatt bluetoothGatt, RxBleGattCallback rxBleGattCallback, DescriptorWriter descriptorWriter) {
        this.configEnableNotification = bArr;
        this.configEnableIndication = bArr2;
        this.configDisable = bArr3;
        this.bluetoothGatt = bluetoothGatt;
        this.gattCallback = rxBleGattCallback;
        this.descriptorWriter = descriptorWriter;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Observable<Observable<byte[]>> setupServerInitiatedCharacteristicRead(final BluetoothGattCharacteristic bluetoothGattCharacteristic, final NotificationSetupMode notificationSetupMode, final boolean z) {
        return Observable.defer(new Callable<ObservableSource<Observable<byte[]>>>() { // from class: com.polidea.rxandroidble2.internal.connection.NotificationAndIndicationManager.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public ObservableSource<Observable<byte[]>> call() {
                synchronized (NotificationAndIndicationManager.this.activeNotificationObservableMap) {
                    final CharacteristicNotificationId characteristicNotificationId = new CharacteristicNotificationId(bluetoothGattCharacteristic.getUuid(), Integer.valueOf(bluetoothGattCharacteristic.getInstanceId()));
                    ActiveCharacteristicNotification activeCharacteristicNotification = NotificationAndIndicationManager.this.activeNotificationObservableMap.get(characteristicNotificationId);
                    boolean z2 = true;
                    if (activeCharacteristicNotification != null) {
                        if (activeCharacteristicNotification.isIndication == z) {
                            return activeCharacteristicNotification.notificationObservable;
                        }
                        UUID uuid = bluetoothGattCharacteristic.getUuid();
                        if (z) {
                            z2 = false;
                        }
                        return Observable.error(new BleConflictingNotificationAlreadySetException(uuid, z2));
                    }
                    byte[] bArr = z ? NotificationAndIndicationManager.this.configEnableIndication : NotificationAndIndicationManager.this.configEnableNotification;
                    final PublishSubject create = PublishSubject.create();
                    Observable refCount = NotificationAndIndicationManager.setCharacteristicNotification(NotificationAndIndicationManager.this.bluetoothGatt, bluetoothGattCharacteristic, true).andThen(ObservableUtil.justOnNext(NotificationAndIndicationManager.observeOnCharacteristicChangeCallbacks(NotificationAndIndicationManager.this.gattCallback, characteristicNotificationId))).compose(NotificationAndIndicationManager.setupModeTransformer(NotificationAndIndicationManager.this.descriptorWriter, bluetoothGattCharacteristic, bArr, notificationSetupMode)).map(new Function<Observable<byte[]>, Observable<byte[]>>() { // from class: com.polidea.rxandroidble2.internal.connection.NotificationAndIndicationManager.1.2
                        @Override // io.reactivex.functions.Function
                        public Observable<byte[]> apply(Observable<byte[]> observable) {
                            return Observable.amb(Arrays.asList(create.cast(byte[].class), observable.takeUntil(create)));
                        }
                    }).doFinally(new Action() { // from class: com.polidea.rxandroidble2.internal.connection.NotificationAndIndicationManager.1.1
                        @Override // io.reactivex.functions.Action
                        public void run() {
                            create.onComplete();
                            synchronized (NotificationAndIndicationManager.this.activeNotificationObservableMap) {
                                NotificationAndIndicationManager.this.activeNotificationObservableMap.remove(characteristicNotificationId);
                            }
                            NotificationAndIndicationManager.setCharacteristicNotification(NotificationAndIndicationManager.this.bluetoothGatt, bluetoothGattCharacteristic, false).compose(NotificationAndIndicationManager.teardownModeTransformer(NotificationAndIndicationManager.this.descriptorWriter, bluetoothGattCharacteristic, NotificationAndIndicationManager.this.configDisable, notificationSetupMode)).subscribe(Functions.EMPTY_ACTION, Functions.emptyConsumer());
                        }
                    }).mergeWith(NotificationAndIndicationManager.this.gattCallback.observeDisconnect()).replay(1).refCount();
                    NotificationAndIndicationManager.this.activeNotificationObservableMap.put(characteristicNotificationId, new ActiveCharacteristicNotification(refCount, z));
                    return refCount;
                }
            }
        });
    }

    static Completable setCharacteristicNotification(final BluetoothGatt bluetoothGatt, final BluetoothGattCharacteristic bluetoothGattCharacteristic, final boolean z) {
        return Completable.fromAction(new Action() { // from class: com.polidea.rxandroidble2.internal.connection.NotificationAndIndicationManager.2
            @Override // io.reactivex.functions.Action
            public void run() {
                if (!bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, z)) {
                    throw new BleCannotSetCharacteristicNotificationException(bluetoothGattCharacteristic, 1, null);
                }
            }
        });
    }

    static ObservableTransformer<Observable<byte[]>, Observable<byte[]>> setupModeTransformer(final DescriptorWriter descriptorWriter, final BluetoothGattCharacteristic bluetoothGattCharacteristic, final byte[] bArr, final NotificationSetupMode notificationSetupMode) {
        return new ObservableTransformer<Observable<byte[]>, Observable<byte[]>>() { // from class: com.polidea.rxandroidble2.internal.connection.NotificationAndIndicationManager.3
            @Override // io.reactivex.ObservableTransformer
            public ObservableSource<Observable<byte[]>> apply(Observable<Observable<byte[]>> observable) {
                int i = AnonymousClass8.$SwitchMap$com$polidea$rxandroidble2$NotificationSetupMode[NotificationSetupMode.this.ordinal()];
                if (i != 1) {
                    if (i == 2) {
                        final Completable ignoreElements = NotificationAndIndicationManager.writeClientCharacteristicConfig(bluetoothGattCharacteristic, descriptorWriter, bArr).toObservable().publish().autoConnect(2).ignoreElements();
                        return observable.mergeWith(ignoreElements).map(new Function<Observable<byte[]>, Observable<byte[]>>() { // from class: com.polidea.rxandroidble2.internal.connection.NotificationAndIndicationManager.3.1
                            @Override // io.reactivex.functions.Function
                            public Observable<byte[]> apply(Observable<byte[]> observable2) {
                                return observable2.mergeWith(ignoreElements.onErrorComplete());
                            }
                        });
                    }
                    return NotificationAndIndicationManager.writeClientCharacteristicConfig(bluetoothGattCharacteristic, descriptorWriter, bArr).andThen(observable);
                }
                return observable;
            }
        };
    }

    /* renamed from: com.polidea.rxandroidble2.internal.connection.NotificationAndIndicationManager$8  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass8 {
        static final /* synthetic */ int[] $SwitchMap$com$polidea$rxandroidble2$NotificationSetupMode;

        static {
            int[] iArr = new int[NotificationSetupMode.values().length];
            $SwitchMap$com$polidea$rxandroidble2$NotificationSetupMode = iArr;
            try {
                iArr[NotificationSetupMode.COMPAT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$polidea$rxandroidble2$NotificationSetupMode[NotificationSetupMode.QUICK_SETUP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$polidea$rxandroidble2$NotificationSetupMode[NotificationSetupMode.DEFAULT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    static CompletableTransformer teardownModeTransformer(final DescriptorWriter descriptorWriter, final BluetoothGattCharacteristic bluetoothGattCharacteristic, final byte[] bArr, final NotificationSetupMode notificationSetupMode) {
        return new CompletableTransformer() { // from class: com.polidea.rxandroidble2.internal.connection.NotificationAndIndicationManager.4
            @Override // io.reactivex.CompletableTransformer
            public Completable apply(Completable completable) {
                return NotificationSetupMode.this == NotificationSetupMode.COMPAT ? completable : completable.andThen(NotificationAndIndicationManager.writeClientCharacteristicConfig(bluetoothGattCharacteristic, descriptorWriter, bArr));
            }
        };
    }

    static Observable<byte[]> observeOnCharacteristicChangeCallbacks(RxBleGattCallback rxBleGattCallback, final CharacteristicNotificationId characteristicNotificationId) {
        return rxBleGattCallback.getOnCharacteristicChanged().filter(new Predicate<CharacteristicChangedEvent>() { // from class: com.polidea.rxandroidble2.internal.connection.NotificationAndIndicationManager.6
            @Override // io.reactivex.functions.Predicate
            public boolean test(CharacteristicChangedEvent characteristicChangedEvent) {
                return characteristicChangedEvent.equals(CharacteristicNotificationId.this);
            }
        }).map(new Function<CharacteristicChangedEvent, byte[]>() { // from class: com.polidea.rxandroidble2.internal.connection.NotificationAndIndicationManager.5
            @Override // io.reactivex.functions.Function
            public byte[] apply(CharacteristicChangedEvent characteristicChangedEvent) {
                return characteristicChangedEvent.data;
            }
        });
    }

    static Completable writeClientCharacteristicConfig(final BluetoothGattCharacteristic bluetoothGattCharacteristic, DescriptorWriter descriptorWriter, byte[] bArr) {
        BluetoothGattDescriptor descriptor = bluetoothGattCharacteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_UUID);
        if (descriptor == null) {
            return Completable.error(new BleCannotSetCharacteristicNotificationException(bluetoothGattCharacteristic, 2, null));
        }
        return descriptorWriter.writeDescriptor(descriptor, bArr).onErrorResumeNext(new Function<Throwable, CompletableSource>() { // from class: com.polidea.rxandroidble2.internal.connection.NotificationAndIndicationManager.7
            @Override // io.reactivex.functions.Function
            public CompletableSource apply(Throwable th) {
                return Completable.error(new BleCannotSetCharacteristicNotificationException(bluetoothGattCharacteristic, 3, th));
            }
        });
    }
}
