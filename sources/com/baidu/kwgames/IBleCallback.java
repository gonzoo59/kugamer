package com.baidu.kwgames;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
/* loaded from: classes.dex */
public interface IBleCallback extends IInterface {

    /* loaded from: classes.dex */
    public static class Default implements IBleCallback {
        @Override // com.baidu.kwgames.IBleCallback
        public void OnBleAICrosshairState(byte b) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void OnBleAIGunDownStageChanged(byte b) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void OnBleChangeAIGunPress(byte b) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void OnBleDynamicGunDataChanged(boolean z) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void OnBleGunPartsReduceChanged(byte b) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void OnUploadOtherParam(int i, byte[] bArr) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void OnUploadToupingParam(byte[] bArr) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void bleConnectState(int i) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void bleGunPressAI(byte[] bArr, int i) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void bleMTKData(byte[] bArr) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void bleMouseMove(byte[] bArr) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void bleMouseOn(boolean z, byte[] bArr) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void bleSystemStatus(byte[] bArr, int i) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void onConfigurationChanged(int i) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IBleCallback
        public void onDeviceConnectStateChanged(boolean z) throws RemoteException {
        }
    }

    void OnBleAICrosshairState(byte b) throws RemoteException;

    void OnBleAIGunDownStageChanged(byte b) throws RemoteException;

    void OnBleChangeAIGunPress(byte b) throws RemoteException;

    void OnBleDynamicGunDataChanged(boolean z) throws RemoteException;

    void OnBleGunPartsReduceChanged(byte b) throws RemoteException;

    void OnUploadOtherParam(int i, byte[] bArr) throws RemoteException;

    void OnUploadToupingParam(byte[] bArr) throws RemoteException;

    void bleConnectState(int i) throws RemoteException;

    void bleGunPressAI(byte[] bArr, int i) throws RemoteException;

    void bleMTKData(byte[] bArr) throws RemoteException;

    void bleMouseMove(byte[] bArr) throws RemoteException;

    void bleMouseOn(boolean z, byte[] bArr) throws RemoteException;

    void bleSystemStatus(byte[] bArr, int i) throws RemoteException;

    void onConfigurationChanged(int i) throws RemoteException;

    void onDeviceConnectStateChanged(boolean z) throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IBleCallback {
        private static final String DESCRIPTOR = "com.baidu.kwgames.IBleCallback";
        static final int TRANSACTION_OnBleAICrosshairState = 9;
        static final int TRANSACTION_OnBleAIGunDownStageChanged = 10;
        static final int TRANSACTION_OnBleChangeAIGunPress = 8;
        static final int TRANSACTION_OnBleDynamicGunDataChanged = 11;
        static final int TRANSACTION_OnBleGunPartsReduceChanged = 12;
        static final int TRANSACTION_OnUploadOtherParam = 15;
        static final int TRANSACTION_OnUploadToupingParam = 14;
        static final int TRANSACTION_bleConnectState = 1;
        static final int TRANSACTION_bleGunPressAI = 5;
        static final int TRANSACTION_bleMTKData = 4;
        static final int TRANSACTION_bleMouseMove = 7;
        static final int TRANSACTION_bleMouseOn = 6;
        static final int TRANSACTION_bleSystemStatus = 3;
        static final int TRANSACTION_onConfigurationChanged = 13;
        static final int TRANSACTION_onDeviceConnectStateChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBleCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBleCallback)) {
                return (IBleCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleConnectState(parcel.readInt());
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    onDeviceConnectStateChanged(parcel.readInt() != 0);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleSystemStatus(parcel.createByteArray(), parcel.readInt());
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleMTKData(parcel.createByteArray());
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleGunPressAI(parcel.createByteArray(), parcel.readInt());
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleMouseOn(parcel.readInt() != 0, parcel.createByteArray());
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleMouseMove(parcel.createByteArray());
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    OnBleChangeAIGunPress(parcel.readByte());
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    OnBleAICrosshairState(parcel.readByte());
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    OnBleAIGunDownStageChanged(parcel.readByte());
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    OnBleDynamicGunDataChanged(parcel.readInt() != 0);
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    OnBleGunPartsReduceChanged(parcel.readByte());
                    return true;
                case 13:
                    parcel.enforceInterface(DESCRIPTOR);
                    onConfigurationChanged(parcel.readInt());
                    return true;
                case 14:
                    parcel.enforceInterface(DESCRIPTOR);
                    OnUploadToupingParam(parcel.createByteArray());
                    return true;
                case 15:
                    parcel.enforceInterface(DESCRIPTOR);
                    OnUploadOtherParam(parcel.readInt(), parcel.createByteArray());
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class Proxy implements IBleCallback {
            public static IBleCallback sDefaultImpl;
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void bleConnectState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleConnectState(i);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void onDeviceConnectStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    if (this.mRemote.transact(2, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().onDeviceConnectStateChanged(z);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void bleSystemStatus(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(3, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleSystemStatus(bArr, i);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void bleMTKData(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(4, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleMTKData(bArr);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void bleGunPressAI(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(5, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleGunPressAI(bArr, i);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void bleMouseOn(boolean z, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(6, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleMouseOn(z, bArr);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void bleMouseMove(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(7, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleMouseMove(bArr);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void OnBleChangeAIGunPress(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    if (this.mRemote.transact(8, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().OnBleChangeAIGunPress(b);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void OnBleAICrosshairState(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    if (this.mRemote.transact(9, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().OnBleAICrosshairState(b);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void OnBleAIGunDownStageChanged(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    if (this.mRemote.transact(10, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().OnBleAIGunDownStageChanged(b);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void OnBleDynamicGunDataChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    if (this.mRemote.transact(11, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().OnBleDynamicGunDataChanged(z);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void OnBleGunPartsReduceChanged(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    if (this.mRemote.transact(12, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().OnBleGunPartsReduceChanged(b);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void onConfigurationChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(13, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().onConfigurationChanged(i);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void OnUploadToupingParam(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(14, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().OnUploadToupingParam(bArr);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IBleCallback
            public void OnUploadOtherParam(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(15, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().OnUploadOtherParam(i, bArr);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBleCallback iBleCallback) {
            if (Proxy.sDefaultImpl == null) {
                if (iBleCallback != null) {
                    Proxy.sDefaultImpl = iBleCallback;
                    return true;
                }
                return false;
            }
            throw new IllegalStateException("setDefaultImpl() called twice");
        }

        public static IBleCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
