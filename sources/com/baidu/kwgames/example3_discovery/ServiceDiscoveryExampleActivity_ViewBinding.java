package com.baidu.kwgames.example3_discovery;

import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.baidu.kwgames.R;
/* loaded from: classes.dex */
public class ServiceDiscoveryExampleActivity_ViewBinding implements Unbinder {
    private ServiceDiscoveryExampleActivity target;
    private View view7f0900c1;

    public ServiceDiscoveryExampleActivity_ViewBinding(ServiceDiscoveryExampleActivity serviceDiscoveryExampleActivity) {
        this(serviceDiscoveryExampleActivity, serviceDiscoveryExampleActivity.getWindow().getDecorView());
    }

    public ServiceDiscoveryExampleActivity_ViewBinding(final ServiceDiscoveryExampleActivity serviceDiscoveryExampleActivity, View view) {
        this.target = serviceDiscoveryExampleActivity;
        View findRequiredView = Utils.findRequiredView(view, R.id.connect, "field 'connectButton' and method 'onConnectToggleClick'");
        serviceDiscoveryExampleActivity.connectButton = (Button) Utils.castView(findRequiredView, R.id.connect, "field 'connectButton'", Button.class);
        this.view7f0900c1 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.example3_discovery.ServiceDiscoveryExampleActivity_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                serviceDiscoveryExampleActivity.onConnectToggleClick();
            }
        });
        serviceDiscoveryExampleActivity.recyclerView = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.scan_results, "field 'recyclerView'", RecyclerView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        ServiceDiscoveryExampleActivity serviceDiscoveryExampleActivity = this.target;
        if (serviceDiscoveryExampleActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        serviceDiscoveryExampleActivity.connectButton = null;
        serviceDiscoveryExampleActivity.recyclerView = null;
        this.view7f0900c1.setOnClickListener(null);
        this.view7f0900c1 = null;
    }
}
