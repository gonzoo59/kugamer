package com.baidu.kwgames;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import com.baidu.kwgames.util.MsgBox;
import com.baidu.kwgames.util.NEAT;
import com.blankj.utilcode.util.SPUtils;
import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/* loaded from: classes.dex */
public class ChangeModeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "ChangeModeActivity";
    private static final String[] g_arrUSBSpeed = {"2000Hz", "500Hz", "333Hz", "250Hz"};
    private static SPUtils m_ini = SPUtils.getInstance();
    @BindView(R.id.m_btnGenerateBleName)
    Button m_btnGenerateBleName;
    @BindView(R.id.btn_touping_question)
    Button m_btnToupingVideo;
    @BindView(R.id.m_btnUSBSpeed)
    Button m_btnUSBSpeed;
    int m_byUSBSpeed;
    @BindView(R.id.m_checkToDisplay)
    SwitchCompat m_checkCastToDisplay;
    @BindView(R.id.m_hardtimer)
    SwitchCompat m_checkHardtimer;
    @BindView(R.id.m_lol)
    SwitchCompat m_checkLOL;
    @BindView(R.id.m_minecraft)
    SwitchCompat m_checkMineCraft;
    @BindView(R.id.m_checkRandomBleName)
    SwitchCompat m_checkRandomBleName;
    @BindView(R.id.m_touping)
    SwitchCompat m_checkTouping;
    @BindView(R.id.m_usbAudio)
    SwitchCompat m_checkUAC;
    @BindView(R.id.edit_display_x)
    EditText m_editDisplayX;
    @BindView(R.id.edit_display_y)
    EditText m_editDisplayY;
    @BindView(R.id.m_editRandownBleName)
    EditText m_editRandownBleName;
    @BindView(R.id.m_layoutRoot)
    RelativeLayout m_layoutRoot;
    @BindView(R.id.m_linerCastInfo)
    LinearLayout m_linerCastInfo;
    @BindView(R.id.m_linerRandomBleName)
    LinearLayout m_linerRandomBleName;
    @BindView(R.id.displayRes)
    TextView m_textDisplayRes;
    @BindView(R.id.textMulti)
    TextView m_textDisplayResMulti;
    private Spinner m_comMode = null;
    private byte[] m_arrSystemStatus = null;
    private ModelInfo m_sModelInfo = null;
    private SystemStatus m_sSysStatus = null;
    private List<Integer> m_lstItem2WorkMode = new ArrayList();
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    String m_strRamdomBleName = "";

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_changemode);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        hide_status_navigate_bar();
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(1024);
        }
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.layoutInDisplayCutoutMode = 1;
            getWindow().setAttributes(attributes);
        }
        byte[] byteArrayExtra = getIntent().getByteArrayExtra("para");
        this.m_arrSystemStatus = byteArrayExtra;
        this.m_sModelInfo = Constants.get_model_info(byteArrayExtra[3]);
        SystemStatus systemStatus = new SystemStatus(this.m_arrSystemStatus);
        this.m_sSysStatus = systemStatus;
        this.m_byUSBSpeed = systemStatus.byUSBSpeed;
        this.m_comMode = (Spinner) findViewById(R.id.m_comMode);
        init_combobox();
        this.m_checkTouping.setChecked(this.m_sSysStatus.is_in_touping_mode());
        this.m_checkUAC.setChecked(true ^ this.m_sSysStatus.is_usb_audio_off());
        this.m_checkLOL.setChecked(this.m_sSysStatus.is_in_moba_mode());
        this.m_checkMineCraft.setChecked(this.m_sSysStatus.is_in_minecraft_mode());
        this.m_checkHardtimer.setChecked(this.m_sSysStatus.is_usb_mode_hard_acc());
        this.m_checkRandomBleName.setChecked(this.m_sSysStatus.is_random_ble_name());
        if (this.m_sSysStatus.is_random_ble_name() && AppInstance.s_strBTName.length() >= 8) {
            String substring = AppInstance.s_strBTName.substring(0, 8);
            this.m_strRamdomBleName = substring;
            this.m_editRandownBleName.setText(substring);
        }
        update_ctrls();
        update_bg_image();
    }

    private boolean HAS_MOBA_CAPACITY() {
        return Units.is_bit_on(this.m_arrSystemStatus[13], 0);
    }

    private boolean HAS_MINICRAFT_CAPACITY() {
        return Units.is_bit_on(this.m_arrSystemStatus[13], 1);
    }

    private boolean HAS_X1_CAPACITY() {
        return Units.is_bit_on(this.m_arrSystemStatus[13], 2);
    }

    private boolean HAS_VIEW_CAST_CAPACITY() {
        return Units.is_bit_on(this.m_arrSystemStatus[13], 3);
    }

    public void init_combobox() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.CtrlF1));
        this.m_lstItem2WorkMode.add(0);
        arrayList.add(getString(R.string.CtrlF2));
        this.m_lstItem2WorkMode.add(1);
        arrayList.add(getString(R.string.CtrlF3));
        this.m_lstItem2WorkMode.add(2);
        ModelInfo modelInfo = this.m_sModelInfo;
        if (modelInfo != null) {
            if (modelInfo.m_boSupportUSBMode) {
                arrayList.add(getString(R.string.CtrlF4));
                this.m_lstItem2WorkMode.add(3);
            }
            if (this.m_sModelInfo.m_boSupportX1 || HAS_X1_CAPACITY()) {
                arrayList.add(getString(R.string.CtrlF5));
                this.m_lstItem2WorkMode.add(4);
            }
        }
        if (this.m_sSysStatus.has_NTS_capacity() && NEAT.is_chinese()) {
            arrayList.add(getString(R.string.nts_mode));
            this.m_lstItem2WorkMode.add(5);
        }
        if (this.m_sSysStatus.has_usb_mtk_capacity()) {
            arrayList.add(getString(R.string.usb_mtk_mode));
            this.m_lstItem2WorkMode.add(6);
        }
        this.m_comMode.setAdapter((SpinnerAdapter) new ArrayAdapter(this, 17367048, (String[]) arrayList.toArray(new String[arrayList.size()])));
        int size = arrayList.size();
        if (Units.is_bit_on(this.m_arrSystemStatus[10], 0)) {
            this.m_comMode.setSelection(1);
        } else if (Units.is_bit_on(this.m_arrSystemStatus[10], 7)) {
            this.m_comMode.setSelection(2);
        } else if (Units.is_bit_on(this.m_arrSystemStatus[5], 1)) {
            if (this.m_sSysStatus.is_in_NTS_mode()) {
                this.m_comMode.setSelection(5);
            } else {
                this.m_comMode.setSelection(size > 3 ? 3 : 0);
            }
        } else if (Units.is_bit_on(this.m_arrSystemStatus[5], 7)) {
            this.m_comMode.setSelection(size > 4 ? 4 : 0);
        } else if (this.m_sSysStatus.is_in_MTK_USB_mode()) {
            this.m_comMode.setSelection(size > 6 ? 6 : 0);
        } else {
            this.m_comMode.setSelection(0);
        }
        this.m_comMode.setOnItemSelectedListener(this);
    }

    private void update_ctrls() {
        boolean z;
        boolean z2;
        boolean HAS_MINICRAFT_CAPACITY;
        boolean z3;
        boolean z4;
        boolean z5;
        int intValue = this.m_lstItem2WorkMode.get(this.m_comMode.getSelectedItemPosition()).intValue();
        boolean z6 = true;
        boolean z7 = (this.m_sSysStatus.uSystemVer >= 60 && this.m_sSysStatus.uSystemVer < 250) || HAS_VIEW_CAST_CAPACITY();
        switch (intValue) {
            case 0:
            case 2:
                z = this.m_sModelInfo.m_boSupportUAC;
                z2 = this.m_sModelInfo.m_boSupportMOBA || HAS_MOBA_CAPACITY();
                HAS_MINICRAFT_CAPACITY = HAS_MINICRAFT_CAPACITY();
                z3 = HAS_MINICRAFT_CAPACITY;
                z4 = z2;
                z5 = false;
                break;
            case 1:
            default:
                z = false;
                z6 = false;
                z5 = false;
                z4 = false;
                z3 = false;
                break;
            case 3:
            case 5:
            case 6:
                z = this.m_sModelInfo.m_boSupportUAC;
                z2 = this.m_sModelInfo.m_boSupportMOBA || HAS_MOBA_CAPACITY();
                HAS_MINICRAFT_CAPACITY = HAS_MINICRAFT_CAPACITY();
                if (this.m_sSysStatus.uSystemVer >= 80) {
                    z3 = HAS_MINICRAFT_CAPACITY;
                    z4 = z2;
                    z5 = true;
                    break;
                }
                z3 = HAS_MINICRAFT_CAPACITY;
                z4 = z2;
                z5 = false;
                break;
            case 4:
                z = this.m_sModelInfo.m_boSupportUAC;
                z6 = false;
                z5 = false;
                z4 = false;
                z3 = false;
                break;
        }
        this.m_checkUAC.setVisibility(z ? 0 : 8);
        this.m_checkMineCraft.setVisibility(z3 ? 0 : 8);
        this.m_checkTouping.setVisibility((z7 && z6) ? 0 : 8);
        this.m_checkLOL.setVisibility(z4 ? 0 : 8);
        this.m_checkHardtimer.setVisibility(z5 ? 0 : 8);
        this.m_btnToupingVideo.setVisibility(this.m_checkTouping.isChecked() ? 0 : 8);
        update_touping_all();
        findViewById(R.id.m_tableRowUSBSpeed).setVisibility((this.m_sSysStatus.has_usb_speed_asjust_capacity() && z5) ? 0 : 8);
        this.m_btnUSBSpeed.setText(g_arrUSBSpeed[this.m_byUSBSpeed]);
        findViewById(R.id.m_tableRowRandomBleName).setVisibility((this.m_sSysStatus.has_random_ble_name_capacity() && NEAT.is_chinese()) ? 0 : 8);
        this.m_editRandownBleName.setVisibility(this.m_checkRandomBleName.isChecked() ? 0 : 8);
        this.m_btnGenerateBleName.setVisibility(this.m_checkRandomBleName.isChecked() ? 0 : 8);
    }

    void update_touping_all() {
        if (this.m_sSysStatus.has_touping_to_monitor_capacity()) {
            this.m_checkCastToDisplay.setVisibility(this.m_checkTouping.isChecked() ? 0 : 8);
            if (this.m_checkTouping.isChecked() && AppInstance.s_nToupingDisplayX != 0 && AppInstance.s_nToupingDisplayY != 0) {
                this.m_checkCastToDisplay.setChecked(true);
                update_tuoping_ctrls(true);
                return;
            }
            this.m_checkCastToDisplay.setChecked(false);
            update_tuoping_ctrls(false);
            return;
        }
        this.m_checkCastToDisplay.setVisibility(8);
        this.m_checkCastToDisplay.setChecked(false);
        update_tuoping_ctrls(false);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        update_ctrls();
        String str = TAG;
        Log.d(str, "Pos=" + i + "id=" + j);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.mCompositeDisposable.clear();
        super.onDestroy();
    }

    public void hide_status_navigate_bar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 256 | 1024 | 512 | 4 | 2 | 4096);
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().setStatusBarColor(0);
            getWindow().setNavigationBarColor(0);
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.systemUiVisibility = 2050;
        getWindow().setAttributes(attributes);
    }

    @OnCheckedChanged({R.id.m_touping})
    public void onTouping(SwitchCompat switchCompat, boolean z) {
        update_ctrls();
    }

    @OnCheckedChanged({R.id.m_usbAudio})
    public void onUAC(SwitchCompat switchCompat, boolean z) {
        update_ctrls();
    }

    @OnCheckedChanged({R.id.m_lol})
    public void onLOL(SwitchCompat switchCompat, boolean z) {
        if (z && this.m_checkMineCraft.isChecked()) {
            this.m_checkMineCraft.setChecked(false);
        }
        update_ctrls();
    }

    @OnCheckedChanged({R.id.m_minecraft})
    public void onMinecraft(SwitchCompat switchCompat, boolean z) {
        if (z && this.m_checkLOL.isChecked()) {
            this.m_checkLOL.setChecked(false);
        }
        update_ctrls();
    }

    @OnClick({R.id.cancel})
    public void onCancel() {
        finish();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00bf  */
    @butterknife.OnClick({com.baidu.kwgames.R.id.ok})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onOk() {
        /*
            Method dump skipped, instructions count: 240
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.kwgames.ChangeModeActivity.onOk():void");
    }

    void update_tuoping_ctrls(boolean z) {
        int i = z ? 0 : 8;
        this.m_textDisplayRes.setVisibility(i);
        this.m_textDisplayResMulti.setVisibility(i);
        this.m_editDisplayX.setVisibility(i);
        this.m_editDisplayY.setVisibility(i);
        if (z) {
            if (AppInstance.s_nToupingDisplayX != 0 && AppInstance.s_nToupingDisplayY != 0) {
                this.m_editDisplayX.setText(NEAT.int_to_string(AppInstance.s_nToupingDisplayX));
                this.m_editDisplayY.setText(NEAT.int_to_string(AppInstance.s_nToupingDisplayY));
                return;
            }
            int i2 = m_ini.getInt(Constants.CFG_TOUPING_DISPLAY_X);
            int i3 = m_ini.getInt(Constants.CFG_TOUPING_DISPLAY_Y);
            if (-1 == i2 || -1 == i3) {
                this.m_editDisplayX.setText(NEAT.int_to_string(UIConst.E_TOUPING_DISPLAY_X_DEF));
                this.m_editDisplayY.setText(NEAT.int_to_string(UIConst.E_TOUPING_DISPLAY_Y_DEF));
                return;
            }
            this.m_editDisplayX.setText(NEAT.int_to_string(i2));
            this.m_editDisplayY.setText(NEAT.int_to_string(i3));
        }
    }

    @OnCheckedChanged({R.id.m_checkToDisplay})
    public void onCastToDisplay(SwitchCompat switchCompat, boolean z) {
        update_tuoping_ctrls(z);
    }

    @OnClick({R.id.m_btnModeVideo})
    public void onModeVideo() {
        this.m_lstItem2WorkMode.get(this.m_comMode.getSelectedItemPosition()).intValue();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(Constants.URL_VIDEO_TOUPING));
        startActivity(intent);
    }

    @OnClick({R.id.btn_touping_question})
    public void onToupingVideo() {
        MsgBox.msg_box1_with_choice2(this, R.string.touping_description, R.string.iamknow, R.string.ineedvideo, new Handler() { // from class: com.baidu.kwgames.ChangeModeActivity.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (1 == message.arg2) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(Constants.URL_VIDEO_TOUPING));
                    ChangeModeActivity.this.startActivity(intent);
                }
            }
        });
    }

    @OnClick({R.id.m_btnUSBSpeed})
    public void onUSBSpeed() {
        this.m_byUSBSpeed = (this.m_byUSBSpeed + 1) & 3;
        update_ctrls();
        MsgBox.msg_box1_once(this, R.string.adjust_usb_speed_msg);
    }

    void update_bg_image() {
        this.m_layoutRoot.setBackgroundResource(UIConst.s_arrBGImages[m_ini.getInt(Constants.CFG_BG_IMAGE, 0)]);
    }

    @OnCheckedChanged({R.id.m_checkRandomBleName})
    public void onCheckRandomBleName(SwitchCompat switchCompat, boolean z) {
        if (z && this.m_strRamdomBleName.isEmpty()) {
            onGenerateBleName();
        }
        update_ctrls();
    }

    int get_a_random_name_int(Random random) {
        int nextInt = random.nextInt(31);
        if (nextInt < 0) {
            nextInt = -nextInt;
        }
        return nextInt & 31;
    }

    @OnClick({R.id.m_btnGenerateBleName})
    public void onGenerateBleName() {
        this.m_strRamdomBleName = "";
        Random random = new Random();
        int i = get_a_random_name_int(random);
        int i2 = get_a_random_name_int(random);
        this.m_strRamdomBleName += Constants.g_arrBleNameRandom[i];
        this.m_strRamdomBleName += Constants.g_arrBleNameRandom[(i + 2) & 31];
        this.m_strRamdomBleName += Constants.g_arrBleNameRandom[i2];
        this.m_strRamdomBleName += Constants.g_arrBleNameRandom[(i2 + 1) & 31];
        for (int i3 = 0; i3 < 4; i3++) {
            this.m_strRamdomBleName += Constants.g_arrBleNameRandom[get_a_random_name_int(random)];
        }
        this.m_editRandownBleName.setText(this.m_strRamdomBleName);
    }
}
