package com.baidu.kwgames;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
/* loaded from: classes.dex */
public class KeyMappingActivity_ViewBinding implements Unbinder {
    private KeyMappingActivity target;
    private View view7f090057;
    private View view7f0900db;
    private View view7f0902cc;
    private View view7f0902d4;
    private View view7f0902f5;
    private View view7f0902f6;

    public KeyMappingActivity_ViewBinding(KeyMappingActivity keyMappingActivity) {
        this(keyMappingActivity, keyMappingActivity.getWindow().getDecorView());
    }

    public KeyMappingActivity_ViewBinding(final KeyMappingActivity keyMappingActivity, View view) {
        this.target = keyMappingActivity;
        keyMappingActivity.mRoot = (LineRelativeLayout) Utils.findRequiredViewAsType(view, R.id.key_mapping_root, "field 'mRoot'", LineRelativeLayout.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.property, "method 'onProperty'");
        this.view7f0902d4 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.KeyMappingActivity_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                keyMappingActivity.onProperty();
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.add, "method 'onAdd'");
        this.view7f090057 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.KeyMappingActivity_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                keyMappingActivity.onAdd();
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, R.id.del, "method 'onDel'");
        this.view7f0900db = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.KeyMappingActivity_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                keyMappingActivity.onDel();
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, R.id.pre_set, "method 'onPreSet'");
        this.view7f0902cc = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.KeyMappingActivity_ViewBinding.4
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                keyMappingActivity.onPreSet();
            }
        });
        View findRequiredView5 = Utils.findRequiredView(view, R.id.save, "method 'onSave'");
        this.view7f0902f5 = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.KeyMappingActivity_ViewBinding.5
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                keyMappingActivity.onSave();
            }
        });
        View findRequiredView6 = Utils.findRequiredView(view, R.id.save_as, "method 'onSaveAs'");
        this.view7f0902f6 = findRequiredView6;
        findRequiredView6.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.KeyMappingActivity_ViewBinding.6
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                keyMappingActivity.onSaveAs();
            }
        });
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        KeyMappingActivity keyMappingActivity = this.target;
        if (keyMappingActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        keyMappingActivity.mRoot = null;
        this.view7f0902d4.setOnClickListener(null);
        this.view7f0902d4 = null;
        this.view7f090057.setOnClickListener(null);
        this.view7f090057 = null;
        this.view7f0900db.setOnClickListener(null);
        this.view7f0900db = null;
        this.view7f0902cc.setOnClickListener(null);
        this.view7f0902cc = null;
        this.view7f0902f5.setOnClickListener(null);
        this.view7f0902f5 = null;
        this.view7f0902f6.setOnClickListener(null);
        this.view7f0902f6 = null;
    }
}
