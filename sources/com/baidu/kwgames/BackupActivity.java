package com.baidu.kwgames;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.baidu.kwgames.adapter.BackupAdapter;
import com.baidu.kwgames.adapter.SelectAdapter;
import com.baidu.kwgames.util.MsgBox;
import com.baidu.kwgames.util.NEAT;
import com.baidu.kwgames.util.ShareWechatFriend;
import com.baidu.kwgames.util.Util;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import io.reactivex.disposables.CompositeDisposable;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;
/* loaded from: classes.dex */
public class BackupActivity extends BaseActivity {
    public static final int E_REVERT_AI_CROSSHAIR = 5;
    public static final int E_REVERT_AI_DYNAMIC = 3;
    public static final int E_REVERT_AI_GUN_DOWN = 2;
    public static final int E_REVERT_AI_GUN_PARTS = 4;
    public static final int E_REVERT_AI_POS = 1;
    public static final int E_REVERT_CNT = 9;
    public static final int E_REVERT_DY_CROSSHAIR = 7;
    public static final int E_REVERT_DY_GUNDOWN = 6;
    public static final int E_REVERT_OTHER_UI = 8;
    public static final int E_REVERT_PRESET = 0;
    public static final int RENAME_REQUEST = 1;
    public static final String STR_PREFIX = ".mybp";
    public static final String TAG_AI_CROSSHAIR = "aicrosshair";
    public static final String TAG_AI_GUNDOWN = "aigundown";
    public static final String TAG_AI_GUNN_DYNAMIC = "aigundynamic";
    public static final String TAG_AI_GUN_DYNAMIC_M4 = "aigundynamicm4";
    public static final String TAG_AI_GUN_PARTS = "aigunparts";
    public static final String TAG_AI_POS = "aipos";
    public static final String TAG_APP_VER = "appVer";
    public static final String TAG_ATTR_ID = "id";
    public static final String TAG_DY_CROSSHAIR = "dycrosshair";
    public static final String TAG_DY_GUNDOWN = "dygundown";
    public static final String TAG_OTHER_INI = "otherini";
    public static final String TAG_PHONE_MODEL = "phone";
    public static final String TAG_PRESET = "preset";
    private BackupAdapter m_adapter;
    private SelectAdapter m_adapterDetails;
    @BindView(R.id.m_btnBackup)
    Button m_btnBackup;
    @BindView(R.id.m_btnDelete)
    Button m_btnDelete;
    @BindView(R.id.m_btnExit)
    Button m_btnExit;
    @BindView(R.id.m_btnRestore)
    Button m_btnRestore;
    @BindView(R.id.m_btnShare)
    Button m_btnShare;
    @BindView(R.id.m_layoutRoot)
    LinearLayout m_layoutRoot;
    String m_strOpenSelFile;
    private static final String TAG = "SettingActivity";
    private static SPUtils m_ini = SPUtils.getInstance();
    ArrayList<String> m_arrBackupItems = new ArrayList<>();
    ArrayList<String> m_arrBackupFiles = new ArrayList<>();
    ArrayList<String> m_arrBackupDetailItems = new ArrayList<>();
    ArrayList<Boolean> m_arrBackupDetailSel = new ArrayList<>();
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override // com.baidu.kwgames.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_backup);
        ButterKnife.bind(this);
        this.m_strOpenSelFile = getIntent().getStringExtra("open");
        init_backup_details_list();
        init_backup_list();
        update_bg_image();
    }

    void init_system_backup() {
        boolean copyFileFromAssets;
        String str = AppInstance.s_strAppVer;
        if (str.equals(m_ini.getString(Constants.CFG_BACKUP_COPYED_VER, "0"))) {
            return;
        }
        try {
            if (NEAT.is_chinese()) {
                copyFileFromAssets = ResourceUtils.copyFileFromAssets("backup/zh", "/storage/emulated/0/MyBackup/");
            } else {
                copyFileFromAssets = ResourceUtils.copyFileFromAssets("backup/en", "/storage/emulated/0/MyBackup/");
            }
            if (copyFileFromAssets) {
                m_ini.put(Constants.CFG_BACKUP_COPYED_VER, str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void init_backup_list() {
        File file = new File(Constants.BACKUP_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        init_system_backup();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.m_backFile);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<File> listFilesInDir = FileUtils.listFilesInDir(Constants.BACKUP_DIR);
        this.m_arrBackupItems.clear();
        this.m_arrBackupFiles.clear();
        int i = 0;
        if (listFilesInDir != null) {
            for (File file2 : listFilesInDir) {
                String name = file2.getName();
                int indexOf = name.indexOf(STR_PREFIX);
                if (-1 != indexOf) {
                    this.m_arrBackupItems.add(name.substring(0, indexOf));
                    this.m_arrBackupFiles.add(file2.getPath());
                }
            }
        }
        BackupAdapter backupAdapter = new BackupAdapter(this, this.m_arrBackupItems, Constants.BACKUP_DIR);
        this.m_adapter = backupAdapter;
        backupAdapter.setClickListener(new BackupAdapter.ItemClickListener() { // from class: com.baidu.kwgames.BackupActivity.1
            @Override // com.baidu.kwgames.adapter.BackupAdapter.ItemClickListener
            public void onItemClick(View view, int i2) {
                ((BackupAdapter) recyclerView.getAdapter()).set_cur_item(i2);
                BackupActivity.this.update_detail_view();
            }
        });
        recyclerView.setAdapter(this.m_adapter);
        if (this.m_strOpenSelFile != null) {
            while (true) {
                if (i >= this.m_arrBackupItems.size()) {
                    break;
                } else if (-1 != this.m_strOpenSelFile.indexOf(this.m_arrBackupItems.get(i))) {
                    this.m_adapter.set_cur_item(i);
                    break;
                } else {
                    i++;
                }
            }
        }
        update_detail_view();
    }

    void init_backup_details_list() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.m_fileDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.m_arrBackupDetailItems.clear();
        this.m_arrBackupDetailSel.clear();
        this.m_arrBackupDetailItems.add(NEAT.s(this, R.string.backup_preset));
        this.m_arrBackupDetailItems.add(NEAT.s(this, R.string.backup_ai_rec_pos));
        this.m_arrBackupDetailItems.add(NEAT.s(this, R.string.backup_ai_gundown));
        this.m_arrBackupDetailItems.add(NEAT.s(this, R.string.backup_ai_dynamic));
        this.m_arrBackupDetailItems.add(NEAT.s(this, R.string.backup_ai_gun_parts));
        this.m_arrBackupDetailItems.add(NEAT.s(this, R.string.backup_ai_crosshair));
        this.m_arrBackupDetailItems.add(NEAT.s(this, R.string.backup_dy_gundown));
        this.m_arrBackupDetailItems.add(NEAT.s(this, R.string.backup_dy_crosshair));
        this.m_arrBackupDetailItems.add(NEAT.s(this, R.string.backup_other_ui));
        for (int i = 0; i < this.m_arrBackupDetailItems.size(); i++) {
            this.m_arrBackupDetailSel.add(true);
        }
        SelectAdapter selectAdapter = new SelectAdapter(this, this.m_arrBackupDetailItems, this.m_arrBackupDetailSel);
        this.m_adapterDetails = selectAdapter;
        selectAdapter.setClickListener(new SelectAdapter.ItemClickListener() { // from class: com.baidu.kwgames.BackupActivity.2
            @Override // com.baidu.kwgames.adapter.SelectAdapter.ItemClickListener
            public void onItemClick(View view, int i2) {
                BackupActivity.this.m_arrBackupDetailSel.set(i2, Boolean.valueOf(!BackupActivity.this.m_arrBackupDetailSel.get(i2).booleanValue()));
                BackupActivity.this.m_adapterDetails.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(this.m_adapterDetails);
    }

    void update_detail_view() {
        ((RecyclerView) findViewById(R.id.m_fileDetail)).setVisibility(-1 == this.m_adapter.get_cur_item() ? 8 : 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.m_btnBackup})
    public void onBackup() {
        Intent intent = new Intent(this, RenameActivity.class);
        Calendar calendar = Calendar.getInstance();
        intent.putExtra("name", String.format("%s-%d%02d%02d%02d%02d", Units.getSystemModel(), Integer.valueOf(calendar.get(1)), Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12))));
        startActivityForResult(intent, 1);
    }

    @OnClick({R.id.m_btnRestore})
    public void onRestore() {
        final int i = this.m_adapter.get_cur_item();
        if (-1 != i) {
            MsgBox.msg_box1_with_choice2(this, R.string.backup_restore_remind, R.string.confirm, R.string.cancel, new Handler() { // from class: com.baidu.kwgames.BackupActivity.3
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (message.arg2 == 0) {
                        BackupActivity backupActivity = BackupActivity.this;
                        if (backupActivity.restore_backup(backupActivity.m_arrBackupFiles.get(i))) {
                            MsgBox.msg_box1_with_choice1(this, (int) R.string.backup_restore_success, "OK", new Handler() { // from class: com.baidu.kwgames.BackupActivity.3.1
                                @Override // android.os.Handler
                                public void handleMessage(Message message2) {
                                    Intent launchIntentForPackage = BackupActivity.this.getPackageManager().getLaunchIntentForPackage(BackupActivity.this.getPackageName());
                                    if (launchIntentForPackage != null) {
                                        launchIntentForPackage.addFlags(67108864);
                                        BackupActivity.this.startActivity(launchIntentForPackage);
                                    }
                                    Process.killProcess(Process.myPid());
                                    System.exit(0);
                                }
                            });
                        }
                    }
                }
            });
        } else {
            NEAT.toast(this, R.string.pls_sel_file);
        }
    }

    @OnClick({R.id.m_btnDelete})
    public void onDelete() {
        final int i = this.m_adapter.get_cur_item();
        if (-1 != i) {
            MsgBox.msg_box1_with_choice2(this, R.string.del_backup_remind, R.string.confirm, R.string.cancel, new Handler() { // from class: com.baidu.kwgames.BackupActivity.4
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (message.arg2 == 0) {
                        File file = new File(BackupActivity.this.m_arrBackupFiles.get(i));
                        if (file.exists()) {
                            file.delete();
                            BackupActivity.this.init_backup_list();
                        }
                    }
                }
            });
        } else {
            NEAT.toast(this, R.string.pls_sel_file);
        }
    }

    @OnClick({R.id.m_btnShare})
    public void onShare() {
        int i = this.m_adapter.get_cur_item();
        if (-1 != i) {
            File file = new File(this.m_arrBackupFiles.get(i));
            if (file.exists()) {
                ShareWechatFriend.shareFile(this, file);
                return;
            }
            return;
        }
        NEAT.toast(this, R.string.pls_sel_file);
    }

    @OnClick({R.id.m_btnExit})
    public void onExit() {
        finish();
    }

    void write_xml(XmlSerializer xmlSerializer, String str, String str2, byte[] bArr) throws IOException {
        if (bArr == null || str2 == null || bArr.length == 0 || str2.isEmpty() || str.isEmpty()) {
            return;
        }
        xmlSerializer.startTag(null, str);
        xmlSerializer.attribute(null, "id", str2);
        xmlSerializer.text(ConvertUtils.bytes2HexString(bArr));
        xmlSerializer.endTag(null, str);
        xmlSerializer.text(System.getProperty("line.separator"));
    }

    void write_xml(XmlSerializer xmlSerializer, String str, String str2, String str3) throws IOException {
        if (str3 == null || str2 == null || str2.isEmpty() || str.isEmpty()) {
            return;
        }
        xmlSerializer.startTag(null, str);
        xmlSerializer.attribute(null, "id", str2);
        xmlSerializer.text(str3);
        xmlSerializer.endTag(null, str);
        xmlSerializer.text(System.getProperty("line.separator"));
    }

    void write_xml(XmlSerializer xmlSerializer, String str, String str2) throws IOException {
        if (str2 == null || str2.isEmpty() || str.isEmpty()) {
            return;
        }
        xmlSerializer.startTag(null, str);
        xmlSerializer.text(str2);
        xmlSerializer.endTag(null, str);
        xmlSerializer.text(System.getProperty("line.separator"));
    }

    void auto_generate_backup(String str) {
        String string;
        try {
            StringWriter stringWriter = new StringWriter();
            XmlSerializer newSerializer = Xml.newSerializer();
            newSerializer.setOutput(stringWriter);
            newSerializer.startDocument("utf-8", true);
            write_xml(newSerializer, TAG_PHONE_MODEL, Units.getSystemModel());
            write_xml(newSerializer, TAG_APP_VER, AppInstance.s_strAppVer);
            for (File file : FileUtils.listFilesInDir(getExternalFilesDir(null).getPath() + "/keyConfig/")) {
                write_xml(newSerializer, TAG_PRESET, file.getName(), FileIOUtils.readFile2BytesByStream(file.getPath()));
            }
            String path = AppInstance.get().getExternalFilesDir(null).getPath();
            for (int i = 0; i < Constants.s_arrAIKits.length; i++) {
                write_xml(newSerializer, TAG_AI_GUNDOWN, NEAT.int_to_string(i), FileIOUtils.readFile2BytesByChannel(path + Constants.s_arrAIKits[i].m_strAIGunDownFileName));
                write_xml(newSerializer, TAG_AI_POS, NEAT.int_to_string(i), Util.getItem(Constants.s_arrAIKits[i].m_strAIFloatViewPosSec));
                write_xml(newSerializer, TAG_AI_CROSSHAIR, NEAT.int_to_string(i), FileIOUtils.readFile2BytesByChannel(path + Constants.s_arrAIKits[i].m_strAICrosshairFileName));
                write_xml(newSerializer, TAG_AI_GUN_PARTS, NEAT.int_to_string(i), FileIOUtils.readFile2BytesByChannel(path + Constants.s_arrAIKits[i].m_strAIGunPartsReduceFileName));
                write_xml(newSerializer, TAG_AI_GUNN_DYNAMIC, NEAT.int_to_string(i), FileIOUtils.readFile2BytesByChannel(path + Constants.s_arrAIKits[i].m_strAIGunStageFileName));
                write_xml(newSerializer, TAG_AI_GUN_DYNAMIC_M4, NEAT.int_to_string(i), FileIOUtils.readFile2BytesByChannel(path + Constants.s_arrAIKits[i].m_strAIGunStageFileNameM4));
            }
            for (File file2 : FileUtils.listFilesInDir(getExternalFilesDir(null).getPath())) {
                if (-1 != file2.getPath().indexOf(".gundyn")) {
                    write_xml(newSerializer, TAG_DY_GUNDOWN, file2.getName(), FileIOUtils.readFile2BytesByStream(file2.getPath()));
                }
            }
            Map<String, ?> all = m_ini.getAll();
            for (Map.Entry<String, ?> entry : all.entrySet()) {
                if (-1 != entry.getKey().indexOf("dyOffCross") && (string = m_ini.getString(entry.getKey())) != null && !string.isEmpty()) {
                    write_xml(newSerializer, TAG_DY_CROSSHAIR, entry.getKey(), string);
                }
            }
            write_xml(newSerializer, TAG_OTHER_INI, new Gson().toJson(all));
            newSerializer.endDocument();
            File file3 = new File(Constants.BACKUP_DIR);
            if (!file3.exists()) {
                file3.mkdirs();
            }
            String stringWriter2 = stringWriter.toString();
            byte[] bArr = new byte[204800];
            Deflater deflater = new Deflater();
            deflater.setInput(stringWriter2.getBytes(StandardCharsets.UTF_8));
            deflater.finish();
            int deflate = deflater.deflate(bArr);
            deflater.end();
            byte[] bArr2 = new byte[deflate];
            System.arraycopy(bArr, 0, bArr2, 0, deflate);
            FileIOUtils.writeFileFromBytesByStream("/storage/emulated/0/MyBackup/" + str + STR_PREFIX, bArr2);
            init_backup_list();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void restore_preset(String str, String str2) {
        Log.d(TAG, "restore_preset");
        if (str == null || str2 == null) {
            return;
        }
        String str3 = getExternalFilesDir(null).getPath() + "/keyConfig";
        File file = new File(str3);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileIOUtils.writeFileFromBytesByStream(str3 + "/" + str, ConvertUtils.hexString2Bytes(str2));
    }

    void restore_AI_gundown(String str, String str2) {
        int string_to_int;
        Log.d(TAG, "restore_AI_gundown");
        if (str == null || str2 == null || (string_to_int = NEAT.string_to_int(str)) >= Constants.s_arrAIKits.length) {
            return;
        }
        String path = AppInstance.get().getExternalFilesDir(null).getPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        byte[] hexString2Bytes = ConvertUtils.hexString2Bytes(str2);
        FileIOUtils.writeFileFromBytesByStream(path + "/" + Constants.s_arrAIKits[string_to_int].m_strAIGunDownFileName, hexString2Bytes);
    }

    void restore_AI_dynamic(String str, String str2) {
        int string_to_int;
        Log.d(TAG, "restore_AI_dynamic");
        if (str == null || str2 == null || (string_to_int = NEAT.string_to_int(str)) >= Constants.s_arrAIKits.length) {
            return;
        }
        String path = AppInstance.get().getExternalFilesDir(null).getPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        byte[] hexString2Bytes = ConvertUtils.hexString2Bytes(str2);
        FileIOUtils.writeFileFromBytesByStream(path + "/" + Constants.s_arrAIKits[string_to_int].m_strAIGunStageFileName, hexString2Bytes);
    }

    void restore_AI_dynamic_M4(String str, String str2) {
        int string_to_int;
        Log.d(TAG, "restore_AI_dynamic_M4");
        if (str == null || str2 == null || (string_to_int = NEAT.string_to_int(str)) >= Constants.s_arrAIKits.length) {
            return;
        }
        String path = AppInstance.get().getExternalFilesDir(null).getPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        byte[] hexString2Bytes = ConvertUtils.hexString2Bytes(str2);
        FileIOUtils.writeFileFromBytesByStream(path + "/" + Constants.s_arrAIKits[string_to_int].m_strAIGunStageFileNameM4, hexString2Bytes);
    }

    void restore_AI_pos(String str, String str2) {
        int string_to_int;
        Log.d(TAG, "restore_AI_pos");
        if (str == null || str2 == null || (string_to_int = NEAT.string_to_int(str)) >= Constants.s_arrAIKits.length) {
            return;
        }
        Util.saveItem(Constants.s_arrAIKits[string_to_int].m_strAIFloatViewPosSec, str2);
    }

    void restore_AI_crosshair(String str, String str2) {
        int string_to_int;
        Log.d(TAG, "restore_AI_crosshair");
        if (str == null || str2 == null || (string_to_int = NEAT.string_to_int(str)) >= Constants.s_arrAIKits.length) {
            return;
        }
        String path = AppInstance.get().getExternalFilesDir(null).getPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        byte[] hexString2Bytes = ConvertUtils.hexString2Bytes(str2);
        FileIOUtils.writeFileFromBytesByStream(path + "/" + Constants.s_arrAIKits[string_to_int].m_strAICrosshairFileName, hexString2Bytes);
    }

    void restore_AI_gun_parts(String str, String str2) {
        int string_to_int;
        Log.d(TAG, "restore_AI_gun_parts");
        if (str == null || str2 == null || (string_to_int = NEAT.string_to_int(str)) >= Constants.s_arrAIKits.length) {
            return;
        }
        String path = AppInstance.get().getExternalFilesDir(null).getPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        byte[] hexString2Bytes = ConvertUtils.hexString2Bytes(str2);
        FileIOUtils.writeFileFromBytesByStream(path + "/" + Constants.s_arrAIKits[string_to_int].m_strAIGunPartsReduceFileName, hexString2Bytes);
    }

    void restore_dynamic_gundown(String str, String str2) {
        Log.d(TAG, "restore_dynamic_gundown");
        if (str == null || str2 == null) {
            return;
        }
        byte[] hexString2Bytes = ConvertUtils.hexString2Bytes(str2);
        FileIOUtils.writeFileFromBytesByStream(getExternalFilesDir(null).getPath() + "/" + str, hexString2Bytes);
    }

    void restore_dynamic_crosshair(String str, String str2) {
        Log.d(TAG, "restore_dynamic_crosshair");
        if (str == null || str2 == null) {
            return;
        }
        SPUtils.getInstance().put(str, str2);
    }

    void restore_ini(String str) {
        Log.d(TAG, "restore_ini");
        if (str == null) {
            return;
        }
        for (Map.Entry entry : ((Map) new Gson().fromJson(str, (Class<Object>) Map.class)).entrySet()) {
            String str2 = (String) entry.getKey();
            if (str2.contains(Constants.CFG_GUN_PARTS_AI) || str2.contains(Constants.CFG_TO_ALL_SCOPE) || str2.contains(Constants.CFG_AI_CROSSHAIR_ONOFF) || str2.contains(Constants.CFG_AI_DYNAMIC_ONOFF) || str2.contains(Constants.CFG_AI_XSCOPE_ONOFF) || str2.contains(Constants.CFG_AI_1ST_BULLET_OPTIMIZE) || str2.contains(Constants.CFG_AI_AUTO_ACTIVE_RUN)) {
                m_ini.put(str2, ((Boolean) entry.getValue()).booleanValue());
            } else if (str2.contains(Constants.CFG_AI_GUN_DOWN_PERCENT) || str2.contains(Constants.CFG_CURSOR_IMAGE)) {
                if (entry.getValue().getClass().toString().contains("Double")) {
                    m_ini.put(str2, (int) ((Double) entry.getValue()).doubleValue());
                }
            }
        }
    }

    boolean restore_backup(String str) {
        try {
            byte[] readFile2BytesByStream = FileIOUtils.readFile2BytesByStream(str);
            if (readFile2BytesByStream != null && readFile2BytesByStream.length != 0) {
                Inflater inflater = new Inflater();
                inflater.setInput(readFile2BytesByStream, 0, readFile2BytesByStream.length);
                byte[] bArr = new byte[readFile2BytesByStream.length * 50];
                int inflate = inflater.inflate(bArr);
                byte[] bArr2 = new byte[inflate];
                System.arraycopy(bArr, 0, bArr2, 0, inflate);
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setInput(new ByteArrayInputStream(bArr2), "utf-8");
                String str2 = null;
                for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                    if (eventType == 2) {
                        newPullParser.getName();
                        if (TAG_PHONE_MODEL.equals(newPullParser.getName())) {
                            str2 = newPullParser.nextText();
                        } else if (TAG_APP_VER.equals(newPullParser.getName())) {
                            newPullParser.nextText();
                        } else if (TAG_PRESET.equals(newPullParser.getName())) {
                            String attributeValue = newPullParser.getAttributeValue(null, "id");
                            String nextText = newPullParser.nextText();
                            if (this.m_arrBackupDetailSel.get(0).booleanValue()) {
                                restore_preset(attributeValue, nextText);
                            }
                        } else if (TAG_AI_GUNDOWN.equals(newPullParser.getName())) {
                            String attributeValue2 = newPullParser.getAttributeValue(null, "id");
                            String nextText2 = newPullParser.nextText();
                            if (this.m_arrBackupDetailSel.get(2).booleanValue()) {
                                restore_AI_gundown(attributeValue2, nextText2);
                            }
                        } else if (TAG_AI_GUNN_DYNAMIC.equals(newPullParser.getName())) {
                            String attributeValue3 = newPullParser.getAttributeValue(null, "id");
                            String nextText3 = newPullParser.nextText();
                            if (this.m_arrBackupDetailSel.get(3).booleanValue()) {
                                restore_AI_dynamic(attributeValue3, nextText3);
                            }
                        } else if (TAG_AI_GUN_DYNAMIC_M4.equals(newPullParser.getName())) {
                            String attributeValue4 = newPullParser.getAttributeValue(null, "id");
                            String nextText4 = newPullParser.nextText();
                            if (this.m_arrBackupDetailSel.get(3).booleanValue()) {
                                restore_AI_dynamic_M4(attributeValue4, nextText4);
                            }
                        } else if (TAG_DY_GUNDOWN.equals(newPullParser.getName())) {
                            String attributeValue5 = newPullParser.getAttributeValue(null, "id");
                            String nextText5 = newPullParser.nextText();
                            if (this.m_arrBackupDetailSel.get(6).booleanValue()) {
                                restore_dynamic_gundown(attributeValue5, nextText5);
                            }
                        } else if (TAG_AI_POS.equals(newPullParser.getName())) {
                            String attributeValue6 = newPullParser.getAttributeValue(null, "id");
                            String nextText6 = newPullParser.nextText();
                            if (this.m_arrBackupDetailSel.get(1).booleanValue() && str2 != null && str2.equals(Units.getSystemModel())) {
                                restore_AI_pos(attributeValue6, nextText6);
                            }
                        } else if (TAG_AI_CROSSHAIR.equals(newPullParser.getName())) {
                            String attributeValue7 = newPullParser.getAttributeValue(null, "id");
                            String nextText7 = newPullParser.nextText();
                            if (this.m_arrBackupDetailSel.get(5).booleanValue()) {
                                restore_AI_crosshair(attributeValue7, nextText7);
                            }
                        } else if (TAG_DY_CROSSHAIR.equals(newPullParser.getName())) {
                            String attributeValue8 = newPullParser.getAttributeValue(null, "id");
                            String nextText8 = newPullParser.nextText();
                            if (this.m_arrBackupDetailSel.get(7).booleanValue()) {
                                restore_dynamic_crosshair(attributeValue8, nextText8);
                            }
                        } else if (TAG_OTHER_INI.equals(newPullParser.getName())) {
                            String nextText9 = newPullParser.nextText();
                            if (this.m_arrBackupDetailSel.get(8).booleanValue()) {
                                restore_ini(nextText9);
                            }
                        } else if (TAG_AI_GUN_PARTS.equals(newPullParser.getName())) {
                            String attributeValue9 = newPullParser.getAttributeValue(null, "id");
                            String nextText10 = newPullParser.nextText();
                            if (this.m_arrBackupDetailSel.get(4).booleanValue()) {
                                restore_AI_gun_parts(attributeValue9, nextText10);
                            }
                        }
                    }
                }
                return true;
            }
            NEAT.toast(this, R.string.action_failed);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1 && i2 == -1) {
            auto_generate_backup(intent.getStringExtra("name"));
        }
        super.onActivityResult(i, i2, intent);
    }

    void update_bg_image() {
        this.m_layoutRoot.setBackgroundResource(UIConst.s_arrBGImages[m_ini.getInt(Constants.CFG_BG_IMAGE, 0)]);
    }
}
