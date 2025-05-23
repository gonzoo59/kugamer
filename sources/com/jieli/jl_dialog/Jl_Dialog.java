package com.jieli.jl_dialog;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.jieli.jl_dialog.interfaces.OnViewClickListener;
/* loaded from: classes2.dex */
public class Jl_Dialog extends DialogFragment {
    public final String a = "Jl_Dialog";
    public boolean b = false;
    public Builder c;

    public static Builder builder() {
        return new Builder();
    }

    public final int b() {
        if (getContext() == null) {
            return 0;
        }
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public final void c(View view) {
        if (TextUtils.isEmpty(this.c.d)) {
            return;
        }
        TextView textView = (TextView) view.findViewById(R.id.tv_title);
        textView.setVisibility(0);
        textView.setText(this.c.d);
        if (this.c.h != -2) {
            textView.setTextColor(this.c.h);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public void dismiss() {
        this.b = false;
        super.dismissAllowingStateLoss();
    }

    public Builder getBuilder() {
        return this.c;
    }

    public void intiDefaultDialog(View view) {
        if (this.c.o != -2) {
            view.findViewById(R.id.ll_dialog_container).setBackgroundColor(this.c.o);
        }
        c(view);
        b(view);
        a(view);
    }

    public boolean isShow() {
        return this.b;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (getDialog().getWindow() == null) {
            return;
        }
        WindowManager.LayoutParams attributes = getDialog().getWindow().getAttributes();
        if (this.c.a != 0) {
            attributes.windowAnimations = this.c.a;
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate;
        if (this.c == null && bundle != null && bundle.getParcelable("builder") != null) {
            this.c = (Builder) bundle.get("builder");
        }
        Builder builder = this.c;
        if (builder != null && builder.b != 0) {
            inflate = layoutInflater.inflate(this.c.b, viewGroup, false);
        } else {
            Builder builder2 = this.c;
            if (builder2 != null && builder2.c != null) {
                inflate = layoutInflater.inflate(R.layout.dialog_container, viewGroup, false);
                ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(R.id.ll_dialog_container);
                viewGroup2.removeAllViews();
                ViewGroup viewGroup3 = (ViewGroup) this.c.c.getParent();
                if (viewGroup3 != null) {
                    viewGroup3.removeAllViews();
                }
                viewGroup2.addView(this.c.c);
            } else {
                inflate = layoutInflater.inflate(R.layout.dialog_container, viewGroup, false);
                intiDefaultDialog(inflate);
            }
        }
        getDialog().requestWindowFeature(1);
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        this.b = false;
        super.onDestroy();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        this.b = false;
        super.onDismiss(dialogInterface);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        this.b = true;
        super.onResume();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable("builder", this.c);
        super.onSaveInstanceState(bundle);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.dimAmount = 0.5f;
        attributes.flags |= 2;
        attributes.width = this.c.p < 0.0f ? -2 : (int) (this.c.p * b());
        attributes.height = this.c.q >= 0.0f ? (int) (this.c.q * a()) : -2;
        window.getDecorView().getRootView().setBackgroundColor(0);
        window.setAttributes(attributes);
        setCancelable(this.c.r);
    }

    @Override // androidx.fragment.app.DialogFragment
    public void show(FragmentManager fragmentManager, String str) {
        this.b = true;
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(this, str);
        beginTransaction.commitAllowingStateLoss();
    }

    public final int a() {
        if (getContext() == null) {
            return 0;
        }
        return getContext().getResources().getDisplayMetrics().heightPixels;
    }

    public final void b(View view) {
        RelativeLayout relativeLayout;
        if (this.c.n == null && this.c.m == 0) {
            relativeLayout = null;
        } else {
            relativeLayout = (RelativeLayout) view.findViewById(R.id.dialog_content_container);
            relativeLayout.setVisibility(0);
            relativeLayout.removeAllViews();
        }
        if (this.c.n != null) {
            ViewGroup viewGroup = (ViewGroup) this.c.n.getParent();
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.addRule(13);
            relativeLayout.addView(this.c.n, layoutParams);
        } else if (this.c.m != 0) {
            View inflate = LayoutInflater.from(getContext()).inflate(this.c.m, (ViewGroup) relativeLayout, false);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) inflate.getLayoutParams();
            layoutParams2.addRule(13);
            view.setLayoutParams(layoutParams2);
            relativeLayout.addView(inflate);
        } else if (!TextUtils.isEmpty(this.c.g) || this.c.s) {
            view.findViewById(R.id.content_parent).setVisibility(0);
            if (!TextUtils.isEmpty(this.c.g)) {
                TextView textView = (TextView) view.findViewById(R.id.tv_content);
                textView.setVisibility(0);
                textView.setText(this.c.g);
                textView.setGravity(this.c.l);
                if (this.c.k != -2) {
                    textView.setTextColor(this.c.k);
                }
            }
            ((ProgressBar) view.findViewById(R.id.progressBar)).setVisibility(this.c.s ? 0 : 8);
        }
    }

    public final void a(View view) {
        TextView textView = (TextView) view.findViewById(R.id.tv_left);
        TextView textView2 = (TextView) view.findViewById(R.id.tv_right);
        if (TextUtils.isEmpty(this.c.e) && TextUtils.isEmpty(this.c.f)) {
            view.findViewById(R.id.dialog_notify_ll).setVisibility(8);
            view.findViewById(R.id.line_id).setVisibility(8);
            return;
        }
        view.findViewById(R.id.dialog_notify_ll).setVisibility(0);
        view.findViewById(R.id.line_id).setVisibility(0);
        if (this.c.i != -2) {
            textView.setTextColor(this.c.i);
        }
        if (this.c.j != -2) {
            textView2.setTextColor(this.c.j);
        }
        if (TextUtils.isEmpty(this.c.e) || !TextUtils.isEmpty(this.c.f)) {
            if (TextUtils.isEmpty(this.c.e) && !TextUtils.isEmpty(this.c.f)) {
                textView2.setVisibility(0);
                textView2.setText(this.c.f);
            } else {
                textView2.setVisibility(0);
                textView2.setText(this.c.f);
                view.findViewById(R.id.divider_id).setVisibility(0);
                textView.setVisibility(0);
                textView.setText(this.c.e);
            }
        } else {
            textView.setVisibility(0);
            textView.setText(this.c.e);
        }
        textView.setOnClickListener(new a(this));
        textView2.setOnClickListener(new b(this));
    }

    /* loaded from: classes2.dex */
    public static class Builder implements Parcelable {
        public static final Parcelable.Creator<Builder> CREATOR = new c();
        public int a;
        public int b;
        public View c;
        public String d;
        public String e;
        public String f;
        public String g;
        public int h;
        public int i;
        public int j;
        public int k;
        public int l;
        public int m;
        public View n;
        public int o;
        public float p;
        public float q;
        public boolean r;
        public boolean s;
        public OnViewClickListener t;
        public OnViewClickListener u;

        public Builder() {
            this.h = -2;
            this.i = -2;
            this.j = -2;
            this.k = -2;
            this.l = 17;
            this.o = -2;
            this.p = -1.0f;
            this.q = -1.0f;
            this.r = true;
            this.s = false;
        }

        public Builder animations(int i) {
            this.a = i;
            return this;
        }

        public Builder backgroundColor(int i) {
            this.o = i;
            return this;
        }

        public Jl_Dialog build() {
            Jl_Dialog jl_Dialog = new Jl_Dialog();
            jl_Dialog.c = this;
            return jl_Dialog;
        }

        public Builder cancel(boolean z) {
            this.r = z;
            return this;
        }

        public Builder container(int i) {
            this.b = i;
            return this;
        }

        public Builder containerView(View view) {
            this.c = view;
            return this;
        }

        public Builder content(String str) {
            this.g = str;
            return this;
        }

        public Builder contentColor(int i) {
            this.k = i;
            return this;
        }

        public Builder contentGravity(int i) {
            this.l = i;
            return this;
        }

        public Builder contentLayoutRes(int i) {
            this.m = i;
            return this;
        }

        public Builder contentLayoutView(View view) {
            this.n = view;
            return this;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Builder height(float f) {
            this.q = f;
            return this;
        }

        public Builder left(String str) {
            this.e = str;
            return this;
        }

        public Builder leftClickListener(OnViewClickListener onViewClickListener) {
            this.t = onViewClickListener;
            return this;
        }

        public Builder leftColor(int i) {
            this.i = i;
            return this;
        }

        public Builder right(String str) {
            this.f = str;
            return this;
        }

        public Builder rightClickListener(OnViewClickListener onViewClickListener) {
            this.u = onViewClickListener;
            return this;
        }

        public Builder rightColor(int i) {
            this.j = i;
            return this;
        }

        public Builder showProgressBar(boolean z) {
            this.s = z;
            return this;
        }

        public Builder title(String str) {
            this.d = str;
            return this;
        }

        public Builder titleColor(int i) {
            this.h = i;
            return this;
        }

        public Builder width(float f) {
            this.p = f;
            return this;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeString(this.d);
            parcel.writeString(this.e);
            parcel.writeString(this.f);
            parcel.writeString(this.g);
            parcel.writeInt(this.h);
            parcel.writeInt(this.i);
            parcel.writeInt(this.j);
            parcel.writeInt(this.k);
            parcel.writeInt(this.l);
            parcel.writeInt(this.m);
            parcel.writeInt(this.o);
            parcel.writeByte(this.r ? (byte) 1 : (byte) 0);
        }

        public Builder(Parcel parcel) {
            this.h = -2;
            this.i = -2;
            this.j = -2;
            this.k = -2;
            this.l = 17;
            this.o = -2;
            this.p = -1.0f;
            this.q = -1.0f;
            this.r = true;
            this.s = false;
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = (View) parcel.readParcelable(View.class.getClassLoader());
            this.d = parcel.readString();
            this.e = parcel.readString();
            this.f = parcel.readString();
            this.g = parcel.readString();
            this.h = parcel.readInt();
            this.i = parcel.readInt();
            this.j = parcel.readInt();
            this.k = parcel.readInt();
            this.l = parcel.readInt();
            this.m = parcel.readInt();
            this.n = (View) parcel.readParcelable(View.class.getClassLoader());
            this.o = parcel.readInt();
            this.r = parcel.readByte() != 0;
            this.t = (OnViewClickListener) parcel.readParcelable(OnViewClickListener.class.getClassLoader());
            this.u = (OnViewClickListener) parcel.readParcelable(OnViewClickListener.class.getClassLoader());
        }
    }
}
