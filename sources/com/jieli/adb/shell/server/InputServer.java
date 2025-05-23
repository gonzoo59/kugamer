package com.jieli.adb.shell.server;

import com.baidu.kwgames.Constants;
import java.io.PrintStream;
import java.net.ServerSocket;
/* loaded from: assets/adb/sincoserver.dex */
public class InputServer {
    private static final int E_DOT_CNT = 6;
    private static final int E_DOWN = 1;
    private static final int E_MOVE = 2;
    private static final int E_TCP_MSG_TYPE_POINTER_DOWN1 = 0;
    private static final int E_TCP_MSG_TYPE_POINTER_END = 18;
    private static final int E_TCP_MSG_TYPE_POINTER_MOVE1 = 6;
    private static final int E_TCP_MSG_TYPE_POINTER_UP1 = 12;
    private static final int E_UP = 0;
    private static final byte TCP_HEAD1 = -2;
    private static final byte TCP_HEAD2 = -17;
    private static final int TCP_XY_SIZE = 4;
    private static final int[] m_arrPorts = {Constants.ADB_HOST_PORT1, Constants.ADB_HOST_PORT2, 20377, 30377, 30378, 30379};
    private int m_nDecodedMsgXY;
    private int m_nMsgType;
    private ETcpDecode m_eTcpDecode = ETcpDecode.E_TCP_DECODE_HEAD1;
    private byte[] m_arrMsgXY = new byte[4];
    private CPointsInfo m_sPointsInfo = new CPointsInfo();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: assets/adb/sincoserver.dex */
    public enum ETcpDecode {
        E_TCP_DECODE_HEAD1,
        E_TCP_DECODE_TYPE,
        E_TCP_DECODE_XY
    }

    private InputServer() {
    }

    public ServerSocket creat_server_socket(int i) {
        try {
            return new ServerSocket(i);
        } catch (Exception e) {
            PrintStream printStream = System.out;
            printStream.println("Exception:" + e);
            return null;
        }
    }

    public static void startServer() {
        System.out.println("setup Server");
        try {
            Class.forName("android.os.Process").getMethod("setArgV0", String.class).invoke(null, "com.doubleghost.inject");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        try {
            new InputServer().execute();
        } catch (Exception e2) {
            System.out.println(e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.jieli.adb.shell.server.InputServer$1  reason: invalid class name */
    /* loaded from: assets/adb/sincoserver.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$jieli$adb$shell$server$InputServer$ETcpDecode = new int[ETcpDecode.values().length];

        static {
            try {
                $SwitchMap$com$jieli$adb$shell$server$InputServer$ETcpDecode[ETcpDecode.E_TCP_DECODE_HEAD1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$jieli$adb$shell$server$InputServer$ETcpDecode[ETcpDecode.E_TCP_DECODE_TYPE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$jieli$adb$shell$server$InputServer$ETcpDecode[ETcpDecode.E_TCP_DECODE_XY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void decode(byte[] bArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = AnonymousClass1.$SwitchMap$com$jieli$adb$shell$server$InputServer$ETcpDecode[this.m_eTcpDecode.ordinal()];
            if (i3 != 1) {
                if (i3 == 2) {
                    this.m_nMsgType = bArr[i2];
                    this.m_nDecodedMsgXY = 0;
                    this.m_eTcpDecode = ETcpDecode.E_TCP_DECODE_XY;
                } else if (i3 == 3) {
                    byte[] bArr2 = this.m_arrMsgXY;
                    int i4 = this.m_nDecodedMsgXY;
                    this.m_nDecodedMsgXY = i4 + 1;
                    bArr2[i4] = bArr[i2];
                    if (this.m_nDecodedMsgXY >= 4) {
                        this.m_eTcpDecode = ETcpDecode.E_TCP_DECODE_HEAD1;
                        byte[] bArr3 = this.m_arrMsgXY;
                        int i5 = ((bArr3[1] & 255) << 8) | (bArr3[0] & 255);
                        int i6 = ((bArr3[3] & 255) << 8) | (bArr3[2] & 255);
                        int i7 = this.m_nMsgType;
                        if (i7 < 6) {
                            this.m_sPointsInfo.point_down(i7, i5, i6);
                        } else if (i7 < 12) {
                            this.m_sPointsInfo.point_move(i7 - 6, i5, i6);
                        } else if (i7 < 18) {
                            this.m_sPointsInfo.point_up(i7 - 12, i5, i6);
                        }
                    }
                }
            } else if (-2 == bArr[i2]) {
                this.m_eTcpDecode = ETcpDecode.E_TCP_DECODE_TYPE;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0072 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x006a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void execute() {
        /*
            r7 = this;
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.String r1 = "InjectService: start success!"
            r0.println(r1)
            int[] r0 = com.jieli.adb.shell.server.InputServer.m_arrPorts
            int r1 = r0.length
            r2 = 0
            r3 = 0
            r4 = r2
        Ld:
            if (r3 >= r1) goto L33
            r4 = r0[r3]
            java.net.ServerSocket r5 = r7.creat_server_socket(r4)
            if (r5 == 0) goto L2f
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "Server listen success, port="
            r1.append(r3)
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
            r4 = r5
            goto L33
        L2f:
            int r3 = r3 + 1
            r4 = r5
            goto Ld
        L33:
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            r1 = 10
            r0.setPriority(r1)
            r0 = 100
            byte[] r0 = new byte[r0]
        L40:
            if (r4 == 0) goto Lbb
            java.net.Socket r1 = r4.accept()     // Catch: java.io.IOException -> L63
            java.io.PrintStream r3 = java.lang.System.out     // Catch: java.io.IOException -> L61
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.io.IOException -> L61
            r5.<init>()     // Catch: java.io.IOException -> L61
            java.lang.String r6 = "Client connected="
            r5.append(r6)     // Catch: java.io.IOException -> L61
            java.lang.String r6 = r1.toString()     // Catch: java.io.IOException -> L61
            r5.append(r6)     // Catch: java.io.IOException -> L61
            java.lang.String r5 = r5.toString()     // Catch: java.io.IOException -> L61
            r3.println(r5)     // Catch: java.io.IOException -> L61
            goto L68
        L61:
            r3 = move-exception
            goto L65
        L63:
            r3 = move-exception
            r1 = r2
        L65:
            r3.printStackTrace()
        L68:
            if (r1 != 0) goto L72
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.String r1 = "Accept client error"
            r0.println(r1)
            goto Lbb
        L72:
            java.io.InputStream r3 = r1.getInputStream()     // Catch: java.io.IOException -> L77
            goto L7c
        L77:
            r3 = move-exception
            r3.printStackTrace()
            r3 = r2
        L7c:
            if (r3 != 0) goto L86
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.String r1 = "client input error"
            r0.println(r1)
            goto Lbb
        L86:
            r5 = -1
            int r6 = r3.read(r0)     // Catch: java.io.IOException -> L8c
            goto L91
        L8c:
            r6 = move-exception
            r6.printStackTrace()
            r6 = -1
        L91:
            if (r6 != r5) goto Lb7
            com.jieli.adb.shell.server.CPointsInfo r5 = r7.m_sPointsInfo
            r5.all_point_up()
            java.io.PrintStream r5 = java.lang.System.out     // Catch: java.io.IOException -> La3
            java.lang.String r6 = "client is off, close socket input"
            r5.println(r6)     // Catch: java.io.IOException -> La3
            r3.close()     // Catch: java.io.IOException -> La3
            goto La7
        La3:
            r3 = move-exception
            r3.printStackTrace()
        La7:
            java.io.PrintStream r3 = java.lang.System.out     // Catch: java.io.IOException -> Lb2
            java.lang.String r5 = "client is off, close socket"
            r3.println(r5)     // Catch: java.io.IOException -> Lb2
            r1.close()     // Catch: java.io.IOException -> Lb2
            goto L40
        Lb2:
            r1 = move-exception
            r1.printStackTrace()
            goto L40
        Lb7:
            r7.decode(r0, r6)
            goto L86
        Lbb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.adb.shell.server.InputServer.execute():void");
    }
}
