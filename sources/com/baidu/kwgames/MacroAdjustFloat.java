package com.baidu.kwgames;

import android.content.Context;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.baidu.kwgames.util.FloatCallbackDragRemIni;
import com.baidu.kwgames.util.FloatMgr;
import com.blankj.utilcode.util.SPUtils;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnInvokeView;
/* loaded from: classes.dex */
public class MacroAdjustFloat {
    public static final int E_PARAM_CNT = 5;
    public static final String FLOAT_X = "GameSetX";
    public static final String FLOAT_Y = "GameSetY";
    public static final String SETTING_STATUS = "status";
    private static String TAG_FLOAT = "MacroAdjust";
    boolean m_boParamChanged;
    Context m_context;
    IThrone m_service;
    int m_nFloatX = 0;
    int m_nFloatY = 0;
    int[] m_arrParam = new int[5];
    TextView[] m_arrParamText = new TextView[5];
    Button[] m_arrBtnMinus1 = new Button[5];
    Button[] m_arrBtnMinus10 = new Button[5];
    Button[] m_arrBtnPlus1 = new Button[5];
    Button[] m_arrBtnPlus10 = new Button[5];
    View.OnClickListener m_listenerMinus1 = new View.OnClickListener() { // from class: com.baidu.kwgames.MacroAdjustFloat.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            for (int i = 0; i < 5; i++) {
                if (MacroAdjustFloat.this.m_arrBtnMinus1[i] == view) {
                    if (MacroAdjustFloat.this.m_arrParam[i] > 0) {
                        int[] iArr = MacroAdjustFloat.this.m_arrParam;
                        iArr[i] = iArr[i] - 1;
                        MacroAdjustFloat.this.onUIParamChange();
                        return;
                    }
                    return;
                }
            }
        }
    };
    View.OnClickListener m_listenerMinus10 = new View.OnClickListener() { // from class: com.baidu.kwgames.MacroAdjustFloat.2
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            for (int i = 0; i < 5; i++) {
                if (MacroAdjustFloat.this.m_arrBtnMinus10[i] == view) {
                    if (MacroAdjustFloat.this.m_arrParam[i] > 10) {
                        int[] iArr = MacroAdjustFloat.this.m_arrParam;
                        iArr[i] = iArr[i] - 10;
                        MacroAdjustFloat.this.onUIParamChange();
                        return;
                    } else if (MacroAdjustFloat.this.m_arrParam[i] > 0) {
                        int[] iArr2 = MacroAdjustFloat.this.m_arrParam;
                        iArr2[i] = iArr2[i] - 1;
                        MacroAdjustFloat.this.onUIParamChange();
                        return;
                    } else {
                        return;
                    }
                }
            }
        }
    };
    View.OnClickListener m_listenerPlus1 = new View.OnClickListener() { // from class: com.baidu.kwgames.MacroAdjustFloat.3
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            for (int i = 0; i < 5; i++) {
                if (MacroAdjustFloat.this.m_arrBtnPlus1[i] == view) {
                    int[] iArr = MacroAdjustFloat.this.m_arrParam;
                    iArr[i] = iArr[i] + 1;
                    MacroAdjustFloat.this.onUIParamChange();
                    return;
                }
            }
        }
    };
    View.OnClickListener m_listenerPlus10 = new View.OnClickListener() { // from class: com.baidu.kwgames.MacroAdjustFloat.4
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            for (int i = 0; i < 5; i++) {
                if (MacroAdjustFloat.this.m_arrBtnPlus10[i] == view) {
                    int[] iArr = MacroAdjustFloat.this.m_arrParam;
                    iArr[i] = iArr[i] + 20;
                    MacroAdjustFloat.this.onUIParamChange();
                    return;
                }
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public MacroAdjustFloat(Context context, IThrone iThrone) {
        this.m_context = context;
        this.m_service = iThrone;
    }

    void update_UI() {
        for (int i = 0; i < 5; i++) {
            TextView textView = this.m_arrParamText[i];
            textView.setText("" + this.m_arrParam[i]);
        }
    }

    void bleSendParams() {
        try {
            byte[] bArr = new byte[15];
            bArr[0] = -1;
            bArr[1] = -104;
            bArr[2] = (byte) 11;
            bArr[3] = 0;
            bArr[4] = 0;
            int i = 5;
            for (int i2 = 0; i2 < 5; i2++) {
                int i3 = i + 1;
                bArr[i] = Units.LOBYTE(this.m_arrParam[i2]);
                i = i3 + 1;
                bArr[i3] = Units.HIBYTE(this.m_arrParam[i2]);
            }
            IThrone iThrone = this.m_service;
            if (iThrone != null) {
                iThrone.bleSendShortData(bArr);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void onUIParamChange() {
        this.m_boParamChanged = true;
        bleSendParams();
        update_UI();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void show_float() {
        if (EasyFloat.getAppFloatView(TAG_FLOAT) != null) {
            EasyFloat.dismissAppFloat(TAG_FLOAT);
            return;
        }
        this.m_nFloatX = SPUtils.getInstance().getInt("GameSetX", 100);
        this.m_nFloatY = SPUtils.getInstance().getInt("GameSetY", 100);
        EasyFloat.Builder layout = EasyFloat.with(AppInstance.get()).setTag(TAG_FLOAT).setGravity(17).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setLayout(R.layout.float_macro_adjust, new AnonymousClass5());
        layout.registerCallbacks(new FloatCallbackDragRemIni("GameSetX", "GameSetY"));
        layout.setLocation(this.m_nFloatX, this.m_nFloatY);
        layout.show();
    }

    /* renamed from: com.baidu.kwgames.MacroAdjustFloat$5  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass5 implements OnInvokeView {
        AnonymousClass5() {
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            MacroAdjustFloat.this.m_boParamChanged = false;
            MacroAdjustFloat.this.m_arrBtnMinus1[0] = (Button) view.findViewById(R.id.m_btnParamMinus11);
            MacroAdjustFloat.this.m_arrBtnMinus1[1] = (Button) view.findViewById(R.id.m_btnParamMinus21);
            MacroAdjustFloat.this.m_arrBtnMinus1[2] = (Button) view.findViewById(R.id.m_btnParamMinus31);
            MacroAdjustFloat.this.m_arrBtnMinus1[3] = (Button) view.findViewById(R.id.m_btnParamMinus41);
            MacroAdjustFloat.this.m_arrBtnMinus1[4] = (Button) view.findViewById(R.id.m_btnParamMinus51);
            MacroAdjustFloat.this.m_arrBtnMinus10[0] = (Button) view.findViewById(R.id.m_btnParamMinus110);
            MacroAdjustFloat.this.m_arrBtnMinus10[1] = (Button) view.findViewById(R.id.m_btnParamMinus210);
            MacroAdjustFloat.this.m_arrBtnMinus10[2] = (Button) view.findViewById(R.id.m_btnParamMinus310);
            MacroAdjustFloat.this.m_arrBtnMinus10[3] = (Button) view.findViewById(R.id.m_btnParamMinus410);
            MacroAdjustFloat.this.m_arrBtnMinus10[4] = (Button) view.findViewById(R.id.m_btnParamMinus510);
            MacroAdjustFloat.this.m_arrBtnPlus1[0] = (Button) view.findViewById(R.id.m_btnParamPlus11);
            MacroAdjustFloat.this.m_arrBtnPlus1[1] = (Button) view.findViewById(R.id.m_btnParamPlus21);
            MacroAdjustFloat.this.m_arrBtnPlus1[2] = (Button) view.findViewById(R.id.m_btnParamPlus31);
            MacroAdjustFloat.this.m_arrBtnPlus1[3] = (Button) view.findViewById(R.id.m_btnParamPlus41);
            MacroAdjustFloat.this.m_arrBtnPlus1[4] = (Button) view.findViewById(R.id.m_btnParamPlus51);
            MacroAdjustFloat.this.m_arrBtnPlus10[0] = (Button) view.findViewById(R.id.m_btnParamPlus110);
            MacroAdjustFloat.this.m_arrBtnPlus10[1] = (Button) view.findViewById(R.id.m_btnParamPlus210);
            MacroAdjustFloat.this.m_arrBtnPlus10[2] = (Button) view.findViewById(R.id.m_btnParamPlus310);
            MacroAdjustFloat.this.m_arrBtnPlus10[3] = (Button) view.findViewById(R.id.m_btnParamPlus410);
            MacroAdjustFloat.this.m_arrBtnPlus10[4] = (Button) view.findViewById(R.id.m_btnParamPlus510);
            MacroAdjustFloat.this.m_arrParamText[0] = (TextView) view.findViewById(R.id.m_textParam1);
            MacroAdjustFloat.this.m_arrParamText[1] = (TextView) view.findViewById(R.id.m_textParam2);
            MacroAdjustFloat.this.m_arrParamText[2] = (TextView) view.findViewById(R.id.m_textParam3);
            MacroAdjustFloat.this.m_arrParamText[3] = (TextView) view.findViewById(R.id.m_textParam4);
            MacroAdjustFloat.this.m_arrParamText[4] = (TextView) view.findViewById(R.id.m_textParam5);
            MacroAdjustFloat.this.update_UI();
            view.findViewById(R.id.close_window).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MacroAdjustFloat$5$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    EasyFloat.dismissAppFloat(MacroAdjustFloat.TAG_FLOAT);
                }
            });
            for (int i = 0; i < 5; i++) {
                MacroAdjustFloat.this.m_arrBtnMinus1[i].setOnClickListener(MacroAdjustFloat.this.m_listenerMinus1);
                MacroAdjustFloat.this.m_arrBtnMinus10[i].setOnClickListener(MacroAdjustFloat.this.m_listenerMinus10);
                MacroAdjustFloat.this.m_arrBtnPlus1[i].setOnClickListener(MacroAdjustFloat.this.m_listenerPlus1);
                MacroAdjustFloat.this.m_arrBtnPlus10[i].setOnClickListener(MacroAdjustFloat.this.m_listenerPlus10);
            }
            FloatMgr.resetVirtualMouse();
        }
    }
}
