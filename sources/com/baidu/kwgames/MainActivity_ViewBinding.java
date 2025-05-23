package com.baidu.kwgames;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.SwitchCompat;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
/* loaded from: classes.dex */
public class MainActivity_ViewBinding implements Unbinder {
    private MainActivity target;
    private View view7f09005c;
    private View view7f090085;
    private View view7f090086;
    private View view7f090087;
    private View view7f090088;
    private View view7f0900c2;
    private View view7f0900fc;
    private View view7f090124;
    private View view7f090127;
    private View view7f090128;
    private View view7f090129;
    private View view7f09012a;
    private View view7f09012b;
    private View view7f09012c;
    private View view7f09012d;
    private View view7f09012e;
    private View view7f09012f;
    private View view7f090130;
    private View view7f090131;
    private View view7f090132;
    private View view7f090133;
    private View view7f090134;
    private View view7f090135;
    private View view7f090189;
    private View view7f09018c;
    private View view7f09018e;
    private View view7f090190;
    private View view7f0901a1;
    private View view7f0901a2;
    private View view7f0901a5;
    private View view7f0901a6;
    private View view7f0901c7;
    private View view7f0901d8;
    private View view7f0901e5;
    private View view7f0901ea;
    private View view7f090318;

    public MainActivity_ViewBinding(MainActivity mainActivity) {
        this(mainActivity, mainActivity.getWindow().getDecorView());
    }

    public MainActivity_ViewBinding(final MainActivity mainActivity, View view) {
        this.target = mainActivity;
        mainActivity.m_layoutRoot = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.m_layoutRoot, "field 'm_layoutRoot'", LinearLayout.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.m_btnID, "field 'm_btnID' and method 'onButtonID'");
        mainActivity.m_btnID = (ImageButton) Utils.castView(findRequiredView, R.id.m_btnID, "field 'm_btnID'", ImageButton.class);
        this.view7f0901a6 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onButtonID();
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.m_btnFacebook, "field 'm_btnFacebook' and method 'onButtonFacebook'");
        mainActivity.m_btnFacebook = (ImageButton) Utils.castView(findRequiredView2, R.id.m_btnFacebook, "field 'm_btnFacebook'", ImageButton.class);
        this.view7f0901a1 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onButtonFacebook();
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, R.id.m_btnAdvice, "field 'm_btnAdvice' and method 'onButtonAdvice'");
        mainActivity.m_btnAdvice = (ImageButton) Utils.castView(findRequiredView3, R.id.m_btnAdvice, "field 'm_btnAdvice'", ImageButton.class);
        this.view7f09018e = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onButtonAdvice();
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, R.id.m_btnHelp, "field 'm_btnHelp', method 'onButtonHelp', and method 'onLongButtonHelp'");
        mainActivity.m_btnHelp = (ImageButton) Utils.castView(findRequiredView4, R.id.m_btnHelp, "field 'm_btnHelp'", ImageButton.class);
        this.view7f0901a5 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.4
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onButtonHelp();
            }
        });
        findRequiredView4.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.5
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onLongButtonHelp();
                return true;
            }
        });
        View findRequiredView5 = Utils.findRequiredView(view, R.id.connect_state, "field 'mConnectState' and method 'onOpenBtSettings'");
        mainActivity.mConnectState = (TextView) Utils.castView(findRequiredView5, R.id.connect_state, "field 'mConnectState'", TextView.class);
        this.view7f0900c2 = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.6
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onOpenBtSettings();
            }
        });
        View findRequiredView6 = Utils.findRequiredView(view, R.id.ai, "field 'mAI' and method 'onAI'");
        mainActivity.mAI = (SwitchCompat) Utils.castView(findRequiredView6, R.id.ai, "field 'mAI'", SwitchCompat.class);
        this.view7f09005c = findRequiredView6;
        ((CompoundButton) findRequiredView6).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                mainActivity.onAI((SwitchCompat) Utils.castParam(compoundButton, "onCheckedChanged", 0, "onAI", 0, SwitchCompat.class), z);
            }
        });
        mainActivity.m_txtAITitle = (TextView) Utils.findRequiredViewAsType(view, R.id.ai_gun_press_title, "field 'm_txtAITitle'", TextView.class);
        View findRequiredView7 = Utils.findRequiredView(view, R.id.btn_ai_question, "field 'm_btnAIQuestion' and method 'onAIQuestion'");
        mainActivity.m_btnAIQuestion = (Button) Utils.castView(findRequiredView7, R.id.btn_ai_question, "field 'm_btnAIQuestion'", Button.class);
        this.view7f090088 = findRequiredView7;
        findRequiredView7.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.8
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onAIQuestion();
            }
        });
        View findRequiredView8 = Utils.findRequiredView(view, R.id.btn_ai_dynamic_onoff, "field 'm_btnAIDynamicOnOff' and method 'onAIDynamicOnOff'");
        mainActivity.m_btnAIDynamicOnOff = (Button) Utils.castView(findRequiredView8, R.id.btn_ai_dynamic_onoff, "field 'm_btnAIDynamicOnOff'", Button.class);
        this.view7f090087 = findRequiredView8;
        findRequiredView8.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.9
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onAIDynamicOnOff();
            }
        });
        View findRequiredView9 = Utils.findRequiredView(view, R.id.btn_ai_continues_shoot_onoff, "field 'm_btnAIContinuesShootOnOff' and method 'onAIContinuesShootOnOff'");
        mainActivity.m_btnAIContinuesShootOnOff = (Button) Utils.castView(findRequiredView9, R.id.btn_ai_continues_shoot_onoff, "field 'm_btnAIContinuesShootOnOff'", Button.class);
        this.view7f090085 = findRequiredView9;
        findRequiredView9.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.10
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onAIContinuesShootOnOff();
            }
        });
        View findRequiredView10 = Utils.findRequiredView(view, R.id.btn_ai_crosshair, "field 'm_btnAICrossHair', method 'onAICrosshair', and method 'onGameLongClick'");
        mainActivity.m_btnAICrossHair = (Button) Utils.castView(findRequiredView10, R.id.btn_ai_crosshair, "field 'm_btnAICrossHair'", Button.class);
        this.view7f090086 = findRequiredView10;
        findRequiredView10.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.11
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onAICrosshair();
            }
        });
        findRequiredView10.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.12
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onGameLongClick();
                return true;
            }
        });
        View findRequiredView11 = Utils.findRequiredView(view, R.id.m_btnAIXScope, "field 'm_btnAIXScope', method 'onAIXScope', and method 'onXScopeLongPress'");
        mainActivity.m_btnAIXScope = (Button) Utils.castView(findRequiredView11, R.id.m_btnAIXScope, "field 'm_btnAIXScope'", Button.class);
        this.view7f09018c = findRequiredView11;
        findRequiredView11.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.13
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onAIXScope();
            }
        });
        findRequiredView11.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.14
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onXScopeLongPress();
                return true;
            }
        });
        View findRequiredView12 = Utils.findRequiredView(view, R.id.m_btn1stBulletOptimize, "field 'm_btn1stBulletOptimize', method 'on1stBulletOptimize', and method 'on1stBulletOptimizeLongPress'");
        mainActivity.m_btn1stBulletOptimize = (Button) Utils.castView(findRequiredView12, R.id.m_btn1stBulletOptimize, "field 'm_btn1stBulletOptimize'", Button.class);
        this.view7f090189 = findRequiredView12;
        findRequiredView12.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.15
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.on1stBulletOptimize();
            }
        });
        findRequiredView12.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.16
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.on1stBulletOptimizeLongPress();
                return true;
            }
        });
        View findRequiredView13 = Utils.findRequiredView(view, R.id.m_btnAutoActiveRun, "field 'm_btnAutoActiveRun', method 'onAutoActiveRun', and method 'onAutoActiveRunLongPress'");
        mainActivity.m_btnAutoActiveRun = (Button) Utils.castView(findRequiredView13, R.id.m_btnAutoActiveRun, "field 'm_btnAutoActiveRun'", Button.class);
        this.view7f090190 = findRequiredView13;
        findRequiredView13.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.17
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onAutoActiveRun();
            }
        });
        findRequiredView13.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.18
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onAutoActiveRunLongPress();
                return true;
            }
        });
        View findRequiredView14 = Utils.findRequiredView(view, R.id.m_btnQianliyan, "field 'm_btnQianliyan', method 'onQianliyanPress', and method 'onQianliyanLongPress'");
        mainActivity.m_btnQianliyan = (Button) Utils.castView(findRequiredView14, R.id.m_btnQianliyan, "field 'm_btnQianliyan'", Button.class);
        this.view7f0901c7 = findRequiredView14;
        findRequiredView14.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.19
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onQianliyanPress();
            }
        });
        findRequiredView14.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.20
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onQianliyanLongPress();
                return true;
            }
        });
        View findRequiredView15 = Utils.findRequiredView(view, R.id.m_btnTPPFPP, "field 'm_btnTPPFPP', method 'onSmartTPPFPP', and method 'onSmartFPPLongPress'");
        mainActivity.m_btnTPPFPP = (Button) Utils.castView(findRequiredView15, R.id.m_btnTPPFPP, "field 'm_btnTPPFPP'", Button.class);
        this.view7f0901e5 = findRequiredView15;
        findRequiredView15.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.21
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onSmartTPPFPP();
            }
        });
        findRequiredView15.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.22
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onSmartFPPLongPress();
                return true;
            }
        });
        View findRequiredView16 = Utils.findRequiredView(view, R.id.m_btnSmartQE, "field 'm_btnSmartQE', method 'onSmartQE', and method 'onSmartQELongPress'");
        mainActivity.m_btnSmartQE = (Button) Utils.castView(findRequiredView16, R.id.m_btnSmartQE, "field 'm_btnSmartQE'", Button.class);
        this.view7f0901d8 = findRequiredView16;
        findRequiredView16.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.23
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onSmartQE();
            }
        });
        findRequiredView16.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.24
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onSmartQELongPress();
                return true;
            }
        });
        mainActivity.mGun1Sensitivity = (AppCompatSeekBar) Utils.findRequiredViewAsType(view, R.id.gun1_sensitivity, "field 'mGun1Sensitivity'", AppCompatSeekBar.class);
        mainActivity.mGun2Sensitivity = (AppCompatSeekBar) Utils.findRequiredViewAsType(view, R.id.gun2_sensitivity, "field 'mGun2Sensitivity'", AppCompatSeekBar.class);
        mainActivity.mMouseMoveSensitivity = (AppCompatSeekBar) Utils.findRequiredViewAsType(view, R.id.mouse_move_sensitivity, "field 'mMouseMoveSensitivity'", AppCompatSeekBar.class);
        View findRequiredView17 = Utils.findRequiredView(view, R.id.m_btnTouchSenseYPercent, "field 'm_btnTouchSenseYPercent' and method 'onTouchSenseYPercent'");
        mainActivity.m_btnTouchSenseYPercent = (Button) Utils.castView(findRequiredView17, R.id.m_btnTouchSenseYPercent, "field 'm_btnTouchSenseYPercent'", Button.class);
        this.view7f0901ea = findRequiredView17;
        findRequiredView17.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.25
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onTouchSenseYPercent();
            }
        });
        View findRequiredView18 = Utils.findRequiredView(view, R.id.m_btnFireSense, "field 'm_btnFireSense' and method 'onFireMouseSense'");
        mainActivity.m_btnFireSense = (Button) Utils.castView(findRequiredView18, R.id.m_btnFireSense, "field 'm_btnFireSense'", Button.class);
        this.view7f0901a2 = findRequiredView18;
        findRequiredView18.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.26
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onFireMouseSense();
            }
        });
        mainActivity.mMousePointerSensitivity = (AppCompatSeekBar) Utils.findRequiredViewAsType(view, R.id.mouse_pointer_sensitivity, "field 'mMousePointerSensitivity'", AppCompatSeekBar.class);
        View findRequiredView19 = Utils.findRequiredView(view, R.id.dynamic_gun, "field 'm_btnDynamicGun' and method 'onDynamicGun'");
        mainActivity.m_btnDynamicGun = (ImageView) Utils.castView(findRequiredView19, R.id.dynamic_gun, "field 'm_btnDynamicGun'", ImageView.class);
        this.view7f0900fc = findRequiredView19;
        findRequiredView19.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.27
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onDynamicGun();
            }
        });
        View findRequiredView20 = Utils.findRequiredView(view, R.id.game1, "method 'onGameClick' and method 'onGameLongClick'");
        this.view7f090124 = findRequiredView20;
        findRequiredView20.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.28
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameClick(view2);
            }
        });
        findRequiredView20.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.29
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onGameLongClick(view2);
                return true;
            }
        });
        View findRequiredView21 = Utils.findRequiredView(view, R.id.game2, "method 'onGameClick' and method 'onGameLongClick'");
        this.view7f090128 = findRequiredView21;
        findRequiredView21.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.30
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameClick(view2);
            }
        });
        findRequiredView21.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.31
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onGameLongClick(view2);
                return true;
            }
        });
        View findRequiredView22 = Utils.findRequiredView(view, R.id.game3, "method 'onGameClick' and method 'onGameLongClick'");
        this.view7f09012a = findRequiredView22;
        findRequiredView22.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.32
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameClick(view2);
            }
        });
        findRequiredView22.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.33
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onGameLongClick(view2);
                return true;
            }
        });
        View findRequiredView23 = Utils.findRequiredView(view, R.id.game4, "method 'onGameClick' and method 'onGameLongClick'");
        this.view7f09012c = findRequiredView23;
        findRequiredView23.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.34
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameClick(view2);
            }
        });
        findRequiredView23.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.35
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onGameLongClick(view2);
                return true;
            }
        });
        View findRequiredView24 = Utils.findRequiredView(view, R.id.game5, "method 'onGameClick' and method 'onGameLongClick'");
        this.view7f09012e = findRequiredView24;
        findRequiredView24.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.36
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameClick(view2);
            }
        });
        findRequiredView24.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.37
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onGameLongClick(view2);
                return true;
            }
        });
        View findRequiredView25 = Utils.findRequiredView(view, R.id.game6, "method 'onGameClick' and method 'onGameLongClick'");
        this.view7f090130 = findRequiredView25;
        findRequiredView25.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.38
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameClick(view2);
            }
        });
        findRequiredView25.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.39
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onGameLongClick(view2);
                return true;
            }
        });
        View findRequiredView26 = Utils.findRequiredView(view, R.id.game7, "method 'onGameClick' and method 'onGameLongClick'");
        this.view7f090132 = findRequiredView26;
        findRequiredView26.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.40
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameClick(view2);
            }
        });
        findRequiredView26.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.41
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onGameLongClick(view2);
                return true;
            }
        });
        View findRequiredView27 = Utils.findRequiredView(view, R.id.game8, "method 'onGameClick' and method 'onGameLongClick'");
        this.view7f090134 = findRequiredView27;
        findRequiredView27.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.42
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameClick(view2);
            }
        });
        findRequiredView27.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.43
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                mainActivity.onGameLongClick(view2);
                return true;
            }
        });
        View findRequiredView28 = Utils.findRequiredView(view, R.id.game1_video, "method 'onGameVideoClick'");
        this.view7f090127 = findRequiredView28;
        findRequiredView28.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.44
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameVideoClick(view2);
            }
        });
        View findRequiredView29 = Utils.findRequiredView(view, R.id.game2_video, "method 'onGameVideoClick'");
        this.view7f090129 = findRequiredView29;
        findRequiredView29.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.45
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameVideoClick(view2);
            }
        });
        View findRequiredView30 = Utils.findRequiredView(view, R.id.game3_video, "method 'onGameVideoClick'");
        this.view7f09012b = findRequiredView30;
        findRequiredView30.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.46
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameVideoClick(view2);
            }
        });
        View findRequiredView31 = Utils.findRequiredView(view, R.id.game4_video, "method 'onGameVideoClick'");
        this.view7f09012d = findRequiredView31;
        findRequiredView31.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.47
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameVideoClick(view2);
            }
        });
        View findRequiredView32 = Utils.findRequiredView(view, R.id.game5_video, "method 'onGameVideoClick'");
        this.view7f09012f = findRequiredView32;
        findRequiredView32.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.48
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameVideoClick(view2);
            }
        });
        View findRequiredView33 = Utils.findRequiredView(view, R.id.game6_video, "method 'onGameVideoClick'");
        this.view7f090131 = findRequiredView33;
        findRequiredView33.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.49
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameVideoClick(view2);
            }
        });
        View findRequiredView34 = Utils.findRequiredView(view, R.id.game7_video, "method 'onGameVideoClick'");
        this.view7f090133 = findRequiredView34;
        findRequiredView34.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.50
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameVideoClick(view2);
            }
        });
        View findRequiredView35 = Utils.findRequiredView(view, R.id.game8_video, "method 'onGameVideoClick'");
        this.view7f090135 = findRequiredView35;
        findRequiredView35.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.51
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onGameVideoClick(view2);
            }
        });
        View findRequiredView36 = Utils.findRequiredView(view, R.id.settings, "method 'onSettings'");
        this.view7f090318 = findRequiredView36;
        findRequiredView36.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.MainActivity_ViewBinding.52
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                mainActivity.onSettings();
            }
        });
        mainActivity.mGames = Utils.listFilteringNull((AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game1, "field 'mGames'", AppCompatButton.class), (AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game2, "field 'mGames'", AppCompatButton.class), (AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game3, "field 'mGames'", AppCompatButton.class), (AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game4, "field 'mGames'", AppCompatButton.class), (AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game5, "field 'mGames'", AppCompatButton.class), (AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game6, "field 'mGames'", AppCompatButton.class), (AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game7, "field 'mGames'", AppCompatButton.class), (AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game8, "field 'mGames'", AppCompatButton.class));
        mainActivity.m_arrLstGameVideo = Utils.listFilteringNull((AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game1_video, "field 'm_arrLstGameVideo'", AppCompatButton.class), (AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game2_video, "field 'm_arrLstGameVideo'", AppCompatButton.class), (AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game3_video, "field 'm_arrLstGameVideo'", AppCompatButton.class), (AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game4_video, "field 'm_arrLstGameVideo'", AppCompatButton.class), (AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game5_video, "field 'm_arrLstGameVideo'", AppCompatButton.class), (AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game6_video, "field 'm_arrLstGameVideo'", AppCompatButton.class), (AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game7_video, "field 'm_arrLstGameVideo'", AppCompatButton.class), (AppCompatButton) Utils.findRequiredViewAsType(view, R.id.game8_video, "field 'm_arrLstGameVideo'", AppCompatButton.class));
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        MainActivity mainActivity = this.target;
        if (mainActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        mainActivity.m_layoutRoot = null;
        mainActivity.m_btnID = null;
        mainActivity.m_btnFacebook = null;
        mainActivity.m_btnAdvice = null;
        mainActivity.m_btnHelp = null;
        mainActivity.mConnectState = null;
        mainActivity.mAI = null;
        mainActivity.m_txtAITitle = null;
        mainActivity.m_btnAIQuestion = null;
        mainActivity.m_btnAIDynamicOnOff = null;
        mainActivity.m_btnAIContinuesShootOnOff = null;
        mainActivity.m_btnAICrossHair = null;
        mainActivity.m_btnAIXScope = null;
        mainActivity.m_btn1stBulletOptimize = null;
        mainActivity.m_btnAutoActiveRun = null;
        mainActivity.m_btnQianliyan = null;
        mainActivity.m_btnTPPFPP = null;
        mainActivity.m_btnSmartQE = null;
        mainActivity.mGun1Sensitivity = null;
        mainActivity.mGun2Sensitivity = null;
        mainActivity.mMouseMoveSensitivity = null;
        mainActivity.m_btnTouchSenseYPercent = null;
        mainActivity.m_btnFireSense = null;
        mainActivity.mMousePointerSensitivity = null;
        mainActivity.m_btnDynamicGun = null;
        mainActivity.mGames = null;
        mainActivity.m_arrLstGameVideo = null;
        this.view7f0901a6.setOnClickListener(null);
        this.view7f0901a6 = null;
        this.view7f0901a1.setOnClickListener(null);
        this.view7f0901a1 = null;
        this.view7f09018e.setOnClickListener(null);
        this.view7f09018e = null;
        this.view7f0901a5.setOnClickListener(null);
        this.view7f0901a5.setOnLongClickListener(null);
        this.view7f0901a5 = null;
        this.view7f0900c2.setOnClickListener(null);
        this.view7f0900c2 = null;
        ((CompoundButton) this.view7f09005c).setOnCheckedChangeListener(null);
        this.view7f09005c = null;
        this.view7f090088.setOnClickListener(null);
        this.view7f090088 = null;
        this.view7f090087.setOnClickListener(null);
        this.view7f090087 = null;
        this.view7f090085.setOnClickListener(null);
        this.view7f090085 = null;
        this.view7f090086.setOnClickListener(null);
        this.view7f090086.setOnLongClickListener(null);
        this.view7f090086 = null;
        this.view7f09018c.setOnClickListener(null);
        this.view7f09018c.setOnLongClickListener(null);
        this.view7f09018c = null;
        this.view7f090189.setOnClickListener(null);
        this.view7f090189.setOnLongClickListener(null);
        this.view7f090189 = null;
        this.view7f090190.setOnClickListener(null);
        this.view7f090190.setOnLongClickListener(null);
        this.view7f090190 = null;
        this.view7f0901c7.setOnClickListener(null);
        this.view7f0901c7.setOnLongClickListener(null);
        this.view7f0901c7 = null;
        this.view7f0901e5.setOnClickListener(null);
        this.view7f0901e5.setOnLongClickListener(null);
        this.view7f0901e5 = null;
        this.view7f0901d8.setOnClickListener(null);
        this.view7f0901d8.setOnLongClickListener(null);
        this.view7f0901d8 = null;
        this.view7f0901ea.setOnClickListener(null);
        this.view7f0901ea = null;
        this.view7f0901a2.setOnClickListener(null);
        this.view7f0901a2 = null;
        this.view7f0900fc.setOnClickListener(null);
        this.view7f0900fc = null;
        this.view7f090124.setOnClickListener(null);
        this.view7f090124.setOnLongClickListener(null);
        this.view7f090124 = null;
        this.view7f090128.setOnClickListener(null);
        this.view7f090128.setOnLongClickListener(null);
        this.view7f090128 = null;
        this.view7f09012a.setOnClickListener(null);
        this.view7f09012a.setOnLongClickListener(null);
        this.view7f09012a = null;
        this.view7f09012c.setOnClickListener(null);
        this.view7f09012c.setOnLongClickListener(null);
        this.view7f09012c = null;
        this.view7f09012e.setOnClickListener(null);
        this.view7f09012e.setOnLongClickListener(null);
        this.view7f09012e = null;
        this.view7f090130.setOnClickListener(null);
        this.view7f090130.setOnLongClickListener(null);
        this.view7f090130 = null;
        this.view7f090132.setOnClickListener(null);
        this.view7f090132.setOnLongClickListener(null);
        this.view7f090132 = null;
        this.view7f090134.setOnClickListener(null);
        this.view7f090134.setOnLongClickListener(null);
        this.view7f090134 = null;
        this.view7f090127.setOnClickListener(null);
        this.view7f090127 = null;
        this.view7f090129.setOnClickListener(null);
        this.view7f090129 = null;
        this.view7f09012b.setOnClickListener(null);
        this.view7f09012b = null;
        this.view7f09012d.setOnClickListener(null);
        this.view7f09012d = null;
        this.view7f09012f.setOnClickListener(null);
        this.view7f09012f = null;
        this.view7f090131.setOnClickListener(null);
        this.view7f090131 = null;
        this.view7f090133.setOnClickListener(null);
        this.view7f090133 = null;
        this.view7f090135.setOnClickListener(null);
        this.view7f090135 = null;
        this.view7f090318.setOnClickListener(null);
        this.view7f090318 = null;
    }
}
