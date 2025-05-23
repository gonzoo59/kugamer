package com.baidu.kwgames.example2_connection;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.baidu.kwgames.R;
/* loaded from: classes.dex */
public class ConnectionExampleActivity_ViewBinding implements Unbinder {
    private ConnectionExampleActivity target;
    private View view7f0900c3;
    private View view7f090317;

    public ConnectionExampleActivity_ViewBinding(ConnectionExampleActivity connectionExampleActivity) {
        this(connectionExampleActivity, connectionExampleActivity.getWindow().getDecorView());
    }

    public ConnectionExampleActivity_ViewBinding(final ConnectionExampleActivity connectionExampleActivity, View view) {
        this.target = connectionExampleActivity;
        connectionExampleActivity.connectionStateView = (TextView) Utils.findRequiredViewAsType(view, R.id.connection_state, "field 'connectionStateView'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.connect_toggle, "field 'connectButton' and method 'onConnectToggleClick'");
        connectionExampleActivity.connectButton = (Button) Utils.castView(findRequiredView, R.id.connect_toggle, "field 'connectButton'", Button.class);
        this.view7f0900c3 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.example2_connection.ConnectionExampleActivity_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                connectionExampleActivity.onConnectToggleClick();
            }
        });
        connectionExampleActivity.textMtu = (EditText) Utils.findRequiredViewAsType(view, R.id.newMtu, "field 'textMtu'", EditText.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.set_mtu, "field 'setMtuButton' and method 'onSetMtu'");
        connectionExampleActivity.setMtuButton = (Button) Utils.castView(findRequiredView2, R.id.set_mtu, "field 'setMtuButton'", Button.class);
        this.view7f090317 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.example2_connection.ConnectionExampleActivity_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                connectionExampleActivity.onSetMtu();
            }
        });
        connectionExampleActivity.autoConnectToggleSwitch = (SwitchCompat) Utils.findRequiredViewAsType(view, R.id.autoconnect, "field 'autoConnectToggleSwitch'", SwitchCompat.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        ConnectionExampleActivity connectionExampleActivity = this.target;
        if (connectionExampleActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        connectionExampleActivity.connectionStateView = null;
        connectionExampleActivity.connectButton = null;
        connectionExampleActivity.textMtu = null;
        connectionExampleActivity.setMtuButton = null;
        connectionExampleActivity.autoConnectToggleSwitch = null;
        this.view7f0900c3.setOnClickListener(null);
        this.view7f0900c3 = null;
        this.view7f090317.setOnClickListener(null);
        this.view7f090317 = null;
    }
}
