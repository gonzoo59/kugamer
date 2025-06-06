package com.tencent.bugly.proguard;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import java.util.HashMap;
import java.util.Map;
/* compiled from: BUGLY */
/* loaded from: classes2.dex */
public final class ae {
    public static bu a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        bu buVar = new bu();
        buVar.a = userInfoBean.e;
        buVar.e = userInfoBean.j;
        buVar.d = userInfoBean.c;
        buVar.c = userInfoBean.d;
        buVar.h = userInfoBean.o == 1;
        int i = userInfoBean.b;
        if (i == 1) {
            buVar.b = (byte) 1;
        } else if (i == 2) {
            buVar.b = (byte) 4;
        } else if (i == 3) {
            buVar.b = (byte) 2;
        } else if (i == 4) {
            buVar.b = (byte) 3;
        } else if (i == 8) {
            buVar.b = (byte) 8;
        } else if (userInfoBean.b < 10 || userInfoBean.b >= 20) {
            al.e("unknown uinfo type %d ", Integer.valueOf(userInfoBean.b));
            return null;
        } else {
            buVar.b = (byte) userInfoBean.b;
        }
        buVar.f = new HashMap();
        if (userInfoBean.p >= 0) {
            Map<String, String> map = buVar.f;
            StringBuilder sb = new StringBuilder();
            sb.append(userInfoBean.p);
            map.put("C01", sb.toString());
        }
        if (userInfoBean.q >= 0) {
            Map<String, String> map2 = buVar.f;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(userInfoBean.q);
            map2.put("C02", sb2.toString());
        }
        if (userInfoBean.r != null && userInfoBean.r.size() > 0) {
            for (Map.Entry<String, String> entry : userInfoBean.r.entrySet()) {
                Map<String, String> map3 = buVar.f;
                map3.put("C03_" + entry.getKey(), entry.getValue());
            }
        }
        if (userInfoBean.s != null && userInfoBean.s.size() > 0) {
            for (Map.Entry<String, String> entry2 : userInfoBean.s.entrySet()) {
                Map<String, String> map4 = buVar.f;
                map4.put("C04_" + entry2.getKey(), entry2.getValue());
            }
        }
        Map<String, String> map5 = buVar.f;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(!userInfoBean.l);
        map5.put("A36", sb3.toString());
        Map<String, String> map6 = buVar.f;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(userInfoBean.g);
        map6.put("F02", sb4.toString());
        Map<String, String> map7 = buVar.f;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(userInfoBean.h);
        map7.put("F03", sb5.toString());
        Map<String, String> map8 = buVar.f;
        map8.put("F04", userInfoBean.j);
        Map<String, String> map9 = buVar.f;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(userInfoBean.i);
        map9.put("F05", sb6.toString());
        Map<String, String> map10 = buVar.f;
        map10.put("F06", userInfoBean.m);
        Map<String, String> map11 = buVar.f;
        StringBuilder sb7 = new StringBuilder();
        sb7.append(userInfoBean.k);
        map11.put("F10", sb7.toString());
        al.c("summary type %d vm:%d", Byte.valueOf(buVar.b), Integer.valueOf(buVar.f.size()));
        return buVar;
    }

    public static <T extends m> T a(byte[] bArr, Class<T> cls) {
        if (bArr != null && bArr.length > 0) {
            try {
                T newInstance = cls.newInstance();
                k kVar = new k(bArr);
                kVar.a("utf-8");
                newInstance.a(kVar);
                return newInstance;
            } catch (Throwable th) {
                if (!al.b(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    public static bq a(Context context, int i, byte[] bArr) {
        aa b = aa.b();
        StrategyBean c = ac.a().c();
        if (b == null || c == null) {
            al.e("Can not create request pkg for parameters is invalid.", new Object[0]);
            return null;
        }
        try {
            bq bqVar = new bq();
            synchronized (b) {
                bqVar.a = b.b;
                bqVar.b = b.e();
                bqVar.c = b.c;
                bqVar.d = b.o;
                bqVar.e = b.s;
                bqVar.f = b.h;
                bqVar.g = i;
                if (bArr == null) {
                    bArr = "".getBytes();
                }
                bqVar.h = bArr;
                bqVar.i = b.h();
                bqVar.j = b.k;
                bqVar.k = new HashMap();
                bqVar.l = b.d();
                bqVar.m = c.o;
                bqVar.o = b.g();
                bqVar.p = ab.c(context);
                bqVar.q = System.currentTimeMillis();
                bqVar.s = b.i();
                bqVar.v = b.g();
                bqVar.w = bqVar.p;
                b.getClass();
                bqVar.n = "com.tencent.bugly";
                Map<String, String> map = bqVar.k;
                map.put("A26", b.s());
                Map<String, String> map2 = bqVar.k;
                StringBuilder sb = new StringBuilder();
                sb.append(aa.C());
                map2.put("A62", sb.toString());
                Map<String, String> map3 = bqVar.k;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(aa.D());
                map3.put("A63", sb2.toString());
                Map<String, String> map4 = bqVar.k;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(b.J);
                map4.put("F11", sb3.toString());
                Map<String, String> map5 = bqVar.k;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(b.I);
                map5.put("F12", sb4.toString());
                Map<String, String> map6 = bqVar.k;
                map6.put("D3", b.q);
                if (p.b != null) {
                    for (o oVar : p.b) {
                        if (oVar.versionKey != null && oVar.version != null) {
                            bqVar.k.put(oVar.versionKey, oVar.version);
                        }
                    }
                }
                bqVar.k.put("G15", ap.d("G15", ""));
                bqVar.k.put("G10", ap.d("G10", ""));
                bqVar.k.put("D4", ap.d("D4", "0"));
            }
            Map<String, String> x = b.x();
            if (x != null) {
                for (Map.Entry<String, String> entry : x.entrySet()) {
                    if (!TextUtils.isEmpty(entry.getValue())) {
                        bqVar.k.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            return bqVar;
        } catch (Throwable th) {
            if (!al.b(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static byte[] a(Object obj) {
        try {
            e eVar = new e();
            eVar.b();
            eVar.a("utf-8");
            eVar.c();
            eVar.b("RqdServer");
            eVar.c("sync");
            eVar.a("detail", (String) obj);
            return eVar.a();
        } catch (Throwable th) {
            if (al.b(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    public static br a(byte[] bArr) {
        if (bArr != null) {
            try {
                e eVar = new e();
                eVar.b();
                eVar.a("utf-8");
                eVar.a(bArr);
                Object b = eVar.b("detail", new br());
                if (br.class.isInstance(b)) {
                    return (br) br.class.cast(b);
                }
                return null;
            } catch (Throwable th) {
                if (!al.b(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] a(m mVar) {
        try {
            l lVar = new l();
            lVar.a("utf-8");
            mVar.a(lVar);
            byte[] bArr = new byte[lVar.a.position()];
            System.arraycopy(lVar.a.array(), 0, bArr, 0, lVar.a.position());
            return bArr;
        } catch (Throwable th) {
            if (al.b(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }
}
