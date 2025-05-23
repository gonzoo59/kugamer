package com.baidu.kwgames.example5_rssi_periodic;

import android.os.Bundle;
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
import com.google.android.material.snackbar.Snackbar;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.concurrent.TimeUnit;
/* loaded from: classes.dex */
public class RssiPeriodicExampleActivity extends AppCompatActivity {
    private RxBleDevice bleDevice;
    @BindView(R.id.connect_toggle)
    Button connectButton;
    private Disposable connectionDisposable;
    @BindView(R.id.connection_state)
    TextView connectionStateView;
    @BindView(R.id.rssi)
    TextView rssiView;
    private Disposable stateDisposable;

    @OnClick({R.id.connect_toggle})
    public void onConnectToggleClick() {
        if (isConnected()) {
            triggerDisconnect();
        } else {
            this.connectionDisposable = this.bleDevice.establishConnection(false).doFinally(new Action() { // from class: com.baidu.kwgames.example5_rssi_periodic.RssiPeriodicExampleActivity$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Action
                public final void run() {
                    RssiPeriodicExampleActivity.this.clearSubscription();
                }
            }).flatMap(new Function() { // from class: com.baidu.kwgames.example5_rssi_periodic.RssiPeriodicExampleActivity$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    ObservableSource flatMapSingle;
                    flatMapSingle = Observable.interval(2L, TimeUnit.SECONDS).flatMapSingle(new Function() { // from class: com.baidu.kwgames.example5_rssi_periodic.RssiPeriodicExampleActivity$$ExternalSyntheticLambda4
                        @Override // io.reactivex.functions.Function
                        public final Object apply(Object obj2) {
                            SingleSource readRssi;
                            Long l = (Long) obj2;
                            readRssi = RxBleConnection.this.readRssi();
                            return readRssi;
                        }
                    });
                    return flatMapSingle;
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.baidu.kwgames.example5_rssi_periodic.RssiPeriodicExampleActivity$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    RssiPeriodicExampleActivity.this.updateRssi(((Integer) obj).intValue());
                }
            }, new Consumer() { // from class: com.baidu.kwgames.example5_rssi_periodic.RssiPeriodicExampleActivity$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    RssiPeriodicExampleActivity.this.onConnectionFailure((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRssi(int i) {
        this.rssiView.setText(getString(R.string.read_rssi, new Object[]{Integer.valueOf(i)}));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_example5);
        ButterKnife.bind(this);
        String stringExtra = getIntent().getStringExtra(DeviceActivity.EXTRA_MAC_ADDRESS);
        setTitle(getString(R.string.mac_address, new Object[]{stringExtra}));
        RxBleDevice bleDevice = App.getRxBleClient(this).getBleDevice(stringExtra);
        this.bleDevice = bleDevice;
        this.stateDisposable = bleDevice.observeConnectionStateChanges().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.baidu.kwgames.example5_rssi_periodic.RssiPeriodicExampleActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RssiPeriodicExampleActivity.this.onConnectionStateChange((RxBleConnection.RxBleConnectionState) obj);
            }
        });
    }

    private boolean isConnected() {
        return this.bleDevice.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectionFailure(Throwable th) {
        View findViewById = findViewById(16908290);
        Snackbar.make(findViewById, "Connection error: " + th, -1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectionStateChange(RxBleConnection.RxBleConnectionState rxBleConnectionState) {
        this.connectionStateView.setText(rxBleConnectionState.toString());
        updateUI();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearSubscription() {
        this.connectionDisposable = null;
        updateUI();
    }

    private void triggerDisconnect() {
        Disposable disposable = this.connectionDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void updateUI() {
        this.connectButton.setText(isConnected() ? R.string.disconnect : R.string.connect);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        triggerDisconnect();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.stateDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
