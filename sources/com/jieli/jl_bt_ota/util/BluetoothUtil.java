package com.jieli.jl_bt_ota.util;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Build;
import android.os.ParcelUuid;
import android.text.TextUtils;
import com.jieli.jl_bt_ota.model.BluetoothOTAConfigure;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
/* loaded from: classes2.dex */
public class BluetoothUtil {
    private static String TAG = "BluetoothUtil";
    private static final char[] mChars = "0123456789ABCDEF".toCharArray();
    private static int sCmdSequence;

    public static int formatBleMtu(int i) {
        if (i < 20) {
            i = 20;
        }
        if (i > 509) {
            return 509;
        }
        return i;
    }

    public static int getCmdSequence() {
        return sCmdSequence;
    }

    public static int autoIncSN() {
        int cmdSequence = getCmdSequence();
        int i = sCmdSequence + 1;
        sCmdSequence = i;
        if (i >= 256) {
            sCmdSequence = 0;
        }
        return cmdSequence;
    }

    public static boolean isBleDevice(BluetoothDevice bluetoothDevice, BluetoothOTAConfigure bluetoothOTAConfigure) {
        return bluetoothDevice != null && (bluetoothDevice.getType() == 2 || (bluetoothDevice.getType() == 3 && bluetoothOTAConfigure != null && bluetoothOTAConfigure.getPriority() == 0));
    }

    public static boolean hasBle(Context context) {
        return context != null && context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
    }

    public static BluetoothDevice getRemoteDevice(String str) {
        BluetoothAdapter defaultAdapter;
        if (BluetoothAdapter.checkBluetoothAddress(str) && (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) != null) {
            try {
                return defaultAdapter.getRemoteDevice(str);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static boolean deviceEquals(BluetoothDevice bluetoothDevice, BluetoothDevice bluetoothDevice2) {
        return (bluetoothDevice == null || bluetoothDevice2 == null || !bluetoothDevice.getAddress().equals(bluetoothDevice2.getAddress())) ? false : true;
    }

    public static boolean createBond(BluetoothDevice bluetoothDevice, int i) {
        try {
            Method declaredMethod = BluetoothDevice.class.getDeclaredMethod("createBond", Integer.TYPE);
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(bluetoothDevice, Integer.valueOf(i))).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createBond(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 20) {
            return bluetoothDevice.createBond();
        }
        try {
            return ((Boolean) bluetoothDevice.getClass().getMethod("createBond", new Class[0]).invoke(bluetoothDevice, new Object[0])).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            String str = TAG;
            JL_Log.e(str, "Invoke createBond : " + e.getMessage());
            return false;
        }
    }

    public static boolean removeBond(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return false;
        }
        try {
            return ((Boolean) bluetoothDevice.getClass().getMethod("removeBond", new Class[0]).invoke(bluetoothDevice, new Object[0])).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            String str = TAG;
            JL_Log.e(str, "Invoke removeBond : " + e.getMessage());
            return false;
        }
    }

    public static int getBtAdapterConnectionState(BluetoothAdapter bluetoothAdapter) {
        int intValue;
        if (bluetoothAdapter != null) {
            try {
                Class[] clsArr = null;
                Method declaredMethod = BluetoothAdapter.class.getDeclaredMethod("getConnectionState", null);
                declaredMethod.setAccessible(true);
                Object[] objArr = null;
                intValue = ((Integer) declaredMethod.invoke(bluetoothAdapter, null)).intValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            JL_Log.i(TAG, "BluetoothAdapter state : " + intValue);
            return intValue;
        }
        intValue = -1;
        JL_Log.i(TAG, "BluetoothAdapter state : " + intValue);
        return intValue;
    }

    public static boolean isBTConnected(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice != null) {
            try {
                Class[] clsArr = null;
                Method declaredMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", null);
                declaredMethod.setAccessible(true);
                Object[] objArr = null;
                return ((Boolean) declaredMethod.invoke(bluetoothDevice, null)).booleanValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static List<BluetoothDevice> getSystemConnectedBtDeviceList() {
        List<BluetoothDevice> connectedDevices;
        ArrayList arrayList = new ArrayList();
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            int btAdapterConnectionState = getBtAdapterConnectionState(defaultAdapter);
            if (btAdapterConnectionState == 2 || btAdapterConnectionState == 0) {
                Set<BluetoothDevice> bondedDevices = defaultAdapter.getBondedDevices();
                String str = TAG;
                JL_Log.i(str, "-getSystemConnectedBtDeviceList- devices:" + bondedDevices.size());
                Iterator<BluetoothDevice> it = bondedDevices.iterator();
                while (it.hasNext()) {
                    BluetoothDevice next = it.next();
                    boolean isBTConnected = isBTConnected(next);
                    String str2 = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("-getSystemConnectedBtDeviceList- bound device:");
                    sb.append(next == null ? "null" : next.getName());
                    sb.append(", isConnected : ");
                    sb.append(isBTConnected);
                    JL_Log.i(str2, sb.toString());
                    if (isBTConnected) {
                        arrayList.add(next);
                    }
                }
            }
            BluetoothManager bluetoothManager = (BluetoothManager) CommonUtil.getMainContext().getSystemService("bluetooth");
            if (bluetoothManager != null && (connectedDevices = bluetoothManager.getConnectedDevices(7)) != null) {
                Iterator<BluetoothDevice> it2 = connectedDevices.iterator();
                while (it2.hasNext()) {
                    BluetoothDevice next2 = it2.next();
                    String str3 = TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("-getSystemConnectedBtDeviceList- connect device:");
                    sb2.append(next2 == null ? "null" : next2.getName());
                    JL_Log.i(str3, sb2.toString());
                    if (!arrayList.contains(next2)) {
                        String str4 = TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("-getSystemConnectedBtDeviceList- add list, device:");
                        sb3.append(next2 == null ? "null" : next2.getName());
                        JL_Log.i(str4, sb3.toString());
                        arrayList.add(next2);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (arrayList.size() > 0) {
            return arrayList;
        }
        return null;
    }

    public static String hexDataCovetToAddress(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr != null && bArr.length == 6) {
            for (int i = 0; i < bArr.length; i++) {
                char[] cArr = mChars;
                sb.append(cArr[(bArr[i] & 255) >> 4]);
                sb.append(cArr[bArr[i] & 15]);
                if (i != bArr.length - 1) {
                    sb.append(":");
                }
            }
        }
        return sb.toString();
    }

    public static byte[] hexStringCovertToByteArray(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            int i3 = i2 + 2;
            if (i3 <= str.length()) {
                try {
                    bArr[i] = (byte) Integer.valueOf(str.substring(i2, i3), 16).intValue();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return bArr;
    }

    public static String reverseAddressString(String str) {
        if (!BluetoothAdapter.checkBluetoothAddress(str)) {
            return str;
        }
        if (str.contains(":")) {
            str = str.replaceAll(":", "");
        }
        byte[] hexStringCovertToByteArray = hexStringCovertToByteArray(str);
        if (hexStringCovertToByteArray == null) {
            return str;
        }
        byte[] bArr = new byte[6];
        int length = hexStringCovertToByteArray.length - 1;
        int i = 0;
        while (true) {
            if ((i < hexStringCovertToByteArray.length) & (length >= 0)) {
                bArr[i] = hexStringCovertToByteArray[length];
                i++;
                length--;
            } else {
                return hexDataCovetToAddress(bArr);
            }
        }
    }

    public static boolean cancelPairingUserInput(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice != null) {
            try {
                Class[] clsArr = null;
                Method declaredMethod = BluetoothDevice.class.getDeclaredMethod("cancelPairingUserInput", null);
                declaredMethod.setAccessible(true);
                Object[] objArr = null;
                return ((Boolean) declaredMethod.invoke(bluetoothDevice, null)).booleanValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String printBtDeviceInfo(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice != null) {
            return "name : " + bluetoothDevice.getName() + " ,type : " + bluetoothDevice.getType() + " ,address : " + bluetoothDevice.getAddress();
        }
        return "null";
    }

    public static String getBluetoothMacAddress() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    Field declaredField = defaultAdapter.getClass().getDeclaredField("mService");
                    declaredField.setAccessible(true);
                    Object obj = declaredField.get(defaultAdapter);
                    return obj != null ? (String) obj.getClass().getMethod("getAddress", new Class[0]).invoke(obj, new Object[0]) : "";
                } catch (Exception unused) {
                    return "";
                }
            }
            return defaultAdapter.getAddress();
        }
        return "";
    }

    public static byte[] addressCovertToByteArray(String str) {
        if (TextUtils.isEmpty(str) || !str.contains(":")) {
            return null;
        }
        String replace = str.replace(":", "");
        int length = replace.length() / 2;
        byte[] bArr = new byte[length];
        if (length == 6) {
            for (int i = 0; i < length; i++) {
                int i2 = i * 2;
                int i3 = i2 + 2;
                if (i3 <= replace.length()) {
                    try {
                        bArr[i] = (byte) Integer.valueOf(replace.substring(i2, i3), 16).intValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
            return bArr;
        }
        return null;
    }

    public static byte[] getVirtualAddress() {
        boolean z;
        byte[] addressCovertToByteArray;
        byte[] bArr = new byte[4];
        String bluetoothMacAddress = getBluetoothMacAddress();
        if (TextUtils.isEmpty(bluetoothMacAddress) || bluetoothMacAddress.equals("02:00:00:00:00:00") || (addressCovertToByteArray = addressCovertToByteArray(bluetoothMacAddress)) == null || addressCovertToByteArray.length != 6) {
            z = true;
        } else {
            System.arraycopy(addressCovertToByteArray, 2, bArr, 0, 4);
            z = false;
        }
        if (z) {
            Random random = new Random();
            for (int i = 0; i < 4; i++) {
                bArr[i] = (byte) random.nextInt(Integer.MAX_VALUE);
            }
        }
        return bArr;
    }

    public static boolean isBluetoothEnable() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        return defaultAdapter != null && defaultAdapter.isEnabled();
    }

    public static boolean deviceHasProfile(BluetoothDevice bluetoothDevice, UUID uuid) {
        ParcelUuid[] uuids;
        if (!isBluetoothEnable() || uuid == null || TextUtils.isEmpty(uuid.toString()) || bluetoothDevice == null || (uuids = bluetoothDevice.getUuids()) == null) {
            return false;
        }
        for (ParcelUuid parcelUuid : uuids) {
            if (parcelUuid != null && uuid.toString().toLowerCase().equals(parcelUuid.toString())) {
                return true;
            }
        }
        return false;
    }

    public static Method getDeclaredMethod() throws Exception {
        return Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
    }

    public static boolean setPriority(BluetoothProfile bluetoothProfile, BluetoothDevice bluetoothDevice, int i) {
        if (bluetoothProfile != null && bluetoothDevice != null) {
            try {
                Method method = (Method) getDeclaredMethod().invoke(bluetoothProfile.getClass(), "setPriority", new Class[]{BluetoothDevice.class, Integer.TYPE});
                if (method == null) {
                    return false;
                }
                return ((Boolean) method.invoke(bluetoothProfile, bluetoothDevice, Integer.valueOf(i))).booleanValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static int getPriority(BluetoothProfile bluetoothProfile, BluetoothDevice bluetoothDevice) {
        if (bluetoothProfile != null && bluetoothDevice != null) {
            try {
                Method method = (Method) getDeclaredMethod().invoke(bluetoothProfile.getClass(), "getPriority", new Class[]{BluetoothDevice.class});
                if (method == null) {
                    return 0;
                }
                return ((Integer) method.invoke(bluetoothProfile, bluetoothDevice)).intValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static boolean setActivityDevice(BluetoothA2dp bluetoothA2dp, BluetoothDevice bluetoothDevice) {
        if (bluetoothA2dp == null || bluetoothDevice == null) {
            return false;
        }
        try {
            Method declaredMethod = bluetoothA2dp.getClass().getDeclaredMethod("setActiveDevice", bluetoothDevice.getClass());
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(bluetoothA2dp, bluetoothDevice)).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static BluetoothDevice getActivityDevice(BluetoothA2dp bluetoothA2dp) {
        if (bluetoothA2dp == null) {
            return null;
        }
        try {
            Method declaredMethod = bluetoothA2dp.getClass().getDeclaredMethod("getActiveDevice", new Class[0]);
            declaredMethod.setAccessible(true);
            return (BluetoothDevice) declaredMethod.invoke(bluetoothA2dp, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void printBleGattServices(BluetoothDevice bluetoothDevice, BluetoothGatt bluetoothGatt, int i) {
        if (bluetoothDevice == null || bluetoothGatt == null || !JL_Log.isIsLog()) {
            return;
        }
        String str = TAG;
        JL_Log.d(str, "[[============================Bluetooth[" + bluetoothDevice.getName() + "] Services [" + i + "]=================================]]\n");
        List<BluetoothGattService> services = bluetoothGatt.getServices();
        if (services != null) {
            String str2 = TAG;
            JL_Log.d(str2, "[[======Service Size:" + services.size() + "======================\n");
            for (BluetoothGattService bluetoothGattService : services) {
                if (bluetoothGattService != null) {
                    String str3 = TAG;
                    JL_Log.d(str3, "[[======Service:" + bluetoothGattService.getUuid() + "======================\n");
                    List<BluetoothGattCharacteristic> characteristics = bluetoothGattService.getCharacteristics();
                    if (characteristics != null) {
                        String str4 = TAG;
                        JL_Log.d(str4, "[[[[=============characteristics Size:" + characteristics.size() + "======================\n");
                        for (BluetoothGattCharacteristic bluetoothGattCharacteristic : characteristics) {
                            if (bluetoothGattCharacteristic != null) {
                                String str5 = TAG;
                                JL_Log.d(str5, "[[[[=============characteristic:" + bluetoothGattCharacteristic.getUuid() + ",write type : " + bluetoothGattCharacteristic.getWriteType() + "======================\n");
                                List<BluetoothGattDescriptor> descriptors = bluetoothGattCharacteristic.getDescriptors();
                                if (descriptors != null) {
                                    String str6 = TAG;
                                    JL_Log.d(str6, "[[[[[[=============descriptors Size:" + descriptors.size() + "======================\n");
                                    for (BluetoothGattDescriptor bluetoothGattDescriptor : descriptors) {
                                        if (bluetoothGattDescriptor != null) {
                                            String str7 = TAG;
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("[[[[[[=============descriptor:");
                                            sb.append(bluetoothGattDescriptor.getUuid());
                                            sb.append(",permission:");
                                            sb.append(bluetoothGattDescriptor.getPermissions());
                                            sb.append("\nvalue : ");
                                            sb.append(bluetoothGattDescriptor.getValue() == null ? "null" : CHexConver.byte2HexStr(bluetoothGattDescriptor.getValue(), bluetoothGattDescriptor.getValue().length));
                                            sb.append("======================\n");
                                            JL_Log.d(str7, sb.toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        String str8 = TAG;
        JL_Log.d(str8, "[[============================Bluetooth[" + bluetoothDevice.getName() + "] Services show End=================================]]\n");
    }
}
