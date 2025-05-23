package com.jieli.component.base;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.jieli.component.Logcat;
import com.jieli.component.R;
import com.jieli.component.network.NetWorkUtil;
import com.jieli.component.network.WifiHelper;
import com.jieli.jl_dialog.Jl_Dialog;
import com.jieli.jl_dialog.interfaces.OnViewClickListener;
/* loaded from: classes2.dex */
public class Jl_BaseActivity extends AppCompatActivity {
    private CustomBackPress mCustomBackPress;
    private Jl_Dialog openNetworkDialog;
    public String TAG = getClass().getSimpleName();
    private WifiHelper.OnWifiCallBack mWifiCallBack = new WifiHelper.OnWifiCallBack() { // from class: com.jieli.component.base.Jl_BaseActivity.1
        @Override // com.jieli.component.network.WifiHelper.OnWifiCallBack
        public void onConnected(WifiInfo wifiInfo) {
            if (wifiInfo != null) {
                NetWorkUtil.checkNetworkIsAvailable(new NetWorkUtil.NetStateCheckCallback() { // from class: com.jieli.component.base.Jl_BaseActivity.1.1
                    @Override // com.jieli.component.network.NetWorkUtil.NetStateCheckCallback
                    public void onBack(boolean z) {
                        String str = Jl_BaseActivity.this.TAG;
                        Logcat.e(str, "---- WifiHelper-onConnected------" + z);
                        Jl_BaseActivity.this.handlerNetWorkStateChange(z, 1);
                    }
                });
            }
        }

        @Override // com.jieli.component.network.WifiHelper.OnWifiCallBack
        public void onState(int i) {
            Jl_BaseActivity.this.onNetWorkStateChange(i);
        }
    };

    /* loaded from: classes2.dex */
    public interface CustomBackPress {
        boolean onBack();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Logcat.i(this.TAG, "---------------------------onCreate----------------  ");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        Logcat.i(this.TAG, "---------------------------onResume----------------  ");
        super.onResume();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        Logcat.i(this.TAG, "---------------------------onStop----------------  ");
        super.onStop();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        Logcat.i(this.TAG, "---------------------------onStart----------------  ");
        super.onStart();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        CustomBackPress customBackPress = this.mCustomBackPress;
        if (customBackPress == null || !customBackPress.onBack()) {
            setResult(-1);
            finish();
        }
    }

    public void setCustomBackPress(CustomBackPress customBackPress) {
        this.mCustomBackPress = customBackPress;
    }

    public CustomBackPress getCustomBackPress() {
        return this.mCustomBackPress;
    }

    public void onBack(View view) {
        onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        Logcat.i(this.TAG, "---------------------------onDestroy----------------  ");
        dismissNetworkDialog();
        super.onDestroy();
    }

    public void onNetWorkStateChange(int i) {
        String str = this.TAG;
        Logcat.e(str, "-----onNetWorkStateChange------" + i);
        switch (i) {
            case WifiHelper.STATE_NETWORK_INFO_EMPTY /* 61168 */:
            case WifiHelper.STATE_WIFI_PWD_NOT_MATCH /* 61171 */:
            case WifiHelper.STATE_NETWORK_NOT_OPEN /* 61172 */:
                handlerNetWorkStateChange(false, 2);
                return;
            case WifiHelper.STATE_NETWORK_TYPE_IS_MOBILE /* 61169 */:
                handlerNetWorkStateChange(true, 2);
                Toast.makeText(getApplicationContext(), getString(R.string.mobile_network_tip), 0).show();
                return;
            case WifiHelper.STATE_WIFI_INFO_EMPTY /* 61170 */:
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlerNetWorkStateChange(boolean z, int i) {
        Intent intent = new Intent(NetWorkUtil.ACTION_JIELI_NET_WORK_STATE_CHANGE);
        intent.putExtra(NetWorkUtil.EXTRA_DATA_NETWORK_IS_AVAILABLE, z);
        intent.putExtra(NetWorkUtil.EXTRA_DATA_NETWORK_MODE, i);
        sendBroadcast(intent);
        onNetworkAvailableChange(z, i);
    }

    protected void onNetworkAvailableChange(boolean z, int i) {
        if (!z) {
            showOpenNetworkDialog();
        } else {
            dismissNetworkDialog();
        }
    }

    private void showOpenNetworkDialog() {
        if (this.openNetworkDialog == null) {
            this.openNetworkDialog = Jl_Dialog.builder().title(getString(R.string.tips)).content(getString(R.string.none_network_tip)).left(getString(R.string.cancel)).right(getString(R.string.to_setting)).leftClickListener(new OnViewClickListener() { // from class: com.jieli.component.base.Jl_BaseActivity.3
                @Override // com.jieli.jl_dialog.interfaces.OnViewClickListener
                public void onClick(View view, DialogFragment dialogFragment) {
                    Jl_BaseActivity.this.dismissNetworkDialog();
                }
            }).rightClickListener(new OnViewClickListener() { // from class: com.jieli.component.base.Jl_BaseActivity.2
                @Override // com.jieli.jl_dialog.interfaces.OnViewClickListener
                public void onClick(View view, DialogFragment dialogFragment) {
                    Jl_BaseActivity.this.dismissNetworkDialog();
                    Jl_BaseActivity.this.startActivity(new Intent("android.settings.WIFI_SETTINGS"));
                }
            }).build();
        }
        if (this.openNetworkDialog.isShow()) {
            return;
        }
        this.openNetworkDialog.show(getSupportFragmentManager(), "open_network_dialog");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissNetworkDialog() {
        Jl_Dialog jl_Dialog = this.openNetworkDialog;
        if (jl_Dialog != null) {
            if (jl_Dialog.isShow()) {
                this.openNetworkDialog.dismiss();
            }
            this.openNetworkDialog = null;
        }
    }

    public void changeFragment(int i, Fragment fragment, String str) {
        if (fragment == null || isFinishing()) {
            return;
        }
        changeFragment(i, getSupportFragmentManager().findFragmentById(i), fragment, str);
    }

    public void changeFragment(int i, Fragment fragment, Fragment fragment2, String str) {
        if (fragment2 == null || isFinishing()) {
            return;
        }
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (!fragment2.isAdded()) {
            if (!TextUtils.isEmpty(str)) {
                beginTransaction.add(i, fragment2, str);
            } else {
                beginTransaction.add(i, fragment2);
            }
        }
        if (fragment != null) {
            beginTransaction.hide(fragment);
        }
        beginTransaction.addToBackStack(null);
        beginTransaction.show(fragment2);
        beginTransaction.commitAllowingStateLoss();
    }

    public void changeFragment(int i, Fragment fragment) {
        changeFragment(i, fragment, null);
    }
}
