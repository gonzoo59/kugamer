package org.opencv.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.io.File;
import java.util.StringTokenizer;
import org.opencv.core.Core;
import org.opencv.engine.OpenCVEngineInterface;
import org.opencv.videoio.Videoio;
/* loaded from: classes2.dex */
class AsyncServiceHelper {
    protected static final int MINIMUM_ENGINE_VERSION = 2;
    protected static final String OPEN_CV_SERVICE_URL = "market://details?id=org.opencv.engine";
    protected static final String TAG = "OpenCVManager/Helper";
    protected static boolean mLibraryInstallationProgress = false;
    protected static boolean mServiceInstallationProgress = false;
    protected Context mAppContext;
    protected OpenCVEngineInterface mEngineService;
    protected String mOpenCVersion;
    protected ServiceConnection mServiceConnection = new ServiceConnection() { // from class: org.opencv.android.AsyncServiceHelper.3
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(AsyncServiceHelper.TAG, "Service connection created");
            AsyncServiceHelper.this.mEngineService = OpenCVEngineInterface.Stub.asInterface(iBinder);
            if (AsyncServiceHelper.this.mEngineService == null) {
                Log.d(AsyncServiceHelper.TAG, "OpenCV Manager Service connection fails. May be service was not installed?");
                AsyncServiceHelper.InstallService(AsyncServiceHelper.this.mAppContext, AsyncServiceHelper.this.mUserAppCallback);
                return;
            }
            int i = 0;
            AsyncServiceHelper.mServiceInstallationProgress = false;
            try {
                if (AsyncServiceHelper.this.mEngineService.getEngineVersion() < 2) {
                    Log.d(AsyncServiceHelper.TAG, "Init finished with status 4");
                    Log.d(AsyncServiceHelper.TAG, "Unbind from service");
                    AsyncServiceHelper.this.mAppContext.unbindService(AsyncServiceHelper.this.mServiceConnection);
                    Log.d(AsyncServiceHelper.TAG, "Calling using callback");
                    AsyncServiceHelper.this.mUserAppCallback.onManagerConnected(4);
                    return;
                }
                Log.d(AsyncServiceHelper.TAG, "Trying to get library path");
                String libPathByVersion = AsyncServiceHelper.this.mEngineService.getLibPathByVersion(AsyncServiceHelper.this.mOpenCVersion);
                if (libPathByVersion != null && libPathByVersion.length() != 0) {
                    Log.d(AsyncServiceHelper.TAG, "Trying to get library list");
                    AsyncServiceHelper.mLibraryInstallationProgress = false;
                    String libraryList = AsyncServiceHelper.this.mEngineService.getLibraryList(AsyncServiceHelper.this.mOpenCVersion);
                    Log.d(AsyncServiceHelper.TAG, "Library list: \"" + libraryList + "\"");
                    Log.d(AsyncServiceHelper.TAG, "First attempt to load libs");
                    if (AsyncServiceHelper.this.initOpenCVLibs(libPathByVersion, libraryList)) {
                        Log.d(AsyncServiceHelper.TAG, "First attempt to load libs is OK");
                        for (String str : Core.getBuildInformation().split(System.getProperty("line.separator"))) {
                            Log.i(AsyncServiceHelper.TAG, str);
                        }
                    } else {
                        Log.d(AsyncServiceHelper.TAG, "First attempt to load libs fails");
                        i = 255;
                    }
                    Log.d(AsyncServiceHelper.TAG, "Init finished with status " + i);
                    Log.d(AsyncServiceHelper.TAG, "Unbind from service");
                    AsyncServiceHelper.this.mAppContext.unbindService(AsyncServiceHelper.this.mServiceConnection);
                    Log.d(AsyncServiceHelper.TAG, "Calling using callback");
                    AsyncServiceHelper.this.mUserAppCallback.onManagerConnected(i);
                    return;
                }
                if (!AsyncServiceHelper.mLibraryInstallationProgress) {
                    AsyncServiceHelper.this.mUserAppCallback.onPackageInstall(0, new InstallCallbackInterface() { // from class: org.opencv.android.AsyncServiceHelper.3.1
                        @Override // org.opencv.android.InstallCallbackInterface
                        public String getPackageName() {
                            return "OpenCV library";
                        }

                        @Override // org.opencv.android.InstallCallbackInterface
                        public void install() {
                            Log.d(AsyncServiceHelper.TAG, "Trying to install OpenCV lib via Google Play");
                            try {
                                if (AsyncServiceHelper.this.mEngineService.installVersion(AsyncServiceHelper.this.mOpenCVersion)) {
                                    AsyncServiceHelper.mLibraryInstallationProgress = true;
                                    Log.d(AsyncServiceHelper.TAG, "Package installation started");
                                    Log.d(AsyncServiceHelper.TAG, "Unbind from service");
                                    AsyncServiceHelper.this.mAppContext.unbindService(AsyncServiceHelper.this.mServiceConnection);
                                } else {
                                    Log.d(AsyncServiceHelper.TAG, "OpenCV package was not installed!");
                                    Log.d(AsyncServiceHelper.TAG, "Init finished with status 2");
                                    Log.d(AsyncServiceHelper.TAG, "Unbind from service");
                                    AsyncServiceHelper.this.mAppContext.unbindService(AsyncServiceHelper.this.mServiceConnection);
                                    Log.d(AsyncServiceHelper.TAG, "Calling using callback");
                                    AsyncServiceHelper.this.mUserAppCallback.onManagerConnected(2);
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                Log.d(AsyncServiceHelper.TAG, "Init finished with status 255");
                                Log.d(AsyncServiceHelper.TAG, "Unbind from service");
                                AsyncServiceHelper.this.mAppContext.unbindService(AsyncServiceHelper.this.mServiceConnection);
                                Log.d(AsyncServiceHelper.TAG, "Calling using callback");
                                AsyncServiceHelper.this.mUserAppCallback.onManagerConnected(255);
                            }
                        }

                        @Override // org.opencv.android.InstallCallbackInterface
                        public void cancel() {
                            Log.d(AsyncServiceHelper.TAG, "OpenCV library installation was canceled");
                            Log.d(AsyncServiceHelper.TAG, "Init finished with status 3");
                            Log.d(AsyncServiceHelper.TAG, "Unbind from service");
                            AsyncServiceHelper.this.mAppContext.unbindService(AsyncServiceHelper.this.mServiceConnection);
                            Log.d(AsyncServiceHelper.TAG, "Calling using callback");
                            AsyncServiceHelper.this.mUserAppCallback.onManagerConnected(3);
                        }

                        @Override // org.opencv.android.InstallCallbackInterface
                        public void wait_install() {
                            Log.e(AsyncServiceHelper.TAG, "Installation was not started! Nothing to wait!");
                        }
                    });
                    return;
                }
                AsyncServiceHelper.this.mUserAppCallback.onPackageInstall(1, new InstallCallbackInterface() { // from class: org.opencv.android.AsyncServiceHelper.3.2
                    @Override // org.opencv.android.InstallCallbackInterface
                    public String getPackageName() {
                        return "OpenCV library";
                    }

                    @Override // org.opencv.android.InstallCallbackInterface
                    public void install() {
                        Log.e(AsyncServiceHelper.TAG, "Nothing to install we just wait current installation");
                    }

                    @Override // org.opencv.android.InstallCallbackInterface
                    public void cancel() {
                        Log.d(AsyncServiceHelper.TAG, "OpenCV library installation was canceled");
                        AsyncServiceHelper.mLibraryInstallationProgress = false;
                        Log.d(AsyncServiceHelper.TAG, "Init finished with status 3");
                        Log.d(AsyncServiceHelper.TAG, "Unbind from service");
                        AsyncServiceHelper.this.mAppContext.unbindService(AsyncServiceHelper.this.mServiceConnection);
                        Log.d(AsyncServiceHelper.TAG, "Calling using callback");
                        AsyncServiceHelper.this.mUserAppCallback.onManagerConnected(3);
                    }

                    @Override // org.opencv.android.InstallCallbackInterface
                    public void wait_install() {
                        Log.d(AsyncServiceHelper.TAG, "Waiting for current installation");
                        try {
                            if (!AsyncServiceHelper.this.mEngineService.installVersion(AsyncServiceHelper.this.mOpenCVersion)) {
                                Log.d(AsyncServiceHelper.TAG, "OpenCV package was not installed!");
                                Log.d(AsyncServiceHelper.TAG, "Init finished with status 2");
                                Log.d(AsyncServiceHelper.TAG, "Calling using callback");
                                AsyncServiceHelper.this.mUserAppCallback.onManagerConnected(2);
                            } else {
                                Log.d(AsyncServiceHelper.TAG, "Waiting for package installation");
                            }
                            Log.d(AsyncServiceHelper.TAG, "Unbind from service");
                            AsyncServiceHelper.this.mAppContext.unbindService(AsyncServiceHelper.this.mServiceConnection);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            Log.d(AsyncServiceHelper.TAG, "Init finished with status 255");
                            Log.d(AsyncServiceHelper.TAG, "Unbind from service");
                            AsyncServiceHelper.this.mAppContext.unbindService(AsyncServiceHelper.this.mServiceConnection);
                            Log.d(AsyncServiceHelper.TAG, "Calling using callback");
                            AsyncServiceHelper.this.mUserAppCallback.onManagerConnected(255);
                        }
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.d(AsyncServiceHelper.TAG, "Init finished with status 255");
                Log.d(AsyncServiceHelper.TAG, "Unbind from service");
                AsyncServiceHelper.this.mAppContext.unbindService(AsyncServiceHelper.this.mServiceConnection);
                Log.d(AsyncServiceHelper.TAG, "Calling using callback");
                AsyncServiceHelper.this.mUserAppCallback.onManagerConnected(255);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            AsyncServiceHelper.this.mEngineService = null;
        }
    };
    protected LoaderCallbackInterface mUserAppCallback;

    public static boolean initOpenCV(String str, Context context, LoaderCallbackInterface loaderCallbackInterface) {
        AsyncServiceHelper asyncServiceHelper = new AsyncServiceHelper(str, context, loaderCallbackInterface);
        Intent intent = new Intent("org.opencv.engine.BIND");
        intent.setPackage("org.opencv.engine");
        if (context.bindService(intent, asyncServiceHelper.mServiceConnection, 1)) {
            return true;
        }
        context.unbindService(asyncServiceHelper.mServiceConnection);
        InstallService(context, loaderCallbackInterface);
        return false;
    }

    protected AsyncServiceHelper(String str, Context context, LoaderCallbackInterface loaderCallbackInterface) {
        this.mOpenCVersion = str;
        this.mUserAppCallback = loaderCallbackInterface;
        this.mAppContext = context;
    }

    protected static boolean InstallServiceQuiet(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(OPEN_CV_SERVICE_URL));
            intent.addFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            context.startActivity(intent);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    protected static void InstallService(Context context, LoaderCallbackInterface loaderCallbackInterface) {
        if (!mServiceInstallationProgress) {
            Log.d(TAG, "Request new service installation");
            loaderCallbackInterface.onPackageInstall(0, new InstallCallbackInterface(context) { // from class: org.opencv.android.AsyncServiceHelper.1
                private LoaderCallbackInterface mUserAppCallback;
                final /* synthetic */ Context val$AppContext;

                @Override // org.opencv.android.InstallCallbackInterface
                public String getPackageName() {
                    return "OpenCV Manager";
                }

                {
                    this.val$AppContext = context;
                    this.mUserAppCallback = LoaderCallbackInterface.this;
                }

                @Override // org.opencv.android.InstallCallbackInterface
                public void install() {
                    Log.d(AsyncServiceHelper.TAG, "Trying to install OpenCV Manager via Google Play");
                    if (AsyncServiceHelper.InstallServiceQuiet(this.val$AppContext)) {
                        AsyncServiceHelper.mServiceInstallationProgress = true;
                        Log.d(AsyncServiceHelper.TAG, "Package installation started");
                        return;
                    }
                    Log.d(AsyncServiceHelper.TAG, "OpenCV package was not installed!");
                    Log.d(AsyncServiceHelper.TAG, "Init finished with status 2");
                    Log.d(AsyncServiceHelper.TAG, "Unbind from service");
                    Log.d(AsyncServiceHelper.TAG, "Calling using callback");
                    this.mUserAppCallback.onManagerConnected(2);
                }

                @Override // org.opencv.android.InstallCallbackInterface
                public void cancel() {
                    Log.d(AsyncServiceHelper.TAG, "OpenCV library installation was canceled");
                    Log.d(AsyncServiceHelper.TAG, "Init finished with status 3");
                    Log.d(AsyncServiceHelper.TAG, "Calling using callback");
                    this.mUserAppCallback.onManagerConnected(3);
                }

                @Override // org.opencv.android.InstallCallbackInterface
                public void wait_install() {
                    Log.e(AsyncServiceHelper.TAG, "Installation was not started! Nothing to wait!");
                }
            });
            return;
        }
        Log.d(TAG, "Waiting current installation process");
        loaderCallbackInterface.onPackageInstall(1, new InstallCallbackInterface(context) { // from class: org.opencv.android.AsyncServiceHelper.2
            private LoaderCallbackInterface mUserAppCallback;
            final /* synthetic */ Context val$AppContext;

            @Override // org.opencv.android.InstallCallbackInterface
            public String getPackageName() {
                return "OpenCV Manager";
            }

            {
                this.val$AppContext = context;
                this.mUserAppCallback = LoaderCallbackInterface.this;
            }

            @Override // org.opencv.android.InstallCallbackInterface
            public void install() {
                Log.e(AsyncServiceHelper.TAG, "Nothing to install we just wait current installation");
            }

            @Override // org.opencv.android.InstallCallbackInterface
            public void cancel() {
                Log.d(AsyncServiceHelper.TAG, "Waiting for OpenCV canceled by user");
                AsyncServiceHelper.mServiceInstallationProgress = false;
                Log.d(AsyncServiceHelper.TAG, "Init finished with status 3");
                Log.d(AsyncServiceHelper.TAG, "Calling using callback");
                this.mUserAppCallback.onManagerConnected(3);
            }

            @Override // org.opencv.android.InstallCallbackInterface
            public void wait_install() {
                AsyncServiceHelper.InstallServiceQuiet(this.val$AppContext);
            }
        });
    }

    private boolean loadLibrary(String str) {
        Log.d(TAG, "Trying to load library " + str);
        try {
            System.load(str);
            Log.d(TAG, "OpenCV libs init was ok!");
            return true;
        } catch (UnsatisfiedLinkError e) {
            Log.d(TAG, "Cannot load library \"" + str + "\"");
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean initOpenCVLibs(String str, String str2) {
        Log.d(TAG, "Trying to init OpenCV libs");
        if (str != null && str.length() != 0) {
            boolean z = true;
            if (str2 != null && str2.length() != 0) {
                Log.d(TAG, "Trying to load libs by dependency list");
                StringTokenizer stringTokenizer = new StringTokenizer(str2, ";");
                while (stringTokenizer.hasMoreTokens()) {
                    z &= loadLibrary(str + File.separator + stringTokenizer.nextToken());
                }
                return z;
            }
            return loadLibrary(str + File.separator + "libopencv_java4.so");
        }
        Log.d(TAG, "Library path \"" + str + "\" is empty");
        return false;
    }
}
