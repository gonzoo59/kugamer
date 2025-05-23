package com.lzf.easyfloat.permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.baidu.kwgames.GameSettingFloat;
import com.lzf.easyfloat.interfaces.OnPermissionResult;
import com.lzf.easyfloat.utils.Logger;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: PermissionFragment.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J\"\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016¨\u0006\u000e"}, d2 = {"Lcom/lzf/easyfloat/permission/PermissionFragment;", "Landroid/app/Fragment;", "()V", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "onActivityResult", "requestCode", "", "resultCode", GameSettingFloat.TAG_ATTR_DATA, "Landroid/content/Intent;", "Companion", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class PermissionFragment extends Fragment {
    public static final Companion Companion = new Companion(null);
    private static OnPermissionResult onPermissionResult;
    private HashMap _$_findViewCache;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // android.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: PermissionFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/lzf/easyfloat/permission/PermissionFragment$Companion;", "", "()V", "onPermissionResult", "Lcom/lzf/easyfloat/interfaces/OnPermissionResult;", "requestPermission", "", "activity", "Landroid/app/Activity;", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void requestPermission(Activity activity, OnPermissionResult onPermissionResult) {
            Intrinsics.checkParameterIsNotNull(activity, "activity");
            Intrinsics.checkParameterIsNotNull(onPermissionResult, "onPermissionResult");
            PermissionFragment.onPermissionResult = onPermissionResult;
            activity.getFragmentManager().beginTransaction().add(new PermissionFragment(), activity.getLocalClassName()).commitAllowingStateLoss();
        }
    }

    @Override // android.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        PermissionUtils.INSTANCE.requestPermission$easyfloat_release(this);
        Logger.INSTANCE.i("PermissionFragment：requestPermission");
    }

    @Override // android.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 199) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.lzf.easyfloat.permission.PermissionFragment$onActivityResult$1
                @Override // java.lang.Runnable
                public final void run() {
                    OnPermissionResult onPermissionResult2;
                    Activity activity = PermissionFragment.this.getActivity();
                    if (activity != null) {
                        boolean checkPermission = PermissionUtils.checkPermission(activity);
                        Logger logger = Logger.INSTANCE;
                        logger.i("PermissionFragment onActivityResult: " + checkPermission);
                        onPermissionResult2 = PermissionFragment.onPermissionResult;
                        if (onPermissionResult2 != null) {
                            onPermissionResult2.permissionResult(checkPermission);
                        }
                        PermissionFragment.this.getFragmentManager().beginTransaction().remove(PermissionFragment.this).commitAllowingStateLoss();
                    }
                }
            }, 500L);
        }
    }
}
