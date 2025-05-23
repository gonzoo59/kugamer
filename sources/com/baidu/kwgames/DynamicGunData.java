package com.baidu.kwgames;

import com.baidu.kwgames.util.NEAT;
import com.blankj.utilcode.util.FileIOUtils;
import java.io.File;
import java.lang.reflect.Array;
/* loaded from: classes.dex */
public class DynamicGunData {
    public static final byte[] s_arrBleSend;
    public static final String[] s_arrGun1ScaleName;
    public static final String[] s_arrGun2ScaleName;
    public static boolean s_boDunxia;
    public static boolean s_boGun1ContinuesShoot;
    public static boolean s_boGun2ContinuesShoot;
    public static boolean s_boOnOff;
    public static boolean s_boPaxia;
    public static boolean s_boScopeOn;
    public static boolean s_boUsingGun2;
    public static int s_nGun1ContinuesSpeed;
    public static byte s_nGun1DynamicLevel;
    public static int s_nGun2ContinuesSpeed;
    public static byte s_nGun2DynamicLevel;
    public static final int s_nLenLimitV1;
    public static final int s_nLenLimitV2;
    public static String s_strAPP;
    public static String s_strContinuesShootOn;
    public static String s_strDunxiaText;
    public static String s_strPaxiaText;
    public static String s_strScopeOff;
    public static String s_strScopeOn;
    public static String s_strStandText;
    public static byte[] s_arrF1F5 = new byte[5];
    public static byte[][] s_arrGunPressDynamic = (byte[][]) Array.newInstance(byte.class, 5, 6);
    public static byte[] s_arrFlags = new byte[2];
    public static byte[] s_arrF1F5C = new byte[5];
    public static byte[] s_arrF1F5Z = new byte[5];
    private static CrosshairInfo s_crossDynamicScopeOff = new CrosshairInfo();

    static {
        byte[] bArr = s_arrF1F5;
        s_nLenLimitV1 = bArr.length + 30 + 5;
        int length = bArr.length + 30 + s_arrFlags.length + s_arrF1F5C.length + s_arrF1F5Z.length;
        s_nLenLimitV2 = length;
        s_arrBleSend = new byte[length + 1 + 4];
        s_arrGun1ScaleName = new String[]{"1+F1(", "1+F2(", "1+F3(", "1+F4(", "1+F5("};
        s_arrGun2ScaleName = new String[]{"2+F1(", "2+F2(", "2+F3(", "2+F4(", "2+F5("};
        s_nGun1DynamicLevel = (byte) 3;
        s_nGun2DynamicLevel = (byte) 3;
        s_nGun1ContinuesSpeed = 3;
        s_nGun2ContinuesSpeed = 3;
        s_boDunxia = false;
        s_boPaxia = false;
        s_strAPP = "";
    }

    public static String get_stage_save_name() {
        return s_strAPP + ".gundyn";
    }

    public static CrosshairInfo get_scope_off_crosshair() {
        return s_crossDynamicScopeOff;
    }

    public static boolean init(String str) {
        if (s_strAPP.equals(str)) {
            return false;
        }
        s_strAPP = str;
        s_boUsingGun2 = false;
        s_boScopeOn = false;
        s_boDunxia = false;
        s_boPaxia = false;
        s_boGun1ContinuesShoot = false;
        s_boGun2ContinuesShoot = false;
        byte[] bArr = s_arrBleSend;
        bArr[0] = -1;
        bArr[1] = -113;
        int i = s_nLenLimitV2;
        bArr[2] = Units.LOBYTE(i + 1);
        bArr[3] = Units.HIBYTE(i + 1);
        s_strContinuesShootOn = NEAT.s(R.string.dynamic_res_continues_on);
        s_strScopeOn = "|" + NEAT.s(R.string.ai_scope_on);
        s_strScopeOff = "|" + NEAT.s(R.string.ai_scope_off);
        s_strStandText = NEAT.s(R.string.stand) + " ";
        s_strDunxiaText = NEAT.s(R.string.squt) + " ";
        s_strPaxiaText = NEAT.s(R.string.fall) + " ";
        s_crossDynamicScopeOff.reset();
        CrosshairInfo crosshairInfo = s_crossDynamicScopeOff;
        crosshairInfo.load(s_strAPP + "dyOffCross");
        byte[] readFile2BytesByChannel = FileIOUtils.readFile2BytesByChannel(AppInstance.get().getExternalFilesDir(null) + "/" + get_stage_save_name());
        if (readFile2BytesByChannel == null) {
            readFile2BytesByChannel = FileIOUtils.readFile2BytesByChannel(AppInstance.get().getExternalFilesDir(null) + Constants.DYNAMIC_GUN_STAGE);
        }
        if (readFile2BytesByChannel != null) {
            if (readFile2BytesByChannel.length >= i) {
                byte[] bArr2 = s_arrF1F5;
                System.arraycopy(readFile2BytesByChannel, 0, bArr2, 0, bArr2.length);
                int length = s_arrF1F5.length;
                for (int i2 = 0; i2 < 5; i2++) {
                    int i3 = 0;
                    while (i3 < 6) {
                        s_arrGunPressDynamic[i2][i3] = readFile2BytesByChannel[length];
                        i3++;
                        length++;
                    }
                }
                byte[] bArr3 = s_arrFlags;
                System.arraycopy(readFile2BytesByChannel, length, bArr3, 0, bArr3.length);
                int length2 = length + s_arrFlags.length;
                byte[] bArr4 = s_arrF1F5C;
                System.arraycopy(readFile2BytesByChannel, length2, bArr4, 0, bArr4.length);
                int length3 = length2 + s_arrF1F5C.length;
                byte[] bArr5 = s_arrF1F5Z;
                System.arraycopy(readFile2BytesByChannel, length3, bArr5, 0, bArr5.length);
            } else if (readFile2BytesByChannel.length >= s_nLenLimitV1) {
                byte[] bArr6 = s_arrF1F5;
                System.arraycopy(readFile2BytesByChannel, 0, bArr6, 0, bArr6.length);
                int length4 = s_arrF1F5.length;
                for (int i4 = 0; i4 < 5; i4++) {
                    int i5 = 0;
                    while (i5 < 6) {
                        s_arrGunPressDynamic[i4][i5] = readFile2BytesByChannel[length4];
                        i5++;
                        length4++;
                    }
                }
                byte[] bArr7 = s_arrFlags;
                System.arraycopy(readFile2BytesByChannel, length4, bArr7, 0, bArr7.length);
                for (int i6 = 0; i6 < 5; i6++) {
                    byte[] bArr8 = s_arrF1F5;
                    if (bArr8[i6] > 4) {
                        s_arrF1F5C[i6] = (byte) (bArr8[i6] - 4);
                    } else {
                        s_arrF1F5C[i6] = bArr8[i6];
                    }
                    if (bArr8[i6] > 10) {
                        s_arrF1F5Z[i6] = (byte) (bArr8[i6] - 10);
                    } else {
                        s_arrF1F5Z[i6] = bArr8[i6];
                    }
                }
            }
        } else {
            for (int i7 = 0; i7 < 5; i7++) {
                for (int i8 = 0; i8 < 6; i8++) {
                    s_arrGunPressDynamic[i7][i8] = Constants.KEY_102ND;
                }
            }
            if (NEAT.is_pad()) {
                byte[] bArr9 = s_arrF1F5;
                bArr9[0] = 31;
                byte[] bArr10 = s_arrF1F5C;
                bArr10[0] = 27;
                byte[] bArr11 = s_arrF1F5Z;
                bArr11[0] = 22;
                bArr9[1] = 38;
                bArr10[1] = 32;
                bArr11[1] = 28;
                bArr9[2] = 42;
                bArr10[2] = 35;
                bArr11[2] = 28;
                bArr9[3] = 51;
                bArr10[3] = 46;
                bArr11[3] = 37;
                bArr9[4] = 55;
                bArr10[4] = 51;
                bArr11[4] = 48;
            } else {
                byte[] bArr12 = s_arrF1F5;
                bArr12[0] = 36;
                byte[] bArr13 = s_arrF1F5C;
                bArr13[0] = 30;
                byte[] bArr14 = s_arrF1F5Z;
                bArr14[0] = 26;
                bArr12[1] = 43;
                bArr13[1] = 36;
                bArr14[1] = 32;
                bArr12[2] = 47;
                bArr13[2] = 42;
                bArr14[2] = 33;
                bArr12[3] = 54;
                bArr13[3] = 50;
                bArr14[3] = 43;
                bArr12[4] = 59;
                bArr13[4] = 54;
                bArr14[4] = 52;
            }
        }
        return true;
    }

    public static boolean is_scope_on() {
        return s_boScopeOn;
    }

    public static synchronized boolean save_dynamic_gun_stage() {
        boolean writeFileFromBytesByChannel;
        synchronized (DynamicGunData.class) {
            byte[] bArr = new byte[s_nLenLimitV2];
            byte[] bArr2 = s_arrF1F5;
            System.arraycopy(bArr2, 0, bArr, 0, bArr2.length);
            int length = s_arrF1F5.length + 0;
            for (int i = 0; i < 5; i++) {
                int i2 = 0;
                while (i2 < 6) {
                    bArr[length] = s_arrGunPressDynamic[i][i2];
                    i2++;
                    length++;
                }
            }
            byte[] bArr3 = s_arrFlags;
            System.arraycopy(bArr3, 0, bArr, length, bArr3.length);
            int length2 = length + s_arrFlags.length;
            byte[] bArr4 = s_arrF1F5C;
            System.arraycopy(bArr4, 0, bArr, length2, bArr4.length);
            int length3 = length2 + s_arrF1F5C.length;
            byte[] bArr5 = s_arrF1F5Z;
            System.arraycopy(bArr5, 0, bArr, length3, bArr5.length);
            int length4 = s_arrF1F5Z.length;
            try {
                File externalFilesDir = AppInstance.get().getExternalFilesDir(null);
                externalFilesDir.mkdirs();
                writeFileFromBytesByChannel = FileIOUtils.writeFileFromBytesByChannel(externalFilesDir + "/" + get_stage_save_name(), bArr, true);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return writeFileFromBytesByChannel;
    }

    public static void on_received_dynamic_level(byte[] bArr) {
        if (bArr.length >= 5) {
            s_boUsingGun2 = (bArr[3] & 1) != 0;
            s_boGun1ContinuesShoot = (bArr[3] & 4) != 0;
            s_boGun2ContinuesShoot = (bArr[3] & 8) != 0;
            s_nGun1DynamicLevel = (byte) (bArr[4] & 7);
            s_nGun2DynamicLevel = (byte) (bArr[5] & 7);
            s_nGun1ContinuesSpeed = (bArr[4] >> 3) & 7;
            s_nGun2ContinuesSpeed = (bArr[5] >> 3) & 7;
            if (AppInstance.g_sSysStatus.uSystemVer >= 89) {
                s_boScopeOn = (bArr[3] & 2) != 0;
                s_boDunxia = (bArr[3] & 16) != 0;
                s_boPaxia = (bArr[3] & 32) != 0;
            }
        }
    }

    public static void on_received_dynamic_value(byte[] bArr) {
        if (bArr.length >= 4) {
            s_boUsingGun2 = ((bArr[3] >> 7) & 1) != 0;
            if (AppInstance.g_sSysStatus.uSystemVer >= 89) {
                s_boDunxia = ((bArr[3] >> 6) & 1) != 0;
                s_boPaxia = ((bArr[3] >> 5) & 1) != 0;
            }
            if (s_boUsingGun2) {
                byte b = (byte) (bArr[3] & 15);
                s_nGun2DynamicLevel = b;
                if (s_boDunxia) {
                    s_arrF1F5C[b - 1] = bArr[4];
                    return;
                } else if (s_boPaxia) {
                    s_arrF1F5Z[b - 1] = bArr[4];
                    return;
                } else {
                    s_arrF1F5[b - 1] = bArr[4];
                    return;
                }
            }
            byte b2 = (byte) (bArr[3] & 15);
            s_nGun1DynamicLevel = b2;
            if (s_boDunxia) {
                s_arrF1F5C[b2 - 1] = bArr[4];
            } else if (s_boPaxia) {
                s_arrF1F5Z[b2 - 1] = bArr[4];
            } else {
                s_arrF1F5[b2 - 1] = bArr[4];
            }
        }
    }

    public static byte[] get_dynamic_gun_ble_send_data(boolean z) {
        byte[] bArr = s_arrBleSend;
        bArr[4] = s_boOnOff;
        byte[] bArr2 = s_arrF1F5;
        System.arraycopy(bArr2, 0, bArr, 5, bArr2.length);
        int length = s_arrF1F5.length + 5;
        for (int i = 0; i < 5; i++) {
            int i2 = 0;
            while (i2 < 6) {
                s_arrBleSend[length] = s_arrGunPressDynamic[i][i2];
                i2++;
                length++;
            }
        }
        int i3 = s_boUsingGun2 ? 128 : 0;
        if (z) {
            i3 |= 64;
        }
        int i4 = s_nGun1DynamicLevel | (s_nGun2DynamicLevel << 3) | i3;
        byte[] bArr3 = s_arrBleSend;
        int i5 = length + 1;
        bArr3[length] = (byte) (i4 & 255);
        int i6 = i5 + 1;
        bArr3[i5] = 0;
        byte[] bArr4 = s_arrF1F5C;
        System.arraycopy(bArr4, 0, bArr3, i6, bArr4.length);
        int length2 = i6 + s_arrF1F5C.length;
        byte[] bArr5 = s_arrF1F5Z;
        System.arraycopy(bArr5, 0, bArr3, length2, bArr5.length);
        return bArr3;
    }

    public static boolean get_onoff() {
        return s_boOnOff;
    }

    public static void set_onoff(boolean z) {
        s_boOnOff = z;
    }

    public static byte get_level_sens(int i) {
        if (i < 0 || i >= 5) {
            return (byte) 0;
        }
        return s_arrF1F5[i];
    }

    public static void set_level_sens(int i, byte b) {
        if (i < 0 || i >= 5) {
            return;
        }
        s_arrF1F5[i] = b;
    }

    public static byte get_level_c_sens(int i) {
        if (i < 0 || i >= 5) {
            return (byte) 0;
        }
        return s_arrF1F5C[i];
    }

    public static void set_level_c_sens(int i, byte b) {
        if (i < 0 || i >= 5) {
            return;
        }
        s_arrF1F5C[i] = b;
    }

    public static byte get_level_z_sens(int i) {
        if (i < 0 || i >= 5) {
            return (byte) 0;
        }
        return s_arrF1F5Z[i];
    }

    public static void set_level_z_sens(int i, byte b) {
        if (i < 0 || i >= 5) {
            return;
        }
        s_arrF1F5Z[i] = b;
    }

    public static byte get_stage_data(int i, int i2) {
        return (i < 0 || i >= 5 || i2 < 0 || i2 >= 6) ? Constants.KEY_102ND : s_arrGunPressDynamic[i][i2];
    }

    public static void set_stage_data(int i, int i2, byte b) {
        if (i < 0 || i >= 5 || i2 < 0 || i2 >= 6 || b > 100) {
            return;
        }
        s_arrGunPressDynamic[i][i2] = b;
    }

    public static boolean has_gun_press() {
        return get_onoff() && !(s_nGun1DynamicLevel == 0 && s_nGun2DynamicLevel == 0);
    }

    public static String get_dynamic_result_string() {
        byte b;
        String str;
        String str2 = !s_boUsingGun2 ? " *" : "";
        byte b2 = 0;
        if (s_boDunxia) {
            byte b3 = s_nGun1DynamicLevel;
            b = b3 == 0 ? (byte) 0 : s_arrF1F5C[b3 - 1];
            byte b4 = s_nGun2DynamicLevel;
            if (b4 != 0) {
                b2 = s_arrF1F5C[b4 - 1];
            }
        } else if (s_boPaxia) {
            byte b5 = s_nGun1DynamicLevel;
            b = b5 == 0 ? (byte) 0 : s_arrF1F5Z[b5 - 1];
            byte b6 = s_nGun2DynamicLevel;
            if (b6 != 0) {
                b2 = s_arrF1F5Z[b6 - 1];
            }
        } else {
            byte b7 = s_nGun1DynamicLevel;
            b = b7 == 0 ? (byte) 0 : s_arrF1F5[b7 - 1];
            byte b8 = s_nGun2DynamicLevel;
            if (b8 != 0) {
                b2 = s_arrF1F5[b8 - 1];
            }
        }
        byte b9 = s_nGun1DynamicLevel;
        if (b9 == 0) {
            str2 = str2 + "1+ESC(0";
        } else if (b9 == 1 || b9 == 2 || b9 == 3 || b9 == 4 || b9 == 5) {
            str2 = str2 + s_arrGun1ScaleName[s_nGun1DynamicLevel - 1] + ((int) b);
        }
        if (s_boGun1ContinuesShoot) {
            if (AppInstance.g_sSysStatus.uSystemVer > 86) {
                str2 = str2 + s_strContinuesShootOn + (s_nGun1ContinuesSpeed + 1);
            } else {
                str2 = str2 + s_strContinuesShootOn;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2 + ")|");
        sb.append(s_boUsingGun2 ? "*" : " ");
        String sb2 = sb.toString();
        byte b10 = s_nGun2DynamicLevel;
        if (b10 == 0) {
            sb2 = sb2 + "2+ESC(0";
        } else if (b10 == 1 || b10 == 2 || b10 == 3 || b10 == 4 || b10 == 5) {
            sb2 = sb2 + s_arrGun2ScaleName[s_nGun2DynamicLevel - 1] + ((int) b2);
        }
        if (s_boGun2ContinuesShoot) {
            if (AppInstance.g_sSysStatus.uSystemVer > 86) {
                sb2 = sb2 + s_strContinuesShootOn + (s_nGun2ContinuesSpeed + 1);
            } else {
                sb2 = sb2 + s_strContinuesShootOn;
            }
        }
        String str3 = sb2 + ")";
        if (AppInstance.g_sSysStatus.uSystemVer >= 89) {
            if (s_boScopeOn) {
                str = str3 + s_strScopeOn;
            } else {
                str = str3 + s_strScopeOff;
            }
            if (s_boDunxia) {
                return str + s_strDunxiaText;
            } else if (s_boPaxia) {
                return str + s_strPaxiaText;
            } else {
                return str + s_strStandText;
            }
        }
        return str3;
    }
}
