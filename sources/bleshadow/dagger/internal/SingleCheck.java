package bleshadow.dagger.internal;

import bleshadow.dagger.Lazy;
import bleshadow.javax.inject.Provider;
/* loaded from: classes.dex */
public final class SingleCheck<T> implements Provider<T>, Lazy<T> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Object UNINITIALIZED = new Object();
    private volatile Object instance = UNINITIALIZED;
    private volatile Provider<T> provider;

    private SingleCheck(Provider<T> provider) {
        this.provider = provider;
    }

    @Override // bleshadow.javax.inject.Provider
    public T get() {
        Provider<T> provider = this.provider;
        if (this.instance == UNINITIALIZED) {
            this.instance = provider.get();
            this.provider = null;
        }
        return (T) this.instance;
    }

    public static <P extends Provider<T>, T> Provider<T> provider(P provider) {
        return ((provider instanceof SingleCheck) || (provider instanceof DoubleCheck)) ? provider : new SingleCheck((Provider) Preconditions.checkNotNull(provider));
    }
}
