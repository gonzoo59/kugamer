package com.jieli.component.utils;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.text.TextUtils;
import androidx.core.app.ActivityCompat;
import com.jieli.component.Logcat;
/* loaded from: classes2.dex */
public class MapHelper {
    public static boolean startGaoDeNavi(Context context, double d, double d2) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("androidamap://navi?sourceApplication=appname&lat=" + d + "&lon=" + d2 + "&dev=0&style=2"));
        context.startActivity(intent);
        return true;
    }

    public static boolean startBaiduNavi(Context context, String str) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("baidumap://map/navi?query=" + str + "&src=andr.baidu.openAPIdemo"));
        context.startActivity(intent);
        return true;
    }

    public static boolean startGaoDeNavi(Context context, String str, double d, double d2) {
        Intent intent = new Intent();
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setPackage(Constants.PACKAGE_NAME_MAP_GAODE);
        String packageName = context.getPackageName();
        intent.setData(Uri.parse("androidamap://navi?sourceApplication=" + packageName + "&poiname=" + str + "&lat=" + d + "&lon=" + d2 + "&dev=1&style=2"));
        context.startActivity(intent);
        return true;
    }

    public static boolean startTengXunNavi(String str, Context context, String str2, double d, double d2) {
        StringBuilder sb = new StringBuilder();
        sb.append("qqmap://map/routeplan?type=drive");
        sb.append("&fromcoord=CurrentLocation");
        if (!TextUtils.isEmpty(str2)) {
            sb.append("&to=");
            sb.append(str2);
        }
        sb.append("&tocoord=");
        sb.append(d);
        sb.append(",");
        sb.append(d2);
        sb.append("&referer=");
        sb.append(str);
        Intent intent = new Intent();
        intent.setData(Uri.parse(sb.toString()));
        context.startActivity(intent);
        return true;
    }

    public static Location getCurrentLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        Criteria criteria = new Criteria();
        criteria.setAccuracy(1);
        Criteria criteria2 = new Criteria();
        criteria2.setAccuracy(1);
        criteria2.setAltitudeRequired(true);
        criteria2.setBearingRequired(true);
        criteria2.setSpeedRequired(true);
        criteria2.setCostAllowed(false);
        criteria2.setPowerRequirement(3);
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            Location lastKnownLocation = locationManager.getLastKnownLocation(bestProvider);
            Logcat.e("sen", lastKnownLocation.toString());
            return lastKnownLocation;
        }
        return null;
    }
}
