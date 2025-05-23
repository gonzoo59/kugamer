package com.baidu.kwgames;
/* loaded from: classes.dex */
public class SystemStatus {
    public byte byADDownAutoAD;
    public byte byADDownAutoADSpeed;
    public byte byCurKeyMap;
    public byte byFlagCapacity;
    public byte byFlagCapacity2;
    public byte byFlagCapacity3;
    public byte byFlagCapacity4;
    public byte byFlagCapacity5;
    public byte byFlagCapacity6;
    public byte byFlagExt3;
    public byte byFlagExt4;
    public byte byFlagExt5;
    public byte byHPVol;
    public byte byStatusExt6;
    public byte byStatusExt7;
    public int byUSBSpeed;
    public boolean m_boHasGunPartsReduceCapacity;
    public byte nGun1DownSens;
    public byte nGun2DownSens;
    public byte nMouseModeSens;
    public byte nTouchSens;
    public int nTouchSensYRatio;
    public byte uStatus;
    public byte uStatusExt;
    public byte uSystemModel;
    public int uSystemVer;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SystemStatus() {
        this.m_boHasGunPartsReduceCapacity = false;
        this.uSystemModel = (byte) 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SystemStatus(byte[] bArr) {
        this.m_boHasGunPartsReduceCapacity = false;
        set_data(bArr);
    }

    public void set_data(byte[] bArr) {
        this.uSystemModel = bArr[3];
        this.uSystemVer = bArr[4] & 255;
        this.uStatusExt = bArr[5];
        this.nTouchSens = bArr[6];
        this.nMouseModeSens = bArr[7];
        this.nGun1DownSens = bArr[8];
        this.nGun2DownSens = bArr[9];
        this.uStatus = bArr[10];
        this.byCurKeyMap = bArr[11];
        this.byFlagExt3 = bArr[12];
        this.byFlagCapacity = bArr[13];
        this.byHPVol = bArr[14];
        this.byFlagCapacity2 = bArr[15];
        this.byFlagExt4 = bArr[16];
        byte b = bArr[17];
        this.byFlagExt5 = b;
        this.byUSBSpeed = 3 & (b >> 4);
        this.nTouchSensYRatio = bArr[18] & 255;
        this.byFlagCapacity3 = bArr[19];
        this.byADDownAutoAD = bArr[20];
        this.byADDownAutoADSpeed = bArr[21];
        this.byFlagCapacity4 = bArr[25];
        this.byFlagCapacity5 = bArr[26];
        this.byFlagCapacity6 = bArr[27];
        this.byStatusExt6 = bArr[28];
        this.byStatusExt7 = bArr[29];
        this.m_boHasGunPartsReduceCapacity = has_gun_parts_reduce_capacity();
    }

    public byte get_device_ID() {
        return this.uSystemModel;
    }

    public boolean is_in_ios_mode() {
        return Units.is_bit_on(this.uStatus, 0);
    }

    public boolean is_in_MTK_mode() {
        return Units.is_bit_on(this.uStatus, 7);
    }

    public boolean is_using_virtual_mouse() {
        return Units.is_bit_on(this.uStatus, 5);
    }

    public boolean is_virtual_mouse_center() {
        return Units.is_bit_on(this.byFlagExt4, 1);
    }

    public boolean is_support_virtual_mouse_USB() {
        ModelInfo modelInfo = Constants.get_model_info(this.uSystemModel);
        return modelInfo != null && modelInfo.m_boSupportVMouseUSB;
    }

    public boolean is_CF_dynamic_continues() {
        return Units.is_bit_on(this.byFlagExt4, 0);
    }

    public boolean is_in_usb_mode() {
        return Units.is_bit_on(this.uStatusExt, 1);
    }

    public boolean is_in_touping_mode() {
        return Units.is_bit_on(this.uStatusExt, 6);
    }

    public boolean is_in_moba_mode() {
        return Units.is_bit_on(this.byFlagExt3, 0);
    }

    public boolean is_in_minecraft_mode() {
        return Units.is_bit_on(this.byFlagExt3, 2);
    }

    public boolean is_usb_audio_off() {
        return Units.is_bit_on(this.byFlagExt3, 1);
    }

    public boolean is_usb_mode_hard_acc() {
        return Units.is_bit_on(this.byFlagExt3, 6);
    }

    public boolean is_AI_continues_shoot() {
        return Units.is_bit_on(this.byFlagExt3, 7);
    }

    public boolean is_in_x1_mode() {
        return Units.is_bit_on(this.uStatusExt, 7);
    }

    public boolean is_in_NTS_mode() {
        return Units.is_bit_on(this.byFlagExt4, 4);
    }

    public boolean is_in_MTK_USB_mode() {
        return Units.is_bit_on(this.byFlagExt4, 5);
    }

    public boolean is_smart_QE_on() {
        return Units.is_bit_on(this.byFlagExt4, 6);
    }

    public boolean is_R_throw_to_left() {
        return Units.is_bit_on(this.byFlagExt4, 7);
    }

    public boolean has_moba_capacity() {
        return Units.is_bit_on(this.byFlagCapacity, 0);
    }

    public boolean has_minecraft_capacity() {
        return Units.is_bit_on(this.byFlagCapacity, 1);
    }

    public boolean has_x1_capacity() {
        return Units.is_bit_on(this.byFlagCapacity, 2);
    }

    public boolean has_touping_capacity() {
        return Units.is_bit_on(this.byFlagCapacity, 3);
    }

    public boolean has_w_gun_down_capacity() {
        return Units.is_bit_on(this.byFlagCapacity, 4);
    }

    public boolean has_dynamic_gun_down_capacity() {
        return Units.is_bit_on(this.byFlagCapacity, 5);
    }

    public boolean has_gun_parts_reduce_capacity() {
        return Units.is_bit_on(this.byFlagCapacity, 6);
    }

    public boolean has_APP_AI_continues_shoot_capacity() {
        return Units.is_bit_on(this.byFlagCapacity, 7);
    }

    public boolean has_AI_capacity() {
        ModelInfo modelInfo = Constants.get_model_info(this.uSystemModel);
        return modelInfo != null && modelInfo.m_boSupportAIGunDown;
    }

    public boolean has_touping_to_monitor_capacity() {
        return Units.is_bit_on(this.byFlagCapacity2, 0);
    }

    public boolean has_shift_2_walk_capacity() {
        return Units.is_bit_on(this.byFlagCapacity2, 1);
    }

    public boolean has_AI_gun_down_percent_capacity() {
        return Units.is_bit_on(this.byFlagCapacity2, 2);
    }

    public boolean has_mouse_wheel_sw_gun_capacity() {
        return Units.is_bit_on(this.byFlagCapacity2, 3);
    }

    public boolean has_key_config_ex_capacity() {
        return Units.is_bit_on(this.byFlagCapacity2, 4);
    }

    public boolean has_x_scope_capacity() {
        return Units.is_bit_on(this.byFlagCapacity2, 5);
    }

    public boolean has_NTS_capacity() {
        return Units.is_bit_on(this.byFlagCapacity2, 6);
    }

    public boolean has_Y_sense_adjust_capacity() {
        return Units.is_bit_on(this.byFlagCapacity2, 7);
    }

    public boolean has_AI_mouse_sense_capacity() {
        return Units.is_bit_on(this.byFlagCapacity3, 0);
    }

    public boolean has_AD_down_auto_AD_capacity() {
        return Units.is_bit_on(this.byFlagCapacity3, 1);
    }

    public boolean has_shunfenger_capacity() {
        return Units.is_bit_on(this.byFlagCapacity3, 2);
    }

    public boolean has_1st_gun_optimize_capacity() {
        return Units.is_bit_on(this.byFlagCapacity3, 3);
    }

    public boolean has_remove_frozen_capacity() {
        return Units.is_bit_on(this.byFlagCapacity3, 4);
    }

    public boolean has_adjust_ctrl_repeat_speed_capacity() {
        return Units.is_bit_on(this.byFlagCapacity3, 5);
    }

    public boolean has_usb_speed_asjust_capacity() {
        return Units.is_bit_on(this.byFlagCapacity3, 6);
    }

    public boolean has_ban_ID_capacity() {
        return Units.is_bit_on(this.byFlagCapacity3, 7);
    }

    public boolean has_qianliyan_capacity() {
        return Units.is_bit_on(this.byFlagCapacity4, 0);
    }

    public boolean has_smart_tppfpp_capacity() {
        return Units.is_bit_on(this.byFlagCapacity4, 1);
    }

    public boolean has_usb_mtk_capacity() {
        return Units.is_bit_on(this.byFlagCapacity4, 2);
    }

    public boolean has_smart_QE_capacity() {
        return Units.is_bit_on(this.byFlagCapacity4, 3);
    }

    public boolean has_M4_AI_dynamic_capacity() {
        return Units.is_bit_on(this.byFlagCapacity4, 4);
    }

    public boolean has_game_param_setting_capacity() {
        return Units.is_bit_on(this.byFlagCapacity4, 5);
    }

    public boolean has_yjwj_macro_capacity() {
        return Units.is_bit_on(this.byFlagCapacity4, 6);
    }

    public boolean has_random_ble_name_capacity() {
        return Units.is_bit_on(this.byFlagCapacity4, 7);
    }

    public int get_gun_down_max() {
        return has_w_gun_down_capacity() ? 99 : 64;
    }

    public boolean is_gun_press_open() {
        return (this.nGun1DownSens == 0 && this.nGun2DownSens == 0) ? false : true;
    }

    public int get_usb_vmouse_Hz() {
        return this.byFlagExt5 & 3;
    }

    public boolean is_random_ble_name() {
        return Units.is_bit_on(this.byStatusExt6, 0);
    }
}
