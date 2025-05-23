package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.content.ContentResolver;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import bleshadow.dagger.Binds;
import bleshadow.dagger.BindsInstance;
import bleshadow.dagger.Component;
import bleshadow.dagger.Module;
import bleshadow.dagger.Provides;
import bleshadow.javax.inject.Named;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.RxBleAdapterStateObservable;
import com.polidea.rxandroidble2.helpers.LocationServicesOkObservable;
import com.polidea.rxandroidble2.internal.DeviceComponent;
import com.polidea.rxandroidble2.internal.scan.BackgroundScannerImpl;
import com.polidea.rxandroidble2.internal.scan.InternalToExternalScanResultConverter;
import com.polidea.rxandroidble2.internal.scan.RxBleInternalScanResult;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifier;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifierApi18;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifierApi24;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilder;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi18;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi21;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi23;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueue;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueueImpl;
import com.polidea.rxandroidble2.internal.serialization.RxBleThreadFactory;
import com.polidea.rxandroidble2.internal.util.LocationServicesOkObservableApi23Factory;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatus;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi18;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi23;
import com.polidea.rxandroidble2.internal.util.ObservableUtil;
import com.polidea.rxandroidble2.scan.BackgroundScanner;
import com.polidea.rxandroidble2.scan.ScanResult;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Component(modules = {ClientModule.class})
@ClientScope
/* loaded from: classes2.dex */
public interface ClientComponent {

    /* loaded from: classes2.dex */
    public interface Builder {
        @BindsInstance
        Builder applicationContext(Context context);

        ClientComponent build();
    }

    /* loaded from: classes2.dex */
    public interface ClientComponentFinalizer {
        void onFinalize();
    }

    LocationServicesOkObservable locationServicesOkObservable();

    RxBleClient rxBleClient();

    /* loaded from: classes2.dex */
    public static class NamedExecutors {
        public static final String BLUETOOTH_INTERACTION = "executor_bluetooth_interaction";
        public static final String CONNECTION_QUEUE = "executor_connection_queue";

        private NamedExecutors() {
        }
    }

    /* loaded from: classes2.dex */
    public static class NamedSchedulers {
        public static final String BLUETOOTH_CALLBACKS = "bluetooth_callbacks";
        public static final String BLUETOOTH_INTERACTION = "bluetooth_interaction";
        public static final String COMPUTATION = "computation";
        public static final String TIMEOUT = "timeout";

        private NamedSchedulers() {
        }
    }

    /* loaded from: classes2.dex */
    public static class PlatformConstants {
        public static final String BOOL_IS_ANDROID_WEAR = "android-wear";
        public static final String INT_DEVICE_SDK = "device-sdk";
        public static final String INT_TARGET_SDK = "target-sdk";
        public static final String STRING_ARRAY_SCAN_PERMISSIONS = "scan-permissions";

        private PlatformConstants() {
        }
    }

    /* loaded from: classes2.dex */
    public static class NamedBooleanObservables {
        public static final String LOCATION_SERVICES_OK = "location-ok-boolean-observable";

        private NamedBooleanObservables() {
        }
    }

    /* loaded from: classes2.dex */
    public static class BluetoothConstants {
        public static final String DISABLE_NOTIFICATION_VALUE = "disable-notification-value";
        public static final String ENABLE_INDICATION_VALUE = "enable-indication-value";
        public static final String ENABLE_NOTIFICATION_VALUE = "enable-notification-value";

        private BluetoothConstants() {
        }
    }

    @Module(subcomponents = {DeviceComponent.class})
    /* loaded from: classes2.dex */
    public static abstract class ClientModule {
        @Binds
        abstract BackgroundScanner bindBackgroundScanner(BackgroundScannerImpl backgroundScannerImpl);

        @Binds
        @ClientScope
        abstract ClientOperationQueue bindClientOperationQueue(ClientOperationQueueImpl clientOperationQueueImpl);

        @Binds
        @ClientScope
        abstract RxBleClient bindRxBleClient(RxBleClientImpl rxBleClientImpl);

        @Binds
        abstract Observable<RxBleAdapterStateObservable.BleAdapterState> bindStateObs(RxBleAdapterStateObservable rxBleAdapterStateObservable);

        @Named(NamedSchedulers.TIMEOUT)
        @Binds
        abstract Scheduler bindTimeoutScheduler(@Named("computation") Scheduler scheduler);

        @Binds
        abstract Function<RxBleInternalScanResult, ScanResult> provideScanResultMapper(InternalToExternalScanResultConverter internalToExternalScanResultConverter);

        /* JADX INFO: Access modifiers changed from: package-private */
        @Provides
        public static BluetoothManager provideBluetoothManager(Context context) {
            return (BluetoothManager) context.getSystemService("bluetooth");
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Provides
        public static BluetoothAdapter provideBluetoothAdapter() {
            return BluetoothAdapter.getDefaultAdapter();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Named(NamedSchedulers.COMPUTATION)
        @Provides
        public static Scheduler provideComputationScheduler() {
            return Schedulers.computation();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Named(PlatformConstants.INT_DEVICE_SDK)
        @Provides
        public static int provideDeviceSdk() {
            return Build.VERSION.SDK_INT;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Named(PlatformConstants.STRING_ARRAY_SCAN_PERMISSIONS)
        @Provides
        public static String[] provideRecommendedScanRuntimePermissionNames(@Named("device-sdk") int i, @Named("target-sdk") int i2) {
            int min = Math.min(i, i2);
            if (min < 23) {
                return new String[0];
            }
            if (min < 29) {
                return new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
            }
            return new String[]{"android.permission.ACCESS_FINE_LOCATION"};
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Provides
        public static ContentResolver provideContentResolver(Context context) {
            return context.getContentResolver();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Provides
        public static LocationServicesStatus provideLocationServicesStatus(@Named("device-sdk") int i, Provider<LocationServicesStatusApi18> provider, Provider<LocationServicesStatusApi23> provider2) {
            if (i < 23) {
                return provider.get();
            }
            return provider2.get();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Named(NamedBooleanObservables.LOCATION_SERVICES_OK)
        @Provides
        public static Observable<Boolean> provideLocationServicesOkObservable(@Named("device-sdk") int i, LocationServicesOkObservableApi23Factory locationServicesOkObservableApi23Factory) {
            if (i < 23) {
                return ObservableUtil.justOnNext(true);
            }
            return locationServicesOkObservableApi23Factory.get();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Named(NamedExecutors.CONNECTION_QUEUE)
        @ClientScope
        @Provides
        public static ExecutorService provideConnectionQueueExecutorService() {
            return Executors.newCachedThreadPool();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Named(NamedExecutors.BLUETOOTH_INTERACTION)
        @ClientScope
        @Provides
        public static ExecutorService provideBluetoothInteractionExecutorService() {
            return Executors.newSingleThreadExecutor();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Named(NamedSchedulers.BLUETOOTH_INTERACTION)
        @ClientScope
        @Provides
        public static Scheduler provideBluetoothInteractionScheduler(@Named("executor_bluetooth_interaction") ExecutorService executorService) {
            return Schedulers.from(executorService);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Named(NamedSchedulers.BLUETOOTH_CALLBACKS)
        @ClientScope
        @Provides
        public static Scheduler provideBluetoothCallbacksScheduler() {
            return RxJavaPlugins.createSingleScheduler(new RxBleThreadFactory());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Provides
        public static ClientComponentFinalizer provideFinalizationCloseable(@Named("executor_bluetooth_interaction") final ExecutorService executorService, @Named("bluetooth_callbacks") final Scheduler scheduler, @Named("executor_connection_queue") final ExecutorService executorService2) {
            return new ClientComponentFinalizer() { // from class: com.polidea.rxandroidble2.ClientComponent.ClientModule.1
                @Override // com.polidea.rxandroidble2.ClientComponent.ClientComponentFinalizer
                public void onFinalize() {
                    executorService.shutdown();
                    scheduler.shutdown();
                    executorService2.shutdown();
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Provides
        public static LocationManager provideLocationManager(Context context) {
            return (LocationManager) context.getSystemService("location");
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Named(PlatformConstants.INT_TARGET_SDK)
        @Provides
        public static int provideTargetSdk(Context context) {
            try {
                return context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).targetSdkVersion;
            } catch (Throwable unused) {
                return Integer.MAX_VALUE;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Named(PlatformConstants.BOOL_IS_ANDROID_WEAR)
        @Provides
        public static boolean provideIsAndroidWear(Context context, @Named("device-sdk") int i) {
            return i >= 20 && context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @ClientScope
        @Provides
        public static ScanSetupBuilder provideScanSetupProvider(@Named("device-sdk") int i, Provider<ScanSetupBuilderImplApi18> provider, Provider<ScanSetupBuilderImplApi21> provider2, Provider<ScanSetupBuilderImplApi23> provider3) {
            if (i < 21) {
                return provider.get();
            }
            if (i < 23) {
                return provider2.get();
            }
            return provider3.get();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Named(BluetoothConstants.ENABLE_NOTIFICATION_VALUE)
        @Provides
        public static byte[] provideEnableNotificationValue() {
            return BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Named(BluetoothConstants.ENABLE_INDICATION_VALUE)
        @Provides
        public static byte[] provideEnableIndicationValue() {
            return BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Named(BluetoothConstants.DISABLE_NOTIFICATION_VALUE)
        @Provides
        public static byte[] provideDisableNotificationValue() {
            return BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Provides
        public static ScanPreconditionsVerifier provideScanPreconditionVerifier(@Named("device-sdk") int i, Provider<ScanPreconditionsVerifierApi18> provider, Provider<ScanPreconditionsVerifierApi24> provider2) {
            if (i < 24) {
                return provider.get();
            }
            return provider2.get();
        }
    }
}
