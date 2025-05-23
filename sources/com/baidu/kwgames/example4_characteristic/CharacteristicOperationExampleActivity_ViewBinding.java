package com.baidu.kwgames.example4_characteristic;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.baidu.kwgames.R;
/* loaded from: classes.dex */
public class CharacteristicOperationExampleActivity_ViewBinding implements Unbinder {
    private CharacteristicOperationExampleActivity target;
    private View view7f0900c1;
    private View view7f0902b1;
    private View view7f0902de;
    private View view7f0903d6;

    public CharacteristicOperationExampleActivity_ViewBinding(CharacteristicOperationExampleActivity characteristicOperationExampleActivity) {
        this(characteristicOperationExampleActivity, characteristicOperationExampleActivity.getWindow().getDecorView());
    }

    public CharacteristicOperationExampleActivity_ViewBinding(final CharacteristicOperationExampleActivity characteristicOperationExampleActivity, View view) {
        this.target = characteristicOperationExampleActivity;
        View findRequiredView = Utils.findRequiredView(view, R.id.connect, "field 'connectButton' and method 'onConnectToggleClick'");
        characteristicOperationExampleActivity.connectButton = (Button) Utils.castView(findRequiredView, R.id.connect, "field 'connectButton'", Button.class);
        this.view7f0900c1 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                characteristicOperationExampleActivity.onConnectToggleClick();
            }
        });
        characteristicOperationExampleActivity.readOutputView = (TextView) Utils.findRequiredViewAsType(view, R.id.read_output, "field 'readOutputView'", TextView.class);
        characteristicOperationExampleActivity.readHexOutputView = (TextView) Utils.findRequiredViewAsType(view, R.id.read_hex_output, "field 'readHexOutputView'", TextView.class);
        characteristicOperationExampleActivity.writeInput = (TextView) Utils.findRequiredViewAsType(view, R.id.write_input, "field 'writeInput'", TextView.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.read, "field 'readButton' and method 'onReadClick'");
        characteristicOperationExampleActivity.readButton = (Button) Utils.castView(findRequiredView2, R.id.read, "field 'readButton'", Button.class);
        this.view7f0902de = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                characteristicOperationExampleActivity.onReadClick();
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, R.id.write, "field 'writeButton' and method 'onWriteClick'");
        characteristicOperationExampleActivity.writeButton = (Button) Utils.castView(findRequiredView3, R.id.write, "field 'writeButton'", Button.class);
        this.view7f0903d6 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                characteristicOperationExampleActivity.onWriteClick();
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, R.id.notify, "field 'notifyButton' and method 'onNotifyClick'");
        characteristicOperationExampleActivity.notifyButton = (Button) Utils.castView(findRequiredView4, R.id.notify, "field 'notifyButton'", Button.class);
        this.view7f0902b1 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.example4_characteristic.CharacteristicOperationExampleActivity_ViewBinding.4
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                characteristicOperationExampleActivity.onNotifyClick();
            }
        });
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        CharacteristicOperationExampleActivity characteristicOperationExampleActivity = this.target;
        if (characteristicOperationExampleActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        characteristicOperationExampleActivity.connectButton = null;
        characteristicOperationExampleActivity.readOutputView = null;
        characteristicOperationExampleActivity.readHexOutputView = null;
        characteristicOperationExampleActivity.writeInput = null;
        characteristicOperationExampleActivity.readButton = null;
        characteristicOperationExampleActivity.writeButton = null;
        characteristicOperationExampleActivity.notifyButton = null;
        this.view7f0900c1.setOnClickListener(null);
        this.view7f0900c1 = null;
        this.view7f0902de.setOnClickListener(null);
        this.view7f0902de = null;
        this.view7f0903d6.setOnClickListener(null);
        this.view7f0903d6 = null;
        this.view7f0902b1.setOnClickListener(null);
        this.view7f0902b1 = null;
    }
}
