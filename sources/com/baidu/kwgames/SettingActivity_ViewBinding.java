package com.baidu.kwgames;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
/* loaded from: classes.dex */
public class SettingActivity_ViewBinding implements Unbinder {
    private SettingActivity target;
    private View view7f090067;
    private View view7f090075;
    private View view7f090093;
    private View view7f09009a;
    private View view7f0900b3;
    private View view7f0900b4;
    private View view7f090154;
    private View view7f09018a;
    private View view7f09018f;
    private View view7f090191;
    private View view7f090192;
    private View view7f090195;
    private View view7f090196;
    private View view7f09019a;
    private View view7f09019b;
    private View view7f0901a4;
    private View view7f0901ae;
    private View view7f0901cb;
    private View view7f0901d0;
    private View view7f0901ec;
    private View view7f0901ed;
    private View view7f09025b;
    private View view7f0902b3;
    private View view7f0902cf;
    private View view7f0902d8;
    private View view7f090328;
    private View view7f09032e;
    private View view7f0903cf;

    public SettingActivity_ViewBinding(SettingActivity settingActivity) {
        this(settingActivity, settingActivity.getWindow().getDecorView());
    }

    public SettingActivity_ViewBinding(final SettingActivity settingActivity, View view) {
        this.target = settingActivity;
        settingActivity.m_layoutRoot = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.m_layoutRoot, "field 'm_layoutRoot'", RelativeLayout.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.sprint, "field 'mSprint' and method 'onSprint'");
        settingActivity.mSprint = (SwitchCompat) Utils.castView(findRequiredView, R.id.sprint, "field 'mSprint'", SwitchCompat.class);
        this.view7f09032e = findRequiredView;
        ((CompoundButton) findRequiredView).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                settingActivity.onSprint((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onSprint", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.m_btnADDownAutoAD, "field 'm_btnADDownAutoAD' and method 'onButtonADDownAutoAD'");
        settingActivity.m_btnADDownAutoAD = (Button) Utils.castView(findRequiredView2, R.id.m_btnADDownAutoAD, "field 'm_btnADDownAutoAD'", Button.class);
        this.view7f09018a = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onButtonADDownAutoAD();
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, R.id.m_btnAutoADSpeed, "field 'm_btnAutoADSpeed' and method 'onButtonADDownAutoADSpeed'");
        settingActivity.m_btnAutoADSpeed = (Button) Utils.castView(findRequiredView3, R.id.m_btnAutoADSpeed, "field 'm_btnAutoADSpeed'", Button.class);
        this.view7f09018f = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onButtonADDownAutoADSpeed();
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, R.id.press_gun, "field 'mPressGun' and method 'onPressGun'");
        settingActivity.mPressGun = (SwitchCompat) Utils.castView(findRequiredView4, R.id.press_gun, "field 'mPressGun'", SwitchCompat.class);
        this.view7f0902cf = findRequiredView4;
        ((CompoundButton) findRequiredView4).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                settingActivity.onPressGun((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onPressGun", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView5 = Utils.findRequiredView(view, R.id.auto_pointer, "field 'mAutoPointer' and method 'onAutoPointer'");
        settingActivity.mAutoPointer = (SwitchCompat) Utils.castView(findRequiredView5, R.id.auto_pointer, "field 'mAutoPointer'", SwitchCompat.class);
        this.view7f090075 = findRequiredView5;
        ((CompoundButton) findRequiredView5).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                settingActivity.onAutoPointer((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onAutoPointer", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView6 = Utils.findRequiredView(view, R.id.special_version, "field 'mSpecialVersion' and method 'onSpecialVersion'");
        settingActivity.mSpecialVersion = (SwitchCompat) Utils.castView(findRequiredView6, R.id.special_version, "field 'mSpecialVersion'", SwitchCompat.class);
        this.view7f090328 = findRequiredView6;
        ((CompoundButton) findRequiredView6).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.6
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                settingActivity.onSpecialVersion((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onSpecialVersion", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView7 = Utils.findRequiredView(view, R.id.always_jump, "field 'mAlwaysJump' and method 'onAlwaysJump'");
        settingActivity.mAlwaysJump = (SwitchCompat) Utils.castView(findRequiredView7, R.id.always_jump, "field 'mAlwaysJump'", SwitchCompat.class);
        this.view7f090067 = findRequiredView7;
        ((CompoundButton) findRequiredView7).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                settingActivity.onAlwaysJump((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onAlwaysJump", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView8 = Utils.findRequiredView(view, R.id.virtual_mouse, "field 'mVirtualMouse' and method 'onVirtualMouse'");
        settingActivity.mVirtualMouse = (SwitchCompat) Utils.castView(findRequiredView8, R.id.virtual_mouse, "field 'mVirtualMouse'", SwitchCompat.class);
        this.view7f0903cf = findRequiredView8;
        ((CompoundButton) findRequiredView8).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                settingActivity.onVirtualMouse((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onVirtualMouse", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView9 = Utils.findRequiredView(view, R.id.btn_virtual_mouse_center, "field 'm_btnVirtualMouseCenter' and method 'onVMouseCenter'");
        settingActivity.m_btnVirtualMouseCenter = (Button) Utils.castView(findRequiredView9, R.id.btn_virtual_mouse_center, "field 'm_btnVirtualMouseCenter'", Button.class);
        this.view7f090093 = findRequiredView9;
        findRequiredView9.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.9
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onVMouseCenter();
            }
        });
        View findRequiredView10 = Utils.findRequiredView(view, R.id.m_btnUSBVMouseHz, "field 'm_btnUSBVMouseHz' and method 'onUSBVMouseHz'");
        settingActivity.m_btnUSBVMouseHz = (Button) Utils.castView(findRequiredView10, R.id.m_btnUSBVMouseHz, "field 'm_btnUSBVMouseHz'", Button.class);
        this.view7f0901ec = findRequiredView10;
        findRequiredView10.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.10
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onUSBVMouseHz();
            }
        });
        View findRequiredView11 = Utils.findRequiredView(view, R.id.m_btnVMouseThrowL, "field 'm_btnVMouseThrowL' and method 'onVMouseThrowLeft'");
        settingActivity.m_btnVMouseThrowL = (Button) Utils.castView(findRequiredView11, R.id.m_btnVMouseThrowL, "field 'm_btnVMouseThrowL'", Button.class);
        this.view7f0901ed = findRequiredView11;
        findRequiredView11.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.11
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onVMouseThrowLeft();
            }
        });
        View findRequiredView12 = Utils.findRequiredView(view, R.id.hold_ctrl_in_fire_repeat, "field 'm_checkCtrlHoldRepeat' and method 'onHoldCtrlInFireRepeat'");
        settingActivity.m_checkCtrlHoldRepeat = (SwitchCompat) Utils.castView(findRequiredView12, R.id.hold_ctrl_in_fire_repeat, "field 'm_checkCtrlHoldRepeat'", SwitchCompat.class);
        this.view7f090154 = findRequiredView12;
        ((CompoundButton) findRequiredView12).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.12
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                settingActivity.onHoldCtrlInFireRepeat((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onHoldCtrlInFireRepeat", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView13 = Utils.findRequiredView(view, R.id.m_btnCtrlRepeatSpeed, "field 'm_btnCtrlRepeatSpeed' and method 'onButtonCtrlRepeatSpeed'");
        settingActivity.m_btnCtrlRepeatSpeed = (Button) Utils.castView(findRequiredView13, R.id.m_btnCtrlRepeatSpeed, "field 'm_btnCtrlRepeatSpeed'", Button.class);
        this.view7f09019a = findRequiredView13;
        findRequiredView13.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.13
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onButtonCtrlRepeatSpeed();
            }
        });
        View findRequiredView14 = Utils.findRequiredView(view, R.id.click_e_in_fire_repeat, "field 'm_checkEHoldRepeat' and method 'onClickEInFireRepeat'");
        settingActivity.m_checkEHoldRepeat = (SwitchCompat) Utils.castView(findRequiredView14, R.id.click_e_in_fire_repeat, "field 'm_checkEHoldRepeat'", SwitchCompat.class);
        this.view7f0900b3 = findRequiredView14;
        ((CompoundButton) findRequiredView14).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.14
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                settingActivity.onClickEInFireRepeat((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onClickEInFireRepeat", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView15 = Utils.findRequiredView(view, R.id.click_q_in_fire_repeat, "field 'm_checkQHoldRepeat' and method 'onClickQInFireRepeat'");
        settingActivity.m_checkQHoldRepeat = (SwitchCompat) Utils.castView(findRequiredView15, R.id.click_q_in_fire_repeat, "field 'm_checkQHoldRepeat'", SwitchCompat.class);
        this.view7f0900b4 = findRequiredView15;
        ((CompoundButton) findRequiredView15).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.15
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                settingActivity.onClickQInFireRepeat((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onClickQInFireRepeat", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView16 = Utils.findRequiredView(view, R.id.m_btnShiftToWalk, "field 'm_btnShiftToWalk' and method 'onClickShiftToWalk'");
        settingActivity.m_btnShiftToWalk = (SwitchCompat) Utils.castView(findRequiredView16, R.id.m_btnShiftToWalk, "field 'm_btnShiftToWalk'", SwitchCompat.class);
        this.view7f0901cb = findRequiredView16;
        ((CompoundButton) findRequiredView16).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.16
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                settingActivity.onClickShiftToWalk((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onClickShiftToWalk", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView17 = Utils.findRequiredView(view, R.id.m_btnMouseWheelSwGun, "field 'm_btnMouseWheelSwGun' and method 'onClickMouseWheelSwGun'");
        settingActivity.m_btnMouseWheelSwGun = (SwitchCompat) Utils.castView(findRequiredView17, R.id.m_btnMouseWheelSwGun, "field 'm_btnMouseWheelSwGun'", SwitchCompat.class);
        this.view7f0901ae = findRequiredView17;
        ((CompoundButton) findRequiredView17).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.17
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                settingActivity.onClickMouseWheelSwGun((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onClickMouseWheelSwGun", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView18 = Utils.findRequiredView(view, R.id.magnification, "field 'mMagnification' and method 'onMagnification'");
        settingActivity.mMagnification = (SwitchCompat) Utils.castView(findRequiredView18, R.id.magnification, "field 'mMagnification'", SwitchCompat.class);
        this.view7f09025b = findRequiredView18;
        ((CompoundButton) findRequiredView18).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.18
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                settingActivity.onMagnification((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onMagnification", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView19 = Utils.findRequiredView(view, R.id.qe_open_scope, "field 'mQeOpenScope' and method 'onEeOpenScope'");
        settingActivity.mQeOpenScope = (AppCompatButton) Utils.castView(findRequiredView19, R.id.qe_open_scope, "field 'mQeOpenScope'", AppCompatButton.class);
        this.view7f0902d8 = findRequiredView19;
        findRequiredView19.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.19
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onEeOpenScope();
            }
        });
        View findRequiredView20 = Utils.findRequiredView(view, R.id.m_btnCFDynamicContinues, "field 'm_checkCFDynamicContinues' and method 'onCFDynamicContinues'");
        settingActivity.m_checkCFDynamicContinues = (SwitchCompat) Utils.castView(findRequiredView20, R.id.m_btnCFDynamicContinues, "field 'm_checkCFDynamicContinues'", SwitchCompat.class);
        this.view7f090195 = findRequiredView20;
        ((CompoundButton) findRequiredView20).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.20
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                settingActivity.onCFDynamicContinues((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onCFDynamicContinues", 0, SwitchCompat.class), z);
            }
        });
        View findRequiredView21 = Utils.findRequiredView(view, R.id.m_btnHPVolPlus, "field 'm_btnHPVolPlus' and method 'onButtonHPVol'");
        settingActivity.m_btnHPVolPlus = (Button) Utils.castView(findRequiredView21, R.id.m_btnHPVolPlus, "field 'm_btnHPVolPlus'", Button.class);
        this.view7f0901a4 = findRequiredView21;
        findRequiredView21.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.21
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onButtonHPVol();
            }
        });
        View findRequiredView22 = Utils.findRequiredView(view, R.id.m_btnShunfenger, "field 'm_btnShunfenger' and method 'onButtonShunfenger'");
        settingActivity.m_btnShunfenger = (Button) Utils.castView(findRequiredView22, R.id.m_btnShunfenger, "field 'm_btnShunfenger'", Button.class);
        this.view7f0901d0 = findRequiredView22;
        findRequiredView22.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.22
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onButtonShunfenger();
            }
        });
        settingActivity.mUpdateBtn = (Button) Utils.findRequiredViewAsType(view, R.id.update_btn, "field 'mUpdateBtn'", Button.class);
        View findRequiredView23 = Utils.findRequiredView(view, R.id.m_btnChangeMode, "field 'm_btnChangeMode' and method 'onChangeMode'");
        settingActivity.m_btnChangeMode = (Button) Utils.castView(findRequiredView23, R.id.m_btnChangeMode, "field 'm_btnChangeMode'", Button.class);
        this.view7f090196 = findRequiredView23;
        findRequiredView23.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.23
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onChangeMode();
            }
        });
        View findRequiredView24 = Utils.findRequiredView(view, R.id.m_btnBackup, "field 'm_btnBackup' and method 'onBackup'");
        settingActivity.m_btnBackup = (Button) Utils.castView(findRequiredView24, R.id.m_btnBackup, "field 'm_btnBackup'", Button.class);
        this.view7f090192 = findRequiredView24;
        findRequiredView24.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.24
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onBackup();
            }
        });
        View findRequiredView25 = Utils.findRequiredView(view, R.id.m_btnBG, "field 'm_btnBG' and method 'onBG'");
        settingActivity.m_btnBG = (Button) Utils.castView(findRequiredView25, R.id.m_btnBG, "field 'm_btnBG'", Button.class);
        this.view7f090191 = findRequiredView25;
        findRequiredView25.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.25
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onBG();
            }
        });
        View findRequiredView26 = Utils.findRequiredView(view, R.id.m_btnCursor, "field 'm_btnCursor' and method 'onCursor'");
        settingActivity.m_btnCursor = (Button) Utils.castView(findRequiredView26, R.id.m_btnCursor, "field 'm_btnCursor'", Button.class);
        this.view7f09019b = findRequiredView26;
        findRequiredView26.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.26
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onCursor();
            }
        });
        View findRequiredView27 = Utils.findRequiredView(view, R.id.cancel, "method 'onCancel'");
        this.view7f09009a = findRequiredView27;
        findRequiredView27.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.27
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onCancel();
            }
        });
        View findRequiredView28 = Utils.findRequiredView(view, R.id.ok, "method 'onOk'");
        this.view7f0902b3 = findRequiredView28;
        findRequiredView28.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.SettingActivity_ViewBinding.28
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                settingActivity.onOk();
            }
        });
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        SettingActivity settingActivity = this.target;
        if (settingActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        settingActivity.m_layoutRoot = null;
        settingActivity.mSprint = null;
        settingActivity.m_btnADDownAutoAD = null;
        settingActivity.m_btnAutoADSpeed = null;
        settingActivity.mPressGun = null;
        settingActivity.mAutoPointer = null;
        settingActivity.mSpecialVersion = null;
        settingActivity.mAlwaysJump = null;
        settingActivity.mVirtualMouse = null;
        settingActivity.m_btnVirtualMouseCenter = null;
        settingActivity.m_btnUSBVMouseHz = null;
        settingActivity.m_btnVMouseThrowL = null;
        settingActivity.m_checkCtrlHoldRepeat = null;
        settingActivity.m_btnCtrlRepeatSpeed = null;
        settingActivity.m_checkEHoldRepeat = null;
        settingActivity.m_checkQHoldRepeat = null;
        settingActivity.m_btnShiftToWalk = null;
        settingActivity.m_btnMouseWheelSwGun = null;
        settingActivity.mMagnification = null;
        settingActivity.mQeOpenScope = null;
        settingActivity.m_checkCFDynamicContinues = null;
        settingActivity.m_btnHPVolPlus = null;
        settingActivity.m_btnShunfenger = null;
        settingActivity.mUpdateBtn = null;
        settingActivity.m_btnChangeMode = null;
        settingActivity.m_btnBackup = null;
        settingActivity.m_btnBG = null;
        settingActivity.m_btnCursor = null;
        ((CompoundButton) this.view7f09032e).setOnCheckedChangeListener(null);
        this.view7f09032e = null;
        this.view7f09018a.setOnClickListener(null);
        this.view7f09018a = null;
        this.view7f09018f.setOnClickListener(null);
        this.view7f09018f = null;
        ((CompoundButton) this.view7f0902cf).setOnCheckedChangeListener(null);
        this.view7f0902cf = null;
        ((CompoundButton) this.view7f090075).setOnCheckedChangeListener(null);
        this.view7f090075 = null;
        ((CompoundButton) this.view7f090328).setOnCheckedChangeListener(null);
        this.view7f090328 = null;
        ((CompoundButton) this.view7f090067).setOnCheckedChangeListener(null);
        this.view7f090067 = null;
        ((CompoundButton) this.view7f0903cf).setOnCheckedChangeListener(null);
        this.view7f0903cf = null;
        this.view7f090093.setOnClickListener(null);
        this.view7f090093 = null;
        this.view7f0901ec.setOnClickListener(null);
        this.view7f0901ec = null;
        this.view7f0901ed.setOnClickListener(null);
        this.view7f0901ed = null;
        ((CompoundButton) this.view7f090154).setOnCheckedChangeListener(null);
        this.view7f090154 = null;
        this.view7f09019a.setOnClickListener(null);
        this.view7f09019a = null;
        ((CompoundButton) this.view7f0900b3).setOnCheckedChangeListener(null);
        this.view7f0900b3 = null;
        ((CompoundButton) this.view7f0900b4).setOnCheckedChangeListener(null);
        this.view7f0900b4 = null;
        ((CompoundButton) this.view7f0901cb).setOnCheckedChangeListener(null);
        this.view7f0901cb = null;
        ((CompoundButton) this.view7f0901ae).setOnCheckedChangeListener(null);
        this.view7f0901ae = null;
        ((CompoundButton) this.view7f09025b).setOnCheckedChangeListener(null);
        this.view7f09025b = null;
        this.view7f0902d8.setOnClickListener(null);
        this.view7f0902d8 = null;
        ((CompoundButton) this.view7f090195).setOnCheckedChangeListener(null);
        this.view7f090195 = null;
        this.view7f0901a4.setOnClickListener(null);
        this.view7f0901a4 = null;
        this.view7f0901d0.setOnClickListener(null);
        this.view7f0901d0 = null;
        this.view7f090196.setOnClickListener(null);
        this.view7f090196 = null;
        this.view7f090192.setOnClickListener(null);
        this.view7f090192 = null;
        this.view7f090191.setOnClickListener(null);
        this.view7f090191 = null;
        this.view7f09019b.setOnClickListener(null);
        this.view7f09019b = null;
        this.view7f09009a.setOnClickListener(null);
        this.view7f09009a = null;
        this.view7f0902b3.setOnClickListener(null);
        this.view7f0902b3 = null;
    }
}
