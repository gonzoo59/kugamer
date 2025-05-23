package com.baidu.kwgames.example3_discovery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.baidu.kwgames.App;
import com.baidu.kwgames.DeviceActivity;
import com.baidu.kwgames.R;
import com.baidu.kwgames.example3_discovery.DiscoveryResultsAdapter;
import com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity;
import com.google.android.material.snackbar.Snackbar;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.RxBleDeviceServices;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.Objects;
/* loaded from: classes.dex */
public class ServiceDiscoveryExampleActivity extends AppCompatActivity {
    private DiscoveryResultsAdapter adapter;
    private RxBleDevice bleDevice;
    @BindView(R.id.connect)
    Button connectButton;
    private String macAddress;
    @BindView(R.id.scan_results)
    RecyclerView recyclerView;
    private final CompositeDisposable servicesDisposable = new CompositeDisposable();

    @OnClick({R.id.connect})
    public void onConnectToggleClick() {
        Observable doFinally = this.bleDevice.establishConnection(false).flatMapSingle(ServiceDiscoveryExampleActivity$$ExternalSyntheticLambda4.INSTANCE).take(1L).observeOn(AndroidSchedulers.mainThread()).doFinally(new Action() { // from class: com.baidu.kwgames.example3_discovery.ServiceDiscoveryExampleActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ServiceDiscoveryExampleActivity.this.updateUI();
            }
        });
        final DiscoveryResultsAdapter discoveryResultsAdapter = this.adapter;
        Objects.requireNonNull(discoveryResultsAdapter);
        this.servicesDisposable.add(doFinally.subscribe(new Consumer() { // from class: com.baidu.kwgames.example3_discovery.ServiceDiscoveryExampleActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DiscoveryResultsAdapter.this.swapScanResult((RxBleDeviceServices) obj);
            }
        }, new Consumer() { // from class: com.baidu.kwgames.example3_discovery.ServiceDiscoveryExampleActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ServiceDiscoveryExampleActivity.this.onConnectionFailure((Throwable) obj);
            }
        }));
        updateUI();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_example3);
        ButterKnife.bind(this);
        this.macAddress = getIntent().getStringExtra(DeviceActivity.EXTRA_MAC_ADDRESS);
        getSupportActionBar().setSubtitle(getString(R.string.mac_address, new Object[]{this.macAddress}));
        this.bleDevice = App.getRxBleClient(this).getBleDevice(this.macAddress);
        configureResultList();
    }

    private void configureResultList() {
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DiscoveryResultsAdapter discoveryResultsAdapter = new DiscoveryResultsAdapter();
        this.adapter = discoveryResultsAdapter;
        this.recyclerView.setAdapter(discoveryResultsAdapter);
        this.adapter.setOnAdapterItemClickListener(new DiscoveryResultsAdapter.OnAdapterItemClickListener() { // from class: com.baidu.kwgames.example3_discovery.ServiceDiscoveryExampleActivity$$ExternalSyntheticLambda0
            @Override // com.baidu.kwgames.example3_discovery.DiscoveryResultsAdapter.OnAdapterItemClickListener
            public final void onAdapterViewClick(View view) {
                ServiceDiscoveryExampleActivity.this.m111x2ca54275(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$configureResultList$0$com-baidu-kwgames-example3_discovery-ServiceDiscoveryExampleActivity  reason: not valid java name */
    public /* synthetic */ void m111x2ca54275(View view) {
        onAdapterItemClick(this.adapter.getItem(this.recyclerView.getChildAdapterPosition(view)));
    }

    private void onAdapterItemClick(DiscoveryResultsAdapter.AdapterItem adapterItem) {
        if (adapterItem.type == 2) {
            startActivity(CharacteristicOperationExampleActivity.startActivityIntent(this, this.macAddress, adapterItem.uuid));
        } else {
            Snackbar.make(findViewById(16908290), (int) R.string.not_clickable, -1).show();
        }
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
    public void updateUI() {
        this.connectButton.setEnabled(!isConnected());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.servicesDisposable.clear();
    }
}
