package com.tencent.bugly.crashreport.crash.h5;

import android.webkit.JavascriptInterface;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.inner.InnerApi;
import com.tencent.bugly.proguard.al;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.bb;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;
/* compiled from: BUGLY */
/* loaded from: classes2.dex */
public class H5JavaScriptInterface {
    private static HashSet<Integer> a = new HashSet<>();
    private String b = null;
    private Thread c = null;
    private String d = null;
    private Map<String, String> e = null;

    private H5JavaScriptInterface() {
    }

    public static H5JavaScriptInterface getInstance(CrashReport.a aVar) {
        String str = null;
        if (aVar == null || a.contains(Integer.valueOf(aVar.hashCode()))) {
            return null;
        }
        H5JavaScriptInterface h5JavaScriptInterface = new H5JavaScriptInterface();
        a.add(Integer.valueOf(aVar.hashCode()));
        Thread currentThread = Thread.currentThread();
        h5JavaScriptInterface.c = currentThread;
        if (currentThread != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            for (int i = 2; i < currentThread.getStackTrace().length; i++) {
                StackTraceElement stackTraceElement = currentThread.getStackTrace()[i];
                if (!stackTraceElement.toString().contains("crashreport")) {
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
            }
            str = sb.toString();
        }
        h5JavaScriptInterface.d = str;
        HashMap hashMap = new HashMap();
        StringBuilder sb2 = new StringBuilder();
        sb2.append((Object) aVar.c());
        hashMap.put("[WebView] ContentDescription", sb2.toString());
        h5JavaScriptInterface.e = hashMap;
        return h5JavaScriptInterface;
    }

    private static bb a(String str) {
        String string;
        if (str != null && str.length() > 0) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                bb bbVar = new bb();
                bbVar.a = jSONObject.getString("projectRoot");
                if (bbVar.a == null) {
                    return null;
                }
                bbVar.b = jSONObject.getString("context");
                if (bbVar.b == null) {
                    return null;
                }
                bbVar.c = jSONObject.getString(FileDownloadModel.URL);
                if (bbVar.c == null) {
                    return null;
                }
                bbVar.d = jSONObject.getString("userAgent");
                if (bbVar.d == null) {
                    return null;
                }
                bbVar.e = jSONObject.getString("language");
                if (bbVar.e == null) {
                    return null;
                }
                bbVar.f = jSONObject.getString("name");
                if (bbVar.f == null || bbVar.f.equals("null") || (string = jSONObject.getString("stacktrace")) == null) {
                    return null;
                }
                int indexOf = string.indexOf("\n");
                if (indexOf < 0) {
                    al.d("H5 crash stack's format is wrong!", new Object[0]);
                    return null;
                }
                bbVar.h = string.substring(indexOf + 1);
                bbVar.g = string.substring(0, indexOf);
                int indexOf2 = bbVar.g.indexOf(":");
                if (indexOf2 > 0) {
                    bbVar.g = bbVar.g.substring(indexOf2 + 1);
                }
                bbVar.i = jSONObject.getString("file");
                if (bbVar.f == null) {
                    return null;
                }
                bbVar.j = jSONObject.getLong("lineNumber");
                if (bbVar.j < 0) {
                    return null;
                }
                bbVar.k = jSONObject.getLong("columnNumber");
                if (bbVar.k < 0) {
                    return null;
                }
                al.a("H5 crash information is following: ", new Object[0]);
                al.a("[projectRoot]: " + bbVar.a, new Object[0]);
                al.a("[context]: " + bbVar.b, new Object[0]);
                al.a("[url]: " + bbVar.c, new Object[0]);
                al.a("[userAgent]: " + bbVar.d, new Object[0]);
                al.a("[language]: " + bbVar.e, new Object[0]);
                al.a("[name]: " + bbVar.f, new Object[0]);
                al.a("[message]: " + bbVar.g, new Object[0]);
                al.a("[stacktrace]: \n" + bbVar.h, new Object[0]);
                al.a("[file]: " + bbVar.i, new Object[0]);
                al.a("[lineNumber]: " + bbVar.j, new Object[0]);
                al.a("[columnNumber]: " + bbVar.k, new Object[0]);
                return bbVar;
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    @JavascriptInterface
    public void printLog(String str) {
        al.d("Log from js: %s", str);
    }

    @JavascriptInterface
    public void reportJSException(String str) {
        if (str == null) {
            al.d("Payload from JS is null.", new Object[0]);
            return;
        }
        String c = ap.c(str.getBytes());
        String str2 = this.b;
        if (str2 != null && str2.equals(c)) {
            al.d("Same payload from js. Please check whether you've injected bugly.js more than one times.", new Object[0]);
            return;
        }
        this.b = c;
        al.d("Handling JS exception ...", new Object[0]);
        bb a2 = a(str);
        if (a2 == null) {
            al.d("Failed to parse payload.", new Object[0]);
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        if (a2.a != null) {
            linkedHashMap2.put("[JS] projectRoot", a2.a);
        }
        if (a2.b != null) {
            linkedHashMap2.put("[JS] context", a2.b);
        }
        if (a2.c != null) {
            linkedHashMap2.put("[JS] url", a2.c);
        }
        if (a2.d != null) {
            linkedHashMap2.put("[JS] userAgent", a2.d);
        }
        if (a2.i != null) {
            linkedHashMap2.put("[JS] file", a2.i);
        }
        if (a2.j != 0) {
            linkedHashMap2.put("[JS] lineNumber", Long.toString(a2.j));
        }
        linkedHashMap.putAll(linkedHashMap2);
        linkedHashMap.putAll(this.e);
        linkedHashMap.put("Java Stack", this.d);
        Thread thread = this.c;
        if (a2 != null) {
            InnerApi.postH5CrashAsync(thread, a2.f, a2.g, a2.h, linkedHashMap);
        }
    }
}
