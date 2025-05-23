package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothDevice;
import bleshadow.dagger.Lazy;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.ClientComponent;
import com.polidea.rxandroidble2.RxBleAdapterStateObservable;
import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.exceptions.BleScanException;
import com.polidea.rxandroidble2.internal.RxBleDeviceProvider;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.operations.LegacyScanOperation;
import com.polidea.rxandroidble2.internal.scan.RxBleInternalScanResult;
import com.polidea.rxandroidble2.internal.scan.RxBleInternalScanResultLegacy;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifier;
import com.polidea.rxandroidble2.internal.scan.ScanSetup;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilder;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueue;
import com.polidea.rxandroidble2.internal.util.CheckerLocationPermission;
import com.polidea.rxandroidble2.internal.util.ClientStateObservable;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatus;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import com.polidea.rxandroidble2.internal.util.UUIDUtil;
import com.polidea.rxandroidble2.scan.BackgroundScanner;
import com.polidea.rxandroidble2.scan.ScanFilter;
import com.polidea.rxandroidble2.scan.ScanResult;
import com.polidea.rxandroidble2.scan.ScanSettings;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class RxBleClientImpl extends RxBleClient {
    @Deprecated
    public static final String TAG = "RxBleClient";
    private final BackgroundScanner backgroundScanner;
    final Scheduler bluetoothInteractionScheduler;
    private final CheckerLocationPermission checkerLocationPermission;
    private final ClientComponent.ClientComponentFinalizer clientComponentFinalizer;
    final Function<RxBleInternalScanResult, ScanResult> internalToExternalScanResultMapFunction;
    private final Lazy<ClientStateObservable> lazyClientStateObservable;
    private final LocationServicesStatus locationServicesStatus;
    final ClientOperationQueue operationQueue;
    final Map<Set<UUID>, Observable<RxBleScanResult>> queuedScanOperations = new HashMap();
    private final Observable<RxBleAdapterStateObservable.BleAdapterState> rxBleAdapterStateObservable;
    private final RxBleAdapterWrapper rxBleAdapterWrapper;
    private final RxBleDeviceProvider rxBleDeviceProvider;
    final ScanPreconditionsVerifier scanPreconditionVerifier;
    final ScanSetupBuilder scanSetupBuilder;
    private final UUIDUtil uuidUtil;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public RxBleClientImpl(RxBleAdapterWrapper rxBleAdapterWrapper, ClientOperationQueue clientOperationQueue, Observable<RxBleAdapterStateObservable.BleAdapterState> observable, UUIDUtil uUIDUtil, LocationServicesStatus locationServicesStatus, Lazy<ClientStateObservable> lazy, RxBleDeviceProvider rxBleDeviceProvider, ScanSetupBuilder scanSetupBuilder, ScanPreconditionsVerifier scanPreconditionsVerifier, Function<RxBleInternalScanResult, ScanResult> function, @Named("bluetooth_interaction") Scheduler scheduler, ClientComponent.ClientComponentFinalizer clientComponentFinalizer, BackgroundScanner backgroundScanner, CheckerLocationPermission checkerLocationPermission) {
        this.uuidUtil = uUIDUtil;
        this.operationQueue = clientOperationQueue;
        this.rxBleAdapterWrapper = rxBleAdapterWrapper;
        this.rxBleAdapterStateObservable = observable;
        this.locationServicesStatus = locationServicesStatus;
        this.lazyClientStateObservable = lazy;
        this.rxBleDeviceProvider = rxBleDeviceProvider;
        this.scanSetupBuilder = scanSetupBuilder;
        this.scanPreconditionVerifier = scanPreconditionsVerifier;
        this.internalToExternalScanResultMapFunction = function;
        this.bluetoothInteractionScheduler = scheduler;
        this.clientComponentFinalizer = clientComponentFinalizer;
        this.backgroundScanner = backgroundScanner;
        this.checkerLocationPermission = checkerLocationPermission;
    }

    protected void finalize() throws Throwable {
        this.clientComponentFinalizer.onFinalize();
        super.finalize();
    }

    @Override // com.polidea.rxandroidble2.RxBleClient
    public RxBleDevice getBleDevice(String str) {
        guardBluetoothAdapterAvailable();
        return this.rxBleDeviceProvider.getBleDevice(str);
    }

    @Override // com.polidea.rxandroidble2.RxBleClient
    public Set<RxBleDevice> getBondedDevices() {
        guardBluetoothAdapterAvailable();
        HashSet hashSet = new HashSet();
        for (BluetoothDevice bluetoothDevice : this.rxBleAdapterWrapper.getBondedDevices()) {
            hashSet.add(getBleDevice(bluetoothDevice.getAddress()));
        }
        return hashSet;
    }

    @Override // com.polidea.rxandroidble2.RxBleClient
    public Observable<ScanResult> scanBleDevices(final ScanSettings scanSettings, final ScanFilter... scanFilterArr) {
        return Observable.defer(new Callable<ObservableSource<? extends ScanResult>>() { // from class: com.polidea.rxandroidble2.RxBleClientImpl.1
            @Override // java.util.concurrent.Callable
            /* renamed from: call */
            public ObservableSource<? extends ScanResult> call2() {
                RxBleClientImpl.this.scanPreconditionVerifier.verify(scanSettings.shouldCheckLocationProviderState());
                ScanSetup build = RxBleClientImpl.this.scanSetupBuilder.build(scanSettings, scanFilterArr);
                return RxBleClientImpl.this.operationQueue.queue(build.scanOperation).unsubscribeOn(RxBleClientImpl.this.bluetoothInteractionScheduler).compose(build.scanOperationBehaviourEmulatorTransformer).map(RxBleClientImpl.this.internalToExternalScanResultMapFunction).doOnNext(new Consumer<ScanResult>() { // from class: com.polidea.rxandroidble2.RxBleClientImpl.1.1
                    @Override // io.reactivex.functions.Consumer
                    public void accept(ScanResult scanResult) {
                        if (RxBleLog.getShouldLogScannedPeripherals()) {
                            RxBleLog.i("%s", scanResult);
                        }
                    }
                }).mergeWith(RxBleClientImpl.this.bluetoothAdapterOffExceptionObservable());
            }
        });
    }

    @Override // com.polidea.rxandroidble2.RxBleClient
    public BackgroundScanner getBackgroundScanner() {
        return this.backgroundScanner;
    }

    @Override // com.polidea.rxandroidble2.RxBleClient
    public Observable<RxBleScanResult> scanBleDevices(final UUID... uuidArr) {
        return Observable.defer(new Callable<ObservableSource<? extends RxBleScanResult>>() { // from class: com.polidea.rxandroidble2.RxBleClientImpl.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public ObservableSource<? extends RxBleScanResult> call() {
                RxBleClientImpl.this.scanPreconditionVerifier.verify(true);
                return RxBleClientImpl.this.initializeScan(uuidArr);
            }
        });
    }

    Observable<RxBleScanResult> initializeScan(UUID[] uuidArr) {
        Observable<RxBleScanResult> observable;
        Set<UUID> distinctSet = this.uuidUtil.toDistinctSet(uuidArr);
        synchronized (this.queuedScanOperations) {
            observable = this.queuedScanOperations.get(distinctSet);
            if (observable == null) {
                observable = createScanOperationApi18(uuidArr);
                this.queuedScanOperations.put(distinctSet, observable);
            }
        }
        return observable;
    }

    <T> Observable<T> bluetoothAdapterOffExceptionObservable() {
        return this.rxBleAdapterStateObservable.filter(new Predicate<RxBleAdapterStateObservable.BleAdapterState>() { // from class: com.polidea.rxandroidble2.RxBleClientImpl.4
            @Override // io.reactivex.functions.Predicate
            public boolean test(RxBleAdapterStateObservable.BleAdapterState bleAdapterState) {
                return bleAdapterState != RxBleAdapterStateObservable.BleAdapterState.STATE_ON;
            }
        }).firstElement().flatMap(new Function<RxBleAdapterStateObservable.BleAdapterState, MaybeSource<T>>() { // from class: com.polidea.rxandroidble2.RxBleClientImpl.3
            @Override // io.reactivex.functions.Function
            public MaybeSource<T> apply(RxBleAdapterStateObservable.BleAdapterState bleAdapterState) {
                return Maybe.error(new BleScanException(1));
            }
        }).toObservable();
    }

    RxBleScanResult convertToPublicScanResult(RxBleInternalScanResultLegacy rxBleInternalScanResultLegacy) {
        return new RxBleScanResult(getBleDevice(rxBleInternalScanResultLegacy.getBluetoothDevice().getAddress()), rxBleInternalScanResultLegacy.getRssi(), rxBleInternalScanResultLegacy.getScanRecord());
    }

    private Observable<RxBleScanResult> createScanOperationApi18(UUID[] uuidArr) {
        final Set<UUID> distinctSet = this.uuidUtil.toDistinctSet(uuidArr);
        return this.operationQueue.queue(new LegacyScanOperation(uuidArr, this.rxBleAdapterWrapper, this.uuidUtil)).doFinally(new Action() { // from class: com.polidea.rxandroidble2.RxBleClientImpl.7
            @Override // io.reactivex.functions.Action
            public void run() {
                synchronized (RxBleClientImpl.this.queuedScanOperations) {
                    RxBleClientImpl.this.queuedScanOperations.remove(distinctSet);
                }
            }
        }).mergeWith(bluetoothAdapterOffExceptionObservable()).map(new Function<RxBleInternalScanResultLegacy, RxBleScanResult>() { // from class: com.polidea.rxandroidble2.RxBleClientImpl.6
            @Override // io.reactivex.functions.Function
            public RxBleScanResult apply(RxBleInternalScanResultLegacy rxBleInternalScanResultLegacy) {
                return RxBleClientImpl.this.convertToPublicScanResult(rxBleInternalScanResultLegacy);
            }
        }).doOnNext(new Consumer<RxBleScanResult>() { // from class: com.polidea.rxandroidble2.RxBleClientImpl.5
            @Override // io.reactivex.functions.Consumer
            public void accept(RxBleScanResult rxBleScanResult) {
                RxBleLog.i("%s", rxBleScanResult);
            }
        }).share();
    }

    private void guardBluetoothAdapterAvailable() {
        if (!this.rxBleAdapterWrapper.hasBluetoothAdapter()) {
            throw new UnsupportedOperationException("RxAndroidBle library needs a BluetoothAdapter to be available in the system to work. If this is a test on an emulator then you can use 'https://github.com/Polidea/RxAndroidBle/tree/master/mockrxandroidble'");
        }
    }

    @Override // com.polidea.rxandroidble2.RxBleClient
    public Observable<RxBleClient.State> observeStateChanges() {
        return this.lazyClientStateObservable.get();
    }

    @Override // com.polidea.rxandroidble2.RxBleClient
    public RxBleClient.State getState() {
        if (!this.rxBleAdapterWrapper.hasBluetoothAdapter()) {
            return RxBleClient.State.BLUETOOTH_NOT_AVAILABLE;
        }
        if (!this.locationServicesStatus.isLocationPermissionOk()) {
            return RxBleClient.State.LOCATION_PERMISSION_NOT_GRANTED;
        }
        if (!this.rxBleAdapterWrapper.isBluetoothEnabled()) {
            return RxBleClient.State.BLUETOOTH_NOT_ENABLED;
        }
        if (!this.locationServicesStatus.isLocationProviderOk()) {
            return RxBleClient.State.LOCATION_SERVICES_NOT_ENABLED;
        }
        return RxBleClient.State.READY;
    }

    @Override // com.polidea.rxandroidble2.RxBleClient
    public boolean isScanRuntimePermissionGranted() {
        return this.checkerLocationPermission.isScanRuntimePermissionGranted();
    }

    @Override // com.polidea.rxandroidble2.RxBleClient
    public String[] getRecommendedScanRuntimePermissions() {
        return this.checkerLocationPermission.getRecommendedScanRuntimePermissions();
    }
}
