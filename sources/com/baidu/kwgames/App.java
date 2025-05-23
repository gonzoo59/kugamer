package com.baidu.kwgames;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import com.jieli.otasdk.MainApplication;
import com.liulishuo.filedownloader.FileDownloader;
import com.lzf.easyfloat.EasyFloat;
import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.exceptions.BleException;
import com.tencent.bugly.crashreport.CrashReport;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
/* loaded from: classes.dex */
public class App extends Application {
    private RxBleClient rxBleClient;

    public static RxBleClient getRxBleClient(Context context) {
        return ((App) context.getApplicationContext()).rxBleClient;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        FileDownloader.setup(this);
        AppInstance.setApp(this);
        MainApplication.setApp(this);
        EasyFloat.init(this, false);
        this.rxBleClient = RxBleClient.create(this);
        RxJavaPlugins.setErrorHandler(new Consumer() { // from class: com.baidu.kwgames.App$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                App.lambda$onCreate$0((Throwable) obj);
            }
        });
        CrashReport.initCrashReport(getApplicationContext(), "3ffdcbfdcb", true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onCreate$0(Throwable th) throws Exception {
        if ((th instanceof UndeliverableException) && (th.getCause() instanceof BleException)) {
            Log.v("App", "Suppressed UndeliverableException: " + th.toString());
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }
}
