package com.baidu.kwgames.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import androidx.core.content.FileProvider;
import com.baidu.kwgames.R;
import java.io.File;
import org.opencv.videoio.Videoio;
/* loaded from: classes.dex */
public class ShareWechatFriend {
    public static void shareFile(Context context, File file) {
        Uri fromFile;
        checkFileUriExposure();
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
        intent.addFlags(3);
        if (file != null) {
            try {
                if (context.getApplicationInfo().targetSdkVersion >= 24 && Build.VERSION.SDK_INT >= 24) {
                    fromFile = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", file);
                } else {
                    fromFile = Uri.fromFile(file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            intent.putExtra("android.intent.extra.STREAM", fromFile);
            intent.setType("*/*");
            context.startActivity(Intent.createChooser(intent, NEAT.s(R.string.share_setting)));
        }
        fromFile = null;
        intent.putExtra("android.intent.extra.STREAM", fromFile);
        intent.setType("*/*");
        context.startActivity(Intent.createChooser(intent, NEAT.s(R.string.share_setting)));
    }

    private static void checkFileUriExposure() {
        if (Build.VERSION.SDK_INT >= 18) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }
}
