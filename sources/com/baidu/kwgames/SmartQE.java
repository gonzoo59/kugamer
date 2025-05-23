package com.baidu.kwgames;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.RemoteException;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.baidu.kwgames.SmartQE;
import com.baidu.kwgames.util.FloatMgr;
import com.blankj.utilcode.util.SPUtils;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnInvokeView;
/* loaded from: classes.dex */
public class SmartQE {
    private static String INI_FLOAT_X = "SmartQEX";
    private static String INI_FLOAT_Y = "SmartQEY";
    private static String INI_SMART_QE_AUTO_SCOPE_OFF = "SmartQEScopeOff";
    private static String INI_SMART_QE_REVERSE = "SmartQEReverse";
    private static String INI_SMART_QE_REVERSE_AUTO_RECOVER = "SmartQEReverseAutoRecover";
    private static String INI_SMART_QE_REVERSE_AUTO_RECOVER_TIME = "SmartQEReverseAutoRecoverTime";
    public static final String SETTING_STATUS = "status";
    private static String TAG_FLOAT = "SmartQE";
    boolean m_boAutoScopeOff;
    boolean m_boReverse;
    boolean m_boReverseAutoRecover;
    Context m_context;
    LinearLayout m_layoutAutoReverseRecover;
    LinearLayout m_layoutAutoReverseRecoverTime;
    int m_nFloatX;
    int m_nFloatY;
    int m_nReverseAutoRecoverTime;
    IThrone m_service;
    TextView m_textRecoverTime;
    View m_viewRoot;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SmartQE(Context context, IThrone iThrone) {
        this.m_boAutoScopeOff = false;
        this.m_boReverse = false;
        this.m_boReverseAutoRecover = true;
        this.m_nReverseAutoRecoverTime = 20;
        this.m_nFloatX = 0;
        this.m_nFloatY = 0;
        this.m_context = context;
        this.m_service = iThrone;
        SPUtils sPUtils = SPUtils.getInstance();
        this.m_boAutoScopeOff = sPUtils.getBoolean(INI_SMART_QE_AUTO_SCOPE_OFF, false);
        this.m_boReverse = sPUtils.getBoolean(INI_SMART_QE_REVERSE, false);
        this.m_boReverseAutoRecover = sPUtils.getBoolean(INI_SMART_QE_REVERSE_AUTO_RECOVER, true);
        this.m_nReverseAutoRecoverTime = sPUtils.getInt(INI_SMART_QE_REVERSE_AUTO_RECOVER_TIME, 20);
        this.m_nFloatX = sPUtils.getInt(INI_FLOAT_X, 0);
        this.m_nFloatY = sPUtils.getInt(INI_FLOAT_Y, 0);
    }

    void update_UI() {
        TextView textView = this.m_textRecoverTime;
        textView.setText("" + this.m_nReverseAutoRecoverTime);
        if (!this.m_boReverse) {
            this.m_layoutAutoReverseRecover.setVisibility(8);
            this.m_layoutAutoReverseRecoverTime.setVisibility(8);
            return;
        }
        this.m_layoutAutoReverseRecover.setVisibility(0);
        this.m_layoutAutoReverseRecoverTime.setVisibility(this.m_boReverseAutoRecover ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void bleSendAISmartQEParams() {
        try {
            byte[] bArr = new byte[8];
            bArr[0] = -1;
            bArr[1] = -107;
            bArr[2] = (byte) 4;
            bArr[3] = 0;
            bArr[4] = (byte) (this.m_boAutoScopeOff ? 1 : 0);
            bArr[5] = (byte) (this.m_boReverse ? 1 : 0);
            bArr[6] = (byte) (this.m_boReverseAutoRecover ? 1 : 0);
            bArr[7] = (byte) this.m_nReverseAutoRecoverTime;
            IThrone iThrone = this.m_service;
            if (iThrone != null) {
                iThrone.bleSendShortData(bArr);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void show_float(boolean z) {
        if (!z) {
            EasyFloat.dismissAppFloat(TAG_FLOAT);
            return;
        }
        EasyFloat.Builder layout = EasyFloat.with(AppInstance.get()).setTag(TAG_FLOAT).setGravity(17).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setLayout(R.layout.float_smart_qe_setting, new AnonymousClass1());
        layout.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.SmartQE.2
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z2, String str, View view) {
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dismiss() {
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void drag(View view, MotionEvent motionEvent) {
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void hide(View view) {
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void show(View view) {
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void touchEvent(View view, MotionEvent motionEvent) {
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                SmartQE.this.m_viewRoot.getLocationOnScreen(iArr);
                SmartQE.this.m_nFloatX = iArr[0];
                SmartQE.this.m_nFloatY = iArr[1];
                SPUtils.getInstance().put(SmartQE.INI_FLOAT_X, SmartQE.this.m_nFloatX);
                SPUtils.getInstance().put(SmartQE.INI_FLOAT_Y, SmartQE.this.m_nFloatY);
            }
        });
        layout.setLocation(this.m_nFloatX, this.m_nFloatY);
        layout.show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.SmartQE$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements OnInvokeView {
        AnonymousClass1() {
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            SmartQE.this.m_viewRoot = view.findViewById(R.id.root);
            Switch r0 = (Switch) view.findViewById(R.id.m_swCloseScope);
            Switch r1 = (Switch) view.findViewById(R.id.m_swReverse);
            Switch r2 = (Switch) view.findViewById(R.id.m_swReverseAutoRecover);
            SmartQE.this.m_textRecoverTime = (TextView) view.findViewById(R.id.m_textRecoverTime);
            SmartQE.this.m_layoutAutoReverseRecover = (LinearLayout) view.findViewById(R.id.m_layoutAutoReverseRecover);
            SmartQE.this.m_layoutAutoReverseRecoverTime = (LinearLayout) view.findViewById(R.id.m_layoutAutoReverseRecoverTime);
            r0.setChecked(SmartQE.this.m_boAutoScopeOff);
            r1.setChecked(SmartQE.this.m_boReverse);
            r2.setChecked(SmartQE.this.m_boReverseAutoRecover);
            SmartQE.this.update_UI();
            view.findViewById(R.id.close_window).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.SmartQE$1$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    EasyFloat.dismissAppFloat(SmartQE.TAG_FLOAT);
                }
            });
            view.findViewById(R.id.m_btnQuestion).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.SmartQE$1$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SmartQE.AnonymousClass1.lambda$invoke$1(view2);
                }
            });
            r0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SmartQE$1$$ExternalSyntheticLambda4
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SmartQE.AnonymousClass1.this.m104lambda$invoke$2$combaidukwgamesSmartQE$1(compoundButton, z);
                }
            });
            r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SmartQE$1$$ExternalSyntheticLambda5
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SmartQE.AnonymousClass1.this.m105lambda$invoke$3$combaidukwgamesSmartQE$1(compoundButton, z);
                }
            });
            r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.SmartQE$1$$ExternalSyntheticLambda6
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SmartQE.AnonymousClass1.this.m106lambda$invoke$4$combaidukwgamesSmartQE$1(compoundButton, z);
                }
            });
            view.findViewById(R.id.m_btnMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.SmartQE$1$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SmartQE.AnonymousClass1.this.m107lambda$invoke$5$combaidukwgamesSmartQE$1(view2);
                }
            });
            view.findViewById(R.id.m_btnPlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.SmartQE$1$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SmartQE.AnonymousClass1.this.m108lambda$invoke$6$combaidukwgamesSmartQE$1(view2);
                }
            });
            FloatMgr.resetVirtualMouse();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ void lambda$invoke$1(View view) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(Constants.URL_VIDEO_SMART_QE));
            AppInstance.s_context.startActivity(intent);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$2$com-baidu-kwgames-SmartQE$1  reason: not valid java name */
        public /* synthetic */ void m104lambda$invoke$2$combaidukwgamesSmartQE$1(CompoundButton compoundButton, boolean z) {
            SmartQE.this.m_boAutoScopeOff = z;
            SPUtils.getInstance().put(SmartQE.INI_SMART_QE_AUTO_SCOPE_OFF, z);
            SmartQE.this.bleSendAISmartQEParams();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$3$com-baidu-kwgames-SmartQE$1  reason: not valid java name */
        public /* synthetic */ void m105lambda$invoke$3$combaidukwgamesSmartQE$1(CompoundButton compoundButton, boolean z) {
            SmartQE.this.m_boReverse = z;
            SPUtils.getInstance().put(SmartQE.INI_SMART_QE_REVERSE, z);
            SmartQE.this.update_UI();
            SmartQE.this.bleSendAISmartQEParams();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$4$com-baidu-kwgames-SmartQE$1  reason: not valid java name */
        public /* synthetic */ void m106lambda$invoke$4$combaidukwgamesSmartQE$1(CompoundButton compoundButton, boolean z) {
            SmartQE.this.m_boReverseAutoRecover = z;
            SPUtils.getInstance().put(SmartQE.INI_SMART_QE_REVERSE, z);
            SmartQE.this.update_UI();
            SmartQE.this.bleSendAISmartQEParams();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$5$com-baidu-kwgames-SmartQE$1  reason: not valid java name */
        public /* synthetic */ void m107lambda$invoke$5$combaidukwgamesSmartQE$1(View view) {
            if (SmartQE.this.m_nReverseAutoRecoverTime > 2) {
                SmartQE.this.m_nReverseAutoRecoverTime -= 2;
                SPUtils.getInstance().put(SmartQE.INI_SMART_QE_REVERSE_AUTO_RECOVER_TIME, SmartQE.this.m_nReverseAutoRecoverTime);
                SmartQE.this.update_UI();
                SmartQE.this.bleSendAISmartQEParams();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$6$com-baidu-kwgames-SmartQE$1  reason: not valid java name */
        public /* synthetic */ void m108lambda$invoke$6$combaidukwgamesSmartQE$1(View view) {
            SmartQE.this.m_nReverseAutoRecoverTime += 2;
            SPUtils.getInstance().put(SmartQE.INI_SMART_QE_REVERSE_AUTO_RECOVER_TIME, SmartQE.this.m_nReverseAutoRecoverTime);
            SmartQE.this.update_UI();
            SmartQE.this.bleSendAISmartQEParams();
        }
    }
}
