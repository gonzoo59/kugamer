package com.baidu.kwgames;

import java.lang.reflect.Array;
/* loaded from: classes.dex */
public class ThroneRingBuffer {
    public static final String TAG = "RingBuf";
    private int dwBufLen;
    private int dwRd;
    private int dwWr;
    private final byte[] mBuffer;
    private final byte[] m_arrCurPacket;
    int m_wKeyMapLen;
    int m_eBleBufDecodeState = 0;
    int m_byBleVariableType = 0;
    int m_uVariableDataLen = 0;
    int m_nMTKDataReceivedLen = 0;
    boolean m_boKeyMap1stByte = false;
    int m_wDecodedKeyMapLen = 0;
    int m_uVariableDecodedLen = 0;
    int m_nCurPacketWritePos = 0;
    boolean m_boSuccessDecode = false;

    public ThroneRingBuffer(int i) {
        Units.checkArgumentPositive(i, "A ThroneRingBuffer cannot have 0 capacity");
        this.mBuffer = (byte[]) Array.newInstance(Byte.TYPE, i);
        this.dwRd = 0;
        this.dwWr = 0;
        this.dwBufLen = i;
        this.m_arrCurPacket = new byte[i];
    }

    public boolean isEmpty() {
        return this.dwRd == this.dwWr;
    }

    public void clear() {
        this.dwWr = 0;
        this.dwRd = 0;
    }

    public void write(byte[] bArr) {
        int i = this.dwWr;
        int length = bArr.length;
        int i2 = i + length;
        int i3 = this.dwBufLen;
        if (i2 <= i3) {
            System.arraycopy(bArr, 0, this.mBuffer, i, length);
        } else {
            int i4 = i3 - i;
            System.arraycopy(bArr, 0, this.mBuffer, i, i4);
            i2 = length - i4;
            System.arraycopy(bArr, i4, this.mBuffer, 0, i2);
        }
        this.dwWr = i2 < this.dwBufLen ? i2 : 0;
    }

    public void write(byte[] bArr, int i) {
        int i2 = this.dwWr;
        int i3 = i2 + i;
        int i4 = this.dwBufLen;
        if (i3 <= i4) {
            System.arraycopy(bArr, 0, this.mBuffer, i2, i);
        } else {
            int i5 = i4 - i2;
            System.arraycopy(bArr, 0, this.mBuffer, i2, i5);
            i3 = i - i5;
            System.arraycopy(bArr, i5, this.mBuffer, 0, i3);
        }
        this.dwWr = i3 < this.dwBufLen ? i3 : 0;
    }

    void ble_decode_none(byte b) {
        if (255 == (b & 255)) {
            this.m_eBleBufDecodeState = 1;
        }
    }

    void ble_decode_head(byte b) {
        if (b != 10) {
            if (b == 19) {
                this.m_eBleBufDecodeState = 8;
                this.m_nMTKDataReceivedLen = 0;
                return;
            }
            switch (b) {
                case 1:
                    this.m_eBleBufDecodeState = 2;
                    return;
                case 2:
                    this.m_eBleBufDecodeState = 3;
                    return;
                case 3:
                    this.m_eBleBufDecodeState = 4;
                    this.m_boKeyMap1stByte = true;
                    return;
                case 4:
                case 5:
                case 6:
                case 8:
                    break;
                case 7:
                    this.m_eBleBufDecodeState = 7;
                    this.m_nMTKDataReceivedLen = 0;
                    return;
                default:
                    this.m_eBleBufDecodeState = 6;
                    this.m_byBleVariableType = b;
                    this.m_uVariableDataLen = -1;
                    return;
            }
        }
        this.m_eBleBufDecodeState = 6;
        this.m_byBleVariableType = b;
        this.m_uVariableDataLen = -1;
    }

    void ble_decode_key_down(byte b) {
        this.m_eBleBufDecodeState = 0;
        this.m_boSuccessDecode = true;
    }

    void ble_decode_mouse_down(byte b) {
        this.m_eBleBufDecodeState = 0;
        this.m_boSuccessDecode = true;
    }

    void ble_decode_upload_key_map_len(byte b) {
        if (this.m_boKeyMap1stByte) {
            this.m_boKeyMap1stByte = false;
            this.m_wKeyMapLen = b & 255;
            return;
        }
        int i = ((b << 8) & 65280) | this.m_wKeyMapLen;
        this.m_wKeyMapLen = i;
        if (i == 0) {
            this.m_eBleBufDecodeState = 0;
            return;
        }
        this.m_eBleBufDecodeState = 5;
        this.m_wDecodedKeyMapLen = 0;
    }

    void ble_decode_upload_key_map_data(byte b) {
        int i = this.m_wDecodedKeyMapLen + 1;
        this.m_wDecodedKeyMapLen = i;
        if (i >= this.m_wKeyMapLen) {
            this.m_eBleBufDecodeState = 0;
            this.m_boSuccessDecode = true;
        }
    }

    void ble_decode_variable_msg(byte b) {
        if (-1 == this.m_uVariableDataLen) {
            this.m_uVariableDataLen = b;
            this.m_uVariableDecodedLen = 0;
        } else {
            this.m_uVariableDecodedLen++;
        }
        if (this.m_uVariableDecodedLen >= this.m_uVariableDataLen) {
            ble_decode_variable_end();
        }
    }

    void ble_decode_variable_end() {
        this.m_eBleBufDecodeState = 0;
        this.m_boSuccessDecode = true;
    }

    void ble_decode_mtk_data(byte b) {
        int i = this.m_nMTKDataReceivedLen + 1;
        this.m_nMTKDataReceivedLen = i;
        if (i >= 15) {
            this.m_eBleBufDecodeState = 0;
            this.m_boSuccessDecode = true;
        }
    }

    void ble_decode_usb_mtk_data(byte b) {
        int i = this.m_nMTKDataReceivedLen + 1;
        this.m_nMTKDataReceivedLen = i;
        if (i >= 18) {
            this.m_eBleBufDecodeState = 0;
            this.m_boSuccessDecode = true;
        }
    }

    public byte[] getNextPackage() {
        int i = this.dwWr;
        int i2 = this.dwRd;
        if (i == i2) {
            return null;
        }
        while (true) {
            if (this.dwWr != i2) {
                int i3 = i2 + 1;
                byte b = this.mBuffer[i2];
                i2 = i3 >= this.dwBufLen ? 0 : i3;
                byte[] bArr = this.m_arrCurPacket;
                int i4 = this.m_nCurPacketWritePos;
                this.m_nCurPacketWritePos = i4 + 1;
                bArr[i4] = b;
                switch (this.m_eBleBufDecodeState) {
                    case 0:
                        this.m_boSuccessDecode = false;
                        ble_decode_none(b);
                        break;
                    case 1:
                        ble_decode_head(b);
                        break;
                    case 2:
                        ble_decode_key_down(b);
                        break;
                    case 3:
                        ble_decode_mouse_down(b);
                        break;
                    case 4:
                        ble_decode_upload_key_map_len(b);
                        break;
                    case 5:
                        ble_decode_upload_key_map_data(b);
                        break;
                    case 6:
                        ble_decode_variable_msg(b);
                        break;
                    case 7:
                        ble_decode_mtk_data(b);
                        break;
                    case 8:
                        ble_decode_usb_mtk_data(b);
                        break;
                }
                if (this.m_boSuccessDecode) {
                    this.m_nCurPacketWritePos = 0;
                } else if (this.m_eBleBufDecodeState == 0) {
                    this.m_nCurPacketWritePos = 0;
                }
            }
        }
        this.dwRd = i2;
        if (this.m_boSuccessDecode) {
            this.m_boSuccessDecode = false;
            return this.m_arrCurPacket;
        }
        return null;
    }

    public byte[] dump() {
        return this.mBuffer;
    }
}
