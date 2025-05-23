package com.baidu.kwgames;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
/* loaded from: classes.dex */
public class PropertyActivity extends AppCompatActivity {
    @OnCheckedChanged({R.id.macro_click})
    public void onMacroClick(SwitchCompat switchCompat, boolean z) {
    }

    public void onMacroClickItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
    }

    @OnCheckedChanged({R.id.normal_click})
    public void onNormalClick(SwitchCompat switchCompat, boolean z) {
    }

    public void onNormalClickItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
    }

    @OnClick({R.id.ok})
    public void onOk() {
    }

    @OnCheckedChanged({R.id.related_mouse})
    public void onRelatedMouse(SwitchCompat switchCompat, boolean z) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_property);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        ((Spinner) findViewById(R.id.macro_click_list)).setSelection(3);
    }

    @OnClick({R.id.cancel})
    public void onCancel() {
        finish();
    }
}
