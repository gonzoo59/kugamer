package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import com.bumptech.glide.load.Key;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/* compiled from: BUGLY */
/* loaded from: classes2.dex */
public final class ao {
    public static boolean a = true;
    public static boolean b = true;
    private static SimpleDateFormat c = null;
    private static int d = 30720;
    private static StringBuilder e = null;
    private static StringBuilder f = null;
    private static boolean g = false;
    private static a h = null;
    private static String i = null;
    private static String j = null;
    private static Context k = null;
    private static String l = null;
    private static boolean m = false;
    private static boolean n = false;
    private static ExecutorService o;
    private static int p;
    private static final Object q = new Object();

    static {
        try {
            c = new SimpleDateFormat("MM-dd HH:mm:ss");
        } catch (Throwable th) {
            al.b(th.getCause());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean d(String str, String str2, String str3) {
        try {
            aa b2 = aa.b();
            if (b2 == null || b2.N == null) {
                return false;
            }
            return b2.N.appendLogToNative(str, str2, str3);
        } catch (Throwable th) {
            if (al.a(th)) {
                return false;
            }
            th.printStackTrace();
            return false;
        }
    }

    private static String b() {
        try {
            aa b2 = aa.b();
            if (b2 == null || b2.N == null) {
                return null;
            }
            return b2.N.getLogFromNative();
        } catch (Throwable th) {
            if (al.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    public static synchronized void a(Context context) {
        synchronized (ao.class) {
            if (m || context == null || !b) {
                return;
            }
            try {
                o = Executors.newSingleThreadExecutor();
                f = new StringBuilder(0);
                e = new StringBuilder(0);
                k = context;
                aa a2 = aa.a(context);
                i = a2.d;
                a2.getClass();
                j = "";
                l = k.getFilesDir().getPath() + "/buglylog_" + i + "_" + j + ".txt";
                p = Process.myPid();
            } catch (Throwable unused) {
            }
            m = true;
        }
    }

    public static void a(int i2) {
        synchronized (q) {
            d = i2;
            if (i2 < 0) {
                d = 0;
            } else if (i2 > 30720) {
                d = 30720;
            }
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (th == null) {
            return;
        }
        String message = th.getMessage();
        if (message == null) {
            message = "";
        }
        a(str, str2, message + '\n' + ap.b(th));
    }

    public static synchronized void a(final String str, final String str2, final String str3) {
        synchronized (ao.class) {
            if (m && b) {
                try {
                    if (n) {
                        o.execute(new Runnable() { // from class: com.tencent.bugly.proguard.ao.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ao.d(str, str2, str3);
                            }
                        });
                    } else {
                        o.execute(new Runnable() { // from class: com.tencent.bugly.proguard.ao.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                ao.e(str, str2, str3);
                            }
                        });
                    }
                } catch (Exception e2) {
                    al.b(e2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void e(String str, String str2, String str3) {
        synchronized (ao.class) {
            if (a) {
                f(str, str2, str3);
            } else {
                g(str, str2, str3);
            }
        }
    }

    private static synchronized void f(String str, String str2, String str3) {
        synchronized (ao.class) {
            String a2 = a(str, str2, str3, Process.myTid());
            synchronized (q) {
                f.append(a2);
                if (f.length() >= d) {
                    StringBuilder sb = f;
                    f = sb.delete(0, sb.indexOf("\u0001\r\n") + 1);
                }
            }
        }
    }

    private static synchronized void g(String str, String str2, String str3) {
        synchronized (ao.class) {
            String a2 = a(str, str2, str3, Process.myTid());
            synchronized (q) {
                try {
                    f.append(a2);
                } catch (Throwable unused) {
                }
                if (f.length() <= d) {
                    return;
                }
                if (g) {
                    return;
                }
                g = true;
                a aVar = h;
                if (aVar == null) {
                    h = new a(l);
                } else if (aVar.b == null || h.b.length() + f.length() > h.c) {
                    h.a();
                }
                if (h.a(f.toString())) {
                    f.setLength(0);
                    g = false;
                }
            }
        }
    }

    private static String a(String str, String str2, String str3, long j2) {
        String date;
        e.setLength(0);
        if (str3.length() > 30720) {
            str3 = str3.substring(str3.length() - 30720, str3.length() - 1);
        }
        Date date2 = new Date();
        SimpleDateFormat simpleDateFormat = c;
        if (simpleDateFormat != null) {
            date = simpleDateFormat.format(date2);
        } else {
            date = date2.toString();
        }
        StringBuilder sb = e;
        sb.append(date);
        sb.append(" ");
        sb.append(p);
        sb.append(" ");
        sb.append(j2);
        sb.append(" ");
        sb.append(str);
        sb.append(" ");
        sb.append(str2);
        sb.append(": ");
        sb.append(str3);
        sb.append("\u0001\r\n");
        return e.toString();
    }

    public static byte[] a() {
        if (!a) {
            return c();
        }
        if (b) {
            return ap.a(f.toString(), "BuglyLog.txt");
        }
        return null;
    }

    private static byte[] c() {
        if (b) {
            if (n) {
                al.a("[LogUtil] Get user log from native.", new Object[0]);
                String b2 = b();
                if (b2 != null) {
                    al.a("[LogUtil] Got user log from native: %d bytes", Integer.valueOf(b2.length()));
                    return ap.a(b2, "BuglyNativeLog.txt");
                }
            }
            StringBuilder sb = new StringBuilder();
            synchronized (q) {
                a aVar = h;
                if (aVar != null && aVar.a && h.b != null && h.b.length() > 0) {
                    sb.append(ap.a(h.b, 30720, true));
                }
                StringBuilder sb2 = f;
                if (sb2 != null && sb2.length() > 0) {
                    sb.append(f.toString());
                }
            }
            return ap.a(sb.toString(), "BuglyLog.txt");
        }
        return null;
    }

    /* compiled from: BUGLY */
    /* loaded from: classes2.dex */
    public static class a {
        boolean a;
        File b;
        long c = 30720;
        private String d;
        private long e;

        public a(String str) {
            if (str == null || str.equals("")) {
                return;
            }
            this.d = str;
            this.a = a();
        }

        final boolean a() {
            try {
                File file = new File(this.d);
                this.b = file;
                if (file.exists() && !this.b.delete()) {
                    this.a = false;
                    return false;
                } else if (this.b.createNewFile()) {
                    return true;
                } else {
                    this.a = false;
                    return false;
                }
            } catch (Throwable th) {
                al.a(th);
                this.a = false;
                return false;
            }
        }

        public final boolean a(String str) {
            byte[] bytes;
            if (!this.a) {
                return false;
            }
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(this.b, true);
                try {
                    fileOutputStream2.write(str.getBytes(Key.STRING_CHARSET_NAME));
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                    this.e += bytes.length;
                    this.a = true;
                    try {
                        fileOutputStream2.close();
                    } catch (IOException unused) {
                    }
                    return true;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    try {
                        al.a(th);
                        this.a = false;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException unused2) {
                            }
                        }
                        return false;
                    } catch (Throwable th2) {
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException unused3) {
                            }
                        }
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
    }
}
