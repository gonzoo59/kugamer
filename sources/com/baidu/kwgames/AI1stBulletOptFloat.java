package com.baidu.kwgames;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.baidu.kwgames.AI1stBulletOptFloat;
import com.baidu.kwgames.adapter.XScopeAdapter;
import com.baidu.kwgames.util.AI1stBulletOpt;
import com.baidu.kwgames.util.FloatMgr;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class AI1stBulletOptFloat {
    XScopeAdapter m_adapterXScope;
    ArrayList<Short> m_arrGunIndex;
    ArrayList<String> m_arrGuns;
    ArrayList<Boolean> m_arrGunsSel;
    public Context m_context;
    RecyclerView m_lstGuns;
    View m_viewXScopeAdjustRoot;

    public void show_float(Context context) {
        this.m_context = context;
        show_adjust_float(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void show_adjust_float(boolean z) {
        if (!z) {
            EasyFloat.dismissAppFloat(Constants.FLOAT_AI_1ST_BULLET_OPT_ADJUST);
        } else if (EasyFloat.getAppFloatView(Constants.FLOAT_AI_1ST_BULLET_OPT_ADJUST) != null) {
        } else {
            EasyFloat.Builder layout = EasyFloat.with(this.m_context).setTag(Constants.FLOAT_AI_1ST_BULLET_OPT_ADJUST).setLayout(R.layout.first_bullet_opt_adjust, new AnonymousClass1());
            layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(false).setAnimator(null);
            layout.setLocation(80, 0);
            layout.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.AI1stBulletOptFloat$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements OnInvokeView {
        AnonymousClass1() {
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            AI1stBulletOptFloat.this.m_viewXScopeAdjustRoot = view.findViewById(R.id.root);
            AI1stBulletOptFloat.this.init_list(view);
            view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.AI1stBulletOptFloat$1$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    AI1stBulletOptFloat.AnonymousClass1.this.m7lambda$invoke$0$combaidukwgamesAI1stBulletOptFloat$1(view2);
                }
            });
            FloatMgr.resetVirtualMouse();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$0$com-baidu-kwgames-AI1stBulletOptFloat$1  reason: not valid java name */
        public /* synthetic */ void m7lambda$invoke$0$combaidukwgamesAI1stBulletOptFloat$1(View view) {
            for (int i = 0; i < AI1stBulletOptFloat.this.m_arrGunIndex.size(); i++) {
                AI1stBulletOpt.set_onoff(AI1stBulletOptFloat.this.m_arrGunIndex.get(i).shortValue(), AI1stBulletOptFloat.this.m_arrGunsSel.get(i).booleanValue());
            }
            AI1stBulletOpt.save();
            AI1stBulletOptFloat.this.show_adjust_float(false);
        }
    }

    void init_list(View view) {
        if (this.m_arrGuns == null) {
            this.m_arrGuns = new ArrayList<>();
        }
        if (this.m_arrGunsSel == null) {
            this.m_arrGunsSel = new ArrayList<>();
        }
        if (this.m_arrGunIndex == null) {
            this.m_arrGunIndex = new ArrayList<>();
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.m_lstAutoActiveGun);
        this.m_lstGuns = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.m_context));
        this.m_arrGuns.clear();
        this.m_arrGunsSel.clear();
        for (int i = 0; i < 66; i++) {
            if (Constants.g_arrGunInfo[i] != null) {
                this.m_arrGuns.add(Constants.g_arrGunInfo[i].m_strName);
                this.m_arrGunsSel.add(Boolean.valueOf(AI1stBulletOpt.get_onoff(i)));
                this.m_arrGunIndex.add(Short.valueOf((short) i));
            }
        }
        XScopeAdapter xScopeAdapter = new XScopeAdapter(this.m_context, this.m_arrGuns, this.m_arrGunsSel);
        this.m_adapterXScope = xScopeAdapter;
        xScopeAdapter.setClickListener(new XScopeAdapter.ItemClickListener() { // from class: com.baidu.kwgames.AI1stBulletOptFloat.2
            @Override // com.baidu.kwgames.adapter.XScopeAdapter.ItemClickListener
            public void onItemClick(View view2, int i2) {
                AI1stBulletOptFloat.this.m_arrGunsSel.set(i2, Boolean.valueOf(!AI1stBulletOptFloat.this.m_arrGunsSel.get(i2).booleanValue()));
                AI1stBulletOptFloat.this.m_adapterXScope.notifyDataSetChanged();
                for (int i3 = 0; i3 < AI1stBulletOptFloat.this.m_arrGunIndex.size(); i3++) {
                    AI1stBulletOpt.set_onoff(AI1stBulletOptFloat.this.m_arrGunIndex.get(i3).shortValue(), AI1stBulletOptFloat.this.m_arrGunsSel.get(i3).booleanValue());
                }
            }
        });
        this.m_lstGuns.setAdapter(this.m_adapterXScope);
    }
}
