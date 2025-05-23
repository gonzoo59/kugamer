package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import com.polidea.rxandroidble2.ConnectionParameters;
import com.polidea.rxandroidble2.HiddenBluetoothGattCallback;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDeviceServices;
import com.polidea.rxandroidble2.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble2.exceptions.BleGattCharacteristicException;
import com.polidea.rxandroidble2.exceptions.BleGattDescriptorException;
import com.polidea.rxandroidble2.exceptions.BleGattException;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.util.ByteAssociation;
import com.polidea.rxandroidble2.internal.util.CharacteristicChangedEvent;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
@ConnectionScope
/* loaded from: classes2.dex */
public class RxBleGattCallback {
    final BluetoothGattProvider bluetoothGattProvider;
    private final Scheduler callbackScheduler;
    final DisconnectionRouter disconnectionRouter;
    final NativeCallbackDispatcher nativeCallbackDispatcher;
    final PublishRelay<RxBleConnection.RxBleConnectionState> connectionStatePublishRelay = PublishRelay.create();
    final Output<RxBleDeviceServices> servicesDiscoveredOutput = new Output<>();
    final Output<ByteAssociation<UUID>> readCharacteristicOutput = new Output<>();
    final Output<ByteAssociation<UUID>> writeCharacteristicOutput = new Output<>();
    final Relay<CharacteristicChangedEvent> changedCharacteristicSerializedPublishRelay = PublishRelay.create().toSerialized();
    final Output<ByteAssociation<BluetoothGattDescriptor>> readDescriptorOutput = new Output<>();
    final Output<ByteAssociation<BluetoothGattDescriptor>> writeDescriptorOutput = new Output<>();
    final Output<Integer> readRssiOutput = new Output<>();
    final Output<Integer> changedMtuOutput = new Output<>();
    final Output<ConnectionParameters> updatedConnectionOutput = new Output<>();
    private final Function<BleGattException, Observable<?>> errorMapper = new Function<BleGattException, Observable<?>>() { // from class: com.polidea.rxandroidble2.internal.connection.RxBleGattCallback.1
        @Override // io.reactivex.functions.Function
        public Observable<?> apply(BleGattException bleGattException) {
            return Observable.error(bleGattException);
        }
    };
    private final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() { // from class: com.polidea.rxandroidble2.internal.connection.RxBleGattCallback.2
        private boolean isDisconnectedOrDisconnecting(int i) {
            return i == 0 || i == 3;
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            LoggerUtil.logCallback("onConnectionStateChange", bluetoothGatt, i, i2);
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeConnectionStateCallback(bluetoothGatt, i, i2);
            super.onConnectionStateChange(bluetoothGatt, i, i2);
            RxBleGattCallback.this.bluetoothGattProvider.updateBluetoothGatt(bluetoothGatt);
            if (isDisconnectedOrDisconnecting(i2)) {
                RxBleGattCallback.this.disconnectionRouter.onDisconnectedException(new BleDisconnectedException(bluetoothGatt.getDevice().getAddress(), i));
            } else if (i != 0) {
                RxBleGattCallback.this.disconnectionRouter.onGattConnectionStateException(new BleGattException(bluetoothGatt, i, BleGattOperationType.CONNECTION_STATE));
            }
            RxBleGattCallback.this.connectionStatePublishRelay.accept(RxBleGattCallback.mapConnectionStateToRxBleConnectionStatus(i2));
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            LoggerUtil.logCallback("onServicesDiscovered", bluetoothGatt, i);
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeServicesDiscoveredCallback(bluetoothGatt, i);
            super.onServicesDiscovered(bluetoothGatt, i);
            if (!RxBleGattCallback.this.servicesDiscoveredOutput.hasObservers() || RxBleGattCallback.propagateErrorIfOccurred(RxBleGattCallback.this.servicesDiscoveredOutput, bluetoothGatt, i, BleGattOperationType.SERVICE_DISCOVERY)) {
                return;
            }
            RxBleGattCallback.this.servicesDiscoveredOutput.valueRelay.accept(new RxBleDeviceServices(bluetoothGatt.getServices()));
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            LoggerUtil.logCallback("onCharacteristicRead", bluetoothGatt, i, bluetoothGattCharacteristic, true);
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeReadCallback(bluetoothGatt, bluetoothGattCharacteristic, i);
            super.onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, i);
            if (!RxBleGattCallback.this.readCharacteristicOutput.hasObservers() || RxBleGattCallback.propagateErrorIfOccurred(RxBleGattCallback.this.readCharacteristicOutput, bluetoothGatt, bluetoothGattCharacteristic, i, BleGattOperationType.CHARACTERISTIC_READ)) {
                return;
            }
            RxBleGattCallback.this.readCharacteristicOutput.valueRelay.accept(new ByteAssociation<>(bluetoothGattCharacteristic.getUuid(), bluetoothGattCharacteristic.getValue()));
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            LoggerUtil.logCallback("onCharacteristicWrite", bluetoothGatt, i, bluetoothGattCharacteristic, false);
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeWriteCallback(bluetoothGatt, bluetoothGattCharacteristic, i);
            super.onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic, i);
            if (!RxBleGattCallback.this.writeCharacteristicOutput.hasObservers() || RxBleGattCallback.propagateErrorIfOccurred(RxBleGattCallback.this.writeCharacteristicOutput, bluetoothGatt, bluetoothGattCharacteristic, i, BleGattOperationType.CHARACTERISTIC_WRITE)) {
                return;
            }
            RxBleGattCallback.this.writeCharacteristicOutput.valueRelay.accept(new ByteAssociation<>(bluetoothGattCharacteristic.getUuid(), bluetoothGattCharacteristic.getValue()));
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            LoggerUtil.logCallback("onCharacteristicChanged", bluetoothGatt, bluetoothGattCharacteristic, true);
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeChangedCallback(bluetoothGatt, bluetoothGattCharacteristic);
            super.onCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic);
            if (RxBleGattCallback.this.changedCharacteristicSerializedPublishRelay.hasObservers()) {
                RxBleGattCallback.this.changedCharacteristicSerializedPublishRelay.accept(new CharacteristicChangedEvent(bluetoothGattCharacteristic.getUuid(), Integer.valueOf(bluetoothGattCharacteristic.getInstanceId()), bluetoothGattCharacteristic.getValue()));
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            LoggerUtil.logCallback("onDescriptorRead", bluetoothGatt, i, bluetoothGattDescriptor, true);
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeDescriptorReadCallback(bluetoothGatt, bluetoothGattDescriptor, i);
            super.onDescriptorRead(bluetoothGatt, bluetoothGattDescriptor, i);
            if (!RxBleGattCallback.this.readDescriptorOutput.hasObservers() || RxBleGattCallback.propagateErrorIfOccurred(RxBleGattCallback.this.readDescriptorOutput, bluetoothGatt, bluetoothGattDescriptor, i, BleGattOperationType.DESCRIPTOR_READ)) {
                return;
            }
            RxBleGattCallback.this.readDescriptorOutput.valueRelay.accept(new ByteAssociation<>(bluetoothGattDescriptor, bluetoothGattDescriptor.getValue()));
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            LoggerUtil.logCallback("onDescriptorWrite", bluetoothGatt, i, bluetoothGattDescriptor, false);
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeDescriptorWriteCallback(bluetoothGatt, bluetoothGattDescriptor, i);
            super.onDescriptorWrite(bluetoothGatt, bluetoothGattDescriptor, i);
            if (!RxBleGattCallback.this.writeDescriptorOutput.hasObservers() || RxBleGattCallback.propagateErrorIfOccurred(RxBleGattCallback.this.writeDescriptorOutput, bluetoothGatt, bluetoothGattDescriptor, i, BleGattOperationType.DESCRIPTOR_WRITE)) {
                return;
            }
            RxBleGattCallback.this.writeDescriptorOutput.valueRelay.accept(new ByteAssociation<>(bluetoothGattDescriptor, bluetoothGattDescriptor.getValue()));
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onReliableWriteCompleted(BluetoothGatt bluetoothGatt, int i) {
            LoggerUtil.logCallback("onReliableWriteCompleted", bluetoothGatt, i);
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeReliableWriteCallback(bluetoothGatt, i);
            super.onReliableWriteCompleted(bluetoothGatt, i);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onReadRemoteRssi(BluetoothGatt bluetoothGatt, int i, int i2) {
            LoggerUtil.logCallback("onReadRemoteRssi", bluetoothGatt, i2, i);
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeReadRssiCallback(bluetoothGatt, i, i2);
            super.onReadRemoteRssi(bluetoothGatt, i, i2);
            if (!RxBleGattCallback.this.readRssiOutput.hasObservers() || RxBleGattCallback.propagateErrorIfOccurred(RxBleGattCallback.this.readRssiOutput, bluetoothGatt, i2, BleGattOperationType.READ_RSSI)) {
                return;
            }
            RxBleGattCallback.this.readRssiOutput.valueRelay.accept(Integer.valueOf(i));
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onMtuChanged(BluetoothGatt bluetoothGatt, int i, int i2) {
            LoggerUtil.logCallback("onMtuChanged", bluetoothGatt, i2, i);
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeMtuChangedCallback(bluetoothGatt, i, i2);
            super.onMtuChanged(bluetoothGatt, i, i2);
            if (!RxBleGattCallback.this.changedMtuOutput.hasObservers() || RxBleGattCallback.propagateErrorIfOccurred(RxBleGattCallback.this.changedMtuOutput, bluetoothGatt, i2, BleGattOperationType.ON_MTU_CHANGED)) {
                return;
            }
            RxBleGattCallback.this.changedMtuOutput.valueRelay.accept(Integer.valueOf(i));
        }

        public void onConnectionUpdated(BluetoothGatt bluetoothGatt, int i, int i2, int i3, int i4) {
            LoggerUtil.logConnectionUpdateCallback("onConnectionUpdated", bluetoothGatt, i4, i, i2, i3);
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeParamsUpdateCallback(bluetoothGatt, i, i2, i3, i4);
            if (!RxBleGattCallback.this.updatedConnectionOutput.hasObservers() || RxBleGattCallback.propagateErrorIfOccurred(RxBleGattCallback.this.updatedConnectionOutput, bluetoothGatt, i4, BleGattOperationType.CONNECTION_PRIORITY_CHANGE)) {
                return;
            }
            RxBleGattCallback.this.updatedConnectionOutput.valueRelay.accept(new ConnectionParametersImpl(i, i2, i3));
        }
    };

    private static boolean isException(int i) {
        return i != 0;
    }

    @Inject
    public RxBleGattCallback(@Named("bluetooth_callbacks") Scheduler scheduler, BluetoothGattProvider bluetoothGattProvider, DisconnectionRouter disconnectionRouter, NativeCallbackDispatcher nativeCallbackDispatcher) {
        this.callbackScheduler = scheduler;
        this.bluetoothGattProvider = bluetoothGattProvider;
        this.disconnectionRouter = disconnectionRouter;
        this.nativeCallbackDispatcher = nativeCallbackDispatcher;
    }

    static RxBleConnection.RxBleConnectionState mapConnectionStateToRxBleConnectionStatus(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return RxBleConnection.RxBleConnectionState.DISCONNECTING;
                }
                return RxBleConnection.RxBleConnectionState.DISCONNECTED;
            }
            return RxBleConnection.RxBleConnectionState.CONNECTED;
        }
        return RxBleConnection.RxBleConnectionState.CONNECTING;
    }

    static boolean propagateErrorIfOccurred(Output<?> output, BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i, BleGattOperationType bleGattOperationType) {
        return isException(i) && propagateStatusError(output, new BleGattCharacteristicException(bluetoothGatt, bluetoothGattCharacteristic, i, bleGattOperationType));
    }

    static boolean propagateErrorIfOccurred(Output<?> output, BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i, BleGattOperationType bleGattOperationType) {
        return isException(i) && propagateStatusError(output, new BleGattDescriptorException(bluetoothGatt, bluetoothGattDescriptor, i, bleGattOperationType));
    }

    static boolean propagateErrorIfOccurred(Output<?> output, BluetoothGatt bluetoothGatt, int i, BleGattOperationType bleGattOperationType) {
        return isException(i) && propagateStatusError(output, new BleGattException(bluetoothGatt, i, bleGattOperationType));
    }

    private static boolean propagateStatusError(Output<?> output, BleGattException bleGattException) {
        output.errorRelay.accept(bleGattException);
        return true;
    }

    private <T> Observable<T> withDisconnectionHandling(Output<T> output) {
        return Observable.merge(this.disconnectionRouter.asErrorOnlyObservable(), output.valueRelay, output.errorRelay.flatMap(this.errorMapper));
    }

    public BluetoothGattCallback getBluetoothGattCallback() {
        return this.bluetoothGattCallback;
    }

    public <T> Observable<T> observeDisconnect() {
        return this.disconnectionRouter.asErrorOnlyObservable();
    }

    public Observable<RxBleConnection.RxBleConnectionState> getOnConnectionStateChange() {
        return this.connectionStatePublishRelay.delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
    }

    public Observable<RxBleDeviceServices> getOnServicesDiscovered() {
        return withDisconnectionHandling(this.servicesDiscoveredOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
    }

    public Observable<Integer> getOnMtuChanged() {
        return withDisconnectionHandling(this.changedMtuOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
    }

    public Observable<ByteAssociation<UUID>> getOnCharacteristicRead() {
        return withDisconnectionHandling(this.readCharacteristicOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
    }

    public Observable<ByteAssociation<UUID>> getOnCharacteristicWrite() {
        return withDisconnectionHandling(this.writeCharacteristicOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
    }

    public Observable<CharacteristicChangedEvent> getOnCharacteristicChanged() {
        return Observable.merge(this.disconnectionRouter.asErrorOnlyObservable(), this.changedCharacteristicSerializedPublishRelay).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
    }

    public Observable<ByteAssociation<BluetoothGattDescriptor>> getOnDescriptorRead() {
        return withDisconnectionHandling(this.readDescriptorOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
    }

    public Observable<ByteAssociation<BluetoothGattDescriptor>> getOnDescriptorWrite() {
        return withDisconnectionHandling(this.writeDescriptorOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
    }

    public Observable<Integer> getOnRssiRead() {
        return withDisconnectionHandling(this.readRssiOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
    }

    public Observable<ConnectionParameters> getConnectionParametersUpdates() {
        return withDisconnectionHandling(this.updatedConnectionOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
    }

    public void setNativeCallback(BluetoothGattCallback bluetoothGattCallback) {
        this.nativeCallbackDispatcher.setNativeCallback(bluetoothGattCallback);
    }

    public void setHiddenNativeCallback(HiddenBluetoothGattCallback hiddenBluetoothGattCallback) {
        this.nativeCallbackDispatcher.setNativeCallbackHidden(hiddenBluetoothGattCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Output<T> {
        final PublishRelay<T> valueRelay = PublishRelay.create();
        final PublishRelay<BleGattException> errorRelay = PublishRelay.create();

        Output() {
        }

        boolean hasObservers() {
            return this.valueRelay.hasObservers() || this.errorRelay.hasObservers();
        }
    }
}
