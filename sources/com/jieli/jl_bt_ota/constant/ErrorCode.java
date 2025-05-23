package com.jieli.jl_bt_ota.constant;
/* loaded from: classes2.dex */
public class ErrorCode {
    public static final int ERR_COMMON = 1;
    public static final int ERR_COMMUNICATION = 3;
    public static final int ERR_NONE = 0;
    public static final int ERR_OTA = 4;
    public static final int ERR_OTHER = 5;
    public static final int ERR_STATUS = 2;
    public static final int ERR_UNKNOWN = -1;
    public static final int SUB_ERR_A2DP_CONNECT_FAILED = 4104;
    public static final int SUB_ERR_A2DP_NOT_INIT = 4103;
    public static final int SUB_ERR_AUTH_DEVICE = 20481;
    public static final int SUB_ERR_BLE_NOT_CONNECTED = 4114;
    public static final int SUB_ERR_BLE_NOT_SUPPORT = 4098;
    public static final int SUB_ERR_BLUETOOTH_NOT_ENABLE = 4099;
    public static final int SUB_ERR_BLUETOOTH_UN_PAIR_FAILED = 4102;
    public static final int SUB_ERR_CHANGE_BLE_MTU = 4115;
    public static final int SUB_ERR_CHECK_RECEIVED_DATA_FAILED = 16389;
    public static final int SUB_ERR_CHECK_UPGRADE_FILE = 16387;
    public static final int SUB_ERR_CONNECT_TIMEOUT = 12289;
    public static final int SUB_ERR_DATA_FORMAT = 12292;
    public static final int SUB_ERR_DEVICE_LOW_VOLTAGE = 16386;
    public static final int SUB_ERR_FILE_NOT_FOUND = 20485;
    public static final int SUB_ERR_HEADSET_NOT_IN_CHARGING_BIN = 16398;
    public static final int SUB_ERR_HFP_CONNECT_FAILED = 4106;
    public static final int SUB_ERR_HFP_NOT_INIT = 4105;
    public static final int SUB_ERR_IO_EXCEPTION = 20486;
    public static final int SUB_ERR_NOT_ALLOW_CONNECT = 12297;
    public static final int SUB_ERR_NO_SERVER = 4112;
    public static final int SUB_ERR_OFFSET_OVER = 16388;
    public static final int SUB_ERR_OP_FAILED = 4113;
    public static final int SUB_ERR_OTA_FAILED = 16385;
    public static final int SUB_ERR_OTA_IN_HANDLE = 16392;
    public static final int SUB_ERR_PAIR_TIMEOUT = 12291;
    public static final int SUB_ERR_PARAMETER = 4097;
    public static final int SUB_ERR_PARSE_DATA = 12293;
    public static final int SUB_ERR_RESPONSE_BAD_STATUS = 12296;
    public static final int SUB_ERR_SCAN_DEVICE_FAILED = 8194;
    public static final int SUB_ERR_SEND_FAILED = 12290;
    public static final int SUB_ERR_SEND_TIMEOUT = 12295;
    public static final int SUB_ERR_SPP_WRITE_DATA_FAIL = 12294;
    public static final int SUB_ERR_TWS_NOT_CONNECT = 16397;
    public static final int SUB_ERR_UPGRADE_CMD_TIMEOUT = 16395;
    public static final int SUB_ERR_UPGRADE_DATA_LEN = 16393;
    public static final int SUB_ERR_UPGRADE_FILE_VERSION_SAME = 16396;
    public static final int SUB_ERR_UPGRADE_FLASH_READ = 16394;
    public static final int SUB_ERR_UPGRADE_KEY_NOT_MATCH = 16390;
    public static final int SUB_ERR_UPGRADE_TYPE_NOT_MATCH = 16391;

    public static String code2Msg(int i, int i2) {
        String str;
        if (i != 1) {
            return "unknown error";
        }
        switch (i2) {
            case 4097:
                str = "Parameter error.";
                break;
            case 4098:
                str = "BLE does not support.";
                break;
            case 4099:
                str = "Bluetooth not open.";
                break;
            case 4100:
            case 4101:
            case 4107:
            case 4108:
            case 4109:
            case 4110:
            case 4111:
            default:
                return "unknown error";
            case SUB_ERR_BLUETOOTH_UN_PAIR_FAILED /* 4102 */:
                str = "Cancel Bluetooth pairing failed";
                break;
            case SUB_ERR_A2DP_NOT_INIT /* 4103 */:
                str = "a2dp not initialization.";
                break;
            case SUB_ERR_A2DP_CONNECT_FAILED /* 4104 */:
                str = "a2dp connect failed.";
                break;
            case SUB_ERR_HFP_NOT_INIT /* 4105 */:
                str = "hfp not initialization.";
                break;
            case SUB_ERR_HFP_CONNECT_FAILED /* 4106 */:
                str = "hfp connect failed.";
                break;
            case SUB_ERR_NO_SERVER /* 4112 */:
                str = "ble no server.";
                break;
            case SUB_ERR_OP_FAILED /* 4113 */:
                str = "operation failed.";
                break;
            case SUB_ERR_BLE_NOT_CONNECTED /* 4114 */:
                str = "ble not connected.";
                break;
            case SUB_ERR_CHANGE_BLE_MTU /* 4115 */:
                str = "change ble mtu failed.";
                break;
        }
        return str;
    }
}
