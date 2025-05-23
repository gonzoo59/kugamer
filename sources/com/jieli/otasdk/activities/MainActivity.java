package com.jieli.otasdk.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.jieli.component.ui.widget.NoScrollViewPager;
import com.jieli.component.utils.ToastUtil;
import com.jieli.otasdk.R;
import com.jieli.otasdk.base.BaseActivity;
import com.jieli.otasdk.fragments.OtaFragment;
import com.jieli.otasdk.tool.ota.ble.BleManager;
import com.jieli.otasdk.util.AppUtil;
import com.jieli.otasdk.util.JL_Constant;
import com.jieli.otasdk.util.OtaFileObserverHelper;
import com.jieli.otasdk.widget.ScanView;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: MainActivity.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\b\u0010\u000e\u001a\u00020\nH\u0014J\b\u0010\u000f\u001a\u00020\nH\u0002J\u0016\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\b\u0010\u0015\u001a\u00020\nH\u0002J\b\u0010\u0016\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0018\u00010\u0006R\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/jieli/otasdk/activities/MainActivity;", "Lcom/jieli/otasdk/base/BaseActivity;", "()V", "mTargetMac", "", "mainBroadcast", "Lcom/jieli/otasdk/activities/MainActivity$MainBroadcast;", "scanView", "Lcom/jieli/otasdk/widget/ScanView;", "onBackPressed", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "registerMainReceiver", "switchSubFragment", "itemIndex", "", "smoothScroll", "", "toSettingsActivity", "unregisterMainReceiver", "MainBroadcast", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class MainActivity extends BaseActivity {
    private HashMap _$_findViewCache;
    private String mTargetMac = "";
    private MainBroadcast mainBroadcast;
    private ScanView scanView;

    @Override // com.jieli.otasdk.base.BaseActivity
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.jieli.otasdk.base.BaseActivity
    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.jieli.otasdk.base.BaseActivity, com.jieli.component.base.Jl_BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ota_main);
        final Fragment[] fragmentArr = {new OtaFragment()};
        NoScrollViewPager viewpage_main = (NoScrollViewPager) _$_findCachedViewById(R.id.viewpage_main);
        Intrinsics.checkExpressionValueIsNotNull(viewpage_main, "viewpage_main");
        final FragmentManager supportFragmentManager = getSupportFragmentManager();
        viewpage_main.setAdapter(new FragmentPagerAdapter(supportFragmentManager) { // from class: com.jieli.otasdk.activities.MainActivity$onCreate$1
            @Override // androidx.fragment.app.FragmentPagerAdapter
            public Fragment getItem(int i) {
                return fragmentArr[i];
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public int getCount() {
                return fragmentArr.length;
            }
        });
        ((ImageView) _$_findCachedViewById(R.id.iv_main_settings)).setOnClickListener(new View.OnClickListener() { // from class: com.jieli.otasdk.activities.MainActivity$onCreate$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.toSettingsActivity();
            }
        });
        registerMainReceiver();
        switchSubFragment(0, false);
        String stringExtra = getIntent().getStringExtra("mac");
        Intrinsics.checkExpressionValueIsNotNull(stringExtra, "intent.getStringExtra(\"mac\")");
        this.mTargetMac = stringExtra;
        this.scanView = new ScanView(this, stringExtra);
    }

    @Override // com.jieli.component.base.Jl_BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        if (!AppUtil.isFastDoubleClick()) {
            ToastUtil.showToastShort(R.string.double_tap_to_exit);
        } else {
            super.onBackPressed();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jieli.component.base.Jl_BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterMainReceiver();
        OtaFileObserverHelper.getInstance().destroy();
        BleManager.getInstance().destroy();
    }

    public final void switchSubFragment(int i, boolean z) {
        if (isDestroyed() || isFinishing()) {
            return;
        }
        NoScrollViewPager noScrollViewPager = (NoScrollViewPager) _$_findCachedViewById(R.id.viewpage_main);
        if (noScrollViewPager != null) {
            noScrollViewPager.setCurrentItem(i, z);
        }
        if (i == 0) {
            RadioButton tab_upgrade = (RadioButton) _$_findCachedViewById(R.id.tab_upgrade);
            Intrinsics.checkExpressionValueIsNotNull(tab_upgrade, "tab_upgrade");
            tab_upgrade.setChecked(true);
        } else if (i != 1) {
        } else {
            RadioButton tab_device = (RadioButton) _$_findCachedViewById(R.id.tab_device);
            Intrinsics.checkExpressionValueIsNotNull(tab_device, "tab_device");
            tab_device.setChecked(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void toSettingsActivity() {
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
    }

    private final void registerMainReceiver() {
        if (this.mainBroadcast == null) {
            this.mainBroadcast = new MainBroadcast();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(JL_Constant.ACTION_EXIT_APP);
            registerReceiver(this.mainBroadcast, intentFilter);
        }
    }

    private final void unregisterMainReceiver() {
        MainBroadcast mainBroadcast = this.mainBroadcast;
        if (mainBroadcast != null) {
            unregisterReceiver(mainBroadcast);
            this.mainBroadcast = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: MainActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016¨\u0006\t"}, d2 = {"Lcom/jieli/otasdk/activities/MainActivity$MainBroadcast;", "Landroid/content/BroadcastReceiver;", "(Lcom/jieli/otasdk/activities/MainActivity;)V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "otasdk_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public final class MainBroadcast extends BroadcastReceiver {
        public MainBroadcast() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null && Intrinsics.areEqual(JL_Constant.ACTION_EXIT_APP, intent.getAction())) {
                MainActivity.this.finish();
            }
        }
    }
}
