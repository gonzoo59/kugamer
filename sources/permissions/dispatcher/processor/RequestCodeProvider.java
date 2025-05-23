package permissions.dispatcher.processor;

import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
/* compiled from: RequestCodeProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lpermissions/dispatcher/processor/RequestCodeProvider;", "", "()V", "currentCode", "Ljava/util/concurrent/atomic/AtomicInteger;", "nextRequestCode", "", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class RequestCodeProvider {
    private final AtomicInteger currentCode = new AtomicInteger(0);

    public final int nextRequestCode() {
        return this.currentCode.getAndIncrement();
    }
}
