package com.jieli.component.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.jieli.component.Logcat;
import com.jieli.component.callbacks.OnFragmentLifeCircle;
import com.jieli.component.network.NetWorkUtil;
/* loaded from: classes2.dex */
public class Jl_BaseFragment extends Fragment {
    protected String TAG = getClass().getSimpleName();
    private Bundle bundle;
    private OnFragmentLifeCircle mOnFragmentLifeCircle;
    private NetWorkStateBroadcastReceiver mReceiver;
    private Toast mToast;

    protected boolean isNeedNetWorkStateWatch() {
        return true;
    }

    public void setOnFragmentLifeCircle(OnFragmentLifeCircle onFragmentLifeCircle) {
        this.mOnFragmentLifeCircle = onFragmentLifeCircle;
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void changeFragment(int i, Fragment fragment, String str) {
        if (fragment == null || !isAdded() || isDetached()) {
            return;
        }
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        if (!TextUtils.isEmpty(str)) {
            beginTransaction.replace(i, fragment, str);
        } else {
            beginTransaction.replace(i, fragment);
        }
        beginTransaction.addToBackStack(null);
        beginTransaction.commitAllowingStateLoss();
    }

    public void changeFragment(int i, Fragment fragment) {
        changeFragment(i, fragment, null);
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void showToastShort(String str) {
        showToast(str, 0);
    }

    public void showToastShort(int i) {
        showToastShort(getResources().getString(i));
    }

    public void showToastLong(String str) {
        showToast(str, 1);
    }

    public void showToastLong(int i) {
        showToastLong(getResources().getString(i));
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        OnFragmentLifeCircle onFragmentLifeCircle = this.mOnFragmentLifeCircle;
        if (onFragmentLifeCircle != null) {
            onFragmentLifeCircle.onActivityCreated(this, getActivity());
        }
        super.onActivityCreated(bundle);
        if (isNeedNetWorkStateWatch()) {
            registerReceiver();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        unregisterReceiver();
        OnFragmentLifeCircle onFragmentLifeCircle = this.mOnFragmentLifeCircle;
        if (onFragmentLifeCircle != null) {
            onFragmentLifeCircle.onDestroyView(this, getActivity());
        }
        super.onDestroyView();
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        OnFragmentLifeCircle onFragmentLifeCircle = this.mOnFragmentLifeCircle;
        if (onFragmentLifeCircle != null) {
            if (z) {
                onFragmentLifeCircle.onHidden(this, getActivity());
            } else {
                onFragmentLifeCircle.onShow(this, getActivity());
            }
        }
    }

    private void registerReceiver() {
        if (getContext() == null) {
            return;
        }
        if (this.mReceiver == null) {
            this.mReceiver = new NetWorkStateBroadcastReceiver();
        }
        getContext().registerReceiver(this.mReceiver, new IntentFilter(NetWorkUtil.ACTION_JIELI_NET_WORK_STATE_CHANGE));
    }

    private void unregisterReceiver() {
        if (getContext() == null || this.mReceiver == null) {
            return;
        }
        getContext().unregisterReceiver(this.mReceiver);
    }

    public void onNetworkAvailableChange(boolean z, int i) {
        String str = this.TAG;
        Logcat.e(str, "----------onNetworkAvailableChange------------isAvailable:" + z + "\tmode:" + i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class NetWorkStateBroadcastReceiver extends BroadcastReceiver {
        private NetWorkStateBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (context == null || intent == null) {
                return;
            }
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return;
            }
            Logcat.e(Jl_BaseFragment.this.TAG, "-----------NetWorkStateBroadcastReceiver----------");
            action.hashCode();
            if (action.equals(NetWorkUtil.ACTION_JIELI_NET_WORK_STATE_CHANGE)) {
                int intExtra = intent.getIntExtra(NetWorkUtil.EXTRA_DATA_NETWORK_MODE, 0);
                Jl_BaseFragment.this.onNetworkAvailableChange(intent.getBooleanExtra(NetWorkUtil.EXTRA_DATA_NETWORK_IS_AVAILABLE, false), intExtra);
            }
        }
    }

    public void showToast(String str, int i) {
        if (getContext() == null || TextUtils.isEmpty(str) || i < 0) {
            return;
        }
        Toast toast = this.mToast;
        if (toast == null) {
            this.mToast = Toast.makeText(getContext().getApplicationContext(), str, i);
        } else {
            toast.setText(str);
            this.mToast.setDuration(i);
        }
        this.mToast.setGravity(17, 0, 0);
        this.mToast.show();
    }
}
