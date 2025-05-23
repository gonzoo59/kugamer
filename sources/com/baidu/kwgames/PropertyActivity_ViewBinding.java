package com.baidu.kwgames;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import androidx.appcompat.widget.SwitchCompat;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
/* loaded from: classes.dex */
public class PropertyActivity_ViewBinding implements Unbinder {
    private PropertyActivity target;
    private View view7f09009a;
    private View view7f090254;
    private View view7f090255;
    private View view7f0902ac;
    private View view7f0902ad;
    private View view7f0902b3;
    private View view7f0902e4;

    public PropertyActivity_ViewBinding(PropertyActivity propertyActivity) {
        this(propertyActivity, propertyActivity.getWindow().getDecorView());
    }

    public PropertyActivity_ViewBinding(final PropertyActivity propertyActivity, View view) {
        this.target = propertyActivity;
        View findRequiredView = Utils.findRequiredView(view, R.id.normal_click, "method 'onNormalClick'");
        this.view7f0902ac = findRequiredView;
        ((CompoundButton) findRequiredView).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.PropertyActivity_ViewBinding.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                propertyActivity.onNormalClick((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onNormalClick", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.related_mouse, "method 'onRelatedMouse'");
        this.view7f0902e4 = findRequiredView2;
        ((CompoundButton) findRequiredView2).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.PropertyActivity_ViewBinding.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                propertyActivity.onRelatedMouse((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onRelatedMouse", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, R.id.macro_click, "method 'onMacroClick'");
        this.view7f090254 = findRequiredView3;
        ((CompoundButton) findRequiredView3).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.PropertyActivity_ViewBinding.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                propertyActivity.onMacroClick((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onMacroClick", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, R.id.cancel, "method 'onCancel'");
        this.view7f09009a = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.PropertyActivity_ViewBinding.4
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                propertyActivity.onCancel();
            }
        });
        View findRequiredView5 = Utils.findRequiredView(view, R.id.ok, "method 'onOk'");
        this.view7f0902b3 = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.PropertyActivity_ViewBinding.5
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                propertyActivity.onOk();
            }
        });
        View findRequiredView6 = Utils.findRequiredView(view, R.id.normal_click_list, "method 'onNormalClickItemSelected'");
        this.view7f0902ad = findRequiredView6;
        ((AdapterView) findRequiredView6).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.baidu.kwgames.PropertyActivity_ViewBinding.6
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view2, int i, long j) {
                propertyActivity.onNormalClickItemSelected(adapterView, view2, i, j);
            }
        });
        View findRequiredView7 = Utils.findRequiredView(view, R.id.macro_click_list, "method 'onMacroClickItemSelected'");
        this.view7f090255 = findRequiredView7;
        ((AdapterView) findRequiredView7).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.baidu.kwgames.PropertyActivity_ViewBinding.7
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view2, int i, long j) {
                propertyActivity.onMacroClickItemSelected(adapterView, view2, i, j);
            }
        });
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        if (this.target == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        ((CompoundButton) this.view7f0902ac).setOnCheckedChangeListener(null);
        this.view7f0902ac = null;
        ((CompoundButton) this.view7f0902e4).setOnCheckedChangeListener(null);
        this.view7f0902e4 = null;
        ((CompoundButton) this.view7f090254).setOnCheckedChangeListener(null);
        this.view7f090254 = null;
        this.view7f09009a.setOnClickListener(null);
        this.view7f09009a = null;
        this.view7f0902b3.setOnClickListener(null);
        this.view7f0902b3 = null;
        ((AdapterView) this.view7f0902ad).setOnItemSelectedListener(null);
        this.view7f0902ad = null;
        ((AdapterView) this.view7f090255).setOnItemSelectedListener(null);
        this.view7f090255 = null;
    }
}
