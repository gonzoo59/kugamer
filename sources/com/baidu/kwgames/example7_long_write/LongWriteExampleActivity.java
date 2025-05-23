package com.baidu.kwgames.example7_long_write;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import com.baidu.kwgames.App;
import com.baidu.kwgames.example7_long_write.LongWriteExampleActivity;
import com.polidea.rxandroidble2.RxBleConnection;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import java.util.UUID;
/* loaded from: classes.dex */
public class LongWriteExampleActivity extends AppCompatActivity {
    public static final String DUMMY_DEVICE_ADDRESS = "AA:AA:AA:AA:AA:AA";
    private final byte[] bytesToWrite = new byte[1024];
    private Disposable disposable;
    private static final UUID DEVICE_CALLBACK_0 = UUID.randomUUID();
    private static final UUID DEVICE_CALLBACK_1 = UUID.randomUUID();
    private static final UUID WRITE_CHARACTERISTIC = UUID.randomUUID();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ ObservableSource lambda$onCreate$2(Observable observable) throws Exception {
        return observable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onCreate$3(byte[] bArr) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onCreate$4(Throwable th) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.disposable = App.getRxBleClient(this).getBleDevice(DUMMY_DEVICE_ADDRESS).establishConnection(false).flatMap(new Function() { // from class: com.baidu.kwgames.example7_long_write.LongWriteExampleActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource combineLatest;
                combineLatest = Observable.combineLatest(r1.setupNotification(LongWriteExampleActivity.DEVICE_CALLBACK_0), ((RxBleConnection) obj).setupNotification(LongWriteExampleActivity.DEVICE_CALLBACK_1), new BiFunction() { // from class: com.baidu.kwgames.example7_long_write.LongWriteExampleActivity$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.BiFunction
                    public final Object apply(Object obj2, Object obj3) {
                        return new Pair((Observable) obj2, (Observable) obj3);
                    }
                });
                return combineLatest;
            }
        }, new BiFunction() { // from class: com.baidu.kwgames.example7_long_write.LongWriteExampleActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return LongWriteExampleActivity.this.m127x322062ca((RxBleConnection) obj, (Pair) obj2);
            }
        }).flatMap(new Function() { // from class: com.baidu.kwgames.example7_long_write.LongWriteExampleActivity$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return LongWriteExampleActivity.lambda$onCreate$2((Observable) obj);
            }
        }).take(1L).subscribe(new Consumer() { // from class: com.baidu.kwgames.example7_long_write.LongWriteExampleActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                LongWriteExampleActivity.lambda$onCreate$3((byte[]) obj);
            }
        }, new Consumer() { // from class: com.baidu.kwgames.example7_long_write.LongWriteExampleActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                LongWriteExampleActivity.lambda$onCreate$4((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$1$com-baidu-kwgames-example7_long_write-LongWriteExampleActivity  reason: not valid java name */
    public /* synthetic */ Observable m127x322062ca(RxBleConnection rxBleConnection, Pair pair) throws Exception {
        return rxBleConnection.createNewLongWriteBuilder().setBytes(this.bytesToWrite).setCharacteristicUuid(WRITE_CHARACTERISTIC).setWriteOperationAckStrategy(new AnonymousClass1((Observable) pair.first, (Observable) pair.second)).build();
    }

    /* renamed from: com.baidu.kwgames.example7_long_write.LongWriteExampleActivity$1  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements RxBleConnection.WriteOperationAckStrategy {
        final /* synthetic */ Observable val$deviceCallback0;
        final /* synthetic */ Observable val$deviceCallback1;

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ Boolean lambda$apply$0(byte[] bArr, byte[] bArr2, Boolean bool) throws Exception {
            return bool;
        }

        AnonymousClass1(Observable observable, Observable observable2) {
            this.val$deviceCallback0 = observable;
            this.val$deviceCallback1 = observable2;
        }

        @Override // io.reactivex.ObservableTransformer
        public ObservableSource<Boolean> apply(Observable<Boolean> observable) {
            return Observable.zip(this.val$deviceCallback0, this.val$deviceCallback1, observable, new Function3() { // from class: com.baidu.kwgames.example7_long_write.LongWriteExampleActivity$1$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Function3
                public final Object apply(Object obj, Object obj2, Object obj3) {
                    return LongWriteExampleActivity.AnonymousClass1.lambda$apply$0((byte[]) obj, (byte[]) obj2, (Boolean) obj3);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.disposable;
        if (disposable != null) {
            disposable.dispose();
            this.disposable = null;
        }
    }
}
