package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import bleshadow.dagger.internal.DelegateFactory;
import bleshadow.dagger.internal.DoubleCheck;
import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.InstanceFactory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.dagger.internal.SetBuilder;
import bleshadow.javax.inject.Provider;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.polidea.rxandroidble2.ClientComponent;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.helpers.LocationServicesOkObservable;
import com.polidea.rxandroidble2.helpers.LocationServicesOkObservable_Factory;
import com.polidea.rxandroidble2.internal.DeviceComponent;
import com.polidea.rxandroidble2.internal.DeviceModule_ProvideBluetoothDeviceFactory;
import com.polidea.rxandroidble2.internal.DeviceModule_ProvideConnectionStateChangeListenerFactory;
import com.polidea.rxandroidble2.internal.DeviceModule_ProvideConnectionStateRelayFactory;
import com.polidea.rxandroidble2.internal.DeviceModule_ProvidesConnectTimeoutConfFactory;
import com.polidea.rxandroidble2.internal.DeviceModule_ProvidesDisconnectTimeoutConfFactory;
import com.polidea.rxandroidble2.internal.RxBleDeviceImpl_Factory;
import com.polidea.rxandroidble2.internal.RxBleDeviceProvider;
import com.polidea.rxandroidble2.internal.RxBleDeviceProvider_Factory;
import com.polidea.rxandroidble2.internal.cache.DeviceComponentCache;
import com.polidea.rxandroidble2.internal.cache.DeviceComponentCache_Factory;
import com.polidea.rxandroidble2.internal.connection.BluetoothGattProvider;
import com.polidea.rxandroidble2.internal.connection.BluetoothGattProvider_Factory;
import com.polidea.rxandroidble2.internal.connection.ConnectionComponent;
import com.polidea.rxandroidble2.internal.connection.ConnectionModule_GattWriteMtuOverheadFactory;
import com.polidea.rxandroidble2.internal.connection.ConnectionModule_MinimumMtuFactory;
import com.polidea.rxandroidble2.internal.connection.ConnectionModule_ProvideBluetoothGattFactory;
import com.polidea.rxandroidble2.internal.connection.ConnectionModule_ProvideCharacteristicPropertiesParserFactory;
import com.polidea.rxandroidble2.internal.connection.ConnectionModule_ProvideIllegalOperationHandlerFactory;
import com.polidea.rxandroidble2.internal.connection.ConnectionModule_ProvidesOperationTimeoutConfFactory;
import com.polidea.rxandroidble2.internal.connection.ConnectionStateChangeListener;
import com.polidea.rxandroidble2.internal.connection.ConnectionSubscriptionWatcher;
import com.polidea.rxandroidble2.internal.connection.ConnectorImpl_Factory;
import com.polidea.rxandroidble2.internal.connection.DescriptorWriter_Factory;
import com.polidea.rxandroidble2.internal.connection.DisconnectAction_Factory;
import com.polidea.rxandroidble2.internal.connection.DisconnectionRouter_Factory;
import com.polidea.rxandroidble2.internal.connection.IllegalOperationChecker_Factory;
import com.polidea.rxandroidble2.internal.connection.IllegalOperationMessageCreator_Factory;
import com.polidea.rxandroidble2.internal.connection.LoggingIllegalOperationHandler_Factory;
import com.polidea.rxandroidble2.internal.connection.LongWriteOperationBuilderImpl_Factory;
import com.polidea.rxandroidble2.internal.connection.MtuBasedPayloadSizeLimit_Factory;
import com.polidea.rxandroidble2.internal.connection.MtuWatcher_Factory;
import com.polidea.rxandroidble2.internal.connection.NativeCallbackDispatcher_Factory;
import com.polidea.rxandroidble2.internal.connection.NotificationAndIndicationManager_Factory;
import com.polidea.rxandroidble2.internal.connection.RxBleConnectionImpl;
import com.polidea.rxandroidble2.internal.connection.RxBleConnectionImpl_Factory;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback_Factory;
import com.polidea.rxandroidble2.internal.connection.ServiceDiscoveryManager_Factory;
import com.polidea.rxandroidble2.internal.connection.ThrowingIllegalOperationHandler_Factory;
import com.polidea.rxandroidble2.internal.logger.LoggerUtilBluetoothServices_Factory;
import com.polidea.rxandroidble2.internal.operations.ConnectOperation;
import com.polidea.rxandroidble2.internal.operations.ConnectOperation_Factory;
import com.polidea.rxandroidble2.internal.operations.DisconnectOperation_Factory;
import com.polidea.rxandroidble2.internal.operations.OperationsProviderImpl_Factory;
import com.polidea.rxandroidble2.internal.operations.ReadRssiOperation_Factory;
import com.polidea.rxandroidble2.internal.operations.TimeoutConfiguration;
import com.polidea.rxandroidble2.internal.scan.AndroidScanObjectsConverter_Factory;
import com.polidea.rxandroidble2.internal.scan.BackgroundScannerImpl_Factory;
import com.polidea.rxandroidble2.internal.scan.InternalScanResultCreator;
import com.polidea.rxandroidble2.internal.scan.InternalScanResultCreator_Factory;
import com.polidea.rxandroidble2.internal.scan.InternalToExternalScanResultConverter_Factory;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifierApi18_Factory;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifierApi24_Factory;
import com.polidea.rxandroidble2.internal.scan.ScanSettingsEmulator_Factory;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilder;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi18_Factory;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi21_Factory;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi23_Factory;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueue;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueueImpl_Factory;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueueImpl;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueueImpl_Factory;
import com.polidea.rxandroidble2.internal.util.BleConnectionCompat;
import com.polidea.rxandroidble2.internal.util.CheckerLocationPermission;
import com.polidea.rxandroidble2.internal.util.CheckerLocationPermission_Factory;
import com.polidea.rxandroidble2.internal.util.CheckerLocationProvider_Factory;
import com.polidea.rxandroidble2.internal.util.ClientStateObservable_Factory;
import com.polidea.rxandroidble2.internal.util.LocationServicesOkObservableApi23Factory;
import com.polidea.rxandroidble2.internal.util.LocationServicesOkObservableApi23Factory_Factory;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatus;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi18_Factory;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi23_Factory;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper_Factory;
import com.polidea.rxandroidble2.internal.util.UUIDUtil_Factory;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import java.util.Set;
import java.util.concurrent.ExecutorService;
/* loaded from: classes2.dex */
public final class DaggerClientComponent implements ClientComponent {
    private AndroidScanObjectsConverter_Factory androidScanObjectsConverterProvider;
    private Context applicationContext;
    private Provider<Context> applicationContextProvider;
    private BackgroundScannerImpl_Factory backgroundScannerImplProvider;
    private Provider<ClientOperationQueue> bindClientOperationQueueProvider;
    private Provider<RxBleClient> bindRxBleClientProvider;
    private Provider<CheckerLocationPermission> checkerLocationPermissionProvider;
    private CheckerLocationProvider_Factory checkerLocationProvider;
    private ClientOperationQueueImpl_Factory clientOperationQueueImplProvider;
    private ClientStateObservable_Factory clientStateObservableProvider;
    private Provider<DeviceComponent.Builder> deviceComponentBuilderProvider;
    private Provider<DeviceComponentCache> deviceComponentCacheProvider;
    private Provider<InternalScanResultCreator> internalScanResultCreatorProvider;
    private InternalToExternalScanResultConverter_Factory internalToExternalScanResultConverterProvider;
    private LocationServicesOkObservableApi23Factory_Factory locationServicesOkObservableApi23FactoryProvider;
    private LocationServicesStatusApi23_Factory locationServicesStatusApi23Provider;
    private Provider<Scheduler> provideBluetoothCallbacksSchedulerProvider;
    private Provider<ExecutorService> provideBluetoothInteractionExecutorServiceProvider;
    private Provider<Scheduler> provideBluetoothInteractionSchedulerProvider;
    private ClientComponent_ClientModule_ProvideBluetoothManagerFactory provideBluetoothManagerProvider;
    private Provider<ExecutorService> provideConnectionQueueExecutorServiceProvider;
    private ClientComponent_ClientModule_ProvideContentResolverFactory provideContentResolverProvider;
    private ClientComponent_ClientModule_ProvideFinalizationCloseableFactory provideFinalizationCloseableProvider;
    private ClientComponent_ClientModule_ProvideIsAndroidWearFactory provideIsAndroidWearProvider;
    private ClientComponent_ClientModule_ProvideLocationManagerFactory provideLocationManagerProvider;
    private ClientComponent_ClientModule_ProvideLocationServicesOkObservableFactory provideLocationServicesOkObservableProvider;
    private ClientComponent_ClientModule_ProvideLocationServicesStatusFactory provideLocationServicesStatusProvider;
    private ClientComponent_ClientModule_ProvideRecommendedScanRuntimePermissionNamesFactory provideRecommendedScanRuntimePermissionNamesProvider;
    private ClientComponent_ClientModule_ProvideScanPreconditionVerifierFactory provideScanPreconditionVerifierProvider;
    private Provider<ScanSetupBuilder> provideScanSetupProvider;
    private ClientComponent_ClientModule_ProvideTargetSdkFactory provideTargetSdkProvider;
    private RxBleAdapterStateObservable_Factory rxBleAdapterStateObservableProvider;
    private RxBleAdapterWrapper_Factory rxBleAdapterWrapperProvider;
    private RxBleClientImpl_Factory rxBleClientImplProvider;
    private Provider<RxBleDeviceProvider> rxBleDeviceProvider;
    private ScanPreconditionsVerifierApi18_Factory scanPreconditionsVerifierApi18Provider;
    private ScanPreconditionsVerifierApi24_Factory scanPreconditionsVerifierApi24Provider;
    private ScanSettingsEmulator_Factory scanSettingsEmulatorProvider;
    private ScanSetupBuilderImplApi18_Factory scanSetupBuilderImplApi18Provider;
    private ScanSetupBuilderImplApi21_Factory scanSetupBuilderImplApi21Provider;
    private ScanSetupBuilderImplApi23_Factory scanSetupBuilderImplApi23Provider;

    private DaggerClientComponent(Builder builder) {
        initialize(builder);
    }

    public static ClientComponent.Builder builder() {
        return new Builder();
    }

    private LocationServicesStatus getLocationServicesStatus() {
        return ClientComponent_ClientModule_ProvideLocationServicesStatusFactory.proxyProvideLocationServicesStatus(ClientComponent.ClientModule.provideDeviceSdk(), LocationServicesStatusApi18_Factory.create(), this.locationServicesStatusApi23Provider);
    }

    private LocationServicesOkObservableApi23Factory getLocationServicesOkObservableApi23Factory() {
        return LocationServicesOkObservableApi23Factory_Factory.newLocationServicesOkObservableApi23Factory(this.applicationContext, getLocationServicesStatus());
    }

    private Observable<Boolean> getNamedObservableOfBoolean() {
        return ClientComponent_ClientModule_ProvideLocationServicesOkObservableFactory.proxyProvideLocationServicesOkObservable(ClientComponent.ClientModule.provideDeviceSdk(), getLocationServicesOkObservableApi23Factory());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RxBleAdapterWrapper getRxBleAdapterWrapper() {
        return new RxBleAdapterWrapper(ClientComponent.ClientModule.provideBluetoothAdapter());
    }

    private void initialize(Builder builder) {
        this.applicationContext = builder.applicationContext;
        Factory create = InstanceFactory.create(builder.applicationContext);
        this.applicationContextProvider = create;
        this.provideContentResolverProvider = ClientComponent_ClientModule_ProvideContentResolverFactory.create(create);
        ClientComponent_ClientModule_ProvideLocationManagerFactory create2 = ClientComponent_ClientModule_ProvideLocationManagerFactory.create(this.applicationContextProvider);
        this.provideLocationManagerProvider = create2;
        this.checkerLocationProvider = CheckerLocationProvider_Factory.create(this.provideContentResolverProvider, create2);
        this.provideTargetSdkProvider = ClientComponent_ClientModule_ProvideTargetSdkFactory.create(this.applicationContextProvider);
        ClientComponent_ClientModule_ProvideRecommendedScanRuntimePermissionNamesFactory create3 = ClientComponent_ClientModule_ProvideRecommendedScanRuntimePermissionNamesFactory.create(ClientComponent_ClientModule_ProvideDeviceSdkFactory.create(), this.provideTargetSdkProvider);
        this.provideRecommendedScanRuntimePermissionNamesProvider = create3;
        this.checkerLocationPermissionProvider = DoubleCheck.provider(CheckerLocationPermission_Factory.create(this.applicationContextProvider, create3));
        ClientComponent_ClientModule_ProvideIsAndroidWearFactory create4 = ClientComponent_ClientModule_ProvideIsAndroidWearFactory.create(this.applicationContextProvider, ClientComponent_ClientModule_ProvideDeviceSdkFactory.create());
        this.provideIsAndroidWearProvider = create4;
        this.locationServicesStatusApi23Provider = LocationServicesStatusApi23_Factory.create(this.checkerLocationProvider, this.checkerLocationPermissionProvider, this.provideTargetSdkProvider, create4);
        this.rxBleAdapterWrapperProvider = RxBleAdapterWrapper_Factory.create(ClientComponent_ClientModule_ProvideBluetoothAdapterFactory.create());
        Provider<ExecutorService> provider = DoubleCheck.provider(ClientComponent_ClientModule_ProvideBluetoothInteractionExecutorServiceFactory.create());
        this.provideBluetoothInteractionExecutorServiceProvider = provider;
        Provider<Scheduler> provider2 = DoubleCheck.provider(ClientComponent_ClientModule_ProvideBluetoothInteractionSchedulerFactory.create(provider));
        this.provideBluetoothInteractionSchedulerProvider = provider2;
        ClientOperationQueueImpl_Factory create5 = ClientOperationQueueImpl_Factory.create(provider2);
        this.clientOperationQueueImplProvider = create5;
        this.bindClientOperationQueueProvider = DoubleCheck.provider(create5);
        this.rxBleAdapterStateObservableProvider = RxBleAdapterStateObservable_Factory.create(this.applicationContextProvider);
        ClientComponent_ClientModule_ProvideLocationServicesStatusFactory create6 = ClientComponent_ClientModule_ProvideLocationServicesStatusFactory.create(ClientComponent_ClientModule_ProvideDeviceSdkFactory.create(), LocationServicesStatusApi18_Factory.create(), this.locationServicesStatusApi23Provider);
        this.provideLocationServicesStatusProvider = create6;
        this.locationServicesOkObservableApi23FactoryProvider = LocationServicesOkObservableApi23Factory_Factory.create(this.applicationContextProvider, create6);
        ClientComponent_ClientModule_ProvideLocationServicesOkObservableFactory create7 = ClientComponent_ClientModule_ProvideLocationServicesOkObservableFactory.create(ClientComponent_ClientModule_ProvideDeviceSdkFactory.create(), this.locationServicesOkObservableApi23FactoryProvider);
        this.provideLocationServicesOkObservableProvider = create7;
        this.clientStateObservableProvider = ClientStateObservable_Factory.create(this.rxBleAdapterWrapperProvider, this.rxBleAdapterStateObservableProvider, create7, this.provideLocationServicesStatusProvider, ClientComponent_ClientModule_ProvideComputationSchedulerFactory.create());
        this.deviceComponentCacheProvider = DoubleCheck.provider(DeviceComponentCache_Factory.create());
        Provider<DeviceComponent.Builder> provider3 = new Provider<DeviceComponent.Builder>() { // from class: com.polidea.rxandroidble2.DaggerClientComponent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // bleshadow.javax.inject.Provider
            public DeviceComponent.Builder get() {
                return new DeviceComponentBuilder();
            }
        };
        this.deviceComponentBuilderProvider = provider3;
        this.rxBleDeviceProvider = DoubleCheck.provider(RxBleDeviceProvider_Factory.create(this.deviceComponentCacheProvider, provider3));
        this.internalScanResultCreatorProvider = DoubleCheck.provider(InternalScanResultCreator_Factory.create(UUIDUtil_Factory.create()));
        ScanSettingsEmulator_Factory create8 = ScanSettingsEmulator_Factory.create(ClientComponent_ClientModule_ProvideComputationSchedulerFactory.create());
        this.scanSettingsEmulatorProvider = create8;
        this.scanSetupBuilderImplApi18Provider = ScanSetupBuilderImplApi18_Factory.create(this.rxBleAdapterWrapperProvider, this.internalScanResultCreatorProvider, create8);
        AndroidScanObjectsConverter_Factory create9 = AndroidScanObjectsConverter_Factory.create(ClientComponent_ClientModule_ProvideDeviceSdkFactory.create());
        this.androidScanObjectsConverterProvider = create9;
        this.scanSetupBuilderImplApi21Provider = ScanSetupBuilderImplApi21_Factory.create(this.rxBleAdapterWrapperProvider, this.internalScanResultCreatorProvider, this.scanSettingsEmulatorProvider, create9);
        this.scanSetupBuilderImplApi23Provider = ScanSetupBuilderImplApi23_Factory.create(this.rxBleAdapterWrapperProvider, this.internalScanResultCreatorProvider, this.scanSettingsEmulatorProvider, this.androidScanObjectsConverterProvider);
        this.provideScanSetupProvider = DoubleCheck.provider(ClientComponent_ClientModule_ProvideScanSetupProviderFactory.create(ClientComponent_ClientModule_ProvideDeviceSdkFactory.create(), this.scanSetupBuilderImplApi18Provider, this.scanSetupBuilderImplApi21Provider, this.scanSetupBuilderImplApi23Provider));
        ScanPreconditionsVerifierApi18_Factory create10 = ScanPreconditionsVerifierApi18_Factory.create(this.rxBleAdapterWrapperProvider, this.provideLocationServicesStatusProvider);
        this.scanPreconditionsVerifierApi18Provider = create10;
        this.scanPreconditionsVerifierApi24Provider = ScanPreconditionsVerifierApi24_Factory.create(create10, ClientComponent_ClientModule_ProvideComputationSchedulerFactory.create());
        this.provideScanPreconditionVerifierProvider = ClientComponent_ClientModule_ProvideScanPreconditionVerifierFactory.create(ClientComponent_ClientModule_ProvideDeviceSdkFactory.create(), this.scanPreconditionsVerifierApi18Provider, this.scanPreconditionsVerifierApi24Provider);
        this.internalToExternalScanResultConverterProvider = InternalToExternalScanResultConverter_Factory.create(this.rxBleDeviceProvider);
        this.provideBluetoothCallbacksSchedulerProvider = DoubleCheck.provider(ClientComponent_ClientModule_ProvideBluetoothCallbacksSchedulerFactory.create());
        Provider<ExecutorService> provider4 = DoubleCheck.provider(ClientComponent_ClientModule_ProvideConnectionQueueExecutorServiceFactory.create());
        this.provideConnectionQueueExecutorServiceProvider = provider4;
        this.provideFinalizationCloseableProvider = ClientComponent_ClientModule_ProvideFinalizationCloseableFactory.create(this.provideBluetoothInteractionExecutorServiceProvider, this.provideBluetoothCallbacksSchedulerProvider, provider4);
        this.backgroundScannerImplProvider = BackgroundScannerImpl_Factory.create(this.rxBleAdapterWrapperProvider, this.androidScanObjectsConverterProvider, this.internalScanResultCreatorProvider, this.internalToExternalScanResultConverterProvider);
        RxBleClientImpl_Factory create11 = RxBleClientImpl_Factory.create(this.rxBleAdapterWrapperProvider, this.bindClientOperationQueueProvider, this.rxBleAdapterStateObservableProvider, UUIDUtil_Factory.create(), this.provideLocationServicesStatusProvider, this.clientStateObservableProvider, this.rxBleDeviceProvider, this.provideScanSetupProvider, this.provideScanPreconditionVerifierProvider, this.internalToExternalScanResultConverterProvider, this.provideBluetoothInteractionSchedulerProvider, this.provideFinalizationCloseableProvider, this.backgroundScannerImplProvider, this.checkerLocationPermissionProvider);
        this.rxBleClientImplProvider = create11;
        this.bindRxBleClientProvider = DoubleCheck.provider(create11);
        this.provideBluetoothManagerProvider = ClientComponent_ClientModule_ProvideBluetoothManagerFactory.create(this.applicationContextProvider);
    }

    @Override // com.polidea.rxandroidble2.ClientComponent
    public LocationServicesOkObservable locationServicesOkObservable() {
        return LocationServicesOkObservable_Factory.newLocationServicesOkObservable(getNamedObservableOfBoolean());
    }

    @Override // com.polidea.rxandroidble2.ClientComponent
    public RxBleClient rxBleClient() {
        return this.bindRxBleClientProvider.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class Builder implements ClientComponent.Builder {
        private Context applicationContext;

        private Builder() {
        }

        @Override // com.polidea.rxandroidble2.ClientComponent.Builder
        public ClientComponent build() {
            if (this.applicationContext == null) {
                throw new IllegalStateException(Context.class.getCanonicalName() + " must be set");
            }
            return new DaggerClientComponent(this);
        }

        @Override // com.polidea.rxandroidble2.ClientComponent.Builder
        public Builder applicationContext(Context context) {
            this.applicationContext = (Context) Preconditions.checkNotNull(context);
            return this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class DeviceComponentBuilder implements DeviceComponent.Builder {
        private String macAddress;

        private DeviceComponentBuilder() {
        }

        @Override // com.polidea.rxandroidble2.internal.DeviceComponent.Builder
        public DeviceComponent build() {
            if (this.macAddress == null) {
                throw new IllegalStateException(String.class.getCanonicalName() + " must be set");
            }
            return new DeviceComponentImpl(this);
        }

        @Override // com.polidea.rxandroidble2.internal.DeviceComponent.Builder
        public DeviceComponentBuilder macAddress(String str) {
            this.macAddress = (String) Preconditions.checkNotNull(str);
            return this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class DeviceComponentImpl implements DeviceComponent {
        private Provider<ConnectionComponent.Builder> connectionComponentBuilderProvider;
        private ConnectorImpl_Factory connectorImplProvider;
        private String macAddress;
        private Provider<String> macAddressProvider;
        private DeviceModule_ProvideBluetoothDeviceFactory provideBluetoothDeviceProvider;
        private Provider<ConnectionStateChangeListener> provideConnectionStateChangeListenerProvider;
        private Provider<BehaviorRelay<RxBleConnection.RxBleConnectionState>> provideConnectionStateRelayProvider;
        private DeviceModule_ProvidesDisconnectTimeoutConfFactory providesDisconnectTimeoutConfProvider;
        private Provider rxBleDeviceImplProvider;

        private DeviceComponentImpl(DeviceComponentBuilder deviceComponentBuilder) {
            initialize(deviceComponentBuilder);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public BluetoothDevice getBluetoothDevice() {
            return DeviceModule_ProvideBluetoothDeviceFactory.proxyProvideBluetoothDevice(this.macAddress, DaggerClientComponent.this.getRxBleAdapterWrapper());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public TimeoutConfiguration getNamedTimeoutConfiguration() {
            return DeviceModule_ProvidesConnectTimeoutConfFactory.proxyProvidesConnectTimeoutConf(ClientComponent_ClientModule_ProvideComputationSchedulerFactory.proxyProvideComputationScheduler());
        }

        private void initialize(DeviceComponentBuilder deviceComponentBuilder) {
            Factory create = InstanceFactory.create(deviceComponentBuilder.macAddress);
            this.macAddressProvider = create;
            this.provideBluetoothDeviceProvider = DeviceModule_ProvideBluetoothDeviceFactory.create(create, DaggerClientComponent.this.rxBleAdapterWrapperProvider);
            this.connectionComponentBuilderProvider = new Provider<ConnectionComponent.Builder>() { // from class: com.polidea.rxandroidble2.DaggerClientComponent.DeviceComponentImpl.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // bleshadow.javax.inject.Provider
                public ConnectionComponent.Builder get() {
                    return new ConnectionComponentBuilder();
                }
            };
            this.connectorImplProvider = ConnectorImpl_Factory.create(DaggerClientComponent.this.bindClientOperationQueueProvider, this.connectionComponentBuilderProvider, DaggerClientComponent.this.provideBluetoothCallbacksSchedulerProvider);
            Provider<BehaviorRelay<RxBleConnection.RxBleConnectionState>> provider = DoubleCheck.provider(DeviceModule_ProvideConnectionStateRelayFactory.create());
            this.provideConnectionStateRelayProvider = provider;
            this.rxBleDeviceImplProvider = DoubleCheck.provider(RxBleDeviceImpl_Factory.create(this.provideBluetoothDeviceProvider, this.connectorImplProvider, provider));
            this.macAddress = deviceComponentBuilder.macAddress;
            this.provideConnectionStateChangeListenerProvider = DoubleCheck.provider(DeviceModule_ProvideConnectionStateChangeListenerFactory.create(this.provideConnectionStateRelayProvider));
            this.providesDisconnectTimeoutConfProvider = DeviceModule_ProvidesDisconnectTimeoutConfFactory.create(ClientComponent_ClientModule_ProvideComputationSchedulerFactory.create());
        }

        @Override // com.polidea.rxandroidble2.internal.DeviceComponent
        public RxBleDevice provideDevice() {
            return (RxBleDevice) this.rxBleDeviceImplProvider.get();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public final class ConnectionComponentBuilder implements ConnectionComponent.Builder {
            private Boolean autoConnect;
            private Timeout operationTimeout;
            private Boolean suppressOperationChecks;

            private ConnectionComponentBuilder() {
            }

            @Override // com.polidea.rxandroidble2.internal.connection.ConnectionComponent.Builder
            public ConnectionComponent build() {
                if (this.autoConnect == null) {
                    throw new IllegalStateException(Boolean.class.getCanonicalName() + " must be set");
                } else if (this.suppressOperationChecks == null) {
                    throw new IllegalStateException(Boolean.class.getCanonicalName() + " must be set");
                } else if (this.operationTimeout == null) {
                    throw new IllegalStateException(Timeout.class.getCanonicalName() + " must be set");
                } else {
                    return new ConnectionComponentImpl(this);
                }
            }

            @Override // com.polidea.rxandroidble2.internal.connection.ConnectionComponent.Builder
            public ConnectionComponentBuilder autoConnect(boolean z) {
                this.autoConnect = (Boolean) Preconditions.checkNotNull(Boolean.valueOf(z));
                return this;
            }

            @Override // com.polidea.rxandroidble2.internal.connection.ConnectionComponent.Builder
            public ConnectionComponentBuilder suppressOperationChecks(boolean z) {
                this.suppressOperationChecks = (Boolean) Preconditions.checkNotNull(Boolean.valueOf(z));
                return this;
            }

            @Override // com.polidea.rxandroidble2.internal.connection.ConnectionComponent.Builder
            public ConnectionComponentBuilder operationTimeout(Timeout timeout) {
                this.operationTimeout = (Timeout) Preconditions.checkNotNull(timeout);
                return this;
            }
        }

        /* loaded from: classes2.dex */
        private final class ConnectionComponentImpl implements ConnectionComponent {
            private Boolean autoConnect;
            private Provider<BluetoothGattProvider> bluetoothGattProvider;
            private Provider<ConnectionOperationQueueImpl> connectionOperationQueueImplProvider;
            private Provider descriptorWriterProvider;
            private Provider disconnectActionProvider;
            private DisconnectOperation_Factory disconnectOperationProvider;
            private Provider disconnectionRouterProvider;
            private IllegalOperationChecker_Factory illegalOperationCheckerProvider;
            private IllegalOperationMessageCreator_Factory illegalOperationMessageCreatorProvider;
            private LoggerUtilBluetoothServices_Factory loggerUtilBluetoothServicesProvider;
            private LoggingIllegalOperationHandler_Factory loggingIllegalOperationHandlerProvider;
            private LongWriteOperationBuilderImpl_Factory longWriteOperationBuilderImplProvider;
            private Provider mtuBasedPayloadSizeLimitProvider;
            private Provider mtuWatcherProvider;
            private Provider notificationAndIndicationManagerProvider;
            private Provider<Timeout> operationTimeoutProvider;
            private OperationsProviderImpl_Factory operationsProviderImplProvider;
            private ConnectionModule_ProvideBluetoothGattFactory provideBluetoothGattProvider;
            private ConnectionModule_ProvideIllegalOperationHandlerFactory provideIllegalOperationHandlerProvider;
            private ConnectionModule_ProvidesOperationTimeoutConfFactory providesOperationTimeoutConfProvider;
            private ReadRssiOperation_Factory readRssiOperationProvider;
            private Provider<RxBleConnectionImpl> rxBleConnectionImplProvider;
            private Provider<RxBleGattCallback> rxBleGattCallbackProvider;
            private Provider serviceDiscoveryManagerProvider;
            private Provider<Boolean> suppressOperationChecksProvider;
            private ThrowingIllegalOperationHandler_Factory throwingIllegalOperationHandlerProvider;

            private ConnectionComponentImpl(ConnectionComponentBuilder connectionComponentBuilder) {
                initialize(connectionComponentBuilder);
            }

            private BleConnectionCompat getBleConnectionCompat() {
                return new BleConnectionCompat(DaggerClientComponent.this.applicationContext);
            }

            private void initialize(ConnectionComponentBuilder connectionComponentBuilder) {
                this.bluetoothGattProvider = DoubleCheck.provider(BluetoothGattProvider_Factory.create());
                this.disconnectionRouterProvider = DoubleCheck.provider(DisconnectionRouter_Factory.create(DeviceComponentImpl.this.macAddressProvider, DaggerClientComponent.this.rxBleAdapterWrapperProvider, DaggerClientComponent.this.rxBleAdapterStateObservableProvider));
                this.rxBleGattCallbackProvider = DoubleCheck.provider(RxBleGattCallback_Factory.create(DaggerClientComponent.this.provideBluetoothCallbacksSchedulerProvider, this.bluetoothGattProvider, this.disconnectionRouterProvider, NativeCallbackDispatcher_Factory.create()));
                this.autoConnect = connectionComponentBuilder.autoConnect;
                this.connectionOperationQueueImplProvider = DoubleCheck.provider(ConnectionOperationQueueImpl_Factory.create(DeviceComponentImpl.this.macAddressProvider, this.disconnectionRouterProvider, DaggerClientComponent.this.provideConnectionQueueExecutorServiceProvider, DaggerClientComponent.this.provideBluetoothInteractionSchedulerProvider));
                this.provideBluetoothGattProvider = ConnectionModule_ProvideBluetoothGattFactory.create(this.bluetoothGattProvider);
                this.loggerUtilBluetoothServicesProvider = LoggerUtilBluetoothServices_Factory.create(ConnectionModule_ProvideCharacteristicPropertiesParserFactory.create());
                this.operationTimeoutProvider = InstanceFactory.create(connectionComponentBuilder.operationTimeout);
                ConnectionModule_ProvidesOperationTimeoutConfFactory create = ConnectionModule_ProvidesOperationTimeoutConfFactory.create(ClientComponent_ClientModule_ProvideComputationSchedulerFactory.create(), this.operationTimeoutProvider);
                this.providesOperationTimeoutConfProvider = create;
                this.readRssiOperationProvider = ReadRssiOperation_Factory.create(this.rxBleGattCallbackProvider, this.provideBluetoothGattProvider, create);
                OperationsProviderImpl_Factory create2 = OperationsProviderImpl_Factory.create(this.rxBleGattCallbackProvider, this.provideBluetoothGattProvider, this.loggerUtilBluetoothServicesProvider, this.providesOperationTimeoutConfProvider, DaggerClientComponent.this.provideBluetoothInteractionSchedulerProvider, ClientComponent_ClientModule_ProvideComputationSchedulerFactory.create(), this.readRssiOperationProvider);
                this.operationsProviderImplProvider = create2;
                this.serviceDiscoveryManagerProvider = DoubleCheck.provider(ServiceDiscoveryManager_Factory.create(this.connectionOperationQueueImplProvider, this.provideBluetoothGattProvider, create2));
                this.descriptorWriterProvider = DoubleCheck.provider(DescriptorWriter_Factory.create(this.connectionOperationQueueImplProvider, this.operationsProviderImplProvider));
                this.notificationAndIndicationManagerProvider = DoubleCheck.provider(NotificationAndIndicationManager_Factory.create(ClientComponent_ClientModule_ProvideEnableNotificationValueFactory.create(), ClientComponent_ClientModule_ProvideEnableIndicationValueFactory.create(), ClientComponent_ClientModule_ProvideDisableNotificationValueFactory.create(), this.provideBluetoothGattProvider, this.rxBleGattCallbackProvider, this.descriptorWriterProvider));
                this.mtuWatcherProvider = DoubleCheck.provider(MtuWatcher_Factory.create(this.rxBleGattCallbackProvider, ConnectionModule_MinimumMtuFactory.create()));
                DelegateFactory delegateFactory = new DelegateFactory();
                this.rxBleConnectionImplProvider = delegateFactory;
                Provider provider = DoubleCheck.provider(MtuBasedPayloadSizeLimit_Factory.create(delegateFactory, ConnectionModule_GattWriteMtuOverheadFactory.create()));
                this.mtuBasedPayloadSizeLimitProvider = provider;
                this.longWriteOperationBuilderImplProvider = LongWriteOperationBuilderImpl_Factory.create(this.connectionOperationQueueImplProvider, provider, this.rxBleConnectionImplProvider, this.operationsProviderImplProvider);
                this.suppressOperationChecksProvider = InstanceFactory.create(connectionComponentBuilder.suppressOperationChecks);
                IllegalOperationMessageCreator_Factory create3 = IllegalOperationMessageCreator_Factory.create(ConnectionModule_ProvideCharacteristicPropertiesParserFactory.create());
                this.illegalOperationMessageCreatorProvider = create3;
                this.loggingIllegalOperationHandlerProvider = LoggingIllegalOperationHandler_Factory.create(create3);
                ThrowingIllegalOperationHandler_Factory create4 = ThrowingIllegalOperationHandler_Factory.create(this.illegalOperationMessageCreatorProvider);
                this.throwingIllegalOperationHandlerProvider = create4;
                ConnectionModule_ProvideIllegalOperationHandlerFactory create5 = ConnectionModule_ProvideIllegalOperationHandlerFactory.create(this.suppressOperationChecksProvider, this.loggingIllegalOperationHandlerProvider, create4);
                this.provideIllegalOperationHandlerProvider = create5;
                this.illegalOperationCheckerProvider = IllegalOperationChecker_Factory.create(create5);
                Provider<RxBleConnectionImpl> provider2 = DoubleCheck.provider(RxBleConnectionImpl_Factory.create(this.connectionOperationQueueImplProvider, this.rxBleGattCallbackProvider, this.provideBluetoothGattProvider, this.serviceDiscoveryManagerProvider, this.notificationAndIndicationManagerProvider, this.mtuWatcherProvider, this.descriptorWriterProvider, this.operationsProviderImplProvider, this.longWriteOperationBuilderImplProvider, DaggerClientComponent.this.provideBluetoothInteractionSchedulerProvider, this.illegalOperationCheckerProvider));
                this.rxBleConnectionImplProvider = provider2;
                ((DelegateFactory) this.rxBleConnectionImplProvider).setDelegatedProvider(provider2);
                this.disconnectOperationProvider = DisconnectOperation_Factory.create(this.rxBleGattCallbackProvider, this.bluetoothGattProvider, DeviceComponentImpl.this.macAddressProvider, DaggerClientComponent.this.provideBluetoothManagerProvider, DaggerClientComponent.this.provideBluetoothInteractionSchedulerProvider, DeviceComponentImpl.this.providesDisconnectTimeoutConfProvider, DeviceComponentImpl.this.provideConnectionStateChangeListenerProvider);
                this.disconnectActionProvider = DoubleCheck.provider(DisconnectAction_Factory.create(DaggerClientComponent.this.bindClientOperationQueueProvider, this.disconnectOperationProvider));
            }

            @Override // com.polidea.rxandroidble2.internal.connection.ConnectionComponent
            public ConnectOperation connectOperation() {
                return ConnectOperation_Factory.newConnectOperation(DeviceComponentImpl.this.getBluetoothDevice(), getBleConnectionCompat(), this.rxBleGattCallbackProvider.get(), this.bluetoothGattProvider.get(), DeviceComponentImpl.this.getNamedTimeoutConfiguration(), this.autoConnect.booleanValue(), (ConnectionStateChangeListener) DeviceComponentImpl.this.provideConnectionStateChangeListenerProvider.get());
            }

            @Override // com.polidea.rxandroidble2.internal.connection.ConnectionComponent
            public RxBleConnection rxBleConnection() {
                return this.rxBleConnectionImplProvider.get();
            }

            @Override // com.polidea.rxandroidble2.internal.connection.ConnectionComponent
            public RxBleGattCallback gattCallback() {
                return this.rxBleGattCallbackProvider.get();
            }

            @Override // com.polidea.rxandroidble2.internal.connection.ConnectionComponent
            public Set<ConnectionSubscriptionWatcher> connectionSubscriptionWatchers() {
                return SetBuilder.newSetBuilder(3).add((ConnectionSubscriptionWatcher) this.mtuWatcherProvider.get()).add((ConnectionSubscriptionWatcher) this.disconnectActionProvider.get()).add(this.connectionOperationQueueImplProvider.get()).build();
            }
        }
    }
}
