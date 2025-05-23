package com.baidu.kwgames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.baidu.kwgames.ScanResultsAdapter;
import com.baidu.kwgames.util.LocationPermission;
import com.baidu.kwgames.util.ScanExceptionHandler;
import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.exceptions.BleScanException;
import com.polidea.rxandroidble2.scan.ScanResult;
import io.reactivex.disposables.Disposable;
/* loaded from: classes.dex */
public class ScanActivity extends AppCompatActivity {
    private boolean hasClickedScan;
    @BindView(R.id.scan_results)
    RecyclerView recyclerView;
    private ScanResultsAdapter resultsAdapter;
    private RxBleClient rxBleClient;
    private Disposable scanDisposable;

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        this.rxBleClient = App.getRxBleClient(this);
        configureResultList();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (LocationPermission.isRequestLocationPermissionGranted(i, strArr, iArr, this.rxBleClient) && this.hasClickedScan) {
            this.hasClickedScan = false;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (isScanning()) {
            this.scanDisposable.dispose();
        }
    }

    private void configureResultList() {
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setItemAnimator(null);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScanResultsAdapter scanResultsAdapter = new ScanResultsAdapter();
        this.resultsAdapter = scanResultsAdapter;
        this.recyclerView.setAdapter(scanResultsAdapter);
        this.resultsAdapter.setOnAdapterItemClickListener(new ScanResultsAdapter.OnAdapterItemClickListener() { // from class: com.baidu.kwgames.ScanActivity$$ExternalSyntheticLambda0
            @Override // com.baidu.kwgames.ScanResultsAdapter.OnAdapterItemClickListener
            public final void onAdapterViewClick(View view) {
                ScanActivity.this.m103lambda$configureResultList$0$combaidukwgamesScanActivity(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$configureResultList$0$com-baidu-kwgames-ScanActivity  reason: not valid java name */
    public /* synthetic */ void m103lambda$configureResultList$0$combaidukwgamesScanActivity(View view) {
        onAdapterItemClick(this.resultsAdapter.getItemAtPosition(this.recyclerView.getChildAdapterPosition(view)));
    }

    private boolean isScanning() {
        return this.scanDisposable != null;
    }

    private void onAdapterItemClick(ScanResult scanResult) {
        String macAddress = scanResult.getBleDevice().getMacAddress();
        Intent intent = new Intent();
        intent.putExtra("macAddress", macAddress);
        setResult(-1, intent);
        finish();
    }

    private void onScanFailure(Throwable th) {
        if (th instanceof BleScanException) {
            ScanExceptionHandler.handleException(this, (BleScanException) th);
        }
    }

    private void dispose() {
        this.scanDisposable = null;
        this.resultsAdapter.clearScanResults();
    }
}
