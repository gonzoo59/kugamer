package com.polidea.rxandroidble2.internal.logger;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.operations.Operation;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import okhttp3.HttpUrl;
/* loaded from: classes2.dex */
public class LoggerUtil {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static String commonCallbackMessage() {
        return " %24s()";
    }

    private static String commonStatusMessage() {
        return ", status=%d";
    }

    private static String commonValueMessage() {
        return ", value=%s";
    }

    private LoggerUtil() {
    }

    public static String bytesToHex(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        if (RxBleLog.getShouldLogAttributeValues()) {
            int length = bArr.length;
            if (length == 0) {
                return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
            }
            int i = length - 1;
            int i2 = (length * 2) + (i * 2) + 2;
            char[] cArr = new char[i2];
            for (int i3 = 0; i3 < length; i3++) {
                int i4 = bArr[i3] & 255;
                int i5 = i3 * 2;
                int i6 = i5 + 1 + i5;
                char[] cArr2 = HEX_ARRAY;
                cArr[i6] = cArr2[i4 >>> 4];
                cArr[i6 + 1] = cArr2[i4 & 15];
            }
            for (int i7 = 0; i7 < i; i7++) {
                int i8 = i7 * 2;
                int i9 = i8 + 1 + i8 + 2;
                cArr[i9] = ',';
                cArr[i9 + 1] = ' ';
            }
            cArr[0] = '[';
            cArr[i2 - 1] = ']';
            return new String(cArr);
        }
        return "[...]";
    }

    public static void logOperationStarted(Operation operation) {
        if (RxBleLog.isAtLeast(3)) {
            RxBleLog.d("STARTED  %s(%d)", operation.getClass().getSimpleName(), Integer.valueOf(System.identityHashCode(operation)));
        }
    }

    public static void logOperationRunning(Operation operation) {
        RxBleLog.i("RUNNING  %s", operation);
    }

    public static void logOperationRemoved(Operation operation) {
        if (RxBleLog.isAtLeast(3)) {
            RxBleLog.d("REMOVED  %s(%d)", operation.getClass().getSimpleName(), Integer.valueOf(System.identityHashCode(operation)));
        }
    }

    public static void logOperationQueued(Operation operation) {
        if (RxBleLog.isAtLeast(3)) {
            RxBleLog.d("QUEUED   %s(%d)", operation.getClass().getSimpleName(), Integer.valueOf(System.identityHashCode(operation)));
        }
    }

    public static void logOperationFinished(Operation operation, long j, long j2) {
        if (RxBleLog.isAtLeast(3)) {
            RxBleLog.d("FINISHED %s(%d) in %d ms", operation.getClass().getSimpleName(), Integer.valueOf(System.identityHashCode(operation)), Long.valueOf(j2 - j));
        }
    }

    public static void logOperationSkippedBecauseDisposedWhenAboutToRun(Operation operation) {
        if (RxBleLog.isAtLeast(2)) {
            RxBleLog.v("SKIPPED  %s(%d) just before running — is disposed", operation.getClass().getSimpleName(), Integer.valueOf(System.identityHashCode(operation)));
        }
    }

    public static void logCallback(String str, BluetoothGatt bluetoothGatt, int i, BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z) {
        if (RxBleLog.isAtLeast(4)) {
            AttributeLogWrapper attributeLogWrapper = new AttributeLogWrapper(bluetoothGattCharacteristic.getUuid(), bluetoothGattCharacteristic.getValue(), z);
            RxBleLog.i(commonMacMessage(bluetoothGatt) + commonCallbackMessage() + commonStatusMessage() + commonValueMessage(), str, Integer.valueOf(i), attributeLogWrapper);
        }
    }

    public static void logCallback(String str, BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z) {
        if (RxBleLog.isAtLeast(4)) {
            AttributeLogWrapper attributeLogWrapper = new AttributeLogWrapper(bluetoothGattCharacteristic.getUuid(), bluetoothGattCharacteristic.getValue(), z);
            RxBleLog.i(commonMacMessage(bluetoothGatt) + commonCallbackMessage() + commonValueMessage(), str, attributeLogWrapper);
        }
    }

    public static void logCallback(String str, BluetoothGatt bluetoothGatt, int i, BluetoothGattDescriptor bluetoothGattDescriptor, boolean z) {
        if (RxBleLog.isAtLeast(4)) {
            AttributeLogWrapper attributeLogWrapper = new AttributeLogWrapper(bluetoothGattDescriptor.getUuid(), bluetoothGattDescriptor.getValue(), z);
            RxBleLog.i(commonMacMessage(bluetoothGatt) + commonCallbackMessage() + commonStatusMessage() + commonValueMessage(), str, Integer.valueOf(i), attributeLogWrapper);
        }
    }

    public static void logCallback(String str, BluetoothGatt bluetoothGatt, int i) {
        if (RxBleLog.isAtLeast(4)) {
            RxBleLog.i(commonMacMessage(bluetoothGatt) + commonCallbackMessage() + commonStatusMessage(), str, Integer.valueOf(i));
        }
    }

    public static void logCallback(String str, BluetoothGatt bluetoothGatt, int i, int i2) {
        if (RxBleLog.isAtLeast(4)) {
            RxBleLog.i(commonMacMessage(bluetoothGatt) + commonCallbackMessage() + commonStatusMessage() + commonValueMessage(), str, Integer.valueOf(i), Integer.valueOf(i2));
        }
    }

    public static void logConnectionUpdateCallback(String str, BluetoothGatt bluetoothGatt, int i, int i2, int i3, int i4) {
        if (RxBleLog.isAtLeast(4)) {
            RxBleLog.i(commonMacMessage(bluetoothGatt) + commonCallbackMessage() + commonStatusMessage() + ", interval=%d (%.2f ms), latency=%d, timeout=%d (%.0f ms)", str, Integer.valueOf(i), Integer.valueOf(i2), Float.valueOf(i2 * 1.25f), Integer.valueOf(i3), Integer.valueOf(i4), Float.valueOf(i4 * 10.0f));
        }
    }

    public static String commonMacMessage(BluetoothGatt bluetoothGatt) {
        return bluetoothGatt == null ? "MAC=null" : commonMacMessage(bluetoothGatt.getDevice().getAddress());
    }

    public static String commonMacMessage(String str) {
        if (str == null) {
            return "MAC=null";
        }
        int macAddressLogSetting = RxBleLog.getMacAddressLogSetting();
        if (macAddressLogSetting == 3) {
            str = str.substring(0, 15) + "XX";
        } else if (macAddressLogSetting == Integer.MAX_VALUE) {
            str = "XX:XX:XX:XX:XX:XX";
        }
        return String.format("MAC='%s'", str);
    }

    public static AttributeLogWrapper wrap(BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z) {
        return new AttributeLogWrapper(bluetoothGattCharacteristic.getUuid(), bluetoothGattCharacteristic.getValue(), z);
    }

    public static AttributeLogWrapper wrap(BluetoothGattDescriptor bluetoothGattDescriptor, boolean z) {
        return new AttributeLogWrapper(bluetoothGattDescriptor.getUuid(), bluetoothGattDescriptor.getValue(), z);
    }

    public static String getUuidToLog(UUID uuid) {
        return RxBleLog.getUuidLogSetting() == 2 ? uuid.toString() : "...";
    }

    public static String getUuidSetToLog(Set<UUID> set) {
        int size = set.size();
        String[] strArr = new String[size];
        Iterator<UUID> it = set.iterator();
        for (int i = 0; i < size; i++) {
            strArr[i] = getUuidToLog(it.next());
        }
        return Arrays.toString(strArr);
    }

    /* loaded from: classes2.dex */
    public static class AttributeLogWrapper {
        private final UUID uuid;
        private final byte[] value;
        private final boolean valueMatters;

        public AttributeLogWrapper(UUID uuid, byte[] bArr, boolean z) {
            this.uuid = uuid;
            this.value = bArr;
            this.valueMatters = z;
        }

        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append("[uuid='");
            sb.append(LoggerUtil.getUuidToLog(this.uuid));
            if (this.valueMatters) {
                str = "', hexValue=" + LoggerUtil.bytesToHex(this.value);
            } else {
                str = "'";
            }
            sb.append(str);
            sb.append(']');
            return sb.toString();
        }
    }
}
