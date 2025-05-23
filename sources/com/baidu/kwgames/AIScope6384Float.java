package com.baidu.kwgames;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import com.baidu.kwgames.util.FloatMgr;
import com.blankj.utilcode.util.SPUtils;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnInvokeView;
/* loaded from: classes.dex */
public class AIScope6384Float {
    public static final String FLOAT_X = "Scope6384X";
    public static final String FLOAT_Y = "Scope6384Y";
    Context m_context;
    int m_nCurGun;
    int m_nGun1ID;
    int m_nGun1ScopeIndex;
    int m_nGun2ID;
    int m_nGun2ScopeIndex;
    int m_nX;
    int m_nY;
    Button m_btnKey = null;
    View m_rootView = null;
    int m_nScopeIndex = 6;

    public AIScope6384Float(Context context) {
        this.m_context = context;
    }

    void update_text() {
        Button button = this.m_btnKey;
        if (button != null) {
            button.setText("" + this.m_nScopeIndex);
        }
    }

    void init_float() {
        EasyFloat.Builder layout = EasyFloat.with(this.m_context).setTag(Constants.FLOAT_SCOPE_6384).setGravity(17).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setLayout(R.layout.float_scope_6384, new OnInvokeView() { // from class: com.baidu.kwgames.AIScope6384Float.1
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                AIScope6384Float.this.m_rootView = view.findViewById(R.id.root);
                AIScope6384Float.this.m_btnKey = (Button) view.findViewById(R.id.m_btnKey);
                AIScope6384Float.this.update_text();
                FloatMgr.resetVirtualMouse();
                AIScope6384Float.this.m_btnKey.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.AIScope6384Float.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        int i = AIScope6384Float.this.m_nScopeIndex;
                        if (i == 3) {
                            AIScope6384Float.this.m_nScopeIndex = 6;
                        } else if (i == 4) {
                            AIScope6384Float.this.m_nScopeIndex = 8;
                        } else if (i == 6) {
                            AIScope6384Float.this.m_nScopeIndex = 3;
                        } else if (i == 8) {
                            AIScope6384Float.this.m_nScopeIndex = 4;
                        }
                        AIScope6384Float.this.update_text();
                    }
                });
            }
        });
        layout.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.AIScope6384Float.2
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                AIScope6384Float.this.m_rootView.getLocationOnScreen(iArr);
                AIScope6384Float.this.m_nX = iArr[0];
                AIScope6384Float.this.m_nY = iArr[1];
                SPUtils.getInstance().put(AIScope6384Float.FLOAT_X, AIScope6384Float.this.m_nX);
                SPUtils.getInstance().put(AIScope6384Float.FLOAT_Y, AIScope6384Float.this.m_nY);
            }
        });
        this.m_nX = SPUtils.getInstance().getInt(FLOAT_X, 200);
        int i = SPUtils.getInstance().getInt(FLOAT_Y, 200);
        this.m_nY = i;
        layout.setLocation(this.m_nX, i);
        layout.show();
    }

    private void set_scope(int i, int i2, boolean z, int i3) {
        boolean z2 = false;
        if (z) {
            this.m_nCurGun = i;
            if (i == 0) {
                z2 = true;
            }
        }
        if (!z2) {
            EasyFloat.hideAppFloat(Constants.FLOAT_SCOPE_6384);
            return;
        }
        this.m_nScopeIndex = i3;
        if (this.m_rootView == null) {
            init_float();
        } else {
            EasyFloat.showAppFloat(Constants.FLOAT_SCOPE_6384);
        }
    }
}
