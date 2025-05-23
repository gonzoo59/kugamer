package com.baidu.kwgames.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.baidu.kwgames.AppInstance;
import com.baidu.kwgames.R;
import com.baidu.kwgames.util.Download;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
/* loaded from: classes.dex */
public class DownLoadDialog extends AlertDialog {
    private DownloadListener mDownloadListener;
    private BaseDownloadTask mDownloadTask;
    private ProgressBar mProgressBar;
    private View mRoot;
    private TextView mTextProgress;
    private String mUrl;

    /* loaded from: classes.dex */
    public interface DownloadListener {
        void onFinish(String str);
    }

    public DownLoadDialog(Context context) {
        super(context, R.style.FullWidthDialog);
        this.mUrl = "";
    }

    public DownLoadDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
        this.mUrl = "";
    }

    public DownLoadDialog(Context context, int i) {
        super(context, R.style.FullWidthDialog);
        this.mUrl = "";
    }

    private void setStyle() {
        Window window = getWindow();
        window.setGravity(17);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = -1;
        attributes.height = -1;
        window.setAttributes(attributes);
    }

    @Override // android.app.Dialog
    public void show() {
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(1024);
        }
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.layoutInDisplayCutoutMode = 1;
            getWindow().setAttributes(attributes);
        }
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        super.show();
        startDownLoad();
    }

    public void setUrl(String str) {
        this.mUrl = str;
    }

    public void setDownLoadFinish(DownloadListener downloadListener) {
        this.mDownloadListener = downloadListener;
    }

    private void startDownLoad() {
        BaseDownloadTask createTask = Download.createTask(this.mUrl, AppInstance.get().getExternalFilesDir(null).getAbsolutePath());
        this.mDownloadTask = createTask;
        createTask.setListener(new FileDownloadLargeFileListener() { // from class: com.baidu.kwgames.dialog.DownLoadDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.liulishuo.filedownloader.FileDownloadListener
            public void error(BaseDownloadTask baseDownloadTask, Throwable th) {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.liulishuo.filedownloader.FileDownloadLargeFileListener
            public void paused(BaseDownloadTask baseDownloadTask, long j, long j2) {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.liulishuo.filedownloader.FileDownloadLargeFileListener
            public void pending(BaseDownloadTask baseDownloadTask, long j, long j2) {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.liulishuo.filedownloader.FileDownloadListener
            public void warn(BaseDownloadTask baseDownloadTask) {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.liulishuo.filedownloader.FileDownloadLargeFileListener
            public void progress(BaseDownloadTask baseDownloadTask, long j, long j2) {
                if (DownLoadDialog.this.mRoot.getVisibility() != 0) {
                    DownLoadDialog.this.mRoot.setVisibility(0);
                }
                int i = (int) ((((float) j) / ((float) j2)) * 100.0f);
                TextView textView = DownLoadDialog.this.mTextProgress;
                textView.setText(i + "%");
                DownLoadDialog.this.mProgressBar.setProgress(i);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.liulishuo.filedownloader.FileDownloadListener
            public void completed(BaseDownloadTask baseDownloadTask) {
                try {
                    DownLoadDialog.this.mTextProgress.setText("100%");
                    DownLoadDialog.this.mProgressBar.setProgress(100);
                    if (DownLoadDialog.this.mDownloadListener != null) {
                        DownLoadDialog.this.mDownloadListener.onFinish(baseDownloadTask.getTargetFilePath());
                    }
                    DownLoadDialog.this.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        BaseDownloadTask baseDownloadTask = this.mDownloadTask;
        if (baseDownloadTask != null) {
            baseDownloadTask.pause();
        }
        super.dismiss();
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle();
        setContentView(R.layout.dialog_download);
        this.mTextProgress = (TextView) findViewById(R.id.text_progress);
        this.mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        this.mRoot = findViewById(R.id.root);
        this.mTextProgress.setText("");
        this.mProgressBar.setProgress(0);
    }
}
