package com.baidu.kwgames.example2_connection;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.baidu.kwgames.App;
import com.baidu.kwgames.DeviceActivity;
import com.baidu.kwgames.R;
import com.google.android.material.snackbar.Snackbar;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
/* loaded from: classes.dex */
public class ConnectionExampleActivity extends AppCompatActivity {
    @BindView(R.id.autoconnect)
    SwitchCompat autoConnectToggleSwitch;
    private RxBleDevice bleDevice;
    @BindView(R.id.connect_toggle)
    Button connectButton;
    private Disposable connectionDisposable;
    @BindView(R.id.connection_state)
    TextView connectionStateView;
    private final CompositeDisposable mtuDisposable = new CompositeDisposable();
    @BindView(R.id.set_mtu)
    Button setMtuButton;
    private Disposable stateDisposable;
    @BindView(R.id.newMtu)
    EditText textMtu;

    @OnClick({R.id.connect_toggle})
    public void onConnectToggleClick() {
        if (isConnected()) {
            triggerDisconnect();
        } else {
            this.connectionDisposable = this.bleDevice.establishConnection(this.autoConnectToggleSwitch.isChecked()).observeOn(AndroidSchedulers.mainThread()).doFinally(new Action() { // from class: com.baidu.kwgames.example2_connection.ConnectionExampleActivity$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ConnectionExampleActivity.this.dispose();
                }
            }).subscribe(new Consumer() { // from class: com.baidu.kwgames.example2_connection.ConnectionExampleActivity$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ConnectionExampleActivity.this.onConnectionReceived((RxBleConnection) obj);
                }
            }, new ConnectionExampleActivity$$ExternalSyntheticLambda5(this));
        }
    }

    @OnClick({R.id.set_mtu})
    public void onSetMtu() {
        this.mtuDisposable.add(this.bleDevice.establishConnection(false).flatMapSingle(new Function() { // from class: com.baidu.kwgames.example2_connection.ConnectionExampleActivity$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                SingleSource requestMtu;
                requestMtu = ((RxBleConnection) obj).requestMtu(72);
                return requestMtu;
            }
        }).take(1L).observeOn(AndroidSchedulers.mainThread()).doFinally(new Action() { // from class: com.baidu.kwgames.example2_connection.ConnectionExampleActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Action
            public final void run() {
                ConnectionExampleActivity.this.updateUI();
            }
        }).subscribe(new Consumer() { // from class: com.baidu.kwgames.example2_connection.ConnectionExampleActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ConnectionExampleActivity.this.onMtuReceived((Integer) obj);
            }
        }, new ConnectionExampleActivity$$ExternalSyntheticLambda5(this)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_example2);
        ButterKnife.bind(this);
        String stringExtra = getIntent().getStringExtra(DeviceActivity.EXTRA_MAC_ADDRESS);
        setTitle(getString(R.string.mac_address, new Object[]{stringExtra}));
        RxBleDevice bleDevice = App.getRxBleClient(this).getBleDevice(stringExtra);
        this.bleDevice = bleDevice;
        this.stateDisposable = bleDevice.observeConnectionStateChanges().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.baidu.kwgames.example2_connection.ConnectionExampleActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ConnectionExampleActivity.this.onConnectionStateChange((RxBleConnection.RxBleConnectionState) obj);
            }
        });
    }

    private boolean isConnected() {
        return this.bleDevice.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTED;
    }

    public void onConnectionFailure(Throwable th) {
        View findViewById = findViewById(16908290);
        Snackbar.make(findViewById, "Connection error: " + th, -1).show();
    }

    public void onConnectionReceived(RxBleConnection rxBleConnection) {
        Snackbar.make(findViewById(16908290), "Connection received", -1).show();
    }

    public void onConnectionStateChange(RxBleConnection.RxBleConnectionState rxBleConnectionState) {
        this.connectionStateView.setText(rxBleConnectionState.toString());
        updateUI();
    }

    public void onMtuReceived(Integer num) {
        View findViewById = findViewById(16908290);
        Snackbar.make(findViewById, "MTU received: " + num, -1).show();
    }

    public void dispose() {
        this.connectionDisposable = null;
        updateUI();
    }

    private void triggerDisconnect() {
        Disposable disposable = this.connectionDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void updateUI() {
        boolean isConnected = isConnected();
        this.connectButton.setText(isConnected ? R.string.disconnect : R.string.connect);
        this.autoConnectToggleSwitch.setEnabled(!isConnected);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        triggerDisconnect();
        this.mtuDisposable.clear();
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
