package com.baidu.kwgames.example4_characteristic;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.baidu.kwgames.App;
import com.baidu.kwgames.DeviceActivity;
import com.baidu.kwgames.R;
import com.baidu.kwgames.example3_discovery.ServiceDiscoveryExampleActivity$$ExternalSyntheticLambda4;
import com.baidu.kwgames.util.HexString;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rx.ReplayingShare;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.RxBleDeviceServices;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;
import java.util.UUID;
/* loaded from: classes.dex */
public class CharacteristicOperationExampleActivity extends AppCompatActivity {
    public static final String EXTRA_CHARACTERISTIC_UUID = "extra_uuid";
    private RxBleDevice bleDevice;
    private UUID characteristicUuid;
    @BindView(R.id.connect)
    Button connectButton;
    private Observable<RxBleConnection> connectionObservable;
    @BindView(R.id.notify)
    Button notifyButton;
    @BindView(R.id.read)
    Button readButton;
    @BindView(R.id.read_hex_output)
    TextView readHexOutputView;
    @BindView(R.id.read_output)
    TextView readOutputView;
    @BindView(R.id.write)
    Button writeButton;
    @BindView(R.id.write_input)
    TextView writeInput;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final PublishSubject<Boolean> disconnectTriggerSubject = PublishSubject.create();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ ObservableSource lambda$onNotifyClick$9(Observable observable) throws Exception {
        return observable;
    }

    public static Intent startActivityIntent(Context context, String str, UUID uuid) {
        Intent intent = new Intent(context, CharacteristicOperationExampleActivity.class);
        intent.putExtra(DeviceActivity.EXTRA_MAC_ADDRESS, str);
        intent.putExtra("extra_uuid", uuid);
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_example4);
        ButterKnife.bind(this);
        String stringExtra = getIntent().getStringExtra(DeviceActivity.EXTRA_MAC_ADDRESS);
        this.characteristicUuid = (UUID) getIntent().getSerializableExtra("extra_uuid");
        this.bleDevice = App.getRxBleClient(this).getBleDevice(stringExtra);
        this.connectionObservable = prepareConnectionObservable();
        getSupportActionBar().setSubtitle(getString(R.string.mac_address, new Object[]{stringExtra}));
    }

    private Observable<RxBleConnection> prepareConnectionObservable() {
        return this.bleDevice.establishConnection(false).takeUntil(this.disconnectTriggerSubject).compose(ReplayingShare.instance());
    }

    @OnClick({R.id.connect})
    public void onConnectToggleClick() {
        if (isConnected()) {
            triggerDisconnect();
            return;
        }
        this.compositeDisposable.add(this.connectionObservable.flatMapSingle(ServiceDiscoveryExampleActivity$$ExternalSyntheticLambda4.INSTANCE).flatMapSingle(new Function() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return CharacteristicOperationExampleActivity.this.m115x4b97d4d2((RxBleDeviceServices) obj);
            }
        }).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                CharacteristicOperationExampleActivity.this.m116x7a493ef1((Disposable) obj);
            }
        }).subscribe(new Consumer() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                CharacteristicOperationExampleActivity.this.m117xa8faa910((BluetoothGattCharacteristic) obj);
            }
        }, new Consumer() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                CharacteristicOperationExampleActivity.this.onConnectionFailure((Throwable) obj);
            }
        }, new Action() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Action
            public final void run() {
                CharacteristicOperationExampleActivity.this.onConnectionFinished();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onConnectToggleClick$0$com-baidu-kwgames-example4_characteristic-CharacteristicOperationExampleActivity  reason: not valid java name */
    public /* synthetic */ SingleSource m115x4b97d4d2(RxBleDeviceServices rxBleDeviceServices) throws Exception {
        return rxBleDeviceServices.getCharacteristic(this.characteristicUuid);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onConnectToggleClick$1$com-baidu-kwgames-example4_characteristic-CharacteristicOperationExampleActivity  reason: not valid java name */
    public /* synthetic */ void m116x7a493ef1(Disposable disposable) throws Exception {
        this.connectButton.setText(R.string.connecting);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onConnectToggleClick$2$com-baidu-kwgames-example4_characteristic-CharacteristicOperationExampleActivity  reason: not valid java name */
    public /* synthetic */ void m117xa8faa910(BluetoothGattCharacteristic bluetoothGattCharacteristic) throws Exception {
        updateUI(bluetoothGattCharacteristic);
        Log.i(getClass().getSimpleName(), "Hey, connection has been established!");
    }

    @OnClick({R.id.read})
    public void onReadClick() {
        if (isConnected()) {
            this.compositeDisposable.add(this.connectionObservable.firstOrError().flatMap(new Function() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    return CharacteristicOperationExampleActivity.this.m120x659145f((RxBleConnection) obj);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda15
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CharacteristicOperationExampleActivity.this.m121x350a7e7e((byte[]) obj);
                }
            }, new Consumer() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda13
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CharacteristicOperationExampleActivity.this.onReadFailure((Throwable) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onReadClick$3$com-baidu-kwgames-example4_characteristic-CharacteristicOperationExampleActivity  reason: not valid java name */
    public /* synthetic */ SingleSource m120x659145f(RxBleConnection rxBleConnection) throws Exception {
        return rxBleConnection.readCharacteristic(this.characteristicUuid);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onReadClick$4$com-baidu-kwgames-example4_characteristic-CharacteristicOperationExampleActivity  reason: not valid java name */
    public /* synthetic */ void m121x350a7e7e(byte[] bArr) throws Exception {
        this.readOutputView.setText(new String(bArr));
        this.readHexOutputView.setText(HexString.bytesToHex(bArr));
        this.writeInput.setText(HexString.bytesToHex(bArr));
    }

    @OnClick({R.id.write})
    public void onWriteClick() {
        if (isConnected()) {
            this.compositeDisposable.add(this.connectionObservable.firstOrError().flatMap(new Function() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    return CharacteristicOperationExampleActivity.this.m122x54af7f6e((RxBleConnection) obj);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda16
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CharacteristicOperationExampleActivity.this.m123x8360e98d((byte[]) obj);
                }
            }, new Consumer() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda12
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CharacteristicOperationExampleActivity.this.onWriteFailure((Throwable) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onWriteClick$5$com-baidu-kwgames-example4_characteristic-CharacteristicOperationExampleActivity  reason: not valid java name */
    public /* synthetic */ SingleSource m122x54af7f6e(RxBleConnection rxBleConnection) throws Exception {
        return rxBleConnection.writeCharacteristic(this.characteristicUuid, getInputBytes());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onWriteClick$6$com-baidu-kwgames-example4_characteristic-CharacteristicOperationExampleActivity  reason: not valid java name */
    public /* synthetic */ void m123x8360e98d(byte[] bArr) throws Exception {
        onWriteSuccess();
    }

    @OnClick({R.id.notify})
    public void onNotifyClick() {
        if (isConnected()) {
            this.compositeDisposable.add(this.connectionObservable.flatMap(new Function() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    return CharacteristicOperationExampleActivity.this.m118x9e7e2a8e((RxBleConnection) obj);
                }
            }).doOnNext(new Consumer() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda9
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CharacteristicOperationExampleActivity.this.m119xcd2f94ad((Observable) obj);
                }
            }).flatMap(new Function() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    return CharacteristicOperationExampleActivity.lambda$onNotifyClick$9((Observable) obj);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CharacteristicOperationExampleActivity.this.onNotificationReceived((byte[]) obj);
                }
            }, new Consumer() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda11
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CharacteristicOperationExampleActivity.this.onNotificationSetupFailure((Throwable) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onNotifyClick$7$com-baidu-kwgames-example4_characteristic-CharacteristicOperationExampleActivity  reason: not valid java name */
    public /* synthetic */ ObservableSource m118x9e7e2a8e(RxBleConnection rxBleConnection) throws Exception {
        return rxBleConnection.setupNotification(this.characteristicUuid);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onNotifyClick$8$com-baidu-kwgames-example4_characteristic-CharacteristicOperationExampleActivity  reason: not valid java name */
    public /* synthetic */ void m119xcd2f94ad(Observable observable) throws Exception {
        runOnUiThread(new Runnable() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                CharacteristicOperationExampleActivity.this.notificationHasBeenSetUp();
            }
        });
    }

    private boolean isConnected() {
        return this.bleDevice.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectionFailure(Throwable th) {
        View findViewById = findViewById(R.id.main);
        Snackbar.make(findViewById, "Connection error: " + th, -1).show();
        updateUI(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectionFinished() {
        updateUI(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onReadFailure(Throwable th) {
        View findViewById = findViewById(R.id.main);
        Snackbar.make(findViewById, "Read error: " + th, -1).show();
    }

    private void onWriteSuccess() {
        Snackbar.make(findViewById(R.id.main), "Write success", -1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onWriteFailure(Throwable th) {
        View findViewById = findViewById(R.id.main);
        Snackbar.make(findViewById, "Write error: " + th, -1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotificationReceived(byte[] bArr) {
        View findViewById = findViewById(R.id.main);
        Snackbar.make(findViewById, "Change: " + HexString.bytesToHex(bArr), -1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotificationSetupFailure(Throwable th) {
        View findViewById = findViewById(R.id.main);
        Snackbar.make(findViewById, "Notifications error: " + th, -1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notificationHasBeenSetUp() {
        Snackbar.make(findViewById(R.id.main), "Notifications has been set up", -1).show();
    }

    private void triggerDisconnect() {
        this.disconnectTriggerSubject.onNext(true);
    }

    private void updateUI(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.connectButton.setText(bluetoothGattCharacteristic != null ? R.string.disconnect : R.string.connect);
        this.readButton.setEnabled(hasProperty(bluetoothGattCharacteristic, 2));
        this.writeButton.setEnabled(hasProperty(bluetoothGattCharacteristic, 8));
        this.notifyButton.setEnabled(hasProperty(bluetoothGattCharacteristic, 16));
    }

    private boolean hasProperty(BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        return bluetoothGattCharacteristic != null && (bluetoothGattCharacteristic.getProperties() & i) > 0;
    }

    private byte[] getInputBytes() {
        return HexString.hexToBytes(this.writeInput.getText().toString());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.compositeDisposable.clear();
    }
}
