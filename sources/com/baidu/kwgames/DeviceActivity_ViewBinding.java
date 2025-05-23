package com.baidu.kwgames;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
/* loaded from: classes.dex */
public class DeviceActivity_ViewBinding implements Unbinder {
    private DeviceActivity target;
    private View view7f0900c1;
    private View view7f0900ec;

    public DeviceActivity_ViewBinding(DeviceActivity deviceActivity) {
        this(deviceActivity, deviceActivity.getWindow().getDecorView());
    }

    public DeviceActivity_ViewBinding(final DeviceActivity deviceActivity, View view) {
        this.target = deviceActivity;
        View findRequiredView = Utils.findRequiredView(view, R.id.connect, "method 'onConnectClick'");
        this.view7f0900c1 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.DeviceActivity_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                deviceActivity.onConnectClick();
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.discovery, "method 'onDiscoveryClick'");
        this.view7f0900ec = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.DeviceActivity_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                deviceActivity.onDiscoveryClick();
            }
        });
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        if (this.target == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        this.view7f0900c1.setOnClickListener(null);
        this.view7f0900c1 = null;
        this.view7f0900ec.setOnClickListener(null);
        this.view7f0900ec = null;
    }
}
