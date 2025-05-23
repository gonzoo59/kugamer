package com.jieli.jl_bt_ota.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/* loaded from: classes2.dex */
public class FileUtil {
    public static final int FILE_TYPE_PIC = 1;
    public static final int FILE_TYPE_UNKNOWN = 0;
    public static final int FILE_TYPE_VIDEO = 2;
    private static String TAG = "FileUtil";

    public static boolean checkFileExist(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return new File(str).exists();
    }

    public static int judgeFileType(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        if (str.endsWith(".png") || str.endsWith(".PNG") || str.endsWith(".JPEG") || str.endsWith(".jpeg") || str.endsWith(".jpg") || str.endsWith(".JPG")) {
            return 1;
        }
        return (str.endsWith(".mov") || str.endsWith(".MOV") || str.endsWith(".mp4") || str.endsWith(".MP4") || str.endsWith(".avi") || str.endsWith(".AVI")) ? 2 : 0;
    }

    public static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            if (file.delete()) {
                JL_Log.i(TAG, "delete file success!");
            }
        } else if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                if (file.delete()) {
                    JL_Log.i(TAG, "delete empty file success!");
                    return;
                }
                return;
            }
            for (File file2 : listFiles) {
                deleteFile(file2);
            }
            if (file.delete()) {
                JL_Log.i(TAG, "delete empty file success!");
            }
        }
    }

    public static boolean bitmapToFile(Bitmap bitmap, String str, int i) {
        FileOutputStream fileOutputStream;
        if (bitmap != null && !TextUtils.isEmpty(str)) {
            FileOutputStream fileOutputStream2 = null;
            try {
                try {
                    fileOutputStream = new FileOutputStream(str);
                } catch (IOException e) {
                    e = e;
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, i, fileOutputStream);
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return true;
                } catch (IOException e2) {
                    e2.printStackTrace();
                    return true;
                }
            } catch (IOException e3) {
                e = e3;
                fileOutputStream2 = fileOutputStream;
                e.printStackTrace();
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.flush();
                        fileOutputStream2.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                return false;
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream2 = fileOutputStream;
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.flush();
                        fileOutputStream2.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                throw th;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v4, types: [byte[]] */
    public static byte[] getFromRaw(Context context, int i) {
        byte[] bArr;
        InputStream openRawResource;
        InputStream inputStream = 0;
        r0 = null;
        byte[] bArr2 = null;
        InputStream inputStream2 = null;
        try {
            try {
                openRawResource = context.getResources().openRawResource(i);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
            bArr = null;
        }
        try {
            Runtime runtime = Runtime.getRuntime();
            int i2 = 512000;
            if (runtime != null && runtime.freeMemory() < 512000) {
                i2 = (int) runtime.freeMemory();
            }
            byte[] bArr3 = new byte[i2];
            byte[] bArr4 = new byte[1024];
            int i3 = 0;
            while (true) {
                int read = openRawResource.read(bArr4, 0, 1024);
                if (read < 0) {
                    break;
                }
                int i4 = i3 + read;
                if (i4 <= i2) {
                    System.arraycopy(bArr4, 0, bArr3, i3, read);
                    i3 = i4;
                }
            }
            if (i3 > 0) {
                bArr2 = new byte[i3];
                System.arraycopy(bArr3, 0, bArr2, 0, i3);
            }
            if (openRawResource != null) {
                try {
                    openRawResource.close();
                    return bArr2;
                } catch (IOException e2) {
                    e2.printStackTrace();
                    return bArr2;
                }
            }
            return bArr2;
        } catch (Exception e3) {
            e = e3;
            byte[] bArr5 = bArr2;
            inputStream2 = openRawResource;
            bArr = bArr5;
            e.printStackTrace();
            if (inputStream2 != null) {
                try {
                    inputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            inputStream = bArr;
            return inputStream;
        } catch (Throwable th2) {
            th = th2;
            inputStream = openRawResource;
            if (inputStream != 0) {
                try {
                    inputStream.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean bytesToFile(byte[] bArr, String str) {
        FileOutputStream fileOutputStream;
        if (bArr == null || TextUtils.isEmpty(str)) {
            return false;
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(str);
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
        }
        try {
            fileOutputStream.write(bArr);
            try {
                fileOutputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return true;
        } catch (IOException e3) {
            e = e3;
            fileOutputStream2 = fileOutputStream;
            e.printStackTrace();
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                    return false;
                } catch (IOException e4) {
                    e4.printStackTrace();
                    return false;
                }
            }
            return false;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
    }

    private static String getRootPath(Context context) {
        File externalFilesDir;
        String path = Environment.getExternalStorageDirectory().getPath();
        return (Build.VERSION.SDK_INT < 29 || context == null || (externalFilesDir = context.getExternalFilesDir(null)) == null) ? path : externalFilesDir.getPath();
    }

    public static String splicingFilePath(Context context, String str, String str2, String str3, String str4) {
        String[] split;
        String rootPath = getRootPath(context);
        if (!TextUtils.isEmpty(str)) {
            if (str.contains(File.separator)) {
                for (String str5 : str.split(File.separator)) {
                    if (!TextUtils.isEmpty(str5)) {
                        rootPath = rootPath + File.separator + str5;
                        File file = new File(rootPath);
                        if (!file.exists() && file.mkdir()) {
                            JL_Log.w(TAG, "create root dir success! path : " + rootPath);
                        }
                    }
                }
            } else {
                rootPath = rootPath + File.separator + str;
                File file2 = new File(rootPath);
                if (!file2.exists() && file2.mkdir()) {
                    JL_Log.w(TAG, "create root dir success! path : " + rootPath);
                }
            }
            if (TextUtils.isEmpty(str2)) {
                return rootPath;
            }
            String str6 = rootPath + File.separator + str2;
            File file3 = new File(str6);
            if (!file3.exists() && file3.mkdir()) {
                JL_Log.w(TAG, "create one dir success!");
            }
            if (TextUtils.isEmpty(str3)) {
                return str6;
            }
            String str7 = str6 + File.separator + str3;
            File file4 = new File(str7);
            if (!file4.exists() && file4.mkdir()) {
                JL_Log.w(TAG, "create two dir success!");
            }
            if (TextUtils.isEmpty(str4)) {
                return str7;
            }
            rootPath = str7 + File.separator + str4;
            File file5 = new File(rootPath);
            if (!file5.exists() && file5.mkdir()) {
                JL_Log.w(TAG, "create three sub dir success!");
            }
        }
        return rootPath;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v11, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v14, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.CharSequence, java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v9 */
    public static void copyFromAssetsToSdcard(Context context, boolean z, String str, String str2) {
        FileOutputStream fileOutputStream;
        IOException e;
        FileNotFoundException e2;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        File file = new File(str2);
        if (!z && (z || file.exists())) {
            return;
        }
        try {
            try {
                try {
                    context = context.getResources().getAssets().open(str);
                    try {
                        fileOutputStream = new FileOutputStream(str2);
                        try {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int read = context.read(bArr, 0, 1024);
                                if (read >= 0) {
                                    fileOutputStream.write(bArr, 0, read);
                                } else {
                                    try {
                                        break;
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                    }
                                }
                            }
                            fileOutputStream.close();
                        } catch (FileNotFoundException e4) {
                            e2 = e4;
                            e2.printStackTrace();
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                            }
                            if (context != 0) {
                                context.close();
                            }
                            return;
                        } catch (IOException e6) {
                            e = e6;
                            e.printStackTrace();
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e7) {
                                    e7.printStackTrace();
                                }
                            }
                            if (context != 0) {
                                context.close();
                            }
                            return;
                        }
                    } catch (FileNotFoundException e8) {
                        fileOutputStream = null;
                        e2 = e8;
                    } catch (IOException e9) {
                        fileOutputStream = null;
                        e = e9;
                    } catch (Throwable th) {
                        str = 0;
                        th = th;
                        if (str != 0) {
                            try {
                                str.close();
                            } catch (IOException e10) {
                                e10.printStackTrace();
                            }
                        }
                        if (context != 0) {
                            try {
                                context.close();
                            } catch (IOException e11) {
                                e11.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e12) {
                    e12.printStackTrace();
                    return;
                }
            } catch (FileNotFoundException e13) {
                fileOutputStream = null;
                e2 = e13;
                context = 0;
            } catch (IOException e14) {
                fileOutputStream = null;
                e = e14;
                context = 0;
            } catch (Throwable th2) {
                str = 0;
                th = th2;
                context = 0;
            }
            if (context != 0) {
                context.close();
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r6v10, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v8, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r6v9, types: [java.io.FileInputStream] */
    public static byte[] getBytes(String str) {
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] bArr = null;
        if (str == 0 || str.isEmpty()) {
            return null;
        }
        ?? file = new File((String) str);
        try {
            try {
                try {
                    str = new FileInputStream((File) file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e2) {
                e = e2;
                str = 0;
                byteArrayOutputStream = null;
            } catch (IOException e3) {
                e = e3;
                str = 0;
                byteArrayOutputStream = null;
            } catch (Throwable th) {
                file = 0;
                th = th;
                str = 0;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byte[] bArr2 = new byte[1024];
                while (true) {
                    int read = str.read(bArr2);
                    if (read != -1) {
                        byteArrayOutputStream.write(bArr2, 0, read);
                    } else {
                        try {
                            break;
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                }
                byteArrayOutputStream.close();
                bArr = byteArrayOutputStream.toByteArray();
                str.close();
            } catch (FileNotFoundException e5) {
                e = e5;
                e.printStackTrace();
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                    bArr = byteArrayOutputStream.toByteArray();
                }
                if (str != 0) {
                    str.close();
                }
                return bArr;
            } catch (IOException e7) {
                e = e7;
                e.printStackTrace();
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                    bArr = byteArrayOutputStream.toByteArray();
                }
                if (str != 0) {
                    str.close();
                }
                return bArr;
            }
        } catch (FileNotFoundException e9) {
            e = e9;
            byteArrayOutputStream = null;
        } catch (IOException e10) {
            e = e10;
            byteArrayOutputStream = null;
        } catch (Throwable th3) {
            file = 0;
            th = th3;
            if (file != 0) {
                try {
                    file.close();
                } catch (IOException e11) {
                    e11.printStackTrace();
                }
                file.toByteArray();
            }
            if (str != 0) {
                try {
                    str.close();
                } catch (IOException e12) {
                    e12.printStackTrace();
                }
            }
            throw th;
        }
        return bArr;
    }
}
