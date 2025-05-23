package com.baidu.kwgames.example4_characteristic.advanced;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.baidu.kwgames.R;
/* loaded from: classes.dex */
public class AdvancedCharacteristicOperationExampleActivity_ViewBinding implements Unbinder {
    private AdvancedCharacteristicOperationExampleActivity target;

    public AdvancedCharacteristicOperationExampleActivity_ViewBinding(AdvancedCharacteristicOperationExampleActivity advancedCharacteristicOperationExampleActivity) {
        this(advancedCharacteristicOperationExampleActivity, advancedCharacteristicOperationExampleActivity.getWindow().getDecorView());
    }

    public AdvancedCharacteristicOperationExampleActivity_ViewBinding(AdvancedCharacteristicOperationExampleActivity advancedCharacteristicOperationExampleActivity, View view) {
        this.target = advancedCharacteristicOperationExampleActivity;
        advancedCharacteristicOperationExampleActivity.connectButton = (Button) Utils.findRequiredViewAsType(view, R.id.connect, "field 'connectButton'", Button.class);
        advancedCharacteristicOperationExampleActivity.readOutputView = (TextView) Utils.findRequiredViewAsType(view, R.id.read_output, "field 'readOutputView'", TextView.class);
        advancedCharacteristicOperationExampleActivity.readHexOutputView = (TextView) Utils.findRequiredViewAsType(view, R.id.read_hex_output, "field 'readHexOutputView'", TextView.class);
        advancedCharacteristicOperationExampleActivity.writeInput = (TextView) Utils.findRequiredViewAsType(view, R.id.write_input, "field 'writeInput'", TextView.class);
        advancedCharacteristicOperationExampleActivity.compatOnlyWarningTextView = (TextView) Utils.findRequiredViewAsType(view, R.id.compat_only_warning, "field 'compatOnlyWarningTextView'", TextView.class);
        advancedCharacteristicOperationExampleActivity.readButton = (Button) Utils.findRequiredViewAsType(view, R.id.read, "field 'readButton'", Button.class);
        advancedCharacteristicOperationExampleActivity.writeButton = (Button) Utils.findRequiredViewAsType(view, R.id.write, "field 'writeButton'", Button.class);
        advancedCharacteristicOperationExampleActivity.notifyButton = (Button) Utils.findRequiredViewAsType(view, R.id.notify, "field 'notifyButton'", Button.class);
        advancedCharacteristicOperationExampleActivity.indicateButton = (Button) Utils.findRequiredViewAsType(view, R.id.indicate, "field 'indicateButton'", Button.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        AdvancedCharacteristicOperationExampleActivity advancedCharacteristicOperationExampleActivity = this.target;
        if (advancedCharacteristicOperationExampleActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        advancedCharacteristicOperationExampleActivity.connectButton = null;
        advancedCharacteristicOperationExampleActivity.readOutputView = null;
        advancedCharacteristicOperationExampleActivity.readHexOutputView = null;
        advancedCharacteristicOperationExampleActivity.writeInput = null;
        advancedCharacteristicOperationExampleActivity.compatOnlyWarningTextView = null;
        advancedCharacteristicOperationExampleActivity.readButton = null;
        advancedCharacteristicOperationExampleActivity.writeButton = null;
        advancedCharacteristicOperationExampleActivity.notifyButton = null;
        advancedCharacteristicOperationExampleActivity.indicateButton = null;
    }
}
