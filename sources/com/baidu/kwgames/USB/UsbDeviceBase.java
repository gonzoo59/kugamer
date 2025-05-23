package com.baidu.kwgames.USB;

import android.content.Context;
import java.io.IOException;
/* loaded from: classes.dex */
public abstract class UsbDeviceBase {
    public abstract void close();

    public abstract String get_dev_name();

    public abstract void open(Context context, OnUsbHidDeviceListener onUsbHidDeviceListener);

    public abstract int read(byte[] bArr, int i, int i2) throws IOException;

    public abstract int write(byte[] bArr, int i, int i2);
}
