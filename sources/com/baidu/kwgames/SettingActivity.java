package com.baidu.kwgames;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import com.baidu.kwgames.dialog.DownLoadDialog;
import com.baidu.kwgames.net.HttpHelper;
import com.baidu.kwgames.net.rsq.UpdateRsq;
import com.baidu.kwgames.util.FloatMgr;
import com.baidu.kwgames.util.ImageSwButton;
import com.baidu.kwgames.util.MsgBox;
import com.baidu.kwgames.util.NEAT;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.opencv.videoio.Videoio;
/* loaded from: classes.dex */
public class SettingActivity extends AppCompatActivity {
    public static final int E_SETTING_RET_GOTO_BACKUP = 2;
    public static final int E_SETTING_RET_GOTO_CHANGE_MODE = 1;
    private static final String TAG = "SettingActivity";
    @BindView(R.id.always_jump)
    SwitchCompat mAlwaysJump;
    @BindView(R.id.auto_pointer)
    SwitchCompat mAutoPointer;
    private DownLoadDialog mDialog;
    private int mEq;
    @BindView(R.id.magnification)
    SwitchCompat mMagnification;
    @BindView(R.id.press_gun)
    SwitchCompat mPressGun;
    @BindView(R.id.qe_open_scope)
    AppCompatButton mQeOpenScope;
    @BindView(R.id.special_version)
    SwitchCompat mSpecialVersion;
    @BindView(R.id.sprint)
    SwitchCompat mSprint;
    private byte mStatus;
    private byte mStatusExt;
    private TextView mTipTextView;
    @BindView(R.id.update_btn)
    Button mUpdateBtn;
    @BindView(R.id.virtual_mouse)
    SwitchCompat mVirtualMouse;
    private boolean m_boEditParam;
    boolean m_boIniting;
    @BindView(R.id.m_btnADDownAutoAD)
    Button m_btnADDownAutoAD;
    @BindView(R.id.m_btnAutoADSpeed)
    Button m_btnAutoADSpeed;
    @BindView(R.id.m_btnBG)
    Button m_btnBG;
    @BindView(R.id.m_btnBackup)
    Button m_btnBackup;
    @BindView(R.id.m_btnChangeMode)
    Button m_btnChangeMode;
    @BindView(R.id.m_btnCtrlRepeatSpeed)
    Button m_btnCtrlRepeatSpeed;
    @BindView(R.id.m_btnCursor)
    Button m_btnCursor;
    @BindView(R.id.m_btnHPVolPlus)
    Button m_btnHPVolPlus;
    @BindView(R.id.m_btnMouseWheelSwGun)
    SwitchCompat m_btnMouseWheelSwGun;
    @BindView(R.id.m_btnShiftToWalk)
    SwitchCompat m_btnShiftToWalk;
    @BindView(R.id.m_btnShunfenger)
    Button m_btnShunfenger;
    @BindView(R.id.m_btnUSBVMouseHz)
    Button m_btnUSBVMouseHz;
    @BindView(R.id.m_btnVMouseThrowL)
    Button m_btnVMouseThrowL;
    @BindView(R.id.btn_virtual_mouse_center)
    Button m_btnVirtualMouseCenter;
    private byte m_byADDownAutoAD;
    private byte m_byADDownAutoADSpeed;
    private byte m_byCtrlRepeatSpeed;
    private byte m_byHPVol;
    private byte m_byShunfenger;
    private byte m_byStatusExt3;
    private byte m_byStatusExt4;
    private byte m_byStatusExt5;
    @BindView(R.id.m_btnCFDynamicContinues)
    SwitchCompat m_checkCFDynamicContinues;
    @BindView(R.id.hold_ctrl_in_fire_repeat)
    SwitchCompat m_checkCtrlHoldRepeat;
    @BindView(R.id.click_e_in_fire_repeat)
    SwitchCompat m_checkEHoldRepeat;
    @BindView(R.id.click_q_in_fire_repeat)
    SwitchCompat m_checkQHoldRepeat;
    @BindView(R.id.m_layoutRoot)
    RelativeLayout m_layoutRoot;
    private WebHandler m_webHandler;
    private static SPUtils m_ini = SPUtils.getInstance();
    static final String[] g_arrHPVol = {"100%", "125%", "150%", "200%", "225%", "250%", "275%", "300%"};
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Map<Integer, String> mTipMap = new HashMap();
    int m_nCurBG = 0;
    int m_nCurCursor = 0;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_setting);
        TextView textView = (TextView) findViewById(R.id.tip_text);
        this.mTipTextView = textView;
        textView.setText("");
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        this.m_webHandler = new WebHandler(this);
        hide_status_navigate_bar();
        initTipText();
        AppInstance.settingPageOpenTime = System.currentTimeMillis();
        ImageSwButton.add(this.m_btnVirtualMouseCenter, R.mipmap.vmouse_c_on_ch, R.mipmap.vmouse_c_off_ch, R.mipmap.vmouse_c_on_en, R.mipmap.vmouse_c_off_en);
        ImageSwButton.add(this.m_btnVMouseThrowL, R.mipmap.throw_l_on_ch, R.mipmap.throw_l_off_ch, R.mipmap.throw_l_on_en, R.mipmap.throw_l_off_en);
        ImageSwButton.add(this.m_btnADDownAutoAD, R.mipmap.auto_ad_on, R.mipmap.auto_ad_off, R.mipmap.auto_ad_on_en, R.mipmap.auto_ad_off_en);
        ImageSwButton.add(this.m_btnShunfenger, R.mipmap.shunfenger_on, R.mipmap.shunfenger_off, R.mipmap.shunfenger_on_en, R.mipmap.shunfenger_off_en);
        if (AppInstance.has_device_connect()) {
            this.m_boIniting = true;
            Intent intent = getIntent();
            this.mStatus = intent.getByteExtra("status", (byte) 0);
            this.mStatusExt = intent.getByteExtra("statusExt", (byte) 0);
            this.m_byStatusExt3 = intent.getByteExtra("statusExt3", (byte) 0);
            this.m_byStatusExt4 = intent.getByteExtra("statusExt4", (byte) 0);
            this.m_byStatusExt5 = intent.getByteExtra("statusExt5", (byte) 0);
            this.m_byADDownAutoAD = intent.getByteExtra("byADDownAutoAD", (byte) 0);
            this.m_byADDownAutoADSpeed = intent.getByteExtra("byADDownAutoADSpeed", (byte) 0);
            this.m_byHPVol = intent.getByteExtra("hpvol", (byte) 0);
            this.m_byShunfenger = intent.getByteExtra("shunfenger", (byte) 0);
            this.m_byCtrlRepeatSpeed = intent.getByteExtra("ctrlrepeatsp", (byte) 0);
            this.mSprint.setChecked(((this.mStatus >> 3) & 1) == 1);
            this.mSpecialVersion.setChecked(((this.mStatus >> 2) & 1) == 1);
            this.mPressGun.setChecked(((this.mStatus >> 4) & 1) == 1);
            this.mVirtualMouse.setChecked(((this.mStatus >> 5) & 1) == 1);
            update_vmouse_center_button();
            this.m_checkCFDynamicContinues.setVisibility(AppInstance.g_sSysStatus.uSystemVer > 86 ? 0 : 8);
            this.m_checkCFDynamicContinues.setChecked(Units.is_bit_on(this.m_byStatusExt4, 0));
            this.mAutoPointer.setChecked((this.mStatusExt & 1) == 1);
            this.mAlwaysJump.setChecked(((this.mStatusExt >> 2) & 1) == 1);
            if (18 == AppInstance.g_sSysStatus.get_device_ID()) {
                this.mAlwaysJump.setVisibility(8);
            }
            this.mMagnification.setChecked(((this.mStatusExt >> 3) & 1) == 1);
            this.m_checkCtrlHoldRepeat.setChecked(((this.m_byStatusExt3 >> 3) & 1) == 1);
            this.m_checkEHoldRepeat.setChecked(((this.m_byStatusExt3 >> 4) & 1) == 1);
            this.m_checkQHoldRepeat.setChecked(((this.m_byStatusExt3 >> 5) & 1) == 1);
            if (AppInstance.g_sSysStatus.uSystemVer < 75) {
                this.m_checkCtrlHoldRepeat.setVisibility(8);
                this.m_checkEHoldRepeat.setVisibility(8);
                this.m_checkQHoldRepeat.setVisibility(8);
            }
            int i = (this.mStatusExt >> 4) & 3;
            this.mEq = i;
            if (i >= 3) {
                this.mEq = 0;
            }
            update_qe_auto_scope_text();
            this.m_btnShiftToWalk.setVisibility(AppInstance.g_sSysStatus.has_shift_2_walk_capacity() ? 0 : 8);
            this.m_btnShiftToWalk.setChecked(Units.is_bit_on(this.m_byStatusExt4, 2));
            this.m_btnMouseWheelSwGun.setVisibility(AppInstance.g_sSysStatus.has_mouse_wheel_sw_gun_capacity() ? 0 : 8);
            this.m_btnMouseWheelSwGun.setChecked(Units.is_bit_on(this.m_byStatusExt4, 3));
            update_usb_vmouse_Hz_button();
            update_other_UI();
            this.m_boEditParam = true;
        } else {
            this.mSpecialVersion.setVisibility(8);
            this.mPressGun.setVisibility(8);
            this.mVirtualMouse.setVisibility(8);
            this.mAutoPointer.setVisibility(8);
            this.mAlwaysJump.setVisibility(8);
            this.mMagnification.setVisibility(8);
            this.mQeOpenScope.setVisibility(8);
            this.mTipTextView.setVisibility(8);
            this.m_btnChangeMode.setVisibility(8);
            this.m_checkCtrlHoldRepeat.setVisibility(8);
            this.m_btnCtrlRepeatSpeed.setVisibility(8);
            this.m_checkEHoldRepeat.setVisibility(8);
            this.m_checkQHoldRepeat.setVisibility(8);
            this.m_checkCFDynamicContinues.setVisibility(8);
            this.m_btnVirtualMouseCenter.setVisibility(8);
            this.m_btnVMouseThrowL.setVisibility(8);
            this.m_btnShiftToWalk.setVisibility(8);
            this.m_btnMouseWheelSwGun.setVisibility(8);
            this.m_btnUSBVMouseHz.setVisibility(8);
            findViewById(R.id.m_layoutL).setVisibility(8);
            this.m_boEditParam = false;
        }
        update_bg_image();
        update_cursor_image();
        String upperCase = Units.getSystemModel().toUpperCase();
        if (-1 == upperCase.indexOf("X9") && -1 == upperCase.indexOf("R9")) {
            this.mSpecialVersion.setVisibility(4);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(1024);
        }
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.layoutInDisplayCutoutMode = 1;
            getWindow().setAttributes(attributes);
        }
        this.mUpdateBtn.setOnClickListener(new AnonymousClass1());
        this.mTipTextView.setText("");
        this.m_boIniting = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.SettingActivity$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements View.OnClickListener {
        AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            SettingActivity.this.mCompositeDisposable.add(HttpHelper.request(Constants.URL_UPDATE).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() { // from class: com.baidu.kwgames.SettingActivity.1.1
                @Override // io.reactivex.functions.Consumer
                public void accept(String str) throws Exception {
                    if (TextUtils.isEmpty(str)) {
                        return;
                    }
                    final UpdateRsq updateRsq = (UpdateRsq) new Gson().fromJson(str, (Class<Object>) UpdateRsq.class);
                    if (Float.parseFloat(updateRsq.strVer) > Float.parseFloat(AppInstance.s_strAppVer)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                        builder.setIcon(R.mipmap.ic_launcher);
                        builder.setTitle("");
                        String string = SettingActivity.this.getString(R.string.find_new_app_ver);
                        builder.setMessage(string + "(V" + updateRsq.strVer + ")?");
                        builder.setPositiveButton(SettingActivity.this.getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.SettingActivity.1.1.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent("android.intent.action.VIEW");
                                intent.setData(Uri.parse(updateRsq.arkUrl));
                                SettingActivity.this.startActivity(intent);
                            }
                        });
                        builder.setNegativeButton(SettingActivity.this.getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.SettingActivity.1.1.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        builder.show();
                        return;
                    }
                    Toast.makeText(SettingActivity.this, (int) R.string.new_version, 0).show();
                }
            }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.SettingActivity.1.2
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable th) throws Exception {
                }
            }));
        }
    }

    void update_usb_vmouse_Hz_button() {
        int i = 0;
        if (AppInstance.g_sSysStatus.is_in_usb_mode() && AppInstance.g_sSysStatus.is_support_virtual_mouse_USB() && !AppInstance.g_sSysStatus.is_in_NTS_mode()) {
            this.m_btnUSBVMouseHz.setVisibility((!this.mVirtualMouse.isChecked() || AppInstance.g_sSysStatus.uSystemVer < 100) ? 8 : 8);
            this.m_btnUSBVMouseHz.setText(UIConst.g_arrUSBVMouseHz[this.m_byStatusExt5 & 3]);
            return;
        }
        this.m_btnUSBVMouseHz.setVisibility((!this.mVirtualMouse.isChecked() || AppInstance.g_sSysStatus.uSystemVer < 101) ? 8 : 8);
        Button button = this.m_btnUSBVMouseHz;
        button.setText(NEAT.s(R.string.speed) + (((this.m_byStatusExt5 >> 2) & 3) + 1));
    }

    void update_vmouse_center_button() {
        this.m_btnVirtualMouseCenter.setVisibility((!this.mVirtualMouse.isChecked() || AppInstance.g_sSysStatus.uSystemVer <= 86 || AppInstance.g_sSysStatus.uSystemVer >= 250) ? 8 : 0);
        ImageSwButton.set_onoff(this.m_btnVirtualMouseCenter, Units.is_bit_on(this.m_byStatusExt4, 1));
    }

    private void update_qe_auto_scope_text() {
        int i = this.mEq;
        if (i == 0) {
            this.mQeOpenScope.setText(getString(R.string.qe_auto_scope_off));
        } else if (i == 1) {
            this.mQeOpenScope.setText(getString(R.string.qe_auto_scope_click));
        } else if (i != 2) {
        } else {
            this.mQeOpenScope.setText(getString(R.string.qe_auto_scope_hold));
        }
    }

    private void initTipText() {
        this.mTipMap.put(Integer.valueOf((int) R.id.sprint), getString(R.string.wasd_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.m_btnADDownAutoAD), getString(R.string.ad_down_auto_ad_sw_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.m_btnAutoADSpeed), getString(R.string.ad_down_auto_ad_speed_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.press_gun), getString(R.string.press_gun_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.virtual_mouse), getString(R.string.virtual_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.special_version), getString(R.string.special_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.magnification), getString(R.string.magnification_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.auto_pointer), getString(R.string.auto_pointer));
        this.mTipMap.put(Integer.valueOf((int) R.id.always_jump), getString(R.string.always_jump));
        this.mTipMap.put(Integer.valueOf((int) R.id.qe_open_scope), getString(R.string.qe_open_ope_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.hold_ctrl_in_fire_repeat), getString(R.string.ctrl_hold_repeat_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.click_e_in_fire_repeat), getString(R.string.e_click_in_fire_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.click_q_in_fire_repeat), getString(R.string.q_click_in_fire_repeat_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.m_btnShiftToWalk), getString(R.string.shift_2_no_run_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.m_btnMouseWheelSwGun), getString(R.string.mouse_wheel_sw_gun_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.m_btnCFDynamicContinues), getString(R.string.cf_dynamic_continues_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.btn_virtual_mouse_center), getString(R.string.vmoue_in_center_tip));
        this.mTipMap.put(Integer.valueOf((int) R.id.m_btnUSBVMouseHz), getString(AppInstance.mBleConnected ? R.string.btn_ble_vmouse_hz : R.string.btn_usb_vmouse_hz));
        this.mTipMap.put(Integer.valueOf((int) R.id.m_btnVMouseThrowL), getString(R.string.vmouse_throw_l_tip));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.mCompositeDisposable.clear();
        AppInstance.settingPageCloseTime = System.currentTimeMillis();
        if ((AppInstance.settingPageCloseTime - AppInstance.settingPageOpenTime) / 1000 < 3) {
            AppInstance.settingPageCloseAmount++;
            if (AppInstance.settingPageCloseAmount >= 3) {
                AppInstance.settingPageCloseAmount = 0;
                if (NEAT.is_gps_open()) {
                    startActivity(new Intent(this, FirmUpdateActivity.class).putExtra("mac", AppInstance.mac).addFlags(67108864));
                } else {
                    NEAT.toast(R.string.gps_not_open);
                }
            }
        } else {
            AppInstance.settingPageCloseAmount = 0;
        }
        ImageSwButton.delete(this.m_btnVirtualMouseCenter);
        ImageSwButton.delete(this.m_btnADDownAutoAD);
        super.onDestroy();
    }

    private void downLoadApp(String str) {
        DownLoadDialog downLoadDialog = this.mDialog;
        if (downLoadDialog == null || !downLoadDialog.isShowing()) {
            DownLoadDialog downLoadDialog2 = new DownLoadDialog(this);
            this.mDialog = downLoadDialog2;
            downLoadDialog2.setCanceledOnTouchOutside(false);
            this.mDialog.setCancelable(false);
            this.mDialog.setUrl(str);
            this.mDialog.show();
            this.mDialog.setDownLoadFinish(new DownLoadDialog.DownloadListener() { // from class: com.baidu.kwgames.SettingActivity.2
                @Override // com.baidu.kwgames.dialog.DownLoadDialog.DownloadListener
                public void onFinish(String str2) {
                    SettingActivity.this.installApk(str2);
                }
            });
        }
    }

    public void installApk(String str) {
        Uri parse;
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
        File file = new File(str);
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(1);
            parse = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
            intent.setDataAndType(parse, "application/vnd.android.package-archive");
        } else {
            parse = Uri.parse("file://" + file.toString());
        }
        intent.setDataAndType(parse, "application/vnd.android.package-archive");
        startActivity(intent);
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

    @OnClick({R.id.qe_open_scope})
    public void onEeOpenScope() {
        int i = this.mEq + 1;
        this.mEq = i;
        this.mEq = i % 3;
        update_qe_auto_scope_text();
        this.mStatusExt = (byte) (((byte) (this.mStatusExt & (-49))) | (this.mEq << 4));
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.qe_open_scope)));
    }

    @OnCheckedChanged({R.id.sprint})
    public void onSprint(SwitchCompat switchCompat, boolean z) {
        if (z) {
            this.mStatus = (byte) (this.mStatus | 8);
        } else {
            this.mStatus = (byte) (this.mStatus & (-9));
        }
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.sprint)));
    }

    @OnCheckedChanged({R.id.press_gun})
    public void onPressGun(SwitchCompat switchCompat, boolean z) {
        if (z) {
            this.mStatus = (byte) (this.mStatus | 16);
        } else {
            this.mStatus = (byte) (this.mStatus & (-17));
        }
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.press_gun)));
    }

    @OnCheckedChanged({R.id.auto_pointer})
    public void onAutoPointer(SwitchCompat switchCompat, boolean z) {
        if (z) {
            this.mStatusExt = (byte) (this.mStatusExt | 1);
        } else {
            this.mStatusExt = (byte) (this.mStatusExt & (-2));
            MsgBox.msg_box_with_never_remind_once(R.string.close_smart_gun_remind, "close_smart_gun_remind");
        }
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.auto_pointer)));
    }

    @OnCheckedChanged({R.id.special_version})
    public void onSpecialVersion(SwitchCompat switchCompat, boolean z) {
        if (z) {
            this.mStatus = (byte) (this.mStatus | 4);
        } else {
            this.mStatus = (byte) (this.mStatus & (-5));
        }
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.special_version)));
    }

    @OnCheckedChanged({R.id.always_jump})
    public void onAlwaysJump(SwitchCompat switchCompat, boolean z) {
        if (z) {
            this.mStatusExt = (byte) (this.mStatusExt | 4);
        } else {
            this.mStatusExt = (byte) (this.mStatusExt & (-5));
        }
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.always_jump)));
    }

    @OnCheckedChanged({R.id.virtual_mouse})
    public void onVirtualMouse(SwitchCompat switchCompat, boolean z) {
        if (this.m_boIniting) {
            return;
        }
        if (z) {
            this.mStatus = (byte) (this.mStatus | 32);
            MsgBox.msg_box1(this, (int) R.string.vmouse_msgbox);
        } else {
            this.mStatus = (byte) (this.mStatus & (-33));
        }
        update_vmouse_center_button();
        update_usb_vmouse_Hz_button();
        update_other_UI();
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.virtual_mouse)));
    }

    @OnCheckedChanged({R.id.magnification})
    public void onMagnification(SwitchCompat switchCompat, boolean z) {
        if (z) {
            this.mStatusExt = (byte) (this.mStatusExt | 8);
        } else {
            this.mStatusExt = (byte) (this.mStatusExt & (-9));
        }
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.magnification)));
    }

    @OnCheckedChanged({R.id.hold_ctrl_in_fire_repeat})
    public void onHoldCtrlInFireRepeat(SwitchCompat switchCompat, boolean z) {
        if (z) {
            this.m_byStatusExt3 = (byte) (this.m_byStatusExt3 | 8);
        } else {
            this.m_byStatusExt3 = (byte) (this.m_byStatusExt3 & (-9));
        }
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.hold_ctrl_in_fire_repeat)));
        update_other_UI();
    }

    @OnClick({R.id.m_btnCtrlRepeatSpeed})
    public void onButtonCtrlRepeatSpeed() {
        this.m_byCtrlRepeatSpeed = (byte) (((byte) (this.m_byCtrlRepeatSpeed + 1)) & 3);
        update_other_UI();
    }

    @OnCheckedChanged({R.id.click_e_in_fire_repeat})
    public void onClickEInFireRepeat(SwitchCompat switchCompat, boolean z) {
        if (z) {
            this.m_byStatusExt3 = (byte) (this.m_byStatusExt3 | 16);
        } else {
            this.m_byStatusExt3 = (byte) (this.m_byStatusExt3 & (-17));
        }
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.click_e_in_fire_repeat)));
    }

    @OnCheckedChanged({R.id.click_q_in_fire_repeat})
    public void onClickQInFireRepeat(SwitchCompat switchCompat, boolean z) {
        if (z) {
            this.m_byStatusExt3 = (byte) (this.m_byStatusExt3 | 32);
        } else {
            this.m_byStatusExt3 = (byte) (this.m_byStatusExt3 & (-33));
        }
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.click_q_in_fire_repeat)));
    }

    @OnCheckedChanged({R.id.m_btnShiftToWalk})
    public void onClickShiftToWalk(SwitchCompat switchCompat, boolean z) {
        if (z) {
            this.m_byStatusExt4 = (byte) (this.m_byStatusExt4 | 4);
        } else {
            this.m_byStatusExt4 = (byte) (this.m_byStatusExt4 & (-5));
        }
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.m_btnShiftToWalk)));
    }

    @OnCheckedChanged({R.id.m_btnMouseWheelSwGun})
    public void onClickMouseWheelSwGun(SwitchCompat switchCompat, boolean z) {
        if (z) {
            this.m_byStatusExt4 = (byte) (this.m_byStatusExt4 | 8);
        } else {
            this.m_byStatusExt4 = (byte) (this.m_byStatusExt4 & (-9));
        }
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.m_btnMouseWheelSwGun)));
    }

    @OnCheckedChanged({R.id.m_btnCFDynamicContinues})
    public void onCFDynamicContinues(SwitchCompat switchCompat, boolean z) {
        if (z) {
            this.m_byStatusExt4 = Units.set_bit(this.m_byStatusExt4, 0);
        } else {
            this.m_byStatusExt4 = Units.clear_bit(this.m_byStatusExt4, 0);
        }
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.m_btnCFDynamicContinues)));
    }

    void update_other_UI() {
        int i = 8;
        this.m_btnADDownAutoAD.setVisibility(AppInstance.g_sSysStatus.has_AD_down_auto_AD_capacity() ? 0 : 8);
        ImageSwButton.set_onoff(this.m_btnADDownAutoAD, this.m_byADDownAutoAD != 0);
        Button button = this.m_btnAutoADSpeed;
        button.setText(NEAT.s(R.string.speed) + (this.m_byADDownAutoADSpeed + 1));
        this.m_btnAutoADSpeed.setVisibility((this.m_byADDownAutoAD == 0 || !AppInstance.g_sSysStatus.has_AD_down_auto_AD_capacity()) ? 8 : 0);
        this.m_btnShunfenger.setVisibility(AppInstance.g_sSysStatus.has_shunfenger_capacity() ? 0 : 8);
        ImageSwButton.set_onoff(this.m_btnShunfenger, this.m_byShunfenger != 0);
        findViewById(R.id.m_textHPVolTitle).setVisibility(AppInstance.g_sSysStatus.has_shunfenger_capacity() ? 0 : 8);
        this.m_btnHPVolPlus.setVisibility(AppInstance.g_sSysStatus.has_shunfenger_capacity() ? 0 : 8);
        this.m_btnHPVolPlus.setText(g_arrHPVol[this.m_byHPVol]);
        this.m_btnCtrlRepeatSpeed.setVisibility((AppInstance.g_sSysStatus.has_adjust_ctrl_repeat_speed_capacity() && this.m_checkCtrlHoldRepeat.isChecked()) ? 0 : 8);
        Button button2 = this.m_btnCtrlRepeatSpeed;
        button2.setText(NEAT.s(R.string.speed) + (this.m_byCtrlRepeatSpeed + 1));
        Button button3 = this.m_btnVMouseThrowL;
        if (this.mVirtualMouse.isChecked() && AppInstance.g_sSysStatus.uSystemVer > 117 && AppInstance.g_sSysStatus.uSystemVer < 250) {
            i = 0;
        }
        button3.setVisibility(i);
        ImageSwButton.set_onoff(this.m_btnVMouseThrowL, Units.is_bit_on(this.m_byStatusExt4, 7));
    }

    @OnClick({R.id.m_btnADDownAutoAD})
    public void onButtonADDownAutoAD() {
        this.m_byADDownAutoAD = (byte) (this.m_byADDownAutoAD == 0 ? 1 : 0);
        update_other_UI();
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.m_btnADDownAutoAD)));
        MsgBox.msg_box_with_never_remind_once_choice2(this, R.string.ad_down_auto_ad_sw_tip, "ad_down_auto_ad_sw_tip", R.string.iamknow, R.string.ineedvideo, this.m_webHandler.set_url(Constants.URL_VIDEO_AUTO_LR));
    }

    @OnClick({R.id.m_btnAutoADSpeed})
    public void onButtonADDownAutoADSpeed() {
        this.m_byADDownAutoADSpeed = (byte) (((byte) (this.m_byADDownAutoADSpeed + 1)) & 3);
        update_other_UI();
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.m_btnAutoADSpeed)));
    }

    @OnClick({R.id.m_btnHPVolPlus})
    public void onButtonHPVol() {
        byte b = (byte) (this.m_byHPVol + 1);
        this.m_byHPVol = b;
        if (b > 7) {
            this.m_byHPVol = (byte) 0;
        }
        update_other_UI();
        MsgBox.msg_box1_once(this, R.string.hp_vol_adjust_msg);
    }

    @OnClick({R.id.m_btnShunfenger})
    public void onButtonShunfenger() {
        this.m_byShunfenger = (byte) (this.m_byShunfenger == 0 ? 1 : 0);
        update_other_UI();
        if (1 == this.m_byShunfenger) {
            MsgBox.msg_box1_with_choice2(this, R.string.shunfenger_tip, R.string.iamknow, R.string.ineedvideo, this.m_webHandler.set_url(Constants.URL_VIDEO_SHUNFENGER));
        }
    }

    @OnClick({R.id.btn_virtual_mouse_center})
    public void onVMouseCenter() {
        if (Units.is_bit_on(this.m_byStatusExt4, 1)) {
            this.m_byStatusExt4 = Units.clear_bit(this.m_byStatusExt4, 1);
        } else {
            this.m_byStatusExt4 = Units.set_bit(this.m_byStatusExt4, 1);
        }
        update_vmouse_center_button();
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.btn_virtual_mouse_center)));
    }

    @OnClick({R.id.m_btnUSBVMouseHz})
    public void onUSBVMouseHz() {
        if (AppInstance.mBleConnected) {
            byte b = this.m_byStatusExt5;
            int i = ((b >> 2) & 3) + 1;
            this.m_byStatusExt5 = (byte) (((byte) (b & Constants.KEY_MOUSE)) | ((i <= 3 ? i : 0) << 2));
        } else {
            byte b2 = this.m_byStatusExt5;
            int i2 = (b2 & 3) + 1;
            this.m_byStatusExt5 = (byte) (((byte) (b2 & FileDownloadStatus.warn)) | (i2 <= 3 ? i2 : 0));
        }
        update_usb_vmouse_Hz_button();
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.m_btnUSBVMouseHz)));
    }

    @OnClick({R.id.m_btnVMouseThrowL})
    public void onVMouseThrowLeft() {
        if (Units.is_bit_on(this.m_byStatusExt4, 7)) {
            this.m_byStatusExt4 = Units.clear_bit(this.m_byStatusExt4, 7);
        } else {
            this.m_byStatusExt4 = Units.set_bit(this.m_byStatusExt4, 7);
        }
        update_other_UI();
        this.mTipTextView.setText(this.mTipMap.get(Integer.valueOf((int) R.id.m_btnVMouseThrowL)));
    }

    @OnClick({R.id.cancel})
    public void onCancel() {
        setResult(0, new Intent());
        finish();
    }

    @OnClick({R.id.ok})
    public void onOk() {
        AppInstance.settingPageCloseAmount = 0;
        Intent intent = new Intent();
        if (this.m_boEditParam) {
            intent.putExtra("status", this.mStatus);
            intent.putExtra("statusExt", this.mStatusExt);
            intent.putExtra("statusExt3", this.m_byStatusExt3);
            intent.putExtra("statusExt4", this.m_byStatusExt4);
            intent.putExtra("statusExt5", this.m_byStatusExt5);
            intent.putExtra("byADDownAutoAD", this.m_byADDownAutoAD);
            intent.putExtra("byADDownAutoADSpeed", this.m_byADDownAutoADSpeed);
            intent.putExtra("hpvol", this.m_byHPVol);
            intent.putExtra("shunfenger", this.m_byShunfenger);
            intent.putExtra("ctrlrepeatsp", this.m_byCtrlRepeatSpeed);
            setResult(-1, intent);
        } else {
            setResult(0, intent);
        }
        finish();
    }

    @OnClick({R.id.m_btnChangeMode})
    public void onChangeMode() {
        AppInstance.settingPageCloseAmount = 0;
        setResult(1, new Intent());
        finish();
    }

    @OnClick({R.id.m_btnBackup})
    public void onBackup() {
        AppInstance.settingPageCloseAmount = 0;
        setResult(2, new Intent());
        finish();
    }

    void update_bg_image() {
        this.m_nCurBG = m_ini.getInt(Constants.CFG_BG_IMAGE, 0) & 7;
        this.m_layoutRoot.setBackgroundResource(UIConst.s_arrBGImages[this.m_nCurBG]);
        Button button = this.m_btnBG;
        button.setText(NEAT.s(this, R.string.bg_image) + (this.m_nCurBG + 1));
    }

    @OnClick({R.id.m_btnBG})
    public void onBG() {
        int i = (this.m_nCurBG + 1) & 7;
        this.m_nCurBG = i;
        m_ini.put(Constants.CFG_BG_IMAGE, i);
        update_bg_image();
    }

    void update_cursor_image() {
        int i = m_ini.getInt(Constants.CFG_CURSOR_IMAGE, 0);
        this.m_nCurCursor = i;
        if (i >= UIConst.s_arrCursorImages.length) {
            this.m_nCurCursor = 0;
        }
        this.m_btnCursor.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(UIConst.s_arrCursorImages[this.m_nCurCursor]), (Drawable) null);
    }

    @OnClick({R.id.m_btnCursor})
    public void onCursor() {
        int i = this.m_nCurCursor + 1;
        this.m_nCurCursor = i;
        if (i >= UIConst.s_arrCursorImages.length) {
            this.m_nCurCursor = 0;
        }
        m_ini.put(Constants.CFG_CURSOR_IMAGE, this.m_nCurCursor);
        update_cursor_image();
        FloatMgr.setMouseImage(UIConst.s_arrCursorImages[this.m_nCurCursor]);
    }
}
