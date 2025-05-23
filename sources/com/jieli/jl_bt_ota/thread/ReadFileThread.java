package com.jieli.jl_bt_ota.thread;

import android.os.Handler;
import android.os.Looper;
import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.interfaces.IActionCallback;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.util.FileUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/* loaded from: classes2.dex */
public class ReadFileThread extends Thread {
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final IActionCallback<byte[]> mReadFileCallback;
    private final String mUpgradeFilePath;

    public ReadFileThread(String str, IActionCallback<byte[]> iActionCallback) {
        this.mUpgradeFilePath = str;
        this.mReadFileCallback = iActionCallback;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        FileInputStream fileInputStream;
        IOException e;
        FileNotFoundException e2;
        JL_Log.d("ReadFileThread", "mUpgradeFilePath : " + this.mUpgradeFilePath);
        if (!FileUtil.checkFileExist(this.mUpgradeFilePath)) {
            notifyError(new BaseError(ErrorCode.SUB_ERR_FILE_NOT_FOUND, "file path not exist."));
            return;
        }
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            try {
                fileInputStream = new FileInputStream(this.mUpgradeFilePath);
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            try {
                byte[] bArr = new byte[fileInputStream.available()];
                fileInputStream.read(bArr);
                notifySuccess(bArr);
                fileInputStream.close();
            } catch (FileNotFoundException e4) {
                e2 = e4;
                e2.printStackTrace();
                notifyError(new BaseError(ErrorCode.SUB_ERR_FILE_NOT_FOUND, "file not found"));
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e5) {
                e = e5;
                e.printStackTrace();
                notifyError(new BaseError(ErrorCode.SUB_ERR_IO_EXCEPTION, e.getMessage()));
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }
        } catch (FileNotFoundException e6) {
            fileInputStream = null;
            e2 = e6;
        } catch (IOException e7) {
            fileInputStream = null;
            e = e7;
        } catch (Throwable th2) {
            fileInputStream = null;
            th = th2;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e8) {
                    e8.printStackTrace();
                }
            }
            throw th;
        }
    }

    private void notifySuccess(final byte[] bArr) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.thread.ReadFileThread.1
            @Override // java.lang.Runnable
            public void run() {
                if (ReadFileThread.this.mReadFileCallback != null) {
                    ReadFileThread.this.mReadFileCallback.onSuccess(bArr);
                }
            }
        });
    }

    private void notifyError(final BaseError baseError) {
        if (baseError != null) {
            this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.thread.ReadFileThread.2
                @Override // java.lang.Runnable
                public void run() {
                    if (ReadFileThread.this.mReadFileCallback != null) {
                        ReadFileThread.this.mReadFileCallback.onError(baseError);
                    }
                }
            });
        }
    }
}
