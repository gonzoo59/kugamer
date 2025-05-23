package com.baidu.kwgames.USB;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Handler;
import java.util.ArrayList;
import java.util.HashMap;
/* loaded from: classes.dex */
public class UsbHidDevice extends UsbDeviceBase {
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private static final int INTERFACE_CLASS_HID = 3;
    private UsbDeviceConnection mConnection;
    private Handler mHandler;
    private UsbEndpoint mInUsbEndpoint;
    private OnUsbHidDeviceListener mListener;
    private UsbEndpoint mOutUsbEndpoint;
    private UsbDevice mUsbDevice;
    private UsbInterface mUsbInterface;
    private UsbManager mUsbManager;
    private BroadcastReceiver mUsbReceiver = null;
    Context m_context;

    public static boolean is_m3_pid_vid(int i, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            int i4 = i3 * 4;
            if (((((i2 >> i4) & 15) + i3 + 1) & 15) != ((i >> i4) & 15)) {
                return false;
            }
        }
        return true;
    }

    public static boolean CheckUSBInOutEndpoint(UsbInterface usbInterface) {
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
            UsbEndpoint endpoint = usbInterface.getEndpoint(i);
            if (endpoint.getDirection() == 128 && endpoint.getType() == 3) {
                z = true;
            }
            if (endpoint.getDirection() == 0 && endpoint.getType() == 3) {
                z2 = true;
            }
        }
        return z && z2;
    }

    public static UsbHidDevice[] enumerate(Context context, int i, int i2) throws Exception {
        UsbManager usbManager = (UsbManager) context.getApplicationContext().getSystemService("usb");
        if (usbManager == null) {
            throw new Exception("no usb service");
        }
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        ArrayList arrayList = new ArrayList();
        for (UsbDevice usbDevice : deviceList.values()) {
            if (i == 0 || usbDevice.getVendorId() == i) {
                if (i2 == 0 || usbDevice.getProductId() == i2) {
                    for (int i3 = 0; i3 < usbDevice.getInterfaceCount(); i3++) {
                        UsbInterface usbInterface = usbDevice.getInterface(i3);
                        if (usbInterface.getInterfaceClass() == 3 && CheckUSBInOutEndpoint(usbInterface)) {
                            arrayList.add(new UsbHidDevice(usbDevice, usbInterface, usbManager));
                        }
                    }
                }
            }
        }
        return (UsbHidDevice[]) arrayList.toArray(new UsbHidDevice[arrayList.size()]);
    }

    public static boolean is_kuwee_device(UsbDevice usbDevice) {
        return usbDevice != null && is_m3_pid_vid(usbDevice.getProductId(), usbDevice.getVendorId());
    }

    public static UsbHidDevice[] enumerate_m3(Context context) throws Exception {
        UsbManager usbManager = (UsbManager) context.getApplicationContext().getSystemService("usb");
        if (usbManager == null) {
            throw new Exception("no usb service");
        }
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        ArrayList arrayList = new ArrayList();
        for (UsbDevice usbDevice : deviceList.values()) {
            if (is_m3_pid_vid(usbDevice.getProductId(), usbDevice.getVendorId())) {
                for (int i = 0; i < usbDevice.getInterfaceCount(); i++) {
                    UsbInterface usbInterface = usbDevice.getInterface(i);
                    if (usbInterface.getInterfaceClass() == 3 && CheckUSBInOutEndpoint(usbInterface)) {
                        arrayList.add(new UsbHidDevice(usbDevice, usbInterface, usbManager));
                    }
                }
            }
        }
        return (UsbHidDevice[]) arrayList.toArray(new UsbHidDevice[arrayList.size()]);
    }

    public static UsbHidDevice factory(Context context) {
        try {
            UsbHidDevice[] enumerate_m3 = enumerate_m3(context);
            if (enumerate_m3.length == 0) {
                return null;
            }
            return enumerate_m3[0];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static UsbHidDevice factory(Context context, int i, int i2, String str) {
        try {
            UsbHidDevice[] enumerate = enumerate(context, i, i2);
            for (UsbHidDevice usbHidDevice : enumerate) {
                if (usbHidDevice.getSerialNumber().equals(usbHidDevice.getSerialNumber())) {
                    return usbHidDevice;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static UsbHidDevice factory(Context context, int i, int i2, int i3) {
        try {
            UsbHidDevice[] enumerate = enumerate(context, i, i2);
            for (UsbHidDevice usbHidDevice : enumerate) {
                if (usbHidDevice.getDeviceId() == i3) {
                    return usbHidDevice;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private UsbHidDevice(UsbDevice usbDevice, UsbInterface usbInterface, UsbManager usbManager) {
        this.mUsbDevice = usbDevice;
        this.mUsbInterface = usbInterface;
        this.mUsbManager = usbManager;
        for (int i = 0; i < this.mUsbInterface.getEndpointCount(); i++) {
            UsbEndpoint endpoint = this.mUsbInterface.getEndpoint(i);
            int direction = endpoint.getDirection();
            int type = endpoint.getType();
            if (this.mInUsbEndpoint == null && direction == 128 && type == 3) {
                this.mInUsbEndpoint = endpoint;
            }
            if (this.mOutUsbEndpoint == null && direction == 0 && type == 3) {
                this.mOutUsbEndpoint = endpoint;
            }
        }
    }

    public UsbDevice getUsbDevice() {
        return this.mUsbDevice;
    }

    public String getSerialNumber() {
        return this.mUsbDevice.getSerialNumber();
    }

    public int getDeviceId() {
        return this.mUsbDevice.getDeviceId();
    }

    @Override // com.baidu.kwgames.USB.UsbDeviceBase
    public void open(Context context, OnUsbHidDeviceListener onUsbHidDeviceListener) {
        this.m_context = context;
        this.mListener = onUsbHidDeviceListener;
        this.mHandler = new Handler(context.getMainLooper());
        if (!this.mUsbManager.hasPermission(this.mUsbDevice)) {
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
            IntentFilter intentFilter = new IntentFilter(ACTION_USB_PERMISSION);
            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.baidu.kwgames.USB.UsbHidDevice.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent) {
                    if (UsbHidDevice.ACTION_USB_PERMISSION.equals(intent.getAction())) {
                        context2.unregisterReceiver(this);
                        synchronized (this) {
                            UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                            if (!intent.getBooleanExtra("permission", false) || usbDevice == null) {
                                UsbHidDevice.this.onConnectFailed();
                            } else {
                                UsbHidDevice.this.openDevice();
                            }
                        }
                    }
                }
            };
            this.mUsbReceiver = broadcastReceiver;
            context.registerReceiver(broadcastReceiver, intentFilter);
            this.mUsbManager.requestPermission(this.mUsbDevice, broadcast);
            return;
        }
        openDevice();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openDevice() {
        try {
            UsbDeviceConnection openDevice = this.mUsbManager.openDevice(this.mUsbDevice);
            this.mConnection = openDevice;
            if (openDevice == null) {
                onConnectFailed();
            } else if (!openDevice.claimInterface(this.mUsbInterface, true)) {
                this.mConnection.close();
                onConnectFailed();
            } else {
                if (Build.VERSION.SDK_INT >= 21) {
                    this.mConnection.setInterface(this.mUsbInterface);
                }
                this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.USB.UsbHidDevice.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (UsbHidDevice.this.mListener != null) {
                            UsbHidDevice.this.mListener.onUsbHidDeviceConnected(UsbHidDevice.this);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            onConnectFailed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectFailed() {
        this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.USB.UsbHidDevice.3
            @Override // java.lang.Runnable
            public void run() {
                if (UsbHidDevice.this.mListener != null) {
                    UsbHidDevice.this.mListener.onUsbHidDeviceConnectFailed(UsbHidDevice.this);
                }
            }
        });
    }

    @Override // com.baidu.kwgames.USB.UsbDeviceBase
    public void close() {
        UsbInterface usbInterface;
        UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (usbDeviceConnection != null && (usbInterface = this.mUsbInterface) != null) {
            usbDeviceConnection.releaseInterface(usbInterface);
        }
        UsbDeviceConnection usbDeviceConnection2 = this.mConnection;
        if (usbDeviceConnection2 != null) {
            usbDeviceConnection2.close();
        }
    }

    @Override // com.baidu.kwgames.USB.UsbDeviceBase
    public int write(byte[] bArr, int i, int i2) {
        UsbEndpoint usbEndpoint = this.mOutUsbEndpoint;
        if (usbEndpoint == null) {
            return -1;
        }
        return this.mConnection.bulkTransfer(usbEndpoint, bArr, i, i2);
    }

    @Override // com.baidu.kwgames.USB.UsbDeviceBase
    public int read(byte[] bArr, int i, int i2) {
        return this.mConnection.bulkTransfer(this.mInUsbEndpoint, bArr, i, i2);
    }

    public static boolean is_meiying_usb_connect(Context context) {
        UsbManager usbManager = (UsbManager) context.getApplicationContext().getSystemService("usb");
        if (usbManager == null) {
            return false;
        }
        for (UsbDevice usbDevice : usbManager.getDeviceList().values()) {
            if (is_m3_pid_vid(usbDevice.getProductId(), usbDevice.getVendorId())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.baidu.kwgames.USB.UsbDeviceBase
    public String get_dev_name() {
        UsbDevice usbDevice = this.mUsbDevice;
        return usbDevice != null ? usbDevice.getDeviceName() : "";
    }
}
