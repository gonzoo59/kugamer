package com.baidu.kwgames;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.hardware.display.VirtualDisplay;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TimingLogger;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnLongClick;
import com.baidu.kwgames.Constants;
import com.baidu.kwgames.IThrone;
import com.baidu.kwgames.MainActivity;
import com.baidu.kwgames.USB.UsbAOADevice;
import com.baidu.kwgames.USB.UsbHidDevice;
import com.baidu.kwgames.adapter.XScopeAdapter;
import com.baidu.kwgames.bean.ViewInfo;
import com.baidu.kwgames.net.ADB;
import com.baidu.kwgames.net.HttpHelper;
import com.baidu.kwgames.net.rsq.NoticeRsq;
import com.baidu.kwgames.util.AI1stBulletOpt;
import com.baidu.kwgames.util.AIActiveRun;
import com.baidu.kwgames.util.AIConvert;
import com.baidu.kwgames.util.AICrosshair;
import com.baidu.kwgames.util.AIDynamicGunDownM4;
import com.baidu.kwgames.util.AISmartFPP;
import com.baidu.kwgames.util.AIXScope;
import com.baidu.kwgames.util.AgileMgr;
import com.baidu.kwgames.util.FloatCallbackDragRemIni;
import com.baidu.kwgames.util.FloatMgr;
import com.baidu.kwgames.util.GunPartsMgr;
import com.baidu.kwgames.util.ImageSwButton;
import com.baidu.kwgames.util.KeyMaps;
import com.baidu.kwgames.util.MsgBox;
import com.baidu.kwgames.util.NEAT;
import com.baidu.kwgames.util.ScanExceptionHandler;
import com.baidu.kwgames.util.Util;
import com.baidu.kwgames.util.ViewMgr;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.lzf.easyfloat.widget.appfloat.FloatManager;
import com.polidea.rxandroidble2.exceptions.BleScanException;
import com.polidea.rxandroidble2.scan.ScanFilter;
import com.polidea.rxandroidble2.scan.ScanResult;
import com.polidea.rxandroidble2.scan.ScanSettings;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/* loaded from: classes.dex */
public class MainActivity extends AppCompatActivity {
    public static final int AI_STAGE_BG_FOCUS = -34817;
    public static final int AI_STAGE_BG_NORMAL = -13158601;
    private static final int BASIC_PERMISSION_REQUEST_CODE = 1;
    private static final int BLE_SCAN_PERMISSION_REQUEST_CODE = 6;
    private static final int DEV_SELECT = 2;
    public static final int E_GUN_PARTS_H = 3;
    public static final int E_PUBG_GUN_PART_BOX_LIANXU_EXIT_THRESHOLD = 3;
    public static final int E_PUBG_GUN_PART_BOX_LIANXU_WHITE = 10;
    public static final int E_PUBG_GUN_PART_BOX_WIDTH_MIN = 20;
    private static final int FLOAT_BTN_SIZE = 60;
    private static final int GAME_SELECT = 1;
    private static final boolean IS_DEBUGGING = false;
    private static final int SCREEN_SHOT = 0;
    private static int SYSTEM_STATUS_BYTES = 0;
    private static final String TAG = "MainActivity";
    private static final int THRONE_CHANGE_MODE = 4;
    private static final int THRONE_GOTO_BACKUP = 5;
    private static final int THRONE_SETTING = 3;
    private static final String TIMING_LOGGER_TAG = "timing";
    private static SPUtils m_ini;
    private static int m_nAIPeriod;
    private static boolean[] s_arrShowGuide;
    private static ViewInfo s_vInfo;
    private View doubleMirrorMask;
    private View doubleMirrorSelect;
    private View downMask;
    private View downSelect;
    private View game1LeftTip;
    private View game1RightTip;
    private View gunMask1;
    private View gunMask2;
    private View gunMaskSelectIcon1;
    private View gunMaskSelectIcon2;
    @BindView(R.id.ai)
    SwitchCompat mAI;
    private TextView mAgileText;
    private Thread mCaptureThread;
    @BindView(R.id.connect_state)
    TextView mConnectState;
    Intent mData;
    int mDpi;
    private TextView mEditTipTextView;
    ImageView mFloatWindowTable;
    @BindViews({R.id.game1, R.id.game2, R.id.game3, R.id.game4, R.id.game5, R.id.game6, R.id.game7, R.id.game8})
    List<AppCompatButton> mGames;
    private Button mGun1Add;
    private Button mGun1Reduce;
    @BindView(R.id.gun1_sensitivity)
    AppCompatSeekBar mGun1Sensitivity;
    boolean mGun1SensitivityTracking;
    private Button mGun2Add;
    private Button mGun2Reduce;
    @BindView(R.id.gun2_sensitivity)
    AppCompatSeekBar mGun2Sensitivity;
    boolean mGun2SensitivityTracking;
    private int mGunValue1;
    private int mGunValue2;
    private TextView mGunValueTV1;
    private TextView mGunValueTV2;
    private boolean mHasGun1;
    private boolean mHasGun2;
    private boolean mHasLie;
    private boolean mHasSquat;
    private Disposable mHideDisposable;
    boolean mIsRecording;
    private boolean mKaijing;
    private TextView mMarqueeTextview;
    MediaProjection mMediaProjection;
    private Disposable mMouseDisposable;
    private Button mMouseMoveAdd;
    private Button mMouseMoveReduce;
    @BindView(R.id.mouse_move_sensitivity)
    AppCompatSeekBar mMouseMoveSensitivity;
    boolean mMouseMoveSensitivityTracking;
    private Button mMousePointerAdd;
    private Button mMousePointerReduce;
    @BindView(R.id.mouse_pointer_sensitivity)
    AppCompatSeekBar mMousePointerSensitivity;
    boolean mMousePointerSensitivityTracking;
    MediaProjectionManager mProjectionManager;
    private View mRecognRoot;
    private TextView mRecognTextview;
    int mResultCode;
    private boolean mResumed;
    private VirtualDisplay mScreenShot;
    private LinearLayout mTable;
    View m_AIdynamicStageRoot;
    XScopeAdapter m_adapterXScope;
    @BindViews({R.id.game1_video, R.id.game2_video, R.id.game3_video, R.id.game4_video, R.id.game5_video, R.id.game6_video, R.id.game7_video, R.id.game8_video})
    List<AppCompatButton> m_arrLstGameVideo;
    int[] m_arrPixels;
    int[] m_arrPreviewTimeScale;
    Button[] m_arrStageButtonsM4;
    ArrayList<Short> m_arrXScopeGunIndex;
    ArrayList<String> m_arrXScopeGuns;
    ArrayList<Boolean> m_arrXScopeGunsSel;
    private boolean m_boAIToAllScope;
    boolean m_boCrosshairChekTimerRunning;
    private boolean m_boDynamicFloatIsVisible;
    private boolean m_boDynamicGunAdjustFloatIsVisible;
    private boolean m_boDynamicGunPressOnOff;
    private boolean m_boDynamicStageAdjustVisible;
    Boolean m_boM4DynamicPreviewing;
    boolean m_boNeedUpdateStageUI;
    private boolean m_boSupportDynamicGunPress;
    Boolean m_boSupportM4Dynamic;
    @BindView(R.id.m_btn1stBulletOptimize)
    Button m_btn1stBulletOptimize;
    @BindView(R.id.btn_ai_continues_shoot_onoff)
    Button m_btnAIContinuesShootOnOff;
    @BindView(R.id.btn_ai_crosshair)
    Button m_btnAICrossHair;
    @BindView(R.id.btn_ai_dynamic_onoff)
    Button m_btnAIDynamicOnOff;
    private Button m_btnAIFloatCrosshair;
    @BindView(R.id.btn_ai_question)
    Button m_btnAIQuestion;
    private Button m_btnAIResultAIStage;
    private Button m_btnAIResultAdd;
    private Button m_btnAIResultReduce;
    private Button m_btnAIResultToAllScope;
    @BindView(R.id.m_btnAIXScope)
    Button m_btnAIXScope;
    Button m_btnAddStage;
    @BindView(R.id.m_btnAdvice)
    ImageButton m_btnAdvice;
    @BindView(R.id.m_btnAutoActiveRun)
    Button m_btnAutoActiveRun;
    Button m_btnCopyM4;
    Button m_btnDeleteStage;
    private Button m_btnDynamicCrossHair;
    @BindView(R.id.dynamic_gun)
    ImageView m_btnDynamicGun;
    Button m_btnDynamicLevelSensMinus;
    Button m_btnDynamicLevelSensPlus;
    private Button m_btnDynamicStage;
    Button m_btnEnableDynamicAdjust;
    @BindView(R.id.m_btnFacebook)
    ImageButton m_btnFacebook;
    @BindView(R.id.m_btnFireSense)
    Button m_btnFireSense;
    @BindView(R.id.m_btnHelp)
    ImageButton m_btnHelp;
    @BindView(R.id.m_btnID)
    ImageButton m_btnID;
    Button m_btnPartsReduceMinus;
    Button m_btnPartsReducePlus;
    @BindView(R.id.m_btnQianliyan)
    Button m_btnQianliyan;
    @BindView(R.id.m_btnSmartQE)
    Button m_btnSmartQE;
    @BindView(R.id.m_btnTPPFPP)
    Button m_btnTPPFPP;
    @BindView(R.id.m_btnTouchSenseYPercent)
    Button m_btnTouchSenseYPercent;
    Button m_btnXSCopeTriggerMode;
    private Spinner m_comDynamicLevel;
    CrosshairInfo m_crossDynamicScopeOff;
    View m_dynamicStageRoot;
    private ImageView m_imageAdjustPreview;
    ImageView m_imageExample;
    long m_lPreviewStartTime;
    View m_layoutAIDynamicM4;
    LinearLayout m_layoutAutoLoopDelay;
    LinearLayout m_layoutAutoTriggerDelay;
    View m_layoutPartsReduce;
    @BindView(R.id.m_layoutRoot)
    LinearLayout m_layoutRoot;
    RecyclerView m_lstXScopeGame;
    MacroAdjustFloat m_macroAdjustFloat;
    private int m_nAIGunDownPercent;
    int m_nCurEditDynamicLevel;
    int m_nCurPreviewStage;
    int m_nGun1PartsFrameCnt;
    int m_nGun2PartsFrameCnt;
    int m_nScreenHeight;
    int m_nScreenWidth;
    View.OnClickListener m_stageM4ClickListener;
    SeekBar m_stageTimeSeekbar;
    SeekBar m_stageValSeekbar;
    String m_strMybpName;
    TextView m_textAICrossHairSize;
    private TextView m_textAIGunDownPercent;
    private TextView m_textDynamicResult;
    TextView m_textDynamicSens;
    TextView m_textDynamicSensC;
    TextView m_textDynamicSensZ;
    TextView m_textErr;
    TextView m_textPartsReduce;
    TextView m_textPartsReduceTitle;
    TextView m_textStageTime;
    TextView m_textStageVal;
    private TextView m_textTouchYPercent;
    TextView m_textXScopeAutoTriggerDelay;
    TextView m_textXScopeHoldTime;
    TextView m_textXScopeLoopDelay;
    Runnable m_timerForM4DynamicPreview;
    @BindView(R.id.ai_gun_press_title)
    TextView m_txtAITitle;
    View m_viewAIDynamicCrosshairFloat;
    TextView m_viewAIEditRebootRemind;
    View m_viewAIEditRoot;
    private View m_viewBagtag;
    private View m_viewBagtagFrame;
    private View m_viewBagtagImage;
    private View m_viewDynamicAdjustLayout;
    View m_viewDynamicCrosshairSet;
    private View m_viewGun1Handle;
    private View m_viewGun1HandleFrame;
    private View m_viewGun1HandleImage;
    private View m_viewGun1Head;
    private View m_viewGun1HeadFrame;
    private View m_viewGun1HeadImage;
    private View m_viewGun1Tail;
    private View m_viewGun1TailFrame;
    private View m_viewGun1TailImage;
    private View m_viewGun2Handle;
    private View m_viewGun2HandleFrame;
    private View m_viewGun2HandleImage;
    private View m_viewGun2Head;
    private View m_viewGun2HeadFrame;
    private View m_viewGun2HeadImage;
    private View m_viewGun2Tail;
    private View m_viewGun2TailFrame;
    private View m_viewGun2TailImage;
    private View m_viewTakeOfftag;
    private View m_viewTakeOfftagFrame;
    private View m_viewTakeOfftagImage;
    View m_viewXScopeAdjustRoot;
    private WebHandler m_webHandler;
    private View mirror;
    private View mirrorSelect;
    private Disposable scanDisposable;
    private View squatMask;
    private View squatSelect;
    private View temp1;
    private View temp2;
    private TimingLogger timingLogger;
    private static final String[] g_arrBleNameSupport = {"MEIYING", "HEATZ", "POLYGOLD", "KR", "DN-G15", "G11", "K10", "EPIK", "SAMZQ", "M3", "M4", "M2", "M1", "M16", "M24", "NUOS", "MK12", "Shanling", "MY"};
    private static final String[] g_arrBleNameSupportNTS = {"NTS"};
    private static int m_nOldVirtualMouseX = -1;
    private static int m_nOldVirtualMouseY = -1;
    private static int m_nNewVirtualMouseX = -1;
    private static int m_nNewVirtualMouseY = -1;
    private static long s_nLastShowVirtualMouseTime = 0;
    private static boolean s_boLOLMoveIconChanged = false;
    private static boolean s_boLOLMoveVisible = false;
    private static long s_nLastShowLOLMoveIconTime = 0;
    private byte[] mSystemStatus = new byte[SYSTEM_STATUS_BYTES + 3];
    private SystemStatus m_sSysStatus = new SystemStatus();
    private final byte[] m_arrSendSystemStatus = new byte[SYSTEM_STATUS_BYTES + 4];
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    ImageReader mImageReader = null;
    private long m_nLastSendSystemStatusTime = 0;
    private ImageView mFloatWindowIcon = null;
    private boolean m_boAIModelInitFinished = false;
    private IThrone mThroneService = null;
    private boolean m_boNeverGetSystemStatus = true;
    public Map<String, AIFloatInfo> m_mapAIFloat = new HashMap();
    private boolean m_boAIEditing = false;
    private boolean m_boGunPartsAI = false;
    private boolean m_boAIDynamic = true;
    Matrix m_matrixAIPreview = new Matrix();
    private boolean m_boIOSReminded = false;
    private int m_nGunDownMax = 64;
    private boolean canSelect = true;
    private Button m_btnAIGunDownPercent = null;
    private boolean m_boShowAdjustButton = true;
    private boolean m_boDynamicGunPressTipReminded = false;
    boolean m_boAIStageEditing = false;
    TextView[] m_arrAIStageTitle = new TextView[6];
    SeekBar[] m_arrAIStageSeekbar = new SeekBar[6];
    Button[] m_arrAIStageBtnPlus = new Button[6];
    Button[] m_arrAIStageBtnMinus = new Button[6];
    TextView[] m_arrAIStageText = new TextView[6];
    private boolean m_boShowDynamicAdjustButton = true;
    TextView[] m_arrDynamicStageTitle = new TextView[6];
    SeekBar[] m_arrDynamicStageSeekbar = new SeekBar[6];
    Button[] m_arrDynamicStageBtnPlus = new Button[6];
    Button[] m_arrDynamicStageBtnMinus = new Button[6];
    TextView[] m_arrDynamicStageText = new TextView[6];
    boolean m_boInNTSMode = false;
    Qianliyan m_qianliyan = null;
    GameSettingFloat m_gameSettingFloat = null;
    AIScope6384Float m_scope6384 = null;
    private final Consumer<Long> halfFloatWindow = new Consumer<Long>() { // from class: com.baidu.kwgames.MainActivity.2
        @Override // io.reactivex.functions.Consumer
        public void accept(Long l) throws Exception {
            if (MainActivity.this.mTable.getVisibility() == 0) {
                MainActivity.this.mTable.setVisibility(8);
                MainActivity.this.mTable.postDelayed(new Runnable() { // from class: com.baidu.kwgames.MainActivity.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.halfIcon();
                    }
                }, 10L);
                return;
            }
            MainActivity.this.halfIcon();
        }
    };
    Runnable m_timerFunc = new Runnable() { // from class: com.baidu.kwgames.MainActivity.3
        @Override // java.lang.Runnable
        public void run() {
            if (AppInstance.mBleConnected || AppInstance.s_boUSBConnected) {
                return;
            }
            if (!UsbAOADevice.is_meiying_usb_connect(AppInstance.s_context)) {
                MainActivity.this.connectAfterSys();
                if (AppInstance.mac == null) {
                    MainActivity.this.m_timerHandler.postDelayed(this, 1000L);
                    return;
                }
                return;
            }
            MainActivity.this.try_connect_usb();
        }
    };
    private boolean m_boVirtualMouseOnOff = false;
    public Runnable m_runableCrosshairCheck = new Runnable() { // from class: com.baidu.kwgames.MainActivity.10
        @Override // java.lang.Runnable
        public void run() {
            MainActivity.this.m_boCrosshairChekTimerRunning = false;
            MainActivity.this.update_crosshair();
        }
    };
    private final IBleCallback mBleCallback = new AnonymousClass11();
    private int m_nInCar = -1;
    private int m_nAIOutCarTimes = 0;
    private int m_nInCarOld = -1;
    private String mBeijingAI = "";
    private String mGun1AI = "";
    private String mGun2AI = "";
    private int m_nCurGun1AI = -1;
    private int m_nCurGun2AI = -1;
    private int m_nCurBagTagAI = -1;
    private int m_nCurGun1Head = -1;
    private int m_nCurGun1Handle = -1;
    private int m_nCurGun1Tail = -1;
    private int m_nCurGun2Head = -1;
    private int m_nCurGun2Handle = -1;
    private int m_nCurGun2Tail = -1;
    private int m_nOldBagTagAI = -1;
    private int m_nOldGunHead = -1;
    private int m_nOldGunHandle = -1;
    private int m_nOldGunTail = -1;
    boolean m_boNeedUpdateMatrix = true;
    Matrix m_matrixScope = new Matrix();
    private int m_nScopeModelW = 76;
    private int m_nScopeModelH = 36;
    private float m_fScopeModeRateW2H = (76 * 1.0f) / 36;
    Matrix m_matrixGun = new Matrix();
    private int m_nGunModelW = 253;
    private int m_nGunModelH = 81;
    private int m_nGunAIW = 253;
    private int m_nGunAIH = 81;
    private float m_fGunModeRateW2H = (253 * 1.0f) / 81;
    Matrix m_matrixBagTag = new Matrix();
    private int m_nBagtagModelW = 53;
    private int m_nBagtagModelH = 64;
    private float m_fBagtagModelRateW2H = (53 * 1.0f) / 64;
    Matrix m_matrixTakeoff = new Matrix();
    private int m_nTakeoffModelW;
    private int m_nTakeoffModelH;
    private float m_fTakeoffModelRateW2H = (this.m_nTakeoffModelW * 1.0f) / this.m_nTakeoffModelH;
    Matrix m_matrixGunOtherParts = new Matrix();
    Matrix m_matrixGunHandle = new Matrix();
    private int m_nGunPartsModelWH = 92;
    private int m_nGunPartsHandleWH = 92;
    private int m_nGunHandleModelWH = 92;
    int m_nBluetoothClickTimes = 0;
    public Runnable m_runableSaveAIPartReduce = new AnonymousClass17();
    public Runnable m_runableSaveAIStage = new AnonymousClass18();
    int m_nCurEditAIStageM4 = 0;
    int m_nCurEditAIStageMaxIndex = 0;
    boolean m_boRemindUpdateFirmware = false;
    private int m_nOldKeyMap = -1;
    boolean m_boDevBanned = false;
    int m_nPressLongTimes = 0;
    private Handler m_hideAICrossHairHandler = new Handler();
    public Runnable m_runableHideAICrosshair = new Runnable() { // from class: com.baidu.kwgames.MainActivity.33
        @Override // java.lang.Runnable
        public void run() {
            if (MainActivity.this.mResumed) {
                FloatMgr.hide_aim_float();
            }
        }
    };
    private boolean m_boAICrossHairOnOff = false;
    Boolean m_boAIXScope = false;
    Boolean m_bo1stBulletOptimize = true;
    Boolean m_boAutoActiveRun = false;
    SmartQE m_smartQE = null;
    SeekBar m_ctlAIPeriodSlider = null;
    TextView m_textAIPeriod = null;
    View m_aiPeriodLayer = null;
    private Handler m_hMsgBoxHandler = new Handler() { // from class: com.baidu.kwgames.MainActivity.52
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int BYTE2INT;
            int i;
            super.handleMessage(message);
            if (message.arg1 != R.string.keymap_prepared) {
                return;
            }
            if (1 == message.arg2) {
                try {
                    KeyMaps.SModelItem sModelItem = KeyMaps.get_model_item();
                    if (sModelItem != null && sModelItem.nKeyMapCount > 0 && sModelItem.arrKeyMapData[0] != null && sModelItem.arrKeyMapData[0][0] == 54 && sModelItem.arrKeyMapData[0][1] == 90 && (BYTE2INT = Units.BYTE2INT(sModelItem.arrKeyMapData[0][2], sModelItem.arrKeyMapData[0][3])) > 0 && (i = BYTE2INT * 14) <= sModelItem.arrKeyMapData[0].length - 6) {
                        Context context = AppInstance.s_context;
                        NEAT.write_file(context, "/keyConfig/" + sModelItem.arrKeyMapName[0], sModelItem.arrKeyMapData[0]);
                        byte[] bArr = new byte[i];
                        System.arraycopy(sModelItem.arrKeyMapData[0], 6, bArr, 0, i);
                        MainActivity.this.mThroneService.bleSendKeyMap(bArr, 0);
                    }
                    MainActivity.m_ini.put(Constants.CFG_KEYMAP_LOADED, 1);
                    Toast.makeText(MainActivity.this.getApplicationContext(), MainActivity.this.getApplicationContext().getString(R.string.action_success), 1).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (2 == message.arg2) {
                MainActivity.m_ini.put(Constants.CFG_KEYMAP_LOADED, 1);
            }
        }
    };
    private ADB m_adb = null;
    private boolean m_boMTKActived = false;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.baidu.kwgames.MainActivity.54
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("onReceiveAc", action);
            if ("ai.iomega.CAPTURE".equals(action)) {
                MainActivity.this.timingLogger.dumpToLog();
                MainActivity.this.timingLogger.reset();
            } else if ("android.intent.action.CONFIGURATION_CHANGED".equals(action)) {
            } else {
                if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action)) {
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                    if (AppInstance.s_usb == null || !usbDevice.getDeviceName().equals(AppInstance.s_usb.get_dev_name())) {
                        return;
                    }
                    try {
                        MainActivity.this.mThroneService.usbDisconnect();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action)) {
                    if (!UsbHidDevice.is_kuwee_device((UsbDevice) intent.getParcelableExtra("device")) || AppInstance.has_device_connect()) {
                        return;
                    }
                    MainActivity.this.try_connect_usb();
                } else if ("android.hardware.usb.action.USB_ACCESSORY_ATTACHED".equals(action)) {
                    if (!UsbAOADevice.is_kuwee_device((UsbAccessory) intent.getParcelableExtra("accessory")) || AppInstance.has_device_connect()) {
                        return;
                    }
                    MainActivity.this.try_connect_usb();
                } else if ("android.hardware.usb.action.USB_ACCESSORY_DETACHED".equals(action)) {
                    UsbAccessory usbAccessory = (UsbAccessory) intent.getParcelableExtra("accessory");
                    if (AppInstance.s_usb == null || !usbAccessory.getModel().equals(AppInstance.s_usb.get_dev_name())) {
                        return;
                    }
                    try {
                        MainActivity.this.mThroneService.usbDisconnect();
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    };
    private String[] m_arrMarqueeText = null;
    private int m_nCurMarqueeText = 0;
    private boolean m_boMarqueeTextChangeTimerRunning = false;
    public Handler m_timerMarqueeTextChange = new Handler();
    public Runnable m_runableMarqueeTextChange = new Runnable() { // from class: com.baidu.kwgames.MainActivity.59
        @Override // java.lang.Runnable
        public void run() {
            if (MainActivity.this.m_boMarqueeTextChangeTimerRunning) {
                MainActivity.this.show_next_marquee_text();
                MainActivity.this.m_timerMarqueeTextChange.postDelayed(MainActivity.this.m_runableMarqueeTextChange, 60000L);
            }
        }
    };
    public Handler m_timerHandler = new Handler();
    public Runnable m_runableSaveAgile = new AnonymousClass60();
    public Runnable m_runableHideAIResultCtrls = new Runnable() { // from class: com.baidu.kwgames.MainActivity$$ExternalSyntheticLambda7
        @Override // java.lang.Runnable
        public final void run() {
            MainActivity.this.m47lambda$new$0$combaidukwgamesMainActivity();
        }
    };
    int m_nAIFloatCrosshairStyle = -1;
    boolean m_boAIFloatCrosshairButtonBGChanged = false;
    int m_nAICrosshairFloatX = 50;
    int m_nAICrosshairFloatY = 0;
    private boolean m_boNeedAICommit = false;
    private final ServiceConnection conn = new ServiceConnection() { // from class: com.baidu.kwgames.MainActivity.99
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            try {
                MainActivity.this.mThroneService.removeBleCallback(MainActivity.this.mBleCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            MainActivity.this.mThroneService = null;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MainActivity.this.mThroneService = IThrone.Stub.asInterface(iBinder);
            try {
                MainActivity.this.mThroneService.addBleCallback(MainActivity.this.mBleCallback);
                if (!UsbHidDevice.is_meiying_usb_connect(AppInstance.s_context) && !UsbAOADevice.is_meiying_usb_connect(AppInstance.s_context)) {
                    MainActivity.this.set_ble_connect_timer(true);
                }
                MainActivity.this.try_connect_usb();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
    Bitmap m_bmpPreview = null;
    Runnable m_runableShowAdjustPreview = new Runnable() { // from class: com.baidu.kwgames.MainActivity.102
        @Override // java.lang.Runnable
        public void run() {
            if (MainActivity.this.m_bmpPreview != null) {
                MainActivity.this.m_imageAdjustPreview.setImageBitmap(MainActivity.this.m_bmpPreview);
            }
        }
    };
    private final int E_FRAME_R = 3145728;
    private final int E_FRAME_G = 12288;
    private final int E_FRAME_B = 48;
    int[][] m_arrGunPartFrameX = (int[][]) Array.newInstance(int.class, 3, 4);
    byte m_byOldGun = -1;
    byte m_byOldPlayerState = -1;
    byte m_byOldScopeState = -1;
    int m_nCurScopeAiID = -1;
    int m_nCurScopeAIChangedTimes = 0;
    int m_nOldScopeAiID = -1;
    int m_nCurGes = 0;
    boolean m_boForceUpdateAIResult = false;
    byte m_byLastBleGun = -1;
    int m_nLastBleGunDown = -1;
    byte m_nOldBleGunPartsReduce = 0;
    long m_lFirstTimeNoGun = 0;
    byte[] m_arrAIGunStageForSend = new byte[8];
    byte[] m_arrAIGunStageOffForSend = {Constants.KEY_102ND, Constants.KEY_102ND, Constants.KEY_102ND, Constants.KEY_102ND, Constants.KEY_102ND, Constants.KEY_102ND, 0, 0};
    byte[] m_arrM4AIGunStageForSend = new byte[12];
    byte[] m_arrM4AIGunStageTimeForSend = new byte[12];
    byte[] m_arrM4AIGunStageOffForSend = {Constants.KEY_102ND, Constants.KEY_102ND, Constants.KEY_102ND, Constants.KEY_102ND, Constants.KEY_102ND, Constants.KEY_102ND, 0, 0, 0, 0, 0, 0};
    byte[] m_arrM4AIGunStageOffTimeForSend = {Constants.KEY_102ND, Constants.KEY_102ND, Constants.KEY_102ND, Constants.KEY_102ND, Constants.KEY_102ND, Constants.KEY_102ND, 0, 0, 0, 0, 0, 0};
    long m_lAICrossUpdateUntil = 0;
    public Runnable m_runableHideDynamicCtrls = new Runnable() { // from class: com.baidu.kwgames.MainActivity$$ExternalSyntheticLambda8
        @Override // java.lang.Runnable
        public final void run() {
            MainActivity.this.m48lambda$new$5$combaidukwgamesMainActivity();
        }
    };
    boolean m_boShowCrosshairTip = false;
    public Runnable m_runableSaveDynamicGunStage = new Runnable() { // from class: com.baidu.kwgames.MainActivity$$ExternalSyntheticLambda9
        @Override // java.lang.Runnable
        public final void run() {
            MainActivity.lambda$new$6();
        }
    };
    int[] m_arrAIFireSense = {160, 130, 100, 100, 100, 100};
    Button[] m_arrAIFireSensePlus = new Button[6];
    Button[] m_arrAIFireSenseMinus = new Button[6];
    TextView[] m_arrAIFireSenseText = new TextView[6];
    int m_nCurBG = 0;

    public native int CreateBagTagModel(Bitmap bitmap, int i);

    public native int CreateGunHandleModel(Bitmap bitmap, int i);

    public native int CreateGunHeadModel(Bitmap bitmap, int i);

    public native int CreateGunModel(Bitmap bitmap, int i);

    public native int CreateGunTailModel(Bitmap bitmap, int i);

    public native int CreateScopeModel(Bitmap bitmap, int i);

    public native int CreateTakeOffModel(Bitmap bitmap, int i);

    public native float algProcess(Bitmap bitmap);

    public native int bagTagAI(Bitmap bitmap);

    public native float centerCompare(Bitmap bitmap, int i, int i2, int i3, String str);

    public native float compareHist(Bitmap bitmap, Bitmap bitmap2, String str);

    public native int gunAI(Bitmap bitmap, int i);

    public native int gunAIwlzy(Bitmap bitmap, int i);

    public native int gunHandleAI(Bitmap bitmap, int i);

    public native int gunHeadAI(Bitmap bitmap, int i);

    public native int gunTailAI(Bitmap bitmap, int i);

    public boolean is_true(byte b, int i) {
        return (b & (1 << i)) != 0;
    }

    public native int scopeAI(Bitmap bitmap, int i);

    public native int takeOffAI(Bitmap bitmap);

    static /* synthetic */ int access$10008(MainActivity mainActivity) {
        int i = mainActivity.m_nAIGunDownPercent;
        mainActivity.m_nAIGunDownPercent = i + 1;
        return i;
    }

    static /* synthetic */ int access$10010(MainActivity mainActivity) {
        int i = mainActivity.m_nAIGunDownPercent;
        mainActivity.m_nAIGunDownPercent = i - 1;
        return i;
    }

    static /* synthetic */ int access$2208(MainActivity mainActivity) {
        int i = mainActivity.mGunValue1;
        mainActivity.mGunValue1 = i + 1;
        return i;
    }

    static /* synthetic */ int access$2210(MainActivity mainActivity) {
        int i = mainActivity.mGunValue1;
        mainActivity.mGunValue1 = i - 1;
        return i;
    }

    static /* synthetic */ int access$2308(MainActivity mainActivity) {
        int i = mainActivity.mGunValue2;
        mainActivity.mGunValue2 = i + 1;
        return i;
    }

    static /* synthetic */ int access$2310(MainActivity mainActivity) {
        int i = mainActivity.mGunValue2;
        mainActivity.mGunValue2 = i - 1;
        return i;
    }

    static {
        System.loadLibrary("recognition");
        SYSTEM_STATUS_BYTES = 27;
        s_vInfo = null;
        m_ini = SPUtils.getInstance();
        s_arrShowGuide = new boolean[8];
        m_nAIPeriod = 100;
    }

    private void stopCaptureThread() {
        Thread thread = this.mCaptureThread;
        if (thread == null || !thread.isAlive()) {
            return;
        }
        this.mCaptureThread.interrupt();
    }

    private void startCaptureThread() {
        Thread thread = this.mCaptureThread;
        if (thread == null || !thread.isAlive()) {
            if (this.m_qianliyan == null) {
                this.m_qianliyan = new Qianliyan(this, this.mHandler);
            }
            if (Build.VERSION.SDK_INT >= 19) {
                Thread thread2 = this.mCaptureThread;
                if (thread2 != null && thread2.isAlive()) {
                    this.mCaptureThread.interrupt();
                }
                Thread thread3 = new Thread(new Runnable() { // from class: com.baidu.kwgames.MainActivity.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.m_boNeedUpdateMatrix = true;
                        MainActivity.this.capture();
                    }
                }, "capture");
                this.mCaptureThread = thread3;
                thread3.setPriority(5);
                this.mCaptureThread.start();
            }
        }
    }

    void set_ble_connect_timer(boolean z) {
        this.m_timerHandler.removeCallbacks(this.m_timerFunc);
        if (z) {
            if (this.m_boInNTSMode) {
                ble_start_scan();
            } else {
                this.m_timerHandler.postDelayed(this.m_timerFunc, 0L);
            }
        }
    }

    void on_device_disconnected() {
        this.mConnectState.setText((getString(R.string.throne_not_connected) + "\n") + getString(R.string.app_version_text) + AppInstance.s_strAppVer);
        this.mConnectState.setTextColor(-65536);
        int i = 0;
        while (true) {
            byte[] bArr = this.mSystemStatus;
            if (i >= bArr.length) {
                this.m_sSysStatus.set_data(bArr);
                stopVirtualMouseUpdateTimer();
                FloatMgr.hideVirtualMouse();
                set_AI_controls_visible(false);
                this.m_boNeverGetSystemStatus = true;
                this.m_nOldKeyMap = -1;
                this.m_btnTouchSenseYPercent.setVisibility(8);
                this.m_btnID.setVisibility(8);
                return;
            }
            bArr[i] = 0;
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void on_ble_disconnected() {
        AppInstance.mac = null;
        AppInstance.mBleConnected = false;
        on_device_disconnected();
    }

    protected void update_gun_press_ui() {
        boolean z;
        if (this.m_nGunDownMax != this.m_sSysStatus.get_gun_down_max()) {
            this.m_nGunDownMax = this.m_sSysStatus.get_gun_down_max();
            z = true;
        } else {
            z = false;
        }
        if (this.m_boSupportDynamicGunPress != this.m_sSysStatus.has_dynamic_gun_down_capacity()) {
            boolean has_dynamic_gun_down_capacity = this.m_sSysStatus.has_dynamic_gun_down_capacity();
            this.m_boSupportDynamicGunPress = has_dynamic_gun_down_capacity;
            this.m_btnDynamicGun.setVisibility(has_dynamic_gun_down_capacity ? 0 : 8);
        }
        if (this.mGun1Sensitivity.getProgress() != this.mSystemStatus[8]) {
            z = true;
        }
        if (this.mGun2Sensitivity.getProgress() == this.mSystemStatus[9] ? z : true) {
            this.mGun1Sensitivity.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity.4
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity.this.mGun1Sensitivity.setMax(MainActivity.this.m_nGunDownMax);
                    MainActivity.this.mGun1Sensitivity.setProgress(MainActivity.this.mSystemStatus[8]);
                }
            });
            this.mGun2Sensitivity.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity.this.mGun2Sensitivity.setMax(MainActivity.this.m_nGunDownMax);
                    MainActivity.this.mGun2Sensitivity.setProgress(MainActivity.this.mSystemStatus[9]);
                }
            });
        }
    }

    protected void update_mouse_move_ui() {
        this.mMouseMoveSensitivity.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity.6
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.mMouseMoveSensitivity.setProgress(MainActivity.this.mSystemStatus[6] - 10);
                Button button = MainActivity.this.m_btnTouchSenseYPercent;
                button.setText("Y=" + MainActivity.this.m_sSysStatus.nTouchSensYRatio + "%");
            }
        });
    }

    protected void update_mouse_cursor_ui() {
        this.mMousePointerSensitivity.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity.7
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.mMousePointerSensitivity.setProgress(MainActivity.this.mSystemStatus[7] - 10);
            }
        });
    }

    protected void check_cast_mode_remind() {
        String upperCase = Units.getSystemModel().toUpperCase();
        if (Constants.is_touping_phones(upperCase) && !this.m_sSysStatus.is_in_touping_mode()) {
            MsgBox.msg_box_with_never_remind_once(R.string.switch_cast_mode, "switch_cast_mode");
        }
        if (Constants.is_oppo_immersion_need_remind(upperCase)) {
            MsgBox.msg_box_with_never_remind_once(R.string.oppo_immersion_mode_remind, "oppo_immersion_mode_remind");
        }
    }

    protected void set_all_AI_relate_controls_visible(boolean z) {
        int i = 0;
        int i2 = z ? 0 : 8;
        this.m_btnAIQuestion.setVisibility(i2);
        this.m_btnAIDynamicOnOff.setVisibility((z && this.m_sSysStatus.has_w_gun_down_capacity()) ? 0 : 8);
        this.m_btnAIContinuesShootOnOff.setVisibility((z && this.m_sSysStatus.has_APP_AI_continues_shoot_capacity()) ? 0 : 8);
        this.m_btnAICrossHair.setVisibility(i2);
        this.m_btnAIDynamicOnOff.setVisibility(i2);
        this.m_btnAIXScope.setVisibility((z && this.m_sSysStatus.has_x_scope_capacity()) ? 0 : 8);
        this.m_btnFireSense.setVisibility((z && this.m_sSysStatus.has_AI_mouse_sense_capacity()) ? 0 : 8);
        this.m_btn1stBulletOptimize.setVisibility((z && this.m_sSysStatus.has_1st_gun_optimize_capacity()) ? 0 : 8);
        this.m_btnAutoActiveRun.setVisibility((z && this.m_sSysStatus.has_remove_frozen_capacity()) ? 0 : 8);
        this.m_btnQianliyan.setVisibility((z && this.m_sSysStatus.has_qianliyan_capacity()) ? 0 : 8);
        this.m_btnTPPFPP.setVisibility((z && this.m_sSysStatus.has_smart_tppfpp_capacity()) ? 0 : 8);
        this.m_btnSmartQE.setVisibility((z && this.m_sSysStatus.has_smart_QE_capacity()) ? 8 : 8);
    }

    protected void set_AI_controls_visible(boolean z) {
        this.mAI.setVisibility(z ? 0 : 8);
        this.m_txtAITitle.setVisibility(z ? 0 : 8);
        String[] stringArray = getResources().getStringArray(R.array.aikits);
        TextView textView = this.m_txtAITitle;
        textView.setText(NEAT.s(this, R.string.ai) + "(" + stringArray[AppInstance.get_cur_AI_kit()] + ")");
        if (!z) {
            set_all_AI_relate_controls_visible(z);
        } else if (this.mIsRecording && 8 == this.m_btnAIQuestion.getVisibility()) {
            set_all_AI_relate_controls_visible(true);
        }
    }

    protected String get_touping_res() {
        return "(" + AppInstance.s_nToupingDisplayX + "X" + AppInstance.s_nToupingDisplayY + ")";
    }

    public void ble_start_scan() {
        if (ble_is_scanning()) {
            return;
        }
        if (App.getRxBleClient(this).isScanRuntimePermissionGranted()) {
            scanBleDevices();
        } else if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(App.getRxBleClient(this).getRecommendedScanRuntimePermissions(), 6);
        }
    }

    private void scanBleDevices() {
        if (!NEAT.is_bluetooth_open()) {
            MsgBox.msg_box1(this, NEAT.s(R.string.pls_open_bt));
            return;
        }
        if (!NEAT.is_gps_open()) {
            MsgBox.msg_box1_once(R.string.pls_open_gps);
        }
        ScanSettings build = new ScanSettings.Builder().setScanMode(2).build();
        ScanFilter build2 = new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString("00001812-0000-1000-8000-00805f9b34fb")).build();
        this.mConnectState.setText(NEAT.s(R.string.dev_scanning));
        this.scanDisposable = App.getRxBleClient(this).scanBleDevices(build, build2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).filter(new Predicate<ScanResult>() { // from class: com.baidu.kwgames.MainActivity.8
            @Override // io.reactivex.functions.Predicate
            public boolean test(ScanResult scanResult) throws Exception {
                String name = scanResult.getBleDevice().getName();
                if (name == null) {
                    return false;
                }
                for (String str : MainActivity.g_arrBleNameSupportNTS) {
                    if (name.contains(str) && MainActivity.this.ble_is_scanning()) {
                        return true;
                    }
                }
                return false;
            }
        }).throttleFirst(1L, TimeUnit.SECONDS).doFinally(new Action() { // from class: com.baidu.kwgames.MainActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Action
            public final void run() {
                MainActivity.this.cancelScan();
            }
        }).subscribe(new Consumer() { // from class: com.baidu.kwgames.MainActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainActivity.this.prepareConnect((ScanResult) obj);
            }
        }, new Consumer() { // from class: com.baidu.kwgames.MainActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainActivity.this.onScanFailure((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareConnect(ScanResult scanResult) {
        cancelScan();
        connectBle(scanResult.getBleDevice().getMacAddress(), scanResult.getBleDevice().getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScanFailure(Throwable th) {
        this.mConnectState.setText(NEAT.s(R.string.dev_scan_failed));
        if (th instanceof BleScanException) {
            ScanExceptionHandler.handleException(this, (BleScanException) th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelScan() {
        this.scanDisposable.dispose();
        this.scanDisposable = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean ble_is_scanning() {
        return this.scanDisposable != null;
    }

    protected void update_connect_status_text() {
        this.mConnectState.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity.9
            @Override // java.lang.Runnable
            public void run() {
                MainActivity mainActivity;
                int i;
                String str;
                String str2;
                MainActivity mainActivity2;
                int i2;
                String str3;
                String str4;
                Constants.SModelStr sModelStr;
                if (MainActivity.this.mResumed) {
                    Log.d(MainActivity.TAG, "set in Main UI ******");
                    MainActivity.this.set_in_main_UI(true);
                }
                KeyMaps.SModelItem sModelItem = KeyMaps.get_model_item();
                if (AppInstance.s_boUSBConnected) {
                    mainActivity = MainActivity.this;
                    i = R.string.dev_usb_connected;
                } else {
                    mainActivity = MainActivity.this;
                    i = R.string.throne_connected;
                }
                String string = mainActivity.getString(i);
                try {
                    if (Constants.m_mapModelStr.get(Integer.valueOf(MainActivity.this.m_sSysStatus.uSystemModel)) != null) {
                        if (MainActivity.this.m_sSysStatus.is_in_moba_mode()) {
                            string = string + sModelStr.m_strMOBA;
                        } else if (MainActivity.this.m_sSysStatus.is_in_minecraft_mode()) {
                            string = string + sModelStr.m_strMinecraft;
                        } else {
                            string = string + sModelStr.m_strNormal;
                        }
                    }
                } catch (Exception unused) {
                }
                ModelInfo modelInfo = Constants.get_model_info(MainActivity.this.m_sSysStatus.uSystemModel);
                if (modelInfo != null && modelInfo.m_boSupportAIGunDown) {
                    MainActivity.this.set_AI_controls_visible(true);
                }
                String str5 = (string + "\n") + MainActivity.this.getString(R.string.throne_version);
                MainActivity.this.m_adb.set_MTK_mode(MainActivity.this.m_sSysStatus.is_in_MTK_mode() || MainActivity.this.m_sSysStatus.is_in_MTK_USB_mode());
                MainActivity mainActivity3 = MainActivity.this;
                boolean is_true = mainActivity3.is_true(mainActivity3.mSystemStatus[10], 0);
                if (!is_true) {
                    if (MainActivity.this.m_sSysStatus.is_in_MTK_mode()) {
                        if (MainActivity.this.m_boMTKActived) {
                            str = str5 + MainActivity.this.getString(R.string.mtkactived);
                        } else {
                            str = str5 + MainActivity.this.getString(R.string.mtkinactived);
                        }
                        if (sModelItem != null && !sModelItem.boNotchScreen) {
                            MsgBox.msg_box_with_never_remind_once(R.string.NOT_MTK_PHONE_IN_MTK_MODE, "NOT_MTK_PHONE_IN_MTK_MODE");
                        }
                    } else if (MainActivity.this.m_sSysStatus.is_in_usb_mode()) {
                        if (Units.is_oppo_vivo().booleanValue()) {
                            MsgBox.msg_box_with_never_remind_once(R.string.OTG_MODE_REMIND, "OTG_MODE_REMIND");
                        }
                        if (MainActivity.this.m_sSysStatus.is_in_MTK_USB_mode()) {
                            MainActivity mainActivity4 = MainActivity.this;
                            if (mainActivity4.is_true(mainActivity4.mSystemStatus[5], 6)) {
                                str2 = str5 + MainActivity.this.getString(R.string.usb_active_cast_mode);
                                if (AppInstance.s_nToupingDisplayX != 0) {
                                    str2 = str2 + MainActivity.this.get_touping_res();
                                }
                            } else {
                                str2 = str5 + MainActivity.this.getString(R.string.usb_mtk_mode);
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append(str2);
                            if (MainActivity.this.m_boMTKActived) {
                                mainActivity2 = MainActivity.this;
                                i2 = R.string.activated;
                            } else {
                                mainActivity2 = MainActivity.this;
                                i2 = R.string.inactivated;
                            }
                            sb.append(mainActivity2.getString(i2));
                            str = sb.toString();
                        } else {
                            MainActivity mainActivity5 = MainActivity.this;
                            if (mainActivity5.is_true(mainActivity5.mSystemStatus[5], 6)) {
                                str = str5 + MainActivity.this.getString(R.string.wired_projection);
                                if (AppInstance.s_nToupingDisplayX != 0) {
                                    str = str + MainActivity.this.get_touping_res();
                                }
                            } else {
                                str = str5 + MainActivity.this.getString(R.string.android_cable);
                            }
                        }
                        if (MainActivity.this.m_sSysStatus.is_in_NTS_mode()) {
                            str = str + "NTS";
                        }
                        if (MainActivity.this.m_sSysStatus.is_usb_mode_hard_acc()) {
                            str = str + "+";
                        }
                    } else {
                        MainActivity mainActivity6 = MainActivity.this;
                        if (!mainActivity6.is_true(mainActivity6.mSystemStatus[5], 6)) {
                            if (MainActivity.this.m_sSysStatus.is_in_x1_mode()) {
                                str = str5 + MainActivity.this.getString(R.string.x1_mode);
                                MsgBox.msg_box_with_never_remind_once(R.string.x1_mode_info, "x1_mode_info");
                            } else {
                                str = str5 + MainActivity.this.getString(R.string.android_text);
                            }
                        } else {
                            str = str5 + MainActivity.this.getString(R.string.android_projection);
                            if (AppInstance.s_nToupingDisplayX != 0) {
                                str = str + MainActivity.this.get_touping_res();
                            }
                        }
                    }
                    MainActivity.this.check_cast_mode_remind();
                } else {
                    str = str5 + "iOS";
                    if (!MainActivity.this.m_boIOSReminded) {
                        MainActivity.this.m_boIOSReminded = true;
                        MsgBox.msg_box1((int) R.string.iosVerRemind);
                    }
                }
                if ((MainActivity.this.mSystemStatus[4] & 255) < 250) {
                    str3 = str + " V" + ((int) MainActivity.this.mSystemStatus[4]);
                } else {
                    String str6 = str + " ";
                    switch (MainActivity.this.mSystemStatus[4] & 255) {
                        case ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION /* 250 */:
                            str3 = str6 + MainActivity.this.getString(R.string.my_world_edition);
                            break;
                        case 251:
                            str3 = str6 + MainActivity.this.getString(R.string.after_tomorrow);
                            break;
                        case 252:
                            str3 = str6 + MainActivity.this.getString(R.string.cross_the_line_of_fire);
                            break;
                        case 253:
                            str3 = str6 + "MOBA";
                            break;
                        default:
                            str3 = str6 + "" + ((int) MainActivity.this.mSystemStatus[4]);
                            break;
                    }
                }
                String str7 = ((str3 + "\n") + MainActivity.this.getString(R.string.app_version_text) + AppInstance.s_strAppVer + "\n") + MainActivity.this.getString(R.string.mobile_version) + Units.getSystemModel();
                if (sModelItem != null && sModelItem.boNotchScreen) {
                    str4 = str7 + MainActivity.this.getString(R.string.MTK) + "\n";
                } else {
                    str4 = str7 + "\n";
                }
                MainActivity mainActivity7 = MainActivity.this;
                if (!mainActivity7.is_true(mainActivity7.mSystemStatus[10], 3)) {
                    str4 = str4 + MainActivity.this.getString(R.string.wasd_close);
                }
                MainActivity mainActivity8 = MainActivity.this;
                if (!mainActivity8.is_true(mainActivity8.mSystemStatus[10], 4)) {
                    str4 = str4 + MainActivity.this.getString(R.string.pressure_gun_close);
                }
                MainActivity mainActivity9 = MainActivity.this;
                if (mainActivity9.is_true(mainActivity9.mSystemStatus[10], 5)) {
                    if (MainActivity.this.m_sSysStatus.is_virtual_mouse_center()) {
                        str4 = str4 + MainActivity.this.getString(R.string.appear_in_center);
                    } else {
                        str4 = str4 + MainActivity.this.getString(R.string.virtual_pointer_open);
                    }
                } else if (MainActivity.this.m_boVirtualMouseOnOff) {
                    MainActivity.this.show_virtual_mouse(false, -1, -1);
                }
                MainActivity mainActivity10 = MainActivity.this;
                if (mainActivity10.is_true(mainActivity10.mSystemStatus[5], 3)) {
                    str4 = str4 + MainActivity.this.getString(R.string.long_press_mirror_open);
                }
                byte b = (byte) ((MainActivity.this.mSystemStatus[5] >> 4) & 3);
                if (1 == b) {
                    str4 = str4 + MainActivity.this.getString(R.string.qe_mirror_open);
                } else if (2 == b) {
                    str4 = str4 + MainActivity.this.getString(R.string.qe_long_open);
                }
                if (!is_true) {
                    MainActivity mainActivity11 = MainActivity.this;
                    if (mainActivity11.is_true(mainActivity11.mSystemStatus[10], 2)) {
                        str4 = str4 + MainActivity.this.getString(R.string.x9_r9_open);
                    }
                }
                if (modelInfo.m_boSupportUAC && MainActivity.this.m_sSysStatus.is_usb_audio_off()) {
                    str4 = str4 + MainActivity.this.getString(R.string.UAC_OFF);
                }
                MainActivity.this.mConnectState.setText(str4);
                if (ImageSwButton.get_onoff(MainActivity.this.m_btnAIContinuesShootOnOff) != MainActivity.this.m_sSysStatus.is_AI_continues_shoot()) {
                    ImageSwButton.set_onoff(MainActivity.this.m_btnAIContinuesShootOnOff, MainActivity.this.m_sSysStatus.is_AI_continues_shoot());
                    MainActivity.this.m_boForceUpdateAIResult = true;
                }
                if (ImageSwButton.get_onoff(MainActivity.this.m_btnSmartQE) != MainActivity.this.m_sSysStatus.is_smart_QE_on()) {
                    ImageSwButton.set_onoff(MainActivity.this.m_btnSmartQE, MainActivity.this.m_sSysStatus.is_smart_QE_on());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void update_system_status_ui() {
        update_gun_press_ui();
        update_mouse_move_ui();
        update_mouse_cursor_ui();
        update_connect_status_text();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void show_virtual_mouse(boolean z, int i, int i2) {
        this.m_boVirtualMouseOnOff = z;
        if (z) {
            FloatMgr.showVirtualMouse(i, i2);
            startMousePositionTicket();
            return;
        }
        FloatMgr.hideVirtualMouse();
        stopVirtualMouseUpdateTimer();
    }

    public void begin_crosshair_check_timer() {
        this.m_boCrosshairChekTimerRunning = true;
        this.m_timerHandler.removeCallbacks(this.m_runableCrosshairCheck);
        this.m_timerHandler.postDelayed(this.m_runableCrosshairCheck, 800L);
    }

    public void stop_crosshair_check_timer() {
        if (this.m_boCrosshairChekTimerRunning) {
            this.m_boCrosshairChekTimerRunning = false;
            this.m_timerHandler.removeCallbacks(this.m_runableCrosshairCheck);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.MainActivity$11  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass11 implements IBleCallback {
        boolean m_boVMouseOnOff;
        int m_nVMouseType;
        int m_nVMouseX = -1;
        int m_nVMouseY = -1;

        AnonymousClass11() {
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void onDeviceConnectStateChanged(boolean z) {
            if (AppInstance.s_boUSBConnected) {
                MainActivity.this.setSystemStatusTask(true);
                MainActivity.this.set_ble_connect_timer(false);
                MainActivity.this.mConnectState.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity$11$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.AnonymousClass11.this.m72x29663ee9();
                    }
                });
            } else if (AppInstance.mBleConnected) {
            } else {
                MainActivity.this.mConnectState.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity.11.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.on_device_disconnected();
                        MainActivity.this.set_ble_connect_timer(true);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$onDeviceConnectStateChanged$0$com-baidu-kwgames-MainActivity$11  reason: not valid java name */
        public /* synthetic */ void m72x29663ee9() {
            MainActivity.this.mConnectState.setTextColor(-16711936);
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void bleConnectState(int i) {
            if (i == 1) {
                AppInstance.mBleConnected = true;
                MainActivity.this.setSystemStatusTask(true);
                MainActivity.this.mConnectState.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity.11.2
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.mConnectState.setTextColor(-16711936);
                    }
                });
            } else if (i == 3 && AppInstance.mBleConnected) {
                AppInstance.mBleConnected = false;
                if (MainActivity.this.mThroneService != null) {
                    try {
                        MainActivity.this.mThroneService.bleDisConnect();
                        MainActivity.this.try_connect_usb();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                MainActivity.this.on_ble_disconnected();
            }
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void bleSystemStatus(byte[] bArr, int i) throws RemoteException {
            boolean z;
            if (AppInstance.has_device_connect() && System.currentTimeMillis() - MainActivity.this.m_nLastSendSystemStatusTime >= 1500) {
                if (MainActivity.this.mSystemStatus.length < i) {
                    MainActivity.this.mSystemStatus = new byte[i];
                }
                int i2 = 0;
                while (true) {
                    if (i2 >= i) {
                        z = true;
                        break;
                    } else if (MainActivity.this.mSystemStatus[i2] != bArr[i2]) {
                        z = false;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (z) {
                    MainActivity.this.m_sSysStatus.set_data(MainActivity.this.mSystemStatus);
                    return;
                }
                System.arraycopy(bArr, 0, MainActivity.this.mSystemStatus, 0, i);
                MainActivity.this.m_sSysStatus.set_data(MainActivity.this.mSystemStatus);
                MainActivity.this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity$11$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.AnonymousClass11.this.m70lambda$bleSystemStatus$1$combaidukwgamesMainActivity$11();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$bleSystemStatus$1$com-baidu-kwgames-MainActivity$11  reason: not valid java name */
        public /* synthetic */ void m70lambda$bleSystemStatus$1$combaidukwgamesMainActivity$11() {
            MainActivity.this.update_system_status_ui();
            if (MainActivity.this.m_boNeverGetSystemStatus) {
                MainActivity.this.m_boNeverGetSystemStatus = false;
                if (MainActivity.this.m_boDynamicGunPressOnOff) {
                    MainActivity.this.bleSendDynamicGunPressData();
                }
                if (MainActivity.this.mIsRecording) {
                    MainActivity.this.send_AI_param();
                }
                if (AppInstance.s_boUSBConnected) {
                    MsgBox.msg_box_with_never_remind_once(R.string.pure_usb_ble_off_remind, "pure_usb_ble_off_remind");
                }
                if (Units.getSystemModel().toLowerCase().contains("shark")) {
                    MsgBox.msg_box_with_never_remind_once(R.string.shark_remind, "shark_remind");
                }
                if (AIConvert.is_huawei_whole_screen(Units.getSystemModel())) {
                    MsgBox.msg_box_with_never_remind_once(R.string.huawei_whole_screen_tip, "huawei_whole_screen_tip");
                }
                KeyMaps.SModelItem sModelItem = KeyMaps.get_model_item();
                if (sModelItem != null && sModelItem.nKeyMapCount != 0 && !MsgBox.is_reminded(R.string.keymap_prepared, "keymap_prepared") && SPUtils.getInstance().getInt(Constants.CFG_KEYMAP_LOADED, 0) == 0) {
                    MsgBox.msg_box_with_never_remind_once_yesno(R.string.keymap_prepared, "keymap_prepared", MainActivity.this.m_hMsgBoxHandler);
                }
                if (MainActivity.this.m_sSysStatus.is_in_touping_mode()) {
                    MainActivity.this.bleGetToupingParam();
                }
                if (MainActivity.this.m_sSysStatus.has_Y_sense_adjust_capacity()) {
                    MainActivity.this.m_btnTouchSenseYPercent.setVisibility(0);
                }
                if (MainActivity.this.m_sSysStatus.has_ban_ID_capacity()) {
                    MainActivity.this.bleGetUUID();
                }
                if (MainActivity.this.m_sSysStatus.is_in_moba_mode()) {
                    FloatMgr.init_lol_vmouse();
                }
                MainActivity mainActivity = MainActivity.this;
                mainActivity.m_boSupportM4Dynamic = Boolean.valueOf(mainActivity.m_sSysStatus.has_M4_AI_dynamic_capacity());
                if (MainActivity.this.mResumed || !MainActivity.this.m_sSysStatus.has_game_param_setting_capacity() || AppInstance.s_strGamePackageName == null || MainActivity.this.m_gameSettingFloat == null) {
                    return;
                }
                MainActivity.this.send_game_setting_param(AppInstance.s_strGamePackageName);
            }
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void bleMTKData(byte[] bArr) throws RemoteException {
            if (AppInstance.has_device_connect()) {
                MainActivity.this.m_adb.receive_mtk_buf(bArr);
            }
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void bleGunPressAI(byte[] bArr, int i) throws RemoteException {
            MainActivity.this.mGunValue1 = bArr[4];
            MainActivity.this.mGunValue2 = bArr[5];
            if (MainActivity.this.mGunValueTV1 != null) {
                MainActivity.this.mGunValueTV1.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity$11$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.AnonymousClass11.this.m67lambda$bleGunPressAI$2$combaidukwgamesMainActivity$11();
                    }
                });
            }
            if (MainActivity.this.mGunValueTV2 != null) {
                MainActivity.this.mGunValueTV2.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity$11$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.AnonymousClass11.this.m68lambda$bleGunPressAI$3$combaidukwgamesMainActivity$11();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$bleGunPressAI$2$com-baidu-kwgames-MainActivity$11  reason: not valid java name */
        public /* synthetic */ void m67lambda$bleGunPressAI$2$combaidukwgamesMainActivity$11() {
            TextView textView = MainActivity.this.mGunValueTV1;
            textView.setText(MainActivity.this.mGunValue1 + "");
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$bleGunPressAI$3$com-baidu-kwgames-MainActivity$11  reason: not valid java name */
        public /* synthetic */ void m68lambda$bleGunPressAI$3$combaidukwgamesMainActivity$11() {
            TextView textView = MainActivity.this.mGunValueTV2;
            textView.setText(MainActivity.this.mGunValue2 + "");
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void bleMouseOn(boolean z, byte[] bArr) throws RemoteException {
            String str = MainActivity.TAG;
            Log.d(str, "bleMouseOn: " + z);
            this.m_boVMouseOnOff = z;
            this.m_nVMouseType = bArr[3];
            if (bArr[2] > 1) {
                this.m_nVMouseX = Units.dev_y_2_ui_x(Units.BYTE2INT(bArr[6], bArr[7]));
                this.m_nVMouseY = AppInstance.s_nScreenH - Units.dev_x_2_ui_y(Units.BYTE2INT(bArr[4], bArr[5]));
            } else {
                this.m_nVMouseX = -1;
                this.m_nVMouseY = -1;
            }
            MainActivity.this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity$11$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.AnonymousClass11.this.m69lambda$bleMouseOn$4$combaidukwgamesMainActivity$11();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$bleMouseOn$4$com-baidu-kwgames-MainActivity$11  reason: not valid java name */
        public /* synthetic */ void m69lambda$bleMouseOn$4$combaidukwgamesMainActivity$11() {
            if (this.m_boVMouseOnOff) {
                FloatMgr.setMouseImageByDevice(Boolean.valueOf(this.m_nVMouseType != 1));
            }
            MainActivity.this.show_virtual_mouse(this.m_boVMouseOnOff, this.m_nVMouseX, this.m_nVMouseY);
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void bleMouseMove(byte[] bArr) throws RemoteException {
            int unused = MainActivity.m_nNewVirtualMouseX = Units.dev_y_2_ui_x(Units.BYTE2INT(bArr[3], bArr[4]));
            int unused2 = MainActivity.m_nNewVirtualMouseY = AppInstance.s_nScreenH - Units.dev_x_2_ui_y(Units.BYTE2INT(bArr[5], bArr[6]));
            if (bArr[2] >= 5) {
                boolean unused3 = MainActivity.s_boLOLMoveIconChanged = true;
                long unused4 = MainActivity.s_nLastShowLOLMoveIconTime = SystemClock.uptimeMillis();
            }
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void OnBleChangeAIGunPress(final byte b) {
            MainActivity.this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity$11$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.AnonymousClass11.this.m63lambda$OnBleChangeAIGunPress$5$combaidukwgamesMainActivity$11(b);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$OnBleChangeAIGunPress$5$com-baidu-kwgames-MainActivity$11  reason: not valid java name */
        public /* synthetic */ void m63lambda$OnBleChangeAIGunPress$5$combaidukwgamesMainActivity$11(byte b) {
            if (MainActivity.this.mIsRecording) {
                MainActivity.this.set_cur_AI_gun_press(b);
            }
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void OnBleAICrosshairState(final byte b) {
            MainActivity.this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity$11$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.AnonymousClass11.this.m60lambda$OnBleAICrosshairState$6$combaidukwgamesMainActivity$11(b);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$OnBleAICrosshairState$6$com-baidu-kwgames-MainActivity$11  reason: not valid java name */
        public /* synthetic */ void m60lambda$OnBleAICrosshairState$6$combaidukwgamesMainActivity$11(byte b) {
            if (b == 0) {
                byte b2 = MainActivity.this.m_byOldGun;
                int ai_scope_to_crosshair_scope = AICrosshair.ai_scope_to_crosshair_scope(MainActivity.this.m_nOldScopeAiID, true);
                int i = AICrosshair.get_gun_crosshair(b2, ai_scope_to_crosshair_scope);
                if (i == 0) {
                    FloatMgr.hide_aim_float();
                } else {
                    FloatMgr.show_aim_float(i, AICrosshair.get_scope_offset_x(b2, ai_scope_to_crosshair_scope), AICrosshair.get_scope_offset_y(b2, ai_scope_to_crosshair_scope));
                }
                MainActivity.this.begin_crosshair_check_timer();
                return;
            }
            FloatMgr.show_aim_float();
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void OnBleAIGunDownStageChanged(final byte b) {
            if (MainActivity.this.m_boAIStageEditing) {
                MainActivity.this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity$11$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.AnonymousClass11.this.m61x8cceeb46(b);
                    }
                });
            } else if (MainActivity.this.m_boDynamicGunAdjustFloatIsVisible && MainActivity.this.m_boDynamicStageAdjustVisible) {
                MainActivity.this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity$11$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.AnonymousClass11.this.m62xf3a7ab07(b);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$OnBleAIGunDownStageChanged$7$com-baidu-kwgames-MainActivity$11  reason: not valid java name */
        public /* synthetic */ void m61x8cceeb46(byte b) {
            int i = 0;
            while (i < 6) {
                MainActivity.this.m_arrAIStageText[i].setTextColor(b == i ? AppInstance.s_colorRed : AppInstance.s_colorGreen);
                i++;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$OnBleAIGunDownStageChanged$8$com-baidu-kwgames-MainActivity$11  reason: not valid java name */
        public /* synthetic */ void m62xf3a7ab07(byte b) {
            int i = 0;
            while (i < 6) {
                MainActivity.this.m_arrDynamicStageText[i].setTextColor(b == i ? AppInstance.s_colorRed : AppInstance.s_colorGreen);
                i++;
            }
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void OnBleDynamicGunDataChanged(final boolean z) {
            if (MainActivity.this.m_boDynamicGunPressOnOff && MainActivity.this.m_boDynamicFloatIsVisible && MainActivity.this.m_textDynamicResult != null) {
                MainActivity.this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity$11$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.AnonymousClass11.this.m64xbc9192e1(z);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$OnBleDynamicGunDataChanged$9$com-baidu-kwgames-MainActivity$11  reason: not valid java name */
        public /* synthetic */ void m64xbc9192e1(boolean z) {
            MainActivity.this.m_textDynamicResult.setText(DynamicGunData.get_dynamic_result_string());
            if (MainActivity.this.m_boDynamicGunAdjustFloatIsVisible) {
                MainActivity.this.on_dynamic_level_changed();
            }
            if (z) {
                MainActivity.this.begin_dynamic_gun_down_stage_save_timer();
            }
            if (!MainActivity.this.m_crossDynamicScopeOff.m_boHideWhileScopeOn || MainActivity.this.m_crossDynamicScopeOff.m_nStyle == 0) {
                return;
            }
            FloatMgr.update_aim_float_visible(!DynamicGunData.is_scope_on());
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void OnBleGunPartsReduceChanged(final byte b) {
            if (MainActivity.this.mHasGun1 || MainActivity.this.mHasGun2) {
                MainActivity.this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity$11$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.AnonymousClass11.this.m65x6f6e7a36(b);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$OnBleGunPartsReduceChanged$10$com-baidu-kwgames-MainActivity$11  reason: not valid java name */
        public /* synthetic */ void m65x6f6e7a36(byte b) {
            int i = MainActivity.this.m_nCurGun1Head;
            int i2 = MainActivity.this.m_nCurGun1Handle;
            int i3 = MainActivity.this.m_nCurGun1Tail;
            if (MainActivity.this.mHasGun2) {
                i = MainActivity.this.m_nCurGun2Head;
                i2 = MainActivity.this.m_nCurGun2Handle;
                i3 = MainActivity.this.m_nCurGun2Tail;
            }
            if (GunPartsMgr.is_gun_parts_empty(MainActivity.this.m_byOldGun, i, i2, i3).booleanValue() || !GunPartsMgr.set_gun_parts_reduce(MainActivity.this.m_byOldGun, i, i2, i3, b)) {
                return;
            }
            if (MainActivity.this.m_boAIStageEditing) {
                MainActivity.this.update_AI_gun_parts_reduce_text();
            }
            MainActivity.this.begin_AI_parts_reduce_save_timer();
            MainActivity.this.m_nLastBleGunDown = -1;
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void onConfigurationChanged(final int i) throws RemoteException {
            MainActivity.this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity$11$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.AnonymousClass11.this.m71x7922c4c(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$onConfigurationChanged$11$com-baidu-kwgames-MainActivity$11  reason: not valid java name */
        public /* synthetic */ void m71x7922c4c(int i) {
            MainActivity.this.close_and_save_AI_edit_float();
            if (i == 2) {
                if (MainActivity.this.mAI.isChecked()) {
                    EasyFloat.showAppFloat(Constants.FLOAT_TAG_AI_RESULT);
                }
                if (MainActivity.this.m_boAIStageEditing) {
                    EasyFloat.showAppFloat(Constants.FLOAT_TAG_AI_STAGE);
                }
                if (MainActivity.this.m_boDynamicFloatIsVisible) {
                    EasyFloat.showAppFloat(Constants.FLOAT_TAG_DYNAMIC_RESULT);
                }
                if (MainActivity.this.m_boDynamicGunAdjustFloatIsVisible) {
                    EasyFloat.showAppFloat(Constants.FLOAT_TAG_DYNAMIC_STAGE);
                    return;
                }
                return;
            }
            EasyFloat.hideAppFloat(Constants.FLOAT_TAG_AI_RESULT);
            if (MainActivity.this.m_boAIStageEditing) {
                EasyFloat.hideAppFloat(Constants.FLOAT_TAG_AI_STAGE);
            }
            if (MainActivity.this.m_boDynamicFloatIsVisible) {
                EasyFloat.hideAppFloat(Constants.FLOAT_TAG_DYNAMIC_RESULT);
            }
            if (MainActivity.this.m_boDynamicGunAdjustFloatIsVisible) {
                EasyFloat.hideAppFloat(Constants.FLOAT_TAG_DYNAMIC_STAGE);
            }
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void OnUploadToupingParam(byte[] bArr) throws RemoteException {
            int BYTE2INT = Units.BYTE2INT(bArr[3], bArr[4]);
            int BYTE2INT2 = Units.BYTE2INT(bArr[5], bArr[6]);
            int BYTE2INT3 = Units.BYTE2INT(bArr[7], bArr[8]);
            int BYTE2INT4 = Units.BYTE2INT(bArr[9], bArr[10]);
            if ((!((BYTE2INT == 0 && BYTE2INT2 == 0 && BYTE2INT3 == 0 && BYTE2INT4 == 0) ? false : true) || BYTE2INT == AppInstance.s_nScreenW) && BYTE2INT2 == AppInstance.s_nScreenH && BYTE2INT3 == AppInstance.s_nToupingDisplayX && BYTE2INT4 == AppInstance.s_nToupingDisplayY) {
                return;
            }
            AppInstance.s_nToupingPhoneX = AppInstance.s_nScreenW;
            AppInstance.s_nToupingPhoneY = AppInstance.s_nScreenH;
            AppInstance.s_nToupingDisplayX = BYTE2INT3;
            AppInstance.s_nToupingDisplayY = BYTE2INT4;
            MsgBox.msg_box1((int) R.string.touping_res_changed);
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void OnUploadOtherParam(int i, byte[] bArr) throws RemoteException {
            if (17 == i) {
                if (bArr.length >= 19) {
                    System.arraycopy(bArr, 3, AppInstance.s_arrUUID, 0, 16);
                    MainActivity.this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.MainActivity$11$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MainActivity.AnonymousClass11.this.m66lambda$OnUploadOtherParam$12$combaidukwgamesMainActivity$11();
                        }
                    });
                }
            } else if (18 == i) {
                if (bArr[3] == 0) {
                    if (MainActivity.this.mIsRecording && MainActivity.this.m_qianliyan != null && MainActivity.this.m_qianliyan.get_onoff()) {
                        MainActivity.this.m_qianliyan.show(bArr[4] != 0, bArr[5] != 0);
                    }
                } else if (1 == bArr[3]) {
                    String str = MainActivity.TAG;
                    Log.d(str, "E_SET_PARAM_STAGE_GUN_PRESS_PREVIOUS=" + ((int) bArr[4]));
                    if (MainActivity.this.m_boAIStageEditing) {
                        MainActivity.this.set_M4_dynamic_preview(Boolean.valueOf(1 == bArr[4]));
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$OnUploadOtherParam$12$com-baidu-kwgames-MainActivity$11  reason: not valid java name */
        public /* synthetic */ void m66lambda$OnUploadOtherParam$12$combaidukwgamesMainActivity$11() {
            MainActivity.this.m_btnID.setVisibility(0);
            MainActivity.this.m_boDevBanned = BanID.is_device_baned(NEAT.byte_to_hex_string(AppInstance.s_arrUUID, AppInstance.s_arrUUID.length));
            MainActivity.this.show_ban_msgbox();
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return MainActivity.this.mThroneService.asBinder();
        }
    }

    void show_ban_msgbox() {
        if (this.m_boDevBanned) {
            MsgBox.msg_box1_with_choice2(this, R.string.dev_baned_tip, R.string.confirm, R.string.unlock, new Handler() { // from class: com.baidu.kwgames.MainActivity.12
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (1 == message.arg2) {
                        MainActivity.this.show_unlock_dialog();
                    }
                }
            });
        }
    }

    private boolean is_virtual_mouse_visible() {
        return this.mMouseDisposable != null;
    }

    private void stopVirtualMouseUpdateTimer() {
        Disposable disposable = this.mMouseDisposable;
        if (disposable != null) {
            disposable.dispose();
            this.mMouseDisposable = null;
        }
    }

    private void startMousePositionTicket() {
        stopVirtualMouseUpdateTimer();
        this.mMouseDisposable = Observable.interval(1L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.baidu.kwgames.MainActivity.13
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                int i;
                int i2;
                int i3;
                int i4;
                long uptimeMillis = SystemClock.uptimeMillis();
                if (AppInstance.mBleConnected) {
                    if (MainActivity.m_nNewVirtualMouseX > MainActivity.m_nOldVirtualMouseX) {
                        i = MainActivity.m_nNewVirtualMouseX;
                        i2 = MainActivity.m_nOldVirtualMouseX;
                    } else {
                        i = MainActivity.m_nOldVirtualMouseX;
                        i2 = MainActivity.m_nNewVirtualMouseX;
                    }
                    int i5 = i - i2;
                    if (MainActivity.m_nNewVirtualMouseY > MainActivity.m_nOldVirtualMouseY) {
                        i3 = MainActivity.m_nNewVirtualMouseY;
                        i4 = MainActivity.m_nOldVirtualMouseY;
                    } else {
                        i3 = MainActivity.m_nOldVirtualMouseY;
                        i4 = MainActivity.m_nNewVirtualMouseY;
                    }
                    int i6 = i3 - i4;
                    if ((i5 != 0 || i6 != 0) && (uptimeMillis - MainActivity.s_nLastShowVirtualMouseTime >= 25 || i5 >= 2 || i6 >= 2)) {
                        long unused = MainActivity.s_nLastShowVirtualMouseTime = uptimeMillis;
                        int unused2 = MainActivity.m_nOldVirtualMouseX = MainActivity.m_nNewVirtualMouseX;
                        int unused3 = MainActivity.m_nOldVirtualMouseY = MainActivity.m_nNewVirtualMouseY;
                        FloatMgr.setVirtualMouseLocation(MainActivity.m_nOldVirtualMouseX, MainActivity.m_nOldVirtualMouseY);
                    }
                } else if (AppInstance.s_boUSBConnected && (MainActivity.m_nOldVirtualMouseX != MainActivity.m_nNewVirtualMouseX || MainActivity.m_nOldVirtualMouseY != MainActivity.m_nNewVirtualMouseY)) {
                    int unused4 = MainActivity.m_nOldVirtualMouseX = MainActivity.m_nNewVirtualMouseX;
                    int unused5 = MainActivity.m_nOldVirtualMouseY = MainActivity.m_nNewVirtualMouseY;
                    FloatMgr.setVirtualMouseLocation(MainActivity.m_nOldVirtualMouseX, MainActivity.m_nOldVirtualMouseY);
                }
                if (MainActivity.s_boLOLMoveIconChanged) {
                    boolean unused6 = MainActivity.s_boLOLMoveIconChanged = false;
                    boolean unused7 = MainActivity.s_boLOLMoveVisible = true;
                    FloatMgr.show_lol_move(true, MainActivity.m_nNewVirtualMouseX, MainActivity.m_nNewVirtualMouseY);
                }
                if (!MainActivity.s_boLOLMoveVisible || uptimeMillis - MainActivity.s_nLastShowLOLMoveIconTime < 120) {
                    return;
                }
                boolean unused8 = MainActivity.s_boLOLMoveVisible = false;
                FloatMgr.show_lol_move(false, MainActivity.m_nNewVirtualMouseX, MainActivity.m_nNewVirtualMouseY);
            }
        });
    }

    public static void saveBitmapToSDCard(Bitmap bitmap, String str) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/sdcard/img-" + str + ".jpg");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void halfIcon() {
        int[] iArr = new int[2];
        this.mFloatWindowIcon.getLocationOnScreen(iArr);
        this.mFloatWindowIcon.getLayoutParams().height = (int) (Util.dip2px(this, 50.0f) * 0.35f);
        if (this.m_sSysStatus.is_in_usb_mode()) {
            this.mFloatWindowIcon.setImageResource(R.mipmap.ic_launcher_round_bottom);
        } else {
            this.mFloatWindowIcon.setImageResource(R.mipmap.ic_ble_bottom);
        }
        this.mFloatWindowIcon.setTag(2);
        EasyFloat.updateFloat(Constants.FLOAT_TAG_BALL, iArr[0], 0);
    }

    @OnClick({R.id.connect_state})
    public void onOpenBtSettings() {
        if (AppInstance.has_device_connect()) {
            return;
        }
        if (this.m_boInNTSMode) {
            MsgBox.msg_box1_with_choice2(this, R.string.exit_nts_msg, R.string.confirm, R.string.cancel, new Handler() { // from class: com.baidu.kwgames.MainActivity.14
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (message.arg2 == 0) {
                        MainActivity.this.m_boInNTSMode = false;
                        MainActivity.m_ini.put(Constants.CFG_NTS_MODE, MainActivity.this.m_boInNTSMode);
                        if (MainActivity.this.ble_is_scanning()) {
                            MainActivity.this.cancelScan();
                        }
                        MainActivity.this.set_ble_connect_timer(true);
                    }
                }
            });
            return;
        }
        int i = this.m_nBluetoothClickTimes;
        this.m_nBluetoothClickTimes = i + 1;
        if (1 == i) {
            MsgBox.msg_box1_with_choice1(this, (int) R.string.ble_not_connect_several_time, (int) R.string.confirm, new Handler() { // from class: com.baidu.kwgames.MainActivity.15
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    NEAT.open_bluetooth_setting(AppInstance.s_context);
                }
            });
        } else {
            NEAT.open_bluetooth_setting(this);
        }
    }

    public void initGunAgile() {
        AgileMgr.loadInfo();
        AIDynamicGunDownM4.init();
        GunPartsMgr.loadInfo();
    }

    public void init_system_preset() {
        boolean copyFileFromAssets;
        String str = AppInstance.s_strAppVer;
        if (str.equals(m_ini.getString(Constants.CFG_PRESET_COPYED_VER, "0"))) {
            return;
        }
        try {
            if (NEAT.is_chinese()) {
                copyFileFromAssets = ResourceUtils.copyFileFromAssets("preset/zh", getExternalFilesDir(null).getPath() + "/keyConfig/");
            } else {
                copyFileFromAssets = ResourceUtils.copyFileFromAssets("preset/en", getExternalFilesDir(null).getPath() + "/keyConfig/");
            }
            if (copyFileFromAssets) {
                m_ini.put(Constants.CFG_PRESET_COPYED_VER, str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load_bag_tag_model() {
        try {
            String str = Constants.get_cur_AI_kit().m_strAIChModelFileName;
            AssetManager assets = getAssets();
            Bitmap bitmap = ImageUtils.getBitmap(assets.open(str + "/bagtag.bmp"));
            if (bitmap != null) {
                this.m_nBagtagModelW = bitmap.getWidth() / 2;
                int height = bitmap.getHeight() / 2;
                this.m_nBagtagModelH = height;
                int i = this.m_nBagtagModelW;
                this.m_fBagtagModelRateW2H = (i * 1.0f) / height;
                Bitmap scale = ImageUtils.scale(bitmap, i, height, true);
                CreateBagTagModel(scale, 0);
                scale.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load_other_model() {
        try {
            String str = Constants.get_cur_AI_kit().m_strAIChModelFileName;
            AssetManager assets = getAssets();
            Bitmap bitmap = ImageUtils.getBitmap(assets.open(str + "/takeoff.bmp"));
            if (bitmap != null) {
                if (AppInstance.is_hpjy_AI_kit()) {
                    this.m_nTakeoffModelW = bitmap.getWidth();
                    this.m_nTakeoffModelH = bitmap.getHeight();
                } else {
                    this.m_nTakeoffModelW = bitmap.getWidth() / 2;
                    this.m_nTakeoffModelH = bitmap.getHeight() / 2;
                }
                int i = this.m_nTakeoffModelW;
                int i2 = this.m_nTakeoffModelH;
                this.m_fTakeoffModelRateW2H = (i * 1.0f) / i2;
                Bitmap scale = ImageUtils.scale(bitmap, i, i2, true);
                CreateTakeOffModel(scale, 0);
                scale.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load_gun_model() {
        String[] list;
        try {
            String str = Constants.get_cur_AI_kit().m_strAIChModelFileName + Constants.E_ASSETS_DIR_GUN_MODEL;
            for (String str2 : getAssets().list(str)) {
                if (str2.contains(".bmp")) {
                    Bitmap bitmap = ImageUtils.getBitmap(getAssets().open(str + "/" + str2));
                    if (bitmap != null) {
                        String substring = str2.substring(0, str2.indexOf(".bmp"));
                        Bitmap scale = ImageUtils.scale(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, true);
                        this.m_nGunModelW = scale.getWidth();
                        int height = scale.getHeight();
                        this.m_nGunModelH = height;
                        this.m_fGunModeRateW2H = (this.m_nGunModelW * 1.0f) / height;
                        int i = Constants.get_gun_ID(substring);
                        if (-1 != i) {
                            CreateGunModel(scale, i);
                        }
                        scale.recycle();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load_scope_model() {
        String[] list;
        try {
            String str = (NEAT.is_chinese() ? Constants.get_cur_AI_kit().m_strAIChModelFileName : Constants.get_cur_AI_kit().m_strAIEnModelFileName) + Constants.E_ASSETS_DIR_SCOPE;
            for (String str2 : getAssets().list(str)) {
                if (str2.contains(".bmp")) {
                    Bitmap bitmap = ImageUtils.getBitmap(getAssets().open(str + "/" + str2));
                    if (bitmap != null) {
                        String substring = str2.substring(0, str2.indexOf(".bmp"));
                        this.m_nScopeModelW = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        this.m_nScopeModelH = height;
                        this.m_fScopeModeRateW2H = (this.m_nScopeModelW * 1.0f) / height;
                        int i = Constants.get_AI_scope_ID(substring);
                        if (-1 != i) {
                            CreateScopeModel(bitmap, i);
                        }
                        bitmap.recycle();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load_gun_head_model() {
        String[] list;
        try {
            String str = Constants.get_cur_AI_kit().m_strAIChModelFileName + Constants.E_ASSETS_DIR_GUN_HEAD;
            for (String str2 : getAssets().list(str)) {
                if (str2.contains(".bmp")) {
                    Bitmap bitmap = ImageUtils.getBitmap(getAssets().open(str + "/" + str2));
                    if (bitmap != null) {
                        String substring = str2.substring(0, str2.indexOf(".bmp"));
                        Bitmap scale = ImageUtils.scale(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, true);
                        this.m_nGunPartsModelWH = scale.getWidth();
                        if (Constants.m_mapPartsNameToID.containsKey(substring)) {
                            CreateGunHeadModel(scale, Constants.m_mapPartsNameToID.get(substring).byteValue());
                        }
                        scale.recycle();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load_gun_handle_model() {
        String[] list;
        try {
            String str = Constants.get_cur_AI_kit().m_strAIChModelFileName + Constants.E_ASSETS_DIR_GUN_HANDLE;
            for (String str2 : getAssets().list(str)) {
                if (str2.contains(".bmp")) {
                    Bitmap bitmap = ImageUtils.getBitmap(getAssets().open(str + "/" + str2));
                    if (bitmap != null) {
                        String substring = str2.substring(0, str2.indexOf(".bmp"));
                        this.m_nGunHandleModelWH = bitmap.getWidth();
                        if (Constants.m_mapPartsNameToID.containsKey(substring)) {
                            CreateGunHandleModel(bitmap, Constants.m_mapPartsNameToID.get(substring).byteValue());
                        }
                        bitmap.recycle();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load_gun_tail_model() {
        String[] list;
        try {
            String str = Constants.get_cur_AI_kit().m_strAIChModelFileName + Constants.E_ASSETS_DIR_GUN_TAIL;
            for (String str2 : getAssets().list(str)) {
                if (str2.contains(".bmp")) {
                    Bitmap bitmap = ImageUtils.getBitmap(getAssets().open(str + "/" + str2));
                    if (bitmap != null) {
                        String substring = str2.substring(0, str2.indexOf(".bmp"));
                        Bitmap scale = ImageUtils.scale(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, true);
                        if (Constants.m_mapPartsNameToID.containsKey(substring)) {
                            CreateGunTailModel(scale, Constants.m_mapPartsNameToID.get(substring).byteValue());
                        }
                        scale.recycle();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onGun() {
        IThrone iThrone = this.mThroneService;
        if (iThrone != null) {
            try {
                iThrone.bleGetGunPressAI();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        EasyFloat.with(this).setTag("gun").setLayout(R.layout.gun, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.16
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.mGunValueTV1 = (TextView) view.findViewById(R.id.gun_value1);
                TextView textView = MainActivity.this.mGunValueTV1;
                textView.setText(MainActivity.this.mGunValue1 + "");
                ((ImageButton) view.findViewById(R.id.up1)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.16.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        MainActivity.access$2208(MainActivity.this);
                        if (MainActivity.this.mGunValueTV1 != null) {
                            TextView textView2 = MainActivity.this.mGunValueTV1;
                            textView2.setText(MainActivity.this.mGunValue1 + "");
                        }
                    }
                });
                ((ImageButton) view.findViewById(R.id.down1)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.16.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        MainActivity.access$2210(MainActivity.this);
                        if (MainActivity.this.mGunValueTV1 != null) {
                            TextView textView2 = MainActivity.this.mGunValueTV1;
                            textView2.setText(MainActivity.this.mGunValue1 + "");
                        }
                    }
                });
                MainActivity.this.mGunValueTV2 = (TextView) view.findViewById(R.id.gun_value2);
                TextView textView2 = MainActivity.this.mGunValueTV2;
                textView2.setText(MainActivity.this.mGunValue2 + "");
                ((ImageButton) view.findViewById(R.id.up2)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.16.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        MainActivity.access$2308(MainActivity.this);
                        if (MainActivity.this.mGunValueTV2 != null) {
                            TextView textView3 = MainActivity.this.mGunValueTV2;
                            textView3.setText(MainActivity.this.mGunValue2 + "");
                        }
                    }
                });
                ((ImageButton) view.findViewById(R.id.down2)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.16.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        MainActivity.access$2310(MainActivity.this);
                        if (MainActivity.this.mGunValueTV2 != null) {
                            TextView textView3 = MainActivity.this.mGunValueTV2;
                            textView3.setText(MainActivity.this.mGunValue2 + "");
                        }
                    }
                });
                ((ImageButton) view.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.16.5
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        MainActivity.this.mGunValueTV1 = null;
                        MainActivity.this.mGunValueTV2 = null;
                        EasyFloat.dismissAppFloat("gun");
                    }
                });
            }
        }).setShowPattern(ShowPattern.ALL_TIME).setDragEnable(true).setAnimator(null).setGravity(83, 80).show();
    }

    public void update_AI_gun_down_stage_UI() {
        for (int i = 0; i < 6; i++) {
            byte b = AgileMgr.get_AI_gun_stage(this.m_byOldGun, i);
            TextView textView = this.m_arrAIStageText[i];
            textView.setText(((int) b) + "%");
            this.m_arrAIStageSeekbar[i].setProgress(b);
        }
        update_AI_gun_parts_ctrls();
        update_AI_gun_parts_reduce_text();
    }

    public void update_AI_gun_parts_reduce_text() {
        if (this.mHasGun1) {
            this.m_textPartsReduce.setText(GunPartsMgr.get_gun_parts_name(this.m_byOldGun, this.m_nCurGun1Head, this.m_nCurGun1Handle, this.m_nCurGun1Tail));
        } else if (this.mHasGun2) {
            this.m_textPartsReduce.setText(GunPartsMgr.get_gun_parts_name(this.m_byOldGun, this.m_nCurGun2Head, this.m_nCurGun2Handle, this.m_nCurGun2Tail));
        } else {
            this.m_textPartsReduce.setText("");
        }
    }

    public void update_AI_gun_parts_ctrls() {
        int i = 8;
        if (this.m_sSysStatus.has_gun_parts_reduce_capacity()) {
            i = (this.m_boGunPartsAI && -1 == this.m_byOldGun) ? 0 : 0;
            this.m_layoutPartsReduce.setVisibility(0);
            this.m_textPartsReduceTitle.setVisibility(0);
            this.m_textPartsReduce.setVisibility(i);
            this.m_btnPartsReducePlus.setVisibility(i);
            this.m_btnPartsReduceMinus.setVisibility(i);
            return;
        }
        this.m_layoutPartsReduce.setVisibility(8);
    }

    /* renamed from: com.baidu.kwgames.MainActivity$17  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass17 implements Runnable {
        AnonymousClass17() {
        }

        @Override // java.lang.Runnable
        public void run() {
            new Thread(new Runnable() { // from class: com.baidu.kwgames.MainActivity$17$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    GunPartsMgr.save();
                }
            }).start();
        }
    }

    public void begin_AI_parts_reduce_save_timer() {
        this.m_timerHandler.removeCallbacks(this.m_runableSaveAIPartReduce);
        this.m_timerHandler.postDelayed(this.m_runableSaveAIPartReduce, 5000L);
    }

    /* renamed from: com.baidu.kwgames.MainActivity$18  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass18 implements Runnable {
        AnonymousClass18() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Log.d(MainActivity.TAG, "save_AI_gun_stage@@@@\n");
            new Thread(new Runnable() { // from class: com.baidu.kwgames.MainActivity$18$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AgileMgr.save_AI_gun_stage();
                }
            }).start();
        }
    }

    public void begin_AI_gun_down_stage_save_timer() {
        this.m_timerHandler.removeCallbacks(this.m_runableSaveAIStage);
        this.m_timerHandler.postDelayed(this.m_runableSaveAIStage, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.MainActivity$19  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass19 implements OnInvokeView {
        AnonymousClass19() {
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            MainActivity.this.m_AIdynamicStageRoot = view.findViewById(R.id.root);
            MainActivity.this.m_layoutPartsReduce = view.findViewById(R.id.layout_parts_reduce);
            MainActivity.this.m_textPartsReduceTitle = (TextView) view.findViewById(R.id.parts_reduce_title);
            MainActivity.this.m_textPartsReduce = (TextView) view.findViewById(R.id.parts_reduce_value);
            MainActivity.this.m_btnPartsReducePlus = (Button) view.findViewById(R.id.parts_reduce_plus);
            MainActivity.this.m_btnPartsReduceMinus = (Button) view.findViewById(R.id.parts_reduce_minus);
            int i = 0;
            MainActivity.this.m_arrAIStageTitle[0] = (TextView) view.findViewById(R.id.stage1_title);
            MainActivity.this.m_arrAIStageTitle[1] = (TextView) view.findViewById(R.id.stage2_title);
            MainActivity.this.m_arrAIStageTitle[2] = (TextView) view.findViewById(R.id.stage3_title);
            MainActivity.this.m_arrAIStageTitle[3] = (TextView) view.findViewById(R.id.stage4_title);
            MainActivity.this.m_arrAIStageTitle[4] = (TextView) view.findViewById(R.id.stage5_title);
            MainActivity.this.m_arrAIStageTitle[5] = (TextView) view.findViewById(R.id.stage6_title);
            MainActivity.this.m_arrAIStageSeekbar[0] = (SeekBar) view.findViewById(R.id.stage1_rate);
            MainActivity.this.m_arrAIStageSeekbar[1] = (SeekBar) view.findViewById(R.id.stage2_rate);
            MainActivity.this.m_arrAIStageSeekbar[2] = (SeekBar) view.findViewById(R.id.stage3_rate);
            MainActivity.this.m_arrAIStageSeekbar[3] = (SeekBar) view.findViewById(R.id.stage4_rate);
            MainActivity.this.m_arrAIStageSeekbar[4] = (SeekBar) view.findViewById(R.id.stage5_rate);
            MainActivity.this.m_arrAIStageSeekbar[5] = (SeekBar) view.findViewById(R.id.stage6_rate);
            MainActivity.this.m_arrAIStageBtnPlus[0] = (Button) view.findViewById(R.id.stage1_plus);
            MainActivity.this.m_arrAIStageBtnPlus[1] = (Button) view.findViewById(R.id.stage2_plus);
            MainActivity.this.m_arrAIStageBtnPlus[2] = (Button) view.findViewById(R.id.stage3_plus);
            MainActivity.this.m_arrAIStageBtnPlus[3] = (Button) view.findViewById(R.id.stage4_plus);
            MainActivity.this.m_arrAIStageBtnPlus[4] = (Button) view.findViewById(R.id.stage5_plus);
            MainActivity.this.m_arrAIStageBtnPlus[5] = (Button) view.findViewById(R.id.stage6_plus);
            MainActivity.this.m_arrAIStageBtnMinus[0] = (Button) view.findViewById(R.id.stage1_minus);
            MainActivity.this.m_arrAIStageBtnMinus[1] = (Button) view.findViewById(R.id.stage2_minus);
            MainActivity.this.m_arrAIStageBtnMinus[2] = (Button) view.findViewById(R.id.stage3_minus);
            MainActivity.this.m_arrAIStageBtnMinus[3] = (Button) view.findViewById(R.id.stage4_minus);
            MainActivity.this.m_arrAIStageBtnMinus[4] = (Button) view.findViewById(R.id.stage5_minus);
            MainActivity.this.m_arrAIStageBtnMinus[5] = (Button) view.findViewById(R.id.stage6_minus);
            MainActivity.this.m_arrAIStageText[0] = (TextView) view.findViewById(R.id.stage1_value);
            MainActivity.this.m_arrAIStageText[1] = (TextView) view.findViewById(R.id.stage2_value);
            MainActivity.this.m_arrAIStageText[2] = (TextView) view.findViewById(R.id.stage3_value);
            MainActivity.this.m_arrAIStageText[3] = (TextView) view.findViewById(R.id.stage4_value);
            MainActivity.this.m_arrAIStageText[4] = (TextView) view.findViewById(R.id.stage5_value);
            MainActivity.this.m_arrAIStageText[5] = (TextView) view.findViewById(R.id.stage6_value);
            MainActivity.this.update_AI_gun_down_stage_UI();
            view.findViewById(R.id.close_window).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$19$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass19.this.m73lambda$invoke$0$combaidukwgamesMainActivity$19(view2);
                }
            });
            MainActivity.this.m_btnPartsReducePlus.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.19.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.mHasGun1 || MainActivity.this.mHasGun2) {
                        int i2 = MainActivity.this.m_nCurGun1Head;
                        int i3 = MainActivity.this.m_nCurGun1Handle;
                        int i4 = MainActivity.this.m_nCurGun1Tail;
                        if (MainActivity.this.mHasGun2) {
                            i2 = MainActivity.this.m_nCurGun2Head;
                            i3 = MainActivity.this.m_nCurGun2Handle;
                            i4 = MainActivity.this.m_nCurGun2Tail;
                        }
                        if (GunPartsMgr.is_gun_parts_empty(MainActivity.this.m_byOldGun, i2, i3, i4).booleanValue()) {
                            return;
                        }
                        byte b = GunPartsMgr.get_gun_parts_reduce(MainActivity.this.m_byOldGun, i2, i3, i4);
                        byte b2 = (byte) (b + 1);
                        if (b < 100) {
                            GunPartsMgr.set_gun_parts_reduce(MainActivity.this.m_byOldGun, i2, i3, i4, b2);
                            MainActivity.this.update_AI_gun_parts_reduce_text();
                            MainActivity.this.begin_AI_parts_reduce_save_timer();
                            MainActivity.this.m_nLastBleGunDown = -1;
                        }
                    }
                }
            });
            MainActivity.this.m_btnPartsReduceMinus.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.19.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.mHasGun1 || MainActivity.this.mHasGun2) {
                        int i2 = MainActivity.this.m_nCurGun1Head;
                        int i3 = MainActivity.this.m_nCurGun1Handle;
                        int i4 = MainActivity.this.m_nCurGun1Tail;
                        if (MainActivity.this.mHasGun2) {
                            i2 = MainActivity.this.m_nCurGun2Head;
                            i3 = MainActivity.this.m_nCurGun2Handle;
                            i4 = MainActivity.this.m_nCurGun2Tail;
                        }
                        if (GunPartsMgr.is_gun_parts_empty(MainActivity.this.m_byOldGun, i2, i3, i4).booleanValue()) {
                            return;
                        }
                        byte b = GunPartsMgr.get_gun_parts_reduce(MainActivity.this.m_byOldGun, i2, i3, i4);
                        byte b2 = (byte) (b - 1);
                        if (b > 0) {
                            GunPartsMgr.set_gun_parts_reduce(MainActivity.this.m_byOldGun, i2, i3, i4, b2);
                            MainActivity.this.update_AI_gun_parts_reduce_text();
                            MainActivity.this.begin_AI_parts_reduce_save_timer();
                            MainActivity.this.m_nLastBleGunDown = -1;
                        }
                    }
                }
            });
            if (!MainActivity.this.m_boAIDynamic) {
                while (i < 6) {
                    MainActivity.this.m_arrAIStageTitle[i].setVisibility(8);
                    MainActivity.this.m_arrAIStageSeekbar[i].setVisibility(8);
                    MainActivity.this.m_arrAIStageBtnPlus[i].setVisibility(8);
                    MainActivity.this.m_arrAIStageBtnMinus[i].setVisibility(8);
                    MainActivity.this.m_arrAIStageText[i].setVisibility(8);
                    i++;
                }
                FloatMgr.resetVirtualMouse();
                return;
            }
            View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.19.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (-1 == MainActivity.this.m_byOldGun) {
                        Toast.makeText(MainActivity.this.getApplicationContext(), MainActivity.this.getApplicationContext().getString(R.string.ai_stage_no_gun), 1).show();
                        return;
                    }
                    for (int i2 = 0; i2 < 6; i2++) {
                        if (view2 == MainActivity.this.m_arrAIStageBtnPlus[i2]) {
                            byte b = AgileMgr.get_AI_gun_stage(MainActivity.this.m_byOldGun, i2);
                            if (b < 100) {
                                byte b2 = (byte) (b + 1);
                                TextView textView = MainActivity.this.m_arrAIStageText[i2];
                                textView.setText(((int) b2) + "%");
                                AgileMgr.set_AI_gun_stage(MainActivity.this.m_byOldGun, i2, b2);
                                MainActivity.this.m_arrAIStageSeekbar[i2].setProgress(b2);
                                MainActivity.this.m_nLastBleGunDown = -1;
                                MainActivity.this.begin_AI_gun_down_stage_save_timer();
                                return;
                            }
                            return;
                        }
                    }
                    for (int i3 = 0; i3 < 6; i3++) {
                        if (view2 == MainActivity.this.m_arrAIStageBtnMinus[i3]) {
                            byte b3 = AgileMgr.get_AI_gun_stage(MainActivity.this.m_byOldGun, i3);
                            if (b3 > 0) {
                                byte b4 = (byte) (b3 - 1);
                                TextView textView2 = MainActivity.this.m_arrAIStageText[i3];
                                textView2.setText(((int) b4) + "%");
                                AgileMgr.set_AI_gun_stage(MainActivity.this.m_byOldGun, i3, b4);
                                MainActivity.this.m_arrAIStageSeekbar[i3].setProgress(b4);
                                MainActivity.this.m_nLastBleGunDown = -1;
                                MainActivity.this.begin_AI_gun_down_stage_save_timer();
                                return;
                            }
                            return;
                        }
                    }
                }
            };
            for (int i2 = 0; i2 < 6; i2++) {
                MainActivity.this.m_arrAIStageBtnPlus[i2].setOnClickListener(onClickListener);
                MainActivity.this.m_arrAIStageBtnMinus[i2].setOnClickListener(onClickListener);
            }
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.baidu.kwgames.MainActivity.19.4
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int i3, boolean z) {
                    if (-1 == MainActivity.this.m_byOldGun) {
                        return;
                    }
                    for (int i4 = 0; i4 < 6; i4++) {
                        if (seekBar == MainActivity.this.m_arrAIStageSeekbar[i4]) {
                            TextView textView = MainActivity.this.m_arrAIStageText[i4];
                            textView.setText(MainActivity.this.m_arrAIStageSeekbar[i4].getProgress() + "%");
                            return;
                        }
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                    if (-1 == MainActivity.this.m_byOldGun) {
                        Toast.makeText(MainActivity.this.getApplicationContext(), MainActivity.this.getApplicationContext().getString(R.string.ai_stage_no_gun), 1).show();
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (-1 == MainActivity.this.m_byOldGun) {
                        return;
                    }
                    for (int i3 = 0; i3 < 6; i3++) {
                        if (seekBar == MainActivity.this.m_arrAIStageSeekbar[i3]) {
                            int progress = MainActivity.this.m_arrAIStageSeekbar[i3].getProgress();
                            TextView textView = MainActivity.this.m_arrAIStageText[i3];
                            textView.setText(progress + "%");
                            AgileMgr.set_AI_gun_stage(MainActivity.this.m_byOldGun, i3, (byte) progress);
                            MainActivity.this.m_nLastBleGunDown = -1;
                            MainActivity.this.begin_AI_gun_down_stage_save_timer();
                            return;
                        }
                    }
                }
            };
            while (i < 6) {
                MainActivity.this.m_arrAIStageSeekbar[i].setOnSeekBarChangeListener(onSeekBarChangeListener);
                i++;
            }
            FloatMgr.resetVirtualMouse();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$0$com-baidu-kwgames-MainActivity$19  reason: not valid java name */
        public /* synthetic */ void m73lambda$invoke$0$combaidukwgamesMainActivity$19(View view) {
            MainActivity.this.m_boAIStageEditing = false;
            EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_STAGE);
            MainActivity.this.begin_hide_AI_result_ctrls_timer();
        }
    }

    public void onAIGunDownStage() {
        EasyFloat.Builder layout = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_STAGE).setLayout(R.layout.ai_stage_gun_down, new AnonymousClass19());
        layout.registerCallbacks(new FloatCallbackDragRemIni(Constants.CFG_AI_DYNAMIC_FLOAT_X, Constants.CFG_AI_DYNAMIC_FLOAT_Y));
        layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null);
        layout.setLocation(NEAT.make_sure_x_visible(m_ini.getInt(Constants.CFG_AI_DYNAMIC_FLOAT_X, 80)), NEAT.make_sure_y_visible(m_ini.getInt(Constants.CFG_AI_DYNAMIC_FLOAT_Y, 0)));
        layout.show();
    }

    public void onAIGunDownStageM4() {
        if (EasyFloat.getAppFloatView(Constants.FLOAT_TAG_AI_STAGE) != null) {
            EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_STAGE);
            return;
        }
        EasyFloat.Builder layout = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_STAGE).setLayout(R.layout.ai_stage_gun_new, new AnonymousClass20());
        layout.registerCallbacks(new FloatCallbackDragRemIni(Constants.CFG_AI_DYNAMIC_FLOAT_X, Constants.CFG_AI_DYNAMIC_FLOAT_Y));
        layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null);
        layout.setLocation(NEAT.make_sure_x_visible(m_ini.getInt(Constants.CFG_AI_DYNAMIC_FLOAT_X, 80)), NEAT.make_sure_y_visible(m_ini.getInt(Constants.CFG_AI_DYNAMIC_FLOAT_Y, 0)));
        layout.show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.MainActivity$20  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass20 implements OnInvokeView {
        AnonymousClass20() {
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            MainActivity.this.m_layoutPartsReduce = view.findViewById(R.id.layout_parts_reduce);
            MainActivity.this.m_textPartsReduce = (TextView) view.findViewById(R.id.parts_reduce_value);
            MainActivity.this.m_btnPartsReducePlus = (Button) view.findViewById(R.id.parts_reduce_plus);
            MainActivity.this.m_btnPartsReduceMinus = (Button) view.findViewById(R.id.parts_reduce_minus);
            MainActivity.this.m_layoutAIDynamicM4 = view.findViewById(R.id.m_layoutAIDynamicM4);
            MainActivity.this.m_btnAddStage = (Button) view.findViewById(R.id.m_btnDeleteStage);
            MainActivity.this.m_btnDeleteStage = (Button) view.findViewById(R.id.m_btnAddStage);
            if (MainActivity.this.m_arrStageButtonsM4 == null) {
                MainActivity.this.m_arrStageButtonsM4 = new Button[12];
            }
            MainActivity.this.m_arrStageButtonsM4[0] = (Button) view.findViewById(R.id.m_btnStage1);
            MainActivity.this.m_arrStageButtonsM4[1] = (Button) view.findViewById(R.id.m_btnStage2);
            MainActivity.this.m_arrStageButtonsM4[2] = (Button) view.findViewById(R.id.m_btnStage3);
            MainActivity.this.m_arrStageButtonsM4[3] = (Button) view.findViewById(R.id.m_btnStage4);
            MainActivity.this.m_arrStageButtonsM4[4] = (Button) view.findViewById(R.id.m_btnStage5);
            MainActivity.this.m_arrStageButtonsM4[5] = (Button) view.findViewById(R.id.m_btnStage6);
            MainActivity.this.m_arrStageButtonsM4[6] = (Button) view.findViewById(R.id.m_btnStage7);
            MainActivity.this.m_arrStageButtonsM4[7] = (Button) view.findViewById(R.id.m_btnStage8);
            MainActivity.this.m_arrStageButtonsM4[8] = (Button) view.findViewById(R.id.m_btnStage9);
            MainActivity.this.m_arrStageButtonsM4[9] = (Button) view.findViewById(R.id.m_btnStage10);
            MainActivity.this.m_arrStageButtonsM4[10] = (Button) view.findViewById(R.id.m_btnStage11);
            MainActivity.this.m_arrStageButtonsM4[11] = (Button) view.findViewById(R.id.m_btnStage12);
            MainActivity.this.m_stageTimeSeekbar = (SeekBar) view.findViewById(R.id.stage1_rate);
            MainActivity.this.m_textStageTime = (TextView) view.findViewById(R.id.stage1_value);
            MainActivity.this.m_stageValSeekbar = (SeekBar) view.findViewById(R.id.stage2_rate);
            MainActivity.this.m_textStageVal = (TextView) view.findViewById(R.id.stage2_value);
            MainActivity.this.m_textErr = (TextView) view.findViewById(R.id.m_textErr);
            MainActivity.this.m_btnCopyM4 = (Button) view.findViewById(R.id.m_btnCopyM4);
            MainActivity.this.update_AI_gun_down_stage_UI_M4();
            view.findViewById(R.id.close_window).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$20$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass20.this.m74lambda$invoke$0$combaidukwgamesMainActivity$20(view2);
                }
            });
            view.findViewById(R.id.m_btnCopyM4).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$20$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass20.this.m75lambda$invoke$1$combaidukwgamesMainActivity$20(view2);
                }
            });
            MainActivity.this.m_btnPartsReducePlus.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.20.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.mHasGun1 || MainActivity.this.mHasGun2) {
                        int i = MainActivity.this.m_nCurGun1Head;
                        int i2 = MainActivity.this.m_nCurGun1Handle;
                        int i3 = MainActivity.this.m_nCurGun1Tail;
                        if (MainActivity.this.mHasGun2) {
                            i = MainActivity.this.m_nCurGun2Head;
                            i2 = MainActivity.this.m_nCurGun2Handle;
                            i3 = MainActivity.this.m_nCurGun2Tail;
                        }
                        if (GunPartsMgr.is_gun_parts_empty(MainActivity.this.m_byOldGun, i, i2, i3).booleanValue()) {
                            return;
                        }
                        byte b = GunPartsMgr.get_gun_parts_reduce(MainActivity.this.m_byOldGun, i, i2, i3);
                        byte b2 = (byte) (b + 1);
                        if (b < 100) {
                            GunPartsMgr.set_gun_parts_reduce(MainActivity.this.m_byOldGun, i, i2, i3, b2);
                            MainActivity.this.update_AI_gun_parts_reduce_text();
                            MainActivity.this.begin_AI_parts_reduce_save_timer();
                            MainActivity.this.m_nLastBleGunDown = -1;
                        }
                    }
                }
            });
            MainActivity.this.m_btnPartsReduceMinus.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.20.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.mHasGun1 || MainActivity.this.mHasGun2) {
                        int i = MainActivity.this.m_nCurGun1Head;
                        int i2 = MainActivity.this.m_nCurGun1Handle;
                        int i3 = MainActivity.this.m_nCurGun1Tail;
                        if (MainActivity.this.mHasGun2) {
                            i = MainActivity.this.m_nCurGun2Head;
                            i2 = MainActivity.this.m_nCurGun2Handle;
                            i3 = MainActivity.this.m_nCurGun2Tail;
                        }
                        if (GunPartsMgr.is_gun_parts_empty(MainActivity.this.m_byOldGun, i, i2, i3).booleanValue()) {
                            return;
                        }
                        byte b = GunPartsMgr.get_gun_parts_reduce(MainActivity.this.m_byOldGun, i, i2, i3);
                        byte b2 = (byte) (b - 1);
                        if (b > 0) {
                            GunPartsMgr.set_gun_parts_reduce(MainActivity.this.m_byOldGun, i, i2, i3, b2);
                            MainActivity.this.update_AI_gun_parts_reduce_text();
                            MainActivity.this.begin_AI_parts_reduce_save_timer();
                            MainActivity.this.m_nLastBleGunDown = -1;
                        }
                    }
                }
            });
            if (!MainActivity.this.m_boAIDynamic) {
                MainActivity.this.m_layoutAIDynamicM4.setVisibility(8);
                FloatMgr.resetVirtualMouse();
                return;
            }
            if (MainActivity.this.m_stageM4ClickListener == null) {
                MainActivity.this.m_stageM4ClickListener = new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$20$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        MainActivity.AnonymousClass20.this.m76lambda$invoke$2$combaidukwgamesMainActivity$20(view2);
                    }
                };
            }
            for (int i = 0; i < 12; i++) {
                MainActivity.this.m_arrStageButtonsM4[i].setOnClickListener(MainActivity.this.m_stageM4ClickListener);
            }
            view.findViewById(R.id.m_btnDeleteStage).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$20$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass20.this.m77lambda$invoke$3$combaidukwgamesMainActivity$20(view2);
                }
            });
            view.findViewById(R.id.m_btnAddStage).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$20$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass20.this.m78lambda$invoke$4$combaidukwgamesMainActivity$20(view2);
                }
            });
            view.findViewById(R.id.stage_plus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$20$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass20.this.m79lambda$invoke$5$combaidukwgamesMainActivity$20(view2);
                }
            });
            view.findViewById(R.id.stage_minus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$20$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass20.this.m80lambda$invoke$6$combaidukwgamesMainActivity$20(view2);
                }
            });
            MainActivity.this.m_stageValSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.baidu.kwgames.MainActivity.20.4
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                    if (-1 == MainActivity.this.m_byOldGun) {
                        return;
                    }
                    TextView textView = MainActivity.this.m_textStageVal;
                    textView.setText(MainActivity.this.m_stageValSeekbar.getProgress() + "%");
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (-1 == MainActivity.this.m_byOldGun) {
                        return;
                    }
                    int progress = MainActivity.this.m_stageValSeekbar.getProgress();
                    TextView textView = MainActivity.this.m_textStageVal;
                    textView.setText(progress + "%");
                    AIDynamicGunDownM4.set_AI_gun_stage(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageM4, progress);
                    MainActivity.this.m_nLastBleGunDown = -1;
                    AIDynamicGunDownM4.begin_save_timer();
                }
            });
            view.findViewById(R.id.stage_time_plus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.20.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int i2;
                    if (-1 != MainActivity.this.m_byOldGun && (i2 = AIDynamicGunDownM4.get_AI_gun_stage_time(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageM4)) < 255) {
                        AIDynamicGunDownM4.set_AI_gun_stage_time(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageM4, i2 + 1);
                        MainActivity.this.update_AI_gun_down_stage_UI_M4();
                        MainActivity.this.m_nLastBleGunDown = -1;
                        AIDynamicGunDownM4.begin_save_timer();
                    }
                }
            });
            view.findViewById(R.id.stage_time_minus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.20.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int i2;
                    if (-1 != MainActivity.this.m_byOldGun && (i2 = AIDynamicGunDownM4.get_AI_gun_stage_time(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageM4)) > 1) {
                        AIDynamicGunDownM4.set_AI_gun_stage_time(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageM4, i2 - 1);
                        MainActivity.this.update_AI_gun_down_stage_UI_M4();
                        MainActivity.this.m_nLastBleGunDown = -1;
                        AIDynamicGunDownM4.begin_save_timer();
                    }
                }
            });
            MainActivity.this.m_stageTimeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.baidu.kwgames.MainActivity.20.7
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                    if (!z || -1 == MainActivity.this.m_byOldGun) {
                        return;
                    }
                    TextView textView = MainActivity.this.m_textStageTime;
                    textView.setText((MainActivity.this.m_stageTimeSeekbar.getProgress() * 10) + "");
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (-1 == MainActivity.this.m_byOldGun) {
                        return;
                    }
                    int progress = MainActivity.this.m_stageTimeSeekbar.getProgress();
                    if (progress == 0) {
                        progress = 1;
                    }
                    AIDynamicGunDownM4.set_AI_gun_stage_time(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageM4, progress);
                    MainActivity.this.update_AI_gun_down_stage_UI_M4();
                    MainActivity.this.m_nLastBleGunDown = -1;
                    AIDynamicGunDownM4.begin_save_timer();
                }
            });
            FloatMgr.resetVirtualMouse();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$0$com-baidu-kwgames-MainActivity$20  reason: not valid java name */
        public /* synthetic */ void m74lambda$invoke$0$combaidukwgamesMainActivity$20(View view) {
            MainActivity.this.m_boAIStageEditing = false;
            EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_STAGE);
            MainActivity.this.begin_hide_AI_result_ctrls_timer();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$1$com-baidu-kwgames-MainActivity$20  reason: not valid java name */
        public /* synthetic */ void m75lambda$invoke$1$combaidukwgamesMainActivity$20(View view) {
            if (-1 == MainActivity.this.m_byOldGun) {
                return;
            }
            MsgBox.msg_box_float_with_choice2(AppInstance.s_context, R.string.copy_m4_remind, R.string.confirm, R.string.cancel, new Handler() { // from class: com.baidu.kwgames.MainActivity.20.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (message.arg2 == 0) {
                        AIDynamicGunDownM4.copy_AI_gun_dynamic(11, MainActivity.this.m_byOldGun);
                        MainActivity.this.update_AI_gun_down_stage_UI_M4();
                        MainActivity.this.m_nLastBleGunDown = -1;
                        AIDynamicGunDownM4.begin_save_timer();
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$2$com-baidu-kwgames-MainActivity$20  reason: not valid java name */
        public /* synthetic */ void m76lambda$invoke$2$combaidukwgamesMainActivity$20(View view) {
            if (-1 == MainActivity.this.m_byOldGun) {
                return;
            }
            for (int i = 0; i < 12; i++) {
                if (view == MainActivity.this.m_arrStageButtonsM4[i] && MainActivity.this.m_nCurEditAIStageM4 != i) {
                    MainActivity.this.m_nCurEditAIStageM4 = i;
                    MainActivity.this.update_AI_gun_down_stage_UI_M4();
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$3$com-baidu-kwgames-MainActivity$20  reason: not valid java name */
        public /* synthetic */ void m77lambda$invoke$3$combaidukwgamesMainActivity$20(View view) {
            if (-1 == MainActivity.this.m_byOldGun || MainActivity.this.m_nCurEditAIStageMaxIndex <= 1) {
                return;
            }
            AIDynamicGunDownM4.set_AI_gun_stage_time(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageMaxIndex, 0);
            MainActivity.this.update_AI_gun_down_stage_UI_M4();
            MainActivity.this.m_nLastBleGunDown = -1;
            AIDynamicGunDownM4.begin_save_timer();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$4$com-baidu-kwgames-MainActivity$20  reason: not valid java name */
        public /* synthetic */ void m78lambda$invoke$4$combaidukwgamesMainActivity$20(View view) {
            if (-1 == MainActivity.this.m_byOldGun || MainActivity.this.m_nCurEditAIStageMaxIndex >= 11) {
                return;
            }
            int i = AIDynamicGunDownM4.get_AI_gun_stage(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageMaxIndex);
            MainActivity.this.m_nCurEditAIStageMaxIndex++;
            MainActivity mainActivity = MainActivity.this;
            mainActivity.m_nCurEditAIStageM4 = mainActivity.m_nCurEditAIStageMaxIndex;
            if (AIDynamicGunDownM4.get_AI_gun_stage_time(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageM4) == 0) {
                AIDynamicGunDownM4.set_AI_gun_stage_time(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageMaxIndex, 50);
            }
            AIDynamicGunDownM4.set_AI_gun_stage(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageMaxIndex, i);
            MainActivity.this.update_AI_gun_down_stage_UI_M4();
            MainActivity.this.m_nLastBleGunDown = -1;
            AIDynamicGunDownM4.begin_save_timer();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$5$com-baidu-kwgames-MainActivity$20  reason: not valid java name */
        public /* synthetic */ void m79lambda$invoke$5$combaidukwgamesMainActivity$20(View view) {
            int i;
            if (-1 != MainActivity.this.m_byOldGun && (i = AIDynamicGunDownM4.get_AI_gun_stage(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageM4)) < 255) {
                AIDynamicGunDownM4.set_AI_gun_stage(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageM4, i + 1);
                MainActivity.this.update_AI_gun_down_stage_UI_M4();
                MainActivity.this.m_nLastBleGunDown = -1;
                AIDynamicGunDownM4.begin_save_timer();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$6$com-baidu-kwgames-MainActivity$20  reason: not valid java name */
        public /* synthetic */ void m80lambda$invoke$6$combaidukwgamesMainActivity$20(View view) {
            int i;
            if (-1 != MainActivity.this.m_byOldGun && (i = AIDynamicGunDownM4.get_AI_gun_stage(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageM4)) > 0) {
                AIDynamicGunDownM4.set_AI_gun_stage(MainActivity.this.m_byOldGun, MainActivity.this.m_nOldGunHandle, MainActivity.this.m_nCurEditAIStageM4, i - 1);
                MainActivity.this.update_AI_gun_down_stage_UI_M4();
                MainActivity.this.m_nLastBleGunDown = -1;
                AIDynamicGunDownM4.begin_save_timer();
            }
        }
    }

    public void update_AI_gun_down_stage_UI_M4() {
        if (-1 == this.m_byOldGun) {
            this.m_textStageVal.setText("");
            this.m_stageValSeekbar.setProgress(0);
            this.m_textStageTime.setText("");
            this.m_stageTimeSeekbar.setProgress(0);
            this.m_textErr.setVisibility(0);
        } else {
            Boolean bool = false;
            int i = 0;
            while (true) {
                if (i >= 12) {
                    break;
                }
                if (bool.booleanValue() || AIDynamicGunDownM4.get_AI_gun_stage_time(this.m_byOldGun, this.m_nOldGunHandle, i) == 0) {
                    bool = true;
                    this.m_arrStageButtonsM4[i].setVisibility(8);
                } else {
                    this.m_nCurEditAIStageMaxIndex = i;
                    this.m_arrStageButtonsM4[i].setVisibility(0);
                    this.m_arrStageButtonsM4[i].setTextColor(i != this.m_nCurEditAIStageM4 ? -1 : -16711936);
                }
                i++;
            }
            int i2 = this.m_nCurEditAIStageM4;
            int i3 = this.m_nCurEditAIStageMaxIndex;
            if (i2 > i3) {
                this.m_nCurEditAIStageM4 = i3;
                this.m_arrStageButtonsM4[i3].setTextColor(-16711936);
            }
            int i4 = AIDynamicGunDownM4.get_AI_gun_stage(this.m_byOldGun, this.m_nOldGunHandle, this.m_nCurEditAIStageM4);
            TextView textView = this.m_textStageVal;
            textView.setText(i4 + "%");
            this.m_stageValSeekbar.setProgress(i4);
            int i5 = AIDynamicGunDownM4.get_AI_gun_stage_time(this.m_byOldGun, this.m_nOldGunHandle, this.m_nCurEditAIStageM4);
            TextView textView2 = this.m_textStageTime;
            textView2.setText((i5 * 10) + "");
            this.m_stageTimeSeekbar.setProgress(i5);
            this.m_textErr.setVisibility(8);
        }
        this.m_btnCopyM4.setVisibility(8);
        if (this.m_sSysStatus.has_gun_parts_reduce_capacity()) {
            if (this.m_boGunPartsAI) {
                byte b = this.m_byOldGun;
            }
            this.m_layoutPartsReduce.setVisibility(0);
        } else {
            this.m_layoutPartsReduce.setVisibility(8);
        }
        update_AI_gun_parts_reduce_text();
    }

    void set_M4_dynamic_preview(Boolean bool) {
        if (this.m_timerForM4DynamicPreview == null) {
            this.m_timerForM4DynamicPreview = new Runnable() { // from class: com.baidu.kwgames.MainActivity.21
                @Override // java.lang.Runnable
                public void run() {
                    if (MainActivity.this.m_nCurPreviewStage < MainActivity.this.m_nCurEditAIStageMaxIndex) {
                        if (NEAT.elapse(MainActivity.this.m_lPreviewStartTime) > MainActivity.this.m_arrPreviewTimeScale[MainActivity.this.m_nCurPreviewStage]) {
                            MainActivity.this.m_nCurPreviewStage++;
                            MainActivity.this.m_arrStageButtonsM4[MainActivity.this.m_nCurPreviewStage].setBackgroundColor(MainActivity.AI_STAGE_BG_FOCUS);
                        }
                        if (MainActivity.this.m_boAIStageEditing && MainActivity.this.m_boM4DynamicPreviewing.booleanValue()) {
                            MainActivity.this.m_timerHandler.postDelayed(this, 10L);
                        }
                    }
                }
            };
            this.m_arrPreviewTimeScale = new int[12];
        }
        this.m_timerHandler.removeCallbacks(this.m_timerForM4DynamicPreview);
        if (bool.booleanValue()) {
            this.m_nCurPreviewStage = 0;
            this.m_boM4DynamicPreviewing = true;
            this.m_lPreviewStartTime = System.currentTimeMillis();
            int i = 0;
            for (int i2 = 0; i2 <= this.m_nCurEditAIStageMaxIndex; i2++) {
                i += AIDynamicGunDownM4.get_AI_gun_stage_time(this.m_byOldGun, this.m_nOldGunHandle, i2) * 10;
                this.m_arrPreviewTimeScale[i2] = i;
            }
            this.m_arrStageButtonsM4[0].setBackgroundColor(AI_STAGE_BG_FOCUS);
            for (int i3 = 1; i3 <= this.m_nCurEditAIStageMaxIndex; i3++) {
                this.m_arrStageButtonsM4[i3].setBackgroundColor(AI_STAGE_BG_NORMAL);
            }
            this.m_timerHandler.postDelayed(this.m_timerForM4DynamicPreview, 10L);
            return;
        }
        this.m_boM4DynamicPreviewing = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean check_b4_show_edit_key_map() {
        try {
            Context applicationContext = getApplicationContext();
            if (!AppInstance.has_device_connect()) {
                Toast.makeText(applicationContext, applicationContext.getString(R.string.need_connect_blutooth), 1).show();
                return false;
            } else if (applicationContext.getResources().getConfiguration().orientation == 1) {
                Toast.makeText(applicationContext, applicationContext.getString(R.string.landscope_only), 1).show();
                return false;
            } else {
                boolean z = this.mResumed;
                int i = R.string.x1_mode_cant;
                if (z) {
                    if (!this.m_sSysStatus.is_in_x1_mode()) {
                        i = R.string.need_sel_game;
                    }
                    MsgBox.msg_box1(i);
                    return false;
                } else if (-1 == AppInstance.s_nCurKeyMap) {
                    Intent intent = new Intent(applicationContext, MainActivity.class);
                    intent.addFlags(603979776);
                    startActivity(intent);
                    Toast.makeText(applicationContext, applicationContext.getString(R.string.click_float_ball_without_sel_game), 1).show();
                    return false;
                } else if (this.m_sSysStatus.is_in_x1_mode()) {
                    Intent intent2 = new Intent(applicationContext, MainActivity.class);
                    intent2.addFlags(603979776);
                    startActivity(intent2);
                    Toast.makeText(applicationContext, applicationContext.getString(R.string.x1_mode_cant), 1).show();
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    void close_and_save_AI_edit_float() {
        if (this.m_boAIEditing) {
            ViewMgr.getInstance();
            ViewMgr.save();
            closeEditFloat();
            this.m_boNeedUpdateMatrix = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void show_float_ball() {
        if (EasyFloat.getAppFloatView(Constants.FLOAT_TAG_BALL) != null) {
            EasyFloat.showAppFloat(Constants.FLOAT_TAG_BALL);
            return;
        }
        EasyFloat.with(this).setTag(Constants.FLOAT_TAG_BALL).setShowPattern(ShowPattern.ALL_TIME).setAnimator(null).setLocation(m_ini.getInt(Constants.CFG_FLOAT_BALL_X, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION), 0).setAppFloatAnimator(null).setLayout(R.layout.float_window, new AnonymousClass23()).setShowPattern(ShowPattern.ALL_TIME).setSidePattern(SidePattern.RESULT_SIDE).registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.22
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                String str = MainActivity.TAG;
                Log.d(str, "touchEvent: " + motionEvent.getAction());
                if (motionEvent.getAction() == 0 || motionEvent.getAction() == 5) {
                    if (MainActivity.this.mHideDisposable != null && !MainActivity.this.mHideDisposable.isDisposed()) {
                        MainActivity.this.mHideDisposable.dispose();
                    }
                    if (MainActivity.this.mFloatWindowIcon.getTag() != null) {
                        ((Integer) MainActivity.this.mFloatWindowIcon.getTag()).intValue();
                        ViewGroup.LayoutParams layoutParams = MainActivity.this.mFloatWindowIcon.getLayoutParams();
                        layoutParams.width = Util.dip2px(MainActivity.this, 60.0f);
                        layoutParams.height = Util.dip2px(MainActivity.this, 60.0f);
                        MainActivity.this.mFloatWindowIcon.setLayoutParams(layoutParams);
                        MainActivity.this.mFloatWindowIcon.setImageResource(R.mipmap.ic_launcher_round);
                        MainActivity.this.mFloatWindowIcon.setTag(null);
                    }
                } else if (motionEvent.getAction() == 1 && MainActivity.this.mFloatWindowIcon.getTag() == null) {
                    if (MainActivity.this.mHideDisposable != null && !MainActivity.this.mHideDisposable.isDisposed()) {
                        MainActivity.this.mHideDisposable.dispose();
                    }
                    MainActivity.this.mHideDisposable = Observable.timer(3L, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(MainActivity.this.halfFloatWindow);
                }
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                view.getLocationOnScreen(iArr);
                MainActivity.m_ini.put(Constants.CFG_FLOAT_BALL_X, iArr[0]);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.MainActivity$23  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass23 implements OnInvokeView {
        AnonymousClass23() {
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            FloatMgr.resetVirtualMouse();
            MainActivity.this.mFloatWindowIcon = (ImageView) view.findViewById(R.id.float_window_icon);
            MainActivity.this.mFloatWindowIcon.getDrawable().setLevel(10000);
            MainActivity.this.mTable = (LinearLayout) view.findViewById(R.id.table);
            final TextView textView = (TextView) view.findViewById(R.id.show);
            final TextView textView2 = (TextView) view.findViewById(R.id.edit);
            final TextView textView3 = (TextView) view.findViewById(R.id.gunPos);
            final TextView textView4 = (TextView) view.findViewById(R.id.m_textGameSetting);
            ViewGroup.LayoutParams layoutParams = MainActivity.this.mFloatWindowIcon.getLayoutParams();
            layoutParams.width = Util.dip2px(MainActivity.this, 60.0f);
            layoutParams.height = Util.dip2px(MainActivity.this, 60.0f);
            FloatMgr.update_float_param_while_create(Constants.FLOAT_TAG_BALL);
            textView.getLayoutParams().width = Util.dip2px(MainActivity.this, 60.0f);
            textView.getLayoutParams().height = Util.dip2px(MainActivity.this, 60.0f);
            textView2.getLayoutParams().width = Util.dip2px(MainActivity.this, 60.0f);
            textView2.getLayoutParams().height = Util.dip2px(MainActivity.this, 60.0f);
            MainActivity.this.mFloatWindowIcon.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity.23.1
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view2) {
                    Intent intent = new Intent(MainActivity.this.getApplicationContext(), MainActivity.class);
                    intent.addFlags(603979776);
                    MainActivity.this.startActivity(intent);
                    return true;
                }
            });
            ((TextView) view.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.23.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (!MainActivity.this.getString(R.string.look_key).equals(textView.getText())) {
                        textView.setText(R.string.look_key);
                        textView2.setVisibility(0);
                        Intent intent = new Intent(MainActivity.this.getApplicationContext(), FloatWindowService.class);
                        intent.setAction(FloatWindowService.ACTION_KEP_MAPPING_HIDE_DISPLAY);
                        MainActivity.this.startService(intent);
                    }
                    if (MainActivity.this.mResumed) {
                        if (MainActivity.this.mTable.getVisibility() == 0) {
                            MainActivity.this.mTable.setVisibility(8);
                            MainActivity.this.mTable.postDelayed(new Runnable() { // from class: com.baidu.kwgames.MainActivity.23.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    MainActivity.this.halfIcon();
                                }
                            }, 10L);
                            return;
                        }
                        return;
                    }
                    MainActivity.this.m_boDynamicFloatIsVisible = false;
                    MainActivity.this.mRecognTextview = null;
                    EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_RESULT);
                    EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_STAGE);
                    MainActivity.this.show_dynamic_gun_press_float(false);
                    MainActivity.this.show_dynamic_gun_adjust_float(false);
                    EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_BALL);
                }
            });
            MainActivity.this.mFloatWindowIcon.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.23.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int i = 8;
                    if (MainActivity.this.mTable.getVisibility() == 0) {
                        MainActivity.this.mTable.setVisibility(8);
                        return;
                    }
                    MainActivity.this.mTable.setVisibility(0);
                    if (!MainActivity.this.check_device_connection() || (AppInstance.g_sSysStatus != null && !Constants.is_model_support_AI(AppInstance.g_sSysStatus.uSystemModel))) {
                        textView3.setVisibility(8);
                    } else {
                        textView3.setVisibility(0);
                    }
                    TextView textView5 = textView4;
                    if (AppInstance.has_device_connect() && MainActivity.this.m_sSysStatus.has_game_param_setting_capacity()) {
                        i = 0;
                    }
                    textView5.setVisibility(i);
                }
            });
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.23.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.m_boDevBanned) {
                        MainActivity.this.show_ban_msgbox();
                    } else if (MainActivity.this.getString(R.string.look_key).equals(textView.getText())) {
                        if (MainActivity.this.check_b4_show_edit_key_map() && MainActivity.this.getString(R.string.look_key).contentEquals(textView.getText())) {
                            MainActivity.this.mTable.setVisibility(8);
                            textView.setText(R.string.hide_key);
                            Intent intent = new Intent(MainActivity.this.getApplicationContext(), FloatWindowService.class);
                            intent.setAction(FloatWindowService.ACTION_KEP_MAPPING_DISPLAY);
                            MainActivity.this.startService(intent);
                        }
                    } else {
                        textView.setText(R.string.look_key);
                        Intent intent2 = new Intent(MainActivity.this.getApplicationContext(), FloatWindowService.class);
                        intent2.setAction(FloatWindowService.ACTION_KEP_MAPPING_HIDE_DISPLAY);
                        MainActivity.this.startService(intent2);
                    }
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.23.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.m_boDevBanned) {
                        MainActivity.this.show_ban_msgbox();
                    } else if (MainActivity.this.check_b4_show_edit_key_map()) {
                        MainActivity.this.mTable.setVisibility(8);
                        textView.setText(R.string.look_key);
                        Intent intent = new Intent(MainActivity.this.getApplicationContext(), FloatWindowService.class);
                        intent.setAction(FloatWindowService.ACTION_KEY_MAPPING_SETTING);
                        MainActivity.this.startService(intent);
                    }
                }
            });
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$23$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass23.this.m81lambda$invoke$0$combaidukwgamesMainActivity$23(view2);
                }
            });
            textView4.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.23.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.m_gameSettingFloat == null) {
                        MsgBox.msg_box1_float(AppInstance.s_context, NEAT.s(R.string.game_setting_disable_warning));
                    } else {
                        MainActivity.this.m_gameSettingFloat.show_float(AppInstance.s_strGamePackageName);
                    }
                }
            });
            if (MainActivity.this.mHideDisposable != null && !MainActivity.this.mHideDisposable.isDisposed()) {
                MainActivity.this.mHideDisposable.dispose();
            }
            MainActivity.this.mHideDisposable = Observable.timer(3L, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(MainActivity.this.halfFloatWindow);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$0$com-baidu-kwgames-MainActivity$23  reason: not valid java name */
        public /* synthetic */ void m81lambda$invoke$0$combaidukwgamesMainActivity$23(View view) {
            if (MainActivity.this.m_boDevBanned) {
                MainActivity.this.show_ban_msgbox();
            } else if (MainActivity.this.mIsRecording) {
                MainActivity.this.mTable.setVisibility(8);
                if (MainActivity.this.m_boAIEditing) {
                    MainActivity.this.close_and_save_AI_edit_float();
                } else {
                    MainActivity.this.show_AI_pos_edit();
                }
            } else {
                MsgBox.msg_box1_float(AppInstance.s_context, NEAT.s(R.string.ai_not_open));
            }
        }
    }

    private boolean is_meiying_ble_paired() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            Class[] clsArr = null;
            BluetoothAdapter.class.getDeclaredMethod("getConnectionState", null).setAccessible(true);
            for (BluetoothDevice bluetoothDevice : defaultAdapter.getBondedDevices()) {
                Class[] clsArr2 = null;
                Method declaredMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", null);
                declaredMethod.setAccessible(true);
                Object[] objArr = null;
                if (!((Boolean) declaredMethod.invoke(bluetoothDevice, null)).booleanValue() && -1 != bluetoothDevice.getName().indexOf("MEIYING")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:139:0x0262, code lost:
        if (r12.m_sSysStatus.uSystemVer < 92) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x026b, code lost:
        if (r12.m_sSysStatus.uSystemVer < 73) goto L138;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    boolean check_b4_enter_game(android.view.View r13, int r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 704
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.kwgames.MainActivity.check_b4_enter_game(android.view.View, int, java.lang.String):boolean");
    }

    void send_game_setting_param(String str) {
        if (this.m_sSysStatus.has_game_param_setting_capacity()) {
            if (this.m_gameSettingFloat == null) {
                this.m_gameSettingFloat = new GameSettingFloat(this, this.mThroneService);
            }
            this.m_gameSettingFloat.bleSendParams(str);
        }
    }

    @OnClick({R.id.game1, R.id.game2, R.id.game3, R.id.game4, R.id.game5, R.id.game6, R.id.game7, R.id.game8})
    public void onGameClick(View view) {
        if (this.m_boDevBanned) {
            show_ban_msgbox();
            return;
        }
        int i = 0;
        if (view.getTag() != null) {
            int[] iArr = {R.id.game1, R.id.game2, R.id.game3, R.id.game4, R.id.game5, R.id.game6, R.id.game7, R.id.game8};
            int id = view.getId();
            while (true) {
                if (i >= 8) {
                    i = -1;
                    break;
                } else if (id == iArr[i]) {
                    break;
                } else {
                    i++;
                }
            }
            final String str = (String) view.getTag();
            if (check_b4_enter_game(view, i, str)) {
                if (i != this.m_nOldKeyMap) {
                    this.m_nOldKeyMap = i;
                    AppInstance.s_nCurKeyMap = i;
                    AppInstance.s_strGamePackageName = str;
                    AppInstance.s_strKeyMapSaveAs = Units.getAppName(this, str);
                    try {
                        if (this.mThroneService != null) {
                            send_game_setting_param(str);
                            Constants.SGame2NameID sGame2NameID = Constants.get_game_info(AppInstance.s_strGamePackageName, Units.getAppName(this, AppInstance.s_strGamePackageName));
                            this.mThroneService.bleGetKeyMap(i, sGame2NameID.m_nGameDevID, sGame2NameID.m_nGameViewResetDelay, sGame2NameID.m_nFireDisableViewParam, sGame2NameID.m_nGameParamExt1);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                if (DynamicGunData.get_onoff() && DynamicGunData.init(str)) {
                    bleSendDynamicGunPressData();
                    TextView textView = this.m_textDynamicResult;
                    if (textView != null) {
                        textView.setText(DynamicGunData.get_dynamic_result_string());
                    }
                }
                Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(str);
                if (launchIntentForPackage != null) {
                    startActivity(launchIntentForPackage);
                    String find_guide_for_app = Constants.find_guide_for_app(str);
                    SPUtils sPUtils = SPUtils.getInstance();
                    if (sPUtils.getInt(str + "-no-tip") != -1 || find_guide_for_app.isEmpty()) {
                        return;
                    }
                    boolean[] zArr = s_arrShowGuide;
                    if (zArr[i]) {
                        return;
                    }
                    zArr[i] = true;
                    this.mHandler.postDelayed(new Runnable() { // from class: com.baidu.kwgames.MainActivity.29
                        @Override // java.lang.Runnable
                        public void run() {
                            GuideFloat guideFloat = new GuideFloat(MainActivity.this.getApplicationContext());
                            guideFloat.setPackageName(str);
                            guideFloat.show();
                        }
                    }, 1000L);
                }
            }
        } else if (this.canSelect) {
            this.canSelect = false;
            Intent intent = new Intent(this, GameSelectActivity.class);
            intent.putExtra("viewId", view.getId());
            startActivityForResult(intent, 1);
        }
    }

    @OnClick({R.id.game1_video, R.id.game2_video, R.id.game3_video, R.id.game4_video, R.id.game5_video, R.id.game6_video, R.id.game7_video, R.id.game8_video})
    public void onGameVideoClick(View view) {
        if (view.getTag() == null) {
            return;
        }
        String find_video_for_app = Constants.find_video_for_app((String) view.getTag());
        if (find_video_for_app.isEmpty()) {
            return;
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(find_video_for_app));
        startActivity(intent);
    }

    @OnLongClick({R.id.game1, R.id.game2, R.id.game3, R.id.game4, R.id.game5, R.id.game6, R.id.game7, R.id.game8})
    public void onGameLongClick(final View view) {
        if (view.getTag() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("");
            builder.setMessage(R.string.del_game_icon_confirm);
            builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.30
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    AppCompatButton appCompatButton = (AppCompatButton) view;
                    int indexOf = MainActivity.this.mGames.indexOf(appCompatButton);
                    appCompatButton.setText("游戏" + (indexOf + 1));
                    appCompatButton.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, MainActivity.this.getResources().getDrawable(17301547), (Drawable) null);
                    SPUtils sPUtils = SPUtils.getInstance();
                    sPUtils.remove(GameSettingFloat.TAG_ATTR_GAME + indexOf);
                    appCompatButton.setTag(null);
                    MainActivity.this.m_arrLstGameVideo.get(indexOf).setTag(null);
                    MainActivity.this.m_arrLstGameVideo.get(indexOf).setVisibility(8);
                    if (view.getId() == R.id.game1) {
                        MainActivity.this.game1LeftTip.setVisibility(8);
                        MainActivity.this.game1RightTip.setVisibility(8);
                    }
                }
            });
            builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.31
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setNeutralButton(R.string.no_show_id, new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.32
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    int indexOf = MainActivity.this.mGames.indexOf((AppCompatButton) view);
                    SPUtils sPUtils = MainActivity.m_ini;
                    String string = sPUtils.getString(GameSettingFloat.TAG_ATTR_GAME + indexOf);
                    if (string != null) {
                        MsgBox.msg_box1(string);
                    }
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
    }

    @OnClick({R.id.settings})
    public void onSettings() {
        if (this.m_boDevBanned) {
            show_ban_msgbox();
        } else if (AppInstance.has_device_connect() && this.m_boNeverGetSystemStatus) {
        } else {
            Intent intent = new Intent(this, SettingActivity.class);
            intent.putExtra("status", this.mSystemStatus[10]);
            intent.putExtra("statusExt", this.mSystemStatus[5]);
            intent.putExtra("statusExt3", this.mSystemStatus[12]);
            intent.putExtra("statusExt4", this.mSystemStatus[16]);
            intent.putExtra("statusExt5", this.mSystemStatus[17]);
            intent.putExtra("byADDownAutoAD", this.mSystemStatus[20]);
            intent.putExtra("byADDownAutoADSpeed", this.mSystemStatus[21]);
            intent.putExtra("hpvol", this.mSystemStatus[22]);
            intent.putExtra("shunfenger", this.mSystemStatus[23]);
            intent.putExtra("ctrlrepeatsp", this.mSystemStatus[24]);
            startActivityForResult(intent, 3);
        }
    }

    @OnClick({R.id.m_btnID})
    public void onButtonID() {
        String byte_to_hex_string = NEAT.byte_to_hex_string(AppInstance.s_arrUUID, AppInstance.s_arrUUID.length);
        ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("ID", byte_to_hex_string));
        MsgBox.msg_box1(NEAT.s(R.string.uuid_copied) + byte_to_hex_string);
    }

    @OnClick({R.id.m_btnFacebook})
    public void onButtonFacebook() {
        NEAT.open_website_url(this, "https://www.facebook.com/profile.php?id=61558805977458");
    }

    @OnClick({R.id.m_btnAdvice})
    public void onButtonAdvice() {
        NEAT.open_website_url(this, "https://docs.qq.com/form/page/DVHBWUWtidHNEcHZM");
    }

    @OnClick({R.id.m_btnHelp})
    public void onButtonHelp() {
        NEAT.open_website_url(this, "https://www.kuwee.cn/h");
    }

    @OnLongClick({R.id.m_btnHelp})
    public void onLongButtonHelp() {
        this.m_nPressLongTimes++;
        if (this.m_macroAdjustFloat == null) {
            this.m_macroAdjustFloat = new MacroAdjustFloat(this, this.mThroneService);
        }
        this.m_macroAdjustFloat.show_float();
    }

    public boolean check_device_connection() {
        if (AppInstance.has_device_connect()) {
            return true;
        }
        MsgBox.msg_box1((int) R.string.need_connect_blutooth);
        this.mAI.setChecked(false);
        return false;
    }

    @OnClick({R.id.btn_ai_question})
    public void onAIQuestion() {
        MsgBox.msg_box1_with_choice2(R.string.ai_quesions, R.string.iamknow, R.string.ineedvideo, this.m_webHandler.set_url(Constants.URL_VIDEO_AI_GUN_PRESS));
    }

    public void begin_hide_AI_crosshair_timer() {
        this.m_hideAICrossHairHandler.removeCallbacks(this.m_runableHideAICrosshair);
        this.m_hideAICrossHairHandler.postDelayed(this.m_runableHideAICrosshair, 3000L);
    }

    @OnClick({R.id.btn_ai_dynamic_onoff})
    public void onAIDynamicOnOff() {
        boolean z = !this.m_boAIDynamic;
        this.m_boAIDynamic = z;
        m_ini.put(Constants.CFG_AI_DYNAMIC_ONOFF, z);
        ImageSwButton.set_onoff(this.m_btnAIDynamicOnOff, this.m_boAIDynamic);
        if (this.m_boAIStageEditing) {
            this.m_boAIStageEditing = false;
            EasyFloat.hideAppFloat(Constants.FLOAT_TAG_AI_STAGE);
        }
        MsgBox.msg_box_with_never_remind_once_choice2(R.string.ai_dynamic_onoff_tip, "ai_dynamic_onoff_tip", R.string.iamknow, R.string.ineedvideo, new Handler() { // from class: com.baidu.kwgames.MainActivity.34
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (1 == message.arg2) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(Constants.URL_VIDEO_AI_GUN_PRESS));
                    MainActivity.this.startActivity(intent);
                }
            }
        });
    }

    @OnClick({R.id.btn_ai_continues_shoot_onoff})
    public void onAIContinuesShootOnOff() {
        byte b;
        if (AppInstance.has_device_connect()) {
            byte b2 = this.mSystemStatus[12];
            if (Units.is_bit_on(b2, 7)) {
                b = Units.clear_bit(b2, 7);
            } else {
                b = Units.set_bit(b2, 7);
            }
            this.mSystemStatus[12] = b;
            send_system_status();
            ImageSwButton.set_onoff(this.m_btnAIContinuesShootOnOff, Units.is_bit_on(b, 7));
            MsgBox.msg_box_with_never_remind_once_choice2(R.string.ai_continues_shoot_tip, "ai_continues_shoot_tip", R.string.iamknow, R.string.ineedvideo, new Handler() { // from class: com.baidu.kwgames.MainActivity.35
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (1 == message.arg2) {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse(Constants.URL_VIDEO_AI_GUN_PRESS));
                        MainActivity.this.startActivity(intent);
                    }
                }
            });
        }
    }

    @OnLongClick({R.id.btn_ai_crosshair})
    public void onGameLongClick() {
        if (this.m_boAICrossHairOnOff) {
            show_AI_crosshair_offset_adjust_float();
        }
    }

    @OnClick({R.id.btn_ai_crosshair})
    public void onAICrosshair() {
        boolean z = !this.m_boAICrossHairOnOff;
        this.m_boAICrossHairOnOff = z;
        m_ini.put(Constants.CFG_AI_CROSSHAIR_ONOFF, z);
        if (this.m_boAICrossHairOnOff) {
            FloatMgr.init_aim_float(0);
            MsgBox.msg_box1_once(R.string.ai_crosshair_info);
        } else {
            Toast.makeText(this, getString(R.string.ai_crosshair_off), 0).show();
            FloatMgr.hide_aim_float();
        }
        set_cross_hair_button(this.m_btnAICrossHair, this.m_boAICrossHairOnOff ? 1 : 0);
        if (this.m_btnAIFloatCrosshair != null) {
            update_AI_result_ctrls();
        }
    }

    @OnLongClick({R.id.m_btnAIXScope})
    public void onXScopeLongPress() {
        if (this.m_boAIXScope.booleanValue()) {
            show_xscope_adjust_float(true);
        }
    }

    @OnClick({R.id.m_btnAIXScope})
    public void onAIXScope() {
        Boolean valueOf = Boolean.valueOf(!this.m_boAIXScope.booleanValue());
        this.m_boAIXScope = valueOf;
        m_ini.put(Constants.CFG_AI_XSCOPE_ONOFF, valueOf.booleanValue());
        ImageSwButton.set_onoff(this.m_btnAIXScope, this.m_boAIXScope.booleanValue());
        if (this.m_boAIXScope.booleanValue()) {
            MsgBox.msg_box1_once(R.string.ai_xscope_info);
        }
    }

    @OnLongClick({R.id.m_btn1stBulletOptimize})
    public void on1stBulletOptimizeLongPress() {
        new AI1stBulletOptFloat().show_float(this);
        this.m_byOldGun = (byte) -1;
    }

    @OnClick({R.id.m_btn1stBulletOptimize})
    public void on1stBulletOptimize() {
        Boolean valueOf = Boolean.valueOf(!this.m_bo1stBulletOptimize.booleanValue());
        this.m_bo1stBulletOptimize = valueOf;
        m_ini.put(Constants.CFG_AI_1ST_BULLET_OPTIMIZE, valueOf.booleanValue());
        ImageSwButton.set_onoff(this.m_btn1stBulletOptimize, this.m_bo1stBulletOptimize.booleanValue());
        bleSetAIGunDownOtherParams();
        if (this.m_bo1stBulletOptimize.booleanValue()) {
            MsgBox.msg_box1_with_choice2(this, R.string.ai_1st_bullet_optimize, R.string.iamknow, R.string.ineedvideo, this.m_webHandler.set_url(Constants.URL_VIDEO_1ST_GUN));
        }
    }

    @OnLongClick({R.id.m_btnAutoActiveRun})
    public void onAutoActiveRunLongPress() {
        if (this.m_boAutoActiveRun.booleanValue()) {
            new AIActiveRunFloat().show_float(this);
        }
    }

    @OnClick({R.id.m_btnAutoActiveRun})
    public void onAutoActiveRun() {
        Boolean valueOf = Boolean.valueOf(!this.m_boAutoActiveRun.booleanValue());
        this.m_boAutoActiveRun = valueOf;
        m_ini.put(Constants.CFG_AI_AUTO_ACTIVE_RUN, valueOf.booleanValue());
        ImageSwButton.set_onoff(this.m_btnAutoActiveRun, this.m_boAutoActiveRun.booleanValue());
        if (this.m_boAutoActiveRun.booleanValue()) {
            MsgBox.msg_box1_with_choice2(this, R.string.ai_auto_active_run_tip, R.string.iamknow, R.string.ineedvideo, this.m_webHandler.set_url(Constants.URL_VIDEO_HOUYAO));
        }
    }

    @OnClick({R.id.m_btnQianliyan})
    public void onQianliyanPress() {
        MsgBox.msg_box1_with_choice2(this, R.string.qianliyan_tip, R.string.iamknow, R.string.ineedvideo, this.m_webHandler.set_url(Constants.URL_VIDEO_QIANLIYAN));
    }

    @OnClick({R.id.m_btnTPPFPP})
    public void onSmartTPPFPP() {
        MsgBox.msg_box1_with_choice2(this, R.string.smart_tppfpp_tip, R.string.iamknow, R.string.ineedvideo, this.m_webHandler.set_url(Constants.URL_VIDEO_SMART_13));
    }

    @OnLongClick({R.id.m_btnTPPFPP})
    public void onSmartFPPLongPress() {
        new AISmartFPPFloat().show_float(this);
        this.m_byOldGun = (byte) -1;
    }

    @OnClick({R.id.m_btnSmartQE})
    public void onSmartQE() {
        byte b;
        byte b2 = this.mSystemStatus[16];
        if (Units.is_bit_on(b2, 6)) {
            b = Units.clear_bit(b2, 6);
            SmartQE smartQE = this.m_smartQE;
            if (smartQE != null) {
                smartQE.show_float(false);
            }
        } else {
            b = Units.set_bit(b2, 6);
            if (this.m_smartQE == null) {
                this.m_smartQE = new SmartQE(this, this.mThroneService);
            }
            this.m_smartQE.bleSendAISmartQEParams();
            MsgBox.msg_box_with_never_remind_once_choice2(this, R.string.smart_qe_tip, "smart_qe_tip", R.string.iamknow, R.string.ineedvideo, this.m_webHandler.set_url(Constants.URL_VIDEO_SMART_QE));
        }
        this.mSystemStatus[16] = b;
        send_system_status();
        ImageSwButton.set_onoff(this.m_btnSmartQE, Units.is_bit_on(b, 6));
    }

    @OnLongClick({R.id.m_btnSmartQE})
    public void onSmartQELongPress() {
        if (this.m_smartQE == null) {
            this.m_smartQE = new SmartQE(this, this.mThroneService);
        }
        this.m_smartQE.show_float(true);
    }

    @OnLongClick({R.id.m_btnQianliyan})
    public void onQianliyanLongPress() {
        if (this.m_qianliyan == null) {
            this.m_qianliyan = new Qianliyan(this, this.mHandler);
        }
        this.m_qianliyan.show_adjust_float();
    }

    @OnCheckedChanged({R.id.ai})
    public void onAI(SwitchCompat switchCompat, boolean z) {
        MediaProjection mediaProjection;
        if (this.m_boDevBanned) {
            show_ban_msgbox();
        } else if (check_device_connection() && this.m_boAIModelInitFinished) {
            if (z) {
                if (!this.m_sSysStatus.has_AI_capacity()) {
                    MsgBox.msg_box1((int) R.string.func_disable_in_cur_dev);
                    this.mAI.setChecked(false);
                    update_AI_period_controls();
                    return;
                } else if (this.mSystemStatus[4] < 70) {
                    MsgBox.msg_box1((int) R.string.func_disable_in_cur_ver);
                    this.mAI.setChecked(false);
                    update_AI_period_controls();
                    return;
                }
            }
            this.mIsRecording = z;
            if (z) {
                if (Build.VERSION.SDK_INT >= 21) {
                    startActivityForResult(this.mProjectionManager.createScreenCaptureIntent(), 0);
                    return;
                }
                return;
            }
            if (Build.VERSION.SDK_INT >= 21 && (mediaProjection = this.mMediaProjection) != null) {
                mediaProjection.stop();
            }
            EasyFloat.dismiss();
            update_AI_period_controls();
            EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_RESULT);
            EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_STAGE);
            if (this.m_boDynamicGunPressOnOff) {
                show_dynamic_gun_press_float(true);
            }
            IThrone iThrone = this.mThroneService;
            if (iThrone != null) {
                try {
                    iThrone.bleSetAIOff();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String copy_backup_file(Uri uri) {
        String str = null;
        try {
            InputStream openInputStream = getContentResolver().openInputStream(uri);
            str = NEAT.get_path_name(uri.getPath());
            NEAT.copy_stream(openInputStream, new FileOutputStream("/storage/emulated/0/MyBackup/" + str));
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        String str = TAG;
        Log.d(str, "onNewIntent:" + intent.getData());
        init_open_file(intent.getData());
        super.onNewIntent(intent);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = configuration.orientation;
    }

    protected void init_image_sw_buttons() {
        ImageSwButton.add(this.m_btnAIDynamicOnOff, R.mipmap.dynamic_on, R.mipmap.dynamic_off, R.mipmap.dynamic_on_en, R.mipmap.dynamic_off_en);
        ImageSwButton.add(this.m_btnAIContinuesShootOnOff, R.mipmap.continues_shoot_on, R.mipmap.continues_shoot_off, R.mipmap.continues_shoot_on_en, R.mipmap.continues_shoot_off_en);
        ImageSwButton.add(this.m_btnDynamicGun, R.mipmap.dynamic_on, R.mipmap.dynamic_off, R.mipmap.dynamic_on_en, R.mipmap.dynamic_off_en);
        ImageSwButton.add(this.m_btnAIXScope, R.mipmap.xscope_on, R.mipmap.xscope_off, R.mipmap.xscope_on_en, R.mipmap.xscope_off_en);
        ImageSwButton.add(this.m_btn1stBulletOptimize, R.mipmap.first_gun_opt_on, R.mipmap.first_gun_opt_off, R.mipmap.first_gun_opt_on_en, R.mipmap.first_gun_opt_off_en);
        ImageSwButton.add(this.m_btnAutoActiveRun, R.mipmap.frozen_break_on, R.mipmap.frozen_break_off, R.mipmap.frozen_break_on_en, R.mipmap.frozen_break_off_en);
        ImageSwButton.add(this.m_btnSmartQE, R.mipmap.smart_qe_on, R.mipmap.smart_qe_off, R.mipmap.smart_qe_on_en, R.mipmap.smart_qe_off_en);
        ImageSwButton.set_onoff(this.m_btnAIDynamicOnOff, this.m_boAIDynamic);
        ImageSwButton.set_onoff(this.m_btnDynamicGun, false);
        ImageSwButton.set_onoff(this.m_btnAIXScope, this.m_boAIXScope.booleanValue());
        ImageSwButton.set_onoff(this.m_btn1stBulletOptimize, this.m_bo1stBulletOptimize.booleanValue());
        ImageSwButton.set_onoff(this.m_btnAutoActiveRun, this.m_boAutoActiveRun.booleanValue());
        if (AppInstance.m_boIsChineseOS) {
            return;
        }
        this.m_btnFireSense.setBackground(getResources().getDrawable(R.mipmap.fire_sense_en));
        this.m_btnQianliyan.setBackground(getResources().getDrawable(R.mipmap.qianliyan_en));
        this.m_btnSmartQE.setBackground(getResources().getDrawable(R.mipmap.smart_qe_off_en));
    }

    int get_cur_AI_gun_down_percent() {
        SPUtils sPUtils = m_ini;
        return sPUtils.getInt(Constants.CFG_AI_GUN_DOWN_PERCENT + AppInstance.s_nAIKit, 100);
    }

    void set_cur_AI_gun_down_percent(int i) {
        SPUtils sPUtils = m_ini;
        sPUtils.put(Constants.CFG_AI_GUN_DOWN_PERCENT + AppInstance.s_nAIKit, i);
    }

    protected void init_variables() {
        this.m_boInNTSMode = m_ini.getBoolean(Constants.CFG_NTS_MODE, false);
        Constants.init();
        DynamicGunData.init(Constants.E_GAME_NAME_HPJY);
        this.m_crossDynamicScopeOff = DynamicGunData.get_scope_off_crosshair();
        byte[] bArr = this.m_arrSendSystemStatus;
        bArr[0] = -1;
        bArr[1] = -123;
        bArr[2] = (byte) (bArr.length - 4);
        bArr[3] = 0;
        Arrays.fill(this.mSystemStatus, (byte) 0);
        this.m_boAIToAllScope = m_ini.getBoolean(Constants.CFG_TO_ALL_SCOPE, true);
        this.m_boAICrossHairOnOff = m_ini.getBoolean(Constants.CFG_AI_CROSSHAIR_ONOFF, false);
        this.m_nAIGunDownPercent = get_cur_AI_gun_down_percent();
        AppInstance.m_boIsChineseOS = NEAT.is_chinese();
        AppInstance.s_strAppVer = Units.getVersionName(this);
        AppInstance.s_colorRed = getResources().getColor(R.color.colorRed);
        AppInstance.s_colorGreen = getResources().getColor(R.color.colorGreen);
        this.m_boAIDynamic = m_ini.getBoolean(Constants.CFG_AI_DYNAMIC_ONOFF, true);
        this.m_boGunPartsAI = m_ini.getBoolean(Constants.CFG_GUN_PARTS_AI, false);
        this.m_boAIXScope = Boolean.valueOf(m_ini.getBoolean(Constants.CFG_AI_XSCOPE_ONOFF, false));
        this.m_bo1stBulletOptimize = Boolean.valueOf(m_ini.getBoolean(Constants.CFG_AI_1ST_BULLET_OPTIMIZE, true));
        this.m_boAutoActiveRun = Boolean.valueOf(m_ini.getBoolean(Constants.CFG_AI_AUTO_ACTIVE_RUN, false));
        ViewInfo viewInfo = ViewMgr.get_view_info_for_AI();
        ViewMgr.getInstance();
        s_vInfo = ViewMgr.getViewInfo();
        this.m_mapAIFloat.put(Constants.FLOAT_TAG_AI_GUN1, new AIFloatInfo(Constants.FLOAT_TAG_AI_GUN1, s_vInfo.gun1, viewInfo.gun1));
        this.m_mapAIFloat.put(Constants.FLOAT_TAG_AI_GUN2, new AIFloatInfo(Constants.FLOAT_TAG_AI_GUN2, s_vInfo.gun2, viewInfo.gun2));
        this.m_mapAIFloat.put(Constants.FLOAT_TAG_AI_MIRROW, new AIFloatInfo(Constants.FLOAT_TAG_AI_MIRROW, s_vInfo.mirrorDTO, viewInfo.mirrorDTO));
        this.m_mapAIFloat.put(Constants.FLOAT_TAG_AI_C, new AIFloatInfo(Constants.FLOAT_TAG_AI_C, s_vInfo.squatDTO, viewInfo.squatDTO));
        this.m_mapAIFloat.put(Constants.FLOAT_TAG_AI_Z, new AIFloatInfo(Constants.FLOAT_TAG_AI_Z, s_vInfo.downDTO, viewInfo.downDTO));
        this.m_mapAIFloat.put(Constants.FLOAT_TAG_SCOPE2, new AIFloatInfo(Constants.FLOAT_TAG_SCOPE2, s_vInfo.doubleMirror, viewInfo.doubleMirror));
        this.m_mapAIFloat.put(Constants.FLOAT_TAG_AI_BAG_TAG, new AIFloatInfo(Constants.FLOAT_TAG_AI_BAG_TAG, s_vInfo.bagTag, viewInfo.bagTag));
        this.m_mapAIFloat.put(Constants.FLOAT_TAG_AI_TAKE_OFF_TAG, new AIFloatInfo(Constants.FLOAT_TAG_AI_TAKE_OFF_TAG, s_vInfo.takeOffTag, viewInfo.takeOffTag));
        this.m_mapAIFloat.put(Constants.FLOAT_TAG_AI_GUN_HEAD1, new AIFloatInfo(Constants.FLOAT_TAG_AI_GUN_HEAD1, s_vInfo.gun1Head, viewInfo.gun1Head));
        this.m_mapAIFloat.put(Constants.FLOAT_TAG_AI_GUN_HEAD2, new AIFloatInfo(Constants.FLOAT_TAG_AI_GUN_HEAD2, s_vInfo.gun2Head, viewInfo.gun2Head));
        this.m_mapAIFloat.put(Constants.FLOAT_TAG_AI_GUN_HANDLE1, new AIFloatInfo(Constants.FLOAT_TAG_AI_GUN_HANDLE1, s_vInfo.gun1Handle, viewInfo.gun1Handle));
        this.m_mapAIFloat.put(Constants.FLOAT_TAG_AI_GUN_HANDLE2, new AIFloatInfo(Constants.FLOAT_TAG_AI_GUN_HANDLE2, s_vInfo.gun2Handle, viewInfo.gun2Handle));
        this.m_mapAIFloat.put(Constants.FLOAT_TAG_AI_GUN_TAIL1, new AIFloatInfo(Constants.FLOAT_TAG_AI_GUN_TAIL1, s_vInfo.gun1Tail, viewInfo.gun1Tail));
        this.m_mapAIFloat.put(Constants.FLOAT_TAG_AI_GUN_TAIL2, new AIFloatInfo(Constants.FLOAT_TAG_AI_GUN_TAIL2, s_vInfo.gun2Tail, viewInfo.gun2Tail));
        int[] read_ini_int_array = Util.read_ini_int_array(m_ini, Constants.CFG_FIRE_MOUSE_SENSE);
        if (read_ini_int_array != null) {
            this.m_arrAIFireSense = read_ini_int_array;
        }
    }

    protected void init_AI_period_controls() {
        int i = SPUtils.getInstance().getInt(Constants.CFG_AI_PERIOD);
        if (-1 != i && i >= 50 && i <= 300) {
            m_nAIPeriod = i;
        }
        this.m_aiPeriodLayer = findViewById(R.id.ai_period_layer);
        SeekBar seekBar = (SeekBar) findViewById(R.id.ai_period_slider);
        this.m_ctlAIPeriodSlider = seekBar;
        seekBar.setMax(350);
        this.m_ctlAIPeriodSlider.setProgress(m_nAIPeriod - 50);
        TextView textView = (TextView) findViewById(R.id.ai_period_text);
        this.m_textAIPeriod = textView;
        textView.setText("" + m_nAIPeriod);
        Button button = (Button) findViewById(R.id.m_btnAIGunDownPercent);
        this.m_btnAIGunDownPercent = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.36
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Boolean.valueOf(MsgBox.msg_box_with_never_remind_once_choice1(AppInstance.s_context, R.string.ai_gun_down_percent_msg, "ai_gun_down_percent_msg", R.string.iamknow, new Handler() { // from class: com.baidu.kwgames.MainActivity.36.1
                    @Override // android.os.Handler
                    public void handleMessage(Message message) {
                        MainActivity.this.show_AI_gun_press_percent_float();
                    }
                })).booleanValue()) {
                    return;
                }
                MainActivity.this.show_AI_gun_press_percent_float();
            }
        });
        this.m_ctlAIPeriodSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.baidu.kwgames.MainActivity.37
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i2, boolean z) {
                int unused = MainActivity.m_nAIPeriod = MainActivity.this.m_ctlAIPeriodSlider.getProgress() + 50;
                TextView textView2 = MainActivity.this.m_textAIPeriod;
                textView2.setText("" + MainActivity.m_nAIPeriod);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
                MainActivity.this.hideAllSeeckAddOrReduce();
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
                int unused = MainActivity.m_nAIPeriod = MainActivity.this.m_ctlAIPeriodSlider.getProgress() + 50;
                TextView textView2 = MainActivity.this.m_textAIPeriod;
                textView2.setText("" + MainActivity.m_nAIPeriod);
                SPUtils.getInstance().put(Constants.CFG_AI_PERIOD, MainActivity.m_nAIPeriod);
            }
        });
    }

    public void update_AI_period_controls() {
        if (this.mAI.isChecked()) {
            set_all_AI_relate_controls_visible(true);
            set_cross_hair_button(this.m_btnAICrossHair, this.m_boAICrossHairOnOff ? 1 : 0);
            FloatMgr.init_aim_float(1);
            this.m_btnAIGunDownPercent.setVisibility(this.m_sSysStatus.has_AI_gun_down_percent_capacity() ? 0 : 8);
            Button button = this.m_btnAIGunDownPercent;
            button.setText(NEAT.s(R.string.ai_gun_down_percent_prefix) + NEAT.int_to_string(this.m_nAIGunDownPercent) + "%");
            this.m_aiPeriodLayer.setVisibility(0);
            AppInstance.m_boAIWorking = true;
        } else {
            set_all_AI_relate_controls_visible(false);
            this.m_aiPeriodLayer.setVisibility(8);
            AppInstance.m_boAIWorking = false;
        }
        update_gun_down_visible();
    }

    protected void init_mouse_pointer_controls() {
        final SeekBar seekBar = (SeekBar) findViewById(R.id.mouse_pointer_sensitivity);
        this.mMousePointerReduce = (Button) findViewById(R.id.mouse_pointer_reduce);
        this.mMousePointerAdd = (Button) findViewById(R.id.mouse_pointer_add);
        final TextView textView = (TextView) findViewById(R.id.mouse_pointer_sensitivity_text);
        textView.setText("" + seekBar.getProgress());
        this.mMousePointerReduce.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.38
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int i;
                int progress = seekBar.getProgress();
                if (progress + 10 <= 10) {
                    return;
                }
                seekBar.setProgress(progress - 1);
                MainActivity.this.mSystemStatus[7] = (byte) (seekBar.getProgress() + 10);
                MainActivity.this.send_system_status();
                textView.setText("" + (i + 10));
            }
        });
        this.mMousePointerAdd.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.39
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int progress = seekBar.getProgress();
                if (progress + 10 >= 64) {
                    return;
                }
                int i = progress + 1;
                seekBar.setProgress(i);
                MainActivity.this.mSystemStatus[7] = (byte) (seekBar.getProgress() + 10);
                MainActivity.this.send_system_status();
                TextView textView2 = textView;
                textView2.setText("" + (i + 10));
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.baidu.kwgames.MainActivity.40
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                TextView textView2 = textView;
                textView2.setText("" + (i + 10));
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
                MainActivity.this.hideAllSeeckAddOrReduce();
                MainActivity.this.mMousePointerSensitivityTracking = true;
                MainActivity.this.mMousePointerAdd.setVisibility(0);
                MainActivity.this.mMousePointerReduce.setVisibility(0);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
                MainActivity.this.mSystemStatus[7] = (byte) (seekBar2.getProgress() + 10);
                MainActivity.this.send_system_status();
                MainActivity.this.mMousePointerSensitivityTracking = false;
            }
        });
    }

    protected void init_mouse_move_controls() {
        final SeekBar seekBar = (SeekBar) findViewById(R.id.mouse_move_sensitivity);
        this.mMouseMoveReduce = (Button) findViewById(R.id.mouse_move_reduce);
        this.mMouseMoveAdd = (Button) findViewById(R.id.mouse_move_add);
        final TextView textView = (TextView) findViewById(R.id.mouse_move_sensitivity_text);
        textView.setText("" + seekBar.getProgress());
        this.mMouseMoveReduce.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.41
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int i;
                int progress = seekBar.getProgress();
                if (progress + 10 <= 10) {
                    return;
                }
                seekBar.setProgress(progress - 1);
                MainActivity.this.mSystemStatus[6] = (byte) (seekBar.getProgress() + 10);
                MainActivity.this.send_system_status();
                textView.setText("" + (i + 10));
            }
        });
        this.mMouseMoveAdd.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.42
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int progress = seekBar.getProgress();
                if (progress + 10 >= 64) {
                    return;
                }
                int i = progress + 1;
                seekBar.setProgress(i);
                MainActivity.this.mSystemStatus[6] = (byte) (seekBar.getProgress() + 10);
                MainActivity.this.send_system_status();
                TextView textView2 = textView;
                textView2.setText("" + (i + 10));
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.baidu.kwgames.MainActivity.43
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                TextView textView2 = textView;
                textView2.setText("" + (i + 10));
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
                MainActivity.this.hideAllSeeckAddOrReduce();
                MainActivity.this.mMouseMoveAdd.setVisibility(0);
                MainActivity.this.mMouseMoveReduce.setVisibility(0);
                MainActivity.this.mMouseMoveSensitivityTracking = true;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
                MainActivity.this.mSystemStatus[6] = (byte) (seekBar2.getProgress() + 10);
                MainActivity.this.send_system_status();
                MainActivity.this.mMouseMoveSensitivityTracking = false;
            }
        });
        this.m_btnTouchSenseYPercent.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void show_touch_sense_Y_percent_float() {
        if (EasyFloat.getAppFloatView(Constants.FLOAT_TAG_TOUCH_SENSE_Y_PERCENT) != null) {
            EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_TOUCH_SENSE_Y_PERCENT);
        } else {
            EasyFloat.with(this).setTag(Constants.FLOAT_TAG_TOUCH_SENSE_Y_PERCENT).setGravity(49).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setLayout(R.layout.float_ai_gun_down_percent, new AnonymousClass44(Constants.FLOAT_TAG_TOUCH_SENSE_Y_PERCENT)).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.MainActivity$44  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass44 implements OnInvokeView {
        final /* synthetic */ String val$strTag;

        AnonymousClass44(String str) {
            this.val$strTag = str;
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            MainActivity.this.m_textTouchYPercent = (TextView) view.findViewById(R.id.m_textGunDownPercent);
            ((TextView) view.findViewById(R.id.title)).setText(NEAT.s(R.string.touch_sense_y_title));
            MainActivity.this.update_touch_Y_sence_percent_ctrls();
            FloatMgr.resetVirtualMouse();
            View findViewById = view.findViewById(R.id.close_window);
            final String str = this.val$strTag;
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$44$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    EasyFloat.dismissAppFloat(str);
                }
            });
            view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.44.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.m_sSysStatus.nTouchSensYRatio < 200) {
                        MainActivity.this.m_sSysStatus.nTouchSensYRatio++;
                        MainActivity.this.mSystemStatus[18] = (byte) MainActivity.this.m_sSysStatus.nTouchSensYRatio;
                        MainActivity.this.send_system_status();
                        MainActivity.this.update_touch_Y_sence_percent_ctrls();
                    }
                }
            });
            view.findViewById(R.id.reduce).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.44.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.m_sSysStatus.nTouchSensYRatio > 20) {
                        SystemStatus systemStatus = MainActivity.this.m_sSysStatus;
                        systemStatus.nTouchSensYRatio--;
                        MainActivity.this.mSystemStatus[18] = (byte) MainActivity.this.m_sSysStatus.nTouchSensYRatio;
                        MainActivity.this.send_system_status();
                        MainActivity.this.update_touch_Y_sence_percent_ctrls();
                    }
                }
            });
        }
    }

    void update_touch_Y_sence_percent_ctrls() {
        String str = "Y=" + this.m_sSysStatus.nTouchSensYRatio + "%";
        TextView textView = this.m_textTouchYPercent;
        if (textView != null) {
            textView.setText(str);
        }
        this.m_btnTouchSenseYPercent.setText(str);
    }

    protected void init_gun1_down_controls() {
        final SeekBar seekBar = (SeekBar) findViewById(R.id.gun1_sensitivity);
        this.mGun1Reduce = (Button) findViewById(R.id.gun1_reduce);
        this.mGun1Add = (Button) findViewById(R.id.gun1_add);
        final TextView textView = (TextView) findViewById(R.id.gun1_sensitivity_text);
        textView.setText("" + seekBar.getProgress());
        this.m_btnDynamicGun.setVisibility(8);
        update_dynamic_gun_down_parts_ctrls();
        this.mGun1Reduce.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.45
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int progress = seekBar.getProgress();
                if (progress <= 0) {
                    return;
                }
                int i = progress - 1;
                seekBar.setProgress(i);
                MainActivity.this.mSystemStatus[8] = (byte) seekBar.getProgress();
                MainActivity.this.send_system_status();
                textView.setText("" + i);
            }
        });
        this.mGun1Add.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.46
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int progress = seekBar.getProgress();
                if (progress >= MainActivity.this.m_nGunDownMax) {
                    return;
                }
                int i = progress + 1;
                seekBar.setProgress(i);
                MainActivity.this.mSystemStatus[8] = (byte) seekBar.getProgress();
                MainActivity.this.send_system_status();
                TextView textView2 = textView;
                textView2.setText("" + i);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.baidu.kwgames.MainActivity.47
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                TextView textView2 = textView;
                textView2.setText("" + i);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
                MainActivity.this.hideAllSeeckAddOrReduce();
                MainActivity.this.mGun1SensitivityTracking = true;
                MainActivity.this.mGun1Reduce.setVisibility(0);
                MainActivity.this.mGun1Add.setVisibility(0);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
                MainActivity.this.mSystemStatus[8] = (byte) seekBar2.getProgress();
                MainActivity.this.send_system_status();
                MainActivity.this.mGun1SensitivityTracking = false;
            }
        });
    }

    public void update_gun_down_visible() {
        TextView textView = (TextView) findViewById(R.id.gun1_down_title);
        TextView textView2 = (TextView) findViewById(R.id.gun1_sensitivity_text);
        int i = 0;
        int i2 = (this.mAI.isChecked() || (this.m_boSupportDynamicGunPress && this.m_boDynamicGunPressOnOff)) ? 8 : 0;
        textView.setVisibility(this.mAI.isChecked() ? 8 : 0);
        textView.setText((this.m_boSupportDynamicGunPress && this.m_boDynamicGunPressOnOff) ? R.string.device_gun_press : R.string._1);
        this.mGun1Sensitivity.setVisibility(i2);
        textView2.setVisibility(i2);
        TextView textView3 = (TextView) findViewById(R.id.gun2_down_title);
        TextView textView4 = (TextView) findViewById(R.id.gun2_sensitivity_text);
        int i3 = (this.mAI.isChecked() || (this.m_boSupportDynamicGunPress && this.m_boDynamicGunPressOnOff)) ? 8 : 0;
        textView3.setVisibility(i3);
        this.mGun2Sensitivity.setVisibility(i3);
        textView4.setVisibility(i3);
        this.m_btnDynamicGun.setVisibility((this.mAI.isChecked() || !this.m_boSupportDynamicGunPress) ? 8 : 8);
        update_dynamic_gun_down_parts_ctrls();
        if (this.mAI.isChecked()) {
            hideAllSeeckAddOrReduce();
        }
    }

    protected void init_gun2_down_controls() {
        final SeekBar seekBar = (SeekBar) findViewById(R.id.gun2_sensitivity);
        this.mGun2Reduce = (Button) findViewById(R.id.gun2_reduce);
        this.mGun2Add = (Button) findViewById(R.id.gun2_add);
        final TextView textView = (TextView) findViewById(R.id.gun2_sensitivity_text);
        textView.setText("" + seekBar.getProgress());
        this.mGun2Reduce.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.48
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int progress = seekBar.getProgress();
                if (progress <= 0) {
                    return;
                }
                int i = progress - 1;
                seekBar.setProgress(i);
                MainActivity.this.mSystemStatus[9] = (byte) seekBar.getProgress();
                MainActivity.this.send_system_status();
                textView.setText("" + i);
            }
        });
        this.mGun2Add.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.49
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int progress = seekBar.getProgress();
                if (progress >= MainActivity.this.m_nGunDownMax) {
                    return;
                }
                int i = progress + 1;
                seekBar.setProgress(i);
                MainActivity.this.mSystemStatus[9] = (byte) seekBar.getProgress();
                MainActivity.this.send_system_status();
                TextView textView2 = textView;
                textView2.setText("" + i);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.baidu.kwgames.MainActivity.50
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                TextView textView2 = textView;
                textView2.setText("" + i);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
                MainActivity.this.hideAllSeeckAddOrReduce();
                MainActivity.this.mGun2Reduce.setVisibility(0);
                MainActivity.this.mGun2Add.setVisibility(0);
                MainActivity.this.mGun2SensitivityTracking = true;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
                MainActivity.this.mSystemStatus[9] = (byte) seekBar2.getProgress();
                MainActivity.this.send_system_status();
                MainActivity.this.mGun2SensitivityTracking = false;
            }
        });
    }

    void init_other_controls() {
        if (NEAT.is_chinese()) {
            this.m_btnFacebook.setVisibility(8);
        } else {
            this.m_btnAdvice.setVisibility(8);
        }
    }

    protected void init_open_file() {
        init_open_file(getIntent().getData());
    }

    protected void init_open_file(Uri uri) {
        if (uri != null) {
            String copy_backup_file = copy_backup_file(uri);
            this.m_strMybpName = copy_backup_file;
            if (copy_backup_file != null) {
                this.m_timerHandler.postDelayed(new Runnable() { // from class: com.baidu.kwgames.MainActivity.51
                    @Override // java.lang.Runnable
                    public void run() {
                        Intent intent = new Intent(AppInstance.s_context, BackupActivity.class);
                        intent.putExtra("open", MainActivity.this.m_strMybpName);
                        MainActivity.this.startActivityForResult(intent, 5);
                    }
                }, 100L);
            }
        }
    }

    protected void init_resolution() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
            this.m_nScreenWidth = displayMetrics.widthPixels;
            this.m_nScreenHeight = displayMetrics.heightPixels;
        } else {
            this.m_nScreenWidth = displayMetrics.heightPixels;
            this.m_nScreenHeight = displayMetrics.widthPixels;
        }
        AppInstance.s_nScreenW = this.m_nScreenWidth;
        AppInstance.s_nScreenH = this.m_nScreenHeight;
        this.mDpi = displayMetrics.densityDpi;
        Units.init();
        if (Units.getSystemModel().equals("PEUM00")) {
            if (1792 == this.m_nScreenHeight && 1920 == this.m_nScreenWidth) {
                return;
            }
            MsgBox.msg_box1((int) R.string.oppo_fold_screen_remind);
        }
    }

    protected void init_adb_exe() {
        try {
            File file = new File(Constants.ADB_DIR);
            if (!file.exists()) {
                file.mkdirs();
            }
            int i = m_ini.getInt("adbver", 0);
            File file2 = new File("/storage/emulated/0/SincoGamer/sinco.bash");
            File file3 = new File("/storage/emulated/0/SincoGamer/sincoserver.dex");
            if (file2.exists() && file3.exists() && i == 16) {
                return;
            }
            if (file3.exists()) {
                file3.delete();
            }
            if (ResourceUtils.copyFileFromAssets("adb", Constants.ADB_DIR)) {
                m_ini.put("adbver", 16);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void init_adb() {
        init_adb_exe();
        try {
            this.m_adb = new ADB(new Handler() { // from class: com.baidu.kwgames.MainActivity.53
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    super.handleMessage(message);
                    if (message.arg1 == 0) {
                        MainActivity.this.m_boMTKActived = message.arg2 == 1;
                        MainActivity.this.update_connect_status_text();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void register_receiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ai.iomega.CAPTURE");
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_ACCESSORY_DETACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
        registerReceiver(this.mReceiver, intentFilter);
    }

    void unregister_receiver() {
        unregisterReceiver(this.mReceiver);
    }

    protected void init_keymap() {
        KeyMaps.init();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AppInstance.s_context = this;
        AppInstance.g_sSysStatus = this.m_sSysStatus;
        AppInstance.s_nAIKit = get_cur_AI_kit();
        if (AppInstance.s_nAIKit >= 5) {
            AppInstance.s_nAIKit = 0;
        }
        this.m_webHandler = new WebHandler(this);
        init_resolution();
        verifyPermissions();
        AIConvert.init();
        FloatMgr.init();
        AICrosshair.init();
        AIXScope.init();
        AIActiveRun.init();
        AISmartFPP.init();
        AI1stBulletOpt.init();
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        init_variables();
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.layoutInDisplayCutoutMode = 1;
            getWindow().setAttributes(attributes);
        }
        setContentView(R.layout.activity_main);
        this.game1RightTip = findViewById(R.id.game1_right_tip);
        this.game1LeftTip = findViewById(R.id.game1_left_tip);
        this.mMarqueeTextview = (TextView) findViewById(R.id.marquee_textview);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        hide_status_navigate_bar();
        init_image_sw_buttons();
        init_game_buttons();
        update_bg_image();
        init_adb();
        new Thread(new Runnable() { // from class: com.baidu.kwgames.MainActivity.55
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.init_keymap();
                MainActivity.this.load_gun_model();
                MainActivity.this.load_scope_model();
                MainActivity.this.load_bag_tag_model();
                MainActivity.this.load_gun_head_model();
                MainActivity.this.load_gun_handle_model();
                MainActivity.this.load_gun_tail_model();
                MainActivity.this.load_other_model();
                MainActivity.this.init_system_preset();
                MainActivity.this.initGunAgile();
                MainActivity.this.m_boAIModelInitFinished = true;
            }
        }).start();
        init_AI_period_controls();
        update_AI_period_controls();
        init_mouse_pointer_controls();
        init_mouse_move_controls();
        init_gun1_down_controls();
        init_gun2_down_controls();
        init_other_controls();
        if (Build.VERSION.SDK_INT >= 21) {
            this.mProjectionManager = (MediaProjectionManager) getSystemService("media_projection");
        }
        register_receiver();
        ignoreBatteryOptimization(this);
        this.timingLogger = new TimingLogger(TIMING_LOGGER_TAG, "capture");
        on_ble_disconnected();
        getNotice();
        hideAllSeeckAddOrReduce();
        init_service();
        init_open_file();
        if (NEAT.get_float_permission(this)) {
            FloatMgr.initVirtualMouse();
        } else {
            MsgBox.msg_box1_with_choice1_no_cancel(this, R.string.float_permission_msgbox, R.string.go_certify, new Handler() { // from class: com.baidu.kwgames.MainActivity.56
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    FloatMgr.initVirtualMouse();
                    MainActivity.this.show_float_ball();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideAllSeeckAddOrReduce() {
        this.mGun1Add.setVisibility(4);
        this.mGun1Reduce.setVisibility(4);
        this.mGun2Add.setVisibility(4);
        this.mGun2Reduce.setVisibility(4);
        this.mMouseMoveAdd.setVisibility(4);
        this.mMouseMoveReduce.setVisibility(4);
        this.mMousePointerReduce.setVisibility(4);
        this.mMousePointerAdd.setVisibility(4);
    }

    private void getNotice() {
        HttpHelper.request(Constants.URL_NOTICE_NEW).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<String>() { // from class: com.baidu.kwgames.MainActivity.57
            @Override // io.reactivex.functions.Consumer
            public void accept(String str) throws Exception {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                try {
                    NoticeRsq noticeRsq = (NoticeRsq) new Gson().fromJson(str, (Class<Object>) NoticeRsq.class);
                    String language = MainActivity.this.getResources().getConfiguration().locale.getLanguage();
                    if (language.equals("zh")) {
                        if (!TextUtils.isEmpty(noticeRsq.notice.zh)) {
                            if (noticeRsq.notice.strID != null && !noticeRsq.notice.strID.isEmpty()) {
                                MsgBox.msg_box_with_never_remind(noticeRsq.notice.zh, noticeRsq.notice.strID);
                            } else {
                                MsgBox.msg_box1(noticeRsq.notice.zh);
                            }
                        }
                        if (!TextUtils.isEmpty(noticeRsq.topMessage.zh)) {
                            noticeRsq.topMessage.zh = noticeRsq.topMessage.zh.trim();
                            MainActivity.this.m_arrMarqueeText = noticeRsq.topMessage.zh.split("\\|");
                            MainActivity.this.begin_marquee_text_change_timer();
                        }
                    } else if (language.equals("th")) {
                        if (!TextUtils.isEmpty(noticeRsq.notice.th)) {
                            MsgBox.msg_box1(noticeRsq.notice.th);
                        }
                        if (!TextUtils.isEmpty(noticeRsq.topMessage.th)) {
                            noticeRsq.topMessage.th = noticeRsq.topMessage.th.trim();
                            MainActivity.this.m_arrMarqueeText = noticeRsq.topMessage.th.split("\\|");
                            MainActivity.this.begin_marquee_text_change_timer();
                        }
                    } else if (language.equals("vi")) {
                        if (!TextUtils.isEmpty(noticeRsq.notice.vi)) {
                            MsgBox.msg_box1(noticeRsq.notice.vi);
                        }
                        if (!TextUtils.isEmpty(noticeRsq.topMessage.vi)) {
                            noticeRsq.topMessage.vi = noticeRsq.topMessage.vi.trim();
                            MainActivity.this.m_arrMarqueeText = noticeRsq.topMessage.vi.split("\\|");
                            MainActivity.this.begin_marquee_text_change_timer();
                        }
                    } else {
                        if (!TextUtils.isEmpty(noticeRsq.notice.en)) {
                            MsgBox.msg_box1(noticeRsq.notice.en);
                        }
                        if (!TextUtils.isEmpty(noticeRsq.topMessage.en)) {
                            noticeRsq.topMessage.en = noticeRsq.topMessage.en.trim();
                            MainActivity.this.m_arrMarqueeText = noticeRsq.topMessage.en.split("\\|");
                            MainActivity.this.begin_marquee_text_change_timer();
                        }
                    }
                    BanID.init(noticeRsq.notice.nBanIDVer, noticeRsq.notice.strMore);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Consumer<Throwable>() { // from class: com.baidu.kwgames.MainActivity.58
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
            }
        });
    }

    void show_next_marquee_text() {
        int i = this.m_nCurMarqueeText;
        String[] strArr = this.m_arrMarqueeText;
        if (i >= strArr.length) {
            this.m_nCurMarqueeText = 0;
        }
        int i2 = this.m_nCurMarqueeText;
        if (i2 < strArr.length) {
            this.mMarqueeTextview.setText(strArr[i2]);
            m_ini.put(Constants.CFG_MARQUEE_TEXT_INDEX, this.m_nCurMarqueeText);
        }
        this.m_nCurMarqueeText++;
    }

    public void begin_marquee_text_change_timer() {
        if (this.m_arrMarqueeText == null || this.m_boMarqueeTextChangeTimerRunning) {
            return;
        }
        try {
            this.m_nCurMarqueeText = m_ini.getInt(Constants.CFG_MARQUEE_TEXT_INDEX, 0);
            show_next_marquee_text();
            this.m_boMarqueeTextChangeTimerRunning = true;
            this.m_timerMarqueeTextChange.postDelayed(this.m_runableMarqueeTextChange, 60000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop_marquee_text_change_timer() {
        this.m_boMarqueeTextChangeTimerRunning = false;
        this.m_timerMarqueeTextChange.removeCallbacks(this.m_runableMarqueeTextChange);
    }

    public void onMarqueeTextClick(View view) {
        try {
            if (this.m_arrMarqueeText != null) {
                this.m_timerMarqueeTextChange.removeCallbacks(this.m_runableMarqueeTextChange);
                show_next_marquee_text();
                this.m_boMarqueeTextChangeTimerRunning = true;
                this.m_timerMarqueeTextChange.postDelayed(this.m_runableMarqueeTextChange, 60000L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: com.baidu.kwgames.MainActivity$60  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass60 implements Runnable {
        AnonymousClass60() {
        }

        @Override // java.lang.Runnable
        public void run() {
            new Thread(new Runnable() { // from class: com.baidu.kwgames.MainActivity$60$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AgileMgr.save();
                }
            }).start();
        }
    }

    public void begin_AI_param_save_timer() {
        this.m_timerHandler.removeCallbacks(this.m_runableSaveAgile);
        this.m_timerHandler.postDelayed(this.m_runableSaveAgile, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void set_cur_AI_gun_press(byte b) {
        byte b2 = this.m_byOldGun;
        int i = this.m_nCurGes;
        int i2 = 0;
        if (this.mKaijing) {
            int i3 = this.m_nCurScopeAiID;
            if (-1 != i3) {
                i2 = i3;
            } else if (this.m_boAIToAllScope) {
                i2 = 4;
            }
            i2 = Constants.scope_AI_id_2_ble_id(i2);
        }
        if (AgileMgr.set_gun_press(b2, i, i2, b, this.m_boAIToAllScope)) {
            if (this.mRecognTextview != null) {
                this.mAgileText.setText(String.valueOf((int) b));
            }
            begin_AI_param_save_timer();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-baidu-kwgames-MainActivity  reason: not valid java name */
    public /* synthetic */ void m47lambda$new$0$combaidukwgamesMainActivity() {
        Log.d(TAG, "HideAIResultCtrls@@@@\n");
        if (this.m_boAIStageEditing || !this.m_boShowAdjustButton) {
            return;
        }
        this.m_boShowAdjustButton = false;
        update_AI_result_ctrls();
    }

    public void begin_hide_AI_result_ctrls_timer() {
        stop_hide_AI_result_ctrls_timer();
        this.m_timerHandler.postDelayed(this.m_runableHideAIResultCtrls, 300000L);
    }

    public void stop_hide_AI_result_ctrls_timer() {
        this.m_timerHandler.removeCallbacks(this.m_runableHideAIResultCtrls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void update_AI_result_ctrls() {
        int i = 0;
        int i2 = this.m_boShowAdjustButton ? 0 : 8;
        this.mAgileText.setVisibility(i2);
        this.m_btnAIResultAdd.setVisibility(i2);
        this.m_btnAIResultReduce.setVisibility(i2);
        this.m_btnAIResultToAllScope.setVisibility(i2);
        this.m_btnAIResultAIStage.setVisibility((this.m_boShowAdjustButton && this.m_sSysStatus.has_w_gun_down_capacity()) ? 0 : 8);
        this.m_btnAIFloatCrosshair.setVisibility((this.m_boShowAdjustButton && this.m_boAICrossHairOnOff) ? 8 : 8);
    }

    void update_AI_float_crosshair_button() {
        Button button = this.m_btnAIFloatCrosshair;
        if (button != null) {
            if (-1 == this.m_byOldGun) {
                if (this.m_nAIFloatCrosshairStyle != 0) {
                    this.m_nAIFloatCrosshairStyle = 0;
                    if (this.m_boShowAdjustButton) {
                        set_cross_hair_button(button, 0);
                        this.m_boAIFloatCrosshairButtonBGChanged = false;
                    } else {
                        this.m_boAIFloatCrosshairButtonBGChanged = true;
                    }
                }
            } else {
                int i = AICrosshair.get_gun_crosshair(this.m_byOldGun, AICrosshair.ai_scope_to_crosshair_scope(this.m_nOldScopeAiID, this.mKaijing));
                if (i != this.m_nAIFloatCrosshairStyle) {
                    this.m_nAIFloatCrosshairStyle = i;
                    if (this.m_boShowAdjustButton) {
                        set_cross_hair_button(this.m_btnAIFloatCrosshair, i);
                        this.m_boAIFloatCrosshairButtonBGChanged = false;
                    } else {
                        this.m_boAIFloatCrosshairButtonBGChanged = true;
                    }
                }
            }
        }
        if (this.m_boShowAdjustButton && this.m_boAIFloatCrosshairButtonBGChanged) {
            set_cross_hair_button(this.m_btnAIFloatCrosshair, this.m_nAIFloatCrosshairStyle);
            this.m_boAIFloatCrosshairButtonBGChanged = false;
        }
    }

    private void showRecogn() {
        this.mRecognTextview = null;
        EasyFloat.Builder layout = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_RESULT).setGravity(49).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setLayout(R.layout.float_window_recogn, new AnonymousClass61());
        layout.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.62
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
            public void dismiss() {
                MainActivity.this.m_timerHandler.removeCallbacks(MainActivity.this.m_runableHideAIResultCtrls);
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.mRecognRoot.getLocationOnScreen(iArr);
                if (MainActivity.s_vInfo.tipMask != null) {
                    MainActivity.s_vInfo.tipMask.x = Integer.valueOf(iArr[0]);
                    MainActivity.s_vInfo.tipMask.y = Integer.valueOf(iArr[1]);
                }
                new Thread(new Runnable() { // from class: com.baidu.kwgames.MainActivity.62.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ViewMgr.save();
                    }
                }).start();
            }
        });
        if (s_vInfo.tipMask != null && s_vInfo.tipMask.x.intValue() > 0) {
            ViewMgr.ensure_AI_result_float_visible();
            layout.setLocation(s_vInfo.tipMask.x.intValue(), s_vInfo.tipMask.y.intValue());
        }
        layout.show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.MainActivity$61  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass61 implements OnInvokeView {
        AnonymousClass61() {
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            Drawable drawable;
            MainActivity.this.mRecognRoot = view.findViewById(R.id.root);
            MainActivity.this.mRecognTextview = (TextView) view.findViewById(R.id.float_window_recogn);
            MainActivity.this.mAgileText = (TextView) view.findViewById(R.id.agile_text);
            MainActivity.this.m_btnAIResultAdd = (Button) view.findViewById(R.id.add);
            MainActivity.this.m_btnAIResultReduce = (Button) view.findViewById(R.id.reduce);
            MainActivity.this.m_btnAIResultToAllScope = (Button) view.findViewById(R.id.btn_to_all_scope);
            MainActivity.this.m_btnAIResultAIStage = (Button) view.findViewById(R.id.btn_ai_stage);
            MainActivity.this.m_btnAIFloatCrosshair = (Button) view.findViewById(R.id.btn_crosshair);
            MainActivity.this.m_btnAIFloatCrosshair.setVisibility(MainActivity.this.m_boAICrossHairOnOff ? 0 : 8);
            MainActivity.this.m_boShowAdjustButton = MainActivity.m_ini.getBoolean(Constants.CFG_AI_ADJUST_VISIBLE, true);
            MainActivity.this.update_AI_result_ctrls();
            MainActivity.this.update_AI_float_crosshair_button();
            MainActivity.this.begin_hide_AI_result_ctrls_timer();
            Button button = MainActivity.this.m_btnAIResultToAllScope;
            if (AppInstance.m_boIsChineseOS) {
                drawable = MainActivity.this.getResources().getDrawable(MainActivity.this.m_boAIToAllScope ? R.mipmap.all_scope_on_chs : R.mipmap.all_scope_off_chs);
            } else {
                drawable = MainActivity.this.getResources().getDrawable(MainActivity.this.m_boAIToAllScope ? R.mipmap.all_scope_on_en : R.mipmap.all_scope_off_en);
            }
            button.setBackground(drawable);
            MainActivity.this.m_boAIStageEditing = false;
            MainActivity.this.m_btnAIResultAIStage.setBackground(MainActivity.this.getResources().getDrawable(AppInstance.m_boIsChineseOS ? R.mipmap.ai_stage_off : R.mipmap.ai_stage_off_en));
            FloatMgr.resetVirtualMouse();
            MainActivity.this.mRecognTextview.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.61.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    MainActivity.this.m_boShowAdjustButton = !MainActivity.this.m_boShowAdjustButton;
                    MainActivity.this.update_AI_result_ctrls();
                    if (MainActivity.this.m_boAIStageEditing) {
                        MainActivity.this.m_boAIStageEditing = false;
                        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_STAGE);
                        MainActivity.this.m_btnAIResultAIStage.setBackground(MainActivity.this.getResources().getDrawable(AppInstance.m_boIsChineseOS ? R.mipmap.ai_stage_off : R.mipmap.ai_stage_off_en));
                    }
                    MainActivity.this.update_AI_float_crosshair_button();
                    MainActivity.this.begin_hide_AI_result_ctrls_timer();
                    MainActivity.m_ini.put(Constants.CFG_AI_ADJUST_VISIBLE, MainActivity.this.m_boShowAdjustButton);
                }
            });
            MainActivity.this.m_btnAIResultAdd.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$61$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass61.this.m82lambda$invoke$0$combaidukwgamesMainActivity$61(view2);
                }
            });
            MainActivity.this.m_btnAIResultReduce.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$61$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass61.this.m83lambda$invoke$1$combaidukwgamesMainActivity$61(view2);
                }
            });
            MainActivity.this.m_btnAIResultToAllScope.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$61$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass61.this.m84lambda$invoke$2$combaidukwgamesMainActivity$61(view2);
                }
            });
            MainActivity.this.m_btnAIResultAIStage.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.61.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    MainActivity.this.begin_hide_AI_result_ctrls_timer();
                    if (MainActivity.this.m_boSupportM4Dynamic.booleanValue()) {
                        if (EasyFloat.getAppFloatView(Constants.FLOAT_TAG_AI_STAGE) == null) {
                            if (MainActivity.this.m_boAIDynamic || MainActivity.this.m_boGunPartsAI) {
                                MainActivity.this.onAIGunDownStageM4();
                                MainActivity.this.m_boAIStageEditing = true;
                                return;
                            }
                            MsgBox.msg_box1_float(MainActivity.this.getApplicationContext(), MainActivity.this.getApplicationContext().getString(R.string.no_dynamic_adjust_tip));
                            return;
                        }
                        MainActivity.this.m_boAIStageEditing = false;
                        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_STAGE);
                    } else if (EasyFloat.getAppFloatView(Constants.FLOAT_TAG_AI_STAGE) == null) {
                        if (MainActivity.this.m_boAIDynamic || MainActivity.this.m_boGunPartsAI) {
                            MainActivity.this.onAIGunDownStage();
                            MainActivity.this.m_boAIStageEditing = true;
                            if (MainActivity.this.m_boDynamicGunPressTipReminded) {
                                return;
                            }
                            MainActivity.this.m_boDynamicGunPressTipReminded = true;
                            MsgBox.msg_box_float_with_never_remind_once(MainActivity.this.getApplicationContext(), R.string.ai_stage_editing_info, MainActivity.this.getApplicationContext().getString(R.string.ai_stage_editing_info), "ai_stage_editing_info");
                            return;
                        }
                        MsgBox.msg_box1_float(MainActivity.this.getApplicationContext(), MainActivity.this.getApplicationContext().getString(R.string.no_dynamic_adjust_tip));
                    } else {
                        MainActivity.this.m_boAIStageEditing = false;
                        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_STAGE);
                    }
                }
            });
            MainActivity.this.m_btnAIFloatCrosshair.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$61$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass61.this.m85lambda$invoke$3$combaidukwgamesMainActivity$61(view2);
                }
            });
            MainActivity.this.m_btnAIFloatCrosshair.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity$61$$ExternalSyntheticLambda4
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view2) {
                    return MainActivity.AnonymousClass61.this.m86lambda$invoke$4$combaidukwgamesMainActivity$61(view2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$0$com-baidu-kwgames-MainActivity$61  reason: not valid java name */
        public /* synthetic */ void m82lambda$invoke$0$combaidukwgamesMainActivity$61(View view) {
            int string_2_int = Units.string_2_int(MainActivity.this.mAgileText.getText().toString());
            if (string_2_int < MainActivity.this.m_sSysStatus.get_gun_down_max()) {
                MainActivity.this.set_cur_AI_gun_press((byte) (string_2_int + 1));
            }
            MainActivity.this.begin_hide_AI_result_ctrls_timer();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$1$com-baidu-kwgames-MainActivity$61  reason: not valid java name */
        public /* synthetic */ void m83lambda$invoke$1$combaidukwgamesMainActivity$61(View view) {
            int string_2_int = Units.string_2_int(MainActivity.this.mAgileText.getText().toString());
            if (string_2_int > 0) {
                MainActivity.this.set_cur_AI_gun_press((byte) (string_2_int - 1));
            }
            MainActivity.this.begin_hide_AI_result_ctrls_timer();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$2$com-baidu-kwgames-MainActivity$61  reason: not valid java name */
        public /* synthetic */ void m84lambda$invoke$2$combaidukwgamesMainActivity$61(View view) {
            Drawable drawable;
            MainActivity mainActivity = MainActivity.this;
            mainActivity.m_boAIToAllScope = !mainActivity.m_boAIToAllScope;
            MainActivity.m_ini.put(Constants.CFG_TO_ALL_SCOPE, MainActivity.this.m_boAIToAllScope);
            if (MainActivity.this.m_boAIToAllScope) {
                MsgBox.msg_box1_float(MainActivity.this.getApplicationContext(), MainActivity.this.getString(R.string.open_to_all_scope_remind));
            }
            Button button = MainActivity.this.m_btnAIResultToAllScope;
            if (AppInstance.m_boIsChineseOS) {
                drawable = MainActivity.this.getResources().getDrawable(MainActivity.this.m_boAIToAllScope ? R.mipmap.all_scope_on_chs : R.mipmap.all_scope_off_chs);
            } else {
                drawable = MainActivity.this.getResources().getDrawable(MainActivity.this.m_boAIToAllScope ? R.mipmap.all_scope_on_en : R.mipmap.all_scope_off_en);
            }
            button.setBackground(drawable);
            MainActivity.this.begin_hide_AI_result_ctrls_timer();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$3$com-baidu-kwgames-MainActivity$61  reason: not valid java name */
        public /* synthetic */ void m85lambda$invoke$3$combaidukwgamesMainActivity$61(View view) {
            byte b = MainActivity.this.m_byOldGun;
            if (-1 != b) {
                int ai_scope_to_crosshair_scope = AICrosshair.ai_scope_to_crosshair_scope(MainActivity.this.m_nOldScopeAiID, MainActivity.this.mKaijing);
                int i = AICrosshair.get_gun_crosshair(b, ai_scope_to_crosshair_scope) + 1;
                if (i > 10) {
                    i = 0;
                }
                AICrosshair.set_gun_crosshair(b, ai_scope_to_crosshair_scope, i);
                MainActivity.this.update_AI_float_crosshair_button();
                if (i == 0) {
                    FloatMgr.hide_aim_float();
                    return;
                } else {
                    FloatMgr.show_aim_float(i, AICrosshair.get_scope_offset_x(b, ai_scope_to_crosshair_scope), AICrosshair.get_scope_offset_y(b, ai_scope_to_crosshair_scope));
                    return;
                }
            }
            MsgBox.msg_box1_float(MainActivity.this.getApplicationContext(), MainActivity.this.getApplicationContext().getString(R.string.ai_cross_need_gun_tip));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$4$com-baidu-kwgames-MainActivity$61  reason: not valid java name */
        public /* synthetic */ boolean m86lambda$invoke$4$combaidukwgamesMainActivity$61(View view) {
            MainActivity.this.show_AI_crosshair_offset_adjust_float();
            return true;
        }
    }

    void update_AI_gun_down_percent_ctrls() {
        TextView textView = this.m_textAIGunDownPercent;
        if (textView != null) {
            textView.setText(NEAT.int_to_string(this.m_nAIGunDownPercent) + "%");
        }
        Button button = this.m_btnAIGunDownPercent;
        button.setText(NEAT.s(R.string.ai_gun_down_percent_prefix) + NEAT.int_to_string(this.m_nAIGunDownPercent) + "%");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void show_AI_gun_press_percent_float() {
        if (EasyFloat.getAppFloatView(Constants.FLOAT_TAG_AI_GUN_DOWN_PERCENT) != null) {
            EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_GUN_DOWN_PERCENT);
        } else {
            EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_GUN_DOWN_PERCENT).setGravity(49).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setLayout(R.layout.float_ai_gun_down_percent, new AnonymousClass63()).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.MainActivity$63  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass63 implements OnInvokeView {
        AnonymousClass63() {
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            MainActivity.this.m_textAIGunDownPercent = (TextView) view.findViewById(R.id.m_textGunDownPercent);
            MainActivity.this.update_AI_gun_down_percent_ctrls();
            FloatMgr.resetVirtualMouse();
            view.findViewById(R.id.close_window).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$63$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_GUN_DOWN_PERCENT);
                }
            });
            view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.63.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.m_nAIGunDownPercent < 300) {
                        MainActivity.access$10008(MainActivity.this);
                        MainActivity.this.bleSetAIGunDownOtherParams();
                        MainActivity.this.set_cur_AI_gun_down_percent(MainActivity.this.m_nAIGunDownPercent);
                        MainActivity.this.update_AI_gun_down_percent_ctrls();
                    }
                }
            });
            view.findViewById(R.id.reduce).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.63.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.m_nAIGunDownPercent > 20) {
                        MainActivity.access$10010(MainActivity.this);
                        MainActivity.this.bleSetAIGunDownOtherParams();
                        MainActivity.this.set_cur_AI_gun_down_percent(MainActivity.this.m_nAIGunDownPercent);
                        MainActivity.this.update_AI_gun_down_percent_ctrls();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void show_AI_crosshair_offset_adjust_float() {
        if (EasyFloat.getAppFloatView(Constants.FLOAT_AI_CROSSHAIR_ADJUST) != null) {
            return;
        }
        EasyFloat.Builder layout = EasyFloat.with(this).setTag(Constants.FLOAT_DYNAMIC_CROSSHAIR_SET).setLayout(R.layout.crosshair_ai, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.64
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.m_viewAIDynamicCrosshairFloat = view;
                MainActivity.this.m_textAICrossHairSize = (TextView) view.findViewById(R.id.m_textBoxSize);
                FloatMgr.resetVirtualMouse();
                view.findViewById(R.id.close_window).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.64.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        EasyFloat.dismissAppFloat(Constants.FLOAT_DYNAMIC_CROSSHAIR_SET);
                    }
                });
                ((Button) view.findViewById(R.id.leftOff)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.64.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        byte b = MainActivity.this.m_byOldGun;
                        if (-1 != b) {
                            int ai_scope_to_crosshair_scope = AICrosshair.ai_scope_to_crosshair_scope(MainActivity.this.m_nOldScopeAiID, MainActivity.this.mKaijing);
                            AICrosshair.scope_offset_x_minus(b, ai_scope_to_crosshair_scope);
                            FloatMgr.show_aim_float(AICrosshair.get_scope_offset_x(b, ai_scope_to_crosshair_scope), AICrosshair.get_scope_offset_y(b, ai_scope_to_crosshair_scope));
                        }
                    }
                });
                ((Button) view.findViewById(R.id.rightOff)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.64.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        byte b = MainActivity.this.m_byOldGun;
                        if (-1 != b) {
                            int ai_scope_to_crosshair_scope = AICrosshair.ai_scope_to_crosshair_scope(MainActivity.this.m_nOldScopeAiID, MainActivity.this.mKaijing);
                            AICrosshair.scope_offset_x_plus(MainActivity.this.m_byOldGun, ai_scope_to_crosshair_scope);
                            FloatMgr.show_aim_float(AICrosshair.get_scope_offset_x(b, ai_scope_to_crosshair_scope), AICrosshair.get_scope_offset_y(b, ai_scope_to_crosshair_scope));
                        }
                    }
                });
                ((Button) view.findViewById(R.id.upOff)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.64.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        byte b = MainActivity.this.m_byOldGun;
                        if (-1 != b) {
                            int ai_scope_to_crosshair_scope = AICrosshair.ai_scope_to_crosshair_scope(MainActivity.this.m_nOldScopeAiID, MainActivity.this.mKaijing);
                            AICrosshair.scope_offset_y_minus(b, ai_scope_to_crosshair_scope);
                            FloatMgr.show_aim_float(AICrosshair.get_scope_offset_x(b, ai_scope_to_crosshair_scope), AICrosshair.get_scope_offset_y(b, ai_scope_to_crosshair_scope));
                        }
                    }
                });
                ((Button) view.findViewById(R.id.downOff)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.64.5
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        byte b = MainActivity.this.m_byOldGun;
                        if (-1 != b) {
                            int ai_scope_to_crosshair_scope = AICrosshair.ai_scope_to_crosshair_scope(MainActivity.this.m_nOldScopeAiID, MainActivity.this.mKaijing);
                            AICrosshair.scope_offset_y_plus(b, ai_scope_to_crosshair_scope);
                            FloatMgr.show_aim_float(AICrosshair.get_scope_offset_x(b, ai_scope_to_crosshair_scope), AICrosshair.get_scope_offset_y(b, ai_scope_to_crosshair_scope));
                        }
                    }
                });
            }
        });
        layout.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.65
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.this.m_viewAIDynamicCrosshairFloat.getLocationOnScreen(iArr);
                MainActivity.this.m_nAICrosshairFloatX = iArr[0];
                MainActivity.this.m_nAICrosshairFloatY = iArr[1];
            }
        });
        layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true);
        layout.setLocation(this.m_nAICrosshairFloatX, this.m_nAICrosshairFloatY);
        layout.show();
    }

    void update_AI_crosshair_float_UI() {
        TextView textView = this.m_textAICrossHairSize;
        textView.setText(FloatMgr.get_aim_float_size() + "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void show_xscope_adjust_float(boolean z) {
        if (!z) {
            EasyFloat.dismissAppFloat(Constants.FLOAT_AI_XSCOPE_ADJUST);
        } else if (EasyFloat.getAppFloatView(Constants.FLOAT_AI_XSCOPE_ADJUST) != null) {
        } else {
            EasyFloat.Builder layout = EasyFloat.with(this).setTag(Constants.FLOAT_AI_XSCOPE_ADJUST).setLayout(R.layout.xscope_adjust, new AnonymousClass66());
            layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(false).setAnimator(null);
            layout.setLocation(80, 0);
            layout.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.MainActivity$66  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass66 implements OnInvokeView {
        AnonymousClass66() {
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            MainActivity.this.m_viewXScopeAdjustRoot = view.findViewById(R.id.root);
            MainActivity.this.m_btnXSCopeTriggerMode = (Button) view.findViewById(R.id.m_btnXSCopeTriggerMode);
            MainActivity.this.m_textXScopeHoldTime = (TextView) view.findViewById(R.id.m_textXScopeHoldTime);
            MainActivity.this.m_textXScopeAutoTriggerDelay = (TextView) view.findViewById(R.id.m_textXScopeAutoTriggerDelay);
            MainActivity.this.m_textXScopeLoopDelay = (TextView) view.findViewById(R.id.m_textXScopeLoopDelay);
            MainActivity.this.m_layoutAutoLoopDelay = (LinearLayout) view.findViewById(R.id.m_layoutAutoLoopDelay);
            MainActivity.this.m_layoutAutoTriggerDelay = (LinearLayout) view.findViewById(R.id.m_layoutAutoTriggerDelay);
            MainActivity.this.init_xscope_game_onoff_list(view);
            MainActivity.this.update_xscope_adjust_UI();
            MainActivity.this.m_btnXSCopeTriggerMode.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$66$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass66.this.m87lambda$invoke$0$combaidukwgamesMainActivity$66(view2);
                }
            });
            view.findViewById(R.id.m_btnXScopeHoldTimeMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$66$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass66.this.m88lambda$invoke$1$combaidukwgamesMainActivity$66(view2);
                }
            });
            view.findViewById(R.id.m_btnXScopeHoldTimePlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$66$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass66.this.m89lambda$invoke$2$combaidukwgamesMainActivity$66(view2);
                }
            });
            view.findViewById(R.id.m_btnXScopeAutoTriggerDelayMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$66$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass66.this.m90lambda$invoke$3$combaidukwgamesMainActivity$66(view2);
                }
            });
            view.findViewById(R.id.m_btnXScopeAutoTriggerDelayPlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$66$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass66.this.m91lambda$invoke$4$combaidukwgamesMainActivity$66(view2);
                }
            });
            view.findViewById(R.id.m_btnXScopeLoopDelayMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$66$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass66.this.m92lambda$invoke$5$combaidukwgamesMainActivity$66(view2);
                }
            });
            view.findViewById(R.id.m_btnXScopeLoopDelayPlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$66$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass66.this.m93lambda$invoke$6$combaidukwgamesMainActivity$66(view2);
                }
            });
            view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$66$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass66.this.m94lambda$invoke$7$combaidukwgamesMainActivity$66(view2);
                }
            });
            view.findViewById(R.id.m_btnQuestion).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$66$$ExternalSyntheticLambda8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MsgBox.msg_box1_float(AppInstance.s_context, NEAT.s(R.string.ai_xscope_adjust_question), true);
                }
            });
            Switch r3 = (Switch) view.findViewById(R.id.m_swDunEnable);
            if (MainActivity.this.m_sSysStatus.uSystemVer < 111) {
                r3.setVisibility(8);
            }
            r3.setChecked(AIXScope.s_nDunEnable != 0);
            r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.MainActivity$66$$ExternalSyntheticLambda9
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    MainActivity.AnonymousClass66.this.m95lambda$invoke$9$combaidukwgamesMainActivity$66(compoundButton, z);
                }
            });
            FloatMgr.resetVirtualMouse();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$0$com-baidu-kwgames-MainActivity$66  reason: not valid java name */
        public /* synthetic */ void m87lambda$invoke$0$combaidukwgamesMainActivity$66(View view) {
            int i = AIXScope.s_nScopeTriggerMode + 1;
            if (i >= 2) {
                i = 0;
            }
            AIXScope.s_nScopeTriggerMode = i;
            MainActivity.this.bleSendAIXScopeParams();
            MainActivity.this.update_xscope_adjust_UI();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$1$com-baidu-kwgames-MainActivity$66  reason: not valid java name */
        public /* synthetic */ void m88lambda$invoke$1$combaidukwgamesMainActivity$66(View view) {
            int i = AIXScope.s_nXScopeHoldTime;
            if (i > 40) {
                AIXScope.s_nXScopeHoldTime = i - 5;
                MainActivity.this.bleSendAIXScopeParams();
                MainActivity.this.update_xscope_adjust_UI();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$2$com-baidu-kwgames-MainActivity$66  reason: not valid java name */
        public /* synthetic */ void m89lambda$invoke$2$combaidukwgamesMainActivity$66(View view) {
            if (AIXScope.s_nXScopeHoldTime < 500) {
                AIXScope.s_nXScopeHoldTime += 5;
                MainActivity.this.bleSendAIXScopeParams();
                MainActivity.this.update_xscope_adjust_UI();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$3$com-baidu-kwgames-MainActivity$66  reason: not valid java name */
        public /* synthetic */ void m90lambda$invoke$3$combaidukwgamesMainActivity$66(View view) {
            int i = AIXScope.s_nAutoTriggerDelayMS;
            if (i > 200) {
                AIXScope.s_nAutoTriggerDelayMS = i - 50;
                MainActivity.this.bleSendAIXScopeParams();
                MainActivity.this.update_xscope_adjust_UI();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$4$com-baidu-kwgames-MainActivity$66  reason: not valid java name */
        public /* synthetic */ void m91lambda$invoke$4$combaidukwgamesMainActivity$66(View view) {
            AIXScope.s_nAutoTriggerDelayMS += 50;
            MainActivity.this.bleSendAIXScopeParams();
            MainActivity.this.update_xscope_adjust_UI();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$5$com-baidu-kwgames-MainActivity$66  reason: not valid java name */
        public /* synthetic */ void m92lambda$invoke$5$combaidukwgamesMainActivity$66(View view) {
            int i = AIXScope.s_nAutoLoopDelayMS - 50;
            if (i < 0) {
                i = 0;
            }
            AIXScope.s_nAutoLoopDelayMS = i;
            MainActivity.this.bleSendAIXScopeParams();
            MainActivity.this.update_xscope_adjust_UI();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$6$com-baidu-kwgames-MainActivity$66  reason: not valid java name */
        public /* synthetic */ void m93lambda$invoke$6$combaidukwgamesMainActivity$66(View view) {
            AIXScope.s_nAutoLoopDelayMS += 50;
            MainActivity.this.bleSendAIXScopeParams();
            MainActivity.this.update_xscope_adjust_UI();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$7$com-baidu-kwgames-MainActivity$66  reason: not valid java name */
        public /* synthetic */ void m94lambda$invoke$7$combaidukwgamesMainActivity$66(View view) {
            for (int i = 0; i < MainActivity.this.m_arrXScopeGunIndex.size(); i++) {
                AIXScope.set_gun_x_scope_onoff(MainActivity.this.m_arrXScopeGunIndex.get(i).shortValue(), MainActivity.this.m_arrXScopeGunsSel.get(i).booleanValue());
            }
            AIXScope.save();
            MainActivity.this.m_arrXScopeGuns.clear();
            MainActivity.this.m_arrXScopeGunsSel.clear();
            MainActivity.this.m_arrXScopeGunIndex.clear();
            MainActivity.this.show_xscope_adjust_float(false);
            MainActivity.this.bleSendAIXScopeParams();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$9$com-baidu-kwgames-MainActivity$66  reason: not valid java name */
        public /* synthetic */ void m95lambda$invoke$9$combaidukwgamesMainActivity$66(CompoundButton compoundButton, boolean z) {
            AIXScope.s_nDunEnable = AIXScope.s_nDunEnable == 0 ? 1 : 0;
            MainActivity.this.bleSendAIXScopeParams();
        }
    }

    void bleSendAIXScopeParams() {
        if (this.m_sSysStatus.has_x_scope_capacity()) {
            try {
                byte[] bArr = {-1, -109, (byte) 8, 0, (byte) (AIXScope.s_nScopeTriggerMode & 255), Units.LOBYTE(AIXScope.s_nAutoTriggerDelayMS), Units.HIBYTE(AIXScope.s_nAutoTriggerDelayMS), Units.LOBYTE(AIXScope.s_nAutoLoopDelayMS), Units.HIBYTE(AIXScope.s_nAutoLoopDelayMS), Units.LOBYTE(AIXScope.s_nXScopeHoldTime), Units.HIBYTE(AIXScope.s_nXScopeHoldTime), (byte) AIXScope.s_nDunEnable};
                IThrone iThrone = this.mThroneService;
                if (iThrone != null) {
                    iThrone.bleSendShortData(bArr);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    void init_xscope_game_onoff_list(View view) {
        if (this.m_arrXScopeGuns == null) {
            this.m_arrXScopeGuns = new ArrayList<>();
        }
        if (this.m_arrXScopeGunsSel == null) {
            this.m_arrXScopeGunsSel = new ArrayList<>();
        }
        if (this.m_arrXScopeGunIndex == null) {
            this.m_arrXScopeGunIndex = new ArrayList<>();
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.m_lstXScopeGame);
        this.m_lstXScopeGame = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.m_arrXScopeGuns.clear();
        this.m_arrXScopeGunsSel.clear();
        for (int i = 0; i < 66; i++) {
            if (Constants.g_arrGunInfo[i] != null) {
                this.m_arrXScopeGuns.add(Constants.g_arrGunInfo[i].m_strName);
                this.m_arrXScopeGunsSel.add(Boolean.valueOf(AIXScope.get_gun_x_scope_onoff(i)));
                this.m_arrXScopeGunIndex.add(Short.valueOf((short) i));
            }
        }
        XScopeAdapter xScopeAdapter = new XScopeAdapter(this, this.m_arrXScopeGuns, this.m_arrXScopeGunsSel);
        this.m_adapterXScope = xScopeAdapter;
        xScopeAdapter.setClickListener(new XScopeAdapter.ItemClickListener() { // from class: com.baidu.kwgames.MainActivity.67
            @Override // com.baidu.kwgames.adapter.XScopeAdapter.ItemClickListener
            public void onItemClick(View view2, int i2) {
                MainActivity.this.m_arrXScopeGunsSel.set(i2, Boolean.valueOf(!MainActivity.this.m_arrXScopeGunsSel.get(i2).booleanValue()));
                MainActivity.this.m_adapterXScope.notifyDataSetChanged();
                for (int i3 = 0; i3 < MainActivity.this.m_arrXScopeGunIndex.size(); i3++) {
                    AIXScope.set_gun_x_scope_onoff(MainActivity.this.m_arrXScopeGunIndex.get(i3).shortValue(), MainActivity.this.m_arrXScopeGunsSel.get(i3).booleanValue());
                }
            }
        });
        this.m_lstXScopeGame.setAdapter(this.m_adapterXScope);
    }

    public void update_xscope_adjust_UI() {
        Button button = this.m_btnXSCopeTriggerMode;
        if (button != null) {
            button.setText(getString(1 == AIXScope.s_nScopeTriggerMode ? R.string.xscope_mode_auto : R.string.xscope_mode_manual));
            this.m_layoutAutoLoopDelay.setVisibility(1 == AIXScope.s_nScopeTriggerMode ? 0 : 8);
            this.m_layoutAutoTriggerDelay.setVisibility(1 != AIXScope.s_nScopeTriggerMode ? 8 : 0);
            TextView textView = this.m_textXScopeHoldTime;
            textView.setText("" + AIXScope.s_nXScopeHoldTime);
            TextView textView2 = this.m_textXScopeAutoTriggerDelay;
            textView2.setText("" + AIXScope.s_nAutoTriggerDelayMS);
            TextView textView3 = this.m_textXScopeLoopDelay;
            textView3.setText("" + AIXScope.s_nAutoLoopDelayMS);
        }
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

    public void ignoreBatteryOptimization(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                if (((PowerManager) getSystemService("power")).isIgnoringBatteryOptimizations(activity.getPackageName())) {
                    return;
                }
                Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void update_AI_float_frame_and_tip() {
        char c;
        int i;
        View view = this.gunMaskSelectIcon1;
        if (view != null) {
            view.setVisibility(8);
        }
        View view2 = this.downSelect;
        if (view2 != null) {
            view2.setVisibility(8);
        }
        View view3 = this.gunMaskSelectIcon2;
        if (view3 != null) {
            view3.setVisibility(8);
        }
        View view4 = this.squatSelect;
        if (view4 != null) {
            view4.setVisibility(8);
        }
        View view5 = this.mirrorSelect;
        if (view5 != null) {
            view5.setVisibility(8);
        }
        View view6 = this.doubleMirrorSelect;
        if (view6 != null) {
            view6.setVisibility(8);
        }
        View view7 = this.m_viewBagtagFrame;
        if (view7 != null) {
            view7.setVisibility(8);
        }
        View view8 = this.m_viewTakeOfftagFrame;
        if (view8 != null) {
            view8.setVisibility(8);
        }
        View view9 = this.m_viewGun1HeadFrame;
        if (view9 != null) {
            view9.setVisibility(8);
        }
        View view10 = this.m_viewGun1HandleFrame;
        if (view10 != null) {
            view10.setVisibility(8);
        }
        View view11 = this.m_viewGun1TailFrame;
        if (view11 != null) {
            view11.setVisibility(8);
        }
        View view12 = this.m_viewGun2HeadFrame;
        if (view12 != null) {
            view12.setVisibility(8);
        }
        View view13 = this.m_viewGun2HandleFrame;
        if (view13 != null) {
            view13.setVisibility(8);
        }
        View view14 = this.m_viewGun2TailFrame;
        if (view14 != null) {
            view14.setVisibility(8);
        }
        String str = s_vInfo.selectTag;
        str.hashCode();
        switch (str.hashCode()) {
            case -2075930639:
                if (str.equals(Constants.FLOAT_TAG_AI_C)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1234755340:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN1)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1073910849:
                if (str.equals(Constants.FLOAT_TAG_AI_MIRROW)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -954571125:
                if (str.equals(Constants.FLOAT_TAG_SCOPE2)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 62270144:
                if (str.equals(Constants.FLOAT_TAG_AI_BAG_TAG)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 179370297:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_HEAD1)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 179370298:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_HEAD2)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 190341321:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_TAIL1)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 190341322:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_TAIL2)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 347818137:
                if (str.equals(Constants.FLOAT_TAG_AI_Z)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 377290174:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN2)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 473712625:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_HANDLE1)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 473712626:
                if (str.equals(Constants.FLOAT_TAG_AI_GUN_HANDLE2)) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 1270510112:
                if (str.equals(Constants.FLOAT_TAG_AI_TAKE_OFF_TAG)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                View view15 = this.squatSelect;
                if (view15 != null) {
                    view15.setVisibility(0);
                    this.mEditTipTextView.setText(R.string.down_tip);
                    i = R.mipmap.ai_squt_pos;
                    break;
                }
                i = -1;
                break;
            case 1:
                View view16 = this.gunMaskSelectIcon1;
                if (view16 != null) {
                    view16.setVisibility(0);
                    this.mEditTipTextView.setText(R.string.gun_tip1);
                }
                i = -1;
                break;
            case 2:
                View view17 = this.mirrorSelect;
                if (view17 != null) {
                    view17.setVisibility(0);
                    this.mEditTipTextView.setText(R.string.open_mirror_tip);
                    i = R.mipmap.ai_scope_pos;
                    break;
                }
                i = -1;
                break;
            case 3:
                View view18 = this.doubleMirrorSelect;
                if (view18 != null) {
                    view18.setVisibility(0);
                    this.mEditTipTextView.setText(R.string.duoble_mirror_tip);
                }
                i = -1;
                break;
            case 4:
                View view19 = this.m_viewBagtagFrame;
                if (view19 != null) {
                    view19.setVisibility(0);
                    this.mEditTipTextView.setText(R.string.ai_rec_bag_tip);
                }
                i = -1;
                break;
            case 5:
                View view20 = this.m_viewGun1HeadFrame;
                if (view20 != null) {
                    view20.setVisibility(0);
                    this.mEditTipTextView.setText(R.string.ai_rec_gun_head_tip);
                }
                i = -1;
                break;
            case 6:
                View view21 = this.m_viewGun2HeadFrame;
                if (view21 != null) {
                    view21.setVisibility(0);
                    this.mEditTipTextView.setText(R.string.ai_rec_gun_head_tip);
                }
                i = -1;
                break;
            case 7:
                View view22 = this.m_viewGun1TailFrame;
                if (view22 != null) {
                    view22.setVisibility(0);
                    this.mEditTipTextView.setText(R.string.ai_rec_gun_tail_tip);
                }
                i = -1;
                break;
            case '\b':
                View view23 = this.m_viewGun2TailFrame;
                if (view23 != null) {
                    view23.setVisibility(0);
                    this.mEditTipTextView.setText(R.string.ai_rec_gun_tail_tip);
                }
                i = -1;
                break;
            case '\t':
                View view24 = this.downSelect;
                if (view24 != null) {
                    view24.setVisibility(0);
                    this.mEditTipTextView.setText(R.string.pa_tip);
                    i = R.mipmap.ai_fall_pos;
                    break;
                }
                i = -1;
                break;
            case '\n':
                View view25 = this.gunMaskSelectIcon2;
                if (view25 != null) {
                    view25.setVisibility(0);
                    this.mEditTipTextView.setText(R.string.gun_tip2);
                }
                i = -1;
                break;
            case 11:
                View view26 = this.m_viewGun1HandleFrame;
                if (view26 != null) {
                    view26.setVisibility(0);
                    this.mEditTipTextView.setText(R.string.ai_rec_gun_handle_tip);
                }
                i = -1;
                break;
            case '\f':
                View view27 = this.m_viewGun2HandleFrame;
                if (view27 != null) {
                    view27.setVisibility(0);
                    this.mEditTipTextView.setText(R.string.ai_rec_gun_handle_tip);
                }
                i = -1;
                break;
            case '\r':
                View view28 = this.m_viewTakeOfftagFrame;
                if (view28 != null) {
                    view28.setVisibility(0);
                    this.mEditTipTextView.setText(R.string.ai_take_off_tip);
                }
                i = -1;
                break;
            default:
                i = -1;
                break;
        }
        if (i == -1) {
            this.m_imageExample.setVisibility(8);
            return;
        }
        this.m_imageExample.setImageResource(i);
        this.m_imageExample.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void update_AI_float_visible() {
        String[] strArr = {Constants.FLOAT_TAG_AI_BAG_TAG, Constants.FLOAT_TAG_AI_GUN_HEAD1, Constants.FLOAT_TAG_AI_GUN_HEAD2, Constants.FLOAT_TAG_AI_GUN_HANDLE1, Constants.FLOAT_TAG_AI_GUN_HANDLE2, Constants.FLOAT_TAG_AI_GUN_TAIL1, Constants.FLOAT_TAG_AI_GUN_TAIL2};
        if (!this.m_boGunPartsAI || !this.m_sSysStatus.has_gun_parts_reduce_capacity()) {
            for (int i = 0; i < 7; i++) {
                String str = strArr[i];
                EasyFloat.hideAppFloat(str);
                if (str.equals(s_vInfo.selectTag)) {
                    s_vInfo.selectTag = Constants.FLOAT_TAG_AI_GUN1;
                    update_AI_float_frame_and_tip();
                }
            }
            return;
        }
        for (int i2 = 0; i2 < 7; i2++) {
            EasyFloat.showAppFloat(strArr[i2]);
        }
        for (int i3 = 0; i3 < 7; i3++) {
            AIFloatInfo aIFloatInfo = this.m_mapAIFloat.get(strArr[i3]);
            aIFloatInfo.m_viewMask.setVisibility(8);
            aIFloatInfo.m_viewMask.setVisibility(0);
        }
    }

    public void set_AI_float_mask_frame(String str, View view, View view2) {
        if (this.m_mapAIFloat.containsKey(str)) {
            this.m_mapAIFloat.get(str).set_mask_frame(view, view2);
        }
    }

    int get_cur_AI_kit() {
        int i;
        if (NEAT.is_chinese()) {
            i = 0;
        } else if (NEAT.is_vitnam()) {
            i = 4;
        } else {
            i = NEAT.is_turkey() ? 3 : 1;
        }
        return m_ini.getInt(Constants.CFG_AI_KIT, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.MainActivity$68  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass68 implements OnInvokeView {
        AnonymousClass68() {
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            MainActivity.this.m_viewAIEditRoot = view;
            View findViewById = view.findViewById(R.id.add);
            View findViewById2 = view.findViewById(R.id.reduce);
            Button button = (Button) view.findViewById(R.id.cancel);
            Button button2 = (Button) view.findViewById(R.id.up);
            Button button3 = (Button) view.findViewById(R.id.down);
            Button button4 = (Button) view.findViewById(R.id.left);
            Button button5 = (Button) view.findViewById(R.id.right);
            Button button6 = (Button) view.findViewById(R.id.AICommit);
            final Button button7 = (Button) view.findViewById(R.id.gunPartsAI);
            Button button8 = (Button) view.findViewById(R.id.selKit);
            MainActivity.this.m_viewAIEditRebootRemind = (TextView) view.findViewById(R.id.reboot_remind);
            final Spinner spinner = (Spinner) view.findViewById(R.id.m_comAIKit);
            spinner.setAdapter((SpinnerAdapter) new ArrayAdapter(AppInstance.get(), (int) R.layout.ai_game_list, (int) R.id.text, AppInstance.get().getResources().getStringArray(R.array.aikits)));
            button7.setBackground(MainActivity.this.getResources().getDrawable(AppInstance.m_boIsChineseOS ? MainActivity.this.m_boGunPartsAI ? R.mipmap.parts_ai_on : R.mipmap.parts_ai_off : MainActivity.this.m_boGunPartsAI ? R.mipmap.parts_ai_on_en : R.mipmap.parts_ai_off_en));
            if (!MainActivity.this.m_sSysStatus.is_in_x1_mode()) {
                button6.setVisibility(8);
            }
            if (!MainActivity.this.m_sSysStatus.has_gun_parts_reduce_capacity()) {
                button7.setVisibility(8);
            }
            button6.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$68$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass68.this.m96lambda$invoke$0$combaidukwgamesMainActivity$68(view2);
                }
            });
            int i = MainActivity.this.get_cur_AI_kit();
            if (i >= 5) {
                i = 0;
            }
            spinner.setSelection(i);
            MainActivity.this.m_viewAIEditRebootRemind.setVisibility(i != AppInstance.s_nAIKit ? 0 : 8);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.baidu.kwgames.MainActivity.68.1
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView adapterView, View view2, int i2, long j) {
                    if (i2 != MainActivity.this.get_cur_AI_kit()) {
                        MainActivity.m_ini.put(Constants.CFG_AI_KIT, i2);
                        MainActivity.this.m_viewAIEditRebootRemind.setVisibility(i2 == AppInstance.s_nAIKit ? 8 : 0);
                        if (i2 != AppInstance.s_nAIKit) {
                            NEAT.toast(R.string.change_new_ai_kit);
                        }
                    }
                }
            });
            button8.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.68.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int i2 = MainActivity.this.get_cur_AI_kit() + 1;
                    if (i2 >= 5) {
                        i2 = 0;
                    }
                    MainActivity.m_ini.put(Constants.CFG_AI_KIT, i2);
                    MainActivity.this.m_viewAIEditRebootRemind.setVisibility(i2 == AppInstance.s_nAIKit ? 8 : 0);
                    spinner.setSelection(i2);
                    if (i2 != AppInstance.s_nAIKit) {
                        NEAT.toast(R.string.change_new_ai_kit);
                    }
                }
            });
            MainActivity.this.m_viewAIEditRebootRemind.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.68.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    ViewMgr.getInstance();
                    ViewMgr.save();
                    MainActivity.this.closeEditFloat();
                    Intent launchIntentForPackage = MainActivity.this.getPackageManager().getLaunchIntentForPackage(MainActivity.this.getPackageName());
                    if (launchIntentForPackage != null) {
                        launchIntentForPackage.addFlags(67108864);
                        MainActivity.this.startActivity(launchIntentForPackage);
                    }
                    Process.killProcess(Process.myPid());
                    System.exit(0);
                }
            });
            button7.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.68.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    MainActivity.this.m_boGunPartsAI = !MainActivity.this.m_boGunPartsAI;
                    MainActivity.m_ini.put(Constants.CFG_GUN_PARTS_AI, MainActivity.this.m_boGunPartsAI);
                    button7.setBackground(MainActivity.this.getResources().getDrawable(AppInstance.m_boIsChineseOS ? MainActivity.this.m_boGunPartsAI ? R.mipmap.parts_ai_on : R.mipmap.parts_ai_off : MainActivity.this.m_boGunPartsAI ? R.mipmap.parts_ai_on_en : R.mipmap.parts_ai_off_en));
                    if (!MainActivity.this.m_boGunPartsAI) {
                        MainActivity.this.m_nCurBagTagAI = -1;
                        MainActivity.this.m_nCurGun1Head = -1;
                        MainActivity.this.m_nCurGun1Handle = -1;
                        MainActivity.this.m_nCurGun1Tail = -1;
                        MainActivity.this.m_nCurGun2Head = -1;
                        MainActivity.this.m_nCurGun2Handle = -1;
                        MainActivity.this.m_nCurGun2Tail = -1;
                    }
                    MainActivity.this.update_AI_float_visible();
                }
            });
            button4.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.68.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.m_mapAIFloat.containsKey(MainActivity.s_vInfo.selectTag)) {
                        AIFloatInfo aIFloatInfo = MainActivity.this.m_mapAIFloat.get(MainActivity.s_vInfo.selectTag);
                        aIFloatInfo.m_data.minus_x();
                        EasyFloat.updateFloat(MainActivity.s_vInfo.selectTag, aIFloatInfo.m_data.x.intValue(), aIFloatInfo.m_data.y.intValue());
                        for (String str : aIFloatInfo.get_same_x_tag()) {
                            AIFloatInfo aIFloatInfo2 = MainActivity.this.m_mapAIFloat.get(str);
                            aIFloatInfo2.m_data.x = aIFloatInfo.m_data.x;
                            EasyFloat.updateFloat(str, aIFloatInfo2.m_data.x.intValue(), aIFloatInfo2.m_data.y.intValue());
                        }
                    }
                }
            });
            button5.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.68.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.m_mapAIFloat.containsKey(MainActivity.s_vInfo.selectTag)) {
                        AIFloatInfo aIFloatInfo = MainActivity.this.m_mapAIFloat.get(MainActivity.s_vInfo.selectTag);
                        aIFloatInfo.m_data.plus_x(AppInstance.s_nScreenW);
                        EasyFloat.updateFloat(MainActivity.s_vInfo.selectTag, aIFloatInfo.m_data.x.intValue(), aIFloatInfo.m_data.y.intValue());
                        for (String str : aIFloatInfo.get_same_x_tag()) {
                            AIFloatInfo aIFloatInfo2 = MainActivity.this.m_mapAIFloat.get(str);
                            aIFloatInfo2.m_data.x = aIFloatInfo.m_data.x;
                            EasyFloat.updateFloat(str, aIFloatInfo2.m_data.x.intValue(), aIFloatInfo2.m_data.y.intValue());
                        }
                    }
                }
            });
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.68.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.m_mapAIFloat.containsKey(MainActivity.s_vInfo.selectTag)) {
                        AIFloatInfo aIFloatInfo = MainActivity.this.m_mapAIFloat.get(MainActivity.s_vInfo.selectTag);
                        aIFloatInfo.m_data.minus_y();
                        EasyFloat.updateFloat(MainActivity.s_vInfo.selectTag, aIFloatInfo.m_data.x.intValue(), aIFloatInfo.m_data.y.intValue());
                        for (String str : aIFloatInfo.get_same_y_tag()) {
                            AIFloatInfo aIFloatInfo2 = MainActivity.this.m_mapAIFloat.get(str);
                            aIFloatInfo2.m_data.y = aIFloatInfo.m_data.y;
                            EasyFloat.updateFloat(str, aIFloatInfo2.m_data.x.intValue(), aIFloatInfo2.m_data.y.intValue());
                        }
                    }
                }
            });
            button3.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.68.8
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (MainActivity.this.m_mapAIFloat.containsKey(MainActivity.s_vInfo.selectTag)) {
                        AIFloatInfo aIFloatInfo = MainActivity.this.m_mapAIFloat.get(MainActivity.s_vInfo.selectTag);
                        aIFloatInfo.m_data.plus_y(AppInstance.s_nScreenH);
                        EasyFloat.updateFloat(MainActivity.s_vInfo.selectTag, aIFloatInfo.m_data.x.intValue(), aIFloatInfo.m_data.y.intValue());
                        for (String str : aIFloatInfo.get_same_y_tag()) {
                            AIFloatInfo aIFloatInfo2 = MainActivity.this.m_mapAIFloat.get(str);
                            aIFloatInfo2.m_data.y = aIFloatInfo.m_data.y;
                            EasyFloat.updateFloat(str, aIFloatInfo2.m_data.x.intValue(), aIFloatInfo2.m_data.y.intValue());
                        }
                    }
                }
            });
            button.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.68.9
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    MainActivity.this.close_and_save_AI_edit_float();
                }
            });
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.68.10
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    ViewInfo viewInfo = MainActivity.s_vInfo;
                    String str = MainActivity.s_vInfo.selectTag;
                    str.hashCode();
                    char c = 65535;
                    switch (str.hashCode()) {
                        case -2075930639:
                            if (str.equals(Constants.FLOAT_TAG_AI_C)) {
                                c = 0;
                                break;
                            }
                            break;
                        case -1234755340:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN1)) {
                                c = 1;
                                break;
                            }
                            break;
                        case -1073910849:
                            if (str.equals(Constants.FLOAT_TAG_AI_MIRROW)) {
                                c = 2;
                                break;
                            }
                            break;
                        case -954571125:
                            if (str.equals(Constants.FLOAT_TAG_SCOPE2)) {
                                c = 3;
                                break;
                            }
                            break;
                        case 62270144:
                            if (str.equals(Constants.FLOAT_TAG_AI_BAG_TAG)) {
                                c = 4;
                                break;
                            }
                            break;
                        case 179370297:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN_HEAD1)) {
                                c = 5;
                                break;
                            }
                            break;
                        case 179370298:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN_HEAD2)) {
                                c = 6;
                                break;
                            }
                            break;
                        case 190341321:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN_TAIL1)) {
                                c = 7;
                                break;
                            }
                            break;
                        case 190341322:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN_TAIL2)) {
                                c = '\b';
                                break;
                            }
                            break;
                        case 347818137:
                            if (str.equals(Constants.FLOAT_TAG_AI_Z)) {
                                c = '\t';
                                break;
                            }
                            break;
                        case 377290174:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN2)) {
                                c = '\n';
                                break;
                            }
                            break;
                        case 473712625:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN_HANDLE1)) {
                                c = 11;
                                break;
                            }
                            break;
                        case 473712626:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN_HANDLE2)) {
                                c = '\f';
                                break;
                            }
                            break;
                        case 1270510112:
                            if (str.equals(Constants.FLOAT_TAG_AI_TAKE_OFF_TAG)) {
                                c = '\r';
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            int intValue = viewInfo.squatDTO.height.intValue() + 1;
                            if (viewInfo.squatDTO.x.intValue() + intValue > AppInstance.s_nScreenW || viewInfo.squatDTO.y.intValue() + intValue > AppInstance.s_nScreenH) {
                                return;
                            }
                            ViewInfo.MaskDTO maskDTO = viewInfo.squatDTO;
                            ViewInfo.MaskDTO maskDTO2 = viewInfo.squatDTO;
                            Integer valueOf = Integer.valueOf(intValue);
                            maskDTO2.height = valueOf;
                            maskDTO.width = valueOf;
                            MainActivity.this.squatMask.getLayoutParams().width = viewInfo.squatDTO.width.intValue();
                            MainActivity.this.squatMask.getLayoutParams().height = viewInfo.squatDTO.height.intValue();
                            MainActivity.this.squatMask.requestLayout();
                            return;
                        case 1:
                            int intValue2 = viewInfo.gun1.height.intValue() + 1;
                            int intValue3 = Float.valueOf((intValue2 * MainActivity.this.m_fGunModeRateW2H) + 0.5f).intValue();
                            if (viewInfo.gun1.x.intValue() + intValue3 > AppInstance.s_nScreenW || viewInfo.gun1.y.intValue() + intValue2 > AppInstance.s_nScreenH) {
                                return;
                            }
                            viewInfo.gun1.width = Integer.valueOf(intValue3);
                            viewInfo.gun1.height = Integer.valueOf(intValue2);
                            MainActivity.this.gunMask1.getLayoutParams().width = viewInfo.gun1.width.intValue();
                            MainActivity.this.gunMask1.getLayoutParams().height = viewInfo.gun1.height.intValue();
                            MainActivity.this.temp1.getLayoutParams().width = viewInfo.gun1.width.intValue();
                            MainActivity.this.temp1.getLayoutParams().height = viewInfo.gun1.height.intValue();
                            MainActivity.this.gunMask1.requestLayout();
                            MainActivity.this.temp1.requestLayout();
                            viewInfo.gun2.width = viewInfo.gun1.width;
                            viewInfo.gun2.height = viewInfo.gun1.height;
                            viewInfo.gun2.x = Integer.valueOf(ViewMgr.make_sure_xy_in_range(viewInfo.gun2.x.intValue(), viewInfo.gun2.width.intValue(), AppInstance.s_nScreenW));
                            viewInfo.gun2.y = Integer.valueOf(ViewMgr.make_sure_xy_in_range(viewInfo.gun2.y.intValue(), viewInfo.gun2.height.intValue(), AppInstance.s_nScreenH));
                            MainActivity.this.gunMask2.getLayoutParams().width = viewInfo.gun2.width.intValue();
                            MainActivity.this.gunMask2.getLayoutParams().height = viewInfo.gun2.height.intValue();
                            MainActivity.this.temp2.getLayoutParams().width = viewInfo.gun2.width.intValue();
                            MainActivity.this.temp2.getLayoutParams().height = viewInfo.gun2.height.intValue();
                            MainActivity.this.gunMask2.requestLayout();
                            MainActivity.this.temp2.requestLayout();
                            return;
                        case 2:
                            int intValue4 = viewInfo.mirrorDTO.height.intValue() + 1;
                            if (viewInfo.mirrorDTO.x.intValue() + intValue4 > AppInstance.s_nScreenW || viewInfo.mirrorDTO.y.intValue() + intValue4 > AppInstance.s_nScreenH) {
                                return;
                            }
                            ViewInfo.MaskDTO maskDTO3 = viewInfo.mirrorDTO;
                            ViewInfo.MaskDTO maskDTO4 = viewInfo.mirrorDTO;
                            Integer valueOf2 = Integer.valueOf(intValue4);
                            maskDTO4.height = valueOf2;
                            maskDTO3.width = valueOf2;
                            MainActivity.this.mirror.getLayoutParams().width = viewInfo.mirrorDTO.width.intValue();
                            MainActivity.this.mirror.getLayoutParams().height = viewInfo.mirrorDTO.height.intValue();
                            MainActivity.this.mirror.requestLayout();
                            return;
                        case 3:
                            int intValue5 = viewInfo.doubleMirror.height.intValue() + 1;
                            int intValue6 = (int) ((viewInfo.doubleMirror.height.intValue() * MainActivity.this.m_fScopeModeRateW2H) + 0.5f);
                            if (viewInfo.doubleMirror.x.intValue() + intValue6 > AppInstance.s_nScreenW || viewInfo.doubleMirror.y.intValue() + intValue5 > AppInstance.s_nScreenH) {
                                return;
                            }
                            viewInfo.doubleMirror.height = Integer.valueOf(intValue5);
                            viewInfo.doubleMirror.width = Integer.valueOf(intValue6);
                            MainActivity.this.doubleMirrorMask.getLayoutParams().width = viewInfo.doubleMirror.width.intValue();
                            MainActivity.this.doubleMirrorMask.getLayoutParams().height = viewInfo.doubleMirror.height.intValue();
                            MainActivity.this.doubleMirrorSelect.getLayoutParams().width = viewInfo.doubleMirror.width.intValue();
                            MainActivity.this.doubleMirrorSelect.getLayoutParams().height = viewInfo.doubleMirror.height.intValue();
                            MainActivity.this.doubleMirrorMask.requestLayout();
                            return;
                        case 4:
                            AIFloatInfo aIFloatInfo = MainActivity.this.m_mapAIFloat.get(MainActivity.s_vInfo.selectTag);
                            if (aIFloatInfo != null) {
                                int intValue7 = aIFloatInfo.m_data.height.intValue() + 1;
                                int intValue8 = (int) ((aIFloatInfo.m_data.height.intValue() * MainActivity.this.m_fBagtagModelRateW2H) + 0.5f);
                                if (aIFloatInfo.m_data.x.intValue() + intValue8 > AppInstance.s_nScreenW || aIFloatInfo.m_data.y.intValue() + intValue7 > AppInstance.s_nScreenH) {
                                    return;
                                }
                                aIFloatInfo.m_data.height = Integer.valueOf(intValue7);
                                aIFloatInfo.m_data.width = Integer.valueOf(intValue8);
                                aIFloatInfo.m_viewMask.getLayoutParams().width = aIFloatInfo.m_data.width.intValue();
                                aIFloatInfo.m_viewMask.getLayoutParams().height = aIFloatInfo.m_data.height.intValue();
                                aIFloatInfo.m_viewFrame.getLayoutParams().width = aIFloatInfo.m_data.width.intValue();
                                aIFloatInfo.m_viewFrame.getLayoutParams().height = aIFloatInfo.m_data.height.intValue();
                                aIFloatInfo.m_viewMask.requestLayout();
                                return;
                            }
                            return;
                        case 5:
                        case 6:
                        case 7:
                        case '\b':
                        case 11:
                        case '\f':
                            AIFloatInfo aIFloatInfo2 = MainActivity.this.m_mapAIFloat.get(MainActivity.s_vInfo.selectTag);
                            int intValue9 = aIFloatInfo2.m_data.height.intValue() + 1;
                            if (aIFloatInfo2.m_data.x.intValue() + intValue9 > AppInstance.s_nScreenW || aIFloatInfo2.m_data.y.intValue() + intValue9 > AppInstance.s_nScreenH) {
                                return;
                            }
                            ViewInfo.MaskDTO maskDTO5 = aIFloatInfo2.m_data;
                            ViewInfo.MaskDTO maskDTO6 = aIFloatInfo2.m_data;
                            Integer valueOf3 = Integer.valueOf(intValue9);
                            maskDTO6.height = valueOf3;
                            maskDTO5.width = valueOf3;
                            aIFloatInfo2.m_viewMask.getLayoutParams().width = aIFloatInfo2.m_data.width.intValue();
                            aIFloatInfo2.m_viewMask.getLayoutParams().height = aIFloatInfo2.m_data.height.intValue();
                            aIFloatInfo2.m_viewFrame.getLayoutParams().width = aIFloatInfo2.m_data.width.intValue();
                            aIFloatInfo2.m_viewFrame.getLayoutParams().height = aIFloatInfo2.m_data.height.intValue();
                            aIFloatInfo2.m_viewMask.requestLayout();
                            String[] strArr = {Constants.FLOAT_TAG_AI_GUN_HEAD1, Constants.FLOAT_TAG_AI_GUN_HEAD2, Constants.FLOAT_TAG_AI_GUN_HANDLE1, Constants.FLOAT_TAG_AI_GUN_HANDLE2, Constants.FLOAT_TAG_AI_GUN_TAIL1, Constants.FLOAT_TAG_AI_GUN_TAIL2};
                            for (int i2 = 0; i2 < 6; i2++) {
                                String str2 = strArr[i2];
                                if (!str2.equals(MainActivity.s_vInfo.selectTag)) {
                                    AIFloatInfo aIFloatInfo3 = MainActivity.this.m_mapAIFloat.get(str2);
                                    int intValue10 = aIFloatInfo2.m_data.height.intValue();
                                    if (aIFloatInfo3.m_data.x.intValue() + intValue10 <= AppInstance.s_nScreenW && aIFloatInfo3.m_data.y.intValue() + intValue10 <= AppInstance.s_nScreenH) {
                                        ViewInfo.MaskDTO maskDTO7 = aIFloatInfo3.m_data;
                                        ViewInfo.MaskDTO maskDTO8 = aIFloatInfo3.m_data;
                                        Integer valueOf4 = Integer.valueOf(intValue10);
                                        maskDTO8.height = valueOf4;
                                        maskDTO7.width = valueOf4;
                                        aIFloatInfo3.m_viewMask.getLayoutParams().width = aIFloatInfo3.m_data.width.intValue();
                                        aIFloatInfo3.m_viewMask.getLayoutParams().height = aIFloatInfo3.m_data.height.intValue();
                                        aIFloatInfo3.m_viewFrame.getLayoutParams().width = aIFloatInfo3.m_data.width.intValue();
                                        aIFloatInfo3.m_viewFrame.getLayoutParams().height = aIFloatInfo3.m_data.height.intValue();
                                        aIFloatInfo3.m_viewMask.requestLayout();
                                    }
                                }
                            }
                            return;
                        case '\t':
                            int intValue11 = viewInfo.downDTO.height.intValue() + 1;
                            if (viewInfo.downDTO.x.intValue() + intValue11 > AppInstance.s_nScreenW || viewInfo.downDTO.y.intValue() + intValue11 > AppInstance.s_nScreenH) {
                                return;
                            }
                            ViewInfo.MaskDTO maskDTO9 = viewInfo.downDTO;
                            ViewInfo.MaskDTO maskDTO10 = viewInfo.downDTO;
                            Integer valueOf5 = Integer.valueOf(intValue11);
                            maskDTO10.height = valueOf5;
                            maskDTO9.width = valueOf5;
                            MainActivity.this.downMask.getLayoutParams().width = viewInfo.downDTO.width.intValue();
                            MainActivity.this.downMask.getLayoutParams().height = viewInfo.downDTO.height.intValue();
                            MainActivity.this.downMask.requestLayout();
                            return;
                        case '\n':
                            int intValue12 = viewInfo.gun2.height.intValue() + 1;
                            int intValue13 = Float.valueOf((intValue12 * MainActivity.this.m_fGunModeRateW2H) + 0.5f).intValue();
                            if (viewInfo.gun2.x.intValue() + intValue13 > AppInstance.s_nScreenW || viewInfo.gun2.y.intValue() + intValue12 > AppInstance.s_nScreenH) {
                                return;
                            }
                            viewInfo.gun2.width = Integer.valueOf(intValue13);
                            viewInfo.gun2.height = Integer.valueOf(intValue12);
                            MainActivity.this.gunMask2.getLayoutParams().width = viewInfo.gun2.width.intValue();
                            MainActivity.this.gunMask2.getLayoutParams().height = viewInfo.gun2.height.intValue();
                            MainActivity.this.temp2.getLayoutParams().width = viewInfo.gun2.width.intValue();
                            MainActivity.this.temp2.getLayoutParams().height = viewInfo.gun2.height.intValue();
                            MainActivity.this.gunMask2.requestLayout();
                            MainActivity.this.temp2.requestLayout();
                            viewInfo.gun1.width = viewInfo.gun2.width;
                            viewInfo.gun1.height = viewInfo.gun2.height;
                            viewInfo.gun1.x = Integer.valueOf(ViewMgr.make_sure_xy_in_range(viewInfo.gun1.x.intValue(), viewInfo.gun1.width.intValue(), AppInstance.s_nScreenW));
                            viewInfo.gun1.y = Integer.valueOf(ViewMgr.make_sure_xy_in_range(viewInfo.gun1.y.intValue(), viewInfo.gun1.height.intValue(), AppInstance.s_nScreenH));
                            MainActivity.this.gunMask1.getLayoutParams().width = viewInfo.gun1.width.intValue();
                            MainActivity.this.gunMask1.getLayoutParams().height = viewInfo.gun1.height.intValue();
                            MainActivity.this.temp1.getLayoutParams().width = viewInfo.gun1.width.intValue();
                            MainActivity.this.temp1.getLayoutParams().height = viewInfo.gun1.height.intValue();
                            MainActivity.this.gunMask1.requestLayout();
                            MainActivity.this.temp1.requestLayout();
                            return;
                        case '\r':
                            AIFloatInfo aIFloatInfo4 = MainActivity.this.m_mapAIFloat.get(MainActivity.s_vInfo.selectTag);
                            if (aIFloatInfo4 != null) {
                                int intValue14 = aIFloatInfo4.m_data.height.intValue() + 1;
                                int intValue15 = (int) ((aIFloatInfo4.m_data.height.intValue() * MainActivity.this.m_fTakeoffModelRateW2H) + 0.5f);
                                if (aIFloatInfo4.m_data.x.intValue() + intValue15 > AppInstance.s_nScreenW || aIFloatInfo4.m_data.y.intValue() + intValue14 > AppInstance.s_nScreenH) {
                                    return;
                                }
                                aIFloatInfo4.m_data.height = Integer.valueOf(intValue14);
                                aIFloatInfo4.m_data.width = Integer.valueOf(intValue15);
                                aIFloatInfo4.m_viewMask.getLayoutParams().width = aIFloatInfo4.m_data.width.intValue();
                                aIFloatInfo4.m_viewMask.getLayoutParams().height = aIFloatInfo4.m_data.height.intValue();
                                aIFloatInfo4.m_viewFrame.getLayoutParams().width = aIFloatInfo4.m_data.width.intValue();
                                aIFloatInfo4.m_viewFrame.getLayoutParams().height = aIFloatInfo4.m_data.height.intValue();
                                aIFloatInfo4.m_viewMask.requestLayout();
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            });
            findViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.68.11
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    ViewInfo viewInfo = MainActivity.s_vInfo;
                    String str = MainActivity.s_vInfo.selectTag;
                    str.hashCode();
                    char c = 65535;
                    switch (str.hashCode()) {
                        case -2075930639:
                            if (str.equals(Constants.FLOAT_TAG_AI_C)) {
                                c = 0;
                                break;
                            }
                            break;
                        case -1234755340:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN1)) {
                                c = 1;
                                break;
                            }
                            break;
                        case -1073910849:
                            if (str.equals(Constants.FLOAT_TAG_AI_MIRROW)) {
                                c = 2;
                                break;
                            }
                            break;
                        case -954571125:
                            if (str.equals(Constants.FLOAT_TAG_SCOPE2)) {
                                c = 3;
                                break;
                            }
                            break;
                        case 62270144:
                            if (str.equals(Constants.FLOAT_TAG_AI_BAG_TAG)) {
                                c = 4;
                                break;
                            }
                            break;
                        case 179370297:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN_HEAD1)) {
                                c = 5;
                                break;
                            }
                            break;
                        case 179370298:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN_HEAD2)) {
                                c = 6;
                                break;
                            }
                            break;
                        case 190341321:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN_TAIL1)) {
                                c = 7;
                                break;
                            }
                            break;
                        case 190341322:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN_TAIL2)) {
                                c = '\b';
                                break;
                            }
                            break;
                        case 347818137:
                            if (str.equals(Constants.FLOAT_TAG_AI_Z)) {
                                c = '\t';
                                break;
                            }
                            break;
                        case 377290174:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN2)) {
                                c = '\n';
                                break;
                            }
                            break;
                        case 473712625:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN_HANDLE1)) {
                                c = 11;
                                break;
                            }
                            break;
                        case 473712626:
                            if (str.equals(Constants.FLOAT_TAG_AI_GUN_HANDLE2)) {
                                c = '\f';
                                break;
                            }
                            break;
                        case 1270510112:
                            if (str.equals(Constants.FLOAT_TAG_AI_TAKE_OFF_TAG)) {
                                c = '\r';
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            int intValue = MainActivity.s_vInfo.squatDTO.height.intValue() - 1;
                            if (intValue < 30) {
                                return;
                            }
                            ViewInfo.MaskDTO maskDTO = MainActivity.s_vInfo.squatDTO;
                            ViewInfo.MaskDTO maskDTO2 = MainActivity.s_vInfo.squatDTO;
                            Integer valueOf = Integer.valueOf(intValue);
                            maskDTO2.height = valueOf;
                            maskDTO.width = valueOf;
                            MainActivity.this.squatMask.getLayoutParams().width = MainActivity.s_vInfo.squatDTO.width.intValue();
                            MainActivity.this.squatMask.getLayoutParams().height = MainActivity.s_vInfo.squatDTO.height.intValue();
                            MainActivity.this.squatMask.requestLayout();
                            return;
                        case 1:
                            int intValue2 = MainActivity.s_vInfo.gun1.height.intValue() - 1;
                            if (intValue2 <= 0) {
                                return;
                            }
                            MainActivity.s_vInfo.gun1.width = Integer.valueOf(Float.valueOf((intValue2 * MainActivity.this.m_fGunModeRateW2H) + 0.5f).intValue());
                            MainActivity.s_vInfo.gun1.height = Integer.valueOf(intValue2);
                            MainActivity.this.gunMask1.getLayoutParams().width = MainActivity.s_vInfo.gun1.width.intValue();
                            MainActivity.this.gunMask1.getLayoutParams().height = MainActivity.s_vInfo.gun1.height.intValue();
                            MainActivity.this.temp1.getLayoutParams().width = MainActivity.s_vInfo.gun1.width.intValue();
                            MainActivity.this.temp1.getLayoutParams().height = MainActivity.s_vInfo.gun1.height.intValue();
                            MainActivity.this.gunMask1.requestLayout();
                            MainActivity.this.temp1.requestLayout();
                            MainActivity.s_vInfo.gun2.width = MainActivity.s_vInfo.gun1.width;
                            MainActivity.s_vInfo.gun2.height = MainActivity.s_vInfo.gun1.height;
                            MainActivity.this.gunMask2.getLayoutParams().width = MainActivity.s_vInfo.gun2.width.intValue();
                            MainActivity.this.gunMask2.getLayoutParams().height = MainActivity.s_vInfo.gun2.height.intValue();
                            MainActivity.this.temp2.getLayoutParams().width = MainActivity.s_vInfo.gun2.width.intValue();
                            MainActivity.this.temp2.getLayoutParams().height = MainActivity.s_vInfo.gun2.height.intValue();
                            MainActivity.this.gunMask2.requestLayout();
                            MainActivity.this.temp2.requestLayout();
                            return;
                        case 2:
                            int intValue3 = MainActivity.s_vInfo.mirrorDTO.height.intValue() - 1;
                            if (intValue3 < 30) {
                                return;
                            }
                            ViewInfo.MaskDTO maskDTO3 = MainActivity.s_vInfo.mirrorDTO;
                            ViewInfo.MaskDTO maskDTO4 = MainActivity.s_vInfo.mirrorDTO;
                            Integer valueOf2 = Integer.valueOf(intValue3);
                            maskDTO4.height = valueOf2;
                            maskDTO3.width = valueOf2;
                            MainActivity.this.mirror.getLayoutParams().width = MainActivity.s_vInfo.mirrorDTO.width.intValue();
                            MainActivity.this.mirror.getLayoutParams().height = MainActivity.s_vInfo.mirrorDTO.height.intValue();
                            MainActivity.this.mirror.requestLayout();
                            return;
                        case 3:
                            if (MainActivity.s_vInfo.doubleMirror.height.intValue() <= 20) {
                                return;
                            }
                            ViewInfo.MaskDTO maskDTO5 = viewInfo.doubleMirror;
                            Integer num = maskDTO5.height;
                            maskDTO5.height = Integer.valueOf(maskDTO5.height.intValue() - 1);
                            viewInfo.doubleMirror.width = Integer.valueOf((int) ((viewInfo.doubleMirror.height.intValue() * MainActivity.this.m_fScopeModeRateW2H) + 0.5f));
                            MainActivity.this.doubleMirrorMask.getLayoutParams().width = MainActivity.s_vInfo.doubleMirror.width.intValue();
                            MainActivity.this.doubleMirrorMask.getLayoutParams().height = MainActivity.s_vInfo.doubleMirror.height.intValue();
                            MainActivity.this.doubleMirrorSelect.getLayoutParams().width = MainActivity.s_vInfo.doubleMirror.width.intValue();
                            MainActivity.this.doubleMirrorSelect.getLayoutParams().height = MainActivity.s_vInfo.doubleMirror.height.intValue();
                            MainActivity.this.doubleMirrorMask.requestLayout();
                            return;
                        case 4:
                            AIFloatInfo aIFloatInfo = MainActivity.this.m_mapAIFloat.get(MainActivity.s_vInfo.selectTag);
                            if (aIFloatInfo == null || aIFloatInfo.m_data.height.intValue() <= 20) {
                                return;
                            }
                            ViewInfo.MaskDTO maskDTO6 = aIFloatInfo.m_data;
                            Integer num2 = maskDTO6.height;
                            maskDTO6.height = Integer.valueOf(maskDTO6.height.intValue() - 1);
                            aIFloatInfo.m_data.width = Integer.valueOf((int) ((aIFloatInfo.m_data.height.intValue() * MainActivity.this.m_fBagtagModelRateW2H) + 0.5f));
                            aIFloatInfo.m_viewMask.getLayoutParams().width = aIFloatInfo.m_data.width.intValue();
                            aIFloatInfo.m_viewMask.getLayoutParams().height = aIFloatInfo.m_data.height.intValue();
                            aIFloatInfo.m_viewFrame.getLayoutParams().width = aIFloatInfo.m_data.width.intValue();
                            aIFloatInfo.m_viewFrame.getLayoutParams().height = aIFloatInfo.m_data.height.intValue();
                            aIFloatInfo.m_viewMask.requestLayout();
                            return;
                        case 5:
                        case 6:
                        case 7:
                        case '\b':
                        case 11:
                        case '\f':
                            AIFloatInfo aIFloatInfo2 = MainActivity.this.m_mapAIFloat.get(MainActivity.s_vInfo.selectTag);
                            int intValue4 = aIFloatInfo2.m_data.height.intValue() - 1;
                            if (intValue4 < 20) {
                                return;
                            }
                            ViewInfo.MaskDTO maskDTO7 = aIFloatInfo2.m_data;
                            ViewInfo.MaskDTO maskDTO8 = aIFloatInfo2.m_data;
                            Integer valueOf3 = Integer.valueOf(intValue4);
                            maskDTO8.height = valueOf3;
                            maskDTO7.width = valueOf3;
                            aIFloatInfo2.m_viewMask.getLayoutParams().width = aIFloatInfo2.m_data.width.intValue();
                            aIFloatInfo2.m_viewMask.getLayoutParams().height = aIFloatInfo2.m_data.height.intValue();
                            aIFloatInfo2.m_viewFrame.getLayoutParams().width = aIFloatInfo2.m_data.width.intValue();
                            aIFloatInfo2.m_viewFrame.getLayoutParams().height = aIFloatInfo2.m_data.height.intValue();
                            aIFloatInfo2.m_viewMask.requestLayout();
                            String[] strArr = {Constants.FLOAT_TAG_AI_GUN_HEAD1, Constants.FLOAT_TAG_AI_GUN_HEAD2, Constants.FLOAT_TAG_AI_GUN_HANDLE1, Constants.FLOAT_TAG_AI_GUN_HANDLE2, Constants.FLOAT_TAG_AI_GUN_TAIL1, Constants.FLOAT_TAG_AI_GUN_TAIL2};
                            for (int i2 = 0; i2 < 6; i2++) {
                                String str2 = strArr[i2];
                                if (!str2.equals(MainActivity.s_vInfo.selectTag)) {
                                    AIFloatInfo aIFloatInfo3 = MainActivity.this.m_mapAIFloat.get(str2);
                                    ViewInfo.MaskDTO maskDTO9 = aIFloatInfo3.m_data;
                                    ViewInfo.MaskDTO maskDTO10 = aIFloatInfo3.m_data;
                                    Integer num3 = aIFloatInfo2.m_data.width;
                                    maskDTO10.height = num3;
                                    maskDTO9.width = num3;
                                    aIFloatInfo3.m_viewMask.getLayoutParams().width = aIFloatInfo3.m_data.width.intValue();
                                    aIFloatInfo3.m_viewMask.getLayoutParams().height = aIFloatInfo3.m_data.height.intValue();
                                    aIFloatInfo3.m_viewFrame.getLayoutParams().width = aIFloatInfo3.m_data.width.intValue();
                                    aIFloatInfo3.m_viewFrame.getLayoutParams().height = aIFloatInfo3.m_data.height.intValue();
                                    aIFloatInfo3.m_viewMask.requestLayout();
                                }
                            }
                            return;
                        case '\t':
                            int intValue5 = MainActivity.s_vInfo.downDTO.height.intValue() - 1;
                            if (intValue5 < 30) {
                                return;
                            }
                            ViewInfo.MaskDTO maskDTO11 = MainActivity.s_vInfo.downDTO;
                            ViewInfo.MaskDTO maskDTO12 = MainActivity.s_vInfo.downDTO;
                            Integer valueOf4 = Integer.valueOf(intValue5);
                            maskDTO12.height = valueOf4;
                            maskDTO11.width = valueOf4;
                            MainActivity.this.downMask.getLayoutParams().width = MainActivity.s_vInfo.downDTO.width.intValue();
                            MainActivity.this.downMask.getLayoutParams().height = MainActivity.s_vInfo.downDTO.height.intValue();
                            MainActivity.this.downMask.requestLayout();
                            return;
                        case '\n':
                            int intValue6 = MainActivity.s_vInfo.gun2.height.intValue() - 1;
                            if (intValue6 <= 0) {
                                return;
                            }
                            MainActivity.s_vInfo.gun2.width = Integer.valueOf(Float.valueOf((intValue6 * MainActivity.this.m_fGunModeRateW2H) + 0.5f).intValue());
                            MainActivity.s_vInfo.gun2.height = Integer.valueOf(intValue6);
                            MainActivity.this.gunMask2.getLayoutParams().width = MainActivity.s_vInfo.gun2.width.intValue();
                            MainActivity.this.gunMask2.getLayoutParams().height = MainActivity.s_vInfo.gun2.height.intValue();
                            MainActivity.this.temp2.getLayoutParams().width = MainActivity.s_vInfo.gun2.width.intValue();
                            MainActivity.this.temp2.getLayoutParams().height = MainActivity.s_vInfo.gun2.height.intValue();
                            MainActivity.this.gunMask2.requestLayout();
                            MainActivity.this.temp2.requestLayout();
                            MainActivity.s_vInfo.gun1.width = MainActivity.s_vInfo.gun2.width;
                            MainActivity.s_vInfo.gun1.height = MainActivity.s_vInfo.gun2.height;
                            MainActivity.this.gunMask1.getLayoutParams().width = MainActivity.s_vInfo.gun1.width.intValue();
                            MainActivity.this.gunMask1.getLayoutParams().height = MainActivity.s_vInfo.gun1.height.intValue();
                            MainActivity.this.temp1.getLayoutParams().width = MainActivity.s_vInfo.gun1.width.intValue();
                            MainActivity.this.temp1.getLayoutParams().height = MainActivity.s_vInfo.gun1.height.intValue();
                            MainActivity.this.gunMask1.requestLayout();
                            MainActivity.this.temp1.requestLayout();
                            return;
                        case '\r':
                            AIFloatInfo aIFloatInfo4 = MainActivity.this.m_mapAIFloat.get(MainActivity.s_vInfo.selectTag);
                            if (aIFloatInfo4 == null || aIFloatInfo4.m_data.height.intValue() <= 20) {
                                return;
                            }
                            ViewInfo.MaskDTO maskDTO13 = aIFloatInfo4.m_data;
                            Integer num4 = maskDTO13.height;
                            maskDTO13.height = Integer.valueOf(maskDTO13.height.intValue() - 1);
                            aIFloatInfo4.m_data.width = Integer.valueOf((int) ((aIFloatInfo4.m_data.height.intValue() * MainActivity.this.m_fTakeoffModelRateW2H) + 0.5f));
                            aIFloatInfo4.m_viewMask.getLayoutParams().width = aIFloatInfo4.m_data.width.intValue();
                            aIFloatInfo4.m_viewMask.getLayoutParams().height = aIFloatInfo4.m_data.height.intValue();
                            aIFloatInfo4.m_viewFrame.getLayoutParams().width = aIFloatInfo4.m_data.width.intValue();
                            aIFloatInfo4.m_viewFrame.getLayoutParams().height = aIFloatInfo4.m_data.height.intValue();
                            aIFloatInfo4.m_viewMask.requestLayout();
                            return;
                        default:
                            return;
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$0$com-baidu-kwgames-MainActivity$68  reason: not valid java name */
        public /* synthetic */ void m96lambda$invoke$0$combaidukwgamesMainActivity$68(View view) {
            MainActivity.this.m_boNeedAICommit = true;
        }
    }

    public void show_AI_pos_edit() {
        EasyFloat.Builder layout = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_MOVE).setLayout(R.layout.save_mould, new AnonymousClass68());
        layout.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.69
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.this.m_viewAIEditRoot.getLocationOnScreen(iArr);
                MainActivity.m_ini.put(Constants.CFG_AI_EDIT_FLOAT_X, iArr[0]);
                MainActivity.m_ini.put(Constants.CFG_AI_EDIT_FLOAT_Y, iArr[1]);
            }
        });
        layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true);
        layout.setLocation(NEAT.make_sure_x_visible(m_ini.getInt(Constants.CFG_AI_EDIT_FLOAT_X, (AppInstance.s_nScreenW * 3) / 10)), NEAT.make_sure_y_visible(m_ini.getInt(Constants.CFG_AI_EDIT_FLOAT_Y, 0)));
        layout.show();
        EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_PREVIEW).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setGravity(51, 0, 100).setLayout(R.layout.top_tip_mould, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.70
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.mEditTipTextView = (TextView) view.findViewById(R.id.text);
                MainActivity.this.m_imageExample = (ImageView) view.findViewById(R.id.m_imageExample);
                MainActivity.this.m_imageAdjustPreview = (ImageView) view.findViewById(R.id.adjust_preview);
            }
        }).show();
        EasyFloat.Builder layout2 = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_GUN1).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setLayout(R.layout.gun_mould, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.71
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.gunMask1 = view.findViewById(R.id.root);
                MainActivity.this.temp1 = view.findViewById(R.id.temp);
                MainActivity.this.gunMaskSelectIcon1 = view.findViewById(R.id.select_icon);
                ((TextView) view.findViewById(R.id.gun_tip)).setText(R.string.gun1);
                MainActivity.this.gunMask1.getLayoutParams().width = MainActivity.s_vInfo.gun1.width.intValue();
                MainActivity.this.gunMask1.getLayoutParams().height = MainActivity.s_vInfo.gun1.height.intValue();
                MainActivity.this.temp1.getLayoutParams().width = MainActivity.s_vInfo.gun1.width.intValue();
                MainActivity.this.temp1.getLayoutParams().height = MainActivity.s_vInfo.gun1.height.intValue();
                MainActivity.this.gunMaskSelectIcon1.setVisibility(8);
                MainActivity mainActivity = MainActivity.this;
                mainActivity.set_AI_float_mask_frame(Constants.FLOAT_TAG_AI_GUN1, mainActivity.gunMask1, MainActivity.this.gunMaskSelectIcon1);
                if (MainActivity.s_vInfo.selectTag.isEmpty()) {
                    MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_AI_GUN1;
                }
                MainActivity.this.update_AI_float_frame_and_tip();
                MainActivity.this.update_AI_float_pos(Constants.FLOAT_TAG_AI_GUN1, MainActivity.s_vInfo.gun1.x.intValue(), MainActivity.s_vInfo.gun1.y.intValue());
            }
        });
        layout2.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.72
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_AI_GUN1;
                MainActivity.this.update_AI_float_frame_and_tip();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.gunMask1.getLocationOnScreen(iArr);
                MainActivity.s_vInfo.gun1.x = Integer.valueOf(iArr[0]);
                MainActivity.s_vInfo.gun1.y = Integer.valueOf(iArr[1]);
            }
        });
        layout2.setLocation(s_vInfo.gun1.x.intValue(), s_vInfo.gun1.y.intValue());
        layout2.show();
        EasyFloat.Builder layout3 = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_GUN2).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setLayout(R.layout.gun_mould, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.73
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                FloatMgr.resetVirtualMouse();
                MainActivity.this.gunMask2 = view.findViewById(R.id.root);
                MainActivity.this.temp2 = view.findViewById(R.id.temp);
                MainActivity.this.gunMaskSelectIcon2 = view.findViewById(R.id.select_icon);
                ((TextView) view.findViewById(R.id.gun_tip)).setText(R.string.gun2);
                MainActivity.this.gunMask2.getLayoutParams().width = MainActivity.s_vInfo.gun2.width.intValue();
                MainActivity.this.gunMask2.getLayoutParams().height = MainActivity.s_vInfo.gun2.height.intValue();
                MainActivity.this.temp2.getLayoutParams().width = MainActivity.s_vInfo.gun2.width.intValue();
                MainActivity.this.temp2.getLayoutParams().height = MainActivity.s_vInfo.gun2.height.intValue();
                MainActivity.this.gunMaskSelectIcon2.setVisibility(8);
                MainActivity mainActivity = MainActivity.this;
                mainActivity.set_AI_float_mask_frame(Constants.FLOAT_TAG_AI_GUN2, mainActivity.gunMask2, MainActivity.this.gunMaskSelectIcon2);
                MainActivity.this.update_AI_float_pos(Constants.FLOAT_TAG_AI_GUN2, MainActivity.s_vInfo.gun2.x.intValue(), MainActivity.s_vInfo.gun2.y.intValue());
                MainActivity.this.update_AI_float_frame_and_tip();
            }
        });
        layout3.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.74
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_AI_GUN2;
                MainActivity.this.update_AI_float_frame_and_tip();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.gunMask2.getLocationOnScreen(iArr);
                MainActivity.s_vInfo.gun2.x = Integer.valueOf(iArr[0]);
                MainActivity.s_vInfo.gun2.y = Integer.valueOf(iArr[1]);
            }
        });
        layout3.setLocation(s_vInfo.gun2.x.intValue(), s_vInfo.gun2.y.intValue());
        layout3.show();
        EasyFloat.Builder layout4 = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_MIRROW).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setLayout(R.layout.distinguish, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.75
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.mirror = view.findViewById(R.id.mirror);
                ((TextView) view.findViewById(R.id.tip_text)).setText(R.string.open_mirror);
                MainActivity.this.mirrorSelect = view.findViewById(R.id.select_icon);
                MainActivity.this.mirror.getLayoutParams().width = MainActivity.s_vInfo.mirrorDTO.width.intValue();
                MainActivity.this.mirror.getLayoutParams().height = MainActivity.s_vInfo.mirrorDTO.height.intValue();
                MainActivity mainActivity = MainActivity.this;
                mainActivity.set_AI_float_mask_frame(Constants.FLOAT_TAG_AI_MIRROW, mainActivity.mirror, MainActivity.this.mirrorSelect);
                MainActivity.this.update_AI_float_pos(Constants.FLOAT_TAG_AI_MIRROW, MainActivity.s_vInfo.mirrorDTO.x.intValue(), MainActivity.s_vInfo.mirrorDTO.y.intValue());
                MainActivity.this.update_AI_float_frame_and_tip();
            }
        });
        layout4.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.76
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_AI_MIRROW;
                MainActivity.this.update_AI_float_frame_and_tip();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.mirror.getLocationOnScreen(iArr);
                MainActivity.s_vInfo.mirrorDTO.x = Integer.valueOf(iArr[0]);
                MainActivity.s_vInfo.mirrorDTO.y = Integer.valueOf(iArr[1]);
            }
        });
        layout4.setLocation(s_vInfo.mirrorDTO.x.intValue(), s_vInfo.mirrorDTO.y.intValue());
        layout4.show();
        EasyFloat.Builder layout5 = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_Z).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setLayout(R.layout.distinguish, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.77
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.downMask = view.findViewById(R.id.mirror);
                ((TextView) view.findViewById(R.id.tip_text)).setText(R.string.pa);
                MainActivity.this.downSelect = view.findViewById(R.id.select_icon);
                MainActivity.this.downMask.getLayoutParams().width = MainActivity.s_vInfo.downDTO.width.intValue();
                MainActivity.this.downMask.getLayoutParams().height = MainActivity.s_vInfo.downDTO.height.intValue();
                MainActivity mainActivity = MainActivity.this;
                mainActivity.set_AI_float_mask_frame(Constants.FLOAT_TAG_AI_Z, mainActivity.downMask, MainActivity.this.downSelect);
                MainActivity.this.update_AI_float_pos(Constants.FLOAT_TAG_AI_Z, MainActivity.s_vInfo.downDTO.x.intValue(), MainActivity.s_vInfo.downDTO.y.intValue());
                MainActivity.this.update_AI_float_frame_and_tip();
            }
        });
        layout5.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.78
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_AI_Z;
                MainActivity.this.update_AI_float_frame_and_tip();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.downMask.getLocationOnScreen(iArr);
                MainActivity.s_vInfo.downDTO.x = Integer.valueOf(iArr[0]);
                MainActivity.s_vInfo.downDTO.y = Integer.valueOf(iArr[1]);
            }
        });
        layout5.setLocation(s_vInfo.downDTO.x.intValue(), s_vInfo.downDTO.y.intValue());
        layout5.show();
        EasyFloat.Builder layout6 = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_C).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setLayout(R.layout.distinguish, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.79
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.squatMask = view.findViewById(R.id.mirror);
                ((TextView) view.findViewById(R.id.tip_text)).setText(R.string.dun);
                MainActivity.this.squatSelect = view.findViewById(R.id.select_icon);
                MainActivity.this.squatMask.getLayoutParams().width = MainActivity.s_vInfo.squatDTO.width.intValue();
                MainActivity.this.squatMask.getLayoutParams().height = MainActivity.s_vInfo.squatDTO.height.intValue();
                MainActivity mainActivity = MainActivity.this;
                mainActivity.set_AI_float_mask_frame(Constants.FLOAT_TAG_AI_C, mainActivity.squatMask, MainActivity.this.squatSelect);
                MainActivity.this.update_AI_float_pos(Constants.FLOAT_TAG_AI_C, MainActivity.s_vInfo.squatDTO.x.intValue(), MainActivity.s_vInfo.squatDTO.y.intValue());
                MainActivity.this.update_AI_float_frame_and_tip();
            }
        });
        layout6.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.80
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_AI_C;
                MainActivity.this.update_AI_float_frame_and_tip();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.squatMask.getLocationOnScreen(iArr);
                MainActivity.s_vInfo.squatDTO.x = Integer.valueOf(iArr[0]);
                MainActivity.s_vInfo.squatDTO.y = Integer.valueOf(iArr[1]);
            }
        });
        layout6.setLocation(s_vInfo.squatDTO.x.intValue(), s_vInfo.squatDTO.y.intValue());
        layout6.show();
        EasyFloat.Builder layout7 = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_SCOPE2).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setLayout(R.layout.double_distinguish, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.81
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.doubleMirrorMask = view.findViewById(R.id.mirror_Layer);
                ((TextView) view.findViewById(R.id.tip_text)).setText(R.string.double_mirror);
                if (!AppInstance.is_hpjy_AI_kit()) {
                    view.findViewById(R.id.model).setBackgroundResource(NEAT.is_chinese() ? Constants.get_cur_AI_kit().m_nScopeModelCh : Constants.get_cur_AI_kit().m_nScopeModelEn);
                }
                MainActivity.this.doubleMirrorSelect = view.findViewById(R.id.select_icon);
                MainActivity.this.doubleMirrorMask.getLayoutParams().width = MainActivity.s_vInfo.doubleMirror.width.intValue();
                MainActivity.this.doubleMirrorMask.getLayoutParams().height = MainActivity.s_vInfo.doubleMirror.height.intValue();
                MainActivity.this.doubleMirrorSelect.getLayoutParams().width = MainActivity.s_vInfo.doubleMirror.width.intValue();
                MainActivity.this.doubleMirrorSelect.getLayoutParams().height = MainActivity.s_vInfo.doubleMirror.height.intValue();
                MainActivity mainActivity = MainActivity.this;
                mainActivity.set_AI_float_mask_frame(Constants.FLOAT_TAG_SCOPE2, mainActivity.doubleMirrorMask, MainActivity.this.doubleMirrorSelect);
                MainActivity.this.update_AI_float_pos(Constants.FLOAT_TAG_SCOPE2, MainActivity.s_vInfo.doubleMirror.x.intValue(), MainActivity.s_vInfo.doubleMirror.y.intValue());
                MainActivity.this.update_AI_float_frame_and_tip();
            }
        });
        layout7.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.82
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_SCOPE2;
                MainActivity.this.update_AI_float_frame_and_tip();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.doubleMirrorMask.getLocationOnScreen(iArr);
                MainActivity.s_vInfo.doubleMirror.x = Integer.valueOf(iArr[0]);
                MainActivity.s_vInfo.doubleMirror.y = Integer.valueOf(iArr[1]);
            }
        });
        layout7.setLocation(s_vInfo.doubleMirror.x.intValue(), s_vInfo.doubleMirror.y.intValue());
        layout7.show();
        final ViewInfo.MaskDTO maskDTO = s_vInfo.bagTag;
        EasyFloat.Builder layout8 = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_BAG_TAG).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setLayout(R.layout.ai_parts, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.83
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.m_viewBagtagImage = view.findViewById(R.id.model);
                MainActivity.this.m_viewBagtagImage.setBackgroundResource(R.mipmap.bagtag);
                ((TextView) view.findViewById(R.id.tip_text)).setText(R.string.ai_bag_tag);
                MainActivity.this.m_viewBagtag = view.findViewById(R.id.mirror_Layer);
                MainActivity.this.m_viewBagtag.getLayoutParams().width = maskDTO.width.intValue();
                MainActivity.this.m_viewBagtag.getLayoutParams().height = maskDTO.height.intValue();
                MainActivity.this.m_viewBagtagFrame = view.findViewById(R.id.select_icon);
                MainActivity.this.m_viewBagtagFrame.getLayoutParams().width = maskDTO.width.intValue();
                MainActivity.this.m_viewBagtagFrame.getLayoutParams().height = maskDTO.height.intValue();
                MainActivity mainActivity = MainActivity.this;
                mainActivity.set_AI_float_mask_frame(Constants.FLOAT_TAG_AI_BAG_TAG, mainActivity.m_viewBagtag, MainActivity.this.m_viewBagtagFrame);
                MainActivity.this.update_AI_float_pos(Constants.FLOAT_TAG_AI_BAG_TAG, maskDTO.x.intValue(), maskDTO.y.intValue());
                MainActivity.this.update_AI_float_frame_and_tip();
                if (MainActivity.this.m_boGunPartsAI && MainActivity.this.m_sSysStatus.has_gun_parts_reduce_capacity()) {
                    return;
                }
                EasyFloat.hideAppFloat(Constants.FLOAT_TAG_AI_BAG_TAG);
            }
        });
        layout8.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.84
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_AI_BAG_TAG;
                MainActivity.this.update_AI_float_frame_and_tip();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.m_viewBagtag.getLocationOnScreen(iArr);
                maskDTO.x = Integer.valueOf(iArr[0]);
                maskDTO.y = Integer.valueOf(iArr[1]);
            }
        });
        layout8.setLocation(maskDTO.x.intValue(), maskDTO.y.intValue());
        layout8.show();
        final ViewInfo.MaskDTO maskDTO2 = s_vInfo.takeOffTag;
        EasyFloat.Builder layout9 = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_TAKE_OFF_TAG).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setLayout(R.layout.ai_parts, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.85
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.m_viewTakeOfftagImage = view.findViewById(R.id.model);
                MainActivity.this.m_viewTakeOfftagImage.setBackgroundResource(Constants.get_cur_AI_kit().m_nTakeOffModel);
                ((TextView) view.findViewById(R.id.tip_text)).setText(R.string.ai_take_off_title);
                MainActivity.this.m_viewTakeOfftag = view.findViewById(R.id.mirror_Layer);
                MainActivity.this.m_viewTakeOfftag.getLayoutParams().width = maskDTO2.width.intValue();
                MainActivity.this.m_viewTakeOfftag.getLayoutParams().height = maskDTO2.height.intValue();
                MainActivity.this.m_viewTakeOfftagFrame = view.findViewById(R.id.select_icon);
                MainActivity.this.m_viewTakeOfftagFrame.getLayoutParams().width = maskDTO2.width.intValue();
                MainActivity.this.m_viewTakeOfftagFrame.getLayoutParams().height = maskDTO2.height.intValue();
                MainActivity mainActivity = MainActivity.this;
                mainActivity.set_AI_float_mask_frame(Constants.FLOAT_TAG_AI_TAKE_OFF_TAG, mainActivity.m_viewTakeOfftag, MainActivity.this.m_viewTakeOfftagFrame);
                MainActivity.this.update_AI_float_pos(Constants.FLOAT_TAG_AI_TAKE_OFF_TAG, maskDTO2.x.intValue(), maskDTO2.y.intValue());
                MainActivity.this.update_AI_float_frame_and_tip();
            }
        });
        layout9.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.86
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_AI_TAKE_OFF_TAG;
                MainActivity.this.update_AI_float_frame_and_tip();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.m_viewTakeOfftag.getLocationOnScreen(iArr);
                maskDTO2.x = Integer.valueOf(iArr[0]);
                maskDTO2.y = Integer.valueOf(iArr[1]);
            }
        });
        layout9.setLocation(maskDTO2.x.intValue(), maskDTO2.y.intValue());
        layout9.show();
        final ViewInfo.MaskDTO maskDTO3 = s_vInfo.gun1Head;
        EasyFloat.Builder layout10 = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_GUN_HEAD1).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setLayout(R.layout.ai_parts, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.87
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.m_viewGun1HeadImage = view.findViewById(R.id.model);
                MainActivity.this.m_viewGun1HeadImage.setBackgroundResource(R.mipmap.gunhead);
                ((TextView) view.findViewById(R.id.tip_text)).setText(R.string.ai_gun_head1);
                MainActivity.this.m_viewGun1Head = view.findViewById(R.id.mirror_Layer);
                MainActivity.this.m_viewGun1Head.getLayoutParams().width = maskDTO3.width.intValue();
                MainActivity.this.m_viewGun1Head.getLayoutParams().height = maskDTO3.height.intValue();
                MainActivity.this.m_viewGun1HeadFrame = view.findViewById(R.id.select_icon);
                MainActivity.this.m_viewGun1HeadFrame.getLayoutParams().width = maskDTO3.width.intValue();
                MainActivity.this.m_viewGun1HeadFrame.getLayoutParams().height = maskDTO3.height.intValue();
                MainActivity mainActivity = MainActivity.this;
                mainActivity.set_AI_float_mask_frame(Constants.FLOAT_TAG_AI_GUN_HEAD1, mainActivity.m_viewGun1Head, MainActivity.this.m_viewGun1HeadFrame);
                MainActivity.this.update_AI_float_pos(Constants.FLOAT_TAG_AI_GUN_HEAD1, maskDTO3.x.intValue(), maskDTO3.y.intValue());
                MainActivity.this.update_AI_float_frame_and_tip();
                if (MainActivity.this.m_boGunPartsAI && MainActivity.this.m_sSysStatus.has_gun_parts_reduce_capacity()) {
                    return;
                }
                EasyFloat.hideAppFloat(Constants.FLOAT_TAG_AI_GUN_HEAD1);
            }
        });
        layout10.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.88
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_AI_GUN_HEAD1;
                MainActivity.this.update_AI_float_frame_and_tip();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.m_viewGun1Head.getLocationOnScreen(iArr);
                maskDTO3.x = Integer.valueOf(iArr[0]);
                maskDTO3.y = Integer.valueOf(iArr[1]);
            }
        });
        layout10.setLocation(maskDTO3.x.intValue(), maskDTO3.y.intValue());
        layout10.show();
        final ViewInfo.MaskDTO maskDTO4 = s_vInfo.gun2Head;
        EasyFloat.Builder layout11 = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_GUN_HEAD2).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setLayout(R.layout.ai_parts, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.89
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.m_viewGun2HeadImage = view.findViewById(R.id.model);
                MainActivity.this.m_viewGun2HeadImage.setBackgroundResource(R.mipmap.gunhead);
                ((TextView) view.findViewById(R.id.tip_text)).setText(R.string.ai_gun_head2);
                MainActivity.this.m_viewGun2Head = view.findViewById(R.id.mirror_Layer);
                MainActivity.this.m_viewGun2Head.getLayoutParams().width = maskDTO4.width.intValue();
                MainActivity.this.m_viewGun2Head.getLayoutParams().height = maskDTO4.height.intValue();
                MainActivity.this.m_viewGun2HeadFrame = view.findViewById(R.id.select_icon);
                MainActivity.this.m_viewGun2HeadFrame.getLayoutParams().width = maskDTO4.width.intValue();
                MainActivity.this.m_viewGun2HeadFrame.getLayoutParams().height = maskDTO4.height.intValue();
                MainActivity mainActivity = MainActivity.this;
                mainActivity.set_AI_float_mask_frame(Constants.FLOAT_TAG_AI_GUN_HEAD2, mainActivity.m_viewGun2Head, MainActivity.this.m_viewGun2HeadFrame);
                MainActivity.this.update_AI_float_pos(Constants.FLOAT_TAG_AI_GUN_HEAD2, maskDTO4.x.intValue(), maskDTO4.y.intValue());
                MainActivity.this.update_AI_float_frame_and_tip();
                if (MainActivity.this.m_boGunPartsAI && MainActivity.this.m_sSysStatus.has_gun_parts_reduce_capacity()) {
                    return;
                }
                EasyFloat.hideAppFloat(Constants.FLOAT_TAG_AI_GUN_HEAD2);
            }
        });
        layout11.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.90
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_AI_GUN_HEAD2;
                MainActivity.this.update_AI_float_frame_and_tip();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.m_viewGun2Head.getLocationOnScreen(iArr);
                maskDTO4.x = Integer.valueOf(iArr[0]);
                maskDTO4.y = Integer.valueOf(iArr[1]);
            }
        });
        layout11.setLocation(maskDTO4.x.intValue(), maskDTO4.y.intValue());
        layout11.show();
        final ViewInfo.MaskDTO maskDTO5 = s_vInfo.gun1Handle;
        EasyFloat.Builder layout12 = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_GUN_HANDLE1).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setLayout(R.layout.ai_parts, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.91
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.m_viewGun1HandleImage = view.findViewById(R.id.model);
                MainActivity.this.m_viewGun1HandleImage.setBackgroundResource(R.mipmap.gunhandle);
                ((TextView) view.findViewById(R.id.tip_text)).setText(R.string.ai_gun_handle1);
                MainActivity.this.m_viewGun1Handle = view.findViewById(R.id.mirror_Layer);
                MainActivity.this.m_viewGun1Handle.getLayoutParams().width = maskDTO5.width.intValue();
                MainActivity.this.m_viewGun1Handle.getLayoutParams().height = maskDTO5.height.intValue();
                MainActivity.this.m_viewGun1HandleFrame = view.findViewById(R.id.select_icon);
                MainActivity.this.m_viewGun1HandleFrame.getLayoutParams().width = maskDTO5.width.intValue();
                MainActivity.this.m_viewGun1HandleFrame.getLayoutParams().height = maskDTO5.height.intValue();
                MainActivity mainActivity = MainActivity.this;
                mainActivity.set_AI_float_mask_frame(Constants.FLOAT_TAG_AI_GUN_HANDLE1, mainActivity.m_viewGun1Handle, MainActivity.this.m_viewGun1HandleFrame);
                MainActivity.this.update_AI_float_pos(Constants.FLOAT_TAG_AI_GUN_HANDLE1, maskDTO5.x.intValue(), maskDTO5.y.intValue());
                MainActivity.this.update_AI_float_frame_and_tip();
                if (MainActivity.this.m_boGunPartsAI && MainActivity.this.m_sSysStatus.has_gun_parts_reduce_capacity()) {
                    return;
                }
                EasyFloat.hideAppFloat(Constants.FLOAT_TAG_AI_GUN_HANDLE1);
            }
        });
        layout12.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.92
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_AI_GUN_HANDLE1;
                MainActivity.this.update_AI_float_frame_and_tip();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.m_viewGun1Handle.getLocationOnScreen(iArr);
                maskDTO5.x = Integer.valueOf(iArr[0]);
                maskDTO5.y = Integer.valueOf(iArr[1]);
            }
        });
        layout12.setLocation(maskDTO5.x.intValue(), maskDTO5.y.intValue());
        layout12.show();
        final ViewInfo.MaskDTO maskDTO6 = s_vInfo.gun2Handle;
        EasyFloat.Builder layout13 = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_GUN_HANDLE2).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setLayout(R.layout.ai_parts, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.93
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.m_viewGun2HandleImage = view.findViewById(R.id.model);
                MainActivity.this.m_viewGun2HandleImage.setBackgroundResource(R.mipmap.gunhandle);
                ((TextView) view.findViewById(R.id.tip_text)).setText(R.string.ai_gun2_handle);
                MainActivity.this.m_viewGun2Handle = view.findViewById(R.id.mirror_Layer);
                MainActivity.this.m_viewGun2Handle.getLayoutParams().width = maskDTO6.width.intValue();
                MainActivity.this.m_viewGun2Handle.getLayoutParams().height = maskDTO6.height.intValue();
                MainActivity.this.m_viewGun2HandleFrame = view.findViewById(R.id.select_icon);
                MainActivity.this.m_viewGun2HandleFrame.getLayoutParams().width = maskDTO6.width.intValue();
                MainActivity.this.m_viewGun2HandleFrame.getLayoutParams().height = maskDTO6.height.intValue();
                MainActivity mainActivity = MainActivity.this;
                mainActivity.set_AI_float_mask_frame(Constants.FLOAT_TAG_AI_GUN_HANDLE2, mainActivity.m_viewGun2Handle, MainActivity.this.m_viewGun2HandleFrame);
                MainActivity.this.update_AI_float_pos(Constants.FLOAT_TAG_AI_GUN_HANDLE2, maskDTO6.x.intValue(), maskDTO6.y.intValue());
                MainActivity.this.update_AI_float_frame_and_tip();
                if (MainActivity.this.m_boGunPartsAI && MainActivity.this.m_sSysStatus.has_gun_parts_reduce_capacity()) {
                    return;
                }
                EasyFloat.hideAppFloat(Constants.FLOAT_TAG_AI_GUN_HANDLE2);
            }
        });
        layout13.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.94
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_AI_GUN_HANDLE2;
                MainActivity.this.update_AI_float_frame_and_tip();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.m_viewGun2Handle.getLocationOnScreen(iArr);
                maskDTO6.x = Integer.valueOf(iArr[0]);
                maskDTO6.y = Integer.valueOf(iArr[1]);
            }
        });
        layout13.setLocation(maskDTO6.x.intValue(), maskDTO6.y.intValue());
        layout13.show();
        final ViewInfo.MaskDTO maskDTO7 = s_vInfo.gun1Tail;
        EasyFloat.Builder layout14 = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_GUN_TAIL1).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setLayout(R.layout.ai_parts, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.95
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.m_viewGun1TailImage = view.findViewById(R.id.model);
                MainActivity.this.m_viewGun1TailImage.setBackgroundResource(R.mipmap.guntail);
                ((TextView) view.findViewById(R.id.tip_text)).setText(R.string.ai_gun1_tail);
                MainActivity.this.m_viewGun1Tail = view.findViewById(R.id.mirror_Layer);
                MainActivity.this.m_viewGun1Tail.getLayoutParams().width = maskDTO7.width.intValue();
                MainActivity.this.m_viewGun1Tail.getLayoutParams().height = maskDTO7.height.intValue();
                MainActivity.this.m_viewGun1TailFrame = view.findViewById(R.id.select_icon);
                MainActivity.this.m_viewGun1TailFrame.getLayoutParams().width = maskDTO7.width.intValue();
                MainActivity.this.m_viewGun1TailFrame.getLayoutParams().height = maskDTO7.height.intValue();
                MainActivity mainActivity = MainActivity.this;
                mainActivity.set_AI_float_mask_frame(Constants.FLOAT_TAG_AI_GUN_TAIL1, mainActivity.m_viewGun1Tail, MainActivity.this.m_viewGun1TailFrame);
                MainActivity.this.update_AI_float_pos(Constants.FLOAT_TAG_AI_GUN_TAIL1, maskDTO7.x.intValue(), maskDTO7.y.intValue());
                MainActivity.this.update_AI_float_frame_and_tip();
                if (MainActivity.this.m_boGunPartsAI && MainActivity.this.m_sSysStatus.has_gun_parts_reduce_capacity()) {
                    return;
                }
                EasyFloat.hideAppFloat(Constants.FLOAT_TAG_AI_GUN_TAIL1);
            }
        });
        layout14.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.96
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_AI_GUN_TAIL1;
                MainActivity.this.update_AI_float_frame_and_tip();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.m_viewGun1Tail.getLocationOnScreen(iArr);
                maskDTO7.x = Integer.valueOf(iArr[0]);
                maskDTO7.y = Integer.valueOf(iArr[1]);
            }
        });
        layout14.setLocation(maskDTO7.x.intValue(), maskDTO7.y.intValue());
        layout14.show();
        final ViewInfo.MaskDTO maskDTO8 = s_vInfo.gun2Tail;
        EasyFloat.Builder layout15 = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_AI_GUN_TAIL2).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setAppFloatAnimator(null).setLayout(R.layout.ai_parts, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.97
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.m_viewGun2TailImage = view.findViewById(R.id.model);
                MainActivity.this.m_viewGun2TailImage.setBackgroundResource(R.mipmap.guntail);
                ((TextView) view.findViewById(R.id.tip_text)).setText(R.string.ai_gun2_tail);
                MainActivity.this.m_viewGun2Tail = view.findViewById(R.id.mirror_Layer);
                MainActivity.this.m_viewGun2Tail.getLayoutParams().width = maskDTO8.width.intValue();
                MainActivity.this.m_viewGun2Tail.getLayoutParams().height = maskDTO8.height.intValue();
                MainActivity.this.m_viewGun2TailFrame = view.findViewById(R.id.select_icon);
                MainActivity.this.m_viewGun2TailFrame.getLayoutParams().width = maskDTO8.width.intValue();
                MainActivity.this.m_viewGun2TailFrame.getLayoutParams().height = maskDTO8.height.intValue();
                MainActivity mainActivity = MainActivity.this;
                mainActivity.set_AI_float_mask_frame(Constants.FLOAT_TAG_AI_GUN_TAIL2, mainActivity.m_viewGun2Tail, MainActivity.this.m_viewGun2TailFrame);
                MainActivity.this.update_AI_float_pos(Constants.FLOAT_TAG_AI_GUN_TAIL2, maskDTO8.x.intValue(), maskDTO8.y.intValue());
                MainActivity.this.update_AI_float_frame_and_tip();
                if (MainActivity.this.m_boGunPartsAI && MainActivity.this.m_sSysStatus.has_gun_parts_reduce_capacity()) {
                    return;
                }
                EasyFloat.hideAppFloat(Constants.FLOAT_TAG_AI_GUN_TAIL2);
            }
        });
        layout15.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.98
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.s_vInfo.selectTag = Constants.FLOAT_TAG_AI_GUN_TAIL2;
                MainActivity.this.update_AI_float_frame_and_tip();
            }

            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void dragEnd(View view) {
                int[] iArr = new int[2];
                MainActivity.this.m_viewGun2Tail.getLocationOnScreen(iArr);
                maskDTO8.x = Integer.valueOf(iArr[0]);
                maskDTO8.y = Integer.valueOf(iArr[1]);
            }
        });
        layout15.setLocation(maskDTO8.x.intValue(), maskDTO8.y.intValue());
        layout15.show();
        this.m_boAIEditing = true;
    }

    public void update_AI_float_pos(String str, int i, int i2) {
        if (NEAT.is_huawei().booleanValue()) {
            return;
        }
        FloatMgr.get_float_touchable_param(FloatManager.INSTANCE.getAppFloatManager(str).getParams());
        EasyFloat.updateFloat(str, i, i2);
    }

    private void connectBle(String str, String str2) {
        if (this.mThroneService != null) {
            AppInstance.mac = str;
            AppInstance.s_strBTName = str2;
            try {
                this.mThroneService.bleDisConnect();
                this.mThroneService.bleConnect(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    void try_connect_usb() {
        IThrone iThrone = this.mThroneService;
        if (iThrone == null) {
            return;
        }
        try {
            if (iThrone.usbConnect()) {
                set_ble_connect_timer(false);
            } else {
                set_ble_connect_timer(true);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void init_service() {
        if (this.mThroneService == null) {
            bindService(new Intent(getApplicationContext(), FloatWindowService.class), this.conn, 1);
        }
    }

    Boolean is_meiying_ble_name(String str) {
        for (String str2 : g_arrBleNameSupport) {
            if (-1 != str.indexOf(str2)) {
                return true;
            }
        }
        if (str.length() >= 8) {
            char charAt = str.charAt(0);
            char charAt2 = str.charAt(2);
            int i = -1;
            int i2 = -1;
            for (int i3 = 0; i3 < 32; i3++) {
                if (charAt == Constants.g_arrBleNameRandom[i3]) {
                    i = i3;
                }
                if (charAt2 == Constants.g_arrBleNameRandom[i3]) {
                    i2 = i3;
                }
            }
            if (-1 != i && -1 != i2) {
                int i4 = (i2 + 1) & 31;
                if (str.charAt(1) == Constants.g_arrBleNameRandom[(i + 2) & 31] && str.charAt(3) == Constants.g_arrBleNameRandom[i4]) {
                    return true;
                }
            }
        }
        return false;
    }

    private Boolean has_old_ble_not_unpair() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            Class[] clsArr = null;
            BluetoothAdapter.class.getDeclaredMethod("getConnectionState", null).setAccessible(true);
            for (BluetoothDevice bluetoothDevice : defaultAdapter.getBondedDevices()) {
                for (String str : g_arrBleNameSupport) {
                    if (-1 != bluetoothDevice.getName().indexOf(str)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectAfterSys() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            Class[] clsArr = null;
            BluetoothAdapter.class.getDeclaredMethod("getConnectionState", null).setAccessible(true);
            for (BluetoothDevice bluetoothDevice : defaultAdapter.getBondedDevices()) {
                if (is_meiying_ble_name(bluetoothDevice.getName()).booleanValue()) {
                    Class[] clsArr2 = null;
                    Method declaredMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", null);
                    declaredMethod.setAccessible(true);
                    Object[] objArr = null;
                    if (((Boolean) declaredMethod.invoke(bluetoothDevice, null)).booleanValue() && !AppInstance.mBleConnected) {
                        connectBle(bluetoothDevice.getAddress(), bluetoothDevice.getName());
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (1 != i) {
            if (6 == i) {
                scanBleDevices();
                return;
            }
            return;
        }
        if (EasyFloat.getAppFloatView("virtualMouse") == null) {
            FloatMgr.initVirtualMouse();
        }
        AppInstance.m_boFileAccess = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0;
        init_adb_exe();
    }

    void verifyPermissions() {
        ArrayList arrayList = new ArrayList();
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            arrayList.add("android.permission.ACCESS_COARSE_LOCATION");
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            arrayList.add("android.permission.ACCESS_FINE_LOCATION");
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            AppInstance.m_boFileAccess = false;
            arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        } else {
            AppInstance.m_boFileAccess = true;
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (arrayList.isEmpty()) {
            return;
        }
        ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[0]), 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        stopCaptureThread();
        release();
        unregister_receiver();
        Disposable disposable = this.mMouseDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        try {
            IThrone iThrone = this.mThroneService;
            if (iThrone != null) {
                iThrone.removeBleCallback(this.mBleCallback);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_BALL);
        try {
            boolean stopService = stopService(new Intent(getApplicationContext(), FloatWindowService.class));
            unbindService(this.conn);
            String str = TAG;
            Log.d(str, "bCon:unbindService=" + stopService);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    void set_in_main_UI(boolean z) {
        try {
            IThrone iThrone = this.mThroneService;
            if (iThrone != null) {
                iThrone.setInMainUI(z);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.mResumed = false;
        stop_marquee_text_change_timer();
        set_in_main_UI(false);
        if (this.mAI.isChecked()) {
            FloatMgr.hide_aim_float();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        set_in_main_UI(true);
        begin_marquee_text_change_timer();
        super.onResume();
        this.mResumed = true;
        setSystemStatusTask(true);
        if (NEAT.get_float_permission(this)) {
            show_float_ball();
        }
        FloatMgr.initVirtualMouse();
        close_and_save_AI_edit_float();
        if (this.mAI.isChecked()) {
            FloatMgr.hide_aim_float();
        }
        this.m_boForceUpdateAIResult = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeEditFloat() {
        this.m_boAIEditing = false;
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_GUN1);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_GUN2);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_MOVE);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_MIRROW);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_Z);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_C);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_SCOPE2);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_PREVIEW);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_BAG_TAG);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_TAKE_OFF_TAG);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_GUN_HEAD1);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_GUN_HEAD2);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_GUN_HANDLE1);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_GUN_HANDLE2);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_GUN_TAIL1);
        EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_AI_GUN_TAIL2);
        ViewMgr.update_view_info_for_AI();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        setSystemStatusTask(false);
        super.onStop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSystemStatusTask(boolean z) {
        IThrone iThrone = this.mThroneService;
        if (iThrone != null) {
            try {
                iThrone.setSystemStatusTask(z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void init_game_buttons() {
        int i = SPUtils.getInstance().getInt("load_default_game_at_first_time");
        for (AppCompatButton appCompatButton : this.m_arrLstGameVideo) {
            appCompatButton.setVisibility(8);
        }
        if (-1 == i) {
            SPUtils.getInstance().put("load_default_game_at_first_time", 1);
            if (Units.getAppName(this, Constants.E_GAME_NAME_HPJY) != null) {
                setGameIcon(R.id.game1, Constants.E_GAME_NAME_HPJY);
            } else if (Units.getAppName(this, Constants.E_GAME_NAME_PUBG) != null) {
                setGameIcon(R.id.game1, Constants.E_GAME_NAME_PUBG);
            }
            if (Units.getAppName(this, Constants.E_GAME_NAME_CF) != null) {
                setGameIcon(R.id.game2, Constants.E_GAME_NAME_CF);
                return;
            } else if (Units.getAppName(this, "com.dts.freefireth") != null) {
                setGameIcon(R.id.game2, "com.dts.freefireth");
                return;
            } else if (Units.getAppName(this, "com.dts.freefiremax") != null) {
                setGameIcon(R.id.game2, "com.dts.freefiremax");
                return;
            } else {
                return;
            }
        }
        for (int i2 = 0; i2 < 8; i2++) {
            SPUtils sPUtils = SPUtils.getInstance();
            String string = sPUtils.getString(GameSettingFloat.TAG_ATTR_GAME + i2);
            if (string != null && string.length() > 0 && Units.getAppName(this, string) != null) {
                this.mGames.get(i2).setTag(string);
                this.mGames.get(i2).setText(Units.getAppName(this, string));
                if (!Constants.find_video_for_app(string).isEmpty()) {
                    this.m_arrLstGameVideo.get(i2).setTag(string);
                    this.m_arrLstGameVideo.get(i2).setVisibility(0);
                }
                Drawable appIcon = Units.getAppIcon(this, string);
                if (appIcon != null) {
                    appIcon.setBounds(1, 1, ConvertUtils.dp2px(60.0f), ConvertUtils.dp2px(60.0f));
                    this.mGames.get(i2).setCompoundDrawables(appIcon, null, null, null);
                }
                if (i2 == 0) {
                    this.game1LeftTip.setVisibility(0);
                    this.game1RightTip.setVisibility(0);
                }
            }
        }
    }

    private void setGameIcon(int i, String str) {
        AppCompatButton appCompatButton = (AppCompatButton) findViewById(i);
        appCompatButton.setText(Units.getAppName(this, str));
        Drawable appIcon = Units.getAppIcon(this, str);
        if (appIcon != null) {
            appIcon.setBounds(1, 1, ConvertUtils.dp2px(60.0f), ConvertUtils.dp2px(60.0f));
            appCompatButton.setCompoundDrawables(appIcon, null, null, null);
        }
        appCompatButton.setTag(str);
        int indexOf = this.mGames.indexOf(appCompatButton);
        SPUtils sPUtils = SPUtils.getInstance();
        sPUtils.put(GameSettingFloat.TAG_ATTR_GAME + indexOf, str);
        if (!Constants.find_video_for_app(str).isEmpty()) {
            this.m_arrLstGameVideo.get(indexOf).setVisibility(0);
            this.m_arrLstGameVideo.get(indexOf).setTag(str);
        }
        if (R.id.game1 == i) {
            this.game1LeftTip.setVisibility(0);
            this.game1RightTip.setVisibility(0);
        }
    }

    void send_AI_param() {
        bleSetAIGunDownOtherParams();
        bleSendAIXScopeParams();
        if (this.m_sSysStatus.is_smart_QE_on()) {
            if (this.m_smartQE == null) {
                this.m_smartQE = new SmartQE(this, this.mThroneService);
            }
            this.m_smartQE.bleSendAISmartQEParams();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 0) {
            if (i2 == -1) {
                this.mResultCode = i2;
                this.mData = intent;
                setUpMediaProjection();
                setUpVirtualDisplay();
                try {
                    if (this.m_scope6384 == null) {
                        this.m_scope6384 = new AIScope6384Float(this);
                    }
                    if (this.mThroneService != null) {
                        if (this.m_boSupportM4Dynamic.booleanValue()) {
                            this.mThroneService.bleSetAIStateM4((byte) -1, (byte) 0, (byte) 0, 0, (byte) 0, (byte) 0, this.m_arrM4AIGunStageOffTimeForSend, this.m_arrM4AIGunStageOffForSend);
                        } else {
                            this.mThroneService.bleSetAIState((byte) -1, (byte) 0, (byte) 0, 0, this.m_arrAIGunStageOffForSend);
                        }
                        send_AI_param();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (this.m_boDynamicGunPressOnOff) {
                    show_dynamic_gun_press_float(false);
                    show_dynamic_gun_adjust_float(false);
                }
                if (this.mImageReader == null) {
                    this.mIsRecording = false;
                    this.mAI.setChecked(false);
                } else {
                    showRecogn();
                    startCaptureThread();
                    MsgBox.msg_box_with_never_remind_once_choice2(this, R.string.remind_open_high_performance_mode, "remind_open_high_performance_mode", R.string.iamknow, R.string.choose_open_setting, new Handler() { // from class: com.baidu.kwgames.MainActivity.100
                        @Override // android.os.Handler
                        public void handleMessage(Message message) {
                            if (1 == message.arg2) {
                                try {
                                    MainActivity.this.startActivity(new Intent("android.settings.SETTINGS"));
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                    });
                }
            } else {
                this.mIsRecording = false;
                this.mAI.setChecked(false);
            }
            update_AI_period_controls();
        } else if (i == 1) {
            this.canSelect = true;
            if (i2 == -1) {
                String stringExtra = intent.getStringExtra("package");
                String str = TAG;
                Log.d(str, "onActivityResult: " + stringExtra);
                int intExtra = intent.getIntExtra("viewId", 0);
                if (intExtra != 0) {
                    setGameIcon(intExtra, stringExtra);
                }
            }
        } else if (i == 3) {
            if (i2 == -1) {
                if (AppInstance.has_device_connect()) {
                    this.mSystemStatus[10] = intent.getByteExtra("status", (byte) 0);
                    this.mSystemStatus[5] = intent.getByteExtra("statusExt", (byte) 0);
                    this.mSystemStatus[12] = intent.getByteExtra("statusExt3", (byte) 0);
                    this.mSystemStatus[16] = intent.getByteExtra("statusExt4", (byte) 0);
                    this.mSystemStatus[17] = intent.getByteExtra("statusExt5", (byte) 0);
                    this.mSystemStatus[20] = intent.getByteExtra("byADDownAutoAD", (byte) 0);
                    this.mSystemStatus[21] = intent.getByteExtra("byADDownAutoADSpeed", (byte) 0);
                    this.mSystemStatus[22] = intent.getByteExtra("hpvol", (byte) 0);
                    this.mSystemStatus[23] = intent.getByteExtra("shunfenger", (byte) 0);
                    this.mSystemStatus[24] = intent.getByteExtra("ctrlrepeatsp", (byte) 0);
                    send_system_status();
                    update_system_status_ui();
                } else {
                    Toast.makeText(this, (int) R.string.need_connect_blutooth, 0).show();
                }
            } else if (1 == i2) {
                if (AppInstance.has_device_connect()) {
                    Intent intent2 = new Intent(this, ChangeModeActivity.class);
                    intent2.putExtra("para", this.mSystemStatus);
                    startActivityForResult(intent2, 4);
                } else {
                    Toast.makeText(this, (int) R.string.need_connect_blutooth, 0).show();
                }
            } else if (2 == i2) {
                startActivityForResult(new Intent(this, BackupActivity.class), 5);
            }
            update_bg_image();
        } else if (i == 4 && i2 == -1) {
            byte byteExtra = intent.getByteExtra("mode", (byte) 0);
            send_change_mode(byteExtra, intent.getByteExtra("param", (byte) 0), intent.getByteExtra("usbspeed", (byte) 1), intent.getStringExtra("bleName"));
            boolean z = byteExtra == 5;
            this.m_boInNTSMode = z;
            m_ini.put(Constants.CFG_NTS_MODE, z);
            if (1 == intent.getByteExtra("gotoble", (byte) 0)) {
                this.m_timerHandler.postDelayed(new Runnable() { // from class: com.baidu.kwgames.MainActivity.101
                    @Override // java.lang.Runnable
                    public void run() {
                        NEAT.open_bluetooth_setting(AppInstance.s_context);
                    }
                }, 100L);
            }
        }
        super.onActivityResult(i, i2, intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void send_system_status() {
        if (this.mThroneService != null) {
            try {
                this.m_nLastSendSystemStatusTime = System.currentTimeMillis();
                this.m_sSysStatus.set_data(this.mSystemStatus);
                System.arraycopy(this.mSystemStatus, 3, this.m_arrSendSystemStatus, 4, SYSTEM_STATUS_BYTES);
                this.mThroneService.bleSetSystemStatus(this.m_arrSendSystemStatus);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void send_change_mode(byte b, byte b2, byte b3, String str) {
        IThrone iThrone = this.mThroneService;
        if (iThrone != null) {
            try {
                iThrone.bleChangeMode(b, b2, b3, str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean is_bitmap_pixel_on(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth() - 1;
        int height = bitmap.getHeight() - 1;
        boolean z = false;
        int make_in_range = Units.make_in_range(i + 1, 0, width);
        int make_in_range2 = Units.make_in_range(i2, 0, height);
        int make_in_range3 = Units.make_in_range(i2 + 1, 0, height);
        for (int make_in_range4 = Units.make_in_range(i, 0, width); make_in_range4 <= make_in_range && !z; make_in_range4++) {
            int i3 = make_in_range2;
            while (true) {
                if (i3 <= make_in_range3) {
                    int pixel = bitmap.getPixel(make_in_range4, i3);
                    int i4 = (16711680 & pixel) >> 16;
                    int i5 = (65280 & pixel) >> 8;
                    int i6 = pixel & 255;
                    if (i4 > 150 && i5 > 150 && i6 < 190) {
                        z = true;
                        break;
                    }
                    i3++;
                }
            }
        }
        return z;
    }

    public static boolean is_gun_bitmap_pixel_on(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth() - 1;
        int height = bitmap.getHeight() - 1;
        boolean z = false;
        int make_in_range = Units.make_in_range(i + 4, 0, width);
        int make_in_range2 = Units.make_in_range(i2 + 2, 0, height);
        int make_in_range3 = Units.make_in_range(i2 + 4, 0, height);
        for (int make_in_range4 = Units.make_in_range(i + 2, 0, width); make_in_range4 <= make_in_range && !z; make_in_range4++) {
            int i3 = make_in_range2;
            while (true) {
                if (i3 <= make_in_range3) {
                    int pixel = bitmap.getPixel(make_in_range4, i3);
                    int i4 = (16711680 & pixel) >> 16;
                    int i5 = (65280 & pixel) >> 8;
                    int i6 = pixel & 255;
                    if (i4 > 150 && i5 > 150 && i6 < 30) {
                        z = true;
                        break;
                    }
                    i3++;
                }
            }
        }
        return z;
    }

    public static boolean is_scope_bitmap_pixel_on(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth() - 1;
        int height = bitmap.getHeight() - 1;
        boolean z = false;
        int make_in_range = Units.make_in_range(i + 1, 0, width);
        int make_in_range2 = Units.make_in_range(i2, 0, height);
        int make_in_range3 = Units.make_in_range(i2 + 1, 0, height);
        for (int make_in_range4 = Units.make_in_range(i, 0, width); make_in_range4 <= make_in_range && !z; make_in_range4++) {
            int i3 = make_in_range2;
            while (true) {
                if (i3 <= make_in_range3) {
                    int pixel = bitmap.getPixel(make_in_range4, i3);
                    int i4 = (16711680 & pixel) >> 16;
                    int i5 = (65280 & pixel) >> 8;
                    int i6 = pixel & 255;
                    if (i4 > 150 && i5 > 150 && i6 < 140) {
                        z = true;
                        break;
                    }
                    i3++;
                }
            }
        }
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private int get_scope_state(int i, int i2) {
        int i3;
        if (-1 == i) {
            i3 = 0;
        } else {
            boolean z = this.mKaijing;
            boolean z2 = z;
            if (this.m_boAIXScope.booleanValue()) {
                z2 = z;
                if (AIXScope.get_gun_x_scope_onoff(i)) {
                    z2 = (z ? 1 : 0) | true;
                }
            }
            i3 = (this.m_boAutoActiveRun.booleanValue() && AIActiveRun.get_onoff(i)) ? z2 | 64 : z2;
            if (-1 != this.m_nInCar) {
                i3 |= 128;
            }
        }
        int i4 = i2 != 3 ? i2 != 4 ? i2 != 5 ? i2 != 6 ? i2 != 7 ? i3 | 2 : i3 | 12 : i3 | 10 : i3 | 8 : i3 | 6 : i3 | 4;
        return this.m_boAIStageEditing ? i4 | 16 : i4;
    }

    void pubg_gun_parts_AI(Bitmap bitmap, ViewInfo viewInfo) {
        int i;
        int i2;
        boolean z;
        this.m_nGun2PartsFrameCnt = 0;
        this.m_nGun1PartsFrameCnt = 0;
        if (viewInfo.gun1Tail.x.intValue() > viewInfo.gun1Head.x.intValue()) {
            int intValue = (viewInfo.gun1Tail.x.intValue() - viewInfo.gun1Head.x.intValue()) + viewInfo.gun1Head.width.intValue() + 5;
            if (viewInfo.gun1Head.x.intValue() + intValue > bitmap.getWidth()) {
                intValue = bitmap.getWidth() - viewInfo.gun1Head.x.intValue();
            }
            int[] iArr = this.m_arrPixels;
            if (iArr == null || iArr.length < intValue * 3) {
                this.m_arrPixels = new int[intValue * 3];
            }
            bitmap.getPixels(this.m_arrPixels, 0, intValue, viewInfo.gun1Head.x.intValue(), viewInfo.gun1Head.y.intValue() - 1, intValue, 3);
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 3; i3 < i5; i5 = 3) {
                int i6 = i4;
                int i7 = 0;
                int i8 = 0;
                boolean z2 = false;
                for (int i9 = 0; i9 < intValue; i9++) {
                    int i10 = this.m_arrPixels[(i3 * intValue) + i9];
                    if ((i10 & 16711680) <= 3145728 || (i10 & 65280) <= 12288 || (i10 & 255) <= 48) {
                        if (i8 == 0 || i8 - 1 == 0) {
                            if (z2 && i6 > 20) {
                                i7++;
                            }
                            i6 = 0;
                            z2 = false;
                        }
                    } else if (z2) {
                        int i11 = i6 + 1;
                        i6 = i11;
                        if (i11 >= 10) {
                            i8 = 3;
                        }
                    } else {
                        if (i7 < 4) {
                            this.m_arrGunPartFrameX[2][i7] = viewInfo.gun1Head.x.intValue() + i9;
                        }
                        i6 = 1;
                        z2 = true;
                    }
                }
                if (i6 > 20) {
                    i7++;
                    i4 = 0;
                } else {
                    i4 = i6;
                }
                if (i7 > this.m_nGun1PartsFrameCnt) {
                    this.m_nGun1PartsFrameCnt = i7;
                    for (int i12 = 0; i12 < i7; i12++) {
                        int[][] iArr2 = this.m_arrGunPartFrameX;
                        iArr2[0][i12] = iArr2[2][i12];
                    }
                }
                i3++;
            }
        }
        if (viewInfo.gun2Tail.x.intValue() > viewInfo.gun2Head.x.intValue()) {
            int intValue2 = (viewInfo.gun2Tail.x.intValue() - viewInfo.gun2Head.x.intValue()) + viewInfo.gun2Head.width.intValue() + 5;
            if (viewInfo.gun2Head.x.intValue() + intValue2 > bitmap.getWidth()) {
                intValue2 = bitmap.getWidth() - viewInfo.gun2Head.x.intValue();
            }
            int[] iArr3 = this.m_arrPixels;
            if (iArr3 == null || iArr3.length < intValue2 * 3) {
                this.m_arrPixels = new int[intValue2 * 3];
            }
            bitmap.getPixels(this.m_arrPixels, 0, intValue2, viewInfo.gun2Head.x.intValue(), viewInfo.gun2Head.y.intValue() - 1, intValue2, 3);
            int i13 = 0;
            int i14 = 0;
            for (int i15 = 3; i13 < i15; i15 = 3) {
                int i16 = i14;
                int i17 = 0;
                int i18 = 0;
                int i19 = 0;
                boolean z3 = false;
                while (i17 < intValue2) {
                    int i20 = this.m_arrPixels[(i13 * intValue2) + i17];
                    if ((i20 & 16711680) <= 3145728 || (i20 & 65280) <= 12288 || (i20 & 255) <= 48) {
                        if (i19 == 0 || i19 - 1 == 0) {
                            if (z3 && i16 > 20) {
                                i18++;
                            }
                            i16 = 0;
                            z = false;
                            i17++;
                            z3 = z;
                        }
                        z = z3;
                    } else if (z3) {
                        i16++;
                        if (i16 >= 10) {
                            z = z3;
                            i19 = 3;
                        }
                        z = z3;
                    } else {
                        if (i18 < 4) {
                            this.m_arrGunPartFrameX[2][i18] = viewInfo.gun2Head.x.intValue() + i17;
                        }
                        i16 = 1;
                        z = true;
                        i17++;
                        z3 = z;
                    }
                    i17++;
                    z3 = z;
                }
                if (i16 > 20) {
                    i18++;
                    i14 = 0;
                } else {
                    i14 = i16;
                }
                if (i18 > this.m_nGun2PartsFrameCnt) {
                    this.m_nGun2PartsFrameCnt = i18;
                    for (int i21 = 0; i21 < i18; i21++) {
                        int[][] iArr4 = this.m_arrGunPartFrameX;
                        iArr4[1][i21] = iArr4[2][i21];
                    }
                }
                i13++;
            }
        }
        this.m_nCurGun1Tail = -1;
        this.m_nCurGun1Handle = -1;
        this.m_nCurGun1Head = -1;
        int i22 = this.m_nGun1PartsFrameCnt;
        if (i22 >= 1) {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, this.m_arrGunPartFrameX[0][0], viewInfo.gun1Head.y.intValue(), viewInfo.gun1Head.width.intValue(), viewInfo.gun1Head.height.intValue(), this.m_matrixGunOtherParts, true);
            this.m_nCurGun1Head = gunHeadAI(createBitmap, 0);
            createBitmap.recycle();
            int i23 = i22 - 1;
            Bitmap createBitmap2 = Bitmap.createBitmap(bitmap, this.m_arrGunPartFrameX[0][i23], viewInfo.gun1Tail.y.intValue(), viewInfo.gun1Tail.width.intValue(), viewInfo.gun1Tail.height.intValue(), this.m_matrixGunOtherParts, true);
            this.m_nCurGun1Tail = gunTailAI(createBitmap2, 0);
            createBitmap2.recycle();
            int i24 = -1 != this.m_nCurGun1Head ? 254 : 255;
            if (-1 != this.m_nCurGun1Tail) {
                i2 = 1;
                i24 &= ~(1 << i23);
            } else {
                i2 = 1;
            }
            int i25 = i24;
            if (i22 > 3) {
                i25 = 2;
            }
            int i26 = 0;
            while (i26 < i22) {
                if (((i2 << i26) & i25) != 0) {
                    Bitmap createBitmap3 = Bitmap.createBitmap(bitmap, this.m_arrGunPartFrameX[0][i26], viewInfo.gun1Handle.y.intValue(), viewInfo.gun1Handle.width.intValue(), viewInfo.gun1Handle.height.intValue(), this.m_matrixGunHandle, true);
                    this.m_nCurGun1Handle = gunHandleAI(createBitmap3, 1);
                    createBitmap3.recycle();
                    if (-1 != this.m_nCurGun1Handle) {
                        break;
                    }
                }
                i26++;
                i2 = 1;
            }
        }
        this.m_nCurGun2Tail = -1;
        this.m_nCurGun2Handle = -1;
        this.m_nCurGun2Head = -1;
        int i27 = this.m_nGun2PartsFrameCnt;
        if (i27 >= 1) {
            Bitmap createBitmap4 = Bitmap.createBitmap(bitmap, this.m_arrGunPartFrameX[1][0], viewInfo.gun2Head.y.intValue(), viewInfo.gun2Head.width.intValue(), viewInfo.gun2Head.height.intValue(), this.m_matrixGunOtherParts, true);
            this.m_nCurGun2Head = gunHeadAI(createBitmap4, 0);
            createBitmap4.recycle();
            int i28 = i27 - 1;
            Bitmap createBitmap5 = Bitmap.createBitmap(bitmap, this.m_arrGunPartFrameX[1][i28], viewInfo.gun2Tail.y.intValue(), viewInfo.gun2Tail.width.intValue(), viewInfo.gun2Tail.height.intValue(), this.m_matrixGunOtherParts, true);
            this.m_nCurGun2Tail = gunTailAI(createBitmap5, 0);
            createBitmap5.recycle();
            int i29 = -1 == this.m_nCurGun2Head ? 255 : 254;
            if (-1 != this.m_nCurGun2Tail) {
                i = 1;
                i29 &= ~(1 << i28);
            } else {
                i = 1;
            }
            int i30 = i27 > 3 ? 2 : i29;
            for (int i31 = 0; i31 < i27; i31++) {
                if (((i << i31) & i30) != 0) {
                    Bitmap createBitmap6 = Bitmap.createBitmap(bitmap, this.m_arrGunPartFrameX[i][i31], viewInfo.gun2Handle.y.intValue(), viewInfo.gun2Handle.width.intValue(), viewInfo.gun2Handle.height.intValue(), this.m_matrixGunHandle, true);
                    i = 1;
                    this.m_nCurGun2Handle = gunHandleAI(createBitmap6, 1);
                    createBitmap6.recycle();
                    if (-1 != this.m_nCurGun2Handle) {
                        return;
                    }
                }
            }
        }
    }

    private void show_adjust_preview(Bitmap bitmap) {
        int i;
        boolean z;
        int intValue;
        int intValue2;
        int intValue3;
        int intValue4;
        int height;
        try {
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (this.m_mapAIFloat.containsKey(s_vInfo.selectTag)) {
            AIFloatInfo aIFloatInfo = this.m_mapAIFloat.get(s_vInfo.selectTag);
            boolean z2 = true;
            if (s_vInfo.selectTag.equals(Constants.FLOAT_TAG_SCOPE2)) {
                i = 8;
                z = true;
            } else {
                i = 2;
                z = false;
            }
            if (aIFloatInfo.m_viewFrame != null) {
                String[] strArr = {Constants.FLOAT_TAG_AI_MIRROW, Constants.FLOAT_TAG_AI_C, Constants.FLOAT_TAG_AI_Z};
                int i2 = 0;
                while (true) {
                    if (i2 >= 3) {
                        break;
                    } else if (strArr[i2].equals(s_vInfo.selectTag)) {
                        z2 = false;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (!z2) {
                    intValue = aIFloatInfo.m_dataAI.x.intValue();
                    intValue2 = aIFloatInfo.m_dataAI.y.intValue();
                    intValue3 = aIFloatInfo.m_dataAI.width.intValue();
                    intValue4 = aIFloatInfo.m_dataAI.height.intValue();
                } else {
                    if (z) {
                        intValue = aIFloatInfo.m_dataAI.x.intValue() >= 5 ? aIFloatInfo.m_dataAI.x.intValue() - 5 : 0;
                        intValue2 = aIFloatInfo.m_dataAI.y.intValue() >= 5 ? aIFloatInfo.m_dataAI.y.intValue() - 5 : 0;
                        intValue3 = aIFloatInfo.m_dataAI.width.intValue() + 10;
                        if (intValue + intValue3 > bitmap.getWidth()) {
                            intValue3 = bitmap.getWidth() - intValue;
                        }
                        intValue4 = aIFloatInfo.m_dataAI.height.intValue() + 10;
                        if (intValue2 + intValue4 > bitmap.getHeight()) {
                            height = bitmap.getHeight();
                            intValue4 = height - intValue2;
                        }
                    } else {
                        intValue = aIFloatInfo.m_dataAI.x.intValue() >= 20 ? aIFloatInfo.m_dataAI.x.intValue() - 20 : 0;
                        intValue2 = aIFloatInfo.m_dataAI.y.intValue() >= 20 ? aIFloatInfo.m_dataAI.y.intValue() - 20 : 0;
                        intValue3 = aIFloatInfo.m_dataAI.width.intValue() + 40;
                        if (intValue + intValue3 > bitmap.getWidth()) {
                            intValue3 = bitmap.getWidth() - intValue;
                        }
                        intValue4 = aIFloatInfo.m_dataAI.height.intValue() + 40;
                        if (intValue2 + intValue4 > bitmap.getHeight()) {
                            height = bitmap.getHeight();
                            intValue4 = height - intValue2;
                        }
                    }
                    e.printStackTrace();
                    return;
                }
                this.m_matrixAIPreview.reset();
                float f = i;
                this.m_matrixAIPreview.postScale(f, f);
                this.m_bmpPreview = Bitmap.createBitmap(bitmap, intValue, intValue2, intValue3, intValue4, this.m_matrixAIPreview, true);
                this.mHandler.post(this.m_runableShowAdjustPreview);
            }
        }
    }

    void update_scale_matrix(ViewInfo viewInfo) {
        this.m_boNeedUpdateMatrix = false;
        this.m_nGunAIW = viewInfo.gun1.width.intValue();
        this.m_nGunAIH = viewInfo.gun1.height.intValue();
        this.m_matrixGun.reset();
        this.m_matrixGun.postScale(this.m_nGunModelW / this.m_nGunAIW, this.m_nGunModelH / this.m_nGunAIH);
        this.m_matrixGunOtherParts.reset();
        this.m_matrixGunOtherParts.postScale(this.m_nGunPartsModelWH / viewInfo.gun1Head.width.intValue(), this.m_nGunPartsModelWH / viewInfo.gun1Head.height.intValue());
        this.m_matrixGunHandle.reset();
        this.m_matrixGunHandle.postScale(this.m_nGunHandleModelWH / viewInfo.gun1Handle.width.intValue(), this.m_nGunHandleModelWH / viewInfo.gun1Handle.height.intValue());
        this.m_matrixScope.reset();
        this.m_matrixScope.postScale(this.m_nScopeModelW / viewInfo.doubleMirror.width.intValue(), this.m_nScopeModelH / viewInfo.doubleMirror.height.intValue());
        this.m_matrixBagTag.reset();
        this.m_matrixBagTag.postScale(this.m_nBagtagModelW / viewInfo.bagTag.width.intValue(), this.m_nBagtagModelH / viewInfo.bagTag.height.intValue());
        this.m_matrixTakeoff.reset();
        this.m_matrixTakeoff.postScale(this.m_nTakeoffModelW / viewInfo.takeOffTag.width.intValue(), this.m_nTakeoffModelH / viewInfo.takeOffTag.height.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(55:21|22|(1:24)|25|(1:27)(1:280)|28|(1:30)(1:279)|31|32|33|(1:275)(1:37)|38|(2:40|(1:42)(1:248))(8:249|(4:251|(1:253)|254|(1:256))|257|(3:262|263|(1:265))|266|(2:268|(1:(2:271|(1:273)))(1:274))|263|(0))|43|(1:45)|46|(1:48)|49|(1:51)|52|(1:56)|57|(1:59)(2:244|(1:246)(32:247|61|(1:63)(2:237|(1:239)(3:240|(1:242)|243))|(31:(2:230|231)|66|(4:68|(1:70)|71|(27:73|(25:78|(2:215|(2:218|(2:220|(1:224))(1:225)))|82|(2:84|(1:86)(1:87))|88|(1:90)(1:214)|(1:92)|93|(6:95|(3:97|(2:193|194)|103)(2:195|(3:197|(2:203|194)|103)(4:204|(2:211|103)|212|194))|104|(3:106|(1:108)(1:191)|109)(1:192)|110|(1:112))(1:213)|113|(1:115)(1:190)|116|(1:189)(2:126|(2:128|(4:130|131|132|133)(1:174))(2:175|(3:177|(1:181)|182)(3:183|(1:187)|188)))|134|(6:149|150|(1:152)(2:157|(1:159))|153|154|155)|160|(1:164)|165|166|167|150|(0)(0)|153|154|155)|226|82|(0)|88|(0)(0)|(0)|93|(0)(0)|113|(0)(0)|116|(3:118|121|124)|189|134|(11:136|138|141|144|146|149|150|(0)(0)|153|154|155)|160|(1:164)|165|166|167|150|(0)(0)|153|154|155)(1:227))|228|229|(31:75|78|(1:80)|215|(0)|218|(0)(0)|82|(0)|88|(0)(0)|(0)|93|(0)(0)|113|(0)(0)|116|(0)|189|134|(0)|160|(0)|165|166|167|150|(0)(0)|153|154|155)|226|82|(0)|88|(0)(0)|(0)|93|(0)(0)|113|(0)(0)|116|(0)|189|134|(0)|160|(0)|165|166|167|150|(0)(0)|153|154|155)|236|229|(0)|226|82|(0)|88|(0)(0)|(0)|93|(0)(0)|113|(0)(0)|116|(0)|189|134|(0)|160|(0)|165|166|167|150|(0)(0)|153|154|155))|60|61|(0)(0)|(0)|236|229|(0)|226|82|(0)|88|(0)(0)|(0)|93|(0)(0)|113|(0)(0)|116|(0)|189|134|(0)|160|(0)|165|166|167|150|(0)(0)|153|154|155) */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x044a, code lost:
        if (r30.mHasGun2 != false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x06f3, code lost:
        r0 = e;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0415  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x041c  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x042b  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0431  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0446  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x047f A[Catch: RemoteException -> 0x06f7, TryCatch #3 {RemoteException -> 0x06f7, blocks: (B:118:0x0448, B:120:0x044c, B:122:0x0450, B:124:0x0458, B:125:0x045e, B:127:0x046b, B:133:0x047f, B:136:0x0488, B:138:0x048c, B:152:0x04f5, B:154:0x0500, B:158:0x0507, B:164:0x0517, B:166:0x051f, B:168:0x0523, B:170:0x0533, B:172:0x0539, B:195:0x058d, B:197:0x0591, B:199:0x0596, B:201:0x059f, B:203:0x05a4, B:205:0x05aa, B:207:0x05b0, B:211:0x05b7, B:213:0x05bb, B:216:0x05c1, B:219:0x05c7, B:221:0x05cb, B:223:0x05d7, B:225:0x05db, B:200:0x059b, B:174:0x053f, B:175:0x054a, B:177:0x054e, B:179:0x055e, B:181:0x0564, B:183:0x056a, B:184:0x0575, B:186:0x0579, B:188:0x057d, B:193:0x0585, B:140:0x0490, B:143:0x04ce, B:145:0x04d2, B:149:0x04dd, B:150:0x04e9, B:151:0x04ed, B:128:0x0471, B:130:0x0478), top: B:294:0x0448 }] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x04d2 A[Catch: RemoteException -> 0x06f7, TryCatch #3 {RemoteException -> 0x06f7, blocks: (B:118:0x0448, B:120:0x044c, B:122:0x0450, B:124:0x0458, B:125:0x045e, B:127:0x046b, B:133:0x047f, B:136:0x0488, B:138:0x048c, B:152:0x04f5, B:154:0x0500, B:158:0x0507, B:164:0x0517, B:166:0x051f, B:168:0x0523, B:170:0x0533, B:172:0x0539, B:195:0x058d, B:197:0x0591, B:199:0x0596, B:201:0x059f, B:203:0x05a4, B:205:0x05aa, B:207:0x05b0, B:211:0x05b7, B:213:0x05bb, B:216:0x05c1, B:219:0x05c7, B:221:0x05cb, B:223:0x05d7, B:225:0x05db, B:200:0x059b, B:174:0x053f, B:175:0x054a, B:177:0x054e, B:179:0x055e, B:181:0x0564, B:183:0x056a, B:184:0x0575, B:186:0x0579, B:188:0x057d, B:193:0x0585, B:140:0x0490, B:143:0x04ce, B:145:0x04d2, B:149:0x04dd, B:150:0x04e9, B:151:0x04ed, B:128:0x0471, B:130:0x0478), top: B:294:0x0448 }] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x04e9 A[Catch: RemoteException -> 0x06f7, TryCatch #3 {RemoteException -> 0x06f7, blocks: (B:118:0x0448, B:120:0x044c, B:122:0x0450, B:124:0x0458, B:125:0x045e, B:127:0x046b, B:133:0x047f, B:136:0x0488, B:138:0x048c, B:152:0x04f5, B:154:0x0500, B:158:0x0507, B:164:0x0517, B:166:0x051f, B:168:0x0523, B:170:0x0533, B:172:0x0539, B:195:0x058d, B:197:0x0591, B:199:0x0596, B:201:0x059f, B:203:0x05a4, B:205:0x05aa, B:207:0x05b0, B:211:0x05b7, B:213:0x05bb, B:216:0x05c1, B:219:0x05c7, B:221:0x05cb, B:223:0x05d7, B:225:0x05db, B:200:0x059b, B:174:0x053f, B:175:0x054a, B:177:0x054e, B:179:0x055e, B:181:0x0564, B:183:0x056a, B:184:0x0575, B:186:0x0579, B:188:0x057d, B:193:0x0585, B:140:0x0490, B:143:0x04ce, B:145:0x04d2, B:149:0x04dd, B:150:0x04e9, B:151:0x04ed, B:128:0x0471, B:130:0x0478), top: B:294:0x0448 }] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0500 A[Catch: RemoteException -> 0x06f7, TryCatch #3 {RemoteException -> 0x06f7, blocks: (B:118:0x0448, B:120:0x044c, B:122:0x0450, B:124:0x0458, B:125:0x045e, B:127:0x046b, B:133:0x047f, B:136:0x0488, B:138:0x048c, B:152:0x04f5, B:154:0x0500, B:158:0x0507, B:164:0x0517, B:166:0x051f, B:168:0x0523, B:170:0x0533, B:172:0x0539, B:195:0x058d, B:197:0x0591, B:199:0x0596, B:201:0x059f, B:203:0x05a4, B:205:0x05aa, B:207:0x05b0, B:211:0x05b7, B:213:0x05bb, B:216:0x05c1, B:219:0x05c7, B:221:0x05cb, B:223:0x05d7, B:225:0x05db, B:200:0x059b, B:174:0x053f, B:175:0x054a, B:177:0x054e, B:179:0x055e, B:181:0x0564, B:183:0x056a, B:184:0x0575, B:186:0x0579, B:188:0x057d, B:193:0x0585, B:140:0x0490, B:143:0x04ce, B:145:0x04d2, B:149:0x04dd, B:150:0x04e9, B:151:0x04ed, B:128:0x0471, B:130:0x0478), top: B:294:0x0448 }] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0511  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0513  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0516  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x051f A[Catch: RemoteException -> 0x06f7, TryCatch #3 {RemoteException -> 0x06f7, blocks: (B:118:0x0448, B:120:0x044c, B:122:0x0450, B:124:0x0458, B:125:0x045e, B:127:0x046b, B:133:0x047f, B:136:0x0488, B:138:0x048c, B:152:0x04f5, B:154:0x0500, B:158:0x0507, B:164:0x0517, B:166:0x051f, B:168:0x0523, B:170:0x0533, B:172:0x0539, B:195:0x058d, B:197:0x0591, B:199:0x0596, B:201:0x059f, B:203:0x05a4, B:205:0x05aa, B:207:0x05b0, B:211:0x05b7, B:213:0x05bb, B:216:0x05c1, B:219:0x05c7, B:221:0x05cb, B:223:0x05d7, B:225:0x05db, B:200:0x059b, B:174:0x053f, B:175:0x054a, B:177:0x054e, B:179:0x055e, B:181:0x0564, B:183:0x056a, B:184:0x0575, B:186:0x0579, B:188:0x057d, B:193:0x0585, B:140:0x0490, B:143:0x04ce, B:145:0x04d2, B:149:0x04dd, B:150:0x04e9, B:151:0x04ed, B:128:0x0471, B:130:0x0478), top: B:294:0x0448 }] */
    /* JADX WARN: Removed duplicated region for block: B:206:0x05ae  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x05b4  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x05b6  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x05bb A[Catch: RemoteException -> 0x06f7, TryCatch #3 {RemoteException -> 0x06f7, blocks: (B:118:0x0448, B:120:0x044c, B:122:0x0450, B:124:0x0458, B:125:0x045e, B:127:0x046b, B:133:0x047f, B:136:0x0488, B:138:0x048c, B:152:0x04f5, B:154:0x0500, B:158:0x0507, B:164:0x0517, B:166:0x051f, B:168:0x0523, B:170:0x0533, B:172:0x0539, B:195:0x058d, B:197:0x0591, B:199:0x0596, B:201:0x059f, B:203:0x05a4, B:205:0x05aa, B:207:0x05b0, B:211:0x05b7, B:213:0x05bb, B:216:0x05c1, B:219:0x05c7, B:221:0x05cb, B:223:0x05d7, B:225:0x05db, B:200:0x059b, B:174:0x053f, B:175:0x054a, B:177:0x054e, B:179:0x055e, B:181:0x0564, B:183:0x056a, B:184:0x0575, B:186:0x0579, B:188:0x057d, B:193:0x0585, B:140:0x0490, B:143:0x04ce, B:145:0x04d2, B:149:0x04dd, B:150:0x04e9, B:151:0x04ed, B:128:0x0471, B:130:0x0478), top: B:294:0x0448 }] */
    /* JADX WARN: Removed duplicated region for block: B:246:0x06ad A[Catch: RemoteException -> 0x06f5, TryCatch #2 {RemoteException -> 0x06f5, blocks: (B:227:0x05f9, B:244:0x06a9, B:246:0x06ad, B:248:0x06b1, B:251:0x06b7, B:254:0x06bf, B:256:0x06c3, B:260:0x06cc, B:264:0x06d4, B:228:0x060e, B:229:0x0633, B:231:0x063a, B:233:0x064a, B:235:0x0650, B:236:0x0658, B:237:0x0673, B:239:0x0683, B:241:0x0689, B:242:0x0691), top: B:292:0x05f9 }] */
    /* JADX WARN: Removed duplicated region for block: B:262:0x06d0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0704  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x070a  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01bd A[Catch: Exception -> 0x03e0, TryCatch #0 {Exception -> 0x03e0, blocks: (B:43:0x0178, B:45:0x017c, B:47:0x0182, B:49:0x01b9, B:51:0x01bd, B:53:0x01c3, B:54:0x01c8, B:55:0x0302, B:57:0x0306, B:59:0x030a, B:60:0x033e, B:62:0x0342, B:63:0x0376, B:65:0x037a, B:68:0x037f, B:78:0x03d7, B:80:0x03db, B:69:0x0382, B:71:0x038d, B:74:0x03c9, B:76:0x03d0, B:77:0x03d3, B:48:0x01b7), top: B:288:0x0178 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0302 A[Catch: Exception -> 0x03e0, TryCatch #0 {Exception -> 0x03e0, blocks: (B:43:0x0178, B:45:0x017c, B:47:0x0182, B:49:0x01b9, B:51:0x01bd, B:53:0x01c3, B:54:0x01c8, B:55:0x0302, B:57:0x0306, B:59:0x030a, B:60:0x033e, B:62:0x0342, B:63:0x0376, B:65:0x037a, B:68:0x037f, B:78:0x03d7, B:80:0x03db, B:69:0x0382, B:71:0x038d, B:74:0x03c9, B:76:0x03d0, B:77:0x03d3, B:48:0x01b7), top: B:288:0x0178 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x03db A[Catch: Exception -> 0x03e0, TRY_LEAVE, TryCatch #0 {Exception -> 0x03e0, blocks: (B:43:0x0178, B:45:0x017c, B:47:0x0182, B:49:0x01b9, B:51:0x01bd, B:53:0x01c3, B:54:0x01c8, B:55:0x0302, B:57:0x0306, B:59:0x030a, B:60:0x033e, B:62:0x0342, B:63:0x0376, B:65:0x037a, B:68:0x037f, B:78:0x03d7, B:80:0x03db, B:69:0x0382, B:71:0x038d, B:74:0x03c9, B:76:0x03d0, B:77:0x03d3, B:48:0x01b7), top: B:288:0x0178 }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x03e8  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x03f6  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x03ff  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void capture() {
        /*
            Method dump skipped, instructions count: 1843
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.kwgames.MainActivity.capture():void");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$capture$1$com-baidu-kwgames-MainActivity  reason: not valid java name */
    public /* synthetic */ void m43lambda$capture$1$combaidukwgamesMainActivity() {
        this.mRecognTextview.setText(getString(R.string.first_ai_result));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$capture$2$com-baidu-kwgames-MainActivity  reason: not valid java name */
    public /* synthetic */ void m44lambda$capture$2$combaidukwgamesMainActivity() {
        TextView textView = this.mRecognTextview;
        if (textView != null) {
            textView.setText(getString(R.string.miss_vscreen));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$capture$3$com-baidu-kwgames-MainActivity  reason: not valid java name */
    public /* synthetic */ void m45lambda$capture$3$combaidukwgamesMainActivity() {
        TextView textView = this.mRecognTextview;
        if (textView != null) {
            textView.setText(getString(R.string.AI_resolution_error));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$capture$4$com-baidu-kwgames-MainActivity  reason: not valid java name */
    public /* synthetic */ void m46lambda$capture$4$combaidukwgamesMainActivity() {
        update_AI_result_text();
        if (this.m_boNeedUpdateStageUI) {
            if (this.m_boSupportM4Dynamic.booleanValue()) {
                update_AI_gun_down_stage_UI_M4();
            } else {
                update_AI_gun_down_stage_UI();
            }
            this.m_boNeedUpdateStageUI = false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void saveImageToGallery(Bitmap bitmap) {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        File externalFilesDir = AppInstance.get().getExternalFilesDir(null);
        externalFilesDir.mkdirs();
        try {
            try {
                try {
                    fileOutputStream = new FileOutputStream(new File(externalFilesDir, "AI.jp"));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            } catch (FileNotFoundException e2) {
                e = e2;
            } catch (IOException e3) {
                e = e3;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
            bitmap.compress(compressFormat, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            fileOutputStream2 = compressFormat;
        } catch (FileNotFoundException e4) {
            e = e4;
            fileOutputStream2 = fileOutputStream;
            e.printStackTrace();
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
                fileOutputStream2 = fileOutputStream2;
            }
        } catch (IOException e5) {
            e = e5;
            fileOutputStream2 = fileOutputStream;
            e.printStackTrace();
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
                fileOutputStream2 = fileOutputStream2;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void update_crosshair() {
        byte b = this.m_byOldGun;
        if (!this.m_boAICrossHairOnOff || -1 == b) {
            return;
        }
        stop_crosshair_check_timer();
        int ai_scope_to_crosshair_scope = AICrosshair.ai_scope_to_crosshair_scope(this.m_nOldScopeAiID, this.mKaijing);
        int i = AICrosshair.get_gun_crosshair(b, ai_scope_to_crosshair_scope);
        update_AI_float_crosshair_button();
        if (i == 0) {
            FloatMgr.hide_aim_float();
        } else {
            FloatMgr.show_aim_float(i, AICrosshair.get_scope_offset_x(b, ai_scope_to_crosshair_scope), AICrosshair.get_scope_offset_y(b, ai_scope_to_crosshair_scope));
        }
    }

    private void update_AI_result_text() {
        if (this.mRecognTextview != null) {
            StringBuilder sb = new StringBuilder();
            if (-1 == this.m_nCurBagTagAI) {
                if (!this.mHasGun1 && !this.mHasGun2) {
                    sb.append(getResources().getString(R.string.no_gun_in_hand));
                    FloatMgr.hide_aim_float();
                } else {
                    if (-1 != this.m_byOldGun) {
                        if (this.m_sSysStatus.is_AI_continues_shoot() && Constants.is_gun_oneshoot(this.m_byOldGun)) {
                            sb.append(Constants.get_gun_name(this.m_byOldGun) + "...|");
                        } else {
                            sb.append(Constants.get_gun_name(this.m_byOldGun) + "|");
                        }
                    }
                    sb.append(this.mKaijing ? Constants.m_strAiResScopeOn : Constants.m_strAiResScopeOff);
                    if (this.mBeijingAI.isEmpty()) {
                        sb.append(Constants.m_strAiResNoScope);
                    } else {
                        sb.append(this.mBeijingAI + "|");
                    }
                    if (this.m_boGunPartsAI) {
                        int append_gun_parts_name_and_value = GunPartsMgr.append_gun_parts_name_and_value(this.m_byOldGun, this.m_nOldGunHead, this.m_nOldGunHandle, this.m_nOldGunTail, sb);
                        if (append_gun_parts_name_and_value == 0) {
                            if (this.mHasSquat) {
                                sb.append(Constants.m_strAiResSqut + (this.m_nLastBleGunDown & 255) + ") ");
                            } else if (this.mHasLie) {
                                sb.append(Constants.m_strAiResFall + (this.m_nLastBleGunDown & 255) + ") ");
                            } else if (-1 != this.m_nInCar) {
                                sb.append(Constants.m_strAiResInCar + (this.m_nLastBleGunDown & 255) + ") ");
                            } else {
                                sb.append(Constants.m_strAiResStand + (this.m_nLastBleGunDown & 255) + ") ");
                            }
                        } else if (this.mHasSquat) {
                            sb.append(Constants.m_strAiResSqut + (this.m_nLastBleGunDown & 255) + "-" + append_gun_parts_name_and_value + "%) ");
                        } else if (this.mHasLie) {
                            sb.append(Constants.m_strAiResFall + (this.m_nLastBleGunDown & 255) + "-" + append_gun_parts_name_and_value + "%) ");
                        } else if (-1 != this.m_nInCar) {
                            sb.append(Constants.m_strAiResInCar + (this.m_nLastBleGunDown & 255) + "-" + append_gun_parts_name_and_value + "%) ");
                        } else {
                            sb.append(Constants.m_strAiResStand + (this.m_nLastBleGunDown & 255) + "-" + append_gun_parts_name_and_value + "%) ");
                        }
                    } else if (this.mHasSquat) {
                        sb.append(Constants.m_strAiResSqut + (this.m_nLastBleGunDown & 255) + ") ");
                    } else if (this.mHasLie) {
                        sb.append(Constants.m_strAiResFall + (this.m_nLastBleGunDown & 255) + ") ");
                    } else if (-1 != this.m_nInCar) {
                        sb.append(Constants.m_strAiResInCar + (this.m_nLastBleGunDown & 255) + ") ");
                    } else {
                        sb.append(Constants.m_strAiResStand + (this.m_nLastBleGunDown & 255) + ") ");
                    }
                    update_crosshair();
                }
            } else if (!this.m_boAIEditing) {
                sb.append(Constants.m_strAiResBagFinished);
            }
            this.mRecognTextview.setText(sb.subSequence(0, sb.length()));
            this.mAgileText.setText(Integer.toString(this.m_nLastBleGunDown & 255));
        }
    }

    private void setUpVirtualDisplay() {
        if (Build.VERSION.SDK_INT >= 19) {
            ImageReader newInstance = ImageReader.newInstance(this.m_nScreenWidth, this.m_nScreenHeight, 1, 1);
            this.mImageReader = newInstance;
            if (newInstance == null) {
                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.open_ai_rec_failed), 1).show();
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.mScreenShot = this.mMediaProjection.createVirtualDisplay("ScreenShot", this.m_nScreenWidth, this.m_nScreenHeight, this.mDpi, 16, this.mImageReader.getSurface(), null, null);
            }
        }
    }

    private void setUpMediaProjection() {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mMediaProjection = this.mProjectionManager.getMediaProjection(this.mResultCode, this.mData);
        }
    }

    public void release() {
        if (Build.VERSION.SDK_INT >= 19) {
            VirtualDisplay virtualDisplay = this.mScreenShot;
            if (virtualDisplay != null) {
                virtualDisplay.release();
            }
            ImageReader imageReader = this.mImageReader;
            if (imageReader != null) {
                imageReader.close();
            }
        }
    }

    public void hideTable(View view) {
        this.mFloatWindowTable.setVisibility(this.mFloatWindowTable.getVisibility() == 8 ? 0 : 8);
    }

    public void dumpLog(View view) {
        this.timingLogger.dumpToLog();
        this.timingLogger.reset();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (resources == null || configuration.fontScale == 1.0f) {
            return resources;
        }
        configuration.fontScale = 1.0f;
        if (Build.VERSION.SDK_INT >= 17) {
            Resources resources2 = createConfigurationContext(configuration).getResources();
            displayMetrics.scaledDensity = displayMetrics.density * configuration.fontScale;
            return resources2;
        }
        resources.updateConfiguration(configuration, displayMetrics);
        return resources;
    }

    void bleSendDynamicGunPressData() {
        try {
            IThrone iThrone = this.mThroneService;
            if (iThrone != null) {
                iThrone.bleSendLongData(DynamicGunData.get_dynamic_gun_ble_send_data(this.m_boDynamicGunAdjustFloatIsVisible && this.m_boDynamicStageAdjustVisible));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void update_gun_down_button_image() {
        if (DynamicGunData.get_onoff()) {
            ImageSwButton.set_onoff(this.m_btnDynamicGun, true);
        } else {
            ImageSwButton.set_onoff(this.m_btnDynamicGun, false);
        }
    }

    void update_dynamic_gun_down_parts_ctrls() {
        if (!this.mAI.isChecked()) {
            boolean z = this.m_boDynamicGunPressOnOff;
        }
        this.m_btnDynamicGun.getVisibility();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$5$com-baidu-kwgames-MainActivity  reason: not valid java name */
    public /* synthetic */ void m48lambda$new$5$combaidukwgamesMainActivity() {
        if (this.m_boShowAdjustButton && this.m_boDynamicGunPressOnOff) {
            this.m_viewDynamicAdjustLayout.setVisibility(8);
            this.m_boShowAdjustButton = false;
        }
    }

    public void begin_hide_dynamic_result_ctrls_timer() {
        stop_hide_dynamic_result_ctrls_timer();
        this.m_timerHandler.postDelayed(this.m_runableHideDynamicCtrls, 30000L);
    }

    public void stop_hide_dynamic_result_ctrls_timer() {
        this.m_timerHandler.removeCallbacks(this.m_runableHideDynamicCtrls);
    }

    @OnClick({R.id.dynamic_gun})
    public void onDynamicGun() {
        if (this.m_boDevBanned) {
            show_ban_msgbox();
        } else if (this.m_boSupportDynamicGunPress) {
            boolean z = !this.m_boDynamicGunPressOnOff;
            this.m_boDynamicGunPressOnOff = z;
            if (z) {
                show_dynamic_gun_press_float(true);
                if (this.m_crossDynamicScopeOff.m_nStyle != 0) {
                    FloatMgr.init_aim_float(this.m_crossDynamicScopeOff.m_nStyle);
                    FloatMgr.show_aim_float(this.m_crossDynamicScopeOff.m_nOffsetX, this.m_crossDynamicScopeOff.m_nOffsetY);
                }
                MsgBox.msg_box_with_never_remind_once_choice2(R.string.dynamic_gun_press_tip, "dynamic_gun_press_tip", R.string.iamknow, R.string.ineedvideo, this.m_webHandler.set_url(Constants.URL_VIDEO_DYNAMIC_GUN_PRESS));
                hideAllSeeckAddOrReduce();
            } else {
                show_dynamic_gun_press_float(false);
                show_dynamic_gun_adjust_float(false);
                FloatMgr.hide_aim_float();
                EasyFloat.dismissAppFloat(Constants.FLOAT_DYNAMIC_CROSSHAIR_SET);
            }
            update_dynamic_gun_down_parts_ctrls();
            DynamicGunData.set_onoff(this.m_boDynamicGunPressOnOff);
            bleSendDynamicGunPressData();
            update_gun_down_visible();
            update_gun_down_button_image();
            update_dynamic_gun_down_parts_ctrls();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void set_cross_hair_button(Button button, int i) {
        if (i < 0 || i > 10) {
            return;
        }
        button.setBackgroundResource(Constants.s_arrCrosshairBtnBG[i]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void show_dynamic_gun_press_float(boolean z) {
        if (!z) {
            this.m_boDynamicFloatIsVisible = false;
            EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_DYNAMIC_RESULT);
            stop_hide_dynamic_result_ctrls_timer();
        } else if (EasyFloat.getAppFloatView(Constants.FLOAT_TAG_DYNAMIC_RESULT) != null) {
        } else {
            EasyFloat.with(this).setTag(Constants.FLOAT_TAG_DYNAMIC_RESULT).setGravity(49).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setLayout(R.layout.float_window_dynamic, new AnonymousClass103()).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.MainActivity$103  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass103 implements OnInvokeView {
        AnonymousClass103() {
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            MainActivity.this.m_viewDynamicAdjustLayout = view.findViewById(R.id.agile_layer);
            MainActivity.this.m_boShowAdjustButton = MainActivity.m_ini.getBoolean(Constants.CFG_DYNAMIC_ADJUST_VISIBLE, true);
            MainActivity.this.m_viewDynamicAdjustLayout.setVisibility(MainActivity.this.m_boShowAdjustButton ? 0 : 8);
            if (MainActivity.this.m_boShowAdjustButton) {
                MainActivity.this.begin_hide_dynamic_result_ctrls_timer();
            }
            MainActivity.this.m_textDynamicResult = (TextView) view.findViewById(R.id.dynamic_result);
            MainActivity.this.m_textDynamicResult.setText(DynamicGunData.get_dynamic_result_string());
            MainActivity.this.m_textDynamicResult.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$103$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass103.this.m49lambda$invoke$0$combaidukwgamesMainActivity$103(view2);
                }
            });
            MainActivity.this.m_boDynamicFloatIsVisible = true;
            MainActivity.this.m_btnDynamicCrossHair = (Button) view.findViewById(R.id.btn_dynamic_crosshair);
            MainActivity mainActivity = MainActivity.this;
            mainActivity.set_cross_hair_button(mainActivity.m_btnDynamicCrossHair, MainActivity.this.m_crossDynamicScopeOff.m_nStyle);
            MainActivity.this.m_btnDynamicCrossHair.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.103.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int i = MainActivity.this.m_crossDynamicScopeOff.m_nStyle + 1;
                    if (i > 10) {
                        i = 0;
                    }
                    FloatMgr.init_aim_float(i);
                    MainActivity.this.set_cross_hair_button(MainActivity.this.m_btnDynamicCrossHair, i);
                    if (i == 0) {
                        NEAT.toast_short(R.string.dynamic_crosshair_off);
                        FloatMgr.hide_aim_float();
                    } else {
                        FloatMgr.show_aim_float();
                        if (!MainActivity.this.m_boShowCrosshairTip) {
                            MainActivity.this.m_boShowCrosshairTip = true;
                            NEAT.toast_short(R.string.dynamic_crosshair_info);
                        }
                    }
                    MainActivity.this.m_crossDynamicScopeOff.m_nStyle = i;
                    MainActivity.this.m_crossDynamicScopeOff.save();
                    MainActivity.this.begin_hide_dynamic_result_ctrls_timer();
                }
            });
            MainActivity.this.m_btnDynamicCrossHair.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.baidu.kwgames.MainActivity.103.2
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view2) {
                    MainActivity.this.show_dynamic_crosshair_edit();
                    MainActivity.this.begin_hide_dynamic_result_ctrls_timer();
                    return true;
                }
            });
            MainActivity.this.m_btnDynamicStage = (Button) view.findViewById(R.id.btn_dynamic_stage);
            MainActivity.this.m_btnDynamicStage.setBackground(MainActivity.this.getResources().getDrawable(AppInstance.m_boIsChineseOS ? R.mipmap.ai_stage_off : R.mipmap.ai_stage_off_en));
            MainActivity.this.m_btnDynamicStage.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$103$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass103.this.m50lambda$invoke$1$combaidukwgamesMainActivity$103(view2);
                }
            });
            FloatMgr.resetVirtualMouse();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$0$com-baidu-kwgames-MainActivity$103  reason: not valid java name */
        public /* synthetic */ void m49lambda$invoke$0$combaidukwgamesMainActivity$103(View view) {
            MainActivity mainActivity = MainActivity.this;
            mainActivity.m_boShowAdjustButton = !mainActivity.m_boShowAdjustButton;
            MainActivity.this.m_viewDynamicAdjustLayout.setVisibility(MainActivity.this.m_boShowAdjustButton ? 0 : 8);
            MainActivity.m_ini.put(Constants.CFG_DYNAMIC_ADJUST_VISIBLE, MainActivity.this.m_boShowAdjustButton);
            if (MainActivity.this.m_boShowAdjustButton) {
                MainActivity.this.begin_hide_dynamic_result_ctrls_timer();
            } else {
                MainActivity.this.stop_hide_dynamic_result_ctrls_timer();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$1$com-baidu-kwgames-MainActivity$103  reason: not valid java name */
        public /* synthetic */ void m50lambda$invoke$1$combaidukwgamesMainActivity$103(View view) {
            MainActivity mainActivity = MainActivity.this;
            mainActivity.show_dynamic_gun_adjust_float(!mainActivity.m_boDynamicGunAdjustFloatIsVisible);
            MainActivity.this.begin_hide_dynamic_result_ctrls_timer();
        }
    }

    public void update_dynamic_gun_down_stage_UI() {
        for (int i = 0; i < 6; i++) {
            byte b = DynamicGunData.get_stage_data(this.m_nCurEditDynamicLevel, i);
            TextView textView = this.m_arrDynamicStageText[i];
            textView.setText(((int) b) + "%");
            this.m_arrDynamicStageSeekbar[i].setProgress(b);
        }
    }

    public void on_dynamic_adjust_onoff() {
        int i;
        if (this.m_boDynamicStageAdjustVisible) {
            update_dynamic_gun_down_stage_UI();
        }
        Button button = this.m_btnEnableDynamicAdjust;
        Resources resources = getResources();
        if (AppInstance.m_boIsChineseOS) {
            i = this.m_boDynamicStageAdjustVisible ? R.mipmap.ai_stage_on : R.mipmap.ai_stage_off;
        } else {
            i = this.m_boDynamicStageAdjustVisible ? R.mipmap.ai_stage_on_en : R.mipmap.ai_stage_off_en;
        }
        button.setBackground(resources.getDrawable(i));
        int i2 = this.m_boDynamicStageAdjustVisible ? 0 : 8;
        for (int i3 = 0; i3 < 6; i3++) {
            this.m_arrDynamicStageTitle[i3].setVisibility(i2);
            this.m_arrDynamicStageText[i3].setVisibility(i2);
            this.m_arrDynamicStageSeekbar[i3].setVisibility(i2);
            this.m_arrDynamicStageBtnPlus[i3].setVisibility(i2);
            this.m_arrDynamicStageBtnMinus[i3].setVisibility(i2);
        }
    }

    public void on_dynamic_level_changed() {
        if (this.m_nCurEditDynamicLevel >= 5) {
            return;
        }
        TextView textView = this.m_textDynamicSens;
        textView.setText("" + ((int) DynamicGunData.get_level_sens(this.m_nCurEditDynamicLevel)));
        TextView textView2 = this.m_textDynamicSensC;
        textView2.setText("" + ((int) DynamicGunData.get_level_c_sens(this.m_nCurEditDynamicLevel)));
        TextView textView3 = this.m_textDynamicSensZ;
        textView3.setText("" + ((int) DynamicGunData.get_level_z_sens(this.m_nCurEditDynamicLevel)));
        this.m_comDynamicLevel.setSelection(this.m_nCurEditDynamicLevel);
        if (this.m_boDynamicStageAdjustVisible) {
            update_dynamic_gun_down_stage_UI();
        }
    }

    public void on_dynamic_level_sens_changed() {
        TextView textView = this.m_textDynamicSens;
        textView.setText("" + ((int) DynamicGunData.get_level_sens(this.m_nCurEditDynamicLevel)));
        TextView textView2 = this.m_textDynamicSensC;
        textView2.setText("" + ((int) DynamicGunData.get_level_c_sens(this.m_nCurEditDynamicLevel)));
        TextView textView3 = this.m_textDynamicSensZ;
        textView3.setText("" + ((int) DynamicGunData.get_level_z_sens(this.m_nCurEditDynamicLevel)));
        this.m_textDynamicResult.setText(DynamicGunData.get_dynamic_result_string());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$new$6() {
        Log.d(TAG, "m_runableSaveDynamicGunStage@@@@\n");
        DynamicGunData.save_dynamic_gun_stage();
    }

    public void begin_dynamic_gun_down_stage_save_timer() {
        this.m_timerHandler.removeCallbacks(this.m_runableSaveDynamicGunStage);
        this.m_timerHandler.postDelayed(this.m_runableSaveDynamicGunStage, 1000L);
    }

    public void on_dynamic_gun_press_changed() {
        bleSendDynamicGunPressData();
        begin_dynamic_gun_down_stage_save_timer();
    }

    public void show_dynamic_gun_adjust_float(boolean z) {
        if (!z) {
            this.m_boDynamicGunAdjustFloatIsVisible = false;
            EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_DYNAMIC_STAGE);
            bleSendDynamicGunPressData();
        } else if (EasyFloat.getAppFloatView(Constants.FLOAT_TAG_DYNAMIC_STAGE) != null) {
        } else {
            EasyFloat.Builder layout = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_DYNAMIC_STAGE).setLayout(R.layout.dynamic_gun_stage, new AnonymousClass104());
            layout.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.105
                @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
                public void createdResult(boolean z2, String str, View view) {
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
                    MainActivity.this.m_dynamicStageRoot.getLocationOnScreen(iArr);
                    MainActivity.m_ini.put(Constants.CFG_DYNAMIC_FLOAT_X, iArr[0]);
                    MainActivity.m_ini.put(Constants.CFG_DYNAMIC_FLOAT_Y, iArr[1]);
                }
            });
            layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null);
            layout.setLocation(NEAT.make_sure_x_visible(m_ini.getInt(Constants.CFG_DYNAMIC_FLOAT_X, 80)), NEAT.make_sure_y_visible(m_ini.getInt(Constants.CFG_DYNAMIC_FLOAT_Y, 0)));
            layout.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.MainActivity$104  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass104 implements OnInvokeView {
        AnonymousClass104() {
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            MainActivity.this.m_dynamicStageRoot = view.findViewById(R.id.root);
            MainActivity.this.m_comDynamicLevel = (Spinner) view.findViewById(R.id.m_comLevel);
            MainActivity.this.m_comDynamicLevel.setAdapter((SpinnerAdapter) new ArrayAdapter(AppInstance.get(), (int) R.layout.dynamic_gun_spinner_item, (int) R.id.text, AppInstance.get().getResources().getStringArray(R.array.dynamic_level)));
            if (!DynamicGunData.s_boUsingGun2) {
                if (DynamicGunData.s_nGun1DynamicLevel > 0) {
                    MainActivity.this.m_nCurEditDynamicLevel = DynamicGunData.s_nGun1DynamicLevel - 1;
                }
            } else if (DynamicGunData.s_nGun2DynamicLevel > 0) {
                MainActivity.this.m_nCurEditDynamicLevel = DynamicGunData.s_nGun2DynamicLevel - 1;
            }
            MainActivity.this.m_btnDynamicLevelSensPlus = (Button) view.findViewById(R.id.main_sens_plus);
            MainActivity.this.m_btnDynamicLevelSensMinus = (Button) view.findViewById(R.id.main_sens_minus);
            MainActivity.this.m_textDynamicSens = (TextView) view.findViewById(R.id.main_sens_value);
            MainActivity.this.m_textDynamicSensC = (TextView) view.findViewById(R.id.c_sens_value);
            MainActivity.this.m_textDynamicSensZ = (TextView) view.findViewById(R.id.z_sens_value);
            MainActivity.this.m_btnEnableDynamicAdjust = (Button) view.findViewById(R.id.dynamic_adjust);
            MainActivity.this.m_arrDynamicStageTitle[0] = (TextView) view.findViewById(R.id.stage1_title);
            MainActivity.this.m_arrDynamicStageTitle[1] = (TextView) view.findViewById(R.id.stage2_title);
            MainActivity.this.m_arrDynamicStageTitle[2] = (TextView) view.findViewById(R.id.stage3_title);
            MainActivity.this.m_arrDynamicStageTitle[3] = (TextView) view.findViewById(R.id.stage4_title);
            MainActivity.this.m_arrDynamicStageTitle[4] = (TextView) view.findViewById(R.id.stage5_title);
            MainActivity.this.m_arrDynamicStageTitle[5] = (TextView) view.findViewById(R.id.stage6_title);
            MainActivity.this.m_arrDynamicStageSeekbar[0] = (SeekBar) view.findViewById(R.id.stage1_rate);
            MainActivity.this.m_arrDynamicStageSeekbar[1] = (SeekBar) view.findViewById(R.id.stage2_rate);
            MainActivity.this.m_arrDynamicStageSeekbar[2] = (SeekBar) view.findViewById(R.id.stage3_rate);
            MainActivity.this.m_arrDynamicStageSeekbar[3] = (SeekBar) view.findViewById(R.id.stage4_rate);
            MainActivity.this.m_arrDynamicStageSeekbar[4] = (SeekBar) view.findViewById(R.id.stage5_rate);
            MainActivity.this.m_arrDynamicStageSeekbar[5] = (SeekBar) view.findViewById(R.id.stage6_rate);
            MainActivity.this.m_arrDynamicStageBtnPlus[0] = (Button) view.findViewById(R.id.stage1_plus);
            MainActivity.this.m_arrDynamicStageBtnPlus[1] = (Button) view.findViewById(R.id.stage2_plus);
            MainActivity.this.m_arrDynamicStageBtnPlus[2] = (Button) view.findViewById(R.id.stage3_plus);
            MainActivity.this.m_arrDynamicStageBtnPlus[3] = (Button) view.findViewById(R.id.stage4_plus);
            MainActivity.this.m_arrDynamicStageBtnPlus[4] = (Button) view.findViewById(R.id.stage5_plus);
            MainActivity.this.m_arrDynamicStageBtnPlus[5] = (Button) view.findViewById(R.id.stage6_plus);
            MainActivity.this.m_arrDynamicStageBtnMinus[0] = (Button) view.findViewById(R.id.stage1_minus);
            MainActivity.this.m_arrDynamicStageBtnMinus[1] = (Button) view.findViewById(R.id.stage2_minus);
            MainActivity.this.m_arrDynamicStageBtnMinus[2] = (Button) view.findViewById(R.id.stage3_minus);
            MainActivity.this.m_arrDynamicStageBtnMinus[3] = (Button) view.findViewById(R.id.stage4_minus);
            MainActivity.this.m_arrDynamicStageBtnMinus[4] = (Button) view.findViewById(R.id.stage5_minus);
            MainActivity.this.m_arrDynamicStageBtnMinus[5] = (Button) view.findViewById(R.id.stage6_minus);
            MainActivity.this.m_arrDynamicStageText[0] = (TextView) view.findViewById(R.id.stage1_value);
            MainActivity.this.m_arrDynamicStageText[1] = (TextView) view.findViewById(R.id.stage2_value);
            MainActivity.this.m_arrDynamicStageText[2] = (TextView) view.findViewById(R.id.stage3_value);
            MainActivity.this.m_arrDynamicStageText[3] = (TextView) view.findViewById(R.id.stage4_value);
            MainActivity.this.m_arrDynamicStageText[4] = (TextView) view.findViewById(R.id.stage5_value);
            MainActivity.this.m_arrDynamicStageText[5] = (TextView) view.findViewById(R.id.stage6_value);
            if (MainActivity.this.m_sSysStatus.uSystemVer <= 88) {
                view.findViewById(R.id.squt_layout).setVisibility(8);
                view.findViewById(R.id.fall_layout).setVisibility(8);
            }
            MainActivity.this.update_dynamic_gun_down_stage_UI();
            MainActivity.this.on_dynamic_adjust_onoff();
            MainActivity.this.on_dynamic_level_changed();
            MainActivity.this.m_comDynamicLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.baidu.kwgames.MainActivity.104.1
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView adapterView, View view2, int i, long j) {
                    MainActivity.this.m_nCurEditDynamicLevel = i;
                    MainActivity.this.on_dynamic_level_changed();
                }
            });
            MainActivity.this.m_btnDynamicLevelSensPlus.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$104$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass104.this.m51lambda$invoke$0$combaidukwgamesMainActivity$104(view2);
                }
            });
            MainActivity.this.m_btnDynamicLevelSensMinus.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$104$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass104.this.m52lambda$invoke$1$combaidukwgamesMainActivity$104(view2);
                }
            });
            view.findViewById(R.id.c_sens_plus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$104$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass104.this.m53lambda$invoke$2$combaidukwgamesMainActivity$104(view2);
                }
            });
            view.findViewById(R.id.c_sens_minus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$104$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass104.this.m54lambda$invoke$3$combaidukwgamesMainActivity$104(view2);
                }
            });
            view.findViewById(R.id.z_sens_plus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$104$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass104.this.m55lambda$invoke$4$combaidukwgamesMainActivity$104(view2);
                }
            });
            view.findViewById(R.id.z_sens_minus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$104$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass104.this.m56lambda$invoke$5$combaidukwgamesMainActivity$104(view2);
                }
            });
            view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$104$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass104.this.m57lambda$invoke$6$combaidukwgamesMainActivity$104(view2);
                }
            });
            view.findViewById(R.id.next_level).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$104$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass104.this.m58lambda$invoke$7$combaidukwgamesMainActivity$104(view2);
                }
            });
            MainActivity.this.m_btnEnableDynamicAdjust.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$104$$ExternalSyntheticLambda8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.AnonymousClass104.this.m59lambda$invoke$8$combaidukwgamesMainActivity$104(view2);
                }
            });
            View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.104.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    for (int i = 0; i < 6; i++) {
                        if (view2 == MainActivity.this.m_arrDynamicStageBtnPlus[i]) {
                            byte b = DynamicGunData.get_stage_data(MainActivity.this.m_nCurEditDynamicLevel, i);
                            if (b < 100) {
                                byte b2 = (byte) (b + 1);
                                TextView textView = MainActivity.this.m_arrDynamicStageText[i];
                                textView.setText(((int) b2) + "%");
                                DynamicGunData.set_stage_data(MainActivity.this.m_nCurEditDynamicLevel, i, b2);
                                MainActivity.this.m_arrDynamicStageSeekbar[i].setProgress(b2);
                                MainActivity.this.on_dynamic_gun_press_changed();
                                return;
                            }
                            return;
                        }
                    }
                    for (int i2 = 0; i2 < 6; i2++) {
                        if (view2 == MainActivity.this.m_arrDynamicStageBtnMinus[i2]) {
                            byte b3 = DynamicGunData.get_stage_data(MainActivity.this.m_nCurEditDynamicLevel, i2);
                            if (b3 > 0) {
                                byte b4 = (byte) (b3 - 1);
                                TextView textView2 = MainActivity.this.m_arrDynamicStageText[i2];
                                textView2.setText(((int) b4) + "%");
                                DynamicGunData.set_stage_data(MainActivity.this.m_nCurEditDynamicLevel, i2, b4);
                                MainActivity.this.m_arrDynamicStageSeekbar[i2].setProgress(b4);
                                MainActivity.this.on_dynamic_gun_press_changed();
                                return;
                            }
                            return;
                        }
                    }
                }
            };
            for (int i = 0; i < 6; i++) {
                MainActivity.this.m_arrDynamicStageBtnPlus[i].setOnClickListener(onClickListener);
                MainActivity.this.m_arrDynamicStageBtnMinus[i].setOnClickListener(onClickListener);
            }
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.baidu.kwgames.MainActivity.104.3
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                    for (int i3 = 0; i3 < 6; i3++) {
                        if (seekBar == MainActivity.this.m_arrDynamicStageSeekbar[i3]) {
                            TextView textView = MainActivity.this.m_arrDynamicStageText[i3];
                            textView.setText(MainActivity.this.m_arrDynamicStageSeekbar[i3].getProgress() + "%");
                            return;
                        }
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    for (int i2 = 0; i2 < 6; i2++) {
                        if (seekBar == MainActivity.this.m_arrDynamicStageSeekbar[i2]) {
                            int progress = MainActivity.this.m_arrDynamicStageSeekbar[i2].getProgress();
                            TextView textView = MainActivity.this.m_arrDynamicStageText[i2];
                            textView.setText(progress + "%");
                            DynamicGunData.set_stage_data(MainActivity.this.m_nCurEditDynamicLevel, i2, (byte) progress);
                            MainActivity.this.on_dynamic_gun_press_changed();
                            return;
                        }
                    }
                }
            };
            for (int i2 = 0; i2 < 6; i2++) {
                MainActivity.this.m_arrDynamicStageSeekbar[i2].setOnSeekBarChangeListener(onSeekBarChangeListener);
            }
            MainActivity.this.m_boDynamicGunAdjustFloatIsVisible = true;
            FloatMgr.resetVirtualMouse();
            MainActivity.this.bleSendDynamicGunPressData();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$0$com-baidu-kwgames-MainActivity$104  reason: not valid java name */
        public /* synthetic */ void m51lambda$invoke$0$combaidukwgamesMainActivity$104(View view) {
            byte b = DynamicGunData.get_level_sens(MainActivity.this.m_nCurEditDynamicLevel);
            if (b < 99) {
                DynamicGunData.set_level_sens(MainActivity.this.m_nCurEditDynamicLevel, (byte) (b + 1));
                MainActivity.this.on_dynamic_level_sens_changed();
                MainActivity.this.on_dynamic_gun_press_changed();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$1$com-baidu-kwgames-MainActivity$104  reason: not valid java name */
        public /* synthetic */ void m52lambda$invoke$1$combaidukwgamesMainActivity$104(View view) {
            byte b = DynamicGunData.get_level_sens(MainActivity.this.m_nCurEditDynamicLevel);
            if (b > 0) {
                DynamicGunData.set_level_sens(MainActivity.this.m_nCurEditDynamicLevel, (byte) (b - 1));
                MainActivity.this.on_dynamic_level_sens_changed();
                MainActivity.this.on_dynamic_gun_press_changed();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$2$com-baidu-kwgames-MainActivity$104  reason: not valid java name */
        public /* synthetic */ void m53lambda$invoke$2$combaidukwgamesMainActivity$104(View view) {
            byte b = DynamicGunData.get_level_c_sens(MainActivity.this.m_nCurEditDynamicLevel);
            if (b < 99) {
                DynamicGunData.set_level_c_sens(MainActivity.this.m_nCurEditDynamicLevel, (byte) (b + 1));
                MainActivity.this.on_dynamic_level_sens_changed();
                MainActivity.this.on_dynamic_gun_press_changed();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$3$com-baidu-kwgames-MainActivity$104  reason: not valid java name */
        public /* synthetic */ void m54lambda$invoke$3$combaidukwgamesMainActivity$104(View view) {
            byte b = DynamicGunData.get_level_c_sens(MainActivity.this.m_nCurEditDynamicLevel);
            if (b > 0) {
                DynamicGunData.set_level_c_sens(MainActivity.this.m_nCurEditDynamicLevel, (byte) (b - 1));
                MainActivity.this.on_dynamic_level_sens_changed();
                MainActivity.this.on_dynamic_gun_press_changed();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$4$com-baidu-kwgames-MainActivity$104  reason: not valid java name */
        public /* synthetic */ void m55lambda$invoke$4$combaidukwgamesMainActivity$104(View view) {
            byte b = DynamicGunData.get_level_z_sens(MainActivity.this.m_nCurEditDynamicLevel);
            if (b < 99) {
                DynamicGunData.set_level_z_sens(MainActivity.this.m_nCurEditDynamicLevel, (byte) (b + 1));
                MainActivity.this.on_dynamic_level_sens_changed();
                MainActivity.this.on_dynamic_gun_press_changed();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$5$com-baidu-kwgames-MainActivity$104  reason: not valid java name */
        public /* synthetic */ void m56lambda$invoke$5$combaidukwgamesMainActivity$104(View view) {
            byte b = DynamicGunData.get_level_z_sens(MainActivity.this.m_nCurEditDynamicLevel);
            if (b > 0) {
                DynamicGunData.set_level_z_sens(MainActivity.this.m_nCurEditDynamicLevel, (byte) (b - 1));
                MainActivity.this.on_dynamic_level_sens_changed();
                MainActivity.this.on_dynamic_gun_press_changed();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$6$com-baidu-kwgames-MainActivity$104  reason: not valid java name */
        public /* synthetic */ void m57lambda$invoke$6$combaidukwgamesMainActivity$104(View view) {
            MainActivity.this.show_dynamic_gun_adjust_float(false);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$7$com-baidu-kwgames-MainActivity$104  reason: not valid java name */
        public /* synthetic */ void m58lambda$invoke$7$combaidukwgamesMainActivity$104(View view) {
            MainActivity.this.m_nCurEditDynamicLevel++;
            if (MainActivity.this.m_nCurEditDynamicLevel >= 5) {
                MainActivity.this.m_nCurEditDynamicLevel = 0;
            }
            MainActivity.this.on_dynamic_level_changed();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$8$com-baidu-kwgames-MainActivity$104  reason: not valid java name */
        public /* synthetic */ void m59lambda$invoke$8$combaidukwgamesMainActivity$104(View view) {
            MainActivity mainActivity = MainActivity.this;
            mainActivity.m_boDynamicStageAdjustVisible = !mainActivity.m_boDynamicStageAdjustVisible;
            MainActivity.this.on_dynamic_adjust_onoff();
            MainActivity.this.bleSendDynamicGunPressData();
        }
    }

    void bleGetToupingParam() {
        try {
            byte[] bArr = {-1, -112, Units.LOBYTE(1), Units.HIBYTE(1), 0};
            IThrone iThrone = this.mThroneService;
            if (iThrone != null) {
                iThrone.bleSendShortData(bArr);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void bleSetAIGunDownOtherParams() {
        if (this.m_sSysStatus.has_AI_gun_down_percent_capacity()) {
            try {
                byte[] bArr = new byte[12];
                bArr[0] = -1;
                bArr[1] = -110;
                bArr[2] = Units.LOBYTE(8);
                bArr[3] = Units.HIBYTE(8);
                bArr[4] = (byte) (this.m_nAIGunDownPercent & 255);
                bArr[5] = (byte) (this.m_bo1stBulletOptimize.booleanValue() ? 1 : 0);
                int[] iArr = this.m_arrAIFireSense;
                bArr[6] = (byte) iArr[0];
                bArr[7] = (byte) iArr[1];
                bArr[8] = (byte) iArr[2];
                bArr[9] = (byte) iArr[3];
                bArr[10] = (byte) iArr[4];
                bArr[11] = (byte) iArr[5];
                IThrone iThrone = this.mThroneService;
                if (iThrone != null) {
                    iThrone.bleSendShortData(bArr);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void show_dynamic_crosshair_edit() {
        if (EasyFloat.getAppFloatView(Constants.FLOAT_DYNAMIC_CROSSHAIR_SET) != null) {
            return;
        }
        EasyFloat.Builder layout = EasyFloat.with(this).setTag(Constants.FLOAT_DYNAMIC_CROSSHAIR_SET).setLayout(R.layout.crosshair_normal, new OnInvokeView() { // from class: com.baidu.kwgames.MainActivity.106
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                MainActivity.this.m_viewDynamicCrosshairSet = view;
                Switch r4 = (Switch) view.findViewById(R.id.sw_scope_on_hide);
                r4.setChecked(MainActivity.this.m_crossDynamicScopeOff.m_boHideWhileScopeOn);
                FloatMgr.resetVirtualMouse();
                r4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.baidu.kwgames.MainActivity.106.1
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        MainActivity.this.m_crossDynamicScopeOff.set_scope_on_hide(z);
                    }
                });
                view.findViewById(R.id.close_window).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.106.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        EasyFloat.dismissAppFloat(Constants.FLOAT_DYNAMIC_CROSSHAIR_SET);
                    }
                });
                ((Button) view.findViewById(R.id.leftOff)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.106.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        MainActivity.this.m_crossDynamicScopeOff.x_offset_minus();
                        FloatMgr.show_aim_float(MainActivity.this.m_crossDynamicScopeOff.m_nOffsetX, MainActivity.this.m_crossDynamicScopeOff.m_nOffsetY);
                    }
                });
                ((Button) view.findViewById(R.id.rightOff)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.106.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        MainActivity.this.m_crossDynamicScopeOff.x_offset_plus();
                        FloatMgr.show_aim_float(MainActivity.this.m_crossDynamicScopeOff.m_nOffsetX, MainActivity.this.m_crossDynamicScopeOff.m_nOffsetY);
                    }
                });
                ((Button) view.findViewById(R.id.upOff)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.106.5
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        MainActivity.this.m_crossDynamicScopeOff.y_offset_minus();
                        FloatMgr.show_aim_float(MainActivity.this.m_crossDynamicScopeOff.m_nOffsetX, MainActivity.this.m_crossDynamicScopeOff.m_nOffsetY);
                    }
                });
                ((Button) view.findViewById(R.id.downOff)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.106.6
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        MainActivity.this.m_crossDynamicScopeOff.y_offset_plus();
                        FloatMgr.show_aim_float(MainActivity.this.m_crossDynamicScopeOff.m_nOffsetX, MainActivity.this.m_crossDynamicScopeOff.m_nOffsetY);
                    }
                });
            }
        });
        layout.registerCallbacks(new OnFloatCallbacks() { // from class: com.baidu.kwgames.MainActivity.107
            @Override // com.lzf.easyfloat.interfaces.OnFloatCallbacks
            public void createdResult(boolean z, String str, View view) {
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
                MainActivity.this.m_viewDynamicCrosshairSet.getLocationOnScreen(iArr);
                MainActivity.m_ini.put(Constants.CFG_DYNAMIC_CROSSHAIR_FLOAT_X, iArr[0]);
                MainActivity.m_ini.put(Constants.CFG_DYNAMIC_CROSSHAIR_FLOAT_Y, iArr[1]);
            }
        });
        layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true);
        layout.setLocation(NEAT.make_sure_x_visible(m_ini.getInt(Constants.CFG_DYNAMIC_CROSSHAIR_FLOAT_X, (AppInstance.s_nScreenW * 3) / 10)), NEAT.make_sure_y_visible(m_ini.getInt(Constants.CFG_DYNAMIC_CROSSHAIR_FLOAT_Y, 0)));
        layout.show();
    }

    @OnClick({R.id.m_btnFireSense})
    public void onFireMouseSense() {
        if (Boolean.valueOf(MsgBox.msg_box_with_never_remind_once_choice2(R.string.fire_sense_msgbox, "fire_sense_msgbox", R.string.iamknow, R.string.ineedvideo, new Handler() { // from class: com.baidu.kwgames.MainActivity.108
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (1 == message.arg2) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(Constants.URL_VIDEO_SENSE_Y));
                    MainActivity.this.startActivity(intent);
                }
                MainActivity.this.show_fire_sense_adjust_float();
            }
        })).booleanValue()) {
            return;
        }
        show_fire_sense_adjust_float();
    }

    public void show_fire_sense_adjust_float() {
        if (EasyFloat.getAppFloatView(Constants.FLOAT_TAG_FIRE_SENSE) != null) {
            EasyFloat.dismissAppFloat(Constants.FLOAT_TAG_FIRE_SENSE);
            return;
        }
        EasyFloat.Builder layout = EasyFloat.with(this).setTag(Constants.FLOAT_TAG_FIRE_SENSE).setLayout(R.layout.ai_fire_sense, new AnonymousClass109(Constants.FLOAT_TAG_FIRE_SENSE));
        layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null);
        layout.setLocation(80, 0);
        layout.show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.MainActivity$109  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass109 implements OnInvokeView {
        final /* synthetic */ String val$strTag;

        AnonymousClass109(String str) {
            this.val$strTag = str;
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            MainActivity.this.m_arrAIFireSensePlus[0] = (Button) view.findViewById(R.id.stage1_plus);
            MainActivity.this.m_arrAIFireSensePlus[1] = (Button) view.findViewById(R.id.stage2_plus);
            MainActivity.this.m_arrAIFireSensePlus[2] = (Button) view.findViewById(R.id.stage3_plus);
            MainActivity.this.m_arrAIFireSensePlus[3] = (Button) view.findViewById(R.id.stage4_plus);
            MainActivity.this.m_arrAIFireSensePlus[4] = (Button) view.findViewById(R.id.stage5_plus);
            MainActivity.this.m_arrAIFireSensePlus[5] = (Button) view.findViewById(R.id.stage6_plus);
            MainActivity.this.m_arrAIFireSenseMinus[0] = (Button) view.findViewById(R.id.stage1_minus);
            MainActivity.this.m_arrAIFireSenseMinus[1] = (Button) view.findViewById(R.id.stage2_minus);
            MainActivity.this.m_arrAIFireSenseMinus[2] = (Button) view.findViewById(R.id.stage3_minus);
            MainActivity.this.m_arrAIFireSenseMinus[3] = (Button) view.findViewById(R.id.stage4_minus);
            MainActivity.this.m_arrAIFireSenseMinus[4] = (Button) view.findViewById(R.id.stage5_minus);
            MainActivity.this.m_arrAIFireSenseMinus[5] = (Button) view.findViewById(R.id.stage6_minus);
            MainActivity.this.m_arrAIFireSenseText[0] = (TextView) view.findViewById(R.id.stage1_value);
            MainActivity.this.m_arrAIFireSenseText[1] = (TextView) view.findViewById(R.id.stage2_value);
            MainActivity.this.m_arrAIFireSenseText[2] = (TextView) view.findViewById(R.id.stage3_value);
            MainActivity.this.m_arrAIFireSenseText[3] = (TextView) view.findViewById(R.id.stage4_value);
            MainActivity.this.m_arrAIFireSenseText[4] = (TextView) view.findViewById(R.id.stage5_value);
            MainActivity.this.m_arrAIFireSenseText[5] = (TextView) view.findViewById(R.id.stage6_value);
            MainActivity.this.update_AI_fire_sense_UI();
            View findViewById = view.findViewById(R.id.close_window);
            final String str = this.val$strTag;
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity$109$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    EasyFloat.dismissAppFloat(str);
                }
            });
            View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.109.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    for (int i = 0; i < MainActivity.this.m_arrAIFireSensePlus.length; i++) {
                        if (view2 == MainActivity.this.m_arrAIFireSensePlus[i]) {
                            if (MainActivity.this.m_arrAIFireSense[i] < 250) {
                                int[] iArr = MainActivity.this.m_arrAIFireSense;
                                iArr[i] = iArr[i] + 1;
                                MainActivity.this.update_AI_fire_sense_UI();
                                Util.save_ini_int_array(MainActivity.m_ini, Constants.CFG_FIRE_MOUSE_SENSE, MainActivity.this.m_arrAIFireSense);
                                MainActivity.this.bleSetAIGunDownOtherParams();
                                return;
                            }
                            return;
                        }
                    }
                    for (int i2 = 0; i2 < MainActivity.this.m_arrAIFireSenseMinus.length; i2++) {
                        if (view2 == MainActivity.this.m_arrAIFireSenseMinus[i2]) {
                            if (MainActivity.this.m_arrAIFireSense[i2] > 5) {
                                int[] iArr2 = MainActivity.this.m_arrAIFireSense;
                                iArr2[i2] = iArr2[i2] - 1;
                                MainActivity.this.update_AI_fire_sense_UI();
                                Util.save_ini_int_array(MainActivity.m_ini, Constants.CFG_FIRE_MOUSE_SENSE, MainActivity.this.m_arrAIFireSense);
                                MainActivity.this.bleSetAIGunDownOtherParams();
                                return;
                            }
                            return;
                        }
                    }
                }
            };
            for (int i = 0; i < MainActivity.this.m_arrAIFireSenseMinus.length; i++) {
                MainActivity.this.m_arrAIFireSensePlus[i].setOnClickListener(onClickListener);
                MainActivity.this.m_arrAIFireSenseMinus[i].setOnClickListener(onClickListener);
            }
            FloatMgr.resetVirtualMouse();
        }
    }

    public void update_AI_fire_sense_UI() {
        for (int i = 0; i < this.m_arrAIFireSenseMinus.length; i++) {
            TextView textView = this.m_arrAIFireSenseText[i];
            textView.setText(this.m_arrAIFireSense[i] + "%");
        }
    }

    @OnClick({R.id.m_btnTouchSenseYPercent})
    public void onTouchSenseYPercent() {
        if (Boolean.valueOf(MsgBox.msg_box_with_never_remind_once_choice2(R.string.touch_sense_y_msgbox, "touch_sense_y_msgbox", R.string.iamknow, R.string.ineedvideo, new Handler() { // from class: com.baidu.kwgames.MainActivity.110
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (1 == message.arg2) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(Constants.URL_VIDEO_SENSE_Y));
                    MainActivity.this.startActivity(intent);
                }
                MainActivity.this.show_touch_sense_Y_percent_float();
            }
        })).booleanValue()) {
            return;
        }
        show_touch_sense_Y_percent_float();
    }

    void bleGetUUID() {
        try {
            byte[] bArr = {-1, -108, Units.LOBYTE(1), 0, 0};
            IThrone iThrone = this.mThroneService;
            if (iThrone != null) {
                iThrone.bleSendShortData(bArr);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void update_bg_image() {
        int i = m_ini.getInt(Constants.CFG_BG_IMAGE, 0);
        if (this.m_nCurBG != i) {
            this.m_nCurBG = i;
            this.m_layoutRoot.setBackgroundResource(UIConst.s_arrBGImages[this.m_nCurBG]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void show_unlock_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(NEAT.s(R.string.unlock_dialog));
        builder.setMessage(NEAT.s(R.string.pls_input_password));
        final EditText editText = new EditText(this);
        builder.setView(editText);
        builder.setPositiveButton(NEAT.s(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.111
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (BanID.unlock(editText.getText().toString())) {
                    MainActivity.this.m_boDevBanned = false;
                    MsgBox.msg_box1((int) R.string.unlock_success);
                    return;
                }
                MsgBox.msg_box1((int) R.string.unlock_password_error);
            }
        });
        builder.setNegativeButton(NEAT.s(R.string.cancle), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.MainActivity.112
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
