package com.lzf.easyfloat.widget.appfloat;

import android.content.Context;
import com.lzf.easyfloat.EasyFloatMessageKt;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.utils.Logger;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
/* compiled from: FloatManager.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0007\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\rJ\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0014J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00072\b\u0010\u0013\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0016\u001a\u00020\u00042\b\u0010\u0013\u001a\u0004\u0018\u00010\u0004J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004J'\u0010\u0019\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b¢\u0006\u0002\u0010\u001dJ+\u0010\u001e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001f\u001a\u00020\u000b2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010 \u001a\u00020\u000b¢\u0006\u0002\u0010!R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\""}, d2 = {"Lcom/lzf/easyfloat/widget/appfloat/FloatManager;", "", "()V", "DEFAULT_TAG", "", "floatMap", "", "Lcom/lzf/easyfloat/widget/appfloat/AppFloatManager;", "getFloatMap", "()Ljava/util/Map;", "checkTag", "", "config", "Lcom/lzf/easyfloat/data/FloatConfig;", "create", "", "context", "Landroid/content/Context;", "dismiss", "tag", "(Ljava/lang/String;)Lkotlin/Unit;", "getAppFloatManager", "getTag", "remove", "floatTag", "updateFloat", "x", "", "y", "(Ljava/lang/String;II)Lkotlin/Unit;", "visible", "isShow", "needShow", "(ZLjava/lang/String;Z)Lkotlin/Unit;", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class FloatManager {
    private static final String DEFAULT_TAG = "default";
    public static final FloatManager INSTANCE = new FloatManager();
    private static final Map<String, AppFloatManager> floatMap = new LinkedHashMap();

    public final String getTag(String str) {
        return str != null ? str : DEFAULT_TAG;
    }

    private FloatManager() {
    }

    public final Map<String, AppFloatManager> getFloatMap() {
        return floatMap;
    }

    public final void create(Context context, FloatConfig config) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(config, "config");
        if (checkTag(config)) {
            Map<String, AppFloatManager> map = floatMap;
            String floatTag = config.getFloatTag();
            if (floatTag == null) {
                Intrinsics.throwNpe();
            }
            Context applicationContext = context.getApplicationContext();
            Intrinsics.checkExpressionValueIsNotNull(applicationContext, "context.applicationContext");
            AppFloatManager appFloatManager = new AppFloatManager(applicationContext, config);
            appFloatManager.createFloat();
            map.put(floatTag, appFloatManager);
            return;
        }
        OnFloatCallbacks callbacks = config.getCallbacks();
        if (callbacks != null) {
            callbacks.createdResult(false, EasyFloatMessageKt.WARN_REPEATED_TAG, null);
        }
        Logger.INSTANCE.w(EasyFloatMessageKt.WARN_REPEATED_TAG);
    }

    public static /* synthetic */ Unit visible$default(FloatManager floatManager, boolean z, String str, boolean z2, int i, Object obj) {
        FloatConfig config;
        if ((i & 2) != 0) {
            str = null;
        }
        if ((i & 4) != 0) {
            AppFloatManager appFloatManager = floatMap.get(str);
            z2 = (appFloatManager == null || (config = appFloatManager.getConfig()) == null) ? true : config.getNeedShow$easyfloat_release();
        }
        return floatManager.visible(z, str, z2);
    }

    public final Unit visible(boolean z, String str, boolean z2) {
        AppFloatManager appFloatManager = floatMap.get(getTag(str));
        if (appFloatManager != null) {
            appFloatManager.setVisible(z ? 0 : 8, z2);
            return Unit.INSTANCE;
        }
        return null;
    }

    public static /* synthetic */ Unit dismiss$default(FloatManager floatManager, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        return floatManager.dismiss(str);
    }

    public final Unit dismiss(String str) {
        AppFloatManager appFloatManager = floatMap.get(getTag(str));
        if (appFloatManager != null) {
            appFloatManager.exitAnim();
            return Unit.INSTANCE;
        }
        return null;
    }

    public final AppFloatManager remove(String str) {
        Map<String, AppFloatManager> map = floatMap;
        if (map != null) {
            return (AppFloatManager) TypeIntrinsics.asMutableMap(map).remove(str);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, V>");
    }

    public final AppFloatManager getAppFloatManager(String str) {
        return floatMap.get(getTag(str));
    }

    private final boolean checkTag(FloatConfig floatConfig) {
        floatConfig.setFloatTag(getTag(floatConfig.getFloatTag()));
        Map<String, AppFloatManager> map = floatMap;
        String floatTag = floatConfig.getFloatTag();
        if (floatTag == null) {
            Intrinsics.throwNpe();
        }
        return !map.containsKey(floatTag);
    }

    public final Unit updateFloat(String str, int i, int i2) {
        AppFloatManager appFloatManager = floatMap.get(getTag(str));
        if (appFloatManager != null) {
            appFloatManager.updateFloat(i, i2);
            return Unit.INSTANCE;
        }
        return null;
    }
}
