package com.baidu.kwgames.USB;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/* loaded from: classes.dex */
public class UsbAOADevice extends UsbDeviceBase {
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    public static final String MODEL_NAME = "GamePad+";
    private Handler mHandler;
    private OnUsbHidDeviceListener mListener;
    private UsbAccessory mUsbDevice;
    private UsbManager mUsbManager;
    private BroadcastReceiver mUsbReceiver;
    Context m_context;
    private FileDescriptor m_fileDesc;
    private ParcelFileDescriptor m_fileDescriptor;
    protected FileInputStream m_inStream;
    protected FileOutputStream m_outStream;

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectFailed() {
        this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.USB.UsbAOADevice.1
            @Override // java.lang.Runnable
            public void run() {
                if (UsbAOADevice.this.mListener != null) {
                    UsbAOADevice.this.mListener.onUsbHidDeviceConnectFailed(UsbAOADevice.this);
                }
            }
        });
    }

    private UsbAOADevice(UsbAccessory usbAccessory, UsbManager usbManager) {
        this.mUsbDevice = usbAccessory;
        this.mUsbManager = usbManager;
    }

    public static UsbAOADevice factory(Context context) {
        UsbAccessory[] accessoryList;
        try {
            UsbManager usbManager = (UsbManager) context.getApplicationContext().getSystemService("usb");
            if (usbManager.getAccessoryList() == null) {
                return null;
            }
            for (UsbAccessory usbAccessory : usbManager.getAccessoryList()) {
                if (usbAccessory.getModel().equals(MODEL_NAME)) {
                    return new UsbAOADevice(usbAccessory, usbManager);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.baidu.kwgames.USB.UsbDeviceBase
    public void open(Context context, OnUsbHidDeviceListener onUsbHidDeviceListener) {
        this.m_context = context;
        this.mListener = onUsbHidDeviceListener;
        this.mHandler = new Handler(context.getMainLooper());
        if (!this.mUsbManager.hasPermission(this.mUsbDevice)) {
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
            IntentFilter intentFilter = new IntentFilter(ACTION_USB_PERMISSION);
            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.baidu.kwgames.USB.UsbAOADevice.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent) {
                    if (intent != null && UsbAOADevice.ACTION_USB_PERMISSION.equals(intent.getAction())) {
                        context2.unregisterReceiver(this);
                        synchronized (this) {
                            UsbAccessory usbAccessory = (UsbAccessory) intent.getParcelableExtra("accessory");
                            if (!intent.getBooleanExtra("permission", false) || usbAccessory == null) {
                                UsbAOADevice.this.onConnectFailed();
                            } else {
                                UsbAOADevice.this.openDevice();
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
            ParcelFileDescriptor openAccessory = this.mUsbManager.openAccessory(this.mUsbDevice);
            this.m_fileDescriptor = openAccessory;
            if (openAccessory == null) {
                return;
            }
            this.m_fileDesc = openAccessory.getFileDescriptor();
            this.m_inStream = new FileInputStream(this.m_fileDesc);
            this.m_outStream = new FileOutputStream(this.m_fileDesc);
            this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.USB.UsbAOADevice.3
                @Override // java.lang.Runnable
                public void run() {
                    if (UsbAOADevice.this.mListener != null) {
                        UsbAOADevice.this.mListener.onUsbHidDeviceConnected(UsbAOADevice.this);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.baidu.kwgames.USB.UsbDeviceBase
    public void close() {
        try {
            ParcelFileDescriptor parcelFileDescriptor = this.m_fileDescriptor;
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
            FileInputStream fileInputStream = this.m_inStream;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            FileOutputStream fileOutputStream = this.m_outStream;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.baidu.kwgames.USB.UsbDeviceBase
    public int write(byte[] bArr, int i, int i2) {
        try {
            this.m_outStream.write(bArr, 0, i);
            this.m_outStream.flush();
            return i;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override // com.baidu.kwgames.USB.UsbDeviceBase
    public int read(byte[] bArr, int i, int i2) throws IOException {
        return this.m_inStream.read(bArr);
    }

    @Override // com.baidu.kwgames.USB.UsbDeviceBase
    public String get_dev_name() {
        UsbAccessory usbAccessory = this.mUsbDevice;
        return usbAccessory == null ? "" : usbAccessory.getModel();
    }

    public static boolean is_kuwee_device(UsbAccessory usbAccessory) {
        return usbAccessory != null && usbAccessory.getModel().equals(MODEL_NAME);
    }

    public static boolean is_meiying_usb_connect(Context context) {
        UsbManager usbManager;
        try {
            usbManager = (UsbManager) context.getApplicationContext().getSystemService("usb");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (usbManager.getAccessoryList() == null) {
            return false;
        }
        for (UsbAccessory usbAccessory : usbManager.getAccessoryList()) {
            if (usbAccessory.getModel().equals(MODEL_NAME)) {
                return true;
            }
        }
        return false;
    }
}
