package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/* compiled from: BUGLY */
/* loaded from: classes2.dex */
public final class w {
    public static boolean a = false;
    private static w b;
    private static x c;

    private w(Context context, List<o> list) {
        c = new x(context, list);
    }

    public static synchronized w a(Context context, List<o> list) {
        w wVar;
        synchronized (w.class) {
            if (b == null) {
                b = new w(context, list);
            }
            wVar = b;
        }
        return wVar;
    }

    public static synchronized w a() {
        w wVar;
        synchronized (w.class) {
            wVar = b;
        }
        return wVar;
    }

    public final Cursor a(String str, String[] strArr, String str2) {
        return a(str, strArr, str2, (String) null, (String) null);
    }

    public final Cursor a(String str, String[] strArr, String str2, String str3, String str4) {
        return a(false, str, strArr, str2, null, null, null, str3, str4, null);
    }

    public final int a(String str, String str2) {
        return a(str, str2, (String[]) null, (v) null);
    }

    public final synchronized long a(String str, ContentValues contentValues, v vVar) {
        long j;
        j = -1;
        SQLiteDatabase writableDatabase = c.getWritableDatabase();
        if (writableDatabase != null && contentValues != null) {
            long replace = writableDatabase.replace(str, FileDownloadModel.ID, contentValues);
            if (replace >= 0) {
                al.c("[Database] insert %s success.", str);
            } else {
                al.d("[Database] replace %s error.", str);
            }
            j = replace;
        }
        if (vVar != null) {
            Long.valueOf(j);
        }
        if (a && writableDatabase != null) {
            writableDatabase.close();
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized Cursor a(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, v vVar) {
        Cursor cursor;
        cursor = null;
        try {
            SQLiteDatabase writableDatabase = c.getWritableDatabase();
            if (writableDatabase != null) {
                cursor = writableDatabase.query(z, str, strArr, str2, strArr2, str3, str4, str5, str6);
            }
        } finally {
            try {
                return cursor;
            } finally {
            }
        }
        return cursor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized int a(String str, String str2, String[] strArr, v vVar) {
        int delete;
        SQLiteDatabase writableDatabase = c.getWritableDatabase();
        delete = writableDatabase != null ? writableDatabase.delete(str, str2, strArr) : 0;
        if (vVar != null) {
            Integer.valueOf(delete);
        }
        if (a && writableDatabase != null) {
            writableDatabase.close();
        }
        return delete;
    }

    public final boolean a(int i, String str, byte[] bArr, boolean z) {
        if (!z) {
            a aVar = new a();
            aVar.a(i, str, bArr);
            ak.a().a(aVar);
            return true;
        }
        return a(i, str, bArr, (v) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(int i, String str, byte[] bArr, v vVar) {
        try {
            y yVar = new y();
            yVar.a = i;
            yVar.f = str;
            yVar.e = System.currentTimeMillis();
            yVar.g = bArr;
            boolean b2 = b(yVar);
            if (vVar != null) {
                Boolean.valueOf(b2);
                return b2;
            }
            return b2;
        } catch (Throwable th) {
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                return false;
            } finally {
                if (vVar != null) {
                    Boolean bool = Boolean.FALSE;
                }
            }
        }
    }

    public final Map<String, byte[]> a(int i, v vVar) {
        HashMap hashMap = null;
        try {
            List<y> c2 = c(i);
            if (c2 != null) {
                HashMap hashMap2 = new HashMap();
                try {
                    for (y yVar : c2) {
                        byte[] bArr = yVar.g;
                        if (bArr != null) {
                            hashMap2.put(yVar.f, bArr);
                        }
                    }
                    return hashMap2;
                } catch (Throwable th) {
                    th = th;
                    hashMap = hashMap2;
                    if (al.a(th)) {
                        return hashMap;
                    }
                    th.printStackTrace();
                    return hashMap;
                }
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final synchronized boolean a(y yVar) {
        ContentValues c2;
        SQLiteDatabase writableDatabase = c.getWritableDatabase();
        if (writableDatabase == null || (c2 = c(yVar)) == null) {
            if (a && writableDatabase != null) {
                writableDatabase.close();
            }
            return false;
        }
        long replace = writableDatabase.replace("t_lr", FileDownloadModel.ID, c2);
        if (replace < 0) {
            if (a && writableDatabase != null) {
                writableDatabase.close();
            }
            return false;
        }
        al.c("[Database] insert %s success.", "t_lr");
        yVar.a = replace;
        if (a && writableDatabase != null) {
            writableDatabase.close();
        }
        return true;
    }

    private synchronized boolean b(y yVar) {
        ContentValues d;
        SQLiteDatabase writableDatabase = c.getWritableDatabase();
        if (writableDatabase == null || (d = d(yVar)) == null) {
            if (a && writableDatabase != null) {
                writableDatabase.close();
            }
            return false;
        }
        long replace = writableDatabase.replace("t_pf", FileDownloadModel.ID, d);
        if (replace < 0) {
            if (a && writableDatabase != null) {
                writableDatabase.close();
            }
            return false;
        }
        al.c("[Database] insert %s success.", "t_pf");
        yVar.a = replace;
        if (a && writableDatabase != null) {
            writableDatabase.close();
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00b3 A[Catch: all -> 0x00c5, TRY_LEAVE, TryCatch #1 {, blocks: (B:3:0x0001, B:14:0x002d, B:15:0x0030, B:18:0x0036, B:35:0x009b, B:36:0x009e, B:39:0x00a4, B:48:0x00b8, B:49:0x00bb, B:52:0x00c1, B:44:0x00ad, B:46:0x00b3), top: B:66:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b8 A[Catch: all -> 0x00d7, TRY_ENTER, TryCatch #1 {, blocks: (B:3:0x0001, B:14:0x002d, B:15:0x0030, B:18:0x0036, B:35:0x009b, B:36:0x009e, B:39:0x00a4, B:48:0x00b8, B:49:0x00bb, B:52:0x00c1, B:44:0x00ad, B:46:0x00b3), top: B:66:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final synchronized java.util.List<com.tencent.bugly.proguard.y> a(int r12) {
        /*
            Method dump skipped, instructions count: 218
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.w.a(int):java.util.List");
    }

    public final synchronized void a(List<y> list) {
        if (list != null) {
            if (list.size() != 0) {
                SQLiteDatabase writableDatabase = c.getWritableDatabase();
                if (writableDatabase != null) {
                    StringBuilder sb = new StringBuilder();
                    for (y yVar : list) {
                        sb.append(" or _id = ");
                        sb.append(yVar.a);
                    }
                    String sb2 = sb.toString();
                    if (sb2.length() > 0) {
                        sb2 = sb2.substring(4);
                    }
                    sb.setLength(0);
                    al.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", sb2, null)));
                    if (a) {
                        writableDatabase.close();
                    }
                }
            }
        }
    }

    public final synchronized void b(int i) {
        SQLiteDatabase writableDatabase = c.getWritableDatabase();
        if (writableDatabase != null) {
            al.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", i >= 0 ? "_tp = ".concat(String.valueOf(i)) : null, null)));
            if (a && writableDatabase != null) {
                writableDatabase.close();
            }
        }
    }

    private static ContentValues c(y yVar) {
        if (yVar == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (yVar.a > 0) {
                contentValues.put(FileDownloadModel.ID, Long.valueOf(yVar.a));
            }
            contentValues.put("_tp", Integer.valueOf(yVar.b));
            contentValues.put("_pc", yVar.c);
            contentValues.put("_th", yVar.d);
            contentValues.put("_tm", Long.valueOf(yVar.e));
            if (yVar.g != null) {
                contentValues.put("_dt", yVar.g);
            }
            return contentValues;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static y a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            y yVar = new y();
            yVar.a = cursor.getLong(cursor.getColumnIndex(FileDownloadModel.ID));
            yVar.b = cursor.getInt(cursor.getColumnIndex("_tp"));
            yVar.c = cursor.getString(cursor.getColumnIndex("_pc"));
            yVar.d = cursor.getString(cursor.getColumnIndex("_th"));
            yVar.e = cursor.getLong(cursor.getColumnIndex("_tm"));
            yVar.g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return yVar;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ae, code lost:
        if (r1 != null) goto L9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b0, code lost:
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00cb, code lost:
        if (r1 != null) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private synchronized java.util.List<com.tencent.bugly.proguard.y> c(int r12) {
        /*
            Method dump skipped, instructions count: 226
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.w.c(int):java.util.List");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean a(int i, String str, v vVar) {
        boolean z;
        String str2;
        SQLiteDatabase sQLiteDatabase = null;
        z = false;
        try {
            SQLiteDatabase writableDatabase = c.getWritableDatabase();
            if (writableDatabase != null) {
                try {
                    if (ap.b(str)) {
                        str2 = "_id = ".concat(String.valueOf(i));
                    } else {
                        str2 = "_id = " + i + " and _tp = \"" + str + "\"";
                    }
                    int delete = writableDatabase.delete("t_pf", str2, null);
                    al.c("[Database] deleted %s data %d", "t_pf", Integer.valueOf(delete));
                    if (delete > 0) {
                        z = true;
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLiteDatabase = writableDatabase;
                    if (!al.a(th)) {
                        th.printStackTrace();
                    }
                    if (vVar != null) {
                        Boolean bool = Boolean.FALSE;
                    }
                    if (a && sQLiteDatabase != null) {
                        sQLiteDatabase.close();
                    }
                    return z;
                }
            }
            if (vVar != null) {
                Boolean.valueOf(z);
            }
            if (a && writableDatabase != null) {
                writableDatabase.close();
            }
        } catch (Throwable th2) {
            th = th2;
        }
        return z;
    }

    private static ContentValues d(y yVar) {
        if (yVar != null && !ap.b(yVar.f)) {
            try {
                ContentValues contentValues = new ContentValues();
                if (yVar.a > 0) {
                    contentValues.put(FileDownloadModel.ID, Long.valueOf(yVar.a));
                }
                contentValues.put("_tp", yVar.f);
                contentValues.put("_tm", Long.valueOf(yVar.e));
                if (yVar.g != null) {
                    contentValues.put("_dt", yVar.g);
                }
                return contentValues;
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    private static y b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            y yVar = new y();
            yVar.a = cursor.getLong(cursor.getColumnIndex(FileDownloadModel.ID));
            yVar.e = cursor.getLong(cursor.getColumnIndex("_tm"));
            yVar.f = cursor.getString(cursor.getColumnIndex("_tp"));
            yVar.g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return yVar;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: BUGLY */
    /* loaded from: classes2.dex */
    public class a extends Thread {
        private int b = 4;
        private v c = null;
        private String d;
        private ContentValues e;
        private boolean f;
        private String[] g;
        private String h;
        private String[] i;
        private String j;
        private String k;
        private String l;
        private String m;
        private String n;
        private String[] o;
        private int p;
        private String q;
        private byte[] r;

        public a() {
        }

        public final void a(int i, String str, byte[] bArr) {
            this.p = i;
            this.q = str;
            this.r = bArr;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            switch (this.b) {
                case 1:
                    w.this.a(this.d, this.e, this.c);
                    return;
                case 2:
                    w.this.a(this.d, this.n, this.o, this.c);
                    return;
                case 3:
                    Cursor a = w.this.a(this.f, this.d, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.c);
                    if (a != null) {
                        a.close();
                        return;
                    }
                    return;
                case 4:
                    w.this.a(this.p, this.q, this.r, this.c);
                    return;
                case 5:
                    w.this.a(this.p, this.c);
                    return;
                case 6:
                    w.this.a(this.p, this.q, this.c);
                    return;
                default:
                    return;
            }
        }
    }
}
