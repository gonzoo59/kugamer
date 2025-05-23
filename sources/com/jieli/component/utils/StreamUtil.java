package com.jieli.component.utils;

import android.text.TextUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
/* loaded from: classes2.dex */
public class StreamUtil {
    public static void closeStream(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileInputStream getFileInputStream(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new RuntimeException("输入路径不能为空");
        }
        File file = new File(str);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getFileInputStream(file);
    }

    public static FileInputStream getFileInputStream(File file) {
        if (file.isDirectory()) {
            throw new RuntimeException("输入路径不能为文件夹路径");
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            return new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long readInputStreamLen(InputStream inputStream) {
        if (inputStream == null) {
            return -1L;
        }
        int i = -1;
        try {
            i = inputStream.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }
}
