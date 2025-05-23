package com.baidu.kwgames;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import java.util.HashMap;
/* loaded from: classes.dex */
public class KeyMappingActivity extends AppCompatActivity {
    private static final String TAG = "KeyMappingActivity";
    @BindView(R.id.key_mapping_root)
    LineRelativeLayout mRoot;
    private final HashMap<String, String> mKeyMap = new HashMap<>();
    private String mSelTag = null;
    private int mKeyIndex = 0;

    @OnClick({R.id.pre_set})
    public void onPreSet() {
    }

    @OnClick({R.id.save})
    public void onSave() {
    }

    @OnClick({R.id.save_as})
    public void onSaveAs() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_key_mapping);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.property})
    public void onProperty() {
        startActivity(new Intent(this, PropertyActivity.class));
    }

    @OnClick({R.id.add})
    public void onAdd() {
        final String str = "key" + this.mKeyIndex;
        this.mKeyIndex++;
        EasyFloat.with(this).setTag(str).setFilter(new Class[0]).setLocation(100, 200).setAnimator(null).setLayout(R.layout.float_window_key, new OnInvokeView() { // from class: com.baidu.kwgames.KeyMappingActivity.2
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                TextView textView = (TextView) view.findViewById(R.id.text);
                textView.setMaxLines(1);
                textView.setGravity(17);
                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(textView, 1, 50, 1, 1);
                textView.setText(str);
                view.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingActivity.2.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        ((ImageView) view2.findViewById(R.id.icon)).setBackground(KeyMappingActivity.this.getResources().getDrawable(R.drawable.shape_circle_green));
                        KeyMappingActivity.this.mSelTag = (String) view2.getTag();
                        for (String str2 : KeyMappingActivity.this.mKeyMap.keySet()) {
                            String unused = KeyMappingActivity.this.mSelTag;
                        }
                    }
                });
            }
        }).registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.KeyMappingActivity.1
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dismiss() {
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
            public void createdResult(boolean z, String str2, View view) {
                HashMap hashMap = KeyMappingActivity.this.mKeyMap;
                String str3 = str;
                hashMap.put(str3, str3);
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void drag(View view, MotionEvent motionEvent) {
                KeyMappingActivity.this.mRoot.invalidate();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                String str2 = KeyMappingActivity.TAG;
                Log.d(str2, "dragEnd: " + ((String) view.getTag()));
            }
        }).show();
        this.mRoot.setLineColor(-65536);
    }

    @OnClick({R.id.del})
    public void onDel() {
        if (this.mSelTag != null) {
            this.mSelTag = null;
        }
    }
}
