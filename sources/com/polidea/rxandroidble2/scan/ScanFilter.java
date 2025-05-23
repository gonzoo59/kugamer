package com.polidea.rxandroidble2.scan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.scan.RxBleInternalScanResult;
import com.polidea.rxandroidble2.internal.scan.ScanFilterInterface;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
/* loaded from: classes2.dex */
public class ScanFilter implements Parcelable, ScanFilterInterface {
    private final String mDeviceAddress;
    private final String mDeviceName;
    private final byte[] mManufacturerData;
    private final byte[] mManufacturerDataMask;
    private final int mManufacturerId;
    private final byte[] mServiceData;
    private final byte[] mServiceDataMask;
    private final ParcelUuid mServiceDataUuid;
    private final ParcelUuid mServiceUuid;
    private final ParcelUuid mServiceUuidMask;
    private static final ScanFilter EMPTY = new Builder().build();
    public static final Parcelable.Creator<ScanFilter> CREATOR = new Parcelable.Creator<ScanFilter>() { // from class: com.polidea.rxandroidble2.scan.ScanFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ScanFilter[] newArray(int i) {
            return new ScanFilter[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ScanFilter createFromParcel(Parcel parcel) {
            Builder builder = new Builder();
            if (parcel.readInt() == 1) {
                builder.setDeviceName(parcel.readString());
            }
            if (parcel.readInt() == 1) {
                builder.setDeviceAddress(parcel.readString());
            }
            if (parcel.readInt() == 1) {
                ParcelUuid parcelUuid = (ParcelUuid) parcel.readParcelable(ParcelUuid.class.getClassLoader());
                builder.setServiceUuid(parcelUuid);
                if (parcel.readInt() == 1) {
                    builder.setServiceUuid(parcelUuid, (ParcelUuid) parcel.readParcelable(ParcelUuid.class.getClassLoader()));
                }
            }
            if (parcel.readInt() == 1) {
                ParcelUuid parcelUuid2 = (ParcelUuid) parcel.readParcelable(ParcelUuid.class.getClassLoader());
                if (parcel.readInt() == 1) {
                    byte[] bArr = new byte[parcel.readInt()];
                    parcel.readByteArray(bArr);
                    if (parcel.readInt() == 0) {
                        builder.setServiceData(parcelUuid2, bArr);
                    } else {
                        byte[] bArr2 = new byte[parcel.readInt()];
                        parcel.readByteArray(bArr2);
                        builder.setServiceData(parcelUuid2, bArr, bArr2);
                    }
                }
            }
            int readInt = parcel.readInt();
            if (parcel.readInt() == 1) {
                byte[] bArr3 = new byte[parcel.readInt()];
                parcel.readByteArray(bArr3);
                if (parcel.readInt() == 0) {
                    builder.setManufacturerData(readInt, bArr3);
                } else {
                    byte[] bArr4 = new byte[parcel.readInt()];
                    parcel.readByteArray(bArr4);
                    builder.setManufacturerData(readInt, bArr3, bArr4);
                }
            }
            return builder.build();
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ScanFilter(String str, String str2, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, ParcelUuid parcelUuid3, byte[] bArr, byte[] bArr2, int i, byte[] bArr3, byte[] bArr4) {
        this.mDeviceName = str;
        this.mServiceUuid = parcelUuid;
        this.mServiceUuidMask = parcelUuid2;
        this.mDeviceAddress = str2;
        this.mServiceDataUuid = parcelUuid3;
        this.mServiceData = bArr;
        this.mServiceDataMask = bArr2;
        this.mManufacturerId = i;
        this.mManufacturerData = bArr3;
        this.mManufacturerDataMask = bArr4;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDeviceName == null ? 0 : 1);
        String str = this.mDeviceName;
        if (str != null) {
            parcel.writeString(str);
        }
        parcel.writeInt(this.mDeviceAddress == null ? 0 : 1);
        String str2 = this.mDeviceAddress;
        if (str2 != null) {
            parcel.writeString(str2);
        }
        parcel.writeInt(this.mServiceUuid == null ? 0 : 1);
        ParcelUuid parcelUuid = this.mServiceUuid;
        if (parcelUuid != null) {
            parcel.writeParcelable(parcelUuid, i);
            parcel.writeInt(this.mServiceUuidMask == null ? 0 : 1);
            ParcelUuid parcelUuid2 = this.mServiceUuidMask;
            if (parcelUuid2 != null) {
                parcel.writeParcelable(parcelUuid2, i);
            }
        }
        parcel.writeInt(this.mServiceDataUuid == null ? 0 : 1);
        ParcelUuid parcelUuid3 = this.mServiceDataUuid;
        if (parcelUuid3 != null) {
            parcel.writeParcelable(parcelUuid3, i);
            parcel.writeInt(this.mServiceData == null ? 0 : 1);
            byte[] bArr = this.mServiceData;
            if (bArr != null) {
                parcel.writeInt(bArr.length);
                parcel.writeByteArray(this.mServiceData);
                parcel.writeInt(this.mServiceDataMask == null ? 0 : 1);
                byte[] bArr2 = this.mServiceDataMask;
                if (bArr2 != null) {
                    parcel.writeInt(bArr2.length);
                    parcel.writeByteArray(this.mServiceDataMask);
                }
            }
        }
        parcel.writeInt(this.mManufacturerId);
        parcel.writeInt(this.mManufacturerData == null ? 0 : 1);
        byte[] bArr3 = this.mManufacturerData;
        if (bArr3 != null) {
            parcel.writeInt(bArr3.length);
            parcel.writeByteArray(this.mManufacturerData);
            parcel.writeInt(this.mManufacturerDataMask != null ? 1 : 0);
            byte[] bArr4 = this.mManufacturerDataMask;
            if (bArr4 != null) {
                parcel.writeInt(bArr4.length);
                parcel.writeByteArray(this.mManufacturerDataMask);
            }
        }
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public ParcelUuid getServiceUuid() {
        return this.mServiceUuid;
    }

    public ParcelUuid getServiceUuidMask() {
        return this.mServiceUuidMask;
    }

    public String getDeviceAddress() {
        return this.mDeviceAddress;
    }

    public byte[] getServiceData() {
        return this.mServiceData;
    }

    public byte[] getServiceDataMask() {
        return this.mServiceDataMask;
    }

    public ParcelUuid getServiceDataUuid() {
        return this.mServiceDataUuid;
    }

    public int getManufacturerId() {
        return this.mManufacturerId;
    }

    public byte[] getManufacturerData() {
        return this.mManufacturerData;
    }

    public byte[] getManufacturerDataMask() {
        return this.mManufacturerDataMask;
    }

    @Override // com.polidea.rxandroidble2.internal.scan.ScanFilterInterface
    public boolean matches(RxBleInternalScanResult rxBleInternalScanResult) {
        if (rxBleInternalScanResult == null) {
            return false;
        }
        BluetoothDevice bluetoothDevice = rxBleInternalScanResult.getBluetoothDevice();
        String str = this.mDeviceAddress;
        if (str == null || (bluetoothDevice != null && str.equals(bluetoothDevice.getAddress()))) {
            ScanRecord scanRecord = rxBleInternalScanResult.getScanRecord();
            if (scanRecord != null || (this.mDeviceName == null && this.mServiceUuid == null && this.mManufacturerData == null && this.mServiceData == null)) {
                String str2 = this.mDeviceName;
                if (str2 == null || str2.equals(scanRecord.getDeviceName()) || this.mDeviceName.equals(bluetoothDevice.getName())) {
                    ParcelUuid parcelUuid = this.mServiceUuid;
                    if (parcelUuid == null || matchesServiceUuids(parcelUuid, this.mServiceUuidMask, scanRecord.getServiceUuids())) {
                        ParcelUuid parcelUuid2 = this.mServiceDataUuid;
                        if (parcelUuid2 == null || matchesPartialData(this.mServiceData, this.mServiceDataMask, scanRecord.getServiceData(parcelUuid2))) {
                            int i = this.mManufacturerId;
                            return i < 0 || matchesPartialData(this.mManufacturerData, this.mManufacturerDataMask, scanRecord.getManufacturerSpecificData(i));
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private static boolean matchesServiceUuids(ParcelUuid parcelUuid, ParcelUuid parcelUuid2, List<ParcelUuid> list) {
        if (parcelUuid == null) {
            return true;
        }
        if (list == null) {
            return false;
        }
        for (ParcelUuid parcelUuid3 : list) {
            if (matchesServiceUuid(parcelUuid.getUuid(), parcelUuid2 == null ? null : parcelUuid2.getUuid(), parcelUuid3.getUuid())) {
                return true;
            }
        }
        return false;
    }

    private static boolean matchesServiceUuid(UUID uuid, UUID uuid2, UUID uuid3) {
        if (uuid2 == null) {
            return uuid.equals(uuid3);
        }
        if ((uuid.getLeastSignificantBits() & uuid2.getLeastSignificantBits()) != (uuid3.getLeastSignificantBits() & uuid2.getLeastSignificantBits())) {
            return false;
        }
        return (uuid.getMostSignificantBits() & uuid2.getMostSignificantBits()) == (uuid2.getMostSignificantBits() & uuid3.getMostSignificantBits());
    }

    private static boolean matchesPartialData(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        if (bArr3 == null || bArr3.length < bArr.length) {
            return false;
        }
        if (bArr2 == null) {
            for (int i = 0; i < bArr.length; i++) {
                if (bArr3[i] != bArr[i]) {
                    return false;
                }
            }
            return true;
        }
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if ((bArr2[i2] & bArr3[i2]) != (bArr2[i2] & bArr[i2])) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BluetoothLeScanFilter [mDeviceName=");
        sb.append(this.mDeviceName);
        sb.append(", ");
        sb.append(LoggerUtil.commonMacMessage(this.mDeviceAddress));
        sb.append(", mUuid=");
        ParcelUuid parcelUuid = this.mServiceUuid;
        sb.append(parcelUuid == null ? null : LoggerUtil.getUuidToLog(parcelUuid.getUuid()));
        sb.append(", mUuidMask=");
        ParcelUuid parcelUuid2 = this.mServiceUuidMask;
        sb.append(parcelUuid2 == null ? null : LoggerUtil.getUuidToLog(parcelUuid2.getUuid()));
        sb.append(", mServiceDataUuid=");
        ParcelUuid parcelUuid3 = this.mServiceDataUuid;
        sb.append(parcelUuid3 != null ? LoggerUtil.getUuidToLog(parcelUuid3.getUuid()) : null);
        sb.append(", mServiceData=");
        sb.append(Arrays.toString(this.mServiceData));
        sb.append(", mServiceDataMask=");
        sb.append(Arrays.toString(this.mServiceDataMask));
        sb.append(", mManufacturerId=");
        sb.append(this.mManufacturerId);
        sb.append(", mManufacturerData=");
        sb.append(Arrays.toString(this.mManufacturerData));
        sb.append(", mManufacturerDataMask=");
        sb.append(Arrays.toString(this.mManufacturerDataMask));
        sb.append("]");
        return sb.toString();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mDeviceName, this.mDeviceAddress, Integer.valueOf(this.mManufacturerId), Integer.valueOf(Arrays.hashCode(this.mManufacturerData)), Integer.valueOf(Arrays.hashCode(this.mManufacturerDataMask)), this.mServiceDataUuid, Integer.valueOf(Arrays.hashCode(this.mServiceData)), Integer.valueOf(Arrays.hashCode(this.mServiceDataMask)), this.mServiceUuid, this.mServiceUuidMask});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ScanFilter scanFilter = (ScanFilter) obj;
        return equals(this.mDeviceName, scanFilter.mDeviceName) && equals(this.mDeviceAddress, scanFilter.mDeviceAddress) && this.mManufacturerId == scanFilter.mManufacturerId && deepEquals(this.mManufacturerData, scanFilter.mManufacturerData) && deepEquals(this.mManufacturerDataMask, scanFilter.mManufacturerDataMask) && equals(this.mServiceDataUuid, scanFilter.mServiceDataUuid) && deepEquals(this.mServiceData, scanFilter.mServiceData) && deepEquals(this.mServiceDataMask, scanFilter.mServiceDataMask) && equals(this.mServiceUuid, scanFilter.mServiceUuid) && equals(this.mServiceUuidMask, scanFilter.mServiceUuidMask);
    }

    private static boolean equals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    private static boolean deepEquals(byte[] bArr, byte[] bArr2) {
        return bArr == bArr2 || !(bArr == null || bArr2 == null || !Arrays.equals(bArr, bArr2));
    }

    @Override // com.polidea.rxandroidble2.internal.scan.ScanFilterInterface
    public boolean isAllFieldsEmpty() {
        return equals(EMPTY);
    }

    public static ScanFilter empty() {
        return new Builder().build();
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private String mDeviceAddress;
        private String mDeviceName;
        private byte[] mManufacturerData;
        private byte[] mManufacturerDataMask;
        private int mManufacturerId = -1;
        private byte[] mServiceData;
        private byte[] mServiceDataMask;
        private ParcelUuid mServiceDataUuid;
        private ParcelUuid mServiceUuid;
        private ParcelUuid mUuidMask;

        public Builder setDeviceName(String str) {
            this.mDeviceName = str;
            return this;
        }

        public Builder setDeviceAddress(String str) {
            if (str != null && !BluetoothAdapter.checkBluetoothAddress(str)) {
                throw new IllegalArgumentException("invalid device address " + str);
            }
            this.mDeviceAddress = str;
            return this;
        }

        public Builder setServiceUuid(ParcelUuid parcelUuid) {
            this.mServiceUuid = parcelUuid;
            this.mUuidMask = null;
            return this;
        }

        public Builder setServiceUuid(ParcelUuid parcelUuid, ParcelUuid parcelUuid2) {
            if (this.mUuidMask != null && this.mServiceUuid == null) {
                throw new IllegalArgumentException("uuid is null while uuidMask is not null!");
            }
            this.mServiceUuid = parcelUuid;
            this.mUuidMask = parcelUuid2;
            return this;
        }

        public Builder setServiceData(ParcelUuid parcelUuid, byte[] bArr) {
            if (parcelUuid == null) {
                throw new IllegalArgumentException("serviceDataUuid is null");
            }
            this.mServiceDataUuid = parcelUuid;
            this.mServiceData = bArr;
            this.mServiceDataMask = null;
            return this;
        }

        public Builder setServiceData(ParcelUuid parcelUuid, byte[] bArr, byte[] bArr2) {
            if (parcelUuid == null) {
                throw new IllegalArgumentException("serviceDataUuid is null");
            }
            byte[] bArr3 = this.mServiceDataMask;
            if (bArr3 != null) {
                byte[] bArr4 = this.mServiceData;
                if (bArr4 == null) {
                    throw new IllegalArgumentException("serviceData is null while serviceDataMask is not null");
                }
                if (bArr4.length != bArr3.length) {
                    throw new IllegalArgumentException("size mismatch for service data and service data mask");
                }
            }
            this.mServiceDataUuid = parcelUuid;
            this.mServiceData = bArr;
            this.mServiceDataMask = bArr2;
            return this;
        }

        public Builder setManufacturerData(int i, byte[] bArr) {
            if (bArr != null && i < 0) {
                throw new IllegalArgumentException("invalid manufacture id");
            }
            this.mManufacturerId = i;
            this.mManufacturerData = bArr;
            this.mManufacturerDataMask = null;
            return this;
        }

        public Builder setManufacturerData(int i, byte[] bArr, byte[] bArr2) {
            if (bArr != null && i < 0) {
                throw new IllegalArgumentException("invalid manufacture id");
            }
            byte[] bArr3 = this.mManufacturerDataMask;
            if (bArr3 != null) {
                byte[] bArr4 = this.mManufacturerData;
                if (bArr4 == null) {
                    throw new IllegalArgumentException("manufacturerData is null while manufacturerDataMask is not null");
                }
                if (bArr4.length != bArr3.length) {
                    throw new IllegalArgumentException("size mismatch for manufacturerData and manufacturerDataMask");
                }
            }
            this.mManufacturerId = i;
            this.mManufacturerData = bArr;
            this.mManufacturerDataMask = bArr2;
            return this;
        }

        public ScanFilter build() {
            return new ScanFilter(this.mDeviceName, this.mDeviceAddress, this.mServiceUuid, this.mUuidMask, this.mServiceDataUuid, this.mServiceData, this.mServiceDataMask, this.mManufacturerId, this.mManufacturerData, this.mManufacturerDataMask);
        }
    }
}
