package androidx.core.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
public final class NavUtils {
    public static final String PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY";
    private static final String TAG = "NavUtils";

    public static boolean shouldUpRecreateTask(Activity activity, Intent intent) {
        if (Build.VERSION.SDK_INT >= 16) {
            return activity.shouldUpRecreateTask(intent);
        }
        String action = activity.getIntent().getAction();
        return (action == null || action.equals("android.intent.action.MAIN")) ? false : true;
    }

    public static void navigateUpFromSameTask(Activity activity) {
        Intent parentActivityIntent = getParentActivityIntent(activity);
        if (parentActivityIntent == null) {
            throw new IllegalArgumentException("Activity " + activity.getClass().getSimpleName() + " does not have a parent activity name specified. (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data>  element in your manifest?)");
        }
        navigateUpTo(activity, parentActivityIntent);
    }

    public static void navigateUpTo(Activity activity, Intent intent) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.navigateUpTo(intent);
            return;
        }
        intent.addFlags(67108864);
        activity.startActivity(intent);
        activity.finish();
    }

    public static Intent getParentActivityIntent(Activity activity) {
        Intent parentActivityIntent;
        if (Build.VERSION.SDK_INT < 16 || (parentActivityIntent = activity.getParentActivityIntent()) == null) {
            String parentActivityName = getParentActivityName(activity);
            if (parentActivityName == null) {
                return null;
            }
            ComponentName componentName = new ComponentName(activity, parentActivityName);
            try {
                if (getParentActivityName(activity, componentName) == null) {
                    return Intent.makeMainActivity(componentName);
                }
                return new Intent().setComponent(componentName);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("NavUtils", "getParentActivityIntent: bad parentActivityName '" + parentActivityName + "' in manifest");
                return null;
            }
        }
        return parentActivityIntent;
    }

    public static Intent getParentActivityIntent(Context context, Class<?> cls) throws PackageManager.NameNotFoundException {
        String parentActivityName = getParentActivityName(context, new ComponentName(context, cls));
        if (parentActivityName == null) {
            return null;
        }
        ComponentName componentName = new ComponentName(context, parentActivityName);
        if (getParentActivityName(context, componentName) == null) {
            return Intent.makeMainActivity(componentName);
        }
        return new Intent().setComponent(componentName);
    }

    public static Intent getParentActivityIntent(Context context, ComponentName componentName) throws PackageManager.NameNotFoundException {
        String parentActivityName = getParentActivityName(context, componentName);
        if (parentActivityName == null) {
            return null;
        }
        ComponentName componentName2 = new ComponentName(componentName.getPackageName(), parentActivityName);
        if (getParentActivityName(context, componentName2) == null) {
            return Intent.makeMainActivity(componentName2);
        }
        return new Intent().setComponent(componentName2);
    }

    public static String getParentActivityName(Activity activity) {
        try {
            return getParentActivityName(activity, activity.getComponentName());
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String getParentActivityName(Context context, ComponentName componentName) throws PackageManager.NameNotFoundException {
        String string;
        String str;
        ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(componentName, 128);
        if (Build.VERSION.SDK_INT < 16 || (str = activityInfo.parentActivityName) == null) {
            if (activityInfo.metaData == null || (string = activityInfo.metaData.getString("android.support.PARENT_ACTIVITY")) == null) {
                return null;
            }
            if (string.charAt(0) == '.') {
                return context.getPackageName() + string;
            }
            return string;
        }
        return str;
    }

    private NavUtils() {
    }
}
