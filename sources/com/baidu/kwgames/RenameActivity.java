package com.baidu.kwgames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.baidu.kwgames.util.NEAT;
/* loaded from: classes.dex */
public class RenameActivity extends BaseActivity {
    private Button cancelBtn;
    private EditText editText;
    private TextView m_textTitle;
    private Button okBtn;

    @Override // com.baidu.kwgames.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_rename);
        this.okBtn = (Button) findViewById(R.id.ok_tn);
        this.editText = (EditText) findViewById(R.id.edit_text);
        this.m_textTitle = (TextView) findViewById(R.id.edit_title);
        this.cancelBtn = (Button) findViewById(R.id.cancel_btn);
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("title");
        if (stringExtra == null) {
            stringExtra = NEAT.s(this, R.string.rename_title);
        }
        this.m_textTitle.setText(stringExtra);
        this.editText.setText(intent.getStringExtra("name"));
        this.cancelBtn.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.RenameActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String obj = RenameActivity.this.editText.getText().toString();
                if (obj.isEmpty()) {
                    return;
                }
                Intent intent2 = new Intent();
                intent2.putExtra("name", obj);
                RenameActivity.this.setResult(-1, intent2);
                RenameActivity.this.finish();
            }
        });
        this.okBtn.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.RenameActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RenameActivity.this.setResult(0, new Intent());
                RenameActivity.this.finish();
            }
        });
    }
}
