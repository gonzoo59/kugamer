package com.baidu.kwgames;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
/* loaded from: classes.dex */
public class ChangeModeActivity_ViewBinding implements Unbinder {
    private ChangeModeActivity target;
    private View view7f090091;
    private View view7f09009a;
    private View view7f0901a3;
    private View view7f0901ab;
    private View view7f0901eb;
    private View view7f0901fd;
    private View view7f0901fe;
    private View view7f090220;
    private View view7f090223;
    private View view7f090252;
    private View view7f090253;
    private View view7f0902b3;

    public ChangeModeActivity_ViewBinding(ChangeModeActivity changeModeActivity) {
        this(changeModeActivity, changeModeActivity.getWindow().getDecorView());
    }

    public ChangeModeActivity_ViewBinding(final ChangeModeActivity changeModeActivity, View view) {
        this.target = changeModeActivity;
        changeModeActivity.m_layoutRoot = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.m_layoutRoot, "field 'm_layoutRoot'", RelativeLayout.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.m_touping, "field 'm_checkTouping' and method 'onTouping'");
        changeModeActivity.m_checkTouping = (SwitchCompat) Utils.castView(findRequiredView, R.id.m_touping, "field 'm_checkTouping'", SwitchCompat.class);
        this.view7f090252 = findRequiredView;
        ((CompoundButton) findRequiredView).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.ChangeModeActivity_ViewBinding.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                changeModeActivity.onTouping((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onTouping", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.m_usbAudio, "field 'm_checkUAC' and method 'onUAC'");
        changeModeActivity.m_checkUAC = (SwitchCompat) Utils.castView(findRequiredView2, R.id.m_usbAudio, "field 'm_checkUAC'", SwitchCompat.class);
        this.view7f090253 = findRequiredView2;
        ((CompoundButton) findRequiredView2).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.ChangeModeActivity_ViewBinding.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                changeModeActivity.onUAC((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onUAC", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, R.id.m_lol, "field 'm_checkLOL' and method 'onLOL'");
        changeModeActivity.m_checkLOL = (SwitchCompat) Utils.castView(findRequiredView3, R.id.m_lol, "field 'm_checkLOL'", SwitchCompat.class);
        this.view7f090220 = findRequiredView3;
        ((CompoundButton) findRequiredView3).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.ChangeModeActivity_ViewBinding.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                changeModeActivity.onLOL((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onLOL", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, R.id.m_minecraft, "field 'm_checkMineCraft' and method 'onMinecraft'");
        changeModeActivity.m_checkMineCraft = (SwitchCompat) Utils.castView(findRequiredView4, R.id.m_minecraft, "field 'm_checkMineCraft'", SwitchCompat.class);
        this.view7f090223 = findRequiredView4;
        ((CompoundButton) findRequiredView4).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.ChangeModeActivity_ViewBinding.4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                changeModeActivity.onMinecraft((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onMinecraft", 0, SwitchCompat.class), z);
            }
        });
        changeModeActivity.m_checkHardtimer = (SwitchCompat) Utils.findRequiredViewAsType(view, R.id.m_hardtimer, "field 'm_checkHardtimer'", SwitchCompat.class);
        changeModeActivity.m_linerCastInfo = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.m_linerCastInfo, "field 'm_linerCastInfo'", LinearLayout.class);
        View findRequiredView5 = Utils.findRequiredView(view, R.id.m_checkToDisplay, "field 'm_checkCastToDisplay' and method 'onCastToDisplay'");
        changeModeActivity.m_checkCastToDisplay = (SwitchCompat) Utils.castView(findRequiredView5, R.id.m_checkToDisplay, "field 'm_checkCastToDisplay'", SwitchCompat.class);
        this.view7f0901fe = findRequiredView5;
        ((CompoundButton) findRequiredView5).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.ChangeModeActivity_ViewBinding.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                changeModeActivity.onCastToDisplay((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onCastToDisplay", 0, SwitchCompat.class), z);
            }
        });
        changeModeActivity.m_textDisplayRes = (TextView) Utils.findRequiredViewAsType(view, R.id.displayRes, "field 'm_textDisplayRes'", TextView.class);
        changeModeActivity.m_textDisplayResMulti = (TextView) Utils.findRequiredViewAsType(view, R.id.textMulti, "field 'm_textDisplayResMulti'", TextView.class);
        changeModeActivity.m_editDisplayX = (EditText) Utils.findRequiredViewAsType(view, R.id.edit_display_x, "field 'm_editDisplayX'", EditText.class);
        changeModeActivity.m_editDisplayY = (EditText) Utils.findRequiredViewAsType(view, R.id.edit_display_y, "field 'm_editDisplayY'", EditText.class);
        View findRequiredView6 = Utils.findRequiredView(view, R.id.btn_touping_question, "field 'm_btnToupingVideo' and method 'onToupingVideo'");
        changeModeActivity.m_btnToupingVideo = (Button) Utils.castView(findRequiredView6, R.id.btn_touping_question, "field 'm_btnToupingVideo'", Button.class);
        this.view7f090091 = findRequiredView6;
        findRequiredView6.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.ChangeModeActivity_ViewBinding.6
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                changeModeActivity.onToupingVideo();
            }
        });
        View findRequiredView7 = Utils.findRequiredView(view, R.id.m_btnUSBSpeed, "field 'm_btnUSBSpeed' and method 'onUSBSpeed'");
        changeModeActivity.m_btnUSBSpeed = (Button) Utils.castView(findRequiredView7, R.id.m_btnUSBSpeed, "field 'm_btnUSBSpeed'", Button.class);
        this.view7f0901eb = findRequiredView7;
        findRequiredView7.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.ChangeModeActivity_ViewBinding.7
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                changeModeActivity.onUSBSpeed();
            }
        });
        View findRequiredView8 = Utils.findRequiredView(view, R.id.m_checkRandomBleName, "field 'm_checkRandomBleName' and method 'onCheckRandomBleName'");
        changeModeActivity.m_checkRandomBleName = (SwitchCompat) Utils.castView(findRequiredView8, R.id.m_checkRandomBleName, "field 'm_checkRandomBleName'", SwitchCompat.class);
        this.view7f0901fd = findRequiredView8;
        ((CompoundButton) findRequiredView8).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.ChangeModeActivity_ViewBinding.8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                changeModeActivity.onCheckRandomBleName((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onCheckRandomBleName", 0, SwitchCompat.class), z);
            }
        });
        changeModeActivity.m_linerRandomBleName = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.m_linerRandomBleName, "field 'm_linerRandomBleName'", LinearLayout.class);
        changeModeActivity.m_editRandownBleName = (EditText) Utils.findRequiredViewAsType(view, R.id.m_editRandownBleName, "field 'm_editRandownBleName'", EditText.class);
        View findRequiredView9 = Utils.findRequiredView(view, R.id.m_btnGenerateBleName, "field 'm_btnGenerateBleName' and method 'onGenerateBleName'");
        changeModeActivity.m_btnGenerateBleName = (Button) Utils.castView(findRequiredView9, R.id.m_btnGenerateBleName, "field 'm_btnGenerateBleName'", Button.class);
        this.view7f0901a3 = findRequiredView9;
        findRequiredView9.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.ChangeModeActivity_ViewBinding.9
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                changeModeActivity.onGenerateBleName();
            }
        });
        View findRequiredView10 = Utils.findRequiredView(view, R.id.cancel, "method 'onCancel'");
        this.view7f09009a = findRequiredView10;
        findRequiredView10.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.ChangeModeActivity_ViewBinding.10
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                changeModeActivity.onCancel();
            }
        });
        View findRequiredView11 = Utils.findRequiredView(view, R.id.ok, "method 'onOk'");
        this.view7f0902b3 = findRequiredView11;
        findRequiredView11.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.ChangeModeActivity_ViewBinding.11
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                changeModeActivity.onOk();
            }
        });
        View findRequiredView12 = Utils.findRequiredView(view, R.id.m_btnModeVideo, "method 'onModeVideo'");
        this.view7f0901ab = findRequiredView12;
        findRequiredView12.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.ChangeModeActivity_ViewBinding.12
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                changeModeActivity.onModeVideo();
            }
        });
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        ChangeModeActivity changeModeActivity = this.target;
        if (changeModeActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        changeModeActivity.m_layoutRoot = null;
        changeModeActivity.m_checkTouping = null;
        changeModeActivity.m_checkUAC = null;
        changeModeActivity.m_checkLOL = null;
        changeModeActivity.m_checkMineCraft = null;
        changeModeActivity.m_checkHardtimer = null;
        changeModeActivity.m_linerCastInfo = null;
        changeModeActivity.m_checkCastToDisplay = null;
        changeModeActivity.m_textDisplayRes = null;
        changeModeActivity.m_textDisplayResMulti = null;
        changeModeActivity.m_editDisplayX = null;
        changeModeActivity.m_editDisplayY = null;
        changeModeActivity.m_btnToupingVideo = null;
        changeModeActivity.m_btnUSBSpeed = null;
        changeModeActivity.m_checkRandomBleName = null;
        changeModeActivity.m_linerRandomBleName = null;
        changeModeActivity.m_editRandownBleName = null;
        changeModeActivity.m_btnGenerateBleName = null;
        ((CompoundButton) this.view7f090252).setOnCheckedChangeListener(null);
        this.view7f090252 = null;
        ((CompoundButton) this.view7f090253).setOnCheckedChangeListener(null);
        this.view7f090253 = null;
        ((CompoundButton) this.view7f090220).setOnCheckedChangeListener(null);
        this.view7f090220 = null;
        ((CompoundButton) this.view7f090223).setOnCheckedChangeListener(null);
        this.view7f090223 = null;
        ((CompoundButton) this.view7f0901fe).setOnCheckedChangeListener(null);
        this.view7f0901fe = null;
        this.view7f090091.setOnClickListener(null);
        this.view7f090091 = null;
        this.view7f0901eb.setOnClickListener(null);
        this.view7f0901eb = null;
        ((CompoundButton) this.view7f0901fd).setOnCheckedChangeListener(null);
        this.view7f0901fd = null;
        this.view7f0901a3.setOnClickListener(null);
        this.view7f0901a3 = null;
        this.view7f09009a.setOnClickListener(null);
        this.view7f09009a = null;
        this.view7f0902b3.setOnClickListener(null);
        this.view7f0902b3 = null;
        this.view7f0901ab.setOnClickListener(null);
        this.view7f0901ab = null;
    }
}
