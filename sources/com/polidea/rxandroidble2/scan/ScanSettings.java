package com.polidea.rxandroidble2.scan;

import android.os.Parcel;
import android.os.Parcelable;
import com.polidea.rxandroidble2.internal.scan.ExternalScanSettingsExtension;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/* loaded from: classes2.dex */
public class ScanSettings implements Parcelable, ExternalScanSettingsExtension<ScanSettings> {
    public static final int CALLBACK_TYPE_ALL_MATCHES = 1;
    public static final int CALLBACK_TYPE_FIRST_MATCH = 2;
    public static final int CALLBACK_TYPE_MATCH_LOST = 4;
    public static final Parcelable.Creator<ScanSettings> CREATOR = new Parcelable.Creator<ScanSettings>() { // from class: com.polidea.rxandroidble2.scan.ScanSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ScanSettings[] newArray(int i) {
            return new ScanSettings[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ScanSettings createFromParcel(Parcel parcel) {
            return new ScanSettings(parcel);
        }
    };
    public static final int MATCH_MODE_AGGRESSIVE = 1;
    public static final int MATCH_MODE_STICKY = 2;
    public static final int MATCH_NUM_FEW_ADVERTISEMENT = 2;
    public static final int MATCH_NUM_MAX_ADVERTISEMENT = 3;
    public static final int MATCH_NUM_ONE_ADVERTISEMENT = 1;
    public static final int SCAN_MODE_BALANCED = 1;
    public static final int SCAN_MODE_LOW_LATENCY = 2;
    public static final int SCAN_MODE_LOW_POWER = 0;
    public static final int SCAN_MODE_OPPORTUNISTIC = -1;
    private int mCallbackType;
    private int mMatchMode;
    private int mNumOfMatchesPerFilter;
    private long mReportDelayMillis;
    private int mScanMode;
    private boolean mShouldCheckLocationProviderState;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface CallbackType {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface MatchMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface MatchNum {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface ScanMode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getScanMode() {
        return this.mScanMode;
    }

    public int getCallbackType() {
        return this.mCallbackType;
    }

    public int getMatchMode() {
        return this.mMatchMode;
    }

    public int getNumOfMatches() {
        return this.mNumOfMatchesPerFilter;
    }

    public long getReportDelayMillis() {
        return this.mReportDelayMillis;
    }

    @Override // com.polidea.rxandroidble2.internal.scan.ExternalScanSettingsExtension
    public boolean shouldCheckLocationProviderState() {
        return this.mShouldCheckLocationProviderState;
    }

    ScanSettings(int i, int i2, long j, int i3, int i4, boolean z) {
        this.mScanMode = i;
        this.mCallbackType = i2;
        this.mReportDelayMillis = j;
        this.mNumOfMatchesPerFilter = i4;
        this.mMatchMode = i3;
        this.mShouldCheckLocationProviderState = z;
    }

    ScanSettings(Parcel parcel) {
        this.mScanMode = parcel.readInt();
        this.mCallbackType = parcel.readInt();
        this.mReportDelayMillis = parcel.readLong();
        this.mMatchMode = parcel.readInt();
        this.mNumOfMatchesPerFilter = parcel.readInt();
        this.mShouldCheckLocationProviderState = parcel.readInt() != 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mScanMode);
        parcel.writeInt(this.mCallbackType);
        parcel.writeLong(this.mReportDelayMillis);
        parcel.writeInt(this.mMatchMode);
        parcel.writeInt(this.mNumOfMatchesPerFilter);
        parcel.writeInt(this.mShouldCheckLocationProviderState ? 1 : 0);
    }

    @Override // com.polidea.rxandroidble2.internal.scan.ExternalScanSettingsExtension
    public ScanSettings copyWithCallbackType(int i) {
        return new ScanSettings(this.mScanMode, i, this.mReportDelayMillis, this.mMatchMode, this.mNumOfMatchesPerFilter, this.mShouldCheckLocationProviderState);
    }

    /* loaded from: classes2.dex */
    public static final class Builder implements ExternalScanSettingsExtension.Builder {
        private int mScanMode = 0;
        private int mCallbackType = 1;
        private long mReportDelayMillis = 0;
        private int mMatchMode = 1;
        private int mNumOfMatchesPerFilter = 3;
        private boolean mShouldCheckLocationProviderState = true;

        private static boolean isValidCallbackType(int i) {
            return i == 1 || i == 2 || i == 4 || i == 6;
        }

        public Builder setScanMode(int i) {
            if (i < -1 || i > 2) {
                throw new IllegalArgumentException("invalid scan mode " + i);
            }
            this.mScanMode = i;
            return this;
        }

        public Builder setCallbackType(int i) {
            if (!isValidCallbackType(i)) {
                throw new IllegalArgumentException("invalid callback type - " + i);
            }
            this.mCallbackType = i;
            return this;
        }

        @Override // com.polidea.rxandroidble2.internal.scan.ExternalScanSettingsExtension.Builder
        public Builder setShouldCheckLocationServicesState(boolean z) {
            this.mShouldCheckLocationProviderState = z;
            return this;
        }

        public ScanSettings build() {
            return new ScanSettings(this.mScanMode, this.mCallbackType, this.mReportDelayMillis, this.mMatchMode, this.mNumOfMatchesPerFilter, this.mShouldCheckLocationProviderState);
        }
    }
}
