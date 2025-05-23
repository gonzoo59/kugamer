package com.baidu.kwgames;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.baidu.kwgames.example2_connection.ConnectionExampleActivity;
import com.baidu.kwgames.example3_discovery.ServiceDiscoveryExampleActivity;
/* loaded from: classes.dex */
public class DeviceActivity extends AppCompatActivity {
    public static final String EXTRA_MAC_ADDRESS = "extra_mac_address";
    private String macAddress;

    @OnClick({R.id.connect})
    public void onConnectClick() {
        Intent intent = new Intent(this, ConnectionExampleActivity.class);
        intent.putExtra(EXTRA_MAC_ADDRESS, this.macAddress);
        startActivity(intent);
    }

    @OnClick({R.id.discovery})
    public void onDiscoveryClick() {
        Intent intent = new Intent(this, ServiceDiscoveryExampleActivity.class);
        intent.putExtra(EXTRA_MAC_ADDRESS, this.macAddress);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_device);
        ButterKnife.bind(this);
        this.macAddress = getIntent().getStringExtra(EXTRA_MAC_ADDRESS);
        getSupportActionBar().setSubtitle(getString(R.string.mac_address, new Object[]{this.macAddress}));
    }
}
