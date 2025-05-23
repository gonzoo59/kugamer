package com.baidu.kwgames;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.baidu.kwgames.AbsFloatBase;
import com.baidu.kwgames.Constants;
import com.baidu.kwgames.IThrone;
import com.baidu.kwgames.USB.OnUsbHidDeviceListener;
import com.baidu.kwgames.USB.UsbAOADevice;
import com.baidu.kwgames.USB.UsbDeviceBase;
import com.baidu.kwgames.USB.UsbHidDevice;
import com.baidu.kwgames.util.FloatMgr;
import com.blankj.utilcode.util.AppUtils;
import com.google.common.base.Strings;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.RxBleDeviceServices;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.PublishSubject;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.opencv.videoio.Videoio;
/* loaded from: classes.dex */
public class FloatWindowService extends Service {
    public static final String ACTION_KEP_MAPPING_DISPLAY = "action_key_mapping_display";
    public static final String ACTION_KEP_MAPPING_HIDE_DISPLAY = "action_key_mapping_hide_display";
    public static final String ACTION_KEY_MAPPING_SETTING = "action_key_mapping_setting";
    public static final String ACTION_KILL = "action_kill";
    public static final int MANAGER_NOTIFICATION_ID = 4097;
    private static final String NOTIFICATION_CHANNEL_ID = "FloatWindowService";
    public static final String TAG = "FloatWindowService";
    public static final int USB_READ_PER_PACKET = 4096;
    public static final int USB_READ_TIMEOUT = 500;
    private RxBleDevice mBleDevice;
    private KeyMappingDisplay mKeyMappingDisplay;
    private KeyMappingSetting mKeyMappingSetting;
    private RxBleConnection mRxBleConnection;
    private Disposable systemStatusSubscribe;
    public static final byte[] m_arrGetSystemStatus = {-1, -124, 1, 0, 0};
    public static final byte[] m_arrDisableTouch = {-1, -125, 1, 0, 1};
    public static final byte[] m_arrGetGunPressAI = {-1, -118, 1, 0, 0};
    public static final byte[] m_arrSetAIOff = {-1, -114, 1, 0, 1};
    final RemoteCallbackList<IBleCallback> mBleCallbacks = new RemoteCallbackList<>();
    private final PublishSubject<Boolean> mDisconnectTriggerSubject = PublishSubject.create();
    private final PublishSubject<Integer> mParseTriggerSubject = PublishSubject.create();
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private final ThroneRingBuffer mBuffer = new ThroneRingBuffer(4096);
    private final ThroneRingBuffer m_bufUSBRead = new ThroneRingBuffer(4096);
    private final RingArray m_arrUsbWriteBuf = new RingArray(50);
    private ConnectableObservable<RxBleDeviceServices> mRxBleDeviceServicesObservable = null;
    private Disposable mDisableTouchDisposable = null;
    private int mPreSetKeyIndex = 0;
    private boolean canCapture = false;
    private UUID m_uuidOut = UUID.fromString("0000fc01-0000-1000-8000-00805f9b34fb");
    private UUID m_uuidIn = UUID.fromString("0000fc02-0000-1000-8000-00805f9b34fb");
    byte[] m_arrAIState = {-1, -116, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    byte[] m_arrAIStateM4 = {-1, -106, 33, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    byte[] m_arrChangeMode = {-1, -121, Units.LOBYTE(10), 0, 0, 0, Units.LOBYTE(AppInstance.s_nScreenW), Units.HIBYTE(AppInstance.s_nScreenW), Units.LOBYTE(AppInstance.s_nScreenH), Units.HIBYTE(AppInstance.s_nScreenH), Units.LOBYTE(AppInstance.s_nToupingDisplayX), Units.HIBYTE(AppInstance.s_nToupingDisplayX), Units.LOBYTE(AppInstance.s_nToupingDisplayY), Units.HIBYTE(AppInstance.s_nToupingDisplayY), 1, 0, 0, 0, 0, 0, 0, 0, 0};
    IThrone.Stub mIBinder = new IThrone.Stub() { // from class: com.baidu.kwgames.FloatWindowService.1
        @Override // com.baidu.kwgames.IThrone
        public void setSystemStatusTask(boolean z) throws RemoteException {
            FloatWindowService.this._setSystemStatusTask(z);
        }

        @Override // com.baidu.kwgames.IThrone
        public void setInMainUI(boolean z) {
            if (FloatWindowService.this.mKeyMappingSetting == null) {
                FloatWindowService.this.enable_touch(!z);
            }
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleConnect(String str) throws RemoteException {
            FloatWindowService.this._bleConnect(str);
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleDisConnect() throws RemoteException {
            FloatWindowService.this._bleDisConnect();
        }

        @Override // com.baidu.kwgames.IThrone
        public boolean usbConnect() {
            return FloatWindowService.this.check_and_connect_usb();
        }

        @Override // com.baidu.kwgames.IThrone
        public void usbDisconnect() {
            FloatWindowService.this._usbDisconnect();
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleSetSystemStatus(byte[] bArr) throws RemoteException {
            if (AppInstance.s_boUSBConnected) {
                AppInstance.s_usb.write(bArr, bArr.length, 2000);
            } else if (AppInstance.mBleConnected) {
                bleSendLongData(bArr);
            }
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleGetKeyMap(int i, int i2, int i3, int i4, int i5) throws RemoteException {
            FloatWindowService.this.GetKeyMap(i, i2, i3, i4, i5);
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleGetGunPressAI() throws RemoteException {
            if (AppInstance.s_boUSBConnected) {
                AppInstance.s_usb.write(FloatWindowService.m_arrGetGunPressAI, FloatWindowService.m_arrGetGunPressAI.length, 2000);
            } else if (AppInstance.mBleConnected) {
                FloatWindowService.this._bleGetGunPressAI();
            }
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleSetAIState(byte b, byte b2, byte b3, int i, byte[] bArr) throws RemoteException {
            FloatWindowService.this.m_arrAIState[4] = b;
            FloatWindowService.this.m_arrAIState[5] = b2;
            FloatWindowService.this.m_arrAIState[6] = b3;
            FloatWindowService.this.m_arrAIState[7] = (byte) (i & 255);
            FloatWindowService.this.m_arrAIState[8] = (byte) ((i >> 8) & 255);
            FloatWindowService.this.m_arrAIState[9] = bArr[0];
            FloatWindowService.this.m_arrAIState[10] = bArr[1];
            FloatWindowService.this.m_arrAIState[11] = bArr[2];
            FloatWindowService.this.m_arrAIState[12] = bArr[3];
            FloatWindowService.this.m_arrAIState[13] = bArr[4];
            FloatWindowService.this.m_arrAIState[14] = bArr[5];
            FloatWindowService.this.m_arrAIState[15] = bArr[6];
            FloatWindowService.this.m_arrAIState[16] = bArr[7];
            if (AppInstance.s_boUSBConnected) {
                AppInstance.s_usb.write(FloatWindowService.this.m_arrAIState, FloatWindowService.this.m_arrAIState.length, 2000);
            } else if (AppInstance.mBleConnected) {
                FloatWindowService.this._bleSetAIState();
            }
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleSetAIStateM4(byte b, byte b2, byte b3, int i, byte b4, byte b5, byte[] bArr, byte[] bArr2) throws RemoteException {
            FloatWindowService.this.m_arrAIStateM4[4] = b;
            FloatWindowService.this.m_arrAIStateM4[5] = b2;
            FloatWindowService.this.m_arrAIStateM4[6] = b3;
            FloatWindowService.this.m_arrAIStateM4[7] = (byte) (i & 255);
            FloatWindowService.this.m_arrAIStateM4[8] = (byte) ((i >> 8) & 255);
            FloatWindowService.this.m_arrAIStateM4[9] = b4;
            FloatWindowService.this.m_arrAIStateM4[10] = b5;
            for (int i2 = 0; i2 < 12; i2++) {
                FloatWindowService.this.m_arrAIStateM4[i2 + 14] = bArr[i2];
                FloatWindowService.this.m_arrAIStateM4[i2 + 26] = bArr2[i2];
            }
            if (AppInstance.s_boUSBConnected) {
                AppInstance.s_usb.write(FloatWindowService.this.m_arrAIStateM4, FloatWindowService.this.m_arrAIStateM4.length, 2000);
            } else if (AppInstance.mBleConnected) {
                FloatWindowService floatWindowService = FloatWindowService.this;
                floatWindowService._bleSendLongData(floatWindowService.m_arrAIStateM4);
            }
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleChangeMode(byte b, byte b2, byte b3, String str) throws RemoteException {
            FloatWindowService.this.m_arrChangeMode[0] = -1;
            FloatWindowService.this.m_arrChangeMode[1] = -121;
            FloatWindowService.this.m_arrChangeMode[2] = Units.LOBYTE(FloatWindowService.this.m_arrChangeMode.length - 4);
            FloatWindowService.this.m_arrChangeMode[3] = 0;
            FloatWindowService.this.m_arrChangeMode[4] = b;
            FloatWindowService.this.m_arrChangeMode[5] = b2;
            FloatWindowService.this.m_arrChangeMode[6] = Units.LOBYTE(AppInstance.s_nScreenW);
            FloatWindowService.this.m_arrChangeMode[7] = Units.HIBYTE(AppInstance.s_nScreenW);
            FloatWindowService.this.m_arrChangeMode[8] = Units.LOBYTE(AppInstance.s_nScreenH);
            FloatWindowService.this.m_arrChangeMode[9] = Units.HIBYTE(AppInstance.s_nScreenH);
            FloatWindowService.this.m_arrChangeMode[10] = Units.LOBYTE(AppInstance.s_nToupingDisplayX);
            FloatWindowService.this.m_arrChangeMode[11] = Units.HIBYTE(AppInstance.s_nToupingDisplayX);
            FloatWindowService.this.m_arrChangeMode[12] = Units.LOBYTE(AppInstance.s_nToupingDisplayY);
            FloatWindowService.this.m_arrChangeMode[13] = Units.HIBYTE(AppInstance.s_nToupingDisplayY);
            FloatWindowService.this.m_arrChangeMode[14] = b3;
            if (str == null || str.isEmpty()) {
                FloatWindowService.this.m_arrChangeMode[15] = 0;
            } else {
                int min = Math.min(8, str.length());
                for (int i = 0; i < min; i++) {
                    FloatWindowService.this.m_arrChangeMode[i + 15] = (byte) str.charAt(i);
                }
            }
            if (AppInstance.s_boUSBConnected) {
                AppInstance.s_usb.write(FloatWindowService.this.m_arrChangeMode, FloatWindowService.this.m_arrChangeMode.length, 2000);
            } else if (AppInstance.mBleConnected) {
                bleSendLongData(FloatWindowService.this.m_arrChangeMode);
            }
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleSetAIOff() throws RemoteException {
            if (AppInstance.s_boUSBConnected) {
                AppInstance.s_usb.write(FloatWindowService.m_arrSetAIOff, FloatWindowService.m_arrSetAIOff.length, 2000);
            } else if (AppInstance.mBleConnected) {
                FloatWindowService.this._bleSetAIOff();
            }
        }

        @Override // com.baidu.kwgames.IThrone
        public void addBleCallback(IBleCallback iBleCallback) throws RemoteException {
            if (iBleCallback != null) {
                FloatWindowService.this.mBleCallbacks.register(iBleCallback);
            }
        }

        @Override // com.baidu.kwgames.IThrone
        public void removeBleCallback(IBleCallback iBleCallback) throws RemoteException {
            if (iBleCallback != null) {
                FloatWindowService.this.mBleCallbacks.unregister(iBleCallback);
            }
        }

        @Override // com.baidu.kwgames.IThrone
        public boolean isLandscape() throws RemoteException {
            return FloatWindowService.this.canCapture;
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleSendKeyMap(byte[] bArr, int i) throws RemoteException {
            FloatWindowService.this.SendKeyMap(bArr, i);
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleSendLongData(byte[] bArr) throws RemoteException {
            if (AppInstance.s_boUSBConnected) {
                AppInstance.s_usb.write(bArr, bArr.length, 2000);
            } else if (AppInstance.mBleConnected) {
                FloatWindowService.this._bleSendLongData(bArr);
            }
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleSendShortData(byte[] bArr) throws RemoteException {
            if (AppInstance.s_boUSBConnected) {
                AppInstance.s_usb.write(bArr, bArr.length, 2000);
            } else if (AppInstance.mBleConnected) {
                FloatWindowService.this._bleSendShortData(bArr);
            }
        }
    };
    private Thread m_threadUSBRead = null;
    public Handler m_timerHandler = new Handler();
    public Runnable m_runableUSBGetSystemStatus = new Runnable() { // from class: com.baidu.kwgames.FloatWindowService.3
        @Override // java.lang.Runnable
        public void run() {
            if (FloatWindowService.this.m_boUSBGetSystemStatusTimerOnOff) {
                AppInstance.s_usb.write(FloatWindowService.m_arrGetSystemStatus, FloatWindowService.m_arrGetSystemStatus.length, 3000);
                FloatWindowService.this.m_timerHandler.postDelayed(FloatWindowService.this.m_runableUSBGetSystemStatus, 2000L);
            }
        }
    };
    public Runnable m_runableUSBDisableTouch = new Runnable() { // from class: com.baidu.kwgames.FloatWindowService.4
        @Override // java.lang.Runnable
        public void run() {
            Log.d("Service", "m_runableUSBDisableTouch=" + FloatWindowService.this.m_boUSBDisableTouchTimerOnOff);
            if (!FloatWindowService.this.m_boUSBDisableTouchTimerOnOff || AppInstance.s_usb == null) {
                return;
            }
            AppInstance.s_usb.write(FloatWindowService.m_arrDisableTouch, FloatWindowService.m_arrDisableTouch.length, 3000);
            FloatWindowService.this.m_timerHandler.postDelayed(FloatWindowService.this.m_runableUSBDisableTouch, 600L);
        }
    };
    OnUsbHidDeviceListener m_hidListener = new OnUsbHidDeviceListener() { // from class: com.baidu.kwgames.FloatWindowService.5
        @Override // com.baidu.kwgames.USB.OnUsbHidDeviceListener
        public void onUsbHidDeviceConnected(UsbDeviceBase usbDeviceBase) {
            AppInstance.s_boUSBConnected = true;
            FloatWindowService.this.begin_usb_read_write_thread();
            FloatWindowService.this.callbackDevConnectChanged(true);
        }

        @Override // com.baidu.kwgames.USB.OnUsbHidDeviceListener
        public void onUsbHidDeviceConnectFailed(UsbDeviceBase usbDeviceBase) {
            AppInstance.s_boUSBConnected = false;
            FloatWindowService.this.callbackDevConnectChanged(false);
        }
    };
    private boolean m_boUSBGetSystemStatusTimerOnOff = false;
    private boolean m_boUSBDisableTouchTimerOnOff = false;

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] get_1byte_cmd(byte b, byte b2) {
        return new byte[]{-1, b, 1, 0, b2};
    }

    private byte[] get_2byte_cmd(byte b, byte b2, byte b3) {
        return new byte[]{-1, b, 2, 0, b2, b3};
    }

    private byte[] get_3byte_cmd(byte b, byte b2, byte b3, byte b4) {
        return new byte[]{-1, b, 3, 0, b2, b3, b4};
    }

    private byte[] get_4byte_cmd(byte b, byte b2, byte b3, byte b4, byte b5) {
        return new byte[]{-1, b, 4, 0, b2, b3, b4, b5};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] get_5byte_cmd(byte b, byte b2, byte b3, byte b4, byte b5, byte b6) {
        return new byte[]{-1, b, 5, 0, b2, b3, b4, b5, b6};
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ ObservableSource lambda$bleReceive$0(Observable observable) throws Exception {
        return observable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$parsePackage$2(Throwable th) throws Exception {
    }

    public void enable_touch(boolean z) {
        if (AppInstance.s_boUSBConnected) {
            if (z) {
                stop_usb_disable_touch_timer();
            } else {
                begin_usb_disable_touch_timer();
            }
        } else if (AppInstance.mBleConnected) {
            if (z) {
                bleEnableTouch();
            } else {
                bleDisableTouch();
            }
        }
    }

    private void stop_usb_read_write_thread() {
        Thread thread = this.m_threadUSBRead;
        if (thread == null || !thread.isAlive()) {
            return;
        }
        this.m_threadUSBRead.interrupt();
        this.m_threadUSBRead = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void begin_usb_read_write_thread() {
        if (Build.VERSION.SDK_INT >= 19) {
            Thread thread = this.m_threadUSBRead;
            if (thread != null && thread.isAlive()) {
                this.m_threadUSBRead.interrupt();
            }
            Thread thread2 = new Thread(new Runnable() { // from class: com.baidu.kwgames.FloatWindowService.2
                @Override // java.lang.Runnable
                public void run() {
                    FloatWindowService.this.USB_READ_THREAD_FUNC();
                }
            }, "USB-I");
            this.m_threadUSBRead = thread2;
            thread2.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void USB_READ_THREAD_FUNC() {
        try {
            byte[] bArr = new byte[4096];
            while (AppInstance.s_boUSBConnected) {
                int read = AppInstance.s_usb.read(bArr, 4096, 500);
                if (read > 0) {
                    this.m_bufUSBRead.write(bArr, read);
                    byte[] nextPackage = this.m_bufUSBRead.getNextPackage();
                    while (nextPackage != null) {
                        m10lambda$parsePackage$1$combaidukwgamesFloatWindowService(nextPackage);
                        nextPackage = this.m_bufUSBRead.getNextPackage();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean check_and_connect_usb() {
        AppInstance.s_usb = UsbHidDevice.factory(AppInstance.s_context);
        if (AppInstance.s_usb != null) {
            AppInstance.s_usb.open(AppInstance.s_context, this.m_hidListener);
            return true;
        }
        AppInstance.s_usb = UsbAOADevice.factory(AppInstance.s_context);
        if (AppInstance.s_usb != null) {
            AppInstance.s_usb.open(AppInstance.s_context, this.m_hidListener);
            return true;
        }
        return false;
    }

    void _usbDisconnect() {
        AppInstance.s_boUSBConnected = false;
        if (AppInstance.s_usb != null) {
            stop_usb_read_write_thread();
            stop_usb_disable_touch_timer();
            stop_usb_get_system_status_timer();
            AppInstance.s_usb.close();
            AppInstance.s_usb = null;
            callbackDevConnectChanged(false);
        }
    }

    private void stop_usb_get_system_status_timer() {
        this.m_boUSBGetSystemStatusTimerOnOff = false;
        this.m_timerHandler.removeCallbacks(this.m_runableUSBGetSystemStatus);
    }

    private void begin_usb_get_system_status_timer() {
        if (AppInstance.s_boUSBConnected) {
            this.m_boUSBGetSystemStatusTimerOnOff = true;
            this.m_timerHandler.removeCallbacks(this.m_runableUSBGetSystemStatus);
            this.m_timerHandler.postDelayed(this.m_runableUSBGetSystemStatus, 0L);
        }
    }

    private void stop_usb_disable_touch_timer() {
        this.m_boUSBDisableTouchTimerOnOff = false;
        this.m_timerHandler.removeCallbacks(this.m_runableUSBDisableTouch);
    }

    private void begin_usb_disable_touch_timer() {
        if (AppInstance.s_boUSBConnected) {
            this.m_boUSBDisableTouchTimerOnOff = true;
            this.m_timerHandler.removeCallbacks(this.m_runableUSBDisableTouch);
            this.m_timerHandler.postDelayed(this.m_runableUSBDisableTouch, 600L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _bleConnect(String str) {
        if (this.mRxBleDeviceServicesObservable != null) {
            _bleDisConnect();
        }
        RxBleDevice bleDevice = App.getRxBleClient(this).getBleDevice(str);
        this.mBleDevice = bleDevice;
        ConnectableObservable<RxBleDeviceServices> replay = bleDevice.establishConnection(false).takeUntil(this.mDisconnectTriggerSubject).flatMapSingle(new Function<RxBleConnection, SingleSource<? extends RxBleDeviceServices>>() { // from class: com.baidu.kwgames.FloatWindowService.6
            @Override // io.reactivex.functions.Function
            public SingleSource<? extends RxBleDeviceServices> apply(RxBleConnection rxBleConnection) throws Exception {
                FloatWindowService.this.mRxBleConnection = rxBleConnection;
                return rxBleConnection.discoverServices();
            }
        }).replay(1);
        this.mRxBleDeviceServicesObservable = replay;
        replay.connect();
        this.mCompositeDisposable.add(this.mBleDevice.observeConnectionStateChanges().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.baidu.kwgames.FloatWindowService$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FloatWindowService.this.callbackBleConnectState((RxBleConnection.RxBleConnectionState) obj);
            }
        }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.FloatWindowService.7
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                Log.d("FloatWindowService", "accept: " + th);
            }
        }));
        parsePackage();
        bleReceive();
        bleGetSystemStatus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _bleDisConnect() {
        Disposable disposable = this.systemStatusSubscribe;
        if (disposable != null) {
            disposable.dispose();
        }
        bleEnableTouch();
        this.mCompositeDisposable.clear();
        this.mDisconnectTriggerSubject.onNext(true);
        this.mPreSetKeyIndex = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _setSystemStatusTask(boolean z) {
        if (z) {
            if (AppInstance.s_boUSBConnected) {
                begin_usb_get_system_status_timer();
            } else if (AppInstance.mBleConnected) {
                bleGetSystemStatus();
            }
        } else {
            Disposable disposable = this.systemStatusSubscribe;
            if (disposable != null) {
                disposable.dispose();
            }
            stop_usb_get_system_status_timer();
        }
        hideKeyMappingDisplay();
    }

    private void _bleSetSystemStatus(final byte[] bArr) {
        this.mCompositeDisposable.add(this.mRxBleDeviceServicesObservable.flatMapSingle(new Function<RxBleDeviceServices, SingleSource<BluetoothGattCharacteristic>>() { // from class: com.baidu.kwgames.FloatWindowService.11
            @Override // io.reactivex.functions.Function
            public SingleSource<BluetoothGattCharacteristic> apply(RxBleDeviceServices rxBleDeviceServices) throws Exception {
                return rxBleDeviceServices.getCharacteristic(FloatWindowService.this.m_uuidOut);
            }
        }).flatMapSingle(new Function<BluetoothGattCharacteristic, SingleSource<byte[]>>() { // from class: com.baidu.kwgames.FloatWindowService.10
            @Override // io.reactivex.functions.Function
            public SingleSource<byte[]> apply(BluetoothGattCharacteristic bluetoothGattCharacteristic) throws Exception {
                return FloatWindowService.this.mRxBleConnection.writeCharacteristic(bluetoothGattCharacteristic, bArr);
            }
        }).subscribe(new Consumer<byte[]>() { // from class: com.baidu.kwgames.FloatWindowService.8
            @Override // io.reactivex.functions.Consumer
            public void accept(byte[] bArr2) throws Exception {
                Log.d("FloatWindowService", "_bleSetSystemStatus: " + Units.bytes2HexStr(bArr2));
            }
        }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.FloatWindowService.9
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                Log.d("FloatWindowService", "_bleSetSystemStatus: " + th);
            }
        }));
    }

    private void _bleSetKey(byte[] bArr, int i) {
        final byte[] bArr2 = new byte[bArr.length + 4 + 5];
        bArr2[0] = -1;
        bArr2[1] = Byte.MIN_VALUE;
        bArr2[2] = Units.LOBYTE(bArr.length);
        bArr2[3] = Units.HIBYTE(bArr.length);
        System.arraycopy(bArr, 0, bArr2, 4, bArr.length);
        int length = bArr.length + 4;
        bArr2[length] = -1;
        bArr2[length + 1] = -126;
        bArr2[length + 2] = 1;
        bArr2[length + 3] = 0;
        bArr2[length + 4] = (byte) i;
        this.mCompositeDisposable.add(this.mRxBleDeviceServicesObservable.flatMap(new Function<RxBleDeviceServices, ObservableSource<? extends byte[]>>() { // from class: com.baidu.kwgames.FloatWindowService.14
            @Override // io.reactivex.functions.Function
            public ObservableSource<? extends byte[]> apply(RxBleDeviceServices rxBleDeviceServices) throws Exception {
                return FloatWindowService.this.mRxBleConnection.createNewLongWriteBuilder().setBytes(bArr2).setCharacteristicUuid(FloatWindowService.this.m_uuidOut).build();
            }
        }).subscribe(new Consumer<byte[]>() { // from class: com.baidu.kwgames.FloatWindowService.12
            @Override // io.reactivex.functions.Consumer
            public void accept(byte[] bArr3) throws Exception {
                Log.d("FloatWindowService", "_bleSetKey: Write=" + bArr3.length);
            }
        }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.FloatWindowService.13
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                Log.d("FloatWindowService", "_bleSetKey: " + th);
            }
        }));
    }

    private void bleGetKeys(final int i, final int i2, final int i3, final int i4, final int i5) {
        this.mCompositeDisposable.add(this.mRxBleDeviceServicesObservable.flatMapSingle(new Function<RxBleDeviceServices, SingleSource<BluetoothGattCharacteristic>>() { // from class: com.baidu.kwgames.FloatWindowService.18
            @Override // io.reactivex.functions.Function
            public SingleSource<BluetoothGattCharacteristic> apply(RxBleDeviceServices rxBleDeviceServices) throws Exception {
                return rxBleDeviceServices.getCharacteristic(FloatWindowService.this.m_uuidOut);
            }
        }).flatMapSingle(new Function<BluetoothGattCharacteristic, SingleSource<byte[]>>() { // from class: com.baidu.kwgames.FloatWindowService.17
            @Override // io.reactivex.functions.Function
            public SingleSource<byte[]> apply(BluetoothGattCharacteristic bluetoothGattCharacteristic) throws Exception {
                return FloatWindowService.this.mRxBleConnection.writeCharacteristic(bluetoothGattCharacteristic, FloatWindowService.this.get_5byte_cmd((byte) -127, (byte) i, (byte) i2, (byte) i3, (byte) i4, (byte) i5));
            }
        }).subscribe(new Consumer<byte[]>() { // from class: com.baidu.kwgames.FloatWindowService.15
            @Override // io.reactivex.functions.Consumer
            public void accept(byte[] bArr) throws Exception {
                Log.d("FloatWindowService", "bleGetKeys: " + Units.bytes2HexStr(bArr));
            }
        }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.FloatWindowService.16
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                Log.d("FloatWindowService", "bleGetKeys: " + th);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _bleGetGunPressAI() {
        this.mCompositeDisposable.add(this.mRxBleDeviceServicesObservable.flatMapSingle(new Function<RxBleDeviceServices, SingleSource<BluetoothGattCharacteristic>>() { // from class: com.baidu.kwgames.FloatWindowService.22
            @Override // io.reactivex.functions.Function
            public SingleSource<BluetoothGattCharacteristic> apply(RxBleDeviceServices rxBleDeviceServices) throws Exception {
                return rxBleDeviceServices.getCharacteristic(FloatWindowService.this.m_uuidOut);
            }
        }).flatMapSingle(new Function<BluetoothGattCharacteristic, SingleSource<byte[]>>() { // from class: com.baidu.kwgames.FloatWindowService.21
            @Override // io.reactivex.functions.Function
            public SingleSource<byte[]> apply(BluetoothGattCharacteristic bluetoothGattCharacteristic) throws Exception {
                return FloatWindowService.this.mRxBleConnection.writeCharacteristic(bluetoothGattCharacteristic, FloatWindowService.m_arrGetGunPressAI);
            }
        }).subscribe(new Consumer<byte[]>() { // from class: com.baidu.kwgames.FloatWindowService.19
            @Override // io.reactivex.functions.Consumer
            public void accept(byte[] bArr) throws Exception {
                Log.d("FloatWindowService", "bleGetGunPressAI: " + Units.bytes2HexStr(bArr));
            }
        }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.FloatWindowService.20
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                Log.d("FloatWindowService", "bleGetGunPressAI: " + th);
            }
        }));
    }

    private void bleGetSystemStatus() {
        Disposable disposable = this.systemStatusSubscribe;
        if (disposable != null) {
            disposable.dispose();
        }
        this.systemStatusSubscribe = Observable.combineLatest(Observable.interval(0L, 2L, TimeUnit.SECONDS), this.mRxBleDeviceServicesObservable, new BiFunction<Long, RxBleDeviceServices, RxBleDeviceServices>() { // from class: com.baidu.kwgames.FloatWindowService.27
            @Override // io.reactivex.functions.BiFunction
            public RxBleDeviceServices apply(Long l, RxBleDeviceServices rxBleDeviceServices) throws Exception {
                return rxBleDeviceServices;
            }
        }).flatMapSingle(new Function<RxBleDeviceServices, SingleSource<BluetoothGattCharacteristic>>() { // from class: com.baidu.kwgames.FloatWindowService.26
            @Override // io.reactivex.functions.Function
            public SingleSource<BluetoothGattCharacteristic> apply(RxBleDeviceServices rxBleDeviceServices) throws Exception {
                return rxBleDeviceServices.getCharacteristic(FloatWindowService.this.m_uuidOut);
            }
        }).flatMapSingle(new Function<BluetoothGattCharacteristic, SingleSource<byte[]>>() { // from class: com.baidu.kwgames.FloatWindowService.25
            @Override // io.reactivex.functions.Function
            public SingleSource<byte[]> apply(BluetoothGattCharacteristic bluetoothGattCharacteristic) throws Exception {
                return FloatWindowService.this.mRxBleConnection.writeCharacteristic(bluetoothGattCharacteristic, FloatWindowService.this.get_1byte_cmd((byte) -124, (byte) 0));
            }
        }).subscribe(new Consumer<byte[]>() { // from class: com.baidu.kwgames.FloatWindowService.23
            @Override // io.reactivex.functions.Consumer
            public void accept(byte[] bArr) throws Exception {
                Log.d("FloatWindowService", "bleGetSystemStatus: " + Units.bytes2HexStr(bArr));
            }
        }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.FloatWindowService.24
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                Log.d("FloatWindowService", "bleGetSystemStatus: " + th);
            }
        });
    }

    private void bleDisableTouch() {
        if (this.mDisableTouchDisposable == null) {
            this.mDisableTouchDisposable = Observable.combineLatest(Observable.interval(0L, 600L, TimeUnit.MILLISECONDS), this.mRxBleDeviceServicesObservable, new BiFunction<Long, RxBleDeviceServices, RxBleDeviceServices>() { // from class: com.baidu.kwgames.FloatWindowService.32
                @Override // io.reactivex.functions.BiFunction
                public RxBleDeviceServices apply(Long l, RxBleDeviceServices rxBleDeviceServices) throws Exception {
                    return rxBleDeviceServices;
                }
            }).flatMapSingle(new Function<RxBleDeviceServices, SingleSource<BluetoothGattCharacteristic>>() { // from class: com.baidu.kwgames.FloatWindowService.31
                @Override // io.reactivex.functions.Function
                public SingleSource<BluetoothGattCharacteristic> apply(RxBleDeviceServices rxBleDeviceServices) throws Exception {
                    return rxBleDeviceServices.getCharacteristic(FloatWindowService.this.m_uuidOut);
                }
            }).flatMapSingle(new Function<BluetoothGattCharacteristic, SingleSource<byte[]>>() { // from class: com.baidu.kwgames.FloatWindowService.30
                @Override // io.reactivex.functions.Function
                public SingleSource<byte[]> apply(BluetoothGattCharacteristic bluetoothGattCharacteristic) throws Exception {
                    return FloatWindowService.this.mRxBleConnection.writeCharacteristic(bluetoothGattCharacteristic, FloatWindowService.this.get_1byte_cmd((byte) -125, (byte) 1));
                }
            }).subscribe(new Consumer<byte[]>() { // from class: com.baidu.kwgames.FloatWindowService.28
                @Override // io.reactivex.functions.Consumer
                public void accept(byte[] bArr) throws Exception {
                    Log.d("FloatWindowService", "disableTouch: ");
                }
            }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.FloatWindowService.29
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable th) throws Exception {
                    Log.d("FloatWindowService", "disableTouch: " + th);
                }
            });
        }
    }

    private void bleEnableTouch() {
        Disposable disposable = this.mDisableTouchDisposable;
        if (disposable != null) {
            disposable.dispose();
            this.mDisableTouchDisposable = null;
        }
    }

    public void _bleSetAIState() {
        this.mCompositeDisposable.add(this.mRxBleDeviceServicesObservable.flatMapSingle(new Function<RxBleDeviceServices, SingleSource<BluetoothGattCharacteristic>>() { // from class: com.baidu.kwgames.FloatWindowService.36
            @Override // io.reactivex.functions.Function
            public SingleSource<BluetoothGattCharacteristic> apply(RxBleDeviceServices rxBleDeviceServices) throws Exception {
                return rxBleDeviceServices.getCharacteristic(FloatWindowService.this.m_uuidOut);
            }
        }).flatMapSingle(new Function<BluetoothGattCharacteristic, SingleSource<byte[]>>() { // from class: com.baidu.kwgames.FloatWindowService.35
            @Override // io.reactivex.functions.Function
            public SingleSource<byte[]> apply(BluetoothGattCharacteristic bluetoothGattCharacteristic) throws Exception {
                return FloatWindowService.this.mRxBleConnection.writeCharacteristic(bluetoothGattCharacteristic, FloatWindowService.this.m_arrAIState);
            }
        }).subscribe(new Consumer<byte[]>() { // from class: com.baidu.kwgames.FloatWindowService.33
            @Override // io.reactivex.functions.Consumer
            public void accept(byte[] bArr) throws Exception {
            }
        }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.FloatWindowService.34
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
            }
        }));
    }

    public void _bleChangeMode() throws RemoteException {
        this.mCompositeDisposable.add(this.mRxBleDeviceServicesObservable.flatMapSingle(new Function<RxBleDeviceServices, SingleSource<BluetoothGattCharacteristic>>() { // from class: com.baidu.kwgames.FloatWindowService.40
            @Override // io.reactivex.functions.Function
            public SingleSource<BluetoothGattCharacteristic> apply(RxBleDeviceServices rxBleDeviceServices) throws Exception {
                return rxBleDeviceServices.getCharacteristic(FloatWindowService.this.m_uuidOut);
            }
        }).flatMapSingle(new Function<BluetoothGattCharacteristic, SingleSource<byte[]>>() { // from class: com.baidu.kwgames.FloatWindowService.39
            @Override // io.reactivex.functions.Function
            public SingleSource<byte[]> apply(BluetoothGattCharacteristic bluetoothGattCharacteristic) throws Exception {
                return FloatWindowService.this.mRxBleConnection.writeCharacteristic(bluetoothGattCharacteristic, FloatWindowService.this.m_arrChangeMode);
            }
        }).subscribe(new Consumer<byte[]>() { // from class: com.baidu.kwgames.FloatWindowService.37
            @Override // io.reactivex.functions.Consumer
            public void accept(byte[] bArr) throws Exception {
                Log.d("FloatWindowService", "_bleSetAIState: " + Units.bytes2HexStr(bArr));
            }
        }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.FloatWindowService.38
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                Log.d("FloatWindowService", "_bleSetAIState: " + th);
            }
        }));
    }

    public void _bleSetAIOff() {
        this.mCompositeDisposable.add(this.mRxBleDeviceServicesObservable.flatMapSingle(new Function<RxBleDeviceServices, SingleSource<BluetoothGattCharacteristic>>() { // from class: com.baidu.kwgames.FloatWindowService.44
            @Override // io.reactivex.functions.Function
            public SingleSource<BluetoothGattCharacteristic> apply(RxBleDeviceServices rxBleDeviceServices) throws Exception {
                return rxBleDeviceServices.getCharacteristic(FloatWindowService.this.m_uuidOut);
            }
        }).flatMapSingle(new Function<BluetoothGattCharacteristic, SingleSource<byte[]>>() { // from class: com.baidu.kwgames.FloatWindowService.43
            @Override // io.reactivex.functions.Function
            public SingleSource<byte[]> apply(BluetoothGattCharacteristic bluetoothGattCharacteristic) throws Exception {
                return FloatWindowService.this.mRxBleConnection.writeCharacteristic(bluetoothGattCharacteristic, FloatWindowService.this.get_1byte_cmd((byte) -114, (byte) 1));
            }
        }).subscribe(new Consumer<byte[]>() { // from class: com.baidu.kwgames.FloatWindowService.41
            @Override // io.reactivex.functions.Consumer
            public void accept(byte[] bArr) throws Exception {
                Log.d("FloatWindowService", "bleGetKeys: " + Units.bytes2HexStr(bArr));
            }
        }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.FloatWindowService.42
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                Log.d("FloatWindowService", "bleSetAIOff: " + th);
            }
        }));
    }

    public void _bleSendLongData(final byte[] bArr) {
        this.mCompositeDisposable.add(this.mRxBleDeviceServicesObservable.flatMap(new Function<RxBleDeviceServices, ObservableSource<? extends byte[]>>() { // from class: com.baidu.kwgames.FloatWindowService.47
            @Override // io.reactivex.functions.Function
            public ObservableSource<? extends byte[]> apply(RxBleDeviceServices rxBleDeviceServices) throws Exception {
                return FloatWindowService.this.mRxBleConnection.createNewLongWriteBuilder().setBytes(bArr).setCharacteristicUuid(FloatWindowService.this.m_uuidOut).build();
            }
        }).subscribe(new Consumer<byte[]>() { // from class: com.baidu.kwgames.FloatWindowService.45
            @Override // io.reactivex.functions.Consumer
            public void accept(byte[] bArr2) throws Exception {
            }
        }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.FloatWindowService.46
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
            }
        }));
    }

    public void _bleSendShortData(final byte[] bArr) {
        this.mCompositeDisposable.add(this.mRxBleDeviceServicesObservable.flatMapSingle(new Function<RxBleDeviceServices, SingleSource<BluetoothGattCharacteristic>>() { // from class: com.baidu.kwgames.FloatWindowService.51
            @Override // io.reactivex.functions.Function
            public SingleSource<BluetoothGattCharacteristic> apply(RxBleDeviceServices rxBleDeviceServices) throws Exception {
                return rxBleDeviceServices.getCharacteristic(FloatWindowService.this.m_uuidOut);
            }
        }).flatMapSingle(new Function<BluetoothGattCharacteristic, SingleSource<byte[]>>() { // from class: com.baidu.kwgames.FloatWindowService.50
            @Override // io.reactivex.functions.Function
            public SingleSource<byte[]> apply(BluetoothGattCharacteristic bluetoothGattCharacteristic) throws Exception {
                return FloatWindowService.this.mRxBleConnection.writeCharacteristic(bluetoothGattCharacteristic, bArr);
            }
        }).subscribe(new Consumer<byte[]>() { // from class: com.baidu.kwgames.FloatWindowService.48
            @Override // io.reactivex.functions.Consumer
            public void accept(byte[] bArr2) throws Exception {
            }
        }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.FloatWindowService.49
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
            }
        }));
    }

    private void bleReceive() {
        this.mCompositeDisposable.add(this.mRxBleDeviceServicesObservable.flatMapSingle(new Function<RxBleDeviceServices, SingleSource<BluetoothGattCharacteristic>>() { // from class: com.baidu.kwgames.FloatWindowService.55
            @Override // io.reactivex.functions.Function
            public SingleSource<BluetoothGattCharacteristic> apply(RxBleDeviceServices rxBleDeviceServices) throws Exception {
                return rxBleDeviceServices.getCharacteristic(FloatWindowService.this.m_uuidIn);
            }
        }).flatMap(new Function<BluetoothGattCharacteristic, ObservableSource<Observable<byte[]>>>() { // from class: com.baidu.kwgames.FloatWindowService.54
            @Override // io.reactivex.functions.Function
            public ObservableSource<Observable<byte[]>> apply(BluetoothGattCharacteristic bluetoothGattCharacteristic) throws Exception {
                return FloatWindowService.this.mRxBleConnection.setupNotification(bluetoothGattCharacteristic);
            }
        }).flatMap(new Function() { // from class: com.baidu.kwgames.FloatWindowService$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return FloatWindowService.lambda$bleReceive$0((Observable) obj);
            }
        }).subscribe(new Consumer<byte[]>() { // from class: com.baidu.kwgames.FloatWindowService.52
            @Override // io.reactivex.functions.Consumer
            public void accept(byte[] bArr) throws Exception {
                FloatWindowService.this.mBuffer.write(bArr);
                FloatWindowService.this.mParseTriggerSubject.onNext(Integer.valueOf(bArr.length));
            }
        }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.FloatWindowService.53
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                Log.d("FloatWindowService", "bleReceive: " + th);
            }
        }));
    }

    private void parsePackage() {
        this.mParseTriggerSubject.flatMap(new Function<Integer, ObservableSource<byte[]>>() { // from class: com.baidu.kwgames.FloatWindowService.56
            @Override // io.reactivex.functions.Function
            public ObservableSource<byte[]> apply(Integer num) throws Exception {
                return Observable.create(new ObservableOnSubscribe<byte[]>() { // from class: com.baidu.kwgames.FloatWindowService.56.1
                    @Override // io.reactivex.ObservableOnSubscribe
                    public void subscribe(ObservableEmitter<byte[]> observableEmitter) throws Exception {
                        byte[] nextPackage = FloatWindowService.this.mBuffer.getNextPackage();
                        while (nextPackage != null) {
                            observableEmitter.onNext(nextPackage);
                            nextPackage = FloatWindowService.this.mBuffer.getNextPackage();
                        }
                        observableEmitter.onComplete();
                    }
                });
            }
        }).subscribe(new Consumer() { // from class: com.baidu.kwgames.FloatWindowService$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FloatWindowService.this.m10lambda$parsePackage$1$combaidukwgamesFloatWindowService((byte[]) obj);
            }
        }, new Consumer() { // from class: com.baidu.kwgames.FloatWindowService$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FloatWindowService.lambda$parsePackage$2((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: decode_device_data */
    public void m10lambda$parsePackage$1$combaidukwgamesFloatWindowService(byte[] bArr) {
        switch (bArr[1]) {
            case 1:
                KeyMappingSetting keyMappingSetting = this.mKeyMappingSetting;
                if (keyMappingSetting != null) {
                    keyMappingSetting.onKeySel(Byte.valueOf(bArr[2]));
                    return;
                }
                return;
            case 2:
                KeyMappingSetting keyMappingSetting2 = this.mKeyMappingSetting;
                if (keyMappingSetting2 != null) {
                    keyMappingSetting2.onKeySel(Byte.valueOf((byte) Constants.KEY_MOUSE_L));
                    return;
                }
                return;
            case 3:
                int BYTE2INT = Units.BYTE2INT(bArr[2], bArr[3]);
                byte[] bArr2 = new byte[BYTE2INT];
                System.arraycopy(bArr, 4, bArr2, 0, BYTE2INT);
                Units.set_key_map(this.mPreSetKeyIndex, bArr2);
                KeyMappingSetting keyMappingSetting3 = this.mKeyMappingSetting;
                if (keyMappingSetting3 != null) {
                    keyMappingSetting3.on_ble_get_key_map();
                }
                KeyMappingDisplay keyMappingDisplay = this.mKeyMappingDisplay;
                if (keyMappingDisplay != null) {
                    keyMappingDisplay.on_ble_get_key_map();
                    return;
                }
                return;
            case 4:
                callbackBleSystemStatus(bArr, bArr[2] + 3);
                return;
            case 5:
                callbackBleMouseMove(bArr);
                return;
            case 6:
                callbackBleMouseOn(bArr[3] != 0, bArr);
                return;
            case 7:
            case 19:
                callbackBleMTKData(bArr);
                return;
            case 8:
            default:
                return;
            case 9:
                callbackBleGunPressAI(bArr, bArr[2]);
                return;
            case 10:
                callbackChangeAIGunPress(bArr[3]);
                return;
            case 11:
                callAICrosshairState(bArr[3]);
                return;
            case 12:
                callAIGunDownStageChanged(bArr[3]);
                return;
            case 13:
                DynamicGunData.on_received_dynamic_level(bArr);
                callDynamicGunDataChanged(false);
                return;
            case 14:
                DynamicGunData.on_received_dynamic_value(bArr);
                callDynamicGunDataChanged(true);
                return;
            case 15:
                callGunPartsReduceChanged(bArr[3]);
                return;
            case 16:
                Log.d("FloatWindowService", "parsePackage:E_BLE_BUF_TYPE_UPLOAD_TOUPING_PARAM");
                callUploadToupingParam(bArr);
                return;
            case 17:
            case 18:
                callUploadOtherParam(bArr[1], bArr);
                return;
        }
    }

    private void callbackBleMouseMove(byte[] bArr) {
        try {
            if (this.mBleCallbacks.beginBroadcast() > 0) {
                try {
                    this.mBleCallbacks.getBroadcastItem(0).bleMouseMove(bArr);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void callbackBleMouseOn(boolean z, byte[] bArr) {
        try {
            int beginBroadcast = this.mBleCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    this.mBleCallbacks.getBroadcastItem(i).bleMouseOn(z, bArr);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void callbackChangeAIGunPress(byte b) {
        try {
            int beginBroadcast = this.mBleCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    this.mBleCallbacks.getBroadcastItem(i).OnBleChangeAIGunPress(b);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void callAICrosshairState(byte b) {
        try {
            if (this.mBleCallbacks.beginBroadcast() > 0) {
                try {
                    this.mBleCallbacks.getBroadcastItem(0).OnBleAICrosshairState(b);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void callAIGunDownStageChanged(byte b) {
        try {
            int beginBroadcast = this.mBleCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    this.mBleCallbacks.getBroadcastItem(i).OnBleAIGunDownStageChanged(b);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void callDynamicGunDataChanged(boolean z) {
        try {
            int beginBroadcast = this.mBleCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    this.mBleCallbacks.getBroadcastItem(i).OnBleDynamicGunDataChanged(z);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void callGunPartsReduceChanged(byte b) {
        try {
            int beginBroadcast = this.mBleCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    this.mBleCallbacks.getBroadcastItem(i).OnBleGunPartsReduceChanged(b);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void callUploadToupingParam(byte[] bArr) {
        try {
            int beginBroadcast = this.mBleCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    this.mBleCallbacks.getBroadcastItem(i).OnUploadToupingParam(bArr);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void callUploadOtherParam(int i, byte[] bArr) {
        try {
            if (this.mBleCallbacks.beginBroadcast() > 0) {
                try {
                    this.mBleCallbacks.getBroadcastItem(0).OnUploadOtherParam(i, bArr);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackBleConnectState(RxBleConnection.RxBleConnectionState rxBleConnectionState) {
        try {
            int beginBroadcast = this.mBleCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    this.mBleCallbacks.getBroadcastItem(i).bleConnectState(rxBleConnectionState.ordinal());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void callbackBleSystemStatus(byte[] bArr, int i) {
        try {
            int beginBroadcast = this.mBleCallbacks.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    this.mBleCallbacks.getBroadcastItem(i2).bleSystemStatus(bArr, i);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void callbackBleMTKData(byte[] bArr) {
        try {
            int beginBroadcast = this.mBleCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    this.mBleCallbacks.getBroadcastItem(i).bleMTKData(bArr);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void callbackBleGunPressAI(byte[] bArr, int i) {
        try {
            int beginBroadcast = this.mBleCallbacks.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    this.mBleCallbacks.getBroadcastItem(i2).bleGunPressAI(bArr, i);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackDevConnectChanged(boolean z) {
        try {
            int beginBroadcast = this.mBleCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    this.mBleCallbacks.getBroadcastItem(i).onDeviceConnectStateChanged(z);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mIBinder;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        addForegroundNotification();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null && intent.getAction() != null) {
            String nullToEmpty = Strings.nullToEmpty(intent.getAction());
            nullToEmpty.hashCode();
            char c = 65535;
            switch (nullToEmpty.hashCode()) {
                case -1510037921:
                    if (nullToEmpty.equals(ACTION_KEP_MAPPING_HIDE_DISPLAY)) {
                        c = 0;
                        break;
                    }
                    break;
                case -1398713112:
                    if (nullToEmpty.equals(ACTION_KEP_MAPPING_DISPLAY)) {
                        c = 1;
                        break;
                    }
                    break;
                case -1084536202:
                    if (nullToEmpty.equals(ACTION_KEY_MAPPING_SETTING)) {
                        c = 2;
                        break;
                    }
                    break;
                case 1583474631:
                    if (nullToEmpty.equals(ACTION_KILL)) {
                        c = 3;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    hideKeyMappingDisplay();
                    break;
                case 1:
                    showKeyMappingDisplay();
                    break;
                case 2:
                    showKeyMappingSetting();
                    break;
                case 3:
                    stopSelf();
                    break;
            }
        }
        return 1;
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.d("FloatWindowService", "Service::onDestroy");
        remove_all_window();
        bleEnableTouch();
        _bleDisConnect();
        this.mCompositeDisposable.clear();
        _usbDisconnect();
        super.onDestroy();
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        hideKeyMappingDisplay();
        if (configuration.orientation == 2) {
            this.canCapture = true;
        } else {
            this.canCapture = false;
        }
        try {
            int beginBroadcast = this.mBleCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    this.mBleCallbacks.getBroadcastItem(i).onConfigurationChanged(configuration.orientation);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mBleCallbacks.finishBroadcast();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        super.onConfigurationChanged(configuration);
    }

    private void remove_all_window() {
        KeyMappingSetting keyMappingSetting = this.mKeyMappingSetting;
        if (keyMappingSetting != null) {
            keyMappingSetting.remove();
            this.mKeyMappingSetting = null;
        }
        KeyMappingDisplay keyMappingDisplay = this.mKeyMappingDisplay;
        if (keyMappingDisplay != null) {
            keyMappingDisplay.remove();
            this.mKeyMappingDisplay = null;
        }
    }

    private void showKeyMappingSetting() {
        remove_all_window();
        this.mPreSetKeyIndex = AppInstance.s_nCurKeyMap;
        KeyMappingSetting keyMappingSetting = new KeyMappingSetting(getApplicationContext());
        this.mKeyMappingSetting = keyMappingSetting;
        keyMappingSetting.setListener(new AbsFloatBase.OnShowListener() { // from class: com.baidu.kwgames.FloatWindowService.57
            @Override // com.baidu.kwgames.AbsFloatBase.OnShowListener
            public void onShow() {
                FloatWindowService.this.enable_touch(false);
            }

            @Override // com.baidu.kwgames.AbsFloatBase.OnShowListener
            public void onRemove() {
                FloatWindowService.this.enable_touch(true);
                FloatWindowService.this.mKeyMappingSetting = null;
            }

            @Override // com.baidu.kwgames.AbsFloatBase.OnShowListener
            public void onSave(byte[] bArr, int i) {
                FloatWindowService.this.SendKeyMap(bArr, i);
            }

            @Override // com.baidu.kwgames.AbsFloatBase.OnShowListener
            public void onEnableTouch(boolean z) {
                FloatWindowService.this.enable_touch(z);
            }
        });
        this.mKeyMappingSetting.show();
        Constants.SGame2NameID sGame2NameID = Constants.get_game_info(AppInstance.s_strGamePackageName, Units.getAppName(getApplicationContext(), AppInstance.s_strGamePackageName));
        GetKeyMap(this.mPreSetKeyIndex, sGame2NameID.m_nGameDevID, sGame2NameID.m_nGameViewResetDelay, sGame2NameID.m_nFireDisableViewParam, sGame2NameID.m_nGameParamExt1);
        FloatMgr.resetVirtualMouse();
    }

    private void showKeyMappingDisplay() {
        remove_all_window();
        this.mPreSetKeyIndex = AppInstance.s_nCurKeyMap;
        KeyMappingDisplay keyMappingDisplay = new KeyMappingDisplay(getApplicationContext());
        this.mKeyMappingDisplay = keyMappingDisplay;
        keyMappingDisplay.show();
        Constants.SGame2NameID sGame2NameID = Constants.get_game_info(AppInstance.s_strGamePackageName, Units.getAppName(getApplicationContext(), AppInstance.s_strGamePackageName));
        GetKeyMap(this.mPreSetKeyIndex, sGame2NameID.m_nGameDevID, sGame2NameID.m_nGameViewResetDelay, sGame2NameID.m_nFireDisableViewParam, sGame2NameID.m_nGameParamExt1);
        FloatMgr.resetVirtualMouse();
    }

    private void hideKeyMappingDisplay() {
        remove_all_window();
    }

    private void addForegroundNotification() {
        createNotificationChannel();
        String string = getApplicationContext().getResources().getString(R.string.notify_title);
        startForeground(4097, new NotificationCompat.Builder(getApplicationContext(), "FloatWindowService").setSmallIcon(R.mipmap.ic_launcher_round).setLargeIcon(((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher_round)).getBitmap()).setContentTitle(string).setContentText(getApplicationContext().getResources().getString(R.string.notify_content)).setWhen(System.currentTimeMillis()).setPriority(0).setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, getStartAppIntent(getApplicationContext()), Videoio.CAP_INTELPERC_IR_GENERATOR)).setAutoCancel(false).build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("FloatWindowService", "Name", 3);
            notificationChannel.setDescription("Description");
            notificationChannel.setShowBadge(false);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }

    private Intent getStartAppIntent(Context context) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(AppUtils.getAppPackageName());
        if (launchIntentForPackage != null) {
            launchIntentForPackage.addFlags(270532608);
        }
        return launchIntentForPackage;
    }

    public void GetKeyMap(int i, int i2, int i3, int i4, int i5) {
        if (AppInstance.s_boUSBConnected) {
            byte[] bArr = get_5byte_cmd((byte) -127, (byte) i, (byte) i2, (byte) i3, (byte) i4, (byte) i5);
            AppInstance.s_usb.write(bArr, bArr.length, 2000);
        } else if (AppInstance.mBleConnected) {
            bleGetKeys(i, i2, i3, i4, i5);
        }
    }

    public void SendKeyMap(byte[] bArr, int i) {
        if (AppInstance.s_boUSBConnected) {
            int length = bArr.length + 4 + 5;
            byte[] bArr2 = new byte[length];
            bArr2[0] = -1;
            bArr2[1] = Byte.MIN_VALUE;
            bArr2[2] = Units.LOBYTE(bArr.length);
            bArr2[3] = Units.HIBYTE(bArr.length);
            System.arraycopy(bArr, 0, bArr2, 4, bArr.length);
            int length2 = bArr.length + 4;
            bArr2[length2] = -1;
            bArr2[length2 + 1] = -126;
            bArr2[length2 + 2] = 1;
            bArr2[length2 + 3] = 0;
            bArr2[length2 + 4] = (byte) i;
            AppInstance.s_usb.write(bArr2, length, 2000);
        } else if (AppInstance.mBleConnected) {
            _bleSetKey(bArr, i);
        }
    }
}
