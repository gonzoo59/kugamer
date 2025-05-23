package com.jieli.component.network;
/* loaded from: classes2.dex */
public class NetWorkUtil {
    public static final String ACTION_JIELI_NET_WORK_STATE_CHANGE = "jieli_network_state_Change";
    private static final String[] ADDRESSES = {"www.baidu.com", "www.qq.com", "www.aliyun.com", "www.fmprc.gov.cn"};
    public static final String EXTRA_DATA_NETWORK_IS_AVAILABLE = "isavailable";
    public static final String EXTRA_DATA_NETWORK_MODE = "networkmode";
    public static final int MOBILE_MODE = 2;
    public static final int UNKONW_MODE = 0;
    public static final int WIFI_MODE = 1;
    private static Thread mWorkThread = null;
    private static String tag = "NetWorkUtil";

    /* loaded from: classes2.dex */
    public interface NetStateCheckCallback {
        void onBack(boolean z);
    }

    public static boolean checkNetworkIsAvailable() {
        int i = 0;
        while (true) {
            try {
                String[] strArr = ADDRESSES;
                if (i >= strArr.length) {
                    break;
                }
                boolean checkNetworkIsAvailable = checkNetworkIsAvailable(strArr[i]);
                if (checkNetworkIsAvailable) {
                    return checkNetworkIsAvailable;
                }
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x007d, code lost:
        if (r1 == null) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0080, code lost:
        if (r2 != 0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0082, code lost:
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:?, code lost:
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean checkNetworkIsAvailable(java.lang.String r10) {
        /*
            r0 = 0
            r1 = 0
            r2 = -1
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            r4.<init>()     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            java.lang.String r5 = "/system/bin/ping  -c 1 -w 1000 "
            r4.append(r5)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            r4.append(r10)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            java.lang.Process r1 = r3.exec(r4)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            r3 = 0
        L1d:
            r4 = 5
            if (r3 >= r4) goto L71
            java.util.Date r4 = new java.util.Date     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            r4.<init>()     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            long r4 = r4.getTime()     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            int r2 = r1.waitFor()     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            java.lang.String r6 = com.jieli.component.network.NetWorkUtil.tag     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            r7.<init>()     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            java.lang.String r8 = "-checkNetworkIsAvailable-->address="
            r7.append(r8)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            r7.append(r10)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            java.lang.String r8 = "\ttake time="
            r7.append(r8)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            java.util.Date r8 = new java.util.Date     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            r8.<init>()     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            long r8 = r8.getTime()     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            long r8 = r8 - r4
            r7.append(r8)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            java.lang.String r4 = "\tstate:"
            r7.append(r4)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            r7.append(r2)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            java.lang.String r4 = "\ti="
            r7.append(r4)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            r7.append(r3)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            java.lang.String r4 = r7.toString()     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            com.jieli.component.Logcat.d(r6, r4)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            if (r2 != 0) goto L68
            goto L71
        L68:
            int r4 = r3 * 20
            long r4 = (long) r4     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            java.lang.Thread.sleep(r4)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            int r3 = r3 + 1
            goto L1d
        L71:
            if (r1 == 0) goto L80
        L73:
            r1.destroy()
            goto L80
        L77:
            r10 = move-exception
            goto L84
        L79:
            r10 = move-exception
            r10.printStackTrace()     // Catch: java.lang.Throwable -> L77
            if (r1 == 0) goto L80
            goto L73
        L80:
            if (r2 != 0) goto L83
            r0 = 1
        L83:
            return r0
        L84:
            if (r1 == 0) goto L89
            r1.destroy()
        L89:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.component.network.NetWorkUtil.checkNetworkIsAvailable(java.lang.String):boolean");
    }

    public static void checkNetworkIsAvailable(final NetStateCheckCallback netStateCheckCallback) {
        if (mWorkThread == null) {
            Thread thread = new Thread() { // from class: com.jieli.component.network.NetWorkUtil.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    String[] strArr = NetWorkUtil.ADDRESSES;
                    int length = strArr.length;
                    boolean z = false;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        } else if (NetWorkUtil.checkNetworkIsAvailable(strArr[i])) {
                            z = true;
                            break;
                        } else {
                            i++;
                        }
                    }
                    NetStateCheckCallback.this.onBack(z);
                    Thread unused = NetWorkUtil.mWorkThread = null;
                }
            };
            mWorkThread = thread;
            thread.start();
        }
    }
}
