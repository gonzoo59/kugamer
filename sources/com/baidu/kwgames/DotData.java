package com.baidu.kwgames;
/* loaded from: classes.dex */
public class DotData {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    byte mId;
    byte mType;
    int m_nMacroUUID;
    int[] mX = new int[3];
    int[] mY = new int[3];
    int m_nMacroTriggerMode = 0;
    int m_nMacroMoreAttr = 0;
    MacroInfo[] arrMacroInfo = new MacroInfo[3];

    /* JADX INFO: Access modifiers changed from: package-private */
    public DotData() {
        for (int i = 0; i < 3; i++) {
            this.arrMacroInfo[i] = new MacroInfo();
        }
    }

    public boolean parse(byte[] bArr, int i) {
        this.mId = (byte) (bArr[i + 12] & 255);
        int i2 = i + 13;
        this.mType = (byte) (bArr[i2] & 7);
        int i3 = i + 2;
        int i4 = i + 3;
        int i5 = i + 4;
        int i6 = i + 5;
        int[] iArr = {Units.Short2Int(bArr[i], bArr[i + 1]), Units.Short2Int(bArr[i3], bArr[i4]), Units.Short2Int(bArr[i5], bArr[i6])};
        int i7 = i + 8;
        int i8 = i + 9;
        int i9 = i + 10;
        int i10 = i + 11;
        int[] iArr2 = {Units.Short2Int(bArr[i + 6], bArr[i + 7]), Units.Short2Int(bArr[i7], bArr[i8]), Units.Short2Int(bArr[i9], bArr[i10])};
        switch (this.mType) {
            case 0:
            case 3:
            case 6:
                this.mY[0] = Units.dev_x_2_ui_y(iArr[0]);
                this.mX[0] = Units.dev_y_2_ui_x(iArr2[0]);
                this.mY[1] = Units.Short2Int(bArr[i3], bArr[i4]);
                this.mX[1] = Units.Short2Int(bArr[i7], bArr[i8]);
                this.mY[2] = Units.Short2Int(bArr[i5], bArr[i6]);
                this.mX[2] = Units.Short2Int(bArr[i9], bArr[i10]);
                break;
            case 1:
            case 2:
                this.mY[0] = Units.dev_x_2_ui_y(iArr[0]);
                this.mY[1] = Units.dev_x_2_ui_y(iArr[1]);
                this.mY[2] = Units.dev_x_2_ui_y(iArr[2]);
                this.mX[0] = Units.dev_y_2_ui_x(iArr2[0]);
                this.mX[1] = Units.dev_y_2_ui_x(iArr2[1]);
                this.mX[2] = Units.dev_y_2_ui_x(iArr2[2]);
                break;
            case 4:
                this.m_nMacroTriggerMode = (bArr[i2] >> 3) & 3;
                this.m_nMacroMoreAttr = (bArr[i2] >> 5) & 7;
                this.arrMacroInfo[0].nMacroTime = (iArr[0] >> 11) & 31;
                this.arrMacroInfo[1].nMacroTime = (iArr[1] >> 11) & 31;
                this.arrMacroInfo[2].nMacroTime = (iArr[2] >> 11) & 31;
                this.arrMacroInfo[0].nMacroWait = (iArr2[0] >> 12) & 15;
                this.arrMacroInfo[1].nMacroWait = (iArr2[1] >> 12) & 15;
                this.arrMacroInfo[2].nMacroWait = (iArr2[2] >> 12) & 15;
                this.mY[0] = -1 == iArr2[0] ? -1 : Units.dev_x_2_ui_y(iArr[0] & 2047);
                this.mY[1] = -1 == iArr2[1] ? -1 : Units.dev_x_2_ui_y(iArr[1] & 2047);
                this.mY[2] = -1 == iArr2[2] ? -1 : Units.dev_x_2_ui_y(iArr[2] & 2047);
                this.mX[0] = -1 == iArr2[0] ? -1 : Units.dev_y_2_ui_x(iArr2[0] & 4095);
                this.mX[1] = -1 == iArr2[1] ? -1 : Units.dev_y_2_ui_x(iArr2[1] & 4095);
                this.mX[2] = -1 != iArr2[2] ? Units.dev_y_2_ui_x(iArr2[2] & 4095) : -1;
                break;
            case 5:
                this.mY[0] = Units.dev_x_2_ui_y(iArr[0]);
                this.mY[1] = Units.dev_x_2_ui_y(iArr[1]);
                this.mX[0] = Units.dev_y_2_ui_x(iArr2[0]);
                this.mX[1] = Units.dev_y_2_ui_x(iArr2[1]);
                this.mY[2] = Units.Short2Int(bArr[i5], bArr[i6]);
                this.mX[2] = Units.Short2Int(bArr[i9], bArr[i10]);
                break;
            default:
                return false;
        }
        return true;
    }

    public byte[] toBytes() {
        byte[] bArr = new byte[14];
        bArr[12] = this.mId;
        byte b = this.mType;
        bArr[13] = b;
        switch (b) {
            case 0:
            case 3:
            case 6:
                int ui_y_2_dev_x = Units.ui_y_2_dev_x(this.mY[0]);
                bArr[0] = Units.LOBYTE(ui_y_2_dev_x);
                bArr[1] = Units.HIBYTE(ui_y_2_dev_x);
                int ui_x_2_dev_y = Units.ui_x_2_dev_y(this.mX[0]);
                bArr[6] = Units.LOBYTE(ui_x_2_dev_y);
                bArr[7] = Units.HIBYTE(ui_x_2_dev_y);
                bArr[2] = Units.LOBYTE(this.mY[1]);
                bArr[3] = Units.HIBYTE(this.mY[1]);
                bArr[4] = Units.LOBYTE(this.mY[2]);
                bArr[5] = Units.HIBYTE(this.mY[2]);
                bArr[8] = Units.LOBYTE(this.mX[1]);
                bArr[9] = Units.HIBYTE(this.mX[1]);
                bArr[10] = Units.LOBYTE(this.mX[2]);
                bArr[11] = Units.HIBYTE(this.mX[2]);
                break;
            case 1:
            case 2:
                int ui_y_2_dev_x2 = Units.ui_y_2_dev_x(this.mY[0]);
                bArr[0] = Units.LOBYTE(ui_y_2_dev_x2);
                bArr[1] = Units.HIBYTE(ui_y_2_dev_x2);
                int ui_x_2_dev_y2 = Units.ui_x_2_dev_y(this.mX[0]);
                bArr[6] = Units.LOBYTE(ui_x_2_dev_y2);
                bArr[7] = Units.HIBYTE(ui_x_2_dev_y2);
                int ui_y_2_dev_x3 = Units.ui_y_2_dev_x(this.mY[1]);
                bArr[2] = Units.LOBYTE(ui_y_2_dev_x3);
                bArr[3] = Units.HIBYTE(ui_y_2_dev_x3);
                int ui_x_2_dev_y3 = Units.ui_x_2_dev_y(this.mX[1]);
                bArr[8] = Units.LOBYTE(ui_x_2_dev_y3);
                bArr[9] = Units.HIBYTE(ui_x_2_dev_y3);
                int ui_y_2_dev_x4 = Units.ui_y_2_dev_x(this.mY[2]);
                bArr[4] = Units.LOBYTE(ui_y_2_dev_x4);
                bArr[5] = Units.HIBYTE(ui_y_2_dev_x4);
                int ui_x_2_dev_y4 = Units.ui_x_2_dev_y(this.mX[2]);
                bArr[10] = Units.LOBYTE(ui_x_2_dev_y4);
                bArr[11] = Units.HIBYTE(ui_x_2_dev_y4);
                break;
            case 4:
                if (AppInstance.g_sSysStatus.uSystemVer < 100) {
                    bArr[13] = this.mType;
                } else {
                    bArr[13] = (byte) (((this.mType & 7) | ((this.m_nMacroTriggerMode << 3) & 24) | ((this.m_nMacroMoreAttr << 5) & 224)) & 255);
                }
                boolean z = AppInstance.g_sSysStatus.uSystemVer >= 96;
                int[] iArr = this.mY;
                if (-1 == iArr[0]) {
                    bArr[0] = -1;
                    bArr[1] = -1;
                    bArr[6] = -1;
                    bArr[7] = -1;
                } else {
                    int ui_y_2_dev_x5 = Units.ui_y_2_dev_x(iArr[0]);
                    if (z) {
                        ui_y_2_dev_x5 |= (this.arrMacroInfo[0].nMacroTime & 31) << 11;
                    }
                    bArr[0] = Units.LOBYTE(ui_y_2_dev_x5);
                    bArr[1] = Units.HIBYTE(ui_y_2_dev_x5);
                    int ui_x_2_dev_y5 = Units.ui_x_2_dev_y(this.mX[0]);
                    if (z) {
                        ui_x_2_dev_y5 |= (this.arrMacroInfo[0].nMacroWait & 15) << 12;
                    }
                    bArr[6] = Units.LOBYTE(ui_x_2_dev_y5);
                    bArr[7] = Units.HIBYTE(ui_x_2_dev_y5);
                }
                int[] iArr2 = this.mY;
                if (-1 == iArr2[1]) {
                    bArr[2] = -1;
                    bArr[3] = -1;
                    bArr[8] = -1;
                    bArr[9] = -1;
                } else {
                    int ui_y_2_dev_x6 = Units.ui_y_2_dev_x(iArr2[1]);
                    if (z) {
                        ui_y_2_dev_x6 |= (this.arrMacroInfo[1].nMacroTime & 31) << 11;
                    }
                    bArr[2] = Units.LOBYTE(ui_y_2_dev_x6);
                    bArr[3] = Units.HIBYTE(ui_y_2_dev_x6);
                    int ui_x_2_dev_y6 = Units.ui_x_2_dev_y(this.mX[1]);
                    if (z) {
                        ui_x_2_dev_y6 |= (this.arrMacroInfo[1].nMacroWait & 15) << 12;
                    }
                    bArr[8] = Units.LOBYTE(ui_x_2_dev_y6);
                    bArr[9] = Units.HIBYTE(ui_x_2_dev_y6);
                }
                int[] iArr3 = this.mY;
                if (-1 != iArr3[2]) {
                    int ui_y_2_dev_x7 = Units.ui_y_2_dev_x(iArr3[2]);
                    if (z) {
                        ui_y_2_dev_x7 |= (this.arrMacroInfo[2].nMacroTime & 31) << 11;
                    }
                    bArr[4] = Units.LOBYTE(ui_y_2_dev_x7);
                    bArr[5] = Units.HIBYTE(ui_y_2_dev_x7);
                    int ui_x_2_dev_y7 = Units.ui_x_2_dev_y(this.mX[2]);
                    if (z) {
                        ui_x_2_dev_y7 |= (this.arrMacroInfo[2].nMacroWait & 15) << 12;
                    }
                    bArr[10] = Units.LOBYTE(ui_x_2_dev_y7);
                    bArr[11] = Units.HIBYTE(ui_x_2_dev_y7);
                    break;
                } else {
                    bArr[4] = -1;
                    bArr[5] = -1;
                    bArr[10] = -1;
                    bArr[11] = -1;
                    break;
                }
            case 5:
                int ui_y_2_dev_x8 = Units.ui_y_2_dev_x(this.mY[0]);
                bArr[0] = Units.LOBYTE(ui_y_2_dev_x8);
                bArr[1] = Units.HIBYTE(ui_y_2_dev_x8);
                int ui_y_2_dev_x9 = Units.ui_y_2_dev_x(this.mY[1]);
                bArr[2] = Units.LOBYTE(ui_y_2_dev_x9);
                bArr[3] = Units.HIBYTE(ui_y_2_dev_x9);
                int ui_x_2_dev_y8 = Units.ui_x_2_dev_y(this.mX[0]);
                bArr[6] = Units.LOBYTE(ui_x_2_dev_y8);
                bArr[7] = Units.HIBYTE(ui_x_2_dev_y8);
                int ui_x_2_dev_y9 = Units.ui_x_2_dev_y(this.mX[1]);
                bArr[8] = Units.LOBYTE(ui_x_2_dev_y9);
                bArr[9] = Units.HIBYTE(ui_x_2_dev_y9);
                bArr[4] = Units.LOBYTE(this.mY[2]);
                bArr[5] = Units.HIBYTE(this.mY[2]);
                bArr[10] = Units.LOBYTE(this.mX[2]);
                bArr[11] = Units.HIBYTE(this.mX[2]);
                break;
        }
        return bArr;
    }

    public int get_normal_key_trigger_mode() {
        return this.mX[2] & 3;
    }

    public void set_normal_key_trigger_mode(int i) {
        int[] iArr = this.mX;
        iArr[2] = iArr[2] & (-4);
        iArr[2] = (i & 3) | iArr[2];
    }

    public int get_mouse_relate_key_slide_range() {
        return this.mX[2] & 255;
    }

    public void set_mouse_relate_key_slide_range(int i) {
        int[] iArr = this.mX;
        iArr[2] = iArr[2] & (-256);
        iArr[2] = i | iArr[2];
    }

    public int get_slide_key_slide_speed() {
        return this.mX[2] & 255;
    }

    public void set_slide_key_slide_speed(int i) {
        int[] iArr = this.mX;
        iArr[2] = iArr[2] & (-256);
        iArr[2] = i | iArr[2];
    }

    public int get_slide_key_loop_wait() {
        return Units.HIBYTE(this.mX[2]);
    }

    public void set_slide_key_loop_wait(int i) {
        int[] iArr = this.mX;
        iArr[2] = iArr[2] & (-65281);
        iArr[2] = ((i & 255) << 8) | iArr[2];
    }

    public int get_slide_key_trigger_mode() {
        return this.mY[2] & 3;
    }

    public void set_slide_key_trigger_mode(int i) {
        int[] iArr = this.mY;
        iArr[2] = iArr[2] & (-64);
        iArr[2] = (i & 3) | iArr[2];
    }

    public int get_macro_key_trigger_mode() {
        return this.m_nMacroTriggerMode;
    }

    public void set_macro_key_trigger_mode(int i) {
        this.m_nMacroTriggerMode = i;
    }

    public int get_macro_key_more_attr() {
        return this.m_nMacroMoreAttr;
    }

    public void set_macro_key_more_attr(int i) {
        this.m_nMacroMoreAttr = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void set_macro_UUID(int i) {
        this.m_nMacroUUID = i;
        this.mType = (byte) 4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void set_macro_data(int i, int i2, int i3) {
        this.m_nMacroUUID = i;
        this.mType = (byte) 4;
        this.m_nMacroTriggerMode = i2;
        this.m_nMacroMoreAttr = i3;
    }

    public int get_uuid() {
        return this.m_nMacroUUID;
    }

    public void copy_macro(DotData dotData) {
        this.m_nMacroUUID = dotData.m_nMacroUUID;
        this.mType = (byte) 4;
        this.m_nMacroTriggerMode = dotData.m_nMacroTriggerMode;
        this.m_nMacroMoreAttr = dotData.m_nMacroMoreAttr;
    }

    public boolean same_macro(DotData dotData) {
        return this.mId == dotData.mId && this.m_nMacroTriggerMode == dotData.m_nMacroTriggerMode;
    }

    public boolean same_macro_UUID(DotData dotData) {
        return this.m_nMacroUUID == dotData.m_nMacroUUID;
    }
}
