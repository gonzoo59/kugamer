package defpackage;

import android.os.Parcel;
import android.os.Parcelable;
import com.jieli.jl_dialog.Jl_Dialog;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Jl_Dialog.java */
/* renamed from: c  reason: default package */
/* loaded from: classes.dex */
public class c implements Parcelable.Creator<Jl_Dialog.Builder> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public Jl_Dialog.Builder createFromParcel(Parcel parcel) {
        return new Jl_Dialog.Builder(parcel);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public Jl_Dialog.Builder[] newArray(int i) {
        return new Jl_Dialog.Builder[i];
    }
}
