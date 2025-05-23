package com.jieli.jl_bt_ota.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;
/* loaded from: classes2.dex */
public class JL_Log {
    private static String SAVE_LOG_PATH = null;
    private static boolean isLog = true;
    private static boolean isSaveLogFile = false;
    private static Context mContext;
    private static SaveLogFileThread mSaveLogFileThread;
    private static final SimpleDateFormat yyyyMMdd_HHmmssSSS = new SimpleDateFormat("yyyyMMddHHmmss.SSS", Locale.getDefault());

    static /* synthetic */ String access$200() {
        return currentTimeString();
    }

    public static boolean isIsLog() {
        return isLog;
    }

    public static void setLog(boolean z) {
        isLog = z;
    }

    public static void setIsSaveLogFile(Context context, boolean z) {
        isSaveLogFile = z;
        if (z) {
            openLogFileStream(context);
        } else {
            closeLogFile();
        }
    }

    public static boolean getSaveLogFile() {
        return isSaveLogFile;
    }

    public static void d(String str, String str2) {
        if (isLog) {
            Log.d("ota:" + str, str2);
        }
        saveLogInFile("d", "ota:" + str, str2);
    }

    public static void i(String str, String str2) {
        if (isLog) {
            Log.i("ota:" + str, str2);
        }
        saveLogInFile("i", "ota:" + str, str2);
    }

    public static void w(String str, String str2) {
        if (isLog) {
            Log.w("ota:" + str, str2);
        }
        saveLogInFile("w", "ota:" + str, str2);
    }

    public static void e(String str, String str2) {
        if (isLog) {
            Log.e("ota:" + str, str2);
        }
        saveLogInFile("e", "ota:" + str, str2);
    }

    private static String currentTimeString() {
        return yyyyMMdd_HHmmssSSS.format(Calendar.getInstance().getTime());
    }

    private static void openLogFileStream(Context context) {
        SaveLogFileThread saveLogFileThread = mSaveLogFileThread;
        if (saveLogFileThread == null || !saveLogFileThread.isSaving) {
            if (mContext == null) {
                if (context == null) {
                    context = CommonUtil.getMainContext();
                }
                mContext = context;
            }
            SaveLogFileThread saveLogFileThread2 = new SaveLogFileThread(mContext);
            mSaveLogFileThread = saveLogFileThread2;
            saveLogFileThread2.start();
        }
    }

    private static String formatLog(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append(currentTimeString());
        sb.append("   ");
        sb.append(str);
        sb.append("   ");
        if (str2 == null) {
            str2 = "null";
        }
        sb.append(str2);
        sb.append(" :  ");
        if (str3 == null) {
            str3 = "null";
        }
        sb.append(str3);
        sb.append("\n");
        return sb.toString();
    }

    private static void saveLogInFile(String str, String str2, String str3) {
        if (isSaveLogFile) {
            if (mSaveLogFileThread == null) {
                openLogFileStream(mContext);
            }
            SaveLogFileThread saveLogFileThread = mSaveLogFileThread;
            if (saveLogFileThread != null) {
                saveLogFileThread.addLog(formatLog(str, str2, str3).getBytes());
            }
        }
    }

    private static void closeLogFile() {
        SaveLogFileThread saveLogFileThread = mSaveLogFileThread;
        if (saveLogFileThread != null) {
            saveLogFileThread.closeSaveFile();
            mSaveLogFileThread = null;
        }
        mContext = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SaveLogFileThread extends Thread {
        private long fileSize;
        private volatile boolean isSaving;
        private volatile boolean isWaiting;
        private FileOutputStream mLogFileOutputStream;
        private final LinkedBlockingQueue<byte[]> mQueue;

        public SaveLogFileThread(Context context) {
            super("SaveLogFileThread");
            this.mQueue = new LinkedBlockingQueue<>();
            if (context != null) {
                if (TextUtils.isEmpty(JL_Log.SAVE_LOG_PATH)) {
                    String unused = JL_Log.SAVE_LOG_PATH = FileUtil.splicingFilePath(context, context.getPackageName(), "logcat", null, null) + "/ota_log_app_" + JL_Log.access$200() + ".txt";
                }
                try {
                    this.mLogFileOutputStream = new FileOutputStream(JL_Log.SAVE_LOG_PATH, true);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0010  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void addLog(byte[] r2) {
            /*
                r1 = this;
                if (r2 == 0) goto Ld
                java.util.concurrent.LinkedBlockingQueue<byte[]> r0 = r1.mQueue     // Catch: java.lang.InterruptedException -> L9
                r0.put(r2)     // Catch: java.lang.InterruptedException -> L9
                r2 = 1
                goto Le
            L9:
                r2 = move-exception
                r2.printStackTrace()
            Ld:
                r2 = 0
            Le:
                if (r2 == 0) goto L13
                r1.wakeupSaveThread()
            L13:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.util.JL_Log.SaveLogFileThread.addLog(byte[]):void");
        }

        @Override // java.lang.Thread
        public synchronized void start() {
            this.fileSize = 0L;
            this.isSaving = true;
            super.start();
        }

        public synchronized void closeSaveFile() {
            this.isSaving = false;
            wakeupSaveThread();
        }

        private void wakeupSaveThread() {
            if (this.isWaiting) {
                synchronized (this.mQueue) {
                    this.mQueue.notify();
                }
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            FileOutputStream fileOutputStream;
            synchronized (this.mQueue) {
                while (true) {
                    if (!this.isSaving) {
                        break;
                    }
                    if (this.mQueue.isEmpty()) {
                        this.isWaiting = true;
                        try {
                            this.mQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        this.isWaiting = false;
                        byte[] poll = this.mQueue.poll();
                        if (poll != null && (fileOutputStream = this.mLogFileOutputStream) != null) {
                            try {
                                fileOutputStream.write(poll);
                                this.fileSize += poll.length;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                            if (this.fileSize >= 314572800) {
                                this.isSaving = false;
                                break;
                            }
                        }
                    }
                }
            }
            this.isSaving = false;
            this.isWaiting = false;
            this.mQueue.clear();
            FileOutputStream fileOutputStream2 = this.mLogFileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            SaveLogFileThread unused = JL_Log.mSaveLogFileThread = null;
        }
    }
}
