package defpackage;

import android.view.View;
import com.jieli.jl_dialog.Jl_Dialog;
import com.jieli.jl_dialog.interfaces.OnViewClickListener;
/* compiled from: Jl_Dialog.java */
/* renamed from: a  reason: default package */
/* loaded from: classes.dex */
public class a implements View.OnClickListener {
    public final /* synthetic */ Jl_Dialog a;

    public a(Jl_Dialog jl_Dialog) {
        this.a = jl_Dialog;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Jl_Dialog.Builder builder;
        OnViewClickListener onViewClickListener;
        Jl_Dialog.Builder builder2;
        OnViewClickListener onViewClickListener2;
        builder = this.a.c;
        onViewClickListener = builder.t;
        if (onViewClickListener != null) {
            builder2 = this.a.c;
            onViewClickListener2 = builder2.t;
            onViewClickListener2.onClick(view, this.a);
        }
    }
}
