package com.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import com.blankj.utilcode.constant.CacheConstants;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
/* loaded from: classes.dex */
public final class CacheDoubleUtils implements CacheConstants {
    private static final Map<String, CacheDoubleUtils> CACHE_MAP = new HashMap();
    private final CacheDiskUtils mCacheDiskUtils;
    private final CacheMemoryUtils mCacheMemoryUtils;

    public static CacheDoubleUtils getInstance() {
        return getInstance(CacheMemoryUtils.getInstance(), CacheDiskUtils.getInstance());
    }

    public static CacheDoubleUtils getInstance(CacheMemoryUtils cacheMemoryUtils, CacheDiskUtils cacheDiskUtils) {
        String str = cacheDiskUtils.toString() + "_" + cacheMemoryUtils.toString();
        Map<String, CacheDoubleUtils> map = CACHE_MAP;
        CacheDoubleUtils cacheDoubleUtils = map.get(str);
        if (cacheDoubleUtils == null) {
            synchronized (CacheDoubleUtils.class) {
                cacheDoubleUtils = map.get(str);
                if (cacheDoubleUtils == null) {
                    cacheDoubleUtils = new CacheDoubleUtils(cacheMemoryUtils, cacheDiskUtils);
                    map.put(str, cacheDoubleUtils);
                }
            }
        }
        return cacheDoubleUtils;
    }

    private CacheDoubleUtils(CacheMemoryUtils cacheMemoryUtils, CacheDiskUtils cacheDiskUtils) {
        this.mCacheMemoryUtils = cacheMemoryUtils;
        this.mCacheDiskUtils = cacheDiskUtils;
    }

    public void put(String str, byte[] bArr) {
        put(str, bArr, -1);
    }

    public void put(String str, byte[] bArr, int i) {
        this.mCacheMemoryUtils.put(str, bArr, i);
        this.mCacheDiskUtils.put(str, bArr, i);
    }

    public byte[] getBytes(String str) {
        return getBytes(str, null);
    }

    public byte[] getBytes(String str, byte[] bArr) {
        byte[] bArr2 = (byte[]) this.mCacheMemoryUtils.get(str);
        if (bArr2 != null) {
            return bArr2;
        }
        byte[] bytes = this.mCacheDiskUtils.getBytes(str);
        if (bytes != null) {
            this.mCacheMemoryUtils.put(str, bytes);
            return bytes;
        }
        return bArr;
    }

    public void put(String str, String str2) {
        put(str, str2, -1);
    }

    public void put(String str, String str2, int i) {
        this.mCacheMemoryUtils.put(str, str2, i);
        this.mCacheDiskUtils.put(str, str2, i);
    }

    public String getString(String str) {
        return getString(str, null);
    }

    public String getString(String str, String str2) {
        String str3 = (String) this.mCacheMemoryUtils.get(str);
        if (str3 != null) {
            return str3;
        }
        String string = this.mCacheDiskUtils.getString(str);
        if (string != null) {
            this.mCacheMemoryUtils.put(str, string);
            return string;
        }
        return str2;
    }

    public void put(String str, JSONObject jSONObject) {
        put(str, jSONObject, -1);
    }

    public void put(String str, JSONObject jSONObject, int i) {
        this.mCacheMemoryUtils.put(str, jSONObject, i);
        this.mCacheDiskUtils.put(str, jSONObject, i);
    }

    public JSONObject getJSONObject(String str) {
        return getJSONObject(str, null);
    }

    public JSONObject getJSONObject(String str, JSONObject jSONObject) {
        JSONObject jSONObject2 = (JSONObject) this.mCacheMemoryUtils.get(str);
        if (jSONObject2 != null) {
            return jSONObject2;
        }
        JSONObject jSONObject3 = this.mCacheDiskUtils.getJSONObject(str);
        if (jSONObject3 != null) {
            this.mCacheMemoryUtils.put(str, jSONObject3);
            return jSONObject3;
        }
        return jSONObject;
    }

    public void put(String str, JSONArray jSONArray) {
        put(str, jSONArray, -1);
    }

    public void put(String str, JSONArray jSONArray, int i) {
        this.mCacheMemoryUtils.put(str, jSONArray, i);
        this.mCacheDiskUtils.put(str, jSONArray, i);
    }

    public JSONArray getJSONArray(String str) {
        return getJSONArray(str, null);
    }

    public JSONArray getJSONArray(String str, JSONArray jSONArray) {
        JSONArray jSONArray2 = (JSONArray) this.mCacheMemoryUtils.get(str);
        if (jSONArray2 != null) {
            return jSONArray2;
        }
        JSONArray jSONArray3 = this.mCacheDiskUtils.getJSONArray(str);
        if (jSONArray3 != null) {
            this.mCacheMemoryUtils.put(str, jSONArray3);
            return jSONArray3;
        }
        return jSONArray;
    }

    public void put(String str, Bitmap bitmap) {
        put(str, bitmap, -1);
    }

    public void put(String str, Bitmap bitmap, int i) {
        this.mCacheMemoryUtils.put(str, bitmap, i);
        this.mCacheDiskUtils.put(str, bitmap, i);
    }

    public Bitmap getBitmap(String str) {
        return getBitmap(str, null);
    }

    public Bitmap getBitmap(String str, Bitmap bitmap) {
        Bitmap bitmap2 = (Bitmap) this.mCacheMemoryUtils.get(str);
        if (bitmap2 != null) {
            return bitmap2;
        }
        Bitmap bitmap3 = this.mCacheDiskUtils.getBitmap(str);
        if (bitmap3 != null) {
            this.mCacheMemoryUtils.put(str, bitmap3);
            return bitmap3;
        }
        return bitmap;
    }

    public void put(String str, Drawable drawable) {
        put(str, drawable, -1);
    }

    public void put(String str, Drawable drawable, int i) {
        this.mCacheMemoryUtils.put(str, drawable, i);
        this.mCacheDiskUtils.put(str, drawable, i);
    }

    public Drawable getDrawable(String str) {
        return getDrawable(str, null);
    }

    public Drawable getDrawable(String str, Drawable drawable) {
        Drawable drawable2 = (Drawable) this.mCacheMemoryUtils.get(str);
        if (drawable2 != null) {
            return drawable2;
        }
        Drawable drawable3 = this.mCacheDiskUtils.getDrawable(str);
        if (drawable3 != null) {
            this.mCacheMemoryUtils.put(str, drawable3);
            return drawable3;
        }
        return drawable;
    }

    public void put(String str, Parcelable parcelable) {
        put(str, parcelable, -1);
    }

    public void put(String str, Parcelable parcelable, int i) {
        this.mCacheMemoryUtils.put(str, parcelable, i);
        this.mCacheDiskUtils.put(str, parcelable, i);
    }

    public <T> T getParcelable(String str, Parcelable.Creator<T> creator) {
        return (T) getParcelable(str, creator, null);
    }

    public <T> T getParcelable(String str, Parcelable.Creator<T> creator, T t) {
        T t2 = (T) this.mCacheMemoryUtils.get(str);
        if (t2 != null) {
            return t2;
        }
        T t3 = (T) this.mCacheDiskUtils.getParcelable(str, creator);
        if (t3 != null) {
            this.mCacheMemoryUtils.put(str, t3);
            return t3;
        }
        return t;
    }

    public void put(String str, Serializable serializable) {
        put(str, serializable, -1);
    }

    public void put(String str, Serializable serializable, int i) {
        this.mCacheMemoryUtils.put(str, serializable, i);
        this.mCacheDiskUtils.put(str, serializable, i);
    }

    public Object getSerializable(String str) {
        return getSerializable(str, null);
    }

    public Object getSerializable(String str, Object obj) {
        Object obj2 = this.mCacheMemoryUtils.get(str);
        if (obj2 != null) {
            return obj2;
        }
        Object serializable = this.mCacheDiskUtils.getSerializable(str);
        if (serializable != null) {
            this.mCacheMemoryUtils.put(str, serializable);
            return serializable;
        }
        return obj;
    }

    public long getCacheDiskSize() {
        return this.mCacheDiskUtils.getCacheSize();
    }

    public int getCacheDiskCount() {
        return this.mCacheDiskUtils.getCacheCount();
    }

    public int getCacheMemoryCount() {
        return this.mCacheMemoryUtils.getCacheCount();
    }

    public void remove(String str) {
        this.mCacheMemoryUtils.remove(str);
        this.mCacheDiskUtils.remove(str);
    }

    public void clear() {
        this.mCacheMemoryUtils.clear();
        this.mCacheDiskUtils.clear();
    }
}
