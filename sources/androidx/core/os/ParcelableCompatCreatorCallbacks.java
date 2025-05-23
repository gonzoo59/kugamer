package androidx.core.os;

import android.os.Parcel;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
@Deprecated
/* loaded from: assets/adb/sincoserver.dex */
public interface ParcelableCompatCreatorCallbacks<T> {
    T createFromParcel(Parcel parcel, ClassLoader classLoader);

    T[] newArray(int i);
}
