package io.reactivex.internal.fuseable;

import java.util.concurrent.Callable;
/* loaded from: classes2.dex */
public interface ScalarCallable<T> extends Callable<T> {
    @Override // java.util.concurrent.Callable
    T call();
}
