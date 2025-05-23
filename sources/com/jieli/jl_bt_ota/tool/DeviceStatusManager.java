package com.jieli.jl_bt_ota.tool;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;
import com.jieli.jl_bt_ota.model.DeviceStatus;
import com.jieli.jl_bt_ota.model.response.TargetInfoResponse;
import java.util.HashMap;
/* loaded from: classes2.dex */
public class DeviceStatusManager {
    private static volatile DeviceStatusManager instance;
    private HashMap<String, DeviceStatus> mDeviceStatusMap = new HashMap<>();

    public static DeviceStatusManager getInstance() {
        if (instance == null) {
            synchronized (DeviceStatusManager.class) {
                if (instance == null) {
                    instance = new DeviceStatusManager();
                }
            }
        }
        return instance;
    }

    public String getDevMD5(BluetoothDevice bluetoothDevice) {
        DeviceStatus deviceStatus = getDeviceStatus(bluetoothDevice);
        if (deviceStatus != null) {
            return deviceStatus.getDevMD5();
        }
        return null;
    }

    public int getDeviceConnectStatus(BluetoothDevice bluetoothDevice) {
        DeviceStatus deviceStatus;
        if (bluetoothDevice == null || (deviceStatus = getDeviceStatus(bluetoothDevice)) == null) {
            return 0;
        }
        return deviceStatus.getStatus();
    }

    public boolean isAuthBtDevice(BluetoothDevice bluetoothDevice) {
        DeviceStatus deviceStatus;
        if (bluetoothDevice == null || (deviceStatus = getDeviceStatus(bluetoothDevice)) == null) {
            return false;
        }
        return deviceStatus.isAuthDevice();
    }

    public boolean isAuthProgressResult(BluetoothDevice bluetoothDevice) {
        DeviceStatus deviceStatus;
        if (bluetoothDevice == null || (deviceStatus = getDeviceStatus(bluetoothDevice)) == null) {
            return false;
        }
        return deviceStatus.isAuthProgressResult();
    }

    public boolean isMandatoryUpgrade(BluetoothDevice bluetoothDevice) {
        DeviceStatus deviceStatus;
        if (bluetoothDevice == null || (deviceStatus = getDeviceStatus(bluetoothDevice)) == null) {
            return false;
        }
        return deviceStatus.isMandatoryUpgrade();
    }

    public boolean isEnterLowPowerMode(BluetoothDevice bluetoothDevice) {
        DeviceStatus deviceStatus;
        if (bluetoothDevice == null || (deviceStatus = getDeviceStatus(bluetoothDevice)) == null) {
            return false;
        }
        return deviceStatus.isEnterLowPowerMode();
    }

    public boolean isDoubleBackupUpgrade(BluetoothDevice bluetoothDevice) {
        DeviceStatus deviceStatus;
        TargetInfoResponse targetInfo;
        if (bluetoothDevice == null || (deviceStatus = getDeviceStatus(bluetoothDevice)) == null || (targetInfo = deviceStatus.getTargetInfo()) == null) {
            return false;
        }
        return targetInfo.isSupportDoubleBackup();
    }

    public String getReconnectEdrAddress(BluetoothDevice bluetoothDevice) {
        DeviceStatus deviceStatus;
        if (bluetoothDevice == null || (deviceStatus = getDeviceStatus(bluetoothDevice)) == null) {
            return null;
        }
        return deviceStatus.getReconnectEdrAddress();
    }

    public TargetInfoResponse getDeviceInfo(BluetoothDevice bluetoothDevice) {
        DeviceStatus deviceStatus;
        if (bluetoothDevice == null || (deviceStatus = getDeviceStatus(bluetoothDevice)) == null) {
            return null;
        }
        return deviceStatus.getTargetInfo();
    }

    public void updateDeviceMD5(BluetoothDevice bluetoothDevice, String str) {
        DeviceStatus deviceStatus = getDeviceStatus(bluetoothDevice);
        if (deviceStatus == null) {
            deviceStatus = new DeviceStatus();
            deviceStatus.setDevMD5(str);
        } else {
            deviceStatus.setDevMD5(str);
        }
        updateDeviceStatus(bluetoothDevice, deviceStatus);
    }

    public void updateDeviceConnectStatus(BluetoothDevice bluetoothDevice, int i) {
        DeviceStatus deviceStatus = getDeviceStatus(bluetoothDevice);
        if (deviceStatus == null) {
            deviceStatus = new DeviceStatus();
            deviceStatus.setStatus(i);
        } else {
            deviceStatus.setStatus(i);
        }
        updateDeviceStatus(bluetoothDevice, deviceStatus);
    }

    public void updateDeviceIsAuth(BluetoothDevice bluetoothDevice, boolean z) {
        DeviceStatus deviceStatus = getDeviceStatus(bluetoothDevice);
        if (deviceStatus == null) {
            deviceStatus = new DeviceStatus();
            deviceStatus.setAuthDevice(z);
        } else {
            deviceStatus.setAuthDevice(z);
        }
        updateDeviceStatus(bluetoothDevice, deviceStatus);
    }

    public void updateAuthProgressResult(BluetoothDevice bluetoothDevice, boolean z) {
        DeviceStatus deviceStatus = getDeviceStatus(bluetoothDevice);
        if (deviceStatus == null) {
            deviceStatus = new DeviceStatus();
            deviceStatus.setAuthProgressResult(z);
        } else {
            deviceStatus.setAuthProgressResult(z);
        }
        updateDeviceStatus(bluetoothDevice, deviceStatus);
    }

    public void updateDeviceIsEnterLowPowerMode(BluetoothDevice bluetoothDevice, boolean z) {
        DeviceStatus deviceStatus = getDeviceStatus(bluetoothDevice);
        if (deviceStatus == null) {
            deviceStatus = new DeviceStatus();
            deviceStatus.setEnterLowPowerMode(z);
        } else {
            deviceStatus.setEnterLowPowerMode(z);
        }
        updateDeviceStatus(bluetoothDevice, deviceStatus);
    }

    public void updateDeviceTargetInfo(BluetoothDevice bluetoothDevice, TargetInfoResponse targetInfoResponse) {
        DeviceStatus deviceStatus = getDeviceStatus(bluetoothDevice);
        boolean z = true;
        z = (targetInfoResponse == null || targetInfoResponse.getMandatoryUpgradeFlag() != 1) ? false : false;
        if (deviceStatus == null) {
            deviceStatus = new DeviceStatus();
            deviceStatus.setTargetInfo(targetInfoResponse);
            deviceStatus.setMandatoryUpgrade(z);
        } else {
            deviceStatus.setTargetInfo(targetInfoResponse);
            deviceStatus.setMandatoryUpgrade(z);
        }
        updateDeviceStatus(bluetoothDevice, deviceStatus);
    }

    public void updateDeviceReConnectEdrAddress(BluetoothDevice bluetoothDevice, String str) {
        DeviceStatus deviceStatus = getDeviceStatus(bluetoothDevice);
        if (deviceStatus == null) {
            deviceStatus = new DeviceStatus();
            deviceStatus.setReconnectEdrAddress(str);
        } else {
            deviceStatus.setReconnectEdrAddress(str);
        }
        updateDeviceStatus(bluetoothDevice, deviceStatus);
    }

    public void updateDeviceStatus(BluetoothDevice bluetoothDevice, DeviceStatus deviceStatus) {
        if (bluetoothDevice != null) {
            updateDeviceStatus(bluetoothDevice.getAddress(), deviceStatus);
        }
    }

    public void updateDeviceStatus(String str, DeviceStatus deviceStatus) {
        if (TextUtils.isEmpty(str) || deviceStatus == null) {
            return;
        }
        this.mDeviceStatusMap.put(str, deviceStatus);
    }

    public DeviceStatus removeDeviceStatus(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return null;
        }
        return removeDeviceStatus(bluetoothDevice.getAddress());
    }

    public DeviceStatus removeDeviceStatus(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.mDeviceStatusMap.remove(str);
    }

    public DeviceStatus getDeviceStatus(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return null;
        }
        return getDeviceStatus(bluetoothDevice.getAddress());
    }

    public DeviceStatus getDeviceStatus(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.mDeviceStatusMap.get(str);
    }

    public void clear() {
        HashMap<String, DeviceStatus> hashMap = this.mDeviceStatusMap;
        if (hashMap != null) {
            hashMap.clear();
        }
    }
}
