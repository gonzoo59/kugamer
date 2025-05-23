package com.jieli.otasdk.util;

import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: JL_Constant.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/jieli/otasdk/util/JL_Constant;", "", "()V", "Companion", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class JL_Constant {
    public static final String ACTION_EXIT_APP = "com.jieli.ota.exit_app";
    public static final Companion Companion = new Companion(null);
    public static final String DIR_UPGRADE = "upgrade";
    public static final boolean HID_DEVICE_WAY = false;
    public static final boolean IS_NEED_DEVICE_AUTH = true;
    public static final String KEY_IS_HID_DEVICE = "is_hid_device";
    public static final String KEY_IS_USE_DEVICE_AUTH = "is_use_device_auth";
    public static final String KEY_USE_CUSTOM_RECONNECT_WAY = "use_custom_reconnect_way";
    public static final boolean NEED_CUSTOM_RECONNECT_WAY = false;
    public static final int SCAN_STATUS_FOUND_DEV = 2;
    public static final int SCAN_STATUS_IDLE = 0;
    public static final int SCAN_STATUS_SCANNING = 1;
    public static final boolean USE_SPP_MULTIPLE_CHANNEL = false;
    private static final UUID UUID_A2DP;
    private static final UUID UUID_SPP;

    /* compiled from: JL_Constant.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000eX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015¨\u0006\u0018"}, d2 = {"Lcom/jieli/otasdk/util/JL_Constant$Companion;", "", "()V", "ACTION_EXIT_APP", "", "DIR_UPGRADE", "HID_DEVICE_WAY", "", "IS_NEED_DEVICE_AUTH", "KEY_IS_HID_DEVICE", "KEY_IS_USE_DEVICE_AUTH", "KEY_USE_CUSTOM_RECONNECT_WAY", "NEED_CUSTOM_RECONNECT_WAY", "SCAN_STATUS_FOUND_DEV", "", "SCAN_STATUS_IDLE", "SCAN_STATUS_SCANNING", "USE_SPP_MULTIPLE_CHANNEL", "UUID_A2DP", "Ljava/util/UUID;", "getUUID_A2DP", "()Ljava/util/UUID;", "UUID_SPP", "getUUID_SPP", "otasdk_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final UUID getUUID_A2DP() {
            return JL_Constant.UUID_A2DP;
        }

        public final UUID getUUID_SPP() {
            return JL_Constant.UUID_SPP;
        }
    }

    static {
        UUID fromString = UUID.fromString("0000110b-0000-1000-8000-00805f9b34fb");
        Intrinsics.checkExpressionValueIsNotNull(fromString, "UUID.fromString(\"0000110…-1000-8000-00805f9b34fb\")");
        UUID_A2DP = fromString;
        UUID fromString2 = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        Intrinsics.checkExpressionValueIsNotNull(fromString2, "UUID.fromString(\"0000110…-1000-8000-00805f9b34fb\")");
        UUID_SPP = fromString2;
    }
}
