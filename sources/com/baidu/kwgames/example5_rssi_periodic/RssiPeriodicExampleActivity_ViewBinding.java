package com.baidu.kwgames.example5_rssi_periodic;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.baidu.kwgames.R;
/* loaded from: classes.dex */
public class RssiPeriodicExampleActivity_ViewBinding implements Unbinder {
    private RssiPeriodicExampleActivity target;
    private View view7f0900c3;

    public RssiPeriodicExampleActivity_ViewBinding(RssiPeriodicExampleActivity rssiPeriodicExampleActivity) {
        this(rssiPeriodicExampleActivity, rssiPeriodicExampleActivity.getWindow().getDecorView());
    }

    public RssiPeriodicExampleActivity_ViewBinding(final RssiPeriodicExampleActivity rssiPeriodicExampleActivity, View view) {
        this.target = rssiPeriodicExampleActivity;
        rssiPeriodicExampleActivity.connectionStateView = (TextView) Utils.findRequiredViewAsType(view, R.id.connection_state, "field 'connectionStateView'", TextView.class);
        rssiPeriodicExampleActivity.rssiView = (TextView) Utils.findRequiredViewAsType(view, R.id.rssi, "field 'rssiView'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.connect_toggle, "field 'connectButton' and method 'onConnectToggleClick'");
        rssiPeriodicExampleActivity.connectButton = (Button) Utils.castView(findRequiredView, R.id.connect_toggle, "field 'connectButton'", Button.class);
        this.view7f0900c3 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.example5_rssi_periodic.RssiPeriodicExampleActivity_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                rssiPeriodicExampleActivity.onConnectToggleClick();
            }
        });
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        RssiPeriodicExampleActivity rssiPeriodicExampleActivity = this.target;
        if (rssiPeriodicExampleActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        rssiPeriodicExampleActivity.connectionStateView = null;
        rssiPeriodicExampleActivity.rssiView = null;
        rssiPeriodicExampleActivity.connectButton = null;
        this.view7f0900c3.setOnClickListener(null);
        this.view7f0900c3 = null;
    }
}
