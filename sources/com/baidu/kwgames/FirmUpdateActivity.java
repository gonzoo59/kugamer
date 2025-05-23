package com.baidu.kwgames;

import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.kwgames.util.Download;
import com.baidu.kwgames.util.NEAT;
import com.jieli.component.base.Jl_BaseActivity;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import com.jieli.otasdk.tool.IOtaContract;
import com.jieli.otasdk.tool.OtaPresenter;
import com.jieli.otasdk.tool.ota.ble.BleManager;
import com.jieli.otasdk.util.OtaFileObserverHelper;
import com.jieli.otasdk.widget.ScanView;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
/* loaded from: classes.dex */
public class FirmUpdateActivity extends Jl_BaseActivity implements IOtaContract.IOtaView {
    private Button cancelBtn;
    private EditText editText;
    private BaseDownloadTask mDownloadTask;
    private Button okBtn;
    private ProgressBar progressBar;
    private ScanView scanView;
    private TextView tipText;
    private OtaPresenter otaHelper = null;
    private String mTargetMac = "";
    private Boolean bleConnect = false;
    private boolean m_boUpdating = false;

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onMandatoryUpgrade() {
    }

    @Override // com.jieli.component.base.Jl_BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.layoutInDisplayCutoutMode = 1;
            getWindow().setAttributes(attributes);
        }
        getSupportActionBar().hide();
        setContentView(R.layout.activity_firm_ware_update);
        hide_status_navigate_bar();
        this.okBtn = (Button) findViewById(R.id.ok_tn);
        this.editText = (EditText) findViewById(R.id.edit_text);
        this.progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        this.tipText = (TextView) findViewById(R.id.tip_text);
        this.cancelBtn = (Button) findViewById(R.id.cancel_btn);
        this.okBtn.setVisibility(8);
        this.otaHelper = new OtaPresenter(this, getApplicationContext());
        this.cancelBtn.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.FirmUpdateActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!FirmUpdateActivity.this.okBtn.isEnabled()) {
                    Toast.makeText(FirmUpdateActivity.this, (int) R.string.updating, 0).show();
                } else {
                    FirmUpdateActivity.this.finish();
                }
            }
        });
        this.okBtn.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.FirmUpdateActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AppInstance.mBleConnected) {
                    if (TextUtils.isEmpty(FirmUpdateActivity.this.editText.getText().toString())) {
                        Toast.makeText(FirmUpdateActivity.this, (int) R.string.input_file_tip, 0).show();
                        return;
                    }
                    FirmUpdateActivity firmUpdateActivity = FirmUpdateActivity.this;
                    firmUpdateActivity.startDownLoad(firmUpdateActivity.editText.getText().toString());
                    return;
                }
                Toast.makeText(FirmUpdateActivity.this, (int) R.string.unconnect_device, 0).show();
            }
        });
        String stringExtra = getIntent().getStringExtra("mac");
        this.mTargetMac = stringExtra;
        this.otaHelper.connectBle(stringExtra);
        ScanView scanView = new ScanView(this, "");
        this.scanView = scanView;
        scanView.setOnListener(new ScanView.OnListener() { // from class: com.baidu.kwgames.FirmUpdateActivity.3
            @Override // com.jieli.otasdk.widget.ScanView.OnListener
            public void onConnectStatus(BluetoothDevice bluetoothDevice, int i) {
                FirmUpdateActivity.this.bleConnect = true;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startDownLoad(String str) {
        String str2;
        byte b = AppInstance.g_sSysStatus.uSystemModel;
        if (b == 2) {
            str2 = Constants.URL_FIRMWARE_M1;
        } else if (b == 3) {
            str2 = Constants.URL_FIRMWARE_M2;
        } else if (b == 15) {
            str2 = Constants.URL_FIRMWARE_MK12;
        } else if (b != 18) {
            switch (b) {
                case 8:
                    str2 = Constants.URL_FIRMWARE_M3;
                    break;
                case 9:
                    str2 = Constants.URL_FIRMWARE_M16;
                    break;
                case 10:
                    str2 = Constants.URL_FIRMWARE_M24;
                    break;
                case 11:
                    str2 = Constants.URL_FIRMWARE_M1_NEW;
                    break;
                case 12:
                    str2 = Constants.URL_FIRMWARE_M2_NEW;
                    break;
                default:
                    this.tipText.setText(getString(R.string.update_unsupport));
                    return;
            }
        } else {
            str2 = Constants.URL_FIRMWARE_M4;
        }
        this.progressBar.setProgress(0);
        TextView textView = this.tipText;
        textView.setText(getString(R.string.firm_ware_download) + this.progressBar.getProgress() + "%");
        BaseDownloadTask createTask = Download.createTask(str2 + str + ".ufw", AppInstance.get().getExternalFilesDir(null).getAbsolutePath());
        this.mDownloadTask = createTask;
        createTask.setListener(new FileDownloadLargeFileListener() { // from class: com.baidu.kwgames.FirmUpdateActivity.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.liulishuo.filedownloader.FileDownloadLargeFileListener
            public void paused(BaseDownloadTask baseDownloadTask, long j, long j2) {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.liulishuo.filedownloader.FileDownloadLargeFileListener
            public void pending(BaseDownloadTask baseDownloadTask, long j, long j2) {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.liulishuo.filedownloader.FileDownloadListener
            public void warn(BaseDownloadTask baseDownloadTask) {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.liulishuo.filedownloader.FileDownloadLargeFileListener
            public void progress(BaseDownloadTask baseDownloadTask, long j, long j2) {
                FirmUpdateActivity.this.progressBar.setProgress((int) ((((float) j) / ((float) j2)) * 100.0f));
                TextView textView2 = FirmUpdateActivity.this.tipText;
                textView2.setText(FirmUpdateActivity.this.getString(R.string.firm_ware_download) + FirmUpdateActivity.this.progressBar.getProgress() + "%");
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.liulishuo.filedownloader.FileDownloadListener
            public void completed(BaseDownloadTask baseDownloadTask) {
                FirmUpdateActivity.this.m_boUpdating = true;
                FirmUpdateActivity.this.editText.setEnabled(false);
                FirmUpdateActivity.this.okBtn.setEnabled(false);
                FirmUpdateActivity.this.cancelBtn.setEnabled(false);
                FirmUpdateActivity.this.progressBar.setProgress(100);
                TextView textView2 = FirmUpdateActivity.this.tipText;
                textView2.setText(FirmUpdateActivity.this.getString(R.string.firm_ware_download) + FirmUpdateActivity.this.progressBar.getProgress() + "%");
                FirmUpdateActivity.this.progressBar.setProgress(0);
                FirmUpdateActivity.this.otaHelper.startOTA(baseDownloadTask.getTargetFilePath());
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.liulishuo.filedownloader.FileDownloadListener
            public void error(BaseDownloadTask baseDownloadTask, Throwable th) {
                FirmUpdateActivity.this.editText.setEnabled(true);
                FirmUpdateActivity.this.okBtn.setEnabled(true);
                FirmUpdateActivity.this.cancelBtn.setEnabled(true);
                FirmUpdateActivity.this.tipText.setText("");
                Toast.makeText(FirmUpdateActivity.this, (int) R.string.download_err, 0).show();
            }
        }).start();
    }

    public void hide_status_navigate_bar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 256 | 1024 | 512 | 4 | 2 | 4096);
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().setStatusBarColor(0);
            getWindow().setNavigationBarColor(0);
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.systemUiVisibility = 2050;
        getWindow().setAttributes(attributes);
    }

    @Override // com.jieli.component.base.Jl_BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        this.otaHelper.start();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jieli.component.base.Jl_BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.scanView.disconnectBtDevice();
        BaseDownloadTask baseDownloadTask = this.mDownloadTask;
        if (baseDownloadTask != null) {
            baseDownloadTask.pause();
        }
        if (this.otaHelper.isOTA()) {
            this.otaHelper.cancelOTA();
        }
        this.otaHelper.destroy();
        OtaFileObserverHelper.getInstance().destroy();
        BleManager.getInstance().destroy();
    }

    @Override // com.jieli.otasdk.base.BaseView
    public void setPresenter(IOtaContract.IOtaPresenter iOtaPresenter) {
        this.otaHelper = (OtaPresenter) iOtaPresenter;
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onConnection(BluetoothDevice bluetoothDevice, int i) {
        String str = this.TAG;
        JL_Log.i(str, "onConnectionS : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice) + ", " + i);
        this.okBtn.setVisibility(i == 1 ? 0 : 8);
        if (i == 0) {
            if (this.m_boUpdating) {
                this.tipText.setText(NEAT.s(this, R.string.exit_to_update_mode));
            }
        } else if (i == 1) {
            if (this.m_boUpdating) {
                this.tipText.setText(NEAT.s(this, R.string.already_in_update_mode));
            }
        } else if (i == 2) {
            if (this.m_boUpdating) {
                this.tipText.setText(NEAT.s(this, R.string.switch_to_update_mode_failed));
            }
        } else if (i == 3 && this.m_boUpdating) {
            this.tipText.setText(NEAT.s(this, R.string.switch_to_update_mode));
        }
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onOTAStart() {
        getWindow().addFlags(128);
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onOTAReconnect(String str) {
        this.otaHelper.reconnectDev(str);
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onOTAProgress(int i, float f) {
        this.editText.setEnabled(false);
        this.okBtn.setEnabled(false);
        this.cancelBtn.setEnabled(false);
        this.progressBar.setProgress((int) f);
        if (this.progressBar.getVisibility() != 0) {
            this.progressBar.setVisibility(0);
        }
        if (f >= 100.0f) {
            this.progressBar.setVisibility(4);
            this.tipText.setText(R.string.update_finish);
            this.m_boUpdating = false;
        } else if (i == 0) {
            TextView textView = this.tipText;
            textView.setText(getString(R.string.verify_firmware) + this.progressBar.getProgress() + "%");
        } else {
            TextView textView2 = this.tipText;
            textView2.setText(getString(R.string.updating) + this.progressBar.getProgress() + "%");
        }
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onOTAStop() {
        this.okBtn.setEnabled(true);
        this.editText.setEnabled(true);
        this.cancelBtn.setEnabled(true);
        getWindow().clearFlags(128);
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onOTACancel() {
        this.editText.setEnabled(true);
        this.okBtn.setEnabled(true);
        this.cancelBtn.setEnabled(true);
        getWindow().clearFlags(128);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && !this.okBtn.isEnabled()) {
            Toast.makeText(this, (int) R.string.updating, 0).show();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onOTAError(int i, String str) {
        this.editText.setEnabled(true);
        this.okBtn.setEnabled(true);
        this.cancelBtn.setEnabled(true);
        getWindow().clearFlags(128);
        Toast.makeText(this, str, 0).show();
        this.tipText.setText("");
        this.progressBar.setVisibility(4);
    }
}
