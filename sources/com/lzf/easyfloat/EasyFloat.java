package com.lzf.easyfloat;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.FloatCallbacks;
import com.lzf.easyfloat.interfaces.OnAppFloatAnimator;
import com.lzf.easyfloat.interfaces.OnDisplayHeight;
import com.lzf.easyfloat.interfaces.OnFloatAnimator;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.lzf.easyfloat.interfaces.OnPermissionResult;
import com.lzf.easyfloat.permission.PermissionUtils;
import com.lzf.easyfloat.utils.LifecycleUtils;
import com.lzf.easyfloat.utils.Logger;
import com.lzf.easyfloat.widget.activityfloat.ActivityFloatManager;
import com.lzf.easyfloat.widget.activityfloat.FloatingView;
import com.lzf.easyfloat.widget.appfloat.AppFloatManager;
import com.lzf.easyfloat.widget.appfloat.FloatManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: EasyFloat.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00042\u00020\u0001:\u0002\u0003\u0004B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0005"}, d2 = {"Lcom/lzf/easyfloat/EasyFloat;", "", "()V", "Builder", "Companion", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class EasyFloat {
    public static final Companion Companion = new Companion(null);
    private static WeakReference<Activity> activityWr;
    private static boolean isDebug;
    private static boolean isInitialized;

    @JvmStatic
    public static final void appFloatDragEnable(boolean z) {
        Companion.appFloatDragEnable$default(Companion, z, null, 2, null);
    }

    @JvmStatic
    public static final void appFloatDragEnable(boolean z, String str) {
        Companion.appFloatDragEnable(z, str);
    }

    @JvmStatic
    public static final boolean appFloatIsShow() {
        return Companion.appFloatIsShow$default(Companion, null, 1, null);
    }

    @JvmStatic
    public static final boolean appFloatIsShow(String str) {
        return Companion.appFloatIsShow(str);
    }

    @JvmStatic
    public static final Unit clearFilters() {
        return Companion.clearFilters$default(Companion, null, 1, null);
    }

    @JvmStatic
    public static final Unit clearFilters(String str) {
        return Companion.clearFilters(str);
    }

    @JvmStatic
    public static final Unit dismiss() {
        return Companion.dismiss$default(Companion, null, null, 3, null);
    }

    @JvmStatic
    public static final Unit dismiss(Activity activity) {
        return Companion.dismiss$default(Companion, activity, null, 2, null);
    }

    @JvmStatic
    public static final Unit dismiss(Activity activity, String str) {
        return Companion.dismiss(activity, str);
    }

    @JvmStatic
    public static final Unit dismissAppFloat() {
        return Companion.dismissAppFloat$default(Companion, null, 1, null);
    }

    @JvmStatic
    public static final Unit dismissAppFloat(String str) {
        return Companion.dismissAppFloat(str);
    }

    @JvmStatic
    public static final Boolean filterActivities(String str, Class<?>... clsArr) {
        return Companion.filterActivities(str, clsArr);
    }

    @JvmStatic
    public static final Boolean filterActivities(Class<?>... clsArr) {
        return Companion.filterActivities$default(Companion, null, clsArr, 1, null);
    }

    @JvmStatic
    public static final Boolean filterActivity(Activity activity) {
        return Companion.filterActivity$default(Companion, activity, null, 2, null);
    }

    @JvmStatic
    public static final Boolean filterActivity(Activity activity, String str) {
        return Companion.filterActivity(activity, str);
    }

    @JvmStatic
    public static final View getAppFloatView() {
        return Companion.getAppFloatView$default(Companion, null, 1, null);
    }

    @JvmStatic
    public static final View getAppFloatView(String str) {
        return Companion.getAppFloatView(str);
    }

    @JvmStatic
    public static final View getFloatView() {
        return Companion.getFloatView$default(Companion, null, null, 3, null);
    }

    @JvmStatic
    public static final View getFloatView(Activity activity) {
        return Companion.getFloatView$default(Companion, activity, null, 2, null);
    }

    @JvmStatic
    public static final View getFloatView(Activity activity, String str) {
        return Companion.getFloatView(activity, str);
    }

    @JvmStatic
    public static final FloatingView hide() {
        return Companion.hide$default(Companion, null, null, 3, null);
    }

    @JvmStatic
    public static final FloatingView hide(Activity activity) {
        return Companion.hide$default(Companion, activity, null, 2, null);
    }

    @JvmStatic
    public static final FloatingView hide(Activity activity, String str) {
        return Companion.hide(activity, str);
    }

    @JvmStatic
    public static final Unit hideAppFloat() {
        return Companion.hideAppFloat$default(Companion, null, 1, null);
    }

    @JvmStatic
    public static final Unit hideAppFloat(String str) {
        return Companion.hideAppFloat(str);
    }

    @JvmStatic
    public static final void init(Application application) {
        Companion.init$default(Companion, application, false, 2, null);
    }

    @JvmStatic
    public static final void init(Application application, boolean z) {
        Companion.init(application, z);
    }

    @JvmStatic
    public static final Boolean isShow() {
        return Companion.isShow$default(Companion, null, null, 3, null);
    }

    @JvmStatic
    public static final Boolean isShow(Activity activity) {
        return Companion.isShow$default(Companion, activity, null, 2, null);
    }

    @JvmStatic
    public static final Boolean isShow(Activity activity, String str) {
        return Companion.isShow(activity, str);
    }

    @JvmStatic
    public static final Boolean removeFilter(Activity activity) {
        return Companion.removeFilter$default(Companion, activity, null, 2, null);
    }

    @JvmStatic
    public static final Boolean removeFilter(Activity activity, String str) {
        return Companion.removeFilter(activity, str);
    }

    @JvmStatic
    public static final Boolean removeFilters(String str, Class<?>... clsArr) {
        return Companion.removeFilters(str, clsArr);
    }

    @JvmStatic
    public static final Boolean removeFilters(Class<?>... clsArr) {
        return Companion.removeFilters$default(Companion, null, clsArr, 1, null);
    }

    @JvmStatic
    public static final Unit setDragEnable(Activity activity, boolean z) {
        return Companion.setDragEnable$default(Companion, activity, z, null, 4, null);
    }

    @JvmStatic
    public static final Unit setDragEnable(Activity activity, boolean z, String str) {
        return Companion.setDragEnable(activity, z, str);
    }

    @JvmStatic
    public static final Unit setDragEnable(boolean z) {
        return Companion.setDragEnable$default(Companion, null, z, null, 5, null);
    }

    @JvmStatic
    public static final FloatingView show() {
        return Companion.show$default(Companion, null, null, 3, null);
    }

    @JvmStatic
    public static final FloatingView show(Activity activity) {
        return Companion.show$default(Companion, activity, null, 2, null);
    }

    @JvmStatic
    public static final FloatingView show(Activity activity, String str) {
        return Companion.show(activity, str);
    }

    @JvmStatic
    public static final Unit showAppFloat() {
        return Companion.showAppFloat$default(Companion, null, 1, null);
    }

    @JvmStatic
    public static final Unit showAppFloat(String str) {
        return Companion.showAppFloat(str);
    }

    @JvmStatic
    public static final Unit updateFloat() {
        return Companion.updateFloat$default(Companion, null, 0, 0, 7, null);
    }

    @JvmStatic
    public static final Unit updateFloat(String str) {
        return Companion.updateFloat$default(Companion, str, 0, 0, 6, null);
    }

    @JvmStatic
    public static final Unit updateFloat(String str, int i) {
        return Companion.updateFloat$default(Companion, str, i, 0, 4, null);
    }

    @JvmStatic
    public static final Unit updateFloat(String str, int i, int i2) {
        return Companion.updateFloat(str, i, i2);
    }

    @JvmStatic
    public static final Builder with(Context context) {
        return Companion.with(context);
    }

    /* compiled from: EasyFloat.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00072\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u0014\u0010\u0012\u001a\u00020\u00072\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u001b\u0010\u0013\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007¢\u0006\u0002\u0010\u0014J'\u0010\u0015\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007¢\u0006\u0002\u0010\u0017J\u001b\u0010\u0018\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007¢\u0006\u0002\u0010\u0014J7\u0010\u0019\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u001a\u0010\u001a\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u001c0\u001b\"\u0006\u0012\u0002\b\u00030\u001cH\u0007¢\u0006\u0002\u0010\u001dJ#\u0010\u001e\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0016\u001a\u00020\u00052\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007¢\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u0004\u0018\u00010!2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u0014\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002J\u001a\u0010$\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010%2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002J\"\u0010&\u001a\u0004\u0018\u00010!2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\"\u0010'\u001a\u0004\u0018\u00010(2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u001b\u0010)\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007¢\u0006\u0002\u0010\u0014J\u001a\u0010*\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020,2\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007J'\u0010-\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007¢\u0006\u0002\u0010\u001fJ\u0014\u0010.\u001a\u0004\u0018\u00010/2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0005H\u0002J#\u00100\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0016\u001a\u00020\u00052\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007¢\u0006\u0002\u0010\u001fJ7\u00101\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u001a\u0010\u001a\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u001c0\u001b\"\u0006\u0012\u0002\b\u00030\u001cH\u0007¢\u0006\u0002\u0010\u001dJ/\u00102\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000f\u001a\u00020\u00072\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007¢\u0006\u0002\u00103J\"\u00104\u001a\u0004\u0018\u00010(2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u001b\u00105\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007¢\u0006\u0002\u0010\u0014J/\u00106\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\b\u0002\u00107\u001a\u0002082\b\b\u0002\u00109\u001a\u000208H\u0007¢\u0006\u0002\u0010:J\u0010\u0010;\u001a\u00020<2\u0006\u0010\u0016\u001a\u00020=H\u0007R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Lcom/lzf/easyfloat/EasyFloat$Companion;", "", "()V", "activityWr", "Ljava/lang/ref/WeakReference;", "Landroid/app/Activity;", "isDebug", "", "isDebug$easyfloat_release", "()Z", "setDebug$easyfloat_release", "(Z)V", "isInitialized", "appFloatDragEnable", "", "dragEnable", "tag", "", "appFloatIsShow", "clearFilters", "(Ljava/lang/String;)Lkotlin/Unit;", "dismiss", "activity", "(Landroid/app/Activity;Ljava/lang/String;)Lkotlin/Unit;", "dismissAppFloat", "filterActivities", "clazz", "", "Ljava/lang/Class;", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/Boolean;", "filterActivity", "(Landroid/app/Activity;Ljava/lang/String;)Ljava/lang/Boolean;", "getAppFloatView", "Landroid/view/View;", "getConfig", "Lcom/lzf/easyfloat/data/FloatConfig;", "getFilterSet", "", "getFloatView", "hide", "Lcom/lzf/easyfloat/widget/activityfloat/FloatingView;", "hideAppFloat", "init", "application", "Landroid/app/Application;", "isShow", "manager", "Lcom/lzf/easyfloat/widget/activityfloat/ActivityFloatManager;", "removeFilter", "removeFilters", "setDragEnable", "(Landroid/app/Activity;ZLjava/lang/String;)Lkotlin/Unit;", "show", "showAppFloat", "updateFloat", "x", "", "y", "(Ljava/lang/String;II)Lkotlin/Unit;", "with", "Lcom/lzf/easyfloat/EasyFloat$Builder;", "Landroid/content/Context;", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public static final class Companion {
        @JvmStatic
        public final void appFloatDragEnable(boolean z) {
            appFloatDragEnable$default(this, z, null, 2, null);
        }

        @JvmStatic
        public final boolean appFloatIsShow() {
            return appFloatIsShow$default(this, null, 1, null);
        }

        @JvmStatic
        public final Unit clearFilters() {
            return clearFilters$default(this, null, 1, null);
        }

        @JvmStatic
        public final Unit dismiss() {
            return dismiss$default(this, null, null, 3, null);
        }

        @JvmStatic
        public final Unit dismiss(Activity activity) {
            return dismiss$default(this, activity, null, 2, null);
        }

        @JvmStatic
        public final Unit dismissAppFloat() {
            return dismissAppFloat$default(this, null, 1, null);
        }

        @JvmStatic
        public final Boolean filterActivities(Class<?>... clsArr) {
            return filterActivities$default(this, null, clsArr, 1, null);
        }

        @JvmStatic
        public final Boolean filterActivity(Activity activity) {
            return filterActivity$default(this, activity, null, 2, null);
        }

        @JvmStatic
        public final View getAppFloatView() {
            return getAppFloatView$default(this, null, 1, null);
        }

        @JvmStatic
        public final View getFloatView() {
            return getFloatView$default(this, null, null, 3, null);
        }

        @JvmStatic
        public final View getFloatView(Activity activity) {
            return getFloatView$default(this, activity, null, 2, null);
        }

        @JvmStatic
        public final FloatingView hide() {
            return hide$default(this, null, null, 3, null);
        }

        @JvmStatic
        public final FloatingView hide(Activity activity) {
            return hide$default(this, activity, null, 2, null);
        }

        @JvmStatic
        public final Unit hideAppFloat() {
            return hideAppFloat$default(this, null, 1, null);
        }

        @JvmStatic
        public final void init(Application application) {
            init$default(this, application, false, 2, null);
        }

        @JvmStatic
        public final Boolean isShow() {
            return isShow$default(this, null, null, 3, null);
        }

        @JvmStatic
        public final Boolean isShow(Activity activity) {
            return isShow$default(this, activity, null, 2, null);
        }

        @JvmStatic
        public final Boolean removeFilter(Activity activity) {
            return removeFilter$default(this, activity, null, 2, null);
        }

        @JvmStatic
        public final Boolean removeFilters(Class<?>... clsArr) {
            return removeFilters$default(this, null, clsArr, 1, null);
        }

        @JvmStatic
        public final Unit setDragEnable(Activity activity, boolean z) {
            return setDragEnable$default(this, activity, z, null, 4, null);
        }

        @JvmStatic
        public final Unit setDragEnable(boolean z) {
            return setDragEnable$default(this, null, z, null, 5, null);
        }

        @JvmStatic
        public final FloatingView show() {
            return show$default(this, null, null, 3, null);
        }

        @JvmStatic
        public final FloatingView show(Activity activity) {
            return show$default(this, activity, null, 2, null);
        }

        @JvmStatic
        public final Unit showAppFloat() {
            return showAppFloat$default(this, null, 1, null);
        }

        @JvmStatic
        public final Unit updateFloat() {
            return updateFloat$default(this, null, 0, 0, 7, null);
        }

        @JvmStatic
        public final Unit updateFloat(String str) {
            return updateFloat$default(this, str, 0, 0, 6, null);
        }

        @JvmStatic
        public final Unit updateFloat(String str, int i) {
            return updateFloat$default(this, str, i, 0, 4, null);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean isDebug$easyfloat_release() {
            return EasyFloat.isDebug;
        }

        public final void setDebug$easyfloat_release(boolean z) {
            EasyFloat.isDebug = z;
        }

        public static /* synthetic */ void init$default(Companion companion, Application application, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            companion.init(application, z);
        }

        @JvmStatic
        public final void init(Application application, boolean z) {
            Intrinsics.checkParameterIsNotNull(application, "application");
            setDebug$easyfloat_release(z);
            EasyFloat.isInitialized = true;
            LifecycleUtils.INSTANCE.setLifecycleCallbacks(application);
        }

        @JvmStatic
        public final Builder with(Context activity) {
            Intrinsics.checkParameterIsNotNull(activity, "activity");
            if (activity instanceof Activity) {
                EasyFloat.activityWr = new WeakReference(activity);
            }
            return new Builder(activity);
        }

        public static /* synthetic */ Unit dismiss$default(Companion companion, Activity activity, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                activity = null;
            }
            if ((i & 2) != 0) {
                str = null;
            }
            return companion.dismiss(activity, str);
        }

        @JvmStatic
        public final Unit dismiss(Activity activity, String str) {
            ActivityFloatManager manager = manager(activity);
            if (manager != null) {
                return manager.dismiss(str);
            }
            return null;
        }

        public static /* synthetic */ FloatingView hide$default(Companion companion, Activity activity, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                activity = null;
            }
            if ((i & 2) != 0) {
                str = null;
            }
            return companion.hide(activity, str);
        }

        @JvmStatic
        public final FloatingView hide(Activity activity, String str) {
            ActivityFloatManager manager = manager(activity);
            if (manager != null) {
                return manager.setVisibility(str, 8);
            }
            return null;
        }

        public static /* synthetic */ FloatingView show$default(Companion companion, Activity activity, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                activity = null;
            }
            if ((i & 2) != 0) {
                str = null;
            }
            return companion.show(activity, str);
        }

        @JvmStatic
        public final FloatingView show(Activity activity, String str) {
            ActivityFloatManager manager = manager(activity);
            if (manager != null) {
                return manager.setVisibility(str, 0);
            }
            return null;
        }

        public static /* synthetic */ Unit setDragEnable$default(Companion companion, Activity activity, boolean z, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                activity = null;
            }
            if ((i & 4) != 0) {
                str = null;
            }
            return companion.setDragEnable(activity, z, str);
        }

        @JvmStatic
        public final Unit setDragEnable(Activity activity, boolean z, String str) {
            ActivityFloatManager manager = manager(activity);
            if (manager != null) {
                manager.setDragEnable(z, str);
                return Unit.INSTANCE;
            }
            return null;
        }

        public static /* synthetic */ Boolean isShow$default(Companion companion, Activity activity, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                activity = null;
            }
            if ((i & 2) != 0) {
                str = null;
            }
            return companion.isShow(activity, str);
        }

        @JvmStatic
        public final Boolean isShow(Activity activity, String str) {
            ActivityFloatManager manager = manager(activity);
            if (manager != null) {
                return Boolean.valueOf(manager.isShow(str));
            }
            return null;
        }

        public static /* synthetic */ View getFloatView$default(Companion companion, Activity activity, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                activity = null;
            }
            if ((i & 2) != 0) {
                str = null;
            }
            return companion.getFloatView(activity, str);
        }

        @JvmStatic
        public final View getFloatView(Activity activity, String str) {
            ActivityFloatManager manager = manager(activity);
            if (manager != null) {
                return manager.getFloatView(str);
            }
            return null;
        }

        private final ActivityFloatManager manager(Activity activity) {
            if (activity == null) {
                WeakReference weakReference = EasyFloat.activityWr;
                activity = weakReference != null ? (Activity) weakReference.get() : null;
            }
            if (activity != null) {
                return new ActivityFloatManager(activity);
            }
            return null;
        }

        public static /* synthetic */ Unit dismissAppFloat$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.dismissAppFloat(str);
        }

        @JvmStatic
        public final Unit dismissAppFloat(String str) {
            return FloatManager.INSTANCE.dismiss(str);
        }

        public static /* synthetic */ Unit hideAppFloat$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.hideAppFloat(str);
        }

        @JvmStatic
        public final Unit hideAppFloat(String str) {
            return FloatManager.INSTANCE.visible(false, str, false);
        }

        public static /* synthetic */ Unit showAppFloat$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.showAppFloat(str);
        }

        @JvmStatic
        public final Unit showAppFloat(String str) {
            return FloatManager.INSTANCE.visible(true, str, true);
        }

        public static /* synthetic */ void appFloatDragEnable$default(Companion companion, boolean z, String str, int i, Object obj) {
            if ((i & 2) != 0) {
                str = null;
            }
            companion.appFloatDragEnable(z, str);
        }

        @JvmStatic
        public final void appFloatDragEnable(boolean z, String str) {
            FloatConfig config = getConfig(str);
            if (config != null) {
                config.setDragEnable(z);
            }
        }

        public static /* synthetic */ boolean appFloatIsShow$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.appFloatIsShow(str);
        }

        @JvmStatic
        public final boolean appFloatIsShow(String str) {
            if (getConfig(str) != null) {
                FloatConfig config = getConfig(str);
                if (config == null) {
                    Intrinsics.throwNpe();
                }
                if (config.isShow()) {
                    return true;
                }
            }
            return false;
        }

        public static /* synthetic */ View getAppFloatView$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.getAppFloatView(str);
        }

        @JvmStatic
        public final View getAppFloatView(String str) {
            FloatConfig config = getConfig(str);
            if (config != null) {
                return config.getLayoutView();
            }
            return null;
        }

        public static /* synthetic */ Boolean filterActivity$default(Companion companion, Activity activity, String str, int i, Object obj) {
            if ((i & 2) != 0) {
                str = null;
            }
            return companion.filterActivity(activity, str);
        }

        @JvmStatic
        public final Boolean filterActivity(Activity activity, String str) {
            Intrinsics.checkParameterIsNotNull(activity, "activity");
            Set<String> filterSet = getFilterSet(str);
            if (filterSet != null) {
                ComponentName componentName = activity.getComponentName();
                Intrinsics.checkExpressionValueIsNotNull(componentName, "activity.componentName");
                String className = componentName.getClassName();
                Intrinsics.checkExpressionValueIsNotNull(className, "activity.componentName.className");
                return Boolean.valueOf(filterSet.add(className));
            }
            return null;
        }

        public static /* synthetic */ Boolean filterActivities$default(Companion companion, String str, Class[] clsArr, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.filterActivities(str, clsArr);
        }

        @JvmStatic
        public final Boolean filterActivities(String str, Class<?>... clazz) {
            Intrinsics.checkParameterIsNotNull(clazz, "clazz");
            Set<String> filterSet = getFilterSet(str);
            if (filterSet != null) {
                ArrayList arrayList = new ArrayList(clazz.length);
                for (Class<?> cls : clazz) {
                    arrayList.add(cls.getName());
                }
                return Boolean.valueOf(filterSet.addAll(arrayList));
            }
            return null;
        }

        public static /* synthetic */ Boolean removeFilter$default(Companion companion, Activity activity, String str, int i, Object obj) {
            if ((i & 2) != 0) {
                str = null;
            }
            return companion.removeFilter(activity, str);
        }

        @JvmStatic
        public final Boolean removeFilter(Activity activity, String str) {
            Intrinsics.checkParameterIsNotNull(activity, "activity");
            Set<String> filterSet = getFilterSet(str);
            if (filterSet != null) {
                ComponentName componentName = activity.getComponentName();
                Intrinsics.checkExpressionValueIsNotNull(componentName, "activity.componentName");
                return Boolean.valueOf(filterSet.remove(componentName.getClassName()));
            }
            return null;
        }

        public static /* synthetic */ Boolean removeFilters$default(Companion companion, String str, Class[] clsArr, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.removeFilters(str, clsArr);
        }

        @JvmStatic
        public final Boolean removeFilters(String str, Class<?>... clazz) {
            Intrinsics.checkParameterIsNotNull(clazz, "clazz");
            Set<String> filterSet = getFilterSet(str);
            if (filterSet != null) {
                ArrayList arrayList = new ArrayList(clazz.length);
                for (Class<?> cls : clazz) {
                    arrayList.add(cls.getName());
                }
                return Boolean.valueOf(filterSet.removeAll(arrayList));
            }
            return null;
        }

        public static /* synthetic */ Unit clearFilters$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.clearFilters(str);
        }

        @JvmStatic
        public final Unit clearFilters(String str) {
            Set<String> filterSet = getFilterSet(str);
            if (filterSet != null) {
                filterSet.clear();
                return Unit.INSTANCE;
            }
            return null;
        }

        private final FloatConfig getConfig(String str) {
            AppFloatManager appFloatManager = FloatManager.INSTANCE.getAppFloatManager(str);
            if (appFloatManager != null) {
                return appFloatManager.getConfig();
            }
            return null;
        }

        private final Set<String> getFilterSet(String str) {
            FloatConfig config = getConfig(str);
            if (config != null) {
                return config.getFilterSet();
            }
            return null;
        }

        public static /* synthetic */ Unit updateFloat$default(Companion companion, String str, int i, int i2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                str = null;
            }
            if ((i3 & 2) != 0) {
                i = -1;
            }
            if ((i3 & 4) != 0) {
                i2 = -1;
            }
            return companion.updateFloat(str, i, i2);
        }

        @JvmStatic
        public final Unit updateFloat(String str, int i, int i2) {
            return FloatManager.INSTANCE.updateFloat(str, i, i2);
        }
    }

    /* compiled from: EasyFloat.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002J\b\u0010\r\u001a\u00020\bH\u0002J\b\u0010\u000e\u001a\u00020\bH\u0002J\u000e\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\fJ\u0010\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\fH\u0016J#\u0010\u0014\u001a\u00020\u00002\u001b\u0010\u0015\u001a\u0017\u0012\b\u0012\u00060\u0017R\u00020\u0018\u0012\u0004\u0012\u00020\b0\u0016¢\u0006\u0002\b\u0019J\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u001cJ\b\u0010\u001d\u001a\u00020\bH\u0002J\u0010\u0010\u001e\u001a\u00020\u00002\b\u0010\u001f\u001a\u0004\u0018\u00010 J\u0010\u0010!\u001a\u00020\u00002\b\u0010\"\u001a\u0004\u0018\u00010#J\u000e\u0010$\u001a\u00020\u00002\u0006\u0010%\u001a\u00020&J\u000e\u0010'\u001a\u00020\u00002\u0006\u0010(\u001a\u00020\fJ'\u0010)\u001a\u00020\u00002\u001a\u0010*\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030,0+\"\u0006\u0012\u0002\b\u00030,¢\u0006\u0002\u0010-J$\u0010.\u001a\u00020\u00002\u0006\u0010/\u001a\u0002002\b\b\u0002\u00101\u001a\u0002002\b\b\u0002\u00102\u001a\u000200H\u0007J\u001c\u00103\u001a\u00020\u00002\u0006\u00104\u001a\u0002002\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u0016\u00105\u001a\u00020\u00002\u0006\u00106\u001a\u0002002\u0006\u00107\u001a\u000200J\u001a\u00108\u001a\u00020\u00002\b\b\u0002\u00109\u001a\u00020\f2\b\b\u0002\u0010:\u001a\u00020\fJ\u000e\u0010;\u001a\u00020\u00002\u0006\u0010<\u001a\u00020=J\u000e\u0010>\u001a\u00020\u00002\u0006\u0010?\u001a\u00020@J\u0010\u0010A\u001a\u00020\u00002\b\u0010B\u001a\u0004\u0018\u00010\nJ\u0006\u0010C\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006D"}, d2 = {"Lcom/lzf/easyfloat/EasyFloat$Builder;", "Lcom/lzf/easyfloat/interfaces/OnPermissionResult;", "activity", "Landroid/content/Context;", "(Landroid/content/Context;)V", "config", "Lcom/lzf/easyfloat/data/FloatConfig;", "callbackCreateFailed", "", "reason", "", "checkUninitialized", "", "createActivityFloat", "createAppFloat", "hasEditText", "invokeView", "Lcom/lzf/easyfloat/interfaces/OnInvokeView;", "permissionResult", "isOpen", "registerCallback", "builder", "Lkotlin/Function1;", "Lcom/lzf/easyfloat/interfaces/FloatCallbacks$Builder;", "Lcom/lzf/easyfloat/interfaces/FloatCallbacks;", "Lkotlin/ExtensionFunctionType;", "registerCallbacks", "callbacks", "Lcom/lzf/easyfloat/interfaces/OnFloatCallbacks;", "requestPermission", "setAnimator", "floatAnimator", "Lcom/lzf/easyfloat/interfaces/OnFloatAnimator;", "setAppFloatAnimator", "appFloatAnimator", "Lcom/lzf/easyfloat/interfaces/OnAppFloatAnimator;", "setDisplayHeight", "displayHeight", "Lcom/lzf/easyfloat/interfaces/OnDisplayHeight;", "setDragEnable", "dragEnable", "setFilter", "clazz", "", "Ljava/lang/Class;", "([Ljava/lang/Class;)Lcom/lzf/easyfloat/EasyFloat$Builder;", "setGravity", "gravity", "", "offsetX", "offsetY", "setLayout", "layoutId", "setLocation", "x", "y", "setMatchParent", "widthMatch", "heightMatch", "setShowPattern", "showPattern", "Lcom/lzf/easyfloat/enums/ShowPattern;", "setSidePattern", "sidePattern", "Lcom/lzf/easyfloat/enums/SidePattern;", "setTag", "floatTag", "show", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public static final class Builder implements OnPermissionResult {
        private final Context activity;
        private final FloatConfig config;

        @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 16})
        /* loaded from: classes2.dex */
        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ShowPattern.values().length];
                $EnumSwitchMapping$0 = iArr;
                iArr[ShowPattern.CURRENT_ACTIVITY.ordinal()] = 1;
                iArr[ShowPattern.FOREGROUND.ordinal()] = 2;
                iArr[ShowPattern.BACKGROUND.ordinal()] = 3;
                iArr[ShowPattern.ALL_TIME.ordinal()] = 4;
            }
        }

        public final Builder setGravity(int i) {
            return setGravity$default(this, i, 0, 0, 6, null);
        }

        public final Builder setGravity(int i, int i2) {
            return setGravity$default(this, i, i2, 0, 4, null);
        }

        public final Builder setLayout(int i) {
            return setLayout$default(this, i, null, 2, null);
        }

        public Builder(Context activity) {
            Intrinsics.checkParameterIsNotNull(activity, "activity");
            this.activity = activity;
            this.config = new FloatConfig(null, null, null, false, false, false, false, false, null, null, false, false, 0, null, null, null, null, null, null, null, null, null, false, false, 16777215, null);
        }

        public final Builder setSidePattern(SidePattern sidePattern) {
            Intrinsics.checkParameterIsNotNull(sidePattern, "sidePattern");
            this.config.setSidePattern(sidePattern);
            return this;
        }

        public final Builder setShowPattern(ShowPattern showPattern) {
            Intrinsics.checkParameterIsNotNull(showPattern, "showPattern");
            this.config.setShowPattern(showPattern);
            return this;
        }

        public static /* synthetic */ Builder setLayout$default(Builder builder, int i, OnInvokeView onInvokeView, int i2, Object obj) {
            if ((i2 & 2) != 0) {
                onInvokeView = null;
            }
            return builder.setLayout(i, onInvokeView);
        }

        public final Builder setLayout(int i, OnInvokeView onInvokeView) {
            this.config.setLayoutId(Integer.valueOf(i));
            this.config.setInvokeView(onInvokeView);
            return this;
        }

        public static /* synthetic */ Builder setGravity$default(Builder builder, int i, int i2, int i3, int i4, Object obj) {
            if ((i4 & 2) != 0) {
                i2 = 0;
            }
            if ((i4 & 4) != 0) {
                i3 = 0;
            }
            return builder.setGravity(i, i2, i3);
        }

        public final Builder setGravity(int i, int i2, int i3) {
            this.config.setGravity(i);
            this.config.setOffsetPair(new Pair<>(Integer.valueOf(i2), Integer.valueOf(i3)));
            return this;
        }

        public final Builder setLocation(int i, int i2) {
            this.config.setLocationPair(new Pair<>(Integer.valueOf(i), Integer.valueOf(i2)));
            return this;
        }

        public final Builder setTag(String str) {
            this.config.setFloatTag(str);
            return this;
        }

        public final Builder setDragEnable(boolean z) {
            this.config.setDragEnable(z);
            return this;
        }

        public final Builder hasEditText(boolean z) {
            this.config.setHasEditText(z);
            return this;
        }

        @Deprecated(message = "建议直接在 setLayout 设置详细布局")
        public final Builder invokeView(OnInvokeView invokeView) {
            Intrinsics.checkParameterIsNotNull(invokeView, "invokeView");
            this.config.setInvokeView(invokeView);
            return this;
        }

        public final Builder registerCallbacks(OnFloatCallbacks callbacks) {
            Intrinsics.checkParameterIsNotNull(callbacks, "callbacks");
            this.config.setCallbacks(callbacks);
            return this;
        }

        public final Builder registerCallback(Function1<? super FloatCallbacks.Builder, Unit> builder) {
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            FloatConfig floatConfig = this.config;
            FloatCallbacks floatCallbacks = new FloatCallbacks();
            floatCallbacks.registerListener(builder);
            floatConfig.setFloatCallbacks(floatCallbacks);
            return this;
        }

        public final Builder setAnimator(OnFloatAnimator onFloatAnimator) {
            this.config.setFloatAnimator(onFloatAnimator);
            return this;
        }

        public final Builder setAppFloatAnimator(OnAppFloatAnimator onAppFloatAnimator) {
            this.config.setAppFloatAnimator(onAppFloatAnimator);
            return this;
        }

        public final Builder setDisplayHeight(OnDisplayHeight displayHeight) {
            Intrinsics.checkParameterIsNotNull(displayHeight, "displayHeight");
            this.config.setDisplayHeight(displayHeight);
            return this;
        }

        public static /* synthetic */ Builder setMatchParent$default(Builder builder, boolean z, boolean z2, int i, Object obj) {
            if ((i & 1) != 0) {
                z = false;
            }
            if ((i & 2) != 0) {
                z2 = false;
            }
            return builder.setMatchParent(z, z2);
        }

        public final Builder setMatchParent(boolean z, boolean z2) {
            this.config.setWidthMatch(z);
            this.config.setHeightMatch(z2);
            return this;
        }

        public final Builder setFilter(Class<?>... clazz) {
            Intrinsics.checkParameterIsNotNull(clazz, "clazz");
            for (Class<?> cls : clazz) {
                Set<String> filterSet = this.config.getFilterSet();
                String name = cls.getName();
                Intrinsics.checkExpressionValueIsNotNull(name, "it.name");
                filterSet.add(name);
                if (this.activity instanceof Activity) {
                    String name2 = cls.getName();
                    ComponentName componentName = ((Activity) this.activity).getComponentName();
                    Intrinsics.checkExpressionValueIsNotNull(componentName, "activity.componentName");
                    if (Intrinsics.areEqual(name2, componentName.getClassName())) {
                        this.config.setFilterSelf$easyfloat_release(true);
                    }
                }
            }
            return this;
        }

        public final void show() {
            if (this.config.getLayoutId() == null) {
                callbackCreateFailed(EasyFloatMessageKt.WARN_NO_LAYOUT);
            } else if (checkUninitialized()) {
                callbackCreateFailed(EasyFloatMessageKt.WARN_UNINITIALIZED);
            } else if (this.config.getShowPattern() == ShowPattern.CURRENT_ACTIVITY) {
                createActivityFloat();
            } else if (PermissionUtils.checkPermission(this.activity)) {
                createAppFloat();
            } else {
                requestPermission();
            }
        }

        private final boolean checkUninitialized() {
            int i = WhenMappings.$EnumSwitchMapping$0[this.config.getShowPattern().ordinal()];
            if (i != 1) {
                if (i == 2 || i == 3) {
                    if (EasyFloat.isInitialized) {
                        return false;
                    }
                } else if (i != 4) {
                    throw new NoWhenBranchMatchedException();
                } else {
                    if (!(!this.config.getFilterSet().isEmpty()) || EasyFloat.isInitialized) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        private final void createActivityFloat() {
            Context context = this.activity;
            if (context instanceof Activity) {
                new ActivityFloatManager((Activity) context).createFloat(this.config);
            } else {
                callbackCreateFailed(EasyFloatMessageKt.WARN_CONTEXT_ACTIVITY);
            }
        }

        private final void createAppFloat() {
            FloatManager.INSTANCE.create(this.activity, this.config);
        }

        private final void requestPermission() {
            Context context = this.activity;
            if (context instanceof Activity) {
                PermissionUtils.requestPermission((Activity) context, this);
            } else {
                callbackCreateFailed(EasyFloatMessageKt.WARN_CONTEXT_REQUEST);
            }
        }

        @Override // com.lzf.easyfloat.interfaces.OnPermissionResult
        public void permissionResult(boolean z) {
            if (z) {
                createAppFloat();
            } else {
                callbackCreateFailed(EasyFloatMessageKt.WARN_PERMISSION);
            }
        }

        private final void callbackCreateFailed(String str) {
            FloatCallbacks.Builder builder;
            Function3<Boolean, String, View, Unit> createdResult$easyfloat_release;
            OnFloatCallbacks callbacks = this.config.getCallbacks();
            if (callbacks != null) {
                callbacks.createdResult(false, str, null);
            }
            FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
            if (floatCallbacks != null && (builder = floatCallbacks.getBuilder()) != null && (createdResult$easyfloat_release = builder.getCreatedResult$easyfloat_release()) != null) {
                createdResult$easyfloat_release.invoke(false, str, null);
            }
            Logger.INSTANCE.w(str);
            if (Intrinsics.areEqual(str, EasyFloatMessageKt.WARN_NO_LAYOUT) || Intrinsics.areEqual(str, EasyFloatMessageKt.WARN_UNINITIALIZED) || Intrinsics.areEqual(str, EasyFloatMessageKt.WARN_CONTEXT_ACTIVITY)) {
                throw new Exception(str);
            }
        }
    }
}
