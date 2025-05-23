package com.baidu.kwgames.net;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/* loaded from: classes.dex */
public class HttpHelper {
    private static final int REQUEST_SUCCESS = 1;
    private static final int THROWABLE_ERROR = -1000;

    public static Observable<String> request(final String str) {
        return Observable.create(new ObservableOnSubscribe<String>() { // from class: com.baidu.kwgames.net.HttpHelper.1
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<String> observableEmitter) throws Exception {
                try {
                    new OkHttpClient().newCall(new Request.Builder().url(str).build()).enqueue(new Callback() { // from class: com.baidu.kwgames.net.HttpHelper.1.1
                        @Override // okhttp3.Callback
                        public void onFailure(Call call, IOException iOException) {
                            iOException.printStackTrace();
                            try {
                                observableEmitter.onError(iOException);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override // okhttp3.Callback
                        public void onResponse(Call call, Response response) throws IOException {
                            observableEmitter.onNext(response.body().string());
                            observableEmitter.onComplete();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
