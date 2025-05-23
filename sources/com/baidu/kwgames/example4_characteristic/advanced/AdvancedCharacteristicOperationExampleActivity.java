package com.baidu.kwgames.example4_characteristic.advanced;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.baidu.kwgames.App;
import com.baidu.kwgames.DeviceActivity;
import com.baidu.kwgames.R;
import com.baidu.kwgames.util.HexString;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding3.view.RxView;
import com.polidea.rxandroidble2.RxBleDevice;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.UUID;
import java.util.concurrent.Callable;
import kotlin.Unit;
/* loaded from: classes.dex */
public class AdvancedCharacteristicOperationExampleActivity extends AppCompatActivity {
    public static final String EXTRA_CHARACTERISTIC_UUID = "extra_uuid";
    private static final String TAG = "AdvancedCharacteristicOperationExampleActivity";
    private Disposable activityFlowDisposable;
    @BindView(R.id.compat_only_warning)
    TextView compatOnlyWarningTextView;
    @BindView(R.id.connect)
    Button connectButton;
    @BindView(R.id.indicate)
    Button indicateButton;
    @BindView(R.id.notify)
    Button notifyButton;
    private Observable<PresenterEvent> presenterEventObservable;
    @BindView(R.id.read)
    Button readButton;
    @BindView(R.id.read_hex_output)
    TextView readHexOutputView;
    @BindView(R.id.read_output)
    TextView readOutputView;
    @BindView(R.id.write)
    Button writeButton;
    @BindView(R.id.write_input)
    TextView writeInput;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ ObservableSource lambda$onCreate$8(Observable observable) throws Exception {
        return observable;
    }

    public static Intent startActivityIntent(Context context, String str, UUID uuid) {
        Intent intent = new Intent(context, AdvancedCharacteristicOperationExampleActivity.class);
        intent.putExtra(DeviceActivity.EXTRA_MAC_ADDRESS, str);
        intent.putExtra("extra_uuid", uuid);
        return intent;
    }

    private static Observable<Boolean> activatedClicksObservable(final Button button) {
        return Observable.using(new Callable() { // from class: com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity$$ExternalSyntheticLambda9
            /*  JADX ERROR: JadxRuntimeException in pass: InlineMethods
                jadx.core.utils.exceptions.JadxRuntimeException: Failed to process method for inline: com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity.lambda$activatedClicksObservable$0(android.widget.Button):android.widget.Button
                	at jadx.core.dex.visitors.InlineMethods.processInvokeInsn(InlineMethods.java:76)
                	at jadx.core.dex.visitors.InlineMethods.visit(InlineMethods.java:51)
                Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.isRegister()" because "arg" is null
                	at jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(RegisterArg.java:173)
                	at jadx.core.dex.instructions.args.InsnArg.isSameVar(InsnArg.java:269)
                	at jadx.core.dex.visitors.MarkMethodsForInline.isSyntheticAccessPattern(MarkMethodsForInline.java:118)
                	at jadx.core.dex.visitors.MarkMethodsForInline.inlineMth(MarkMethodsForInline.java:86)
                	at jadx.core.dex.visitors.MarkMethodsForInline.process(MarkMethodsForInline.java:53)
                	at jadx.core.dex.visitors.InlineMethods.processInvokeInsn(InlineMethods.java:65)
                	... 1 more
                */
            @Override // java.util.concurrent.Callable
            public final java.lang.Object call() {
                /*
                    r1 = this;
                    android.widget.Button r0 = r1
                    android.widget.Button r0 = com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity.lambda$activatedClicksObservable$0(r0)
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity$$ExternalSyntheticLambda9.call():java.lang.Object");
            }
        }, new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource map;
                map = RxView.clicks((Button) obj).map(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity$$ExternalSyntheticLambda8
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj2) {
                        Boolean bool;
                        Unit unit = (Unit) obj2;
                        bool = Boolean.TRUE;
                        return bool;
                    }
                });
                return map;
            }
        }, new Consumer() { // from class: com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ((Button) obj).setEnabled(false);
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).unsubscribeOn(AndroidSchedulers.mainThread());
    }

    /*  JADX ERROR: NullPointerException in pass: MarkMethodsForInline
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.isRegister()" because "arg" is null
        	at jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(RegisterArg.java:173)
        	at jadx.core.dex.instructions.args.InsnArg.isSameVar(InsnArg.java:269)
        	at jadx.core.dex.visitors.MarkMethodsForInline.isSyntheticAccessPattern(MarkMethodsForInline.java:118)
        	at jadx.core.dex.visitors.MarkMethodsForInline.inlineMth(MarkMethodsForInline.java:86)
        	at jadx.core.dex.visitors.MarkMethodsForInline.process(MarkMethodsForInline.java:53)
        	at jadx.core.dex.visitors.MarkMethodsForInline.visit(MarkMethodsForInline.java:37)
        */
    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ android.widget.Button lambda$activatedClicksObservable$0(android.widget.Button r1) throws java.lang.Exception {
        /*
            r0 = 1
            r1.setEnabled(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity.lambda$activatedClicksObservable$0(android.widget.Button):android.widget.Button");
    }

    private static ObservableTransformer<Boolean, Boolean> onSubscribeSetText(final Button button, final int i) {
        return new ObservableTransformer() { // from class: com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.ObservableTransformer
            public final ObservableSource apply(Observable observable) {
                ObservableSource subscribeOn;
                subscribeOn = observable.doOnSubscribe(new Consumer() { // from class: com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        Disposable disposable = (Disposable) obj;
                        r1.setText(r2);
                    }
                }).subscribeOn(AndroidSchedulers.mainThread());
                return subscribeOn;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_example4_advanced);
        ButterKnife.bind(this);
        String stringExtra = getIntent().getStringExtra(DeviceActivity.EXTRA_MAC_ADDRESS);
        UUID uuid = (UUID) getIntent().getSerializableExtra("extra_uuid");
        RxBleDevice bleDevice = App.getRxBleClient(this).getBleDevice(stringExtra);
        getSupportActionBar().setSubtitle(getString(R.string.mac_address, new Object[]{stringExtra}));
        Observable<Boolean> share = activatedClicksObservable(this.connectButton).share();
        Observable<Boolean> share2 = activatedClicksObservable(this.notifyButton).share();
        Observable<Boolean> share3 = activatedClicksObservable(this.indicateButton).share();
        this.presenterEventObservable = Presenter.prepareActivityLogic(bleDevice, uuid, share.compose(onSubscribeSetText(this.connectButton, R.string.connect)), share.compose(onSubscribeSetText(this.connectButton, R.string.connecting)), share.compose(onSubscribeSetText(this.connectButton, R.string.disconnect)), activatedClicksObservable(this.readButton), activatedClicksObservable(this.writeButton).map(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return AdvancedCharacteristicOperationExampleActivity.this.m124x827178ed((Boolean) obj);
            }
        }).doOnError(new Consumer() { // from class: com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AdvancedCharacteristicOperationExampleActivity.this.m125xa12bb0ae((Throwable) obj);
            }
        }).retryWhen(new Function() { // from class: com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return AdvancedCharacteristicOperationExampleActivity.lambda$onCreate$8((Observable) obj);
            }
        }), share2.compose(onSubscribeSetText(this.notifyButton, R.string.setup_notification)), share2.compose(onSubscribeSetText(this.notifyButton, R.string.setting_notification)), share2.compose(onSubscribeSetText(this.notifyButton, R.string.teardown_notification)), share3.compose(onSubscribeSetText(this.indicateButton, R.string.setup_indication)), share3.compose(onSubscribeSetText(this.indicateButton, R.string.setting_indication)), share3.compose(onSubscribeSetText(this.indicateButton, R.string.teardown_indication)));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$6$com-baidu-kwgames-example4_characteristic-advanced-AdvancedCharacteristicOperationExampleActivity  reason: not valid java name */
    public /* synthetic */ byte[] m124x827178ed(Boolean bool) throws Exception {
        return getInputBytes();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$7$com-baidu-kwgames-example4_characteristic-advanced-AdvancedCharacteristicOperationExampleActivity  reason: not valid java name */
    public /* synthetic */ void m125xa12bb0ae(Throwable th) throws Exception {
        showNotification("Could not parse input: " + th);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.activityFlowDisposable = this.presenterEventObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AdvancedCharacteristicOperationExampleActivity.this.handleEvent((PresenterEvent) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.activityFlowDisposable.dispose();
        this.activityFlowDisposable = null;
    }

    private byte[] getInputBytes() {
        return HexString.hexToBytes(this.writeInput.getText().toString());
    }

    private void showNotification(String str) {
        Snackbar.make(findViewById(R.id.main), str, -1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleEvent(PresenterEvent presenterEvent) {
        String str;
        String str2 = TAG;
        Log.i(str2, presenterEvent.toString());
        if (presenterEvent instanceof InfoEvent) {
            showNotification(((InfoEvent) presenterEvent).infoText);
        }
        if (presenterEvent instanceof CompatibilityModeEvent) {
            boolean z = ((CompatibilityModeEvent) presenterEvent).show;
            this.compatOnlyWarningTextView.setVisibility(z ? 0 : 4);
            if (z) {
                Log.e(str2, "THIS PERIPHERAL CHARACTERISTIC HAS PROPERTY_NOTIFY OR PROPERTY_INDICATE BUT DOES NOT HAVE CLIENT CHARACTERISTIC CONFIG DESCRIPTOR WHICH VIOLATES BLUETOOTH SPECIFICATION - CONTACT THE FIRMWARE DEVELOPER TO FIX IF POSSIBLE");
            }
        }
        if (presenterEvent instanceof ResultEvent) {
            ResultEvent resultEvent = (ResultEvent) presenterEvent;
            int i = AnonymousClass1.$SwitchMap$com$baidu$kwgames$example4_characteristic$advanced$Type[resultEvent.type.ordinal()];
            if (i == 1) {
                byte[] bArr = resultEvent.result;
                this.readOutputView.setText(new String(bArr));
                String bytesToHex = HexString.bytesToHex(bArr);
                this.readHexOutputView.setText(bytesToHex);
                this.writeInput.setText(bytesToHex);
            } else if (i == 2) {
                showNotification("Write success");
            } else if (i == 3) {
                showNotification("Notification: " + HexString.bytesToHex(resultEvent.result));
            } else {
                showNotification("Indication: " + HexString.bytesToHex(resultEvent.result));
            }
        }
        if (presenterEvent instanceof ErrorEvent) {
            ErrorEvent errorEvent = (ErrorEvent) presenterEvent;
            Throwable th = errorEvent.error;
            int i2 = AnonymousClass1.$SwitchMap$com$baidu$kwgames$example4_characteristic$advanced$Type[errorEvent.type.ordinal()];
            if (i2 == 1) {
                str = "Read error: " + th;
            } else if (i2 == 2) {
                str = "Write error: " + th;
            } else if (i2 == 3) {
                str = "Notifications error: " + th;
            } else {
                str = "Indications error: " + th;
            }
            showNotification(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.example4_characteristic.advanced.AdvancedCharacteristicOperationExampleActivity$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$baidu$kwgames$example4_characteristic$advanced$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$com$baidu$kwgames$example4_characteristic$advanced$Type = iArr;
            try {
                iArr[Type.READ.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$baidu$kwgames$example4_characteristic$advanced$Type[Type.WRITE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$baidu$kwgames$example4_characteristic$advanced$Type[Type.NOTIFY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$baidu$kwgames$example4_characteristic$advanced$Type[Type.INDICATE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }
}
