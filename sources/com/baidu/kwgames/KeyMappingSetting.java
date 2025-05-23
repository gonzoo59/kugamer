package com.baidu.kwgames;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.baidu.kwgames.DragTouchListener;
import com.baidu.kwgames.KeyMappingSetting;
import com.baidu.kwgames.PreSetAdapter;
import com.baidu.kwgames.util.FloatMgr;
import com.baidu.kwgames.util.GunPartsMgr;
import com.baidu.kwgames.util.MsgBox;
import com.baidu.kwgames.util.NEAT;
import com.baidu.kwgames.util.Util;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPUtils;
import com.jieli.jl_bt_ota.constant.Command;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import okhttp3.internal.cache.DiskLruCache;
import org.opencv.core.Core;
import org.opencv.videoio.Videoio;
/* loaded from: classes.dex */
public class KeyMappingSetting extends AbsFloatBase {
    public static final int E_MACRO_DELAY_DEF = 4;
    public static final int E_NORMAL_KEY_ATTR_AVOID_TAKE_OFF_STUCK = 6;
    public static final int E_NORMAL_KEY_ATTR_AVOID_VIEW_STUCK = 5;
    public static final int E_NORMAL_KEY_ATTR_CLICK = 0;
    public static final int E_NORMAL_KEY_ATTR_CONTINUES_10_PER_SECOND = 2;
    public static final int E_NORMAL_KEY_ATTR_CONTINUES_13_PER_SECOND = 3;
    public static final int E_NORMAL_KEY_ATTR_CONTINUES_20_PER_SECOND = 4;
    public static final int E_NORMAL_KEY_ATTR_CONTINUES_8_PER_SECOND = 1;
    public static final int E_NORMAL_KEY_ATTR_DUNXIA = 16;
    public static final int E_NORMAL_KEY_ATTR_E = 25;
    public static final int E_NORMAL_KEY_ATTR_HIDE_CURSOR = 9;
    public static final int E_NORMAL_KEY_ATTR_PAXIA = 17;
    public static final int E_NORMAL_KEY_ATTR_Q = 24;
    public static final int E_NORMAL_KEY_ATTR_QIANLIYAN_OFF_NO_CLICK = 20;
    public static final int E_NORMAL_KEY_ATTR_QIANLIYAN_ON_NO_CLICK = 18;
    public static final int E_NORMAL_KEY_ATTR_QIANLIYAN_ON_REVERT_NO_CLICK = 19;
    public static final int E_NORMAL_KEY_ATTR_QIANLIYAN_TOGGLE_NO_CLICK = 21;
    public static final int E_NORMAL_KEY_ATTR_QIANLIYAN_TOGGLE_REVERT_NO_CLICK = 22;
    public static final int E_NORMAL_KEY_ATTR_RECALL_CURSOR = 10;
    public static final int E_NORMAL_KEY_ATTR_SHOW_CURSOR = 8;
    public static final int E_NORMAL_KEY_ATTR_SHUNFENGER_OFF = 13;
    public static final int E_NORMAL_KEY_ATTR_SHUNFENGER_OFF_NO_CLICK = 14;
    public static final int E_NORMAL_KEY_ATTR_SHUNFENGER_ON = 11;
    public static final int E_NORMAL_KEY_ATTR_SHUNFENGER_ONOFF_NO_CLICK = 15;
    public static final int E_NORMAL_KEY_ATTR_SHUNFENGER_ON_NO_CLICK = 12;
    public static final int E_NORMAL_KEY_ATTR_TOOGLE_CURSOR = 7;
    public static final int E_NORMAL_KEY_ATTR_TPP_FPP_SWITCH_WITH_MOUSE_L = 23;
    static final int E_VIEW_AUTO_UP_TIME_MIN = 200;
    private TextView errTip;
    private View exitTable;
    private PreSetAdapter mAdapter;
    private List<KeyInfo> mCurKeyInfoConfig;
    private ArrayList<DotData> mDotDatas;
    private int mKeyIndex;
    private HashMap<String, View> mKeyMapForUI;
    private LineRelativeLayout mKeyRoot;
    private TextView mKeyTip;
    private Switch mMacroClick;
    private int mMacroClickItem;
    private Spinner mMacroClickList;
    private Switch mNormalClick;
    private int mNormalClickItem;
    private Spinner mNormalClickList;
    private Switch mRelatedMouse;
    private String mSelTag;
    private Switch mSlideClick;
    private byte[] m_arrKeyCantDel;
    boolean m_boKeyPropInited;
    private boolean m_boModified;
    boolean m_boMouseViewPropInited;
    boolean m_boWASDPropInited;
    private Button m_btnAdd;
    private Button m_btnDel;
    private Button m_btnExit;
    Button m_btnMacroMoreAttr;
    Button m_btnMacroTriggerMode;
    private Button m_btnNextAttr;
    private Button m_btnNextMacroAttr;
    private Button m_btnNormalKeyTriggerMode;
    private Button m_btnPreset;
    private Button m_btnSave;
    private Button m_btnSaveAs;
    Button m_btnSlideKeyTriggerMode;
    private Button m_btnVideo;
    DotData m_dotCurMacroDot;
    private EditText m_editText;
    CompoundButton.OnCheckedChangeListener m_modeCheckedListenner;
    private int m_nCuKeyMap;
    private int m_nExitConfirmFlag;
    int m_nMacroEditDotIndex;
    int m_nMacroKeyTriggerMode;
    int m_nMacroMoreAttr;
    private int m_nMacroUUID;
    int m_nMouseDelayUp;
    int m_nMouseRelateRange;
    int m_nNormalKeyTriggerMode;
    int m_nSlideLoopWait;
    int m_nSlideSpeed;
    int m_nSlideTriggerMode;
    int m_nTakeOffViewFrozenTime;
    int m_nTakeOffWASDFrozen;
    int m_nViewAutoUpTime;
    int m_nWASDRelateRange;
    String m_strDotTag;
    Switch m_swWASDRelate;
    TextView m_textDelayUP;
    TextView m_textMacroPressTime;
    TextView m_textMacroWaitTime;
    TextView m_textSaveSuccessText;
    TextView m_textSlideLoopWait;
    TextView m_textSlideRange;
    TextView m_textSlideSpeed;
    TextView m_textTakeOffViewFrozen;
    TextView m_textTakeOffWASDFrozen;
    TextView m_textViewAutoUP;
    TextView m_textWASDRelateRange;
    TextView m_txtMacroTimeSettingTitle;
    View m_viewMacroTimeSetting;
    private View m_viewSaveSuccess;
    private Button testBtn;
    private View testKeyView;
    private LineRelativeLayout testLayer;
    private boolean touchFinish;
    public static final int[] s_arrMacroPressTime = {0, 15, 17, 20, 25, 30, 35, 40, 45, 50, 55, 60, 70, 80, 90, 100, 110, 120, 130, 140, Constants.E_BLE_BUF_TYPE_SET_M4_AI_STATE, 170, 200, Command.CMD_OTA_GET_DEVICE_REFRESH_FIRMWARE_STATUS, 260, 300, 350, 400, Videoio.CAP_PROP_XI_WB_KB, 500, 600, 800};
    public static final int[] s_arrMacroPressTimeYJWJ = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 140, 160, 180, 200, 220, Command.CMD_CUSTOM, 260, GunPartsMgr.GUN_HEAD_JUJI_MASK, 300, 350, 400, 500, 600, 700, Videoio.CAP_OPENNI, Videoio.CAP_XIAPI, Videoio.CAP_MSMF, Videoio.CAP_OPENNI2, 2000};
    public static final int[] s_arrMacroWaitTime = {0, 15, 20, 25, 30, 40, 50, 60, 80, 100, Constants.E_BLE_BUF_TYPE_SET_M4_AI_STATE, 200, 300, 500, 800, 1000};
    private static SPUtils m_ini = SPUtils.getInstance();

    @Override // com.baidu.kwgames.AbsFloatBase
    protected void onAddWindowFailed(Exception exc) {
    }

    static /* synthetic */ int access$4208(KeyMappingSetting keyMappingSetting) {
        int i = keyMappingSetting.m_nMacroUUID;
        keyMappingSetting.m_nMacroUUID = i + 1;
        return i;
    }

    public KeyMappingSetting(Context context) {
        super(context);
        this.mKeyIndex = 0;
        this.mSelTag = null;
        this.m_nNormalKeyTriggerMode = 0;
        this.m_nMacroKeyTriggerMode = 0;
        this.m_nMacroUUID = 1;
        this.m_boModified = false;
        this.touchFinish = false;
        this.m_nExitConfirmFlag = 0;
        this.m_arrKeyCantDel = new byte[]{Constants.KEY_MOUSE, -7};
        this.m_boKeyPropInited = false;
        this.m_nSlideSpeed = 0;
        this.m_nMouseRelateRange = 0;
        this.m_nWASDRelateRange = 0;
        this.m_modeCheckedListenner = null;
        this.m_boMouseViewPropInited = false;
        this.m_nMouseDelayUp = 0;
        this.m_nViewAutoUpTime = 0;
        this.m_nTakeOffViewFrozenTime = 0;
        this.m_boWASDPropInited = false;
        this.m_nTakeOffWASDFrozen = 0;
        this.m_dotCurMacroDot = null;
        this.m_nMacroEditDotIndex = 0;
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNextMId() {
        ArrayList<DotData> arrayList = this.mDotDatas;
        byte b = Constants.KEY_MOUSE_L;
        if (arrayList != null) {
            for (int i = 0; i < this.mDotDatas.size(); i++) {
                if (this.mDotDatas.get(i).mId > b) {
                    b = this.mDotDatas.get(i).mId;
                }
            }
        }
        return b + 1;
    }

    private void removeEmptyKey() {
        for (Map.Entry<String, View> entry : this.mKeyMapForUI.entrySet()) {
            KeyInfo keyInfo = (KeyInfo) entry.getValue().getTag(R.id.tag_key_info);
            if (keyInfo.title != null && keyInfo.title.contains("?") && keyInfo.title.length() == 1) {
                for (int size = this.mDotDatas.size() - 1; size >= 0; size--) {
                    if (this.mDotDatas.get(size).mId == keyInfo.id) {
                        this.mDotDatas.remove(size);
                    }
                }
            }
        }
    }

    @Override // com.baidu.kwgames.AbsFloatBase
    public synchronized void show() {
        super.show();
        this.mKeyTip.setText("");
    }

    void update_ctrl_visible() {
        if (this.mNormalClick.isChecked()) {
            String[] stringArray = this.mContext.getResources().getStringArray(R.array.normal_Key_trigger_modes);
            int i = this.m_nNormalKeyTriggerMode;
            if (i < stringArray.length) {
                this.m_btnNormalKeyTriggerMode.setText(stringArray[i]);
            }
        } else if (this.mRelatedMouse.isChecked()) {
            if (this.m_nMouseRelateRange == 0) {
                this.m_textSlideRange.setText(NEAT.s(R.string.screen_edge));
            } else {
                TextView textView = this.m_textSlideRange;
                textView.setText("" + (this.m_nMouseRelateRange * 20));
            }
        } else if (this.mMacroClick.isChecked()) {
            String[] stringArray2 = this.mContext.getResources().getStringArray(R.array.macro_Key_trigger_modes);
            int i2 = this.m_nMacroKeyTriggerMode;
            if (i2 < stringArray2.length) {
                this.m_btnMacroTriggerMode.setText(stringArray2[i2]);
            }
            String[] stringArray3 = this.mContext.getResources().getStringArray(R.array.macro_Key_more_attr);
            int i3 = this.m_nMacroMoreAttr;
            if (i3 < stringArray3.length) {
                this.m_btnMacroMoreAttr.setText(stringArray3[i3]);
            }
        } else if (this.mSlideClick.isChecked()) {
            TextView textView2 = this.m_textSlideSpeed;
            textView2.setText("" + (this.m_nSlideSpeed + 1));
            TextView textView3 = this.m_textSlideLoopWait;
            textView3.setText("" + (this.m_nSlideLoopWait * 10));
            String[] stringArray4 = this.mContext.getResources().getStringArray(R.array.slide_Key_trigger_modes);
            int i4 = this.m_nSlideTriggerMode;
            if (i4 < stringArray4.length) {
                this.m_btnSlideKeyTriggerMode.setText(stringArray4[i4]);
            }
        } else if (this.m_swWASDRelate.isChecked()) {
            if (this.m_nWASDRelateRange == 0) {
                this.m_textWASDRelateRange.setText(NEAT.s(R.string.str_default));
            } else {
                TextView textView4 = this.m_textWASDRelateRange;
                textView4.setText("" + (this.m_nWASDRelateRange * 20));
            }
        }
        int i5 = 0;
        this.mNormalClickList.setVisibility(this.mNormalClick.isChecked() ? 0 : 8);
        this.mMacroClickList.setVisibility(this.mMacroClick.isChecked() ? 0 : 8);
        findView(R.id.m_layoutSlideMore).setVisibility((!this.mSlideClick.isChecked() || AppInstance.g_sSysStatus.uSystemVer < 116) ? 8 : 0);
        findView(R.id.m_btnSlideKeyTriggerMode).setVisibility((this.mSlideClick.isChecked() && AppInstance.g_sSysStatus.has_yjwj_macro_capacity()) ? 0 : 8);
        findView(R.id.m_layoutSlideLoopWait).setVisibility((this.mSlideClick.isChecked() && 2 == this.m_nSlideTriggerMode && AppInstance.g_sSysStatus.has_yjwj_macro_capacity()) ? 0 : 8);
        findView(R.id.m_layoutRelateMouseMore).setVisibility((!this.mRelatedMouse.isChecked() || AppInstance.g_sSysStatus.uSystemVer < 118) ? 8 : 0);
        findView(R.id.m_layoutWASDRelateMore).setVisibility((!this.m_swWASDRelate.isChecked() || AppInstance.g_sSysStatus.uSystemVer < 118) ? 8 : 0);
        this.m_swWASDRelate.setVisibility(AppInstance.g_sSysStatus.uSystemVer >= 118 ? 0 : 8);
        this.m_btnNextAttr.setVisibility(this.mNormalClickList.getVisibility());
        this.m_btnNormalKeyTriggerMode.setVisibility((AppInstance.g_sSysStatus.has_key_config_ex_capacity() && this.mNormalClick.isChecked()) ? 8 : 8);
        this.m_btnNextMacroAttr.setVisibility(this.mMacroClickList.getVisibility());
        this.m_btnMacroTriggerMode.setVisibility(AppInstance.g_sSysStatus.uSystemVer >= 100 ? this.mMacroClickList.getVisibility() : 8);
        this.m_btnMacroMoreAttr.setVisibility(AppInstance.g_sSysStatus.uSystemVer >= 100 ? this.mMacroClickList.getVisibility() : 8);
    }

    @Override // com.baidu.kwgames.AbsFloatBase
    public void create() {
        super.create();
        this.mViewMode = 1;
        inflate(R.layout.key_mapping_setting);
        this.mKeyMapForUI = new HashMap<>();
        this.mCurKeyInfoConfig = new ArrayList();
        RelativeLayout relativeLayout = (RelativeLayout) findView(R.id.root);
        this.testBtn = (Button) findView(R.id.test_btn);
        this.m_btnAdd = (Button) findView(R.id.add);
        this.m_btnDel = (Button) findView(R.id.del);
        this.m_btnPreset = (Button) findView(R.id.pre_set);
        this.m_btnSave = (Button) findView(R.id.save);
        this.m_btnSaveAs = (Button) findView(R.id.save_as);
        this.m_btnVideo = (Button) findView(R.id.help);
        this.m_btnExit = (Button) findView(R.id.back);
        this.testLayer = (LineRelativeLayout) findView(R.id.test_layer);
        TextView textView = (TextView) findView(R.id.key_tip);
        this.mKeyTip = textView;
        textView.setVisibility(8);
        LineRelativeLayout lineRelativeLayout = (LineRelativeLayout) findView(R.id.key_root);
        this.mKeyRoot = lineRelativeLayout;
        lineRelativeLayout.setLineColor(-65536);
        requestFocus(true);
        Util.hideSystemUI(this.mKeyRoot);
        this.m_nCuKeyMap = AppInstance.s_nCurKeyMap;
        this.errTip = (TextView) findView(R.id.err_tip);
        this.testBtn.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                KeyMappingSetting.this.hide_macro_time_setting_float();
                KeyMappingSetting.this.mKeyTip.setVisibility(8);
                if (KeyMappingSetting.this.testLayer.getVisibility() == 0) {
                    KeyMappingSetting.this.setAllKeyEnable(true);
                    KeyMappingSetting.this.testLayer.setVisibility(8);
                    KeyMappingSetting.this.testBtn.setBackground(KeyMappingSetting.this.mContext.getResources().getDrawable(R.mipmap.edit_test1));
                    KeyMappingSetting.this.m_btnAdd.setVisibility(0);
                    KeyMappingSetting.this.m_btnDel.setVisibility(0);
                    KeyMappingSetting.this.m_btnPreset.setVisibility(0);
                    KeyMappingSetting.this.m_btnSave.setVisibility(0);
                    KeyMappingSetting.this.m_btnSaveAs.setVisibility(0);
                    KeyMappingSetting.this.m_btnVideo.setVisibility(0);
                    KeyMappingSetting.this.m_btnExit.setVisibility(0);
                    KeyMappingSetting.this.mListener.onEnableTouch(false);
                    return;
                }
                KeyMappingSetting.this.saveKey();
                KeyMappingSetting.this.setAllKeyEnable(false);
                KeyMappingSetting.this.testLayer.setVisibility(0);
                KeyMappingSetting.this.testBtn.setBackground(KeyMappingSetting.this.mContext.getResources().getDrawable(R.mipmap.edit_test2));
                KeyMappingSetting.this.m_btnAdd.setVisibility(8);
                KeyMappingSetting.this.m_btnDel.setVisibility(8);
                KeyMappingSetting.this.m_btnPreset.setVisibility(8);
                KeyMappingSetting.this.m_btnSave.setVisibility(8);
                KeyMappingSetting.this.m_btnSaveAs.setVisibility(8);
                KeyMappingSetting.this.m_btnVideo.setVisibility(8);
                KeyMappingSetting.this.m_btnExit.setVisibility(8);
                KeyMappingSetting.this.mListener.onEnableTouch(true);
            }
        });
        this.testLayer.setOnTouchListener(new View.OnTouchListener() { // from class: com.baidu.kwgames.KeyMappingSetting.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int dp2px = ConvertUtils.dp2px(30.0f);
                if (KeyMappingSetting.this.testKeyView != null) {
                    float f = dp2px / 2;
                    KeyMappingSetting.this.testKeyView.setX(motionEvent.getX() - f);
                    KeyMappingSetting.this.testKeyView.setY(motionEvent.getY() - f);
                    return true;
                }
                View inflate = View.inflate(KeyMappingSetting.this.mContext, R.layout.test_key, null);
                View findViewById = inflate.findViewById(R.id.image);
                findViewById.getLayoutParams().width = dp2px;
                findViewById.getLayoutParams().height = dp2px;
                float f2 = dp2px / 2;
                inflate.setX(motionEvent.getX() - f2);
                inflate.setY(motionEvent.getY() - f2);
                KeyMappingSetting.this.testKeyView = inflate;
                KeyMappingSetting.this.mKeyRoot.addView(inflate);
                return true;
            }
        });
        this.m_btnAdd.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                KeyMappingSetting.this.hide_macro_time_setting_float();
                KeyMappingSetting.this.mKeyTip.setVisibility(8);
                if (KeyMappingSetting.this.testLayer.getVisibility() == 0) {
                    return;
                }
                KeyInfo keyInfo = new KeyInfo();
                DotData dotData = new DotData();
                keyInfo.title = "?";
                keyInfo.imageId = 0;
                keyInfo.tag = keyInfo.title;
                keyInfo.set_normal_size_xy(AppInstance.s_nScreenW / 2, AppInstance.s_nScreenH / 2);
                keyInfo.type = 0;
                keyInfo.id = (byte) KeyMappingSetting.this.getNextMId();
                keyInfo.property = 0;
                dotData.mX[0] = keyInfo.x + ConvertUtils.dp2px(15.0f);
                dotData.mY[0] = keyInfo.y + ConvertUtils.dp2px(15.0f);
                dotData.mType = (byte) 0;
                dotData.mId = keyInfo.id;
                if (KeyMappingSetting.this.mDotDatas == null) {
                    KeyMappingSetting.this.mDotDatas = new ArrayList();
                }
                KeyMappingSetting.this.mDotDatas.add(dotData);
                KeyMappingSetting.this.mNormalClickItem = 0;
                KeyMappingSetting.this.m_nNormalKeyTriggerMode = 0;
                KeyMappingSetting.this.mMacroClickItem = 0;
                KeyMappingSetting.this.onViewClick(KeyMappingSetting.this.addKey(keyInfo, dotData, 0, false));
            }
        });
        findView(R.id.del).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                KeyMappingSetting.this.hide_macro_time_setting_float();
                KeyMappingSetting.this.mKeyTip.setVisibility(8);
                if (KeyMappingSetting.this.testLayer.getVisibility() == 0 || KeyMappingSetting.this.mSelTag == null) {
                    return;
                }
                KeyMappingSetting.this.m_boModified = true;
                View view2 = (View) KeyMappingSetting.this.mKeyMapForUI.get(KeyMappingSetting.this.mSelTag);
                if (view2 == null) {
                    return;
                }
                KeyInfo keyInfo = (KeyInfo) view2.getTag(R.id.tag_key_info);
                DotData dotData = (DotData) view2.getTag(R.id.tag_dot_data);
                for (byte b : KeyMappingSetting.this.m_arrKeyCantDel) {
                    if (b == dotData.mId) {
                        Toast.makeText(KeyMappingSetting.this.mContext, KeyMappingSetting.this.mContext.getString(R.string.delete_tip), 1).show();
                        return;
                    }
                }
                if (KeyMappingSetting.this.mCurKeyInfoConfig != null) {
                    KeyMappingSetting.this.mCurKeyInfoConfig.remove(view2.getTag(R.id.tag_key_info));
                }
                KeyMappingSetting.this.mKeyRoot.delLineByNode(view2);
                KeyMappingSetting.this.mKeyRoot.removeView(view2);
                if (dotData.mType == 1 || dotData.mType == 2) {
                    Iterator it = KeyMappingSetting.this.mKeyMapForUI.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        KeyInfo keyInfo2 = (KeyInfo) ((View) entry.getValue()).getTag(R.id.tag_key_info);
                        DotData dotData2 = (DotData) ((View) entry.getValue()).getTag(R.id.tag_dot_data);
                        if (keyInfo2.id == keyInfo.id && keyInfo2.type == dotData.mType) {
                            it.remove();
                            KeyMappingSetting.this.mKeyRoot.removeView((View) entry.getValue());
                            KeyMappingSetting.this.mDotDatas.remove(dotData2);
                        }
                    }
                    KeyMappingSetting.this.mKeyRoot.delLineByMacroNode(view2);
                } else if (dotData.mType == 4 || dotData.mType == 5) {
                    Iterator it2 = KeyMappingSetting.this.mKeyMapForUI.entrySet().iterator();
                    while (it2.hasNext()) {
                        Map.Entry entry2 = (Map.Entry) it2.next();
                        DotData dotData3 = (DotData) ((View) entry2.getValue()).getTag(R.id.tag_dot_data);
                        if (((KeyInfo) ((View) entry2.getValue()).getTag(R.id.tag_key_info)).same_macro(keyInfo)) {
                            it2.remove();
                            KeyMappingSetting.this.mKeyRoot.removeView((View) entry2.getValue());
                            KeyMappingSetting.this.mDotDatas.remove(dotData3);
                        }
                    }
                    KeyMappingSetting.this.mKeyRoot.delLineByMacroNode(view2);
                } else {
                    KeyMappingSetting.this.mKeyRoot.removeView(view2);
                    KeyMappingSetting.this.mDotDatas.remove(dotData);
                }
                KeyMappingSetting.this.mKeyMapForUI.remove(KeyMappingSetting.this.mSelTag);
                KeyMappingSetting.this.findView(R.id.property).setVisibility(4);
                KeyMappingSetting.this.mSelTag = null;
                KeyMappingSetting.this.m_boModified = true;
            }
        });
        init_prop_button();
        this.exitTable = findView(R.id.exit_table);
        findView(R.id.confirm).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (1 == KeyMappingSetting.this.m_nExitConfirmFlag) {
                    KeyMappingSetting.this.open_video_website();
                }
                KeyMappingSetting.this.remove();
                EasyFloat.showAppFloat(Constants.FLOAT_TAG_BALL);
            }
        });
        findView(R.id.cancel).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (KeyMappingSetting.this.errTip != null) {
                    KeyMappingSetting.this.errTip.setText("");
                }
                KeyMappingSetting.this.exitTable.setVisibility(8);
            }
        });
        this.m_btnExit.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (KeyMappingSetting.this.m_boModified) {
                    KeyMappingSetting.this.m_nExitConfirmFlag = 0;
                    KeyMappingSetting.this.exitTable.setVisibility(0);
                    return;
                }
                KeyMappingSetting.this.remove();
                EasyFloat.showAppFloat(Constants.FLOAT_TAG_BALL);
            }
        });
        this.m_btnVideo.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                KeyMappingSetting.this.hide_macro_time_setting_float();
                KeyMappingSetting.this.mKeyTip.setVisibility(8);
                if (KeyMappingSetting.this.m_boModified) {
                    KeyMappingSetting.this.m_nExitConfirmFlag = 1;
                    KeyMappingSetting.this.exitTable.setVisibility(0);
                    return;
                }
                KeyMappingSetting.this.open_video_website();
                KeyMappingSetting.this.remove();
                EasyFloat.showAppFloat(Constants.FLOAT_TAG_BALL);
            }
        });
        this.m_textSaveSuccessText = (TextView) findView(R.id.m_textSaveSuccessText);
        this.m_viewSaveSuccess = findView(R.id.save_success);
        findView(R.id.closesavesucc).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                KeyMappingSetting.this.m_viewSaveSuccess.setVisibility(8);
            }
        });
        this.m_btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (-1 != KeyMappingSetting.this.m_nCuKeyMap) {
                    KeyMappingSetting.this.saveKey();
                    KeyMappingSetting.this.m_textSaveSuccessText.setText(R.string.save_success);
                    KeyMappingSetting.this.m_viewSaveSuccess.setVisibility(0);
                    return;
                }
                KeyMappingSetting.this.findView(R.id.save_as_table).setVisibility(0);
            }
        });
        findView(R.id.save_as).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                KeyMappingSetting.this.hide_macro_time_setting_float();
                KeyMappingSetting.this.mKeyTip.setVisibility(8);
                KeyMappingSetting.this.findView(R.id.save_as_table).setVisibility(0);
            }
        });
        this.m_editText = (EditText) findView(R.id.edit_text);
        if (AppInstance.s_strKeyMapSaveAs != null) {
            this.m_editText.setText(AppInstance.s_strKeyMapSaveAs);
        }
        findView(R.id.save_as_ok).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!TextUtils.isEmpty(KeyMappingSetting.this.m_editText.getText())) {
                    AppInstance.s_strKeyMapSaveAs = KeyMappingSetting.this.m_editText.getText().toString();
                    KeyMappingSetting.this.saveAsKey(AppInstance.s_strKeyMapSaveAs);
                    KeyMappingSetting.this.m_textSaveSuccessText.setText(R.string.save_as_sucess);
                    KeyMappingSetting.this.m_viewSaveSuccess.setVisibility(0);
                    KeyMappingSetting.this.findView(R.id.save_as_table).setVisibility(8);
                    return;
                }
                Toast.makeText(KeyMappingSetting.this.mContext, KeyMappingSetting.this.mContext.getString(R.string.input_name_tip), 1).show();
            }
        });
        findView(R.id.save_as_table).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                KeyMappingSetting.this.findView(R.id.save_as_table).setVisibility(8);
            }
        });
        findView(R.id.pre_set).setOnClickListener(new AnonymousClass14());
        findView(R.id.pre_set_table).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                KeyMappingSetting.this.findView(R.id.pre_set_table).setVisibility(8);
            }
        });
        this.m_boModified = false;
    }

    /* renamed from: com.baidu.kwgames.KeyMappingSetting$14  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass14 implements View.OnClickListener {
        AnonymousClass14() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (KeyMappingSetting.this.testLayer.getVisibility() == 0) {
                return;
            }
            KeyMappingSetting.this.hide_macro_time_setting_float();
            KeyMappingSetting.this.mKeyTip.setVisibility(8);
            View findView = KeyMappingSetting.this.findView(R.id.pre_set_table);
            if (findView.getVisibility() == 0) {
                findView.setVisibility(8);
                return;
            }
            List<File> listFilesInDir = FileUtils.listFilesInDir(KeyMappingSetting.this.mContext.getExternalFilesDir(null).getPath() + "/keyConfig/");
            ArrayList arrayList = new ArrayList();
            if (listFilesInDir != null && listFilesInDir.size() > 0) {
                for (File file : listFilesInDir) {
                    arrayList.add(file.getName());
                }
                Collections.sort(arrayList, new Comparator() { // from class: com.baidu.kwgames.KeyMappingSetting$14$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return KeyMappingSetting.AnonymousClass14.lambda$onClick$0((String) obj, (String) obj2);
                    }
                });
                final RecyclerView recyclerView = (RecyclerView) KeyMappingSetting.this.findView(R.id.pre_set_list);
                recyclerView.setLayoutManager(new LinearLayoutManager(KeyMappingSetting.this.mContext));
                KeyMappingSetting keyMappingSetting = KeyMappingSetting.this;
                Context context = KeyMappingSetting.this.mContext;
                keyMappingSetting.mAdapter = new PreSetAdapter(context, arrayList, KeyMappingSetting.this.mContext.getExternalFilesDir(null).getPath() + "/keyConfig/");
                KeyMappingSetting.this.mAdapter.setClickListener(new PreSetAdapter.ItemClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.14.1
                    @Override // com.baidu.kwgames.PreSetAdapter.ItemClickListener
                    public void onItemClick(View view2, int i) {
                        KeyMappingSetting.this.findView(R.id.pre_set_table).setVisibility(8);
                        KeyMappingSetting.this.loadKey(((PreSetAdapter) recyclerView.getAdapter()).getItem(i));
                    }
                });
                final Button button = (Button) KeyMappingSetting.this.findView(R.id.edit_preset);
                button.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.14.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        KeyMappingSetting.this.mAdapter.toggle_enable_edit();
                        if (KeyMappingSetting.this.mAdapter.get_enable_edit()) {
                            button.setBackground(KeyMappingSetting.this.mContext.getResources().getDrawable(R.drawable.check_box_on));
                        } else {
                            button.setBackground(KeyMappingSetting.this.mContext.getResources().getDrawable(R.drawable.check_box_off));
                        }
                    }
                });
                recyclerView.setAdapter(KeyMappingSetting.this.mAdapter);
                findView.setVisibility(0);
                return;
            }
            Toast.makeText(KeyMappingSetting.this.mContext, KeyMappingSetting.this.mContext.getString(R.string.no_pre_key), 1).show();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ int lambda$onClick$0(String str, String str2) {
            boolean contains = str.contains(NEAT.s(R.string.system_preset));
            boolean contains2 = str2.contains(NEAT.s(R.string.system_preset));
            if (contains) {
                if (!contains2) {
                    return 1;
                }
            } else if (contains2) {
                return -1;
            }
            return str.compareTo(str2);
        }
    }

    void init_prop_button() {
        findView(R.id.property).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                View view2;
                KeyMappingSetting.this.hide_macro_time_setting_float();
                KeyMappingSetting.this.mKeyTip.setVisibility(8);
                KeyMappingSetting.this.findView(R.id.control).setVisibility(8);
                KeyMappingSetting.this.findView(R.id.key_root).setVisibility(8);
                if (KeyMappingSetting.this.mSelTag == null || (view2 = (View) KeyMappingSetting.this.mKeyMapForUI.get(KeyMappingSetting.this.mSelTag)) == null) {
                    return;
                }
                KeyInfo keyInfo = (KeyInfo) view2.getTag(R.id.tag_key_info);
                DotData dotData = (DotData) view2.getTag(R.id.tag_dot_data);
                if (-13 == dotData.mId) {
                    KeyMappingSetting.this.init_mouse_view_prop();
                    KeyMappingSetting.this.findView(R.id.m_layoutMouseProp).setVisibility(0);
                } else if (-12 == dotData.mId || -9 == dotData.mId) {
                    KeyMappingSetting.this.init_takeoff_WASD_frozen_prop();
                    KeyMappingSetting.this.findView(R.id.m_layoutWASDProp).setVisibility(0);
                } else {
                    KeyMappingSetting.this.init_key_prop_page();
                    KeyMappingSetting.this.findView(R.id.property_table).setVisibility(0);
                    KeyMappingSetting.this.m_nSlideSpeed = 0;
                    KeyMappingSetting.this.m_nSlideTriggerMode = 0;
                    KeyMappingSetting.this.m_nSlideLoopWait = 0;
                    KeyMappingSetting.this.m_nMouseRelateRange = 0;
                    KeyMappingSetting.this.mNormalClick.setChecked(keyInfo.type == 0);
                    KeyMappingSetting.this.mRelatedMouse.setChecked(keyInfo.type == 3);
                    KeyMappingSetting.this.mSlideClick.setChecked(keyInfo.type == 5);
                    KeyMappingSetting.this.mMacroClick.setChecked(keyInfo.type == 4);
                    KeyMappingSetting.this.m_swWASDRelate.setChecked(keyInfo.type == 6);
                    if (keyInfo.type == 0) {
                        KeyMappingSetting.this.m_nNormalKeyTriggerMode = dotData.get_normal_key_trigger_mode();
                        KeyMappingSetting.this.mNormalClickItem = keyInfo.property;
                        if (KeyMappingSetting.this.mNormalClickItem >= KeyMappingSetting.this.mNormalClickList.getAdapter().getCount()) {
                            KeyMappingSetting.this.mNormalClickItem = 0;
                        }
                        KeyMappingSetting.this.mNormalClickList.setSelection(KeyMappingSetting.this.mNormalClickItem);
                        KeyMappingSetting.this.mMacroClickList.setSelection(0);
                    } else if (keyInfo.type == 3) {
                        KeyMappingSetting.this.mNormalClickList.setSelection(0);
                        KeyMappingSetting.this.mMacroClickList.setSelection(0);
                        KeyMappingSetting.this.m_nMouseRelateRange = dotData.get_mouse_relate_key_slide_range();
                    } else if (keyInfo.type == 4) {
                        KeyMappingSetting.this.mNormalClickList.setSelection(0);
                        KeyMappingSetting.this.mMacroClickList.setSelection(keyInfo.property - 2);
                        KeyMappingSetting.this.m_nMacroKeyTriggerMode = dotData.get_macro_key_trigger_mode();
                        KeyMappingSetting.this.m_nMacroMoreAttr = dotData.get_macro_key_more_attr();
                    } else if (keyInfo.type == 5) {
                        KeyMappingSetting.this.mNormalClickList.setSelection(0);
                        KeyMappingSetting.this.mMacroClickList.setSelection(0);
                        KeyMappingSetting.this.m_nSlideSpeed = dotData.get_slide_key_slide_speed();
                        KeyMappingSetting.this.m_nSlideTriggerMode = dotData.get_slide_key_trigger_mode();
                        KeyMappingSetting.this.m_nSlideLoopWait = dotData.get_slide_key_loop_wait();
                    } else if (keyInfo.type == 6) {
                        KeyMappingSetting.this.mNormalClickList.setSelection(0);
                        KeyMappingSetting.this.mMacroClickList.setSelection(0);
                        KeyMappingSetting.this.m_nWASDRelateRange = dotData.get_mouse_relate_key_slide_range();
                    }
                    KeyMappingSetting.this.update_ctrl_visible();
                }
            }
        });
    }

    void init_key_prop_page() {
        if (this.m_boKeyPropInited) {
            return;
        }
        this.m_boKeyPropInited = true;
        if (this.m_modeCheckedListenner == null) {
            this.m_modeCheckedListenner = new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.KeyMappingSetting.17
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    KeyMappingSetting.this.errTip.setText("");
                    if (KeyMappingSetting.this.mSelTag == null || !z) {
                        return;
                    }
                    if (KeyMappingSetting.this.mNormalClick != compoundButton) {
                        KeyMappingSetting.this.mNormalClick.setChecked(false);
                    }
                    if (KeyMappingSetting.this.mRelatedMouse != compoundButton) {
                        KeyMappingSetting.this.mRelatedMouse.setChecked(false);
                    }
                    if (KeyMappingSetting.this.mSlideClick != compoundButton) {
                        KeyMappingSetting.this.mSlideClick.setChecked(false);
                    }
                    if (KeyMappingSetting.this.mMacroClick != compoundButton) {
                        KeyMappingSetting.this.mMacroClick.setChecked(false);
                    }
                    if (KeyMappingSetting.this.m_swWASDRelate != compoundButton) {
                        KeyMappingSetting.this.m_swWASDRelate.setChecked(false);
                    }
                    KeyMappingSetting.this.update_ctrl_visible();
                }
            };
        }
        Switch r0 = (Switch) findView(R.id.normal_click);
        this.mNormalClick = r0;
        r0.setOnCheckedChangeListener(this.m_modeCheckedListenner);
        Button button = (Button) findView(R.id.m_btnNormalKeyTriggerMode);
        this.m_btnNormalKeyTriggerMode = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m19lambda$init_key_prop_page$0$combaidukwgamesKeyMappingSetting(view);
            }
        });
        Button button2 = (Button) findView(R.id.btn_next_attribute);
        this.m_btnNextAttr = button2;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int selectedItemPosition = KeyMappingSetting.this.mNormalClickList.getSelectedItemPosition() + 1;
                if (selectedItemPosition >= KeyMappingSetting.this.mNormalClickList.getCount()) {
                    selectedItemPosition = 0;
                }
                KeyMappingSetting.this.mNormalClickList.setSelection(selectedItemPosition);
            }
        });
        Button button3 = (Button) findView(R.id.btn_next_macro_attribute);
        this.m_btnNextMacroAttr = button3;
        button3.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.19
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int selectedItemPosition = KeyMappingSetting.this.mMacroClickList.getSelectedItemPosition() + 1;
                if (selectedItemPosition >= KeyMappingSetting.this.mMacroClickList.getCount()) {
                    selectedItemPosition = 0;
                }
                KeyMappingSetting.this.mMacroClickList.setSelection(selectedItemPosition);
            }
        });
        this.m_textSlideRange = (TextView) findView(R.id.m_textSlideRange);
        Switch r02 = (Switch) findView(R.id.related_mouse);
        this.mRelatedMouse = r02;
        r02.setOnCheckedChangeListener(this.m_modeCheckedListenner);
        findView(R.id.m_btnSlideRangeMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m20lambda$init_key_prop_page$1$combaidukwgamesKeyMappingSetting(view);
            }
        });
        findView(R.id.m_btnSlideRangePlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda18
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m23lambda$init_key_prop_page$2$combaidukwgamesKeyMappingSetting(view);
            }
        });
        this.m_textWASDRelateRange = (TextView) findView(R.id.m_textWASDRelateRange);
        Switch r03 = (Switch) findView(R.id.m_swWASDRelate);
        this.m_swWASDRelate = r03;
        r03.setOnCheckedChangeListener(this.m_modeCheckedListenner);
        findView(R.id.m_btnWASDRelateRangeMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda19
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m24lambda$init_key_prop_page$3$combaidukwgamesKeyMappingSetting(view);
            }
        });
        findView(R.id.m_btnWASDRelateRangePlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda20
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m25lambda$init_key_prop_page$4$combaidukwgamesKeyMappingSetting(view);
            }
        });
        this.mSlideClick = (Switch) findView(R.id.slide_click);
        this.m_textSlideSpeed = (TextView) findView(R.id.m_textSlideSpeed);
        this.m_btnSlideKeyTriggerMode = (Button) findView(R.id.m_btnSlideKeyTriggerMode);
        this.m_textSlideLoopWait = (TextView) findView(R.id.m_textSlideLoopWait);
        this.mSlideClick.setOnTouchListener(new View.OnTouchListener() { // from class: com.baidu.kwgames.KeyMappingSetting.20
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                View view2 = (View) KeyMappingSetting.this.mKeyMapForUI.get(KeyMappingSetting.this.mSelTag);
                KeyInfo keyInfo = (KeyInfo) view2.getTag(R.id.tag_key_info);
                for (Map.Entry entry : KeyMappingSetting.this.mKeyMapForUI.entrySet()) {
                    KeyInfo keyInfo2 = (KeyInfo) ((View) entry.getValue()).getTag(R.id.tag_key_info);
                    if (entry.getValue() != view2 && keyInfo2.id == keyInfo.id && (keyInfo2.type == 5 || (keyInfo2.type == 4 && !keyInfo2.same_macro(keyInfo)))) {
                        KeyMappingSetting.this.errTip.setText(R.string.sile_err_tip);
                        return true;
                    }
                }
                return false;
            }
        });
        findView(R.id.m_btnSlideKeyTriggerMode).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda21
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m26lambda$init_key_prop_page$5$combaidukwgamesKeyMappingSetting(view);
            }
        });
        findView(R.id.m_btnSlideLoopWaitMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda22
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m27lambda$init_key_prop_page$6$combaidukwgamesKeyMappingSetting(view);
            }
        });
        findView(R.id.m_btnSlideLoopWaitPlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda23
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m28lambda$init_key_prop_page$7$combaidukwgamesKeyMappingSetting(view);
            }
        });
        this.mSlideClick.setOnCheckedChangeListener(this.m_modeCheckedListenner);
        findView(R.id.m_btnSlideSpeedMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m29lambda$init_key_prop_page$8$combaidukwgamesKeyMappingSetting(view);
            }
        });
        findView(R.id.m_btnSlideSpeedPlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m30lambda$init_key_prop_page$9$combaidukwgamesKeyMappingSetting(view);
            }
        });
        Switch r04 = (Switch) findView(R.id.macro_click);
        this.mMacroClick = r04;
        r04.setOnTouchListener(new View.OnTouchListener() { // from class: com.baidu.kwgames.KeyMappingSetting.21
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        Button button4 = (Button) findView(R.id.m_btnMacroTriggerMode);
        this.m_btnMacroTriggerMode = button4;
        button4.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m21lambda$init_key_prop_page$10$combaidukwgamesKeyMappingSetting(view);
            }
        });
        Button button5 = (Button) findView(R.id.m_btnMacroMoreAttr);
        this.m_btnMacroMoreAttr = button5;
        button5.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m22lambda$init_key_prop_page$11$combaidukwgamesKeyMappingSetting(view);
            }
        });
        this.mMacroClick.setOnCheckedChangeListener(this.m_modeCheckedListenner);
        this.mNormalClickList = (Spinner) findView(R.id.normal_click_list);
        int i = R.array.normal_click_list;
        if (AppInstance.g_sSysStatus.uSystemVer >= 119) {
            i = R.array.normal_click_list_v119;
        } else if (AppInstance.g_sSysStatus.uSystemVer >= 111) {
            i = R.array.normal_click_list_v111;
        } else if (AppInstance.g_sSysStatus.uSystemVer >= 100) {
            i = R.array.normal_click_list_v100;
        } else if (AppInstance.g_sSysStatus.uSystemVer >= 75) {
            i = R.array.normal_click_list_v75;
        }
        this.mNormalClickList.setAdapter((SpinnerAdapter) new ArrayAdapter(AppInstance.get(), (int) R.layout.simple_spinner_item, (int) R.id.text, AppInstance.get().getResources().getStringArray(i)));
        this.mMacroClickList = (Spinner) findView(R.id.macro_click_list);
        this.mMacroClickList.setAdapter((SpinnerAdapter) new ArrayAdapter(AppInstance.get(), (int) R.layout.simple_spinner_item, (int) R.id.text, AppInstance.get().getResources().getStringArray(R.array.macro_click_list)));
        this.mNormalClickList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.baidu.kwgames.KeyMappingSetting.22
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) {
                KeyMappingSetting.this.mNormalClickItem = i2;
            }
        });
        this.mMacroClickList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.baidu.kwgames.KeyMappingSetting.23
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) {
                KeyMappingSetting.this.mMacroClickItem = i2;
            }
        });
        findView(R.id.property_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.24
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (KeyMappingSetting.this.errTip != null) {
                    KeyMappingSetting.this.errTip.setText("");
                }
                KeyMappingSetting.this.findView(R.id.control).setVisibility(0);
                KeyMappingSetting.this.findView(R.id.key_root).setVisibility(0);
                KeyMappingSetting.this.findView(R.id.property_table).setVisibility(8);
            }
        });
        findView(R.id.property_ok).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.25
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (KeyMappingSetting.this.mNormalClick.isChecked() || KeyMappingSetting.this.mRelatedMouse.isChecked() || KeyMappingSetting.this.mMacroClick.isChecked() || KeyMappingSetting.this.mSlideClick.isChecked() || KeyMappingSetting.this.m_swWASDRelate.isChecked()) {
                    if (KeyMappingSetting.this.errTip != null) {
                        KeyMappingSetting.this.errTip.setText("");
                    }
                    View view2 = (View) KeyMappingSetting.this.mKeyMapForUI.get(KeyMappingSetting.this.mSelTag);
                    float f = 15.0f;
                    int i2 = 2;
                    if (!KeyMappingSetting.this.mSlideClick.isChecked() || view2 == null) {
                        if (!KeyMappingSetting.this.mNormalClick.isChecked() || view2 == null) {
                            if (KeyMappingSetting.this.mRelatedMouse.isChecked() && view2 != null) {
                                KeyInfo keyInfo = (KeyInfo) view2.getTag(R.id.tag_key_info);
                                if (keyInfo.type != 3) {
                                    KeyMappingSetting.this.remove_macro_other_dots(view2);
                                    KeyMappingSetting.this.remove_slide_other_dots(view2);
                                    keyInfo.type = 3;
                                    KeyMappingSetting.this.set_key_view_text_white(view2);
                                    DotData dotData = (DotData) view2.getTag(R.id.tag_dot_data);
                                    dotData.mX[2] = 0;
                                    dotData.mX[1] = 0;
                                    dotData.mY[1] = 0;
                                    dotData.mY[2] = 0;
                                    dotData.mType = (byte) 3;
                                    dotData.set_mouse_relate_key_slide_range(KeyMappingSetting.this.m_nMouseRelateRange);
                                } else {
                                    ((DotData) view2.getTag(R.id.tag_dot_data)).set_mouse_relate_key_slide_range(KeyMappingSetting.this.m_nMouseRelateRange);
                                }
                            } else if (!KeyMappingSetting.this.m_swWASDRelate.isChecked() || view2 == null) {
                                if (KeyMappingSetting.this.mMacroClick.isChecked() && view2 != null) {
                                    KeyMappingSetting keyMappingSetting = KeyMappingSetting.this;
                                    if (!keyMappingSetting.check_macro_error(keyMappingSetting.m_nMacroKeyTriggerMode)) {
                                        return;
                                    }
                                    KeyInfo keyInfo2 = (KeyInfo) view2.getTag(R.id.tag_key_info);
                                    DotData dotData2 = (DotData) view2.getTag(R.id.tag_dot_data);
                                    boolean z = (KeyMappingSetting.this.m_nMacroMoreAttr == dotData2.get_macro_key_more_attr() && KeyMappingSetting.this.m_nMacroKeyTriggerMode == dotData2.get_macro_key_trigger_mode()) ? false : true;
                                    dotData2.set_macro_key_trigger_mode(KeyMappingSetting.this.m_nMacroKeyTriggerMode);
                                    dotData2.set_macro_key_more_attr(KeyMappingSetting.this.m_nMacroMoreAttr);
                                    if (keyInfo2.type != 4 || keyInfo2.property != KeyMappingSetting.this.mMacroClickItem + 2) {
                                        KeyMappingSetting.this.remove_macro_other_dots(view2);
                                        KeyMappingSetting.this.remove_slide_other_dots(view2);
                                        int access$4208 = KeyMappingSetting.access$4208(KeyMappingSetting.this);
                                        dotData2.set_macro_UUID(access$4208);
                                        keyInfo2.set_data(dotData2.mId, 4, dotData2.m_nMacroTriggerMode, access$4208);
                                        keyInfo2.property = KeyMappingSetting.this.mMacroClickItem + 2;
                                        String str = KeyMappingSetting.this.get_macro_tag(dotData2, 1);
                                        if (!KeyMappingSetting.this.mSelTag.equals(str)) {
                                            KeyMappingSetting.this.mKeyMapForUI.remove(KeyMappingSetting.this.mSelTag);
                                            KeyMappingSetting.this.mKeyMapForUI.put(str, view2);
                                            KeyMappingSetting.this.mSelTag = str;
                                            keyInfo2.tag = str;
                                        }
                                        KeyMappingSetting.this.set_key_view_text_red(view2);
                                        KeyInfo keyInfo3 = keyInfo2;
                                        DotData dotData3 = dotData2;
                                        int i3 = 1;
                                        while (i3 < keyInfo2.property) {
                                            KeyInfo keyInfo4 = new KeyInfo();
                                            int i4 = i3 % 3;
                                            if (i4 == 0) {
                                                DotData dotData4 = new DotData();
                                                KeyMappingSetting.this.mDotDatas.add(KeyMappingSetting.this.mDotDatas.indexOf(dotData3) + 1, dotData4);
                                                dotData4.set_macro_data(access$4208, KeyMappingSetting.this.m_nMacroKeyTriggerMode, KeyMappingSetting.this.m_nMacroMoreAttr);
                                                dotData3 = dotData4;
                                            }
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("");
                                            i3++;
                                            sb.append(i3);
                                            keyInfo4.title = sb.toString();
                                            keyInfo4.imageId = 0;
                                            keyInfo4.tag = KeyMappingSetting.this.get_macro_tag(dotData2, i3);
                                            if (keyInfo2.x > AppInstance.s_nScreenW / i2) {
                                                keyInfo4.x = keyInfo3.x - ConvertUtils.dp2px(30.0f);
                                            } else {
                                                keyInfo4.x = keyInfo3.x + ConvertUtils.dp2px(30.0f);
                                            }
                                            if (keyInfo4.x < 0) {
                                                keyInfo4.x = 10;
                                            }
                                            if (keyInfo4.x + keyInfo4.w > AppInstance.s_nScreenW) {
                                                keyInfo4.x = (AppInstance.s_nScreenW - keyInfo4.w) - 10;
                                            }
                                            keyInfo4.y = keyInfo3.y - ConvertUtils.dp2px(f);
                                            keyInfo4.ensure_xy_in_screen();
                                            keyInfo4.w = ConvertUtils.dp2px(30.0f);
                                            keyInfo4.h = ConvertUtils.dp2px(30.0f);
                                            keyInfo4.set_data(keyInfo2.id, 4, KeyMappingSetting.this.m_nMacroKeyTriggerMode, access$4208);
                                            keyInfo4.property = 0;
                                            dotData3.mX[i4] = keyInfo4.x + ConvertUtils.dp2px(f);
                                            dotData3.mY[i4] = keyInfo3.y;
                                            dotData3.mType = (byte) 4;
                                            dotData3.mId = keyInfo4.id;
                                            dotData3.set_macro_data(access$4208, KeyMappingSetting.this.m_nMacroKeyTriggerMode, KeyMappingSetting.this.m_nMacroMoreAttr);
                                            KeyMappingSetting.this.addKey(keyInfo4, dotData3, i4, false);
                                            KeyMappingSetting.this.mKeyRoot.addLine((View) KeyMappingSetting.this.mKeyMapForUI.get(keyInfo3.tag), (View) KeyMappingSetting.this.mKeyMapForUI.get(keyInfo4.tag), -65536);
                                            ((View) KeyMappingSetting.this.mKeyMapForUI.get(keyInfo4.tag)).findViewById(R.id.image).setBackground(KeyMappingSetting.this.mContext.getResources().getDrawable(R.drawable.shape_circle_green));
                                            keyInfo4.y = keyInfo3.y;
                                            keyInfo3 = keyInfo4;
                                            f = 15.0f;
                                            i2 = 2;
                                        }
                                        if (keyInfo2.property % 3 == 1) {
                                            dotData3.mX[1] = -1;
                                            dotData3.mX[2] = -1;
                                            dotData3.mY[1] = -1;
                                            dotData3.mY[2] = -1;
                                        } else if (keyInfo2.property % 3 == 2) {
                                            dotData3.mX[2] = -1;
                                            dotData3.mY[2] = -1;
                                        }
                                    } else if (z) {
                                        KeyMappingSetting.this.update_macro_other_dots_attr(view2);
                                    }
                                }
                            } else {
                                KeyInfo keyInfo5 = (KeyInfo) view2.getTag(R.id.tag_key_info);
                                if (keyInfo5.type != 6) {
                                    KeyMappingSetting.this.remove_macro_other_dots(view2);
                                    KeyMappingSetting.this.remove_slide_other_dots(view2);
                                    keyInfo5.type = 6;
                                    KeyMappingSetting.this.set_key_view_text_white(view2);
                                    DotData dotData5 = (DotData) view2.getTag(R.id.tag_dot_data);
                                    dotData5.mX[2] = 0;
                                    dotData5.mX[1] = 0;
                                    dotData5.mY[1] = 0;
                                    dotData5.mY[2] = 0;
                                    dotData5.mType = (byte) 6;
                                    dotData5.set_mouse_relate_key_slide_range(KeyMappingSetting.this.m_nWASDRelateRange);
                                } else {
                                    ((DotData) view2.getTag(R.id.tag_dot_data)).set_mouse_relate_key_slide_range(KeyMappingSetting.this.m_nWASDRelateRange);
                                }
                            }
                        } else {
                            KeyInfo keyInfo6 = (KeyInfo) view2.getTag(R.id.tag_key_info);
                            DotData dotData6 = (DotData) view2.getTag(R.id.tag_dot_data);
                            if (keyInfo6.type != 0) {
                                KeyMappingSetting.this.remove_macro_other_dots(view2);
                                KeyMappingSetting.this.remove_slide_other_dots(view2);
                                keyInfo6.type = 0;
                                KeyMappingSetting.this.set_key_view_text_white(view2);
                                dotData6.mType = (byte) 0;
                            }
                            if ((dotData6.mId == 20 && KeyMappingSetting.this.mNormalClickItem == 24) || (dotData6.mId == 8 && KeyMappingSetting.this.mNormalClickItem == 25)) {
                                KeyMappingSetting.this.mNormalClickItem = 0;
                            }
                            dotData6.mX[2] = 0;
                            dotData6.mX[1] = 0;
                            dotData6.mY[1] = 0;
                            dotData6.mY[2] = KeyMappingSetting.this.mNormalClickItem;
                            dotData6.set_normal_key_trigger_mode(KeyMappingSetting.this.m_nNormalKeyTriggerMode);
                            keyInfo6.property = KeyMappingSetting.this.mNormalClickItem;
                        }
                    } else {
                        KeyInfo keyInfo7 = (KeyInfo) view2.getTag(R.id.tag_key_info);
                        DotData dotData7 = (DotData) view2.getTag(R.id.tag_dot_data);
                        if (keyInfo7.type != 5) {
                            KeyMappingSetting.this.remove_macro_other_dots(view2);
                            KeyMappingSetting.this.remove_slide_other_dots(view2);
                            keyInfo7.type = 5;
                            dotData7.mType = (byte) 5;
                            keyInfo7.property = 2;
                            KeyMappingSetting.this.set_key_view_text_white(view2);
                            KeyInfo keyInfo8 = new KeyInfo();
                            keyInfo8.title = KeyMappingSetting.this.mContext.getString(R.string.end);
                            keyInfo8.imageId = 0;
                            keyInfo8.tag = "";
                            if (keyInfo7.x > AppInstance.s_nScreenW / 2) {
                                keyInfo8.set_normal_size_xy(keyInfo7.x - ConvertUtils.dp2px(54.0f), keyInfo7.y);
                            } else {
                                keyInfo8.set_normal_size_xy(keyInfo7.x + ConvertUtils.dp2px(54.0f), keyInfo7.y);
                            }
                            keyInfo8.type = 5;
                            keyInfo8.id = keyInfo7.id;
                            keyInfo8.property = 0;
                            dotData7.mX[1] = keyInfo8.x;
                            dotData7.mY[1] = keyInfo8.y;
                            keyInfo8.x = dotData7.mX[1] - ConvertUtils.dp2px(15.0f);
                            keyInfo8.y = dotData7.mY[1] - ConvertUtils.dp2px(15.0f);
                            dotData7.mType = (byte) 5;
                            dotData7.mId = keyInfo8.id;
                            KeyMappingSetting.this.addKey(keyInfo8, dotData7, 1, false);
                            KeyMappingSetting.this.mKeyRoot.addLine((View) KeyMappingSetting.this.mKeyMapForUI.get(keyInfo7.tag), (View) KeyMappingSetting.this.mKeyMapForUI.get(keyInfo8.tag), -1);
                            ((View) KeyMappingSetting.this.mKeyMapForUI.get(keyInfo8.tag)).findViewById(R.id.image).setBackground(KeyMappingSetting.this.mContext.getResources().getDrawable(R.drawable.shape_circle_green));
                            dotData7.mY[2] = 0;
                        }
                        dotData7.set_slide_key_slide_speed(KeyMappingSetting.this.m_nSlideSpeed);
                        dotData7.set_slide_key_trigger_mode(KeyMappingSetting.this.m_nSlideTriggerMode);
                        dotData7.set_slide_key_loop_wait(KeyMappingSetting.this.m_nSlideLoopWait);
                    }
                    KeyMappingSetting.this.findView(R.id.control).setVisibility(0);
                    KeyMappingSetting.this.findView(R.id.key_root).setVisibility(0);
                    KeyMappingSetting.this.findView(R.id.property_table).setVisibility(8);
                    KeyMappingSetting.this.m_boModified = true;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_key_prop_page$0$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m19lambda$init_key_prop_page$0$combaidukwgamesKeyMappingSetting(View view) {
        int i = this.m_nNormalKeyTriggerMode + 1;
        this.m_nNormalKeyTriggerMode = i;
        if (i >= 3) {
            this.m_nNormalKeyTriggerMode = 0;
        }
        update_ctrl_visible();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_key_prop_page$1$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m20lambda$init_key_prop_page$1$combaidukwgamesKeyMappingSetting(View view) {
        int i = this.m_nMouseRelateRange - 1;
        this.m_nMouseRelateRange = i;
        if (i < 0) {
            this.m_nMouseRelateRange = 0;
        }
        update_ctrl_visible();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_key_prop_page$2$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m23lambda$init_key_prop_page$2$combaidukwgamesKeyMappingSetting(View view) {
        int i = this.m_nMouseRelateRange + 1;
        this.m_nMouseRelateRange = i;
        if (i > 100) {
            this.m_nMouseRelateRange = 100;
        }
        update_ctrl_visible();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_key_prop_page$3$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m24lambda$init_key_prop_page$3$combaidukwgamesKeyMappingSetting(View view) {
        int i = this.m_nWASDRelateRange - 1;
        this.m_nWASDRelateRange = i;
        if (i < 0) {
            this.m_nWASDRelateRange = 0;
        }
        update_ctrl_visible();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_key_prop_page$4$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m25lambda$init_key_prop_page$4$combaidukwgamesKeyMappingSetting(View view) {
        int i = this.m_nWASDRelateRange + 1;
        this.m_nWASDRelateRange = i;
        if (i > 100) {
            this.m_nWASDRelateRange = 100;
        }
        update_ctrl_visible();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_key_prop_page$5$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m26lambda$init_key_prop_page$5$combaidukwgamesKeyMappingSetting(View view) {
        int i = this.m_nSlideTriggerMode + 1;
        this.m_nSlideTriggerMode = i;
        if (i >= 3) {
            this.m_nSlideTriggerMode = 0;
        }
        update_ctrl_visible();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_key_prop_page$6$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m27lambda$init_key_prop_page$6$combaidukwgamesKeyMappingSetting(View view) {
        int i = this.m_nSlideLoopWait - 1;
        this.m_nSlideLoopWait = i;
        if (i < 0) {
            this.m_nSlideLoopWait = 0;
        }
        update_ctrl_visible();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_key_prop_page$7$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m28lambda$init_key_prop_page$7$combaidukwgamesKeyMappingSetting(View view) {
        int i = this.m_nSlideLoopWait + 1;
        this.m_nSlideLoopWait = i;
        if (i > 200) {
            this.m_nSlideLoopWait = 200;
        }
        update_ctrl_visible();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_key_prop_page$8$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m29lambda$init_key_prop_page$8$combaidukwgamesKeyMappingSetting(View view) {
        int i = this.m_nSlideSpeed - 1;
        this.m_nSlideSpeed = i;
        if (i < 0) {
            this.m_nSlideSpeed = 0;
        }
        update_ctrl_visible();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_key_prop_page$9$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m30lambda$init_key_prop_page$9$combaidukwgamesKeyMappingSetting(View view) {
        int i = this.m_nSlideSpeed + 1;
        this.m_nSlideSpeed = i;
        if (i > 10) {
            this.m_nSlideSpeed = 10;
        }
        update_ctrl_visible();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_key_prop_page$10$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m21lambda$init_key_prop_page$10$combaidukwgamesKeyMappingSetting(View view) {
        int i = this.m_nMacroKeyTriggerMode + 1;
        this.m_nMacroKeyTriggerMode = i;
        if (i >= 3) {
            this.m_nMacroKeyTriggerMode = 0;
        }
        update_ctrl_visible();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_key_prop_page$11$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m22lambda$init_key_prop_page$11$combaidukwgamesKeyMappingSetting(View view) {
        int i = this.m_nMacroMoreAttr + 1;
        this.m_nMacroMoreAttr = i;
        if (i >= 3) {
            this.m_nMacroMoreAttr = 0;
        }
        update_ctrl_visible();
    }

    void init_mouse_view_prop() {
        if (this.m_boMouseViewPropInited) {
            return;
        }
        findView(R.id.m_btnDelayUPMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m31x2abf4f1b(view);
            }
        });
        findView(R.id.m_btnDelayUPPlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m32x3e67229c(view);
            }
        });
        findView(R.id.m_btnViewAutoUPMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m33x520ef61d(view);
            }
        });
        findView(R.id.m_btnViewAutoUPPlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m34x65b6c99e(view);
            }
        });
        findView(R.id.m_btnTakeOffViewFrozenMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m35x795e9d1f(view);
            }
        });
        findView(R.id.m_btnTakeOffViewFrozenPlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m36x8d0670a0(view);
            }
        });
        findView(R.id.m_btnMousePropOK).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.26
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (KeyMappingSetting.this.errTip != null) {
                    KeyMappingSetting.this.errTip.setText("");
                }
                View view2 = (View) KeyMappingSetting.this.mKeyMapForUI.get(KeyMappingSetting.this.mSelTag);
                if (view2 != null) {
                    DotData dotData = (DotData) view2.getTag(R.id.tag_dot_data);
                    dotData.mX[1] = 0;
                    dotData.mY[1] = KeyMappingSetting.this.m_nTakeOffViewFrozenTime;
                    dotData.mX[2] = KeyMappingSetting.this.m_nViewAutoUpTime;
                    dotData.mY[2] = KeyMappingSetting.this.m_nMouseDelayUp;
                    KeyInfo keyInfo = (KeyInfo) view2.getTag(R.id.tag_key_info);
                    keyInfo.property = 0;
                    if (KeyMappingSetting.this.m_nTakeOffViewFrozenTime != 0) {
                        keyInfo.property = KeyMappingSetting.this.m_nTakeOffViewFrozenTime;
                    }
                    if (KeyMappingSetting.this.m_nMouseDelayUp != 0) {
                        keyInfo.property = KeyMappingSetting.this.m_nMouseDelayUp;
                    }
                    if (KeyMappingSetting.this.m_nViewAutoUpTime != 0) {
                        keyInfo.property = KeyMappingSetting.this.m_nViewAutoUpTime;
                    }
                }
                KeyMappingSetting.this.findView(R.id.control).setVisibility(0);
                KeyMappingSetting.this.findView(R.id.key_root).setVisibility(0);
                KeyMappingSetting.this.findView(R.id.m_layoutMouseProp).setVisibility(8);
                KeyMappingSetting.this.m_boModified = true;
            }
        });
        findView(R.id.m_btnMousePropCancel).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m37xa0ae4421(view);
            }
        });
        this.m_textDelayUP = (TextView) findView(R.id.m_textDelayUP);
        this.m_textViewAutoUP = (TextView) findView(R.id.m_textViewAutoUP);
        this.m_textTakeOffViewFrozen = (TextView) findView(R.id.m_textTakeOffViewFrozen);
        View view = this.mKeyMapForUI.get(this.mSelTag);
        if (view != null) {
            DotData dotData = (DotData) view.getTag(R.id.tag_dot_data);
            this.m_nMouseDelayUp = dotData.mY[2];
            this.m_nTakeOffViewFrozenTime = dotData.mY[1] & 255;
            this.m_nViewAutoUpTime = dotData.mX[2];
            update_mouse_view_prop_text();
        }
        this.m_boMouseViewPropInited = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_mouse_view_prop$12$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m31x2abf4f1b(View view) {
        int i = this.m_nMouseDelayUp - 1;
        this.m_nMouseDelayUp = i;
        if (i < 0) {
            this.m_nMouseDelayUp = 0;
        }
        update_mouse_view_prop_text();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_mouse_view_prop$13$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m32x3e67229c(View view) {
        this.m_nMouseDelayUp++;
        update_mouse_view_prop_text();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_mouse_view_prop$14$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m33x520ef61d(View view) {
        int i = this.m_nViewAutoUpTime - 20;
        this.m_nViewAutoUpTime = i;
        if (i < 200) {
            this.m_nViewAutoUpTime = 200;
        }
        update_mouse_view_prop_text();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_mouse_view_prop$15$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m34x65b6c99e(View view) {
        if (this.m_nViewAutoUpTime < 200) {
            this.m_nViewAutoUpTime = 200;
        }
        this.m_nViewAutoUpTime += 20;
        update_mouse_view_prop_text();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_mouse_view_prop$16$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m35x795e9d1f(View view) {
        int i = this.m_nTakeOffViewFrozenTime - 1;
        this.m_nTakeOffViewFrozenTime = i;
        if (i < 0) {
            this.m_nTakeOffViewFrozenTime = 0;
        }
        update_mouse_view_prop_text();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_mouse_view_prop$17$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m36x8d0670a0(View view) {
        this.m_nTakeOffViewFrozenTime++;
        update_mouse_view_prop_text();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_mouse_view_prop$18$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m37xa0ae4421(View view) {
        findView(R.id.control).setVisibility(0);
        findView(R.id.key_root).setVisibility(0);
        findView(R.id.m_layoutMouseProp).setVisibility(8);
    }

    void init_takeoff_WASD_frozen_prop() {
        if (this.m_boWASDPropInited) {
            return;
        }
        findView(R.id.m_btnTakeOffWASDFrozenMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m38xa4b46e0c(view);
            }
        });
        findView(R.id.m_btnTakeOffWASDFrozenPlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m39x55209b22(view);
            }
        });
        findView(R.id.m_btnWASDPropOK).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.27
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (KeyMappingSetting.this.errTip != null) {
                    KeyMappingSetting.this.errTip.setText("");
                }
                View view2 = (View) KeyMappingSetting.this.mKeyMapForUI.get(KeyMappingSetting.this.mSelTag);
                if (view2 != null) {
                    DotData dotData = (DotData) view2.getTag(R.id.tag_dot_data);
                    dotData.mX[1] = 0;
                    dotData.mY[1] = 0;
                    dotData.mX[2] = 0;
                    dotData.mY[2] = KeyMappingSetting.this.m_nTakeOffWASDFrozen;
                    KeyInfo keyInfo = (KeyInfo) view2.getTag(R.id.tag_key_info);
                    keyInfo.property = 0;
                    if (KeyMappingSetting.this.m_nTakeOffWASDFrozen != 0) {
                        keyInfo.property = KeyMappingSetting.this.m_nTakeOffWASDFrozen;
                    }
                }
                KeyMappingSetting.this.findView(R.id.control).setVisibility(0);
                KeyMappingSetting.this.findView(R.id.key_root).setVisibility(0);
                KeyMappingSetting.this.findView(R.id.m_layoutWASDProp).setVisibility(8);
                KeyMappingSetting.this.m_boModified = true;
            }
        });
        findView(R.id.m_btnWASDPropCancel).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyMappingSetting.this.m40x68c86ea3(view);
            }
        });
        this.m_textTakeOffWASDFrozen = (TextView) findView(R.id.m_textTakeOffWASDFrozen);
        View view = this.mKeyMapForUI.get(this.mSelTag);
        if (view != null) {
            this.m_nTakeOffWASDFrozen = ((DotData) view.getTag(R.id.tag_dot_data)).mY[2];
            update_WASD_prop_text();
        }
        this.m_boWASDPropInited = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_takeoff_WASD_frozen_prop$19$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m38xa4b46e0c(View view) {
        int i = this.m_nTakeOffWASDFrozen - 1;
        this.m_nTakeOffWASDFrozen = i;
        if (i < 0) {
            this.m_nTakeOffWASDFrozen = 0;
        }
        update_WASD_prop_text();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_takeoff_WASD_frozen_prop$20$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m39x55209b22(View view) {
        this.m_nTakeOffWASDFrozen += 2;
        update_WASD_prop_text();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$init_takeoff_WASD_frozen_prop$21$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m40x68c86ea3(View view) {
        findView(R.id.control).setVisibility(0);
        findView(R.id.key_root).setVisibility(0);
        findView(R.id.m_layoutWASDProp).setVisibility(8);
    }

    void update_mouse_view_prop_text() {
        TextView textView = this.m_textDelayUP;
        textView.setText("" + (this.m_nMouseDelayUp * 20));
        if (this.m_nViewAutoUpTime <= 200) {
            this.m_textViewAutoUP.setText(NEAT.s(R.string.str_default));
        } else {
            TextView textView2 = this.m_textViewAutoUP;
            textView2.setText("" + this.m_nViewAutoUpTime);
        }
        if (this.m_nTakeOffViewFrozenTime == 0) {
            this.m_textTakeOffViewFrozen.setText(NEAT.s(R.string.str_default));
            return;
        }
        TextView textView3 = this.m_textTakeOffViewFrozen;
        textView3.setText("" + (this.m_nTakeOffViewFrozenTime * 20));
    }

    void update_WASD_prop_text() {
        if (this.m_nTakeOffWASDFrozen == 0) {
            this.m_textTakeOffWASDFrozen.setText(NEAT.s(R.string.str_default));
            return;
        }
        TextView textView = this.m_textTakeOffWASDFrozen;
        textView.setText("" + (this.m_nTakeOffWASDFrozen * 20));
    }

    boolean check_macro_error(int i) {
        KeyInfo keyInfo = (KeyInfo) this.mKeyMapForUI.get(this.mSelTag).getTag(R.id.tag_key_info);
        this.errTip.setText("");
        for (Map.Entry<String, View> entry : this.mKeyMapForUI.entrySet()) {
            KeyInfo keyInfo2 = (KeyInfo) entry.getValue().getTag(R.id.tag_key_info);
            if (keyInfo2.type == 4 && keyInfo2.same_key(keyInfo) && !keyInfo2.same_macro(keyInfo) && keyInfo2.m_nMacroTriggerMode == i) {
                this.errTip.setText(this.mContext.getString(R.string.same_macro_tip));
                return false;
            }
        }
        return true;
    }

    void remove_macro_other_dots(View view) {
        KeyInfo keyInfo = (KeyInfo) view.getTag(R.id.tag_key_info);
        DotData dotData = (DotData) view.getTag(R.id.tag_dot_data);
        if (dotData.mType == 4) {
            Iterator<Map.Entry<String, View>> it = this.mKeyMapForUI.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, View> next = it.next();
                KeyInfo keyInfo2 = (KeyInfo) next.getValue().getTag(R.id.tag_key_info);
                if (keyInfo2.same_macro(keyInfo) && view != next.getValue() && keyInfo2.type == 4) {
                    it.remove();
                    this.mKeyRoot.removeView(next.getValue());
                    this.mCurKeyInfoConfig.remove(keyInfo2);
                    this.mKeyRoot.delLineByNode(next.getValue());
                }
            }
            Iterator<DotData> it2 = this.mDotDatas.iterator();
            while (it2.hasNext()) {
                DotData next2 = it2.next();
                if (next2.same_macro(dotData) && dotData != next2 && next2.mType == 4) {
                    it2.remove();
                }
            }
            this.mKeyRoot.delLineByMacroNode(view);
            this.mKeyRoot.invalidate();
        }
    }

    void update_macro_other_dots_attr(View view) {
        KeyInfo keyInfo = (KeyInfo) view.getTag(R.id.tag_key_info);
        DotData dotData = (DotData) view.getTag(R.id.tag_dot_data);
        if (dotData.mType != 4) {
            return;
        }
        for (Map.Entry<String, View> entry : this.mKeyMapForUI.entrySet()) {
            KeyInfo keyInfo2 = (KeyInfo) entry.getValue().getTag(R.id.tag_key_info);
            if (keyInfo2.same_macro(keyInfo) && view != entry.getValue() && keyInfo2.type == 4) {
                keyInfo2.copy_macro(keyInfo);
            }
        }
        Iterator<DotData> it = this.mDotDatas.iterator();
        while (it.hasNext()) {
            DotData next = it.next();
            if (next.same_macro_UUID(dotData) && dotData != next && next.mType == 4) {
                next.copy_macro(dotData);
            }
        }
    }

    void remove_slide_other_dots(View view) {
        KeyInfo keyInfo = (KeyInfo) view.getTag(R.id.tag_key_info);
        DotData dotData = (DotData) view.getTag(R.id.tag_dot_data);
        if (dotData.mType == 5) {
            Iterator<Map.Entry<String, View>> it = this.mKeyMapForUI.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, View> next = it.next();
                KeyInfo keyInfo2 = (KeyInfo) next.getValue().getTag(R.id.tag_key_info);
                if (keyInfo2.id == keyInfo.id && view != next.getValue() && keyInfo2.type == 5) {
                    it.remove();
                    this.mKeyRoot.removeView(next.getValue());
                    this.mCurKeyInfoConfig.remove(keyInfo2);
                    this.mKeyRoot.delLineByNode(next.getValue());
                }
            }
            Iterator<DotData> it2 = this.mDotDatas.iterator();
            while (it2.hasNext()) {
                DotData next2 = it2.next();
                if (next2.mId == dotData.mId && dotData != next2 && next2.mType == 5) {
                    it2.remove();
                }
            }
            this.mKeyRoot.delLineByMacroNode(view);
            this.mKeyRoot.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void open_video_website() {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            intent.setData(Uri.parse(Constants.URL_VIDEO_APP));
            this.mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideAllChildMacroKey() {
        for (View view : this.mKeyMapForUI.values()) {
            KeyInfo keyInfo = (KeyInfo) view.getTag(R.id.tag_key_info);
            if (keyInfo.type == 4) {
                if (keyInfo.property == 0) {
                    view.setVisibility(8);
                } else {
                    this.mKeyRoot.changeLineColorByMacroNode(view, 16777215, null);
                    this.mKeyRoot.invalidate();
                }
            }
        }
    }

    KeyInfo get_key_info(View view) {
        return (KeyInfo) (view == null ? null : view.getTag(R.id.tag_key_info));
    }

    DotData get_dot_data(View view) {
        return (DotData) (view == null ? null : view.getTag(R.id.tag_dot_data));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onViewClick(View view) {
        this.mKeyRoot.changeAllLines(-1);
        this.mKeyRoot.invalidate();
        hideAllChildMacroKey();
        int i = R.id.tag_key_info;
        KeyInfo keyInfo = (KeyInfo) view.getTag(R.id.tag_key_info);
        if (NEAT.is_chinese() && NEAT.is_pubg_ch(AppInstance.s_strGamePackageName)) {
            if (Constants.keyDesc.containsKey(Byte.valueOf(keyInfo.id))) {
                String str = Constants.keyDesc.get(Byte.valueOf(keyInfo.id));
                if (str != null && !str.isEmpty()) {
                    this.mKeyTip.setText(str);
                    this.mKeyTip.setVisibility(0);
                } else {
                    this.mKeyTip.setVisibility(8);
                }
            } else {
                this.mKeyTip.setVisibility(8);
            }
        }
        if (keyInfo.type == 4) {
            show_macro_time_setting_float(((Integer) view.getTag(R.id.tag_dot_index)).intValue(), keyInfo.tag, (DotData) view.getTag(R.id.tag_dot_data));
            for (View view2 : this.mKeyMapForUI.values()) {
                if (((KeyInfo) view2.getTag(R.id.tag_key_info)).same_macro(keyInfo)) {
                    view2.setVisibility(0);
                }
            }
        } else {
            hide_macro_time_setting_float();
        }
        this.mSelTag = keyInfo.tag;
        if (keyInfo.type == 0) {
            findView(R.id.property).setVisibility(0);
        } else if (keyInfo.type == 4) {
            if (keyInfo.property == 0) {
                findView(R.id.property).setVisibility(4);
                this.mKeyRoot.changeLineColorByMacroNode(view, -65536, null);
                this.mKeyRoot.invalidate();
                return;
            }
            findView(R.id.property).setVisibility(0);
        } else if (keyInfo.type == 5) {
            if (keyInfo.property == 0) {
                findView(R.id.property).setVisibility(4);
                this.mKeyRoot.changeLineColorByMacroNode(view, -65536, null);
                this.mKeyRoot.invalidate();
                return;
            }
            findView(R.id.property).setVisibility(0);
        } else if (keyInfo.type == 3 || keyInfo.type == 6) {
            findView(R.id.property).setVisibility(0);
        } else {
            findView(R.id.property).setVisibility(4);
        }
        KeyInfo keyInfo2 = (KeyInfo) view.getTag(R.id.tag_key_info);
        for (View view3 : this.mKeyMapForUI.values()) {
            KeyInfo keyInfo3 = (KeyInfo) view3.getTag(i);
            view3.findViewById(R.id.image).setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_circle_white));
            setKeyColor(keyInfo3, get_dot_data(view3), (ImageView) view3.findViewById(R.id.image));
            if (keyInfo2.id == keyInfo3.id) {
                if (keyInfo2.type != 0) {
                    if (keyInfo3.is_macro()) {
                        if (keyInfo3.is_macro_first_key()) {
                            set_key_view_text_red(view3);
                        } else {
                            set_key_view_text_white(view3);
                        }
                    }
                    view3.findViewById(R.id.image).setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_circle_green));
                    if (keyInfo2.type != keyInfo3.type) {
                        view3.findViewById(R.id.image).setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_circle_white));
                        setKeyColor(keyInfo3, get_dot_data(view3), (ImageView) view3.findViewById(R.id.image));
                    }
                } else {
                    view3.findViewById(R.id.image).setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_circle_white));
                }
            } else if (keyInfo3.is_macro()) {
                ((TextView) view3.findViewById(R.id.text)).setTextColor(NEAT.get_color(R.color.colorWhite));
            }
            i = R.id.tag_key_info;
        }
        view.findViewById(R.id.image).setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_circle_green));
        if (keyInfo.type == 5 || keyInfo.type == 4 || keyInfo.type == 1 || keyInfo.type == 2) {
            this.mKeyRoot.changeLineColorByMacroNode(view, -65536, null);
            this.mKeyRoot.invalidate();
        }
        if ((keyInfo.imageId != R.mipmap.direction || AppInstance.g_sSysStatus.uSystemVer >= 116) && keyInfo.imageId != R.mipmap.mouse_m && keyInfo.imageId != R.mipmap.beijing_scale2 && (keyInfo.imageId != R.mipmap.mouse || AppInstance.g_sSysStatus.uSystemVer >= 114)) {
            return;
        }
        findView(R.id.property).setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void set_key_view_text_red(View view) {
        ((TextView) view.findViewById(R.id.text)).setTextColor(NEAT.get_color(R.color.colorRed));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void set_key_view_text_white(View view) {
        ((TextView) view.findViewById(R.id.text)).setTextColor(NEAT.get_color(R.color.colorWhite));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View addKey(KeyInfo keyInfo, DotData dotData, int i, boolean z) {
        this.m_boModified = true;
        RelativeLayout relativeLayout = (RelativeLayout) View.inflate(this.mContext, R.layout.normal_key, null);
        relativeLayout.setTag(R.id.tag_key_info, keyInfo);
        relativeLayout.setTag(R.id.tag_dot_data, dotData);
        relativeLayout.setTag(R.id.tag_dot_index, Integer.valueOf(i));
        DragTouchListener dragTouchListener = new DragTouchListener(relativeLayout);
        dragTouchListener.setOnDragListener(new DragTouchListener.OnDragListener() { // from class: com.baidu.kwgames.KeyMappingSetting.28
            @Override // com.baidu.kwgames.DragTouchListener.OnDragListener
            public void onClick(View view) {
                KeyMappingSetting.this.onViewClick(view);
            }

            @Override // com.baidu.kwgames.DragTouchListener.OnDragListener
            public void onDragging(View view) {
                if (!KeyMappingSetting.this.touchFinish) {
                    KeyMappingSetting.this.touchFinish = true;
                    KeyMappingSetting.this.onViewClick(view);
                }
                KeyInfo keyInfo2 = (KeyInfo) view.getTag(R.id.tag_key_info);
                if (keyInfo2.imageId == R.mipmap.direction || keyInfo2.imageId == R.mipmap.udlr) {
                    keyInfo2.x = Math.round(view.getX()) + ConvertUtils.dp2px(70.0f);
                    keyInfo2.y = Math.round(view.getY()) + ConvertUtils.dp2px(70.0f);
                } else {
                    keyInfo2.x = Math.round(view.getX()) + ConvertUtils.dp2px(15.0f);
                    keyInfo2.y = Math.round(view.getY()) + ConvertUtils.dp2px(15.0f);
                }
                DotData dotData2 = (DotData) view.getTag(R.id.tag_dot_data);
                int intValue = ((Integer) view.getTag(R.id.tag_dot_index)).intValue();
                dotData2.mX[intValue] = keyInfo2.x;
                dotData2.mY[intValue] = keyInfo2.y;
                KeyMappingSetting.this.m_boModified = true;
                KeyMappingSetting.this.mKeyRoot.invalidate();
                if (keyInfo2.type == 5) {
                    KeyMappingSetting.this.updateSlideKeyArrow(view, keyInfo2);
                }
            }

            @Override // com.baidu.kwgames.DragTouchListener.OnDragListener
            public void onDragFinish(View view) {
                KeyMappingSetting.this.touchFinish = false;
            }
        });
        relativeLayout.setOnTouchListener(dragTouchListener);
        relativeLayout.setGravity(17);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.image);
        if (keyInfo.imageId == R.mipmap.mouse || keyInfo.imageId == R.mipmap.mouse_l || keyInfo.imageId == R.mipmap.mouse_m || keyInfo.imageId == R.mipmap.mouse_r || keyInfo.imageId == R.mipmap.mouse_s1 || keyInfo.imageId == R.mipmap.mouse_s2 || keyInfo.imageId == R.mipmap.direction || keyInfo.imageId == R.mipmap.beijing_exchange1 || keyInfo.imageId == R.mipmap.wave || keyInfo.imageId == R.mipmap.beijing_exchange || keyInfo.imageId == R.mipmap.beijing_scale2 || keyInfo.imageId == R.mipmap.beijing_scale) {
            imageView.setImageResource(keyInfo.imageId);
        } else {
            imageView.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_circle_white));
            imageView.setImageResource(keyInfo.imageId);
        }
        if (dotData.mId == -9 || dotData.mId == -12) {
            imageView.setAlpha(0.7f);
        }
        if (keyInfo.imageId == R.mipmap.beijing_scale2 || keyInfo.imageId == R.mipmap.beijing_scale || keyInfo.imageId == R.mipmap.beijing_exchange || keyInfo.imageId == R.mipmap.wave) {
            imageView.setAlpha(1.0f);
        }
        TextView textView = (TextView) relativeLayout.findViewById(R.id.text);
        textView.setText(keyInfo.title);
        textView.setMaxLines(1);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(textView, 1, 50, 1, 1);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(keyInfo.w, keyInfo.h);
        relativeLayout.setX(keyInfo.x);
        relativeLayout.setY(keyInfo.y);
        this.mKeyRoot.addView(relativeLayout, layoutParams);
        if (this.mKeyMapForUI == null) {
            this.mKeyMapForUI = new HashMap<>();
        }
        while (this.mKeyMapForUI.get(keyInfo.tag) != null) {
            keyInfo.tag += "?";
        }
        this.mKeyMapForUI.put(keyInfo.tag, relativeLayout);
        this.mKeyIndex++;
        if (z && keyInfo.type == 4 && !keyInfo.tag.contains(DiskLruCache.VERSION_1)) {
            relativeLayout.setVisibility(8);
        }
        setKeyColor(keyInfo, dotData, imageView);
        if (keyInfo.type == 5) {
            updateSlideKeyArrow(relativeLayout, keyInfo);
        }
        return relativeLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSlideKeyArrow(View view, KeyInfo keyInfo) {
        ImageView imageView = (ImageView) view.findViewById(R.id.arrow);
        if (!keyInfo.tag.contains(DiskLruCache.VERSION_1) && keyInfo.property == 0) {
            imageView.setVisibility(0);
        } else {
            imageView.setVisibility(8);
        }
        for (View view2 : this.mKeyMapForUI.values()) {
            if (((KeyInfo) view2.getTag(R.id.tag_key_info)).id == keyInfo.id && view2 != view) {
                imageView.setRotation((float) ((Math.atan2(view.getY() - view2.getY(), view.getX() - view2.getX()) * 180.0d) / 3.141592653589793d));
                view2.findViewById(R.id.arrow).setRotation((float) ((Math.atan2(view2.getY() - view.getY(), view2.getX() - view.getX()) * 180.0d) / 3.141592653589793d));
            }
        }
    }

    private void setKeyColor(KeyInfo keyInfo, DotData dotData, ImageView imageView) {
        if (keyInfo.type == 4) {
            imageView.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_double_circle_write));
        } else if (keyInfo.type == 3) {
            imageView.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_double_circle_yellow));
        } else if (keyInfo.type == 6) {
            imageView.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_double_circle_blue));
        } else if (keyInfo.type == 0) {
            if (keyInfo.property == 0 && dotData.get_normal_key_trigger_mode() == 0) {
                return;
            }
            imageView.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_double_circle_pink));
        } else {
            imageView.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_circle_white));
        }
    }

    private void clear_all_views() {
        for (int i = 0; i < this.mKeyMapForUI.values().size(); i++) {
            this.mKeyRoot.removeView((View) this.mKeyMapForUI.values().toArray()[i]);
            this.mKeyRoot.delLineByNode((View) this.mKeyMapForUI.values().toArray()[i]);
        }
        this.mKeyMapForUI.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadKey(String str) {
        clear_all_views();
        byte[] readFile2BytesByStream = FileIOUtils.readFile2BytesByStream(this.mContext.getExternalFilesDir(null) + "/keyConfig/" + str);
        if (readFile2BytesByStream == null || readFile2BytesByStream.length <= 6 || (readFile2BytesByStream.length - 6) % 14 != 0) {
            return;
        }
        if (readFile2BytesByStream[0] == 54 && readFile2BytesByStream[1] == 90) {
            int cumBytes = Units.cumBytes(readFile2BytesByStream, 6, readFile2BytesByStream.length - 6);
            if (Units.LOBYTE(cumBytes) == readFile2BytesByStream[4] && Units.HIBYTE(cumBytes) == readFile2BytesByStream[5]) {
                this.mDotDatas = new ArrayList<>();
                int BYTE2INT = Units.BYTE2INT(readFile2BytesByStream[2], readFile2BytesByStream[3]);
                for (int i = 0; i < BYTE2INT; i++) {
                    DotData dotData = new DotData();
                    dotData.parse(readFile2BytesByStream, (i * 14) + 6);
                    this.mDotDatas.add(dotData);
                }
                this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyMappingSetting.this.m41lambda$loadKey$22$combaidukwgamesKeyMappingSetting();
                    }
                });
            }
        }
    }

    public void on_ble_get_key_map() {
        Log.d(AbsFloatBase.TAG, "on_ble_get_key_map\n");
        loadKey(this.m_nCuKeyMap, false);
    }

    private void loadKey(final int i, final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.KeyMappingSetting$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                KeyMappingSetting.this.m42lambda$loadKey$23$combaidukwgamesKeyMappingSetting(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$loadKey$23$com-baidu-kwgames-KeyMappingSetting  reason: not valid java name */
    public /* synthetic */ void m42lambda$loadKey$23$combaidukwgamesKeyMappingSetting(int i, boolean z) {
        clear_all_views();
        this.mDotDatas = new ArrayList<>();
        byte[] bArr = Units.get_key_map(i);
        if (bArr == null) {
            return;
        }
        int length = bArr.length;
        for (int i2 = 0; i2 < length; i2 += 14) {
            DotData dotData = new DotData();
            if (dotData.parse(bArr, i2)) {
                this.mDotDatas.add(dotData);
            }
        }
        m41lambda$loadKey$22$combaidukwgamesKeyMappingSetting();
        this.m_boModified = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAllKeyEnable(boolean z) {
        View view = this.testKeyView;
        if (view != null) {
            this.mKeyRoot.removeView(view);
            this.testKeyView = null;
        }
        findView(R.id.property).setVisibility(4);
        this.mSelTag = null;
        for (View view2 : this.mKeyMapForUI.values()) {
            view2.findViewById(R.id.image).setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_circle_white));
            setKeyColor((KeyInfo) view2.getTag(R.id.tag_key_info), get_dot_data(view2), (ImageView) view2.findViewById(R.id.image));
            view2.setEnabled(z);
        }
        this.mKeyRoot.changeAllLines(-1);
        this.mKeyRoot.invalidate();
        hideAllChildMacroKey();
    }

    @Override // com.baidu.kwgames.AbsFloatBase
    public void remove() {
        hide_macro_time_setting_float();
        this.testLayer.setVisibility(8);
        setAllKeyEnable(true);
        super.remove();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveAsKey(String str) {
        this.m_boModified = false;
        removeEmptyKey();
        if (this.mDotDatas != null) {
            File externalFilesDir = this.mContext.getExternalFilesDir(null);
            FileIOUtils.writeFileFromBytesByStream(externalFilesDir + "/keyConfig/" + str, new byte[]{54, Constants.KEY_KP2});
            int size = this.mDotDatas.size();
            FileIOUtils.writeFileFromBytesByStream(externalFilesDir + "/keyConfig/" + str, new byte[]{Units.LOBYTE(size), Units.HIBYTE(size)}, true);
            int i = size * 14;
            byte[] bArr = new byte[i];
            for (int i2 = 0; i2 < size; i2++) {
                System.arraycopy(this.mDotDatas.get(i2).toBytes(), 0, bArr, i2 * 14, 14);
            }
            int cumBytes = Units.cumBytes(bArr, 0, i);
            FileIOUtils.writeFileFromBytesByStream(externalFilesDir + "/keyConfig/" + str, new byte[]{Units.LOBYTE(cumBytes), Units.HIBYTE(cumBytes)}, true);
            FileIOUtils.writeFileFromBytesByStream(externalFilesDir + "/keyConfig/" + str, bArr, true);
            Units.set_key_map(this.m_nCuKeyMap, bArr);
            this.mListener.onSave(bArr, this.m_nCuKeyMap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveKey() {
        if (this.mDotDatas == null) {
            return;
        }
        this.m_boModified = false;
        removeEmptyKey();
        int size = this.mDotDatas.size();
        byte[] bArr = new byte[size * 14];
        for (int i = 0; i < size; i++) {
            System.arraycopy(this.mDotDatas.get(i).toBytes(), 0, bArr, i * 14, 14);
        }
        Units.set_key_map(this.m_nCuKeyMap, bArr);
        this.mListener.onSave(bArr, this.m_nCuKeyMap);
        check_b4_save();
    }

    void check_b4_save() {
        ArrayList<DotData> arrayList = this.mDotDatas;
        if (arrayList == null) {
            return;
        }
        byte[] bArr = {44, Constants.KEY_MOUSE_R, 20, 8};
        int size = arrayList.size();
        for (int i = 0; i < 4; i++) {
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                if (this.mDotDatas.get(i3).mId == bArr[i]) {
                    i2++;
                }
            }
            if (i2 >= 2) {
                MsgBox.msg_box_float_with_never_remind_once(AppInstance.s_context, R.string.edit_too_many_copies, String.format(NEAT.s(R.string.edit_too_many_copies), Integer.valueOf(i2), Constants.ID2String.get(Byte.valueOf(bArr[i]))), "edit_too_many_copies");
                return;
            }
        }
    }

    String get_macro_tag(DotData dotData, int i) {
        if (5 == dotData.mType) {
            return ((int) dotData.mId) + "M" + i;
        } else if (dotData.m_nMacroTriggerMode == 0) {
            return ((int) dotData.mId) + "ML" + i;
        } else if (1 == dotData.m_nMacroTriggerMode) {
            return ((int) dotData.mId) + "MD" + i;
        } else if (2 == dotData.m_nMacroTriggerMode) {
            return ((int) dotData.mId) + "MU" + i;
        } else {
            return ((int) dotData.mId) + "M" + i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: insertInUI */
    public void m41lambda$loadKey$22$combaidukwgamesKeyMappingSetting() {
        this.mKeyRoot.delete_all_lines();
        int i = this.m_nMacroUUID;
        char c = 0;
        int i2 = 0;
        KeyInfo keyInfo = null;
        int i3 = 1;
        KeyInfo keyInfo2 = null;
        while (i2 < this.mDotDatas.size()) {
            DotData dotData = this.mDotDatas.get(i2);
            if (dotData.mType == 0 || dotData.mType == 3 || dotData.mType == 6) {
                KeyInfo keyInfo3 = new KeyInfo();
                keyInfo3.tag = ((int) dotData.mId) + "";
                keyInfo3.id = dotData.mId;
                keyInfo3.type = dotData.mType;
                if (dotData.mId == -12) {
                    keyInfo3.x = dotData.mX[0] - ConvertUtils.dp2px(70.0f);
                    keyInfo3.y = dotData.mY[0] - ConvertUtils.dp2px(70.0f);
                    keyInfo3.w = ConvertUtils.dp2px(140.0f);
                    keyInfo3.h = ConvertUtils.dp2px(140.0f);
                    keyInfo3.imageId = R.mipmap.direction;
                } else if (dotData.mId == -9) {
                    keyInfo3.x = dotData.mX[0] - ConvertUtils.dp2px(70.0f);
                    keyInfo3.y = dotData.mY[0] - ConvertUtils.dp2px(70.0f);
                    keyInfo3.w = ConvertUtils.dp2px(140.0f);
                    keyInfo3.h = ConvertUtils.dp2px(140.0f);
                    keyInfo3.imageId = R.mipmap.udlr;
                } else {
                    byte b = dotData.mId;
                    if (b == -11) {
                        keyInfo3.imageId = R.mipmap.mouse_s1;
                    } else if (b == -10) {
                        keyInfo3.imageId = R.mipmap.mouse_s2;
                    } else if (b == -8) {
                        keyInfo3.imageId = R.mipmap.beijing_exchange1;
                    } else if (b == -7) {
                        keyInfo3.imageId = R.mipmap.beijing_scale2;
                    } else if (b != 53) {
                        switch (b) {
                            case Core.BadNumChannel1U /* -16 */:
                                keyInfo3.imageId = R.mipmap.mouse_l;
                                break;
                            case Core.BadNumChannels /* -15 */:
                                keyInfo3.imageId = R.mipmap.mouse_m;
                                break;
                            case Core.BadModelOrChSeq /* -14 */:
                                keyInfo3.imageId = R.mipmap.mouse_r;
                                break;
                            case Core.BadStep /* -13 */:
                                keyInfo3.imageId = R.mipmap.mouse;
                                break;
                        }
                    } else {
                        keyInfo3.imageId = R.mipmap.wave;
                    }
                    if (keyInfo3.imageId == 0) {
                        keyInfo3.title = Constants.ID2String.get(Byte.valueOf(dotData.mId));
                    }
                    keyInfo3.x = dotData.mX[0] - ConvertUtils.dp2px(15.0f);
                    keyInfo3.y = dotData.mY[0] - ConvertUtils.dp2px(15.0f);
                    keyInfo3.w = ConvertUtils.dp2px(30.0f);
                    keyInfo3.h = ConvertUtils.dp2px(30.0f);
                }
                if (dotData.mType == 0) {
                    keyInfo3.property = dotData.mY[2];
                    if (keyInfo3.property == 0 && -13 == dotData.mId) {
                        keyInfo3.property = dotData.mX[2];
                    }
                }
                addKey(keyInfo3, dotData, 0, false);
            } else if (dotData.mType == 2 || (dotData.mType == 1 && (dotData.mId == 53 || dotData.mId == -8))) {
                KeyInfo keyInfo4 = new KeyInfo();
                keyInfo4.tag = ((int) dotData.mId) + "";
                byte b2 = dotData.mId;
                if (b2 == -8) {
                    keyInfo4.imageId = R.mipmap.beijing_exchange;
                } else if (b2 == 53) {
                    keyInfo4.imageId = R.mipmap.wave;
                }
                if (keyInfo4.imageId == 0) {
                    keyInfo4.title = Constants.ID2String.get(Byte.valueOf(dotData.mId));
                }
                keyInfo4.id = dotData.mId;
                keyInfo4.type = dotData.mType;
                keyInfo4.x = dotData.mX[0] - ConvertUtils.dp2px(15.0f);
                keyInfo4.y = dotData.mY[0] - ConvertUtils.dp2px(15.0f);
                keyInfo4.w = ConvertUtils.dp2px(30.0f);
                keyInfo4.h = ConvertUtils.dp2px(30.0f);
                KeyInfo keyInfo5 = new KeyInfo();
                if (dotData.mId == -8) {
                    keyInfo5.title = "F1";
                    keyInfo5.tag = keyInfo5.title + DiskLruCache.VERSION_1;
                } else if (dotData.mId == 53) {
                    keyInfo5.title = DiskLruCache.VERSION_1;
                    keyInfo5.tag = keyInfo5.title + "1s";
                } else {
                    keyInfo5.tag = keyInfo5.title + DiskLruCache.VERSION_1;
                }
                keyInfo5.id = dotData.mId;
                keyInfo5.type = dotData.mType;
                keyInfo5.x = dotData.mX[1] - ConvertUtils.dp2px(15.0f);
                keyInfo5.y = dotData.mY[1] - ConvertUtils.dp2px(15.0f);
                keyInfo5.w = ConvertUtils.dp2px(30.0f);
                keyInfo5.h = ConvertUtils.dp2px(30.0f);
                KeyInfo keyInfo6 = new KeyInfo();
                if (dotData.mId == -8) {
                    keyInfo6.title = "F2";
                    keyInfo6.tag = keyInfo6.title + "2";
                } else if (dotData.mId == 53) {
                    keyInfo6.title = "2";
                    keyInfo6.tag = keyInfo6.title + "2s";
                } else {
                    keyInfo6.tag = keyInfo6.title + "2";
                }
                keyInfo6.id = dotData.mId;
                keyInfo6.type = dotData.mType;
                keyInfo6.x = dotData.mX[2] - ConvertUtils.dp2px(15.0f);
                keyInfo6.y = dotData.mY[2] - ConvertUtils.dp2px(15.0f);
                keyInfo6.w = ConvertUtils.dp2px(30.0f);
                keyInfo6.h = ConvertUtils.dp2px(30.0f);
                this.mCurKeyInfoConfig.add(keyInfo4);
                this.mCurKeyInfoConfig.add(keyInfo5);
                this.mCurKeyInfoConfig.add(keyInfo6);
                addKey(keyInfo4, dotData, 0, false);
                addKey(keyInfo5, dotData, 1, false);
                addKey(keyInfo6, dotData, 2, false);
                if (keyInfo4.x >= 0 && keyInfo4.y >= 0) {
                    this.mKeyRoot.addLine(this.mKeyMapForUI.get(keyInfo4.tag), this.mKeyMapForUI.get(keyInfo5.tag));
                    this.mKeyRoot.addLine(this.mKeyMapForUI.get(keyInfo4.tag), this.mKeyMapForUI.get(keyInfo6.tag));
                }
            } else if (dotData.mType == 4) {
                KeyInfo keyInfo7 = new KeyInfo();
                if (keyInfo == null || keyInfo.id != dotData.mId || keyInfo.m_nMacroTriggerMode != dotData.m_nMacroTriggerMode) {
                    keyInfo7.title = Constants.ID2String.get(Byte.valueOf(dotData.mId));
                    i = this.m_nMacroUUID;
                    this.m_nMacroUUID = i + 1;
                    keyInfo2 = keyInfo7;
                    keyInfo = null;
                    i3 = 1;
                } else {
                    keyInfo7.title = i3 + "";
                }
                if (keyInfo2 == null) {
                    keyInfo2 = keyInfo7;
                }
                dotData.set_macro_UUID(i);
                keyInfo7.tag = get_macro_tag(dotData, i3);
                int i4 = i3 + 1;
                keyInfo7.set_data(dotData.mId, dotData.mType, dotData.m_nMacroTriggerMode, i);
                keyInfo7.set_normal_size_xy(dotData.mX[c], dotData.mY[c]);
                KeyInfo keyInfo8 = new KeyInfo();
                keyInfo8.title = i4 + "";
                if (dotData.mType == 5) {
                    keyInfo8.title = "";
                }
                keyInfo8.tag = get_macro_tag(dotData, i4);
                int i5 = i4 + 1;
                keyInfo8.set_data(dotData.mId, dotData.mType, dotData.m_nMacroTriggerMode, i);
                keyInfo8.set_normal_size_xy(dotData.mX[1], dotData.mY[1]);
                KeyInfo keyInfo9 = new KeyInfo();
                keyInfo9.title = i5 + "";
                if (dotData.mType == 5) {
                    keyInfo9.title = "";
                }
                keyInfo9.tag = get_macro_tag(dotData, i5);
                i3 = i5 + 1;
                keyInfo9.set_data(dotData.mId, dotData.mType, dotData.m_nMacroTriggerMode, i);
                keyInfo9.set_normal_size_xy(dotData.mX[2], dotData.mY[2]);
                addKey(keyInfo7, dotData, 0, true);
                keyInfo2.property++;
                if (keyInfo != null && keyInfo7.same_macro(keyInfo) && keyInfo != keyInfo7) {
                    if (dotData.mType == 4) {
                        this.mKeyRoot.addLine(this.mKeyMapForUI.get(keyInfo7.tag), this.mKeyMapForUI.get(keyInfo.tag), 16777215);
                    } else {
                        this.mKeyRoot.addLine(this.mKeyMapForUI.get(keyInfo7.tag), this.mKeyMapForUI.get(keyInfo.tag), -1);
                    }
                }
                if (dotData.mX[1] >= 0 && dotData.mY[1] >= 0) {
                    addKey(keyInfo8, dotData, 1, true);
                    keyInfo2.property++;
                    if (dotData.mType == 4) {
                        this.mKeyRoot.addLine(this.mKeyMapForUI.get(keyInfo7.tag), this.mKeyMapForUI.get(keyInfo8.tag), 16777215);
                    } else {
                        this.mKeyRoot.addLine(this.mKeyMapForUI.get(keyInfo7.tag), this.mKeyMapForUI.get(keyInfo8.tag), -1);
                    }
                    if (dotData.mX[2] >= 0 && dotData.mY[2] >= 0 && dotData.mType == 4) {
                        addKey(keyInfo9, dotData, 2, true);
                        keyInfo2.property++;
                        if (dotData.mType == 4) {
                            this.mKeyRoot.addLine(this.mKeyMapForUI.get(keyInfo8.tag), this.mKeyMapForUI.get(keyInfo9.tag), 16777215);
                        } else {
                            this.mKeyRoot.addLine(this.mKeyMapForUI.get(keyInfo8.tag), this.mKeyMapForUI.get(keyInfo9.tag), -1);
                        }
                        keyInfo = keyInfo9;
                    }
                }
                keyInfo = null;
                i3 = 1;
                keyInfo2 = null;
            } else if (dotData.mType == 5) {
                KeyInfo keyInfo10 = new KeyInfo();
                keyInfo10.title = Constants.ID2String.get(Byte.valueOf(dotData.mId));
                int i6 = this.m_nMacroUUID;
                this.m_nMacroUUID = i6 + 1;
                keyInfo10.tag = get_macro_tag(dotData, 1);
                keyInfo10.set_data(dotData.mId, dotData.mType, dotData.m_nMacroTriggerMode, i6);
                keyInfo10.set_normal_size_xy(dotData.mX[0], dotData.mY[0]);
                KeyInfo keyInfo11 = new KeyInfo();
                keyInfo11.title = "";
                keyInfo11.tag = get_macro_tag(dotData, 2);
                keyInfo11.set_data(dotData.mId, dotData.mType, dotData.m_nMacroTriggerMode, i6);
                keyInfo11.set_normal_size_xy(dotData.mX[1], dotData.mY[1]);
                addKey(keyInfo10, dotData, 0, true);
                keyInfo10.property++;
                addKey(keyInfo11, dotData, 1, true);
                keyInfo10.property++;
                this.mKeyRoot.addLine(this.mKeyMapForUI.get(keyInfo10.tag), this.mKeyMapForUI.get(keyInfo11.tag), -1);
                i = i6;
                keyInfo = null;
                i3 = 1;
                keyInfo2 = null;
            }
            i2++;
            c = 0;
        }
        hideAllChildMacroKey();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void modifyKey(byte b) {
        View view;
        String str = this.mSelTag;
        if (str == null || (view = this.mKeyMapForUI.get(str)) == null) {
            return;
        }
        KeyInfo keyInfo = (KeyInfo) view.getTag(R.id.tag_key_info);
        int i = R.id.tag_dot_data;
        DotData dotData = (DotData) view.getTag(R.id.tag_dot_data);
        String str2 = Constants.ID2String.get(Byte.valueOf(b));
        if (TextUtils.isEmpty(str2)) {
            Toast.makeText(this.mContext, this.mContext.getString(R.string.un_find_key), 1).show();
        } else if (!is_key_enable_modify(dotData)) {
            Toast.makeText(this.mContext, this.mContext.getString(R.string.modify_err_tip), 1).show();
        } else {
            if (str2.equals("W") || str2.equals("A") || str2.equals("S") || str2.equals("D")) {
                for (Map.Entry<String, View> entry : this.mKeyMapForUI.entrySet()) {
                    DotData dotData2 = (DotData) entry.getValue().getTag(R.id.tag_dot_data);
                    if (entry.getValue() != view && dotData2.mId == -12) {
                        Toast.makeText(this.mContext, this.mContext.getString(R.string.exit_key_tip), 0).show();
                        return;
                    }
                }
            }
            if (str2.equals("↑") || str2.equals("↓") || str2.equals("←") || str2.equals("→")) {
                for (Map.Entry<String, View> entry2 : this.mKeyMapForUI.entrySet()) {
                    DotData dotData3 = (DotData) entry2.getValue().getTag(R.id.tag_dot_data);
                    if (entry2.getValue() != view && dotData3.mId == -9) {
                        Toast.makeText(this.mContext, this.mContext.getString(R.string.exit_key_tip), 0).show();
                        return;
                    }
                }
            }
            if (keyInfo.title != null && ((str2.equals("↑") && keyInfo.title.equals("↑")) || (str2.equals("W") && keyInfo.title.equals("W")))) {
                for (Map.Entry<String, View> entry3 : this.mKeyMapForUI.entrySet()) {
                    DotData dotData4 = (DotData) entry3.getValue().getTag(i);
                    if (str2.equals("W")) {
                        if (entry3.getValue() != view && (dotData4.mId == 26 || dotData4.mId == 4 || dotData4.mId == 22 || dotData4.mId == 7)) {
                            Toast.makeText(this.mContext, this.mContext.getString(R.string.del_wasd_to_wasd), 0).show();
                            return;
                        }
                    } else if (entry3.getValue() != view && (dotData4.mId == 82 || dotData4.mId == 81 || dotData4.mId == 80 || dotData4.mId == 79)) {
                        Toast.makeText(this.mContext, this.mContext.getString(R.string.del_udlr_to_udlr), 0).show();
                        return;
                    }
                    i = R.id.tag_dot_data;
                }
                TextView textView = (TextView) view.findViewById(R.id.text);
                ImageView imageView = (ImageView) view.findViewById(R.id.image);
                keyInfo.w = ConvertUtils.dp2px(140.0f);
                keyInfo.h = ConvertUtils.dp2px(140.0f);
                keyInfo.type = dotData.mType;
                if (str2.equals("W")) {
                    dotData.mId = Constants.KEY_WASD;
                    keyInfo.id = Constants.KEY_WASD;
                    keyInfo.imageId = R.mipmap.direction;
                } else {
                    dotData.mId = (byte) -9;
                    keyInfo.id = (byte) -9;
                    keyInfo.imageId = R.mipmap.udlr;
                }
                textView.setText("");
                imageView.setImageResource(keyInfo.imageId);
                view.getLayoutParams().width = keyInfo.w;
                view.getLayoutParams().height = keyInfo.h;
                view.setX((view.getX() - (keyInfo.w / 2)) + (ConvertUtils.dp2px(20.0f) / 2));
                view.setY((view.getY() - (keyInfo.h / 2)) + (ConvertUtils.dp2px(20.0f) / 2));
                view.invalidate();
                return;
            }
            if (keyInfo.w > 100) {
                keyInfo.w = ConvertUtils.dp2px(30.0f);
                keyInfo.h = ConvertUtils.dp2px(30.0f);
                view.getLayoutParams().width = keyInfo.w;
                view.getLayoutParams().height = keyInfo.h;
                view.setX(keyInfo.x);
                view.setY(keyInfo.y);
                view.invalidate();
            }
            keyInfo.title = str2;
            if (keyInfo.type == 4 || keyInfo.type == 5) {
                remove_macro_other_dots(view);
                remove_slide_other_dots(view);
                set_key_view_text_white(view);
                dotData.mType = (byte) 0;
                dotData.mX[2] = 0;
                dotData.mX[1] = 0;
                dotData.mY[1] = 0;
                dotData.mY[2] = 0;
                keyInfo.type = 0;
                keyInfo.property = 0;
            }
            keyInfo.id = b;
            dotData.mId = b;
            TextView textView2 = (TextView) view.findViewById(R.id.text);
            ImageView imageView2 = (ImageView) view.findViewById(R.id.image);
            keyInfo.imageId = 0;
            byte b2 = keyInfo.id;
            if (b2 == -11) {
                keyInfo.imageId = R.mipmap.mouse_s1;
            } else if (b2 == -10) {
                keyInfo.imageId = R.mipmap.mouse_s2;
            } else if (b2 == -8) {
                keyInfo.imageId = R.mipmap.beijing_exchange1;
            } else if (b2 == -7) {
                keyInfo.imageId = R.mipmap.beijing_scale2;
            } else if (b2 != 53) {
                switch (b2) {
                    case Core.BadNumChannel1U /* -16 */:
                        keyInfo.imageId = R.mipmap.mouse_l;
                        break;
                    case Core.BadNumChannels /* -15 */:
                        keyInfo.imageId = R.mipmap.mouse_m;
                        break;
                    case Core.BadModelOrChSeq /* -14 */:
                        keyInfo.imageId = R.mipmap.mouse_r;
                        break;
                    case Core.BadStep /* -13 */:
                        keyInfo.imageId = R.mipmap.mouse;
                        break;
                }
            } else {
                keyInfo.imageId = R.mipmap.wave;
            }
            if (keyInfo.imageId == 0) {
                imageView2.setImageResource(R.color.transparent);
                textView2.setText(str2);
            } else {
                textView2.setText("");
                imageView2.setImageResource(keyInfo.imageId);
            }
            this.m_boModified = true;
        }
    }

    public boolean is_key_enable_modify(DotData dotData) {
        return (dotData.mType == 1 || dotData.mId == -13 || dotData.mId == -8 || dotData.mId == -7 || dotData.mId == -15) ? false : true;
    }

    public void onKeySel(final Byte b) {
        if (this.testLayer.getVisibility() == 0) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.KeyMappingSetting.29
            @Override // java.lang.Runnable
            public void run() {
                if (KeyMappingSetting.this.mSelTag != null) {
                    HashMap hashMap = KeyMappingSetting.this.mKeyMapForUI;
                    View view = (View) hashMap.get(b + "");
                    if (view == null) {
                        KeyMappingSetting.this.modifyKey(b.byteValue());
                        return;
                    }
                    KeyInfo keyInfo = (KeyInfo) view.getTag(R.id.tag_key_info);
                    if (keyInfo.type == 0 || keyInfo.type == 3 || keyInfo.type == 5 || keyInfo.type == 6) {
                        KeyMappingSetting.this.modifyKey(b.byteValue());
                    } else {
                        Toast.makeText(KeyMappingSetting.this.mContext, KeyMappingSetting.this.mContext.getString(R.string.modify_err_tip), 1).show();
                    }
                }
            }
        });
    }

    void update_macro_setting_text() {
        String str;
        if (this.m_dotCurMacroDot == null || this.m_textMacroPressTime == null || this.m_textMacroWaitTime == null) {
            return;
        }
        TextView textView = this.m_txtMacroTimeSettingTitle;
        StringBuilder sb = new StringBuilder();
        sb.append(NEAT.s(R.string.macro_time_set_title));
        sb.append(Constants.ID2String.get(Byte.valueOf(this.m_dotCurMacroDot.mId)));
        sb.append("-");
        if (this.m_strDotTag.length() > 0) {
            String str2 = this.m_strDotTag;
            str = str2.substring(str2.length() - 1);
        } else {
            str = "";
        }
        sb.append(str);
        textView.setText(sb.toString());
        if (this.m_dotCurMacroDot.arrMacroInfo[this.m_nMacroEditDotIndex].nMacroTime == 0) {
            this.m_textMacroPressTime.setText(NEAT.s(R.string.str_default));
        } else if (AppInstance.s_strGamePackageName.contains(Constants.E_GAME_YONGJIEWUJIAN) && AppInstance.g_sSysStatus.has_yjwj_macro_capacity()) {
            TextView textView2 = this.m_textMacroPressTime;
            textView2.setText("" + s_arrMacroPressTimeYJWJ[this.m_dotCurMacroDot.arrMacroInfo[this.m_nMacroEditDotIndex].nMacroTime]);
        } else {
            TextView textView3 = this.m_textMacroPressTime;
            textView3.setText("" + s_arrMacroPressTime[this.m_dotCurMacroDot.arrMacroInfo[this.m_nMacroEditDotIndex].nMacroTime]);
        }
        if (this.m_dotCurMacroDot.arrMacroInfo[this.m_nMacroEditDotIndex].nMacroWait == 0) {
            this.m_textMacroWaitTime.setText(NEAT.s(R.string.str_default));
            return;
        }
        TextView textView4 = this.m_textMacroWaitTime;
        textView4.setText("" + s_arrMacroWaitTime[this.m_dotCurMacroDot.arrMacroInfo[this.m_nMacroEditDotIndex].nMacroWait]);
    }

    private void show_macro_time_setting_float(int i, String str, DotData dotData) {
        if (AppInstance.g_sSysStatus.uSystemVer < 96) {
            return;
        }
        this.m_nMacroEditDotIndex = i;
        this.m_dotCurMacroDot = dotData;
        this.m_strDotTag = str;
        if (EasyFloat.getAppFloatView(Constants.FLOAT_MACRO_TIME) != null) {
            update_macro_setting_text();
            return;
        }
        EasyFloat.Builder layout = EasyFloat.with(AppInstance.s_context).setTag(Constants.FLOAT_MACRO_TIME).setLayout(R.layout.macro_time, new OnInvokeView() { // from class: com.baidu.kwgames.KeyMappingSetting.30
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                FloatMgr.resetVirtualMouse();
                KeyMappingSetting.this.m_viewMacroTimeSetting = view;
                KeyMappingSetting.this.m_txtMacroTimeSettingTitle = (TextView) view.findViewById(R.id.macro_time_title);
                KeyMappingSetting.this.m_textMacroPressTime = (TextView) view.findViewById(R.id.text_macro_press_time);
                KeyMappingSetting.this.m_textMacroWaitTime = (TextView) view.findViewById(R.id.text_release_press_time);
                KeyMappingSetting.this.update_macro_setting_text();
                view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.30.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        EasyFloat.dismissAppFloat(Constants.FLOAT_MACRO_TIME);
                    }
                });
                view.findViewById(R.id.macro_press_time_minus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.30.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        if (KeyMappingSetting.this.m_dotCurMacroDot.arrMacroInfo[KeyMappingSetting.this.m_nMacroEditDotIndex].nMacroTime > 0) {
                            KeyMappingSetting.this.m_dotCurMacroDot.arrMacroInfo[KeyMappingSetting.this.m_nMacroEditDotIndex].nMacroTime--;
                            KeyMappingSetting.this.update_macro_setting_text();
                            KeyMappingSetting.this.m_boModified = true;
                        }
                    }
                });
                view.findViewById(R.id.macro_press_time_plus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.30.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        if (KeyMappingSetting.this.m_dotCurMacroDot.arrMacroInfo[KeyMappingSetting.this.m_nMacroEditDotIndex].nMacroTime < 31) {
                            KeyMappingSetting.this.m_dotCurMacroDot.arrMacroInfo[KeyMappingSetting.this.m_nMacroEditDotIndex].nMacroTime++;
                            KeyMappingSetting.this.update_macro_setting_text();
                            KeyMappingSetting.this.m_boModified = true;
                        }
                    }
                });
                view.findViewById(R.id.macro_wait_time_minus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.30.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        if (KeyMappingSetting.this.m_dotCurMacroDot.arrMacroInfo[KeyMappingSetting.this.m_nMacroEditDotIndex].nMacroWait > 0) {
                            KeyMappingSetting.this.m_dotCurMacroDot.arrMacroInfo[KeyMappingSetting.this.m_nMacroEditDotIndex].nMacroWait--;
                            KeyMappingSetting.this.update_macro_setting_text();
                            KeyMappingSetting.this.m_boModified = true;
                        }
                    }
                });
                view.findViewById(R.id.macro_wait_time_plus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.KeyMappingSetting.30.5
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        if (KeyMappingSetting.this.m_dotCurMacroDot.arrMacroInfo[KeyMappingSetting.this.m_nMacroEditDotIndex].nMacroWait < 15) {
                            KeyMappingSetting.this.m_dotCurMacroDot.arrMacroInfo[KeyMappingSetting.this.m_nMacroEditDotIndex].nMacroWait++;
                            KeyMappingSetting.this.update_macro_setting_text();
                            KeyMappingSetting.this.m_boModified = true;
                        }
                    }
                });
            }
        });
        layout.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.KeyMappingSetting.31
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str2, View view) {
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dismiss() {
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void drag(View view, MotionEvent motionEvent) {
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
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                KeyMappingSetting.this.m_viewMacroTimeSetting.getLocationOnScreen(iArr);
                KeyMappingSetting.m_ini.put(Constants.CFG_MACRO_TIME_SET_FLOAT_X, iArr[0]);
                KeyMappingSetting.m_ini.put(Constants.CFG_MACRO_TIME_SET_FLOAT_Y, iArr[1]);
            }
        });
        layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true);
        layout.setLocation(NEAT.make_sure_x_visible(m_ini.getInt(Constants.CFG_MACRO_TIME_SET_FLOAT_X, 100)), NEAT.make_sure_y_visible(m_ini.getInt(Constants.CFG_MACRO_TIME_SET_FLOAT_Y, 0)));
        layout.show();
    }

    public void hide_macro_time_setting_float() {
        EasyFloat.dismissAppFloat(Constants.FLOAT_MACRO_TIME);
        this.m_dotCurMacroDot = null;
    }
}
