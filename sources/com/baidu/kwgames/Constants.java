package com.baidu.kwgames;

import com.baidu.kwgames.util.AIKit;
import com.baidu.kwgames.util.NEAT;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import okhttp3.internal.cache.DiskLruCache;
/* loaded from: classes.dex */
public class Constants {
    public static final String ADB_BASH = "sinco.bash";
    public static final String ADB_DEX = "sincoserver.dex";
    public static final String ADB_DIR = "/storage/emulated/0/SincoGamer/";
    public static final String ADB_HOST_IP = "localhost";
    public static final int ADB_HOST_PORT1 = 5988;
    public static final int ADB_HOST_PORT2 = 10377;
    public static final String BACKUP_DIR = "/storage/emulated/0/MyBackup";
    public static final String CFG_AI_1ST_BULLET_OPTIMIZE = "1STBulletOpti";
    public static final String CFG_AI_ADJUST_VISIBLE = "AIAdjustVisble";
    public static final String CFG_AI_AUTO_ACTIVE_RUN = "AutoActiveRun";
    public static final String CFG_AI_CROSSHAIR_ONOFF = "AICrossHairOnOff";
    public static final String CFG_AI_DYNAMIC_FLOAT_X = "AIDynX";
    public static final String CFG_AI_DYNAMIC_FLOAT_Y = "AIDynY";
    public static final String CFG_AI_DYNAMIC_ONOFF = "AIDynamicOnOff";
    public static final String CFG_AI_EDIT_FLOAT_X = "AIEditX";
    public static final String CFG_AI_EDIT_FLOAT_Y = "AIEditY";
    public static final String CFG_AI_GUN_DOWN_PERCENT = "AIGunDownPercent";
    public static final String CFG_AI_KIT = "AIKit";
    public static final String CFG_AI_PERIOD = "AIPeriod";
    public static final String CFG_AI_XSCOPE_AUTO_DELAY = "AIXScopeAutoDelay";
    public static final String CFG_AI_XSCOPE_AUTO_LOOP = "AIXScopeAutoLoop";
    public static final String CFG_AI_XSCOPE_ONOFF = "AIXScopeOnOff";
    public static final String CFG_BACKUP_COPYED_VER = "BKCopyVer";
    public static final String CFG_BG_IMAGE = "BG_IMAGE";
    public static final String CFG_CURSOR_IMAGE = "CURSOR_IMAGE";
    public static final String CFG_DYNAMIC_ADJUST_VISIBLE = "DyAdjustVisble";
    public static final String CFG_DYNAMIC_CROSSHAIR_FLOAT_X = "DyCrossX";
    public static final String CFG_DYNAMIC_CROSSHAIR_FLOAT_Y = "DyCrossY";
    public static final String CFG_DYNAMIC_FLOAT_X = "DynX";
    public static final String CFG_DYNAMIC_FLOAT_Y = "DynY";
    public static final String CFG_DYNAMIC_OFF_CROSSHAIR_OFFSET_X = "DyOffCrossOX";
    public static final String CFG_DYNAMIC_OFF_CROSSHAIR_OFFSET_Y = "DyOffCrossOY";
    public static final String CFG_DYNAMIC_ON_CROSSHAIR_OFFSET_X = "DyOnCrossOX";
    public static final String CFG_DYNAMIC_ON_CROSSHAIR_OFFSET_Y = "DyOnCrossOY";
    public static final String CFG_DYNAMIC_SCOPE_OFF_CROSSHAIR = "DyScopeOffCross";
    public static final String CFG_DYNAMIC_SCOPE_ON_CROSSHAIR = "DyScopeOnCross";
    public static final String CFG_FIRE_MOUSE_SENSE = "FireMouseSense";
    public static final String CFG_FLOAT_BALL_X = "FloatBallX";
    public static final String CFG_GUN_PARTS_AI = "PartsAI";
    public static final String CFG_KEYMAP_LOADED = "KeyMapLoaded";
    public static final String CFG_MACRO_TIME_SET_FLOAT_X = "MacroTimeX";
    public static final String CFG_MACRO_TIME_SET_FLOAT_Y = "MacroTimeY";
    public static final String CFG_MARQUEE_TEXT_INDEX = "MTextIndex";
    public static final String CFG_NTS_MODE = "NTSMode";
    public static final String CFG_PRESET_COPYED_VER = "PresetCopyVer";
    public static final String CFG_TOUPING_DISPLAY_X = "ToupingDX";
    public static final String CFG_TOUPING_DISPLAY_Y = "ToupingDY";
    public static final String CFG_TO_ALL_SCOPE = "ToAllScope";
    public static final String CFG_XSCOPE_FLOAT_X = "XScopeX";
    public static final String CFG_XSCOPE_FLOAT_Y = "XScopeY";
    public static final String DYNAMIC_GUN_STAGE = "/DynamicGunStage.bin";
    public static final int E_ADB_VER = 16;
    public static final int E_AI_CORRECT_TIME = 2000;
    public static final int E_AI_CROSS_HARIR_RECHECK_PERIOD = 800;
    public static final int E_AI_DYNAMIC_GUN_SAVE_PERIOD = 1000;
    public static final int E_AI_GUN_DOWN_PERCENT_MAX = 300;
    public static final int E_AI_GUN_DOWN_PERCENT_MIN = 20;
    public static final int E_AI_GUN_STAGE_CNT = 6;
    public static final int E_AI_ITEM_GUN1 = 1;
    public static final int E_AI_ITEM_GUN2 = 2;
    public static final int E_AI_ITEM_SCOPE = 3;
    public static final int E_AI_KIT_CNT = 5;
    public static final int E_AI_KIT_HPJY = 0;
    public static final int E_AI_KIT_HPJY_CHAOTI = 2;
    public static final int E_AI_KIT_PUBG = 1;
    public static final int E_AI_KIT_PUBG_TU = 3;
    public static final int E_AI_KIT_PUBG_VN = 4;
    public static final int E_AI_PARAM_SAVE_PERIOD = 3000;
    public static final int E_AI_PERIOD_DEF = 100;
    public static final int E_AI_PERIOD_MAX = 300;
    public static final int E_AI_PERIOD_MIN = 50;
    public static final int E_AI_PERIOD_WHILE_VIRTUAL_MOUSE_ON = 300;
    public static final int E_AI_RESULT_AUTO_HIDE_PERIOD = 300000;
    public static final int E_AI_STAGE_SAVE_PERIOD = 5000;
    public static final String E_ASSETS_DIR_GUN_HANDLE = "/gunhandle";
    public static final String E_ASSETS_DIR_GUN_HEAD = "/gunhead";
    public static final String E_ASSETS_DIR_GUN_MODEL = "/Model";
    public static final String E_ASSETS_DIR_GUN_TAIL = "/guntail";
    public static final String E_ASSETS_DIR_SCOPE = "/scope";
    public static final int E_BLE_BUF_DECODE_HEAD = 1;
    public static final int E_BLE_BUF_DECODE_KEY_DOWN = 2;
    public static final int E_BLE_BUF_DECODE_MOUSE_DOWN = 3;
    public static final int E_BLE_BUF_DECODE_MTK_DATA = 7;
    public static final int E_BLE_BUF_DECODE_NONE = 0;
    public static final int E_BLE_BUF_DECODE_UPLOAD_KEY_MAP_DATA = 5;
    public static final int E_BLE_BUF_DECODE_UPLOAD_KEY_MAP_LEN = 4;
    public static final int E_BLE_BUF_DECODE_USB_MTK_DATA = 8;
    public static final int E_BLE_BUF_DECODE_VARIABLE_DATA = 6;
    public static final int E_BLE_BUF_TYPE_AI_CROSSHAIR_STATE = 11;
    public static final int E_BLE_BUF_TYPE_AI_GUN_DOWN_OTHERS = 146;
    public static final int E_BLE_BUF_TYPE_AI_GUN_DOWN_STAGE = 12;
    public static final int E_BLE_BUF_TYPE_AI_GUN_PARTS_REDUCE = 15;
    public static final int E_BLE_BUF_TYPE_AI_GUN_PRESS = 10;
    public static final int E_BLE_BUF_TYPE_AI_SMART_QE = 149;
    public static final int E_BLE_BUF_TYPE_AI_XSCOPE = 147;
    public static final int E_BLE_BUF_TYPE_CHANGE_MODE = 135;
    public static final int E_BLE_BUF_TYPE_CUSTOMIZE_PARAM = 152;
    public static final int E_BLE_BUF_TYPE_DISABLE_TOUCH = 131;
    public static final int E_BLE_BUF_TYPE_DYNAMIC_LEVEL_CHANGED = 13;
    public static final int E_BLE_BUF_TYPE_ENTER_UPDATE = 134;
    public static final int E_BLE_BUF_TYPE_GET_GUN_PRESS_AI = 138;
    public static final int E_BLE_BUF_TYPE_GET_GUN_PRESS_MORE = 136;
    public static final int E_BLE_BUF_TYPE_GET_KEY_MAP = 129;
    public static final int E_BLE_BUF_TYPE_GET_SYSTEM_STATUS = 132;
    public static final int E_BLE_BUF_TYPE_GET_TOUPING_PARAM = 144;
    public static final int E_BLE_BUF_TYPE_GET_UUID = 148;
    public static final int E_BLE_BUF_TYPE_GUN_PRESS_AI = 9;
    public static final int E_BLE_BUF_TYPE_GUN_PRESS_DYNAMIC = 143;
    public static final int E_BLE_BUF_TYPE_GUN_PRESS_MORE = 8;
    public static final int E_BLE_BUF_TYPE_KEY_DOWN = 1;
    public static final int E_BLE_BUF_TYPE_MAP = 128;
    public static final int E_BLE_BUF_TYPE_MOUSE_DOWN = 2;
    public static final int E_BLE_BUF_TYPE_MTK_DATA = 7;
    public static final int E_BLE_BUF_TYPE_SAVE_KEY_MAP = 130;
    public static final int E_BLE_BUF_TYPE_SET_AI_OFF = 142;
    public static final int E_BLE_BUF_TYPE_SET_AI_STATE = 140;
    public static final int E_BLE_BUF_TYPE_SET_GAME_PARAM = 151;
    public static final int E_BLE_BUF_TYPE_SET_GUN_PRESS_AI = 139;
    public static final int E_BLE_BUF_TYPE_SET_GUN_PRESS_MORE = 137;
    public static final int E_BLE_BUF_TYPE_SET_M4_AI_STATE = 150;
    public static final int E_BLE_BUF_TYPE_SET_PARAM = 18;
    public static final int E_BLE_BUF_TYPE_SET_SYSTEM_STATUS = 133;
    public static final int E_BLE_BUF_TYPE_SET_TOUPING_PARAM = 145;
    public static final int E_BLE_BUF_TYPE_SET_TOUPING_TOGGLE = 141;
    public static final int E_BLE_BUF_TYPE_UPLOAD_DYNAMIC_VALUE = 14;
    public static final int E_BLE_BUF_TYPE_UPLOAD_KEY_MAP = 3;
    public static final int E_BLE_BUF_TYPE_UPLOAD_SYSTEM_STATUS = 4;
    public static final int E_BLE_BUF_TYPE_UPLOAD_TOUPING_PARAM = 16;
    public static final int E_BLE_BUF_TYPE_UPLOAD_UUID = 17;
    public static final int E_BLE_BUF_TYPE_USB_MTK_DATA = 19;
    public static final int E_BLE_BUF_TYPE_VIRTUAL_MOUSE_MOVE = 5;
    public static final int E_BLE_BUF_TYPE_VIRTUAL_MOUSE_ONOFF = 6;
    public static final byte E_CHANGE_MODE_PARAM_BIT_HARD_TIMER = 4;
    public static final byte E_CHANGE_MODE_PARAM_BIT_MINECRAFT = 3;
    public static final byte E_CHANGE_MODE_PARAM_BIT_MOBA_MODE = 0;
    public static final byte E_CHANGE_MODE_PARAM_BIT_TOUPING = 2;
    public static final byte E_CHANGE_MODE_PARAM_BIT_USB_AUDIO_ON = 1;
    public static final int E_CROSSHAIR_BRACKET = 9;
    public static final int E_CROSSHAIR_BRACKET2 = 10;
    public static final int E_CROSSHAIR_CYCLE = 8;
    public static final int E_CROSSHAIR_GREEN = 3;
    public static final int E_CROSSHAIR_MAX = 10;
    public static final int E_CROSSHAIR_OFF = 0;
    public static final int E_CROSSHAIR_RED = 2;
    public static final int E_CROSSHAIR_REDDOT_1 = 5;
    public static final int E_CROSSHAIR_REDDOT_2 = 6;
    public static final int E_CROSSHAIR_REDDOT_3 = 7;
    public static final int E_CROSSHAIR_WHITE = 1;
    public static final int E_CROSSHAIR_YELLOW = 4;
    public static final int E_CUSTOMIZE_PARAM_MACRO_ADJUST = 0;
    public static final int E_DEV_ID_M16 = 9;
    public static final int E_DEV_ID_M1_NEW = 11;
    public static final int E_DEV_ID_M1_PRO = 2;
    public static final int E_DEV_ID_M2 = 3;
    public static final int E_DEV_ID_M24 = 10;
    public static final int E_DEV_ID_M2_NEW = 12;
    public static final int E_DEV_ID_M3 = 8;
    public static final int E_DEV_ID_M3_AQTW = 17;
    public static final int E_DEV_ID_M4 = 18;
    public static final int E_DEV_ID_M4_AQTW = 19;
    public static final int E_DEV_ID_MK12 = 15;
    public static final float E_DIALOG_ID_OTG_REMIND = 1.0f;
    public static final int E_DISABLE_TOUCH_PERIOD = 600;
    public static final int E_DYNAMIC_GUN_BIT_C = 4;
    public static final int E_DYNAMIC_GUN_BIT_GUN1_CONTINUES = 2;
    public static final int E_DYNAMIC_GUN_BIT_GUN2_CONTINUES = 3;
    public static final int E_DYNAMIC_GUN_BIT_OPEN_SCOPE = 1;
    public static final int E_DYNAMIC_GUN_BIT_USING_GUN2 = 0;
    public static final int E_DYNAMIC_GUN_BIT_Z = 5;
    public static final int E_DYNAMIC_GUN_DOWN_STATGE_CNT = 6;
    public static final int E_DYNAMIC_RESULT_AUTO_HIDE_PERIOD = 30000;
    public static final byte E_EXT3_BIT_MINECRAFT_MODE = 2;
    public static final byte E_EXT3_BIT_MOBA_MODE = 0;
    public static final byte E_EXT3_BIT_USB_AUDIO_OFF = 1;
    public static final byte E_EXT_FLAG3_BIT_AI_CONTINUES_SHOOT = 7;
    public static final byte E_EXT_FLAG3_BIT_CTRL_HOLD = 3;
    public static final byte E_EXT_FLAG3_BIT_E_REPEAT = 4;
    public static final byte E_EXT_FLAG3_BIT_HARD_TIMER = 6;
    public static final byte E_EXT_FLAG3_BIT_Q_REPEAT = 5;
    public static final int E_EXT_FLAG4_BIT_CF_DYNAMIC_CONTINUES = 0;
    public static final int E_EXT_FLAG4_BIT_MOUSE_WHEEL_SWITCH_GUN = 3;
    public static final int E_EXT_FLAG4_BIT_MTK_USB_MODE = 5;
    public static final int E_EXT_FLAG4_BIT_NTS_MODE = 4;
    public static final int E_EXT_FLAG4_BIT_R_THROW_L = 7;
    public static final int E_EXT_FLAG4_BIT_SHIFT_WALK = 2;
    public static final int E_EXT_FLAG4_BIT_SMART_QE = 6;
    public static final int E_EXT_FLAG4_BIT_VMOUSE_CENTER = 1;
    public static final int E_EXT_FLAG6_BIT_RANDOM_BLE_NAME = 0;
    public static final String E_GAME_ALBION = "com.sandboxinteractive.albiononline";
    public static final String E_GAME_NAME_MINECRAFT = "com.mojang.minecraftpe";
    public static final String E_GAME_NAME_WMCQ = "com.yingxiong.hero";
    public static final String E_GAME_YANYUN = "com.netease.yyslscn";
    public static final byte E_GESTURE_CNT = 3;
    public static final byte E_GESTURE_LIE = 2;
    public static final byte E_GESTURE_SQUAT = 1;
    public static final byte E_GESTURE_STAND = 0;
    public static final int E_GET_SYSTEM_STATUS_PERIOD = 2000;
    public static final byte E_GUNHANDLE_BANJIE = 4;
    public static final byte E_GUNHANDLE_CHUIZHI = 2;
    public static final byte E_GUNHANDLE_CNT = 6;
    public static final byte E_GUNHANDLE_JIGUANG = 3;
    public static final byte E_GUNHANDLE_MUZHI = 5;
    public static final byte E_GUNHANDLE_QINXING = 0;
    public static final byte E_GUNHANDLE_SANJIAO = 1;
    public static final byte E_GUNHEAD_BUCHANG = 0;
    public static final byte E_GUNHEAD_CFBUCHANG = 5;
    public static final byte E_GUNHEAD_CFXIAOYAN = 7;
    public static final byte E_GUNHEAD_CFXIAOYIN = 6;
    public static final byte E_GUNHEAD_CNT = 8;
    public static final byte E_GUNHEAD_JUBUCHANG = 3;
    public static final byte E_GUNHEAD_JUXIAOYAN = 4;
    public static final byte E_GUNHEAD_XIAOYAN = 2;
    public static final byte E_GUNHEAD_XIAOYIN = 1;
    public static final byte E_GUNTAIL_CNT = 3;
    public static final byte E_GUNTAIL_JUQIANGTUO = 1;
    public static final byte E_GUNTAIL_QIANGTUO = 0;
    public static final byte E_GUNTAIL_UZI = 2;
    public static final byte E_GUN_98k = 0;
    public static final byte E_GUN_AA12 = 56;
    public static final byte E_GUN_ACE32 = 63;
    public static final byte E_GUN_ACVAL = 42;
    public static final byte E_GUN_AKM = 1;
    public static final byte E_GUN_AKS = 58;
    public static final byte E_GUN_AMR = 41;
    public static final byte E_GUN_AUG = 2;
    public static final byte E_GUN_AVD = 60;
    public static final byte E_GUN_AWM = 3;
    public static final byte E_GUN_CNT = 66;
    public static final byte E_GUN_DBS = 4;
    public static final byte E_GUN_DP28 = 5;
    public static final byte E_GUN_DUANGUAN_SANDAN = 36;
    public static final byte E_GUN_DUN = 55;
    public static final byte E_GUN_F57 = 65;
    public static final byte E_GUN_FAMAS = 57;
    public static final byte E_GUN_FUHEGONG = 59;
    public static final byte E_GUN_G36C = 6;
    public static final byte E_GUN_GROZA = 7;
    public static final byte E_GUN_JS9 = 64;
    public static final byte E_GUN_JUMANG = 62;
    public static final byte E_GUN_LIEGONG = 48;
    public static final byte E_GUN_M16A4 = 8;
    public static final byte E_GUN_M200 = 53;
    public static final byte E_GUN_M24 = 9;
    public static final byte E_GUN_M249 = 10;
    public static final byte E_GUN_M416 = 11;
    public static final byte E_GUN_M417 = 47;
    public static final byte E_GUN_M762 = 12;
    public static final byte E_GUN_MG3 = 13;
    public static final byte E_GUN_MG36 = 61;
    public static final byte E_GUN_MIHUAN = 49;
    public static final byte E_GUN_MK12 = 46;
    public static final byte E_GUN_MK14 = 15;
    public static final byte E_GUN_MK20 = 51;
    public static final byte E_GUN_MK47 = 16;
    public static final byte E_GUN_MOXIN_NAGAN = 38;
    public static final byte E_GUN_MP5K = 17;
    public static final byte E_GUN_MiNi14 = 14;
    public static final byte E_GUN_NU = 33;
    public static final byte E_GUN_P18C = 18;
    public static final byte E_GUN_P1911 = 45;
    public static final byte E_GUN_P90 = 44;
    public static final byte E_GUN_P92 = 50;
    public static final byte E_GUN_PKM = 54;
    public static final byte E_GUN_PRESS_MIN = 0;
    public static final byte E_GUN_QBU = 19;
    public static final byte E_GUN_QBZ = 20;
    public static final byte E_GUN_R1895 = 21;
    public static final byte E_GUN_R45 = 22;
    public static final byte E_GUN_S12K = 23;
    public static final byte E_GUN_S1897 = 24;
    public static final byte E_GUN_SCAR = 26;
    public static final byte E_GUN_SHAMOZHIYING = 35;
    public static final byte E_GUN_SKS = 27;
    public static final byte E_GUN_SLR = 28;
    public static final byte E_GUN_SPAS = 43;
    public static final byte E_GUN_TOMSON = 34;
    public static final byte E_GUN_UMP45 = 29;
    public static final byte E_GUN_UZI = 30;
    public static final byte E_GUN_VER_3 = 49;
    public static final byte E_GUN_VER_4 = 50;
    public static final byte E_GUN_VICTOR = 37;
    public static final byte E_GUN_VSS = 31;
    public static final byte E_GUN_WIN94 = 32;
    public static final byte E_GUN_XIESHI = 39;
    public static final byte E_GUN_YENIU = 40;
    public static final byte E_GUN_ZSNU = 52;
    public static final byte E_GUN_s686 = 25;
    public static final int E_KEY_TRIGGER_CNT = 3;
    public static final int E_KEY_TRIGGER_DOWN = 1;
    public static final int E_KEY_TRIGGER_NORMAL = 0;
    public static final int E_KEY_TRIGGER_UP = 2;
    public static final int E_KEY_TYPE_WASD_KEY = 6;
    public static final int E_MACRO_FLAG_AVOID_TAKE_OFF_STUCK = 2;
    public static final int E_MACRO_FLAG_AVOID_VIEW_STUCK = 1;
    public static final int E_MACRO_FLAG_CNT = 3;
    public static final int E_MACRO_FLAG_NONE = 0;
    public static final int E_MACRO_TRIGGER_CNT = 3;
    public static final int E_MACRO_TRIGGER_DOWN_ONCE = 1;
    public static final int E_MACRO_TRIGGER_NORMAL = 0;
    public static final int E_MACRO_TRIGGER_UP = 2;
    public static final int E_MARQUEE_TEXT_CHANGE_PERIOD = 60000;
    public static final int E_MARQUEE_TEXT_TIMER_TOKEN = 1;
    public static final int E_MSG_ID_MSG_BOX_SEL = 1;
    public static final int E_MSG_ID_MTK_STATUS_CHANGED = 0;
    public static final byte E_SCOPE_0 = 0;
    public static final byte E_SCOPE_1 = 1;
    public static final byte E_SCOPE_2 = 2;
    public static final byte E_SCOPE_3 = 3;
    public static final byte E_SCOPE_4 = 4;
    public static final byte E_SCOPE_6 = 5;
    public static final byte E_SCOPE_8 = 6;
    public static final int E_SCOPE_AI_2X = 3;
    public static final int E_SCOPE_AI_2X2 = 15;
    public static final int E_SCOPE_AI_3X = 4;
    public static final int E_SCOPE_AI_3X2 = 16;
    public static final int E_SCOPE_AI_4X = 5;
    public static final int E_SCOPE_AI_6X = 6;
    public static final int E_SCOPE_AI_8X = 7;
    public static final int E_SCOPE_AI_CNT = 17;
    public static final int E_SCOPE_AI_HOLO2 = 10;
    public static final int E_SCOPE_AI_HOLO3 = 11;
    public static final int E_SCOPE_AI_HONGDIAN = 1;
    public static final int E_SCOPE_AI_HONGDIAN2 = 8;
    public static final int E_SCOPE_AI_HONGDIAN3 = 9;
    public static final int E_SCOPE_AI_JIMIAO = 0;
    public static final int E_SCOPE_AI_QUANXI = 2;
    public static final int E_SCOPE_AI_SCOPE2 = 12;
    public static final int E_SCOPE_AI_SCOPE3 = 13;
    public static final int E_SCOPE_AI_ZS = 14;
    public static final byte E_SCOPE_CNT = 7;
    public static final int E_SCOPE_MASK_23468X = 248;
    public static final int E_SCOPE_MASK_68X = 192;
    public static final int E_SCOPE_MASK_8X = 128;
    public static final byte E_SEND_STATUS_DATA_POS = 4;
    public static final int E_SET_PARAM_QIANLIYAN = 0;
    public static final int E_SET_PARAM_STAGE_GUN_PRESS_PREVIOUS = 1;
    public static final int E_SLIDE_KEY_TRIGGER_CNT = 3;
    public static final int E_SLIDE_KEY_TRIGGER_DOWN = 0;
    public static final int E_SLIDE_KEY_TRIGGER_HOLD_LOOP = 2;
    public static final int E_SLIDE_KEY_TRIGGER_UP = 1;
    public static final byte E_STATUE_BYTE_AD_AUTO_AD_ON_OFF = 20;
    public static final byte E_STATUE_BYTE_AD_AUTO_AD_SPEED = 21;
    public static final byte E_STATUE_BYTE_CAPACITY = 13;
    public static final byte E_STATUE_BYTE_CAPACITY2 = 15;
    public static final byte E_STATUE_BYTE_CAPACITY3 = 19;
    public static final byte E_STATUE_BYTE_CAPACITY4 = 25;
    public static final byte E_STATUE_BYTE_CAPACITY5 = 26;
    public static final byte E_STATUE_BYTE_CAPACITY6 = 27;
    public static final byte E_STATUE_BYTE_CTRL_REPEAT_SPEED = 24;
    public static final byte E_STATUE_BYTE_EXT = 5;
    public static final byte E_STATUE_BYTE_EXT3 = 12;
    public static final byte E_STATUE_BYTE_EXT4 = 16;
    public static final byte E_STATUE_BYTE_EXT5 = 17;
    public static final byte E_STATUE_BYTE_EXT6 = 28;
    public static final byte E_STATUE_BYTE_EXT7 = 29;
    public static final byte E_STATUE_BYTE_GUN1_SENS = 8;
    public static final byte E_STATUE_BYTE_GUN2_SENS = 9;
    public static final byte E_STATUE_BYTE_HP_VOL = 14;
    public static final byte E_STATUE_BYTE_KEY_MAP = 11;
    public static final byte E_STATUE_BYTE_MODEL = 3;
    public static final byte E_STATUE_BYTE_MOUSE_SENS = 7;
    public static final byte E_STATUE_BYTE_SHUN_FENG_ER = 23;
    public static final byte E_STATUE_BYTE_STATUS = 10;
    public static final byte E_STATUE_BYTE_TOUCH_SENS = 6;
    public static final byte E_STATUE_BYTE_VER = 4;
    public static final byte E_STATUE_BYTE_VOL_PLUS = 22;
    public static final byte E_STATUE_Y_SENS_PERCENT = 18;
    public static final byte E_STATUS_EXT_BIT_X1 = 7;
    public static final byte E_SUPPORT_1ST_GUN_OPT = 3;
    public static final byte E_SUPPORT_ADJUST_CTRL_REPEAT = 5;
    public static final byte E_SUPPORT_ADJUST_USB_SPEED = 6;
    public static final byte E_SUPPORT_AD_AUTO_AD = 1;
    public static final byte E_SUPPORT_AI_CONTINUES_ADJUST = 7;
    public static final byte E_SUPPORT_AI_GUN_DOWN_RATE = 2;
    public static final byte E_SUPPORT_AI_MOUSE_SENSE = 0;
    public static final byte E_SUPPORT_BAN_ID = 7;
    public static final byte E_SUPPORT_DIRECT_MINECRAFT_MODE = 1;
    public static final byte E_SUPPORT_DIRECT_MOBA_MODE = 0;
    public static final byte E_SUPPORT_DIRECT_VIEW_CAST = 3;
    public static final byte E_SUPPORT_DIRECT_X1_MODE = 2;
    public static final byte E_SUPPORT_DYNAMIC_GUN_DOWN = 5;
    public static final byte E_SUPPORT_GAME_SETTING = 5;
    public static final byte E_SUPPORT_GUN_PARTS_REDUCE = 6;
    public static final byte E_SUPPORT_KEY_CONFIG_EX = 4;
    public static final byte E_SUPPORT_M4_DYNAMIC = 4;
    public static final byte E_SUPPORT_MACRO_TIME = 6;
    public static final byte E_SUPPORT_MOUSE_WHEEL_SW_GUN = 3;
    public static final byte E_SUPPORT_MTK_USB_MODE = 2;
    public static final byte E_SUPPORT_NTS = 6;
    public static final byte E_SUPPORT_QIANLIYAN = 0;
    public static final byte E_SUPPORT_RANDOM_BLE_NAME = 7;
    public static final byte E_SUPPORT_REMOVE_FROZEN = 4;
    public static final byte E_SUPPORT_SHIFT_2_WALK = 1;
    public static final byte E_SUPPORT_SHUNFENGER = 2;
    public static final byte E_SUPPORT_SMART_QE = 3;
    public static final byte E_SUPPORT_SMART_TPPFPP = 1;
    public static final byte E_SUPPORT_TOUPING_2_MONITOR = 0;
    public static final byte E_SUPPORT_W_GUN_DOWN = 4;
    public static final byte E_SUPPORT_X_SCOPE = 5;
    public static final byte E_SUPPORT_Y_RATIO = 7;
    public static final int E_TOUCH_SIZE_X = 1200;
    public static final int E_TOUCH_SIZE_Y = 2200;
    public static final float E_UI_KEY_DISPLAY_AI_ICON_ALPHA = 0.3f;
    public static final float E_UI_KEY_DISPLAY_BG_ALPHA = 0.3f;
    public static final float E_UI_KEY_DISPLAY_TEXT_ALPHA = 0.8f;
    public static final float E_UI_KEY_DISPLAY_WASD_ALPHA = 0.3f;
    public static final float E_UI_SETTING_OTHER_IMAGE_ALPHA = 1.0f;
    public static final float E_UI_SETTING_WASD_ALPHA = 0.7f;
    public static final int E_VER_SUPPORT_MACRO_DELAY = 96;
    public static final int E_VER_SUPPORT_MACRO_TRIGGER_TYPE = 100;
    public static final int E_VIRTUAL_MOUSE_MOVE_PERIOD_MIN = 25;
    public static final int E_VIRTUAL_MOUSE_MOVE_STEP = 2;
    public static final int E_VIRTUAL_MOUSE_UPDATE_PERIOD = 1;
    public static final int E_WORK_PLATEFORM_ANDROID = 0;
    public static final int E_WORK_PLATEFORM_IOS = 1;
    public static final int E_WORK_PLATEFORM_MTK = 2;
    public static final int E_WORK_PLATEFORM_MTK_USB = 6;
    public static final int E_WORK_PLATEFORM_NTS = 5;
    public static final int E_WORK_PLATEFORM_USB = 3;
    public static final int E_WORK_PLATEFORM_X1 = 4;
    public static final int E_X_SCOPE_TRIGGER_MODE_AUTO = 1;
    public static final int E_X_SCOPE_TRIGGER_MODE_CNT = 2;
    public static final int E_X_SCOPE_TRIGGER_MODE_MANUAL = 0;
    public static final int E_Y_SENSE_PERCENT_MAX = 200;
    public static final int E_Y_SENSE_PERCENT_MIN = 20;
    public static final String FLOAT_ACTIVE_RUN_ADJUST = "ActRunAdj";
    public static final String FLOAT_AI_1ST_BULLET_OPT_ADJUST = "1STBulOptAdj";
    public static final String FLOAT_AI_CROSSHAIR_ADJUST = "AICrossAdj";
    public static final String FLOAT_AI_SMART_FPP_ADJUST = "SFPPAdj";
    public static final String FLOAT_AI_XSCOPE_ADJUST = "XScopeAdj";
    public static final String FLOAT_DYNAMIC_CROSSHAIR_SET = "DyCross";
    public static final String FLOAT_MACRO_TIME = "MacroTime";
    public static final String FLOAT_MSG_BOX1 = "FloatMsg1";
    public static final String FLOAT_MSG_BOX2 = "FloatMsg2";
    public static final String FLOAT_SCOPE_6384 = "SCOPE6384";
    public static final String FLOAT_TAG_AI_BAG_TAG = "AIBag";
    public static final String FLOAT_TAG_AI_C = "squatBuilder";
    public static final String FLOAT_TAG_AI_GUN1 = "gunPos";
    public static final String FLOAT_TAG_AI_GUN2 = "gunPos2";
    public static final String FLOAT_TAG_AI_GUN_DOWN_PERCENT = "aiGunPercent";
    public static final String FLOAT_TAG_AI_GUN_HANDLE1 = "AIGunHandle1";
    public static final String FLOAT_TAG_AI_GUN_HANDLE2 = "AIGunHandle2";
    public static final String FLOAT_TAG_AI_GUN_HEAD1 = "AIGunHead1";
    public static final String FLOAT_TAG_AI_GUN_HEAD2 = "AIGunHead2";
    public static final String FLOAT_TAG_AI_GUN_TAIL1 = "AIGunTail1";
    public static final String FLOAT_TAG_AI_GUN_TAIL2 = "AIGunTail2";
    public static final String FLOAT_TAG_AI_MIRROW = "mirror";
    public static final String FLOAT_TAG_AI_MOVE = "saveMould";
    public static final String FLOAT_TAG_AI_PREVIEW = "mouldTip";
    public static final String FLOAT_TAG_AI_RESULT = "recogn";
    public static final String FLOAT_TAG_AI_STAGE = "aistage";
    public static final String FLOAT_TAG_AI_TAKE_OFF_TAG = "AITakeOff";
    public static final String FLOAT_TAG_AI_Z = "downBuilder";
    public static final String FLOAT_TAG_BALL = "icon";
    public static final String FLOAT_TAG_DYNAMIC_RESULT = "dynares";
    public static final String FLOAT_TAG_DYNAMIC_STAGE = "dynastage";
    public static final String FLOAT_TAG_FIRE_SENSE = "aiFireSense";
    public static final String FLOAT_TAG_SCOPE2 = "doubleMirrorBuilder";
    public static final String FLOAT_TAG_TOUCH_SENSE_Y_PERCENT = "touchSenseYPercent";
    public static final byte KEY_0 = 39;
    public static final byte KEY_1 = 30;
    public static final byte KEY_102ND = 100;
    public static final byte KEY_2 = 31;
    public static final byte KEY_3 = 32;
    public static final byte KEY_4 = 33;
    public static final byte KEY_5 = 34;
    public static final byte KEY_6 = 35;
    public static final byte KEY_7 = 36;
    public static final byte KEY_8 = 37;
    public static final byte KEY_9 = 38;
    public static final byte KEY_A = 4;
    public static final byte KEY_ALT_L = -30;
    public static final byte KEY_ALT_R = -26;
    public static final byte KEY_APOSTROPHE = 52;
    public static final byte KEY_B = 5;
    public static final byte KEY_BACKSLASH = 49;
    public static final byte KEY_BACKSPACE = 42;
    public static final byte KEY_BEIJING_EXCHANGE = -8;
    public static final byte KEY_BEIJING_SCALE = -7;
    public static final byte KEY_C = 6;
    public static final byte KEY_CAPSLOCK = 57;
    public static final byte KEY_COMMA = 54;
    public static final byte KEY_COMPOSE = 101;
    public static final byte KEY_CTRL_L = -32;
    public static final byte KEY_CTRL_R = -28;
    public static final byte KEY_D = 7;
    public static final byte KEY_DELETE = 76;
    public static final byte KEY_DOT = 55;
    public static final byte KEY_DOWN = 81;
    public static final byte KEY_E = 8;
    public static final byte KEY_END = 77;
    public static final byte KEY_ENTER = 40;
    public static final byte KEY_EQUAL = 46;
    public static final byte KEY_ESC = 41;
    public static final byte KEY_F = 9;
    public static final byte KEY_F1 = 58;
    public static final byte KEY_F10 = 67;
    public static final byte KEY_F11 = 68;
    public static final byte KEY_F12 = 69;
    public static final byte KEY_F2 = 59;
    public static final byte KEY_F3 = 60;
    public static final byte KEY_F4 = 61;
    public static final byte KEY_F5 = 62;
    public static final byte KEY_F6 = 63;
    public static final byte KEY_F7 = 64;
    public static final byte KEY_F8 = 65;
    public static final byte KEY_F9 = 66;
    public static final byte KEY_G = 10;
    public static final byte KEY_GRAVE = 53;
    public static final byte KEY_H = 11;
    public static final byte KEY_HASHTILDE = 50;
    public static final byte KEY_HOME = 74;
    public static final byte KEY_I = 12;
    public static final byte KEY_INSERT = 73;
    public static final byte KEY_J = 13;
    public static final byte KEY_K = 14;
    public static final byte KEY_KP0 = 98;
    public static final byte KEY_KP1 = 89;
    public static final byte KEY_KP2 = 90;
    public static final byte KEY_KP3 = 91;
    public static final byte KEY_KP4 = 92;
    public static final byte KEY_KP5 = 93;
    public static final byte KEY_KP6 = 94;
    public static final byte KEY_KP7 = 95;
    public static final byte KEY_KP8 = 96;
    public static final byte KEY_KP9 = 97;
    public static final byte KEY_KPASTERISK = 85;
    public static final byte KEY_KPDOT = 99;
    public static final byte KEY_KPENTER = 88;
    public static final byte KEY_KPEQUAL = 103;
    public static final byte KEY_KPMINUS = 86;
    public static final byte KEY_KPPLUS = 87;
    public static final byte KEY_KPSLASH = 84;
    public static final byte KEY_L = 15;
    public static final byte KEY_LEFT = 80;
    public static final byte KEY_LEFTBRACE = 47;
    public static final byte KEY_M = 16;
    public static final byte KEY_MINUS = 45;
    public static final byte KEY_MOBA_CTRL_E = -62;
    public static final byte KEY_MOBA_CTRL_Q = -64;
    public static final byte KEY_MOBA_CTRL_R = -61;
    public static final byte KEY_MOBA_CTRL_W = -63;
    public static final byte KEY_MOUSE = -13;
    public static final byte KEY_MOUSE_L = -16;
    public static final byte KEY_MOUSE_M = -15;
    public static final byte KEY_MOUSE_R = -14;
    public static final byte KEY_MOUSE_SIDE1 = -11;
    public static final byte KEY_MOUSE_SIDE2 = -10;
    public static final byte KEY_MOUSE_WHEEL_DOWN = -5;
    public static final byte KEY_MOUSE_WHEEL_UP = -6;
    public static final byte KEY_N = 17;
    public static final byte KEY_NUMLOCK = 83;
    public static final byte KEY_O = 18;
    public static final byte KEY_P = 19;
    public static final byte KEY_PAGEDOWN = 78;
    public static final byte KEY_PAGEUP = 75;
    public static final byte KEY_PAUSE = 72;
    public static final byte KEY_POWER = 102;
    public static final byte KEY_Q = 20;
    public static final byte KEY_R = 21;
    public static final byte KEY_RIGHT = 79;
    public static final byte KEY_RIGHTBRACE = 48;
    public static final byte KEY_S = 22;
    public static final byte KEY_SCROLLLOCK = 71;
    public static final byte KEY_SEMICOLON = 51;
    public static final byte KEY_SHIFT_L = -31;
    public static final byte KEY_SHIFT_R = -27;
    public static final byte KEY_SLASH = 56;
    public static final byte KEY_SPACE = 44;
    public static final byte KEY_SYSRQ = 70;
    public static final byte KEY_T = 23;
    public static final byte KEY_TAB = 43;
    public static final int KEY_TYPE_1KEY = 0;
    public static final int KEY_TYPE_COMBO_NUM = 1;
    public static final int KEY_TYPE_MACRO = 4;
    public static final int KEY_TYPE_MOUSE_KEY = 3;
    public static final int KEY_TYPE_MULTI_COL_COMBO_NUM = 2;
    public static final int KEY_TYPE_SLIDE = 5;
    public static final byte KEY_U = 24;
    public static final byte KEY_UDLR = -9;
    public static final byte KEY_UP = 82;
    public static final byte KEY_V = 25;
    public static final byte KEY_W = 26;
    public static final byte KEY_WASD = -12;
    public static final byte KEY_X = 27;
    public static final byte KEY_Y = 28;
    public static final byte KEY_Z = 29;
    public static final byte STATUS_BIT_ANDROID_NO_MOUSE = 2;
    public static final byte STATUS_BIT_DISABLE_BLE_FREQ_CALI = 6;
    public static final byte STATUS_BIT_INTEL_GUN_DOWN = 4;
    public static final byte STATUS_BIT_IOS_SUPPORT_CURSOR = 1;
    public static final byte STATUS_BIT_MTK_MODE = 7;
    public static final byte STATUS_BIT_SYSTEM = 0;
    public static final byte STATUS_BIT_VIRTUAL_CURSOR = 5;
    public static final byte STATUS_BIT_WASD_DEFAULT_RUN = 3;
    public static final byte STATUS_EXT_BIT_BEIJING_HOLD_MODE = 3;
    public static final byte STATUS_EXT_BIT_QE_OPEN_SCOPE = 4;
    public static final byte STATUS_EXT_BIT_SPACE_REPEAT = 2;
    public static final byte STATUS_EXT_BIT_TAB_MAP_AUTO_CURSOR = 0;
    public static final byte STATUS_EXT_BIT_TOUPING = 6;
    public static final byte STATUS_EXT_BIT_USB_MODE = 1;
    public static final String URL_BAN_ID = "http://106.12.213.175:8080/m4/banID.json";
    public static final String URL_FIRMWARE_M1 = "http://106.12.213.175:8080/gamer_firmware_m1s/";
    public static final String URL_FIRMWARE_M16 = "http://106.12.213.175:8080/gamer_firmware_m16/";
    public static final String URL_FIRMWARE_M1_NEW = "http://106.12.213.175:8080/gamer_firmware_m1n/";
    public static final String URL_FIRMWARE_M2 = "http://106.12.213.175:8080/gamer_firmware_m2s/";
    public static final String URL_FIRMWARE_M24 = "http://106.12.213.175:8080/gamer_firmware_m24/";
    public static final String URL_FIRMWARE_M2_NEW = "http://106.12.213.175:8080/gamer_firmware_m2n/";
    public static final String URL_FIRMWARE_M3 = "http://106.12.213.175:8080/gamer_firmware_m3/";
    public static final String URL_FIRMWARE_M4 = "http://106.12.213.175:8080/gamer_firmware_m4/";
    public static final String URL_FIRMWARE_MK12 = "http://106.12.213.175:8080/gamer_firmware_mk12/";
    public static final String URL_NOTICE_NEW = "http://106.12.213.175:8080/m4/info.json";
    public static final String URL_UPDATE = "http://106.12.213.175:8080/update.json";
    public static final String URL_VIDEO_1ST_GUN = "https://www.bilibili.com/video/BV1ku4y137jA";
    public static final String URL_VIDEO_AI_GUN_PRESS = "https://www.bilibili.com/video/BV1iP4y1P7X5";
    public static final String URL_VIDEO_ANDROID = "https://www.bilibili.com/video/BV1FV411U74f";
    public static final String URL_VIDEO_ANDROID_M16 = "https://www.bilibili.com/video/BV1fS4y1b7Sh";
    public static final String URL_VIDEO_ANDROID_M3 = "https://www.bilibili.com/video/BV1jq4y137t9";
    public static final String URL_VIDEO_APP = "https://www.bilibili.com/video/BV1bE411e7SK";
    public static final String URL_VIDEO_AUTO_LR = "https://www.bilibili.com/video/BV1FC4y1X7kK";
    public static final String URL_VIDEO_CLOSE_GUN_PRESS = "https://v.douyin.com/ircAwpPd";
    public static final String URL_VIDEO_DYNAMIC_GUN_PRESS = "https://www.bilibili.com/video/BV1vv4y1w7Yj";
    public static final String URL_VIDEO_HOUYAO = "https://www.bilibili.com/video/BV11w411b7Cd";
    public static final String URL_VIDEO_QIANLIYAN = "https://www.bilibili.com/video/BV1nc411y73C";
    public static final String URL_VIDEO_SENSE_Y = "https://www.bilibili.com/video/BV16b4y1L7iG";
    public static final String URL_VIDEO_SHUNFENGER = "https://www.bilibili.com/video/BV1cw41187YM";
    public static final String URL_VIDEO_SMART_13 = "https://www.bilibili.com/video/BV1Du4y1T7ob";
    public static final String URL_VIDEO_SMART_QE = "https://www.bilibili.com/video/BV16C411h75K";
    public static final String URL_VIDEO_TOUPING = "https://www.bilibili.com/video/BV18R4y1478n";
    public static String m_strAiResBagFinished;
    public static String m_strAiResFall;
    public static String m_strAiResInCar;
    public static String m_strAiResNoScope;
    public static String m_strAiResScopeOff;
    public static String m_strAiResScopeOn;
    public static String m_strAiResSqut;
    public static String m_strAiResStand;
    public static final Map<String, Byte> m_mapPartsNameToID = new HashMap<String, Byte>() { // from class: com.baidu.kwgames.Constants.1
        {
            put("1-buchang", (byte) 0);
            put("2-xiaoyin", (byte) 1);
            put("3-xiaoyan", (byte) 2);
            put("4-jubuchang", (byte) 3);
            put("5-juxiaoyan", (byte) 4);
            put("6-cfbuchang", (byte) 5);
            put("7-cfxiaoyin", (byte) 6);
            put("8-cfxiaoyan", (byte) 7);
            put("1-qinxing", (byte) 0);
            put("2-sanjiao", (byte) 1);
            put("3-chuizhi", (byte) 2);
            put("4-jiguang", (byte) 3);
            put("5-banjie", (byte) 4);
            put("6-muzhi", (byte) 5);
            put("1-qiangtuo", (byte) 0);
            put("2-juqiangtuo", (byte) 1);
            put("3-uzi", (byte) 2);
        }
    };
    public static final String[] m_arrGunHeadName = new String[8];
    public static final String[] m_arrGunHandleName = new String[6];
    public static final String[] m_arrGunTailName = new String[3];
    public static final CScopeName[] m_arrAIScope = {new CScopeName("scope", NEAT.s(R.string.aiming)), new CScopeName("red-dot", NEAT.s(R.string.red_dot)), new CScopeName("holo", NEAT.s(R.string.holographic)), new CScopeName("2x", NEAT.s(R.string.mirror2)), new CScopeName("3x", NEAT.s(R.string.mirror3)), new CScopeName("4x", NEAT.s(R.string.mirror4)), new CScopeName("6x", NEAT.s(R.string.mirror6)), new CScopeName("8x", NEAT.s(R.string.mirror8)), new CScopeName("red-dot2", NEAT.s(R.string.red_dot)), new CScopeName("red-dot3", NEAT.s(R.string.red_dot)), new CScopeName("holo2", NEAT.s(R.string.holographic)), new CScopeName("holo3", NEAT.s(R.string.holographic)), new CScopeName("scope2", NEAT.s(R.string.aiming)), new CScopeName("scope3", NEAT.s(R.string.aiming)), new CScopeName("zs", NEAT.s(R.string.scope_zs)), new CScopeName("2x2", NEAT.s(R.string.mirror2)), new CScopeName("3x2", NEAT.s(R.string.mirror3))};
    public static final Map<Integer, Byte> m_mapAIId2BleID = new HashMap<Integer, Byte>() { // from class: com.baidu.kwgames.Constants.2
        {
            put(0, (byte) 1);
            put(1, (byte) 1);
            put(2, (byte) 1);
            put(3, (byte) 2);
            put(4, (byte) 3);
            put(5, (byte) 4);
            put(6, (byte) 5);
            put(7, (byte) 6);
            put(8, (byte) 1);
            put(9, (byte) 1);
            put(10, (byte) 1);
            put(11, (byte) 1);
            put(12, (byte) 1);
            put(13, (byte) 1);
            put(14, (byte) 6);
            put(15, (byte) 2);
            put(16, (byte) 3);
        }
    };
    public static final int[] s_arrCrosshairImage = {0, R.mipmap.aimwhite, R.mipmap.aimred, R.mipmap.aim, R.mipmap.aimyellow, R.mipmap.aim_red1, R.mipmap.aim_red2, R.mipmap.aim_red3, R.mipmap.aim_circle, R.mipmap.aim_bracket, R.mipmap.aim_bracket2};
    public static final int[] s_arrCrosshairBtnBG = {R.mipmap.btnaimoff, R.mipmap.btnaimwhite, R.mipmap.btnaimred, R.mipmap.btnaimgreen, R.mipmap.btnaimyellow, R.mipmap.btnaim_red1, R.mipmap.btnaim_red2, R.mipmap.btnaim_red3, R.mipmap.btnaim_circle, R.mipmap.btnaim_bracket, R.mipmap.btnaim_bracket2};
    public static GunInfo[] g_arrGunInfo = new GunInfo[66];
    private static final long[] s_arrGunsNeedCrosshair = {0, 3, 9, 14, 18, 19, 21, 22, 27, 28, 32, 35, 38, 39, 41, 45, 46, 50, 52, 53, 54, 59, 62};
    private static final long[] s_arrGunsScopeOnNeedCrosshair = {21, 24, 25, 36, 43};
    private static final long[] s_arrGunsScopeOnNoNeedCrosshair = {31, 44, 61};
    private static long m_lGunNeedCrosshair = 0;
    private static long m_lGunScopeOnNeedCrosshair = 0;
    private static long m_lGunScopeOnNoNeedCrosshair = 0;
    public static Map<Byte, String> ID2String = new HashMap<Byte, String>() { // from class: com.baidu.kwgames.Constants.3
        {
            put((byte) 4, "A");
            put((byte) 5, "B");
            put((byte) 6, "C");
            put((byte) 7, "D");
            put((byte) 8, "E");
            put((byte) 9, "F");
            put((byte) 10, "G");
            put((byte) 11, "H");
            put((byte) 12, "I");
            put((byte) 13, "J");
            put((byte) 14, "K");
            put((byte) 15, "L");
            put((byte) 16, "M");
            put((byte) 17, "N");
            put((byte) 18, "O");
            put((byte) 19, "P");
            put((byte) 20, "Q");
            put((byte) 21, "R");
            put((byte) 22, "S");
            put((byte) 23, "T");
            put((byte) 24, "U");
            put((byte) 25, "V");
            put((byte) 26, "W");
            put((byte) 27, "X");
            put((byte) 28, "Y");
            put((byte) 29, "Z");
            put((byte) 30, DiskLruCache.VERSION_1);
            put((byte) 31, "2");
            put((byte) 32, "3");
            put((byte) 33, "4");
            put((byte) 34, "5");
            put((byte) 35, "6");
            put((byte) 36, "7");
            put((byte) 37, "8");
            put((byte) 38, "9");
            put((byte) 39, "0");
            put((byte) 40, "Enter");
            put((byte) 41, "Esc");
            put((byte) 42, "Back");
            put((byte) 43, "Tab");
            put((byte) 44, "Space");
            put((byte) 45, "-");
            put((byte) 46, "=");
            put((byte) 47, "[");
            put((byte) 48, "]");
            put((byte) 49, "\\");
            put((byte) 50, "#");
            put((byte) 51, ";");
            put((byte) 52, "'");
            put((byte) 53, "`");
            put((byte) 54, ",");
            put((byte) 55, ".");
            put((byte) 56, "/");
            put((byte) 57, "Caps");
            put((byte) 58, "F1");
            put((byte) 59, "F2");
            put((byte) 60, "F3");
            put((byte) 61, "F4");
            put((byte) 62, "F5");
            put((byte) 63, "F6");
            put((byte) 64, "F7");
            put((byte) 65, "F8");
            put((byte) 66, "F9");
            put(Byte.valueOf((byte) Constants.KEY_F10), "F10");
            put(Byte.valueOf((byte) Constants.KEY_F11), "F11");
            put(Byte.valueOf((byte) Constants.KEY_F12), "F12");
            put(Byte.valueOf((byte) Constants.KEY_SYSRQ), "PS");
            put(Byte.valueOf((byte) Constants.KEY_SCROLLLOCK), "Lock");
            put(Byte.valueOf((byte) Constants.KEY_PAUSE), "Pause");
            put(Byte.valueOf((byte) Constants.KEY_INSERT), "Ins");
            put(Byte.valueOf((byte) Constants.KEY_HOME), "Home");
            put(Byte.valueOf((byte) Constants.KEY_PAGEUP), "PgUp");
            put(Byte.valueOf((byte) Constants.KEY_DELETE), "Del");
            put(Byte.valueOf((byte) Constants.KEY_END), "End");
            put(Byte.valueOf((byte) Constants.KEY_PAGEDOWN), "PgDn");
            put(Byte.valueOf((byte) Constants.KEY_RIGHT), "→");
            put(Byte.valueOf((byte) Constants.KEY_LEFT), "←");
            put(Byte.valueOf((byte) Constants.KEY_DOWN), "↓");
            put(Byte.valueOf((byte) Constants.KEY_UP), "↑");
            put(Byte.valueOf((byte) Constants.KEY_NUMLOCK), "Num");
            put(Byte.valueOf((byte) Constants.KEY_KPSLASH), "Num/");
            put(Byte.valueOf((byte) Constants.KEY_KPASTERISK), "Num*");
            put(Byte.valueOf((byte) Constants.KEY_KPMINUS), "Num-");
            put(Byte.valueOf((byte) Constants.KEY_KPPLUS), "Num+");
            put(Byte.valueOf((byte) Constants.KEY_KPENTER), "Ent");
            put(Byte.valueOf((byte) Constants.KEY_KP1), "Num1");
            put(Byte.valueOf((byte) Constants.KEY_KP2), "Num2");
            put(Byte.valueOf((byte) Constants.KEY_KP3), "Num3");
            put(Byte.valueOf((byte) Constants.KEY_KP4), "Num4");
            put(Byte.valueOf((byte) Constants.KEY_KP5), "Num5");
            put(Byte.valueOf((byte) Constants.KEY_KP6), "Num6");
            put(Byte.valueOf((byte) Constants.KEY_KP7), "Num7");
            put(Byte.valueOf((byte) Constants.KEY_KP8), "Num8");
            put(Byte.valueOf((byte) Constants.KEY_KP9), "Num9");
            put(Byte.valueOf((byte) Constants.KEY_KP0), "Num0");
            put(Byte.valueOf((byte) Constants.KEY_KPDOT), "Num.");
            put(Byte.valueOf((byte) Constants.KEY_102ND), "Num\\");
            put(Byte.valueOf((byte) Constants.KEY_COMPOSE), "App");
            put(Byte.valueOf((byte) Constants.KEY_POWER), "Power");
            put(Byte.valueOf((byte) Constants.KEY_KPEQUAL), "=");
            put(Byte.valueOf((byte) Constants.KEY_SHIFT_L), "Shift");
            put(Byte.valueOf((byte) Constants.KEY_SHIFT_R), "ShiftR");
            put(Byte.valueOf((byte) Constants.KEY_ALT_L), "Alt");
            put(Byte.valueOf((byte) Constants.KEY_ALT_R), "AltR");
            put(Byte.valueOf((byte) Constants.KEY_CTRL_L), "Ctrl");
            put(Byte.valueOf((byte) Constants.KEY_CTRL_R), "CtrlR");
            put(Byte.valueOf((byte) Constants.KEY_MOBA_CTRL_Q), "Ctrl+Q");
            put(Byte.valueOf((byte) Constants.KEY_MOBA_CTRL_W), "Ctrl+W");
            put(Byte.valueOf((byte) Constants.KEY_MOBA_CTRL_E), "Ctrl+E");
            put(Byte.valueOf((byte) Constants.KEY_MOBA_CTRL_R), "Ctrl+R");
            put(Byte.valueOf((byte) Constants.KEY_MOUSE_L), "MouseL");
            put(Byte.valueOf((byte) Constants.KEY_MOUSE_M), "MouseM");
            put(Byte.valueOf((byte) Constants.KEY_MOUSE_R), "MouseR");
            put(Byte.valueOf((byte) Constants.KEY_MOUSE), "Mouse");
            put(Byte.valueOf((byte) Constants.KEY_WASD), "WASD");
            put(Byte.valueOf((byte) Constants.KEY_MOUSE_SIDE1), "MSide1");
            put((byte) -10, "MSide2");
            put((byte) -9, "↑↓←→");
            put((byte) -8, "ScopEx");
            put((byte) -7, "ScopZoom");
            put((byte) -6, "Wheel↑");
            put((byte) -5, "Wheel↓");
        }
    };
    public static final Map<Integer, ModelInfo> m_mapModels = new HashMap<Integer, ModelInfo>() { // from class: com.baidu.kwgames.Constants.4
        {
            put(2, new ModelInfo(8, false, false, true, false, false, false));
            put(3, new ModelInfo(8, false, false, true, false, false, false));
            put(8, new ModelInfo(8, true, true, true, true, true, true));
            put(9, new ModelInfo(8, true, true, false, false, true, false));
            put(10, new ModelInfo(8, true, true, true, false, true, false));
            put(11, new ModelInfo(8, true, false, false, false, false, false));
            put(12, new ModelInfo(8, true, true, true, false, false, false));
            put(13, new ModelInfo(8, true, false, false, false, false, false));
            put(14, new ModelInfo(8, true, true, true, false, true, false));
            put(15, new ModelInfo(8, true, true, false, false, true, false));
            put(16, new ModelInfo(8, true, true, false, false, true, false));
            put(17, new ModelInfo(8, true, true, true, true, false, true));
            put(18, new ModelInfo(8, true, true, true, true, true, true));
            put(19, new ModelInfo(8, true, true, true, true, false, true));
            put(20, new ModelInfo(8, true, true, false, false, false, false));
            put(21, new ModelInfo(8, true, true, false, false, false, false));
            put(22, new ModelInfo(8, true, true, false, false, true, false));
            put(23, new ModelInfo(8, true, true, true, false, true, true));
            put(24, new ModelInfo(8, true, true, true, false, true, true));
            put(25, new ModelInfo(8, true, true, true, true, true, true));
            put(26, new ModelInfo(8, true, true, true, true, true, true));
        }
    };
    private static Set<String> m_setOPPOImmersionMode = new HashSet<String>() { // from class: com.baidu.kwgames.Constants.5
        {
            add("PEQM00");
        }
    };
    private static final String[] m_setToupingModePhones = {"TAH-AN00", "TAH-AN00M", "WGR-W09", "WGR-W19", "WGRR-W09", "XYAO-W00", "WGRR-W19", "SCMR-W09", "GOT-W09", "PCE-W30", "LENOVO TB-J60", "LENOVO TB-J716F", "LENOVO YT-K60", "PEUM00", "PGU110"};
    public static final AIKit[] s_arrAIKits = {new AIKit("/AIGunDown.bin", "/AIGunStage2.bin", "/AIGunParts2.bin", "configData64", "PUBG", "PUBG", "/AICross0.bin", R.mipmap.mirror, R.mipmap.mirror, "/AIXScope0.bin", "/AIActiveRun0.bin", "/AIStageM41", R.mipmap.takeoff_ch), new AIKit("/AIGunDown21.bin", "/AIGunStage21.bin", "/AIGunParts21.bin", "configData21", "PUBGG-CH", "PUBGG-EN", "/AICross1.bin", R.mipmap.pubg2xch, R.mipmap.pubg2xen, "/AIXScope1.bin", "/AIActiveRun1.bin", "/AIStageM42", R.mipmap.takeoff), new AIKit("/AIGunDown.bin", "/AIGunStage2.bin", "/AIGunParts2.bin", "configData65", "PUBG", "PUBG", "/AICross0.bin", R.mipmap.mirror, R.mipmap.mirror, "/AIXScope0.bin", "/AIActiveRun0.bin", "/AIStageM41", R.mipmap.takeoff_ch), new AIKit("/AIGunDown21.bin", "/AIGunStage21.bin", "/AIGunParts21.bin", "configData21", "PUBGG-CH", "PUBGG-TU", "/AICross1.bin", R.mipmap.pubg2xch, R.mipmap.pubg2xtu, "/AIXScope1.bin", "/AIActiveRun1.bin", "/AIStageM42", R.mipmap.takeoff), new AIKit("/AIGunDown21.bin", "/AIGunStage21.bin", "/AIGunParts21.bin", "configData21", "PUBGG-CH", "PUBGG-VN", "/AICross1.bin", R.mipmap.pubg2xch, R.mipmap.pubg2xvn, "/AIXScope1.bin", "/AIActiveRun1.bin", "/AIStageM42", R.mipmap.takeoff)};
    public static final Map<Integer, SModelStr> m_mapModelStr = new HashMap<Integer, SModelStr>() { // from class: com.baidu.kwgames.Constants.6
    };
    public static final Map<String, String> guideAssets = new HashMap<String, String>() { // from class: com.baidu.kwgames.Constants.7
        {
            put(Constants.E_GAME_NAME_HPJY, DiskLruCache.VERSION_1);
            put(Constants.E_GAME_NAME_CF, "2");
            put(Constants.E_GAME_NAME_MRZH, "mrzh");
            put(Constants.E_GAME_NAME_LOLM, "lol");
            put(Constants.E_GAME_NAME_CODM, "smzh");
            put(Constants.E_GAME_NAME_WMCQ, "wmcq");
            put(Constants.E_GAME_NAME_APEX, Constants.E_GAME_NAME_APEX);
            put(Constants.E_GAME_NAME_AQTW, "aqtw");
        }
    };
    public static final Map<String, String> s_mapGameVideo = new HashMap<String, String>() { // from class: com.baidu.kwgames.Constants.8
        {
            put(Constants.E_GAME_NAME_HPJY, Constants.URL_VIDEO_ANDROID);
            put(Constants.E_GAME_NAME_CF, "https://www.bilibili.com/video/BV1DJ41117Az");
            put(Constants.E_GAME_NAME_MRZH, "https://www.bilibili.com/video/BV1Nh411R7na");
            put(Constants.E_GAME_NAME_LOLM, "https://www.bilibili.com/video/BV1nL411G7gV");
            put("com.tencent.tmgp.sgame", "https://www.bilibili.com/video/BV1nL411G7gV");
            put(Constants.E_GAME_NAME_CODM, "https://www.bilibili.com/video/BV1wv411t7tq");
            put("E_GAME_NAME_CODMG", "https://www.youtube.com/watch?v=1dxXyqaxnEs&list=PLjzD6BHCrB9itnMcY-PuIcf2x9Iv51UgI");
            put(Constants.E_GAME_NAME_WMCQ, "https://www.bilibili.com/video/BV1kC4y1t7u7/");
            put(Constants.E_GAME_NAME_APEX, "https://www.bilibili.com/video/BV1cU4y1U7ys/");
            put("com.netease.mc", "https://www.bilibili.com/video/BV1QA411J7Lw/");
            put(Constants.E_GAME_NAME_MINECRAFT, "https://www.bilibili.com/video/BV1QA411J7Lw/");
            put("com.sofunny.sausage", "https://www.bilibili.com/video/BV1gS4y1b7TJ/");
            put("com.njsr.xcpkpd", "https://www.bilibili.com/video/BV1gS4y1b7TJ/");
            put("com.bairimeng.dmmdzz", "https://www.bilibili.com/video/BV1DB4y1C74K/");
            put("com.tencent.tmgp.dwrg", "https://www.bilibili.com/video/BV1x34y1a7zV/");
            put("com.pubg.newstate", "https://www.bilibili.com/video/BV1A34y1e7RF/");
            put("com.ztgame.bob", "https://www.bilibili.com/video/BV1US4y1h7Xi/");
            put("com.mihoyo.yuanshen", "https://www.bilibili.com/video/BV1Af4y1M7cQ/");
            put("com.netease.sheltergp", "https://www.bilibili.com/video/BV15b4y1a75i/");
            put("com.tencent.kihan", "https://www.bilibili.com/video/BV1zT4y1R7KS/");
            put("com.netease.wotb", "https://www.bilibili.com/video/BV1w44y1x7Cg/");
            put("com.tencent.tmgp.wepop", "https://www.bilibili.com/video/BV1E54y1p7kh/");
            put("com.tencent.tmgp.speedmobile", "https://www.bilibili.com/video/BV1E54y1p7kh/");
            put(Constants.E_GAME_NAME_HYXD, "https://www.bilibili.com/video/bv1QY411u7t3");
            put(Constants.E_GAME_NAME_AQTW, "https://www.bilibili.com/video/BV1ga411s7Qm");
            put("com.wooduan.ssjj", "https://www.bilibili.com/video/BV1qt4y147Rd");
            put(Constants.E_GAME_NAME_XQCQ, "https://www.bilibili.com/video/BV1vH4y127mh");
            put(Constants.E_GAME_NAME_GNYX, "https://www.bilibili.com/video/BV1tw411v78a");
            put(Constants.E_GAME_NAME_JINGHE, "https://www.bilibili.com/video/BV1Zr4y1X7Dm");
            put(Constants.E_GAME_NAME_WJXD, "https://www.bilibili.com/video/BV1mu411A7o9");
            put(Constants.E_GAME_NAME_NSH, "https://www.bilibili.com/video/BV1A14y1d7Mi");
            put(Constants.E_GAME_NAME_LMJX, "https://www.bilibili.com/video/BV1TM4y1d71A");
            put(Constants.E_GAME_NAME_PUBG, "https://youtu.be/TIny_gjRVU4");
            put(Constants.E_GAME_NAME_YHTJ, "https://www.bilibili.com/video/BV1d64y1A7LC");
            put(Constants.E_GAME_NAME_DFJS, "https://www.bilibili.com/video/BV1sb4y1N7G1");
            put(Constants.E_GAME_NAME_YMZX, "https://www.bilibili.com/video/BV1Mi4y1s7vg");
            put(Constants.E_GAME_NAME_TIANQI, "https://www.bilibili.com/video/BV1RC411n7Wf");
            put(Constants.E_GAME_NAME_WPZS2, "https://www.bilibili.com/video/BV1J4421U7ag");
            put(Constants.E_GAME_NAME_JQ0, "https://www.bilibili.com/video/BV1jS411w7RH");
            put(Constants.E_GAME_FREE_FIRE, "https://www.youtube.com/watch?v=5AoZ41mQvSI");
            put(Constants.E_GAME_YONGJIEWUJIAN, "https://www.bilibili.com/video/BV14x4y147VF");
            put(Constants.E_GAME_SANJIAOZHOU, "https://www.bilibili.com/video/BV1w1xEejENy");
            put(Constants.E_GAME_YANYUN, "https://www.bilibili.com/video/BV1bucJeaExE");
            put(Constants.E_GAME_ALBION, "https://www.bilibili.com/video/BV13oc2eLEBP");
        }
    };
    public static final String E_GAME_NAME_CF = "com.tencent.tmgp.cf";
    public static final String E_GAME_NAME_CODM = "com.tencent.tmgp.cod";
    public static final String E_GAME_NAME_CODMG = "com.activision.callofduty";
    public static final String E_GAME_NAME_APEX = "apex";
    public static final String E_GAME_NAME_LOLM = "com.tencent.lolm";
    public static final String E_GAME_NAME_AQTW = "com.tencent.mf.uam";
    public static final String E_GAME_NAME_XQCQ = "com.hermes.j1game";
    public static final String E_GAME_NAME_GNYX = "com.tencent.tmgp.gnyx";
    public static final String E_GAME_NAME_JINGHE = "com.hermes.p6game.aligames";
    public static final String E_GAME_NAME_NSH = "com.netease.nshm";
    public static final String E_GAME_NAME_LMJX = "com.tencent.toaa";
    public static final String E_GAME_NAME_WJXD = "cc.ccplay.com.gamedevltd.destinywarfare";
    public static final String E_GAME_NAME_YHTJ = "com.netease.yhtj";
    public static final String E_GAME_NAME_DFJS = "com.netease.race";
    public static final String E_GAME_NAME_YMZX = "com.tencent.letsgo";
    public static final String E_GAME_NAME_TIANQI = "com.sssgame.ch";
    public static final String E_GAME_NAME_WPZS2 = "com.tencent.nbn";
    public static final String E_GAME_NAME_JQ0 = "com.hoyoverse.nap";
    public static final String E_GAME_FREE_FIRE = "com.dts.freefire";
    public static final String E_GAME_MAOXIANDAO = "com.tencent.tmgp.maplem";
    public static final String E_GAME_YONGJIEWUJIAN = "com.netease.l22";
    public static final String E_GAME_SANJIAOZHOU = "com.tencent.tmgp.dfm";
    public static final String[] s_arrGameNeedVMouse = {E_GAME_NAME_CF, E_GAME_NAME_CODM, E_GAME_NAME_CODMG, E_GAME_NAME_APEX, E_GAME_NAME_LOLM, "com.sofunny.sausage", "com.njsr.xcpkpd", "com.pubg.newstate", "com.ztgame.bob", "com.tencent.kihan", E_GAME_NAME_AQTW, E_GAME_NAME_XQCQ, E_GAME_NAME_GNYX, E_GAME_NAME_JINGHE, E_GAME_NAME_NSH, E_GAME_NAME_LMJX, E_GAME_NAME_WJXD, E_GAME_NAME_YHTJ, E_GAME_NAME_DFJS, E_GAME_NAME_YMZX, E_GAME_NAME_TIANQI, E_GAME_NAME_WPZS2, E_GAME_NAME_JQ0, E_GAME_FREE_FIRE, E_GAME_MAOXIANDAO, E_GAME_YONGJIEWUJIAN, E_GAME_SANJIAOZHOU};
    public static final String[] s_arrGameNeedNoGunPress = {E_GAME_NAME_LOLM, "com.ztgame.bob", "com.tencent.kihan", E_GAME_NAME_NSH, E_GAME_NAME_DFJS, E_GAME_MAOXIANDAO, E_GAME_YONGJIEWUJIAN};
    public static final String E_GAME_NAME_HPJY = "com.tencent.tmgp.pubgmhd";
    public static final String E_GAME_NAME_PUBG = "com.tencent.ig";
    public static final String E_GAME_NAME_MRZH = "com.netease.mrzh";
    public static final String E_GAME_NAME_HYXD = "com.netease.hyxd";
    public static final String E_GAME_COMBAT_MASTER = "combatmaster";
    public static final SGame2NameID[] s_arrGameName2ID = {new SGame2NameID(E_GAME_NAME_HPJY, "和平精英", 128, 128, 128, 128), new SGame2NameID(E_GAME_NAME_PUBG, "PUBG", 128, 128, 128, 128), new SGame2NameID(E_GAME_NAME_CF, "穿越火线", 129, 128, 128, 128), new SGame2NameID(E_GAME_NAME_MRZH, "明日之后", 130, 128, 128, 128), new SGame2NameID(E_GAME_NAME_AQTW, "暗区突围", 131, 128, 128, 129), new SGame2NameID(E_GAME_NAME_NSH, "逆水寒", 132, 132, 128, 128), new SGame2NameID(E_GAME_NAME_HYXD, "荒野行动", 133, 128, 128, 128), new SGame2NameID(E_GAME_NAME_XQCQ, "星球：重启", 133, 132, 128, 128), new SGame2NameID(E_GAME_COMBAT_MASTER, "COMBATMASTER", 133, 136, 128, 128), new SGame2NameID(E_GAME_FREE_FIRE, "Free Fire", 134, 128, 162, 129), new SGame2NameID(E_GAME_MAOXIANDAO, "冒险岛", 131, 128, 128, 129), new SGame2NameID(E_GAME_YONGJIEWUJIAN, "永劫无间", 135, 131, 128, 128), new SGame2NameID(E_GAME_NAME_CODM, "使命召唤", 136, 128, 128, 128), new SGame2NameID(E_GAME_NAME_CODMG, "Call of Duty", 136, 128, 128, 128), new SGame2NameID(E_GAME_SANJIAOZHOU, "三角洲行动", 137, 128, 128, 131)};
    public static final Map<Byte, String> keyDesc = new HashMap<Byte, String>() { // from class: com.baidu.kwgames.Constants.9
        {
            put((byte) 9, AppInstance.get().getString(R.string.KEY_F_DESC));
            put((byte) 21, AppInstance.get().getString(R.string.KEY_R_DESC));
            put((byte) 27, AppInstance.get().getString(R.string.KEY_X_DESC));
            put((byte) 29, AppInstance.get().getString(R.string.KEY_Z_DESC));
            put((byte) 30, AppInstance.get().getString(R.string.KEY_1_DESC));
            put((byte) 31, AppInstance.get().getString(R.string.KEY_2_DESC));
            put((byte) 43, AppInstance.get().getString(R.string.KEY_TAB_DESC));
            put(Byte.valueOf((byte) Constants.KEY_MOUSE_M), AppInstance.get().getString(R.string.KEY_MOUSE_M_DESC));
            put(Byte.valueOf((byte) Constants.KEY_MOUSE_R), AppInstance.get().getString(R.string.KEY_MOUSE_R_DESC));
            put(Byte.valueOf((byte) Constants.KEY_MOUSE), AppInstance.get().getString(R.string.KEY_MOUSE_DESC));
            put(Byte.valueOf((byte) Constants.KEY_WASD), AppInstance.get().getString(R.string.KEY_WASD_DESC));
            put(Byte.valueOf((byte) Constants.KEY_MOUSE_SIDE1), AppInstance.get().getString(R.string.KEY_MOUSE_SIDE1_DESC));
            put((byte) -10, AppInstance.get().getString(R.string.KEY_MOUSE_SIDE2_DESC));
            put((byte) -9, AppInstance.get().getString(R.string.KEY_UDLR_DESC));
            put((byte) -8, AppInstance.get().getString(R.string.KEY_BEIJING_EXCHANGE_DESC));
            put((byte) -7, AppInstance.get().getString(R.string.KEY_BEIJING_SCALE_DESC));
            put((byte) -6, AppInstance.get().getString(R.string.mouse_wheel_up_tip));
            put((byte) -5, AppInstance.get().getString(R.string.mouse_wheel_down_tip));
        }
    };
    public static final char[] g_arrBleNameRandom = {'A', 'E', '1', '5', 'i', 'M', 'Q', 'u', 'B', 'F', '2', '6', 'j', 'N', 'R', 'v', 'C', 'G', '3', '7', 'k', 'O', 'S', 'w', 'D', 'H', '4', '8', 'l', 'P', 'T', 'X'};

    /* loaded from: classes.dex */
    static class CScopeName {
        public String m_strDisplay;
        public String m_strFileName;

        CScopeName(String str, String str2) {
            this.m_strFileName = str;
            this.m_strDisplay = str2;
        }
    }

    public static String get_AI_scope_name(int i) {
        return (i < 0 || i > 17) ? "" : m_arrAIScope[i].m_strDisplay;
    }

    public static int get_AI_scope_ID(String str) {
        int i = 0;
        while (true) {
            CScopeName[] cScopeNameArr = m_arrAIScope;
            if (i >= cScopeNameArr.length) {
                return -1;
            }
            if (cScopeNameArr[i].m_strFileName.equalsIgnoreCase(str)) {
                return i;
            }
            i++;
        }
    }

    public static int scope_AI_id_2_ble_id(int i) {
        Map<Integer, Byte> map = m_mapAIId2BleID;
        if (map.containsKey(Integer.valueOf(i))) {
            return map.get(Integer.valueOf(i)).byteValue();
        }
        return 0;
    }

    /* loaded from: classes.dex */
    public static class GunInfo {
        boolean m_boOnShootGun;
        int m_nScopeAIDisableMask;
        String m_strModel;
        String m_strName;

        GunInfo() {
        }

        GunInfo(String str, String str2, boolean z, int i) {
            this.m_strModel = str;
            this.m_strName = str2;
            this.m_boOnShootGun = z;
            this.m_nScopeAIDisableMask = i;
        }
    }

    public static String get_gun_name(int i) {
        GunInfo[] gunInfoArr = g_arrGunInfo;
        return (i >= gunInfoArr.length || i < 0) ? "" : gunInfoArr[i].m_strName;
    }

    public static boolean is_gun_oneshoot(int i) {
        if (i >= 66 || i < 0) {
            return false;
        }
        return g_arrGunInfo[i].m_boOnShootGun;
    }

    public static int get_gun_ID(String str) {
        for (int i = 0; i < 66; i++) {
            GunInfo[] gunInfoArr = g_arrGunInfo;
            if (gunInfoArr[i] != null && str.equalsIgnoreCase(gunInfoArr[i].m_strModel)) {
                return i;
            }
        }
        return -1;
    }

    public static void init() {
        for (long j : s_arrGunsNeedCrosshair) {
            m_lGunNeedCrosshair = (1 << ((int) j)) | m_lGunNeedCrosshair;
        }
        for (long j2 : s_arrGunsScopeOnNeedCrosshair) {
            m_lGunScopeOnNeedCrosshair = (1 << ((int) j2)) | m_lGunScopeOnNeedCrosshair;
        }
        for (long j3 : s_arrGunsScopeOnNoNeedCrosshair) {
            m_lGunScopeOnNoNeedCrosshair = (1 << ((int) j3)) | m_lGunScopeOnNoNeedCrosshair;
        }
        String[] strArr = m_arrGunHeadName;
        strArr[0] = AppInstance.s_context.getString(R.string.buchang);
        strArr[1] = AppInstance.s_context.getString(R.string.xiaoyin);
        strArr[2] = AppInstance.s_context.getString(R.string.xiaoyan);
        strArr[3] = AppInstance.s_context.getString(R.string.buchang);
        strArr[4] = AppInstance.s_context.getString(R.string.xiaoyan);
        strArr[5] = AppInstance.s_context.getString(R.string.buchang);
        strArr[6] = AppInstance.s_context.getString(R.string.xiaoyin);
        strArr[7] = AppInstance.s_context.getString(R.string.xiaoyan);
        String[] strArr2 = m_arrGunHandleName;
        strArr2[0] = AppInstance.s_context.getString(R.string.qinxing);
        strArr2[1] = AppInstance.s_context.getString(R.string.sanjiao);
        strArr2[2] = AppInstance.s_context.getString(R.string.chuizhi);
        strArr2[3] = AppInstance.s_context.getString(R.string.jiguang);
        strArr2[4] = AppInstance.s_context.getString(R.string.banjie);
        strArr2[5] = AppInstance.s_context.getString(R.string.muzhi);
        String[] strArr3 = m_arrGunTailName;
        strArr3[0] = AppInstance.s_context.getString(R.string.qiangtuo);
        strArr3[1] = AppInstance.s_context.getString(R.string.qiangtuo);
        strArr3[2] = AppInstance.s_context.getString(R.string.qiangtuo);
        m_strAiResStand = AppInstance.s_context.getString(R.string.ai_res_stand);
        m_strAiResSqut = AppInstance.s_context.getString(R.string.ai_res_squt);
        m_strAiResFall = AppInstance.s_context.getString(R.string.ai_res_fall);
        m_strAiResScopeOn = AppInstance.s_context.getString(R.string.ai_scope_on);
        m_strAiResScopeOff = AppInstance.s_context.getString(R.string.ai_scope_off);
        m_strAiResBagFinished = AppInstance.s_context.getString(R.string.ai_res_bag_finished);
        m_strAiResInCar = AppInstance.s_context.getString(R.string.ai_res_in_car);
        m_strAiResNoScope = AppInstance.s_context.getString(R.string.no_scope);
        g_arrGunInfo[0] = new GunInfo("98k", NEAT.s(R.string.gun_98k), false, 0);
        g_arrGunInfo[1] = new GunInfo("AKM", NEAT.s(R.string.gun_akm), false, 128);
        g_arrGunInfo[2] = new GunInfo("AUG", NEAT.s(R.string.gun_aug), false, 128);
        g_arrGunInfo[3] = new GunInfo("AWM", NEAT.s(R.string.gun_awm), false, 0);
        g_arrGunInfo[4] = new GunInfo("DBS", NEAT.s(R.string.gun_dbs), true, 128);
        g_arrGunInfo[5] = new GunInfo("DP28", NEAT.s(R.string.gun_dp28), false, 128);
        g_arrGunInfo[6] = new GunInfo("G36C", NEAT.s(R.string.gun_g36c), false, 128);
        g_arrGunInfo[7] = new GunInfo("GROZA", NEAT.s(R.string.gun_groza), false, 128);
        g_arrGunInfo[8] = new GunInfo("M16A4", NEAT.s(R.string.gun_m16a4), true, 128);
        g_arrGunInfo[9] = new GunInfo("M24", NEAT.s(R.string.gun_m24), false, 0);
        g_arrGunInfo[10] = new GunInfo("M249", NEAT.s(R.string.gun_m249), false, 128);
        g_arrGunInfo[11] = new GunInfo("M416", NEAT.s(R.string.gun_m416), false, 128);
        g_arrGunInfo[12] = new GunInfo("M762", NEAT.s(R.string.gun_m762), false, 128);
        g_arrGunInfo[13] = new GunInfo("MG3", NEAT.s(R.string.gun_mg3), false, 128);
        g_arrGunInfo[14] = new GunInfo("Mini14", NEAT.s(R.string.gun_mini14), true, 0);
        g_arrGunInfo[15] = new GunInfo("MK14", NEAT.s(R.string.gun_mk14), false, 0);
        g_arrGunInfo[16] = new GunInfo("MK47", NEAT.s(R.string.gun_mk47), true, 128);
        g_arrGunInfo[17] = new GunInfo("MP5K", NEAT.s(R.string.gun_mp5k), false, 128);
        g_arrGunInfo[18] = new GunInfo("P18C", NEAT.s(R.string.gun_p18c), true, E_SCOPE_MASK_23468X);
        g_arrGunInfo[19] = new GunInfo("QBU", NEAT.s(R.string.gun_qbu), true, 0);
        g_arrGunInfo[20] = new GunInfo("QBZ", NEAT.s(R.string.gun_qbz), false, 128);
        g_arrGunInfo[21] = new GunInfo("R1895", NEAT.s(R.string.gun_r1895), true, E_SCOPE_MASK_23468X);
        g_arrGunInfo[22] = new GunInfo("R45", NEAT.s(R.string.gun_r45), true, E_SCOPE_MASK_23468X);
        g_arrGunInfo[23] = new GunInfo("S12K", NEAT.s(R.string.gun_s12k), true, 128);
        g_arrGunInfo[24] = new GunInfo("S1897", NEAT.s(R.string.gun_s1897), true, -1);
        g_arrGunInfo[25] = new GunInfo("s686", NEAT.s(R.string.gun_s686), true, -1);
        g_arrGunInfo[26] = new GunInfo("SCAR-L", NEAT.s(R.string.gun_scar), false, 128);
        g_arrGunInfo[27] = new GunInfo("SKS", NEAT.s(R.string.gun_sks), true, 0);
        g_arrGunInfo[28] = new GunInfo("SLR", NEAT.s(R.string.gun_slr), true, 0);
        g_arrGunInfo[29] = new GunInfo("UMP45", NEAT.s(R.string.gun_ump45), false, 128);
        g_arrGunInfo[30] = new GunInfo("UZI", NEAT.s(R.string.gun_uzi), false, E_SCOPE_MASK_23468X);
        g_arrGunInfo[31] = new GunInfo("VSS", NEAT.s(R.string.gun_vss), false, -1);
        g_arrGunInfo[32] = new GunInfo("WIN94", NEAT.s(R.string.gun_win94), true, -1);
        g_arrGunInfo[33] = new GunInfo("nu", NEAT.s(R.string.gun_nu), false, 128);
        g_arrGunInfo[34] = new GunInfo("tomson", NEAT.s(R.string.gun_tomson), false, E_SCOPE_MASK_23468X);
        g_arrGunInfo[35] = new GunInfo("smzy", NEAT.s(R.string.gun_shamozhiying), true, E_SCOPE_MASK_23468X);
        g_arrGunInfo[36] = new GunInfo("dgsd", NEAT.s(R.string.gun_duanguan), true, E_SCOPE_MASK_23468X);
        g_arrGunInfo[37] = new GunInfo("vector", NEAT.s(R.string.gun_victor), false, 128);
        g_arrGunInfo[38] = new GunInfo("mxng", NEAT.s(R.string.gun_moxinnagan), false, 0);
        g_arrGunInfo[39] = new GunInfo("xieshi", NEAT.s(R.string.gun_xieshi), false, E_SCOPE_MASK_23468X);
        g_arrGunInfo[40] = new GunInfo("yeniu", NEAT.s(R.string.gun_yeniu), false, 128);
        g_arrGunInfo[41] = new GunInfo("AMR", NEAT.s(R.string.gun_amr), false, 0);
        g_arrGunInfo[42] = new GunInfo("AC-VAL", NEAT.s(R.string.gun_acval), false, E_SCOPE_MASK_68X);
        g_arrGunInfo[45] = new GunInfo("P1911", NEAT.s(R.string.gun_p1911), true, E_SCOPE_MASK_23468X);
        g_arrGunInfo[46] = new GunInfo("MK12", NEAT.s(R.string.gun_mk12), true, 0);
        g_arrGunInfo[47] = new GunInfo("M417", NEAT.s(R.string.gun_m417), true, 0);
        g_arrGunInfo[64] = new GunInfo("JS9", NEAT.s(R.string.gun_js9), false, 128);
        if (AppInstance.is_hpjy_AI_kit()) {
            g_arrGunInfo[48] = new GunInfo("liegong", NEAT.s(R.string.gun_liegong), false, -1);
            g_arrGunInfo[43] = new GunInfo("SPAS-12", NEAT.s(R.string.gun_spas), true, -1);
            g_arrGunInfo[44] = new GunInfo("P90", NEAT.s(R.string.gun_p90), false, -1);
            g_arrGunInfo[51] = new GunInfo("MK20", NEAT.s(R.string.gun_mk20), false, 0);
            g_arrGunInfo[52] = new GunInfo("ZSNU", NEAT.s(R.string.gun_zsnu), false, 128);
            g_arrGunInfo[53] = new GunInfo("M200", NEAT.s(R.string.gun_m200), false, 0);
            g_arrGunInfo[54] = new GunInfo("PKM", NEAT.s(R.string.gun_pkm), false, 128);
            g_arrGunInfo[55] = new GunInfo("DUN", NEAT.s(R.string.gun_dun), false, 128);
            g_arrGunInfo[56] = new GunInfo("AA12-G", NEAT.s(R.string.gun_aa12), false, E_SCOPE_MASK_23468X);
            g_arrGunInfo[57] = new GunInfo("FAMAS", NEAT.s(R.string.gun_famas), false, 128);
            g_arrGunInfo[58] = new GunInfo("AKS", NEAT.s(R.string.gun_aks), false, 128);
            g_arrGunInfo[59] = new GunInfo("FUHEGONG", NEAT.s(R.string.gun_fuhegong), true, 128);
            g_arrGunInfo[60] = new GunInfo("SVD", NEAT.s(R.string.gun_svd), false, 0);
            g_arrGunInfo[61] = new GunInfo("MG36", NEAT.s(R.string.gun_mg36), false, E_SCOPE_MASK_23468X);
            g_arrGunInfo[62] = new GunInfo("jumang", NEAT.s(R.string.gun_jumang), false, E_SCOPE_MASK_23468X);
            g_arrGunInfo[63] = new GunInfo("ACE32", NEAT.s(R.string.gun_ace32), false, 128);
            g_arrGunInfo[65] = new GunInfo("F57", "F57", false, E_SCOPE_MASK_23468X);
        } else if (AppInstance.is_pubg_AI_kit()) {
            g_arrGunInfo[48] = new GunInfo("liegong", NEAT.s(R.string.gun_famas), false, 128);
            g_arrGunInfo[43] = new GunInfo("SPAS-12", NEAT.s(R.string.gun_m1d14), true, -1);
            g_arrGunInfo[44] = new GunInfo("P90", NEAT.s(R.string.gun_p90), false, -1);
            g_arrGunInfo[53] = new GunInfo("MS200", NEAT.s(R.string.gun_ms200), true, 128);
            g_arrGunInfo[54] = new GunInfo("ACE32", NEAT.s(R.string.gun_ace32), false, 128);
            g_arrGunInfo[55] = new GunInfo("DSR", "DSR", false, 0);
            g_arrGunInfo[56] = new GunInfo("MP7", "MP7", false, -1);
        }
        g_arrGunInfo[49] = new GunInfo("mihuan", NEAT.s(R.string.gun_mihuan), false, 128);
        g_arrGunInfo[50] = new GunInfo("P92", NEAT.s(R.string.gun_p92), false, E_SCOPE_MASK_23468X);
        ID2String.put((byte) -6, NEAT.s(R.string.mouse_wheel_up));
        ID2String.put((byte) -5, NEAT.s(R.string.mouse_wheel_down));
        Map<Integer, SModelStr> map = m_mapModelStr;
        map.put(1, new SModelStr("", NEAT.s(R.string.LOL), NEAT.s(R.string.MINECRAFT)));
        map.put(2, new SModelStr("", NEAT.s(R.string.LOL), NEAT.s(R.string.MINECRAFT)));
        map.put(11, new SModelStr("", NEAT.s(R.string.LOL), NEAT.s(R.string.MINECRAFT)));
        map.put(13, new SModelStr("(NUOS-N1)", "(NUOS-LOL)", NEAT.s(R.string.MINECRAFT)));
        map.put(3, new SModelStr("(M2U)", "(M2U-LOL)", NEAT.s(R.string.M2U_MINECRAFT)));
        map.put(12, new SModelStr("(M2U)", "(M2U-LOL)", NEAT.s(R.string.M2U_MINECRAFT)));
        map.put(8, new SModelStr("(M3)", "(M3-LOL)", NEAT.s(R.string.M3_MINECRAFT)));
        map.put(9, new SModelStr("(M16)", "(M16-LOL)", NEAT.s(R.string.M16_MINECRAFT)));
        map.put(10, new SModelStr("(M24)", "(M24-LOL)", NEAT.s(R.string.M24_MINECRAFT)));
        map.put(14, new SModelStr("(M2S)", "(M2S-LOL)", NEAT.s(R.string.M2S_MINECRAFT)));
        map.put(15, new SModelStr("(MK12)", "(MK12-LOL)", NEAT.s(R.string.mk12_minecraft)));
        map.put(17, new SModelStr("(安全版)", "(安全版-LOL)", "(安全版-Minecraft)"));
        map.put(19, new SModelStr("(安全版)", "(安全版-LOL)", "(安全版-Minecraft)"));
        map.put(18, new SModelStr("(M4)", "(M4-LOL)", NEAT.s(R.string.M4_Minecraft)));
        map.put(20, new SModelStr("(M1_Mini)", "(M1_Mini-LOL)", NEAT.s(R.string.m1mini_mincraft)));
        map.put(21, new SModelStr("(魅影闪灵)", "(魅影闪灵-LOL)", NEAT.s(R.string.shanling_minicraft)));
        map.put(22, new SModelStr("(魅影闪灵Pro)", "(魅影闪灵Pro-LOL)", NEAT.s(R.string.shanlingp_minicraft)));
        map.put(23, new SModelStr("(M2P)", "(M2P-LOL)", "M2P" + NEAT.s(R.string.minecraft)));
        map.put(24, new SModelStr("(MK12 Pro)", "(MK12 Pro-LOL)", "MK12 Pro" + NEAT.s(R.string.minecraft)));
        map.put(25, new SModelStr("(M3 Turkey)", "(M3-LOL)", NEAT.s(R.string.M3_MINECRAFT)));
        map.put(26, new SModelStr("(M4 Turkey)", "(M4-LOL)", NEAT.s(R.string.M4_Minecraft)));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static byte get_gun_ble_ID(byte b) {
        switch (b) {
            case 47:
            case 51:
                return (byte) 15;
            case 48:
                if (!AppInstance.is_hpjy_AI_kit()) {
                    return (byte) 11;
                }
                break;
            case 49:
                return (byte) 7;
            case 50:
                return (byte) 45;
            case 52:
            case 57:
            case 59:
            case 60:
            case 62:
            case 63:
            case 64:
            case 65:
                break;
            case 53:
            case 61:
                return (byte) 9;
            case 54:
            case 56:
                return (byte) 10;
            case 55:
                return (byte) 30;
            case 58:
                return (byte) 29;
            default:
                return b;
        }
        return (byte) 33;
    }

    public static ModelInfo get_model_info(int i) {
        Map<Integer, ModelInfo> map = m_mapModels;
        if (map.containsKey(Integer.valueOf(i))) {
            return map.get(Integer.valueOf(i));
        }
        return null;
    }

    public static boolean is_model_support_AI(int i) {
        Map<Integer, ModelInfo> map = m_mapModels;
        if (map.containsKey(Integer.valueOf(i))) {
            return map.get(Integer.valueOf(i)).m_boSupportAIGunDown;
        }
        return false;
    }

    public static boolean is_oppo_immersion_need_remind(String str) {
        return m_setOPPOImmersionMode.contains(str);
    }

    public static boolean is_touping_phones(String str) {
        for (String str2 : m_setToupingModePhones) {
            if (-1 != str.indexOf(str2)) {
                return true;
            }
        }
        return false;
    }

    /* loaded from: classes.dex */
    public static class SModelStr {
        public String m_strMOBA;
        public String m_strMinecraft;
        public String m_strNormal;

        SModelStr(String str, String str2, String str3) {
            this.m_strNormal = str;
            this.m_strMOBA = str2;
            this.m_strMinecraft = str3;
        }
    }

    public static AIKit get_cur_AI_kit() {
        return s_arrAIKits[AppInstance.s_nAIKit];
    }

    public static String find_guide_for_app(String str) {
        for (Map.Entry<String, String> entry : guideAssets.entrySet()) {
            if (-1 != str.indexOf(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "";
    }

    public static String find_video_for_app(String str) {
        String lowerCase = str == null ? "" : str.toLowerCase();
        for (Map.Entry<String, String> entry : s_mapGameVideo.entrySet()) {
            if (-1 != lowerCase.indexOf(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "";
    }

    public static boolean is_game_need_vmouse(String str) {
        for (String str2 : s_arrGameNeedVMouse) {
            if (-1 != str.indexOf(str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean is_game_no_need_gun_press(String str) {
        for (String str2 : s_arrGameNeedNoGunPress) {
            if (-1 != str.indexOf(str2)) {
                return true;
            }
        }
        return false;
    }

    /* loaded from: classes.dex */
    public static class SGame2NameID {
        public static final int E_EXT_SPACE_NO_RESET_SCOPE = 2;
        public static final int E_EXT_WASD_RESET_MID = 1;
        public static final int E_GAME_AQTW = 3;
        public static final int E_GAME_CF = 1;
        public static final int E_GAME_FREE_FIRE = 6;
        public static final int E_GAME_HYXD = 5;
        public static final int E_GAME_MRZH = 2;
        public static final int E_GAME_PUBG = 0;
        public static final int E_GAME_YSH = 4;
        public int m_nFireDisableViewParam;
        public int m_nGameDevID;
        public int m_nGameParamExt1;
        public int m_nGameViewResetDelay;
        public String m_strAppID;
        public String m_strName;

        SGame2NameID(String str, String str2, int i, int i2, int i3, int i4) {
            this.m_strAppID = str;
            this.m_strName = str2;
            this.m_nGameDevID = i;
            this.m_nGameViewResetDelay = i2;
            this.m_nFireDisableViewParam = i3;
            this.m_nGameParamExt1 = i4;
        }
    }

    public static SGame2NameID get_game_info(String str, String str2) {
        SGame2NameID[] sGame2NameIDArr;
        String lowerCase = str == null ? "" : str.toLowerCase();
        if (str2 == null) {
            str2 = "";
        }
        SGame2NameID sGame2NameID = s_arrGameName2ID[6];
        int i = 0;
        while (true) {
            sGame2NameIDArr = s_arrGameName2ID;
            if (i >= sGame2NameIDArr.length) {
                return sGame2NameID;
            }
            if (-1 != lowerCase.indexOf(sGame2NameIDArr[i].m_strAppID) || str2.equals(sGame2NameIDArr[i].m_strName)) {
                break;
            }
            i++;
        }
        return sGame2NameIDArr[i];
    }
}
