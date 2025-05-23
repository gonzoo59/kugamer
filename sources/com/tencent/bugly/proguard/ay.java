package com.tencent.bugly.proguard;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.FileObserver;
import android.text.TextUtils;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.anr.TraceFileHelper;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
/* compiled from: BUGLY */
/* loaded from: classes2.dex */
public final class ay {
    public static ay f;
    public final ActivityManager b;
    final aa c;
    final ak d;
    String e;
    private final Context g;
    private final ac h;
    private final as i;
    private FileObserver k;
    private bg m;
    private int n;
    public final AtomicBoolean a = new AtomicBoolean(false);
    private final Object j = new Object();
    private boolean l = true;
    private long o = 0;

    public static synchronized ay a() {
        ay ayVar;
        synchronized (ay.class) {
            ayVar = f;
        }
        return ayVar;
    }

    public ay(Context context, ac acVar, aa aaVar, ak akVar, as asVar) {
        Context a = ap.a(context);
        this.g = a;
        this.b = (ActivityManager) a.getSystemService("activity");
        if (ap.b(NativeCrashHandler.getDumpFilePath())) {
            this.e = context.getDir("bugly", 0).getAbsolutePath();
        } else {
            this.e = NativeCrashHandler.getDumpFilePath();
        }
        this.c = aaVar;
        this.d = akVar;
        this.h = acVar;
        this.i = asVar;
    }

    private CrashDetailBean a(ax axVar) {
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        try {
            crashDetailBean.C = ab.j();
            crashDetailBean.D = ab.f();
            crashDetailBean.E = ab.l();
            crashDetailBean.F = this.c.k();
            crashDetailBean.G = this.c.j();
            crashDetailBean.H = this.c.l();
            crashDetailBean.I = ab.b(this.g);
            crashDetailBean.J = ab.g();
            crashDetailBean.K = ab.h();
            crashDetailBean.b = 3;
            crashDetailBean.e = this.c.g();
            crashDetailBean.f = this.c.o;
            crashDetailBean.g = this.c.q();
            crashDetailBean.m = this.c.f();
            crashDetailBean.n = "ANR_EXCEPTION";
            crashDetailBean.o = axVar.f;
            crashDetailBean.q = axVar.g;
            crashDetailBean.T = new HashMap();
            crashDetailBean.T.put("BUGLY_CR_01", axVar.e);
            int indexOf = crashDetailBean.q != null ? crashDetailBean.q.indexOf("\n") : -1;
            crashDetailBean.p = indexOf > 0 ? crashDetailBean.q.substring(0, indexOf) : "GET_FAIL";
            crashDetailBean.r = axVar.c;
            if (crashDetailBean.q != null) {
                crashDetailBean.u = ap.c(crashDetailBean.q.getBytes());
            }
            crashDetailBean.z = axVar.b;
            crashDetailBean.A = axVar.a;
            crashDetailBean.B = "main(1)";
            crashDetailBean.L = this.c.s();
            crashDetailBean.h = this.c.p();
            crashDetailBean.i = this.c.A();
            crashDetailBean.v = axVar.d;
            crashDetailBean.P = this.c.u;
            crashDetailBean.Q = this.c.a;
            crashDetailBean.R = this.c.a();
            crashDetailBean.U = this.c.z();
            crashDetailBean.V = this.c.x;
            crashDetailBean.W = this.c.t();
            crashDetailBean.X = this.c.y();
            crashDetailBean.y = ao.a();
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
        }
        return crashDetailBean;
    }

    private static boolean a(String str, String str2, String str3) {
        TraceFileHelper.a readTargetDumpInfo = TraceFileHelper.readTargetDumpInfo(str3, str, true);
        if (readTargetDumpInfo == null || readTargetDumpInfo.d == null || readTargetDumpInfo.d.isEmpty()) {
            al.e("not found trace dump for %s", str3);
            return false;
        }
        StringBuilder sb = new StringBuilder(1024);
        String[] strArr = readTargetDumpInfo.d.get("main");
        if (strArr != null && strArr.length >= 3) {
            sb.append("\"main\" tid=");
            sb.append(strArr[2]);
            sb.append(" :\n");
            sb.append(strArr[0]);
            sb.append("\n");
            sb.append(strArr[1]);
            sb.append("\n\n");
        }
        for (Map.Entry<String, String[]> entry : readTargetDumpInfo.d.entrySet()) {
            if (!entry.getKey().equals("main") && entry.getValue() != null && entry.getValue().length >= 3) {
                sb.append("\"");
                sb.append(entry.getKey());
                sb.append("\" tid=");
                sb.append(entry.getValue()[2]);
                sb.append(" :\n");
                sb.append(entry.getValue()[0]);
                sb.append("\n");
                sb.append(entry.getValue()[1]);
                sb.append("\n\n");
            }
        }
        return am.a(str2, sb.toString(), sb.length() * 2);
    }

    private static String a(List<ba> list, long j) {
        if (list == null || list.isEmpty()) {
            return "main thread stack not enable";
        }
        StringBuilder sb = new StringBuilder(4096);
        sb.append("\n>>>>> 以下为anr过程中主线程堆栈记录，可根据堆栈出现次数推测在该堆栈阻塞的时间，出现次数越多对anr贡献越大，越可能是造成anr的原因 >>>>>\n");
        sb.append("\n>>>>> Thread Stack Traces Records Start >>>>>\n");
        for (int i = 0; i < list.size(); i++) {
            ba baVar = list.get(i);
            sb.append("Thread name:");
            sb.append(baVar.a);
            sb.append("\n");
            long j2 = baVar.b - j;
            String str = j2 <= 0 ? "before " : "after ";
            sb.append("Got ");
            sb.append(str);
            sb.append("anr:");
            sb.append(Math.abs(j2));
            sb.append("ms\n");
            sb.append(baVar.c);
            sb.append("\n");
            if (sb.length() * 2 >= 101376) {
                break;
            }
        }
        sb.append("\n<<<<< Thread Stack Traces Records End <<<<<\n");
        return sb.toString();
    }

    public final boolean a(boolean z) {
        boolean compareAndSet = this.a.compareAndSet(!z, z);
        al.c("tryChangeAnrState to %s, success:%s", Boolean.valueOf(z), Boolean.valueOf(compareAndSet));
        return compareAndSet;
    }

    private synchronized void c() {
        if (e()) {
            al.d("start when started!", new Object[0]);
            return;
        }
        FileObserver fileObserver = new FileObserver("/data/anr/") { // from class: com.tencent.bugly.proguard.ay.1
            @Override // android.os.FileObserver
            public final void onEvent(int i, String str) {
                if (str == null) {
                    return;
                }
                final String concat = "/data/anr/".concat(String.valueOf(str));
                al.d("watching file %s", concat);
                if (!concat.contains("trace")) {
                    al.d("not anr file %s", concat);
                } else {
                    ay.this.d.a(new Runnable() { // from class: com.tencent.bugly.proguard.ay.1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ay ayVar = ay.this;
                            String str2 = concat;
                            if (ayVar.a(true)) {
                                try {
                                    al.c("read trace first dump for create time!", new Object[0]);
                                    TraceFileHelper.a readFirstDumpInfo = TraceFileHelper.readFirstDumpInfo(str2, false);
                                    long j = readFirstDumpInfo != null ? readFirstDumpInfo.c : -1L;
                                    if (j == -1) {
                                        al.d("trace dump fail could not get time!", new Object[0]);
                                        j = System.currentTimeMillis();
                                    }
                                    if (ayVar.a(j)) {
                                        return;
                                    }
                                    ayVar.a(j, str2);
                                } catch (Throwable th) {
                                    if (!al.a(th)) {
                                        th.printStackTrace();
                                    }
                                    al.e("handle anr error %s", th.getClass().toString());
                                }
                            }
                        }
                    });
                }
            }
        };
        this.k = fileObserver;
        fileObserver.startWatching();
        al.a("start anr monitor!", new Object[0]);
        this.d.a(new Runnable() { // from class: com.tencent.bugly.proguard.ay.2
            @Override // java.lang.Runnable
            public final void run() {
                ay.a(ay.this);
            }
        });
    }

    private synchronized void d() {
        if (!e()) {
            al.d("close when closed!", new Object[0]);
            return;
        }
        this.k.stopWatching();
        this.k = null;
        al.d("close anr monitor!", new Object[0]);
    }

    private synchronized boolean e() {
        return this.k != null;
    }

    private synchronized void c(boolean z) {
        if (Build.VERSION.SDK_INT <= 19) {
            if (z) {
                c();
            } else {
                d();
            }
        } else if (z) {
            g();
        } else {
            h();
        }
    }

    private synchronized boolean f() {
        return this.l;
    }

    private synchronized void d(boolean z) {
        if (this.l != z) {
            al.a("user change anr %b", Boolean.valueOf(z));
            this.l = z;
        }
    }

    public final void b(boolean z) {
        d(z);
        boolean f2 = f();
        ac a = ac.a();
        if (a != null) {
            f2 = f2 && a.c().f;
        }
        if (f2 != e()) {
            al.a("anr changed to %b", Boolean.valueOf(f2));
            c(f2);
        }
    }

    public final synchronized void b() {
        al.d("customer decides whether to open or close.", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0069 A[Catch: all -> 0x0211, TryCatch #3 {all -> 0x0211, blocks: (B:3:0x0007, B:4:0x0017, B:9:0x0029, B:11:0x0049, B:13:0x004f, B:18:0x0059, B:21:0x0069, B:24:0x007a, B:27:0x0085, B:29:0x008e, B:31:0x0099, B:33:0x009d, B:35:0x00a2, B:37:0x00a6, B:39:0x00ab, B:41:0x00b9, B:42:0x00bd, B:46:0x00ef, B:48:0x010e, B:51:0x011b, B:53:0x0144, B:55:0x0172, B:56:0x0175, B:62:0x0183, B:63:0x0194, B:69:0x01a5, B:71:0x01b6, B:73:0x01c5, B:77:0x01e3, B:79:0x01e9, B:80:0x01ee, B:81:0x01f5, B:72:0x01be, B:54:0x015c, B:68:0x019e, B:45:0x00e9, B:30:0x0091, B:22:0x0072, B:82:0x01f6, B:84:0x01fa, B:85:0x0206, B:57:0x0176, B:59:0x017a, B:60:0x0180, B:5:0x0018, B:7:0x001c, B:8:0x0028), top: B:103:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007a A[Catch: all -> 0x0211, TRY_LEAVE, TryCatch #3 {all -> 0x0211, blocks: (B:3:0x0007, B:4:0x0017, B:9:0x0029, B:11:0x0049, B:13:0x004f, B:18:0x0059, B:21:0x0069, B:24:0x007a, B:27:0x0085, B:29:0x008e, B:31:0x0099, B:33:0x009d, B:35:0x00a2, B:37:0x00a6, B:39:0x00ab, B:41:0x00b9, B:42:0x00bd, B:46:0x00ef, B:48:0x010e, B:51:0x011b, B:53:0x0144, B:55:0x0172, B:56:0x0175, B:62:0x0183, B:63:0x0194, B:69:0x01a5, B:71:0x01b6, B:73:0x01c5, B:77:0x01e3, B:79:0x01e9, B:80:0x01ee, B:81:0x01f5, B:72:0x01be, B:54:0x015c, B:68:0x019e, B:45:0x00e9, B:30:0x0091, B:22:0x0072, B:82:0x01f6, B:84:0x01fa, B:85:0x0206, B:57:0x0176, B:59:0x017a, B:60:0x0180, B:5:0x0018, B:7:0x001c, B:8:0x0028), top: B:103:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0085 A[Catch: all -> 0x0211, TRY_ENTER, TryCatch #3 {all -> 0x0211, blocks: (B:3:0x0007, B:4:0x0017, B:9:0x0029, B:11:0x0049, B:13:0x004f, B:18:0x0059, B:21:0x0069, B:24:0x007a, B:27:0x0085, B:29:0x008e, B:31:0x0099, B:33:0x009d, B:35:0x00a2, B:37:0x00a6, B:39:0x00ab, B:41:0x00b9, B:42:0x00bd, B:46:0x00ef, B:48:0x010e, B:51:0x011b, B:53:0x0144, B:55:0x0172, B:56:0x0175, B:62:0x0183, B:63:0x0194, B:69:0x01a5, B:71:0x01b6, B:73:0x01c5, B:77:0x01e3, B:79:0x01e9, B:80:0x01ee, B:81:0x01f5, B:72:0x01be, B:54:0x015c, B:68:0x019e, B:45:0x00e9, B:30:0x0091, B:22:0x0072, B:82:0x01f6, B:84:0x01fa, B:85:0x0206, B:57:0x0176, B:59:0x017a, B:60:0x0180, B:5:0x0018, B:7:0x001c, B:8:0x0028), top: B:103:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01b6 A[Catch: all -> 0x0211, TryCatch #3 {all -> 0x0211, blocks: (B:3:0x0007, B:4:0x0017, B:9:0x0029, B:11:0x0049, B:13:0x004f, B:18:0x0059, B:21:0x0069, B:24:0x007a, B:27:0x0085, B:29:0x008e, B:31:0x0099, B:33:0x009d, B:35:0x00a2, B:37:0x00a6, B:39:0x00ab, B:41:0x00b9, B:42:0x00bd, B:46:0x00ef, B:48:0x010e, B:51:0x011b, B:53:0x0144, B:55:0x0172, B:56:0x0175, B:62:0x0183, B:63:0x0194, B:69:0x01a5, B:71:0x01b6, B:73:0x01c5, B:77:0x01e3, B:79:0x01e9, B:80:0x01ee, B:81:0x01f5, B:72:0x01be, B:54:0x015c, B:68:0x019e, B:45:0x00e9, B:30:0x0091, B:22:0x0072, B:82:0x01f6, B:84:0x01fa, B:85:0x0206, B:57:0x0176, B:59:0x017a, B:60:0x0180, B:5:0x0018, B:7:0x001c, B:8:0x0028), top: B:103:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01be A[Catch: all -> 0x0211, TryCatch #3 {all -> 0x0211, blocks: (B:3:0x0007, B:4:0x0017, B:9:0x0029, B:11:0x0049, B:13:0x004f, B:18:0x0059, B:21:0x0069, B:24:0x007a, B:27:0x0085, B:29:0x008e, B:31:0x0099, B:33:0x009d, B:35:0x00a2, B:37:0x00a6, B:39:0x00ab, B:41:0x00b9, B:42:0x00bd, B:46:0x00ef, B:48:0x010e, B:51:0x011b, B:53:0x0144, B:55:0x0172, B:56:0x0175, B:62:0x0183, B:63:0x0194, B:69:0x01a5, B:71:0x01b6, B:73:0x01c5, B:77:0x01e3, B:79:0x01e9, B:80:0x01ee, B:81:0x01f5, B:72:0x01be, B:54:0x015c, B:68:0x019e, B:45:0x00e9, B:30:0x0091, B:22:0x0072, B:82:0x01f6, B:84:0x01fa, B:85:0x0206, B:57:0x0176, B:59:0x017a, B:60:0x0180, B:5:0x0018, B:7:0x001c, B:8:0x0028), top: B:103:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01e9 A[Catch: all -> 0x0211, TryCatch #3 {all -> 0x0211, blocks: (B:3:0x0007, B:4:0x0017, B:9:0x0029, B:11:0x0049, B:13:0x004f, B:18:0x0059, B:21:0x0069, B:24:0x007a, B:27:0x0085, B:29:0x008e, B:31:0x0099, B:33:0x009d, B:35:0x00a2, B:37:0x00a6, B:39:0x00ab, B:41:0x00b9, B:42:0x00bd, B:46:0x00ef, B:48:0x010e, B:51:0x011b, B:53:0x0144, B:55:0x0172, B:56:0x0175, B:62:0x0183, B:63:0x0194, B:69:0x01a5, B:71:0x01b6, B:73:0x01c5, B:77:0x01e3, B:79:0x01e9, B:80:0x01ee, B:81:0x01f5, B:72:0x01be, B:54:0x015c, B:68:0x019e, B:45:0x00e9, B:30:0x0091, B:22:0x0072, B:82:0x01f6, B:84:0x01fa, B:85:0x0206, B:57:0x0176, B:59:0x017a, B:60:0x0180, B:5:0x0018, B:7:0x001c, B:8:0x0028), top: B:103:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01f6 A[Catch: all -> 0x020b, TRY_ENTER, TryCatch #3 {all -> 0x0211, blocks: (B:3:0x0007, B:4:0x0017, B:9:0x0029, B:11:0x0049, B:13:0x004f, B:18:0x0059, B:21:0x0069, B:24:0x007a, B:27:0x0085, B:29:0x008e, B:31:0x0099, B:33:0x009d, B:35:0x00a2, B:37:0x00a6, B:39:0x00ab, B:41:0x00b9, B:42:0x00bd, B:46:0x00ef, B:48:0x010e, B:51:0x011b, B:53:0x0144, B:55:0x0172, B:56:0x0175, B:62:0x0183, B:63:0x0194, B:69:0x01a5, B:71:0x01b6, B:73:0x01c5, B:77:0x01e3, B:79:0x01e9, B:80:0x01ee, B:81:0x01f5, B:72:0x01be, B:54:0x015c, B:68:0x019e, B:45:0x00e9, B:30:0x0091, B:22:0x0072, B:82:0x01f6, B:84:0x01fa, B:85:0x0206, B:57:0x0176, B:59:0x017a, B:60:0x0180, B:5:0x0018, B:7:0x001c, B:8:0x0028), top: B:103:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void a(long r19, java.lang.String r21) {
        /*
            Method dump skipped, instructions count: 543
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ay.a(long, java.lang.String):void");
    }

    private synchronized void g() {
        if (e()) {
            al.d("start when started!", new Object[0]);
        } else if (TextUtils.isEmpty(this.e)) {
        } else {
            synchronized (this.j) {
                bg bgVar = this.m;
                if (bgVar == null || !bgVar.isAlive()) {
                    bg bgVar2 = new bg();
                    this.m = bgVar2;
                    boolean z = this.c.S;
                    bgVar2.b = z;
                    al.c("set record stack trace enable:".concat(String.valueOf(z)), new Object[0]);
                    bg bgVar3 = this.m;
                    StringBuilder sb = new StringBuilder("Bugly-ThreadMonitor");
                    int i = this.n;
                    this.n = i + 1;
                    sb.append(i);
                    bgVar3.setName(sb.toString());
                    this.m.b();
                }
            }
            FileObserver fileObserver = new FileObserver(this.e) { // from class: com.tencent.bugly.proguard.ay.3
                @Override // android.os.FileObserver
                public final void onEvent(int i2, String str) {
                    if (str == null) {
                        return;
                    }
                    boolean z2 = true;
                    al.d("observe file, dir:%s fileName:%s", ay.this.e, str);
                    if (!((str.startsWith("manual_bugly_trace_") && str.endsWith(".txt")) ? false : false)) {
                        al.c("not manual trace file, ignore.", new Object[0]);
                    } else if (!ay.this.a.get()) {
                        al.c("proc is not in anr, just ignore", new Object[0]);
                    } else if (ay.this.c.a()) {
                        al.c("Found foreground anr, resend sigquit immediately.", new Object[0]);
                        NativeCrashHandler.getInstance().resendSigquit();
                        long a = am.a(str, "manual_bugly_trace_", ".txt");
                        ay ayVar = ay.this;
                        ayVar.a(a, ay.this.e + "/" + str);
                        al.c("Finish handling one anr.", new Object[0]);
                    } else {
                        al.c("Found background anr, resend sigquit later.", new Object[0]);
                        long a2 = am.a(str, "manual_bugly_trace_", ".txt");
                        ay ayVar2 = ay.this;
                        ayVar2.a(a2, ay.this.e + "/" + str);
                        al.c("Finish handling one anr, now resend sigquit.", new Object[0]);
                        NativeCrashHandler.getInstance().resendSigquit();
                    }
                }
            };
            this.k = fileObserver;
            fileObserver.startWatching();
            al.a("startWatchingPrivateAnrDir! dumFilePath is %s", this.e);
            this.d.a(new Runnable() { // from class: com.tencent.bugly.proguard.ay.4
                @Override // java.lang.Runnable
                public final void run() {
                    ay.a(ay.this);
                }
            });
        }
    }

    public final boolean a(long j) {
        if (Math.abs(j - this.o) < 10000) {
            al.d("should not process ANR too Fre in %dms", 10000);
            return true;
        }
        this.o = j;
        return false;
    }

    private synchronized void h() {
        if (!e()) {
            al.d("close when closed!", new Object[0]);
            return;
        }
        synchronized (this.j) {
            bg bgVar = this.m;
            if (bgVar != null) {
                bgVar.a();
                this.m = null;
            }
        }
        al.a("stopWatchingPrivateAnrDir", new Object[0]);
        this.k.stopWatching();
        this.k = null;
        al.d("close anr monitor!", new Object[0]);
    }

    static /* synthetic */ void a(ay ayVar) {
        long currentTimeMillis = (at.j + System.currentTimeMillis()) - ap.b();
        am.a(ayVar.e, "bugly_trace_", ".txt", currentTimeMillis);
        am.a(ayVar.e, "manual_bugly_trace_", ".txt", currentTimeMillis);
        am.a(ayVar.e, "main_stack_record_", ".txt", currentTimeMillis);
        am.a(ayVar.e, "main_stack_record_", ".txt.merged", currentTimeMillis);
    }
}
