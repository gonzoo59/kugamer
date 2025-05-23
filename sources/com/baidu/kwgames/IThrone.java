package com.baidu.kwgames;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.baidu.kwgames.IBleCallback;
/* loaded from: classes.dex */
public interface IThrone extends IInterface {

    /* loaded from: classes.dex */
    public static class Default implements IThrone {
        @Override // com.baidu.kwgames.IThrone
        public void addBleCallback(IBleCallback iBleCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleChangeMode(byte b, byte b2, byte b3, String str) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleConnect(String str) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleDisConnect() throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleGetGunPressAI() throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleGetKeyMap(int i, int i2, int i3, int i4, int i5) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleSendKeyMap(byte[] bArr, int i) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleSendLongData(byte[] bArr) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleSendShortData(byte[] bArr) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleSetAIOff() throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleSetAIState(byte b, byte b2, byte b3, int i, byte[] bArr) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleSetAIStateM4(byte b, byte b2, byte b3, int i, byte b4, byte b5, byte[] bArr, byte[] bArr2) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public void bleSetSystemStatus(byte[] bArr) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public boolean isLandscape() throws RemoteException {
            return false;
        }

        @Override // com.baidu.kwgames.IThrone
        public void removeBleCallback(IBleCallback iBleCallback) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public void setInMainUI(boolean z) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public void setSystemStatusTask(boolean z) throws RemoteException {
        }

        @Override // com.baidu.kwgames.IThrone
        public boolean usbConnect() throws RemoteException {
            return false;
        }

        @Override // com.baidu.kwgames.IThrone
        public void usbDisconnect() throws RemoteException {
        }
    }

    void addBleCallback(IBleCallback iBleCallback) throws RemoteException;

    void bleChangeMode(byte b, byte b2, byte b3, String str) throws RemoteException;

    void bleConnect(String str) throws RemoteException;

    void bleDisConnect() throws RemoteException;

    void bleGetGunPressAI() throws RemoteException;

    void bleGetKeyMap(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void bleSendKeyMap(byte[] bArr, int i) throws RemoteException;

    void bleSendLongData(byte[] bArr) throws RemoteException;

    void bleSendShortData(byte[] bArr) throws RemoteException;

    void bleSetAIOff() throws RemoteException;

    void bleSetAIState(byte b, byte b2, byte b3, int i, byte[] bArr) throws RemoteException;

    void bleSetAIStateM4(byte b, byte b2, byte b3, int i, byte b4, byte b5, byte[] bArr, byte[] bArr2) throws RemoteException;

    void bleSetSystemStatus(byte[] bArr) throws RemoteException;

    boolean isLandscape() throws RemoteException;

    void removeBleCallback(IBleCallback iBleCallback) throws RemoteException;

    void setInMainUI(boolean z) throws RemoteException;

    void setSystemStatusTask(boolean z) throws RemoteException;

    boolean usbConnect() throws RemoteException;

    void usbDisconnect() throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IThrone {
        private static final String DESCRIPTOR = "com.baidu.kwgames.IThrone";
        static final int TRANSACTION_addBleCallback = 14;
        static final int TRANSACTION_bleChangeMode = 12;
        static final int TRANSACTION_bleConnect = 3;
        static final int TRANSACTION_bleDisConnect = 4;
        static final int TRANSACTION_bleGetGunPressAI = 9;
        static final int TRANSACTION_bleGetKeyMap = 8;
        static final int TRANSACTION_bleSendKeyMap = 17;
        static final int TRANSACTION_bleSendLongData = 18;
        static final int TRANSACTION_bleSendShortData = 19;
        static final int TRANSACTION_bleSetAIOff = 13;
        static final int TRANSACTION_bleSetAIState = 10;
        static final int TRANSACTION_bleSetAIStateM4 = 11;
        static final int TRANSACTION_bleSetSystemStatus = 7;
        static final int TRANSACTION_isLandscape = 16;
        static final int TRANSACTION_removeBleCallback = 15;
        static final int TRANSACTION_setInMainUI = 2;
        static final int TRANSACTION_setSystemStatusTask = 1;
        static final int TRANSACTION_usbConnect = 5;
        static final int TRANSACTION_usbDisconnect = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IThrone asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IThrone)) {
                return (IThrone) queryLocalInterface;
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
                    setSystemStatusTask(parcel.readInt() != 0);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    setInMainUI(parcel.readInt() != 0);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleConnect(parcel.readString());
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleDisConnect();
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean usbConnect = usbConnect();
                    parcel2.writeNoException();
                    parcel2.writeInt(usbConnect ? 1 : 0);
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    usbDisconnect();
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleSetSystemStatus(parcel.createByteArray());
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleGetKeyMap(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleGetGunPressAI();
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleSetAIState(parcel.readByte(), parcel.readByte(), parcel.readByte(), parcel.readInt(), parcel.createByteArray());
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleSetAIStateM4(parcel.readByte(), parcel.readByte(), parcel.readByte(), parcel.readInt(), parcel.readByte(), parcel.readByte(), parcel.createByteArray(), parcel.createByteArray());
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleChangeMode(parcel.readByte(), parcel.readByte(), parcel.readByte(), parcel.readString());
                    return true;
                case 13:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleSetAIOff();
                    return true;
                case 14:
                    parcel.enforceInterface(DESCRIPTOR);
                    addBleCallback(IBleCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 15:
                    parcel.enforceInterface(DESCRIPTOR);
                    removeBleCallback(IBleCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 16:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isLandscape = isLandscape();
                    parcel2.writeNoException();
                    parcel2.writeInt(isLandscape ? 1 : 0);
                    return true;
                case 17:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleSendKeyMap(parcel.createByteArray(), parcel.readInt());
                    return true;
                case 18:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleSendLongData(parcel.createByteArray());
                    return true;
                case 19:
                    parcel.enforceInterface(DESCRIPTOR);
                    bleSendShortData(parcel.createByteArray());
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class Proxy implements IThrone {
            public static IThrone sDefaultImpl;
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

            @Override // com.baidu.kwgames.IThrone
            public void setSystemStatusTask(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    if (this.mRemote.transact(1, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().setSystemStatusTask(z);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void setInMainUI(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    if (this.mRemote.transact(2, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().setInMainUI(z);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void bleConnect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (this.mRemote.transact(3, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleConnect(str);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void bleDisConnect() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(4, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleDisConnect();
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public boolean usbConnect() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().usbConnect();
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void usbDisconnect() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(6, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().usbDisconnect();
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void bleSetSystemStatus(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(7, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleSetSystemStatus(bArr);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void bleGetKeyMap(int i, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    if (this.mRemote.transact(8, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleGetKeyMap(i, i2, i3, i4, i5);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void bleGetGunPressAI() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(9, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleGetGunPressAI();
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void bleSetAIState(byte b, byte b2, byte b3, int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeByte(b2);
                    obtain.writeByte(b3);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(10, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleSetAIState(b, b2, b3, i, bArr);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void bleSetAIStateM4(byte b, byte b2, byte b3, int i, byte b4, byte b5, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeByte(b2);
                    obtain.writeByte(b3);
                    obtain.writeInt(i);
                    obtain.writeByte(b4);
                    obtain.writeByte(b5);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    try {
                        if (this.mRemote.transact(11, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                            obtain.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().bleSetAIStateM4(b, b2, b3, i, b4, b5, bArr, bArr2);
                        obtain.recycle();
                    } catch (Throwable th) {
                        th = th;
                        obtain.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void bleChangeMode(byte b, byte b2, byte b3, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeByte(b2);
                    obtain.writeByte(b3);
                    obtain.writeString(str);
                    if (this.mRemote.transact(12, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleChangeMode(b, b2, b3, str);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void bleSetAIOff() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(13, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleSetAIOff();
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void addBleCallback(IBleCallback iBleCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBleCallback != null ? iBleCallback.asBinder() : null);
                    if (this.mRemote.transact(14, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().addBleCallback(iBleCallback);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void removeBleCallback(IBleCallback iBleCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBleCallback != null ? iBleCallback.asBinder() : null);
                    if (this.mRemote.transact(15, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().removeBleCallback(iBleCallback);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public boolean isLandscape() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isLandscape();
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void bleSendKeyMap(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(17, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleSendKeyMap(bArr, i);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void bleSendLongData(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(18, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleSendLongData(bArr);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.baidu.kwgames.IThrone
            public void bleSendShortData(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(19, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().bleSendShortData(bArr);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IThrone iThrone) {
            if (Proxy.sDefaultImpl == null) {
                if (iThrone != null) {
                    Proxy.sDefaultImpl = iThrone;
                    return true;
                }
                return false;
            }
            throw new IllegalStateException("setDefaultImpl() called twice");
        }

        public static IThrone getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
