package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
/* JADX INFO: Access modifiers changed from: package-private */
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
public class FullLifecycleObserverAdapter implements GenericLifecycleObserver {
    private final FullLifecycleObserver mObserver;

    /* JADX INFO: Access modifiers changed from: package-private */
    public FullLifecycleObserverAdapter(FullLifecycleObserver fullLifecycleObserver) {
        this.mObserver = fullLifecycleObserver;
    }

    @Override // androidx.lifecycle.GenericLifecycleObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                this.mObserver.onCreate(lifecycleOwner);
                return;
            case ON_START:
                this.mObserver.onStart(lifecycleOwner);
                return;
            case ON_RESUME:
                this.mObserver.onResume(lifecycleOwner);
                return;
            case ON_PAUSE:
                this.mObserver.onPause(lifecycleOwner);
                return;
            case ON_STOP:
                this.mObserver.onStop(lifecycleOwner);
                return;
            case ON_DESTROY:
                this.mObserver.onDestroy(lifecycleOwner);
                return;
            case ON_ANY:
                throw new IllegalArgumentException("ON_ANY must not been send by anybody");
            default:
                return;
        }
    }
}
