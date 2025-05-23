package com.baidu.kwgames.net;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import com.baidu.kwgames.Constants;
import com.baidu.kwgames.Units;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
/* loaded from: classes.dex */
public class ADB {
    protected static final int E_ADB_POINT_CNT = 6;
    protected static final int E_CONNECT_PERIOD = 1000;
    protected static final int E_DOT_SEND_LEN = 6;
    protected static final byte E_TCP_EVENT_HEAD1 = -2;
    protected static final byte E_TCP_MSG_TYPE_POINTER_DOWN1 = 0;
    protected static final byte E_TCP_MSG_TYPE_POINTER_MOVE1 = 6;
    protected static final byte E_TCP_MSG_TYPE_POINTER_UP1 = 12;
    protected static final String TAG = "ADB";
    private static final int[] s_arrPorts = {Constants.ADB_HOST_PORT1, Constants.ADB_HOST_PORT2, 20377, 30377, 30378, 30379};
    private boolean m_boMTKMode;
    private Thread m_connectThread;
    private Handler m_handleMainUI;
    private int m_nAdbBufWritePos;
    private OutputStream m_outputStream;
    private Socket m_socket;
    private SPoint[] m_arrTouchPoints = new SPoint[6];
    private byte[] m_arrOutBuf = new byte[60];
    private byte[] m_arrOutBuf1 = new byte[6];
    private byte[] m_arrOutBuf2 = new byte[12];
    private byte[] m_arrOutBuf3 = new byte[18];
    private byte[] m_arrOutBuf4 = new byte[24];
    private byte[] m_arrOutBuf5 = new byte[30];
    private byte[] m_arrOutBuf6 = new byte[36];
    private int[] m_arrMtkDevX2UiY = new int[1200];
    private int[] m_arrMtkDevY2UiX = new int[2200];

    /* loaded from: classes.dex */
    class SPoint {
        public boolean boDown;
        public int nX;
        public int nY;

        SPoint() {
        }
    }

    public ADB(Handler handler) {
        this.m_handleMainUI = handler;
        int i = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.m_arrMtkDevX2UiY;
            if (i2 >= iArr.length) {
                break;
            }
            iArr[i2] = Units.mtk_dev_x_2_ui_y(i2);
            i2++;
        }
        int i3 = 0;
        while (true) {
            int[] iArr2 = this.m_arrMtkDevY2UiX;
            if (i3 >= iArr2.length) {
                break;
            }
            iArr2[i3] = Units.mtk_dev_y_2_ui_x(i3);
            i3++;
        }
        while (true) {
            SPoint[] sPointArr = this.m_arrTouchPoints;
            if (i >= sPointArr.length) {
                return;
            }
            sPointArr[i] = new SPoint();
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void send_msg_to_main_UI(int i, int i2) {
        Message obtain = Message.obtain();
        obtain.arg1 = i;
        obtain.arg2 = i2;
        this.m_handleMainUI.sendMessage(obtain);
    }

    private void stop_connect_thread() {
        Thread thread = this.m_connectThread;
        if (thread != null && thread.isAlive()) {
            this.m_connectThread.interrupt();
            this.m_connectThread = null;
        }
        disconnect();
    }

    private void start_connect_thread() {
        Thread thread = this.m_connectThread;
        if (thread == null || !thread.isAlive()) {
            Thread thread2 = new Thread(new Runnable() { // from class: com.baidu.kwgames.net.ADB.1
                @Override // java.lang.Runnable
                public void run() {
                    ADB.this.disconnect();
                    while (true) {
                        int i = 0;
                        if (ADB.this.m_socket == null) {
                            try {
                                int[] iArr = ADB.s_arrPorts;
                                int length = iArr.length;
                                while (true) {
                                    if (i < length) {
                                        int i2 = iArr[i];
                                        ADB.this.m_socket = new Socket(Constants.ADB_HOST_IP, i2);
                                        if (ADB.this.m_socket != null) {
                                            ADB adb = ADB.this;
                                            adb.m_outputStream = adb.m_socket.getOutputStream();
                                            break;
                                        }
                                        i++;
                                    }
                                }
                            } catch (Exception unused) {
                            }
                            SystemClock.sleep(1000L);
                        } else {
                            ADB.this.send_msg_to_main_UI(0, 1);
                            return;
                        }
                    }
                }
            }, "adb");
            this.m_connectThread = thread2;
            thread2.setPriority(8);
            this.m_connectThread.start();
        }
    }

    public void disconnect() {
        try {
            OutputStream outputStream = this.m_outputStream;
            if (outputStream != null) {
                outputStream.close();
            }
            Socket socket = this.m_socket;
            if (socket != null) {
                socket.close();
                this.m_socket = null;
            }
        } catch (IOException unused) {
            this.m_outputStream = null;
            this.m_socket = null;
        }
    }

    public void receive_mtk_buf(byte[] bArr) {
        this.m_nAdbBufWritePos = 0;
        int i = bArr[1] == 7 ? 5 : 6;
        int i2 = 2;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = i2 + 1;
            int i5 = (bArr[i2] & 255) | ((bArr[i4] & 15) << 8);
            int i6 = ((bArr[i4] & Constants.KEY_MOUSE_L) >> 4) | ((bArr[i2 + 2] & 255) << 4);
            if (4095 != i5) {
                int i7 = this.m_arrMtkDevX2UiY[i5];
                int i8 = this.m_arrMtkDevY2UiX[i6];
                if (this.m_arrTouchPoints[i3].boDown) {
                    if (i7 != this.m_arrTouchPoints[i3].nX || i8 != this.m_arrTouchPoints[i3].nY) {
                        this.m_arrTouchPoints[i3].nX = i7;
                        this.m_arrTouchPoints[i3].nY = i8;
                        point_move(i3, i8, i7);
                    }
                } else {
                    this.m_arrTouchPoints[i3].nX = i7;
                    this.m_arrTouchPoints[i3].nY = i8;
                    this.m_arrTouchPoints[i3].boDown = true;
                    point_down(i3, i8, i7);
                }
            } else if (this.m_arrTouchPoints[i3].boDown) {
                point_up(i3, this.m_arrTouchPoints[i3].nY, this.m_arrTouchPoints[i3].nX);
                this.m_arrTouchPoints[i3].boDown = false;
            }
            i2 += 3;
        }
        if (this.m_outputStream == null || this.m_nAdbBufWritePos == 0) {
            return;
        }
        try {
            Log.d(TAG, "m_nAdbBufWritePos=" + this.m_nAdbBufWritePos);
            int i9 = this.m_nAdbBufWritePos;
            if (6 == i9) {
                System.arraycopy(this.m_arrOutBuf, 0, this.m_arrOutBuf1, 0, i9);
                this.m_outputStream.write(this.m_arrOutBuf1);
                this.m_outputStream.flush();
            } else if (12 == i9) {
                System.arraycopy(this.m_arrOutBuf, 0, this.m_arrOutBuf2, 0, i9);
                this.m_outputStream.write(this.m_arrOutBuf2);
                this.m_outputStream.flush();
            } else if (18 == i9) {
                System.arraycopy(this.m_arrOutBuf, 0, this.m_arrOutBuf3, 0, i9);
                this.m_outputStream.write(this.m_arrOutBuf3);
                this.m_outputStream.flush();
            } else if (24 == i9) {
                System.arraycopy(this.m_arrOutBuf, 0, this.m_arrOutBuf4, 0, i9);
                this.m_outputStream.write(this.m_arrOutBuf4);
                this.m_outputStream.flush();
            } else if (30 == i9) {
                System.arraycopy(this.m_arrOutBuf, 0, this.m_arrOutBuf5, 0, i9);
                this.m_outputStream.write(this.m_arrOutBuf5);
                this.m_outputStream.flush();
            } else if (36 == i9) {
                System.arraycopy(this.m_arrOutBuf, 0, this.m_arrOutBuf6, 0, i9);
                this.m_outputStream.write(this.m_arrOutBuf6);
                this.m_outputStream.flush();
            }
        } catch (IOException unused) {
            send_msg_to_main_UI(0, 0);
            this.m_boMTKMode = false;
        }
    }

    void point_move(int i, int i2, int i3) {
        Log.d(TAG, "point_move pt=" + i + " x=" + i2 + " y=" + i3);
        byte[] bArr = this.m_arrOutBuf;
        int i4 = this.m_nAdbBufWritePos;
        int i5 = i4 + 1;
        this.m_nAdbBufWritePos = i5;
        bArr[i4] = -2;
        int i6 = i5 + 1;
        this.m_nAdbBufWritePos = i6;
        bArr[i5] = (byte) (i + 6);
        this.m_nAdbBufWritePos = i6 + 1;
        bArr[i6] = Units.LOBYTE(i2);
        byte[] bArr2 = this.m_arrOutBuf;
        int i7 = this.m_nAdbBufWritePos;
        this.m_nAdbBufWritePos = i7 + 1;
        bArr2[i7] = Units.HIBYTE(i2);
        byte[] bArr3 = this.m_arrOutBuf;
        int i8 = this.m_nAdbBufWritePos;
        this.m_nAdbBufWritePos = i8 + 1;
        bArr3[i8] = Units.LOBYTE(i3);
        byte[] bArr4 = this.m_arrOutBuf;
        int i9 = this.m_nAdbBufWritePos;
        this.m_nAdbBufWritePos = i9 + 1;
        bArr4[i9] = Units.HIBYTE(i3);
    }

    void point_down(int i, int i2, int i3) {
        Log.d(TAG, "point_down pt=" + i + " x=" + i2 + " y=" + i3);
        byte[] bArr = this.m_arrOutBuf;
        int i4 = this.m_nAdbBufWritePos;
        int i5 = i4 + 1;
        this.m_nAdbBufWritePos = i5;
        bArr[i4] = -2;
        int i6 = i5 + 1;
        this.m_nAdbBufWritePos = i6;
        bArr[i5] = (byte) (i + 0);
        this.m_nAdbBufWritePos = i6 + 1;
        bArr[i6] = Units.LOBYTE(i2);
        byte[] bArr2 = this.m_arrOutBuf;
        int i7 = this.m_nAdbBufWritePos;
        this.m_nAdbBufWritePos = i7 + 1;
        bArr2[i7] = Units.HIBYTE(i2);
        byte[] bArr3 = this.m_arrOutBuf;
        int i8 = this.m_nAdbBufWritePos;
        this.m_nAdbBufWritePos = i8 + 1;
        bArr3[i8] = Units.LOBYTE(i3);
        byte[] bArr4 = this.m_arrOutBuf;
        int i9 = this.m_nAdbBufWritePos;
        this.m_nAdbBufWritePos = i9 + 1;
        bArr4[i9] = Units.HIBYTE(i3);
    }

    void point_up(int i, int i2, int i3) {
        Log.d(TAG, "point_up pt=" + i + " x=" + i2 + " y=" + i3);
        byte[] bArr = this.m_arrOutBuf;
        int i4 = this.m_nAdbBufWritePos;
        int i5 = i4 + 1;
        this.m_nAdbBufWritePos = i5;
        bArr[i4] = -2;
        int i6 = i5 + 1;
        this.m_nAdbBufWritePos = i6;
        bArr[i5] = (byte) (i + 12);
        this.m_nAdbBufWritePos = i6 + 1;
        bArr[i6] = Units.LOBYTE(i2);
        byte[] bArr2 = this.m_arrOutBuf;
        int i7 = this.m_nAdbBufWritePos;
        this.m_nAdbBufWritePos = i7 + 1;
        bArr2[i7] = Units.HIBYTE(i2);
        byte[] bArr3 = this.m_arrOutBuf;
        int i8 = this.m_nAdbBufWritePos;
        this.m_nAdbBufWritePos = i8 + 1;
        bArr3[i8] = Units.LOBYTE(i3);
        byte[] bArr4 = this.m_arrOutBuf;
        int i9 = this.m_nAdbBufWritePos;
        this.m_nAdbBufWritePos = i9 + 1;
        bArr4[i9] = Units.HIBYTE(i3);
    }

    public void set_MTK_mode(boolean z) {
        if (this.m_boMTKMode != z) {
            this.m_boMTKMode = z;
            if (z) {
                start_connect_thread();
            } else {
                stop_connect_thread();
            }
        }
    }
}
