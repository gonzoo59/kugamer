package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import com.polidea.rxandroidble2.RxBleDeviceServices;
import com.polidea.rxandroidble2.exceptions.BleGattCallbackTimeoutException;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.SingleResponseOperation;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.logger.LoggerUtilBluetoothServices;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
/* loaded from: classes2.dex */
public class ServiceDiscoveryOperation extends SingleResponseOperation<RxBleDeviceServices> {
    final LoggerUtilBluetoothServices bleServicesLogger;
    final BluetoothGatt bluetoothGatt;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ServiceDiscoveryOperation(RxBleGattCallback rxBleGattCallback, BluetoothGatt bluetoothGatt, LoggerUtilBluetoothServices loggerUtilBluetoothServices, TimeoutConfiguration timeoutConfiguration) {
        super(bluetoothGatt, rxBleGattCallback, BleGattOperationType.SERVICE_DISCOVERY, timeoutConfiguration);
        this.bluetoothGatt = bluetoothGatt;
        this.bleServicesLogger = loggerUtilBluetoothServices;
    }

    @Override // com.polidea.rxandroidble2.internal.SingleResponseOperation
    protected Single<RxBleDeviceServices> getCallback(RxBleGattCallback rxBleGattCallback) {
        return rxBleGattCallback.getOnServicesDiscovered().firstOrError().doOnSuccess(new Consumer<RxBleDeviceServices>() { // from class: com.polidea.rxandroidble2.internal.operations.ServiceDiscoveryOperation.1
            @Override // io.reactivex.functions.Consumer
            public void accept(RxBleDeviceServices rxBleDeviceServices) {
                ServiceDiscoveryOperation.this.bleServicesLogger.log(rxBleDeviceServices, ServiceDiscoveryOperation.this.bluetoothGatt.getDevice());
            }
        });
    }

    @Override // com.polidea.rxandroidble2.internal.SingleResponseOperation
    protected boolean startOperation(BluetoothGatt bluetoothGatt) {
        return bluetoothGatt.discoverServices();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.polidea.rxandroidble2.internal.operations.ServiceDiscoveryOperation$2  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass2 implements Callable<SingleSource<? extends RxBleDeviceServices>> {
        final /* synthetic */ BluetoothGatt val$bluetoothGatt;
        final /* synthetic */ Scheduler val$timeoutScheduler;

        AnonymousClass2(BluetoothGatt bluetoothGatt, Scheduler scheduler) {
            this.val$bluetoothGatt = bluetoothGatt;
            this.val$timeoutScheduler = scheduler;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public SingleSource<? extends RxBleDeviceServices> call() {
            if (this.val$bluetoothGatt.getServices().size() == 0) {
                return Single.error(new BleGattCallbackTimeoutException(this.val$bluetoothGatt, BleGattOperationType.SERVICE_DISCOVERY));
            }
            return Single.timer(5L, TimeUnit.SECONDS, this.val$timeoutScheduler).flatMap(new Function<Long, Single<RxBleDeviceServices>>() { // from class: com.polidea.rxandroidble2.internal.operations.ServiceDiscoveryOperation.2.1
                @Override // io.reactivex.functions.Function
                public Single<RxBleDeviceServices> apply(Long l) {
                    return Single.fromCallable(new Callable<RxBleDeviceServices>() { // from class: com.polidea.rxandroidble2.internal.operations.ServiceDiscoveryOperation.2.1.1
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // java.util.concurrent.Callable
                        public RxBleDeviceServices call() {
                            return new RxBleDeviceServices(AnonymousClass2.this.val$bluetoothGatt.getServices());
                        }
                    });
                }
            });
        }
    }

    @Override // com.polidea.rxandroidble2.internal.SingleResponseOperation
    protected Single<RxBleDeviceServices> timeoutFallbackProcedure(BluetoothGatt bluetoothGatt, RxBleGattCallback rxBleGattCallback, Scheduler scheduler) {
        return Single.defer(new AnonymousClass2(bluetoothGatt, scheduler));
    }

    @Override // com.polidea.rxandroidble2.internal.SingleResponseOperation
    public String toString() {
        return "ServiceDiscoveryOperation{" + super.toString() + '}';
    }
}
